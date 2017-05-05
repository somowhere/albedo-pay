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
package com.qingju.java.common.pay;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统的全局常量on
 * Created by Liuyihua. on 2016/3/3.
 */
public class ConstantPay {

    /**
     * http错误码表
     */
    public static final int HTTP_SUCCESS = 0;/* 成功 */
    public static final int HTTP_FAIL = 1;/* 失败 */
    public static final int HTTP_CLIENT = 2;/* 客户端 */
    public static final int HTTP_SERVER = 3;/* 服务端 */
    public static final int HTTP_FAIL_NEED_DEAL= 4;/* 需处理错误 */

    public static final int SOCKET_SUCCESS = 0;/* 成功 */
    public static final int SOCKET_FAIL = 1;/* 失败 */

    /**
     * secure域
     */
    public static final int SECURE_SALT_SIZE = 8;/* 密码盐长度 */
    public static final String SECURE_HASH_ALGORITHM = "SHA-1";/* 散列算法 */
    public static final int SECURE_HASH_INTERATION = 1024;/* 散列迭代次数 */

    /**
     * session域
     */
    public static final String SESSION_USER = "SESSION_USER";/* 会话用户 */
    public static final String SESSION_USER_ID = "SESSION_USER_ID";/* 用户ID */
    public static final String SESSION_USER_NAME = "SESSION_USER_NAME";/* 用户登录名 */
    public static final String SESSION_NICK_NAME = "SESSION_NICK_NAME";/* 用户昵称 */
    public static final String SESSION_USER_VERIFY_CODE = "SESSION_USER_VERIFY_CODE";/* 会话验证码 */
    public static final String SESSION_USER_LOGIN_URL = "SESSION_USER_LOGIN_URL";/* 会话请求的URL */
    public static final String SEESION_KICKOUT_USER_KEY = "SEESION_KICKOUT_USER_KEY";/* 强制踢出的用户键 */


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

    /**
     * 卡卡联接口地址
     * 平台标识 0-本地,1-开发,2-测试，3-正式 ;传入其他值表示可以任意地址
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
        ZLINE_URL.put("3", "https://spayment.kklpay.com/");
    }

    /**
     * 卡卡联接口商户数据
     */
    public static final String ZLINE_AUTH_KEY = "97323f5b-87d3-496a-97da-16c65bce72f0";
    public static final String ZLINE_MERCHANT_CODE_AUTH = "1000002575";
    public static final String ZLINE_NFC_KEY = "ae1dadea-e433-45a5-ba85-b94146190c3a";
    public static final String ZLINE_MERCHANT_CODE_NFC = "1000003073";
    public static final String ZLINE_EBANK_KEY = "d90903db-0bbc-42b5-81a9-c3dac25d8943";
    public static final String ZLINE_MERCHANT_CODE_EBANK = "1000003072";
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
    public static String ALIPAY_KEY_PRI = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANnxMx/7mrbbZfSUJBLVXes3bJH6TuMDkAIcRA8sZ8dAGOcV33JkPtVOqdSyaK0SfCuz1OjNqamsQl+qXOc+xVrzNYrtRxK5giyxDO4Wufa7SnTdRmw5PGhkR+v7JIUXMzDvKaUvmhK0Ki3HUA34G7NUGBMF8IjhmmEZxaYVBFr1AgMBAAECgYEAxqGQKOwVe+bGK30Ay7FvIbun1fu9/iT3ERVV6kZcZzrB94r11boLkjlZi6L2yBZ62RHTUEJxCVUYQTxx48hP8P/vkpBej9IJ7U1k5A+66gmQ9gRKhA8k/cvsKmjB8RV2stW1nCzaj9lmBWAsx3OwOICl9Uz9cO+w3xzpXv5Ve1kCQQDv+edV3JokTTaiVPdKjOT3+xf52+7FxOTIxfS5gTMHEkHWr/RQpKh06xgpoVjdggrAWO3fGzu5CL+gNvhvo2bHAkEA6H6m7Rq7/V+vtfSnzgX9tg/oJGW2yAqWOvQxk2DP0vF8uAocYulTXDhTkqZVk5pIjzovvhTRzcsPIKoicIOEYwJBAOrBLoP5JTUj77FHqw5b3FWWZbMAaok/fovF0wxxjmh5fLy3LiqBmWWe4ek/tH4ki730gOaDbp4pJ8RkYg5b69sCQG4Jk6Pg5Vy0bwtlcg52k3AZ8ztW0L25FQVuEhC432PtW6QXVPwThpq9JgDgrNwhR3pShconPG10UAKbw5URChUCQQDkHk+7rC1ob8b6b6jVNjitBjftLtOc6x+3PDRRbgaepEbYxyJSXCoX+QyziGD3H8E+eSVbYNRexwjXhiANuvvP";
    public static String ALIPAY_KEY_PUB = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    public static String ALIPAY_PARTNER = "2088421884653076";
    public static String ALIPAY_SELLER_ID = "weixin@hcxdi.com";
    public static String ALIPAY_SELLER_EMAIL = "weixin@hcxdi.com";
    public static String ALIPAY_NOTIFY_URL = "/alipay/notify";
    public static String ALIPAY_PAY_SERVICE_API = "mobile.securitypay.pay";
    public static String ALIPAY_PAYMENT_TYPE_BUY = "1";
    public static String ALIPAY_GATEWAY = "https://mapi.alipay.com/gateway.do?";
    public static String ALIPAY_VERYFY_SERVICE_API = "notify_verify";
    public static String ALIPAY_PAY_TIME_OUT = "10m";
    public static String ALIPAY_REWARD_SERVICE_API = "batch_trans_notify";
    public static String ALIPAY_REWARD_NOTIFY_URL = "/reward/notify";
    public static String ALIPAY_ACCOUNT_NAME = "成都青桔科技有限公司";
    public static String ALIPAY_QUERY_SERVICE_API = "single_trade_query";


