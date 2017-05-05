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
 * 支付订单管理Entity 支付订单
 * @author lj
 * @version 2017-05-02
 */
@Entity(table = "pay_order")
@Data @ToString @NoArgsConstructor @AllArgsConstructor
public class Order extends DataEntity {
	
	private static final long serialVersionUID = 1L;
	/** F_PAYCODE pay_code  :  支付编码 */
	public static final String F_PAYCODE = "payCode";
	/** F_BIZCODE biz_code  :  业务编码 */
	public static final String F_BIZCODE = "bizCode";
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
	/** F_OPENID open_id  :  open_id */
	public static final String F_OPENID = "openId";
	/** F_PAYSTATUS pay_status  :  支付状态 0 待支付 1支付成功 2支付失败 3 退款  4关闭 */
	public static final String F_PAYSTATUS = "payStatus";
	
	//columns START
	@Column(name = "id_")
	@SearchField
	@Id(strategy = Id.GenerationType.UUID)
	private String id;
	/** payCode 支付编码 */@NotBlank @Length(max=32)@Column(name = "pay_code")
	private String payCode;
	/** bizCode 业务编码 */@NotBlank @Length(max=32)@Column(name = "biz_code")
	private String bizCode;
	/** amount 金额 */@NotNull @Column(name = "amount_")
	private BigDecimal amount;
	/** payType 支付类型 */@NotNull @Column(name = "pay_type")@DictType(name="order_pay_type")
	private Integer payType;
	/** bizType 业务类型 */@NotNull @Column(name = "biz_type")@DictType(name="order_biz_type")
	private Integer bizType;
	/** clientIp client_ip */@NotBlank @Length(max=32)@Column(name = "client_ip")
	private String clientIp;
	/** subject subject_ */@NotBlank @Length(max=32)@Column(name = "subject_")
	private String subject;
	/** invokeType 支付调起方式1: app, 2: js */@NotNull @Column(name = "invoke_type")@DictType(name="invoke_type")
	private Integer invokeType;
	/** openId open_id */@NotBlank @Length(max=128)@Column(name = "open_id")
	private String openId;
	/** payStatus 支付状态 0 待支付 1支付成功 2支付失败 3 退款  4关闭 */@NotNull @Column(name = "pay_status")@DictType(name="order_pay_status")
	private Integer payStatus;
	//columns END

	public Order(String id) {
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
		if(obj instanceof Order == false) return false;
		if(this == obj) return true;
		Order other = (Order)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}
