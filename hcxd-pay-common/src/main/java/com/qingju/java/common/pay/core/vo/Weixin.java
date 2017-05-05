package com.qingju.java.common.pay.core.vo;

import com.albedo.java.util.Json;
import com.albedo.java.util.base.Reflections;
import com.alibaba.fastjson.JSONObject;
import com.qingju.java.common.pay.ConstantPay;
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
public class Weixin extends BaseTrade {

    //必传,微信分配的公众账号ID（企业号corpid即为此appId）
    private String appid = ConstantPay.WEIXIN_PAY_APP_ID;

    //必传,微信支付分配的商户号
    private String mch_id = ConstantPay.WEIXIN_PAY_MCHID;

    //必传,取值如下：JSAPI，NATIVE，APP，详细说明见参数规定
    private String trade_type = ConstantPay.WEIXIN_PAY_TRADE_TYPE_APP;

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

    public Weixin(){
        this.notify_url = ConstantPay.WEIXIN_PAY_NOTIFY_URL;
    }
    public Weixin(String domain){
        this.notify_url = domain + ConstantPay.WEIXIN_PAY_NOTIFY_URL;
    }

    /**
     * 支付参数封装
     * @return
     */
    public String buildRequestPara(int invokeType){
        String prepayId = preTrade(invokeType);
        Map<String, Object> params = new HashedMap();
        if (ConstantPay.PAY_INVOKE_JS == invokeType) {
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
        params.put("package", ConstantPay.WEIXIN_PAY_PACKAGE);
        return params;
    }

    private Map<String, Object> jsPara(){
        Map<String, Object> params = new HashMap<>();
        params.put("appId", ConstantPay.WEIXIN_PAY_TAPP_ID);
        params.put("nonceStr", nonce_str);
        params.put("timeStamp", System.currentTimeMillis() / 1000);
        params.put("signType", ConstantPay.WEIXIN_PAY_SIGN_MD5);
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
        if (ConstantPay.PAY_INVOKE_JS == invokeType) {
            this.appid = ConstantPay.WEIXIN_PAY_TAPP_ID;
            this.trade_type = ConstantPay.WEIXIN_PAY_TRADE_TYPE_JSAPI;
            map.put("appid", this.appid);
            map.put("trade_type", this.trade_type);
            map.put("openid", this.openid);
        } else {
            map.remove("openid");
        }
        map.remove("sign");
        this.sign(map);
        String result = sslXmlPost(ConstantPay.WEIXIN_PAY_UNIFIED_ORDER_API, ConstantPay.WEIXIN_PAY_FILE_CERT,
            ConstantPay.WEIXIN_PAY_CERT_PASS, this.xmlString());
        Map<String, Object> resultMap = XmlMapper.xmlToMap(result);
        String resultSign = (String) resultMap.remove("sign");
        this.sign(resultMap);
        AssertHandler.isTrue(sign.equals(resultSign) && "SUCCESS".equals(resultMap.get("result_code")),
            (String) resultMap.get("err_code_des"));
        return (String) resultMap.get("prepay_id");
    }

    public void sign(Map<String, Object> map){
        sign = buildRequestMysign(map, ConstantPay.TRADE_TYPE_WEIXIN, ConstantPay.WEIXIN_PAY_API_KEY, true);
    }

    public Map<String, Object> payValidate(String orderCode){
        Map<String, Object> map = new HashMap<>();
        map.put("appid", ConstantPay.WEIXIN_PAY_APP_ID);
        map.put("mch_id", ConstantPay.WEIXIN_PAY_MCHID);
        map.put("nonce_str", this.nonce_str);
        map.put("out_trade_no", orderCode);
        sign(map);
        map.put("sign", this.sign);
        String xml = XmlMapper.map2SimplXml(map);
        String result = sslXmlPost(ConstantPay.WEIXIN_PAY_QUERY_ORDER_API, ConstantPay.WEIXIN_PAY_FILE_CERT,
            ConstantPay.WEIXIN_PAY_CERT_PASS, xml
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
        String appId = ConstantPay.WEIXIN_PAY_APP_ID;
        String secret = ConstantPay.WEIXIN_PAY_APP_SECRET;
        if (ConstantPay.USER_TYPE_DRIVER != userType && ConstantPay.USER_TYPE_OIL_GROUP != userType) {
            appId = ConstantPay.WEIXIN_PAY_REPAIR_APP_ID;
            secret = ConstantPay.WEIXIN_PAY_REPAIR_APP_SECRET;
        }
        String authReq = "appid=" + appId
            + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
        HttpRequest request = new HttpRequest(HttpResultType.STRING);
        request.setMethod(HttpRequest.METHOD_GET);
        request.setCharset(ConstantPay.CHARSET_UTF8);
        request.setConnectionTimeout(ConstantPay.REQUEST_TIME_OUT);
        request.setUrl(ConstantPay.WEIXIN_SNS_OAUTH2_API);
        request.setQueryString(authReq);
        String result = get(request);
        JSONObject jsonObject = Json.parseObject(result);
        return jsonObject.get("openid") == null ? null : (String) jsonObject.get("openid");
    }

    public String getTOpenId(String code){
        String authReq = "appid=" + ConstantPay.WEIXIN_PAY_TAPP_ID
            + "&secret=" + ConstantPay.WEIXIN_PAY_TAPP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        HttpRequest request = new HttpRequest(HttpResultType.STRING);
        request.setMethod(HttpRequest.METHOD_GET);
        request.setCharset(ConstantPay.CHARSET_UTF8);
        request.setConnectionTimeout(ConstantPay.REQUEST_TIME_OUT);
        request.setUrl(ConstantPay.WEIXIN_PAY_TAPP_OAUTH2_API);
        request.setQueryString(authReq);
        String result = get(request);
        JSONObject jsonObject = Json.parseObject(result);
        return jsonObject.get("openid") == null ? null : (String) jsonObject.get("openid");
    }

    public String reward(String tradeNo, String openId, String userName,
                         String amount, int userType){
        String certFile = ConstantPay.WEIXIN_PAY_FILE_CERT;
        if (ConstantPay.USER_TYPE_DRIVER != userType) {
            appid = ConstantPay.WEIXIN_PAY_REPAIR_APP_ID;
            mch_id = ConstantPay.WEIXIN_PAY_REPAIR_MCHID;
            certFile = ConstantPay.WEIXIN_PAY_REPAIR_FILE_CERT;
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

        String mysign = buildRequestMysign(params, ConstantPay.TRADE_TYPE_WEIXIN, ConstantPay.WEIXIN_PAY_API_KEY, true);
        params.put("sign", mysign);

        String xml = XmlMapper.map2SimplXml(params);
        String result = sslXmlPost(ConstantPay.WEIXIN_PAY_TRANSFERS_API, certFile,
            mch_id, xml);
        return result;
    }

}
