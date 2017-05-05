/**
 * Copyright &copy; 2015 <a href="http://www.bs-innotech.com/">bs-innotech</a> All rights reserved.
 */
package com.qingju.java.modules.pay.service;

import com.albedo.java.common.service.DataService;
import com.qingju.java.modules.pay.domain.OrderAmountLog;
import com.qingju.java.modules.pay.repository.OrderAmountLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单日志Service 订单日志
 * @author lj
 * @version 2017-05-02
 */
@Service
@Transactional
public class OrderAmountLogService extends DataService<OrderAmountLogRepository, OrderAmountLog, String>{

}