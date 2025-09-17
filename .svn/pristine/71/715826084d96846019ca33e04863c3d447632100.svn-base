package com.wanmi.sbc.setting.api.provider.pickupsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingByIdRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingIdsRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingListRequest;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingPageRequest;
import com.wanmi.sbc.setting.api.response.pickupsetting.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>pickup_setting查询服务Provider</p>
 *
 * @author 黄昭
 * @date 2021-09-03 11:01:10
 */
@FeignClient(value = "${application.setting.name}", contextId = "PickupSettingQueryProvider")
public interface PickupSettingQueryProvider {

    /**
     * 分页查询pickup_settingAPI
     *
     * @param pickupSettingPageReq 分页请求参数和筛选对象 {@link PickupSettingPageRequest}
     * @return pickup_setting分页列表信息 {@link PickupSettingPageResponse}
     * @author 黄昭
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/page")
    BaseResponse<PickupSettingPageResponse> page(@RequestBody @Valid PickupSettingPageRequest pickupSettingPageReq);

    /**
     * 列表查询pickup_settingAPI
     *
     * @param pickupSettingListReq 列表请求参数和筛选对象 {@link PickupSettingListRequest}
     * @return pickup_setting的列表信息 {@link PickupSettingListResponse}
     * @author 黄昭
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/list")
    BaseResponse<PickupSettingListResponse> list(@RequestBody @Valid PickupSettingListRequest pickupSettingListReq);

    /**
     * 自提点总数
     * @param pickupSettingPageReq
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/total")
    BaseResponse<Long> total(@RequestBody @Valid PickupSettingPageRequest pickupSettingPageReq);

    /**
     * 单个查询pickup_settingAPI
     *
     * @param pickupSettingByIdRequest 单个查询pickup_setting请求参数 {@link PickupSettingByIdRequest}
     * @return pickup_setting详情 {@link PickupSettingByIdResponse}
     * @author 黄昭
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/get-by-id")
    BaseResponse<PickupSettingByIdResponse> getById(@RequestBody @Valid PickupSettingByIdRequest pickupSettingByIdRequest);

    /**
     * 查询自提订单设置
	 * @return
     */
	@PostMapping("/setting/${application.setting.version}/pickupsetting/config/show")
    BaseResponse<PickupSettingConfigResponse> pickupSettingConfigShow();

    /**
     * 根据员工Id查询关联自提点
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/pickup/ids")
    BaseResponse<PickupSettingIdsResponse> getPickupIdsByEmployeeId(@RequestBody @Valid PickupSettingIdsRequest request);

    /**
     * 查询当前商家下未关联员工自提点
     * @return
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/noemployee/pickup/ids")
    BaseResponse<PickupSettingIdsResponse> getNoEmployeePickupIdsByStoreId(@RequestBody @Valid PickupSettingIdsRequest request);

    /**
     * 查询高德地图是否开启
     */
    @PostMapping("/setting/${application.setting.version}/pickupsetting/open/map")
    BaseResponse<PickupSettingConfigResponse> getWhetherOpenMap();
}

