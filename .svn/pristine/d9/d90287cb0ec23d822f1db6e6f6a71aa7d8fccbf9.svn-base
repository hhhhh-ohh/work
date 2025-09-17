package com.wanmi.sbc.common.util;

import com.alibaba.fastjson2.JSON;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/***
 * Bean工具类
 * @author zhengyang
 * @since 2021-03-01
 */
@Slf4j
public final class BeanUtils {
    private BeanUtils() {

    }

    /**
     * 将原类型转为指定类型返回
     *
     * @param source
     * @param targetCls
     * @param <T>
     * @return
     */
    public static <T> T beanCopy(Object source, Class<T> targetCls) {
        if (Objects.isNull(source)) {
            return null;
        }

        // 判断是否存在无参构造方法
        boolean hasConstructor = false;

        Constructor[] constructors = targetCls.getConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                hasConstructor = true;
                break;
            }
        }

        if (!hasConstructor) {
            throw new RuntimeException("unsupport class:" + targetCls.getName() + "! must has a no args constructor!");
        }

        T target = null;
        try {
            target = targetCls.getDeclaredConstructor().newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            log.error("BeanUtils beanCopy error!", e);
        }

        return target;
    }

    /**
     * 将原类型中的属性赋值给指定类型中的属性
     * 对于SpringBeanUtils copyProperties的代理
     *
     * @param source
     * @param target
     * @return
     */
    public static void beanCopy(Object source, Object target) {
        if (Objects.isNull(source) || Objects.isNull(target)) {
            return;
        }
        try {
            org.springframework.beans.BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            log.error("BeanUtils beanCopy error!", e);
        }
    }


    /**
     * 转换实体类
     * @param list
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> castEntity(List<Object[]> list, Class<T> clazz) {
        List<T> returnList = new ArrayList<T>();
        try {
            if (CollectionUtils.isEmpty(list)) {
                return returnList;
            }
            Object[] co = list.get(0);
            Class[] c2 = new Class[co.length];
            //确定构造方法
            for (int i = 0; i < co.length; i++) {
                if (co[i] != null) {
                    c2[i] = co[i].getClass();
                } else {
                    c2[i] = String.class;
                }
            }
            for (Object[] o : list) {
                Constructor<T> constructor = clazz.getConstructor(c2);
                returnList.add(constructor.newInstance(o));
            }
            return returnList;
        }catch (Exception e){
            log.error("BeanUtils.castEntity error!",e);
        }
        return returnList;
    }

    public static <T> T beanCovert(Object source, Class<T> clazz) {
        String jsonString = JSON.toJSONString(source);
        return JSON.parseObject(jsonString,clazz);
    }
}
