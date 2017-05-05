package com.qingju.java.common.pay.core.factory;

import com.qingju.java.common.pay.ConstantPay;
import com.qingju.java.common.pay.core.PayInstance;
import com.qingju.java.common.pay.core.PayInstanceAlipay;
import com.qingju.java.common.pay.core.PayInstanceWechat;

/**
 * Created by lijie on 2017/4/28.
 */
public class PayInstanceFactory {

    private PayInstanceFactory() {
    }

    private static class HolderEventClass {
        private final static PayInstanceFactory instance = new PayInstanceFactory();
    }

    public static PayInstanceFactory getInstance() {
        return PayInstanceFactory.HolderEventClass.instance;
    }

    public PayInstance create(int payType){

        PayInstance instance;

        switch (payType){
            case ConstantPay.ORDER_PAY_TYPE_ALIPAY: instance = new PayInstanceAlipay();break;
            case ConstantPay.ORDER_PAY_TYPE_WECHAT: instance = new PayInstanceWechat();break;
            default: throw new RuntimeException("unkown payType : " + payType);
        }
        return instance;

    }
}
