package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsSpecDetailVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsInfoConsignIdRequest extends BaseRequest {
    private static final long serialVersionUID = 712116094839998228L;

    /**
     * SKU编号
     */
    @NotEmpty
    @Schema(description = "SKU编号")
    private List<String> goodsInfoIds;

    /**
     * 是否删除 0 否  1 是
     */
    @NotNull
    @Schema(description = "是否删除")
    private DeleteFlag delFlag;

    /**
     * 商品信息
     */
    @NotNull
    @Schema(description = "商品信息")
    private GoodsVO goods;


    /**
     * 商品SKU列表
     */
    @Schema(description = "商品SKU")
    private GoodsInfoVO goodsInfos;

    /**
     * 商品规格值列表
     */
    @Schema(description = "商品规格值列表")
    private List<GoodsSpecDetailVO> goodsSpecDetails;

    /**
     * 公司信息编号
     */
    private Long companyInfoId;

    /**
     * 商户类型
     */
    private BoolFlag companyType;

    /**
     * 店铺编号
     */
    private Long storeId;

    /**
     * 商家名称
     */
    private String supplierName;

    /**
     * 商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家
     */
    @Schema(description = "商家类型0品牌商城，1商家,2:O2O商家，3：跨境商家")
    private StoreType storeType;


    @Schema(description = "在商家端编辑供应商商品页面且智能设价下，标志加价比例是否独立设置")
    private EnableStatus isIndependent;
}
