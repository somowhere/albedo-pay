/**
 * Copyright &copy; 2015 <a href="http://www.bs-innotech.com/">bs-innotech</a> All rights reserved.
 */
package com.qingju.java.pay.domain;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mybatis.annotations.Column;
import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.annotations.Id;
import org.springframework.data.mybatis.annotations.PreInssert;
import org.springframework.data.mybatis.annotations.PreUpdate;

import com.albedo.java.common.data.mybatis.persistence.IdGen;
import com.albedo.java.common.domain.base.DataEntity;
import com.albedo.java.util.PublicUtil;
import com.albedo.java.util.annotation.DictType;
import com.albedo.java.util.annotation.SearchField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 支付参数Entity 支付参数
 * 
 * @author lj
 * @version 2017-05-02
 */
@Entity(table = "pay_order_args")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderArgs extends DataEntity {

	private static final long serialVersionUID = 1L;
	/** F_NAME name_ : 名称 */
	public static final String F_NAME = "name";
	/** F_ARGSTYPE args_type : 参数类型 */
	public static final String F_ARGSTYPE = "argsType";
	/** F_PAYTYPE pay_type : 支付类型 */
	public static final String F_PAYTYPE = "payType";
	/** F_BIZTYPE biz_type : 业务类型 */
	public static final String F_BIZTYPE = "bizType";

	// columns START
	@Column(name = "id_")
	@SearchField
	@Id(strategy = Id.GenerationType.UUID)
	private String id;
	/** name 名称 */
	@NotBlank
	@Length(max = 64)
	@Column(name = "name_")
	private String name;
	/** argsType 参数类型 */
	@NotNull
	@Column(name = "args_type")
	@DictType(name = "pay_args")
	private Integer argsType;
	/** payType 支付类型 */
	@NotNull
	@Column(name = "pay_type")
	@DictType(name = "order_pay_type")
	private Integer payType;
	/** bizType 业务类型 */
	@NotNull
	@Column(name = "biz_type")
	@DictType(name = "order_biz_type")
	private Integer bizType;
	// columns END

	public OrderArgs(String id) {
		this.id = id;
	}

	/** id 编号 */
	public void setId(String id) {
		if (PublicUtil.isNotEmpty(id))
			this.id = id;
	}

	/** id 编号 */
	public String getId() {
		return this.id;
	}

	@PreInssert
	public void preInssert() {
		if (PublicUtil.isEmpty(getId())) {
			setId(IdGen.uuid());
		}
	}

	@PreUpdate
	public void preUpdate() {

	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof OrderArgs == false)
			return false;
		if (this == obj)
			return true;
		OrderArgs other = (OrderArgs) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
}
