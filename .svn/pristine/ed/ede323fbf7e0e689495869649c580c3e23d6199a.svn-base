package com.wanmi.sbc.setting.provider.impl.pickupemployeerela;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.provider.pickupemployeerela.PickupSettingRelaProvider;
import com.wanmi.sbc.setting.api.request.pickupemployeerela.PickupSettingRelaDelByEmployeesRequest;
import com.wanmi.sbc.setting.pickupemployeerela.service.PickupEmployeeRelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>pickup_setting_rela服务接口实现</p>
 * @author xufeng
 * @date 2021-09-26 11:01:10
 */
@RestController
@Validated
public class PickupSettingRelaController implements PickupSettingRelaProvider {

	@Autowired
	private PickupEmployeeRelaService pickupEmployeeRelaService;

	@Override
	@Transactional
	public BaseResponse deleteByEmployeeIds(@RequestBody @Valid PickupSettingRelaDelByEmployeesRequest pickupSettingRelaDelByEmployeesRequest) {
		pickupEmployeeRelaService.deleteByEmployeeIds(pickupSettingRelaDelByEmployeesRequest.getEmployeeIds());
		return BaseResponse.SUCCESSFUL();
	}

}