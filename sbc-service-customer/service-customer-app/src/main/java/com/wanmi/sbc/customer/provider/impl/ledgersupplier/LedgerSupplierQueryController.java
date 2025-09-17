package com.wanmi.sbc.customer.provider.impl.ledgersupplier;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgersupplier.LedgerSupplierQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierByIdRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierListRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierPageRequest;
import com.wanmi.sbc.customer.api.request.ledgersupplier.LedgerSupplierQueryRequest;
import com.wanmi.sbc.customer.api.response.ledgersupplier.LedgerSupplierByIdResponse;
import com.wanmi.sbc.customer.api.response.ledgersupplier.LedgerSupplierListResponse;
import com.wanmi.sbc.customer.api.response.ledgersupplier.LedgerSupplierPageResponse;
import com.wanmi.sbc.customer.bean.vo.LedgerSupplierVO;
import com.wanmi.sbc.customer.ledgersupplier.model.root.LedgerSupplier;
import com.wanmi.sbc.customer.ledgersupplier.service.LedgerSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>商户分账绑定数据查询服务接口实现</p>
 * @author 许云鹏
 * @date 2022-07-01 15:56:20
 */
@RestController
@Validated
public class LedgerSupplierQueryController implements LedgerSupplierQueryProvider {
	@Autowired
	private LedgerSupplierService ledgerSupplierService;

	@Override
	public BaseResponse<LedgerSupplierPageResponse> page(@RequestBody @Valid LedgerSupplierPageRequest ledgerSupplierPageReq) {
		LedgerSupplierQueryRequest queryReq = KsBeanUtil.convert(ledgerSupplierPageReq, LedgerSupplierQueryRequest.class);
		Page<LedgerSupplier> ledgerSupplierPage = ledgerSupplierService.page(queryReq);
		Page<LedgerSupplierVO> newPage = ledgerSupplierPage.map(entity -> ledgerSupplierService.wrapperVo(entity));
		MicroServicePage<LedgerSupplierVO> microPage = new MicroServicePage<>(newPage, ledgerSupplierPageReq.getPageable());
		LedgerSupplierPageResponse finalRes = new LedgerSupplierPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<LedgerSupplierListResponse> list(@RequestBody @Valid LedgerSupplierListRequest ledgerSupplierListReq) {
		LedgerSupplierQueryRequest queryReq = KsBeanUtil.convert(ledgerSupplierListReq, LedgerSupplierQueryRequest.class);
		List<LedgerSupplier> ledgerSupplierList = ledgerSupplierService.list(queryReq);
		List<LedgerSupplierVO> newList = ledgerSupplierList.stream().map(entity -> ledgerSupplierService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new LedgerSupplierListResponse(newList));
	}

	@Override
	public BaseResponse<LedgerSupplierByIdResponse> getById(@RequestBody @Valid LedgerSupplierByIdRequest ledgerSupplierByIdRequest) {
		LedgerSupplier ledgerSupplier =
		ledgerSupplierService.getOne(ledgerSupplierByIdRequest.getId());
		return BaseResponse.success(new LedgerSupplierByIdResponse(ledgerSupplierService.wrapperVo(ledgerSupplier)));
	}
}

