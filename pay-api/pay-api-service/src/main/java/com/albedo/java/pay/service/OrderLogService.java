/**
 * Copyright &copy; 2015 <a href="http://www.bs-innotech.com/">bs-innotech</a> All rights reserved.
 */
package com.albedo.java.pay.service;

import com.albedo.java.pay.repository.OrderLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albedo.java.common.service.DataService;
import com.albedo.java.pay.domain.OrderLog;

/**
 * 订单日志Service 订单日志
 * 
 * @author lj
 * @version 2017-05-05
 */
@Service
@Transactional
public class OrderLogService extends DataService<OrderLogRepository, OrderLog, String> {

}