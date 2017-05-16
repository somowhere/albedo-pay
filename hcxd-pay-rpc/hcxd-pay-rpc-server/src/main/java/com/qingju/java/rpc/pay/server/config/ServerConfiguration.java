package com.qingju.java.rpc.pay.server.config;

//import com.albedo.java.rpc.server.map.ServiceMap;

import com.albedo.java.thrift.rpc.server.map.ServiceMap;
import com.qingju.java.rpc.pay.service.thrift.PayThriftService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lijie on 2017/5/16.
 *
 * @author 837158334@qq.com
 */
@Configuration
public class ServerConfiguration {

    @Bean
    public ServiceMap serviceMap(PayThriftService.Iface payService){
        ServiceMap serviceMap=new ServiceMap();
        serviceMap.addService(PayThriftService.Iface.class.getName(), payService);
        return serviceMap;
    }

}
