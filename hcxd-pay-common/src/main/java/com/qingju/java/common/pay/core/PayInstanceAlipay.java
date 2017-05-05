package com.qingju.java.common.pay.core;

import com.qingju.java.common.pay.core.vo.Alipay;
import com.qingju.java.vo.pay.PayMsg;

import java.math.BigDecimal;

/**
 * Created by lijie on 2017/5/3.
 */
public class PayInstanceAlipay implements PayInstance {
    @Override
    public String create(PayMsg payMsg, String domain) {
        Alipay alipay = new Alipay(domain);
        alipay.setOut_trade_no(payMsg.getBizCode());
        alipay.setSubject(payMsg.getSubject());
        alipay.setTotal_fee(payMsg.getAmount());
        alipay.setBody(payMsg.getSubject());
        return alipay.buildRequestPara();
    }
}
