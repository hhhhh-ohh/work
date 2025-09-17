package com.wanmi.sbc.setting.api.provider.pagemanage;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.pagemanage.PageInfoExtendByCouponInfoRequest;
import com.wanmi.sbc.setting.api.request.pagemanage.PageInfoExtendByIdRequest;
import com.wanmi.sbc.setting.api.response.pagemanage.PageInfoExtendByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>页面投放查询接口</p>
 * @author dyt
 * @date 2020-04-16
 */
@FeignClient(value = "${application.setting.name}", contextId = "PageInfoExtendQueryProvider")
public interface PageInfoExtendQueryProvider {

    /**
     * 查询预置搜索词
     */
    @PostMapping("/setting/${application.setting.version}/page-info-extend/find-by-id")
    BaseResponse<PageInfoExtendByIdResponse> findById(@RequestBody PageInfoExtendByIdRequest request);

    /**
     * 根据券ID和活动ID查询推广详情
     */
    @PostMapping("/setting/${application.setting.version}/page-info-extend/find-by-coupon-info")
    BaseResponse<PageInfoExtendByIdResponse> findByCouponInfo(@RequestBody @Valid PageInfoExtendByCouponInfoRequest request);

    /**
     * 营销活动推广
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/marketing-page-info-extend/find-by-id")
    BaseResponse<PageInfoExtendByIdResponse> findExtendById(@RequestBody PageInfoExtendByIdRequest request);
}
