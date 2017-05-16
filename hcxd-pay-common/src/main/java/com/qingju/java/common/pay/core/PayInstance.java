package com.qingju.java.common.pay.core;

import com.qingju.java.pay.vo.PayCreate;

/**
 * Created by lijie on 2017/5/3.
 */
public interface PayInstance {

    String genParams(PayCreate payCreate, String domain);

}
