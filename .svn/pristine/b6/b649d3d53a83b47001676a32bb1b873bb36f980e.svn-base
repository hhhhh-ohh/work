package com.wanmi.sbc.order.optimization.trade1.commit.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.response.store.ListNoDeleteStoreByIdsResponse;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.groupongoodsinfo.GrouponGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.groupongoodsinfo.GrouponGoodsByGrouponActivityIdAndGoodsInfoIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoBatchMinusStockRequest;
import com.wanmi.sbc.goods.api.response.groupongoodsinfo.GrouponGoodsByGrouponActivityIdAndGoodsInfoIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMinusStockDTO;
import com.wanmi.sbc.goods.bean.vo.BookingSaleGoodsVO;
import com.wanmi.sbc.marketing.api.provider.bargain.BargainSaveProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsalegoods.BookingSaleGoodsProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsalegoods.BookingSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.provider.communitysku.CommunitySkuQueryProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponQueryProvider;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.grouponrecord.GrouponRecordProvider;
import com.wanmi.sbc.marketing.api.request.bargaingoods.UpdateStockRequest;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingSaleGoodsCountRequest;
import com.wanmi.sbc.marketing.api.request.bookingsalegoods.BookingSaleGoodsListRequest;
import com.wanmi.sbc.marketing.api.request.communitysku.UpdateSalesRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeBatchModifyRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponByIdRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponUpdateFreezeStockRequest;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponActivityByIdRequest;
import com.wanmi.sbc.marketing.api.request.grouponrecord.GrouponRecordIncrBuyNumRequest;
import com.wanmi.sbc.marketing.bean.dto.CouponCodeBatchModifyDTO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.GrouponOrderStatus;
import com.wanmi.sbc.marketing.bean.vo.ElectronicCouponVO;
import com.wanmi.sbc.marketing.bean.vo.GrouponActivityVO;
import com.wanmi.sbc.marketing.bean.vo.TradeCouponVO;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.bean.enums.AuditState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.enums.PaymentOrder;
import com.wanmi.sbc.order.mq.OrderProducerService;
import com.wanmi.sbc.order.optimization.trade1.commit.service.Trade1CommitCreateInterface;
import com.wanmi.sbc.order.payorder.model.root.PayOrder;
import com.wanmi.sbc.order.payorder.request.PayOrderGenerateRequest;
import com.wanmi.sbc.order.payorder.service.PayOrderService;
import com.wanmi.sbc.order.receivables.service.ReceivableService;
import com.wanmi.sbc.order.sellplatform.SellPlatformTradeService;
import com.wanmi.sbc.order.thirdplatformtrade.service.ThirdPlatformTradeService;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.entity.value.TradeEventLog;
import com.wanmi.sbc.order.trade.model.entity.value.TradePrice;
import com.wanmi.sbc.order.trade.model.root.*;
import com.wanmi.sbc.order.trade.repository.GrouponInstanceRepository;
import com.wanmi.sbc.order.trade.repository.TradeRepository;
import com.wanmi.sbc.order.trade.service.ProviderTradeService;
import com.wanmi.sbc.order.trade.service.TradeCacheService;
import com.wanmi.sbc.order.trade.service.TradeGroupService;
import com.wanmi.sbc.order.util.mapper.OrderMapper;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className Trade1CommitCreateService
 * @description TODO
 * @date 2022/3/1 3:39 下午
 */
@Service
public class Trade1CommitCreateService implements Trade1CommitCreateInterface {

    @Autowired GrouponActivityQueryProvider grouponActivityQueryProvider;

    @Autowired GrouponInstanceRepository grouponInstanceRepository;

    @Autowired GrouponRecordProvider grouponRecordProvider;

    @Autowired PayOrderService payOrderService;

    @Autowired ReceivableService receivableService;

    @Autowired GeneratorService generatorService;

    @Autowired ProviderTradeService providerTradeService;

    @Autowired ThirdPlatformTradeService thirdPlatformTradeService;

    @Autowired TradeRepository tradeRepository;

    @Autowired StoreQueryProvider storeQueryProvider;

    @Autowired TradeGroupService tradeGroupService;

    @Autowired GoodsInfoProvider goodsInfoProvider;

    @Autowired BookingSaleGoodsQueryProvider bookingSaleGoodsQueryProvider;

    @Autowired BookingSaleGoodsProvider bookingSaleGoodsProvider;

    @Autowired CustomerPointsDetailSaveProvider customerPointsDetailSaveProvider;

    @Autowired CouponCodeProvider couponCodeProvider;

    @Autowired TradeCacheService tradeCacheService;

    @Autowired OrderProducerService orderProducerService;

    @Autowired GrouponGoodsInfoQueryProvider grouponGoodsInfoQueryProvider;

    @Autowired OrderMapper orderMapper;

    @Autowired SellPlatformTradeService sellPlatformTradeService;

    @Autowired
    GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    ElectronicCouponQueryProvider electronicCouponQueryProvider;

    @Autowired
    ElectronicCouponProvider electronicCouponProvider;

    @Autowired
    BargainSaveProvider bargainSaveProvider;

    @Autowired
    CommunitySkuQueryProvider communitySkuQueryProvider;

