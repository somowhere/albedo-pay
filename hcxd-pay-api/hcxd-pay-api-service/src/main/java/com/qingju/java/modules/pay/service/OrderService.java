/**
 * Copyright &copy; 2015 <a href="http://www.bs-innotech.com/">bs-innotech</a> All rights reserved.
 */
package com.qingju.java.modules.pay.service;

import com.albedo.java.common.service.DataService;
import com.albedo.java.util.base.Assert;
import com.qingju.java.common.pay.core.param.PayWechatParam;
import com.qingju.java.common.pay.core.vo.Wechat;
import com.qingju.java.config.PayProperties;
import com.qingju.java.modules.pay.domain.Order;
import com.qingju.java.modules.pay.repository.OrderRepository;
import com.qingju.java.util.PayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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