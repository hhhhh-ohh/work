package com.wanmi.sbc.marketing.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-19 9:38
 */
@Schema
@Data
public class SkuExistsDTO implements Serializable {


    /**
     * skuId集合，逗号分割
     */
    @Schema(description = "skuId集合，逗号分割")
    @NotEmpty
    List<String> skuIds;


    /**
     * 营销类型
     */
    @Schema(description = "营销活动类型")
    @NotNull
    MarketingType marketingType;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @NotNull
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @NotNull
    private LocalDateTime endTime;

    /**
     * 需要排除的营销Id，比如编辑时的自己
     */
    @Schema(description = "需要排除的营销Id，比如编辑时的自己")
    Long excludeId;

}
