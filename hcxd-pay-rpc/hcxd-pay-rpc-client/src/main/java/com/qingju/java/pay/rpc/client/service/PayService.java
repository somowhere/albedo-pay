package com.qingju.java.pay.rpc.client.service;

import com.albedo.java.grpc.client.GrpcClient;
import com.albedo.java.rpc.vo.DataMessage;
import com.alibaba.fastjson.JSON;
import com.qingju.java.pay.vo.Order;
import com.qingju.java.pay.vo.PayCreate;
import com.qingju.java.pay.vo.PayQuery;
import com.qingju.java.rpc.pay.service.GrpcPayConstant;
import com.qingju.java.rpc.pay.service.grpc.DataResponse;
import com.qingju.java.rpc.pay.service.grpc.PayRequest;
import com.qingju.java.rpc.pay.service.grpc.PayServiceGrpc;
import io.grpc.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijie on 2017/4/12.
 */
@Service
public class PayService {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	@GrpcClient(GrpcPayConstant.RPC_SERVER_NAME)
	private Channel serverChannel;

	public DataMessage create(PayCreate payCreate) {
		String payCreateStr = JSON.toJSONString(payCreate);
		PayServiceGrpc.PayServiceBlockingStub stub = PayServiceGrpc.newBlockingStub(serverChannel);
		DataResponse dataResponse = stub.create(PayRequest.newBuilder().setData(payCreateStr).build());

		return DataMessage.create(dataResponse.getStatus(), dataResponse.getMsg(), dataResponse.getData());
	}

	public List<Order> query(PayQuery payQuery) {

		String payCreateStr = JSON.toJSONString(payQuery);
		PayServiceGrpc.PayServiceBlockingStub stub = PayServiceGrpc.newBlockingStub(serverChannel);
		DataResponse dataResponse = stub.query(PayRequest.newBuilder().setData(payCreateStr).build());
		if (GrpcPayConstant.DATA_RESPONSE_STATUS_SUCCESS != dataResponse.getStatus()) {
			throw new RuntimeException(dataResponse.getMsg());
		}
		return JSON.parseArray(dataResponse.getData(), Order.class);

	}

}
