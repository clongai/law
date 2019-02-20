package com.law.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.law.annotation.Component;
import com.law.service.CacheService;

@Service
public class ComponentFactory implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	private static Map<Class<?>, ComponentAdapter> componentAdapterMap = new HashMap<>(10);
	
	public ComponentAdapter get(Component componentAnno,Object body) {
		ComponentAdapter adapter = null;
		if(componentAnno!=null&&body instanceof PageImpl) {
			adapter = componentAdapterMap.get(TableComp.class);
			adapter.setObj(body);
			CacheService cache = applicationContext.getBean(CacheService.class);
			adapter.setCache(cache);
		}
		return adapter;
	}
	
	
	public boolean equalsComponent() {
		
		return false;
	}
	
	
	public void init(){
		componentAdapterMap.put(TableComp.class, new TableComp(applicationContext));
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		init();
	}
	
}
