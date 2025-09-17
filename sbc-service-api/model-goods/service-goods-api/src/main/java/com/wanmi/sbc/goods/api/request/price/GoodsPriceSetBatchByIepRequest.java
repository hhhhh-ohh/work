package com.wanmi.sbc.goods.api.request.price;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>企业购商品设价参数</p>
 * Created by of628-wenzhi on 2020-03-04-下午507.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Valid
public class GoodsPriceSetBatchByIepRequest extends BaseRequest {

    private static final long serialVersionUID = -5129253233451375377L;

    @Schema(description = "全量商品SKU信息")
    @NotEmpty
    private List<GoodsInfoVO> goodsInfos;

    @Schema(description = "需要企业购商品设价的SKU ID列表，排除了分销商品")
    private List<String> filteredGoodsInfoIds;

    @Schema(description = "商品区间价格列表")
    @NotNull
    private List<GoodsIntervalPriceVO> goodsIntervalPrices;

    @Schema(description = "客户信息")
    @NotNull
    private CustomerVO customer;
}
