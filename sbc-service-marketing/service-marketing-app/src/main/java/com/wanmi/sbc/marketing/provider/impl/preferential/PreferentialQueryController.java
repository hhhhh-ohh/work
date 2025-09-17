package com.wanmi.sbc.marketing.provider.impl.preferential;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.preferential.PreferentialQueryProvider;
import com.wanmi.sbc.marketing.api.request.market.MarketingIdRequest;
import com.wanmi.sbc.marketing.api.request.preferential.DetailByMIdsAndLIdsRequest;
import com.wanmi.sbc.marketing.api.request.preferential.LeveByMIdsRequest;
import com.wanmi.sbc.marketing.api.response.preferential.DetailByMIdsAndLIdsResponse;
import com.wanmi.sbc.marketing.api.response.preferential.LeveByMIdsResponse;
import com.wanmi.sbc.marketing.api.response.preferential.PreferentialDetailResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingPreferentialGoodsDetailVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingPreferentialLevelVO;
import com.wanmi.sbc.marketing.preferential.model.root.MarketingPreferentialDetail;
import com.wanmi.sbc.marketing.preferential.service.MarketingPreferentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @author edz
 * @className PreferentialQueryController
 * @description 加价购读操作
 * @date 2022/11/17 17:23
 **/
@Validated
@RestController
public class PreferentialQueryController implements PreferentialQueryProvider {

    @Autowired
    private MarketingPreferentialService marketingPreferentialService;

    /**
     * @description 加价购活动详情
     * @author  edz
     * @date: 2022/11/18 16:20
     * @param marketingIdRequest
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.preferential.PreferentialDetailResponse>
     */
    @Override
    public BaseResponse<PreferentialDetailResponse> getPreferentialDetail(
            @RequestBody @Valid MarketingIdRequest marketingIdRequest){
        return BaseResponse.success(marketingPreferentialService.getPreferentialDetail(marketingIdRequest));
    }

    /**
     * @description 活动ID和等级ID查询关联商品信息
     * @author  bob
     * @date: 2022/12/4 01:10
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.preferential.DetailByMIdsAndLIdsResponse>
     */
    @Override
    public BaseResponse<DetailByMIdsAndLIdsResponse> listDetailByMarketingIdsAndLevelIds(@RequestBody DetailByMIdsAndLIdsRequest request) {
        List<MarketingPreferentialDetail> details =
                marketingPreferentialService.listDetailByMarketingIdsAndLevelIds(request.getMarketingIds(),
                request.getLevelIds());
        List<MarketingPreferentialGoodsDetailVO> vos = KsBeanUtil.convert(details, MarketingPreferentialGoodsDetailVO.class);
        return BaseResponse.success(DetailByMIdsAndLIdsResponse.builder().preferentialGoodsDetailVOS(vos).build());
    }

    /**
     * @description 活动ID和等级ID查询关联商品信息
     * @author  bob
     * @date: 2022/12/4 01:10
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.preferential.DetailByMIdsAndLIdsResponse>
     */
    @Override
    public BaseResponse<DetailByMIdsAndLIdsResponse> listDetailByMarketingIds(@RequestBody DetailByMIdsAndLIdsRequest request) {
        List<MarketingPreferentialDetail> details =
                marketingPreferentialService.listDetailByMarketingIds(request.getMarketingIds());
        List<MarketingPreferentialGoodsDetailVO> vos = KsBeanUtil.convert(details, MarketingPreferentialGoodsDetailVO.class);
        return BaseResponse.success(DetailByMIdsAndLIdsResponse.builder().preferentialGoodsDetailVOS(vos).build());
    }

    @Override
    public BaseResponse<LeveByMIdsResponse> listLeveByMarketingIds(@Valid LeveByMIdsRequest request) {
        List<MarketingPreferentialLevelVO> levelVOList = marketingPreferentialService.listLevelByLevelId(request.getMarketingIds());
        return BaseResponse.success(LeveByMIdsResponse.builder().levelVOList(levelVOList).build());
    }
}
