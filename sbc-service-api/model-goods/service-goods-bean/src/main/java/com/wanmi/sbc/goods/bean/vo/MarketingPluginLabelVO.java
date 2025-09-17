package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author zhanggaolei
 * @className MarketingPluginLabelVO
 * @description TODO
 * @date 2021/6/26 10:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingPluginLabelVO  extends MarketingPluginSimpleLabelVO{

    /**
     * 关联的id，如couponInfoId
     */
    private String linkId;


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
     * 预热开始时间
     */
    @Schema(description = "预热开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime preStartTime;

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

    /**
     * 拼团人数
     */
    @Schema(description = "拼团人数")
    private Integer grouponNum;

    /**
     * 新人券是否可领取
     */
    @Schema(description = "新人券是否可领取")
    private Boolean canfetchFlag;


}
