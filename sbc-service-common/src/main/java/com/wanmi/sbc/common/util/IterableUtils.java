package com.wanmi.sbc.common.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author houshuai
 * @date 2021/5/28 18:12
 * @description <p> 集合拆分 </p>
 */
public class IterableUtils {

    /**
     * 将list平均拆分
     * @param collection
     * @param maxSize
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> collection, int maxSize) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyList();
        }
        int size = CollectionUtils.size(collection);
        int splitSize = (size + maxSize - 1) / maxSize;
        return Stream.iterate(0, f -> f + 1)
                .limit(maxSize)
                .parallel()
                .map(a -> collection.stream().skip((long) a * splitSize).limit(splitSize).collect(Collectors.toList()))
                .filter(b -> !b.isEmpty())
                .collect(Collectors.toList());
    }
}
