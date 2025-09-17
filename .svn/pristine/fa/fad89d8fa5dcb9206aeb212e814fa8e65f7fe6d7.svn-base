package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.IsImage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * GoodsImageDTO
 *
 * @author lipeng
 * @dateTime 2018/11/8 下午1:57
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsImageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片编号
     */
    @Schema(description = "图片编号")
    private Long imageId;

    /**
     * 商品编号
     */
    @Schema(description = "商品编号")
    private String goodsId;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    private String goodsInfoId;

    /**
     * 原图路径
     */
    @IsImage
    @Schema(description = "原图路径")
    private String artworkUrl;

    /**
     * 中图路径
     */
    @IsImage
    @Schema(description = "中图路径")
    private String middleUrl;

    /**
     * 小图路径
     */
    @IsImage
    @Schema(description = "小图路径")
    private String thumbUrl;

    /**
     * 大图路径
     */
    @IsImage
    @Schema(description = "大图路径")
    private String bigUrl;

    /**
     * 图片排序字段
     */
    @Schema(description = "图片排序字段")
    private Integer sort;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记 0: 否, 1: 是")
    private DeleteFlag delFlag;
}
