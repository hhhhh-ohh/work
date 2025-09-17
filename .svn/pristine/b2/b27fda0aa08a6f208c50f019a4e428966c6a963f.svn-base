package com.wanmi.sbc.common.util;

import com.google.common.collect.Lists;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 比较两个对象的属性值
 * @author xufeng
 */
public class CompareUtil {

    /**
     * 比较两个实体属性值，返回一个boolean,true则表时两个对象中的属性值无差异
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @return 属性差异比较结果boolean
     */
    public static boolean compareObject(Object oldObject, Object newObject) {
        Map<String, List<Object>> resultMap=compareFields(oldObject,newObject,null);
        return Objects.isNull(resultMap) || resultMap.size() <= 0;
    }

    /**
     * 比较两个实体属性值，返回一个boolean,true则表时两个对象中的属性值无差异
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @return 属性差异比较结果boolean
     */
    public static boolean compareObject(Object oldObject, Object newObject, String[] ignoreArr) {
        Map<String, List<Object>> resultMap=compareFields(oldObject,newObject,ignoreArr);
        return Objects.isNull(resultMap) || resultMap.size() <= 0;
    }

    /**
     * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个Map分别存oldObject,newObject此属性名的值
     * @param oldObject 进行属性比较的对象1
     * @param newObject 进行属性比较的对象2
     * @param ignoreArr 忽略比较的字段
     * @return 属性差异比较结果map
     */
    public static Map<String, List<Object>> compareFields(Object oldObject, Object newObject, String[] ignoreArr) {
        try{
            if (Objects.isNull(oldObject)||Objects.isNull(newObject)){
                return null;
            }
            Map<String, List<Object>> map = new HashMap<>();
            List<String> ignoreList = null;
            if(Objects.nonNull(ignoreArr) && ignoreArr.length > 0){
                // array转化为list
                ignoreList = Arrays.asList(ignoreArr);
            }
            // 只有两个对象都是同一类型的才有可比性
            if (oldObject.getClass() == newObject.getClass()) {
                // 获取object的属性描述
                PropertyDescriptor[] pds =
                        Introspector.getBeanInfo(oldObject.getClass(), Object.class).getPropertyDescriptors();
                // 这里就是所有的属性了
                for (PropertyDescriptor pd : pds) {
                    // 属性名
                    String name = pd.getName();
                    // 如果当前属性选择忽略比较，跳到下一次循环
                    if(ignoreList != null && ignoreList.contains(name)){
                        continue;
                    }
                    // get方法
                    Method readMethod = pd.getReadMethod();
                    // 在oldObj上调用get方法等同于获得oldObj的属性值
                    Object oldObj = readMethod.invoke(oldObject);
                    // 在newObj上调用get方法等同于获得newObj的属性值
                    Object newObj = readMethod.invoke(newObject);

                    if(Objects.isNull(oldObj) && Objects.isNull(newObj)){
                        continue;
                    }else if(Objects.isNull(oldObj)){
                        List<Object> list = Lists.newArrayList();
                        list.add(null);
                        list.add(newObj);
                        map.put(name, list);
                        continue;
                    }
                    // 比较这两个值是否相等,不等就可以放入map了
                    if (!oldObj.equals(newObj)) {
                        if(oldObj instanceof BigDecimal && newObj instanceof  BigDecimal){
                            if (((BigDecimal) oldObj).compareTo((BigDecimal) newObj) == 0){
                                continue;
                            }
                        }
                        List<Object> list = Lists.newArrayList();
                        list.add(oldObj);
                        list.add(newObj);
                        map.put(name, list);
                    }
                }
            }
            return map;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

