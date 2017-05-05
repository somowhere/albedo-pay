package com.qingju.java.util;

import com.albedo.java.common.data.mybatis.persistence.BaseEntity;
import com.albedo.java.common.data.mybatis.persistence.DynamicSpecifications;
import com.albedo.java.common.data.mybatis.persistence.SpecificationDetail;
import com.albedo.java.modules.sys.domain.Dict;
import com.albedo.java.util.DictUtil;
import com.albedo.java.util.PublicUtil;
import com.albedo.java.util.base.Assert;
import com.albedo.java.util.base.Reflections;
import com.albedo.java.util.domain.QueryCondition;
import com.albedo.java.util.spring.SpringContextHolder;
import com.qingju.java.common.pay.ConstantDictPay;
import com.qingju.java.common.pay.ConstantPay;
import com.qingju.java.common.pay.core.vo.Weixin;
import com.qingju.java.modules.pay.domain.OrderArgs;
import com.qingju.java.modules.pay.service.OrderArgsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by lijie on 2017/5/3.
 */
@Slf4j
public class PayUtil {

    public OrderArgsService orderArgsService = SpringContextHolder.getBean(OrderArgsService.class);

    public void initConstants(Integer bizType,Integer payType){
        try {
            OrderArgs orderArgs = orderArgsService.findAllByBizTypeAndPayType(bizType, payType);
            Dict itemDict = DictUtil.getValItem(ConstantDictPay.PAY_ARGS, String.valueOf(orderArgs.getArgsType()));
            List<Dict> dictList = DictUtil.getDictList(itemDict.getCode());
            ConstantPay obj = ConstantPay.class.newInstance();
            if(PublicUtil.isNotEmpty(dictList)){
                for (Dict item : dictList){
                    Reflections.setFieldValue(obj, item.getKey(), item.getVal());
                }
            }
        }catch (Exception e){
            log.warn("exception:{}",e);
            Assert.buildException("无法获取支付微信参数 bizType:"+bizType+" payType:"+payType);
        }
    }

    public String genOrderCode(){
        return  PublicUtil.toAppendStr(
                PublicUtil.getCurrentTime("yyMMddHHmmsssss"), PublicUtil.getRandomNumber(6));
    }


}
