package com.wanmi.sbc.empower.api.provider.customerservice;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingByStoreIdRequest;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingPageRequest;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingByStoreIdResponse;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingPageResponse;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingListRequest;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingListResponse;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingByIdRequest;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * 在线客服配置查询服务Provider
 *
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@FeignClient(
        value = "${application.empower.name}",
        contextId = "CustomerServiceSettingQueryProvider")
public interface CustomerServiceSettingQueryProvider {

    /**
     * 分页查询在线客服配置API
     *
     * @author 韩伟
     * @param customerServiceSettingPageReq 分页请求参数和筛选对象 {@link CustomerServiceSettingPageRequest}
     * @return 在线客服配置分页列表信息 {@link CustomerServiceSettingPageResponse}
     */
    @PostMapping("/empower/${application.empower.version}/customerservicesetting/page")
    BaseResponse<CustomerServiceSettingPageResponse> page(
            @RequestBody @Valid CustomerServiceSettingPageRequest customerServiceSettingPageReq);

    /**
     * 列表查询在线客服配置API
     *
     * @author 韩伟
     * @param customerServiceSettingListReq 列表请求参数和筛选对象 {@link CustomerServiceSettingListRequest}
     * @return 在线客服配置的列表信息 {@link CustomerServiceSettingListResponse}
     */
    @PostMapping("/empower/${application.empower.version}/customerservicesetting/list")
    BaseResponse<CustomerServiceSettingListResponse> list(
            @RequestBody @Valid CustomerServiceSettingListRequest customerServiceSettingListReq);

    /**
     * 单个查询在线客服配置API
     *
     * @author 韩伟
     * @param customerServiceSettingByIdRequest 单个查询在线客服配置请求参数 {@link
     *     CustomerServiceSettingByIdRequest}
     * @return 在线客服配置详情 {@link CustomerServiceSettingByIdResponse}
     */
    @PostMapping("/empower/${application.empower.version}/customerservicesetting/get-by-id")
    BaseResponse<CustomerServiceSettingByIdResponse> getById(
            @RequestBody @Valid
                    CustomerServiceSettingByIdRequest customerServiceSettingByIdRequest);

    /**
     * 根据店铺ID查询在线客服配置API
     *
     * @param customerServiceSettingByStoreIdRequest
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/customerservicesetting/get-by-stort-id")
    BaseResponse<CustomerServiceSettingByStoreIdResponse> getByStoreId(
            @RequestBody @Valid
                    CustomerServiceSettingByStoreIdRequest customerServiceSettingByStoreIdRequest);
}
