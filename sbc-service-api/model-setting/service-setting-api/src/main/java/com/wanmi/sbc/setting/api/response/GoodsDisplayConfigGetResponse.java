package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 增专资质配置
 */
@Schema
@Data
public class GoodsDisplayConfigGetResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;
    /**
     * 0:SKU 1:SPU
     */
    @Schema(description = "商品维度-0:SKU 1:SPU",contentSchema = com.wanmi.sbc.setting.bean.enums.GoodsShowType.class)
    private Integer goodsShowType;
    /**
     * 0:小图 1:大图
     */
    @Schema(description = "图片显示方式-0:小图 1:大图",contentSchema = com.wanmi.sbc.setting.bean.enums.ImageShowType.class)
    private Integer imageShowType;
}
