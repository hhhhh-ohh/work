package com.wanmi.sbc.goods.bean.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品品牌DTO
 * Created by dyt on 2017/4/11.
 */
@Schema
@Data
public class GoodsBrandDTO implements Serializable {

    private static final long serialVersionUID = 2554135381839907544L;

    /**
     * 品牌编号
     */
    @Schema(description = "品牌编号")
    private Long brandId;

    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String brandName;

    /**
     * 拼音
     */
    @Schema(description = "拼音")
    private String pinYin;

    /**
     * 简拼
     */
    @Schema(description = "简拼")
    private String sPinYin;

    /**
     * 店铺id(平台默认为0)
     */
    @Schema(description = "店铺id(平台默认为0)")
    private Long storeId = 0L;

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
     * 删除标志
     */
    @Schema(description = "删除标志，0: 否, 1: 是")
    private DeleteFlag delFlag;

    /**
     * 品牌别名
     */
    @Schema(description = "品牌别名")
    private String nickName;

    /**
     * 品牌logo
     */
    @Schema(description = "品牌logo")
    private String logo;

    /**
     * 是否推荐品牌
     */
    @Schema(description = "是否推荐品牌")
    private DefaultFlag recommendFlag;

    /**
     * 品牌排序
     */
    @Schema(description = "品牌排序")
    private Long brandSort;
}
