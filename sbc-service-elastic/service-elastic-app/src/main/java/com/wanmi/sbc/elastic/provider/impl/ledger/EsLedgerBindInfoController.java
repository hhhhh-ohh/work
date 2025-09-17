package com.wanmi.sbc.elastic.provider.impl.ledger;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.ledger.EsLedgerBindInfoProvider;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoBatchSaveRequest;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoInitRequest;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoUpdateRequest;
import com.wanmi.sbc.elastic.ledger.model.EsLedgerBindInfo;
import com.wanmi.sbc.elastic.ledger.service.EsLedgerBindInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @author xuyunpeng
 * @className EsLedgerBindInfoController
 * @description
 * @date 2022/7/13 4:09 PM
 **/
@RestController
@Validated
public class EsLedgerBindInfoController implements EsLedgerBindInfoProvider {
    @Autowired
    private EsLedgerBindInfoService esLedgerBindInfoService;

    @Override
    public BaseResponse init(@RequestBody @Valid EsLedgerBindInfoInitRequest request) {
        esLedgerBindInfoService.initEs(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse batchSave(@RequestBody @Valid EsLedgerBindInfoBatchSaveRequest request) {
        List<EsLedgerBindInfo> infos = KsBeanUtil.convertList(request.getInfos(), EsLedgerBindInfo.class);
        esLedgerBindInfoService.saveAll(infos);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateNameAndAccount(@RequestBody @Valid EsLedgerBindInfoUpdateRequest request) {
        esLedgerBindInfoService.updateNameAndAccount(request);
        return BaseResponse.SUCCESSFUL();
    }
}
