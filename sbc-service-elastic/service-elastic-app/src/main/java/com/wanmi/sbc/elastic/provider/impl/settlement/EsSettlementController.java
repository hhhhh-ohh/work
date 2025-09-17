package com.wanmi.sbc.elastic.provider.impl.settlement;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.settlement.EsSettlementProvider;
import com.wanmi.sbc.elastic.api.request.settlement.*;
import com.wanmi.sbc.elastic.settlement.service.EsSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @Author yangzhen
 * @Description //结算单实现
 * @Date 19:05 2020/12/7
 * @Param
 * @return
 **/
@RestController
@Validated
public class EsSettlementController implements EsSettlementProvider {

    @Autowired
    private EsSettlementService esSettlementService;

    @Override
    public BaseResponse initSettlement(@RequestBody @Valid EsSettlementInitRequest request) {
        esSettlementService.initSettlementToEs(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse initLakalaSettlement(@RequestBody @Valid EsSettlementInitRequest request) {
        esSettlementService.initLakalaSettlementToEs(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse initSettlementList(@RequestBody @Valid EsSettlementListRequest request) {
        esSettlementService.initSettlementList(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateSettlementStatus(@RequestBody @Valid SettlementQueryRequest request) {
        esSettlementService.updateSettlementStatus(request);
        return BaseResponse.SUCCESSFUL();
    }

    public BaseResponse syncLakalaSettlementStutus(@RequestBody EsSettlementInitRequest esSettlementInitRequest){
        esSettlementService.syncLakalaSettlementStutus(esSettlementInitRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse delLakalaSettlement(@RequestBody EsLakalaSettlementDelRequest esLakalaSettlementDelRequest){
        esSettlementService.delLakalaSettlement(esLakalaSettlementDelRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
