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
package com.qingju.java.common.pay;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统的全局常量on
 * Created by Liuyihua. on 2016/3/3.
 */
public class Constant {

    /**
     * http错误码表
     */
    public static final int HTTP_SUCCESS = 0;/* 成功 */
    public static final int HTTP_FAIL = 1;/* 失败 */
    public static final int HTTP_CLIENT = 2;/* 客户端 */
    public static final int HTTP_SERVER = 3;/* 服务端 */
    public static final int HTTP_FAIL_NEED_DEAL= 4;/* 需处理错误 */

    public static final int SOCKET_SUCCESS = 0;/* 成功 */
    public static final int SOCKET_FAIL = 1;/* 失败 */

    /**
     * secure域
     */
    public static final int SECURE_SALT_SIZE = 8;/* 密码盐长度 */
    public static final String SECURE_HASH_ALGORITHM = "SHA-1";/* 散列算法 */
    public static final int SECURE_HASH_INTERATION = 1024;/* 散列迭代次数 */

    /**
     * session域
     */
    public static final String SESSION_USER = "SESSION_USER";/* 会话用户 */
    public static final String SESSION_USER_ID = "SESSION_USER_ID";/* 用户ID */
    public static final String SESSION_USER_NAME = "SESSION_USER_NAME";/* 用户登录名 */
    public static final String SESSION_NICK_NAME = "SESSION_NICK_NAME";/* 用户昵称 */
    public static final String SESSION_USER_VERIFY_CODE = "SESSION_USER_VERIFY_CODE";/* 会话验证码 */
    public static final String SESSION_USER_LOGIN_URL = "SESSION_USER_LOGIN_URL";/* 会话请求的URL */
    public static final String SEESION_KICKOUT_USER_KEY = "SEESION_KICKOUT_USER_KEY";/* 强制踢出的用户键 */

    /**
     * 提现状态
     */
    public static final int REWARD_STATUS_APPLY = 1;
    public static final int REWARD_STATUS_CONFIRM = 2;
    public static final int REWARD_STATUS_SUCCESS = 3;
    public static final int REWARD_STATUS_REJECT = 4;
    public static final int REWARD_STATUS_FAILED = 9;

    /**
     * push消息前缀
     */
    public static final String PREFIX_JPUSH_USER = "APP_USER_";

    /**
     * 用户类型
     */
    public static final int USER_TYPE_WORKER = 5;/* 技工 */
    public static final int USER_TYPE_DRIVER = 6;/* 司机 */
    public static final int USER_TYPE_OILER = 7;/* 油工 */
    public static final int USER_TYPE_TIRE = 8;/* 轮胎工 */
    public static final int USER_TYPE_OIL = 9;/* 油库管理员 */
    public static final int USER_TYPE_MANAGER = 10;/* 轮胎店管理员 */
    public static final int USER_TYPE_OIL_GROUP = 11;/* 集团加油司机 */
    public static final int USER_TYPE_OIL_GROUP_MANAGER = 12;/* 集团加油管理员 */

    /**
     * 优惠券状态
     */
    public static final int COUPON_STATUS_NOMAL = 0;
    public static final int COUPON_STATUS_LOCK = 1;
    public static final int COUPON_STATUS_DEL = 2;

    /**
     * 用户拥有的优惠券状态
     */
    public static final int COUPON_USE_STATUS_AVAIL = 0;
    public static final int COUPON_USE_STATUS_UNAVAI = 1;
    public static final int COUPON_USE_STATUS_USED = 2;

    /**
     * 优惠券类型
     * 0 默认, 1 减价(数量) 2 打折 3 满减(金额) 4抵扣
     */
    public static final int COUPON_TYPE_SUB_PRICE = 1;
    public static final int COUPON_TYPE_DISCOUNT = 2;
    public static final int COUPON_TYPE_TAKE_OFF_WHEN = 3;
    public static final int COUPON_TYPE_DEDUCTION = 4;

    /**
     * 支付调起方式1: app, 2: js
     */
    public static final int PAY_INVOKE_APP = 1;
    public static final int PAY_INVOKE_JS = 2;

    /**
     * 支付类型1: 支付宝, 2: 微信
     */
    public static final int ORDER_PAY_TYPE_ALIPAY = 1;
    public static final int ORDER_PAY_TYPE_WECHAT = 2;

    /**
     * 业务类型 1:基础类型
     */
    public static final int ORDER_TYPE_BIZ_BASE = 1;


}
