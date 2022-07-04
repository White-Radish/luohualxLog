package com.carrot.luohualx.utils;

import com.carrot.luohualx.enums.ErrorCode;
import com.carrot.luohualx.exception.LuohualxException;
import com.carrot.luohualx.service.ReflectionService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.core.DefaultParameterNameDiscoverer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author carrot
 * @date 2022/7/1
 */
public class LuohualxReflectUtils {
    /**
     * 获取目标方法
     *
     * @param proxyObject
     * @param methodStr
     * @return
     */
    public static Method getMethod(Class proxyObject, String methodStr) {
        Method[] methods = proxyObject.getMethods();
        for (Method method : methods) {
            if (method.getName().equalsIgnoreCase(methodStr)) {
                return method;
            }
        }
        return null;
    }

    /**
     * 获取方法的参数列表并进行赋值
     * @param method
     * @param paramMap
     * @return
     */
    public static List<Object> fillValueToMethodParam(Method method, Map<String, Object> paramMap) {
        List<Object> valueList = new ArrayList<>();
        // 利用Spring提供的类获取方法形参名
        DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
        String[] param = nameDiscoverer.getParameterNames(method);
        for (int i = 0; i < method.getParameterTypes().length; i++) {
            Class<?> parameterType = method.getParameterTypes()[i];
            Object object = null;

            if(isPrimitive(parameterType)) {
                if(param != null && paramMap.containsKey(param[i])){
                    object = paramMap.get(param[i]);
                    object = ConvertUtils.convert(object, parameterType);
                }
            }else  {
                try {
                    object = getInstance(parameterType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 赋值
                try {
                    BeanUtils.populate(object, paramMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

//            if (param != null && paramMap.containsKey(param[i])) {
//                object = paramMap.get(param[i]);
//            }
            valueList.add(object);
        }
        return valueList;
    }
    public static Object getInstance(Class<?> parameterType) throws Exception {
        if(parameterType.isAssignableFrom(List.class)) {
            return  new ArrayList();

        }else if(parameterType.isAssignableFrom(Map.class)) {
            return new HashMap();
        }else if(parameterType.isAssignableFrom(Set.class)) {
            return  new HashSet();
        }
        return parameterType.newInstance();
    }

    public static Object invokeService(Class beanClass, String methodName, Map<String, Object> paramMap) throws Exception {
        // 从Spring中获取代理对象（可能被JDK或者CGLIB代理）
        if (ReflectionService.applicationContext == null) {
            return null;
        }

        Object proxyObject = null;
        try {
            proxyObject = ReflectionService.applicationContext.getBean(beanClass);
        } catch (BeansException e) {
            throw new LuohualxException(ErrorCode.FAILED.getCode(), e.getMessage());
        }
        // 获取代理对象执行的方法
        Method method = LuohualxReflectUtils.getMethod(proxyObject.getClass(), methodName);
        // 获取代理对象中的目标对象
        Class target = AopUtils.getTargetClass(proxyObject);
        // 获取目标对象的方法
        Method targetMethod = LuohualxReflectUtils.getMethod(target, methodName);
        if (method == null) {
            throw new LuohualxException(ErrorCode.NO_DATA_ERROR.getCode(), "没有找到对应的方法");
        }
        // 获取方法执行的参数
        List<Object> objects = LuohualxReflectUtils.fillValueToMethodParam(targetMethod, paramMap);
        // 执行方法
        return method.invoke(proxyObject, objects.toArray());
    }


    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否为基本类型
     * @param clz
     * @return
     */
    public static boolean isPrimitive(Class clz) {
        boolean primitive = clz.isPrimitive();
        if (!primitive) {
            primitive = isWrapClass(clz);
            if (String.class.getName().equals(clz.getName())
                    || Date.class.getName().equals(clz.getName())) {
                primitive = true;
            }
        }

        return primitive;
    }

    /**
     * 判断字段属性是基础类型还是对象
     *
     * @param field
     * @return true 表示是基础类型 类似String int 等
     */
    public static boolean isPrimitive(Field field) {
        return isPrimitive(field.getType());
    }
}
