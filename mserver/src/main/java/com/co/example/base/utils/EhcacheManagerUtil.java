package com.co.example.base.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheManagerUtil {
		
	public static CacheManager manager = CacheManager.create();

	public static void put(String cacheName, String key, Object value) {
		Cache cache = manager.getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
	}

	public static Object get(String cacheName, String key) {
		Cache cache = manager.getCache(cacheName);
		Element element = cache.get(key);
		return element == null ? null : element.getObjectValue();
	}

	public static void remove(String cacheName, String key) {
		Cache cache = manager.getCache(cacheName);
		cache.remove(key);
	}

	public static void removeAll(String cacheName) {
		Cache cache = manager.getCache(cacheName);
		cache.removeAll();
	}

	public static Cache get(String cacheName) {
		return manager.getCache(cacheName);
	}
	
}