package com.albedo.java.pay.service;

import java.util.List;
import java.util.Map;

import com.albedo.java.pay.core.factory.PayInstanceFactory;
import com.albedo.java.pay.repository.OrderLogRepository;
import com.albedo.java.pay.repository.OrderRepository;
import com.albedo.java.pay.util.PayUtil;
import com.albedo.java.pay.vo.PayCreate;
import com.albedo.java.pay.vo.PayQuery;
import com.albedo.java.pay.vo.PayUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albedo.java.util.HttpUtil;
import com.albedo.java.util.InputStreamUtils;
import com.albedo.java.util.Json;
import com.albedo.java.util.PublicUtil;
import com.albedo.java.util.base.Assert;
import com.albedo.java.common.pay.Constant;
import com.albedo.java.common.pay.ConstantPay;
import com.albedo.java.common.pay.core.param.PayAlipayParam;
import com.albedo.java.common.pay.core.param.PayWechatParam;
import com.albedo.java.common.pay.core.vo.Alipay;
import com.albedo.java.common.pay.core.vo.Wechat;
import com.albedo.java.common.pay.util.XmlMapper;
import com.albedo.java.pay.config.PayProperties;
import com.albedo.java.pay.domain.Order;
import com.albedo.java.pay.domain.OrderLog;

/**
 * Created by lijie on 2017/4/28.
 */
@Service
@Transactional
public class PayService {

	private Logger log = LoggerFactory.getLogger(PayService.class);


	@Autowired
	PayProperties payProperties;

	@Autowired
    OrderRepository orderRepository;
	@Autowired
    OrderLogRepository orderLogRepository;
	@Autowired
	OrderService orderService;

	/**
	 * 创建订单，根据业务编号判断，存在并且支付成功则直接返回，否则更新订单内容,并保存日志
	 * @param payCreate
	 * @return
	 */
	public Order create(PayCreate payCreate) {
		Assert.assertNotNull(payCreate, "订单创建参数不能为空");

		Order order = orderService.findOneByBizCode(payCreate.getBizCode());

		if (order == null) {
			order = new Order();
			order.setPayCode(PayUtil.genPayCode());
		} else {
			if (Constant.ORDER_PAY_STATUS_SUCEESS == order.getPayStatus()) { // 已经支付成功直接返回
				return order;
			}
			OrderLog orderLog = new OrderLog();
			orderLog.setType(Constant.ORDER_LOG_CHANGE_TYPE_1);
			orderLog.setBefore(order.getAmount());
			orderLog.setOrderId(order.getId());
			orderLog.setAfter(payCreate.getAmount());
			orderLog.setDescription(order.toString());
			orderLogRepository.save(orderLog);
	}
		BeanUtils.copyProperties(payCreate, order);
		order.setPayStatus(Constant.ORDER_PAY_STATUS_WAIT_PAY);
		orderRepository.save(order);
		return order;
	}

	/**
	 * 根据支付编码生成支付参数
	 * @param payCode
	 * @return
	 */
	public String genParams(String payCode) {
		Order order = orderRepository.findOneByPayCode(payCode);
		Assert.assertNotNull(order, "无法获取订单信息");
		PayCreate payCreate = new PayCreate();
		BeanUtils.copyProperties(order, payCreate);
		String params = PayInstanceFactory.create(order.getPayType()).genParams(payCreate, payProperties.getDomain());
		return params;
	}

	/**
	 * 查询订单，对账
	 * @param payQuery
	 * @return 对象
	 */
	public List<Order> queryOrders(PayQuery payQuery) {
		Assert.assertNotNull(payQuery, "订单查询参数不能为空");
		List<Order> orderList = orderRepository.findOrders(payQuery);
		return orderList;
	}
	/**
	 * 查询订单，对账
	 * @param payQuery
	 * @return json
	 */
	public String query(PayQuery payQuery) {
		List<Order> orderList = queryOrders(payQuery);
		return Json.toJsonString(orderList);
	}

