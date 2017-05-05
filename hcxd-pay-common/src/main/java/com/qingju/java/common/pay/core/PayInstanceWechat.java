package com.qingju.java.common.pay.core;

import com.albedo.java.util.base.Assert;
import com.qingju.java.common.pay.ConstantPay;
import com.qingju.java.common.pay.core.vo.Weixin;
import com.qingju.java.vo.pay.PayMsg;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * Created by lijie on 2017/5/3.
 */
public class PayInstanceWechat implements PayInstance {
    @Override
    public String create(PayMsg payMsg, String domain) {
        Weixin weixin = new Weixin(domain);
        weixin.setBody(payMsg.getSubject());
        weixin.setOut_trade_no(payMsg.getBizCode());
        weixin.setSpbill_create_ip(payMsg.getClientIp());
        weixin.setTotal_fee(payMsg.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue());
        weixin.setAttach(payMsg.getAttach());
        if (ConstantPay.PAY_INVOKE_JS == payMsg.getInvokeType()) {
            Assert.assertNotBlank(payMsg.getOpenId(), "微信支付授权信息缺失");
            weixin.setOpenid(payMsg.getOpenId());
        }
        String paramStr = weixin.buildRequestPara(payMsg.getInvokeType());

        return paramStr;
    }
}
