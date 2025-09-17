package com.wanmi.sbc.common.base;

import com.wanmi.sbc.common.enums.SortType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 请求基类
 * Created by aqlu on 15/11/30.
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class BaseQueryRequest extends BaseRequest implements Serializable {

    /**
     * 第几页
     */
    @Schema(description = "第几页")
    private Integer pageNum = 0;

    /**
     * 每页显示多少条
     */
    @Schema(description = "每页显示多少条")
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private String sortColumn;

    /**
     * 排序规则 desc asc
     */
    @Schema(description = "排序规则 desc asc")
    private String sortRole;

    /**
     * 排序类型
     */
    @Schema(description = "排序类型")
    private String sortType;

    /**
     * 多重排序
     * 内容：key:字段,value:desc或asc
     */
    @Schema(description = "多重排序，内容：key:字段,value:desc或asc", hidden = true)
    private Map<String, String> sortMap = new LinkedHashMap<>();

    @Schema(description = "排序标识")
    private Integer sortFlag;

    /**
     * 获取分页参数对象与排序条件
     *
     * @return
     */
    @Schema(description = "分页请求", hidden = true)
    public PageRequest getPageRequest() {
        if (pageNum < 0){
            pageNum = 0;
        }
        if (pageSize <= 0){
            pageSize = 10;
        }
        //无排序
        Sort sort = getSort();
        if (Objects.nonNull(sort)) {
            return PageRequest.of(pageNum, pageSize, sort);
        } else {
            return PageRequest.of(pageNum, pageSize,Sort.unsorted());
        }
    }

    @Schema(description = "分页排序", hidden = true)
    public Sort getSort() {
        // 单个排序
        if (StringUtils.isNotBlank(sortColumn)) {
            // 判断规则 DESC ASC
            Sort.Direction direction = SortType.ASC.toValue().equalsIgnoreCase(sortRole) ? Sort.Direction.ASC : Sort
                    .Direction.DESC;
            return Sort.by(direction, sortColumn);
        }

        //多重排序
        if (MapUtils.isNotEmpty(sortMap)) {
            List<Sort.Order> orders =
                    sortMap.keySet().stream().filter(StringUtils::isNotBlank)
                            .map(column -> new Sort.Order(SortType.ASC.toValue().equalsIgnoreCase(sortMap.get(column)
                            ) ? Sort.Direction.ASC : Sort.Direction.DESC, column))
                            .collect(Collectors.toList());
            return Sort.by(orders);
        }
        return Sort.unsorted();
    }

    /**
     * 获取分页参数对象
     *
     * @return
     */
    @Schema(description = "分页参数对象", hidden = true)
    public PageRequest getPageable() {
        if (pageNum < 0){
            pageNum = 0;
        }
        if (pageSize <= 0){
            pageSize = 10;
        }
        return PageRequest.of(pageNum, pageSize);
    }

    /**
     * 填序排序
     *
     * @param column
     * @param sort
     */
    @Schema(description = "填序排序", hidden = true)
    public void putSort(String column, String sort) {
        sortMap.put(column, sort);
    }

    public Integer getPageNum() {
        if (pageNum < 0){
            pageNum = 0;
        }
        return pageNum;
    }

    public Integer getPageSize() {
        if (pageSize <= 0){
            pageSize = 10;
        }
        return pageSize;
    }
}
