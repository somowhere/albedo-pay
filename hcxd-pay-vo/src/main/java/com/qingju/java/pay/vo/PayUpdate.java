package com.qingju.java.pay.vo;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

/**
 * Created by lijie on 2017/4/28.
 */
@Data @ToString
public class PayUpdate {

    /** bizCode 业务编码 */
    private String bizCode;
    /** amount 金额 */
    private BigDecimal amount;
    /** changeType 变更类型 */
    private Integer changeType;


}
