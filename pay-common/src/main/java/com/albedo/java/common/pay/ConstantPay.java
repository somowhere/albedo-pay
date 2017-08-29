/*
 *
 *       '||      '||  '.
 *      ||  ||     ||         ....     ....
 *     ||    ||    ||   ||  .|...||  ||    ||
 *    ||......||   ||   ||  ||       ||    ||
 *   ||        || .||. .||.  '|...' .||.  .||.
 *
 * --------------- By Liuyihua. ------------- '''' -----------
 *
 * @url http://www.alien.pub
 */
package com.albedo.java.common.pay;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统的全局常量on Created by Liuyihua. on 2016/3/3.
 */
public class ConstantPay {

	/**
	 * 三方交易方式
	 */
	public static final int TRADE_TYPE_ZLINE = 1;
	public static final int TRADE_TYPE_ALIPAY = 2;
	public static final int TRADE_TYPE_WEIXIN = 3;
	public static final int TRADE_TYPE_WFT_WEIXIN = 4;
	public static final int TRADE_TYPE_UNIONPAY = 5;

	/**
	 * 全局统一编码方式
	 */
	public static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * 全局统一请求超时时间30s
	 */
	public static final int REQUEST_TIME_OUT = 10000;

	public static String PAY_ALIPAY_NOTIFY_URL = "/notify/alipay";
	public static String PAY_WECHAT_NOTIFY_URL = "/notify/wechat";

	/**
	 * 卡卡联接口地址 平台标识 0-本地,1-开发,2-测试，3-正式 ;传入其他值表示可以任意地址
	 */
	public static final Map<String, String> ZLINE_URL = new HashMap<String, String>();
	static {
		ZLINE_URL.put("-1", "");
		// 本机
		ZLINE_URL.put("0", "http://localhost:8080/payment-pre-interface/");
		// 开发
		ZLINE_URL.put("1", "http://192.168.6.41:10086/payment-pre-interface/");
		// 测试
		ZLINE_URL.put("2", "http://192.168.6.34:10086/");
		// 正式环境
		ZLINE_URL.put("3", "https://");
	}

	/**
	 * 卡卡联接口商户数据
	 */
	public static final String ZLINE_AUTH_KEY = "";
	public static final String ZLINE_MERCHANT_CODE_AUTH = "";
	public static final String ZLINE_NFC_KEY = "";
	public static final String ZLINE_MERCHANT_CODE_NFC = "";
	public static final String ZLINE_EBANK_KEY = "";
	public static final String ZLINE_MERCHANT_CODE_EBANK = "";
	public static final String ZLINE_HTTPS_PAY_URL = "payment/payment.do";
	public static final String ZLINE_HTTPS_BATCH_PAY_URL = "payment/batch.do";
	public static final String ZLINE_HTTPS_QUERY_URL = "payment/queryState.do";
	public static final String ZLINE_CARD_TYPE_PERSON = "2";
	public static final String ZLINE_TRADE_TYPE_IN_TIME = "04";
	public static final String ZLINE_NOTIFY_URL = "/zline/notify";

	/**
	 * 支付宝接口商户数据
	 */
	public static String ALIPAY_SIGN_RSA = "RSA";
	public static String ALIPAY_KEY_PRI = "";
	public static String ALIPAY_KEY_PUB = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	public static String ALIPAY_PARTNER = "2088421884653076";
	public static String ALIPAY_SELLER_ID = "";
	public static String ALIPAY_SELLER_EMAIL = "";
	public static String ALIPAY_NOTIFY_URL = "/alipay/notify";
	public static String ALIPAY_PAY_SERVICE_API = "mobile.securitypay.pay";
	public static String ALIPAY_PAYMENT_TYPE_BUY = "1";
	public static String ALIPAY_GATEWAY = "https://mapi.alipay.com/gateway.do?";
	public static String ALIPAY_VERYFY_SERVICE_API = "notify_verify";
	public static String ALIPAY_PAY_TIME_OUT = "10m";
	public static String ALIPAY_REWARD_SERVICE_API = "batch_trans_notify";
	public static String ALIPAY_REWARD_NOTIFY_URL = "/reward/notify";
	public static String ALIPAY_ACCOUNT_NAME = "";
	public static String ALIPAY_QUERY_SERVICE_API = "single_trade_query";

