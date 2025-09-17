package com.wanmi.sbc.setting.api.provider;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.response.PlatformPayCompanyListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @description 插件可用支付方式服务
 * @author malianfeng
 * @date 2021/8/3 19:42
 */
@FeignClient(value = "${application.setting.name}", contextId = "PlatformPayChannelProvider")
public interface PlatformPayCompanyProvider {

    @PostMapping("/setting/${application.setting.version}/platform/pay-company/list")
    BaseResponse<PlatformPayCompanyListResponse> payCompanyList();
}
