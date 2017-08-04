package com.co.example.base.cache.ehcache;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@CacheConfig(cacheNames={"demo"})  //cache name 
public class EhCacheServiceImpl implements EhCacheService {

	/** cache name 可以用@CacheConfig指定
	 *  value属性表示使用哪个缓存策略，缓存策略在ehcache.xml
	 *  eg:@Cacheable(value=DEMO_CACHE_NAME)
	 */
	//public static final String DEMO_CACHE_NAME = "demo";
	
	
	//这里的单引号不能少，否则会报错，被识别是一个对象;
    public static final String CACHE_KEY = "'info'";
 
    
	//默认从缓存获取，如果缓存没有，则运行方法体，并将返回数据写到缓存
	@Cacheable(key="'info_'+#id")
	@Override
	public Object get(Long id) {
		log.debug("没走缓存，从数据库获取");
		return null;
	}
	
	//直接执行方法体，然后将返回数据写到缓存
	@CachePut(key="'info_'+#id")
	@Override
	public void put(Object obj) {
		
	}

	@CacheEvict(key = "'info_'+#id")//这是清除缓存.
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	@CacheEvict(allEntries=true)
	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	
    
   
}