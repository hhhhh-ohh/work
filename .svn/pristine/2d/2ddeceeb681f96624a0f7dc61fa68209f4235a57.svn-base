package com.wanmi.sbc.customer.api.provider.ledgersupplier;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierByIdRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierListRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierPageRequest;
import com.wanmi.sbc.customer.api.response.ledgersupplier.LedgerSupplierByIdResponse;
import com.wanmi.sbc.customer.api.response.ledgersupplier.LedgerSupplierListResponse;
import com.wanmi.sbc.customer.api.response.ledgersupplier.LedgerSupplierPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>商户分账绑定数据查询服务Provider</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@FeignClient(value = "${application.customer.name}", contextId = "LedgerSupplierQueryProvider")
public interface LedgerSupplierQueryProvider {

	/**
	 * 分页查询商户分账绑定数据API
	 *
	 * @author 许云鹏
	 * @param ledgerSupplierPageReq 分页请求参数和筛选对象 {@link LedgerSupplierPageRequest}
	 * @return 商户分账绑定数据分页列表信息 {@link LedgerSupplierPageResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgersupplier/page")
	BaseResponse<LedgerSupplierPageResponse> page(@RequestBody @Valid LedgerSupplierPageRequest ledgerSupplierPageReq);

	/**
	 * 列表查询商户分账绑定数据API
	 *
	 * @author 许云鹏
	 * @param ledgerSupplierListReq 列表请求参数和筛选对象 {@link LedgerSupplierListRequest}
	 * @return 商户分账绑定数据的列表信息 {@link LedgerSupplierListResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgersupplier/list")
	BaseResponse<LedgerSupplierListResponse> list(@RequestBody @Valid LedgerSupplierListRequest ledgerSupplierListReq);

	/**
	 * 单个查询商户分账绑定数据API
	 *
	 * @author 许云鹏
	 * @param ledgerSupplierByIdRequest 单个查询商户分账绑定数据请求参数 {@link LedgerSupplierByIdRequest}
	 * @return 商户分账绑定数据详情 {@link LedgerSupplierByIdResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgersupplier/get-by-id")
	BaseResponse<LedgerSupplierByIdResponse> getById(@RequestBody @Valid LedgerSupplierByIdRequest ledgerSupplierByIdRequest);

}

