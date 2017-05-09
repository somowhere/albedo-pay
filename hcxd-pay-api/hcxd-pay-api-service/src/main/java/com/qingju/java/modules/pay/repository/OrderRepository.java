/**
 * Copyright &copy; 2015 <a href="http://www.bs-innotech.com/">bs-innotech</a> All rights reserved.
 */
package com.qingju.java.modules.pay.repository;


import com.albedo.java.common.data.mybatis.persistence.repository.BaseRepository;
import com.qingju.java.modules.pay.domain.Order;
import com.qingju.java.vo.pay.PayQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.mybatis.repository.annotation.Query;

import java.util.List;

/**
 * 支付订单管理Repository 支付订单
 * @author lj
 * @version 2017-05-02
 */
public interface OrderRepository extends BaseRepository<Order, String> {


    Order findOneByBizCode(String bizCode);

    @Query
    List<Order> findOrders(@Param("payQuery") PayQuery payQuery);
}