package com.wanmi.sbc.order.returnorder.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.provider.finance.record.AccountRecordProvider;
import com.wanmi.sbc.account.api.request.credit.CreditOrderQueryRequest;
import com.wanmi.sbc.account.api.request.finance.record.AccountRecordAddRequest;
import com.wanmi.sbc.account.api.request.finance.record.AccountRecordDeleteByReturnOrderCodeAndTypeRequest;
import com.wanmi.sbc.account.api.response.credit.CreditRepayPageResponse;
import com.wanmi.sbc.account.bean.enums.*;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MessageMQRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.enums.node.ReturnOrderProcessType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.common.validation.Assert;
import com.wanmi.sbc.customer.api.provider.account.CustomerAccountProvider;
import com.wanmi.sbc.customer.api.provider.account.CustomerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.provider.storereturnaddress.StoreReturnAddressQueryProvider;
import com.wanmi.sbc.customer.api.request.account.CustomerAccountAddRequest;
import com.wanmi.sbc.customer.api.request.account.CustomerAccountByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.account.CustomerAccountOptionalRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailListByConditionRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.request.storereturnaddress.StoreReturnAddressByIdRequest;
import com.wanmi.sbc.customer.api.response.account.CustomerAccountAddResponse;
import com.wanmi.sbc.customer.api.response.account.CustomerAccountByCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.account.CustomerAccountOptionalResponse;
import com.wanmi.sbc.customer.api.response.storereturnaddress.StoreReturnAddressByIdResponse;
import com.wanmi.sbc.customer.bean.dto.CustomerAccountAddOrModifyDTO;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.CustomerAccountVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.StoreReturnAddressVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByIdRequest;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemResponse;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.groupongoodsinfo.GrouponGoodsInfoSaveProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsByIdRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleRecordRequest;
import com.wanmi.sbc.goods.api.request.groupongoodsinfo.GrouponGoodsInfoReturnModifyRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoBatchPlusStockRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoPlusStockDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsPlusStockDTO;
import com.wanmi.sbc.goods.bean.vo.BookingSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.api.provider.bargain.BargainSaveProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsalegoods.BookingSaleGoodsProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsalegoods.BookingSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.provider.communitysku.CommunitySkuQueryProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivitySaveProvider;
import com.wanmi.sbc.marketing.api.provider.grouponrecord.GrouponRecordProvider;
import com.wanmi.sbc.marketing.api.request.bargaingoods.UpdateStockRequest;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingSaleGoodsCountRequest;
import com.wanmi.sbc.marketing.api.request.bookingsalegoods.BookingSaleGoodsListRequest;
import com.wanmi.sbc.marketing.api.request.communitysku.UpdateSalesRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardTransRequest;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityModifyStatisticsNumByIdRequest;
import com.wanmi.sbc.marketing.api.request.grouponrecord.GrouponRecordDecrBuyNumRequest;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.GiftCardTransBusinessVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingPreferentialGoodsDetailVO;
import com.wanmi.sbc.marketing.bean.vo.TradeMarketingVO;
import com.wanmi.sbc.order.api.constant.RefundReasonConstants;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.ProviderTradeQueryProvider;
import com.wanmi.sbc.order.api.request.distribution.ReturnOrderSendMQRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderRefundRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderByIdRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderOnlineRefundByTidRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.response.trade.TradeGetByIdResponse;
import com.wanmi.sbc.order.bean.dto.*;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.order.common.GoodsStockService;
import com.wanmi.sbc.order.common.OperationLogMq;
import com.wanmi.sbc.order.common.SystemPointsConfigService;
import com.wanmi.sbc.order.customer.service.CustomerCommonService;
import com.wanmi.sbc.order.groupon.service.GrouponOrderService;
import com.wanmi.sbc.order.message.StoreMessageBizService;
import com.wanmi.sbc.order.mq.OrderProducerService;
import com.wanmi.sbc.order.mqconsumer.OrderMqConsumerService;
import com.wanmi.sbc.order.orderperformance.service.OrderPerformanceService;
import com.wanmi.sbc.order.payorder.model.root.PayOrder;
import com.wanmi.sbc.order.payorder.response.PayOrderResponse;
import com.wanmi.sbc.order.payorder.service.PayOrderService;
import com.wanmi.sbc.order.paytraderecord.model.root.PayTradeRecord;
import com.wanmi.sbc.order.paytraderecord.service.PayTradeRecordService;
import com.wanmi.sbc.order.refund.model.root.RefundBill;
import com.wanmi.sbc.order.refund.model.root.RefundOrder;
import com.wanmi.sbc.order.refund.repository.RefundOrderRepository;
import com.wanmi.sbc.order.refund.service.RefundBillService;
import com.wanmi.sbc.order.refund.service.RefundOrderService;
import com.wanmi.sbc.order.returnorder.fsm.ReturnFSMService;
import com.wanmi.sbc.order.returnorder.fsm.event.ReturnEvent;
import com.wanmi.sbc.order.returnorder.fsm.params.ReturnStateRequest;
import com.wanmi.sbc.order.returnorder.model.entity.ReturnAddress;
import com.wanmi.sbc.order.returnorder.model.entity.ReturnItem;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrderTransfer;
import com.wanmi.sbc.order.returnorder.model.value.*;
import com.wanmi.sbc.order.returnorder.mq.ReturnOrderProducerService;
import com.wanmi.sbc.order.returnorder.repository.ReturnOrderRepository;
import com.wanmi.sbc.order.returnorder.repository.ReturnOrderTransferRepository;
import com.wanmi.sbc.order.returnorder.request.ReturnQueryRequest;
import com.wanmi.sbc.order.sellplatform.SellPlatformReturnTradeService;
import com.wanmi.sbc.order.thirdplatformtrade.service.ThirdPlatformTradeService;
import com.wanmi.sbc.order.trade.model.entity.CreditPayInfo;
import com.wanmi.sbc.order.trade.model.entity.TradeDeliver;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Buyer;
import com.wanmi.sbc.order.trade.model.entity.value.Company;
import com.wanmi.sbc.order.trade.model.entity.value.ProviderFreight;
import com.wanmi.sbc.order.trade.model.entity.value.TradePrice;
import com.wanmi.sbc.order.trade.model.root.*;
import com.wanmi.sbc.order.trade.service.ProviderTradeService;
import com.wanmi.sbc.order.trade.service.StockService;
import com.wanmi.sbc.order.trade.service.TradeCacheService;
import com.wanmi.sbc.order.trade.service.TradeService;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by jinwei on 20/4/2017.
 */
@Slf4j
@Service
public class ReturnOrderService {

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private ReturnOrderRepository returnOrderRepository;

    @Autowired
    private ReturnOrderTransferRepository returnOrderTransferRepository;

    @Autowired
    private ReturnOrderTransferService returnOrderTransferService;

    @Autowired
    private StoreQueryProvider storeQueryProvider;


    @Autowired
    private TradeService tradeService;

    @Autowired
    private ReturnFSMService returnFSMService;

    @Autowired
    private RefundOrderService refundOrderService;

    @Autowired
    private RefundBillService refundBillService;

    @Autowired
    private CustomerAccountQueryProvider customerAccountQueryProvider;

    @Autowired
    private CustomerAccountProvider customerAccountProvider;

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private AccountRecordProvider accountRecordProvider;

    @Autowired
    private RefundOrderRepository refundOrderRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CustomerCommonService customerCommonService;

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private OperationLogMq operationLogMq;

    /**
     * 注入退单状态变更生产者service
     */
    @Autowired
    private ReturnOrderProducerService returnOrderProducerService;

    @Autowired
    private ReturnOrderService returnOrderService;

    @Autowired
    private GrouponGoodsInfoSaveProvider grouponGoodsInfoProvider;

    @Autowired
    private GrouponActivitySaveProvider activityProvider;

    @Autowired
    private GrouponRecordProvider recordProvider;

    @Autowired
    private GrouponOrderService grouponOrderService;

    @Autowired
    private OrderProducerService orderProducerService;

    @Autowired
    private GoodsStockService goodsStockService;

    @Autowired
    private TradeCacheService tradeCacheService;

    @Autowired
    private BookingSaleGoodsQueryProvider bookingSaleGoodsQueryProvider;

    @Autowired
    private BookingSaleGoodsProvider bookingSaleGoodsProvider;

    @Autowired
    private ThirdPlatformTradeService thirdPlatformTradeService;

    @Autowired
    private ThirdPlatformReturnOrderService thirdPlatformReturnOrderService;

    @Autowired
    private ProviderTradeQueryProvider providerTradeQueryProvider;

    @Autowired
    private ProviderTradeService providerTradeService;

    @Autowired
    private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired
    private StoreReturnAddressQueryProvider returnAddressQueryProvider;

    @Autowired
    private PayTradeRecordService payTradeRecordService;

    @Autowired
    private OrderPerformanceService orderPerformanceService;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;
    @Autowired
    private ReturnTradeIncision returnTradeIncision;

    @Autowired
    private StockService stockService;

    @Autowired private SellPlatformReturnTradeService sellPlatformReturnTradeService;

    @Autowired private StoreMessageBizService storeMessageBizService;

    @Autowired
    private OrderMqConsumerService orderMqConsumerService;

    @Autowired private BargainSaveProvider bargainSaveProvider;

    @Autowired
    private CreditRepayQueryProvider creditRepayQueryProvider;

    @Autowired
    private UserGiftCardProvider userGiftCardProvider;

    @Autowired
    private CommunitySkuQueryProvider communitySkuQueryProvider;

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    /**
     * 新增文档
     * 专门用于数据新增服务,不允许数据修改的时候调用
     *
     * @param returnOrder
     */
    @Transactional
    public ReturnOrder addReturnOrder(ReturnOrder returnOrder) {
        Company company = returnOrder.getCompany();
        Long storeId = company.getStoreId();
        StoreVO store = storeQueryProvider.getById(StoreByIdRequest.builder()
                .storeId(storeId)
                .build()).getContext().getStoreVO();
        company.setStoreType(store.getStoreType());
        returnOrder.setCompany(company);
        return returnOrderRepository.save(returnOrder);
    }


    /**
     * 修改文档
     * 专门用于数据修改服务,不允许数据新增的时候调用
     *
     * @param returnOrder
     */
    public void updateReturnOrder(ReturnOrder returnOrder) {
        returnOrderRepository.save(returnOrder);
    }


