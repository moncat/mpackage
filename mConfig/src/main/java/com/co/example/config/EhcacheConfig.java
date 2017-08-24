package com.co.example.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * @author zyl
 *
 */
@Configuration
@EnableCaching
public class EhcacheConfig {

	/**
	 * 默认 缓存 暂时不使用自己配置的cache 使用默认实现
	 * @param bean
	 * @return
	 */
//	@Primary
//	@Bean(name = "cacheManager")
//	public CacheManager cacheManager(EhCacheManagerFactoryBean bean) {
//		return new EhCacheCacheManager(bean.getObject());
//	}

	/*
	 * ehcache 主要的管理器
	 */
	@Bean(name = "appEhCacheCacheManager")
	@DependsOn("ehCacheManagerFactoryBean")
	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
		return new EhCacheCacheManager(bean.getObject());
	}

	/*
	 * 据shared与否的设置,Spring分别通过CacheManager.create()或new
	 * CacheManager()方式来创建一个ehcache基地.
	 */
	@Bean(name = "ehCacheManagerFactoryBean")
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("/ehcache.xml"));
		cacheManagerFactoryBean.setShared(true);
		return cacheManagerFactoryBean;
	}

	/**
	 * 使用ehcache
	 * 
	 * 使用ehcache主要通过spring的缓存机制，上面我们将spring的缓存机制使用了ehcache进行实现，所以使用方面就完全使用spring缓存机制就行了。
	 * 具体牵扯到几个注解： @Cacheable：负责将方法的返回值加入到缓存中，参数3 @CacheEvict：负责清除缓存，参数4
	 * 
	 * 参数解释： value：缓存位置名称，不能为空，如果使用EHCache，就是ehcache.xml中声明的cache的name
	 * key：缓存的key，默认为空，既表示使用方法的参数类型及参数值作为key，支持SpEL
	 * condition：触发条件，只有满足条件的情况才会加入缓存，默认为空，既表示全部都加入缓存，支持SpEL
	 * allEntries：CacheEvict参数，true表示清除value中的全部缓存，默认为false
	 */

}