package com.co.example.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.co.example.common.utils.PageInterceptor;


@Configuration
@PropertySource("classpath:application-mybatis.properties")
public class Transaction {
	
	@Inject
	DataSource dataSource;
	
	@Value("${mybatis.mapperLocations}")
	private String mapperLocations;
	
	@Value("${mybatis.databaseType}")
	private String databaseType;
	
	
	@Bean(name = "pageHelper")
    public Interceptor pageHelper() {
	  	Interceptor pageHelper = new PageInterceptor();
        Properties p = new Properties();
        p.setProperty("databaseType", databaseType);
        pageHelper.setProperties(p);
        return pageHelper;
    }
	
	@DependsOn("pageHelper")
	@Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(Interceptor pageHelper) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setFailFast(true);
        //new PageInterceptor();
        sessionFactory.setPlugins(new Interceptor[] { pageHelper });
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return sessionFactory.getObject();
    }

	@DependsOn("dataSource")
	@Bean(name = "txManager")
	public PlatformTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@DependsOn("txManager")
	@Bean(name = "transactionInterceptor")
	public TransactionInterceptor transactionInterceptor(PlatformTransactionManager platformTransactionManager) {
		TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
		// 事物管理器
		transactionInterceptor.setTransactionManager(platformTransactionManager);
		Properties transactionAttributes = new Properties();
		// 新增
		transactionAttributes.setProperty("insert*", "PROPAGATION_REQUIRED,-Throwable");
		transactionAttributes.setProperty("add*", "PROPAGATION_REQUIRED,-Throwable");
		// 修改
		transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED,-Throwable");
		transactionAttributes.setProperty("merge*", "PROPAGATION_REQUIRED,-Throwable");
		transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED,-Throwable");
		// 删除
		transactionAttributes.setProperty("delete*", "PROPAGATION_REQUIRED,-Throwable");
		//其他REQUIRED
		transactionAttributes.setProperty("do*", "PROPAGATION_REQUIRED,-Throwable");
		transactionAttributes.setProperty("sync*", "PROPAGATION_REQUIRED,-Throwable");
		transactionAttributes.setProperty("lock*", "PROPAGATION_REQUIRED,-Throwable");
		// 查询
		transactionAttributes.setProperty("select*", "PROPAGATION_REQUIRED,-Throwable,readOnly");
		
		transactionInterceptor.setTransactionAttributes(transactionAttributes);
		return transactionInterceptor;
	}

	// 代理到ServiceImpl的Bean
	@Bean
	public BeanNameAutoProxyCreator transactionAutoProxy() {
		BeanNameAutoProxyCreator transactionAutoProxy = new BeanNameAutoProxyCreator();
		transactionAutoProxy.setProxyTargetClass(true);
		transactionAutoProxy.setBeanNames("*ServiceImpl");
		transactionAutoProxy.setInterceptorNames("transactionInterceptor");
		return transactionAutoProxy;
	}
}
