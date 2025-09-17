package com.wanmi.sbc.customer.provider.impl.ledgerreceiverrel;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelRecordQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelRecordPageRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelRecordQueryRequest;
import com.wanmi.sbc.customer.api.response.ledgerreceiverrel.LedgerReceiverRelRecordPageResponse;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelRecordVO;
import com.wanmi.sbc.customer.ledgerreceiverrelrecord.model.root.LedgerReceiverRelRecord;
import com.wanmi.sbc.customer.ledgerreceiverrelrecord.service.LedgerReceiverRelRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>分账绑定关系补偿记录查询服务接口实现</p>
 * @author xuyunpeng
 * @date 2022-07-14 15:15:45
 */
@RestController
@Validated
public class LedgerReceiverRelRecordQueryController implements LedgerReceiverRelRecordQueryProvider {
	@Autowired
	private LedgerReceiverRelRecordService ledgerReceiverRelRecordService;

	@Override
	public BaseResponse<LedgerReceiverRelRecordPageResponse> page(@RequestBody @Valid LedgerReceiverRelRecordPageRequest ledgerReceiverRelRecordPageReq) {
		LedgerReceiverRelRecordQueryRequest queryReq = KsBeanUtil.convert(ledgerReceiverRelRecordPageReq, LedgerReceiverRelRecordQueryRequest.class);
		Page<LedgerReceiverRelRecord> ledgerReceiverRelRecordPage = ledgerReceiverRelRecordService.page(queryReq);
		Page<LedgerReceiverRelRecordVO> newPage = ledgerReceiverRelRecordPage.map(entity -> ledgerReceiverRelRecordService.wrapperVo(entity));
		MicroServicePage<LedgerReceiverRelRecordVO> microPage = new MicroServicePage<>(newPage, ledgerReceiverRelRecordPageReq.getPageable());
		LedgerReceiverRelRecordPageResponse finalRes = new LedgerReceiverRelRecordPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<Long> count(@RequestBody @Valid LedgerReceiverRelRecordQueryRequest request) {
		Long count = ledgerReceiverRelRecordService.count(request);
		return BaseResponse.success(count);
	}
}

