package com.wanmi.sbc.marketing.common.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SkuExistsRequest extends BaseRequest {

    /**
     * skuId集合，逗号分割
     */
    @NotEmpty
    List<String> skuIds;

    /**
     * 营销类型
     */
    @NotNull
    MarketingType marketingType;

    /**
     * 开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @NotNull
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @NotNull
    private LocalDateTime endTime;

    /**
     * 需要排除的营销Id，比如编辑时的自己
     */
    Long excludeId;


    /**
     * 营销类型
     */
    private PluginType pluginType;
}
