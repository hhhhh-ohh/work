package com.wanmi.sbc.job;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.enums.storemessage.BossMessageNode;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.elastic.api.provider.settlement.EsSettlementProvider;
import com.wanmi.sbc.elastic.api.request.settlement.EsSettlementListRequest;
import com.wanmi.sbc.elastic.bean.dto.settlement.EsSettlementDTO;
import com.wanmi.sbc.job.service.SettlementAnalyseJobService;import com.wanmi.sbc.message.service.StoreMessageBizService;
import com.wanmi.sbc.order.api.provider.settlement.SettlementAnalyseProvider;
import com.wanmi.sbc.order.api.request.settlement.SettlementAnalyseRequest;
import com.wanmi.sbc.order.api.response.settlement.SettlementForEsResponse;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 定时任务Handler（Bean模式）
 * 供应商结算任务
 *
 * @author bail 2019-3-24
 */
@Component
public class ProviderSettlementAnalyseJobHandler {
    @Autowired
    private SettlementAnalyseProvider settlementAnalyseProvider;

    @Autowired
    private EsSettlementProvider esSettlementProvider;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    @Autowired private SettlementAnalyseJobService settlementAnalyseJobService;

    @XxlJob(value="ProviderSettlementAnalyseJobHandler")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        settlementAnalyseJobService.analyseSettlement(SettlementAnalyseRequest.builder().param(param).storeType(StoreType.PROVIDER).build());
//        BaseResponse<SettlementForEsResponse> response  = settlementAnalyseProvider.analyse(new SettlementAnalyseRequest(param , StoreType.PROVIDER));
//        SettlementForEsResponse context = response.getContext();
//        if(Objects.nonNull(context)){
//            //同步结算单到es
//            if(CollectionUtils.isNotEmpty(context.getLists())){
//                esSettlementProvider.initSettlementList(EsSettlementListRequest.builder().lists(
//                        context.getLists().stream().map(settlementViewVo -> {
//                            EsSettlementDTO dto =  KsBeanUtil.convert(settlementViewVo, EsSettlementDTO.class);
//                            return dto;
//                        }).collect(Collectors.toList())
//                ).build());
//
//                // ============= 处理平台的消息发送，供应商待结算单生成提醒 START =============
//                storeMessageBizService.handleForSettlementProduce(BossMessageNode.PROVIDER_SETTLEMENT_PRODUCE.getCode(), context.getLists());
//                // ============= 处理平台的消息发送：供应商待结算单生成提醒 END =============
//
//            }
//        }
    }

}
