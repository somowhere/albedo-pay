package com.qingju.java.rpc.pay.server.service.impl;

import com.albedo.java.grpc.server.GrpcService;
import com.albedo.java.rpc.vo.DataMessage;
import com.alibaba.fastjson.JSON;
import com.qingju.java.pay.domain.Order;
import com.qingju.java.pay.service.PayService;
import com.qingju.java.pay.vo.PayCreate;
import com.qingju.java.pay.vo.PayQuery;
import com.qingju.java.rpc.pay.server.util.GrpcUtil;
import com.qingju.java.rpc.pay.service.GrpcPayConstant;
import com.qingju.java.rpc.pay.service.grpc.DataResponse;
import com.qingju.java.rpc.pay.service.grpc.PayRequest;
import com.qingju.java.rpc.pay.service.grpc.PayServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lijie on 2017/5/16.
 *
 * @author 837158334@qq.com
 */

@GrpcService(PayServiceGrpc.class)
public class PayServiceImpl extends PayServiceGrpc.PayServiceImplBase {

	@Autowired
	private PayService payService;

	@Override
	public void create(PayRequest request, StreamObserver<DataResponse> responseObserver) {
		PayCreate payCreate = JSON.parseObject(request.getData(), PayCreate.class);
		Order order = payService.create(payCreate);

		GrpcUtil.response(DataMessage.createData(GrpcPayConstant.DATA_RESPONSE_STATUS_SUCCESS, order.getPayCode()),
				responseObserver);

	}

	@Override
	public void query(PayRequest request, StreamObserver<DataResponse> responseObserver) {
		PayQuery payQuery = JSON.parseObject(request.getData(), PayQuery.class);
		String orderJson = payService.query(payQuery);
		GrpcUtil.response(DataMessage.createData(GrpcPayConstant.DATA_RESPONSE_STATUS_SUCCESS, orderJson),
				responseObserver);
	}
}
