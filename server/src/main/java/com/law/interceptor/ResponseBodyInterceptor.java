package com.law.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.law.annotation.Component;
import com.law.component.ComponentAdapter;
import com.law.component.ComponentFactory;


@ControllerAdvice
public class ResponseBodyInterceptor implements ResponseBodyAdvice<Object>{

	@Autowired
	ComponentFactory componentFactory;
	
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> arg3, ServerHttpRequest request, ServerHttpResponse response) {
		ComponentAdapter componentAdapter = null;
		//获取注解
		Component methodAnnotation = methodParameter.getMethodAnnotation(Component.class);
		if(methodAnnotation!=null) {//获取注解对应的处理适配器
			componentAdapter = componentFactory.get(methodAnnotation, body);
			if(componentAdapter!=null) {
				componentAdapter.convert();
			}
		}
		return body;
	}

	@Override
	public boolean supports(MethodParameter arg0, Class<? extends HttpMessageConverter<?>> arg1) {
		return true;
	}

}
