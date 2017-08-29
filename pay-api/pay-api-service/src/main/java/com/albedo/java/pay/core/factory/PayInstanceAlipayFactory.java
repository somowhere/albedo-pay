package com.albedo.java.pay.core.factory;

import com.albedo.java.common.spi.Spi;
import com.albedo.java.pay.core.PayInstanceAlipay;
import com.albedo.java.common.pay.core.PayInstance;

/**
 * Created by lijie on 2017/5/9.
 *
 * @author 837158334@qq.com
 */
@Spi
public class PayInstanceAlipayFactory implements PayInstanceFactory {
	@Override
	public PayInstance get() {
		return PayInstanceAlipay.I;
	}
}
