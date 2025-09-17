package com.wanmi.sbc.common.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.base.MicroServicePage;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 实体属性copy,属性为空不copy
 * Created by hehu on 16/5/19.
 * @author QM-HEHU
 */
@Slf4j
public class KsBeanUtil {

    public static List copyListProperties(List source, Class clazz){
        List target = new ArrayList();
        source.forEach(o -> {
            try {
                target.add( JSONObject.parseObject(JSONObject.toJSONString(o),clazz));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return target;
    }

    /**
     * 转换List泛型
     * @param source 源列表
     * @param clazz 目标列表
     */
    public static <T> List<T> convertList(List source, Class<T> clazz){
        if(Objects.isNull(source)){
            return null;
        }
        List<T> res = new ArrayList<>();
        source.forEach(o -> res.add(KsBeanUtil.copyPropertiesThird(o, clazz)));
        return res;
    }

    /**
     * 复制，用不为空的字段替换为空的字段
     * @param sourceObj
     * @param targetObj
     */
    public static void copyDataToNullProperties(Object sourceObj, Object targetObj) {
        String fileName,str,getName,setName;
        List fields = new ArrayList();
        Method getMethod;
        Method setMethod;
        try {
            Class c1 = sourceObj.getClass();
            Class c2 = targetObj.getClass();
            List<Field> c1Fields = getBeanFields(c1, new ArrayList<>());
            List<Field> c2Fields = getBeanFields(c2, new ArrayList<>());

            //两个类属性比较剔除不相同的属性，只留下相同可修改的属性
            c2Fields.stream()
                    .filter(field2 -> !Modifier.isFinal(field2.getModifiers()))
                    .forEach(field2 -> c1Fields.stream()
                            .filter(field1 -> !Modifier.isFinal(field1.getModifiers()))
                            .forEach(field1 -> {
                                if (field1.getName().equals(field2.getName()) && field1.getType().equals
                                        (field2.getType())) {
                                    fields.add(field1);
                                }
                            }));

            if(fields.size() > 0){
                for (Object field : fields) {
                    //获取属性名称
                    Field f = (Field) field;
                    fileName = f.getName();
                    //属性名第一个字母大写
                    str = fileName.substring(0, 1).toUpperCase();
                    //拼凑getXXX和setXXX方法名
                    getName = "get" + str + fileName.substring(1);
                    setName = "set" + str + fileName.substring(1);
                    //获取get、set方法
                    getMethod = c1.getMethod(getName);
                    setMethod = c2.getMethod(setName, f.getType());

                    //获取属性值
                    Object o = getMethod.invoke(sourceObj);
                    //判断如果值是空直接跳过
                    if (o == null) {
                        continue;
                    } else {
                        //如果目标不为空也跳过
                        Method method2 = c2.getMethod(getName);
                        Object o2 = method2.invoke(targetObj);
                        if (o2 != null) {
                            continue;
                        }
                    }
                    //将属性值放入另一个对象中对应的属性
                    //如果有此注解，直接放值
                    if(f.isAnnotationPresent(CanEmpty.class)){
                        setMethod.invoke(targetObj, o);
                    }else if (null != o) {
                        setMethod.invoke(targetObj, o);
                    }
                }
            }
        } catch (SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 合并实体属性值,为空不覆盖
     * @param sourceObj 源对象
     * @param targetObj 目标对象
     */
    public static void copyProperties(Object sourceObj, Object targetObj) {
        String fileName,str,getName,setName;
        List fields = new ArrayList();
        Method getMethod;
        Method setMethod;
        try {
            Class c1 = sourceObj.getClass();
            Class c2 = targetObj.getClass();
            List<Field> c1Fields = getBeanFields(c1, new ArrayList<>());
            List<Field> c2Fields = getBeanFields(c2, new ArrayList<>());

            //两个类属性比较剔除不相同的属性，只留下相同可修改的属性
            c2Fields.stream()
                    .filter(field2 -> !Modifier.isFinal(field2.getModifiers()))
                    .forEach(field2 -> c1Fields.stream()
                                .filter(field1 -> !Modifier.isFinal(field1.getModifiers()))
                                .forEach(field1 -> {
                                    if (field1.getName().equals(field2.getName()) && field1.getType().equals
                                            (field2.getType())) {
                                        fields.add(field1);
                                    }
                                }));

            if(fields.size() > 0){
                for (Object field : fields) {
                    //获取属性名称
                    Field f = (Field) field;
                    fileName = f.getName();
                    //属性名第一个字母大写
                    str = fileName.substring(0, 1).toUpperCase();
                    //拼凑getXXX和setXXX方法名
                    getName = "get" + str + fileName.substring(1);
                    setName = "set" + str + fileName.substring(1);
                    //获取get、set方法
                    getMethod = c1.getMethod(getName);
                    setMethod = c2.getMethod(setName, f.getType());

                    //获取属性值
                    Object o = getMethod.invoke(sourceObj);
                    //将属性值放入另一个对象中对应的属性
                    //如果有此注解，直接放值
                    if(f.isAnnotationPresent(CanEmpty.class)){
                        setMethod.invoke(targetObj, o);
                    }else if (null != o) {
                        setMethod.invoke(targetObj, o);
                    }
                }
            }
        } catch (SecurityException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 源对象和目标对象的浅拷贝
     * 注意点：目标对象与源对象中的属性名称必须一致，否则无法拷贝
     *
     * @param sourceObj 源对象
     * @param targetObj 目标对象
     */
    public static void copyPropertiesThird(Object sourceObj, Object targetObj) {
        BeanUtils.copyProperties(sourceObj, targetObj);
    }

    /**
     * 源对象和目标对象的浅拷贝
     * 注意点：目标对象与源对象中的属性名称必须一致，否则无法拷贝
     *
     * @param sourceObj             源对象
     * @param targetObj             目标对象
     * @param ignoreProperties      不拷贝属性
     */
    public static void copyPropertiesThird(Object sourceObj, Object targetObj,String[] ignoreProperties) {
        BeanUtils.copyProperties(sourceObj, targetObj, ignoreProperties);
    }

    /**
     * 源对象和目标对象的浅拷贝
     * 忽略空值对象
     * 注意点：目标对象与源对象中的属性名称必须一致，否则无法拷贝
     *
     * @param sourceObj 源对象
     * @param targetObj 目标对象
     */
    public static void copyPropertiesIgnoreNullVal(Object sourceObj, Object targetObj) {
        copyPropertiesIgnoreNullVal(sourceObj, targetObj, null);
    }



    /**
     * 源对象和目标对象的浅拷贝
     * 忽略空值对象
     * 注意点：目标对象与源对象中的属性名称必须一致，否则无法拷贝
     *
     * @param sourceObj 源对象
     * @param targetObj 目标对象
     */
    public static void copyPropertiesIgnoreNullVal(Object sourceObj, Object targetObj, String[] ignoreProperties) {
        BeanUtils.copyProperties(sourceObj, targetObj, getNullPropertyNames(sourceObj, ignoreProperties));
    }



    /**
     * 源对象和目标对象的浅拷贝
     * 注意点：目标对象与源对象中的属性名称必须一致，否则无法拷贝
     *
     * @param sourceObj 源对象
     * @param clazz     目标对象的Class对象
     * @param <T>
     * @return          目标对象
     */
    public static <T> T copyPropertiesThird(Object sourceObj,  Class<T> clazz) {
        if (Objects.isNull(sourceObj) || Objects.isNull(clazz)) {
            return null;
        }
        T c = null;
        try {
            c = clazz.getDeclaredConstructor().newInstance();
            KsBeanUtil.copyPropertiesThird(sourceObj, c);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error("KsBeanUtil -> copyPropertiesThird error ", e);
        }
        return c;
    }

    /**
     * 使用cglib实现对象浅拷贝，性能比BeanUtils更好
     * 注意：仅支持符合javaBean规范的属性，属性类型必须相同，不支持原始类型和包装类型互相转换，不支持泛型属性
     *
     * @param sourceObj
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copyPropertiesCglib(Object sourceObj,  Class<T> clazz) {
        T c = null;
        try {
            c = clazz.getDeclaredConstructor().newInstance();
            KsBeanUtil.copyPropertiesCglib(sourceObj, c);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error("KsBeanUtil -> copyPropertiesCglib error ", e);
        }
        return c;
    }

    /**
     * 使用cglib实现对象浅拷贝，性能比BeanUtils更好
     * 注意：仅支持符合javaBean规范的属性，属性类型必须相同，不支持原始类型和包装类型互相转换，不支持泛型属性
     *
     * @param source
     * @param target
     */
    public static void copyPropertiesCglib(Object source, Object target) {
        BeanCopier beanCopier = getCacheBeanCopier(source.getClass(), target.getClass());
        beanCopier.copy(source, target, null);
    }

    private final static Map<Class<?>, Map<Class<?>, BeanCopier>> beanCopierCache = new HashMap<>();

    private static <S, T> BeanCopier getCacheBeanCopier(Class<S> source, Class<T> target) {
        Map<Class<?>, BeanCopier> copierMap = beanCopierCache.computeIfAbsent(source
                , aClass -> new HashMap<>(1));
        return copierMap.computeIfAbsent(target
                , aClass -> BeanCopier.create(source, target, false));
    }

    /**
     * 用于源对象和目标对象中属性类型不同的深度拷贝
     * 注意点：目标对象与源对象中的属性名称必须一致，否则无法拷贝
     * 测试示例可参考 {com.wanmi.sbc.convert.ConvertUtils}
     * @param source        源对象
     * @param targetClass   目标对象的Class对象
     * @param <T>
     * @return              目标对象
     */
    public static <T> T convert(Object source, Class<T> targetClass, JSONWriter.Feature... features) {
        if (Objects.isNull(source) || Objects.isNull(targetClass)){
            return null;
        }
        String sourceJsonStr = JSONObject.toJSONString(source,features);
        return JSONObject.parseObject(sourceJsonStr, targetClass);
    }

    /**
     * 用于源集合和目标集合之间的深度转换
     * 注意点：目标集合对象与源集合对象中的属性名称必须一致，否则无法拷贝
     * 测试示例可参考 {com.wanmi.sbc.convert.ConvertUtils}
     * @param sourceList    源集合
     * @param targetClass   目标对象的Class对象
     * @param <S>           源集合对象类型
     * @param <T>           目标集合对象类型
     * @return              目标集合
     */
    public static <S,T> List<T> convert(List<S> sourceList, Class<T> targetClass,JSONWriter.Feature... features) {
        if (Objects.isNull(sourceList)) {
          return null;
        }
        return sourceList.stream().map(s -> convert(s, targetClass,features))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 用于源分页集合和目标分页集合之间的深度转换
     * 注意点：目标分页元素对象与源分页元素对象中的属性名称必须一致，否则无法拷贝
     * 测试示例可参考 {com.wanmi.sbc.convert.ConvertUtils}
     * @param sourcePage    源分页集合
     * @param targetClass   目标元素对象中Class对象
     * @param <S>           源分页对象中元素类型
     * @param <T>           目标分页对象中元素类型
     * @return              目标分页集合
     */
    public static <S,T> MicroServicePage<T> convertPage(Page<S> sourcePage, Class<T> targetClass) {
        if(CollectionUtils.isEmpty(sourcePage.getContent())){
            return new MicroServicePage<>(
                    Collections.emptyList(),
                    PageRequest.of(sourcePage.getNumber(), sourcePage.getSize(), sourcePage.getSort()),
                    0
            );
        }
        return new MicroServicePage<>(
                convert(sourcePage.getContent(), targetClass),
                PageRequest.of(sourcePage.getNumber(), sourcePage.getSize(), sourcePage.getSort()),
                sourcePage.getTotalElements()
        );
    }

    /**
     * 获取类所有属性（包含父类属性）
     * @param cls 类
     * @param fields 属性List
     * @return 所有fields
     */
    private static List<Field> getBeanFields(Class cls, List<Field> fields){
        fields.addAll(Arrays.asList(cls.getDeclaredFields()));
        if(Objects.nonNull(cls.getSuperclass())){
            fields = getBeanFields(cls.getSuperclass() , fields);
        }
        return fields;
    }


    /**
     * 将List<T>转换为List<Map<String, Object>>
     *
     * @param objList
     * @return
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = (Map<String, Object>) JSON.toJSON(bean);
                list.add(map);
            }
        }
        return list;
    }

    /***
     * 获得指定实例空值字段
     * @param source                实例对象
     * @param ignoreProperties      不涉及对象
     * @return          空值
     */
    private static String[] getNullPropertyNames(Object source, String[] ignoreProperties) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            if (Objects.isNull(src.getPropertyValue(pd.getName()))) {
                emptyNames.add(pd.getName());
            }
        }
        if(Objects.nonNull(ignoreProperties) && ignoreProperties.length > 0){
            for (String ignoreProperty : ignoreProperties) {
                emptyNames.add(ignoreProperty);
            }
        }
        return emptyNames.toArray(new String[emptyNames.size()]);
    }


}