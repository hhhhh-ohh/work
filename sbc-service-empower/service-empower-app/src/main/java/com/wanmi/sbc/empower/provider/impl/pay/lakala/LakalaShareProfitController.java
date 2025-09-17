package com.wanmi.sbc.empower.provider.impl.pay.lakala;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.LedgerAccountFindRequest;
import com.wanmi.sbc.customer.api.response.ledgeraccount.LedgerAccountByIdResponse;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.empower.api.provider.pay.Lakala.LakalaShareProfitProvider;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaShareProfitQueryRequest;
import com.wanmi.sbc.empower.api.request.settlement.SeporcancelForSupplierReq;
import com.wanmi.sbc.empower.api.request.settlement.SettlementRequest;
import com.wanmi.sbc.empower.api.request.settlement.SepTranSidRequest;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaShareProfitQueryResponse;
import com.wanmi.sbc.empower.pay.service.lakala.LakalaPayServiceImpl;
import com.wanmi.sbc.empower.pay.service.lakala.LakalaSeporcancelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author edz
 * @className LakalaShareProfitController
 * @description TODO
 * @date 2022/7/20 11:21
 */
@RestController
@Slf4j
public class LakalaShareProfitController implements LakalaShareProfitProvider {

    @Autowired private LakalaSeporcancelService lakalaSeporcancelService;

    @Autowired private LedgerAccountQueryProvider ledgerAccountQueryProvider;


    @Override
    public BaseResponse seporcancel(@RequestBody SettlementRequest settlementRequest) {
        log.debug("分账-seporcancel入参;{}", settlementRequest);
        if (settlementRequest.getType() == 0) {
            lakalaSeporcancelService.syncMode(settlementRequest);
        } else {
            lakalaSeporcancelService.asyncMode(settlementRequest);
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse seporcancelForSupplier(@RequestBody SeporcancelForSupplierReq settlementRequest) {
        lakalaSeporcancelService.seporcancelForSupplier(settlementRequest.getId());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description 拉卡拉分账查询
     * @author edz
     * @date: 2022/7/30 17:10
     * @param sepTranSidRequest
     * @return
     *     com.wanmi.sbc.common.base.BaseResponse<java.util.List<com.wanmi.sbc.empower.api.response.pay.lakala.LakalaShareProfitQueryResponse>>
     */
    public BaseResponse<List<LakalaShareProfitQueryResponse>> seporcancelQuery(
            @RequestBody SepTranSidRequest sepTranSidRequest) {
        List<LakalaShareProfitQueryResponse> lakalaShareProfitQueryResponses = new ArrayList<>();
        LakalaShareProfitQueryRequest request =
                LakalaShareProfitQueryRequest.builder()
                        .sepType("1")
                        .build();
        sepTranSidRequest
                .getSepTranSids()
                .forEach(
                        str -> {
                            request.setSepTranSid(str);
                            LakalaShareProfitQueryResponse response =
                                    lakalaSeporcancelService.seporcancelQuery(request).getContext();
                            response.setSepTranSid(str);
                            lakalaShareProfitQueryResponses.add(response);
                        });
        return BaseResponse.success(lakalaShareProfitQueryResponses);
    }
}
