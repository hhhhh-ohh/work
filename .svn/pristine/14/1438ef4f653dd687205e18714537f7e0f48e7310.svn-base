package com.wanmi.sbc.account.provider.impl.ledgerfunds;

import com.wanmi.sbc.account.api.request.ledgerfunds.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.account.api.provider.ledgerfunds.LedgerFundsQueryProvider;
import com.wanmi.sbc.account.api.response.ledgerfunds.LedgerFundsPageResponse;
import com.wanmi.sbc.account.api.response.ledgerfunds.LedgerFundsListResponse;
import com.wanmi.sbc.account.api.response.ledgerfunds.LedgerFundsByIdResponse;
import com.wanmi.sbc.account.bean.vo.LedgerFundsVO;
import com.wanmi.sbc.account.ledgerfunds.service.LedgerFundsService;
import com.wanmi.sbc.account.ledgerfunds.model.root.LedgerFunds;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>会员分账资金查询服务接口实现</p>
 * @author xuyunpeng
 * @date 2022-07-25 16:54:38
 */
@RestController
@Validated
public class LedgerFundsQueryController implements LedgerFundsQueryProvider {
	@Autowired
	private LedgerFundsService ledgerFundsService;

	@Override
	public BaseResponse<LedgerFundsPageResponse> page(@RequestBody @Valid LedgerFundsPageRequest ledgerFundsPageReq) {
		LedgerFundsQueryRequest queryReq = KsBeanUtil.convert(ledgerFundsPageReq, LedgerFundsQueryRequest.class);
		Page<LedgerFunds> ledgerFundsPage = ledgerFundsService.page(queryReq);
		Page<LedgerFundsVO> newPage = ledgerFundsPage.map(entity -> ledgerFundsService.wrapperVo(entity));
		MicroServicePage<LedgerFundsVO> microPage = new MicroServicePage<>(newPage, ledgerFundsPageReq.getPageable());
		LedgerFundsPageResponse finalRes = new LedgerFundsPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<LedgerFundsListResponse> list(@RequestBody @Valid LedgerFundsListRequest ledgerFundsListReq) {
		LedgerFundsQueryRequest queryReq = KsBeanUtil.convert(ledgerFundsListReq, LedgerFundsQueryRequest.class);
		List<LedgerFunds> ledgerFundsList = ledgerFundsService.list(queryReq);
		List<LedgerFundsVO> newList = ledgerFundsList.stream().map(entity -> ledgerFundsService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new LedgerFundsListResponse(newList));
	}

	@Override
	public BaseResponse<LedgerFundsByIdResponse> getById(@RequestBody @Valid LedgerFundsByIdRequest ledgerFundsByIdRequest) {
		LedgerFunds ledgerFunds =
		ledgerFundsService.getOne(ledgerFundsByIdRequest.getId());
		return BaseResponse.success(new LedgerFundsByIdResponse(ledgerFundsService.wrapperVo(ledgerFunds)));
	}

	@Override
	public BaseResponse<LedgerFundsByIdResponse> getByCustomerId(@RequestBody @Valid LedgerFundsByCustomerIdRequest request) {
		LedgerFunds ledgerFunds = ledgerFundsService.findByCustomerId(request.getCustomerId());
		return BaseResponse.success(new LedgerFundsByIdResponse(ledgerFundsService.wrapperVo(ledgerFunds)));
	}
}

