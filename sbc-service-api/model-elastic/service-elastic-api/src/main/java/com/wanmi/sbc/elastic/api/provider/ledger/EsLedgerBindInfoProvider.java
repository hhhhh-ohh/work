package com.wanmi.sbc.elastic.api.provider.ledger;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoBatchSaveRequest;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoInitRequest;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * 分账绑定es接口
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsLedgerBindInfoProvider")
public interface EsLedgerBindInfoProvider {

    /**
     * 初始化ES数据
     *
     * @param request 初始化ES条件 {@link EsLedgerBindInfoInitRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/ledger-bind-info/init")
    BaseResponse init(@RequestBody @Valid EsLedgerBindInfoInitRequest request);

    /**
     * 批量保存ES数据
     *
     * @param request 初始化ES条件 {@link EsLedgerBindInfoBatchSaveRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/ledger-bind-info/batch-save")
    BaseResponse batchSave(@RequestBody @Valid EsLedgerBindInfoBatchSaveRequest request);

    /**
     * 批量保存ES数据
     *
     * @param request 初始化ES条件 {@link EsLedgerBindInfoUpdateRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/ledger-bind-info/update-name-and-account")
    BaseResponse updateNameAndAccount(@RequestBody @Valid EsLedgerBindInfoUpdateRequest request);
}