	/**
	 * 更新订单金额信息，保存变更日志
	 * @param payUpdate
	 */
	public void update(PayUpdate payUpdate) {
		Assert.assertNotNull(payUpdate, "订单更新参数不能为空");
		Assert.assertIsTrue(payUpdate.getAmount() != null && payUpdate.getAmount().intValue() <= 0, "订单支付金额不能小于等于0");
		Order order = orderRepository.findOneByPayCode(payUpdate.getPayCode());
		Assert.assertNotNull(order, "无法获取订单信息");
		if (payUpdate.getAmount() != order.getAmount()) {
			OrderLog orderLog = new OrderLog();
			orderLog.setType(payUpdate.getChangeType());
			orderLog.setBefore(order.getAmount());
			orderLog.setOrderId(order.getId());
			orderLog.setAfter(payUpdate.getAmount());
			orderLogRepository.save(orderLog);
			order.setAmount(payUpdate.getAmount());
			orderRepository.updateIgnoreNull(order);
		}
	}

	public Order findOneByPayCode(String payCode) {
		Order order = orderRepository.findOneByPayCode(payCode);
		Assert.assertIsTrue(order != null, "无法获取 payCode:" + payCode + " 订单信息");
		return order;
	}

	public Order findOneByBizCode(String bizCode) {
		Order order = orderRepository.findOneByBizCode(bizCode);
		Assert.assertIsTrue(order != null, "无法获取 bizCobde:" + bizCode + " 订单信息");
		return order;
	}

	/**
	 * 回调处理
	 * @param params
	 * @param orderNumber
	 * @return
	 * @throws Exception
	 */
	public boolean notifyByTradeParams(Map<String, Object> params, String orderNumber) throws Exception {
		Order order = findOneByBizCode(orderNumber);
		boolean flag = false;
		String tradeId = null;
		try {
			switch (order.getPayType()){
				case ConstantPay.TRADE_TYPE_WEIXIN:
					tradeId = (String) params.get("transaction_id");
					PayWechatParam payWechatParam = PayUtil.findParamsByClass(order.getBizType(), order.getPayType(),
							PayWechatParam.class);
					Wechat wechat = new Wechat(payWechatParam, payProperties.getDomain());
					boolean verifyResult = wechat.notifyVerify(params);
					if (verifyResult) {
						order.setFinishTime(PublicUtil.parseDate((String) params.get("time_end"), "yyyyMMddHHmmss"));
						String xml = XmlMapper.map2SimplXml(params, true);
						HttpUtil.sendPostRequest(payProperties.getAppDomain() + payWechatParam.getPayNotifyUrl(),
								InputStreamUtils.StringTOInputStream(xml, ConstantPay.CHARSET_UTF8));
						flag = true;
					} else {
						Assert.buildException("非法微信订单回调");
					}
					break;
				case ConstantPay.TRADE_TYPE_ALIPAY:
					tradeId = (String) params.get("trade_no");
					PayAlipayParam payAlipayParam = PayUtil.findParamsByClass(order.getBizType(), order.getPayType(),
							PayAlipayParam.class);
					Alipay alipay = new Alipay(payAlipayParam, payProperties.getDomain());
					boolean result = alipay.notifyVerify((String) params.get("notify_id"));
					if (result) {
						order.setFinishTime(
								PublicUtil.parseDate((String) params.get("gmt_payment"), PublicUtil.TIME_FORMAT));
						HttpUtil.sendPostRequestMapObject(payProperties.getAppDomain() + payAlipayParam.getNotifyUrl(),
								params);
						flag = true;
					} else {
						Assert.buildException("非法支付宝订单回调");
					}
					break;
				default:
					Assert.buildException("未知的订单类型 PayType" + order.getPayType());
			}
		} catch (Exception e) {
			log.warn("notifyByTradeParams {}", e);
		} finally {
			if (flag && PublicUtil.isNotEmpty(tradeId) && PublicUtil.isEmpty(order.getPayCode())) {
				order.setPayCode(tradeId);
				order.setPayStatus(Constant.ORDER_PAY_STATUS_SUCEESS);
				orderRepository.save(order);
			}
		}
		return flag;
	}

}
