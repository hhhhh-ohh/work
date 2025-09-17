package com.wanmi.sbc.order.api.provider.pickupcoderecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.pickupcoderecord.PickupCodeRecordAddRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>提货码记录保存服务Provider</p>
 * @author 吕振伟
 * @date 2023-04-19 14:03:52
 */
@FeignClient(value = "${application.order.name}", contextId = "PickupCodeRecordProvider")
public interface PickupCodeRecordProvider {

	/**
	 * 删除过期提货码
	 *
	 * @author 吕振伟
	 */
	@PostMapping("/order/${application.order.version}/delete-expire-pickup-code-record")
	BaseResponse deleteExpirePickupCodeRecord();


}

