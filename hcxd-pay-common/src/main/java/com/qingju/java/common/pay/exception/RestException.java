/*
 *
 *       '||      '||  '.
 *      ||  ||     ||         ....     ....
 *     ||    ||    ||   ||  .|...||  ||    ||
 *    ||......||   ||   ||  ||       ||    ||
 *   ||        || .||. .||.  '|...' .||.  .||.
 *
 * --------------- By Liuyihua. ------------- '''' -----------
 *
 * @url http://www.alien.pub
 */

package com.qingju.java.common.pay.exception;

import org.springframework.http.HttpStatus;

/**
 * 自定义rest异常,处理controller端抛出的rest异常
 * Created by Liuyihua. on 2015/11/11.
 */
public class RestException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;/* HTTP状态码,默认500 */
    public Integer errorCode=1;/* 自定义错误码 */

    /**
     * 返回消息
     */
    public RestException(String message) {
        super(message);
    }

    /**
     * 返回消息和状态码
     */
    public RestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * 返回消息和自定义状态码
     */
    public RestException(String message, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * 返回错误类和消息
     */
    public RestException(Class clazz, String message) {
        super("[" + clazz.getSimpleName() + "]" + message);
    }

}
