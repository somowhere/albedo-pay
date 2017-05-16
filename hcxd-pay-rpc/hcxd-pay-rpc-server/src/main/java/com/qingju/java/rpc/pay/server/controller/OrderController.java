package com.qingju.java.rpc.pay.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qingju.java.pay.service.PayService;
import com.qingju.java.pay.vo.PayUpdate;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lijie on 2017/5/4.
 */
@Controller
@RequestMapping(value = "order")
@Slf4j
public class OrderController {

	@Autowired
	PayService payService;

	@RequestMapping("/update")
	public String update(PayUpdate payUpdate) {
		payService.update(payUpdate);
		return "success";
	}

}
