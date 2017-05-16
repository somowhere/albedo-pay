package com.qingju.java.pay.rpc.client.config;

import com.albedo.java.grpc.client.GlobalClientInterceptorConfigurerAdapter;
import com.albedo.java.grpc.client.GlobalClientInterceptorRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


/**
 * Created by lijie on 2017/4/12.
 */
@Configuration
@Order
public class ServiceConfiguration {

    @Bean
    public GlobalClientInterceptorConfigurerAdapter globalInterceptorConfigurerAdapter() {
        return new GlobalClientInterceptorConfigurerAdapter() {

            @Override
            public void addClientInterceptors(GlobalClientInterceptorRegistry registry) {
                registry.addClientInterceptors(new LogGrpcInterceptor());
            }
        };
    }




}
