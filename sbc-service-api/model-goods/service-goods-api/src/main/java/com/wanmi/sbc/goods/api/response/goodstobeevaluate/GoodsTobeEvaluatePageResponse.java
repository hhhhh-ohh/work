package com.wanmi.sbc.goods.api.response.goodstobeevaluate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsTobeEvaluateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>订单商品待评价分页结果</p>
 * @author lzw
 * @date 2019-03-20 14:47:38
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTobeEvaluatePageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 订单商品待评价分页结果
     */
    @Schema(description = "订单商品待评价分页结果")
    private MicroServicePage<GoodsTobeEvaluateVO> goodsTobeEvaluateVOPage;
}
