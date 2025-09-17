package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.goods.bean.dto.GoodsCustomerPriceDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsIntervalPriceDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsLevelPriceDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 商品设价编辑请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsInfoPriceModifyRequest extends BaseRequest {

    private static final long serialVersionUID = 1155286274397367391L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    @NotNull
    private GoodsInfoDTO goodsInfo;

    /**
     * 商品等级价格列表
     */
    @Schema(description = "商品等级价格列表")
    private List<GoodsLevelPriceDTO> goodsLevelPrices;

    /**
     * 商品客户价格列表
     */
    @Schema(description = "商品客户价格列表")
    private List<GoodsCustomerPriceDTO> goodsCustomerPrices;

    /**
     * 商品订货区间价格列表
     */
    @Schema(description = "商品订货区间价格列表")
    private List<GoodsIntervalPriceDTO> goodsIntervalPrices;

    /**
     * sku维度设价
     */
    BoolFlag skuEditPrice = BoolFlag.NO;
}
