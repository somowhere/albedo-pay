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
package com.albedo.java.common.pay.core.param;

import lombok.Data;
import lombok.ToString;

/**
 * 系统的全局常量on Created by Liuyihua. on 2016/3/3.
 */
@Data
@ToString
public class PayWechatParam {

	private String payAppId;
	private String payRepairAppId;
	private String payAppSecret;
	private String payRepairAppSecret;
	private String payMchId;
	private String payRepairMchId;
	private String payNotifyUrl;
	private String payTradeTypeApp;
	private String payTradeTypeJsapi;
	private String payPackage;
	private String payApiKey;
	private String payFileCert;
	private String payRepairFileCert;
	private String payCertPass;
	private String payRepairCertPass;
	private String payUnifiedOrderApi;
	private String payQueryOrderApi;
	private String snsOauth2Api;
	private String payTransfersApi;
	private String paySignMd5;
	private String payTappId;
	private String payTappSecret;
	private String payTappOauth2Api;

}
