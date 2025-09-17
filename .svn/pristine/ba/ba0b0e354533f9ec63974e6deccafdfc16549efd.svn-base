package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ClassName LifeCycleGroupStatistics
 * @Description TODO
 * @Author zhanggaolei
 * @Date 2021/2/25 11:21
 * @Version 1.0
 **/
@Data
@Schema
public class SimpleLifeCycleGroupStatisticsVO extends BasicResponse {

    /**
     * 统计日期
     */
    @Schema(description = "统计时间")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
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

    @Schema(description = "分群所占比 当前分群人数/分群总人数")
    private BigDecimal ratio;

}
