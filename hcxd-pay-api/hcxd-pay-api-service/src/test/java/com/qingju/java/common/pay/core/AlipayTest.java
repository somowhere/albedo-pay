package com.qingju.java.common.pay.core;

import com.albedo.java.util.spring.SpringContextHolder;
import com.qingju.java.common.pay.Constant;
import com.qingju.java.common.pay.ConstantPay;
import com.qingju.java.common.pay.core.param.PayAlipayParam;
import com.qingju.java.common.pay.core.vo.Alipay;
import com.qingju.java.config.PayProperties;
import com.qingju.java.util.PayUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


/**
 * Created by lijie on 2017/4/28.
 */
@ContextConfiguration(classes = {RedisAutoConfiguration.class, TestConfig.class},
        initializers = { ConfigFileApplicationContextInitializer.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class AlipayTest{

    @Autowired
    PayProperties payProperties;

    @org.junit.Test
    public void notifyVerify() throws Exception {
        PayAlipayParam payAlipayParam = PayUtil.
                findParamsByClass(Constant.ORDER_TYPE_BIZ_BASE, ConstantPay.TRADE_TYPE_ALIPAY,
                        PayAlipayParam.class);
        Alipay alipay = new Alipay(payAlipayParam, payProperties.getDomain());
        alipay.setOut_trade_no("aaaaaaa");
        alipay.setSubject("主题");
        alipay.setTotal_fee(new BigDecimal(0.01));
        alipay.setBody("描述");
        String paramStr = alipay.buildRequestPara();
        System.out.println(paramStr);
        assertThat(paramStr, is(notNullValue()));
    }



}
