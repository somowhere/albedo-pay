package com.qingju.java.vo.pay;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Created by lijie on 2017/4/28.
 */
@Data @ToString
public class PayCreate {

    /** bizCode 业务编码 */
    private String bizCode;
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
    /** openId open_id */
    private String openId;
    /** attach 附加参数 */
    private String attach;

}
