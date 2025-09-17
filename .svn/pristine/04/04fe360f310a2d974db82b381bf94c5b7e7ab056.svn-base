package com.wanmi.sbc.setting.api.provider.pickupemployeerela;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.pickupemployeerela.PickupSettingRelaDelByEmployeesRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author xufeng
 * @date 2021-09-26 11:01:10
 */
@FeignClient(value = "${application.setting.name}", contextId = "PickupSettingRelaProvider")
public interface PickupSettingRelaProvider {

    /**
     * @param pickupSettingRelaDelByEmployeesRequest
     * @author xufeng
     */
    @PostMapping("/setting/${application.setting.version}/pickupsettingrela/delete-by-employees")
    BaseResponse deleteByEmployeeIds(@RequestBody @Valid PickupSettingRelaDelByEmployeesRequest pickupSettingRelaDelByEmployeesRequest);

}