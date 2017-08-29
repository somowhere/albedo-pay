// package com.albedo.java.common.pay.util;
//
//
//
//
//
//
//
//
//
// import com.google.common.collect.Maps;
// import PayWechatParam;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
//
// import java.util.*;
//
/// * *
// *类名：WechatNotify
// *功能：支付宝通知处理类
// *详细：处理支付宝各接口通知返回
// *版本：3.3
// *日期：2012-08-17
// *说明：
// *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
// *该代码仅供学习和研究支付宝接口使用，只是提供一个参考
//
// *************************注意*************************
// *调试通知返回时，可查看或改写log日志的写入TXT里的数据，来检查通知返回是否正常
// */
// public class WechatNotify {
// protected static Logger logger = LoggerFactory.getLogger(WechatNotify.class);
//
// /**
// * 验证消息是否是微信发出的合法消息
// * @param params 通知返回来的参数数组
// * @return 验证结果
// */
// public static boolean verify(Map<String, Object> params) {
// boolean flag = false;
// try {
// String verifySign = SignUtil.buildRequestMysign(params,
// PayWechatParam.TRADE_TYPE_WEIXIN, PayWechatParam.W, false);
// if(params.get("sign").equals(verifySign)){
// flag=true;
// }
// } catch (Exception e) {
// logger.error("微信回调异常："+e.getMessage());
// e.printStackTrace();
// }
// return flag;
// }
//
// }
