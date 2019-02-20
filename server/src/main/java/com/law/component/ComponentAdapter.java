package com.law.component;

import com.law.service.CacheService;

public abstract class ComponentAdapter {

	private ThreadLocal<Object> obj = new ThreadLocal<>();
	private CacheService cacheService;
	
	
	public CacheService getCache() {
		return cacheService;
	}

	public void setCache(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	protected abstract Object innerConvert();

	public Object convert() {
		if (obj.get() == null) {
			return null;
		}
		Object o = innerConvert();
		return o;
	}
	
	public Object getObj() {
		return this.obj.get();
	}

	public void setObj(Object obj) {
		this.obj.set(obj);
	}
	
	
}