    /**
     * 微信支付接口商户数据
     */
    public static String WEIXIN_PAY_APP_ID = "wx34ab44a8222c441f";
    public static String WEIXIN_PAY_REPAIR_APP_ID = "wx2249096df9c7d3cd";
    public static String WEIXIN_PAY_APP_SECRET = "2cd55de15bcb655ae9d0facb5ff535ed";
    public static String WEIXIN_PAY_REPAIR_APP_SECRET = "451ef5065b2ff8ac42b1e5f7f49371f4";
    public static String WEIXIN_PAY_MCHID = "1401969102";
    public static String WEIXIN_PAY_REPAIR_MCHID = "1402143602";
    public static String WEIXIN_PAY_NOTIFY_URL = "/weixin/notify";
    public static String WEIXIN_PAY_TRADE_TYPE_APP = "APP";
    public static String WEIXIN_PAY_TRADE_TYPE_JSAPI = "JSAPI";
    public static String WEIXIN_PAY_PACKAGE = "Sign=WXPay";
    public static String WEIXIN_PAY_API_KEY = "kMdipdcJ7MUvy6osw6D9r2MbhA07mc6p";
    public static String WEIXIN_PAY_FILE_CERT = "classpath:/ssl/apiclient_cert.p12";
    public static String WEIXIN_PAY_REPAIR_FILE_CERT = "classpath:/ssl/apiclient_cert_repair.p12";
    public static String WEIXIN_PAY_CERT_PASS = "1401969102";
    public static String WEIXIN_PAY_REPAIR_CERT_PASS = "1402143602";
    public static String WEIXIN_PAY_UNIFIED_ORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static String WEIXIN_PAY_QUERY_ORDER_API = "https://api.mch.weixin.qq.com/pay/orderquery";
    public static String WEIXIN_SNS_OAUTH2_API = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static String WEIXIN_PAY_TRANSFERS_API = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
    public static String WEIXIN_PAY_SIGN_MD5 =  "MD5";
    public static String WEIXIN_PAY_TAPP_ID = "wxa61923a515aae36c";
    public static String WEIXIN_PAY_TAPP_SECRET = "c7cccc2816fb321a2b9cdb989c4a4d9f";
    public static String WEIXIN_PAY_TAPP_OAUTH2_API = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 威富通微信支付接口商户数据
     */
    public static final String WFT_WEIXIN_PAY_APP_ID = "wx34ab44a8222c441f";
    public static final String WFT_WEIXIN_PAY_APP_SECRET = "7daa4babae15ae17eee90c9e";
    public static final String WFT_WEIXIN_PAY_VERSION = "2.0";
    public static final String WFT_WEIXIN_PAY_MCHID = "755437000006";
    public static final String WFT_WEIXIN_PAY_NOTIFY_URL = "/wftweixin/notify";
    public static final String WFT_WEIXIN_PAY_SERVICE_PAY = "unified.trade.pay";
    public static final String WFT_WEIXIN_PAY_SERVICE_QUERY = "unified.trade.query";
    public static final String WFT_WEIXIN_PAY_UNIFIED_ORDER_API = "https://pay.swiftpass.cn/pay/gateway";
    public static final String WFT_WEIXIN_PAY_SIGN_MD5 =  "MD5";
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
    public static final String UNIONPAY_MERID = "777290058110048";
    public static final String UNIONPAY_BACKURL =  "/unionpay/notify";
    public static final String UNIONPAY_CURRENCY_CODE = "156";
    public static final String UNIONPAY_CERT_PASS = "000000";
    public static final int UNIONPAY_EXPIRE_MINS = 20 * 60 * 1000;
    public static final String UNIONPAY_TRANS_QUERY = "https://gateway.test.95516.com/gateway/api/queryTrans.do";
    public static final String UNIONPAY_TRANS_APP_RANS_URL = "https://gateway.test.95516.com/gateway/api/appTransReq.do";
    public static final String UNIONPAY_TXN_QUERY_TYPE = "00";
    public static final String UNIONPAY_TXN_QUERY_SUBTYPE = "00";

