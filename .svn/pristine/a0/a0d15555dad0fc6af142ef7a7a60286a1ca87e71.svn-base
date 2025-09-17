package com.wanmi.sbc.marketing.api.request.buyoutprice;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.dto.MarketingQueryBaseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Author: weiwenhao
 * @Description:
 * @Date: 2020-04-14
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class MarketingBuyoutPriceSearchRequest extends MarketingQueryBaseDTO {
    private static final long serialVersionUID = 4709873515000508764L;

    /**
     * 营销名称
     */
    @Schema(description = "营销名称")
    private String marketingName;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String shopName;

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

    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 查询平台类型
     */
    @Schema(description = "查询平台类型")
    private Platform platform;

    /**
     * 营销类型：0店铺，2门店
     */
    @Schema(description = "营销类型")
    private PluginType pluginType;

}
