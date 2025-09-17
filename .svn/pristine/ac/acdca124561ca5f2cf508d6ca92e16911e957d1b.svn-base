package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.GrouponOrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单状态
 * Created by jinwei on 14/3/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class GrouponInstanceVO extends BasicResponse {
    private static final long serialVersionUID = 1L;


    /**
     * 团号
     */
    @Schema(description = "团号")
    private String grouponNo;

    /**
     * 拼团活动id
     */
    @Schema(description = "拼团活动id")
    private String grouponActivityId;

    /**
     * 开团时间
     */
    @Schema(description = "开团时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     *团截止时间
     */
    @Schema(description = "团截止时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 成团时间
     */
    @Schema(description = "成团时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime completeTime;

    /**
     * 拼团人数
     */
    @Schema(description = "拼团人数")
    private Integer grouponNum;

    /**
     * 参团人数
     */
    @Schema(description = "参团人数")
    private Integer joinNum;

    /**
     * 团长用户id
     */
    @Schema(description = "团长用户id")
    private String customerId;

    /**
     * 拼团状态
     */
    @Schema(description = "拼团状态")
    private GrouponOrderStatus grouponStatus;

}
