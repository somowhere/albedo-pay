package com.qingju.java.pay.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.ToString;

/**
 * Created by lijie on 2017/5/3.
 */
@ConfigurationProperties(prefix = "pay", ignoreUnknownFields = true, ignoreInvalidFields = true, exceptionIfInvalid = false)
@Data
@ToString
public class PayProperties {

	private String domain;

	private String appDomain;

}
