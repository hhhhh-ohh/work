package com.wanmi.sbc.goods.api.response.goodsrestrictedsale;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedPurchaseVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>购物车限售配置新增结果</p>
 * @author baijz
 * @date 2020-04-08 11:20:28
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedSalePurchaseResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 购物车限售校验的返回信息
     */
    @Schema(description = "购物车限售校验的返回信息")
    private List<GoodsRestrictedPurchaseVO> goodsRestrictedPurchaseVOS;
}
