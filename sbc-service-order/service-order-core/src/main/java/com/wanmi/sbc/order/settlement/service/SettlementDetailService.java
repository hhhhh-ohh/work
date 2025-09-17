package com.wanmi.sbc.order.settlement.service;

// import com.codingapi.txlcn.tc.annotation.TccTransaction;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.account.api.provider.finance.record.SettlementProvider;
import com.wanmi.sbc.account.api.provider.ledgerfunds.LedgerFundsProvider;
import com.wanmi.sbc.account.api.request.finance.record.LakalaSettlementBatchUpdateStatusRequest;
import com.wanmi.sbc.account.api.request.ledgerfunds.LedgerFundsAmountRequest;
import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.elastic.api.provider.settlement.EsSettlementProvider;
import com.wanmi.sbc.elastic.api.request.settlement.EsSettlementInitRequest;
import com.wanmi.sbc.empower.api.provider.pay.Lakala.LakalaShareProfitProvider;
import com.wanmi.sbc.empower.api.request.settlement.SepTranSidRequest;
import com.wanmi.sbc.empower.api.request.settlement.SeporcancelForSupplierReq;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaShareProfitQueryResponse;
import com.wanmi.sbc.order.api.request.distribution.DistributionLedgerRequest;
import com.wanmi.sbc.order.api.request.settlement.BatchUpdateSettlementDetailStatus;
import com.wanmi.sbc.order.api.request.settlement.LakalaSettlementDetailPageRequest;
import com.wanmi.sbc.order.api.request.settlement.SettlementDetailListBySettleUuidsRequest;
import com.wanmi.sbc.order.api.request.settlement.SettlementDetailPageRequest;
import com.wanmi.sbc.order.api.response.settlement.LakalaLedgerStatusResponse;
import com.wanmi.sbc.order.api.response.settlement.LakalaSettlementStatusResponse;
import com.wanmi.sbc.order.bean.dto.TradeDistributeItemCommissionDTO;
import com.wanmi.sbc.order.distribution.service.DistributionTaskTempService;
import com.wanmi.sbc.order.settlement.model.root.LakalaSettlementDetail;
import com.wanmi.sbc.order.settlement.model.root.SettlementDetail;
import com.wanmi.sbc.order.settlement.model.value.LakalaSettleTrade;
import com.wanmi.sbc.order.settlement.model.value.SettleTrade;
import com.wanmi.sbc.order.settlement.repository.LakalaSettlementDetailRepository;
import com.wanmi.sbc.order.settlement.repository.SettlementDetailRepository;
import com.wanmi.sbc.order.trade.model.entity.PayInfo;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.util.XssUtils;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by hht on 2017/12/7.
 */
@Service
public class SettlementDetailService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SettlementDetailRepository settlementDetailRepository;

    @Autowired
    private LakalaSettlementDetailRepository lakalaSettlementDetailRepository;

    @Autowired
    private SettlementDetailService settlementDetailService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LakalaShareProfitProvider lakalaShareProfitProvider;

    @Autowired
    private SettlementProvider settlementProvider;

    @Autowired
    private EsSettlementProvider esSettlementProvider;

    @Autowired
    private DistributionTaskTempService distributionTaskTempService;

    @Autowired
    private LedgerFundsProvider ledgerFundsProvider;


    /**
     * 新增文档
     * 专门用于数据新增服务,不允许数据修改的时候调用
     *
     * @param settlementDetail
     */
    public void addReturnOrder(SettlementDetail settlementDetail) {
        settlementDetailRepository.save(settlementDetail);
    }

    /**
     * 批量新增结算明细
     *
     * @param settlementDetailList 结算明细批量数据
     */
    @Transactional
    public void save(List<SettlementDetail> settlementDetailList) {
        logger.info("保存结算单明细列表");
        settlementDetailRepository.saveAll(settlementDetailList);
    }

//    @Transactional
    public List<LakalaSettlementDetail> lakalaSave(List<LakalaSettlementDetail> lakalaSettlementDetails) {
        logger.info("保存拉卡拉结算单明细列表");
        return lakalaSettlementDetailRepository.saveAll(lakalaSettlementDetails);
    }

    /*@Resource
    private MongoTccHelper mongoTccHelper;

    @SuppressWarnings("unused")
    public void confirmSave(SettlementDetail settlementDetail) {
        mongoTccHelper.confirm();
    }

    @SuppressWarnings("unused")
    public void cancelSave(SettlementDetail settlementDetail) {
        mongoTccHelper.cancel();
    }*/

    /**
     * 保存结算明细
     *
     * @param settlementDetail 结算明细
     */
    //@TccTransaction
