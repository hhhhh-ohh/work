package com.wanmi.sbc.empower.provider.impl.sm.recommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.sm.recommend.StratagemRecommendProvider;
import com.wanmi.sbc.empower.api.request.sm.recommend.RecommendGoodsRequest;
import com.wanmi.sbc.empower.api.response.sm.recommend.RecommendGoodsResponse;
import com.wanmi.sbc.empower.bean.enums.StratagemServiceType;
import com.wanmi.sbc.empower.sm.StratagemContext;
import com.wanmi.sbc.empower.sm.StratagemRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @description   数谋 智能推荐服务
 * @author  wur
 * @date: 2022/11/17 17:30
 **/
@RestController
public class StratagemRecommendController implements StratagemRecommendProvider {

    @Autowired private StratagemContext stratagemContext;

    /**
     * @description  查询智能推荐商品
     * @author  wur
     * @date: 2022/11/17 17:33
     * @param request
     * @return
     **/
    @Override
    public BaseResponse<RecommendGoodsResponse> queryGoods(@RequestBody RecommendGoodsRequest request) {
        StratagemRecommendService recommendService =
                stratagemContext.getStratagemService(StratagemServiceType.RECOMMEND_SERVICE);
        if (Objects.isNull(recommendService)) {
            return BaseResponse.success(RecommendGoodsResponse.builder().build());
        }
        return recommendService.queryGoods(request);
    }
}
