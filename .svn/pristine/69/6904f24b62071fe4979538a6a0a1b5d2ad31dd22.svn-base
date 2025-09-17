package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * MarketingLabelDTO
 *
 * @author lipeng
 * @dateTime 2018/11/8 下午4:38
 */
@Schema
@Data
public class MarketingLabelDTO implements Serializable {

    private static final long serialVersionUID = 8836709224778513705L;

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
     * 促销描述
     */
    @Schema(description = "促销描述")
    private String marketingDesc;
}
