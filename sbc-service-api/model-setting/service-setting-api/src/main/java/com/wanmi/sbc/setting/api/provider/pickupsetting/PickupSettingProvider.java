package com.wanmi.sbc.setting.api.provider.pickupsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.ConfigRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingAddRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingAuditRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingDefaultAddressRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingDelByIdRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingModifyRequest;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingAddResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>pickup_setting保存服务Provider</p>
 *
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
@FeignClient(value = "${application.setting.name}", contextId = "PickupSettingProvider")
public interface PickupSettingProvider {

    /**
     * 新增pickup_settingAPI
     *
     * @param pickupSettingAddRequest pickup_setting新增参数结构 {@link PickupSettingAddRequest}
     * @return 新增的pickup_setting信息 {@link PickupSettingAddResponse}
     * @author 黄昭
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/add")
    BaseResponse add(@RequestBody @Valid PickupSettingAddRequest pickupSettingAddRequest);

    /**
     * 修改pickup_settingAPI
     *
     * @param pickupSettingModifyRequest pickup_setting修改参数结构 {@link PickupSettingModifyRequest}
     * @return 修改的pickup_setting信息 {@link BaseResponse}
     * @author 黄昭
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/modify")
    BaseResponse modify(@RequestBody @Valid PickupSettingModifyRequest pickupSettingModifyRequest);

    /**
     * 单个删除pickup_settingAPI
     *
     * @param pickupSettingDelByIdRequest 单个删除参数结构 {@link PickupSettingDelByIdRequest}
     * @return 删除结果 {@link BaseResponse}
     * @author 黄昭
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/delete-by-id")
    BaseResponse deleteById(@RequestBody @Valid PickupSettingDelByIdRequest pickupSettingDelByIdRequest);

    /**
     * 自提点审核/启停用
     *
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/audit")
    BaseResponse pickupSettingAudit(@RequestBody @Valid PickupSettingAuditRequest request);

    /**
     * 自提点设置
     *
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/config")
    BaseResponse pickupSettingConfig(@RequestBody @Valid ConfigRequest request);

    /**
     * 自提点审核/启停用
     *
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/defaultAddress")
    BaseResponse pickupSettingDefaultAddress(@RequestBody @Valid PickupSettingDefaultAddressRequest request);

    /**
     * 修改高德地图设置
     *
     * @param request
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/modifiy/config")
    BaseResponse modifyMapSetting(@RequestBody @Valid ConfigRequest request);
}

