package com.wanmi.sbc.job.service;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.api.provider.finance.record.SettlementProvider;
import com.wanmi.sbc.account.api.provider.finance.record.SettlementQueryProvider;
import com.wanmi.sbc.account.api.request.finance.record.LakalaSettlementAddRequest;
import com.wanmi.sbc.account.api.request.finance.record.SettlementAddRequest;
import com.wanmi.sbc.account.api.request.finance.record.SettlementDeleteRequest;
import com.wanmi.sbc.account.api.request.finance.record.SettlementLastByStoreIdRequest;
import com.wanmi.sbc.account.api.response.finance.record.LakalaSettlementAddResponse;
import com.wanmi.sbc.account.api.response.finance.record.LakalaSettlementGetByIdResponse;
import com.wanmi.sbc.account.api.response.finance.record.SettlementAddResponse;
import com.wanmi.sbc.account.api.response.finance.record.SettlementLastResponse;
import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.account.bean.vo.LakalaSettlementVO;
import com.wanmi.sbc.account.bean.vo.SettlementVO;
import com.wanmi.sbc.account.bean.vo.SettlementViewVO;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.enums.storemessage.BossMessageNode;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgeraccount.LedgerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgeraccount.QueryByBusinessIdsRequest;
import com.wanmi.sbc.customer.api.request.store.ListStoreForSettleRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.store.StoreByIdResponse;
import com.wanmi.sbc.customer.bean.vo.LedgerAccountVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.elastic.api.provider.settlement.EsSettlementProvider;
import com.wanmi.sbc.elastic.api.request.settlement.EsSettlementInitRequest;
import com.wanmi.sbc.elastic.api.request.settlement.EsSettlementListRequest;
import com.wanmi.sbc.elastic.bean.dto.settlement.EsSettlementDTO;
import com.wanmi.sbc.empower.api.provider.pay.Lakala.LakalaShareProfitProvider;
import com.wanmi.sbc.empower.api.request.settlement.SettlementRequest;
import com.wanmi.sbc.message.service.StoreMessageBizService;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;
import com.wanmi.sbc.order.api.provider.settlement.SettlementAnalyseProvider;
import com.wanmi.sbc.order.api.request.settlement.SettlementAnalyseRequest;
import com.wanmi.sbc.order.api.response.settlement.SettlementBatchAddResponse;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
/**
 * @type SettlementAnalyseJobService.java
 * @desc
 * @author zhanggaolei
 * @date 2023/4/7 15:05
 * @version
 */
@Slf4j
@Service
public class SettlementAnalyseJobService {

    @Autowired private StoreQueryProvider storeQueryProvider;

    @Autowired private LedgerAccountQueryProvider ledgerAccountQueryProvider;

    @Autowired private EsSettlementProvider esSettlementProvider;

    @Autowired private StoreMessageBizService storeMessageBizService;

    @Autowired private LakalaShareProfitProvider lakalaShareProfitProvider;

    @Autowired private SettlementQueryProvider settlementQueryProvider;

    @Autowired private SettlementProvider settlementProvider;

    @Autowired private SettlementAnalyseProvider settlementAnalyseProvider;

    @Autowired private MqSendProvider mqSendProvider;

    @GlobalTransactional
    public void analyseSettlement( SettlementAnalyseRequest request) {
        Date targetDate = new Date();

        String param = request.getParam();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(targetDate);
//        if(StringUtils.isNotBlank(param)){
//            calendar.setTime(DateUtil.parseDate1(param));
//        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (StringUtils.isNotEmpty(param)) {
            calendar.add(Calendar.DATE, 1);
            log.info("包含今日订单的结算");
        }

        // 获取当天的日期，几号
        int targetDay = calendar.get(Calendar.DAY_OF_MONTH);
        List<StoreVO> storeList =
                storeQueryProvider
                        .listForSettle(
                                ListStoreForSettleRequest.builder()
                                        .targetDay(targetDay)
                                        .storeType(request.getStoreType())
                                        .build())
                        .getContext()
                        .getStoreVOList();
        log.info("商家列表>>>{}", JSON.toJSONString(storeList));


        List<String> companyInfoIds =
                storeList.stream()
                        .map(StoreVO::getCompanyInfoId)
                        .map(String::valueOf)
                        .collect(Collectors.toList());
        Map<String, LedgerAccountVO> businessIdToLedgerAccountVOMap =
                ledgerAccountQueryProvider
                        .findByBusinessIds(
                                QueryByBusinessIdsRequest.builder()
                                        .businessIds(companyInfoIds)
                                        .build())
                        .getContext()
                        .getBusinessIdToLedgerAccountVOMap();
        for (StoreVO store : storeList) {
            this.analyseSettlementForStore(
                    businessIdToLedgerAccountVOMap, store, request.getStoreType(), calendar);
        }

