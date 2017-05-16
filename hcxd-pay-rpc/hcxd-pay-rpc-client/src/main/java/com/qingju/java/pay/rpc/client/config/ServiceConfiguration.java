package com.qingju.java.pay.rpc.client.config;

import com.albedo.java.thrift.rpc.client.proxy.ServiceStarter;
import com.albedo.java.thrift.rpc.common.vo.ServiceApi;
import com.qingju.java.rpc.pay.service.thrift.PayThriftService;
import com.qingju.java.rpc.pay.service.thrift.ThriftServiceConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lijie on 2017/4/12.
 */
@Configuration
public class ServiceConfiguration {

    @Bean
    public ServiceStarter serviceMap(){

        return new ServiceStarter()
                .startService(PayThriftService.Iface.class,
                        ServiceApi.create(ThriftServiceConstant.PAY_THRIFT_SERVICE_NAME));
    }



}
