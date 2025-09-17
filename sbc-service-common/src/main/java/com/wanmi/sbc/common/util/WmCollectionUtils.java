package com.wanmi.sbc.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.lang.Nullable;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 集合容器工具集合类
 * @author zhengyang
 * @date 2021/6/3 16:47
 **/
public class WmCollectionUtils {

    private WmCollectionUtils() {}

    /**
     * 数字1
     */
    private static final Integer ONE = 1;

    /***
     * 判断一个集合是否为空
     * @param collection    参数集合类
     * @return              是否为空
     */
    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return (Objects.isNull(collection) || collection.isEmpty());
    }

    /***
     * 判断一个Map是否为空
     * @param map           Map类
     * @return              是否为空
     */
    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return (Objects.isNull(map) || map.isEmpty());
    }

    /***
     * 判断一个集合是否不为空
     * @param collection    参数集合类
     * @return              是否为空
     */
    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !isEmpty(collection);
    }

    /***
     * 判断一个Map是否不为空
     * @param map           Map类
     * @return              是否为空
     */
    public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 集合中仅有一个对象
     * @param collection    集合对象
     * @return              是否不为空并且仅仅存在一个对象
     */
    public static boolean onlyOne(@Nullable Collection<?> collection){
        return isNotEmpty(collection) && collection.size() == ONE;
    }

    /***
     * 完成一次类型转换
     * @param list      来源List
     * @param mapper    转换Function
     * @param <T>       来源泛型
     * @param <R>       转化泛型
     * @return          结果List
     */
    public static <T,R> List<R> convert(Collection<T> list, Function<T, R> mapper){
        return convert(list, mapper, false);
    }


    /***
     * 完成一次类型转换
     * @param list              来源List
     * @param mapper            转换Function
     * @param needDistinct      是否需要去重
     * @param <T>               来源泛型
     * @param <R>               转化泛型
     * @return                  结果List
     */
    public static <T,R> List<R> convert(Collection<T> list, Function<T, R> mapper, boolean needDistinct){
        if(isEmpty(list)){
            return Lists.newArrayList();
        }
        Stream<R> stream = list.stream().map(mapper).filter(Objects::nonNull);
        if (needDistinct) {
            return stream.distinct().collect(Collectors.toList());
        }
        return stream.collect(Collectors.toList());
    }

    /***
     * 如果集合不为空，则循环集合
     * @param collection    集合对象
     * @param <T>           集合泛型
     */
    public static <T> void notEmpty2Loop(Collection<T> collection, Consumer<T> consumer){
        if (isNotEmpty(collection)) {
            collection.forEach(consumer);
        }
    }

    /***
     * 如果集合不为空，则转为Map
     * 值为自身
     * @param collection    集合对象
     * @param <T>           集合泛型
     */
    public static <T, K, U> Map<K, U> notEmpty2Map(Collection<T> collection, Function<T, K> keyMapper) {
        return notEmpty2Map(collection, keyMapper, v -> (U)v, (o, n) -> o);
    }

    /***
     * 如果集合不为空，则转为Map
     * @param collection    集合对象
     * @param <T>           集合泛型
     */
    public static <T, K, U> Map<K, U> notEmpty2Map(Collection<T> collection, Function<T, K> keyMapper,
                                            Function<T, U> valueMapper) {
        return notEmpty2Map(collection, keyMapper, valueMapper, (o, n) -> o);
    }

    /***
     * 如果集合不为空，则转为Map
     * @param collection    集合对象
     * @param <T>           集合泛型
     */
    public static <T, K, U> Map<K, U> notEmpty2Map(Collection<T> collection, Function<T, K> keyMapper,
                                            Function<T, U> valueMapper, BinaryOperator<U> mergeFunction) {
        if (isNotEmpty(collection)) {
            return collection.stream().collect(Collectors.toMap(keyMapper,valueMapper,mergeFunction));
        }
        return Maps.newHashMap();
    }

    /***
     * 在集合中查询第一条
     * @param collection    集合
     * @param <T>           泛型
     * @return              第一条对象
     */
    public static <T> T findFirst(Collection<T> collection) {
        return findFirst(collection, null);
    }

    /***
     * 在集合中查询第一条
     * @param collection    集合
     * @param predicate     过滤函数
     * @param <T>           泛型
     * @return              第一条对象
     */
    public static <T> T findFirst(Collection<T> collection, Predicate<? super T> predicate) {
        if (isNotEmpty(collection)) {
            if (Objects.nonNull(predicate)) {
                Optional<T> first = collection.stream().filter(predicate).findFirst();
                if (first.isPresent()) {
                    return first.get();
                }
            } else if (collection.stream().findFirst().isPresent()){
                return collection.stream().findFirst().get();
            }
        }
        return null;
    }
}
