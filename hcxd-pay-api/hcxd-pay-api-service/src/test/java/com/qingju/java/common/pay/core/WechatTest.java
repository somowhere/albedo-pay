package com.qingju.java.common.pay.core;

import com.albedo.java.modules.sys.domain.Dict;
import com.albedo.java.modules.sys.service.DictService;
import com.qingju.java.common.pay.Constant;
import com.qingju.java.common.pay.ConstantPay;
import com.qingju.java.common.pay.core.param.PayAlipayParam;
import com.qingju.java.common.pay.core.param.PayWechatParam;
import com.qingju.java.common.pay.core.vo.Wechat;
import com.qingju.java.config.PayProperties;
import com.qingju.java.util.PayUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;


/**
 * Created by lijie on 2017/4/28.
 */
@ContextConfiguration(classes = {RedisAutoConfiguration.class, TestConfig.class},
        initializers = { ConfigFileApplicationContextInitializer.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class WechatTest {

    @Autowired
    PayProperties payProperties;

    @Autowired
    DictService dictService;

    @org.junit.Test
    public void notifyVerify() throws Exception {
//        Dict dict = dictService.findOne("cabfaded960e47b1be0ae4c796f98bc7");
//        System.out.println(dict);
        PayWechatParam payWechatParam = PayUtil.
                findParamsByClass(Constant.ORDER_TYPE_BIZ_BASE, ConstantPay.TRADE_TYPE_WEIXIN,
                        PayWechatParam.class);
        Wechat wechat = new Wechat(payWechatParam, payProperties.getDomain());
        wechat.setBody("主题");
        wechat.setOut_trade_no("aaaaaaa444");
        wechat.setSpbill_create_ip("192.168.1.1");
        wechat.setTotal_fee(1);
        wechat.setAttach("111");
        String paramStr = wechat.buildRequestPara(1);
        System.out.println(paramStr);
        assertThat(paramStr, is(notNullValue()));
    }



}