    /**
     * 提现状态
     */
    public static final int REWARD_STATUS_APPLY = 1;
    public static final int REWARD_STATUS_CONFIRM = 2;
    public static final int REWARD_STATUS_SUCCESS = 3;
    public static final int REWARD_STATUS_REJECT = 4;
    public static final int REWARD_STATUS_FAILED = 9;

    /**
     * push消息前缀
     */
    public static final String PREFIX_JPUSH_USER = "APP_USER_";

    /**
     * 用户类型
     */
    public static final int USER_TYPE_WORKER = 5;/* 技工 */
    public static final int USER_TYPE_DRIVER = 6;/* 司机 */
    public static final int USER_TYPE_OILER = 7;/* 油工 */
    public static final int USER_TYPE_TIRE = 8;/* 轮胎工 */
    public static final int USER_TYPE_OIL = 9;/* 油库管理员 */
    public static final int USER_TYPE_MANAGER = 10;/* 轮胎店管理员 */
    public static final int USER_TYPE_OIL_GROUP = 11;/* 集团加油司机 */
    public static final int USER_TYPE_OIL_GROUP_MANAGER = 12;/* 集团加油管理员 */

    /**
     * 优惠券状态
     */
    public static final int COUPON_STATUS_NOMAL = 0;
    public static final int COUPON_STATUS_LOCK = 1;
    public static final int COUPON_STATUS_DEL = 2;

    /**
     * 用户拥有的优惠券状态
     */
    public static final int COUPON_USE_STATUS_AVAIL = 0;
    public static final int COUPON_USE_STATUS_UNAVAI = 1;
    public static final int COUPON_USE_STATUS_USED = 2;

    /**
     * 优惠券类型
     * 0 默认, 1 减价(数量) 2 打折 3 满减(金额) 4抵扣
     */
    public static final int COUPON_TYPE_SUB_PRICE = 1;
    public static final int COUPON_TYPE_DISCOUNT = 2;
    public static final int COUPON_TYPE_TAKE_OFF_WHEN = 3;
    public static final int COUPON_TYPE_DEDUCTION = 4;

    /**
     * 支付调起方式1: app, 2: js
     */
    public static final int PAY_INVOKE_APP = 1;
    public static final int PAY_INVOKE_JS = 2;

    /**
     * 支付类型1: 支付宝, 2: 微信
     */
    public static final int ORDER_PAY_TYPE_ALIPAY = 1;
    public static final int ORDER_PAY_TYPE_WECHAT = 2;

    /**
     * 业务类型 1:基础类型
     */
    public static final int ORDER_TYPE_BIZ_BASE = 1;


}