    /**
     * @param returnOrder
     * @param trade
     * @param returnOrders
     */
    public void splitReturnTrade(ReturnOrder returnOrder, Trade trade, List<ReturnOrder> returnOrders) {
        log.info("splitReturnTrade start");
        //订单详情集合
        List<TradeItem> tradeItemList = trade.getTradeItems();
        List<ReturnItem> returnItemList = returnOrder.getReturnItems();
        // 查询订单商品,赠品所属供应商id集合
        List<Long> returnProviderIds = returnItemList.stream()
                .filter(returnItem -> Objects.nonNull(returnItem.getProviderId()))
                .map(ReturnItem::getProviderId).distinct().collect(Collectors.toList());

        //赠品信息由getAndSetReturnGifts()获取
        List<ReturnItem> returnGifts = returnOrder.getReturnGifts();
        returnProviderIds.addAll(
                returnGifts.stream().filter(item -> Objects.nonNull(item.getProviderId()))
                        .map(ReturnItem::getProviderId)
                        .collect(Collectors.toList()));

        List<ReturnItem> returnPreferentialList = returnOrder.getReturnPreferential();
        returnProviderIds.addAll(
                returnPreferentialList.stream().filter(item -> Objects.nonNull(item.getProviderId()))
                        .map(ReturnItem::getProviderId)
                        .collect(Collectors.toList()));

        returnProviderIds = returnProviderIds.stream().distinct().collect(Collectors.toList());

        //供应商商品，赠品退单
        if (CollectionUtils.isNotEmpty(returnProviderIds)) {
            Map<Long, StoreVO> storeVOMap = tradeCacheService.queryStoreList(returnProviderIds).stream().collect(Collectors.toMap(StoreVO::getStoreId, Function.identity()));
            //根据供应商id拆单
            for (Long providerId : returnProviderIds) {
                //对应的子单
                ProviderTrade providerTrade = providerTradeService.getProviderTradeByIdAndPid(trade.getId(), providerId);
                ReturnOrder providerReturnOrder = KsBeanUtil.convert(returnOrder, ReturnOrder.class);
                if (Objects.nonNull(providerTrade)) {
                    providerReturnOrder.setPtid(providerTrade.getId());
                }
                //退款时赠品拆分
                this.fullReturnGifts(providerReturnOrder, providerId);
                this.fullReturnPreferentialS(providerReturnOrder, providerId);
                this.buildReturnOrder(providerReturnOrder, trade, StoreType.PROVIDER, providerId, null, providerTrade);
                StoreVO storeVO = storeVOMap.get(providerId);
                //判断是否第三方渠道订单,拆分第三方渠道店铺子订单
                if (storeVO != null && Objects.nonNull(storeVO.getCompanySourceType()) && !CompanySourceType.SBC_MALL.equals(storeVO.getCompanySourceType())) {
                    for(ThirdPlatformType thirdPlatformType : ThirdPlatformType.values()){
                        returnOrders.addAll(thirdPlatformReturnOrderService.splitReturnOrder(providerReturnOrder, thirdPlatformType));
                        returnOrders.forEach(o -> {
                            calcReturnPrice(o,
                                    trade.getTradeItems().stream().filter(tradeItem -> providerId.equals(tradeItem.getProviderId())).collect(Collectors.toList()),thirdPlatformType);
                        });
                    }
                } else {
                    returnOrders.add(providerReturnOrder);
                }
            }
        }

        //商家商品,赠品退单
        List<ReturnItem> returnStoreItemList =
                returnItemList.stream().filter(returnItem -> Objects.isNull(returnItem.getProviderId())).collect(Collectors.toList());
        List<ReturnItem> giftItemList =
                returnGifts.stream().filter(item -> Objects.isNull(item.getProviderId())).collect(Collectors.toList());
        List<ReturnItem> preferentialList =
                returnPreferentialList.stream().filter(item -> Objects.isNull(item.getProviderId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(returnStoreItemList) || CollectionUtils.isNotEmpty(giftItemList) || CollectionUtils.isNotEmpty(preferentialList)) {
            //退款时赠品拆分
            this.fullReturnGifts(returnOrder, null);
            this.fullReturnPreferentialS(returnOrder, null);
            //没有供应商，且该订单没有被拆分子单
            ProviderTrade providerTrade = providerTradeService.getProviderTradeByIdAndPid(trade.getId(), trade.getSupplier().getStoreId());
            if (CollectionUtils.isEmpty(returnProviderIds) && Objects.isNull(providerTrade)) {
                returnOrders.add(returnOrder);
            } else {
                List<TradeItem> storeItemList =
                        tradeItemList.stream().filter(tradeItem -> Objects.isNull(tradeItem.getProviderId())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(storeItemList) || CollectionUtils.isNotEmpty(giftItemList) || CollectionUtils.isNotEmpty(preferentialList)) {
                    //对应的子单
                    returnOrder.setPtid(providerTrade.getId());
                    this.buildReturnOrder(returnOrder, trade, StoreType.SUPPLIER, null, storeItemList, providerTrade);
                    returnOrders.add(returnOrder);
                }
            }
        }

        log.info("splitReturnTrade end");

    }

    /**
     * 退款时，赠品也需要退单拆分
     *
     * @param returnOrder
     */
    private void fullReturnGifts(ReturnOrder returnOrder, Long providerId) {
        //退货单赠品的供应商/商家拆分
        List<ReturnItem> newGiftItem;
        if (Objects.nonNull(providerId)) {
            newGiftItem = returnOrder.getReturnGifts().stream()
                    .filter(item -> providerId.equals(item.getProviderId()))
                    .collect(Collectors.toList());
        } else {
            newGiftItem = returnOrder.getReturnGifts().stream()
                    .filter(item -> item.getProviderId() == null)
                    .collect(Collectors.toList());
        }
        returnOrder.setReturnGifts(newGiftItem);
    }

    private void fullReturnPreferentialS(ReturnOrder returnOrder, Long providerId) {
        //退货单赠品的供应商/商家拆分
        List<ReturnItem> items;
        if (Objects.nonNull(providerId)) {
            items = returnOrder.getReturnPreferential().stream()
                    .filter(item -> providerId.equals(item.getProviderId()))
                    .collect(Collectors.toList());
        } else {
            items = returnOrder.getReturnPreferential().stream()
                    .filter(item -> item.getProviderId() == null)
                    .collect(Collectors.toList());
        }
        returnOrder.setReturnPreferential(items);
    }

    private void buildReturnOrder(ReturnOrder returnOrder, Trade trade, StoreType storeType, Long providerId, List<TradeItem> storeItemList, ProviderTrade providerTrade) {

        if (StoreType.PROVIDER.equals(storeType)) {
            StoreVO storeVO =
                    storeQueryProvider.getById(StoreByIdRequest.builder().storeId(providerId).build()).getContext().getStoreVO();
            if (storeVO != null) {
                String companyCode = storeVO.getCompanyInfo().getCompanyCode();
                Long companyInfoId = storeVO.getCompanyInfo().getCompanyInfoId();
                String supplierName = storeVO.getCompanyInfo().getSupplierName();

                returnOrder.setProviderId(String.valueOf(providerId));
                returnOrder.setProviderCode(companyCode);
                returnOrder.setProviderName(supplierName);
                returnOrder.setProviderCompanyInfoId(companyInfoId);
                // 筛选当前供应商的订单商品信息
                List<TradeItem> providerTradeItems =
                        trade.getTradeItems().stream().filter(tradeItem -> providerId.equals(tradeItem.getProviderId())).collect(Collectors.toList());
                List<ReturnItem> returnItemDTOList = new ArrayList<>();
                //组装退单商品详情
                BigDecimal providerTotalPrice = BigDecimal.ZERO;
                BigDecimal price = BigDecimal.ZERO;
                Long points = 0L;
                BigDecimal giftCardPrice = BigDecimal.ZERO;
                for (TradeItem tradeItemVO : providerTradeItems) {
                    for (ReturnItem returnItemDTO : returnOrder.getReturnItems()) {
                        if (tradeItemVO.getSkuId().equals(returnItemDTO.getSkuId())) {
                            returnItemDTO.setSupplyPrice(tradeItemVO.getSupplyPrice());
                            BigDecimal supplyPrice = Objects.nonNull(tradeItemVO.getSupplyPrice()) ?
                                    tradeItemVO.getSupplyPrice() : BigDecimal.ZERO;
                            returnItemDTO.setProviderPrice(supplyPrice.multiply(new BigDecimal(returnItemDTO.getNum())));
                            providerTotalPrice = providerTotalPrice.add(returnItemDTO.getProviderPrice());
                            price = price.add(returnItemDTO.getSplitPrice());
                            if (returnItemDTO.getSplitPoint() != null) {
                                points += returnItemDTO.getSplitPoint();
                            }
                            if(CollectionUtils.isNotEmpty(returnItemDTO.getGiftCardItemList())) {
                                giftCardPrice = giftCardPrice.add(returnItemDTO.getGiftCardItemList().stream().map(item->Objects.isNull(item.getReturnPrice())?BigDecimal.ZERO : item.getReturnPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                            }
                            returnItemDTOList.add(returnItemDTO);
                        }
                    }
                }

                //填充赠品供应商信息
                if (CollectionUtils.isNotEmpty(trade.getGifts()) && CollectionUtils.isNotEmpty(returnOrder.getReturnGifts())) {
                    // 筛选当前供应商的订单商品信息
                    Map<String, BigDecimal> providerTradeGifts = trade.getGifts().stream()
                            .filter(tradeItem -> providerId.equals(tradeItem.getProviderId()) && tradeItem.getSupplyPrice() != null)
                            .collect(Collectors.toMap(TradeItem::getSkuId, TradeItem::getSupplyPrice, (a, b) -> a));
                    //组装退单详情
                    for (ReturnItem returnItemDTO : returnOrder.getReturnGifts()) {
                        returnItemDTO.setSupplyPrice(providerTradeGifts.getOrDefault(returnItemDTO.getSkuId(), BigDecimal.ZERO));
                        returnItemDTO.setProviderPrice(returnItemDTO.getSupplyPrice().multiply(new BigDecimal(returnItemDTO.getNum())));
                        providerTotalPrice = providerTotalPrice.add(returnItemDTO.getProviderPrice());
                    }
                }

                if (CollectionUtils.isNotEmpty(trade.getPreferential()) && CollectionUtils.isNotEmpty(returnOrder.getReturnPreferential())) {
                    // 筛选当前供应商的订单商品信息
                    Map<Long, Map<String, BigDecimal>> providerTradeGifts = trade.getPreferential().stream()
                            .filter(tradeItem -> providerId.equals(tradeItem.getProviderId()) && tradeItem.getSupplyPrice() != null)
                            .collect(Collectors.groupingBy(g -> g.getMarketingIds().get(0),
                                    Collectors.toMap(TradeItem::getSkuId, TradeItem::getSupplyPrice)));
                    //组装退单详情
                    for (ReturnItem returnItemDTO : returnOrder.getReturnPreferential()) {
                        returnItemDTO.setSupplyPrice(BigDecimal.ZERO);
                        Map<String, BigDecimal> bigDecimalMap = providerTradeGifts.get(returnItemDTO.getMarketingId());
                        if (MapUtils.isNotEmpty(bigDecimalMap)){
                            returnItemDTO.setSupplyPrice(bigDecimalMap.getOrDefault(returnItemDTO.getSkuId(), BigDecimal.ZERO));
                        }
                        returnItemDTO.setProviderPrice(returnItemDTO.getSupplyPrice().multiply(new BigDecimal(returnItemDTO.getNum())));
                        providerTotalPrice = providerTotalPrice.add(returnItemDTO.getProviderPrice());
                        price = price.add(returnItemDTO.getSplitPrice());
                        if (returnItemDTO.getSplitPoint() != null) {
                            points += returnItemDTO.getSplitPoint();
                        }
                        if(CollectionUtils.isNotEmpty(returnItemDTO.getGiftCardItemList())) {
                            giftCardPrice = giftCardPrice.add(returnItemDTO.getGiftCardItemList().stream().map(ite->Objects.isNull(ite.getReturnPrice())?BigDecimal.ZERO:ite.getReturnPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                        }
                    }
                }
                returnOrder.setReturnItems(returnItemDTOList);
                returnOrder.getReturnPrice().setProviderTotalPrice(providerTotalPrice);
                returnOrder.getReturnPrice().setApplyPrice(price);
                returnOrder.getReturnPrice().setTotalPrice(price);
                returnOrder.getReturnPoints().setApplyPoints(points);
                returnOrder.getReturnPrice().setGiftCardPrice(giftCardPrice);
            }
        } else {
            if (CollectionUtils.isNotEmpty(storeItemList)) {
                List<ReturnItem> returnItemDTOList = new ArrayList<>();
                BigDecimal price = BigDecimal.ZERO;
                Long points = 0L;
                BigDecimal giftCardPrice = BigDecimal.ZERO;
                //组装退单详情
                for (TradeItem tradeItemVO : storeItemList) {
                    for (ReturnItem returnItemDTO : returnOrder.getReturnItems()) {
                        if (tradeItemVO.getSkuId().equals(returnItemDTO.getSkuId())) {
                            BigDecimal splitPrice = Objects.nonNull(tradeItemVO.getSplitPrice()) ?
                                    tradeItemVO.getSplitPrice() : BigDecimal.ZERO;
                            price = price.add(returnItemDTO.getSplitPrice());
                            if (returnItemDTO.getSplitPoint() != null) {
                                points += returnItemDTO.getSplitPoint();
                            }
                            if(CollectionUtils.isNotEmpty(returnItemDTO.getGiftCardItemList())) {
                                giftCardPrice = giftCardPrice.add(returnItemDTO.getGiftCardItemList().stream().map(item->Objects.isNull(item.getReturnPrice())? BigDecimal.ZERO : item.getReturnPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                            }
                            returnItemDTOList.add(returnItemDTO);
                        }
                    }
                }
                for (ReturnItem returnItemDTO : returnOrder.getReturnPreferential()) {
                    price = price.add(returnItemDTO.getSplitPrice());
                    if (returnItemDTO.getSplitPoint() != null) {
                        points += returnItemDTO.getSplitPoint();
                    }
                    if(CollectionUtils.isNotEmpty(returnItemDTO.getGiftCardItemList())) {
                        giftCardPrice = giftCardPrice.add(returnItemDTO.getGiftCardItemList().stream().map(item->Objects.isNull(item.getReturnPrice())?BigDecimal.ZERO:item.getReturnPrice()).reduce(BigDecimal.ZERO, BigDecimal::add));
                    }
                }
                returnOrder.setReturnItems(returnItemDTOList);
                returnOrder.getReturnPrice().setApplyPrice(price);
                returnOrder.getReturnPrice().setTotalPrice(price);
                returnOrder.getReturnPoints().setApplyPoints(points);
                returnOrder.getReturnPrice().setGiftCardPrice(giftCardPrice);
            } else {
                // 订单的主商品不存在商家商品则清空数据
                returnOrder.setReturnItems(new ArrayList<>());
                returnOrder.getReturnPrice().setApplyPrice(BigDecimal.ZERO);
                returnOrder.getReturnPrice().setTotalPrice(BigDecimal.ZERO);
                returnOrder.getReturnPoints().setApplyPoints(NumberUtils.LONG_ZERO);
                returnOrder.getReturnPrice().setGiftCardPrice(BigDecimal.ZERO);
            }

        }
    }

    private void calcReturnPrice(ReturnOrder returnOrder, List<TradeItem> providerTradeItems,ThirdPlatformType thirdPlatformType) {
        Map<String, TradeItem> itemMap = providerTradeItems.stream()
                .collect(Collectors.toMap(TradeItem::getSkuId, Function.identity()));
        //组装退单详情
        BigDecimal providerTotalPrice = BigDecimal.ZERO;
        BigDecimal price = BigDecimal.ZERO;
        Long points = 0L;
        for (ReturnItem returnItemDTO : returnOrder.getReturnItems()) {
            providerTotalPrice = providerTotalPrice.add(returnItemDTO.getProviderPrice());
            if (returnItemDTO.getSplitPoint() != null) {
                points += returnItemDTO.getSplitPoint();
            }
            TradeItem item = itemMap.get(returnItemDTO.getSkuId());
            if (item != null) {
                BigDecimal splitPrice = Objects.nonNull(item.getSplitPrice()) ? item.getSplitPrice() : BigDecimal.ZERO;
                BigDecimal goodsPrice = splitPrice.divide(BigDecimal.valueOf(item.getNum()), 10, RoundingMode.DOWN);
                price = price.add(goodsPrice.multiply(BigDecimal.valueOf(returnItemDTO.getNum())));
            }
        }
        returnOrder.getReturnPrice().setProviderTotalPrice(providerTotalPrice);
        if(!ThirdPlatformType.VOP.equals(thirdPlatformType)){
            returnOrder.getReturnPrice().setApplyPrice(price);
            returnOrder.getReturnPrice().setTotalPrice(price);
        }
        returnOrder.getReturnPoints().setApplyPoints(points);
    }


    /**
     * 退单创建
     *
     * @param returnOrder
     * @param operator
     */
    @GlobalTransactional
    @Transactional
    public String create(ReturnOrder returnOrder, Operator operator) {
        if (returnOrder.getDescription().length() > Constants.NUM_100) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //查询订单信息
        Trade trade = this.queryCanReturnItemNumByTid(returnOrder.getTid());
        // 类型
        returnTradeIncision.setCrossReturnOrderType(trade, returnOrder);
        //查询该订单所有退单
        List<ReturnOrder> returnOrderList = returnOrderRepository.findByTid(trade.getId());
        //筛选出已完成的退单列表
        List<ReturnOrder> completedReturnOrders = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(returnOrderList)) {

            completedReturnOrders = returnOrderList.stream().filter(allOrder -> allOrder
                    .getReturnFlowState() == ReturnFlowState.COMPLETED)
                    .collect(Collectors.toList());
        }


        //是否仅退款
        boolean isRefund = returnOrder.getReturnType() == ReturnType.REFUND;
        //退单总金额
        ReturnPrice returnPrice = returnOrder.getReturnPrice();

        //计算该订单下所有已完成退单的总金额
        BigDecimal allOldPrice = new BigDecimal(0);
        for (ReturnOrder order : completedReturnOrders) {
            BigDecimal p = order.getReturnPrice().getApplyStatus() ? order.getReturnPrice().getApplyPrice() : order
                    .getReturnPrice().getTotalPrice();
            allOldPrice = allOldPrice.add(p);
        }
        //是否boss创建并且申请特价状态
        boolean isSpecial = returnPrice.getApplyStatus() && operator.getPlatform() == Platform.BOSS;
        if (!isSpecial) {
            //退单商品价格校验
//            verifyPrice(trade.skuItemMap(), returnOrder.getReturnItems());
        }

        List<ReturnItem> returnItems = returnOrder.getReturnItems();
        List<ReturnItem> returns = returnOrder.getReturnPreferential();
        List<String> skuIds = returnItems.stream().map(ReturnItem::getSkuId).collect(Collectors.toList());
        skuIds.addAll(returns.stream().map(ReturnItem::getSkuId).collect(Collectors.toList()));
        List<TradeDistributeItemVO> tradeDistributeItemVos = returnOrder.getDistributeItems();
        BigDecimal price = null;
        Long points = 0L;

        //填充商品信息
        List<GoodsInfoVO> goodsInfoVOList = goodsInfoQueryProvider.listByIds(GoodsInfoListByIdsRequest.builder()
                .goodsInfoIds(skuIds).build()).getContext().getGoodsInfos();
        if (CollectionUtils.isNotEmpty(goodsInfoVOList)) {
            Map<String, GoodsInfoVO> goodsInfoVOMap = goodsInfoVOList.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
            returnOrder.getReturnItems().forEach(item -> {
                GoodsInfoVO vo = goodsInfoVOMap.get(item.getSkuId());
                if (Objects.nonNull(vo)) {
                    item.setGoodsId(vo.getGoodsId());
                    item.setSpuId(vo.getGoodsId());
                    item.setSpuName(vo.getGoodsInfoName());
                    item.setCateTopId(vo.getCateTopId());
                    item.setCateId(vo.getCateId());
                    item.setBrandId(vo.getBrandId());
                }
            });
            // 计算并设置需要退的加价购商品
            if (CollectionUtils.isNotEmpty(trade.getPreferential())) {
                getAndSetReturnPreferentials(returnOrder, trade, returnOrderList);
            }
            returnOrder.getReturnPreferential().forEach(item -> {
                GoodsInfoVO vo = goodsInfoVOMap.get(item.getSkuId());
                if (Objects.nonNull(vo)) {
                    item.setGoodsId(vo.getGoodsId());
                    item.setSpuId(vo.getGoodsId());
                    item.setSpuName(vo.getGoodsInfoName());
                    item.setCateTopId(vo.getCateTopId());
                    item.setCateId(vo.getCateId());
                    item.setBrandId(vo.getBrandId());
                }
            });
        }
        OrderTag orderTag = trade.getOrderTag();
        boolean buyCycleFlag = Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag();
        //------------------start-------------------
        returnOrder.getReturnItems().forEach(info -> {
            Optional<TradeItem> tradeItemOptional = trade.getTradeItems().stream().filter(tradeItem -> info
                    .getSkuId().equals(tradeItem.getSkuId())).findFirst();
            if (!tradeItemOptional.isPresent()) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            TradeItem tradeItem = tradeItemOptional.get();
            info.setOrderSplitPrice(tradeItem.getSplitPrice());
            if (tradeItem.getNum() == 0) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            BigDecimal unitPrice = tradeItem.getSplitPrice().divide(new BigDecimal(buyCycleFlag ?
                            tradeItem.getNum() * tradeItem.getBuyCycleNum() :tradeItem.getNum()), 2,
                    RoundingMode.DOWN);
            if (buyCycleFlag) {
                List<TradeDeliver> tradeDelivers = trade.getTradeDelivers();
                if (tradeDelivers.size() == Constants.ZERO) {
                    info.setSplitPrice(tradeItem.getSplitPrice());
                } else {
                    info.setSplitPrice(unitPrice.multiply(new BigDecimal(
                            ((long) info.getNum() * tradeItem.getBuyCycleNum()) - tradeItem.getDeliveredNum())));
                }
            } else {
                //unitPrice = returnCrossBorderIncision.calcUnitPrice(tradeItem,unitPrice);
                if (tradeItem.getCanReturnNum().intValue() - info.getNum().intValue() > 0) {
                    // 该商品未退完
                    if (unitPrice.compareTo(new BigDecimal("0.01")) < 0) {
                        info.setSplitPrice(BigDecimal.ZERO);
                    } else {
                        info.setPrice(unitPrice);
                        info.setSplitPrice(unitPrice.multiply(new BigDecimal(info.getNum())));
                    }
                } else {
                    //该商品已退完
                    if (unitPrice.compareTo(new BigDecimal("0.01")) < 0) {
                        info.setSplitPrice(tradeItem.getSplitPrice());
                    } else {
                        info.setSplitPrice(tradeItem.getSplitPrice().subtract(unitPrice.multiply(new BigDecimal
                                (tradeItem.getNum() - info.getNum()))));
                        info.setPrice(info.getSplitPrice().divide(new BigDecimal(info.getNum()), 2,
                                RoundingMode.DOWN));
                    }
                }
            }
        });
        //-------------------------end--------------------------
        price = isSpecial ? returnPrice.getApplyPrice() :
                returnOrder.getReturnItems().stream().map(ReturnItem::getSplitPrice)
                        .reduce(BigDecimal::add).orElse(BigDecimal.ZERO)
                        .add(returnOrder.getReturnPreferential().stream().map(ReturnItem::getSplitPrice)
                                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
        // 计算积分
        if (Objects.nonNull(trade.getTradePrice().getPoints())) {
            points = getPoints(returnOrder, trade, returnOrderList);
            points = points + getPreferentialPoints(returnOrder, trade, returnOrderList);
        }

        //分销商品数据接口赋值开始
        tradeDistributeItemVos = tradeDistributeItemVos.stream().filter(item -> skuIds
                .contains(item.getGoodsInfoId())).collect(Collectors.toList());
        if (Objects.nonNull(tradeDistributeItemVos) && tradeDistributeItemVos.size() > 0) {
            List<TradeDistributeItemVO> distributeItems = tradeDistributeItemVos.stream().map
                    (tradeDistributeItemVo -> {
                        Optional<TradeItem> tradeItemOptional =
                                trade.getTradeItems().stream().filter(tradeItem -> tradeDistributeItemVo
                                        .getGoodsInfoId().equals(tradeItem.getSkuId())).findFirst();
                        TradeItem tradeItem = tradeItemOptional.get();
                        Optional<ReturnItem> returnItemOpt = returnItems.stream().filter(item -> item.getSkuId()
                                .equals(tradeDistributeItemVo.getGoodsInfoId())).findFirst();
                        BigDecimal unitPrice = tradeItem.getSplitPrice().divide(new BigDecimal
                                        (tradeDistributeItemVo.getNum())
                                , 2,
                                RoundingMode.DOWN);
                        if (returnItemOpt.isPresent()) {
                            ReturnItem returnItem = returnItemOpt.get();
                            if (tradeItem.getCanReturnNum().intValue() - returnItem.getNum().intValue() > 0) {
                                //部分退款
                                if (unitPrice.compareTo(new BigDecimal("0.01")) < 0) {
                                    tradeDistributeItemVo.setActualPaidPrice(BigDecimal.ZERO);
                                } else {
                                    tradeDistributeItemVo.setActualPaidPrice(unitPrice.multiply(new BigDecimal
                                            (returnItem.getNum())));
                                }
                            } else {
                                //该商品已退完
                                if (unitPrice.compareTo(new BigDecimal("0.01")) < 0) {
                                    tradeDistributeItemVo.setActualPaidPrice(BigDecimal.ZERO);
                                } else {
                                    tradeDistributeItemVo.setActualPaidPrice(tradeItem.getSplitPrice().subtract(unitPrice.multiply(new BigDecimal
                                            (tradeItem.getNum() - returnItem.getNum()))));
                                }
                            }
                            tradeDistributeItemVo.setNum(returnItem.getNum().longValue());
                        }
                        return tradeDistributeItemVo;

                    }).collect(Collectors.toList());
            returnOrder.setDistributeItems(distributeItems);
        }
        //分销商品的数据处理结束
        returnOrder.getReturnPrice().setTotalPrice(price);
        returnOrder.getReturnPrice().setApplyPrice(price);
        if (returnOrder.getReturnPoints() == null) {
            returnOrder.setReturnPoints(ReturnPoints.builder().applyPoints(points).build());
        }

        PayOrder payOrder = payOrderService.findPayOrderByOrderCode(returnOrder.getTid()).orElse(null);
        if (Objects.isNull(payOrder)) {
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020032);
        }
        BigDecimal payOrderPrice = payOrder.getPayOrderPrice();
        if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods()
                && trade.getBookingType() == BookingType.EARNEST_MONEY && StringUtils.isNotEmpty(trade.getTailOrderNo())) {
            payOrderPrice = payOrderPrice.add(payOrderService.findPayOrderByOrderCode(trade.getTailOrderNo())
                    .orElse(new PayOrder()).getPayOrderPrice());
        }
        if (operator.getPlatform() == Platform.BOSS && PayType.fromValue(Integer.parseInt(trade.getPayInfo()
                .getPayTypeId())) == PayType.ONLINE && payOrderPrice.compareTo(price.add(allOldPrice)) < 0) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050036);
        }

        if (isRefund) {
            //创建退款单，会过滤已完成部分退款的的商品
            createRefund(returnOrder, operator, trade);
        } else {
            createReturn(returnOrder, operator, trade);
        }
        // 计算并设置需要退的赠品
        if (CollectionUtils.isNotEmpty(trade.getGifts())) {
            getAndSetReturnGifts(returnOrder, trade, returnOrderList);
        }

        //处理礼品卡 周期购
        if (buyCycleFlag) {
            this.giftCardCycleProcess(trade, returnOrder);
        } else {
            this.giftCardProcess(trade, returnOrder, returnOrderList);
        }

        List<ReturnOrder> returnOrders = new ArrayList<>();
        //退单拆分
        splitReturnTrade(returnOrder, trade, returnOrders);
        String returnOrderId = StringUtils.EMPTY;

        for (ReturnOrder newReturnOrder : returnOrders) {

            newReturnOrder.setBuyer(trade.getBuyer());
            newReturnOrder.setConsignee(trade.getConsignee());
            newReturnOrder.setCreateTime(LocalDateTime.now());
            newReturnOrder.setPayType(PayType.valueOf(trade.getPayInfo().getPayTypeName()));
            newReturnOrder.setPlatform(operator.getPlatform());
            //授信支付 且 已经还款完成 改为线下退款
//            if(null != trade
//                    && null != trade.getCreditPayInfo()
//                    && trade.getCreditPayInfo().getHasRepaid()){
//                newReturnOrder.setPayType(PayType.OFFLINE);
//            }

            String rid = generatorService.generate("R");
            newReturnOrder.setId(rid);
            boolean flag = false;

            //记录日志
            newReturnOrder.appendReturnEventLog(
                    new ReturnEventLog(operator, "创建退单", "创建退单", "", LocalDateTime.now())
            );

            newReturnOrder.setReturnFlowState(ReturnFlowState.INIT);


            //处理代销平台退单
            newReturnOrder = sellPlatformReturnTradeService.addReturnOrder(newReturnOrder, trade);

            //保存退单
            returnOrderService.addReturnOrder(newReturnOrder);

            returnOrderId = rid;

            this.operationLogMq.convertAndSend(operator, "创建退单", "创建退单");

            Boolean auditFlag = Boolean.TRUE;
            //渠道退单，不可以自动审核
            if (Objects.nonNull(newReturnOrder.getThirdPlatformType())) {
                auditFlag = Boolean.FALSE;
            }

            if (auditFlag && (operator.getPlatform() == Platform.BOSS || operator.getPlatform() == Platform.SUPPLIER)) {
                audit(rid, operator, null);
            }

            ReturnOrderSendMQRequest sendMQRequest = ReturnOrderSendMQRequest.builder()
                    .addFlag(true)
                    .customerId(trade.getBuyer().getId())
                    .orderId(trade.getId())
                    .returnId(rid)
                    .build();
            returnOrderProducerService.returnOrderFlow(sendMQRequest);

            //售后单提交成功发送MQ消息
            if (CollectionUtils.isNotEmpty(newReturnOrder.getReturnItems())
                    || CollectionUtils.isNotEmpty(newReturnOrder.getReturnGifts())
                    || CollectionUtils.isNotEmpty(newReturnOrder.getReturnPreferential())) {
                List<String> params;
                String pic;
                if (CollectionUtils.isNotEmpty(newReturnOrder.getReturnItems())) {
                    params = Lists.newArrayList(newReturnOrder.getReturnItems().get(0).getSkuName());
                    pic = newReturnOrder.getReturnItems().get(0).getPic();
                } else if (CollectionUtils.isNotEmpty(newReturnOrder.getReturnPreferential())) {
                    params = Lists.newArrayList(newReturnOrder.getReturnPreferential().get(0).getSkuName());
                    pic = newReturnOrder.getReturnPreferential().get(0).getPic();
                } else {
                    params = Lists.newArrayList(newReturnOrder.getReturnGifts().get(0).getSkuName());
                    pic = newReturnOrder.getReturnGifts().get(0).getPic();
                }
                this.sendNoticeMessage(NodeType.RETURN_ORDER_PROGRESS_RATE,
                        ReturnOrderProcessType.AFTER_SALE_ORDER_COMMIT_SUCCESS,
                        params,
                        newReturnOrder.getId(),
                        newReturnOrder.getBuyer().getId(),
                        pic,
                        newReturnOrder.getBuyer().getAccount()
                );
                // ============= 处理商家/供应商的消息发送：待审核退单提醒 START =============
                storeMessageBizService.handleForReturnOrderAudit(newReturnOrder);
                // ============= 处理商家/供应商的消息发送：待审核退单提醒 END =============

                // ============= 处理商家/供应商的消息发送：库存预警提醒 START =============
                storeMessageBizService.handleForReturnOrderStock(newReturnOrder);
                // ============= 处理商家/供应商的消息发送：库存预警提醒 END =============
            }

        }

        return returnOrderId;
    }

    /**
     * @description   退单积分计算
     * @author  wur
     * @date: 2023/1/13 16:29
     * @param returnOrder       当前所有退单
     * @param trade             原订单信息
     * @param returnOrderList
     * @return
     **/
    public Long getPoints(ReturnOrder returnOrder, Trade trade, List<ReturnOrder> returnOrderList) {
        List<ReturnFlowState> returnFlowStates = Arrays.asList(ReturnFlowState.REJECT_REFUND,
                ReturnFlowState.REJECT_RECEIVE, ReturnFlowState.VOID, ReturnFlowState.REFUND_FAILED);

        // 1. 获取已退数据
        Map<String, BigDecimal> alReturnPointMap = new HashMap<>();
        Map<String, BigDecimal> alReturnNumMap = new HashMap<>();
        // 1.1 获取有效退单
        List<ReturnOrder> newReturnOrderList = returnOrderList.stream().filter(returnTrade ->
                !returnFlowStates.contains(returnTrade.getReturnFlowState())).collect(Collectors.toList());
        // 1.2 封装已退数量
        for (ReturnOrder returnOrder1: newReturnOrderList) {
            returnOrder1.getReturnItems().forEach(item -> {
                BigDecimal returnPoint = Objects.isNull(item.getSplitPoint())
                        ? BigDecimal.ZERO : BigDecimal.valueOf(item.getSplitPoint().longValue());
                if (alReturnPointMap.containsKey(item.getSkuId())) {
                    returnPoint = returnPoint.add(alReturnPointMap.get(item.getSkuId()));
                }
                alReturnPointMap.put(item.getSkuId(), returnPoint);
                BigDecimal returnNum  = BigDecimal.valueOf(item.getNum().intValue());
                if (alReturnNumMap.containsKey(item.getSkuId())) {
                    returnNum = returnNum.add(alReturnNumMap.get(item.getSkuId()));
                }
                alReturnNumMap.put(item.getSkuId(), returnNum);
            });
        }

        // 2. 获取原订单数据
        Map<String, BigDecimal> orderPointMap = new HashMap<>();
        Map<String, BigDecimal> orderNumMap = new HashMap<>();
        for (TradeItem tradeItem : trade.getTradeItems()) {
            if (Objects.isNull(tradeItem.getPoints())) {
                continue;
            }
            BigDecimal num  = BigDecimal.valueOf(tradeItem.getNum().intValue());
            if (orderNumMap.containsKey(tradeItem.getSkuId())) {
                num = num.add(orderNumMap.get(tradeItem.getSkuId()));
            }
            orderNumMap.put(tradeItem.getSkuId(), num);

            BigDecimal point  = BigDecimal.valueOf(tradeItem.getPoints().longValue());
            if (orderPointMap.containsKey(tradeItem.getSkuId())) {
                point = point.add(orderPointMap.get(tradeItem.getSkuId()));
            }
            orderPointMap.put(tradeItem.getSkuId(), point);
        }

        // 周期购验证
        OrderTag orderTag = trade.getOrderTag();
        boolean buyCycleFlag = Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag();

        // 3. 计算退单商品积分
        BigDecimal sumShouldPoints = BigDecimal.ZERO;
        for (ReturnItem returnItem : returnOrder.getReturnItems()) {
            // 商品是否有积分
            BigDecimal shouldPoints = BigDecimal.ZERO;
            if (!orderNumMap.containsKey(returnItem.getSkuId())) {
                returnItem.setSplitPoint(shouldPoints.longValue());
                continue;
            }
            // 验证是否是周期购
            if (buyCycleFlag) {
                int size  = trade.getTradeDelivers().size();
                if (size == 0) {
                    shouldPoints = orderPointMap.get(returnItem.getSkuId());
                } else {
                    Integer deliveryCycleNum = trade.getTradeBuyCycle().getDeliveryCycleNum();
                    BigDecimal ratio = new BigDecimal(deliveryCycleNum-size).divide(new BigDecimal(deliveryCycleNum), 10, RoundingMode.HALF_UP);
                    shouldPoints = ratio.multiply(orderPointMap.get(returnItem.getSkuId())).setScale(0, RoundingMode.HALF_UP);
                }
            } else {
                // 验证是否最后退单
                BigDecimal returnNum = BigDecimal.valueOf(returnItem.getNum().intValue());
                BigDecimal alReturnNum = alReturnNumMap.containsKey(returnItem.getSkuId()) ? alReturnNumMap.get(returnItem.getSkuId()) : BigDecimal.ZERO;
                BigDecimal alReturnPoint = alReturnPointMap.containsKey(returnItem.getSkuId()) ? alReturnPointMap.get(returnItem.getSkuId()) : BigDecimal.ZERO;
                if (returnNum.add(alReturnNum).compareTo(orderNumMap.get(returnItem.getSkuId())) == 0) {
                    shouldPoints = orderPointMap.get(returnItem.getSkuId()).subtract(alReturnPoint);
                } else {
                    BigDecimal ratio = returnNum.divide(orderNumMap.get(returnItem.getSkuId()), 10, RoundingMode.HALF_UP);
                    shouldPoints = ratio.multiply(orderPointMap.get(returnItem.getSkuId())).setScale(0, RoundingMode.HALF_UP);
                }
            }
            sumShouldPoints = sumShouldPoints.add(shouldPoints);
            returnItem.setSplitPoint(shouldPoints.longValue());
        }
        return Long.valueOf(sumShouldPoints.longValue());
    }

    /**
     * @description   退单 加价购积分计算
     * @author  wur
     * @date: 2023/1/14 14:09
     * @param returnOrder  当前退单
     * @param trade        原订单
     * @param returnOrderList  已退单
     * @return
     **/
    public Long getPreferentialPoints(ReturnOrder returnOrder, Trade trade, List<ReturnOrder> returnOrderList) {
        List<ReturnFlowState> returnFlowStates = Arrays.asList(ReturnFlowState.REJECT_REFUND,
                ReturnFlowState.REJECT_RECEIVE, ReturnFlowState.VOID, ReturnFlowState.REFUND_FAILED);

        Map<Long, Map<String, Long>> marketingIdTostoreSplitPointMap = returnOrderList.stream()
                .filter(returnTrade -> !returnFlowStates.contains(returnTrade.getReturnFlowState()))
                .peek(returnTrade -> returnTrade.setReturnPreferential(returnTrade.getReturnPreferential().parallelStream()
                        .peek(returnItem -> {
                            if(Objects.isNull(returnItem.getSplitPoint())) {
                                returnItem.setSplitPoint(0L);
                            }
                        })
                        .collect(Collectors.toList())
                ))
                .flatMap(g -> g.getReturnPreferential().stream())
                .collect(
                        Collectors.groupingBy(
                                ReturnItem::getMarketingId,
                                Collectors.groupingBy(ReturnItem::getSkuId, Collectors.summingLong(returnItem -> Optional.ofNullable(returnItem.getSplitPoint()).orElse(0L)))));
        // 各商品消耗积分
        Map<Long, Map<String, Long>> pointsMap = new HashMap<>(2);
        List<TradeItem> tradeItems = trade.getPreferential();
        for (TradeItem tradeItem : tradeItems) {
            Long points = Objects.nonNull(tradeItem.getPoints()) ? tradeItem.getPoints() : NumberUtils.LONG_ZERO;
            String skuId = tradeItem.getSkuId();
            Long marketingId = tradeItem.getMarketingIds().get(0);
            Map<String, Long> map = pointsMap.get(marketingId);
            if (MapUtils.isNotEmpty(map)){
                map.put(skuId, points);
            } else {
                Map<String, Long> itemMap = new HashMap<>();
                itemMap.put(skuId, points);
                pointsMap.put(marketingId, itemMap);
            }
        }

        List<ReturnItem> returnItems = returnOrder.getReturnPreferential();
        returnItems.forEach(g -> {
            if (Objects.nonNull(marketingIdTostoreSplitPointMap.getOrDefault(g.getMarketingId(), new HashMap<>()).get(g.getSkuId()))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (!Objects.equals(g.getNum(), g.getCanReturnNum())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        });
        // 可退积分计算
        return returnItems.stream()
                .map(returnItem -> {
                    String skuId = returnItem.getSkuId();
                    long shouldPoints = pointsMap.get(returnItem.getMarketingId()).get(skuId);
                    returnItem.setSplitPoint(shouldPoints);
                    return shouldPoints;
                })
                .reduce(0L, Long::sum);
    }

    /**
     * 创建退单快照
     *
     * @param returnOrder
     * @param operator
     */
    @Transactional
    @GlobalTransactional
    public void transfer(ReturnOrder returnOrder, Operator operator) {
        Trade trade = tradeService.detail(returnOrder.getTid());
        //查询该订单所有退单
        List<ReturnOrder> returnOrderList = returnOrderRepository.findByTid(trade.getId());
        //校验商品是否全部售后
        if (tradeService.tradeVerifyAfterProcessingAll(trade.getId())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050078);
        }

        if (trade.getOrderTag() != null && Boolean.TRUE.equals(trade.getOrderTag().getBuyCycleFlag())) {
            TradeItem tradeItem = trade.getTradeItems().get(Constants.ZERO);
            returnOrder.getReturnItems().get(Constants.ZERO).setCycleReturnNum(tradeItem.getBuyCycleNum() * tradeItem.getNum().intValue());
        }

        this.verifyNum(trade, returnOrder.getReturnItems());

        Buyer buyer = new Buyer();
        buyer.setId(operator.getUserId());

        returnOrder.setBuyer(buyer);
        returnOrder.setSellPlatformType(trade.getSellPlatformType());

        // 计算并设置需要退的赠品
        if (Objects.nonNull(returnOrder.getReturnGift()) && returnOrder.getReturnGift()) {
            getAndSetReturnGifts(returnOrder, trade, returnOrderList);
        }

        // 计算并设置需要退的加价购
        if (CollectionUtils.isNotEmpty(returnOrder.getReturnPreferentialIds())) {
            getAndSetReturnPreferentials(returnOrder, trade, returnOrderList);
        }

        // 处理退礼品卡逻辑
        this.giftCardProcess(trade, returnOrder, returnOrderList);

        ReturnOrderTransfer returnOrderTransfer = new ReturnOrderTransfer();
        KsBeanUtil.copyProperties(returnOrder, returnOrderTransfer);
        delTransfer(operator.getUserId());
        returnOrderTransfer.setId(UUIDUtil.getUUID());
        returnOrderTransfer.setReturnTag(wapperTag(returnOrder.getReturnTag(), trade.getOrderTag()));
        BigDecimal total =
                returnOrderTransfer.getReturnPreferential().stream()
                        .map(ReturnItem::getSplitPrice)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO,
                BigDecimal::add);
        Optional<Long> pointTotal =
                returnOrderTransfer.getReturnPreferential().stream()
                        .map(ReturnItem::getSplitPoint)
                        .filter(Objects::nonNull)
                        .reduce(Long::sum);
        ReturnPrice returnPrice = returnOrderTransfer.getReturnPrice();
        returnPrice.setTotalPrice(returnPrice.getTotalPrice().add(total));

        ReturnPoints returnPoints = returnOrderTransfer.getReturnPoints();

        Long applyPoints = pointTotal.orElse(0L);
        if(Objects.isNull(returnPoints) || (Objects.nonNull(returnPoints) && returnPoints.getApplyPoints() == null)){
            applyPoints += 0L;
        }else{
            applyPoints += returnPoints.getApplyPoints();
        }
        if(Objects.nonNull(returnPoints)){
            returnPoints.setApplyPoints(applyPoints);
        }
        returnOrderTransferService.addReturnOrderTransfer(returnOrderTransfer);
    }

    /**
     * @description   封装退单礼品卡信息
     * @author  wur
     * @date: 2022/12/30 13:57
     * @param trade  原订单信息
     * @param newReturnOrder  本次退单
     * @param returnOrderList 历史退单
     * @return
     **/
    public void giftCardProcess(Trade trade, ReturnOrder newReturnOrder, List<ReturnOrder> returnOrderList) {
        BigDecimal returnGiftCardPrice = BigDecimal.ZERO;
        // 处理普通商品
        if(CollectionUtils.isNotEmpty(newReturnOrder.getReturnItems())) {
            returnGiftCardPrice = this.giftCardProcess(trade.getTradeItems(), newReturnOrder.getReturnItems(), returnOrderList);
        }

        // 处理加价购商品
        if(CollectionUtils.isNotEmpty(newReturnOrder.getReturnPreferential())) {
            returnGiftCardPrice = returnGiftCardPrice.add(this.preferentialGiftCardProcess(trade.getPreferential(), newReturnOrder.getReturnPreferential(), returnOrderList));
        }
        newReturnOrder.getReturnPrice().setGiftCardPrice(returnGiftCardPrice);
    }

    /**
     * @description  周期够退单
     * @author  wur
     * @date: 2023/1/4 11:01
     * @param trade
     * @param newReturnOrder
     * @return
     **/
    public void giftCardCycleProcess(Trade trade, ReturnOrder newReturnOrder) {
        List<TradeDeliver> tradeDelivers = trade.getTradeDelivers();
        int sendNum = tradeDelivers.size();
        int deliveryCycleNum = trade.getTradeBuyCycle().getDeliveryCycleNum();
        // 处理退单中每个商品应退金额
        Map<String, TradeItem> tradeItemMap = trade.getTradeItems().stream().collect(Collectors.toMap(TradeItem::getSkuId, s->s));
        BigDecimal giftCardPrice = BigDecimal.ZERO;
        // 按照退单数量 计算均摊礼品卡金额
        for (ReturnItem returnItem : newReturnOrder.getReturnItems()) {
            if(!tradeItemMap.containsKey(returnItem.getSkuId())) {
                continue;
            }
            TradeItem tradeItem = tradeItemMap.get(returnItem.getSkuId());
            if (CollectionUtils.isEmpty(tradeItem.getGiftCardItemList())) {
                continue;
            }
            // 计算出剩余的占比
            BigDecimal ratio = new BigDecimal(deliveryCycleNum-sendNum).divide(new BigDecimal(deliveryCycleNum), 10, RoundingMode.HALF_UP);
            List<ReturnItem.GiftCardItem> giftCardItemList = new ArrayList<>();
            for (TradeItem.GiftCardItem giftCardItem  : tradeItem.getGiftCardItemList()) {
                BigDecimal returnPrice = ratio.multiply(giftCardItem.getPrice()).setScale(2, RoundingMode.UP);
                if(BigDecimal.ZERO.compareTo(returnPrice) < 0) {
                    giftCardPrice = giftCardPrice.add(returnPrice);
                    ReturnItem.GiftCardItem cardItem = new ReturnItem.GiftCardItem();
                    cardItem.setGiftCardNo(giftCardItem.getGiftCardNo());
                    cardItem.setUserGiftCardID(giftCardItem.getUserGiftCardID());
                    cardItem.setReturnPrice(returnPrice);
                    giftCardItemList.add(cardItem);
                }
            }
            if (CollectionUtils.isNotEmpty(giftCardItemList)) {
                returnItem.setGiftCardItemList(giftCardItemList);
            }
        }
        newReturnOrder.getReturnPrice().setGiftCardPrice(giftCardPrice);
    }

    /**
     * @description     普通商品退单礼品卡
     * @author  wur
     * @date: 2023/1/14 10:47
     * @param tradeItems    原订单商品信息
     * @param returnItems     本次退单 商品信息
     * @param returnOrderList  历史退单
     * @return
     **/
    private BigDecimal giftCardProcess(List<TradeItem> tradeItems, List<ReturnItem> returnItems, List<ReturnOrder> returnOrderList) {
        if (CollectionUtils.isEmpty(returnItems)) {
            return BigDecimal.ZERO;
        }
        // 1. 封装已退信息
        List<ReturnOrder> newReturnOrderList = returnOrderList.stream().filter(t -> !(t.getReturnFlowState() == ReturnFlowState.VOID)
                && !(t.getReturnFlowState() == ReturnFlowState.REJECT_RECEIVE) &&
                !(t.getReturnType() == ReturnType.REFUND && t.getReturnFlowState() == ReturnFlowState.REJECT_REFUND)).collect(Collectors.toList());
        // 已退数量 <skuId, 已退数量>
        Map<String, BigDecimal> alReturnNumMap = new HashMap<>();
        // 已退金额<skuId+giftCardId, 已退金额>
        Map<String, BigDecimal> alReturnPriceMap = new HashMap<>();
        for (ReturnOrder returnOrder : newReturnOrderList) {
            returnOrder.getReturnItems().forEach(item -> {
                BigDecimal alReturnNum = BigDecimal.valueOf(item.getNum().intValue());
                if (alReturnNumMap.containsKey(item.getSkuId())) {
                    alReturnNum = alReturnNum.add(alReturnNumMap.get(item.getSkuId()));
                }
                alReturnNumMap.put(item.getSkuId(), alReturnNum);
                if (CollectionUtils.isNotEmpty(item.getGiftCardItemList())) {
                    for (ReturnItem.GiftCardItem giftCardItem : item.getGiftCardItemList()) {
                        String key = item.getSkuId() + "-" + giftCardItem.getUserGiftCardID();
                        BigDecimal alReturnPrice = giftCardItem.getReturnPrice();
                        if (alReturnPriceMap.containsKey(key)) {
                            alReturnPrice = alReturnPriceMap.get(key).add(alReturnPrice);
                        }
                        alReturnPriceMap.put(key, alReturnPrice);
                    }
                }
            });
        }
        // 2. 获取原订单数据
        // 原订单数量 <skuId, 数量>
        Map<String, BigDecimal> orderNumMap = new HashMap<>();
        // 原订单礼品卡金额<skuId+giftCardId, 金额>
        Map<String, BigDecimal> orderPriceMap = new HashMap<>();
        Map<String, List<TradeItem.GiftCardItem>> orderCardMap = new HashMap<>();
        for (TradeItem tradeItem : tradeItems) {
            if (CollectionUtils.isEmpty(tradeItem.getGiftCardItemList())) {
                continue;
            }
            orderCardMap.put(tradeItem.getSkuId(), tradeItem.getGiftCardItemList());
            orderNumMap.put(tradeItem.getSkuId(), BigDecimal.valueOf(tradeItem.getNum().intValue()));
            for (TradeItem.GiftCardItem giftCardItem : tradeItem.getGiftCardItemList()) {
                String key = tradeItem.getSkuId() + "-" + giftCardItem.getUserGiftCardID();
                orderPriceMap.put(key, giftCardItem.getPrice());
            }
        }
        // 3. 计算应退礼品卡金额
        BigDecimal sumShouldPrice = BigDecimal.ZERO;
        for (ReturnItem returnItem : returnItems) {
            // 验证原商品是否有礼品卡信息
            if (!orderNumMap.containsKey(returnItem.getSkuId())
                    || !orderCardMap.containsKey(returnItem.getSkuId())) {
                continue;
            }
            List<TradeItem.GiftCardItem> giftCardItemList = orderCardMap.get(returnItem.getSkuId());
            List<ReturnItem.GiftCardItem> newCardItemList = new ArrayList<>();
            BigDecimal returnNum = BigDecimal.valueOf(returnItem.getNum().intValue());
            BigDecimal alReturnNum = alReturnNumMap.containsKey(returnItem.getSkuId()) ? alReturnNumMap.get(returnItem.getSkuId()) : BigDecimal.ZERO;
            boolean isList = returnNum.add(alReturnNum).compareTo(orderNumMap.get(returnItem.getSkuId())) == 0;
            for (TradeItem.GiftCardItem cardItem : giftCardItemList) {
                BigDecimal shouldPrice = BigDecimal.ZERO;
                String key = returnItem.getSkuId() + "-" + cardItem.getUserGiftCardID();
                BigDecimal alReturnPrice = alReturnPriceMap.containsKey(key)?alReturnPriceMap.get(key):BigDecimal.ZERO;
                // 验证是否最后退单
                if(isList) {
                    shouldPrice = orderPriceMap.get(key).subtract(alReturnPrice);
                } else {
                    // 计算比例
                    BigDecimal ratio = returnNum.divide(orderNumMap.get(returnItem.getSkuId()), 10, RoundingMode.HALF_UP);
                    shouldPrice = ratio.multiply(orderPriceMap.get(key)).setScale(2, RoundingMode.HALF_UP);
                }
                sumShouldPrice = sumShouldPrice.add(shouldPrice);
                if (shouldPrice.compareTo(BigDecimal.ZERO) > 0) {
                    ReturnItem.GiftCardItem giftCardItem = new ReturnItem.GiftCardItem();
                    giftCardItem.setGiftCardNo(cardItem.getGiftCardNo());
                    giftCardItem.setUserGiftCardID(cardItem.getUserGiftCardID());
                    giftCardItem.setReturnPrice(shouldPrice);
                    newCardItemList.add(giftCardItem);
                }
            }
            if (CollectionUtils.isNotEmpty(newCardItemList)) {
                returnItem.setGiftCardItemList(newCardItemList);
            }
        }
        return sumShouldPrice;
    }

    /**
     * @description    封装退单礼品卡信息
     * @author  wur
     * @date: 2022/12/19 11:34
     * @param tradeItems    原订单商品信息
     * @param returnItems   本次退单 商品信息
     * @param returnOrderList   历史退单
     * @return
     **/
    private BigDecimal preferentialGiftCardProcess(List<TradeItem> tradeItems, List<ReturnItem> returnItems, List<ReturnOrder> returnOrderList) {
        if (CollectionUtils.isEmpty(returnItems)) {
            return BigDecimal.ZERO;
        }
        // 1. 封装已退信息
        List<ReturnFlowState> returnFlowStates = Arrays.asList(ReturnFlowState.REJECT_REFUND,
                ReturnFlowState.REJECT_RECEIVE, ReturnFlowState.VOID, ReturnFlowState.REFUND_FAILED);

        List<ReturnOrder> newReturnOrderList = returnOrderList.stream()
                .filter(returnTrade -> !returnFlowStates.contains(returnTrade.getReturnFlowState())).collect(Collectors.toList());
        List<String> alReturnKey = new ArrayList<>();
        for (ReturnOrder returnOrder : newReturnOrderList) {
            if (CollectionUtils.isEmpty(returnOrder.getReturnPreferential())) {
                continue;
            }
            returnOrder.getReturnPreferential().forEach(pre->{
                String key = pre.getSkuId()+"p"+pre.getMarketingId().toString();
                alReturnKey.add(key);
            });
        }

        returnItems.forEach(g -> {
            String key = g.getSkuId()+"p"+g.getMarketingId().toString();
            if (alReturnKey.contains(key)){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        });

        // 查询原订单商品礼品卡
        Map<Long, Map<String, List<TradeItem.GiftCardItem>>> orderMap = new HashMap<>();
        for (TradeItem tradeItem : tradeItems) {
            if (CollectionUtils.isEmpty(tradeItem.getGiftCardItemList())) {
                continue;
            }
            List<TradeItem.GiftCardItem> giftCardItemList = tradeItem.getGiftCardItemList();
            String skuId = tradeItem.getSkuId();
            Long marketingId = tradeItem.getMarketingIds().get(0);

            if (orderMap.containsKey(marketingId)){
                orderMap.get(marketingId).put(skuId, giftCardItemList);
            } else {
                Map<String, List<TradeItem.GiftCardItem>> Map = new HashMap<>();
                Map.put(skuId, giftCardItemList);
                orderMap.put(marketingId, Map);
            }
        }

        BigDecimal giftCardPrice = BigDecimal.ZERO;
        for (ReturnItem returnItem : returnItems) {
            // 验证商品是否有使用礼品卡
            if(orderMap.containsKey(returnItem.getMarketingId())
                    && orderMap.get(returnItem.getMarketingId()).containsKey(returnItem.getSkuId())) {
                List<TradeItem.GiftCardItem> giftCardItemList = orderMap.get(returnItem.getMarketingId()).get(returnItem.getSkuId());
                if (CollectionUtils.isNotEmpty(giftCardItemList)) {
                    List<ReturnItem.GiftCardItem> newCardItemList = new ArrayList<>();
                    for (TradeItem.GiftCardItem cardItem : giftCardItemList) {
                        ReturnItem.GiftCardItem giftCardItem = new ReturnItem.GiftCardItem();
                        giftCardItem.setGiftCardNo(cardItem.getGiftCardNo());
                        giftCardItem.setUserGiftCardID(cardItem.getUserGiftCardID());
                        giftCardItem.setReturnPrice(cardItem.getPrice());
                        giftCardPrice = giftCardPrice.add(cardItem.getPrice());
                        newCardItemList.add(giftCardItem);
                    }
                    returnItem.setGiftCardItemList(newCardItemList);
                }
            }
        }
        return giftCardPrice;
    }

    /**
     * @description  根据退单来处理退礼品卡
     * @author  wur
     * @date: 2022/12/16 17:38
     * @param returnOrder
     * @return
     **/
    public void returnGiftCard(ReturnOrder returnOrder) {
        ReturnPrice returnPrice = returnOrder.getReturnPrice();

        //判断订单是否有礼品卡支付金额
        if (Objects.isNull(returnPrice.getGiftCardPrice())
                || BigDecimal.ZERO.compareTo(returnPrice.getGiftCardPrice()) >= 0) {
            return;
        }

        //循环处理每个商品礼品卡使用情况
        Map<Long, UserGiftCardTransRequest> userGiftCardTransRequestMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(returnOrder.getReturnItems())) {
            for (ReturnItem returnItem : returnOrder.getReturnItems()) {
                if (CollectionUtils.isEmpty(returnItem.getGiftCardItemList())) {
                    continue;
                }
                for (ReturnItem.GiftCardItem giftCardItem : returnItem.getGiftCardItemList()) {
                    UserGiftCardTransRequest cardTransRequest = new UserGiftCardTransRequest();
                    if (userGiftCardTransRequestMap.containsKey(giftCardItem.getUserGiftCardID())) {
                        cardTransRequest = userGiftCardTransRequestMap.getOrDefault(giftCardItem.getUserGiftCardID(),  new UserGiftCardTransRequest());
                    }
                    cardTransRequest.setUserGiftCardId(giftCardItem.getUserGiftCardID());
                    cardTransRequest.setGiftCardNo(giftCardItem.getGiftCardNo());
                    if (Objects.isNull(cardTransRequest.getSumTradePrice())) {
                        cardTransRequest.setSumTradePrice(giftCardItem.getReturnPrice());
                    } else {
                        cardTransRequest.setSumTradePrice(cardTransRequest.getSumTradePrice().add(giftCardItem.getReturnPrice()));
                    }
                    userGiftCardTransRequestMap.put(giftCardItem.getUserGiftCardID(), cardTransRequest);
                }
            }
        }

        // 处理加价购商品
        if(CollectionUtils.isNotEmpty(returnOrder.getReturnPreferential())) {
            for (ReturnItem returnItem : returnOrder.getReturnPreferential()) {
                if (CollectionUtils.isEmpty(returnItem.getGiftCardItemList())) {
                    continue;
                }
                for (ReturnItem.GiftCardItem giftCardItem : returnItem.getGiftCardItemList()) {
                    UserGiftCardTransRequest cardTransRequest = new UserGiftCardTransRequest();
                    if (userGiftCardTransRequestMap.containsKey(giftCardItem.getUserGiftCardID())) {
                        cardTransRequest = userGiftCardTransRequestMap.getOrDefault(giftCardItem.getUserGiftCardID(),  new UserGiftCardTransRequest());
                    }
                    cardTransRequest.setUserGiftCardId(giftCardItem.getUserGiftCardID());
                    cardTransRequest.setGiftCardNo(giftCardItem.getGiftCardNo());
                    if (Objects.isNull(cardTransRequest.getSumTradePrice())) {
                        cardTransRequest.setSumTradePrice(giftCardItem.getReturnPrice());
                    } else {
                        cardTransRequest.setSumTradePrice(cardTransRequest.getSumTradePrice().add(giftCardItem.getReturnPrice()));
                    }
                    userGiftCardTransRequestMap.put(giftCardItem.getUserGiftCardID(), cardTransRequest);
                }
            }
        }

        // 循环处理每个礼品卡退款
        for (Map.Entry<Long, UserGiftCardTransRequest> entry:userGiftCardTransRequestMap.entrySet()){
            UserGiftCardTransRequest cardTransRequest = entry.getValue();
            cardTransRequest.setCustomerId(returnOrder.getBuyer().getId());
            cardTransRequest.setTradePersonType(DefaultFlag.NO);
            cardTransRequest.setBusinessType(GiftCardBusinessType.ORDER_REFUND);
            GiftCardTransBusinessVO transBusinessVO = new GiftCardTransBusinessVO();
            transBusinessVO.setBusinessId(returnOrder.getId());
            transBusinessVO.setTradePrice(cardTransRequest.getSumTradePrice());
            cardTransRequest.setTransBusinessVOList(Arrays.asList(transBusinessVO));
            userGiftCardProvider.returnUserGiftCard(cardTransRequest);
        }
    }

    /**
     * 获取并设置本次退单需要退的赠品信息
     * @param returnOrder     本次退单
     * @param trade           对应的订单信息
     * @param returnOrderList 订单对应的所有退单
     * @author bail
     */
    public void getAndSetReturnGifts(ReturnOrder returnOrder, Trade trade, List<ReturnOrder> returnOrderList) {
        List<TradeMarketingVO> tradeMarketings = trade.getTradeMarketings();
        if (CollectionUtils.isNotEmpty(tradeMarketings)) {
            // 1.找到原订单的所有满赠的营销活动marketingList
            List<TradeMarketingVO> giftMarketings = tradeMarketings.stream().filter(tradeMarketing -> MarketingType
                    .GIFT.equals(tradeMarketing.getMarketingType())).collect(Collectors.toList());
            if (giftMarketings.size() > 0) {
                Map<String, TradeItem> tradeItemMap = trade.getTradeItems().stream().collect(Collectors.toMap
                        (TradeItem::getSkuId, Function.identity()));//原订单所有商品的Map,方便根据skuId快速找到对应的商品信息
                //原订单所有赠品的Map,方便根据skuId快速找到对应的赠品信息
                Map<Long, List<TradeItem>> giftItemMap =
                        trade.getGifts().stream().collect(Collectors.groupingBy(g -> g.getMarketingIds().get(0)));
                List<ReturnOrder> comReturnOrders = filterFinishedReturnOrder(returnOrderList);//该订单之前已完成的退单list
                // (分批退单的场景)
                Map<String, ReturnItem> comReturnSkus = new HashMap<>();//已经退的商品汇总(根据skuId汇总所有商品的数量)
                Map<String, ReturnItem> currReturnSkus = returnOrder.getReturnItems().stream().collect(Collectors
                        .toMap(ReturnItem::getSkuId, Function.identity()));//本次退的商品汇总
                Map<Long, Map<String, ReturnItem>> allReturnGifts = new HashMap<>();//可能需要退的赠品汇总
                Map<Long, Map<String, ReturnItem>> comReturnGifts = new HashMap<>();//已经退的赠品汇总
                comReturnOrders.stream().forEach(reOrder -> {
                    reOrder.getReturnItems().stream().forEach(returnItem -> {
                        ReturnItem currItem = comReturnSkus.get(returnItem.getSkuId());
                        if (currItem == null) {
                            comReturnSkus.put(returnItem.getSkuId(), returnItem);
                        } else {
                            currItem.setNum(currItem.getNum().intValue() + returnItem.getNum().intValue());
                        }
                    });

                    if (CollectionUtils.isNotEmpty(reOrder.getReturnGifts())) {
                        reOrder.getReturnGifts().stream().forEach(retrunGift -> {
                            Map<String, ReturnItem> skuToReturnMap = comReturnGifts.get(retrunGift.getMarketingId());
                            if (MapUtils.isEmpty(skuToReturnMap)) {
                                Map<String, ReturnItem> returnItemMap = new HashMap<>();
                                returnItemMap.put(retrunGift.getSkuId(), retrunGift);
                                comReturnGifts.put(retrunGift.getMarketingId(), returnItemMap);
                            } else {
                                skuToReturnMap.put(retrunGift.getSkuId(), retrunGift);
                            }
                        });
                    }
                });

                // 2.遍历满赠营销活动list,验证每个活动对应的剩余商品(购买数量或金额-已退的总数或总金额)是否还满足满赠等级的条件
                //   PS: 已退的总数或总金额分为两部分: a.该订单关联的所有已完成的退单的商品 b.本次用户准备退货的商品
                giftMarketings.forEach(giftMarketing -> {
                    if (MarketingSubType.GIFT_FULL_AMOUNT.equals(giftMarketing.getSubType())) {
                        BigDecimal leftSkuAmount = giftMarketing.getSkuIds().stream().map(skuId -> {
                            TradeItem skuItem = tradeItemMap.get(skuId);
                            long comReSkuCount = comReturnSkus.get(skuId) == null ? 0L : comReturnSkus.get(skuId)
                                    .getNum().longValue();
                            long currReSkuCount = currReturnSkus.get(skuId) == null ? 0L : currReturnSkus.get(skuId)
                                    .getNum().longValue();
                            return skuItem.getLevelPrice().multiply(new BigDecimal(skuItem.getNum() -
                                    comReSkuCount - currReSkuCount));//某商品的总价格 - 已退商品价格 - 当前准备退的商品价格
                        }).reduce(BigDecimal::add).get();//剩余商品价格汇总

                        // 3.若不满足满赠条件,则退该活动的所有赠品,汇总到所有的退货赠品数量中(若满足满赠条件,则无需退赠品)
                        if (leftSkuAmount.compareTo(giftMarketing.getGiftLevel().getFullAmount()) < 0 && giftItemMap.size()>0) {
                            setReturnGiftsMap(giftMarketing, allReturnGifts, giftItemMap);
                        }
                    } else if (MarketingSubType.GIFT_FULL_COUNT.equals(giftMarketing.getSubType())) {
                        long leftSkuCount = giftMarketing.getSkuIds().stream().mapToLong(skuId -> {
                            TradeItem skuItem = tradeItemMap.get(skuId);
                            long comReSkuCount = comReturnSkus.get(skuId) == null ? 0L : comReturnSkus.get(skuId)
                                    .getNum().longValue();
                            long currReSkuCount = currReturnSkus.get(skuId) == null ? 0L : currReturnSkus.get(skuId)
                                    .getNum().longValue();
                            return skuItem.getNum().longValue() - comReSkuCount - currReSkuCount;//某商品的总数
                            // - 已退商品数 - 当前准备退的商品数
                        }).sum();//剩余商品数量汇总

                        // 3.若不满足满赠条件,则退该活动的所有赠品,汇总到所有的退货赠品数量中(若满足满赠条件,则无需退赠品)
                        if (leftSkuCount < giftMarketing.getGiftLevel().getFullCount() && giftItemMap.size()>0) {
                            setReturnGiftsMap(giftMarketing, allReturnGifts, giftItemMap);
                        }
                    }
                });

                // 4.设置具体的退单赠品信息
                returnOrder.setReturnGifts(getReturnGiftList(trade, allReturnGifts, comReturnGifts));
            }
        }
    }

    public void getAndSetReturnPreferentials(ReturnOrder returnOrder, Trade trade, List<ReturnOrder> returnOrderList) {
        List<TradeMarketingVO> tradeMarketings = trade.getTradeMarketings();
        if (CollectionUtils.isNotEmpty(tradeMarketings)) {
            List<TradeMarketingVO> preferentialMarketings = tradeMarketings.stream().filter(tradeMarketing -> MarketingType
                    .PREFERENTIAL.equals(tradeMarketing.getMarketingType())).collect(Collectors.toList());
            if (preferentialMarketings.size() > 0) {
                //原订单所有商品的Map,方便根据skuId快速找到对应的商品信息
                Map<String, TradeItem> tradeItemMap = trade.getTradeItems().stream().collect(Collectors.toMap
                        (TradeItem::getSkuId, Function.identity()));
//                //原订单所有赠品的Map,方便根据skuId快速找到对应的信息
                Map<Long, List<TradeItem>> marketingIdToTradeItemsMap =
                        trade.getPreferential().stream().collect(Collectors.groupingBy(g -> g.getMarketingIds().get(0)));

                //该订单之前已完成的退单list
                List<ReturnOrder> comReturnOrders = filterFinishedReturnOrder(returnOrderList);
                // (分批退单的场景)
                // 已经退的商品汇总(根据skuId汇总所有商品的数量)
                Map<String, ReturnItem> comReturnSkus = new HashMap<>();
                // 本次退的商品汇总
                Map<String, ReturnItem> currReturnSkus = returnOrder.getReturnItems().stream().collect(Collectors
                        .toMap(ReturnItem::getSkuId, Function.identity()));
                // 可能需要退的商品汇总
                Map<Long, Map<String, ReturnItem>> allReturn = new HashMap<>();
                // 已经退的商品汇总
                Map<Long, Map<String, ReturnItem>> comReturn = new HashMap<>();
                comReturnOrders.forEach(reOrder -> {
                    reOrder.getReturnItems().forEach(returnItem -> {
                        ReturnItem currItem = comReturnSkus.get(returnItem.getSkuId());
                        if (currItem == null) {
                            comReturnSkus.put(returnItem.getSkuId(), returnItem);
                        } else {
                            currItem.setNum(currItem.getNum() + returnItem.getNum());
                        }
                    });

                    if (CollectionUtils.isNotEmpty(reOrder.getReturnPreferential())) {
                        reOrder.getReturnPreferential().forEach(returnItem -> {
                            Map<String, ReturnItem> skuToReturnMap = comReturn.get(returnItem.getMarketingId());
                            if (MapUtils.isEmpty(skuToReturnMap)){
                                Map<String, ReturnItem> returnItemMap = new HashMap<>();
                                returnItemMap.put(returnItem.getSkuId(), returnItem);
                                comReturn.put(returnItem.getMarketingId(), returnItemMap);
                            } else {
                                skuToReturnMap.put(returnItem.getSkuId(), returnItem);
                            }
                        });
                    }
                });

                // 2.遍历满赠营销活动list,验证每个活动对应的剩余商品(购买数量或金额-已退的总数或总金额)是否还满足满赠等级的条件
                //   PS: 已退的总数或总金额分为两部分: a.该订单关联的所有已完成的退单的商品 b.本次用户准备退货的商品
                preferentialMarketings.forEach(marketing -> {
                    if (MarketingSubType.PREFERENTIAL_FULL_AMOUNT.equals(marketing.getSubType())) {
                        BigDecimal leftSkuAmount = marketing.getSkuIds().stream().map(skuId -> {
                            TradeItem skuItem = tradeItemMap.get(skuId);
                            long comReSkuCount = comReturnSkus.get(skuId) == null ? 0L : comReturnSkus.get(skuId)
                                    .getNum().longValue();
                            long currReSkuCount = currReturnSkus.get(skuId) == null ? 0L : currReturnSkus.get(skuId)
                                    .getNum().longValue();
                            return skuItem.getLevelPrice().multiply(new BigDecimal(skuItem.getNum() -
                                    comReSkuCount - currReSkuCount));//某商品的总价格 - 已退商品价格 - 当前准备退的商品价格
                        }).reduce(BigDecimal::add).get();//剩余商品价格汇总

                        // 3.若不满足满赠条件,则退该活动的所有赠品,汇总到所有的退货赠品数量中(若满足满赠条件,则无需退赠品)
                        List<MarketingPreferentialGoodsDetailVO> goodsDetailVOList =
                                marketing.getPreferentialLevelVOS().stream()
                                        .filter(g -> leftSkuAmount.compareTo(g.getFullAmount()) < 0)
                                        .flatMap(g -> g.getPreferentialDetailList().stream())
                                        .collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(goodsDetailVOList)){
                            setReturnPreferentialsMap(goodsDetailVOList, allReturn, marketingIdToTradeItemsMap);
                        }
                    } else if (MarketingSubType.PREFERENTIAL_FULL_COUNT.equals(marketing.getSubType())) {
                        long leftSkuCount = marketing.getSkuIds().stream().mapToLong(skuId -> {
                            TradeItem skuItem = tradeItemMap.get(skuId);
                            long comReSkuCount = comReturnSkus.get(skuId) == null ? 0L : comReturnSkus.get(skuId)
                                    .getNum().longValue();
                            long currReSkuCount = currReturnSkus.get(skuId) == null ? 0L : currReturnSkus.get(skuId)
                                    .getNum().longValue();
                            return skuItem.getNum() - comReSkuCount - currReSkuCount;//某商品的总数
                            // - 已退商品数 - 当前准备退的商品数
                        }).sum();//剩余商品数量汇总

                        // 3.若不满足满赠条件,则退该活动的所有赠品,汇总到所有的退货赠品数量中(若满足满赠条件,则无需退赠品)
                        List<MarketingPreferentialGoodsDetailVO> goodsDetailVOList =
                                marketing.getPreferentialLevelVOS().stream()
                                        .filter(g -> leftSkuCount < (g.getFullCount()))
                                        .flatMap(g -> g.getPreferentialDetailList().stream())
                                        .collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(goodsDetailVOList)){
                            setReturnPreferentialsMap(goodsDetailVOList, allReturn, marketingIdToTradeItemsMap);
                        }
                    }
                });

                List<ReturnItem> returnItemList = getReturnPreferentialList(allReturn, comReturn);
                List<ReturnItem> returnList =
                        returnItemList.stream().filter(g -> returnOrder.getReturnPreferentialIds().contains(g.getSkuId())).collect(Collectors.toList());
                if (returnOrder.getReturnPreferentialIds().size() != returnList.size()){
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050057);
                }
                returnList.forEach(returnItem -> {
                    trade.getPreferential().forEach(item -> {
                        if (Objects.equals(returnItem.getSkuId(), item.getSkuId())
                        && Objects.equals(returnItem.getMarketingId(), item.getMarketingIds().get(0))){
                            returnItem.setSplitPrice(item.getSplitPrice());
                            returnItem.setSplitPoint(item.getPoints());
                        }
                    });
                });
                // 4.设置具体的退单赠品信息
                returnOrder.setReturnPreferential(returnList);
            }
        }
    }

