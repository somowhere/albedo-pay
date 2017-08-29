package com.albedo.java.rpc.pay.server.controller;

import com.albedo.java.rpc.pay.service.GrpcPayConstant;
import com.albedo.java.rpc.vo.DataMessage;
import com.albedo.java.pay.service.PayService;
import com.albedo.java.pay.vo.PayUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	public DataMessage update(PayUpdate payUpdate) {
		payService.update(payUpdate);
		return DataMessage.createMsg(GrpcPayConstant.DATA_RESPONSE_STATUS_SUCCESS, "操作成功");
	}

}
