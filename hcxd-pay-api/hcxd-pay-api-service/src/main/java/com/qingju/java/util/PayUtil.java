package com.qingju.java.util;

import com.albedo.java.modules.sys.domain.Dict;
import com.albedo.java.util.DictUtil;
import com.albedo.java.util.JedisUtil;
import com.albedo.java.util.Json;
import com.albedo.java.util.PublicUtil;
import com.albedo.java.util.base.Assert;
import com.albedo.java.util.spring.SpringContextHolder;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.qingju.java.common.pay.Constant;
import com.qingju.java.common.pay.ConstantDictPay;
import com.qingju.java.common.pay.ConstantPay;
import com.qingju.java.modules.pay.domain.OrderArgs;
import com.qingju.java.modules.pay.service.OrderArgsService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * Created by lijie on 2017/5/3.
 */
@Slf4j
public class PayUtil {
    public static OrderArgsService orderArgsService = SpringContextHolder.getBean(OrderArgsService.class);
    public static final String CACHE_PAY_PARAM_MAP = "payParamMap";
    private static final String KEY_PAY_PREFIX = "pay-arg-";
    private static Map<String, String> dataMap = Maps.newHashMap();

    public static void clearCache() {
        JedisUtil.removeSys(PayUtil.CACHE_PAY_PARAM_MAP);
    }


    private static String getKey(String key) {
        return KEY_PAY_PREFIX + key;
    }

    public static <T> T findParamsByClass(Integer bizType, Integer payType, Class<T> clz) {
        String paramStr = findParams(bizType, payType);
        Assert.assertIsTrue(PublicUtil.isNotEmpty(paramStr), "未知的支付信息 bizType:"+bizType+" payType:"+payType);
        return Json.parseObject(paramStr, clz);

    }

    public static String findParams(Integer bizType, Integer payType){
        try {

            String realKey = getKey(bizType+"-"+payType);
            String paramStr = (String) JedisUtil.getSys(realKey);

            if(PublicUtil.isEmpty(paramStr)){
                OrderArgs orderArgs = orderArgsService.findAllByBizTypeAndPayType(bizType, payType);
                Dict itemDict = DictUtil.getValItem(ConstantDictPay.PAY_DCIT_ARGS, String.valueOf(orderArgs.getArgsType()));
                List<Dict> dictList = DictUtil.getDictList(itemDict.getCode());

                JSONObject jsonObject = new JSONObject();
                if(PublicUtil.isNotEmpty(dictList)){

                    String codeConstant = ConstantPay.TRADE_TYPE_ALIPAY == payType ?
                            ConstantDictPay.PAY_DCIT_CONSTANT_APLIPAY : ConstantPay.TRADE_TYPE_WEIXIN == payType ?
                            ConstantDictPay.PAY_DCIT_CONSTANT_WECHAT : null;
                    if(PublicUtil.isNotEmpty(codeConstant)){
                        dictList.addAll(DictUtil.getDictList(codeConstant));
                    }else{
                        Assert.buildException("未知的支付类型 payType" + payType);
                    }
                    for (Dict item : dictList){
                        jsonObject.put(item.getKey(), item.getVal());
                    }
                }
                paramStr = jsonObject.toJSONString();
                JedisUtil.putSys(realKey, paramStr);
            }
            return paramStr;

        }catch (Exception e){
            log.warn("exception:{}",e);
            Assert.buildException("无法获取支付微信参数 bizType:"+bizType+" payType:"+payType);
        }
        return null;
    }

    public static String genPayCode(){
        return  PublicUtil.toAppendStr(
                PublicUtil.getCurrentTime("yyMMddHHmmsssss"), PublicUtil.getRandomNumber(6));
    }


}
