package com.wanmi.sbc.setting.api.provider.thirdexpresscompany;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.thirdexpresscompany.ThirdExpressCompanyBatchAddRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @description 第三方平台物流公司保存服务
 * @author malianfeng
 * @date 2022/4/26 17:44
 */
@FeignClient(value = "${application.setting.name}", contextId = "ThirdExpressCompanyProvider")
public interface ThirdExpressCompanyProvider {

    /**
     * 直接批量添加物流公司
     * @param request 批量添加请求
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/thirdexpresscompany/batchAdd")
    BaseResponse batchAdd(@Valid @RequestBody ThirdExpressCompanyBatchAddRequest request);
}
