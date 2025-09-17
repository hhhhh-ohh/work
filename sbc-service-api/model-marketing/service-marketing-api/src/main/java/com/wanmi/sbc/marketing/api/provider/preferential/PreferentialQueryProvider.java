package com.wanmi.sbc.marketing.api.provider.preferential;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.market.MarketingIdRequest;
import com.wanmi.sbc.marketing.api.request.preferential.DetailByMIdsAndLIdsRequest;
import com.wanmi.sbc.marketing.api.request.preferential.LeveByMIdsRequest;
import com.wanmi.sbc.marketing.api.response.preferential.DetailByMIdsAndLIdsResponse;
import com.wanmi.sbc.marketing.api.response.preferential.LeveByMIdsResponse;
import com.wanmi.sbc.marketing.api.response.preferential.PreferentialDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description 加价购读操作
 * @author edz
 * @date 2022/11/18 16:16
 */
@FeignClient(value = "${application.marketing.name}", contextId = "PreferentialQueryProvider")
public interface PreferentialQueryProvider {

    /**
     * @description 加价购活动详情
     * @author  edz
     * @date: 2022/11/18 16:20
     * @param marketingIdRequest
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.preferential.PreferentialDetailResponse>
     */
    @PostMapping("/marketing/${application.marketing.version}/marketing/preferential/detail")
    BaseResponse<PreferentialDetailResponse> getPreferentialDetail(@RequestBody @Valid MarketingIdRequest marketingIdRequest);

    /**
     * @description 活动ID和等级ID查询关联商品信息
     * @author  bob
     * @date: 2022/12/4 01:10
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.preferential.DetailByMIdsAndLIdsResponse>
     */
    @PostMapping("/marketing/${application.marketing.version}/marketing/preferential/detail-MIds-LIds")
    BaseResponse<DetailByMIdsAndLIdsResponse> listDetailByMarketingIdsAndLevelIds(@RequestBody @Valid DetailByMIdsAndLIdsRequest request);

    /**
     * @description 活动ID查询关联商品信息
     * @author  bob
     * @date: 2022/12/4 01:10
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.preferential.DetailByMIdsAndLIdsResponse>
     */
    @PostMapping("/marketing/${application.marketing.version}/marketing/preferential/detail-MIds")
    BaseResponse<DetailByMIdsAndLIdsResponse> listDetailByMarketingIds(@RequestBody @Valid DetailByMIdsAndLIdsRequest request);

    /**
     * @description  根据活动Id查询活动级别
     * @author  wur
     * @date: 2023/1/6 16:44
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/marketing/preferential/leve-MIds")
    BaseResponse<LeveByMIdsResponse> listLeveByMarketingIds(@RequestBody @Valid LeveByMIdsRequest request);
}
