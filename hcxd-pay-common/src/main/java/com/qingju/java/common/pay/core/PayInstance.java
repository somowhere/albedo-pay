package com.qingju.java.common.pay.core;

import com.qingju.java.vo.pay.PayMsg;

/**
 * Created by lijie on 2017/5/3.
 */
public interface PayInstance {

    String create(PayMsg payMsg, String domain);

}
