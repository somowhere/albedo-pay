package com.qingju.java.rpc.pay.server.util;

import com.albedo.java.rpc.vo.DataMessage;
import com.qingju.java.rpc.pay.service.grpc.DataResponse;
import io.grpc.stub.StreamObserver;

/**
 * Created by lijie on 2017/5/16.
 *
 * @author 837158334@qq.com
 */
public class GrpcUtil {

	public static void response(DataMessage grpcMessage, StreamObserver<DataResponse> responseObserver) {
		DataResponse reply = DataResponse.newBuilder().setStatus(grpcMessage.getStatus()).setMsg(grpcMessage.getMsg())
				.setData(grpcMessage.getData()).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

}
