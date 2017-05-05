package com.qingju.java.common.pay.http;

import com.qingju.java.common.pay.ConstantPay;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/* *
 *类名：HttpProtocolHandler
 *功能：HttpClient方式访问
 *详细：获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-17
 *说明：
 */
@Slf4j
public class HttpProtocolHandler {

    private static String              DEFAULT_CHARSET                     = "GBK";

    /** 连接超时时间，由bean factory设置，缺省为8秒钟 */
    private int                        defaultConnectionTimeout            = 8000;

    /** 回应超时时间, 由bean factory设置，缺省为10秒钟 */
    private int                        defaultSoTimeout                    = 10000;

    /** 默认等待HttpConnectionManager返回连接超时（只有在达到最大连接数时起作用）：1秒*/
    private static final long          defaultHttpConnectionManagerTimeout = 3 * 1000;


    /** 平台标识 0-本地,1-开发,2-测试，3-正式 ;传入其他值表示可以任意地址 */
    private static final String PLATFLG = "3";

    /**
     * 工厂方法
     *
     * @return
     */
    public static HttpProtocolHandler getInstance() {
        return new HttpProtocolHandler();
    }

    /**
     * 私有的构造方法
     */
//    private HttpProtocolHandler() {
    // 创建一个线程安全的HTTP连接池
//        connectionManager = new MultiThreadedHttpConnectionManager();
//        connectionManager.getParams().setDefaultMaxConnectionsPerHost(defaultMaxConnPerHost);
//        connectionManager.getParams().setMaxTotalConnections(defaultMaxTotalConn);
//
//        IdleConnectionTimeoutThread ict = new IdleConnectionTimeoutThread();
//        ict.addConnectionManager(connectionManager);
//        ict.setConnectionTimeout(defaultIdleConnTimeout);
//
//        ict.start();
//    }

    /**
     * 执行Http请求
     *
     * @param request 请求数据
     * @param strParaFileName 文件类型的参数名
     * @param strFilePath 文件路径
     * @return
     * @throws HttpException, IOException
     */
    public HttpResponse execute(HttpRequest request, String strParaFileName, String strFilePath) throws HttpException, IOException {
        HttpClient httpclient = new HttpClient();

        // 设置连接超时
        int connectionTimeout = defaultConnectionTimeout;
        if (request.getConnectionTimeout() > 0) {
            connectionTimeout = request.getConnectionTimeout();
        }
        httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeout);

        // 设置回应超时
        int soTimeout = defaultSoTimeout;
        if (request.getTimeout() > 0) {
            soTimeout = request.getTimeout();
        }
        httpclient.getHttpConnectionManager().getParams().setSoTimeout(soTimeout);

        // 设置等待ConnectionManager释放connection的时间
        httpclient.getParams().setConnectionManagerTimeout(defaultHttpConnectionManagerTimeout);

        String charset = request.getCharset();
        charset = charset == null ? DEFAULT_CHARSET : charset;
        HttpMethod method;

        //get模式且不带上传文件
        if (request.getMethod().equals(HttpRequest.METHOD_GET)) {
            method = new GetMethod(request.getUrl());
            method.getParams().setCredentialCharset(charset);

            // parseNotifyConfig会保证使用GET方法时，request一定使用QueryString
            method.setQueryString(request.getQueryString());
        } else if(strParaFileName.equals("") && strFilePath.equals("")) {
            //post模式且不带上传文件
            method = new PostMethod(request.getUrl());
            ((PostMethod) method).addParameters(request.getParameters());
            method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=" + charset);
        }
        else {
            //post模式且带上传文件
            method = new PostMethod(request.getUrl());
            List<Part> parts = new ArrayList<Part>();
            for (int i = 0; i < request.getParameters().length; i++) {
                parts.add(new StringPart(request.getParameters()[i].getName(), request.getParameters()[i].getValue(), charset));
            }
            //增加文件参数，strParaFileName是参数名，使用本地文件
            parts.add(new FilePart(strParaFileName, new FilePartSource(new File(strFilePath))));

            // 设置请求体
            ((PostMethod) method).setRequestEntity(new MultipartRequestEntity(parts.toArray(new Part[0]), new HttpMethodParams()));
        }

        // 设置Http Header中的User-Agent属性
        method.addRequestHeader("User-Agent", "Mozilla/4.0");
        HttpResponse response = new HttpResponse();

