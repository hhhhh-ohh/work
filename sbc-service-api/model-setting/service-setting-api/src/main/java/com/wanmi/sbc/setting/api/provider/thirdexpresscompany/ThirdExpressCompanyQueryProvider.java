package com.wanmi.sbc.setting.api.provider.thirdexpresscompany;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.thirdexpresscompany.ThirdExpressCompanyQueryRequest;
import com.wanmi.sbc.setting.api.response.thirdexpresscompany.ThirdExpressCompanyListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description 第三方平台物流公司查询服务
 * @author malianfeng
 * @date 2022/4/26 17:44
 */
@FeignClient(value = "${application.setting.name}", contextId = "ThirdExpressCompanyQueryProvider")
public interface ThirdExpressCompanyQueryProvider {

    /**
     * 查询第三方平台物流公司列表
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/thirdexpresscompany/list")
    BaseResponse<ThirdExpressCompanyListResponse> list(@RequestBody ThirdExpressCompanyQueryRequest request);

}
