package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 签约品牌VO
 * Created by sunkun on 2017/10/31.
 */
@Schema
@Data
public class ContractBrandVO extends BasicResponse {

    private static final long serialVersionUID = -5340314199677396723L;

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
}
