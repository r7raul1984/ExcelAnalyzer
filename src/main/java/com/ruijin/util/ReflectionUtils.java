package com.ruijin.util;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtils {

  public static void invokeSetterMethod(Object obj, String propertyName, Object value,
      Class<?> propertyType) {
    Class<?> type = propertyType != null ? propertyType : value.getClass();
    String setterMethodName = "set" + StringUtils.capitalize(propertyName);
    invokeMethod(obj, setterMethodName, new Class[] { type }, new Object[] { value });
  }

  private static Object invokeMethod(final Object obj, final String methodName,
      final Class<?>[] parameterTypes, final Object[] args) {
    Method method = getAccessibleMethod(obj, methodName, parameterTypes);
    if (method == null) {
      throw new IllegalArgumentException(
          "Could not find method [" + methodName + "] on target [" + obj + "]");
    }
    try {
      return method.invoke(obj, args);
    } catch (Exception e) {
      throw convertReflectionExceptionToUnchecked(e);
    }
  }

  private static Method getAccessibleMethod(final Object obj, final String methodName,
      final Class<?>... parameterTypes) {
    for (Class<?> superClass = obj.getClass();
         superClass != Object.class; superClass = superClass.getSuperclass()) {
      try {
        Method method = superClass.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method;
      } catch (NoSuchMethodException e) {
        //
      }
    }
    return null;
  }

  private static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
    if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
        || e instanceof NoSuchMethodException) {
      return new IllegalArgumentException("Reflection Exception.", e);
    } else if (e instanceof InvocationTargetException) {
      return new RuntimeException("Reflection Exception.",
          ((InvocationTargetException) e).getTargetException());
    } else if (e instanceof RuntimeException) {
      return (RuntimeException) e;
    }
    return new RuntimeException("Unexpected Checked Exception.", e);
  }
}
