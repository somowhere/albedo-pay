package com.qingju.java.pay.config;

import com.albedo.java.common.config.AlbedoProperties;
import com.albedo.java.util.spring.SpringContextHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mybatis.domains.AuditDateAware;
import org.springframework.data.mybatis.replication.datasource.ReplicationRoutingDataSource;
import org.springframework.data.mybatis.replication.transaction.ReadWriteManagedTransactionFactory;
import org.springframework.data.mybatis.repository.config.EnableMybatisRepositories;
import org.springframework.data.mybatis.support.SqlSessionFactoryBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by lijie on 2017/5/15.
 *
 * @author 837158334@qq.com
 */
@Configuration
@EnableMybatisRepositories(value = { "com.albedo.java.modules.*.repository",
		"com.qingju.java.*.repository" }, mapperLocations = "classpath*:/mappings/modules/*/*Mapper.xml")
@EnableTransactionManagement
public class DatabaseConfiguration {

}
