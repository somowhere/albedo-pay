package com.qingju.java.util;

import com.albedo.java.util.base.Reflections;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Created by lijie on 2017/5/3.
 */
public class PayUtilTest {
    @Test
    public void initConstantsWeixin() throws Exception {

        Object o=Constants.class.newInstance();//获取对象
        Field f=Constants.class.getField("param1");//根据key获取参数
        Field [] field=Constants.class.getFields();//获取全部参数
        String str="20";
        for(int i=0;i<field.length;i++){
            f=field[i];
            if(9==f.getModifiers()){//获取字段的修饰符，public 1,static 8
                Object val = str;
                if(f.getType().getName().indexOf("int")!=-1){
                    val = Integer.valueOf(str);
                }else if(f.getType().getName().indexOf("boolean")!=-1){
                    val = Boolean.valueOf(str);
                }
                Reflections.setFieldValue(o, f.getName(), val);
            }
        }
        assertThat(Constants.param1, is(20));
        assertThat(Constants.param1, not(15));
        assertThat(Constants.param2, is("20"));
        assertThat(Constants.param2, not("参数2"));
        assertThat(Constants.param3, is(false));
        assertThat(Constants.param3, not(true));
    }



}
class Constants{
    public static int param1=15;
    public static String param2="参数2";
    public static boolean param3=true;
}