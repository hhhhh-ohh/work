package com.wanmi.sbc.crm.customerplan.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @ClassName PlanStatisticsMessagePushVo
 * @Description TODO
 * @Author lvzhenwei
 * @Date 2020/2/6 11:39
 **/
@Schema
@Data
public class PlanStatisticsMessagePushVo extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 运营计划id
     */
    @Schema(description = "运营计划id")
    private Long planId;

    /**
     * 站内信收到人数
     */
    @Schema(description = "站内信收到人数")
    private Integer messageReceiveNum;

    /**
     * 站内信收到人次
     */
    @Schema(description = "站内信收到人次")
    private Integer messageReceiveTotal;

    /**
     * 运营计划效果统计push收到次数
     */
    @Schema(description = "运营计划效果统计push收到次数")
    private Integer pushNum;

    /**
     * 统计日期
     */
    @Schema(description = "统计日期")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate statisticsDate;
}
