package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ClassName LifeCycleGroupStatistics
 * @description
 * @Author zhanggaolei
 * @Date 2021/2/25 11:21
 * @Version 1.0
 **/
@Data
@Schema
public class LifeCycleGroupStatisticsVO extends BasicResponse {

    /**
     * 统计日期
     */
    @Schema(description = "统计时间")
    private LocalDate statDate;

    /**
     * 分群id
     */
    @Schema(description = "分群id")
    private Long groupId;

    /**
     * 分群名称
     */
    @Schema(description = "分群名称")
    private String groupName;

    /**
     * 会员人数
     */
    @Schema(description = "会员人数")
    private Long customerCount;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 会员占比，分群人数/会员总人数*100
     */
    @Schema(description = "会员占比，分群人数/会员总人数*100")
    private BigDecimal ratio;

    /**
     * 流失人数
     */
    @Schema(description = "流失人数")
    private Long outflowCount;

    /**
     * 回归人数
     */
    @Schema(description = "回归人数")
    private long inflowCount;
}
