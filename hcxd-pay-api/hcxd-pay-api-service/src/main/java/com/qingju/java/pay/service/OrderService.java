/**
 * Copyright &copy; 2015 <a href="http://www.bs-innotech.com/">bs-innotech</a> All rights reserved.
 */
package com.qingju.java.pay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albedo.java.common.service.DataService;
import com.qingju.java.pay.config.PayProperties;
import com.qingju.java.pay.domain.Order;
import com.qingju.java.pay.repository.OrderRepository;

/**
 * 支付订单管理Service 支付订单
 * @author lj
 * @version 2017-05-02
 */
@Service
@Transactional
public class OrderService extends DataService<OrderRepository, Order, String>{

    @Autowired
    PayProperties payProperties;

}