package com.qingju.java.common.pay.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.qingju.java.common.pay.ConstantPay;
import com.qingju.java.common.pay.base.MD5;
import com.qingju.java.common.pay.base.RSA;

/**
 * Created by lijie on 2017/5/8.
 */
public class SignUtil {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SignUtil.class);

	public static String createLinkString(Map<String, Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key).toString();
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

	public static String createLinkStringWithQuote(Map<String, Object> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key).toString();
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=\"" + value + "\"";
			} else {
				prestr = prestr + key + "=\"" + value + "\"&";
			}
		}
		return prestr;
	}

	public static String buildRequestMysign(Map<String, Object> sPara, int tradeType, String key, boolean needSort) {
		String prestr = needSort ? createLinkString(sPara) : createLinkStringWithQuote(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String mysign = "";
		switch (tradeType) {
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

}
