package com.qingju.java.common.pay.core.vo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;

import com.qingju.java.common.pay.http.HttpProtocolHandler;
import com.qingju.java.common.pay.http.HttpRequest;
import com.qingju.java.common.pay.http.HttpResponse;
import com.qingju.java.common.pay.util.SignUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseTrade {

	public String get(HttpRequest request) {
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
		HttpResponse response;
		String strResult = null;
		try {
			log.info("Post url: [" + request.getUrl() + "] \n" + " params: [" + request.getQueryString() + "]");
			response = httpProtocolHandler.execute(request, "", "");
			strResult = getResult(response);
		} catch (Exception e) {
			log.error("BaseTrade->get exception: " + e.getMessage());
		}
		return strResult;
	}

	public String post(HttpRequest request, String strParaFileName, String strFilePath) {
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
		HttpResponse response;
		String strResult = null;
		try {
			log.info("Post url: [" + request.getUrl() + "] \n" + " params: ["
					+ getParametersStr(request.getParameters()) + "]");
			response = httpProtocolHandler.execute(request, strParaFileName, strFilePath);
			strResult = getResultFromHeader(response);
		} catch (Exception e) {
			log.error("BaseTrade->post exception: " + e.getMessage());
		}
		return strResult;
	}

	public String sslXmlPost(String url, String certFile, String certPass, String content) {
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

	public String xmlPost(String url, String content) {
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

	public String zlinePost(String url, String data) {
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

	private String getResultFromHeader(HttpResponse response) {
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

	private String getResult(HttpResponse response) {
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
		String prestr = SignUtil.createLinkString(params);
		return prestr;
	}

	public String createLinkStringWithQuote(Map<String, Object> params) {
		String prestr = SignUtil.createLinkStringWithQuote(params);
		return prestr;
	}

	public LinkedHashMap sort(Map<String, String> params) {
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

	public String buildRequestMysign(Map<String, Object> sPara, int tradeType, String privateKey, boolean needSort) {
		String mysign = SignUtil.buildRequestMysign(sPara, tradeType, privateKey, needSort);
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
		char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
				'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z' };
		String res = "";
		for (int i = 0; i < 30; i++) {
			int id = (int) Math.ceil(Math.random() * 60);
			res += chars[id];
		}
		return res;
	}
}
