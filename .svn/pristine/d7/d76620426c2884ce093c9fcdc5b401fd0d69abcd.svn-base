package com.wanmi.sbc.common.util;

import com.wanmi.sbc.common.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/***
 * 常用工具类
 * @author zhengyang
 * @since 2021-03-01
 */
@Slf4j
public final class Nutils {
    private Nutils() {}



    /***
     * 将一个对象转为字符串，为空则返回空字符串
     * @param obj   对象参数
     * @return      字符串表达
     */
    public static String toStr(Object obj) {
        return toStr(obj,false);
    }

    /***
     * 将一个对象转为字符串，为空则返回空字符串
     * @param obj   对象参数
     * @param trim  是否进行trim操作
     * @return      字符串表达
     */
    public static String toStr(Object obj, boolean trim) {
        return Objects.nonNull(obj) ? trim ? obj.toString().trim() : obj.toString() : "";
    }

    /***
     * 给空值返回一个默认值
     * @param val
     * @param defVal
     * @param <T>
     * @return
     */
    public static <T> T defaultVal(T val, T defVal) {
        return Objects.isNull(val) ? defVal : val;
    }

    /***
     * 判断两个参数是否相等
     * @param val       参数1
     * @param val1      参数2
     * @return          是否相等
     */
    public static boolean equals(Object val, Object val1) {
        if(Objects.isNull(val) && Objects.isNull(val1)){
            return true;
        }

        if(Objects.nonNull(val) && Objects.nonNull(val1)){
            return val == val1 || val.equals(val1);
        }
        return false;
    }

    /***
     * 给定值为空值则默认执行一个动作
     * @param val           给定值
     * @param defVal        默认值
     * @param consumer      动作
     * @param <T>           泛型
     */
    public static <T> void isNullAction(T val, T defVal, Consumer<T> consumer) {
        if (Objects.isNull(val)) {
            consumer.accept(defVal);
        }
    }

    /**
     * 返回Double对应的Int对象
     * @param doubleVal Double对象
     * @return          int值
     */
    public static Integer intVal(Double doubleVal){
        if (Objects.nonNull(doubleVal)){
            return doubleVal.intValue();
        }
        return 0;
    }

    /***
     * 给定值不为空值则默认执行一个动作
     * @param val           给定值
     * @param consumer      动作
     * @param <T>           泛型
     */
    public static <T> void nonNullAction(T val, Consumer<T> consumer) {
        if (Objects.nonNull(val)) {
            consumer.accept(val);
        }
    }

    /***
     * 给定值不为空值则默认执行一个带返回值的动作
     * 否则返回一个空
     * @param val           给定值
     * @param function      动作
     * @param defVal        为空默认值
     * @param <T>           泛型
     */
    public static <T, R> R nonNullActionRt(T val, Function<T, R> function, R defVal) {
        if (Objects.nonNull(val)) {
            return function.apply(val);
        }
        return defVal;
    }

    /***
     * 从一个返回对象中取得一个值
     * @param response
     * @param fieldKey
     * @return
     */
    public static String getValFromResponse(BaseResponse<?> response, String fieldKey) {
        if (Objects.nonNull(response) && Objects.nonNull(response.getContext())) {
            Field field = ReflectionUtils.findField(response.getContext().getClass(), fieldKey);
            if (Objects.nonNull(field)) {
                ReflectionUtils.makeAccessible(field);
                try {
                    return Optional.of(field.get(response.getContext())).orElse("").toString();
                } catch (Exception e) {
                    log.error("Nutils getValFromResponse error details:", e);
                }
            }
        }
        return null;
    }

    /***
     * 返回一个集合中的第一个元素
     * @param collection    集合
     * @param <T>           泛型
     * @return              集合中的第一个元素
     */
    public static <T> T getFirst(Collection<T> collection) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            return null;
        }
        if (collection.stream().findFirst().isPresent()) {
            return collection.stream().findFirst().get();
        } else {
            return null;
        }
    }

    /***
     * 如果集合不为空，执行Consumer并将集合第一个元素作为参数
     * @param collection    集合
     * @param consumer      动作
     * @param <T>           泛型
     * @return              集合中的第一个元素
     */
    public static <T> void getFirst(Collection<T> collection, Consumer<T> consumer) {
        if (Objects.isNull(collection) || collection.isEmpty()) {
            return;
        }
        if (collection.stream().findFirst().isPresent()) {
            consumer.accept(collection.stream().findFirst().get());
        }
    }
}
