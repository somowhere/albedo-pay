/**
 * Copyright &copy; 2015 <a href="http://www.bs-innotech.com/">bs-innotech</a> All rights reserved.
 */
package com.qingju.java.vo.pay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付订单管理Entity 支付订单
 * @author lj
 * @version 2017-05-02
 */
@Data @ToString @NoArgsConstructor @AllArgsConstructor
public class Order {
	
	private static final long serialVersionUID = 1L;
	/** F_PAYCODE pay_code  :  支付编码 */
	public static final String F_PAYCODE = "payCode";
	/** F_BIZCODE biz_code  :  业务编码 */
	public static final String F_BIZCODE = "bizCode";
	/** F_TRADEID trade_id  :  第三方交易编号 */
	public static final String F_TRADEID = "tradeId";
	/** F_AMOUNT amount_  :  金额 */
	public static final String F_AMOUNT = "amount";
	/** F_PAYTYPE pay_type  :  支付类型 */
	public static final String F_PAYTYPE = "payType";
	/** F_BIZTYPE biz_type  :  业务类型 */
	public static final String F_BIZTYPE = "bizType";
	/** F_CLIENTIP client_ip  :  client_ip */
	public static final String F_CLIENTIP = "clientIp";
	/** F_SUBJECT subject_  :  subject_ */
	public static final String F_SUBJECT = "subject";
	/** F_INVOKETYPE invoke_type  :  支付调起方式1: app, 2: js */
	public static final String F_INVOKETYPE = "invokeType";
	/** F_FINISHTIME finish_time  :  订单完成时间 */
	public static final String F_FINISHTIME = "finishTime";
	/** F_OPENID open_id  :  open_id */
	public static final String F_OPENID = "openId";
	/** F_ATTACH attach_  :  附加参数 */
	public static final String F_ATTACH = "attach";
	/** F_PAYSTATUS pay_status  :  支付状态 0 待支付 1支付成功 2支付失败 3 退款  4关闭 */
	public static final String F_PAYSTATUS = "payStatus";
	
	//columns START
	private String id;
	/** payCode 支付编码 */
	private String payCode;
	/** bizCode 业务编码 */
	private String bizCode;
	/** tradeId 第三方交易编号 */
	private String tradeId;
	/** amount 金额 */
	private BigDecimal amount;
	/** payType 支付类型 */
	private Integer payType;
	/** bizType 业务类型 */
	private Integer bizType;
	/** clientIp client_ip */
	private String clientIp;
	/** subject subject_ */
	private String subject;
	/** invokeType 支付调起方式1: app, 2: js */
	private Integer invokeType;
	/** finishTime 订单完成时间 */
	private Date finishTime;
	/** openId open_id */
	private String openId;
	/** attach 附加参数 */
	private String attach;
	/** payStatus 支付状态 0 待支付 1支付成功 2支付失败 3 退款  4关闭 */
	private Integer payStatus;
	private String createdBy;
	private Date createdDate;
	private String lastModifiedBy;
	private Date lastModifiedDate;
	private String description;
	
	//columns END

}
