/**
 * Copyright &copy; 2015 <a href="http://www.bs-innotech.com/">bs-innotech</a> All rights reserved.
 */
package com.albedo.java.pay.service;

import com.albedo.java.common.data.persistence.DynamicSpecifications;
import com.albedo.java.common.data.persistence.SpecificationDetail;
import com.albedo.java.common.service.DataService;
import com.albedo.java.util.domain.QueryCondition;
import com.google.common.collect.Lists;
import com.albedo.java.pay.domain.OrderArgs;
import com.albedo.java.pay.repository.OrderArgsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 支付参数Service 支付参数
 * 
 * @author lj
 * @version 2017-05-02
 */
@Service
@Transactional
public class OrderArgsService extends DataService<OrderArgsRepository, OrderArgs, String> {

	public OrderArgs findAllByBizTypeAndPayType(Integer bizType, Integer payType) {
		SpecificationDetail<OrderArgs> specificationDetail = DynamicSpecifications.bySearchQueryCondition(
				QueryCondition.eq(OrderArgs.F_BIZTYPE, bizType), QueryCondition.eq(OrderArgs.F_PAYTYPE, payType),
				QueryCondition.eq(OrderArgs.F_STATUS, OrderArgs.FLAG_NORMAL));
		specificationDetail
				.setOrders(Lists.newArrayList(com.albedo.java.util.domain.Order.desc(OrderArgs.F_LASTMODIFIEDDATE)));
		List<OrderArgs> orderArgsList = findAll(specificationDetail);
		OrderArgs item = orderArgsList == null ? null : orderArgsList.get(0);
		return item;
	}
}