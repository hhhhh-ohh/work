package com.wanmi.sbc.marketing.api.provider.communitysku;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityValidateRequest;
import com.wanmi.sbc.marketing.api.request.communitysku.CommunitySkuRelQueryRequest;
import com.wanmi.sbc.marketing.api.request.communitysku.UpdateSalesRequest;
import com.wanmi.sbc.marketing.api.response.communitysku.CommunitySkuListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description 社区购商品
 * @author edz
 * @date 2023/7/24 17:30
 */
@FeignClient(value = "${application.marketing.name}", contextId = "CommunitySkuQueryProvider")
public interface CommunitySkuQueryProvider {

    /**
     * @description 社区购商品列表
     * @author  edz
     * @date: 2023/7/24 17:31
     * @param communitySkuRelQueryRequest
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.communitysku.CommunitySkuListResponse>
     */
    @PostMapping("/marketing/${application.marketing.version}/communitysku/list")
    BaseResponse<CommunitySkuListResponse> list(@RequestBody @Valid CommunitySkuRelQueryRequest communitySkuRelQueryRequest);

    /**
     * @description 社区团购库存处理
     * @author  bob
     * @date: 2023/7/28 00:26
     * @param updateSalesRequest
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.common.base.BaseResponse>
     */
    @PostMapping("/marketing/${application.marketing.version}/communitysku/updateSales")
    BaseResponse<BaseResponse> updateSales(@RequestBody @Valid UpdateSalesRequest updateSalesRequest);

    /**
     * 互斥验证
     *
     * @author dyt
     * @param request 互斥请求参数 {@link CommunityActivityValidateRequest}
     */
    @PostMapping("/marketing/${application.goods.version}/communitysku/validate")
    BaseResponse validate(@RequestBody @Valid CommunityActivityValidateRequest request);
}
