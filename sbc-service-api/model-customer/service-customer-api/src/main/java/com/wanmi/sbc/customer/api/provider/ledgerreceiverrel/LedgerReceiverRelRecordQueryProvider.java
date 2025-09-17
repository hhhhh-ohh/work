package com.wanmi.sbc.customer.api.provider.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelRecordPageRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelRecordQueryRequest;
import com.wanmi.sbc.customer.api.response.ledgerreceiverrel.LedgerReceiverRelRecordPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>分账绑定关系补偿记录查询服务Provider</p>
 * @author xuyunpeng
 * @date 2022-07-14 15:15:45
 */
@FeignClient(value = "${application.customer.name}", contextId = "LedgerReceiverRelRecordQueryProvider")
public interface LedgerReceiverRelRecordQueryProvider {

	/**
	 * 分页查询分账绑定关系补偿记录API
	 *
	 * @author xuyunpeng
	 * @param ledgerReceiverRelRecordPageReq 分页请求参数和筛选对象 {@link LedgerReceiverRelRecordPageRequest}
	 * @return 分账绑定关系补偿记录分页列表信息 {@link LedgerReceiverRelRecordPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrelrecord/page")
	BaseResponse<LedgerReceiverRelRecordPageResponse> page(@RequestBody @Valid LedgerReceiverRelRecordPageRequest ledgerReceiverRelRecordPageReq);

	/**
	 * 分页查询分账绑定关系补偿记录API
	 *
	 * @author xuyunpeng
	 * @param request 分页请求参数和筛选对象 {@link LedgerReceiverRelRecordQueryRequest}
	 * @return 分账绑定关系补偿记录分页列表信息 {@link Long}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgerreceiverrelrecord/count")
	BaseResponse<Long> count(@RequestBody @Valid LedgerReceiverRelRecordQueryRequest request);

}