	/**
	 * 微信支付接口商户数据
	 */
	public static String WEIXIN_PAY_APP_ID = "";
	public static String WEIXIN_PAY_REPAIR_APP_ID = "";
	public static String WEIXIN_PAY_APP_SECRET = "";
	public static String WEIXIN_PAY_REPAIR_APP_SECRET = "";
	public static String WEIXIN_PAY_MCHID = "";
	public static String WEIXIN_PAY_REPAIR_MCHID = "";
	public static String WEIXIN_PAY_NOTIFY_URL = "/weixin/notify";
	public static String WEIXIN_PAY_TRADE_TYPE_APP = "APP";
	public static String WEIXIN_PAY_TRADE_TYPE_JSAPI = "JSAPI";
	public static String WEIXIN_PAY_PACKAGE = "Sign=WXPay";
	public static String WEIXIN_PAY_API_KEY = "";
	public static String WEIXIN_PAY_FILE_CERT = "";
	public static String WEIXIN_PAY_REPAIR_FILE_CERT = "";
	public static String WEIXIN_PAY_CERT_PASS = "1401969102";
	public static String WEIXIN_PAY_REPAIR_CERT_PASS = "1402143602";
	public static String WEIXIN_PAY_UNIFIED_ORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static String WEIXIN_PAY_QUERY_ORDER_API = "https://api.mch.weixin.qq.com/pay/orderquery";
	public static String WEIXIN_SNS_OAUTH2_API = "https://api.weixin.qq.com/sns/oauth2/access_token";
	public static String WEIXIN_PAY_TRANSFERS_API = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	public static String WEIXIN_PAY_SIGN_MD5 = "MD5";
	public static String WEIXIN_PAY_TAPP_ID = "";
	public static String WEIXIN_PAY_TAPP_SECRET = "";
	public static String WEIXIN_PAY_TAPP_OAUTH2_API = "https://api.weixin.qq.com/sns/jscode2session";

	/**
	 * 威富通微信支付接口商户数据
	 */
	public static final String WFT_WEIXIN_PAY_APP_ID = "";
	public static final String WFT_WEIXIN_PAY_APP_SECRET = "";
	public static final String WFT_WEIXIN_PAY_VERSION = "2.0";
	public static final String WFT_WEIXIN_PAY_MCHID = "";
	public static final String WFT_WEIXIN_PAY_NOTIFY_URL = "/wftweixin/notify";
	public static final String WFT_WEIXIN_PAY_SERVICE_PAY = "unified.trade.pay";
	public static final String WFT_WEIXIN_PAY_SERVICE_QUERY = "unified.trade.query";
	public static final String WFT_WEIXIN_PAY_UNIFIED_ORDER_API = "https://pay.swiftpass.cn/pay/gateway";
	public static final String WFT_WEIXIN_PAY_SIGN_MD5 = "MD5";
	public static final int WFT_WEIXIN_PAY_EXPIRE_MINS = 20 * 60 * 1000;

	/**
	 * 银联空间支付
	 */
	public static final String UNIONPAY_VERSION = "5.1.0";
	public static final String UNIONPAY_SIGN_TYPE_RSA = "01";
	public static final String UNIONPAY_TXN_TYPE = "01";
	public static final String UNIONPAY_TXN_SUBTYPE = "01";
	public static final String UNIONPAY_BIZ_TYPE = "000201";
	public static final String UNIONPAY_CHANNEL_TYPE = "08";
	public static final String UNIONPAY_ACCESS_TYPE = "0";
	public static final String UNIONPAY_MERID = "";
	public static final String UNIONPAY_BACKURL = "/unionpay/notify";
	public static final String UNIONPAY_CURRENCY_CODE = "156";
	public static final String UNIONPAY_CERT_PASS = "000000";
	public static final int UNIONPAY_EXPIRE_MINS = 20 * 60 * 1000;
	public static final String UNIONPAY_TRANS_QUERY = "https://gateway.test.95516.com/gateway/api/queryTrans.do";
	public static final String UNIONPAY_TRANS_APP_RANS_URL = "https://gateway.test.95516.com/gateway/api/appTransReq.do";
	public static final String UNIONPAY_TXN_QUERY_TYPE = "00";
	public static final String UNIONPAY_TXN_QUERY_SUBTYPE = "00";

}
