package com.qingju.java.modules.pay.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.albedo.java.util.DateUtil;
import com.albedo.java.util.PublicUtil;
import com.qingju.java.common.pay.Constant;
import com.qingju.java.common.pay.ConstantPay;
import com.qingju.java.common.pay.core.TestConfig;
import com.qingju.java.pay.domain.Order;
import com.qingju.java.pay.domain.OrderLog;
import com.qingju.java.pay.repository.OrderLogRepository;
import com.qingju.java.pay.repository.OrderRepository;
import com.qingju.java.pay.service.PayService;
import com.qingju.java.pay.vo.PayCreate;
import com.qingju.java.pay.vo.PayQuery;
import com.qingju.java.pay.vo.PayUpdate;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lijie on 2017/5/15.
 *
 * @author 837158334@qq.com
 */
@ContextConfiguration(classes = {RedisAutoConfiguration.class, TestConfig.class},
        initializers = { ConfigFileApplicationContextInitializer.class })
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class PayServiceTest {
    @Autowired
    PayService payService;
    @Autowired
    OrderLogRepository orderLogRepository;
    @Autowired
    OrderRepository orderRepository;


    public String orderCodeAlipay = PublicUtil.getRandomString(4) + PublicUtil.getCurrentTime("yyyyMMddHHmmss");
    public String orderCodeWechat = PublicUtil.getRandomString(4) + PublicUtil.getCurrentTime("yyyyMMddHHmmss");
    public String orderPayCodeAlipay = PublicUtil.getRandomString(4) + PublicUtil.getCurrentTime("yyyyMMddHHmmss");
    public String orderPayCodeWechat = PublicUtil.getRandomString(4) + PublicUtil.getCurrentTime("yyyyMMddHHmmss");
    @Before
    public void beforeInit(){
        PayCreate payCreate = new PayCreate();
        payCreate.setAmount(new BigDecimal(0.01));
        payCreate.setBizCode(orderCodeAlipay);
        payCreate.setClientIp("127.0.0.1");
        payCreate.setBizType(Constant.ORDER_TYPE_BIZ_BASE);
        payCreate.setSubject("我是支付宝测试订单啊");
        payCreate.setPayType(ConstantPay.TRADE_TYPE_ALIPAY);
        Order order = payService.create(payCreate);
        orderPayCodeAlipay = order.getPayCode();
        log.info("payCreate {}", payCreate);
        assertThat(payCreate, is(notNullValue()));
        payCreate = new PayCreate();
        payCreate.setAmount(new BigDecimal(0.01));
        payCreate.setBizCode(orderCodeWechat);
        payCreate.setClientIp("127.0.0.1");
        payCreate.setBizType(Constant.ORDER_TYPE_BIZ_BASE);
        payCreate.setSubject("我是微信App测试订单啊");
        payCreate.setAttach(orderCodeWechat);
        payCreate.setPayType(ConstantPay.TRADE_TYPE_WEIXIN);
        order = payService.create(payCreate);
        orderPayCodeWechat = order.getPayCode();
        log.info("payCreate {}", payCreate);
        assertThat(payCreate, is(notNullValue()));
    }


    @Test
    public void genParams() throws Exception {
        String params = payService.genParams(orderCodeAlipay);
        log.info("params Alipay {}", params);
        assertThat(params, is(notNullValue()));
        params = payService.genParams(orderCodeWechat);
        log.info("params Wechat {}", params);
        assertThat(params, is(notNullValue()));

    }


    @Test
    public void query() throws Exception {


        List<Order> tempOrders = orderRepository.findAll();
        log.info(tempOrders.toString());
        PayQuery payQuery = new PayQuery();
        payQuery.setPayType(ConstantPay.TRADE_TYPE_ALIPAY);
        payQuery.setBizType(Constant.ORDER_TYPE_BIZ_BASE);
        payQuery.setBeginCreateTime(DateUtil.addHours(PublicUtil.getCurrentDate(), -1));
        payQuery.setEndCreateTime(DateUtil.addHours(PublicUtil.getCurrentDate(), +1));
        payQuery.setPayStatus(Constant.ORDER_PAY_STATUS_WAIT_PAY);
        List<Order> orders = payService.queryOrders(payQuery);
        assertThat(orders.size()>0, is(true));
        payQuery = new PayQuery();
        payQuery.setPayType(ConstantPay.TRADE_TYPE_WEIXIN);
        payQuery.setBizType(Constant.ORDER_TYPE_BIZ_BASE);
        payQuery.setBeginCreateTime(DateUtil.addHours(PublicUtil.getCurrentDate(), -1));
        payQuery.setEndCreateTime(DateUtil.addHours(PublicUtil.getCurrentDate(), +1));
        payQuery.setPayStatus(Constant.ORDER_PAY_STATUS_WAIT_PAY);
        orders = payService.queryOrders(payQuery);
        assertThat(orders.size()>2, is(true));
    }


    @Test
    public void update() throws Exception {

        Order orderBefore = payService.findOneByBizCode(orderCodeAlipay);
        BigDecimal bd = new BigDecimal(0.02);
        PayUpdate payUpdate = new PayUpdate();
        payUpdate.setPayCode(orderPayCodeAlipay);
        payUpdate.setAmount(bd);
        payUpdate.setChangeType(Constant.ORDER_LOG_CHANGE_TYPE_1);
        payService.update(payUpdate);
        Order order = payService.findOneByBizCode(orderCodeAlipay);
        assertThat(order.getAmount().subtract(bd).intValue()==0, is(true));

        OrderLog orderLog = orderLogRepository.findOneByOrderId(order.getId());

        assertThat(orderLog.getBefore().subtract(orderBefore.getAmount()).intValue()==0, is(true));
        assertThat(orderLog.getAfter().subtract(order.getAmount()).intValue()==0, is(true));

        orderBefore = payService.findOneByBizCode(orderCodeWechat);
        bd = new BigDecimal(0.02);
        payUpdate = new PayUpdate();
        payUpdate.setPayCode(orderPayCodeWechat);
        payUpdate.setAmount(bd);
        payUpdate.setChangeType(Constant.ORDER_LOG_CHANGE_TYPE_1);
        payService.update(payUpdate);
        order = payService.findOneByBizCode(orderCodeWechat);
        assertThat(order.getAmount().subtract(bd).intValue()==0, is(true));
        orderLog = orderLogRepository.findOneByOrderId(order.getId());

        assertThat(orderLog.getBefore().subtract(orderBefore.getAmount()).intValue()==0, is(true));
        assertThat(orderLog.getAfter().subtract(order.getAmount()).intValue()==0, is(true));


    }



}