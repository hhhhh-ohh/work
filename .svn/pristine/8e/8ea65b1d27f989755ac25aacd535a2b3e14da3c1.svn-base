package com.wanmi.sbc.empower.provider.impl.ledgermcc;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.empower.api.provider.ledgermcc.LedgerMccProvider;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccAddRequest;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccAddResponse;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccModifyRequest;
import com.wanmi.sbc.empower.api.response.ledgermcc.LedgerMccModifyResponse;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccDelByIdRequest;
import com.wanmi.sbc.empower.api.request.ledgermcc.LedgerMccDelByIdListRequest;
import com.wanmi.sbc.empower.ledgermcc.service.LedgerMccService;
import com.wanmi.sbc.empower.ledgermcc.model.root.LedgerMcc;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>拉卡拉mcc表保存服务接口实现</p>
 * @author zhanghao
 * @date 2022-07-08 11:01:18
 */
@RestController
@Validated
public class LedgerMccController implements LedgerMccProvider {
	@Autowired
	private LedgerMccService ledgerMccService;

	@Override
	public BaseResponse<LedgerMccAddResponse> add(@RequestBody @Valid LedgerMccAddRequest ledgerMccAddRequest) {
		LedgerMcc ledgerMcc = KsBeanUtil.convert(ledgerMccAddRequest, LedgerMcc.class);
		return BaseResponse.success(new LedgerMccAddResponse(
				ledgerMccService.wrapperVo(ledgerMccService.add(ledgerMcc))));
	}

	@Override
	public BaseResponse<LedgerMccModifyResponse> modify(@RequestBody @Valid LedgerMccModifyRequest ledgerMccModifyRequest) {
		LedgerMcc ledgerMcc = KsBeanUtil.convert(ledgerMccModifyRequest, LedgerMcc.class);
		return BaseResponse.success(new LedgerMccModifyResponse(
				ledgerMccService.wrapperVo(ledgerMccService.modify(ledgerMcc))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid LedgerMccDelByIdRequest ledgerMccDelByIdRequest) {
		LedgerMcc ledgerMcc = KsBeanUtil.convert(ledgerMccDelByIdRequest, LedgerMcc.class);
		ledgerMcc.setDelFlag(DeleteFlag.YES);
		ledgerMccService.deleteById(ledgerMcc);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid LedgerMccDelByIdListRequest ledgerMccDelByIdListRequest) {
		ledgerMccService.deleteByIdList(ledgerMccDelByIdListRequest.getMccIdList());
		return BaseResponse.SUCCESSFUL();
	}

}

