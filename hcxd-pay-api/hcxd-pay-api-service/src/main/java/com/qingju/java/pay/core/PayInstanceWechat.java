package com.qingju.java.pay.core;

import java.math.BigDecimal;

import com.albedo.java.util.base.Assert;
import com.qingju.java.common.pay.Constant;
import com.qingju.java.common.pay.core.PayInstance;
import com.qingju.java.common.pay.core.param.PayWechatParam;
import com.qingju.java.common.pay.core.vo.Wechat;
import com.qingju.java.pay.util.PayUtil;
import com.qingju.java.pay.vo.PayCreate;

/**
 * Created by lijie on 2017/5/3.
 */
public class PayInstanceWechat implements PayInstance {

	public static final PayInstanceWechat I = new PayInstanceWechat();

	@Override
	public String genParams(PayCreate payCreate, String domain) {
		PayWechatParam payWechatParam = PayUtil.findParamsByClass(payCreate.getBizType(), payCreate.getPayType(),
				PayWechatParam.class);
		Wechat wechat = new Wechat(payWechatParam, domain);
		wechat.setBody(payCreate.getSubject());
		wechat.setOut_trade_no(payCreate.getBizCode());
		wechat.setSpbill_create_ip(payCreate.getClientIp());
		wechat.setTotal_fee(
				payCreate.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue());
		wechat.setAttach(payCreate.getAttach());
		if (Constant.PAY_INVOKE_JS == payCreate.getInvokeType()) {
			Assert.assertNotBlank(payCreate.getOpenId(), "微信支付授权信息缺失");
			wechat.setOpenid(payCreate.getOpenId());
		}
		String paramStr = wechat.buildRequestPara(payCreate.getInvokeType());

		return paramStr;
	}
}
