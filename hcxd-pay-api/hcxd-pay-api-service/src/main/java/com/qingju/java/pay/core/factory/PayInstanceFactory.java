package com.qingju.java.pay.core.factory;

import com.albedo.java.common.spi.Factory;
import com.albedo.java.common.spi.SpiLoader;
import com.qingju.java.common.pay.ConstantPay;
import com.qingju.java.common.pay.core.PayInstance;

/**
 * Created by lijie on 2017/4/28.
 */
public interface PayInstanceFactory extends Factory<PayInstance> {

	static PayInstance create(int payType) {
		String className;
		switch (payType) {
		case ConstantPay.TRADE_TYPE_ALIPAY:
			className = PayInstanceAlipayFactory.class.getName();
			break;
		case ConstantPay.TRADE_TYPE_WEIXIN:
			className = PayInstanceWechatFactory.class.getName();
			break;
		default:
			throw new RuntimeException("unkown payType : " + payType);
		}
		return SpiLoader.load(PayInstanceFactory.class, className).get();
	}

}
