package com.qingju.java.pay.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

import com.albedo.java.util.JedisUtil;
import com.alibaba.fastjson.JSONObject;
import com.qingju.java.common.pay.Constant;
import com.qingju.java.common.pay.ConstantPay;
import com.qingju.java.pay.util.PayUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lijie on 2017/5/15.
 *
 * @author 837158334@qq.com
 */
@Slf4j
public class RedisMessageListener implements MessageListener {

	RedisTemplate redisTemplate;

	public RedisMessageListener(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void onMessage(Message message, byte[] bytes) {
		String channel = new String(message.getChannel());
		Object obj = redisTemplate.getValueSerializer().deserialize(message.getBody());
		JSONObject json;
		log.info("channel {} msgObj {}", channel, obj);
		switch (channel) {
		case Constant.REDIS_RECEVER_UPDATE_PAY_ARGS_PARAMS:// 更新支付参数
			JedisUtil.clearAllCacheSys();
			log.info("update ORDER_TYPE_BIZ_BASE - ORDER_PAY_TYPE_ALIPAY params {}",
					PayUtil.findParams(Constant.ORDER_TYPE_BIZ_BASE, ConstantPay.TRADE_TYPE_ALIPAY));
			log.info("update ORDER_TYPE_BIZ_BASE - ORDER_PAY_TYPE_WECHAT params {}",
					PayUtil.findParams(Constant.ORDER_TYPE_BIZ_BASE, ConstantPay.TRADE_TYPE_WEIXIN));
			break;
		default:
			log.warn("未知 channel");
		}

	}
}
