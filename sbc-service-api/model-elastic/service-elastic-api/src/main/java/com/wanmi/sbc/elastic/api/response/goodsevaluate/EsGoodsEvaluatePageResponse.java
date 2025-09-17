package com.wanmi.sbc.elastic.api.response.goodsevaluate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsEvaluateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>商品评价分页结果</p>
 * @author liutao
 * @date 2019-02-25 15:17:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsGoodsEvaluatePageResponse extends BasicResponse {

    /**
     * 商品评价分页结果
     */
    private MicroServicePage<GoodsEvaluateVO> goodsEvaluateVOPage;
}