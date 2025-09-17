package com.wanmi.sbc.mq.report.entity;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xuyunpeng
 * @className RepostUtil
 * @description 工具
 * @date 2021/5/31 5:33 下午
 **/
public class ReportUtil {

    /**
     * 分割list
     *
     * @param collection 目标集合
     * @param maxSize    分页数量
     * @param splitSize  分割值
     * @return
     */
    public static <T> List<Collection> splitList(Collection<T> collection, int maxSize, int splitSize) {
        if (CollectionUtils.isEmpty(collection)) {
            return Collections.emptyList();
        }
        return Stream.iterate(0, f -> f + 1)
                .limit(maxSize)
                .parallel()
                .map(a -> collection.parallelStream().skip(a * (long) splitSize).limit(splitSize).collect(Collectors.toList()))
                .filter(b -> !b.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * 计算页码
     *
     * @param count
     * @param size
     * @return
     */
    public static long calPage(long count, int size) {
        long page = count / size;
        if (count % size == 0) {
            return page;
        } else {
            return page + 1;
        }
    }
}
