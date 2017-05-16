package com.qingju.java.pay.rpc.client.service;

import com.albedo.java.util.base.Assert;
import com.alibaba.fastjson.JSON;
import com.qingju.java.rpc.pay.service.thrift.PayThriftService;
import com.qingju.java.vo.pay.Order;
import com.qingju.java.vo.pay.PayCreate;
import com.qingju.java.vo.pay.PayQuery;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lijie on 2017/4/12.
 */
@Service
public class PayService {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    PayThriftService.Iface payThriftService;


    public void create(PayCreate payCreate) {
        try {
            payThriftService.create(JSON.toJSONString(payCreate));
        } catch (TException e) {
            logger.warn("TException {}", e);
            Assert.buildException(e.getMessage());
        }
    }

    public List<Order> query(PayQuery payQuery) {
        try {
            String rs = payThriftService.query(JSON.toJSONString(payQuery));
            return JSON.parseArray(rs, Order.class);
        } catch (TException e) {
            logger.warn("TException {}", e);
            Assert.buildException(e.getMessage());
        }
        return null;
    }

}
