package com.albedo.java.rpc.pay.server.config;

import com.albedo.java.grpc.server.GlobalServerInterceptorConfigurerAdapter;
import com.albedo.java.grpc.server.GlobalServerInterceptorRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import com.albedo.java.rpc.server.map.ServiceMap;

/**
 * Created by lijie on 2017/5/16.
 *
 * @author 837158334@qq.com
 */
@Configuration
public class ServerConfiguration {
	@Bean
	public GlobalServerInterceptorConfigurerAdapter globalInterceptorConfigurerAdapter() {
		return new GlobalServerInterceptorConfigurerAdapter() {
			@Override
			public void addServerInterceptors(GlobalServerInterceptorRegistry registry) {
				registry.addServerInterceptors(new LogGrpcInterceptor());
			}
		};
	}

}