    /**
     * 不满足满赠条件时,需要退的所有赠品
     *
     * @param giftMarketing  某个满赠营销活动
     * @param allReturnGifts 不满足满赠条件,满赠营销活动中所有需要退的赠品信息
     * @param giftItemMap    赠品具体信息Map(获取除了skuId以外的详细信息)
     */
    private void setReturnGiftsMap(TradeMarketingVO giftMarketing, Map<Long,
            Map<String, ReturnItem>> allReturnGifts,
                                   Map<Long, List<TradeItem>> giftItemMap) {
        Map<Long, Map<String, TradeItem>> tradeItemMap = new HashMap<>();
        giftItemMap.forEach((marketingId, tradeItem) -> {
            Map<String, TradeItem> skuIdToTradeItemMap =
                    tradeItem.stream().collect(Collectors.toMap(TradeItem::getSkuId, Function.identity()));
            tradeItemMap.put(marketingId, skuIdToTradeItemMap);
        });
        // 不满足满赠条件,则退该活动的所有赠品,汇总到所有的退货赠品数量中
        giftMarketing.getGiftLevel().getFullGiftDetailList().stream().forEach(gift -> {
            ReturnItem currGiftItem =
                    allReturnGifts.getOrDefault(gift.getMarketingId(), new HashMap<>()).get(gift.getProductId());
            TradeItem giftDetail =
                    tradeItemMap.getOrDefault(gift.getMarketingId(),
              new HashMap<>()).get(gift.getProductId());
            if (currGiftItem == null) {
                ReturnItem returnItem = ReturnItem.builder().skuId(giftDetail.getSkuId())
                        .num(gift.getProductNum().intValue())
                        .skuName(giftDetail.getSkuName())
                        .skuNo(giftDetail.getSkuNo())
                        .pic(giftDetail.getPic()).price(giftDetail.getPrice())
                        .specDetails(giftDetail.getSpecDetails())
                        .unit(giftDetail.getUnit())
                        .supplyPrice(giftDetail.getSupplyPrice())
                        .providerPrice(giftDetail.getTotalSupplyPrice())
                        .thirdPlatformType(giftDetail.getThirdPlatformType())
                        .thirdPlatformSkuId(giftDetail.getThirdPlatformSkuId())
                        .thirdPlatformSpuId(giftDetail.getThirdPlatformSpuId())
                        .thirdPlatformSubOrderId(giftDetail.getThirdPlatformSubOrderId())
                        .build();
                if(allReturnGifts.containsKey(gift.getMarketingId())){
                    allReturnGifts.get(gift.getMarketingId()).put(gift.getProductId(), returnItem);
                } else {
                    Map<String, ReturnItem> map = new HashMap<>();
                    map.put(gift.getProductId(), returnItem);
                    allReturnGifts.put(gift.getMarketingId(), map);
                }
            } else {
                currGiftItem.setNum(currGiftItem.getNum().intValue() + gift.getProductNum().intValue());
            }
        });
    }

