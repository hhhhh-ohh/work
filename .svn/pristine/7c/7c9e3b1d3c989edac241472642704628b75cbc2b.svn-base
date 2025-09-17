package com.wanmi.sbc.elastic.api.provider.ledger;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoPageRequest;
import com.wanmi.sbc.elastic.api.response.ledger.EsLedgerBindInfoPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * 分账绑定es查询接口
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsLedgerBindInfoQueryProvider")
public interface EsLedgerBindInfoQueryProvider {

    /**
     * 初始化ES数据
     *
     * @param request 初始化ES条件 {@link EsLedgerBindInfoPageRequest}
     * @return 操作结果 {@link BaseResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/ledger-bind-info/page")
    BaseResponse<EsLedgerBindInfoPageResponse> page(@RequestBody @Valid EsLedgerBindInfoPageRequest request);
}