        try {
            httpclient.executeMethod(method);
            if (request.getResultType().equals(HttpResultType.STRING)) {
                response.setStringResult(method.getResponseBodyAsString());
            } else if (request.getResultType().equals(HttpResultType.BYTES)) {
                response.setByteResult(method.getResponseBody());
            }
            response.setResponseHeaders(method.getResponseHeaders());
        } catch (UnknownHostException ex) {
            log.error("[" + request.getUrl() + "] " + ex.getMessage());
            return null;
        } catch (IOException ex) {
            log.error("[" + request.getUrl() + "] " + ex.getMessage());
            return null;
        } catch (Exception ex) {
            log.error("[" + request.getUrl() + "] " + ex.getMessage());
            return null;
        } finally {
            method.releaseConnection();
        }
        return response;
    }

    public HttpResponse sslXmlPost(String url, String certFile, String certPass, String content) throws IOException {
        CloseableHttpClient closeableHttpClient = null;
        HttpResponse httpResponse = null;
        ClientCustomSSL clientCustomSSL = ClientCustomSSL.getInstance();
        closeableHttpClient = clientCustomSSL.getWCCloseableHttpClient(certFile, certPass);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "text/xml");
        //封装发送请求的xml参数
        StringEntity entity = new StringEntity(content, "UTF-8");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
        BufferedReader bufferedReader = null;
        try {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                httpResponse = new HttpResponse();
                bufferedReader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
                StringBuilder text = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    text.append(line);
                }
                httpResponse.setStringResult(text.toString());
            }
            EntityUtils.consume(responseEntity);
        } finally {
            response.close();
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return httpResponse;
    }

    public HttpResponse xmlPost(String url, String content) throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.custom().build();
        HttpResponse httpResponse = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "text/xml");
        //封装发送请求的xml参数
        StringEntity entity = new StringEntity(content, "UTF-8");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
        BufferedReader bufferedReader = null;
        try {
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                httpResponse = new HttpResponse();
                bufferedReader = new BufferedReader(new InputStreamReader(responseEntity.getContent()));
                StringBuilder text = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    text.append(line);
                }
                httpResponse.setStringResult(text.toString());
            }
            EntityUtils.consume(responseEntity);
        } finally {
            response.close();
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return httpResponse;
    }

    /**
     *
     * @Title: sendHttpRequest
     * @Description: 发送地址
     *            :0-本地,1-开发,2-测试，3-正式
     * @param url
     * @param data
     * @return
     * @throws Exception
     *
     * @since 1.0
     */
    public static String sendHttpRequest(String url, String data) throws Exception {
        // 创建链接
        HttpURLConnection hconn;
        OutputStream os;
        InputStream is;
        hconn = (HttpURLConnection) new URL(ConstantPay.ZLINE_URL.get(PLATFLG) + url).openConnection();
        hconn.setRequestMethod(HttpRequest.METHOD_POST); // 设置为post请求
        hconn.setDoInput(true);
        hconn.setDoOutput(true);
        hconn.setUseCaches(false);
        hconn.setRequestProperty("Content-Type", "application/json");
        hconn.setConnectTimeout(ConstantPay.REQUEST_TIME_OUT); // 30s
        hconn.setReadTimeout(ConstantPay.REQUEST_TIME_OUT); // 30s
        // 发送数据
        os = hconn.getOutputStream();
        byte[] f = data.getBytes(ConstantPay.CHARSET_UTF8);
        os.write(f, 0, f.length);
        os.flush();
        // 接收数据
        is = hconn.getInputStream();
        List<Byte> byteList = new ArrayList<>();
        byte[] buf = new byte[1];
        while ((is.read(buf)) > 0) {
            byteList.add(buf[0]);
        }
        is.close();
        hconn.disconnect();

        String recStr = "";
        int size = byteList.size();
        if (size > 0) {
            byte[] b = new byte[size];
            for (int i = 0; i < size; i++) {
                b[i] = byteList.get(i);
            }
            recStr = new String(b, ConstantPay.CHARSET_UTF8);
        }
        return recStr;

    }

    /**
     * 将NameValuePairs数组转变为字符串
     *
     * @param nameValues
     * @return
     */
    protected String toString(NameValuePair[] nameValues) {
        if (nameValues == null || nameValues.length == 0) {
            return "null";
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < nameValues.length; i++) {
            NameValuePair nameValue = nameValues[i];

            if (i == 0) {
                buffer.append(nameValue.getName() + "=" + nameValue.getValue());
            } else {
                buffer.append("&" + nameValue.getName() + "=" + nameValue.getValue());
            }
        }

        return buffer.toString();
    }

}
