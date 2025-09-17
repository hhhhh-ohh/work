package com.wanmi.sbc.vas.api.request.recommend.intelligentrecommendation;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 订单商品埋点数据
 *
 * @author lvzhenwei
 * @date 2021/4/17 11:37 上午
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationOrderGoodsListRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /** 订单商品数据 */
    @Schema(description = "订单商品数据")
    private List<IntelligentRecommendationClickGoodsOrderRequest> orderGoodsLis;
}
