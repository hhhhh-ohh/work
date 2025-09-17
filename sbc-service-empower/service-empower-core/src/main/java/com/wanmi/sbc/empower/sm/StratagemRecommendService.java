package com.wanmi.sbc.empower.sm;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.sm.recommend.RecommendGoodsRequest;
import com.wanmi.sbc.empower.api.response.sm.recommend.RecommendGoodsResponse;

/**
*
 * @description    订单相关处理
 * @author  wur
 * @date: 2022/4/19 10:19
 **/
public interface StratagemRecommendService extends StratagemBaseService {

    /**
     * @description
     * @author  wur
     * @date: 2022/11/17 10:47
     * @param request
     * @return
     **/
    BaseResponse<RecommendGoodsResponse> queryGoods(RecommendGoodsRequest request);

}
