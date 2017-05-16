package com.qingju.java.pay.core.factory;

import com.albedo.java.common.spi.Spi;
import com.qingju.java.common.pay.core.PayInstance;
import com.qingju.java.pay.core.PayInstanceWechat;

/**
 * Created by lijie on 2017/5/9.
 *
 * @author 837158334@qq.com
 */
@Spi
public class PayInstanceWechatFactory implements PayInstanceFactory {
	@Override
	public PayInstance get() {
		return PayInstanceWechat.I;
	}
}