    private void setReturnPreferentialsMap(List<MarketingPreferentialGoodsDetailVO> goodsDetailVOList,
                                            Map<Long, Map<String, ReturnItem>> allReturn,
                                           Map<Long, List<TradeItem>> marketingIdToTradeItemsMap) {
        Map<Long, Map<String, TradeItem>> tradeItemMap = new HashMap<>();
        marketingIdToTradeItemsMap.forEach((marketingId, tradeItem) -> {
            Map<String, TradeItem> skuIdToTradeItemMap =
                    tradeItem.stream().collect(Collectors.toMap(TradeItem::getSkuId, Function.identity()));
            tradeItemMap.put(marketingId, skuIdToTradeItemMap);
        });
        goodsDetailVOList.forEach(detail -> {
            TradeItem preferentialDetail =
                    tradeItemMap.getOrDefault(detail.getMarketingId(), new HashMap<>()).get(detail.getGoodsInfoId());
            ReturnItem returnItem = KsBeanUtil.convert(preferentialDetail, ReturnItem.class);
            returnItem.setSplitPoint(preferentialDetail.getBuyPoint());
            returnItem.setMarketingId(preferentialDetail.getMarketingIds().get(0));
            Map<String, ReturnItem> returnItemMap = allReturn.get(detail.getMarketingId());
            if (MapUtils.isEmpty(returnItemMap)){
                Map<String, ReturnItem> map = new HashMap<>();
                map.put(detail.getGoodsInfoId(), returnItem);
                allReturn.put(detail.getMarketingId(), map);
            } else {
                returnItemMap.put(detail.getGoodsInfoId(), returnItem);
            }
        });
    }

    /**
     * 获取具体的退单赠品信息
     *
     * @param trade          订单
     * @param allReturnGifts 不满足满赠条件,满赠营销活动中所有需要退的赠品信息
     * @param comReturnGifts 所有已完成退单中的退掉的赠品信息
     * @return
     */
    private List<ReturnItem> getReturnGiftList(Trade trade, Map<Long, Map<String,
            ReturnItem>> allReturnGifts,
                                               Map<Long, Map<String, ReturnItem>> comReturnGifts) {
        // 本次退单的退货赠品总数: 每个商品所有退货赠品数量 - 之前所有退单中已经退掉的赠品总数
        //   PS: 为了保证退单中赠品顺序与订单中的赠品顺序一致,遍历订单赠品,依次计算得出本次退单需要退的赠品list
        List<ReturnItem> returnGiftList = trade.getGifts().stream().map(tradeItem -> {
            ReturnItem readyGiftItem =
                    allReturnGifts.getOrDefault(tradeItem.getMarketingIds().get(0), new HashMap<>()).getOrDefault(tradeItem.getSkuId(), null);
            //准备退的
            ReturnItem comGiftItem =
                    comReturnGifts.getOrDefault(tradeItem.getMarketingIds().get(0), new HashMap<>()).get(tradeItem.getSkuId());
            //之前已完成退单已经退掉的
            if (readyGiftItem != null) {
                int totalNum = readyGiftItem.getNum() < tradeItem.getNum().intValue() ? readyGiftItem.getNum
                        () : tradeItem.getNum().intValue();//退货总数 与 购买数量对比,取最小的值
                //仅退款数量设置为赠品总数
                if (trade.getTradeState().getDeliverStatus() == DeliverStatus.VOID) {
                    totalNum = readyGiftItem.getNum();
                }
                if (comGiftItem != null) {
                    int currNum = totalNum - comGiftItem.getNum();
                    if (currNum > 0) {
                        readyGiftItem.setNum(currNum);
                    } else {
                        return null;
                    }
                } else {
                    readyGiftItem.setNum(totalNum);
                }
                readyGiftItem.setProviderId(tradeItem.getProviderId());
                readyGiftItem.setMarketingId(tradeItem.getMarketingIds().get(0));
                return readyGiftItem;
            }
            return null;
        }).filter(reGift -> reGift != null).collect(Collectors.toList());
        return returnGiftList;
    }

    private List<ReturnItem> getReturnPreferentialList(Map<Long, Map<String, ReturnItem>> allReturn,
                                                       Map<Long, Map<String, ReturnItem>> comReturnGifts) {
        Map<Long, List<String>> del = new HashMap<>();
        allReturn.forEach((marketingId, returnMap) -> {
            Map<String, ReturnItem> comReturnMap = comReturnGifts.get(marketingId);
            if (MapUtils.isNotEmpty(comReturnMap)){
                returnMap.forEach((skuId, item) -> {
                    item.setMarketingId(marketingId);
                    ReturnItem returnItem = comReturnMap.get(skuId);
                    if (Objects.nonNull(returnItem)){
                        List<String> delSkuIds = del.get(marketingId);
                        if (CollectionUtils.isNotEmpty(delSkuIds)){
                            delSkuIds.add(skuId);
                        } else {
                            List<String> skuIds = new ArrayList<>();
                            skuIds.add(skuId);
                            del.put(marketingId, skuIds);
                        }
                    }
                });
            }
        });
        del.forEach((marketingId, skuIds) -> {
            Map<String, ReturnItem> skuToItemMap = allReturn.get(marketingId);
            skuIds.forEach(skuToItemMap::remove);
        });
        return allReturn.values().stream().flatMap(g -> g.values().stream()).collect(Collectors.toList());
    }


    /**
     * 删除订单快照
     *
     * @param userId
     */
    @Transactional
    public void delTransfer(String userId) {
        ReturnOrderTransfer returnOrderTransferByBuyerId = returnOrderTransferRepository.findReturnOrderTransferByBuyerId(userId);
        if (Objects.nonNull(returnOrderTransferByBuyerId)) {
            returnOrderTransferService.deleteReturnOrderTransfer(returnOrderTransferByBuyerId.getId());
        }
    }

    /**
     * 查询退单快照
     *
     * @param userId
     * @return
     */
    public ReturnOrder findTransfer(String userId) {
        ReturnOrder returnOrder = null;
        ReturnOrderTransfer returnOrderTransfer = returnOrderTransferRepository.findReturnOrderTransferByBuyerId
                (userId);
        if (returnOrderTransfer != null) {
            returnOrder = new ReturnOrder();
            KsBeanUtil.copyProperties(returnOrderTransfer, returnOrder);
        }
        return returnOrder;
    }

    /*@EsCacheAnnotation(name = "returnOrderESCacheService")
    public void insertES(ReturnOrder returnOrder) {
        returnOrderESRepository.delete(returnOrder.getId());
        returnOrderESRepository.index(returnOrder);
    }

    @EsCacheAnnotation(name = "returnOrderESCacheService")
    public void deleteES(ReturnOrder returnOrder) {
        returnOrderESRepository.delete(returnOrder.getId());
    }*/

    /**
     * 创建退货单
     *
     * @param returnOrder
     */
    private void createReturn(ReturnOrder returnOrder, Operator operator, Trade trade) {

        // 新增订单日志
        tradeService.returnOrder(returnOrder.getTid(), operator);

        this.verifyNum(trade, returnOrder.getReturnItems());

        returnOrder.setReturnType(ReturnType.RETURN);
        // 退单类型
        returnTradeIncision.setCrossReturnOrderType(trade, returnOrder);
        //本次的退或商品去除已完成的退单商品，赠品同理
        List<ReturnOrder> returnOrders = returnOrderRepository.findByTid(trade.getId());
        this.filterCompletedReturnItem(returnOrders, trade);
        //退单标记
        returnOrder.setReturnTag(wapperTag(returnOrder.getReturnTag(), trade.getOrderTag()));
        //填充退货商品信息
        Map<String, Integer> map = findLeftItems(trade);
        Map<String, TradeItem> tmpMap = trade.skuItemMap();
        returnOrder.getReturnItems().forEach(item ->
                {
                    item.setSkuName(tmpMap.get(item.getSkuId()).getSkuName());
                    item.setPic(tmpMap.get(item.getSkuId()).getPic());
                    item.setSkuNo(tmpMap.get(item.getSkuId()).getSkuNo());
                    item.setSpecDetails(tmpMap.get(item.getSkuId()).getSpecDetails());
                    item.setUnit(tmpMap.get(item.getSkuId()).getUnit());
                    item.setCanReturnNum(map.get(item.getSkuId()));
                    item.setBuyPoint(tmpMap.get(item.getSkuId()).getBuyPoint());
                    item.setGoodsSource(tmpMap.get(item.getSkuId()).getGoodsSource());
                    item.setThirdPlatformType(tmpMap.get(item.getSkuId()).getThirdPlatformType());
                    item.setThirdPlatformSpuId(tmpMap.get(item.getSkuId()).getThirdPlatformSpuId());
                    item.setThirdPlatformSkuId(tmpMap.get(item.getSkuId()).getThirdPlatformSkuId());
                    item.setThirdPlatformSubOrderId(tmpMap.get(item.getSkuId()).getThirdPlatformSubOrderId());
                    item.setProviderId(tmpMap.get(item.getSkuId()).getProviderId());
                    item.setSupplyPrice(tmpMap.get(item.getSkuId()).getSupplyPrice());
                    item.setGoodsType(tmpMap.get(item.getSkuId()).getGoodsType());
                    //跨境商品信息
                    returnTradeIncision.setReturnItemExpand(item, tmpMap.get(item.getSkuId()));
                }
        );

    }

    /**
     * 创建退款单
     *
     * @param returnOrder
     */
    private void createRefund(ReturnOrder returnOrder, Operator operator, Trade trade) {
        //校验该订单关联的退款单状态
        List<ReturnOrder> returnOrders = returnOrderRepository.findByTid(trade.getId());
        this.verifyNum(trade, returnOrder.getReturnItems());
        //本次的退单商品去除已完成的退单商品，赠品同理
        this.filterCompletedReturnItem(returnOrders, trade);
        // 新增订单日志
        tradeService.returnOrder(returnOrder.getTid(), operator);
        returnOrder.setReturnType(ReturnType.REFUND);
        //类型
        returnTradeIncision.setCrossReturnOrderType(trade, returnOrder);
        //退单标记
        returnOrder.setReturnTag(wapperTag(returnOrder.getReturnTag(), trade.getOrderTag()));
        //填充退货商品信息
        Map<String, Integer> map = findLeftItems(trade);
        Map<String, TradeItem> tmpMap = trade.skuItemMap();
        returnOrder.getReturnItems().forEach(item ->
                {
                    item.setSkuName(tmpMap.get(item.getSkuId()).getSkuName());
                    item.setPic(tmpMap.get(item.getSkuId()).getPic());
                    item.setSkuNo(tmpMap.get(item.getSkuId()).getSkuNo());
                    item.setSpecDetails(tmpMap.get(item.getSkuId()).getSpecDetails());
                    item.setUnit(tmpMap.get(item.getSkuId()).getUnit());
                    item.setCanReturnNum(map.get(item.getSkuId()));
                    item.setBuyPoint(tmpMap.get(item.getSkuId()).getBuyPoint());
                    item.setGoodsSource(tmpMap.get(item.getSkuId()).getGoodsSource());
                    item.setThirdPlatformType(tmpMap.get(item.getSkuId()).getThirdPlatformType());
                    item.setThirdPlatformSpuId(tmpMap.get(item.getSkuId()).getThirdPlatformSpuId());
                    item.setThirdPlatformSkuId(tmpMap.get(item.getSkuId()).getThirdPlatformSkuId());
                    item.setThirdPlatformSubOrderId(tmpMap.get(item.getSkuId()).getThirdPlatformSubOrderId());
                    item.setProviderId(tmpMap.get(item.getSkuId()).getProviderId());
                    item.setSupplyPrice(tmpMap.get(item.getSkuId()).getSupplyPrice());
                    item.setGoodsType(tmpMap.get(item.getSkuId()).getGoodsType());
                    item.setBuyCycleNum(tmpMap.get(item.getSkuId()).getBuyCycleNum());
                    //跨境商品信息
                    returnTradeIncision.setReturnItemExpand(item, tmpMap.get(item.getSkuId()));
                }
        );

    }

