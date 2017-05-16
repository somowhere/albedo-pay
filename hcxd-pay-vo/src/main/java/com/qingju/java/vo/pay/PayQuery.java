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

    /** beginCreateTime 订单创建 开始时间 */
    private Date beginCreateTime;
    /** endCreateTime 订单创建 结束时间 */
    private Date endCreateTime;
    /** payType 支付类型 */
    private Integer payType;
    /** payStatus 支付类型 */
    private Integer payStatus;
    /** bizType 业务类型 */
    private Integer bizType;


}
