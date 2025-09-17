package com.wanmi.sbc.customer.provider.impl.ledgersupplier;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgersupplier.LedgerSupplierProvider;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierAddRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierModifyRequest;
import com.wanmi.sbc.customer.api.response.ledgersupplier.LedgerSupplierAddResponse;
import com.wanmi.sbc.customer.api.response.ledgersupplier.LedgerSupplierModifyResponse;
import com.wanmi.sbc.customer.ledgersupplier.model.root.LedgerSupplier;
import com.wanmi.sbc.customer.ledgersupplier.service.LedgerSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>商户分账绑定数据保存服务接口实现</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@RestController
@Validated
public class LedgerSupplierController implements LedgerSupplierProvider {
	@Autowired
	private LedgerSupplierService ledgerSupplierService;

	@Override
	public BaseResponse<LedgerSupplierAddResponse> add(@RequestBody @Valid LedgerSupplierAddRequest ledgerSupplierAddRequest) {
		LedgerSupplier ledgerSupplier = KsBeanUtil.convert(ledgerSupplierAddRequest, LedgerSupplier.class);
		return BaseResponse.success(new LedgerSupplierAddResponse(
				ledgerSupplierService.wrapperVo(ledgerSupplierService.add(ledgerSupplier))));
	}


	@Override
	public BaseResponse updatePlatBindStateById(@RequestBody @Valid LedgerSupplierModifyRequest ledgerSupplierModifyRequest) {
		ledgerSupplierService.updatePlatBindStateById(ledgerSupplierModifyRequest.getPlatBindState(), ledgerSupplierModifyRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateProviderNumById(@RequestBody @Valid LedgerSupplierModifyRequest ledgerSupplierModifyRequest) {
		ledgerSupplierService.updateProviderNumById(ledgerSupplierModifyRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse updateDistributionNumById(@RequestBody @Valid LedgerSupplierModifyRequest ledgerSupplierModifyRequest) {
		ledgerSupplierService.updateDistributionNumById(ledgerSupplierModifyRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}
}

