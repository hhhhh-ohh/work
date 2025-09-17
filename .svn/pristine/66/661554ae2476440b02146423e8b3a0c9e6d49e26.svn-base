package com.wanmi.sbc.goods.api.response.info;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>供应商商品库存同步接口出参</p>
 * Created by of628-wenzhi on 2020-09-09-9:28 下午.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProviderGoodsInfoResponse extends BasicResponse {

    private static final long serialVersionUID = -3859624361297914095L;

    /**
     * 商品SKU信息
     */
    @Schema(description = "商品SKU信息")
    private List<GoodsInfoVO> goodsInfos = new ArrayList<>();

    /**
     * 供应商商品SKU信息
     */
    @Schema(description = "供应商商品SKU信息")
    private List<GoodsInfoVO> providerGoodsInfos = new ArrayList<>();
}
