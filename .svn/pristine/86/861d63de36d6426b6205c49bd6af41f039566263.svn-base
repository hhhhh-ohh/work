package com.wanmi.sbc.job;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.settlement.EsSettlementProvider;
import com.wanmi.sbc.elastic.api.request.settlement.EsSettlementListRequest;
import com.wanmi.sbc.elastic.bean.dto.settlement.EsSettlementDTO;
import com.wanmi.sbc.job.service.SettlementAnalyseJobService;import com.wanmi.sbc.order.api.provider.settlement.SettlementAnalyseProvider;
import com.wanmi.sbc.order.api.request.settlement.SettlementAnalyseRequest;
import com.wanmi.sbc.order.api.response.settlement.SettlementForEsResponse;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 定时任务Handler（Bean模式）
 * o2o门店结算
 *
 * @author bail 2019-3-24
 */
@Component
@Slf4j
public class O2oSettlementAnalyseJobHandler {

    @Autowired
    private SettlementAnalyseProvider settlementAnalyseProvider;


    @Autowired
    private EsSettlementProvider esSettlementProvider;

    @Autowired private SettlementAnalyseJobService settlementAnalyseJobService;

    @XxlJob(value = "o2oSettlementAnalyseJobHandler")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        settlementAnalyseJobService.analyseSettlement(SettlementAnalyseRequest.builder().param(param).storeType(StoreType.PROVIDER).build());
//        BaseResponse<SettlementForEsResponse> response = settlementAnalyseProvider.analyse(new SettlementAnalyseRequest(param, StoreType.O2O));
//        SettlementForEsResponse context = response.getContext();
//        if (Objects.nonNull(context)) {
//            //同步结算单到es
//            if (CollectionUtils.isNotEmpty(context.getLists())) {
//                esSettlementProvider.initSettlementList(EsSettlementListRequest.builder().lists(
//                        context.getLists().stream().map(settlementViewVo -> {
//                            EsSettlementDTO dto = KsBeanUtil.convert(settlementViewVo, EsSettlementDTO.class);
//                            return dto;
//                        }).collect(Collectors.toList())
//                ).build());
//            }
//        }
    }
}
