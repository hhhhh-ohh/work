package com.wanmi.sbc.order.provider.impl.pickupcoderecord;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.provider.pickupcoderecord.PickupCodeRecordProvider;
import com.wanmi.sbc.order.pickupcoderecord.service.PickupCodeRecordService;

/**
 * <p>提货码记录保存服务接口实现</p>
 * @author 吕振伟
 * @date 2023-04-19 14:03:52
 */
@RestController
@Validated
public class PickupCodeRecordController implements PickupCodeRecordProvider {
	@Autowired
	private PickupCodeRecordService pickupCodeRecordService;

	@Override
	public BaseResponse deleteExpirePickupCodeRecord() {
		pickupCodeRecordService.deleteExpirePickupCodeRecord();
		return BaseResponse.SUCCESSFUL();
	}
}