    /**
     * 去除已经完成退单的商品
     *
     * @param returnOrders
     * @param trade
     */
    public void filterCompletedReturnItem(List<ReturnOrder> returnOrders, Trade trade) {
        List<ReturnOrder> completedReturnOrderList
                = returnOrders.stream().filter(o -> o.getReturnFlowState() == ReturnFlowState.COMPLETED).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(completedReturnOrderList)) {
            List<String> completedGoodsInfoIds = new ArrayList<>();
            List<String> completedGiftIds = new ArrayList<>();
            Map<Long, List<String>> completedPreferentialMap = new HashMap<>();
            completedReturnOrderList.forEach(o -> {
                completedGoodsInfoIds.addAll(o.getReturnItems().stream().map(ReturnItem::getSkuId).collect(Collectors.toList()));
                completedGiftIds.addAll(o.getReturnGifts().stream().map(ReturnItem::getSkuId).collect(Collectors.toList()));
                completedPreferentialMap.putAll(o.getReturnPreferential().stream().collect(Collectors.groupingBy(ReturnItem::getMarketingId, Collectors.mapping(ReturnItem::getSkuId, Collectors.toList()))));
            });
            //未发货且该商品退单完成、发过货且该商品全部退单完成的需要过滤掉
            trade.setTradeItems(trade.getTradeItems().stream().filter(item ->
                    !((Objects.isNull(item.getCanReturnNum()) || item.getCanReturnNum() == 0)
                            && completedGoodsInfoIds.contains(item.getSkuId()))).collect(Collectors.toList()));
            trade.setGifts(trade.getGifts().stream().filter(item -> !completedGiftIds.contains(item.getSkuId())).collect(Collectors.toList()));
            trade.setPreferential(trade.getPreferential().stream().filter(item ->  !completedPreferentialMap
                    .getOrDefault(item.getMarketingIds().get(0), new ArrayList<>()).contains(item.getSkuId())).collect(Collectors.toList()));
        }
    }

    /**
     * 根据动态条件统计
     *
     * @param request
     * @return
     */
    public long countNum(ReturnQueryRequest request) {
        Query query = new Query(request.build());
        return mongoTemplate.count(query, ReturnOrder.class);
    }


    /**
     * 分页查询退单列表
     *
     * @param request
     * @return
     */
    public Page<ReturnOrder> page(ReturnQueryRequest request) {
        long total = this.countNum(request);

        if (total < 1) {
            return new PageImpl<>(new ArrayList<>(), request.getPageRequest(), total);
        }

        request.putSort(request.getSortColumn(), request.getSortRole());

        Query query = new Query(request.build());
        List<ReturnOrder> returnOrderList = mongoTemplate.find(query.with(request.getPageRequest()), ReturnOrder.class);

        this.fillActualReturnPrice(returnOrderList);

        // 填充退款单状态
        if (CollectionUtils.isNotEmpty(returnOrderList)) {
            List<String> ridList = returnOrderList.stream().map(ReturnOrder::getId).collect(Collectors.toList());
            Map<String, List<RefundOrder>> refundOrdersMap = refundOrderService.getRefundOrderByReturnOrderNos(ridList)
                    .stream().collect(Collectors.groupingBy(RefundOrder::getReturnOrderCode));
            returnOrderList.stream().filter(returnOrder -> refundOrdersMap.containsKey(returnOrder.getId()))
                    .forEach(returnOrder -> {
                        if (CollectionUtils.isNotEmpty(refundOrdersMap.get(returnOrder.getId()))) {
                            returnOrder.setRefundStatus(refundOrdersMap.get(returnOrder.getId()).get(0).getRefundStatus());
                        }
                    });
        }
        return new PageImpl<>(returnOrderList, request.getPageable(), total);
    }

    public List<ReturnOrder> findByCondition(ReturnQueryRequest request) {
        return mongoTemplate.find(new Query(request.build()), ReturnOrder.class);
    }

    public List<ReturnOrder> findByPage(ReturnQueryRequest request) {
        return mongoTemplate.find(new Query(request.build()).with(request.getPageRequest()).with(request.getSort()), ReturnOrder.class);
    }

    /**
     * 填充实际退款金额，捕获异常，避免对主列表产生影响
     *
     * @param iterable
     */
    private void fillActualReturnPrice(Iterable<ReturnOrder> iterable) {
        try {
            List<String> returnOrderCodes = new ArrayList<>();
            // 如果有已退款的，查询退款流水的金额
            iterable.forEach(returnOrder -> {
                if (returnOrder.getReturnFlowState() == ReturnFlowState.COMPLETED) {
                    returnOrderCodes.add(returnOrder.getId());
                }
            });

            if (returnOrderCodes.size() > 0) {
                RefundOrderRequest request = new RefundOrderRequest();
                request.setReturnOrderCodes(returnOrderCodes);
                // 查询退款单信息
                List<RefundOrder> refundOrderList = refundOrderService.findAll(request);

                if (!CollectionUtils.isEmpty(refundOrderList)) {

                    // 实退金额赋值
                    iterable.forEach(returnOrder ->
                            refundOrderList.stream()
                                    .filter(o -> Objects.equals(returnOrder.getId(), o.getReturnOrderCode()))
                                    .findFirst()
                                    .ifPresent(o -> {
                                        if (Objects.nonNull(o.getRefundBill())) {
                                            //定金预售查询尾款实退金额
                                            RefundOrder tailOrder = null;
                                            if (StringUtils.isNotBlank(returnOrder.getBusinessTailId())) {
                                                tailOrder = refundOrderService.findRefundOrderByReturnOrderNo(returnOrder.getBusinessTailId());
                                            }
                                            BigDecimal tailActualReturnPrice = BigDecimal.ZERO;
                                            if (Objects.nonNull(tailOrder) && Objects.nonNull(tailOrder.getRefundBill())) {
                                                tailActualReturnPrice = Objects.nonNull(tailOrder.getRefundBill().getActualReturnPrice()) ? tailOrder.getRefundBill().getActualReturnPrice() : BigDecimal.ZERO;
                                            }

                                            returnOrder.getReturnPrice().setActualReturnPrice(o.getRefundBill()
                                                    .getActualReturnPrice().add(tailActualReturnPrice));
                                        }
                                    }));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    public ReturnOrder findById(String rid) {
        ReturnOrder returnOrder = returnOrderRepository.findById(rid).orElse(null);
        if (returnOrder == null) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050006);
        }
        return returnOrder;
    }

    /**
     * 根据尾款单号查询退单
     *
     * @param rid
     * @return
     */
    public ReturnOrder findByBusinessTailId(String rid) {
        ReturnOrder returnOrder = returnOrderRepository.findByBusinessTailId(rid);
        if (Objects.isNull(returnOrder)) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050006);
        }
        return returnOrder;
    }

    /**
     * 查找所有退货方式
     *
     * @return
     */
    public List<ReturnWay> findReturnWay() {
        return Arrays.asList(ReturnWay.values());
    }

    /**
     * 所有退货原因
     *
     * @return
     */
    public List<ReturnReason> findReturnReason() {
        return Arrays.asList(ReturnReason.values());
    }

    /**
     * 审核退单
     * @param rid
     * @param operator
     */
    @Transactional
    public void audit(String rid, Operator operator, String addressId) {
        //查询退单详情
        ReturnOrder returnOrder = findById(rid);
        if (Objects.nonNull(returnOrder.getPtid())){
            List<ReturnOrder> returnOrderList = returnOrderRepository.findByPtid(returnOrder.getPtid());
        }
        // 查询订单相关的所有退单
        List<ReturnOrder> returnAllOrders = returnOrderRepository.findByTid(returnOrder.getTid());
        // 筛选出已完成的退单
        List<ReturnOrder> returnOrders = returnAllOrders.stream().filter(allOrder -> allOrder.getReturnFlowState() ==
                ReturnFlowState.COMPLETED)
                .collect(Collectors.toList());
        //计算所有已完成的退单总价格
        BigDecimal allOldPrice = new BigDecimal(0);
        for (ReturnOrder order : returnOrders) {
            BigDecimal p = order.getReturnPrice().getApplyStatus() ? order.getReturnPrice().getApplyPrice() : order
                    .getReturnPrice().getTotalPrice();
            allOldPrice = allOldPrice.add(p);
        }
        ReturnPrice returnPrice = returnOrder.getReturnPrice();

        BigDecimal price = returnPrice.getApplyStatus() ? returnPrice.getApplyPrice() : returnPrice.getTotalPrice();
        Optional<PayOrder> payOrderOptional = payOrderService.findPayOrderByOrderCode(returnOrder.getTid());
        if (payOrderOptional.isPresent()) {
            PayOrder payOrder = payOrderOptional.get();
            BigDecimal payOrderPrice = payOrder.getPayOrderPrice();
            Trade trade = tradeService.detail(returnOrder.getTid());
            if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods()
                    && trade.getBookingType() == BookingType.EARNEST_MONEY && StringUtils.isNotEmpty(trade.getTailOrderNo())) {
                payOrderPrice = payOrderPrice.add(payOrderService.findPayOrderByOrderCode(trade.getTailOrderNo()).orElse(new PayOrder()).getPayOrderPrice());
            }
            // 退单金额校验 退款金额不可大于可退金额
            if (payOrder.getPayType() == PayType.ONLINE && payOrderPrice.compareTo(price.add(allOldPrice)) < 0) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050036);
            }
            PayType payType = returnOrder.getPayType();
            if (PayWay.CREDIT.equals(trade.getPayWay())) {
                CreditRepayPageResponse repayPageResponse = creditRepayQueryProvider.findFinishRepayOrderByOrderId(CreditOrderQueryRequest.builder().orderId(trade.getId()).build()).getContext();
                if (repayPageResponse.getRepayWay() != null) {
                    payType = PayType.fromValue(repayPageResponse.getRepayWay());
                }
            }
            if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods() && trade.getBookingType() == BookingType.EARNEST_MONEY) {
                TradePrice tradePrice = trade.getTradePrice();
                BigDecimal needBackAmount = price;
                // 应退定金
                BigDecimal earnestPrice = BigDecimal.ZERO;
                // 应退尾款
                BigDecimal tailPrice = BigDecimal.ZERO;
                if (tradePrice.getCanBackEarnestPrice().compareTo(BigDecimal.ZERO) > 0) {
                    // 可退定金充足
                    if (tradePrice.getCanBackEarnestPrice().compareTo(needBackAmount) >= 0) {
                        tradePrice.setCanBackEarnestPrice(tradePrice.getCanBackEarnestPrice().subtract(needBackAmount));
                        earnestPrice = needBackAmount;
                        needBackAmount = BigDecimal.ZERO;
                    } else {
                        earnestPrice = tradePrice.getCanBackEarnestPrice();
                        // 还剩的退款金额
                        needBackAmount = needBackAmount.subtract(earnestPrice);
                        tradePrice.setCanBackEarnestPrice(BigDecimal.ZERO);
                    }
                }
                if (needBackAmount.compareTo(BigDecimal.ZERO) > 0) {
                    if (tradePrice.getCanBackTailPrice().compareTo(needBackAmount) < 0) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050036);
                    }
                    // 可退尾款充足
                    tradePrice.setCanBackTailPrice(tradePrice.getCanBackTailPrice().subtract(needBackAmount));
                    tailPrice = needBackAmount;
                }
                if (tailPrice.compareTo(BigDecimal.ZERO) >= 0 && StringUtils.isEmpty(returnOrder.getBusinessTailId())) {
                    returnOrder.setBusinessTailId(generatorService.generate("RT"));
                }
                // 需更新 订单
                tradeService.updateTrade(trade);
                if (earnestPrice.compareTo(BigDecimal.ZERO) >= 0) {
                    returnPrice.setEarnestPrice(earnestPrice);
                    if (returnOrder.getReturnType() == ReturnType.REFUND) {
                        refundOrderService.generateRefundOrderByReturnOrderCode(rid, returnOrder.getBuyer().getId(), earnestPrice,
                                payType, Nutils.defaultVal(payOrder.getPayOrderPoints(), 0L), true);
                    }
                }
                if (tailPrice.compareTo(BigDecimal.ZERO) >= 0) {
                    returnPrice.setTailPrice(tailPrice);
                    returnPrice.setIsTailApply(Boolean.TRUE);
                    Long points = null;
                    if (Objects.nonNull(trade.getTailOrderNo())) {
                        // 尾款从支付单查询
                        PayOrder tailPayOrder = payOrderService.findPayOrderByCode(trade.getTailOrderNo());
                        points = Objects.nonNull(tailPayOrder) && Objects.nonNull(tailPayOrder.getPayOrderPoints())
                                ? tailPayOrder.getPayOrderPoints() : 0L;
                    }

                    refundOrderService.generateTailRefundOrderByReturnOrderCode(returnOrder, returnOrder.getBuyer().getId(), tailPrice,
                            returnOrder.getPayType(), Nutils.defaultVal(points, 0L));
                }
                returnOrderService.updateReturnOrder(returnOrder);
            } else if (returnOrder.getReturnType() == ReturnType.REFUND) {
                refundOrderService.generateRefundOrderByReturnOrderCode(rid, returnOrder.getBuyer().getId(), price,
                        payType, Nutils.defaultVal(returnOrder.getReturnPoints().getApplyPoints(), 0L), false);
            }
            ReturnAddress returnAddress = null;
            //构造退单收货地址
            if (StringUtils.isNotBlank(addressId)) {
                returnAddress = wapperReturnAddress(addressId, StringUtils.isNotBlank(returnOrder.getProviderId()) ? Long.valueOf(returnOrder.getProviderId()) : returnOrder.getCompany().getStoreId());
            }

            //修改退单状态
            ReturnStateRequest request = ReturnStateRequest
                    .builder()
                    .rid(rid)
                    .operator(operator)
                    .returnEvent(ReturnEvent.AUDIT)
                    .data(returnAddress)
                    .build();
            returnFSMService.changeState(request);
            //自动发货
            autoDeliver(rid, operator);

            //售后审核通过发送MQ消息
            List<String> params;
            String pic;
            if (CollectionUtils.isNotEmpty(returnOrder.getReturnItems())) {
                params = Lists.newArrayList(returnOrder.getReturnItems().get(0).getSkuName());
                pic = returnOrder.getReturnItems().get(0).getPic();
            } else if (CollectionUtils.isNotEmpty(returnOrder.getReturnPreferential())) {
                params = Lists.newArrayList(returnOrder.getReturnPreferential().get(0).getSkuName());
                pic = returnOrder.getReturnPreferential().get(0).getPic();
            } else {
                params = Lists.newArrayList(returnOrder.getReturnGifts().get(0).getSkuName());
                pic = returnOrder.getReturnGifts().get(0).getPic();
            }
            this.sendNoticeMessage(NodeType.RETURN_ORDER_PROGRESS_RATE,
                    ReturnOrderProcessType.AFTER_SALE_ORDER_CHECK_PASS,
                    params,
                    returnOrder.getId(),
                    returnOrder.getBuyer().getId(),
                    pic,
                    returnOrder.getBuyer().getAccount());

            //代销平台退货同意退货
            sellPlatformReturnTradeService.acceptReturnOrder(returnOrder, returnAddress);
        }
    }

    /**
     * 查询可退金额
     *
     * @param rid
     * @return
     */
    public BigDecimal queryRefundPrice(String rid) {
        ReturnOrder returnOrder = findById(rid);
        return returnOrder.getReturnPrice().getTotalPrice();
    }

    /**
     * 退货发出
     *
     * @param rid
     * @param logistics
     * @param operator
     */
    @GlobalTransactional
    @Transactional
    public void deliver(String rid, ReturnLogistics logistics, Operator operator) {
        ReturnStateRequest request = ReturnStateRequest
                .builder()
                .rid(rid)
                .operator(operator)
                .returnEvent(ReturnEvent.DELIVER)
                .data(logistics)
                .build();
        returnFSMService.changeState(request);

        //同步代销平台物流单
        sellPlatformReturnTradeService.upReturnInfo(rid, logistics);
    }


    /**
     * 收货
     * @param rid
     * @param operator
     */
    @Transactional
    public void receive(String rid, Operator operator) {

        // 查询退单信息
        ReturnOrder returnOrder = findById(rid);

        // 生成财务退款单
        ReturnPrice returnPrice = returnOrder.getReturnPrice();
        if (Objects.isNull(returnPrice.getApplyPrice())) {
            return;
        }
        BigDecimal price = returnPrice.getApplyStatus() ? returnPrice.getApplyPrice() : returnPrice.getTotalPrice();
        refundOrderService.generateRefundOrderByReturnOrderCode(rid, returnOrder.getBuyer().getId(), price,
                returnOrder.getPayType(), null, false);
        ReturnStateRequest request = ReturnStateRequest
                .builder()
                .rid(rid)
                .operator(operator)
                .returnEvent(ReturnEvent.RECEIVE)
                .build();
        returnFSMService.changeState(request);


        if (isProviderFull(returnOrder)) {
            // 更新子单状态
            updateProviderTrade(returnOrder);
        }
        //判断是否全量退货完成
        if (isReturnFull(returnOrder) && providerTradeAllVoid(returnOrder)) {
            //作废主订单
            tradeService.voidTrade(returnOrder.getTid(), operator, Boolean.FALSE);
            Trade trade = tradeService.detail(returnOrder.getTid());
            trade.setRefundFlag(Boolean.TRUE);
            tradeService.updateTrade(trade);
        }
    }

    /**
     * 子订单所对应的商品是否全部退还商家
     *
     * @param returnOrder
     * @return
     */
    private boolean isProviderFull(ReturnOrder returnOrder) {

        String ptid = returnOrder.getPtid();

        if (StringUtils.isNotBlank(ptid)) {
            List<ReturnOrder> returnOrders = returnOrderRepository.findByPtid(ptid);

            List<ReturnOrder> returnOrderList = returnOrders.stream()
                    .filter(item -> item.getReturnFlowState() == ReturnFlowState.COMPLETED
                            || item.getReturnFlowState() == ReturnFlowState.RECEIVED
                            || item.getReturnType() == ReturnType.RETURN && item.getReturnFlowState() == ReturnFlowState.REJECT_REFUND)
                    .collect(Collectors.toList());
            List<ReturnItem> returnItems = returnOrderList.stream().flatMap(item -> item.getReturnItems()
                    .stream()).collect(Collectors.toList());
            List<ReturnItem> giftsReturnItems = returnOrderList.stream().flatMap(item -> item.getReturnGifts()
                    .stream()).collect(Collectors.toList());
            List<ReturnItem> preferentialReturnItems =
                    returnOrderList.stream().flatMap(item -> item.getReturnPreferential()
                    .stream()).collect(Collectors.toList());

            ProviderTrade providerTrade = providerTradeService.findbyId(ptid);
            List<TradeItem> tradeItems = providerTrade.getTradeItems();
            List<TradeItem> giftsTradeItems = providerTrade.getGifts();
            List<TradeItem> preferentialTradeItems = providerTrade.getPreferential();

            int returnNum = returnItems.stream().mapToInt(ReturnItem::getNum).sum();
            int giftsReturnNum = giftsReturnItems.stream().mapToInt(ReturnItem::getNum).sum();
            int preferentialReturnNum = preferentialReturnItems.stream().mapToInt(ReturnItem::getNum).sum();
            int totalReturnNum = returnNum + giftsReturnNum + preferentialReturnNum;
            long num = tradeItems.stream().mapToLong(TradeItem::getNum).sum();
            long giftsNum = giftsTradeItems.stream().mapToLong(TradeItem::getNum).sum();
            long preferentialNum = preferentialTradeItems.stream().mapToLong(TradeItem::getNum).sum();
            long total = num + giftsNum + preferentialNum;
            return totalReturnNum == total;
        }
        return false;
    }

    private void updateProviderTrade(ReturnOrder returnOrder) {
        String ptid = returnOrder.getPtid();
        if (StringUtils.isNotBlank(ptid)) {
            TradeGetByIdResponse tradeGetByIdResponse = providerTradeQueryProvider.providerGetById(TradeGetByIdRequest.builder().tid(ptid).build()).getContext();
            if (tradeGetByIdResponse != null && tradeGetByIdResponse.getTradeVO() != null) {
                TradeVO tradeVO = tradeGetByIdResponse.getTradeVO();
                ProviderTrade providerTrade = KsBeanUtil.convert(tradeVO, ProviderTrade.class);
                providerTrade.setOrderTag(KsBeanUtil.convert(tradeVO.getOrderTag(),OrderTag.class));
                providerTrade.setTradeBuyCycle(KsBeanUtil.convert(tradeVO.getTradeBuyCycle(), TradeBuyCycleDTO.class));
                if (providerTrade.getTradeState() != null) {
                    providerTrade.getTradeState().setFlowState(FlowState.VOID);
                    providerTradeService.updateProviderTrade(providerTrade);
                    //第三方作废
                    thirdPlatformTradeService.voidTrade(providerTrade.getId());
                }
            }
        }

    }


    /**
     * 拒绝收货
     *
     * @param rid
     * @param reason
     * @param operator
     */
    @GlobalTransactional
    @Transactional
    public void rejectReceive(String rid, String reason, Operator operator) {
        ReturnStateRequest request = ReturnStateRequest
                .builder()
                .rid(rid)
                .operator(operator)
                .returnEvent(ReturnEvent.REJECT_RECEIVE)
                .data(reason)
                .build();
        returnFSMService.changeState(request);

        // 拒绝退单时，发送MQ消息
        ReturnOrder returnOrder = this.findById(rid);
        ReturnOrderSendMQRequest sendMQRequest = ReturnOrderSendMQRequest.builder()
                .addFlag(false)
                .customerId(returnOrder.getBuyer().getId())
                .orderId(returnOrder.getTid())
                .returnId(rid)
                .build();
        returnOrderProducerService.returnOrderFlow(sendMQRequest);

        Trade trade = tradeService.detail(returnOrder.getTid());
        //预售商品退款，商家审核会把订单预售可退金额减掉，客户再次发起申请，商家审核校验金额不通过，所以商家拒绝操作要加上
        if (ReturnType.RETURN == returnOrder.getReturnType()
                && Objects.nonNull(trade.getIsBookingSaleGoods())
                && trade.getIsBookingSaleGoods()
                && trade.getBookingType() == BookingType.EARNEST_MONEY) {
            TradePrice tradePrice = trade.getTradePrice();
            tradePrice.setCanBackEarnestPrice(tradePrice.getCanBackEarnestPrice().add(returnOrder.getReturnPrice().getEarnestPrice()));
            tradePrice.setCanBackTailPrice(tradePrice.getCanBackTailPrice().add(returnOrder.getReturnPrice().getTailPrice()));
            // 需更新订单
            tradeService.updateTrade(trade);
        }

        //退货物品拒收通知发送MQ消息
        List<String> params;
        String pic;
        if (CollectionUtils.isNotEmpty(returnOrder.getReturnItems())) {
            params = Lists.newArrayList(returnOrder.getReturnItems().get(0).getSkuName(), reason);
            pic = returnOrder.getReturnItems().get(0).getPic();
        } else if (CollectionUtils.isNotEmpty(returnOrder.getReturnPreferential())) {
            params = Lists.newArrayList(returnOrder.getReturnPreferential().get(0).getSkuName());
            pic = returnOrder.getReturnPreferential().get(0).getPic();
        }else {
            params = Lists.newArrayList(returnOrder.getReturnGifts().get(0).getSkuName(), reason);
            pic = returnOrder.getReturnGifts().get(0).getPic();
        }
        this.sendNoticeMessage(NodeType.RETURN_ORDER_PROGRESS_RATE,
                ReturnOrderProcessType.RETURN_ORDER_GOODS_REJECT,
                params,
                returnOrder.getId(),
                returnOrder.getBuyer().getId(),
                pic,
                returnOrder.getBuyer().getAccount());

    }

    /**
     * 退款
     *
     * @param rid
     * @param operator
     */
    @Transactional
    @GlobalTransactional
    public void refund(String rid, Operator operator, BigDecimal price) {
        ReturnOrder returnOrder = findById(rid);
        Trade trade = tradeService.detail(returnOrder.getTid());
        DeliverStatus deliverStatus = trade.getTradeState().getDeliverStatus();
        ReturnPrice returnPrice = returnOrder.getReturnPrice();
        returnPrice.setActualReturnPrice(price);
        returnOrder.setReturnPrice(returnPrice);
        if (trade.getGrouponFlag()) {
            //拼团订单退款后的处理
            modifyGrouponInfo(returnOrder, trade);
        }
        //退单状态
        ReturnStateRequest request = ReturnStateRequest
                .builder()
                .rid(rid)
                .operator(operator)
                .returnEvent(ReturnEvent.REFUND)
                .data(price)
                .build();
        returnFSMService.changeState(request);
        trade.setRefundFlag(Boolean.TRUE);

        CreditPayInfo creditPayInfo = trade.getCreditPayInfo();
        // 如果是授信支付的订单进行退单
        if (Objects.nonNull(creditPayInfo)) {
            // 本次退单金额
            BigDecimal returnedPrice = returnOrder.getReturnPrice().getApplyPrice();
            // 查询订单下所有的退单
            List<ReturnOrder> returnOrderList = returnOrderRepository.findByTid(returnOrder.getTid());
            if (CollectionUtils.isNotEmpty(returnOrderList)) {
                // 已经完成退单的退单总金额
                BigDecimal completedReturnPrice = returnOrderList.stream()
                        .filter(returnOrder1 -> !returnOrder1.getId().equals(rid))
                        .filter(returnOrder1 -> returnOrder1.getReturnFlowState() == ReturnFlowState.COMPLETED)
                        .map(returnOrderRes -> returnOrderRes.getReturnPrice().getActualReturnPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                // 本次将要退单的金额+已经退单完成的金额
                returnedPrice = returnedPrice.add(completedReturnPrice);
            }
            // 订单金额
            BigDecimal orderPrice = trade.getTradePrice().getTotalPrice();
            // 运费包含在尾款中，退单是不退运费的，如果仅仅只是定金使用了授信支付，尾款没使用的话，
            // 此单进行了退款，此时订单金额-退单金额=运费，此单是不需要进行授信还款的
            // 如果金额都退完了 或者 只剩下运费并且是定金使用授信支付，则不需要授信还款了
            if (orderPrice.compareTo(returnedPrice) <= 0 || (orderPrice.compareTo(returnedPrice) > 0 && CreditPayState.DEPOSIT == creditPayInfo.getCreditPayState())) {
                // 不需要还款了
                trade.setNeedCreditRepayFlag(Boolean.FALSE);
            }
        }
        tradeService.updateTrade(trade);

        //修改订单状态
        tradeService.updateTradeToDelivered(returnOrder);

        String businessId = trade.getPayInfo().isMergePay() ? trade.getParentId() : trade.getId();

        if (returnOrder.getReturnType() == ReturnType.REFUND) {
            OrderTag orderTag = trade.getOrderTag();
            boolean buyCycleFlag = Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag();
            if (buyCycleFlag) {
                List<TradeDeliver> tradeDelivers = trade.getTradeDelivers();
                //没有发货，全部退需要作废子订单
                if (CollectionUtils.isEmpty(tradeDelivers)) {
                    updateProviderTrade(returnOrder);
                    //没有发货，全部退需要作废主订单
                    tradeService.voidTrade(returnOrder.getTid(), operator, Boolean.FALSE);
                    trade.getTradeState().setEndTime(LocalDateTime.now());
                }
            }else {
                // 作废子订单
                if (isProviderFull(returnOrder)) {
                    updateProviderTrade(returnOrder);
                }
                if (isReturnFull(returnOrder) && providerTradeAllVoid(returnOrder)) {
                    //作废主订单
                    tradeService.voidTrade(returnOrder.getTid(), operator, Boolean.FALSE);
                    trade.getTradeState().setEndTime(LocalDateTime.now());
                }
            }
            //仅退款退单在退款完成后释放商品库存
//            freeStock(returnOrder, trade);
        }
        //如果是未发货则释放库存
        if(Objects.equals(DeliverStatus.NOT_YET_SHIPPED, deliverStatus)) {
            freeStock(returnOrder, trade);
        }

        if (returnOrder.getPayType() == PayType.OFFLINE) {
            saveReconciliation(returnOrder, "", businessId, "", returnOrder.getReturnPrice().getApplyStatus() ?
                    returnOrder.getReturnPrice().getApplyPrice() : returnOrder.getReturnPrice().getActualReturnPrice(), "");
        }

        //唯一码业务处理
        orderPerformanceService.returnOrderPerformance(returnOrder);

    }

    /**
     * 判断子单是否全部作废，再决定是否作废主订单
     *
     * @param returnOrder
     * @return
     */
    public boolean providerTradeAllVoid(ReturnOrder returnOrder) {
        //子订单
        List<ProviderTrade> providerTrades = providerTradeService.findListByParentId(returnOrder.getTid());
        List<ProviderTrade> noVoidProviderTrades = providerTrades.stream()
                .filter(trade -> trade.getTradeState().getFlowState() != FlowState.VOID)
                .collect(Collectors.toList());
        return CollectionUtils.isEmpty(noVoidProviderTrades);
    }

    /**
     * 保存退款对账明细
     *
     * @param returnOrder
     * @param payWayStr
     */
    @Transactional
    public void saveReconciliation(ReturnOrder returnOrder, String payWayStr, String businessId, String tradeNo, BigDecimal amount, String returnOrderId) {
        RefundOrder refundOrder = refundOrderService.findRefundOrderByReturnOrderNo(StringUtils.isNotEmpty(returnOrderId) ? returnOrderId : returnOrder.getId());
        if (Objects.isNull(refundOrder)) {
            return;
        }
        addReconciliation(refundOrder, returnOrder, payWayStr, businessId, tradeNo, amount);
    }

    /**
     * 保存退款对账明细
     *
     * @param returnOrder
     * @param payWayStr
     */
    @Transactional
    public void saveReconciliation(RefundOrder refundOrder, ReturnOrder returnOrder, String payWayStr, String businessId, String tradeNo, BigDecimal amount) {
        if (Objects.isNull(refundOrder)) {
            return;
        }
        addReconciliation(refundOrder, returnOrder, payWayStr, businessId, tradeNo, amount);
    }

    /**
     * 保存退款对账明细
     *
     * @param returnOrder
     * @param payWayStr
     */
    @Transactional
    public void addReconciliation(RefundOrder refundOrder, ReturnOrder returnOrder, String payWayStr, String businessId, String tradeNo, BigDecimal amount) {
        AccountRecordAddRequest reconciliation = new AccountRecordAddRequest();
        if (Objects.nonNull(amount)) {
            reconciliation.setAmount(amount);
        } else {
            reconciliation.setAmount(returnOrder.getReturnPrice().getApplyStatus() ?
                    returnOrder.getReturnPrice().getApplyPrice() : refundOrder.getRefundBill().getActualReturnPrice());
        }
        reconciliation.setCustomerId(returnOrder.getBuyer().getId());
        reconciliation.setCustomerName(returnOrder.getBuyer().getName());
        reconciliation.setOrderCode(returnOrder.getTid());
        reconciliation.setOrderTime(returnOrder.getCreateTime());
        reconciliation.setTradeTime(Objects.isNull(refundOrder.getRefundBill()) ? LocalDateTime.now() :
                refundOrder.getRefundBill().getCreateTime());

        SystemPointsConfigQueryResponse pointsConfig = systemPointsConfigService.querySettingCache();
        final BigDecimal pointWorth = BigDecimal.valueOf(pointsConfig.getPointsWorth());

        // 实退积分数量
        Long points =
                Objects.isNull(refundOrder.getRefundBill()) || Objects.isNull(refundOrder.getRefundBill().getActualReturnPoints()) ? 0 : refundOrder.getRefundBill().getActualReturnPoints();
        reconciliation.setPoints(points);

        // 积分抵现金额
        reconciliation.setPointsPrice(BigDecimal.valueOf(points).divide(pointWorth, 2,
                RoundingMode.HALF_UP));

        if (StringUtils.isNotBlank(businessId)) {
            // 根据订单id查询流水号并存进对账明细
            PayTradeRecord payTradeRecord = payTradeRecordService.queryByBusinessId(businessId);
            if (Objects.nonNull(payTradeRecord) && StringUtils.isNotEmpty(payTradeRecord.getTradeNo())) {
                tradeNo = payTradeRecord.getTradeNo();
            }
        }
        reconciliation.setTradeNo(tradeNo);
        // 退款金额等于0 退款渠道标记为银联渠道
        if (reconciliation.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            payWayStr = "unionpay";
        }
        PayWay payWay;
        payWayStr = StringUtils.isBlank(payWayStr) ? payWayStr : payWayStr.toUpperCase();
        switch (payWayStr) {
            case "ALIPAY":
                payWay = PayWay.ALIPAY;
                break;
            case "WECHAT":
                payWay = PayWay.WECHAT;
                break;
            case "UNIONPAY":
                payWay = PayWay.UNIONPAY;
                break;
            case "UNIONPAY_B2B":
                payWay = PayWay.UNIONPAY_B2B;
                break;
            case "BALANCE":
                payWay = PayWay.BALANCE;
                break;
            case "CREDIT":
                payWay = PayWay.CREDIT;
                break;
            case "LAKALACASHIER":
                payWay = PayWay.LAKALACASHIER;
                break;
            default:
                payWay = PayWay.CASH;
        }
        reconciliation.setPayWay(payWay);
        reconciliation.setReturnOrderCode(returnOrder.getId());
        reconciliation.setStoreId(returnOrder.getCompany().getStoreId());
        reconciliation.setSupplierId(returnOrder.getCompany().getCompanyInfoId());
        reconciliation.setType((byte) 1);
        reconciliation.setGiftCardPrice(returnOrder.getReturnPrice().getGiftCardPrice());
        // 提货卡不可退款
        reconciliation.setGiftCardType(GiftCardType.CASH_CARD);
        accountRecordProvider.add(reconciliation);
    }


    /**
     * 商家退款申请(修改退单价格新增流水)
     *
     * @param returnOrder
     * @param refundComment
     * @param actualReturnPoints
     * @param operator
     */
    @Transactional
    @GlobalTransactional
    public void onlineEditPrice(ReturnOrder returnOrder, String refundComment, BigDecimal actualReturnPrice,
                                Long actualReturnPoints, Operator operator,
                                String customerAccountId, ReturnCustomerAccountDTO returnCustomerAccountDTO,BigDecimal fee) {
        ReturnPrice returnPrice = returnOrder.getReturnPrice();
        returnPrice.setFee(fee);
        returnOrder.setReturnPrice(returnPrice);
        if (StringUtils.isNotEmpty(returnOrder.getBusinessTailId()) && Objects.nonNull(returnPrice.getIsTailApply()) && returnPrice.getIsTailApply()) {
            BigDecimal refundPrice = returnPrice.getEarnestPrice().add(returnPrice.getTailPrice()).add(fee);
            if (refundPrice.compareTo(actualReturnPrice) < 0) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050042, new Object[]{refundPrice});
            }
            if (actualReturnPrice.compareTo(returnPrice.getEarnestPrice()) > 0) {
                returnPrice.setTailPrice(actualReturnPrice.subtract(returnPrice.getEarnestPrice()));
            } else {
                returnPrice.setEarnestPrice(actualReturnPrice);
                returnPrice.setTailPrice(BigDecimal.ZERO);
            }

            CustomerAccountVO customerAccount = null;
            if (Objects.nonNull(returnCustomerAccountDTO)) {
                customerAccount = KsBeanUtil.convert(returnCustomerAccountDTO, CustomerAccountVO.class);
            }

            // 如果customerAccount非空，临时账号，当快照冗余在退单
            if (Objects.nonNull(customerAccount)) {
                // 客户编号
                customerAccount.setCustomerId(returnOrder.getBuyer().getId());
                returnOrder.setCustomerAccount(customerAccount);
            } else if (null != customerAccountId) {
                //客户账号冗余至退单
                CustomerAccountOptionalRequest customerAccountOptionalRequest = new CustomerAccountOptionalRequest();
                customerAccountOptionalRequest.setCustomerAccountId(customerAccountId);
                BaseResponse<CustomerAccountOptionalResponse> customerAccountOptionalResponseBaseResponse =
                        customerAccountQueryProvider.getByCustomerAccountIdAndDelFlag(customerAccountOptionalRequest);
                CustomerAccountOptionalResponse customerAccountOptionalResponse =
                        customerAccountOptionalResponseBaseResponse.getContext();
                if (Objects.nonNull(customerAccountOptionalResponse)) {
                    customerAccount = new CustomerAccountVO();
                    returnOrder.setCustomerAccount(customerAccount);
                } else {
                    throw new SbcRuntimeException(AccountErrorCodeEnum.K020034);
                }
            }

            //订单
            Trade trade = tradeService.detail(returnOrder.getTid());
            Boolean isOff = Boolean.FALSE;
            CreditPayState creditPayState = null;
            Boolean hasRepaid = Boolean.FALSE;
            if (Objects.nonNull(trade) && Objects.nonNull(trade.getCreditPayInfo())) {
                creditPayState = trade.getCreditPayInfo().getCreditPayState();
                hasRepaid = trade.getCreditPayInfo().getHasRepaid();
                returnOrder.setCreditPayInfo(trade.getCreditPayInfo());
            }

            //定金 已还款走线下
            if (Objects.nonNull(creditPayState) && hasRepaid && creditPayState == CreditPayState.DEPOSIT) {
                isOff = Boolean.TRUE;
            }
            onlineEditPrice(returnOrder, refundComment, returnPrice.getEarnestPrice(), 0L, operator, returnOrder.getId(), isOff);

            //尾款 已还款走线下
            isOff = Boolean.FALSE;
            if (Objects.nonNull(creditPayState) && hasRepaid && creditPayState == CreditPayState.BALANCE) {
                isOff = Boolean.TRUE;
            }
            onlineEditPrice(returnOrder, refundComment, returnPrice.getTailPrice(), actualReturnPoints, operator, returnOrder.getBusinessTailId(), isOff);
        } else {
            onlineEditPrice(returnOrder, refundComment, actualReturnPrice, actualReturnPoints, operator, returnOrder.getId(), Boolean.FALSE);
        }
    }


    /**
     * 商家退款申请(修改退单价格新增流水)
     * @param returnOrder
     * @param refundComment
     * @param actualReturnPoints
     * @param operator
     */
    @Transactional
    @GlobalTransactional
    public void onlineEditPrice(ReturnOrder returnOrder, String refundComment, BigDecimal actualReturnPrice,
                                Long actualReturnPoints, Operator operator, String returnOrderNo, Boolean isOff) {
        // 查询退款单
        RefundOrder refundOrder = refundOrderService.findRefundOrderByReturnOrderNo(returnOrderNo);
        // 退款单状态不等于待退款 -- 参数错误
        if (refundOrder.getRefundStatus() != RefundStatus.TODO) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050005);
        }
        // 填充退款流水
        RefundBill refundBill;
        if ((refundBill = refundOrder.getRefundBill()) == null) {
            refundBill = new RefundBill();
            refundBill.setActualReturnPrice(Objects.isNull(actualReturnPrice) ? refundOrder.getReturnPrice() :
                    actualReturnPrice);
            refundBill.setActualReturnPoints(actualReturnPoints);
            refundBill.setCreateTime(LocalDateTime.now());
            refundBill.setRefundId(refundOrder.getRefundId());
            refundBill.setRefundComment(refundComment);
            refundBillService.save(refundBill);
        } else {
            refundBill.setActualReturnPrice(Objects.isNull(actualReturnPrice) ? refundOrder.getReturnPrice() :
                    actualReturnPrice);
            refundBill.setActualReturnPoints(actualReturnPoints);
        }
        refundBillService.saveAndFlush(refundBill);
        //设置退款单状态为待平台退款
        refundOrder.setRefundStatus(RefundStatus.APPLY);

        ReturnPrice returnPrice = returnOrder.getReturnPrice();
        //普通订单
        if (StringUtils.isBlank(returnOrder.getBusinessTailId())) {
            returnOrder.getReturnPrice().setApplyStatus(Boolean.TRUE);
            returnOrder.getReturnPrice().setApplyPrice(actualReturnPrice);
        } else if (StringUtils.isNotBlank(returnOrder.getBusinessTailId()) &&
                returnPrice.getTotalPrice().compareTo(returnPrice.getEarnestPrice().add(returnPrice.getTailPrice())) > 0) {
            //定金预售
            returnOrder.getReturnPrice().setApplyStatus(Boolean.TRUE);
            returnOrder.getReturnPrice().setApplyPrice(returnPrice.getEarnestPrice().add(returnPrice.getTailPrice()));
        }
        returnOrder.getReturnPoints().setActualPoints(actualReturnPoints);

        String detail;
        if (isOff) {
            // 生成退款记录
            detail = String.format("退单[%s]已添加线下退款单，操作人:%s", returnOrder.getId(), operator.getName());
            returnOrder.appendReturnEventLog(
                    ReturnEventLog.builder()
                            .operator(operator)
                            .eventType(ReturnEvent.REFUND.getDesc())
                            .eventTime(LocalDateTime.now())
                            .eventDetail(String.format("退单[%s]已添加线下退款单，操作人:%s", returnOrder.getId(), operator.getName()))
                            .build()
            );
            //在线退款如果有收款账户 说明是授信支付已还款走线下
            refundOrder.setPayType(PayType.OFFLINE);
        } else {
            detail = String.format("退单[%s]已添加线上退款单，操作人:%s", returnOrder.getId(), operator.getName());
            returnOrder.appendReturnEventLog(
                    ReturnEventLog.builder()
                            .operator(operator)
                            .eventType(ReturnEvent.REFUND.getDesc())
                            .eventTime(LocalDateTime.now())
                            .eventDetail(detail)
                            .build()
            );
        }

        refundOrderRepository.saveAndFlush(refundOrder);
        returnOrderService.updateReturnOrder(returnOrder);

        this.operationLogMq.convertAndSend(operator, ReturnEvent.REFUND.getDesc(), detail);

        //退款审核通过发送MQ消息
        List<String> params;
        String pic;
        if (CollectionUtils.isNotEmpty(returnOrder.getReturnItems())) {
            params = Lists.newArrayList(returnOrder.getReturnItems().get(0).getSkuName());
            pic = returnOrder.getReturnItems().get(0).getPic();
        } else if (CollectionUtils.isNotEmpty(returnOrder.getReturnPreferential())) {
            params = Lists.newArrayList(returnOrder.getReturnPreferential().get(0).getSkuName());
            pic = returnOrder.getReturnPreferential().get(0).getPic();
        } else {
            params = Lists.newArrayList(returnOrder.getReturnGifts().get(0).getSkuName());
            pic = returnOrder.getReturnGifts().get(0).getPic();
        }

        // 社区团购佣金重新计算
        OrderTagVO orderTag = returnOrder.getTradeVO().getOrderTag();
        if (Objects.nonNull(orderTag) && orderTag.getCommunityFlag()){
            tradeService.updateCommunityTradeCommission(returnOrder);
        }

        this.sendNoticeMessage(NodeType.RETURN_ORDER_PROGRESS_RATE,
                ReturnOrderProcessType.REFUND_CHECK_PASS,
                params,
                returnOrder.getId(),
                returnOrder.getBuyer().getId(),
                pic,
                returnOrder.getBuyer().getAccount());

    }


    /**
     * 在线退款
     *
     * @param returnOrder
     * @param refundOrder
     * @param operator
     */
    @Transactional
    @GlobalTransactional
    public void refundOnline(ReturnOrder returnOrder, RefundOrder refundOrder, Operator operator) {
        try {
            RefundBill refundBill;

            // 交易记录查询流程
            // 1.判断是尾款退款使用BusinessTailId查询
            // 2.否则使用退单ID查询
            // 3.如果查询不出，使用退单对应订单查询交易记录
            //   如果是尾款退单并且订单TailOrderNo不为空使用TailOrderNo查询
            //   否则根据是否合并支付判断使用订单号或者父订单号查询
            PayTradeRecord payTradeRecord = null;
            if (RefundChannel.TAIL == refundOrder.getRefundChannel()) {
                payTradeRecord = payTradeRecordService.queryByBusinessId(returnOrder.getBusinessTailId());
            } else {
                payTradeRecord = payTradeRecordService.queryByBusinessId(returnOrder.getId());
            }
            if (Objects.isNull(payTradeRecord)) {
                Trade trade = tradeService.detail(returnOrder.getTid());
                String tid = null;
                if (RefundChannel.TAIL == refundOrder.getRefundChannel() && Objects.nonNull(trade.getTailOrderNo())) {
                    tid = trade.getTailOrderNo();
                } else {
                    tid = trade.getPayInfo().isMergePay() ? trade.getParentId() : trade.getId();
                }
                payTradeRecord = payTradeRecordService.queryByBusinessId(tid);
            }
            PayChannelItemResponse channelItemResponse = paySettingQueryProvider.getChannelItemById(new
                    ChannelItemByIdRequest(Objects.isNull(payTradeRecord) || Objects.isNull(
                    payTradeRecord.getChannelItemId()) ? Constants.DEFAULT_RECEIVABLE_ACCOUNT :
                    payTradeRecord.getChannelItemId())).getContext();
            // 退款流水保存
            if ((refundBill = refundOrder.getRefundBill()) == null) {
                refundBill = new RefundBill();
                refundBill.setPayChannel(channelItemResponse.getName());
                refundBill.setPayChannelId(channelItemResponse.getId());
                refundBill.setActualReturnPrice(refundOrder.getReturnPrice());
                refundBill.setActualReturnPoints(refundOrder.getReturnPoints());
                refundBill.setCreateTime(LocalDateTime.now());
                refundBill.setRefundId(refundOrder.getRefundId());
                refundBill.setOfflineAccountId(returnOrder.getOfflineAccountId());
                log.info("refundOnline,refundOrder================:{}", JSON.toJSONString(refundOrder));
                refundBillService.save(refundBill);
            } else {
                refundBill.setPayChannel(channelItemResponse.getName());
                refundBill.setPayChannelId(channelItemResponse.getId());
                refundBill.setCreateTime(LocalDateTime.now());
                refundBill.setOfflineAccountId(returnOrder.getOfflineAccountId());
                log.info("refundOnline,refundOrder================:{}", JSON.toJSONString(refundOrder));
                refundBillService.saveAndFlush(refundBill);
            }
            if (returnOrder.getReturnFlowState() != ReturnFlowState.COMPLETED && (Objects.isNull(refundOrder.getRefundChannel()) || refundOrder.getRefundChannel() == RefundChannel.EARNEST)) {
                //退款
                refund(returnOrder.getId(), operator, returnOrder.getReturnPrice().getApplyStatus() ?
                        returnOrder.getReturnPrice().getApplyPrice() : refundBill.getActualReturnPrice());
                refundOrder.setRefundStatus(RefundStatus.FINISH);
                // 更改订单状态
                refundOrderRepository.save(refundOrder);
                //保存退款对账明细
                String tradeNo = Objects.nonNull(payTradeRecord) ? payTradeRecord.getTradeNo() : "";
                saveReconciliation(refundOrder, returnOrder, channelItemResponse.getChannel(), "", tradeNo, returnOrder.getReturnPrice().getEarnestPrice());
                //判断是否是跨境退单,非跨境订单退优惠券，跨境订单不退优惠券
                if(Objects.isNull(returnOrder.getReturnOrderType()) ||
                        (Objects.nonNull(returnOrder.getReturnOrderType()) &&
                        returnOrder.getReturnOrderType() != ReturnOrderType.CROSS_BORDER)){
                    tradeService.returnCoupon(returnOrder.getTid());
                }

                //处理订单退礼品卡
                this.returnGiftCard(returnOrder);

                // 退单完成时，发送MQ消息
                ReturnOrderSendMQRequest sendMQRequest = ReturnOrderSendMQRequest.builder()
                        .addFlag(false)
                        .customerId(returnOrder.getBuyer().getId())
                        .orderId(returnOrder.getTid())
                        .returnId(returnOrder.getId())
                        .build();
                returnOrderProducerService.returnOrderFlow(sendMQRequest);
                // 返还限售记录 —— 自动退款
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        orderProducerService.backRestrictedPurchaseNum(null, returnOrder.getId(), BackRestrictedType.REFUND_ORDER);
                    }
                });
            }
            if (Objects.nonNull(refundOrder.getRefundChannel()) && refundOrder.getRefundChannel() == RefundChannel.TAIL) {
                RefundOrder newRefundOrder = refundOrderRepository.findById(refundOrder.getRefundId()).orElse(null);
                if (Objects.isNull(newRefundOrder) || newRefundOrder.getRefundStatus() != RefundStatus.FINISH) {
                    //保存退款对账明细
                    refundOrder.setRefundStatus(RefundStatus.FINISH);
                    // 更改订单状态
                    refundOrderRepository.saveAndFlush(refundOrder);
                    String tradeNo = Objects.nonNull(payTradeRecord) ? payTradeRecord.getTradeNo() : "";
                    saveReconciliation(returnOrder, channelItemResponse.getChannel(), "", tradeNo, returnOrder.getReturnPrice().getTailPrice(), returnOrder.getBusinessTailId());
                    ReturnOrder newReturnOrder = findById(returnOrder.getId());
                    newReturnOrder.getReturnPrice().setActualReturnPrice(newReturnOrder.getReturnPrice().getEarnestPrice().add(newReturnOrder.getReturnPrice().getTailPrice()));
                    returnOrderService.updateReturnOrder(newReturnOrder);
                }
            }

            if (null != returnOrder.getOfflineAccountId()) {
                // 线下退款完成，发送MQ消息
                ReturnOrderSendMQRequest sendMQRequest = ReturnOrderSendMQRequest.builder()
                        .addFlag(false)
                        .customerId(returnOrder.getBuyer().getId())
                        .orderId(returnOrder.getTid())
                        .returnId(returnOrder.getId())
                        .build();
                returnOrderProducerService.returnOrderFlow(sendMQRequest);
            }

        } catch (SbcRuntimeException e) {
            log.error("{}退单状态修改异常,error={}", returnOrder.getId(), e);
            throw e;
//            throw new SbcRuntimeException(e.getErrorCode(), e.getParams());
        }
        // 异步处理退款发送
        orderMqConsumerService.sendReturnMiniProgramMsg(returnOrder);
    }

    /**
     * b2b线下退款
     *
     * @param rid
     * @param operator
     */
    @Transactional
    public void refundOffline(String rid, CustomerAccountAddOrModifyDTO customerAccount, RefundBill refundBill,
                              Operator operator) {
        // 查询退单信息
        ReturnOrder returnOrder = findById(rid);

        // 如果offlineAccount非空，新增后使用
        if (Objects.nonNull(customerAccount)) {

            CustomerAccountByCustomerIdRequest customerAccountByCustomerIdRequest =
                    CustomerAccountByCustomerIdRequest.builder().customerId(customerAccount.getCustomerId()).build();
            //查询会员有几条银行账户信息
            BaseResponse<CustomerAccountByCustomerIdResponse> integerBaseResponse =
                    customerAccountQueryProvider.countByCustomerId(customerAccountByCustomerIdRequest);
            Integer count = integerBaseResponse.getContext().getResult();
            if (null != count && count >= Constants.FIVE) {
                //会员最多有5条银行账户信息
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010009);
            }

            CustomerAccountAddRequest customerAccountAddRequest = new CustomerAccountAddRequest();
            BeanUtils.copyProperties(customerAccount, customerAccountAddRequest);

            // 客户编号
            customerAccountAddRequest.setCustomerId(returnOrder.getBuyer().getId());
            customerAccountAddRequest.setEmployeeId(operator.getUserId());
            BaseResponse<CustomerAccountAddResponse> customerAccountAddResponseBaseResponse =
                    customerAccountProvider.add(customerAccountAddRequest);
            CustomerAccountAddResponse customerAccountAddResponse = customerAccountAddResponseBaseResponse.getContext();

            // 设置财务退款单号
            refundBill.setCustomerAccountId(customerAccountAddResponse.getCustomerAccountId());
        }

        // 根据退款单号查询财务退款单据的编号
        RefundOrder refundOrder = refundOrderService.findRefundOrderByReturnOrderNo(rid);
        refundBill.setRefundId(refundOrder.getRefundId());

        // 生成退款记录
        refundBillService.save(refundBill);

        // 退单状态修改
        refund(rid, operator, refundBill.getActualReturnPrice());

    }

    /**
     * 商家线下退款
     *
     * @param rid
     * @param operator
     */
    @Transactional
    @GlobalTransactional
    public void supplierRefundOffline(String rid, CustomerAccountVO customerAccount, RefundBill refundBill, Operator
            operator,BigDecimal fee) {
        // 查询退单信息
        ReturnOrder returnOrder = findById(rid);
        ReturnPrice returnPrice = returnOrder.getReturnPrice();
        returnPrice.setFee(fee);
        returnOrder.setReturnPrice(returnPrice);
//        BigDecimal price = returnOrder.getReturnItems().stream().map(r -> r.getSplitPrice())
//                .reduce(BigDecimal::add).get();
//
//
//        if(refundBill.getActualReturnPrice().compareTo(price) == 1){
//            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
//        }

        // 积分信息
        returnOrder.getReturnPoints().setActualPoints(refundBill.getActualReturnPoints());
        // 退货金额
        if (refundBill.getActualReturnPrice().compareTo(returnOrder.getReturnPrice().getApplyPrice()) < 0) {
            returnOrder.getReturnPrice().setApplyStatus(Boolean.TRUE);
            returnOrder.getReturnPrice().setApplyPrice(refundBill.getActualReturnPrice());
        }
        // 如果customerAccount非空，临时账号，当快照冗余在退单
        if (Objects.nonNull(customerAccount)) {
            // 客户编号
            customerAccount.setCustomerId(returnOrder.getBuyer().getId());
            returnOrder.setCustomerAccount(customerAccount);
        } else if ( StringUtils.isNotBlank(refundBill.getCustomerAccountId())){
            //客户账号冗余至退单
            CustomerAccountOptionalRequest customerAccountOptionalRequest = new CustomerAccountOptionalRequest();
            customerAccountOptionalRequest.setCustomerAccountId(refundBill.getCustomerAccountId());
            BaseResponse<CustomerAccountOptionalResponse> customerAccountOptionalResponseBaseResponse =
                    customerAccountQueryProvider.getByCustomerAccountIdAndDelFlag(customerAccountOptionalRequest);
            CustomerAccountOptionalResponse customerAccountOptionalResponse =
                    customerAccountOptionalResponseBaseResponse.getContext();
            if (Objects.nonNull(customerAccountOptionalResponse)) {
                customerAccount = new CustomerAccountVO();
                KsBeanUtil.copyPropertiesThird(customerAccountOptionalResponse, customerAccount);
                returnOrder.setCustomerAccount(customerAccount);
            } else {
                throw new SbcRuntimeException(AccountErrorCodeEnum.K020034);
            }
        }
        // 根据退款单号查询财务退款单据的编号
        RefundOrder refundOrder = refundOrderService.findRefundOrderByReturnOrderNo(rid);
        if (refundOrder.getRefundStatus() == RefundStatus.APPLY || returnOrder.getReturnFlowState() ==
                ReturnFlowState.REJECT_REFUND) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050005, new Object[]{"退款"});
        }
        if (!Objects.isNull(refundOrder.getRefundBill())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        refundBill.setRefundId(refundOrder.getRefundId());

        // 生成退款记录
        refundBillService.save(refundBill);
        returnOrder.appendReturnEventLog(
                ReturnEventLog.builder()
                        .operator(operator)
                        .eventType(ReturnEvent.REFUND.getDesc())
                        .eventTime(LocalDateTime.now())
                        .eventDetail(String.format("退单[%s]已添加线下退款单，操作人:%s", returnOrder.getId(), operator.getName()))
                        .build()
        );
        refundOrder.setRefundStatus(RefundStatus.APPLY);

        //校验订单是否是授信支付 和 还款状态
        Trade trade = tradeService.detail(returnOrder.getTid());
        if (Objects.nonNull(trade) && Objects.nonNull(trade.getCreditPayInfo())) {
            //如果已经还款 则更改退款方式为线下退款
            if (trade.getCreditPayInfo().getHasRepaid()) {
                refundOrder.setPayType(PayType.OFFLINE);
                log.info("supplierRefundOffline credit offLine");
            } else {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050005);
            }
        }
        refundOrderRepository.save(refundOrder);
        returnOrderService.updateReturnOrder(returnOrder);
        // 返还限售记录 —— 线下退款
        orderProducerService.backRestrictedPurchaseNum(null, returnOrder.getId(), BackRestrictedType.REFUND_ORDER);
    }


    /**
     * s2b线下退款
     *
     * @param rid
     * @param operator
     */
    @Transactional
    @GlobalTransactional
    public void s2bBoosRefundOffline(String rid, RefundBill refundBill, Operator operator, String tid) {
        //修改退款单状态
        RefundOrder refundOrder = refundOrderService.findRefundOrderByReturnOrderNo(rid);
        ReturnOrder returnOrder = this.findById(rid);
        //订单信息
        Trade trade = tradeService.detail(tid);

        RefundBill result = refundBillService.save(refundBill).orElse(null);
        if (Objects.nonNull(result)) {
            refundOrder.setRefundBill(result);
        }
        // 退单状态修改
        refund(rid, operator, refundBill.getActualReturnPrice());
        refundOrder.setRefundStatus(RefundStatus.FINISH);
        refundOrderRepository.saveAndFlush(refundOrder);
        tradeService.returnCoupon(tid);
        //退礼品卡
        this.returnGiftCard(returnOrder);
        // 线下退款完成，发送MQ消息
        ReturnOrderSendMQRequest sendMQRequest = ReturnOrderSendMQRequest.builder()
                .addFlag(false)
                .customerId(returnOrder.getBuyer().getId())
                .orderId(returnOrder.getTid())
                .returnId(rid)
                .build();
        returnOrderProducerService.returnOrderFlow(sendMQRequest);


    }


    /**
     * 拒绝退款
     *
     * @param rid
     * @param operator
     */
    @Transactional
    @GlobalTransactional
    public void refundReject(String rid, String reason, Operator operator) {
        ReturnOrder returnOrder = findById(rid);
        TradeStatus tradeStatus = payTradeRecordService.queryRefundResult(returnOrder.getId(), returnOrder.getTid());
        if (tradeStatus != null) {
            if (tradeStatus == TradeStatus.SUCCEED) {
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060032);
            } else if (tradeStatus == TradeStatus.PROCESSING) {
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060033);
            }
        }

        //验证订单是否是视频号订单
        if(Objects.nonNull(returnOrder.getSellPlatformType()) && SellPlatformType.WECHAT_VIDEO.equals(returnOrder.getSellPlatformType())) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070097);
        }

        //修改财务退款单状态
        RefundOrder refundOrder = refundOrderService.findRefundOrderByReturnOrderNo(rid);
        if (refundOrder.getRefundStatus().equals(RefundStatus.APPLY)) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050005);
        }
        refundOrderService.refuse(refundOrder.getRefundId(), reason);
        //修改退单状态
        ReturnStateRequest request = ReturnStateRequest
                .builder()
                .rid(rid)
                .operator(operator)
                .returnEvent(ReturnEvent.REJECT_REFUND)
                .data(reason)
                .build();
        returnFSMService.changeState(request);
        Trade trade = tradeService.detail(returnOrder.getTid());
        //预售商品退款，商家审核会把订单预售可退金额减掉，客户再次发起申请，商家审核校验金额不通过，所以商家拒绝操作要加上
        if (ReturnType.REFUND == returnOrder.getReturnType()
                && Objects.nonNull(trade.getIsBookingSaleGoods())
                && trade.getIsBookingSaleGoods()
                && trade.getBookingType() == BookingType.EARNEST_MONEY) {
            TradePrice tradePrice = trade.getTradePrice();
            tradePrice.setCanBackEarnestPrice(returnOrder.getReturnPrice().getEarnestPrice());
            tradePrice.setCanBackTailPrice(returnOrder.getReturnPrice().getTailPrice());
            // 需更新订单
            tradeService.updateTrade(trade);
        }

        //退货拒绝退款修改订单状态
        if (ReturnType.RETURN == returnOrder.getReturnType()) {
            tradeService.updateTradeToDelivered(returnOrder);
        }

        // 拒绝退款时，发送MQ消息
        ReturnOrderSendMQRequest sendMQRequest = ReturnOrderSendMQRequest.builder()
                .addFlag(false)
                .customerId(returnOrder.getBuyer().getId())
                .orderId(returnOrder.getTid())
                .returnId(rid)
                .build();
        returnOrderProducerService.returnOrderFlow(sendMQRequest);

        //退款审核未通过发送MQ消息
        List<String> params;
        String pic;
        if (CollectionUtils.isNotEmpty(returnOrder.getReturnItems())) {
            params = Lists.newArrayList(returnOrder.getReturnItems().get(0).getSkuName(), reason);
            pic = returnOrder.getReturnItems().get(0).getPic();
        } else if (CollectionUtils.isNotEmpty(returnOrder.getReturnPreferential())) {
            params = Lists.newArrayList(returnOrder.getReturnPreferential().get(0).getSkuName());
            pic = returnOrder.getReturnPreferential().get(0).getPic();
        } else {
            params = Lists.newArrayList(returnOrder.getReturnGifts().get(0).getSkuName(), reason);
            pic = returnOrder.getReturnGifts().get(0).getPic();
        }
        this.sendNoticeMessage(NodeType.RETURN_ORDER_PROGRESS_RATE,
                ReturnOrderProcessType.REFUND_CHECK_NOT_PASS,
                params,
                returnOrder.getId(),
                returnOrder.getBuyer().getId(),
                pic,
                returnOrder.getBuyer().getAccount());
    }

    /**
     * 拒绝退款
     *
     * @param rid
     * @param operator
     */
    @Transactional
    public void refundRejectAndRefuse(String rid, String reason, Operator operator) {
        refundOrderService.refuse(rid, reason);
        refundOrderService.findById(rid).ifPresent(refundOrderResponse -> {
            ReturnOrder returnOrder = findById(refundOrderResponse.getReturnOrderCode());
            TradeStatus tradeStatus = payTradeRecordService.queryRefundResult(returnOrder.getId(), returnOrder.getTid());
            if (tradeStatus != null) {
                if (tradeStatus == TradeStatus.SUCCEED) {
                    throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060032);
                } else if (tradeStatus == TradeStatus.PROCESSING) {
                    throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060033);
                }
            }
            //修改财务退款单状态
            RefundOrder refundOrder =
                    refundOrderService.findRefundOrderByReturnOrderNo(refundOrderResponse.getReturnOrderCode());
            if (refundOrder.getRefundStatus().equals(RefundStatus.APPLY)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050005);
            }
            //修改退单状态
            ReturnStateRequest request = ReturnStateRequest
                    .builder()
                    .rid(refundOrderResponse.getReturnOrderCode())
                    .operator(operator)
                    .returnEvent(ReturnEvent.REJECT_REFUND)
                    .data(refundOrderResponse.getRefuseReason())
                    .build();
            returnFSMService.changeState(request);

            //退货拒绝退款修改订单状态
            if (ReturnType.RETURN == returnOrder.getReturnType()) {
                tradeService.updateTradeToDelivered(returnOrder);
            }
        });
    }

    /**
     * 驳回退单
     *
     * @param rid
     * @param operator
     */
    @GlobalTransactional
    @Transactional
    public void cancel(String rid, Operator operator, String remark) {

        ReturnOrder returnOrder = this.findById(rid);

        ReturnStateRequest request = ReturnStateRequest
                .builder()
                .rid(rid)
                .operator(operator)
                .returnEvent(ReturnEvent.VOID)
                .data(remark)
                .build();
        returnFSMService.changeState(request);

        // 取消退单时，发送MQ消息
        ReturnOrderSendMQRequest sendMQRequest = ReturnOrderSendMQRequest.builder()
                .addFlag(false)
                .customerId(Objects.isNull(returnOrder) || Objects.isNull(returnOrder.getBuyer()) ? null : returnOrder.getBuyer().getId())
                .orderId(Nutils.nonNullActionRt(returnOrder, ReturnOrder::getTid, null))
                .returnId(rid)
                .build();
        returnOrderProducerService.returnOrderFlow(sendMQRequest);

        //售后审核未通过发送MQ消息
        List<String> params;
        String pic;
        if (CollectionUtils.isNotEmpty(returnOrder.getReturnItems())) {
            params = Lists.newArrayList(returnOrder.getReturnItems().get(0).getSkuName(), remark);
            pic = returnOrder.getReturnItems().get(0).getPic();
        } else if (CollectionUtils.isNotEmpty(returnOrder.getReturnPreferential())) {
            params = Lists.newArrayList(returnOrder.getReturnPreferential().get(0).getSkuName());
            pic = returnOrder.getReturnPreferential().get(0).getPic();
        } else {
            params = Lists.newArrayList(returnOrder.getReturnGifts().get(0).getSkuName(), remark);
            pic = returnOrder.getReturnGifts().get(0).getPic();
        }
        this.sendNoticeMessage(NodeType.RETURN_ORDER_PROGRESS_RATE,
                ReturnOrderProcessType.AFTER_SALE_ORDER_CHECK_NOT_PASS,
                params,
                returnOrder.getId(),
                returnOrder.getBuyer().getId(),
                pic,
                returnOrder.getBuyer().getAccount());

        //取消代收平台退单
        sellPlatformReturnTradeService.cancelReturnOrder(returnOrder);

    }

    /**
     * 查询订单详情,如已发货则带出可退商品数
     * @param tid
     * @return
     */
    public Trade queryCanReturnItemNumByTid(String tid) {
        Trade trade = tradeService.detail(tid);
        if (Objects.isNull(trade)) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050010, new Object[]{tid});
        }
        //校验支付状态
        PayOrderResponse payOrder = payOrderService.findPayOrder(trade.getId());
        if (payOrder.getPayOrderStatus() != PayOrderStatus.PAYED) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050015);
        }
        if (trade.getTradeState().getFlowState() == FlowState.GROUPON) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050050);
        }
        DeliverStatus deliverStatus = trade.getTradeState().getDeliverStatus();
        if (deliverStatus != DeliverStatus.VOID) {
            //计算商品可退数 商品可退数量=购买数量-退货处理中&退货成功的商品数量
            Map<String, Integer> map = findLeftItems(trade);
            trade.getTradeItems().forEach(
                    item -> item.setCanReturnNum(map.get(item.getSkuId()))
            );
            //计算赠品可退数 赠品可退数量=购买数量-退货处理中&退货成功的赠品数量
            if (CollectionUtils.isNotEmpty(trade.getGifts())) {
                Map<Long, Map<String, Long>> giftMap = findLeftGiftItems(trade);
                if(giftMap.keySet().size() > 0){
                    trade.getGifts().forEach(
                            item -> item.setCanReturnNum(giftMap.getOrDefault(item.getMarketingIds().get(0),
                                    new HashMap<>()).get(item.getSkuId()).intValue())
                    );
                }
            }

            if (CollectionUtils.isNotEmpty(trade.getPreferential())) {
                Map<Long, Map<String, Long>> preferentialMap = findLeftPreferentialItems(trade);
                trade.getPreferential().forEach(
                        item -> item.setCanReturnNum(preferentialMap.getOrDefault(item.getMarketingIds().get(0),
                                new HashMap<>()).get(item.getSkuId()).intValue())
                );
            }
        }
        List<ReturnOrder> returnsNotVoid = findReturnsNotVoid(tid);
        // 已退积分
        Long retiredPoints = returnsNotVoid.stream()
                .filter(o -> Objects.nonNull(o.getReturnPoints()) && Objects.nonNull(o.getReturnPoints().getActualPoints()))
                .map(o -> o.getReturnPoints().getActualPoints())
                .reduce((long) 0, Long::sum);
        // 可退积分
        Long points = trade.getTradePrice().getPoints() == null ? Long.valueOf("0") : trade.getTradePrice().getPoints();
        trade.setCanReturnPoints(points - retiredPoints);
        // 可退金额
        BigDecimal totalPrice = trade.getTradeItems().stream()
                .map(TradeItem::getSplitPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
//        totalPrice = returnTradeIncision.calcCanReturnPrice(totalPrice,trade);
        BigDecimal retiredPrice = returnsNotVoid.stream()
                .filter(o -> Objects.nonNull(o.getReturnPrice().getActualReturnPrice()))
                .map(o -> o.getReturnPrice().getActualReturnPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal canReturnPrice = totalPrice.subtract(retiredPrice);
        // 跨境税费
        //canReturnPrice = returnCrossBorderIncision.cala()
        trade.setCanReturnPrice(canReturnPrice);
        // 填充订单商品providerID  这里似乎不需要填充，tradeItem中已存放了providerId
        trade.getTradeItems().forEach(tradeItem -> {
            String skuId = tradeItem.getSkuId();
            GoodsInfoByIdResponse goodsInfoVo = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder().goodsInfoId(skuId).build()).getContext();
            if (goodsInfoVo != null) {
                tradeItem.setProviderId(goodsInfoVo.getProviderId());
            }
        });
        //填充赠品 providerID
        trade.getGifts().forEach(tradeItem -> {
            String skuId = tradeItem.getSkuId();
            GoodsInfoByIdResponse goodsInfoVo = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder().goodsInfoId(skuId).build()).getContext();
            if (goodsInfoVo != null) {
                tradeItem.setProviderId(goodsInfoVo.getProviderId());
            }
        });

        //填充加价购商品
        trade.getPreferential().forEach(tradeItem -> {
            String skuId = tradeItem.getSkuId();
            GoodsInfoByIdResponse goodsInfoVo = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder().goodsInfoId(skuId).build()).getContext();
            if (goodsInfoVo != null) {
                tradeItem.setProviderId(goodsInfoVo.getProviderId());
            }
        });

        // <sku, 已退积分>
        Map<String, Long> skuPointMap =
                returnsNotVoid.stream().flatMap(item -> item.getReturnItems().stream())
                        .collect(Collectors.groupingBy(ReturnItem::getSkuId,
                                Collectors.summingLong(returnItem -> Optional.ofNullable(returnItem.getSplitPoint()).orElse(0L))));
        trade.getTradeItems().forEach(tradeItem -> {
            if (skuPointMap.get(tradeItem.getSkuId()) != null && Objects.nonNull(tradeItem.getPoints())){
                tradeItem.setCanReturnPoint(tradeItem.getPoints() - skuPointMap.get(tradeItem.getSkuId()));
            } else {
                tradeItem.setCanReturnPoint(tradeItem.getPoints());
            }
        });


        //换购商品积分处理
        Map<Long,Map<String, Long>> preferSkuPointMap = returnsNotVoid.stream()
                .map(ReturnOrder::getReturnPreferential)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(ReturnItem::getMarketingId,
                        Collectors.groupingBy(ReturnItem::getSkuId,
                                Collectors.summingLong(returnItem -> Optional.ofNullable(returnItem.getSplitPoint()).orElse(0L)))));

        if(MapUtils.isNotEmpty(preferSkuPointMap)){
            trade.getPreferential().forEach(tradeItem -> {
                Long point =
                        preferSkuPointMap.getOrDefault(tradeItem.getMarketingIds().get(0), new HashMap<>()).get(tradeItem.getSkuId());
                if (point != null &&
                        Objects.nonNull(tradeItem.getPoints())){
                    tradeItem.setCanReturnPoint(tradeItem.getPoints() - point);
                } else {
                    tradeItem.setCanReturnPoint(tradeItem.getPoints());
                }
            });
        }


        return trade;
    }

    /**
     * 查询退单详情,如已发货则带出可退商品数
     *
     * @param rid
     * @return
     */
    public ReturnOrder queryCanReturnItemNumById(String rid) {
        ReturnOrder returnOrder = findById(rid);
        Trade trade = tradeService.detail(returnOrder.getTid());
        if (trade.getTradeState().getDeliverStatus() != DeliverStatus.NOT_YET_SHIPPED && trade.getTradeState()
                .getDeliverStatus() != DeliverStatus.VOID) {
            //计算商品可退数
            Map<String, Integer> map = findLeftItems(trade);
            returnOrder.getReturnItems().forEach(item -> item.setCanReturnNum(map.get(item.getSkuId())));
        }
        return returnOrder;
    }

    /**
     * 退款单作废状态扭转
     */
    @Transactional
    public void reverse(String rid, Operator operator) {
        //删除对账记录
        AccountRecordDeleteByReturnOrderCodeAndTypeRequest deleteRequest = new
                AccountRecordDeleteByReturnOrderCodeAndTypeRequest();
        deleteRequest.setReturnOrderCode(rid);
        deleteRequest.setAccountRecordType(AccountRecordType.REFUND);
        accountRecordProvider.deleteByReturnOrderCodeAndType(deleteRequest);
        ReturnOrder returnOrder = returnOrderRepository.findById(rid).orElseGet(() -> new ReturnOrder());
        ReturnEvent event = returnOrder.getReturnType() == ReturnType.RETURN ? ReturnEvent.REVERSE_RETURN :
                ReturnEvent.REVERSE_REFUND;
        ReturnStateRequest request = ReturnStateRequest
                .builder()
                .rid(rid)
                .operator(operator)
                .returnEvent(event)
                .build();
        returnFSMService.changeState(request);
    }

    /**
     * 修改退单
     *
     * @param newReturnOrder
     * @param operator
     */
    @GlobalTransactional
    @Transactional
    public void remedy(ReturnOrder newReturnOrder, Operator operator) {
        ReturnStateRequest request = ReturnStateRequest
                .builder()
                .rid(newReturnOrder.getId())
                .operator(operator)
                .returnEvent(ReturnEvent.REMEDY)
                .data(newReturnOrder)
                .build();
        returnFSMService.changeState(request);
    }

    /**
     * 订单中可退货的数量
     * @param trade
     */
    public Map<String, Integer> findLeftItems(Trade trade) {
        //获取是否是虚拟订单的标识
        OrderTag orderTag = trade.getOrderTag();
        boolean buyCycleFlag = Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag();
        boolean electronicCouponFlag = Objects.nonNull(orderTag) && orderTag.getElectronicCouponFlag();
        boolean virtualFlag = Objects.nonNull(orderTag) && orderTag.getVirtualFlag();
        // 是否允许在途退货
        if (!electronicCouponFlag && !virtualFlag && !buyCycleFlag && Boolean.FALSE.equals(trade.getTransitReturn()) ) {
            if (!(trade.getTradeState().getDeliverStatus() == DeliverStatus.NOT_YET_SHIPPED) && !(trade.getTradeState()
                    .getFlowState() == FlowState.COMPLETED)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050005);
            }
        } else {
            if ((trade.getTradeState().getDeliverStatus() == DeliverStatus.VOID) && (trade.getTradeState()
                    .getFlowState() != FlowState.COMPLETED)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050005);
            }
        }
        return this.getLeftItems(trade);
    }

    /**
     * 计算订单中可退货的数量
     * @param trade
     * @return
     */
    public Map<String, Integer> getLeftItems(Trade trade){
        String id = trade.getId().startsWith(GeneratorService._PREFIX_PROVIDER_TRADE_ID)
                || trade.getId().startsWith(GeneratorService._PREFIX_STORE_TRADE_ID) ? trade.getParentId() : trade.getId();
        Map<String, Long> map;
        if (Objects.nonNull(trade.getOrderTag()) && Boolean.TRUE.equals(trade.getOrderTag().getBuyCycleFlag())) {
            map = trade.getTradeItems().stream().collect(Collectors.toMap(TradeItem::getSkuId,
                    item -> item.getNum() * item.getBuyCycleNum()));
        } else {
            map = trade.getTradeItems().stream().collect(Collectors.toMap(TradeItem::getSkuId,
                    TradeItem::getNum));
        }
        List<ReturnItem> allReturnItems = this.findReturnsNotVoid(id).stream()
                .map(ReturnOrder::getReturnItems)
                .reduce(new ArrayList<>(), (a, b) -> {
                    a.addAll(b);
                    return a;
                });
        Map<String, List<ReturnItem>> groupMap = IteratorUtils.groupBy(allReturnItems, ReturnItem::getSkuId);
        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            String key = entry.getKey();
                            Integer total = map.get(key).intValue();
                            Integer returned = 0;
                            if (groupMap.get(key) != null) {
                                returned = groupMap.get(key).stream().mapToInt(ReturnItem::getNum).sum();
                            }
                            return total - returned;
                        }
                ));
    }

    /**
     * 订单中可退赠品的数量
     * @param trade
     */
    public Map<Long, Map<String, Long>> findLeftGiftItems(Trade trade) {
        if (CollectionUtils.isNotEmpty(trade.getGifts())) {
            String id = trade.getId().startsWith(GeneratorService._PREFIX_PROVIDER_TRADE_ID)
                    || trade.getId().startsWith(GeneratorService._PREFIX_STORE_TRADE_ID) ? trade.getParentId() : trade.getId();
            //兼容老数据
            for (TradeItem gift : trade.getGifts()) {
                if(CollectionUtils.isEmpty(gift.getMarketingIds())){
                    return new HashMap<>();
                }
            }
            Map<Long, Map<String, Long>> map =
                    trade.getGifts().stream().collect(Collectors.groupingBy(g -> g.getMarketingIds().get(0),
                            Collectors.toMap(TradeItem::getSkuId, TradeItem::getNum)));
            List<ReturnItem> allReturnItems = this.findReturnsNotVoid(id).stream()
                    .map(ReturnOrder::getReturnGifts)
                    .reduce(new ArrayList<>(), (a, b) -> {
                        a.addAll(b);
                        return a;
                    });
            if (CollectionUtils.isNotEmpty(allReturnItems)){
                Map<Long, Map<String, ReturnItem>> groupMap =
                        allReturnItems.stream().filter(returnItem -> Objects.nonNull(returnItem.getMarketingId())).collect(Collectors.groupingBy(ReturnItem::getMarketingId,
                                Collectors.toMap(ReturnItem::getSkuId, Function.identity())));
                map.forEach((marketingId, data) -> {
                    Map<String, ReturnItem> returnItemMap = groupMap.get(marketingId);
                    if (MapUtils.isNotEmpty(returnItemMap)){
                        data.forEach((skuId, num) -> {
                            ReturnItem returnItem = returnItemMap.get(skuId);
                            if (Objects.nonNull(returnItem)){
                                data.put(skuId, num - returnItem.getNum());
                            }
                        });
                    }
                });
            }
            return map;
        }
        return new HashMap<>();
    }

    public Map<Long, Map<String, Long>> findLeftPreferentialItems(Trade trade) {
        if (CollectionUtils.isNotEmpty(trade.getPreferential())) {
            String id = trade.getId().startsWith(GeneratorService._PREFIX_PROVIDER_TRADE_ID)
                    || trade.getId().startsWith(GeneratorService._PREFIX_STORE_TRADE_ID) ? trade.getParentId() : trade.getId();
            Map<Long, Map<String, Long>> map =
                    trade.getPreferential().stream().collect(Collectors.groupingBy(g -> g.getMarketingIds().get(0),
                    Collectors.toMap(TradeItem::getSkuId, TradeItem::getNum)));
            List<ReturnItem> allReturnItems = this.findReturnsNotVoid(id).stream()
                    .map(ReturnOrder::getReturnPreferential)
                    .reduce(new ArrayList<>(), (a, b) -> {
                        a.addAll(b);
                        return a;
                    });
            if (CollectionUtils.isNotEmpty(allReturnItems)){
                Map<Long, Map<String, ReturnItem>> groupMap =
                        allReturnItems.stream().collect(Collectors.groupingBy(ReturnItem::getMarketingId,
                                Collectors.toMap(ReturnItem::getSkuId, Function.identity())));
                map.forEach((marketingId, data) -> {
                    Map<String, ReturnItem> returnItemMap = groupMap.get(marketingId);
                    if (MapUtils.isNotEmpty(returnItemMap)){
                        data.forEach((skuId, num) -> {
                            ReturnItem returnItem = returnItemMap.get(skuId);
                            if (Objects.nonNull(returnItem)){
                                data.put(skuId, num - returnItem.getNum());
                            }
                        });
                    }
                });
            }
            return map;
        }
        return new HashMap<>();
    }


    private void verifyNum(Trade trade, List<ReturnItem> returnItems) {
        Map<String, Integer> map = this.findLeftItems(trade);
        OrderTag orderTag = trade.getOrderTag();
        returnItems.stream().forEach(
                t -> {
                    if (map.get(t.getSkuId()) - t.getNum() < 0) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050057);
                    }
                    if (t.getNum() <= 0) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050012);
                    }
                }
        );
        if (Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag()) {
            Map<String, TradeItem> tradeItemMap = trade.getTradeItems().parallelStream()
                    .collect(Collectors.toMap(TradeItem::getSkuId, Function.identity()));
            returnItems.forEach(
                    t -> {
                        TradeItem tradeItem = tradeItemMap.get(t.getSkuId());
                        if (!Objects.equals(t.getNum(),tradeItem.getNum().intValue())) {
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                        }
                        if (!Objects.equals(t.getBuyCycleNum(),tradeItem.getBuyCycleNum())) {
                            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                        }
                    }
            );
        }
    }

    /**
     * 查询退单列表，不包含已作废状态以及拒绝收货的退货单与拒绝退款的退款单
     *
     * @return
     */
    public List<ReturnOrder> findReturnsNotVoid(String tid) {
        List<ReturnOrder> returnOrders = returnOrderRepository.findByTid(tid);
        return filterFinishedReturnOrder(returnOrders);
    }

    /**
     * 过滤出已经收到退货的退单
     * (
     * 作废的不算
     * 拒绝收货不算
     * 仅退款的拒绝退款不算
     * )
     */
    public List<ReturnOrder> filterFinishedReturnOrder(List<ReturnOrder> returnOrders) {
        return returnOrders.stream()
                .filter(t -> !(t.getReturnFlowState() == ReturnFlowState.VOID) &&
                        !(t.getReturnFlowState() == ReturnFlowState.REJECT_RECEIVE) &&
                        !(t.getReturnType() == ReturnType.REFUND &&
                                t.getReturnFlowState() == ReturnFlowState.REJECT_REFUND))
                .peek(returnOrder -> returnOrder.setReturnItems(returnOrder.getReturnItems().parallelStream()
                                .peek(returnItem -> {
                                    if (Objects.isNull(returnItem.getSplitPoint())) {
                                        returnItem.setSplitPoint(0L);
                                    }
                                })
                        .collect(Collectors.toList())))
                .peek(returnOrder -> returnOrder.setReturnPreferential(returnOrder.getReturnPreferential().parallelStream()
                        .peek(returnItem -> {
                            if (Objects.isNull(returnItem.getSplitPoint())) {
                                returnItem.setSplitPoint(0L);
                            }
                        })
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    /**
     * 根据订单id查询所有退单
     *
     * @param tid
     * @return
     */
    public List<ReturnOrder> findReturnByTid(String tid) {
        List<ReturnOrder> returnOrders = returnOrderRepository.findByTid(tid);
        return returnOrders == null ? Collections.emptyList() : returnOrders;
    }

    /**
     * 分页
     *
     * @param endDate
     * @return
     */
    public int countReturnOrderByEndDate(LocalDateTime endDate, ReturnFlowState returnFlowState) {
        Query query = getReturnOrderQuery(endDate, returnFlowState);
        return mongoTemplate.find(query, ReturnOrder.class).size();
    }

    /**
     * 分页查询退单
     *
     * @param endDate
     * @param start
     * @param end
     * @return
     */
    public List<ReturnOrder> queryReturnOrderByEndDate(LocalDateTime endDate, int start, int end, ReturnFlowState
            returnFlowState) {
        Query query = getReturnOrderQuery(endDate, returnFlowState);
        return mongoTemplate.find(query, ReturnOrder.class).subList(start, end);
    }

    /**
     * 构建查询条件
     *
     * @param endDate endDate
     * @return Query
     */
    private Query getReturnOrderQuery(LocalDateTime endDate, ReturnFlowState returnFlowState) {
        Criteria criteria = new Criteria();
        if (ReturnFlowState.DELIVERED == returnFlowState) {
            Criteria expressCriteria = Criteria.where("returnFlowState").is("DELIVERED")
                    .and("returnType").is("RETURN").and("returnWay").is("EXPRESS").and("returnLogistics.createTime")
                    .lte(endDate);
            Criteria otherCriteria = Criteria.where("returnFlowState").is("DELIVERED")
                    .and("returnWay").is("OTHER").and("returnType").is("RETURN").and("auditTime").lte(endDate);
            criteria.orOperator(expressCriteria, otherCriteria);
        } else {
            criteria = Criteria.where("returnFlowState").is("INIT").and("createTime").lte(endDate);
        }

        return new Query(criteria);
    }

    /**
     * 分页查询订单
     *
     * @param endDate         endDate
     * @param returnFlowState returnFlowState
     * @param pageable        pageable
     * @return List<Trade>
     */
//    public List<ReturnOrder> queryReturnOrderByPage(LocalDateTime endDate, ReturnFlowState returnFlowState, Pageable
//            pageable) {
//        val pageSize = 1000;
//        //超过
//        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        ExistsQueryBuilder filter = QueryBuilders.existsQuery("returnLogistics.createTime");
//        QueryBuilder queryBuilder;
//        //已发货的查询条件
//        if (ReturnFlowState.DELIVERED.equals(returnFlowState)) {
//            queryBuilder = QueryBuilders.boolQuery()
//                    .mustNot(QueryBuilders.boolQuery().filter(filter))
//                    .must(QueryBuilders.matchQuery("returnFlowState", returnFlowState.toValue()))
//                    .must(QueryBuilders.rangeQuery("createTime")
//                            .to(DateUtil.format(endDate, DateUtil.FMT_TIME_4)))
//                    .should(QueryBuilders.boolQuery()
//                            .must(QueryBuilders.matchQuery("returnFlowState", returnFlowState.toValue()))
//                            .must(QueryBuilders.rangeQuery("returnLogistics.createTime")
//                                    .to(DateUtil.format(endDate, DateUtil.FMT_TIME_4))));
//            //客户端带客退单的初始化状态
//        } else {
//            queryBuilder = QueryBuilders.boolQuery()
//                    .must(QueryBuilders.matchQuery("returnFlowState", returnFlowState.toValue()))
//                    .must(QueryBuilders.rangeQuery("createTime")
//                            .to(DateUtil.format(endDate, DateUtil.FMT_TIME_4)));
//        }
//
//
//        builder
//                .withIndices(EsConstants.RETURN_ORDER_INDEX)
//                .withTypes(EsConstants.RETURN_ORDER_TYPE)
//                .withQuery(
//                        queryBuilder
//                ).withPageable(new PageRequest(0, pageSize));
//
//        FacetedPage<ReturnOrder> facetedPage = template.queryForPage(builder.build(), ReturnOrder.class);
//        return facetedPage.getContent();
//    }


    /**
     * 根据退单状态统计退单
     *
     * @param
     * @return
     */
//    public ReturnOrderTodoReponse countReturnOrderByFlowState(ReturnQueryRequest request) {
//        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
//        nativeSearchQueryBuilder.withQuery(request.buildEs());
//        nativeSearchQueryBuilder.withIndices("b2b_return_order");
//        nativeSearchQueryBuilder.withTypes("return_order");
//      //  nativeSearchQueryBuilder.withSearchType(SearchType.COUNT);
//        AbstractAggregationBuilder abstractAggregationBuilder = AggregationBuilders.terms("returnType").field
//                ("returnFlowState").size(0);
//        nativeSearchQueryBuilder.addAggregation(abstractAggregationBuilder);
//        return this.template.query(nativeSearchQueryBuilder.build(), ReturnOrderTodoReponse::build);
//    }
    private void autoDeliver(String rid, Operator operator) {
        ReturnStateRequest request;
        ReturnOrder returnOrder = findById(rid);

        //非快递退回的退货单，审核通过后变更为已发货状态
        if (returnOrder.getReturnType() == ReturnType.RETURN && returnOrder.getReturnWay() == ReturnWay.OTHER) {
            request = ReturnStateRequest
                    .builder()
                    .rid(rid)
                    .operator(operator)
                    .returnEvent(ReturnEvent.DELIVER)
                    .build();
            returnFSMService.changeState(request);
        }

    }

    /**
     * 释放订单商品库存
     *
     * @param returnOrder
     */
    @Transactional
    @GlobalTransactional
    public void freeStock(ReturnOrder returnOrder, Trade trade) {
        //秒杀扣减抢购销量
        if ((Objects.nonNull(trade.getIsFlashSaleGoods()) && trade.getIsFlashSaleGoods()) ||
                (Objects.nonNull(trade.getIsFlashPromotionGoods()) && trade.getIsFlashPromotionGoods())) {
            //获取秒杀抢购活动详情
            FlashSaleGoodsVO flashSaleGoodsVO = flashSaleGoodsQueryProvider.getById(FlashSaleGoodsByIdRequest.builder()
                    .id(trade.getTradeItems().get(0).getFlashSaleGoodsId())
                    .build())
                    .getContext().getFlashSaleGoodsVO();
            Long flashSaleGoodsId = trade.getTradeItems().get(0).getFlashSaleGoodsId();
            Long purchaseNum = trade.getTradeItems().get(0).getNum();
            String buyerId = trade.getBuyer().getId();
            String skuId = trade.getTradeItems().get(0).getSkuId();
            LocalDateTime startTime;
            LocalDateTime endTime;
            // 限时购逻辑
            if (Objects.nonNull(flashSaleGoodsVO.getType()) && flashSaleGoodsVO.getType() == Constants.ONE){
                startTime = flashSaleGoodsVO.getStartTime();
                endTime = flashSaleGoodsVO.getEndTime();
            }else {
                startTime = flashSaleGoodsVO.getActivityFullTime();
                endTime = flashSaleGoodsVO.getActivityFullTime().plusHours(2);
            }
            //判断活动是否还在进行中，如果在进行中，将库存和购买数量还回redis
            if (LocalDateTime.now().isAfter(startTime) &&
                    LocalDateTime.now().isBefore(endTime)) {
                String flashSaleStockKey = RedisKeyConstant.FLASH_SALE_GOODS_INFO_STOCK_KEY + skuId;
                //会员维度存取抢购数量
                String haveBuyingKey =
                        RedisKeyConstant.FLASH_SALE_GOODS_HAVE_BUYING_KEY + buyerId + flashSaleGoodsId;
                //如果不是卡券订单，释放库存
                OrderTag orderTag = trade.getOrderTag();
                if (!(Objects.nonNull(orderTag) && orderTag.getElectronicCouponFlag())) {
                    tradeService.dealStockAndSaleNumRedis(purchaseNum, flashSaleStockKey, haveBuyingKey,orderTag,skuId);
                    //通过mq更新数据库数据
                    //异步处理销量和个人购买记录
                    FlashSaleRecordRequest request = new FlashSaleRecordRequest();
                    request.setFlashGoodsId(flashSaleGoodsId);
                    request.setPurchaseNum(-purchaseNum);
                    request.setCustomerId(buyerId);
                    request.setGoodsInfoId(skuId);
                    orderProducerService.sendFlashTrade(request);
                }
            }
            return;
        }

        //处理砍价订单
        if (Boolean.TRUE.equals(trade.getBargain())) {
            bargainSaveProvider.addStock(new UpdateStockRequest(trade.getBargainId(), 1L));
            return;
        }
        //卡券订单 不释放订单商品库存
        OrderTag orderTag = trade.getOrderTag();
        Boolean grouponFlag = trade.getGrouponFlag();
        if(grouponFlag) {
            GrouponOrderStatus grouponOrderStatus = trade.getTradeGroupon().getGrouponOrderStatus();
            // 如果是卡券拼团订单，并且已成团就不释放订单商品库存
            if (Objects.nonNull(orderTag) && orderTag.getElectronicCouponFlag()) {
                if (grouponOrderStatus.equals(GrouponOrderStatus.COMPLETE)) {
                    return;
                }
            }
        } else {
            if (Objects.nonNull(orderTag) && orderTag.getElectronicCouponFlag()) {
                return;
            }
        }
//        returnOrder.getReturnItems().stream().forEach(returnItem -> goodsInfoService.addStockById(returnItem.getNum()
//                .longValue(), returnItem.getSkuId()));
//        // 若存在赠品,赠品库存也释放
//        if (CollectionUtils.isNotEmpty(trade.getGifts())) {
//            trade.getGifts().stream().forEach(gift -> goodsInfoService.addStockById(gift.getNum(), gift.getSkuId()));
//        }
        boolean buyCycleFlag = Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag();
        int size = trade.getTradeDelivers().size();
        //批量库存释放
        List<GoodsInfoPlusStockDTO> stockList = returnOrder.getReturnItems().stream().map(returnItem -> {
            GoodsInfoPlusStockDTO dto = new GoodsInfoPlusStockDTO();
            dto.setStock(buyCycleFlag ? (returnItem.getNum().longValue() * (returnItem.getBuyCycleNum()-size)): returnItem.getNum().longValue());
            dto.setGoodsInfoId(returnItem.getSkuId());
            dto.setStoreId(trade.getSupplier().getStoreId());
            return dto;
        }).collect(Collectors.toList());

        //批量SPU库存释放
        Map<String, List<TradeItem>> items = trade.getTradeItems().stream().collect(Collectors.groupingBy(TradeItem::getSkuId));
        List<GoodsPlusStockDTO> spuStockList = returnOrder.getReturnItems().stream()
                .filter(returnItem -> items.containsKey(returnItem.getSkuId()))
                .map(returnItem -> GoodsPlusStockDTO.builder()
                        .stock(buyCycleFlag ? (returnItem.getNum().longValue() * (returnItem.getBuyCycleNum()-size)): returnItem.getNum().longValue())
                        .goodsId(items.get(returnItem.getSkuId()).get(0).getSpuId())
                        .build()).collect(Collectors.toList());


        if (CollectionUtils.isNotEmpty(returnOrder.getReturnGifts())) {
            returnOrder.getReturnGifts().forEach(gift -> {
                GoodsInfoPlusStockDTO dto = new GoodsInfoPlusStockDTO();
                dto.setStock(Long.valueOf(gift.getNum()));
                dto.setGoodsInfoId(gift.getSkuId());
                dto.setStoreId(returnOrder.getCompany().getStoreId());
                stockList.add(dto);
                spuStockList.add(new GoodsPlusStockDTO(Long.valueOf(gift.getNum()), gift.getSkuId(), returnOrder.getCompany().getStoreId()));
            });
        }

        if (CollectionUtils.isNotEmpty(returnOrder.getReturnPreferential())) {
            returnOrder.getReturnPreferential().forEach(item -> {
                GoodsInfoPlusStockDTO dto = new GoodsInfoPlusStockDTO();
                dto.setStock(Long.valueOf(item.getNum()));
                dto.setGoodsInfoId(item.getSkuId());
                dto.setStoreId(returnOrder.getCompany().getStoreId());
                stockList.add(dto);
                spuStockList.add(new GoodsPlusStockDTO(Long.valueOf(item.getNum()), item.getSkuId(),returnOrder.getCompany().getStoreId()));
            });
        }

        if (CollectionUtils.isNotEmpty(stockList)) {
            GoodsInfoBatchPlusStockRequest goodsInfoBatchPlusStockRequest = GoodsInfoBatchPlusStockRequest.builder().stockList(stockList).build();
            if(log.isInfoEnabled()) {
                log.info("freeStock call batchPlusStock Supplier params is {}", JSON.toJSONString(trade.getSupplier()));
            }
            if(trade.getSupplier().getStoreType() == StoreType.O2O) {
                stockService.batchPlusStock(goodsInfoBatchPlusStockRequest);
            }else{
                goodsInfoProvider.batchPlusStock(goodsInfoBatchPlusStockRequest);
            }
        }

        if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods()) {
            TradeItem tradeItem = trade.getTradeItems().get(0);
            List<BookingSaleGoodsVO> bookingSaleGoodsVOList = bookingSaleGoodsQueryProvider.list(BookingSaleGoodsListRequest.builder().goodsInfoId(tradeItem.getSkuId()).bookingSaleId(tradeItem.getBookingSaleId()).build()).getContext().getBookingSaleGoodsVOList();
            if (Objects.nonNull(bookingSaleGoodsVOList.get(0).getBookingCount())) {
                bookingSaleGoodsProvider.addCanBookingCount(BookingSaleGoodsCountRequest.builder().goodsInfoId(tradeItem.getSkuId()).
                        bookingSaleId(tradeItem.getBookingSaleId()).stock(stockList.get(0).getStock()).build());
            }
        }

        //SPU库存释放
       // goodsStockService.batchAddStock(spuStockList);

        if (trade.getOrderTag().getCommunityFlag()){
            List<UpdateSalesRequest.UpdateSalesDTO> updateSalesDTOS = new ArrayList<>();
            returnOrder.getReturnItems().forEach(item -> {
                UpdateSalesRequest.UpdateSalesDTO dto = new UpdateSalesRequest.UpdateSalesDTO();
                dto.setActivityId(trade.getCommunityTradeCommission().getActivityId());
                dto.setGoodsInfoId(item.getSkuId());
                dto.setStock(Long.parseLong(item.getNum().toString()));
                updateSalesDTOS.add(dto);
            });
            UpdateSalesRequest updateSalesRequest = new UpdateSalesRequest();
            updateSalesRequest.setUpdateSalesDTOS(updateSalesDTOS);
            updateSalesRequest.setAddFlag(Boolean.FALSE);
            communitySkuQueryProvider.updateSales(updateSalesRequest);
        }

    }

    public boolean isReturnFull(ReturnOrder returnOrder) {
        List<ReturnOrder> returnOrders = returnOrderRepository.findByTid(returnOrder.getTid());

        List<ReturnItem> returnItems = returnOrders.stream().filter(item -> item.getReturnFlowState() ==
                ReturnFlowState.COMPLETED
                || item.getReturnFlowState() == ReturnFlowState.RECEIVED
                || item.getReturnType() == ReturnType.RETURN && item.getReturnFlowState() == ReturnFlowState.REJECT_REFUND)
                .flatMap(item -> item.getReturnItems()
                .stream())
                .collect(Collectors.toList());
        Map<String, Long> tradeNumMap = tradeService.detail(returnOrder.getTid()).getTradeItems().stream().collect(
                Collectors.toMap(TradeItem::getSkuId, TradeItem::getNum));

        Map<String, Integer> returnNumMap = IteratorUtils.groupBy(returnItems, ReturnItem::getSkuId).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToInt(ReturnItem::getNum).sum()));
        Optional optional = tradeNumMap.entrySet().stream().filter(entry -> {
            Integer num = returnNumMap.get(entry.getKey());
            return num == null || num != entry.getValue().intValue();
        }).findFirst();
        return !optional.isPresent();
    }


    /**
     * 更新退单的业务员
     *
     * @param employeeId 业务员
     * @param customerId 客户
     */
    public void updateEmployeeId(String employeeId, String customerId) {
        mongoTemplate.updateMulti(new Query(Criteria.where("buyer.id").is(customerId)), new Update().set("buyer" +
                ".employeeId", employeeId), ReturnOrder.class);
    }

    /**
     * 完善没有业务员的退单
     */
    public void fillEmployeeId() {
        List<ReturnOrder> trades = mongoTemplate.find(new Query(Criteria.where("buyer.employeeId").is(null)),
                ReturnOrder.class);
        if (CollectionUtils.isEmpty(trades)) {
            return;
        }
        List<String> buyerIds = trades.stream()
                .filter(t -> Objects.nonNull(t.getBuyer()) && StringUtils.isNotBlank(t.getBuyer().getId()))
                .map(ReturnOrder::getBuyer)
                .map(Buyer::getId)
                .distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(buyerIds)) {
            return;
        }

        Map<String, String> customerId = customerCommonService.listCustomerDetailByCondition(
                CustomerDetailListByConditionRequest.builder().customerIds(buyerIds).build())
                .stream()
                .filter(customerDetail -> StringUtils.isNotBlank(customerDetail.getEmployeeId()))
                .collect(Collectors.toMap(CustomerDetailVO::getCustomerId, CustomerDetailVO::getEmployeeId));

        customerId.forEach((key, value) -> this.updateEmployeeId(value, key));
    }

    /**
     * 退款失败
     *
     * @param refundOrderRefundRequest
     */
    @Transactional
    @GlobalTransactional
    public void refundFailed(RefundOrderRefundRequest refundOrderRefundRequest) {
        ReturnOrder returnOrder = returnOrderRepository.findById(refundOrderRefundRequest.getRid()).orElse(null);
        if (Objects.isNull(returnOrder)) {
            log.error("退单ID:{},查询不到退单信息", refundOrderRefundRequest.getRid());
            return;
        }
        ReturnFlowState flowState = returnOrder.getReturnFlowState();
        // 如果已是退款状态的订单，直接return，不做状态扭转处理
        if (flowState == ReturnFlowState.REFUND_FAILED) {
            return;
        }
        returnOrder.setRefundFailedReason(refundOrderRefundRequest.getFailedReason());
        returnOrderService.updateReturnOrder(returnOrder);
        //修改退单状态
        ReturnStateRequest request = ReturnStateRequest
                .builder()
                .rid(refundOrderRefundRequest.getRid())
                .operator(refundOrderRefundRequest.getOperator())
                .returnEvent(ReturnEvent.REFUND_FAILED)
                .build();
        returnFSMService.changeState(request);
        // ============= 处理平台的消息发送：平台退款失败提醒 START =============
        storeMessageBizService.handleForRefundFailed(refundOrderRefundRequest);
        // ============= 处理平台的消息发送：平台退款失败提醒 END =============
    }


    /**
     * 关闭退款
     *
     * @param rid
     * @param operator
     */
    @Transactional
    @GlobalTransactional
    public void closeRefund(String rid, Operator operator) {
        ReturnOrder returnOrder = findById(rid);
        TradeStatus tradeStatus = payTradeRecordService.queryRefundResult(returnOrder.getId(), returnOrder.getTid());
        if (tradeStatus != null) {
            if (tradeStatus == TradeStatus.SUCCEED) {
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060032);
            } else if (tradeStatus == TradeStatus.PROCESSING) {
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060033);
            }
        }

        //修改财务退款单状态
        RefundOrder refundOrder = refundOrderService.findRefundOrderByReturnOrderNo(rid);
        refundOrder.setRefundStatus(RefundStatus.FINISH);
        refundOrderRepository.saveAndFlush(refundOrder);

        //修改退单状态
        ReturnStateRequest request = ReturnStateRequest
                .builder()
                .rid(rid)
                .operator(operator)
                .returnEvent(ReturnEvent.CLOSE_REFUND)
                .build();
        returnFSMService.changeState(request);

        Trade trade = tradeService.detail(returnOrder.getTid());
        trade.setRefundFlag(Boolean.TRUE);
        tradeService.updateTrade(trade);
        // 作废订单也需要释放库存
        freeStock(returnOrder, trade);
        //作废订单
        if (providerTradeAllVoid(returnOrder)) {
            tradeService.voidTrade(returnOrder.getTid(), operator);
        }
        trade.getTradeState().setEndTime(LocalDateTime.now());
    }

    /**
     * 返回掩码后的字符串
     *
     * @param bankNo
     * @return
     */
    public static String getDexAccount(String bankNo) {
        String middle = "****";
        if (bankNo.length() > Constants.FOUR) {
            if (bankNo.length() <= Constants.EIGHT) {
                return middle;
            } else {
                // 如果是手机号
                if (bankNo.length() == Constants.NUM_11) {
                    bankNo = bankNo.substring(0, 3) + middle + bankNo.substring(bankNo.length() - 4);
                } else {
                    bankNo = bankNo.substring(0, 4) + middle + bankNo.substring(bankNo.length() - 4);
                }
            }
        } else {
            return middle;
        }
        return bankNo;
    }

    /**
     * 拼团退单相应处理
     *
     * @param returnOrder
     * @param trade
     */
    private void modifyGrouponInfo(final ReturnOrder returnOrder, final Trade trade) {
        List<String> tradeItemIds =
                trade.getTradeItems().stream().map(TradeItem::getSkuId).collect(Collectors.toList());
        TradeGroupon tradeGroupon = trade.getTradeGroupon();
        //拼团订(退)单只可能有一个sku
        ReturnItem returnItem = returnOrder.getReturnItems().get(0);
        //step1. 修改订单拼团信息
        tradeGroupon.setReturnPrice(returnOrder.getReturnPrice().getActualReturnPrice());
        tradeGroupon.setReturnNum(returnItem.getNum());
        //step2. 修改拼团商品计数
        GrouponGoodsInfoReturnModifyRequest returnModifyRequest;
        if (RefundReasonConstants.Q_ORDER_SERVICE_GROUPON_AUTO_REFUND.equals(returnOrder.getDescription())) {
            returnModifyRequest = GrouponGoodsInfoReturnModifyRequest.builder()
                    .grouponActivityId(tradeGroupon.getGrouponActivityId())
                    .amount(returnOrder.getReturnPrice().getActualReturnPrice())
                    .goodsInfoId(returnItem.getSkuId())
                    .num(NumberUtils.INTEGER_ZERO)
                    .build();
        } else {
            returnModifyRequest = GrouponGoodsInfoReturnModifyRequest.builder()
                    .grouponActivityId(tradeGroupon.getGrouponActivityId())
                    .amount(returnOrder.getReturnPrice().getActualReturnPrice())
                    .goodsInfoId(returnItem.getSkuId())
                    .num(returnItem.getNum())
                    .build();
        }
        grouponGoodsInfoProvider.modifyReturnInfo(returnModifyRequest);
        //step3.修改拼团活动计数
        if (RefundReasonConstants.Q_ORDER_SERVICE_GROUPON_AUTO_REFUND.equals(returnOrder.getDescription()) ||
                RefundReasonConstants.Q_ORDER_SERVICE_GROUPON_AUTO_REFUND_USER.equals(returnOrder.getDescription())) {
            GrouponOrderStatus grouponOrderStatus = GrouponOrderStatus.FAIL;
            //自动成团设置未选中，活动到期参团失败，减待成团数，加失败数
            activityProvider.modifyStatisticsNumById(GrouponActivityModifyStatisticsNumByIdRequest.builder()
                    .grouponActivityId(tradeGroupon.getGrouponActivityId())
                    .grouponNum(1)
                    .grouponOrderStatus(grouponOrderStatus)
                    .build()
            );
            tradeGroupon.setGrouponOrderStatus(grouponOrderStatus);
        }
        //用户支付成功-立即退款 & 拼团失败：团状态更新为拼团失败
        if (RefundReasonConstants.Q_ORDER_SERVICE_GROUPON_AUTO_REFUND.equals(returnOrder.getDescription()) ||
                RefundReasonConstants.Q_ORDER_SERVICE_GROUPON_AUTO_REFUND_USER.equals(returnOrder.getDescription())) {
            if (tradeGroupon.getLeader()) {
                GrouponInstance grouponInstance = grouponOrderService.getGrouponInstanceByActivityIdAndGroupon(tradeGroupon.getGrouponNo());
                grouponInstance.setGrouponStatus(GrouponOrderStatus.FAIL);
                grouponInstance.setFailTime(LocalDateTime.now());
                grouponOrderService.updateGrouponInstance(grouponInstance);
            }
        }
        //step4.减去拼团商品已购买数
        recordProvider.decrBuyNumByGrouponActivityIdAndCustomerIdAndGoodsInfoId(GrouponRecordDecrBuyNumRequest.builder()
                .customerId(returnOrder.getBuyer().getId())
                .grouponActivityId(tradeGroupon.getGrouponActivityId())
                .goodsInfoId(returnItem.getSkuId())
                .buyNum(returnItem.getNum())
                .build()
        );
    }

    /**
     * 根据退单ID在线退款
     *
     * @param returnOrderCode
     * @param operator
     * @return
     */
    public List<Object> refundOnlineByTid(String returnOrderCode, Operator operator, Long offlineAccountId) {
        // 查询退单
        ReturnOrder returnOrder = returnOrderService.findById(returnOrderCode);

        // 退款退单状态需要是已审核
        if (returnOrder != null && returnOrder.getReturnType() == ReturnType.REFUND) {
            if (returnOrder.getReturnFlowState() != ReturnFlowState.AUDIT &&
                    returnOrder.getReturnFlowState() != ReturnFlowState.REFUND_FAILED) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050007);
            }
        }
        // 退货退单状态需要是已收到退货/退款失败
        if (returnOrder != null && returnOrder.getReturnType() == ReturnType.RETURN) {
            if (returnOrder.getReturnFlowState() != ReturnFlowState.RECEIVED &&
                    returnOrder.getReturnFlowState() != ReturnFlowState.REFUND_FAILED) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050007);
            }
        }
        // 查询退款单
        RefundOrder refundOrder = refundOrderService.findRefundOrderByReturnOrderNo(returnOrderCode);

        Trade trade = null;
        if (Objects.nonNull(returnOrder) && StringUtils.isNotBlank(returnOrder.getTid())) {
            trade = tradeService.detail(returnOrder.getTid());
        }
        if (Objects.nonNull(trade) && Objects.nonNull(trade.getBuyer()) && StringUtils.isNotEmpty(trade.getBuyer()
                .getAccount())) {
            trade.getBuyer().setAccount(ReturnOrderService.getDexAccount(trade.getBuyer().getAccount()));
        }

        if (operator.getPlatform() == Platform.BOSS) {
            if (Objects.isNull(operator) || StringUtils.isBlank(operator.getUserId())) {
                operator = Operator.builder().ip("127.0.0.1").adminId("1").name("system").platform(Platform
                        .BOSS).build();
            }
        }

        return refundOrderService.autoRefund(
                Collections.singletonList(trade),
                Collections.singletonList(returnOrder),
                Collections.singletonList(refundOrder),
                operator,
                offlineAccountId
        );
    }


    /**
     * 退单通知节点发送MQ消息
     *
     * @param nodeType
     * @param processType
     * @param params
     * @param rid
     * @param customerId
     */
    public void sendNoticeMessage(NodeType nodeType, ReturnOrderProcessType processType, List<String> params,
                                  String rid, String customerId, String pic, String mobile) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", nodeType.toValue());
        map.put("node", processType.toValue());
        map.put("id", rid);
        MessageMQRequest messageMQRequest = new MessageMQRequest();
        messageMQRequest.setNodeCode(processType.getType());
        messageMQRequest.setNodeType(nodeType.toValue());
        messageMQRequest.setParams(Lists.newArrayList(params));
        messageMQRequest.setRouteParam(map);
        messageMQRequest.setCustomerId(customerId);
        messageMQRequest.setPic(pic);
        messageMQRequest.setMobile(mobile);
        returnOrderProducerService.sendMessage(messageMQRequest);
    }

    /**
     * 构建退货收件地址
     *
     * @param addressId
     * @return 封装后的
     */
    private ReturnAddress wapperReturnAddress(String addressId, Long storeId) {
        StoreReturnAddressByIdResponse address = returnAddressQueryProvider.getById(
                StoreReturnAddressByIdRequest.builder()
                        .addressId(addressId)
                        .storeId(storeId)
                        .showAreaName(Boolean.TRUE)
                        .build()).getContext();
        if (Objects.nonNull(address) && Objects.nonNull(address.getStoreReturnAddressVO())) {
            StoreReturnAddressVO addressVO = address.getStoreReturnAddressVO();
            StringBuilder sb = new StringBuilder();
            sb.append(StringUtils.defaultString(addressVO.getProvinceName()));
            sb.append(StringUtils.defaultString(addressVO.getCityName()));
            sb.append(StringUtils.defaultString(addressVO.getAreaName()));
            sb.append(StringUtils.defaultString(addressVO.getStreetName()));
            sb.append(StringUtils.defaultString(addressVO.getReturnAddress()));
            return ReturnAddress.builder()
                    .id(addressVO.getAddressId())
                    .name(addressVO.getConsigneeName())
                    .phone(addressVO.getConsigneeNumber())
                    .provinceId(addressVO.getProvinceId())
                    .cityId(addressVO.getCityId())
                    .areaId(addressVO.getAreaId())
                    .streetId(addressVO.getStreetId())
                    .address(addressVO.getReturnAddress())
                    .detailAddress(sb.toString())
                    .provinceName(addressVO.getProvinceName())
                    .cityName(addressVO.getCityName())
                    .areaName(addressVO.getAreaName())
                    .streetName(addressVO.getStreetName())
                    .build();
        }
        return null;
    }

    /**
     * 查询退单中有售后的订单id
     * @param tidList
     * @return
     */
    public Map<String,List<String>> getRidMap(List<String> tidList) {
        if (CollectionUtils.isEmpty(tidList)) {
            return Collections.emptyMap();
        }
        List<ReturnOrder> returnOrderList = returnOrderRepository.findByPtidIn(tidList);
        if (CollectionUtils.isEmpty(returnOrderList)) {
            return Collections.emptyMap();
        }
        Map<String, ReturnFlowState> flowStateMap = ReturnFlowState.getMap();
        flowStateMap.remove(ReturnFlowState.REFUNDED.getStateId());
        return returnOrderList.stream()
                .filter(returnOrder -> this.tradeIsHasPostSales(flowStateMap, returnOrder))
                .collect(Collectors.groupingBy(ReturnOrder::getPtid,
                        Collectors.mapping(ReturnOrder::getId,Collectors.toList())));
    }

    /**
     * 根据订单id查询退单
     * @param tid
     * @return
     */
    public List<ReturnOrder> findByTid(String tid){
        return returnOrderRepository.findByTid(tid);
    }

    /**
     * 根据providerTid查询退单
     * @param providerTid
     * @return
     */
    public List<ReturnOrder> findByPtid(String providerTid){
        return returnOrderRepository.findByPtid(providerTid);
    }

    /**
     * 查询退单中有售后的商品
     * @param tradeVO
     *  @param returnOrderList
     * @return
     */
    public void goodsIsHasPostSales(TradeVO tradeVO,List<ReturnOrder> returnOrderList) {
        if(CollectionUtils.isEmpty(returnOrderList)){
            return;
        }
        //判断是否退款处理中，退款完成的订单
        Map<String, ReturnFlowState> flowStateMap = ReturnFlowState.getMap();
        flowStateMap.remove(ReturnFlowState.REFUNDED.getStateId());
        List<ReturnOrder> returnOrders = returnOrderList.stream()
                .filter(returnOrder -> this.tradeIsHasPostSales(flowStateMap,returnOrder))
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(returnOrderList)){
            return;
        }
        //商品是否有售后
        List<String> returnItemSkuIdList = returnOrders.stream()
                .map(ReturnOrder::getReturnItems)
                .filter(CollectionUtils::isNotEmpty)
                .flatMap(List::stream)
                .map(ReturnItem::getSkuId)
                .collect(Collectors.toList());
        List<TradeItemVO> tradeItemVOList = tradeVO.getTradeItems();
        tradeItemVOList.stream()
                .filter(tradeItemVO -> returnItemSkuIdList.contains(tradeItemVO.getSkuId()))
                .forEach(tradeItemVO -> tradeItemVO.setIsHasPostSales(Boolean.TRUE));
        //如果商品存在售后，则设置该商品涉及退单Id集合
        tradeItemVOList.stream()
                .filter(tradeItemVO -> Boolean.TRUE.equals(tradeItemVO.getIsHasPostSales()))
                .forEach(tradeItemVO -> {
                    List<String> rids = returnOrders.parallelStream().filter(returnOrder -> {
                        List<ReturnItem> returnItems = returnOrder.getReturnItems();
                        long count = returnItems.parallelStream().filter(returnItem -> StringUtils.equals(returnItem.getSkuId(), tradeItemVO.getSkuId())).count();
                        return count > 0;
                    }).map(ReturnOrder::getId).collect(Collectors.toList());
                    tradeItemVO.setRids(rids);
                });
        //判断赠品是否有售后
        List<TradeItemVO> giftList = tradeVO.getGifts();
        if(CollectionUtils.isEmpty(giftList)){
            return;
        }
        Map<Long, List<String>> returnGiftMap = returnOrders.stream()
                .map(ReturnOrder::getReturnGifts)
                .filter(CollectionUtils::isNotEmpty)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(ReturnItem::getMarketingId,
                Collectors.mapping(ReturnItem::getSkuId,
                        Collectors.toList())));
        giftList.stream()
                .filter(tradeItemVO -> returnGiftMap.getOrDefault(tradeItemVO.getMarketingIds().get(0),
                        new ArrayList<>()).contains(tradeItemVO.getSkuId()))
                .forEach(tradeItemVO -> tradeItemVO.setIsHasPostSales(Boolean.TRUE));

        //如果赠品存在售后，则设置该赠品涉及退单Id集合
        giftList.stream()
                .filter(tradeItemVO -> Boolean.TRUE.equals(tradeItemVO.getIsHasPostSales()))
                .forEach(tradeItemVO -> {
                    List<String> rids = returnOrders.parallelStream().filter(returnOrder -> {
                        List<ReturnItem> returnGifts = returnOrder.getReturnGifts();
                        long count = returnGifts.parallelStream().filter(returnItem -> StringUtils.equals(returnItem.getSkuId(), tradeItemVO.getSkuId())).count();
                        return count > 0;
                    }).map(ReturnOrder::getId).collect(Collectors.toList());
                    tradeItemVO.setRids(rids);
                });

        List<TradeItemVO> preferentialList = tradeVO.getPreferential();
        if(CollectionUtils.isEmpty(preferentialList)){
            return;
        }
        Map<Long, List<String>> returnPreferentialMap = returnOrders.stream()
                .map(ReturnOrder::getReturnPreferential)
                .filter(CollectionUtils::isNotEmpty)
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(ReturnItem::getMarketingId,Collectors.mapping(ReturnItem::getSkuId,
                        Collectors.toList())));
        preferentialList.stream()
                .filter(tradeItemVO -> returnPreferentialMap.getOrDefault(tradeItemVO.getMarketingIds().get(0),
                        new ArrayList<>()).contains(tradeItemVO.getSkuId()))
                .forEach(tradeItemVO -> tradeItemVO.setIsHasPostSales(Boolean.TRUE));
        //如果赠品存在售后，则设置该赠品涉及退单Id集合
        preferentialList.stream()
                .filter(tradeItemVO -> Boolean.TRUE.equals(tradeItemVO.getIsHasPostSales()))
                .forEach(tradeItemVO -> {
                    List<String> rids = returnOrders.parallelStream().filter(returnOrder -> {
                        List<ReturnItem> returnGifts = returnOrder.getReturnGifts();
                        long count =
                                returnGifts.parallelStream().filter(returnItem -> StringUtils.equals(returnItem.getSkuId(), tradeItemVO.getSkuId())
                                        && Objects.equals(returnItem.getMarketingId(),
                                        tradeItemVO.getMarketingIds().get(0))).count();
                        return count > 0;
                    }).map(ReturnOrder::getId).collect(Collectors.toList());
                    tradeItemVO.setRids(rids);
                });
    }



    /**
     * 有售后判断
     * @param flowStateMap
     * @param returnOrder
     * @return
     */
    private Boolean tradeIsHasPostSales(Map<String, ReturnFlowState> flowStateMap,ReturnOrder returnOrder){
        ReturnFlowState flowState = returnOrder.getReturnFlowState();
        String stateId = flowState.getStateId();
        ReturnFlowState returnFlowState = flowStateMap.get(stateId);
        return Objects.nonNull(returnFlowState);
    }

    /**
     * 查询可退的运费
     * @param tid
     * @return
     */
    public BigDecimal getCanReturnFee(String tid,String providerId, StoreType storeType){
        Trade trade = tradeService.detail(tid);
        BigDecimal deliveryPrice;
        //如果是供应商商品
        if(Objects.nonNull(trade.getFreight())) {
            if (StringUtils.isNotEmpty(providerId)) {
                if (Objects.isNull(trade.getFreight().getProviderFreightList())) {
                    return BigDecimal.ZERO;
                }
                ProviderFreight pFreight = trade.getFreight().getProviderFreightList().parallelStream().filter(providerFreight -> Objects.equals(Long.valueOf(providerId), providerFreight.getProviderId())).findFirst().orElse(null);
                if (Objects.isNull(pFreight) || (Constants.ONE == pFreight.getBearFreight() && Objects.isNull(pFreight.getSupplierBearFreight()))) {
                   return BigDecimal.ZERO;
                }
                //供应商出现已发货不允许退运费
                List<ProviderTrade> providerTrades = providerTradeService.findListByParentId(tid);
                if (CollectionUtils.isNotEmpty(providerTrades)) {
                    ProviderTrade providerTrade = providerTrades.stream()
                            .filter(p -> Long.valueOf(providerId).equals(p.getSupplier().getStoreId())
                                    && DeliverStatus.SHIPPED.equals(p.getTradeState().getDeliverStatus()))
                            .findFirst().orElse(null);
                    if (Objects.nonNull(providerTrade)) {
                        return BigDecimal.ZERO;
                    }
                }
                deliveryPrice = pFreight.getSupplierFreight();
            } else {
                deliveryPrice = trade.getFreight().getSupplierFreight();
            }
        } else {
            //兼容老数据，历史数据只有商家运费
            deliveryPrice = trade.getTradePrice().getDeliveryPrice();
        }

        boolean flag = Objects.nonNull(deliveryPrice) && (deliveryPrice.doubleValue() > 0);
        if (flag) {
            List<ReturnOrder> returnOrders = returnOrderService.findByCondition(ReturnQueryRequest.builder()
                    .tid(tid)
                    .storeType(storeType)
                    .build());
            //父单单已经退款的运费
            BigDecimal sumAlreadyReturnFee = returnOrders.parallelStream().filter(returnOrder1 -> returnOrder1.getReturnFlowState() == ReturnFlowState.RECEIVED
                    || returnOrder1.getReturnFlowState() == ReturnFlowState.REFUNDED || returnOrder1.getReturnFlowState() == ReturnFlowState.COMPLETED
                    || returnOrder1.getReturnFlowState() == ReturnFlowState.AUDIT)
                    .map(returnOrder2 -> {
                        ReturnPrice returnPrice = returnOrder2.getReturnPrice();
                        if (Objects.isNull(returnPrice.getFee())) {
                            return BigDecimal.ZERO;
                        }
                        return returnPrice.getFee();
                    }).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            if (StringUtils.isNotEmpty(providerId)) {
                returnOrders = returnOrders.parallelStream().filter(returnOrder -> Objects.nonNull(returnOrder.getProviderId()) && returnOrder.getProviderId().equals(providerId)).collect(Collectors.toList());
            } else {
                returnOrders = returnOrders.parallelStream().filter(returnOrder -> Objects.isNull(returnOrder.getProviderId())).collect(Collectors.toList());
            }
            //子单已经退款的运费
            BigDecimal alreadyReturnFee = returnOrders.parallelStream().filter(returnOrder1 -> returnOrder1.getReturnFlowState() == ReturnFlowState.RECEIVED
                            || returnOrder1.getReturnFlowState() == ReturnFlowState.REFUNDED || returnOrder1.getReturnFlowState() == ReturnFlowState.COMPLETED
                    || returnOrder1.getReturnFlowState() == ReturnFlowState.AUDIT)
                    .map(returnOrder2 -> {
                        ReturnPrice returnPrice = returnOrder2.getReturnPrice();
                        if (Objects.isNull(returnPrice.getFee())) {
                            return BigDecimal.ZERO;
                        }
                        return returnPrice.getFee();
                    }).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            //按照实付运费处理
            deliveryPrice = deliveryPrice.subtract(alreadyReturnFee);
            if (Objects.nonNull(trade.getTradePrice()) && Objects.nonNull(trade.getTradePrice().getDeliveryPrice())) {
                //剩余运费
                BigDecimal surplusDelivery = trade.getTradePrice().getDeliveryPrice().subtract(sumAlreadyReturnFee);
                deliveryPrice = deliveryPrice.compareTo(surplusDelivery) <= 0 ? deliveryPrice : surplusDelivery;
            }
            return deliveryPrice;
        }else{
           return BigDecimal.ZERO;
        }
    }

    /**
     * 查询退单中有售后的订单id
     * @param tidList
     * @return map K：订单id，V：退单id的集合
     */
    public Map<String,List<String>> getReturnOrderIdMap(List<String> tidList) {
        if (CollectionUtils.isEmpty(tidList)) {
            return Collections.emptyMap();
        }
        List<ReturnOrder> returnOrderList = returnOrderRepository.findByTidIn(tidList);
        if (CollectionUtils.isEmpty(returnOrderList)) {
            return Collections.emptyMap();
        }
        Map<String, ReturnFlowState> flowStateMap = ReturnFlowState.getMap();
        flowStateMap.remove(ReturnFlowState.REFUNDED.getStateId());
        return returnOrderList.stream()
                .filter(returnOrder -> this.tradeIsHasPostSales(flowStateMap, returnOrder))
                .collect(Collectors.groupingBy(ReturnOrder::getTid,
                        Collectors.mapping(ReturnOrder::getId,Collectors.toList())));
    }

    /**
     * 退单-修改卖家备注
     *
     * @param rid
     * @param sellerRemark
     */
    @Transactional
    public void remedySellerRemark(String rid, String sellerRemark, Operator operator) {
        //1、查找退单信息
        ReturnOrder returnOrder = findById(rid);
        returnOrder.setSellerRemark(sellerRemark);
        returnOrder.appendReturnEventLog(
                ReturnEventLog.builder()
                        .operator(operator)
                        .eventType("修改退单的卖家备注")
                        .eventTime(LocalDateTime.now())
                        .eventDetail(String.format("退单[%s]修改卖家备注，操作人:%s", returnOrder.getId(), operator.getName()))
                        .build()
        );
        //保存
        returnOrderService.updateReturnOrder(returnOrder);
        this.operationLogMq.convertAndSend(operator, "修改备注", "修改卖家备注");
    }

    /**
     * 标记转化
     * @param returnTag
     * @param orderTag
     */
    public ReturnTag wapperTag(ReturnTag returnTag, OrderTag orderTag) {
        if (Objects.isNull(returnTag)) {
            returnTag = new ReturnTag();
        }
        //兼容历史订单，历史订单无orderTag字段
        if (Objects.nonNull(orderTag)) {
            returnTag.setVirtualFlag(orderTag.getVirtualFlag());
            returnTag.setElectronicCouponFlag(orderTag.getElectronicCouponFlag());
            returnTag.setBuyCycleFlag(orderTag.getBuyCycleFlag());
        }
        return returnTag;
    }

    /***
     * 自营商品映射的供货商编码
     */
    private final static Long SELF_SUPPORT_LINK_ID = -10086L;

    /***
     * 校验退单是否可以批量审核
     * 自营商品 + 代销商品不可批量审核
     * 不同供应商之间的商品不可批量审核
     */
    public void checkBatchAuditReturnOrder(List<String> rids) {
        Assert.assertNotEmpty(rids, CommonErrorCodeEnum.K000009);
        List<ReturnOrder> returnOrderList = returnOrderRepository.findByIdIn(rids);
        // 返回值不可为空
        Assert.assertNotEmpty(returnOrderList, CommonErrorCodeEnum.K000009);
        // 聚合供应商ID
        List<Long> providerIds = returnOrderList.stream()
                .filter(ro-> WmCollectionUtils.isNotEmpty(ro.getReturnItems()))
                .map(ReturnOrder::getReturnItems).flatMap(ri->ri.stream())
                .map(ri-> Nutils.defaultVal(ri.getProviderId(), SELF_SUPPORT_LINK_ID))
                .distinct().collect(Collectors.toList());
        // 聚合赠品供应商ID
        List<Long> giftProviderIds = returnOrderList.stream()
                .filter(ro-> WmCollectionUtils.isNotEmpty(ro.getReturnGifts()))
                .map(ReturnOrder::getReturnGifts).flatMap(ri->ri.stream())
                .map(ri-> Nutils.defaultVal(ri.getProviderId(), SELF_SUPPORT_LINK_ID))
                .distinct().collect(Collectors.toList());
        List<Long> preferentialProviderIds = returnOrderList.stream()
                .filter(ro-> WmCollectionUtils.isNotEmpty(ro.getReturnPreferential()))
                .map(ReturnOrder::getReturnPreferential).flatMap(ri->ri.stream())
                .map(ri-> Nutils.defaultVal(ri.getProviderId(), SELF_SUPPORT_LINK_ID))
                .distinct().collect(Collectors.toList());
        // 聚合主商品和赠品供应商ID
        WmCollectionUtils.notEmpty2Loop(giftProviderIds, id -> {
            if (!providerIds.contains(id)) {
                providerIds.add(id);
            }
        });
        WmCollectionUtils.notEmpty2Loop(preferentialProviderIds, id -> {
            if (!providerIds.contains(id)) {
                providerIds.add(id);
            }
        });
        if (providerIds.size() > 1) {
            if (providerIds.contains(SELF_SUPPORT_LINK_ID)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050113);
            } else {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050112);
            }
        }
    }

    /**
     * 订单项转退单项
     * @param items 订单项
     * @return 退单项
     */
    public List<ReturnItem> chgTradeTime(List<TradeItem> items){
        return items.stream().map(item -> ReturnItem.builder()
                .num(item.getNum().intValue())
                .skuId(item.getSkuId())
                .skuNo(item.getSkuNo())
                .pic(item.getPic())
                .skuName(item.getSkuName())
                .unit(item.getUnit())
                .price(item.getPrice())
                .splitPrice(item.getSplitPrice())
                .specDetails(item.getSpecDetails())
                .providerId(item.getProviderId())
                .goodsSource(item.getGoodsSource())
                .buyPoint(item.getBuyPoint())
                .splitPoint(item.getPoints())
                .thirdPlatformType(item.getThirdPlatformType())
                .thirdPlatformSpuId(item.getThirdPlatformSpuId())
                .thirdPlatformSkuId(item.getThirdPlatformSkuId())
                .thirdPlatformSubOrderId(item.getThirdPlatformSubOrderId())
                .supplyPrice(item.getSupplyPrice())
                .providerPrice(Objects.isNull(item.getSupplyPrice())?BigDecimal.ZERO:item.getSupplyPrice().multiply(BigDecimal.valueOf(item.getNum())))
                .goodsType(item.getGoodsType())
                .giftCardItemList(this.returnGiftCardItemMapper(item.getGiftCardItemList()))
                .marketingId(CollectionUtils.isNotEmpty(item.getMarketingIds()) ? item.getMarketingIds().get(0) : null)
                .build()).collect(Collectors.toList());
    }

    /**
     * 封装退单礼品卡信息
     * @param giftCardItemList
     * @return
     */
    private List<ReturnItem.GiftCardItem> returnGiftCardItemMapper(List<TradeItem.GiftCardItem> giftCardItemList) {
        List<ReturnItem.GiftCardItem> returnGiftCardItemList = new ArrayList<>();
        if(CollectionUtils.isEmpty(giftCardItemList)) {
            return returnGiftCardItemList;
        }
        giftCardItemList.forEach(giftCardItem -> {
            ReturnItem.GiftCardItem returnItem = new ReturnItem.GiftCardItem();
            returnItem.setGiftCardNo(giftCardItem.getGiftCardNo());
            returnItem.setReturnPrice(giftCardItem.getPrice());
            returnItem.setUserGiftCardID(giftCardItem.getUserGiftCardID());
            returnGiftCardItemList.add(returnItem);
        });
        return returnGiftCardItemList;
    }

    public void sendFastRefundMessage(ReturnOrderOnlineRefundByTidRequest request) {
        ReturnOrderSendMQRequest sendMQRequest = ReturnOrderSendMQRequest.builder()
                .addFlag(true)
                .returnId(request.getReturnOrderCode())
                .operator(request.getOperator())
                .build();
        returnOrderProducerService.sendFastRefundMessage(sendMQRequest);
    }


    public void doFastRefund(ReturnOrderOnlineRefundByTidRequest request) {
        // 按照操作顺序
        // 第一步: 用户申请退货单 (前面已处理)
        String rid = request.getReturnOrderCode();
        Operator operator = request.getOperator();

        // 第二步: 商家审核通过
        log.info("开始处理极速退款生成退款单逻辑-商家审核: {}", rid);
        returnOrderService.audit(rid, operator,  "");
        log.info("处理极速退款生成退款单逻辑完成-商家审核: {}", rid);

        // 2.5 查询退款单信息
        ReturnOrderVO returnOrderVO = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid)
                .build()).getContext();


        // 第三步: 商家退款通过
        // 退款评论
        String refundComment = "极速退款";
        // 实退金额
        BigDecimal actualReturnPrice = returnOrderVO.getReturnPrice().getApplyPrice();
        // 实退积分
        Long actualReturnPoints = returnOrderVO.getReturnPoints().getApplyPoints();
        // 客户账户ID
        String customerAccountId = null;
        // 客户账号
        ReturnCustomerAccountDTO returnCustomerAccountDTO = null;
        // 运费
        BigDecimal canReturnFee = returnOrderService.getCanReturnFee(returnOrderVO.getTid(),returnOrderVO.getProviderId(),null);
        log.info("开始处理极速退款-商家退款{}-设置运费: {}", rid, canReturnFee);
        BigDecimal fee = canReturnFee;
        // 实退金额  这里要加上运费 (自提订单没有运费)
        if (Objects.nonNull(fee)) {
            actualReturnPrice = actualReturnPrice.add(fee);
        }

        // 退货单
        Long applyPoints = returnOrderVO.getReturnPoints().getApplyPoints();
        ReturnOrderDTO returnOrderDTO = KsBeanUtil.convert(returnOrderVO, ReturnOrderDTO.class);
        returnOrderDTO.setId(rid);
        ReturnPointsDTO returnPoints = ReturnPointsDTO.builder()
                .actualPoints(actualReturnPoints)
                .applyPoints(applyPoints)
                .build();
        returnOrderDTO.setReturnPoints(returnPoints);
        // 关联商品
        TradeVO tradeVO = Optional.ofNullable(returnOrderDTO.getTradeVO()).orElse(new TradeVO());
        returnOrderDTO.setTradeVO(tradeVO);
        // 客户信息 买家信息
        BuyerDTO buyer = KsBeanUtil.convert(returnOrderVO.getBuyer(), BuyerDTO.class);
        returnOrderDTO.setBuyer(buyer);

        // 转换
        ReturnOrder returnOrder = KsBeanUtil.convert(returnOrderVO, ReturnOrder.class);

        log.info("开始处理极速退款逻辑-商家同意退款: {}", JSONObject.toJSONString(returnOrderVO));
        returnOrderService.onlineEditPrice(
                returnOrder,
                refundComment,
                actualReturnPrice,
                actualReturnPoints,
                operator,
                customerAccountId,
                returnCustomerAccountDTO,
                fee
        );


        // 第四步: 平台退款通过
        // 4.1 校验退单退款状态
        // 4.2 在线退款
        log.info("开始处理在线退款逻辑-平台同意: {}", rid);
        List<Object> result = returnOrderService.refundOnlineByTid(
                rid,
                operator,
                null);
        log.info("结束处理在线退款逻辑-平台同意: {},返回结果:{}", rid,result);

        if (CollectionUtils.isEmpty(result)){
            //无返回信息，追踪退单退款状态
            ReturnFlowState state = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid).build()).getContext().getReturnFlowState();
            if (state.equals(ReturnFlowState.REFUND_FAILED)) {
                log.info("无返回信息，追踪退单退款状态: {}", JSONObject.toJSONString(state));
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050188);
            }
        }

    }
}
