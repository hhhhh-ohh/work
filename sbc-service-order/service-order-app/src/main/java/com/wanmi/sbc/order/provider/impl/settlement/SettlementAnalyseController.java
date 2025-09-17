package com.wanmi.sbc.order.provider.impl.settlement;

import com.wanmi.sbc.order.api.response.settlement.SettlementBatchAddResponse;import com.wanmi.sbc.account.api.response.finance.record.SettlementResponse;
import com.wanmi.sbc.account.bean.vo.SettlementViewVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.settlement.SettlementAnalyseProvider;
import com.wanmi.sbc.order.api.request.settlement.SettlementAnalyseRequest;
import com.wanmi.sbc.order.api.response.settlement.SettlementForEsResponse;
import com.wanmi.sbc.order.settlement.SettlementAnalyseService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-07 13:51
 */
@Validated
@RestController
public class SettlementAnalyseController implements SettlementAnalyseProvider {

    @Autowired
    private SettlementAnalyseService settlementAnalyseService;

    /**
     * 分析结算单
     *
     * @return 操作结果 {@link BaseResponse}
     */
    @Override
    public BaseResponse<SettlementForEsResponse> analyse(
            @RequestBody @Valid SettlementAnalyseRequest request) {
        SettlementResponse responseList =
                settlementAnalyseService.analyseSettlement(new Date(), request);
        if (Objects.isNull(responseList)) {
            return BaseResponse.success(null);
        }

        if (CollectionUtils.isEmpty(responseList.getSettlementAddResponses())
                && CollectionUtils.isEmpty(responseList.getLakalaSettlementAddResponses())) {
            return BaseResponse.success(null);
        }

        return BaseResponse.success(
                new SettlementForEsResponse(
                        responseList.getSettlementAddResponses().stream()
                                .map(
                                        settlementViewVo -> {
                                            SettlementViewVO vo = new SettlementViewVO();
                                            KsBeanUtil.copyPropertiesThird(settlementViewVo, vo);
                                            vo.setSettleCode(
                                                    String.format("S%07d", vo.getSettleId()));
                                            return vo;
                                        })
                                .collect(Collectors.toList()), null));
    }

    public BaseResponse<List<SettlementBatchAddResponse>> analyseStore(@RequestBody @Valid SettlementAnalyseRequest request){
        return BaseResponse.success(settlementAnalyseService
                .analyseSettlementForStore(request.getLedgerAccountVO(),request.getStore(),request.getStoreType(),request.getEndTime()));
    }
}
