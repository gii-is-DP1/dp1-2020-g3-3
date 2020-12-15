package org.springframework.samples.aerolineasAAAFC.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class propertyUtils {
	public static Object getProperty(Object bean, String propertyName) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	    BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
	    PropertyDescriptor propertyDescriptor = Arrays
	            .stream(beanInfo.getPropertyDescriptors())
	            .filter(pd -> pd.getName().equals(propertyName)).findFirst()
	            .get();
	    return propertyDescriptor.getReadMethod().invoke(bean);
	}
}
