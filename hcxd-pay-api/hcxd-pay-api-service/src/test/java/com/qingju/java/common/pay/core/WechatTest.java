package com.qingju.java.common.pay.core;

import com.qingju.java.common.pay.Constant;
import com.qingju.java.common.pay.core.param.PayAlipayParam;
import com.qingju.java.common.pay.core.param.PayWechatParam;
import com.qingju.java.common.pay.core.vo.Wechat;
import com.qingju.java.util.PayUtil;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


/**
 * Created by lijie on 2017/4/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class WechatTest {
    @org.junit.Test
    public void notifyVerify() throws Exception {
        PayWechatParam payWechatParam = PayUtil.
                findParamsByClass(Constant.ORDER_TYPE_BIZ_BASE, Constant.ORDER_PAY_TYPE_WECHAT,
                        PayWechatParam.class);
        Wechat wechat = new Wechat(payWechatParam, "http://120.26.85.230:9000");
        wechat.setBody("主题");
        wechat.setOut_trade_no("aaaaaaa12");
        wechat.setSpbill_create_ip("192.168.1.1");
        wechat.setTotal_fee(1);
        wechat.setAttach("111");
        String paramStr = wechat.buildRequestPara(1);
        System.out.println(paramStr);
    }



}
