package com.qingju.java.common.pay.core.vo;

import com.qingju.java.common.pay.ConstantPay;
import com.qingju.java.common.pay.http.HttpProtocolHandler;
import com.qingju.java.common.pay.http.HttpRequest;
import com.qingju.java.common.pay.http.HttpResponse;
import com.qingju.java.common.pay.sign.MD5;
import com.qingju.java.common.pay.sign.RSA;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
public class BaseTrade {


	public String get(HttpRequest request){
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
		HttpResponse response;
		String strResult = null;
		try {
			log.info("Post url: [" + request.getUrl() + "] \n" + " params: [" +  request.getQueryString() + "]");
			response = httpProtocolHandler.execute(request, "", "");
			strResult = getResult(response);
		} catch (Exception e) {
			log.error("BaseTrade->get exception: " + e.getMessage());
		}
		return strResult;
	}


	public String post(HttpRequest request, String  strParaFileName, String  strFilePath){
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
		HttpResponse response;
		String strResult = null;
		try {
			log.info("Post url: [" + request.getUrl() + "] \n" + " params: [" +getParametersStr( request.getParameters() )+ "]");
			response = httpProtocolHandler.execute(request,strParaFileName,strFilePath);
			strResult = getResultFromHeader(response);
		} catch (Exception e) {
			log.error("BaseTrade->post exception: " + e.getMessage());
		}
		return strResult;
	}

	public String sslXmlPost(String url, String certFile, String certPass, String content){
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
		HttpResponse response = null;
		try {
			log.info("Post url: [" + url + "] \n" + " params: [" + content + "]");
			response = httpProtocolHandler.sslXmlPost(url, certFile, certPass, content);
		} catch (Exception e) {
			log.error("BaseTrade->post exception: " + e.getMessage());
		}
		return getResult(response);
	}

    public String xmlPost(String url, String content){
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        HttpResponse response = null;
        try {
            log.info("Post url: [" + url + "] \n" + " params: [" + content + "]");
            response = httpProtocolHandler.xmlPost(url, content);
        } catch (Exception e) {
            log.error("BaseTrade->post exception: " + e.getMessage());
        }
        return getResult(response);
    }

	public String zlinePost(String url, String data){
		String response = null;
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
		try {
			log.info("Post url: [" + url + "] \n" + " params: [" + data + "]");
			response = httpProtocolHandler.sendHttpRequest(url, data);
		} catch (Exception e) {
			log.error("BaseTrade->post exception: " + e.getMessage());
		}
		return response;
	}

	private String getResultFromHeader(HttpResponse response){
		if (response == null) {
			return "";
		}
		String strResult = null;
		try {
			strResult = response.getStringResult();
			if (StringUtils.isEmpty(strResult)) {
				Header[] headers = response.getResponseHeaders();
				for (Header header : headers) {
					if ("Location".equals(header.getName())) {
						strResult = header.getValue();
						break;
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			log.error("BaseTrade->post exception: " + e.getMessage());
		}
		return strResult;
	}

	private String getResult(HttpResponse response){
		if (response == null) {
			return "";
		}
		String strResult = null;
		try {
			strResult = response.getStringResult();
		} catch (UnsupportedEncodingException e) {
			log.error("BaseTrade->post exception: " + e.getMessage());
		}
		return strResult;
	}

	private String getParametersStr(NameValuePair[] nvp) {
		if (nvp == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (NameValuePair e : nvp) {
			sb.append(e.getName() + "=" + e.getValue()).append("&");
		}
		return sb.substring(0, sb.length() - 1);
	}

	public String createLinkString(Map<String, Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value =  params.get(key).toString();
			if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

	public String createLinkStringWithQuote(Map<String, Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value =  params.get(key).toString();
			if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
				prestr = prestr + key + "=\"" + value + "\"";
			} else {
				prestr = prestr + key + "=\"" + value + "\"&";
			}
		}
		return prestr;
	}

	public LinkedHashMap sort(Map<String, String> params){
		LinkedHashMap linkedHashMap = new LinkedHashMap();
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		keys.forEach((key) -> linkedHashMap.put(key, params.get(key)));
		return linkedHashMap;
	}

	public Map<String, String> paraFilter(Map<String, ? extends Object> map) {
		Map<String, String> result = new HashMap<String, String>();
		if (map == null || map.size() <= 0) {
			return result;
		}
		for (String key : map.keySet()) {
			String value = (String) map.get(key);
			if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
					|| key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}
		return result;
	}

	public String buildRequestMysign(Map<String, Object> sPara, int tradeType, String key, boolean needSort) {
		String prestr = needSort ? createLinkString(sPara) : createLinkStringWithQuote(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = "";
		switch (tradeType){
			case ConstantPay.TRADE_TYPE_ALIPAY:
				mysign = RSA.sign(prestr, key, ConstantPay.CHARSET_UTF8);
				if (ConstantPay.ALIPAY_PAY_SERVICE_API.equals(sPara.get("service"))) {
					try {
						mysign = URLEncoder.encode(mysign, "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
				}
				break;
			case ConstantPay.TRADE_TYPE_WEIXIN:
            case ConstantPay.TRADE_TYPE_WFT_WEIXIN:
				prestr = prestr + "&key=" + key;
                mysign = MD5.sign(prestr, "", ConstantPay.CHARSET_UTF8);
				mysign = mysign.toUpperCase();
				break;
			case ConstantPay.TRADE_TYPE_ZLINE:
				prestr = prestr + "&KEY=" + key;
				mysign = MD5.sign(prestr, "", ConstantPay.CHARSET_UTF8);
				mysign = mysign.toUpperCase();
				break;
		}
		return mysign;
	}

	public NameValuePair[] convert(Map<String, ? extends Object> map) {
		if (map == null || map.isEmpty()) {
			return new NameValuePair[0];
		}
		NameValuePair[] kvs = new NameValuePair[map.size()];
		int i = 0;
		for (Map.Entry<String, ? extends Object> ele : map.entrySet()) {
			kvs[i++] = new NameValuePair(ele.getKey(), (String) ele.getValue());
		}
		return kvs;
	}

	public String randomStr() {
		char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
				'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z' };
		String res = "";
		for (int i = 0; i < 30; i++) {
			int id = (int) Math.ceil(Math.random() * 60);
			res += chars[id];
		}
		return res;
	}
}
