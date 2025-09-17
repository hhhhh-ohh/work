package com.wanmi.sbc.empower.api.provider.customerservice;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingDelByIdRequest;
import com.wanmi.sbc.empower.api.request.customerservice.CustomerServiceSettingModifyRequest;
import com.wanmi.sbc.empower.api.response.customerservice.CustomerServiceSettingModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * 在线客服配置保存服务Provider
 *
 * @author 韩伟
 * @date 2021-04-08 15:35:16
 */
@FeignClient(value = "${application.empower.name}", contextId = "CustomerServiceSettingProvider")
public interface CustomerServiceSettingProvider {

    /**
     * 修改在线客服配置API
     *
     * @author 韩伟
     * @param customerServiceSettingModifyRequest 在线客服配置修改参数结构 {@link
     *     CustomerServiceSettingModifyRequest}
     * @return 修改的在线客服配置信息 {@link CustomerServiceSettingModifyResponse}
     */
    @PostMapping("/empower/${application.empower.version}/customerservicesetting/modify")
    BaseResponse<CustomerServiceSettingModifyResponse> modify(
            @RequestBody @Valid
                    CustomerServiceSettingModifyRequest customerServiceSettingModifyRequest);

    /**
     * 修改在线网易七鱼客服配置API
     *
     * @author 韩伟
     * @param customerServiceSettingModifyRequest 在线网易七鱼客服配置修改参数结构 {@link
     *     CustomerServiceSettingModifyRequest}
     * @return 修改的在线客服配置信息 {@link CustomerServiceSettingModifyResponse}
     */
    @PostMapping("/empower/${application.empower.version}/customerservicesetting/qiYumodify")
    BaseResponse<CustomerServiceSettingModifyResponse> qiYuModify(
            @RequestBody @Valid
                    CustomerServiceSettingModifyRequest customerServiceSettingModifyRequest);

    /**
     * 修改在线客服配置API
     *
     * @author 韩伟
     * @param customerServiceSettingModifyRequest 在线客服配置修改参数结构 {@link
     *     CustomerServiceSettingModifyRequest}
     * @return 修改的在线客服配置信息 {@link CustomerServiceSettingModifyResponse}
     */
    @PostMapping("/empower/${application.empower.version}/customerservicesetting/weChatModify")
    BaseResponse<CustomerServiceSettingModifyResponse> weChatModify(
            @RequestBody @Valid
                    CustomerServiceSettingModifyRequest customerServiceSettingModifyRequest);

    /**
     * 单个删除在线客服配置API
     *
     * @author 韩伟
     * @param customerServiceSettingDelByIdRequest 单个删除参数结构 {@link
     *     CustomerServiceSettingDelByIdRequest}
     * @return 删除结果 {@link BaseResponse}
     */
    @PostMapping("/empower/${application.empower.version}/customerservicesetting/delete-by-id")
    BaseResponse deleteById(
            @RequestBody @Valid
                    CustomerServiceSettingDelByIdRequest customerServiceSettingDelByIdRequest);
}
