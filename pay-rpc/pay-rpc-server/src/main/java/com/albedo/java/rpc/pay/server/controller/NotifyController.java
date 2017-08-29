package com.albedo.java.rpc.pay.server.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.albedo.java.common.pay.util.XmlMapper;
import com.albedo.java.pay.service.PayService;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lijie on 2017/5/4.
 */
@Controller
@RequestMapping(value = "notify")
@Slf4j
public class NotifyController {

	@Autowired
	PayService payService;

	@RequestMapping("/alipay")
	@ResponseBody
	public String alipay(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		log.info("alipay notify: {}", params);
		String orderNumber = (String) params.get("out_trade_no");
		try {
			boolean flag = payService.notifyByTradeParams(params, orderNumber);
		} catch (Exception e) {
			log.error("支付宝支付,支付结果通知接口,异常 {}" + e.getMessage());
		}
		return "success";
	}

	@RequestMapping("/wechat")
	public void wechat(HttpServletResponse response, HttpServletRequest request) {
		String message = "<xml>\n" + "  <return_code><![CDATA[FAIL]]></return_code>\n"
				+ "  <return_msg><![CDATA[失败]]></return_msg>\n" + "</xml>";
		PrintWriter writer = null;
		try {
			response.setCharacterEncoding("UTF-8");
			request.setCharacterEncoding("UTF-8");
			writer = response.getWriter();
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			log.info("wei xin notify: " + sb.toString());
			Map<String, Object> params = XmlMapper.xmlToMap(sb.toString());
			String orderNumber = (String) params.get("out_trade_no");
			boolean flag = payService.notifyByTradeParams(params, orderNumber);
			if (flag == true)
				message = "<xml>\n" + "  <return_code><![CDATA[SUCCESS]]></return_code>\n"
						+ "  <return_msg><![CDATA[OK]]></return_msg>\n" + "</xml>";
		} catch (Exception e) {
			log.error("微信支付,支付结果通知接口,异常 {}" + e.getMessage());
		}

		writer.print(message);
	}

}
