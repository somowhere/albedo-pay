package com.albedo.java.pay.vo;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

/**
 * Created by lijie on 2017/4/28.
 */
@Data
@ToString
public class PayUpdate {

	/** payCode 支付编码 */
	private String payCode;
	/** amount 金额 */
	private BigDecimal amount;
	/** changeType 变更类型 */
	private Integer changeType;

}
