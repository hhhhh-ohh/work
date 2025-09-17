package com.wanmi.sbc.customer.api.provider.ledgercontract;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.response.ledgercontract.LedgerContractByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>分账合同协议配置查询服务Provider</p>
 * @author 许云鹏
 * @date 2022-07-07 17:54:08
 */
@FeignClient(value = "${application.customer.name}", contextId = "LedgerContractQueryProvider")
public interface LedgerContractQueryProvider {

	/**
	 * 单个查询分账合同协议配置API
	 *
	 * @author 许云鹏
	 * @param
	 * @return 分账合同协议配置详情 {@link LedgerContractByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgercontract/get")
	BaseResponse<LedgerContractByIdResponse> getContract();

}

