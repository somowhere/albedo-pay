package com.albedo.java.pay.rpc.client.config;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Michael Email: yidongnan@gmail.com Date: 2016/12/8
 */
public class LogGrpcInterceptor implements ClientInterceptor {

	private static final Logger log = LoggerFactory.getLogger(LogGrpcInterceptor.class);

	@Override
	public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
			CallOptions callOptions, Channel next) {
		log.info(method.getFullMethodName());
		return next.newCall(method, callOptions);
	}
}
