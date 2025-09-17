package com.wanmi.sbc.marketing.api.provider.fullreturn;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnDetailListByMarketingIdAndLevelIdRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnDetailListByMarketingIdsAndLevelIdsRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnLevelListByMarketingIdAndCustomerRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnLevelListByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.response.fullreturn.FullReturnDetailListByMarketingIdAndLevelIdResponse;
import com.wanmi.sbc.marketing.api.response.fullreturn.FullReturnDetailListByMarketingIdsAndLevelIdsResponse;
import com.wanmi.sbc.marketing.api.response.fullreturn.FullReturnLevelListByMarketingIdAndCustomerResponse;
import com.wanmi.sbc.marketing.api.response.fullreturn.FullReturnLevelListByMarketingIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Author: xufeng
 * @Description: 满返优惠服务查询操作接口
 * @Date: 2022-04-11 15:56
 */
@FeignClient(value = "${application.marketing.name}", contextId = "FullReturnQueryProvider")
public interface FullReturnQueryProvider {

    /**
     * 根据marketingid获取满返等级列表
     * @param request 营销id {@link FullReturnLevelListByMarketingIdRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/full-return/list-by-marketing-id")
    BaseResponse<FullReturnLevelListByMarketingIdResponse> listLevelByMarketingId(@RequestBody @Valid FullReturnLevelListByMarketingIdRequest request);

    /**
     * 根据营销获取用户满返等级列表
     * @param request 营销id与客户信息 {@link FullReturnLevelListByMarketingIdAndCustomerRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/full-return/list-return-by-marketing-id-and-customer")
    BaseResponse<FullReturnLevelListByMarketingIdAndCustomerResponse> listReturnByMarketingIdAndCustomer(@RequestBody @Valid FullReturnLevelListByMarketingIdAndCustomerRequest request);

    /**
     * 根据营销和等级获取满返详情信息列表
     * @param request 营销id与等级id {@link FullReturnDetailListByMarketingIdAndLevelIdRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/full-return/list-detail-by-marketing-id-and-level-id")
    BaseResponse<FullReturnDetailListByMarketingIdAndLevelIdResponse> listDetailByMarketingIdAndLevelId(@RequestBody @Valid FullReturnDetailListByMarketingIdAndLevelIdRequest request);

    /**
     * 根据营销和等级获取满返详情信息列表
     *
     * @param request 营销id与等级id {@link FullReturnDetailListByMarketingIdAndLevelIdRequest}
     * @return
     */
    @PostMapping(
            "/marketing/${application.marketing.version}/full-return/list-detail-by-marketing-ids-and-level-ids")
    BaseResponse<FullReturnDetailListByMarketingIdsAndLevelIdsResponse>
            listDetailByMarketingIdsAndLevelIds(
                    @RequestBody @Valid FullReturnDetailListByMarketingIdsAndLevelIdsRequest request);
}
