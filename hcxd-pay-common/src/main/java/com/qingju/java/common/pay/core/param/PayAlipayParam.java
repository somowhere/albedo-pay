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
package com.qingju.java.common.pay.core.param;

import lombok.Data;
import lombok.ToString;

/**
 * 系统的全局常量on Created by Liuyihua. on 2016/3/3.
 */
@Data
@ToString
public class PayAlipayParam {

	private String signRsa;
	private String keyPri;
	private String keyPub;
	private String partner;
	private String sellerId;
	private String sellerEmail;
	private String notifyUrl;
	private String payServiceApi;
	private String paymentTypeBuy;
	private String gateway;
	private String veryfyServiceApi;
	private String payTimeOut;
	private String rewardServiceApi;
	private String rewardNotifyUrl;
	private String accountName;
	private String queryServiceApi;

}
