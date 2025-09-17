package com.wanmi.ares.request.paymember;

import com.wanmi.ares.base.SortType;
import com.wanmi.ares.enums.QueryDateCycle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuyunpeng
 * @className PayMemberGrowthResponse
 * @description
 * @date 2022/5/26 3:55 PM
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayMemberGrowthRequest implements Serializable {
    private static final long serialVersionUID = -5780497147973538210L;

    /**
     * 查询周期
     */
    @Schema(description = "查询周期")
    private QueryDateCycle dateCycle;

    /**
     * 升降序
     * @see com.wanmi.ares.base.SortType
     */
    @Schema(description = "升降序 0、生序 1、降序")
    private SortType sortType;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private String sortField;

    /**
     * 当前页码
     */
    @Schema(description = "当前页码")
    private int pageNum;

    /**
     * 每页数据量
     */
    @Schema(description = "每页数据量")
    private int pageSize;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 指定月
     */
    @Schema(description = "指定月 yyyy-mm")
    private String month;

    /**
     * 升降序text
     */
    private String sortTypeText;
}
