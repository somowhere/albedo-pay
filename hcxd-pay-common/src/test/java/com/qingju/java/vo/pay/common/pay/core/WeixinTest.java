package com.qingju.java.vo.pay.common.pay.core;

import com.qingju.java.common.pay.core.vo.Weixin;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


/**
 * Created by lijie on 2017/4/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class WeixinTest {
    @org.junit.Test
    public void notifyVerify() throws Exception {
        Weixin weixin = new Weixin("http://120.26.85.230:9000");
        weixin.setBody("主题");
        weixin.setOut_trade_no("aaaaaaa12");
        weixin.setSpbill_create_ip("192.168.1.1");
        weixin.setTotal_fee(1);
        weixin.setAttach("111");
        String paramStr = weixin.buildRequestPara(1);
        System.out.println(paramStr);
    }



}
