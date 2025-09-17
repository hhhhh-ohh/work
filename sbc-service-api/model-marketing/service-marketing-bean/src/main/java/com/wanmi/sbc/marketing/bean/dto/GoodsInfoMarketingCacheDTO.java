package com.wanmi.sbc.marketing.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhanggaolei
 * @className MarketingCacheDTO
 * @description TODO
 * @date 2021/6/24 9:54
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoodsInfoMarketingCacheDTO {

    /**
     * 营销活动id
     */
    private Object id;

    /**
     * 营销活动类型
     */
    private MarketingPluginType marketingPluginType;

    /**
     * 预热开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime preStartTime;

    /**
     * 开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;


    private List<Long> joinLevel;

    private DefaultFlag joinLevelType;

    /**
     * 商品id
     */
    private String skuId;

    private BigDecimal price;

    /**
     * 拼团人数
     */
    private Integer grouponNum;

}