        //        settlementResponse.setSettlementAddResponses(responses);
        //        return settlementResponse;
    }

    private void analyseSettlementForStore(
            Map<String, LedgerAccountVO> businessIdToLedgerAccountVOMap,
            StoreVO store,
            StoreType storeType,
            Calendar calendar) {
        List<String> lakalaIds = new ArrayList<>();
        List<LakalaSettlementAddResponse> lakalaSettlementAddResponses = new ArrayList<>();
        List<SettlementViewVO> settlementVOList = new ArrayList<>();
        LedgerAccountVO ledgerAccountVO =
                businessIdToLedgerAccountVOMap.get(store.getCompanyInfoId().toString());

        try {
            // 根据结束时间获取账期的开始时间
//            Date startDate = this.getStartDate(store);
//            if (startDate != null) {
                List<SettlementBatchAddResponse> responseList =
                        settlementAnalyseProvider
                                .analyseStore(
                                        SettlementAnalyseRequest.builder()
                                                .store(store)
                                                .storeType(storeType)
                                                .endTime(DateUtil.format(calendar.getTime(), DateUtil.FMT_TIME_1))
                                                .ledgerAccountVO(ledgerAccountVO)
                                                .build())
                                .getContext();
                for (SettlementBatchAddResponse settlementBatchAddResponse : responseList) {
                    if (settlementBatchAddResponse
                            .getSettlementType()
                            .equals(SettlementBatchAddResponse.SettlementType.NORMAL)) {
                        SettlementVO settlementVO = settlementBatchAddResponse.getSettlementVO();
                        settlementProvider.delete(
                                SettlementDeleteRequest.builder()
                                        .storeId(settlementVO.getStoreId())
                                        .startTime(settlementVO.getStartTime())
                                        .endTime(settlementVO.getEndTime())
                                        .build());
                        SettlementAddResponse response = settlementProvider.add(
                                KsBeanUtil.convert(settlementVO, SettlementAddRequest.class)).getContext();
                        SettlementViewVO settlementViewVO = new SettlementViewVO();
                        KsBeanUtil.copyPropertiesThird(settlementVO, settlementViewVO);
                        settlementViewVO.setSettleId(response.getSettleId());
                        settlementViewVO.setSettleCode(
                                String.format("S%07d", settlementViewVO.getSettleId()));
                        settlementVOList.add(settlementViewVO);
                    } else if (settlementBatchAddResponse
                            .getSettlementType()
                            .equals(SettlementBatchAddResponse.SettlementType.LAKALA)) {
                        LakalaSettlementVO lakalaSettlementVO =
                                settlementBatchAddResponse.getLakalaSettlementVO();
                        settlementProvider.lakalaDelete(
                                SettlementDeleteRequest.builder()
                                        .storeId(lakalaSettlementVO.getStoreId())
                                        .startTime(lakalaSettlementVO.getStartTime())
                                        .endTime(lakalaSettlementVO.getEndTime())
                                        .supplierStoreId(lakalaSettlementVO.getSupplierStoreId())
                                        .build());
                        LakalaSettlementAddResponse lakalaSettlementAddResponse =
                                settlementProvider.lakalaAdd(KsBeanUtil.convert(lakalaSettlementVO,
                                LakalaSettlementAddRequest.class)).getContext();
                        if(lakalaSettlementVO.getLakalaLedgerStatus().equals(LakalaLedgerStatus.FAIL)){
                            lakalaIds.add(lakalaSettlementVO.getSettleUuid());
                        }
//                        KsBeanUtil.copyPropertiesThird(lakalaSettlementVO,lakalaSettlementAddResponse);
                        lakalaSettlementAddResponse.setStoreName(settlementBatchAddResponse.getStoreName());
                        lakalaSettlementAddResponses.add(
                                lakalaSettlementAddResponse);
                    }
//                }
                List<String> settlementIds =
                        lakalaSettlementAddResponses.stream()
                                .map(LakalaSettlementAddResponse::getSettleId)
                                .map(String::valueOf)
                                .collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(settlementIds)) {
                    EsSettlementInitRequest esSettlementInitRequest = new EsSettlementInitRequest();
                    esSettlementInitRequest.setIdList(settlementIds);
                    esSettlementProvider.initLakalaSettlement(esSettlementInitRequest);

                    // ============= 处理消息发送：平台、商家、供应商结算单生成提醒 START =============
                    // 平台
                    storeMessageBizService.handleForLakalaBossSettlementProduce(
                            BossMessageNode.LAKALA_SETTLEMENT_SETTLE.getCode(),
                            lakalaSettlementAddResponses);
                    // 商家、供应商
                    storeMessageBizService.handleForLakalaSupplierSettlementProduce(
                            lakalaSettlementAddResponses);
                    // ============= 处理消息发送：平台、商家、供应商结算单生成提醒 END =============
                }

                //同步结算单到es
                if(CollectionUtils.isNotEmpty(settlementVOList)){
                    esSettlementProvider.initSettlementList(EsSettlementListRequest.builder().lists(
                            settlementVOList.stream().map(settlementViewVo -> {
                                EsSettlementDTO dto =  KsBeanUtil.convert(settlementViewVo, EsSettlementDTO.class);
                                return dto;
                            }).collect(Collectors.toList())
                    ).build());

                    // ============= 处理平台的消息发送，供应商待结算单生成提醒 START =============
                    storeMessageBizService.handleForSettlementProduce(BossMessageNode.PROVIDER_SETTLEMENT_PRODUCE.getCode(), settlementVOList);
                    // ============= 处理平台的消息发送：供应商待结算单生成提醒 END =============

                }
            }
        } catch (Exception e) {
            log.error("结算数据异常", e);
        }
        if (CollectionUtils.isNotEmpty(lakalaIds)) {
            // 拉卡拉自动分账
            MqSendDelayDTO dto = new MqSendDelayDTO();
            dto.setTopic(ProducerTopic.LAKALA_SEPORCANCEL);
            SettlementRequest settlementRequest = SettlementRequest.builder()
                    .type(NumberUtils.INTEGER_ONE)
                    .uuids(lakalaIds)
                    .build();
            dto.setData(JSON.toJSONString(settlementRequest));
            mqSendProvider.send(dto);
//            lakalaShareProfitProvider.seporcancel(SettlementRequest.builder()
//                    .type(NumberUtils.INTEGER_ONE)
//                    .uuids(lakalaIds)
//                    .build());
        }
    }

    /**
     * 获取结算单最后一天日期
     *
     * <p>目前设定新的业务逻辑: 准备生成结算单: 获取开始时间的逻辑 1. 查询上一次结算单的终止时间 2. 如果没有, 则认为该店铺没有结算过, 则设为该店铺的签约起始日期
     *
     * @param store
     * @return
     */
    private Date getStartDate(StoreVO store) {
        ZoneId zoneId = ZoneId.systemDefault();
        SettlementLastResponse response =
                settlementQueryProvider
                        .getLastSettlementByStoreId(
                                SettlementLastByStoreIdRequest.builder()
                                        .storeId(store.getStoreId())
                                        .build())
                        .getContext();

        LakalaSettlementGetByIdResponse lakalaSettlementGetByIdResponse =
                settlementQueryProvider
                        .getLastLakalaSettlementByStoreId(
                                SettlementLastByStoreIdRequest.builder()
                                        .storeId(store.getStoreId())
                                        .build())
                        .getContext();
        Date date = null;
        if (Objects.nonNull(response) || Objects.nonNull(lakalaSettlementGetByIdResponse)) {
            LocalDate lakalaSettlement = null, settlement = null;
            if (Objects.nonNull(lakalaSettlementGetByIdResponse)) {
                String lakalaSettlementStr = lakalaSettlementGetByIdResponse.getEndTime();
                lakalaSettlement =
                        LocalDate.parse(
                                lakalaSettlementStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            if (Objects.nonNull(response)) {
                String str = response.getEndTime();
                settlement = LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            if (Objects.nonNull(lakalaSettlement) && Objects.nonNull(settlement)) {
                if (lakalaSettlement.isAfter(settlement)) {
                    LocalDateTime beginDateTime =
                            lakalaSettlement.atTime(LocalTime.MIN).plusDays(1);
                    date = Date.from(beginDateTime.atZone(zoneId).toInstant());
                } else {
                    LocalDateTime beginDateTime = settlement.atTime(LocalTime.MIN).plusDays(1);
                    date = Date.from(beginDateTime.atZone(zoneId).toInstant());
                }
            } else if (Objects.nonNull(lakalaSettlement)) {
                LocalDateTime beginDateTime = lakalaSettlement.atTime(LocalTime.MIN).plusDays(1);
                date = Date.from(beginDateTime.atZone(zoneId).toInstant());
            } else if (Objects.nonNull(settlement)) {
                LocalDateTime beginDateTime = settlement.atTime(LocalTime.MIN).plusDays(1);
                date = Date.from(beginDateTime.atZone(zoneId).toInstant());
            }
        } else {
            if (store.getContractStartDate() == null) {
                StoreByIdResponse storeByIdResponse =
                        storeQueryProvider
                                .getById(
                                        StoreByIdRequest.builder()
                                                .storeId(store.getStoreId())
                                                .build())
                                .getContext();
                if (store.getContractStartDate() == null) {
                    if (storeByIdResponse != null
                            && storeByIdResponse.getStoreVO().getContractStartDate() != null) {
                        store.setContractStartDate(
                                storeByIdResponse.getStoreVO().getContractStartDate());
                    }
                }
            }
            LocalDateTime localDateTime = store.getContractStartDate();
            ZonedDateTime zdt = localDateTime.atZone(zoneId);
            date = Date.from(zdt.toInstant());
        }
        return date;
    }
}
