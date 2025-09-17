package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
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
 * 商品库图片VO
 * Created by dyt on 2017/4/11.
 */
@Schema
@Data
public class StandardImageVO extends BasicResponse {

    private static final long serialVersionUID = 4807628172761770413L;

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
    @Schema(description = "原图路径")
    private String artworkUrl;

    /**
     * 中图路径
     */
    @Schema(description = "中图路径")
    private String middleUrl;

    /**
     * 小图路径
     */
    @Schema(description = "小图路径")
    private String thumbUrl;

    /**
     * 大图路径
     */
    @Schema(description = "大图路径")
    private String bigUrl;

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
