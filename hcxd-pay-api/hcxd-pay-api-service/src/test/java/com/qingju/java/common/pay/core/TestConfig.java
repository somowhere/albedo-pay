package com.qingju.java.common.pay.core;

import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

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

import com.albedo.java.common.config.AlbedoProperties;
import com.albedo.java.util.spring.SpringContextHolder;
import com.qingju.java.pay.config.PayProperties;

/**
 * Created by lijie on 2017/5/15.
 *
 * @author 837158334@qq.com
 */
@Configuration
@EnableMybatisRepositories(
        value = {"com.albedo.java.modules.*.repository", "com.qingju.java.pay.*.repository"},
        mapperLocations = "classpath*:/mappings/modules/*/*Mapper.xml"
)
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.albedo.java.*","com.qingju.java.*"})
@AutoConfigureAfter(value = RedisAutoConfiguration.class)
@EnableConfigurationProperties({AlbedoProperties.class, PayProperties.class, RedisProperties.class})
public class TestConfig implements ResourceLoaderAware, ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.setStaticApplicationContext(applicationContext);
    }

    private ResourceLoader resourceLoader;

    @Bean
    public DataSource routingDataSource() throws SQLException {
        EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:/test-init.sql").build();

        ReplicationRoutingDataSource proxy = new ReplicationRoutingDataSource(embeddedDatabase, null);
        proxy.addSlave(embeddedDatabase);
        proxy.addSlave(embeddedDatabase);
        proxy.addSlave(embeddedDatabase);
        return proxy;
    }

    @Bean
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTransactionFactory(new ReadWriteManagedTransactionFactory());
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAware<String>() {
            @Override
            public String getCurrentAuditor() {
                return "1";
            }
        };
    }

    @Bean
    public AuditDateAware<Date> auditDateAware() {
        return new AuditDateAware<Date>() {
            @Override
            public Date getCurrentDate() {
                return new Date();
            }
        };
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

        this.resourceLoader = resourceLoader;
    }
}
