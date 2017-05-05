/**
 * Copyright &copy; 2015 <a href="http://www.bs-innotech.com/">bs-innotech</a> All rights reserved.
 */
package com.qingju.java.modules.pay.domain;

import com.albedo.java.common.data.mybatis.persistence.IdGen;
import com.albedo.java.common.domain.base.DataEntity;
import com.albedo.java.util.PublicUtil;
import com.albedo.java.util.annotation.DictType;
import com.albedo.java.util.annotation.SearchField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mybatis.annotations.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 订单日志Entity 订单日志
 * @author lj
 * @version 2017-05-02
 */
@Entity(table = "pay_order_amount_log")
@Data @ToString @NoArgsConstructor @AllArgsConstructor
public class OrderAmountLog extends DataEntity {
	
	private static final long serialVersionUID = 1L;
	/** F_ORDERID order_id  :  支付id */
	public static final String F_ORDERID = "orderId";
	/** F_TYPE type_  :  类型 */
	public static final String F_TYPE = "type";
	/** F_BEFORE before_  :  改变前金额 */
	public static final String F_BEFORE = "before";
	/** F_AFTER after_  :  改变后金额 */
	public static final String F_AFTER = "after";
	
	//columns START
	@Column(name = "id_")
	@SearchField
	@Id(strategy = Id.GenerationType.UUID)
	private String id;
	/** orderId 支付id */@NotBlank @Length(max=32)@Column(name = "order_id")
	private String orderId;
	/** type 类型 */@NotNull @Column(name = "type_")@DictType(name="order_amout_type")
	private Integer type;
	/** before 改变前金额 */@NotNull @Column(name = "before_")
	private BigDecimal before;
	/** after 改变后金额 */@NotNull @Column(name = "after_")
	private BigDecimal after;
	//columns END

	public OrderAmountLog(String id) {
		this.id = id;
	}
	/** id 编号 */
	public void setId(String id) {
		if (PublicUtil.isNotEmpty(id))this.id = id;
	}
	/** id 编号 */
	public String getId() {
		return this.id;
	}
	
	@PreInssert
	public void preInssert() {
		if(PublicUtil.isEmpty(getId())){
			setId(IdGen.uuid());
		}
	}
	@PreUpdate
	public void preUpdate() {

	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof OrderAmountLog == false) return false;
		if(this == obj) return true;
		OrderAmountLog other = (OrderAmountLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}
