/**
 * Copyright &copy; 2015 <a href="http://www.bs-innotech.com/">bs-innotech</a> All rights reserved.
 */
package com.qingju.java.pay.service;

import com.albedo.java.common.data.mybatis.persistence.DynamicSpecifications;
import com.albedo.java.common.data.mybatis.persistence.SpecificationDetail;
import com.albedo.java.util.domain.QueryCondition;
import com.google.common.collect.Lists;
import com.qingju.java.pay.domain.OrderArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albedo.java.common.service.DataService;
import com.qingju.java.pay.config.PayProperties;
import com.qingju.java.pay.domain.Order;
import com.qingju.java.pay.repository.OrderRepository;

import java.util.List;

/**
 * 支付订单管理Service 支付订单
 * @author lj
 * @version 2017-05-02
 */
@Service
@Transactional
public class OrderService extends DataService<OrderRepository, Order, String>{


    public Order findOneByBizCode(String bizCode) {
        SpecificationDetail<Order> specificationDetail = DynamicSpecifications.bySearchQueryCondition(
                QueryCondition.eq(Order.F_BIZCODE, bizCode),
                QueryCondition.eq(Order.F_STATUS, OrderArgs.FLAG_NORMAL));
        List<Order> orderList = findAll(specificationDetail);
        Order item = orderList==null ? null : orderList.get(0);
        return item;
    }

}