//    @Transactional
    public void save(SettlementDetail settlementDetail) {
        logger.info("保存结算单明细");
        if (StringUtils.isEmpty(settlementDetail.getId())) {
            settlementDetail.setId(UUIDUtil.getUUID());
        }
        settlementDetailService.addReturnOrder(settlementDetail);
    }

    /**
     * 删除结算明细
     *
     * @param storeId   店铺Id
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
//    @Transactional
    public void deleteSettlement(Long storeId, String startDate, String endDate) {
        settlementDetailRepository.deleteByStartTimeAndEndTimeAndStoreId(startDate, endDate, storeId);
        logger.info("商家id{}删除{}-{}结算单明细" , storeId , startDate , endDate);
    }

//    @Transactional
    public void deleteLakalaSettlement(Long storeId, String startDate, String endDate) {
        lakalaSettlementDetailRepository.deleteByStartTimeAndEndTimeAndStoreId(startDate, endDate, storeId);
        logger.info("商家id{}删除{}-{}拉卡拉结算单明细" , storeId , startDate , endDate);
    }

    /**
     * 删除供应商
     * @param storeId
     * @param startDate
     * @param endDate
     * @param supplierStoreId
     */
//    @Transactional
    public void deleteProviderLakalaSettlement(Long storeId, String startDate, String endDate,Long supplierStoreId) {
        lakalaSettlementDetailRepository.deleteByStartTimeAndEndTimeAndStoreIdAndSupplierStoreId(startDate, endDate, storeId,supplierStoreId);
        logger.info("商家id{}删除{}-{}拉卡拉结算单明细" , storeId , startDate , endDate);
    }

    /**
     * @description 订单ID对应拉卡拉分账流水号
     * @author  edz
     * @date: 2022/10/12 11:06
     * @param storeId
     * @param startDate
     * @param endDate
     * @return java.util.Map<java.lang.String,java.lang.String>
     */
    public Map<String, String> getTidToSepTranSidMap(Long storeId, String startDate, String endDate) {
        List<LakalaSettlementDetail> lakalaSettlementDetails =
                lakalaSettlementDetailRepository.findByStartTimeAndEndTimeAndStoreId(startDate, endDate, storeId);
        logger.info("商家ID{}结算单明细总数{}", storeId, lakalaSettlementDetails.size());
        Map<String, String> tidToSepTranSidMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(lakalaSettlementDetails)){
            tidToSepTranSidMap.putAll(lakalaSettlementDetails.stream()
                            .filter(g -> Objects.nonNull(g.getSepTranSid()))
                    .collect(Collectors.toMap(k -> k.getSettleTrade().getTradeCode(),
                    LakalaSettlementDetail::getSepTranSid)));
        }
        return tidToSepTranSidMap;
    }

    /**
     * 根据订单id查询结算明细
     *
     * @param tradeId   订单id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 结算明细Optional
     */
    public Optional<SettlementDetail> getByTradeId(String tradeId, String startDate, String endDate) {
        return settlementDetailRepository.findBySettleTrade_TradeCodeAndStartTimeAndEndTime(tradeId, startDate,
                endDate);
    }

    /**
     * 查询结算明细
     *
     * @param settleUuid 结算单uuid
     * @return 结算明细列表
     */
    public List<SettlementDetail> getSettlementDetail(String settleUuid) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("settleUuid").is(settleUuid));
        Query query = new Query(criteria).with(Sort.by(new Sort.Order(Sort.Direction.DESC, "settleTrade" +
                ".tradeEndTime")));
        List<SettlementDetail> settlementDetails = mongoTemplate.find(query, SettlementDetail.class);

        //组装支付方式
        setPayWay(settlementDetails);
        return settlementDetails;
    }

    public LakalaLedgerStatusResponse getLakalaSettlementStatus(List<String> settleUuids) {
        // 操作按钮展示状态
        Map<String, LakalaLedgerStatus> resultMap = new HashMap<>();
        Map<String, LakalaLedgerStatus> statusMapmap = new HashMap<>();
        // settleUuid批量查询结算单明细
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("settleUuid").in(settleUuids));
        // uuid批量查询结算单详情
        List<LakalaSettlementDetail> settlementDetails =
                mongoTemplate.find(new Query(criteria), LakalaSettlementDetail.class);
        // 根据uuid分组
        Map<String, List<LakalaSettlementDetail>> uuidToLakalaSettlementDetailList =
                settlementDetails.stream()
                        .collect(Collectors.groupingBy(LakalaSettlementDetail::getSettleUuid));
        uuidToLakalaSettlementDetailList.forEach(
                (k, v) -> {
                    Map<String, LakalaLedgerStatus> sepTranSidToLakalaLedgerStatusMap = new HashMap<>();
                    // <拉卡拉分账流水号, 详单> 过滤出状态为分账中的详单
                    Map<String, LakalaSettlementDetail> sepTranSidsToLakalaSettlementDetailMap =
                            v.stream()
                                    .filter(
                                            g ->
                                                    LakalaLedgerStatus.PROCESSING.equals(
                                                            g.getLakalaLedgerStatus()) && Objects.nonNull(g.getSepTranSid()))
                                    .collect(
                                            Collectors.toMap(
                                                    LakalaSettlementDetail::getSepTranSid,
                                                    Function.identity()));
                    // 调用拉卡拉接口批量查询详单分账状态并更新
                    if (!sepTranSidsToLakalaSettlementDetailMap.isEmpty()){
                        List<LakalaShareProfitQueryResponse> lakalaShareProfitQueryResponses =
                                lakalaShareProfitProvider
                                        .seporcancelQuery(
                                                SepTranSidRequest.builder()
                                                        .supplierComPanyInfoId(
                                                                settlementDetails
                                                                        .get(0)
                                                                        .getSettleTrade()
                                                                        .getSupplierId()
                                                                        .toString())
                                                        .sepTranSids(
                                                                new ArrayList<>(
                                                                        sepTranSidsToLakalaSettlementDetailMap
                                                                                .keySet()))
                                                        .build())
                                        .getContext();
                        lakalaShareProfitQueryResponses.forEach(
                                lakalaShareProfitQueryResponse -> {
                                    if (!"2".equals(lakalaShareProfitQueryResponse.getStatus())) {
                                        LakalaLedgerStatus status = null;
                                        switch (lakalaShareProfitQueryResponse.getStatus()){
                                            case "0":
                                                status = LakalaLedgerStatus.NOT_SETTLED;
                                                break;
                                            case "1":
                                                status = LakalaLedgerStatus.SUCCESS;
                                                break;
                                            case "2":
                                                status = LakalaLedgerStatus.PROCESSING;
                                                break;
                                            case "3":
                                                status = LakalaLedgerStatus.FAIL;
                                                break;
                                            default:
                                                logger.error("getLakalaSettlementStatus-未匹配上分账状态");
                                        }
                                        logger.info("getLakalaSettlementStatus查询分账状态status:{}", status);
                                        sepTranSidToLakalaLedgerStatusMap.put(lakalaShareProfitQueryResponse.getSepTranSid(), status);

                                        sepTranSidsToLakalaSettlementDetailMap
                                                .get(lakalaShareProfitQueryResponse.getSepTranSid())
                                                .setLakalaLedgerStatus(status);
                                        statusMapmap.put(k, status);

                                    }
                                });
                    }

                    // 更新结算单结算状态
                    if (!sepTranSidToLakalaLedgerStatusMap.isEmpty()){
                        sepTranSidToLakalaLedgerStatusMap.forEach(this::updateLakalaSettlementDetailStatus);
                    }


                    // 一个uuid结算单下有没有除去分账成功的剩下的是否全是分账失败或是因金额不足无法分账的。是：前端展示线下分账按钮
                    List<LakalaSettlementDetail> lakalaSettlementDetailList =  v.stream()
                                    .filter(g -> !LakalaLedgerStatus.SUCCESS.equals(
                                                            g.getLakalaLedgerStatus())).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(lakalaSettlementDetailList)){
                        boolean flag = lakalaSettlementDetailList.stream().allMatch(
                                g -> LakalaLedgerStatus.INSUFFICIENT_AMOUNT.equals(g.getLakalaLedgerStatus()));
                        if (flag) {
                            resultMap.put(k, LakalaLedgerStatus.OFFLINE);
                        }
                    }
                });
        return LakalaLedgerStatusResponse.builder().operateMap(resultMap).statusMap(statusMapmap).build();
    }

    public List<LakalaSettlementDetail> getLakalaSettlementDetailByTid(List<String> tid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("settleTrade.tradeCode").in(tid));
        query.addCriteria(Criteria.where("sepTranSid").exists(true));
        return mongoTemplate.find(query, LakalaSettlementDetail.class);
    }

    public List<LakalaSettlementDetail> getLakalaSettlementDetail(List<String> ids) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("id").in(ids));
        Query query = new Query(criteria).with(Sort.by(new Sort.Order(Sort.Direction.DESC, "settleTrade" +
                ".tradeEndTime")));

        return mongoTemplate.find(query, LakalaSettlementDetail.class);
    }

    public LakalaSettlementDetail getLakalaSettlementDetail(String id) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("id").is(id));
        Query query = new Query(criteria).with(Sort.by(new Sort.Order(Sort.Direction.DESC, "settleTrade" +
                ".tradeEndTime")));

        return mongoTemplate.findOne(query, LakalaSettlementDetail.class);
    }


    /**
     * 查询结算明细
     *
     * @param request 结算单uuid
     * @return 结算明细列表
     */
    public Page<SettlementDetail> findSettlementDetailPage(SettlementDetailPageRequest request) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("settleUuid").is(request.getSettleUuid()));
        Query query = new Query(criteria).with(Sort.by(new Sort.Order(Sort.Direction.DESC, "settleTrade" +
                ".tradeEndTime")));
        int count = (int) mongoTemplate.count(query, SettlementDetail.class);
        query.with(request.getPageable());
        List<SettlementDetail> settlementDetails = mongoTemplate.find(query, SettlementDetail.class);

        //组装支付方式
        setPayWay(settlementDetails);

        return PageableExecutionUtils.getPage(settlementDetails, request.getPageable(), () -> count);
    }

    public Page<LakalaSettlementDetail> findLakalaSettlementDetailPage(LakalaSettlementDetailPageRequest request) {
        Criteria criteria = new Criteria();
        List<Criteria> criteriaList = new ArrayList<>();
        criteriaList.add(Criteria.where("settleUuid").is(request.getSettleUuid()));
        if (StringUtils.isNotBlank(request.getProviderName())){
            criteriaList.add(XssUtils.regex("settleGoodList.providerName", request.getProviderName()));
        }

        if (StringUtils.isNotBlank(request.getDistributorName())){
            criteriaList.add(XssUtils.regex("settleGoodList.distributorName", request.getDistributorName()));
        }

        if (StringUtils.isNotBlank(request.getTradeCode())){
            criteriaList.add(XssUtils.regex("settleTrade.tradeCode", request.getTradeCode()));
        }

        if (request.getStatus() != null){
            List<LakalaLedgerStatus> statusList = new ArrayList<>();
            switch (request.getStatus()) {
                case SUCCESS:
                    statusList.add(LakalaLedgerStatus.SUCCESS);
                    break;
                case FAIL:
                    statusList.add(LakalaLedgerStatus.FAIL);
                    statusList.add(LakalaLedgerStatus.INSUFFICIENT_AMOUNT);
                    break;
                case PROCESSING:
                    statusList.add(LakalaLedgerStatus.PROCESSING);
                    break;
                case OFFLINE:
                    statusList.add(LakalaLedgerStatus.OFFLINE);
                    break;
                default:
            }
            if (CollectionUtils.isNotEmpty(statusList)) {
                criteriaList.add(Criteria.where("lakalaLedgerStatus").in(statusList));
            }
        }

        criteria.andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]));
        Query query = new Query(criteria).with(Sort.by(new Sort.Order(Sort.Direction.DESC, "settleTrade" +
                ".tradeEndTime")));
        int count = (int) mongoTemplate.count(query, LakalaSettlementDetail.class);
        query.with(request.getPageable());
        List<LakalaSettlementDetail> settlementDetails = mongoTemplate.find(query, LakalaSettlementDetail.class);
        return PageableExecutionUtils.getPage(settlementDetails, request.getPageable(), () -> count);
    }

    /**
     * 组装支付方式
     * @param settlementDetails
     */
    private void setPayWay(List<SettlementDetail> settlementDetails){
        //组装支付方式
        if(CollectionUtils.isNotEmpty(settlementDetails)){
            settlementDetails.stream().map(detail -> {
                SettleTrade settleTrade = detail.getSettleTrade();
                if(Objects.nonNull(settleTrade) && Objects.nonNull(settleTrade.getTradeCode())){
                    Criteria ca = new Criteria();
                    ca.andOperator(Criteria.where("id").is(detail.getSettleTrade().getTradeCode()));
                    Query q = new Query(ca);
                    List<Trade> tradeList = mongoTemplate.find((q), Trade.class);
                    if(CollectionUtils.isNotEmpty(tradeList)){
                        settleTrade.setPoints(tradeList.get(0).getTradePrice().getPoints());

                        PayWay payWay = tradeList.get(0).getPayWay();
                        settleTrade.setPayWay(payWay);

                        PayInfo payInfo = tradeList.get(0).getPayInfo();
                        if(Objects.nonNull(payInfo) && payInfo.getPayTypeId().equals(NumberUtils.INTEGER_ONE.toString())){
                            settleTrade.setPayWay(PayWay.CASH);
                        }
                    }
                }

                return detail;
            }).collect(Collectors.toList());
        }
    }
    /**
     * 导出结算明细
     *
     * @param settlement 结单单
     * @param outputStream 输出流
     */
    // 已移入BFF层直接调用
    /*  public void exportSettlementDetail(Settlement settlement, OutputStream outputStream) {
            String fileKey = "settlement/" + settlement.getStoreId() + "/" + settlement.getSettleUuid() + ".xls";
            OutputStream excelOutputStream = aliYunFileService.downloadFileByKey(fileKey);
            if (excelOutputStream == null) {
                List<SettlementDetail> detailList = this.getSettlementDetail(settlement.getSettleUuid());
                List<SettlementDetailView> viewList = SettlementDetailView.renderSettlementDetailForView(detailList, true);
                ExcelHelper<SettlementDetailView> excelHelper = new ExcelHelper<>();
                excelHelper.addSheet("结算明细", new SpanColumn[]{
                        new SpanColumn("序号", "index", null),
                        new SpanColumn("订单创建时间", "tradeCreateTime", null),
                        new SpanColumn("订单编号", "tradeCode", null),
                        new SpanColumn("订单类型", "tradeType", null),
                        new SpanColumn("收款方", "gatherType", null),
                        new SpanColumn("商品名称/名称/规格", "goodsViewList", "goodsName"),
                        new SpanColumn("所属类目", "goodsViewList", "cateName"),
                        new SpanColumn("商品单价", "goodsViewList", "goodsPrice"),
                        new SpanColumn("数量", "goodsViewList", "num"),
                        new SpanColumn("满减优惠", "goodsViewList", "reductionPrice"),
                        new SpanColumn("满折优惠", "goodsViewList", "discountPrice"),
                        new SpanColumn("订单改价差额", "goodsViewList", "specialPrice"),
                        new SpanColumn("商品实付金额", "goodsViewList", "splitPayPrice"),
                        new SpanColumn("类目扣率", "goodsViewList", "cateRate"),
                        new SpanColumn("平台佣金", "goodsViewList", "platformPriceString"),
                        new SpanColumn("退货数量", "goodsViewList", "returnNum"),
                        new SpanColumn("退货返还佣金", "goodsViewList", "backPlatformPrice"),
                        new SpanColumn("应退金额", "goodsViewList", "shouldReturnPrice"),
                        new SpanColumn("实退金额", "returnPrice", null),
                        new SpanColumn("运费", "deliveryPrice", null),
    //                    new SpanColumn("退款状态", "goodsViewList", "returnStatus"),
                        new SpanColumn("店铺应收金额", "storePrice", null)
                }, viewList, "goodsViewList");

                //向response写入流
                excelHelper.write(outputStream);

                //如果excel文件内容为空不上传至oss，后期可以设置超过一定的长度再上传oss
                if (viewList.size() > 1000) {
                    //向oss写入流
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    excelHelper.write(byteArrayOutputStream);
                    aliYunFileService.uploadFile(byteArrayOutputStream, fileKey);
                }
            } else {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) excelOutputStream)
                        .toByteArray());
                try {
                    int ch;
                    while ((ch = inputStream.read()) != -1) {
                        outputStream.write(ch);
                    }
                } catch (IOException e) {
                    logger.error("excel write error", e);
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (excelOutputStream != null) {
                            excelOutputStream.close();
                        }
                    } catch (IOException e) {
                        logger.error("excel write error", e);
                    }
                }
            }

        }*/

    /**
     * @description 批量查询拉卡拉结算单明细
     * @author edz
     * @date: 2022/7/20 14:43
     * @param request
     * @return
     *     org.springframework.data.domain.Page<com.wanmi.sbc.order.settlement.model.root.LakalaSettlementDetail>
     */
    public Page<LakalaSettlementDetail> getLakalaSettlementDetailsPageByUuid(
            SettlementDetailListBySettleUuidsRequest request) {
        if (CollectionUtils.isNotEmpty(request.getSettleUuids()) && CollectionUtils.isNotEmpty(request.getIds())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        Query query = new Query();
        if (CollectionUtils.isNotEmpty(request.getSettleUuids())) {
            query.addCriteria(Criteria.where("settleUuid").in(request.getSettleUuids()));
        }

        if (CollectionUtils.isNotEmpty(request.getIds())) {
            query.addCriteria(Criteria.where("id").in(request.getIds()));
        }

        if (CollectionUtils.isNotEmpty(request.getLakalaLedgerStatus())) {
            query.addCriteria(Criteria.where("lakalaLedgerStatus").in(request.getLakalaLedgerStatus()));
        }
        query.with(Sort.by(new Sort.Order(Sort.Direction.DESC, "settleTrade" + ".tradeEndTime")));
        int count = (int) mongoTemplate.count(query, LakalaSettlementDetail.class);
        query.with(request.getPageable());
        List<LakalaSettlementDetail> settlementDetails =
                mongoTemplate.find(query, LakalaSettlementDetail.class);
        return PageableExecutionUtils.getPage(
                settlementDetails, request.getPageable(), () -> count);
    }

    public void offlineByUuid(String uuid){
        Query query = new Query();
        query.addCriteria(Criteria.where("settleUuid").is(uuid));
        query.addCriteria(Criteria.where("lakalaLedgerStatus").is(LakalaLedgerStatus.INSUFFICIENT_AMOUNT));
        List<LakalaSettlementDetail> settlementDetails =
                mongoTemplate.find(query, LakalaSettlementDetail.class);
        List<String> ids = settlementDetails.stream().map(LakalaSettlementDetail::getId).collect(Collectors.toList());
        this.batchUpdateSettlementDetailStatus(BatchUpdateSettlementDetailStatus.builder().Offline(ids).build());

        // 更新相关联的结算单状态
        this.updateLakalaSettleStatus(Collections.singletonList(uuid));

        EsSettlementInitRequest esSettlementInitRequest = new EsSettlementInitRequest();
        esSettlementInitRequest.setUuidList(Collections.singletonList(uuid));
        esSettlementProvider.syncLakalaSettlementStutus(esSettlementInitRequest);

    }

    @Transactional
    public List<String> batchUpdateSettlementDetailStatus(BatchUpdateSettlementDetailStatus request) {
        // 记录供应商结算单状态发生变更的uuid
        Set<String> settlementUuids = new HashSet<>();

        // 线下分账
        if (CollectionUtils.isNotEmpty(request.getOffline())) {
            // 线下分账拉卡拉账户钱全分给商家
            request.getOffline().forEach(k ->
                    lakalaShareProfitProvider.seporcancelForSupplier(SeporcancelForSupplierReq.builder().id(k).build()));

            Query insufficientAmountQuery = new Query();
            List<ObjectId> objectIds = new ArrayList<>();
            request.getOffline().forEach(g -> objectIds.add(new ObjectId(g)));
            insufficientAmountQuery.addCriteria(Criteria.where("id").in(objectIds));
            List<LakalaSettlementDetail> offlineAmountSettlementDetails =
                    mongoTemplate.find(insufficientAmountQuery, LakalaSettlementDetail.class);
            mergeSettlementDetails(offlineAmountSettlementDetails);
            settlementUuids.addAll(offlineAmountSettlementDetails.stream().map(LakalaSettlementDetail::getSettleUuid).collect(Collectors.toList()));
            offlineAmountSettlementDetails.forEach(
                    lakalaSettlementDetail -> {
                        lakalaSettlementDetail.setLakalaLedgerStatus(LakalaLedgerStatus.OFFLINE);
                        mongoTemplate.save(lakalaSettlementDetail);
                    });
        }

        // 分账成功
        if (CollectionUtils.isNotEmpty(request.getSuccess())) {
            Query successQuery = new Query();
            List<ObjectId> objectIds = new ArrayList<>();
            request.getSuccess().forEach(g -> objectIds.add(new ObjectId(g)));
            successQuery.addCriteria(Criteria.where("id").in(objectIds));
            List<LakalaSettlementDetail> settlementDetails =
                    mongoTemplate.find(successQuery, LakalaSettlementDetail.class);
            mergeSettlementDetails(settlementDetails);
            settlementUuids.addAll(settlementDetails.stream().map(LakalaSettlementDetail::getSettleUuid).collect(Collectors.toList()));
            settlementDetails.forEach(
                    lakalaSettlementDetail -> {
                        lakalaSettlementDetail.setLakalaLedgerStatus(LakalaLedgerStatus.SUCCESS);
                        mongoTemplate.save(lakalaSettlementDetail);
                    });
        }

        // 分账失败
        if (CollectionUtils.isNotEmpty(request.getFail())) {
            Query failQuery = new Query();
            List<ObjectId> objectIds = new ArrayList<>();
            request.getFail().forEach(g -> objectIds.add(new ObjectId(g)));
            failQuery.addCriteria(Criteria.where("id").in(objectIds));
            List<LakalaSettlementDetail> failSettlementDetails =
                    mongoTemplate.find(failQuery, LakalaSettlementDetail.class);
            mergeSettlementDetails(failSettlementDetails);
            settlementUuids.addAll(failSettlementDetails.stream().map(LakalaSettlementDetail::getSettleUuid).collect(Collectors.toList()));
            failSettlementDetails.forEach(
                    lakalaSettlementDetail -> {
                        lakalaSettlementDetail.setLakalaLedgerStatus(LakalaLedgerStatus.FAIL);
                        mongoTemplate.save(lakalaSettlementDetail);
                    });
        }

        // 分账中
        if (CollectionUtils.isNotEmpty(request.getProcessing())) {
            Query processingQuery = new Query();
            List<ObjectId> objectIds = new ArrayList<>();
            request.getProcessing().forEach(g -> objectIds.add(new ObjectId(g)));
            processingQuery.addCriteria(Criteria.where("id").in(objectIds));
            List<LakalaSettlementDetail> processingSettlementDetails =
                    mongoTemplate.find(processingQuery, LakalaSettlementDetail.class);
            mergeSettlementDetails(processingSettlementDetails);
            settlementUuids.addAll(processingSettlementDetails.stream().map(LakalaSettlementDetail::getSettleUuid).collect(Collectors.toList()));
            processingSettlementDetails.forEach(
                    lakalaSettlementDetail -> {
                        lakalaSettlementDetail.setLakalaLedgerStatus(LakalaLedgerStatus.PROCESSING);
                        mongoTemplate.save(lakalaSettlementDetail);
                    });
        }

        // 分账余额不足
        if (CollectionUtils.isNotEmpty(request.getInsufficientAmount())) {
            Query insufficientAmountQuery = new Query();
            List<ObjectId> objectIds = new ArrayList<>();
            request.getInsufficientAmount().forEach(g -> objectIds.add(new ObjectId(g)));
            insufficientAmountQuery.addCriteria(
                    Criteria.where("id").in(objectIds));
            List<LakalaSettlementDetail> insufficientAmountSettlementDetails =
                    mongoTemplate.find(insufficientAmountQuery, LakalaSettlementDetail.class);
            mergeSettlementDetails(insufficientAmountSettlementDetails);
            settlementUuids.addAll(insufficientAmountSettlementDetails.stream().map(LakalaSettlementDetail::getSettleUuid).collect(Collectors.toList()));
            insufficientAmountSettlementDetails.forEach(
                    lakalaSettlementDetail -> {
                        lakalaSettlementDetail.setLakalaLedgerStatus(
                                LakalaLedgerStatus.INSUFFICIENT_AMOUNT);
                        mongoTemplate.save(lakalaSettlementDetail);
                    });
        }

        if (request.getLakalaSettlementDetailIdTosepTranSidMap() != null){
            request.getLakalaSettlementDetailIdTosepTranSidMap().forEach((k, v) -> {
                Query query = new Query();
                query.addCriteria(Criteria.where("id").is(new ObjectId(k)));
                Update update = new Update();
                mongoTemplate.upsert(query, update.set("sepTranSid", v), LakalaSettlementDetail.class);
            });
        }
        return new ArrayList<>(settlementUuids);
    }

    /**
     * @description 分账流水更新分账状态
     * @author  edz
     * @date: 2022/8/3 17:28
     * @param sepTranSid
     * @param status
     */
    public void updateLakalaSettlementDetailStatus(String sepTranSid, LakalaLedgerStatus status){
        Query query = new Query();
        query.addCriteria(Criteria.where("sepTranSid").is(sepTranSid));
        LakalaSettlementDetail lakalaSettlementDetail = mongoTemplate.findOne(query, LakalaSettlementDetail.class);
        List<LakalaSettlementDetail> LakalaSettlementDetails = new ArrayList<>();
        LakalaSettlementDetails.add(lakalaSettlementDetail);
        mergeSettlementDetails(LakalaSettlementDetails);
        if (CollectionUtils.isNotEmpty(LakalaSettlementDetails)){
            LakalaSettlementDetails.forEach(g -> {
                g.setLakalaLedgerStatus(status);
                mongoTemplate.save(g);
            });

        } else {
            logger.info("根据结算单流水查询结果空。入参sepTranSid：{}，status:{}", sepTranSid, status);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
        List<String> uuids =
                LakalaSettlementDetails.stream().map(LakalaSettlementDetail::getSettleUuid).collect(Collectors.toList());
        this.updateLakalaSettleStatus(uuids);

        EsSettlementInitRequest esSettlementInitRequest = new EsSettlementInitRequest();
        esSettlementInitRequest.setUuidList(uuids);
        esSettlementProvider.syncLakalaSettlementStutus(esSettlementInitRequest);
    }

    private void mergeSettlementDetails(List<LakalaSettlementDetail> settlementDetails) {
        Map<String, Long> tIdToProviderIdMap =
                settlementDetails.stream()
                        .filter(
                                lakalaSettlementDetail ->
                                        Objects.nonNull(lakalaSettlementDetail.getProviderId()))
                        .collect(
                                Collectors.toMap(
                                        lakalaSettlementDetail ->
                                                lakalaSettlementDetail
                                                        .getSettleTrade()
                                                        .getTradeCode(),
                                        LakalaSettlementDetail::getProviderId));
        tIdToProviderIdMap.forEach(
                (k, v) -> {
                    Query query = new Query();
                    query.addCriteria(Criteria.where("storeId").is(v));
                    query.addCriteria(Criteria.where("settleTrade.parentId").is(k));
                    query.addCriteria(Criteria.where("storeType").is(StoreType.PROVIDER));
                    List<LakalaSettlementDetail> providerSettlementDetails =
                            mongoTemplate.find(query, LakalaSettlementDetail.class);
                    settlementDetails.addAll(providerSettlementDetails);
                });
    }

    public LakalaSettlementStatusResponse updateLakalaSettleStatus(List<String> settleUuids){
        if (CollectionUtils.isEmpty(settleUuids)) return new LakalaSettlementStatusResponse();
        Query query = new Query();
        query.addCriteria(Criteria.where("settleUuid").in(settleUuids));
        List<LakalaSettlementDetail> lakalaSettlementDetailList = mongoTemplate.find(query,
                LakalaSettlementDetail.class);
        Map<String, List<LakalaSettlementDetail>> settleUuisToLakalaSettlementLIst =
                lakalaSettlementDetailList.stream().collect(Collectors.groupingBy(LakalaSettlementDetail::getSettleUuid));
        List<String> successList = new ArrayList<>();
        List<String> processingList = new ArrayList<>();
        List<String> filList = new ArrayList<>();
        List<String> insufficientAmountList = new ArrayList<>();
        List<String> offlineList = new ArrayList<>();
        List<String> partialSuccessList = new ArrayList<>();
        settleUuisToLakalaSettlementLIst.forEach(
                (k, v) -> {
                    // 分账总数
                    long total = v.size();
                    // 成功总数
                    long successCount =
                            v.stream()
                                    .filter(
                                            g ->
                                                    LakalaLedgerStatus.SUCCESS.equals(
                                                            g.getLakalaLedgerStatus()))
                                    .count();
                    // 分账中的总数
                    long processingCount =
                            v.stream()
                                    .filter(
                                            g ->
                                                    LakalaLedgerStatus.PROCESSING.equals(
                                                            g.getLakalaLedgerStatus()))
                                    .count();
                    // 分账失败的总数
                    long filCount =
                            v.stream()
                                    .filter(
                                            g ->
                                                    LakalaLedgerStatus.FAIL.equals(
                                                            g.getLakalaLedgerStatus()))
                                    .count();
                    // 分账余额不足的总数
                    long insufficientAmountCount =
                            v.stream()
                                    .filter(
                                            g ->
                                                    LakalaLedgerStatus.INSUFFICIENT_AMOUNT.equals(
                                                            g.getLakalaLedgerStatus()))
                                    .count();
                    // 线下分账总数
                    long offlineCount =
                            v.stream()
                                    .filter(
                                            g ->
                                                    LakalaLedgerStatus.OFFLINE.equals(
                                                            g.getLakalaLedgerStatus()))
                                    .count();
                    if (total == successCount || total == successCount + offlineCount || total == offlineCount) {
                        successList.add(k);
                    } else if (total == processingCount || processingCount > 0) {
                        processingList.add(k);
                    } else if (total == filCount) {
                        filList.add(k);
                    } else if (total == insufficientAmountCount) {
                        insufficientAmountList.add(k);
                    } else if (successCount > 0) {
                        partialSuccessList.add(k);
                    }
                });
        Map<LakalaLedgerStatus, List<String>> LakalaLedgerStatusToSettlementUuidMap = new HashMap<>();
        LakalaLedgerStatusToSettlementUuidMap.put(LakalaLedgerStatus.SUCCESS, successList);
        LakalaLedgerStatusToSettlementUuidMap.put(LakalaLedgerStatus.PARTIAL_SUCCESS, partialSuccessList);
        LakalaLedgerStatusToSettlementUuidMap.put(LakalaLedgerStatus.FAIL, filList);
        LakalaLedgerStatusToSettlementUuidMap.put(LakalaLedgerStatus.INSUFFICIENT_AMOUNT, insufficientAmountList);
        LakalaLedgerStatusToSettlementUuidMap.put(LakalaLedgerStatus.OFFLINE, offlineList);
        LakalaLedgerStatusToSettlementUuidMap.put(LakalaLedgerStatus.PROCESSING, processingList);
        settlementProvider.LakalabatchModifyStatus(
                LakalaSettlementBatchUpdateStatusRequest.builder()
                        .LakalaLedgerStatusToSettlementUuidMap(
                                LakalaLedgerStatusToSettlementUuidMap)
                        .build());

        return LakalaSettlementStatusResponse.builder()
                .filList(filList)
                .insufficientAmountList(insufficientAmountList)
                .offlineList(offlineList)
                .partialSuccessList(partialSuccessList)
                .processingList(processingList)
                .successList(successList)
                .build();

    }


    @GlobalTransactional
    public void updateForDistribution(String sepTranSid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sepTranSid").is(sepTranSid));
        LakalaSettlementDetail lakalaSettlementDetail = mongoTemplate.findOne(query, LakalaSettlementDetail.class);
        if (lakalaSettlementDetail == null) {
            logger.error("入参sepTranSid:{}，结算明细未查询到", sepTranSid);
            return;
        }
        logger.info("更新分销员提现金额，入参sepTranSid：{}，订单号：{}", sepTranSid, lakalaSettlementDetail.getSettleTrade().getTradeCode());
        LakalaSettleTrade settleTrade = lakalaSettlementDetail.getSettleTrade();
        DistributionLedgerRequest distributionLedgerRequest =
                DistributionLedgerRequest.builder()
                        .commission(settleTrade.getCommission())
                        .commissions(
                                KsBeanUtil.convertList(
                                        settleTrade.getCommissions(),
                                        TradeDistributeItemCommissionDTO
                                                .class))
                        .orderId(settleTrade.getTradeCode())
                        .build();
        List<LedgerFundsAmountRequest> ledgerFundsAmountRequests = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(settleTrade.getCommissions())) {
            settleTrade.getCommissions().forEach(
                    tradeCommissionVO -> {
                        ledgerFundsAmountRequests.add(
                                LedgerFundsAmountRequest.builder()
                                        .amount(tradeCommissionVO.getCommission())
                                        .customerId(tradeCommissionVO.getCustomerId())
                                        .build());
                    });
        }

        lakalaSettlementDetail
                .getSettleGoodList()
                .forEach(
                        lakalaSettleGoodVO -> {
                            if (StringUtils.isNotBlank(
                                    lakalaSettleGoodVO.getDistributorCustomerId())) {
                                ledgerFundsAmountRequests.add(
                                        LedgerFundsAmountRequest.builder()
                                                .amount(lakalaSettleGoodVO.getCommission())
                                                .customerId(
                                                        lakalaSettleGoodVO
                                                                .getDistributorCustomerId())
                                                .build());
                            }
                        });
        if (CollectionUtils.isNotEmpty(ledgerFundsAmountRequests)) {
            ledgerFundsAmountRequests.forEach(g -> ledgerFundsProvider.updateWithdrawnAmount(g));
        }
        distributionTaskTempService.updateForLedger(
                settleTrade.getTradeCode(),
                JSON.toJSONString(distributionLedgerRequest),
                LocalDateTime.now().plusDays(Constants.ONE)
        );

    }
}