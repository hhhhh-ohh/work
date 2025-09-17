package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className SupplierCommissionGoodsVO
 * @description TODO
 * @date 2021/9/14 16:39
 **/
@Schema
@Data
public class SupplierCommissionGoodsVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Schema(description = "商品Id")
    private String id;

    /**
     *
     */
    @Schema(description = "商品Id")
    private String goodsId;

    /**
     *
     */
    @Schema(description = "供应商商品Id")
    private String providerGoodsId;

    /**
     *
     */
    @Schema(description = "供应商商品图片")
    private String providerGoodsImg;

    /**
     *
     */
    @Schema(description = "供应商商品编码")
    private String providerGoodsNo;

    /**
     * 供应商商品名
     */
    @Schema(description = "供应商商品名")
    private String providerGoodsName;

    /**
     * 供应商
     */
    @Schema(description = "供应商")
    private String providerName;

    /**
     * 同步状态
     */
    @Schema(description = "同步状态")
    private DefaultFlag synStatus;

    @Schema(description = "更新信息")
    private List<ProviderGoodsEditVO> providerGoodsEditVOList;


}