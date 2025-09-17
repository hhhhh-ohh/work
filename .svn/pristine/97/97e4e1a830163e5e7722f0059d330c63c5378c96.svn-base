package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * GoodsSpecDetailDTO
 *
 * @author lipeng
 * @dateTime 2018/11/8 下午1:59
 */
@Schema
@Data
public class GoodsSpecDetailDTO implements Serializable {

    private static final long serialVersionUID = -7853255468848216075L;

    /**
     * 规格明细ID
     */
    @Schema(description = "规格明细ID")
    private Long specDetailId;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String goodsId;

    /**
     * 规格ID
     */
    @Schema(description = "规格ID")
    private Long specId;

    /**
     * 规格值明称
     */
    @Schema(description = "规格值明称")
    private String detailName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记 0: 否, 1: 是")
    private DeleteFlag delFlag;

    /**
     * 新增时，规格摸拟ID
     */
    @Schema(description = "新增时，规格摸拟ID")
    private Long mockSpecId;

    /**
     * 新增时，规格值摸拟ID
     */
    @Schema(description = "新增时，规格值摸拟ID")
    private Long mockSpecDetailId;
}
