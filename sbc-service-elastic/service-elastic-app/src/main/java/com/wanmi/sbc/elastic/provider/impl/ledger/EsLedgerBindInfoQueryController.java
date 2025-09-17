package com.wanmi.sbc.elastic.provider.impl.ledger;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.ledger.EsLedgerBindInfoQueryProvider;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoPageRequest;
import com.wanmi.sbc.elastic.api.response.ledger.EsLedgerBindInfoPageResponse;
import com.wanmi.sbc.elastic.bean.vo.ledger.EsLedgerBindInfoVO;
import com.wanmi.sbc.elastic.ledger.model.EsLedgerBindInfo;
import com.wanmi.sbc.elastic.ledger.service.EsLedgerBindInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author xuyunpeng
 * @className EsLedgerBindInfoQueryController
 * @description
 * @date 2022/7/13 4:12 PM
 **/
@RestController
@Validated
public class EsLedgerBindInfoQueryController implements EsLedgerBindInfoQueryProvider {

    @Autowired
    private EsLedgerBindInfoService esLedgerBindInfoService;

    @Override
    public BaseResponse<EsLedgerBindInfoPageResponse> page(@RequestBody @Valid EsLedgerBindInfoPageRequest request) {
        Page<EsLedgerBindInfo> page = esLedgerBindInfoService.page(request);
        MicroServicePage<EsLedgerBindInfoVO> esLedgerBindInfoVOS = KsBeanUtil.convertPage(page, EsLedgerBindInfoVO.class);
        return BaseResponse.success(new EsLedgerBindInfoPageResponse(esLedgerBindInfoVOS));
    }
}
