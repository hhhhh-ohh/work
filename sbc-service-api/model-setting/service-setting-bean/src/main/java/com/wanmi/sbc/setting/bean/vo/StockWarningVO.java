package com.wanmi.sbc.setting.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商家库存预警VO
 */
@Schema
@Data
public class StockWarningVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long id;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long storeId;

    /**
     * 商品skuId
     */
    @Schema(description = "商品skuId")
    private String skuId;

    /**
     * 是否已预警 0：已预警 1：未预警
     */
    @Schema(description = "是否已预警 0：已预警 1：未预警")
    private BoolFlag isWarning;

    /**
     * 是否删除 0：未删除 1：已删除
     */
    @Schema(description = "是否删除 0：未删除 1：已删除")
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

}
