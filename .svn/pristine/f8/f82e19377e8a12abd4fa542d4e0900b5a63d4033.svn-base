package com.wanmi.sbc.common.util;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;

import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description JPA相关工具类
 * @author  daiyitian
 * @date 2021/4/27 14:14
 **/
public final class JpaUtil {

    /**
     * 字符串
     * @param tuple
     * @param name
     * @param cols
     * @return
     */
    public static String toString(Tuple tuple, String name, List<String> cols) {
        if(!cols.contains(name)){
            return null;
        }
        return tuple.get(name, String.class);
    }

    /**
     * 删除标识
     * @param tuple
     * @param name
     * @param cols
     * @return
     */
    public static DeleteFlag toDeleteFlag(Tuple tuple, String name, List<String> cols) {
        if(!cols.contains(name)){
            return null;
        }
        return tuple.get(name, DeleteFlag.class);
    }

    /**
     * Long
     * @param tuple
     * @param name
     * @param cols
     * @return
     */
    public static Long toLong(Tuple tuple, String name, List<String> cols) {
        if(!cols.contains(name)){
            return null;
        }
        return tuple.get(name, Long.class);
    }

    /**
     * Long
     * @param tuple
     * @param name
     * @param cols
     * @return
     */
    public static Integer toInteger(Tuple tuple, String name, List<String> cols) {
        if(!cols.contains(name)){
            return null;
        }
        return tuple.get(name, Integer.class);
    }

    /**
     * 时间
     * @param tuple
     * @param name
     * @param cols
     * @return
     */
    public static LocalDateTime toDate(Tuple tuple, String name, List<String> cols) {
        if(!cols.contains(name)){
            return null;
        }
        return tuple.get(name, LocalDateTime.class);
    }

    /**
     * boolFlag
     * @param tuple
     * @param name
     * @param cols
     * @return
     */
    public static BoolFlag toBoolFlag(Tuple tuple, String name, List<String> cols) {
        if(!cols.contains(name)){
            return null;
        }
        return tuple.get(name, BoolFlag.class);
    }

    /**
     * 自定义类
     * @param tuple
     * @param name
     * @param cols
     * @return
     */
    public static <T> T toClass(Tuple tuple, String name, List<String> cols, Class<T> clazz) {
        if(!cols.contains(name)){
            return null;
        }
        return tuple.get(name, clazz);
    }
}
