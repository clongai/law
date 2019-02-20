package com.law.component;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.google.common.collect.Lists;
import com.law.annotation.EnumField;
import com.law.model.SysCode;

public class TableComp extends ComponentAdapter {

	private Log logger = LogFactory.getLog(TableComp.class);

	public TableComp(ApplicationContext applicationContext) {
	}

	@Override
	protected Object innerConvert() {
		Object body = getObj();
		if (body != null) {
			if (body instanceof PageImpl) {
				// 进行枚举值数据转换
				convertPageImpl();
			}
		}

		return null;
	}

	private PageImpl<?> convertList(List<?> list) {
		Object obj = getObj();
		List<List<Map<String, Object>>> tableContent = Lists.newArrayList();
		for (Object o : list) {
			Class<?> clazz = o.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if ("serialVersionUID".equals(field.getName()) || Modifier.isFinal(field.getModifiers())) {
					continue;
				}
				Object val = null;
				try {
					field.setAccessible(true);
					val = field.get(o);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					logger.error("pageImpl class responseBody translate happened error:", e);
				}

				// 为了获取get方法
				PropertyDescriptor pd = null;
				try {
					pd = new PropertyDescriptor(field.getName(), clazz);
				} catch (IntrospectionException e) {
					logger.warn("pageImpl class responseBody translate happened error:", e);
				}

				if (val instanceof String) {

					String codeType = null;
					if (field.getAnnotation(EnumField.class) != null) {
						codeType = field.getAnnotation(EnumField.class).codeType();
						val = enumConvert(codeType, val);
						try {
							field.set(o, val);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}

				}
			}

			// tableContent.add(rowContent);
		}
		@SuppressWarnings("deprecation")
		PageRequest pageRequest = new PageRequest(BigDecimal.ZERO.intValue(), tableContent.size() == 0 ? 1 : tableContent.size());
		return new PageImpl<>(list, pageRequest, ((PageImpl<?>) obj).getTotalElements());
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private PageImpl<?> convertPageImpl() {
		Object obj = getObj();
		List<?> list = ((PageImpl<?>) obj).getContent();
		PageImpl<?> pageResult = convertList(list);
		List<List<Map<String, Object>>> tableContent = (List<List<Map<String, Object>>>) pageResult.getContent();
		PageRequest pageRequest = new PageRequest(((PageImpl<?>) obj).getNumber(), ((PageImpl<?>) obj).getSize(), ((PageImpl<?>) obj).getSort());
		return new PageImpl<>(tableContent, pageRequest, ((PageImpl<?>) obj).getTotalElements());
	}

	private Object enumConvert(String codeType,Object val) {
		List<SysCode> sysCode = getCache().getSysCode();
		if(sysCode!=null) {
			for(SysCode sc: sysCode) {
				if(sc.getCodeType().equals(codeType)) {
					if(val.equals(""+sc.getCodeValue()))
					{
						val = sc.getCodeName();
					}
				}
			}
		}
		return val;
	}

}
