package com.co.example.base.cache.ehcache;

public interface EhCacheService {
	
	Object get(Long id);
	
	void put(Object obj);
	 
	void delete(Long id);
	
	void deleteAll();
 
 
}
