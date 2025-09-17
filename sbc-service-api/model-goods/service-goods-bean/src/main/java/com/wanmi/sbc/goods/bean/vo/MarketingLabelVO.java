package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 营销标签
 * Created by dyt on 2018/2/28.
 */
@Schema
@Data
public class MarketingLabelVO extends BasicResponse {

    private static final long serialVersionUID = -3676178394754455968L;

    /**
     * 营销编号
     */
    @Schema(description = "营销编号")
    private Long marketingId;

    /**
     * 促销类型 0：满减 1:满折 2:满赠
     * 与Marketing.marketingType保持一致
     */
    @Schema(description = "促销类型", contentSchema = com.wanmi.sbc.goods.bean.enums.MarketingType.class)
    private Integer marketingType;

    /**
     * 参加会员 0:全部等级 -1:全部用户 other:其他等级
     */
    private String joinLevel;

    /**
     * 促销描述
     */
    @Schema(description = "促销描述")
    private String marketingDesc;

    /**
     * 活动状态
     */
    @Schema(description = "活动状态", contentMediaType = "com.wanmi.sbc.marketing.bean.enums.MarketingStatus.class")
    private Integer marketingStatus;

    /**
     * 进度比例
     */
    @Schema(description = "进度比例，以%为单位")
    private BigDecimal progressRatio;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

}
