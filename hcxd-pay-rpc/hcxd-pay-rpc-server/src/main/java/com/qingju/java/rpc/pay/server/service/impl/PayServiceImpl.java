package com.qingju.java.rpc.pay.server.service.impl;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.albedo.java.thrift.rpc.server.service.IThriftServerService;
import com.albedo.java.thrift.rpc.server.service.ThriftServerService;
import com.albedo.java.util.Json;
import com.qingju.java.pay.service.PayService;
import com.qingju.java.pay.vo.PayCreate;
import com.qingju.java.pay.vo.PayQuery;
import com.qingju.java.rpc.pay.service.thrift.PayThriftService;
import com.qingju.java.rpc.pay.service.thrift.ThriftServiceConstant;

/**
 * Created by lijie on 2017/5/16.
 *
 * @author 837158334@qq.com
 */
@Service
public class PayServiceImpl extends ThriftServerService implements PayThriftService.Iface{

    @Autowired
    private PayService payService;

    @Override
    public String getName() {
        return ThriftServiceConstant.PAY_THRIFT_SERVICE_NAME;
    }

    @Override
    public TProcessor getProcessor(IThriftServerService bean) {
        PayThriftService.Iface impl = (PayThriftService.Iface) bean;
        return new PayThriftService.Processor<PayThriftService.Iface>(impl);
    }

    @Override
    public void create(String payCreate) throws TException {
        PayCreate item = Json.parseObject(payCreate, PayCreate.class);
        payService.create(item);
    }

    @Override
    public String query(String payQuery) throws TException {
        PayQuery item = Json.parseObject(payQuery, PayQuery.class);
        return payService.query(item);
    }
}
