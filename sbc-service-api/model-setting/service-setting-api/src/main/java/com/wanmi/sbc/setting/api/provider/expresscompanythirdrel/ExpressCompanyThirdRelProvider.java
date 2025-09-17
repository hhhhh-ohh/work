package com.wanmi.sbc.setting.api.provider.expresscompanythirdrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.ExpressCompanyThirdRelBatchSaveRequest;
import com.wanmi.sbc.setting.api.request.expresscompanythirdrel.ExpressCompanyThirdRelMappingRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description 平台和第三方代销平台物流公司映射保存服务
 * @author malianfeng
 * @date 2022/4/26 17:44
 */
@FeignClient(value = "${application.setting.name}", contextId = "ExpressCompanyThirdRelProvider")
public interface ExpressCompanyThirdRelProvider {

    /**
     * 平台与第三方平台物流公司映射关系批量保存
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/expresscompanythirdrel/batch-save")
    BaseResponse batchSave(@Valid @RequestBody ExpressCompanyThirdRelBatchSaveRequest request);

    /**
     * 平台与第三方平台物流公司映射关系批量保存
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/expresscompanythirdrel/mapping")
    BaseResponse mapping(@Valid @RequestBody ExpressCompanyThirdRelMappingRequest request);
}