    @Override
    public void createGrouponInstance(List<Trade> tradeList) {
        tradeList.forEach(
                trade -> {
                    if (trade.getGrouponFlag()) {
                        if (trade.getTradeGroupon() == null
                                || StringUtils.isEmpty(
                                        trade.getTradeGroupon().getGrouponActivityId())) {
                            throw new SbcRuntimeException(
                                    OrderErrorCodeEnum.K050093);
                        }
                        // 3.如果是开团，设置团实例
                        if (trade.getTradeGroupon().getLeader()) {
                            GrouponActivityVO grouponActivity =
                                    grouponActivityQueryProvider
                                            .getById(
                                                    new GrouponActivityByIdRequest(
                                                            trade.getTradeGroupon()
                                                                    .getGrouponActivityId()))
                                            .getContext()
                                            .getGrouponActivity();
                            // 设置团实例
                            GrouponInstance grouponInstance =
                                    GrouponInstance.builder()
                                            .id(trade.getTradeGroupon().getGrouponNo())
                                            .grouponNo(trade.getTradeGroupon().getGrouponNo())
                                            .grouponActivityId(
                                                    trade.getTradeGroupon().getGrouponActivityId())
                                            .grouponNum(grouponActivity.getGrouponNum())
                                            .joinNum(NumberUtils.INTEGER_ZERO)
                                            .customerId(trade.getBuyer().getId())
                                            .grouponStatus(GrouponOrderStatus.UNPAY)
                                            .build();
                            // 修改拼团信息中的团号
                            grouponInstanceRepository.save(grouponInstance);
                        }
                        //                        else{
                        //                            GrouponInstance grouponInstance =
                        // grouponInstanceRepository.findTopByGrouponNo(trade.getTradeGroupon().getGrouponNo());
                        //                            Integer joinNum =
                        // grouponInstance.getJoinNum();
                        //                            Integer grouponNum =
                        // grouponInstance.getGrouponNum();
                        //                            joinNum+=1;
                        //                            grouponInstance.setJoinNum(joinNum);
                        //                            if(joinNum.equals(grouponNum)){
                        //
                        // grouponInstance.setGrouponStatus(GrouponOrderStatus.COMPLETE);
                        //
                        // grouponInstance.setCompleteTime(LocalDateTime.now());
                        //                            }
                        //
                        // grouponInstanceRepository.save(grouponInstance);
                        //                        }

                    }
                });
    }

    @Override
    public void createPayOrder(List<Trade> tradeList, TradeCommitRequest request) {
        tradeList.forEach(
                trade -> {
                    if (trade.getTradeState().getAuditState().equals(AuditState.CHECKED)
                            || request.getOperator().getPlatform() == Platform.BOSS
                            || request.getOperator().getPlatform() == Platform.SUPPLIER) {
                        if (trade.getPayOrderId() != null) {
                            payOrderService.deleteByPayOrderId(trade.getPayOrderId());
                            receivableService.deleteReceivables(
                                    Collections.singletonList(trade.getPayOrderId()));
                        }
                        // 创建支付单
                        Optional<PayOrder> optional =
                                payOrderService.generatePayOrderByOrderCode(
                                        new PayOrderGenerateRequest(
                                                trade.getId(),
                                                trade.getBuyer().getId(),
                                                Objects.nonNull(trade.getIsBookingSaleGoods())
                                                                && trade.getIsBookingSaleGoods()
                                                                && trade.getBookingType()
                                                                        == BookingType.EARNEST_MONEY
                                                                && StringUtils.isEmpty(
                                                                        trade.getTailOrderNo())
                                                        ? trade.getTradePrice().getEarnestPrice()
                                                        : trade.getTradePrice().getTotalPrice(),
                                                trade.getTradePrice().getPoints(),
                                                PayType.valueOf(
                                                        trade.getPayInfo().getPayTypeName()),
                                                trade.getSupplier().getSupplierId(),
                                                trade.getTradeState().getCreateTime(),
                                                trade.getOrderType(),
                                                trade.getTradePrice().getGiftCardPrice(),
                                                trade.getTradePrice().getGiftCardType()));

                        trade.getTradeState().setPayState(PayState.NOT_PAID);
                        optional.ifPresent(
                                payOrder -> trade.setPayOrderId(payOrder.getPayOrderId()));
                    }
                });
    }

    @Override
    public void createTradeGroup(List<Trade> tradeList, TradeCommitRequest request) {
        if (StringUtils.isNotEmpty(request.getCommonCodeId())) {
            TradeCouponVO commonCoupon = new TradeCouponVO();

            commonCoupon.setDiscountsAmount(new BigDecimal(0));
            commonCoupon.setGoodsInfoIds(new ArrayList<>());

            tradeList.forEach(
                    trade -> {
                        trade.getTradeItems()
                                .forEach(
                                        tradeItem -> {
                                            if (CollectionUtils.isNotEmpty(
                                                    tradeItem.getCouponSettlements())) {
                                                Optional<TradeItem.CouponSettlement> optional =
                                                        tradeItem.getCouponSettlements().stream()
                                                                .filter(
                                                                        c ->
                                                                                c.getCouponCodeId()
                                                                                        .equals(
                                                                                                request
                                                                                                        .getCommonCodeId()))
                                                                .findFirst();
                                                if (optional.isPresent()) {
                                                    commonCoupon.setCouponCodeId(
                                                            optional.get().getCouponCodeId());
                                                    commonCoupon.setCouponType(
                                                            optional.get().getCouponType());
                                                    commonCoupon.setDiscountsAmount(
                                                            commonCoupon
                                                                    .getDiscountsAmount()
                                                                    .add(
                                                                            optional.get()
                                                                                    .getReducePrice()));
                                                    commonCoupon
                                                            .getGoodsInfoIds()
                                                            .add(tradeItem.getSkuId());
                                                }
                                            }
                                        });
                    });
            if (StringUtils.isNotEmpty(commonCoupon.getCouponCodeId())) {
                TradeGroup tradeGroup = new TradeGroup();
                tradeGroup.setId(tradeList.get(0).getParentId());
                tradeGroup.setCommonCoupon(commonCoupon);
                //   tradeGroup.setCommonSkuIds(commonCoupon.getGoodsInfoIds());
                tradeGroupService.addTradeGroup(tradeGroup);
            }
        }
    }

