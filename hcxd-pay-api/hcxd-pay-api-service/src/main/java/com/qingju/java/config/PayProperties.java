package com.qingju.java.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by lijie on 2017/5/3.
 */
@ConfigurationProperties(prefix = "pay",
        ignoreUnknownFields = true,
        ignoreInvalidFields= true,
        exceptionIfInvalid = false)
@Data @ToString
public class PayProperties {

    public String domain;

}
