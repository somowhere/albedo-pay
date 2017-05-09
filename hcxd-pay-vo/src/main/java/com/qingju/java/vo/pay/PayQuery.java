package com.qingju.java.vo.pay;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lijie on 2017/4/28.
 */
@Data @ToString
public class PayQuery {

    /** beginTime 开始时间 */
    private Date beginTime;
    /** endTime 结束时间 */
    private Date endTime;
    /** payType 支付类型 */
    private Integer payType;

}
