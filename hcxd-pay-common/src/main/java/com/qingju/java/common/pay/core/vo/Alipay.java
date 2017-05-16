package com.qingju.java.common.pay.core.vo;

import com.google.common.collect.Maps;
import com.qingju.java.common.pay.ConstantPay;
import com.qingju.java.common.pay.core.param.PayAlipayParam;
import com.qingju.java.common.pay.http.HttpRequest;
import com.qingju.java.common.pay.http.HttpResultType;
import com.qingju.java.common.pay.util.XmlMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * Created by taxus on 16-4-16.
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class Alipay extends BaseTrade {

    //支付接口名称，固定值
    private String service = ConstantPay.ALIPAY_PAY_SERVICE_API;

    //商户网站使用的编码格式，固定为utf-8
    private String _input_charset = ConstantPay.CHARSET_UTF8;

    //签名类型，目前仅支持RSA
    private String sign_type = ConstantPay.ALIPAY_SIGN_RSA;
    //提现接口
    private String reward_service = ConstantPay.ALIPAY_REWARD_SERVICE_API;
    //支付类型。默认值为：1（商品购买）
    private String payment_type = ConstantPay.ALIPAY_PAYMENT_TYPE_BUY;

    //签约的支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成
    private String partner;
    //卖家支付宝账号（邮箱或手机号码格式）或其对应的支付宝唯一用户号（以2088开头的纯16位数字）
    private String seller_id;

    //未付款交易的超时时间
    private String it_b_pay;

    //账户名字
    private String account_name;

    private String return_url = "m.alipay.com";

    //支付宝服务器主动通知商户网站里指定的页面http路径
    private String notify_url;

    //签名
    private String sign;

    //支付宝合作商户网站唯一订单号
    private String out_trade_no;

    //商品的标题/交易标题/订单标题/订单关键字等。该参数最长为128个汉字
    private String subject;

    //该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01，100000000.00]，精确到小数点后两位
    private String total_fee;

    //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body
    private String body;

    private PayAlipayParam payAlipayParam;

    /**
     * 支付宝消息验证地址
     */
    private static Map<String, ? extends Object> params;

    private static HttpRequest request;

    public Alipay(PayAlipayParam payAlipayParam, String domain){
        this.request = new HttpRequest(HttpResultType.STRING);
        this.request.setMethod(HttpRequest.METHOD_POST);
        this.request.setCharset(ConstantPay.CHARSET_UTF8);
        this.request.setConnectionTimeout(ConstantPay.REQUEST_TIME_OUT);
        //支付接口名称，固定值
        this.service = payAlipayParam.getPayServiceApi();
        //签约的支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成
        this.partner = payAlipayParam.getPartner();
        //商户网站使用的编码格式，固定为utf-8
        this._input_charset = ConstantPay.CHARSET_UTF8;
        //签名类型，目前仅支持RSA
        this.sign_type = payAlipayParam.getSignRsa();
        //支付类型。默认值为：1（商品购买）
        this.payment_type = payAlipayParam.getPaymentTypeBuy();
        //卖家支付宝账号（邮箱或手机号码格式）或其对应的支付宝唯一用户号（以2088开头的纯16位数字）
        this.seller_id = payAlipayParam.getSellerId();
        //未付款交易的超时时间
        this.it_b_pay = payAlipayParam.getPayTimeOut();
        //提现接口
        this.reward_service = payAlipayParam.getRewardServiceApi();
        //账户名字
        this.account_name = payAlipayParam.getAccountName();
        this.payAlipayParam = payAlipayParam;
        this.notify_url = domain;
    }

    /**
     * 验证接口
     */
    public boolean notifyVerify (String notifyId) {
        Map<String, String> map = new java.util.HashMap<>();
        map.put("notify_id", notifyId);
        map.put("service", payAlipayParam.getVeryfyServiceApi());
        map.put("partner", partner);
        this.request.setUrl(payAlipayParam.getGateway());
        this.request.setParameters(convert(map));
        String result = super.post(this.request, "", "");
        return Boolean.TRUE.toString().equals(result) ? true : false;
    }

    /**
     * 支付参数封装
     * @return
     */
    public String buildRequestPara(){
        String notifyUrl = notify_url + ConstantPay.PAY_ALIPAY_NOTIFY_URL;
        Map<String, Object> param = Maps.newLinkedHashMap();
        param.put("partner", partner);
        param.put("seller_id", seller_id);
        param.put("out_trade_no", out_trade_no);
        param.put("subject", subject);
        param.put("body", body);
        param.put("total_fee", total_fee);
        param.put("notify_url", notifyUrl);
        param.put("service", service);
        param.put("payment_type", payment_type);
        param.put("_input_charset", _input_charset);
        param.put("it_b_pay", it_b_pay);
        param.put("return_url", return_url);
        sign(param, false);
        param.put("sign", sign);
        param.put("sign_type", sign_type);
        return createLinkStringWithQuote(param);
    }

    private void sign(Map<String, Object> param, boolean withQoute){
        sign = buildRequestMysign(param,
                ConstantPay.TRADE_TYPE_ALIPAY, ConstantPay.ALIPAY_KEY_PRI, withQoute);

    }

//    public void buildRewardParam(Map<String, Object> params){
//        params.put("service", reward_service);
//        params.put("partner", partner);
//        params.put("_input_charset", _input_charset);
//        params.put("notify_url", notify_url + PayWechatParam.ALIPAY_REWARD_NOTIFY_URL);
//        params.put("account_name", account_name);
//        params.put("email", seller_id);
//        params.put("pay_date", DateUtil.getDate());
//        params.put("buyer_account_name", seller_id);
//        params.put("sign", buildRequestMysign(params, ConstantPay.TRADE_TYPE_ALIPAY, payAlipayParam.getKeyPri(), true));
//        params.put("sign_type", sign_type);
//    }

    public void setTotal_fee(BigDecimal totalFee) {
        total_fee = new DecimalFormat("0.00").format(totalFee);
    }

    public Map<String, Object> payValidate(String payCode){
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("_input_charset", _input_charset);
        map.put("out_trade_no", payCode);
        map.put("partner", partner);
        map.put("service", payAlipayParam.getQueryServiceApi());
        sign(map, true);
        map.put("sign_type", sign_type);
        map.put("sign", sign);
        this.request.setUrl(payAlipayParam.getGateway());
        this.request.setParameters(convert(map));
        String result = super.get(this.request);
        Map<String, Object> resultMap = XmlMapper.xmlToJson(result);
        return resultMap;
    }

}
