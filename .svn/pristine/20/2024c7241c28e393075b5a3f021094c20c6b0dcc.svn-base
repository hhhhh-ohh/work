package com.wanmi.sbc.customer.api.provider.ledger;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.ledger.LakalaJobRequest;
import com.wanmi.sbc.customer.api.request.ledger.LakalaAccountFunctionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>拉卡拉接口服务Provider</p>
 * @author 许云鹏
 * @date 2022-07-01 15:50:40
 */
@FeignClient(value = "${application.customer.name}", contextId = "LakalaProvider")
public interface LakalaProvider {

	/**
	 * 分账功能调用接口API
	 *
	 * @author 许云鹏
	 * @param request 参数结构 {@link LakalaAccountFunctionRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledger/excute-function")
	BaseResponse excuteFunction(@RequestBody @Valid LakalaAccountFunctionRequest request);

	/**
	 * 定时任务补偿API
	 *
	 * @author 许云鹏
	 * @param request 定时任务补偿参数结构 {@link LakalaJobRequest}
	 * @return
	 */
	@PostMapping("/customer/${application.customer.version}/ledger/job")
	BaseResponse ledgerJob(@RequestBody @Valid LakalaJobRequest request);

}

