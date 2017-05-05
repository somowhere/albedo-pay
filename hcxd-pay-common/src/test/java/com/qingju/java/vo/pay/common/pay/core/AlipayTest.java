package com.qingju.java.vo.pay.common.pay.core;

import com.qingju.java.common.pay.core.vo.Alipay;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


/**
 * Created by lijie on 2017/4/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class AlipayTest {
    @org.junit.Test
    public void notifyVerify() throws Exception {
        Alipay alipay = new Alipay("");
        alipay.setOut_trade_no("aaaaaaa");
        alipay.setSubject("主题");
        alipay.setTotal_fee(new BigDecimal(0.01));
        alipay.setBody("描述");
        String paramStr = alipay.buildRequestPara();
        System.out.println(paramStr);
        assertThat(paramStr, is(notNullValue()));
    }



}
