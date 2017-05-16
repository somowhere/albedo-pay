package com.qingju.java.pay.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by lijie on 2017/5/16.
 *
 * @author 837158334@qq.com
 */
@Data @ToString @AllArgsConstructor @NoArgsConstructor
public class GrpcMessage {

    private int status;
    private String msg;
    private String data;

    public static GrpcMessage create(int status, String msg, String data){
        return new GrpcMessage(status, msg, data);
    }

    public static GrpcMessage createMsg(int status, String msg){
        return new GrpcMessage(status, msg, null);
    }
    public static GrpcMessage createData(int status, String data){
        return new GrpcMessage(status, null, data);
    }
}
