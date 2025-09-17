package com.wanmi.sbc.elastic.goods.model.root;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.persistence.Id;
import java.io.Serializable;

/**
 * 商品品牌实体类
 * Created by dyt on 2017/4/11.
 */
@Data
@Schema
public class GoodsBrandNest implements Serializable {

    /**
     * 品牌编号
     */
    @Id
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
}