    @Override
    public void minusStock(List<Trade> tradeList) {
        Trade t = tradeList.get(0);
        // 砍价订单  扣除砍价订单库存
        if(Objects.equals(Boolean.TRUE, t.getBargain())) {
            bargainSaveProvider.subStock(new UpdateStockRequest(t.getBargainId(), 1L));
            return;
        }
        OrderTag orderTag = t.getOrderTag();
        boolean buyCycleFlag =  Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag();
        tradeList.forEach(
                trade -> {
                    List<GoodsInfoMinusStockDTO> stockList =
                            trade.getTradeItems().stream()
                                    // 过滤出来非第三方的
                                    .filter(tradeItem -> tradeItem.getThirdPlatformType() == null)
                                    .map(
                                            tradeItem -> {
//                                                GoodsInfoVO goodsInfoVO = goodsInfoQueryProvider.getGoodsInfoById(GoodsInfoListByIdRequest.builder()
//                                                        .goodsInfoId(tradeItem.getSkuId())
//                                                        .build()).getContext().getGoodsInfoVO();
                                                //判断是否是卡券商品，如果是，则冻结库存
                                                if (Objects.nonNull(tradeItem.getGoodsType()) && tradeItem.getGoodsType() == Constants.TWO) {
                                                    ElectronicCouponVO electronicCouponVO = electronicCouponQueryProvider.getById(ElectronicCouponByIdRequest.builder()
                                                            .id(tradeItem.getElectronicCouponsId())
                                                            .build()).getContext().getElectronicCouponVO();
                                                    electronicCouponProvider.updateFreezeStock(ElectronicCouponUpdateFreezeStockRequest.builder()
                                                            .freezeStock(tradeItem.getNum())
                                                            .id(electronicCouponVO.getId())
                                                            .orderNo(trade.getId())
                                                            .build());
                                                }

                                                GoodsInfoMinusStockDTO dto =
                                                        new GoodsInfoMinusStockDTO();
                                                dto.setStock(buyCycleFlag ? tradeItem.getNum() * tradeItem.getBuyCycleNum() :tradeItem.getNum());

                                                dto.setGoodsInfoId(tradeItem.getSkuId());
                                                dto.setStoreId(tradeItem.getStoreId());
                                                return dto;
                                            })
                                    .collect(Collectors.toList());
                    stockList.addAll(trade.getPreferential().stream()
                            // 过滤出来非第三方的
                            .filter(tradeItem -> tradeItem.getThirdPlatformType() == null)
                            .map(
                                    tradeItem -> {
                                        //判断是否是卡券商品，如果是，则冻结库存
                                        if (Objects.nonNull(tradeItem.getGoodsType()) && tradeItem.getGoodsType() == Constants.TWO) {
                                            ElectronicCouponVO electronicCouponVO = electronicCouponQueryProvider.getById(ElectronicCouponByIdRequest.builder()
                                                    .id(tradeItem.getElectronicCouponsId())
                                                    .build()).getContext().getElectronicCouponVO();
                                            electronicCouponProvider.updateFreezeStock(ElectronicCouponUpdateFreezeStockRequest.builder()
                                                    .freezeStock(tradeItem.getNum())
                                                    .id(electronicCouponVO.getId())
                                                    .orderNo(trade.getId())
                                                    .build());
                                        }

                                        GoodsInfoMinusStockDTO dto = new GoodsInfoMinusStockDTO();
                                        dto.setStock(tradeItem.getNum());
                                        dto.setGoodsInfoId(tradeItem.getSkuId());
                                        dto.setStoreId(tradeItem.getStoreId());
                                        return dto;
                                    })
                            .collect(Collectors.toList()));
                    // 社区团购订单
                    if (Objects.nonNull(trade.getOrderTag()) && trade.getOrderTag().getCommunityFlag()){
                        List<UpdateSalesRequest.UpdateSalesDTO> updateSalesDTOS = new ArrayList<>();
                        trade.getCommunityTradeCommission().getGoodsInfoItem().forEach(goodsInfoItem -> {
                            UpdateSalesRequest.UpdateSalesDTO dto = new UpdateSalesRequest.UpdateSalesDTO();
                            dto.setActivityId(trade.getCommunityTradeCommission().getActivityId());
                            dto.setGoodsInfoId(goodsInfoItem.getGoodsInfoId());
                            dto.setStock(goodsInfoItem.getNum());
                            updateSalesDTOS.add(dto);
                        });
                        UpdateSalesRequest updateSalesRequest = new UpdateSalesRequest();
                        updateSalesRequest.setUpdateSalesDTOS(updateSalesDTOS);
                        updateSalesRequest.setAddFlag(Boolean.TRUE);
                        communitySkuQueryProvider.updateSales(updateSalesRequest);
                    }

                    if (CollectionUtils.isNotEmpty(stockList)) {
                        goodsInfoProvider.batchMinusStockTcc(
                                GoodsInfoBatchMinusStockRequest.builder()
                                        .stockList(stockList)
                                        .build());
                    }
                });
    }

