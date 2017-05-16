package com.qingju.java.common.pay.core.vo;

import com.albedo.java.util.Json;
import com.albedo.java.util.base.Reflections;
import com.alibaba.fastjson.JSONObject;
import com.qingju.java.common.pay.Constant;
import com.qingju.java.common.pay.ConstantPay;
import com.qingju.java.common.pay.core.param.PayWechatParam;
import com.qingju.java.common.pay.exception.AssertHandler;
import com.qingju.java.common.pay.http.HttpRequest;
import com.qingju.java.common.pay.http.HttpResultType;
import com.qingju.java.common.pay.util.XmlMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.map.HashedMap;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by taxus on 16-4-21.
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class Wechat extends BaseTrade {

    //必传,微信分配的公众账号ID（企业号corpid即为此appId）
    private String appid;

    //必传,微信支付分配的商户号
    private String mch_id;

    //必传,取值如下：JSAPI，NATIVE，APP，详细说明见参数规定
    private String trade_type;

    //必传,随机字符串，不长于32位。推荐随机数生成算法
    private String nonce_str = randomStr();

    //必传,接收微信支付异步通知回调地址
    private String notify_url;

    //必传,商品或支付单简要描述
    private String body;

    //必传,商户系统内部的订单号,32个字符内、可包含字母, 其他说明见商户订单号
    private String out_trade_no;

    //必传,订单总金额，单位为分，详见支付金额
    private Integer total_fee;

    //必传,APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
    private String spbill_create_ip;

    //附加参数
    private String attach;

    //必传,签名
    private String sign;

    private String openid;

    private PayWechatParam payWechatParam;
    public Wechat(PayWechatParam payWechatParam, String domain){

        //必传,微信分配的公众账号ID（企业号corpid即为此appId）
        this.appid = payWechatParam.getPayAppId();

        //必传,微信支付分配的商户号
        this.mch_id = payWechatParam.getPayMchId();

        //必传,取值如下：JSAPI，NATIVE，APP，详细说明见参数规定
        this.trade_type = payWechatParam.getPayTradeTypeApp();

        this.notify_url = domain + ConstantPay.PAY_WECHAT_NOTIFY_URL;

        this.payWechatParam = payWechatParam;
    }

    /**
     * 支付参数封装
     * @return
     */
    public String buildRequestPara(int invokeType){
        String prepayId = preTrade(invokeType);
        Map<String, Object> params = new HashedMap();
        if (Constant.PAY_INVOKE_JS == invokeType) {
            params = jsPara();
            params.put("package", "prepay_id=" + prepayId);
        } else {
            params = appPara();
            params.put("prepayid", prepayId);
        }
        this.sign(params);
        params.put("sign", sign);
        return Json.toJsonString(params);
    }

    private Map<String, Object> appPara(){
        Map<String, Object> params = new HashMap<>();
        params.put("appid", appid);
        params.put("partnerid", mch_id);
        params.put("noncestr", nonce_str);
        params.put("timestamp", System.currentTimeMillis() / 1000);
        params.put("package", payWechatParam.getPayPackage());
        return params;
    }

    private Map<String, Object> jsPara(){
        Map<String, Object> params = new HashMap<>();
        params.put("appId", payWechatParam.getPayTappId());
        params.put("nonceStr", nonce_str);
        params.put("timeStamp", System.currentTimeMillis() / 1000);
        params.put("signType", payWechatParam.getPaySignMd5());
        return params;
    }

    /**
     * 验证接口
     */
    public boolean notifyVerify (Map<String, Object> params) {
        String sign = (String) params.remove("sign");
        sign(params);
        return this.sign.equals(sign);
    }

    private String preTrade(int invokeType){
        Map<String, Object> map = (Map<String, Object>) Reflections.bean2Map(this);
        map.remove("payWechatParam");
        if (Constant.PAY_INVOKE_JS == invokeType) {
            this.appid = payWechatParam.getPayTappId();
            this.trade_type = payWechatParam.getPayTradeTypeJsapi();
            map.put("appid", this.appid);
            map.put("trade_type", this.trade_type);
            map.put("openid", this.openid);
        } else {
            map.remove("openid");
        }
        map.remove("sign");
        this.sign(map);
        String result = sslXmlPost(payWechatParam.getPayUnifiedOrderApi(), payWechatParam.getPayFileCert(),
                payWechatParam.getPayCertPass(), this.xmlString());
        Map<String, Object> resultMap = XmlMapper.xmlToMap(result);
        String resultSign = (String) resultMap.remove("sign");
        this.sign(resultMap);
        AssertHandler.isTrue(sign.equals(resultSign) && "SUCCESS".equals(resultMap.get("result_code")),
            (String) resultMap.get("err_code_des"));
        return (String) resultMap.get("prepay_id");
    }

    public void sign(Map<String, Object> map){
        sign = buildRequestMysign(map, ConstantPay.TRADE_TYPE_WEIXIN, payWechatParam.getPayApiKey(), true);
    }

    public Map<String, Object> payValidate(String orderCode){
        Map<String, Object> map = new HashMap<>();
        map.put("appid", payWechatParam.getPayAppId());
        map.put("mch_id", payWechatParam.getPayMchId());
        map.put("nonce_str", this.nonce_str);
        map.put("out_trade_no", orderCode);
        sign(map);
        map.put("sign", this.sign);
        String xml = XmlMapper.map2SimplXml(map);
        String result = sslXmlPost(payWechatParam.getPayQueryOrderApi(), payWechatParam.getPayFileCert(),
                payWechatParam.getPayCertPass(), xml
        );
        return XmlMapper.xmlToMap(result);
    }

    private String xmlString(){
        return xmlString(this);
    }

    private String xmlString(Object obj){
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xStreamForRequestPostData.alias("xml", this.getClass());
        //将要提交给API的数据对象转换成XML格式数据Post给API
        return xStreamForRequestPostData.toXML(obj);
    }


    public String getOpenId(String code, int userType){
        String appId = payWechatParam.getPayAppId();
        String secret = payWechatParam.getPayAppSecret();
        if (Constant.USER_TYPE_DRIVER != userType && Constant.USER_TYPE_OIL_GROUP != userType) {
            appId = payWechatParam.getPayRepairAppId();
            secret = payWechatParam.getPayRepairAppSecret();
        }
        String authReq = "appid=" + appId
            + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
        HttpRequest request = new HttpRequest(HttpResultType.STRING);
        request.setMethod(HttpRequest.METHOD_GET);
        request.setCharset(ConstantPay.CHARSET_UTF8);
        request.setConnectionTimeout(ConstantPay.REQUEST_TIME_OUT);
        request.setUrl(payWechatParam.getSnsOauth2Api());
        request.setQueryString(authReq);
        String result = get(request);
        JSONObject jsonObject = Json.parseObject(result);
        return jsonObject.get("openid") == null ? null : (String) jsonObject.get("openid");
    }

    public String getTOpenId(String code){
        String authReq = "appid=" + payWechatParam.getPayTappId()
            + "&secret=" + payWechatParam.getPayTappSecret() + "&js_code=" + code + "&grant_type=authorization_code";
        HttpRequest request = new HttpRequest(HttpResultType.STRING);
        request.setMethod(HttpRequest.METHOD_GET);
        request.setCharset(ConstantPay.CHARSET_UTF8);
        request.setConnectionTimeout(ConstantPay.REQUEST_TIME_OUT);
        request.setUrl(payWechatParam.getSnsOauth2Api());
        request.setQueryString(authReq);
        String result = get(request);
        JSONObject jsonObject = Json.parseObject(result);
        return jsonObject.get("openid") == null ? null : (String) jsonObject.get("openid");
    }

    public String reward(String tradeNo, String openId, String userName,
                         String amount, int userType){
        String certFile = payWechatParam.getPayFileCert();
        if (Constant.USER_TYPE_DRIVER != userType) {
            appid = payWechatParam.getPayRepairAppId();
            mch_id = payWechatParam.getPayRepairMchId();
            certFile = payWechatParam.getPayRepairFileCert();
        }
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("mch_appid", appid);
        params.put("mchid", mch_id);
        params.put("nonce_str", nonce_str);
        params.put("partner_trade_no", tradeNo);
        params.put("openid", openId);
        params.put("check_name", "FORCE_CHECK");
        params.put("re_user_name", userName);
        params.put("amount", amount);
        params.put("desc", "钱包提现");
        params.put("spbill_create_ip", "114.55.73.189");

        String mysign = buildRequestMysign(params, ConstantPay.TRADE_TYPE_WEIXIN, payWechatParam.getPayApiKey(), true);
        params.put("sign", mysign);

        String xml = XmlMapper.map2SimplXml(params);
        String result = sslXmlPost(payWechatParam.getPayTransfersApi(), certFile,
            mch_id, xml);
        return result;
    }

}
