package com.qingju.java.rpc.pay.server.controller;

import com.qingju.java.common.pay.util.XmlMapper;
import com.qingju.java.modules.pay.service.PayService;
import com.qingju.java.vo.pay.PayUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    public String update(PayUpdate payUpdate){
        payService.update(payUpdate);
        return "success";
    }

}
