/**
 * Copyright &copy; 2015 <a href="http://www.bs-innotech.com/">bs-innotech</a> All rights reserved.
 */
package com.albedo.java.pay.repository;

import java.util.List;

import com.albedo.java.common.data.persistence.repository.BaseRepository;
import com.albedo.java.pay.vo.PayQuery;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.repository.query.Param;

import com.albedo.java.pay.domain.Order;

/**
 * 支付订单管理Repository 支付订单
 * 
 * @author lj
 * @version 2017-05-02
 */
public interface OrderRepository extends BaseRepository<Order, String> {

	Order findOneByPayCode(String bizCode);

	@Query
	List<Order> findOrders(@Param("payQuery") PayQuery payQuery);

	Order findOneByBizCode(String payCode);
}