    @Override
    public void minusGiftStock(List<Trade> tradeList) {
        tradeList.forEach(
                trade -> {
                    if (CollectionUtils.isNotEmpty(trade.getGifts())) {
                        List<GoodsInfoMinusStockDTO> stockList =
                                trade.getGifts().stream()
                                        // 过滤出来非第三方的
                                        .filter(
                                                tradeItem ->
                                                        tradeItem.getThirdPlatformType() == null)
                                        .map(
                                                tradeItem -> {
                                                    GoodsInfoMinusStockDTO dto =
                                                            new GoodsInfoMinusStockDTO();
                                                    dto.setStock(tradeItem.getNum());

                                                    dto.setGoodsInfoId(tradeItem.getSkuId());
                                                    dto.setStoreId(tradeItem.getStoreId());
                                                    return dto;
                                                })
                                        .collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(stockList)) {
                            List<String> errList =
                                    goodsInfoProvider
                                            .batchMinusGiftStockTcc(
                                                    GoodsInfoBatchMinusStockRequest.builder()
                                                            .stockList(stockList)
                                                            .build())
                                            .getContext();
                            // 将扣减库存失败的赠品移除
                            if (CollectionUtils.isNotEmpty(errList)) {
                                trade.setGifts(
                                        trade.getGifts().stream()
                                                .filter(g -> !errList.contains(g.getSkuId()))
                                                .collect(Collectors.toList()));
                            }
                        }
                    }
                });
    }

    @Override
    public void minusGrouponStock(List<Trade> tradeList) {
        tradeList.forEach(
                trade -> {
                    if (trade.getGrouponFlag()) {
                        TradeItem tradeItem = trade.getTradeItems().get(0);
                        GrouponGoodsByGrouponActivityIdAndGoodsInfoIdRequest grouponGoodsRequest =
                                GrouponGoodsByGrouponActivityIdAndGoodsInfoIdRequest.builder()
                                        .grouponActivityId(
                                                trade.getTradeGroupon().getGrouponActivityId())
                                        .goodsInfoId(tradeItem.getSkuId())
                                        .build();
                        BaseResponse<GrouponGoodsByGrouponActivityIdAndGoodsInfoIdResponse>
                                baseResponse =
                                grouponGoodsInfoQueryProvider
                                        .getByGrouponActivityIdAndGoodsInfoId(
                                                grouponGoodsRequest);
                        // 增加拼团活动单品的购买量
                        GrouponRecordIncrBuyNumRequest request =
                                GrouponRecordIncrBuyNumRequest.builder()
                                        .buyNum(tradeItem.getNum().intValue())
                                        .customerId(trade.getBuyer().getId())
                                        .goodsId(tradeItem.getSpuId())
                                        .goodsInfoId(tradeItem.getSkuId())
                                        .grouponActivityId(
                                                trade.getTradeGroupon().getGrouponActivityId())
                                        .limitSellingNum(
                                                baseResponse
                                                        .getContext()
                                                        .getGrouponGoodsInfoVO()
                                                        .getLimitSellingNum())
                                        // ★先不设置看看有没有问题  .limitSellingNum()
                                        .build();
                        grouponRecordProvider
                                .incrBuyNumByGrouponActivityIdAndCustomerIdAndGoodsInfoId(request);
                    }
                });
    }

    @Override
    public void minusBookingSaleStock(List<Trade> tradeList) {
        tradeList.forEach(
                trade -> {
                    if (Objects.nonNull(trade.getIsBookingSaleGoods())
                            && trade.getIsBookingSaleGoods()) {
                        TradeItem tradeItem = trade.getTradeItems().get(0);
                        List<BookingSaleGoodsVO> bookingSaleGoodsVOList =
                                bookingSaleGoodsQueryProvider
                                        .list(
                                                BookingSaleGoodsListRequest.builder()
                                                        .goodsInfoId(tradeItem.getSkuId())
                                                        .bookingSaleId(tradeItem.getBookingSaleId())
                                                        .build())
                                        .getContext()
                                        .getBookingSaleGoodsVOList();
                        if (Objects.nonNull(bookingSaleGoodsVOList.get(0).getBookingCount())) {
                            bookingSaleGoodsProvider.subCanBookingCount(
                                    BookingSaleGoodsCountRequest.builder()
                                            .goodsInfoId(tradeItem.getSkuId())
                                            .bookingSaleId(tradeItem.getBookingSaleId())
                                            .stock(tradeItem.getNum())
                                            .build());
                        }
                    }
                });
    }

    @Override
    public void minusPoint(List<Trade> tradeList) {
        tradeList.stream()
                .filter(
                        trade ->
                                Objects.nonNull(trade.getTradePrice())
                                        && Objects.nonNull(trade.getTradePrice().getPoints())
                                        && trade.getTradePrice().getPoints() > 0)
                .forEach(
                        trade -> {
                            // 增加客户积分明细 扣除积分
                            customerPointsDetailSaveProvider.add(
                                    CustomerPointsDetailAddRequest.builder()
                                            .customerId(trade.getBuyer().getId())
                                            .type(OperateType.DEDUCT)
                                            .serviceType(PointsServiceType.ORDER_DEDUCTION)
                                            .points(trade.getTradePrice().getPoints())
                                            .content(
                                                    JSONObject.toJSONString(
                                                            Collections.singletonMap(
                                                                    "orderNo", trade.getId())))
                                            .build());
                        });
    }

    @Override
    public void minusCoupon(List<Trade> tradeList, TradeCommitRequest request) {

        List<CouponCodeBatchModifyDTO> dtoList = new ArrayList<>();

        // 店铺优惠券
        // 批量修改优惠券状态
        AtomicReference<String> commonCodeId = new AtomicReference<>();
        tradeList.forEach(
                trade -> {
                    //店铺商品券
                    if (trade.getTradeCoupon() != null) {
                        TradeCouponVO tradeCoupon = trade.getTradeCoupon();
                        dtoList.add(
                                CouponCodeBatchModifyDTO.builder()
                                        .couponCodeId(tradeCoupon.getCouponCodeId())
                                        .orderCode(trade.getId())
                                        .customerId(trade.getBuyer().getId())
                                        .useStatus(DefaultFlag.YES)
                                        .build());
                    }

                    //店铺运费券
                    if (Objects.nonNull(trade.getFreightCoupon())) {
                        dtoList.add(
                                CouponCodeBatchModifyDTO.builder()
                                        .couponCodeId(trade.getFreightCoupon().getCouponCodeId())
                                        .orderCode(trade.getId())
                                        .customerId(trade.getBuyer().getId())
                                        .useStatus(DefaultFlag.YES)
                                        .build());
                    }
                    // 平台优惠券
                    if (StringUtils.isNotEmpty(request.getCommonCodeId())) {

                        trade.getTradeItems()
                                .forEach(
                                        tradeItem -> {
                                            if (CollectionUtils.isNotEmpty(
                                                    tradeItem.getCouponSettlements())) {
                                                TradeItem.CouponSettlement couponSettlement =
                                                        tradeItem.getCouponSettlements().stream()
                                                                .filter(
                                                                        c ->
                                                                                c.getCouponCodeId()
                                                                                        .equals(
                                                                                                request
                                                                                                        .getCommonCodeId()))
                                                                .findFirst()
                                                                .orElse(null);
                                                if (couponSettlement != null) {
                                                    commonCodeId.set(
                                                            couponSettlement.getCouponCodeId());
                                                }
                                            }
                                        });
                    }
                });
        // 平台优惠券
        if (commonCodeId != null && commonCodeId.get() != null) {
            dtoList.add(
                    CouponCodeBatchModifyDTO.builder()
                            .couponCodeId(commonCodeId.get())
                            .orderCode(null)
                            .customerId(request.getOperator().getUserId())
                            .useStatus(DefaultFlag.YES)
                            .build());
        }
        if (dtoList.size() > 0) {
            couponCodeProvider.batchModify(
                    CouponCodeBatchModifyRequest.builder().modifyDTOList(dtoList).build());
        }
    }

    @Override
    public void createTrade(List<Trade> tradeList, TradeCommitRequest request) {
        tradeList.forEach(
                trade -> {
                    trade.appendTradeEventLog(
                            new TradeEventLog(
                                    request.getOperator(), "创建订单", "创建订单", LocalDateTime.now()));
                });
        tradeRepository.saveAll(tradeList);
    }

    @Override
    public void createThirdPlatformTrade(List<Trade> tradeList) {
        tradeList.forEach(
                tradeT -> {
                    // 原订单流程拷贝过来，此处要用深拷贝，不能用原对象，不然下面会把订单改掉，巨坑！！！
                    Trade trade = KsBeanUtil.convert(tradeT, Trade.class);
                    List<TradeItem> tradeItemList = trade.getTradeItems();
                    List<TradeItem> gifts = trade.getGifts();
                    List<TradeItem> preferentialList = trade.getPreferential();

                    // 订单商品id集合
                    List<String> goodsInfoIdList =
                            tradeItemList.stream()
                                    .map(TradeItem::getSkuId)
                                    .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(gifts)) {
                        goodsInfoIdList.addAll(
                                gifts.stream()
                                        .map(TradeItem::getSkuId)
                                        .distinct()
                                        .collect(Collectors.toList()));
                    }
                    if (CollectionUtils.isNotEmpty(preferentialList)) {
                        goodsInfoIdList.addAll(
                                preferentialList.stream()
                                        .map(TradeItem::getSkuId)
                                        .distinct()
                                        .collect(Collectors.toList()));
                    }

                    // 查询订单商品所属供应商id集合
                    List<Long> providerIds =
                            tradeItemList.stream()
                                    .filter(t -> Objects.nonNull(t.getProviderId()))
                                    .map(TradeItem::getProviderId)
                                    .distinct()
                                    .collect(Collectors.toList());

                    // 赠品是供应商的商品
                    List<TradeItem> providerGifts = new ArrayList<>();
                    List<TradeItem> otherProviderGifts = new ArrayList<>();
                    // 商户赠品信息
                    List<TradeItem> storeGifts = new ArrayList<>();
                    List<TradeItem> storePreferentialList = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(gifts)) {
                        // 商户赠品
                        storeGifts.addAll(
                                gifts.stream()
                                        .filter(g -> Objects.isNull(g.getProviderId()))
                                        .collect(Collectors.toList()));
                        // 供应商赠品
                        providerGifts.addAll(
                                gifts.stream()
                                        .filter(g -> Objects.nonNull(g.getProviderId()))
                                        .collect(Collectors.toList()));
                        if (CollectionUtils.isNotEmpty(providerGifts)) {
                            // 赠品不属于下单商品的供应商
                            otherProviderGifts.addAll(
                                    providerGifts.stream()
                                            .filter(g -> !providerIds.contains(g.getProviderId()))
                                            .collect(Collectors.toList()));
                            // 如果赠品不属于下单商品的供应商，则再另外拆单
                            if (CollectionUtils.isNotEmpty(otherProviderGifts)) {
                                List<Long> otherProviderIds =
                                        otherProviderGifts.stream()
                                                .map(TradeItem::getProviderId)
                                                .collect(Collectors.toList());
                                providerIds.addAll(otherProviderIds);
                            }
                        }
                    }
                    if (CollectionUtils.isNotEmpty(preferentialList)) {
                        // 商户赠品
                        storePreferentialList.addAll(
                                preferentialList.stream()
                                        .filter(g -> Objects.isNull(g.getProviderId()))
                                        .collect(Collectors.toList()));
                        // 供应商赠品
                        List<TradeItem> providerPreferentialList = preferentialList.stream()
                                .filter(g -> Objects.nonNull(g.getProviderId())).collect(Collectors.toList());
                        if (CollectionUtils.isNotEmpty(providerPreferentialList)) {
                            // 赠品不属于下单商品的供应商
                            List<TradeItem> otherProviderPreferentialList =
                                    providerPreferentialList.stream()
                                            .filter(g -> !providerIds.contains(g.getProviderId())).collect(Collectors.toList());
                            // 如果赠品不属于下单商品的供应商，则再另外拆单
                            if (CollectionUtils.isNotEmpty(otherProviderPreferentialList)) {
                                List<Long> otherProviderIds =
                                        otherProviderPreferentialList.stream()
                                                .map(TradeItem::getProviderId)
                                                .distinct()
                                                .collect(Collectors.toList());
                                providerIds.addAll(otherProviderIds);
                            }
                        }
                    }

                    // 判断是否有供应商id，有则需要根据供应商拆单
                    if (CollectionUtils.isNotEmpty(providerIds)) {
                        // 1. 商户自己的商品信息，单独作为一个拆单项保存
                        List<TradeItem> storeItemList =
                                tradeItemList.stream()
                                        .filter(
                                                tradeItem ->
                                                        Objects.isNull(tradeItem.getProviderId()))
                                        .collect(Collectors.toList());

                        if (CollectionUtils.isNotEmpty(storeItemList)
                                || CollectionUtils.isNotEmpty(storeGifts)
                        || CollectionUtils.isNotEmpty(storePreferentialList)) {
                            ProviderTrade storeTrade =
                                    KsBeanUtil.convert(trade, ProviderTrade.class);
                            // 用经营商户订单id作为供应商订单的父id
                            storeTrade.setParentId(trade.getId());
                            storeTrade.setId(generatorService.generateStoreTid());
                            storeTrade.setTradeItems(storeItemList);

                            // 拆单后，重新计算价格信息
                            TradePrice tradePrice = storeTrade.getTradePrice();
                            // 商品总价
                            BigDecimal goodsPrice = BigDecimal.ZERO;
                            // 订单总价:实付金额
                            BigDecimal orderPrice = BigDecimal.ZERO;
                            // 订单供货价总额
                            BigDecimal orderSupplyPrice = BigDecimal.ZERO;
                            // 积分价
                            Long buyPoints = NumberUtils.LONG_ZERO;

                            for (TradeItem providerTradeItem : storeItemList) {
                                // 积分
                                if (Objects.nonNull(providerTradeItem.getBuyPoint())) {
                                    buyPoints += providerTradeItem.getBuyPoint();
                                }
                                // 商品总价
                                goodsPrice =
                                        goodsPrice.add(
                                                providerTradeItem
                                                        .getPrice()
                                                        .multiply(
                                                                new BigDecimal(
                                                                        providerTradeItem
                                                                                .getNum())));
                                // 商品分摊价格
                                BigDecimal splitPrice =
                                        Objects.isNull(providerTradeItem.getSplitPrice())
                                                ? BigDecimal.ZERO
                                                : providerTradeItem.getSplitPrice();
                                // 订单总价:用分摊金额乘以数量，计算订单实际价格
                                orderPrice = orderPrice.add(splitPrice);
                                // 订单供货价总额
                                orderSupplyPrice =
                                        orderSupplyPrice.add(
                                                providerTradeItem.getTotalSupplyPrice());
                            }
                            List<TradeItem> storePreferentialItemList =
                                    preferentialList.stream()
                                            .filter(
                                                    tradeItem ->
                                                            Objects.isNull(tradeItem.getProviderId()))
                                            .collect(Collectors.toList());
                            for (TradeItem providerTradeItem : storePreferentialItemList) {
                                // 积分
                                if (Objects.nonNull(providerTradeItem.getBuyPoint())) {
                                    buyPoints += providerTradeItem.getBuyPoint();
                                }
                                // 商品总价
                                goodsPrice =
                                        goodsPrice.add(
                                                providerTradeItem
                                                        .getPrice()
                                                        .multiply(
                                                                new BigDecimal(
                                                                        providerTradeItem
                                                                                .getNum())));
                                // 商品分摊价格
                                BigDecimal splitPrice =
                                        Objects.isNull(providerTradeItem.getSplitPrice())
                                                ? BigDecimal.ZERO
                                                : providerTradeItem.getSplitPrice();
                                // 订单总价:用分摊金额乘以数量，计算订单实际价格
                                orderPrice = orderPrice.add(splitPrice);
                                // 订单供货价总额
                                orderSupplyPrice =
                                        orderSupplyPrice.add(
                                                providerTradeItem.getTotalSupplyPrice());
                            }
                            // 商品总价
                            tradePrice.setGoodsPrice(goodsPrice);
                            tradePrice.setOriginPrice(goodsPrice);
                            // 计算运费
                            BigDecimal deliveryPrice = tradePrice.getDeliveryPrice();
                            if (Objects.nonNull(trade.getFreight())
                                    && Objects.nonNull(trade.getFreight().getProviderFreight())) {
                                BigDecimal supplierBearFreight =
                                        Objects.isNull(trade.getFreight().getSupplierBearFreight())
                                                ? BigDecimal.ZERO
                                                : trade.getFreight().getSupplierBearFreight();
                                deliveryPrice =
                                        deliveryPrice.subtract(
                                                trade.getFreight()
                                                        .getProviderFreight()
                                                        .subtract(supplierBearFreight));
                            }
                            tradePrice.setDeliveryPrice(deliveryPrice);
                            // 订单总价
                            orderPrice =
                                    orderPrice.compareTo(BigDecimal.ZERO) > 0
                                            ? orderPrice
                                            : BigDecimal.ZERO;
                            tradePrice.setTotalPrice(orderPrice.add(tradePrice.getDeliveryPrice()));
                            tradePrice.setTotalPayCash(orderPrice);
                            // 订单供货价总额
                            tradePrice.setOrderSupplyPrice(orderSupplyPrice);
                            // 积分价
                            tradePrice.setBuyPoints(buyPoints);

                            storeTrade.setTradePrice(tradePrice);
                            storeTrade.setThirdPlatformType(null);
                            // 赠品
                            storeTrade.setGifts(storeGifts);
                            storeTrade.setPreferential(storePreferentialList);
                            providerTradeService.addProviderTrade(storeTrade);
                        }

                        // 查询供货商店铺信息
                        BaseResponse<ListNoDeleteStoreByIdsResponse> storesResposne =
                                storeQueryProvider.listNoDeleteStoreByIds(
                                        ListNoDeleteStoreByIdsRequest.builder()
                                                .storeIds(providerIds)
                                                .build());
                        List<StoreVO> storeVOList = storesResposne.getContext().getStoreVOList();

                        // 2. 根据供货商id拆单
                        List<BigDecimal> thirdFreightList = new ArrayList<>();
                        providerIds.forEach(
                                providerId -> {
                                    ProviderTrade providerTrade =
                                            orderMapper.tradeToProviderTrade(trade);
                                    providerTrade.setOrderTag(trade.getOrderTag());
                                    providerTrade.setTradeBuyCycle(trade.getTradeBuyCycle());
                                    // 用经营商户订单id作为供应商订单的父id
                                    providerTrade.setParentId(trade.getId());
                                    providerTrade.setId(generatorService.generateProviderTid());
                                    // 筛选当前供应商的订单商品信息
                                    List<TradeItem> providerTradeItems =
                                            tradeItemList.stream()
                                                    .filter(
                                                            tradeItem ->
                                                                    providerId.equals(
                                                                            tradeItem
                                                                                    .getProviderId()))
                                                    .collect(Collectors.toList());

                                    providerTrade.setTradeItems(providerTradeItems);
                                    // 原订单所属商家名称
                                    providerTrade.setSupplierName(
                                            trade.getSupplier().getSupplierName());
                                    // 原订单所属商家编号
                                    providerTrade.setSupplierCode(
                                            trade.getSupplier().getSupplierCode());
                                    // 原订单所属商户id
                                    providerTrade.setStoreId(trade.getSupplier().getStoreId());
                                    // 深拷贝，防止数据修改
                                    Supplier supplier = KsBeanUtil.convert(providerTrade.getSupplier(), Supplier.class);

                                    // 供应商信息
                                    StoreVO provider =
                                            storeVOList.stream()
                                                    .filter(
                                                            store ->
                                                                    store.getStoreId()
                                                                            .equals(providerId))
                                                    .findFirst()
                                                    .get();
                                    // 保存供应商店铺信息
                                    supplier.setStoreId(provider.getStoreId());
                                    supplier.setSupplierName(provider.getSupplierName());
                                    supplier.setSupplierId(
                                            provider.getCompanyInfo().getCompanyInfoId());
                                    supplier.setSupplierCode(
                                            provider.getCompanyInfo().getCompanyCode());
                                    // 使用的运费模板类别(0:店铺运费,1:单品运费)
                                    supplier.setFreightTemplateType(
                                            provider.getFreightTemplateType());
                                    // providerTrade中supplier对象更新为供应商信息
                                    providerTrade.setSupplier(supplier);

                                    // 拆单后，重新计算价格信息
                                    TradePrice tradePrice = providerTrade.getTradePrice();
                                    // 商品总价
                                    BigDecimal goodsPrice = BigDecimal.ZERO;
                                    // 订单总价:实付金额
                                    BigDecimal orderPrice = BigDecimal.ZERO;
                                    // 订单供货价总额
                                    BigDecimal orderSupplyPrice = BigDecimal.ZERO;
                                    // 积分价
                                    Long buyPoints = NumberUtils.LONG_ZERO;
                                    for (TradeItem providerTradeItem : providerTradeItems) {
                                        if (!OrderType.POINTS_ORDER.equals(trade.getOrderType())) {
                                            // 积分
                                            if (Objects.nonNull(providerTradeItem.getBuyPoint())) {
                                                buyPoints += providerTradeItem.getBuyPoint();
                                            }
                                            // 商品总价
                                            goodsPrice =
                                                    goodsPrice.add(
                                                            providerTradeItem
                                                                    .getPrice()
                                                                    .multiply(
                                                                            new BigDecimal(
                                                                                    providerTradeItem
                                                                                            .getNum())));
                                            // 商品分摊价格
                                            BigDecimal splitPrice =
                                                    Objects.isNull(
                                                                    providerTradeItem
                                                                            .getSplitPrice())
                                                            ? BigDecimal.ZERO
                                                            : providerTradeItem.getSplitPrice();
                                            orderPrice = orderPrice.add(splitPrice);
                                        }
                                        // 订单供货价总额
                                        orderSupplyPrice =
                                                orderSupplyPrice.add(
                                                        providerTradeItem.getTotalSupplyPrice());
                                        // 供应商名称
                                        providerTradeItem.setProviderName(
                                                provider.getSupplierName());
                                        // 供应商编号
                                        providerTradeItem.setProviderCode(
                                                provider.getCompanyInfo().getCompanyCode());
                                    }
                                    List<TradeItem> providerPreferentialTradeItems =
                                            preferentialList.stream()
                                                    .filter(
                                                            tradeItem ->
                                                                    providerId.equals(
                                                                            tradeItem
                                                                                    .getProviderId()))
                                                    .collect(Collectors.toList());
                                    for (TradeItem providerTradeItem : providerPreferentialTradeItems) {
                                        if (!OrderType.POINTS_ORDER.equals(trade.getOrderType())) {
                                            // 积分
                                            if (Objects.nonNull(providerTradeItem.getBuyPoint())) {
                                                buyPoints += providerTradeItem.getBuyPoint();
                                            }
                                            // 商品总价
                                            goodsPrice =
                                                    goodsPrice.add(
                                                            providerTradeItem
                                                                    .getPrice()
                                                                    .multiply(
                                                                            new BigDecimal(
                                                                                    providerTradeItem
                                                                                            .getNum())));
                                            // 商品分摊价格
                                            BigDecimal splitPrice =
                                                    Objects.isNull(
                                                            providerTradeItem
                                                                    .getSplitPrice())
                                                            ? BigDecimal.ZERO
                                                            : providerTradeItem.getSplitPrice();
                                            orderPrice = orderPrice.add(splitPrice);
                                        }
                                        // 订单供货价总额
                                        orderSupplyPrice =
                                                orderSupplyPrice.add(
                                                        providerTradeItem.getTotalSupplyPrice());
                                        // 供应商名称
                                        providerTradeItem.setProviderName(
                                                provider.getSupplierName());
                                        // 供应商编号
                                        providerTradeItem.setProviderCode(
                                                provider.getCompanyInfo().getCompanyCode());
                                    }

                                    List<TradeItem> pGifts = new ArrayList<>();
                                    if (CollectionUtils.isNotEmpty(providerGifts)) {
                                        pGifts =
                                                providerGifts.stream()
                                                        .filter(
                                                                g ->
                                                                        providerId.equals(
                                                                                g.getProviderId()))
                                                        .collect(Collectors.toList());
                                        for (TradeItem pgift : pGifts) {
                                            // 供应商名称
                                            pgift.setProviderName(provider.getSupplierName());
                                            // 供应商编号
                                            pgift.setProviderCode(
                                                    provider.getCompanyInfo().getCompanyCode());
                                            // 供货价
                                            orderSupplyPrice =
                                                    orderSupplyPrice.add(
                                                            pgift.getTotalSupplyPrice());
                                        }
                                    }

                                    // 商品总价
                                    tradePrice.setGoodsPrice(goodsPrice);
                                    tradePrice.setOriginPrice(goodsPrice);
                                    tradePrice.setDeliveryPrice(BigDecimal.ZERO);
                                    // 查询供应商 配送费用 deliveryPrice
                                    if (trade.getFreight() != null
                                            && CollectionUtils.isNotEmpty(
                                                    trade.getFreight().getProviderFreightList())) {
                                        trade.getFreight()
                                                .getProviderFreightList()
                                                .forEach(
                                                        providerFreight -> {
                                                            if (providerFreight
                                                                    .getProviderId()
                                                                    .equals(providerId)) {
                                                                providerTrade
                                                                        .setThirdPlatFormFreight(
                                                                                providerFreight
                                                                                        .getSupplierFreight());
                                                                // 买家承担才会有运费
                                                                // 放开承担方的判断，供应商订单运费始终展示总运费
//                                                                if (0
//                                                                        == providerFreight
//                                                                                .getBearFreight()) {
                                                                    tradePrice.setDeliveryPrice(
                                                                            providerFreight
                                                                                    .getSupplierFreight());
//                                                                }
                                                            }
                                                        });
                                    }

                                    orderPrice =
                                            orderPrice.compareTo(BigDecimal.ZERO) > 0
                                                    ? orderPrice
                                                    : BigDecimal.ZERO;
                                    // 订单总价
                                    tradePrice.setTotalPrice(
                                            orderPrice.add(tradePrice.getDeliveryPrice()));
                                    tradePrice.setTotalPayCash(orderPrice);
                                    // 订单供货价总额
                                    tradePrice.setOrderSupplyPrice(orderSupplyPrice);
                                    // 积分价
                                    tradePrice.setBuyPoints(buyPoints);

                                    providerTrade.setTradePrice(tradePrice);
                                    // 赠品
                                    providerTrade.setGifts(pGifts);
                                    providerTrade.setPreferential(providerPreferentialTradeItems);

                                    // linkedMall供应商
                                    if (CompanySourceType.LINKED_MALL.equals(
                                            provider.getCompanySourceType())) {
                                        providerTrade.setThirdPlatformType(
                                                ThirdPlatformType.LINKED_MALL);
                                    } else if (CompanySourceType.JD_VOP.equals(
                                            provider.getCompanySourceType())) {
                                        providerTrade.setThirdPlatformType(ThirdPlatformType.VOP);
                                    } else {
                                        providerTrade.setThirdPlatformType(null);
                                    }

                                    // 第三方渠道拆分订单
                                    if (providerTrade.getThirdPlatformType() != null) {
                                        thirdPlatformTradeService.splitTrade(providerTrade);
                                    }
                                    // 供应商订单不保存自提信息
                                    if (Objects.nonNull(providerTrade.getPickupFlag())) {
                                        providerTrade.setPickupFlag(null);
                                        providerTrade.setPickSettingInfo(null);
                                    }

                                    providerTradeService.addProviderTrade(providerTrade);

                                    if (providerTrade.getThirdPlatFormFreight() != null
                                            && providerTrade
                                                            .getThirdPlatFormFreight()
                                                            .compareTo(BigDecimal.ZERO)
                                                    > 0) {
                                        thirdFreightList.add(
                                                providerTrade.getThirdPlatFormFreight());
                                    }
                                });

                        // 保存第三方订单运费
                        if (CollectionUtils.isNotEmpty(thirdFreightList)) {
                            tradeT.setThirdPlatFormFreight(
                                    thirdFreightList.stream()
                                            .reduce(BigDecimal.ZERO, BigDecimal::add));
                            tradeRepository.save(tradeT);
                        }
                    }
                });
    }

    @Override
    public void createSellPlatformTrade(List<Trade> tradeList, TradeCommitRequest request) {
        if (!request.getIsChannelsFlag()) {
            return;
        }
        if (tradeList.size() == 1) {
            sellPlatformTradeService.addOrder(tradeList.get(0));
        }
        if (tradeList.size() > 1) {
            sellPlatformTradeService.batchAddOrder(tradeList);
        }
    }

    @Override
    public void createTimeOutMQ(List<Trade> tradeList) {

        // 先货后款情况下，查询订单是否开启订单失效时间设置
        ConfigVO timeoutCancelConfig =
                tradeCacheService.getTradeConfigByType(ConfigType.ORDER_SETTING_TIMEOUT_CANCEL);
        tradeList.forEach(
                trade -> {

                    // 先款后货且已审核订单（审核开关关闭）且线上支付单
                    Boolean needTimeOut =
                            Objects.equals(
                                            trade.getTradeState().getAuditState(),
                                            AuditState.CHECKED)
                                    && trade.getPaymentOrder() == PaymentOrder.PAY_FIRST
                                    && !PayType.OFFLINE
                                            .name()
                                            .equals(trade.getPayInfo().getPayTypeName());

                    if (needTimeOut) {

                        Integer timeoutSwitch = timeoutCancelConfig.getStatus();
                        if (timeoutSwitch == 1) {
                            // 查询设置中订单超时时间
                            Integer minutes =
                                    Integer.valueOf(
                                            JSON.parseObject(timeoutCancelConfig.getContext())
                                                    .get("minute")
                                                    .toString());

                            if (Objects.nonNull(trade.getGrouponFlag())
                                    && !trade.getGrouponFlag()) {
                                // 发送非拼团单取消订单延迟队列;
                                trade.setOrderTimeOut(LocalDateTime.now().plusMinutes(minutes));
                                orderProducerService.cancelOrder(
                                        trade.getId(), minutes * 60 * 1000L);
                                tradeRepository.save(trade);
                            }
                        }
                    }

                    // 拼团订单--设置订单状态
                    if (Objects.nonNull(trade.getGrouponFlag()) && trade.getGrouponFlag()) {
                        // 发送拼团单取消订单延迟队列，拼团订单默认是5分钟超时
                        trade.setOrderTimeOut(LocalDateTime.now().plusMinutes(5));
                        orderProducerService.cancelOrder(trade.getId(), 5 * 60 * 1000L);
                        tradeRepository.save(trade);
                    }
                });
    }
}
