package com.wanmi.sbc.customer.api.provider.ledgersupplier;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierAddRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierModifyRequest;
import com.wanmi.sbc.customer.api.response.ledgersupplier.LedgerSupplierAddResponse;
import com.wanmi.sbc.customer.api.response.ledgersupplier.LedgerSupplierModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>商户分账绑定数据保存服务Provider</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@FeignClient(value = "${application.customer.name}", contextId = "LedgerSupplierProvider")
public interface LedgerSupplierProvider {

	/**
	 * 新增商户分账绑定数据API
	 *
	 * @author 许云鹏
	 * @param ledgerSupplierAddRequest 商户分账绑定数据新增参数结构 {@link LedgerSupplierAddRequest}
	 * @return 新增的商户分账绑定数据信息 {@link LedgerSupplierAddResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgersupplier/add")
	BaseResponse<LedgerSupplierAddResponse> add(@RequestBody @Valid LedgerSupplierAddRequest ledgerSupplierAddRequest);

	/**
	 * 修改商户分账绑定数据API
	 *
	 * @author 许云鹏
	 * @param ledgerSupplierModifyRequest 商户分账绑定数据修改参数结构 {@link LedgerSupplierModifyRequest}
	 * @return 修改的商户分账绑定数据信息 {@link LedgerSupplierModifyResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgersupplier/update-platBindState-by-id")
	BaseResponse updatePlatBindStateById(@RequestBody @Valid LedgerSupplierModifyRequest ledgerSupplierModifyRequest);

	/**
	 * 修改商户分账绑定数据API
	 *
	 * @author 许云鹏
	 * @param ledgerSupplierModifyRequest 商户分账绑定数据修改参数结构 {@link LedgerSupplierModifyRequest}
	 * @return 修改的商户分账绑定数据信息 {@link LedgerSupplierModifyResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgersupplier/update-providerNum-by-id")
	BaseResponse updateProviderNumById(@RequestBody @Valid LedgerSupplierModifyRequest ledgerSupplierModifyRequest);


	/**
	 * 修改商户分账绑定数据API
	 *
	 * @author 许云鹏
	 * @param ledgerSupplierModifyRequest 商户分账绑定数据修改参数结构 {@link LedgerSupplierModifyRequest}
	 * @return 修改的商户分账绑定数据信息 {@link LedgerSupplierModifyResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/ledgersupplier/update-distributionNum-by-id")
	BaseResponse updateDistributionNumById(@RequestBody @Valid LedgerSupplierModifyRequest ledgerSupplierModifyRequest);

}

