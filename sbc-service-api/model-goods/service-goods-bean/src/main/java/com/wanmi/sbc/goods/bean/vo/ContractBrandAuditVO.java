package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 二次签约品牌VO
 * @author wangchao
 */
@Schema
@Data
public class ContractBrandAuditVO extends BasicResponse {

    private static final long serialVersionUID = 7401744552521450837L;

    /**
     * 签约品牌分类
     */
    @Schema(description = "签约品牌分类")
    private Long contractBrandId;

    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    private Long storeId;

    /**
     * 商品品牌
     */
    @Schema(description = "商品品牌")
    private GoodsBrandVO goodsBrand;

    /**
     * 待审核品牌
     */
    @Schema(description = "待审核品牌")
    private CheckBrandVO checkBrand;

    /**
     * 授权图片路径
     */
    @Schema(description = "授权图片路径")
    private String authorizePic;

    /**
     * 品牌id
     */
    @Schema(description = "品牌id")
    private Long brandId;

    /**
     * 是否需要删除 标记
     */
    @Schema(description = "是否需要删除")
    private DeleteFlag deleteFlag;
}
