package com.wanmi.sbc.order.provider.impl.payorder;

import com.google.common.collect.Lists;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelMapByCustomerIdAndStoreIdsRequest;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderQueryProvider;
import com.wanmi.sbc.order.api.request.payorder.FindByOrderNosRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderByOrderCodeRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderByOrderCodesRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderByPayOrderIdsRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderListRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrdersRequest;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrdersWithNoPageRequest;
import com.wanmi.sbc.order.api.request.payorder.SumPayOrderPriceRequest;
import com.wanmi.sbc.order.api.response.payorder.FindByOrderNosResponse;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrderByOrderCodeResponse;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrderByOrderCodesResponse;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrderByPayOrderIdsResponse;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrderListResponse;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrderResponse;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrdersResponse;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrdersWithNoPageResponse;
import com.wanmi.sbc.order.api.response.payorder.SumPayOrderPriceResponse;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.order.payorder.model.root.PayOrder;
import com.wanmi.sbc.order.payorder.request.PayOrderRequest;
import com.wanmi.sbc.order.payorder.response.PayOrderPageResponse;
import com.wanmi.sbc.order.payorder.response.PayOrderResponse;
import com.wanmi.sbc.order.payorder.service.PayOrderService;
import com.wanmi.sbc.order.paytraderecord.service.PayTradeRecordService;
import com.wanmi.sbc.order.receivables.model.root.Receivable;
import com.wanmi.sbc.order.receivables.service.ReceivableService;
import com.wanmi.sbc.order.thirdplatformtrade.model.root.ThirdPlatformTrade;
import com.wanmi.sbc.order.thirdplatformtrade.service.ThirdPlatformTradeService;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.service.ProviderTradeService;
import com.wanmi.sbc.order.trade.service.TradeCommitIncision;
import com.wanmi.sbc.order.trade.service.TradeService;
import com.wanmi.sbc.order.trade.service.TradeSimpleQueryService;
import com.wanmi.sbc.setting.api.provider.payadvertisement.PayAdvertisementQueryProvider;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementPageRequest;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementPageResponse;
import com.wanmi.sbc.setting.bean.vo.PayAdvertisementStoreVO;
import com.wanmi.sbc.setting.bean.vo.PayAdvertisementVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Validated
@RestController
public class PayOrderQueryController implements PayOrderQueryProvider {

    @Autowired
    private PayOrderService payOrderService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ProviderTradeService providerTradeService;

    @Autowired
    private ThirdPlatformTradeService thirdPlatformTradeService;

    @Autowired
    private TradeCommitIncision tradeCommitIncision;

    @Resource
    private PayTradeRecordService payTradeRecordService;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private PayAdvertisementQueryProvider payAdvertisementQueryProvider;

    @Resource
    private TradeSimpleQueryService tradeSimpleQueryService;

    @Autowired
    private ReceivableService receivableService;

    private List<PayOrderVO> toVoList(List<PayOrder> voList) {

        List<PayOrderVO> result = new ArrayList<>();

        voList.forEach(e -> {
            PayOrderVO target = new PayOrderVO();
            BeanUtils.copyProperties(e, target);
            result.add(target);
        });

    return result;
  }

    @Override
    public BaseResponse<FindPayOrderByPayOrderIdsResponse> findPayOrderByPayOrderIds(@RequestBody @Valid FindPayOrderByPayOrderIdsRequest request) {


        FindPayOrderByPayOrderIdsResponse response = FindPayOrderByPayOrderIdsResponse.builder()
                .orders(toVoList(payOrderService.findPayOrderByPayOrderIds(request.getPayOrderIds())))
                .build();

    return BaseResponse.success(response);
  }

  @Override
  public BaseResponse<FindPayOrderByOrderCodeResponse> findPayOrderByOrderCode(
      @RequestBody @Valid FindPayOrderByOrderCodeRequest request) {

    PayOrderVO target = new PayOrderVO();

    Optional<PayOrder> raw = payOrderService.findPayOrderByOrderCode(request.getValue());

    if (raw.isPresent()) {

      BeanUtils.copyProperties(raw.get(), target);

    } else {

      target = null;
    }

    FindPayOrderByOrderCodeResponse response =
        FindPayOrderByOrderCodeResponse.builder().value(target).build();

    return BaseResponse.success(response);
  }

  @Override
  public BaseResponse<FindPayOrderByOrderCodesResponse> findPayOrderByOrderCodes(
      @RequestBody @Valid FindPayOrderByOrderCodesRequest request) {

    List<PayOrder> list = payOrderService.findPayOrderByOrderCodes(request.getValue());
    // 转换成map
    Map<String, PayOrder> payOrderMap =
        list.stream().collect(Collectors.toMap(PayOrder::getOrderCode, Function.identity()));

    List<PayOrderVO> payOrderVOList = new ArrayList<>();
    list.forEach(
        vo -> {
          PayOrder payOrder = payOrderMap.get(vo.getOrderCode());
          if (Objects.nonNull(payOrder)) {
            PayOrderVO target = new PayOrderVO();
            BeanUtils.copyProperties(payOrder, target);
            payOrderVOList.add(target);
          }
        });
    FindPayOrderByOrderCodesResponse response =
        FindPayOrderByOrderCodesResponse.builder().values(payOrderVOList).build();

    return BaseResponse.success(response);
  }

  @Override
  public BaseResponse<FindPayOrderResponse> findPayOrder(
      @RequestBody @Valid FindPayOrderRequest orderNo) {

    PayOrderResponse src = payOrderService.findPayOrder(orderNo.getValue());

    FindPayOrderResponse target = new FindPayOrderResponse();

    BeanUtils.copyProperties(src, target);
    target.setCancelTime(tradeCommitIncision.findPayOrder(orderNo.getValue()));
    return BaseResponse.success(target);
  }

    @Override
    public BaseResponse<FindPayOrderListResponse> findPayOrderList(@RequestBody @Valid FindPayOrderListRequest request) {
        boolean sendCouponFlag = false;
        List<Trade> trades = tradeService.detailsByParentId(request.getParentTid(),request.getCustomerId());
        if (CollectionUtils.isEmpty(trades)){
            return BaseResponse.success(new FindPayOrderListResponse());
        }
        List<String> goodsIdList = new ArrayList<>();
        for (Trade tradeVO : trades){
            if (Objects.nonNull(tradeVO.getSendCouponFlag()) && tradeVO.getSendCouponFlag()
                    && tradeVO.getTradeState().getPayState() == PayState.PAID){
                sendCouponFlag = true;
            }
            tradeVO.getTradeItems().forEach(tradeItemVO -> {
                if(Objects.nonNull(tradeItemVO.getSpuId())){
                    goodsIdList.add(tradeItemVO.getSpuId());
                }
            });
        }
        List<PayOrderDetailVO> list = trades.stream().map(i -> {
            PayOrderResponse src = payOrderService.findPayOrder(i.getId());
            PayOrderDetailVO target = new PayOrderDetailVO();
            BeanUtils.copyProperties(src, target);
            if (StringUtils.isNoneBlank(i.getShopName(), i.getDistributorId(), i.getDistributorName())){
                target.setStoreName(i.getDistributorName().concat("的").concat(i.getShopName()));
            } else {
                target.setStoreName(i.getSupplier().getStoreName());
            }
            target.setIsSelf(i.getSupplier().getIsSelf());
            if(Objects.nonNull(i.getGrouponFlag()) && i.getGrouponFlag().equals(Boolean.TRUE)) {
                target.setGrouponNo(i.getTradeGroupon().getGrouponNo());
            }
            return target;
        }).collect(Collectors.toList());
        FindPayOrderListResponse findPayOrderListResponse = tradeCommitIncision.findPayOrderList(trades);
        findPayOrderListResponse.setGoodsIdList(goodsIdList);
        findPayOrderListResponse.setPayOrders(list);
        findPayOrderListResponse.setSendCouponFlag(Boolean.FALSE);
        // 第一次进来
        String redisKey = CacheKeyConstant.PAY_ORDER_STATUS_KEY.concat(request.getParentTid());
        if (sendCouponFlag && !redisService.hasKey(redisKey)){
            redisService.setString(redisKey, Constants.STR_1);
            findPayOrderListResponse.setSendCouponFlag(Boolean.TRUE);
        }
        // 线下订单不展示支付广告
        if (!PayType.OFFLINE.name().equals(trades.get(0).getPayInfo().getPayTypeName())){
            // 已支付的展示广告
            PayAdvertisementPageRequest pageReq = new PayAdvertisementPageRequest();
            pageReq.setQueryTab(Constants.ONE);
            pageReq.setDelFlag(DeleteFlag.NO);
            pageReq.putSort("createTime", "desc");
            BaseResponse<PayAdvertisementPageResponse> response = payAdvertisementQueryProvider.page(pageReq);
            //获取进行中的广告数据
            List<PayAdvertisementVO> page = response.getContext()
                    .getPayAdvertisementVOPage()
                    .getContent();
            if (CollectionUtils.isNotEmpty(page)){
                List<PayAdvertisementVO> payAdvertisementVOS = Lists.newArrayList();
                for (PayAdvertisementVO payAdvertisementVO:page){
                    if (validateShowAd(trades, payAdvertisementVO)) {
                        payAdvertisementVOS.add(payAdvertisementVO);
                    }
                }
                findPayOrderListResponse.setPayAdvertisementVOS(payAdvertisementVOS);
            }
        }
        return BaseResponse.success(findPayOrderListResponse);
    }

    private boolean validateShowAd(List<Trade> trades, PayAdvertisementVO payAdvertisementVO) {
        // 店铺判断
        if (Objects.nonNull(payAdvertisementVO.getStoreType())){
            // 全部店铺
            if (Constants.ONE == payAdvertisementVO.getStoreType()){
                return dealShowAd(trades, payAdvertisementVO);
            } else{
                // 获取配置店铺
                List<PayAdvertisementStoreVO> payAdvertisementStore = payAdvertisementVO.getPayAdvertisementStore();
                List<Long> storeIds = payAdvertisementStore.stream().map(PayAdvertisementStoreVO::getStoreId)
                        .filter(Objects::nonNull).distinct().collect(Collectors.toList());
                // 过滤不满足配置店铺的订单
                List<Trade> filterTrade = trades.stream().filter(trade -> storeIds.contains(trade.getSupplier().getStoreId())).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(filterTrade)){
                    return false;
                }
                return dealShowAd(filterTrade, payAdvertisementVO);
            }
        }
        return false;
    }

    private boolean dealShowAd(List<Trade> trades, PayAdvertisementVO payAdvertisementVO) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        // 全部店铺金额相加
        for (Trade trade:trades) {
            BigDecimal total = trade.getTradePrice().getTotalPrice();
            // 定金预售按付款价格计算
            if (trade.getIsBookingSaleGoods() != null
                    && trade.getIsBookingSaleGoods()
                    && trade.getBookingType() != null
                    && trade.getBookingType().equals(BookingType.EARNEST_MONEY)) {
                if (trade.getTradeState().getPayState() == PayState.PAID_EARNEST && trade.getTradeState().getFlowState() == FlowState.WAIT_PAY_TAIL) {
                    total = BigDecimal.ZERO;
                }
            }
            totalPrice = totalPrice.add(total);
        }
        // 订单实付金额判断
        if (totalPrice.compareTo(payAdvertisementVO.getOrderPrice()) >= 0){
            //不限等级
            if (Constants.STR_0.equals(payAdvertisementVO.getJoinLevel())) {
                return true;
            }
            // 店铺列表
            List<Long> storeIds = Lists.newArrayList();
            for (Trade trade:trades){
                storeIds.add(trade.getSupplier().getStoreId());
            }
            // 目标客户判断
            CustomerLevelMapByCustomerIdAndStoreIdsRequest customerLevelMapRequest =
                    new CustomerLevelMapByCustomerIdAndStoreIdsRequest();
            customerLevelMapRequest.setCustomerId(trades.get(0).getBuyer().getId());
            customerLevelMapRequest.setStoreIds(storeIds);
            // 根据会员ID查询平台会员等级的Map结果
            Map<Long, CommonLevelVO> storeLevelMap =
                    customerLevelQueryProvider
                            .listCustomerLevelMapByCustomerId(customerLevelMapRequest)
                            .getContext()
                            .getCommonLevelVOMap();
            CommonLevelVO level = storeLevelMap.get(Constants.BOSS_DEFAULT_STORE_ID);
            if (Objects.nonNull(level)) {
                if (Arrays.asList(payAdvertisementVO.getJoinLevel().split(",")).contains(String.valueOf(level.getLevelId()))) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public BaseResponse<FindPayOrdersResponse> findPayOrders(@RequestBody @Valid FindPayOrdersRequest srcrequest) {
        if (StringUtils.isNotBlank(srcrequest.getOrderNo())) {
            Trade trade = tradeService.detail(srcrequest.getOrderNo());
            if (Objects.nonNull(trade) && Objects.nonNull(trade.getIsBookingSaleGoods())
                    && trade.getIsBookingSaleGoods()
                    && trade.getBookingType() == BookingType.EARNEST_MONEY
                    && StringUtils.isNotEmpty(trade.getTailOrderNo())) {
                srcrequest.setOrderNo(null);
                List<String> orderNoList = new ArrayList<>();
                orderNoList.add(trade.getId());
                orderNoList.add(trade.getTailOrderNo());
                srcrequest.setOrderNoList(orderNoList);
            }
        }

        PayOrderRequest target = new PayOrderRequest();
        BeanUtils.copyProperties(srcrequest, target);
        PayOrderPageResponse rawresponse = payOrderService.findPayOrders(target);

        FindPayOrdersResponse response = KsBeanUtil.convert(rawresponse, FindPayOrdersResponse.class);

        if (Objects.isNull(response) || CollectionUtils.isEmpty(response.getPayOrderResponses())) {
            return BaseResponse.success(response);
        }

        // 查询主订单编号列表
        List<PayOrderResponseVO> payOrderVOList = response.getPayOrderResponses();
        List<String> parentIdList = new ArrayList<>();
        parentIdList.add(srcrequest.getOrderNo());
        if (srcrequest.getOrderNo() == null){
            parentIdList = srcrequest.getOrderNoList();
        }

        // 根据主订单编号列表查询子订单
        List<ProviderTrade> result = providerTradeService.findListByParentIdList(parentIdList);
        List<TradeVO> items = new ArrayList<>();

        List<ThirdPlatformTrade> thirdPlatformTrades =
                thirdPlatformTradeService.findListByParentIds(
                        result.stream().map(ProviderTrade::getId).collect(Collectors.toList()));

        if (CollectionUtils.isNotEmpty(result)) {
            result.forEach(
                    item -> {
                        TradeVO tradeVO = KsBeanUtil.convert(item, TradeVO.class);
                        // 是linkedmall/京东订单,填充linkedmall/京东子单信息
                        if (Objects.nonNull(item.getThirdPlatformType())
                                && (ThirdPlatformType.LINKED_MALL.equals(item.getThirdPlatformType())
                                || ThirdPlatformType.VOP.equals(item.getThirdPlatformType()))) {
                            //                    List<ThirdPlatformTrade> thirdPlatformTrades =
                            // thirdPlatformTradeService.findListByParentId(item.getId());
                            if (CollectionUtils.isNotEmpty(thirdPlatformTrades)) {
                                tradeVO.setTradeVOList(
                                        thirdPlatformTrades.stream()
                                                .filter(vo -> StringUtils.equals(vo.getParentId(), item.getId()))
                                                .map(vo -> KsBeanUtil.convert(vo, TradeVO.class))
                                                .collect(Collectors.toList()));
                            } else {
                                tradeVO.setTradeVOList(Lists.newArrayList());
                            }
                        }

                        if (srcrequest.getOrderNo() != null && srcrequest.getOrderNo().equals(item.getParentId())) {
                            items.add(tradeVO);
                        }
                    });
            if (srcrequest.getOrderNo() != null){
                PayOrderResponseVO vo = new PayOrderResponseVO();
                if (CollectionUtils.isNotEmpty(payOrderVOList) && payOrderVOList.size() == 1) {
                    vo = payOrderVOList.get(0);
                    vo.setTradeVOList(items);
                } else {
                    vo.setTradeVOList(items);
                    payOrderVOList.add(vo);
                }
            }else {
                // 填充子订单
                fillTradeVOList(payOrderVOList, result);
            }
        }

        // 查询列表中，添加交易流水号字段
        fillTradeNo2PayOrderList(payOrderVOList);
        response.setPayOrderResponses(payOrderVOList);
        return BaseResponse.success(response);
    }

    private List<PayOrderResponseVO> fillTradeVOList(List<PayOrderResponseVO> payOrderVOList, List<ProviderTrade> result) {
        // 过滤掉京东vop和linkmall的订单；转换成map
        List<TradeVO> collect = result.stream().filter(t -> t.getThirdPlatformType() == null).map(t -> KsBeanUtil.convert(t, TradeVO.class)).collect(Collectors.toList());
        Map<String, List<TradeVO>> listMap = collect.stream().collect(Collectors.groupingBy(TradeVO::getParentId));

        for (PayOrderResponseVO responseVO : payOrderVOList){
            String tid = responseVO.getTid();
            List<TradeVO> tradeVOS = listMap.get(tid);
            responseVO.setTradeVOList(tradeVOS);
        }
        //如果子单都是vop订单和linkmall订单，不返回
        return payOrderVOList.stream().filter(payOrderResponseVO -> CollectionUtils.isNotEmpty(payOrderResponseVO.getTradeVOList())).collect(Collectors.toList());
    }

    @Override
    public BaseResponse<FindPayOrdersWithNoPageResponse> findPayOrdersWithNoPage(@RequestBody @Valid FindPayOrdersWithNoPageRequest payOrderRequest) {

        PayOrderRequest request = KsBeanUtil.convert(payOrderRequest, PayOrderRequest.class);

        PayOrderPageResponse rawresponse = payOrderService.findPayOrdersWithNoPage(request);

        FindPayOrdersWithNoPageResponse target = KsBeanUtil.convert(rawresponse, FindPayOrdersWithNoPageResponse.class);

        return BaseResponse.success(target);
    }

    @Override
    public BaseResponse<FindByOrderNosResponse> findByOrderNos(@RequestBody @Valid FindByOrderNosRequest request) {

        List<PayOrder> rawOrders = payOrderService.findByOrderNos(request.getOrderNos(), request.getPayOrderStatus());

        List<PayOrderVO> target = toVoList(rawOrders);

        if(CollectionUtils.isNotEmpty(rawOrders) && DefaultFlag.YES.equals(request.getQueryReceivable())){
            List<Receivable> receivables = receivableService.findByDelFlagAndPayOrderIds(rawOrders.stream().map(PayOrder::getPayOrderId).collect(Collectors.toList()));
            if(CollectionUtils.isNotEmpty(receivables)){
                List<ReceivableVO> receivableVOS = KsBeanUtil.convertList(receivables, ReceivableVO.class);
                Map<String, List<ReceivableVO>> map = receivableVOS.stream().collect(Collectors.groupingBy(ReceivableVO::getPayOrderId));
                for (PayOrderVO payOrderVO : target) {
                    if(map.containsKey(payOrderVO.getPayOrderId())){
                        payOrderVO.setReceivable(map.get(payOrderVO.getPayOrderId()).get(0));
                    }
                }
            }
        }

        FindByOrderNosResponse response = FindByOrderNosResponse.builder().orders(target).build();

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<SumPayOrderPriceResponse> sumPayOrderPrice(@RequestBody @Valid SumPayOrderPriceRequest payOrderRequest) {

        PayOrderRequest request = KsBeanUtil.convert(payOrderRequest, PayOrderRequest.class);

        SumPayOrderPriceResponse response =
                SumPayOrderPriceResponse.builder().value(payOrderService.sumPayOrderPrice(request)).build();

        return BaseResponse.success(response);
    }

    /***
     * 向支付单返回对象中填充订单流水号
     * @param payOrderVOList
     */
    private void fillTradeNo2PayOrderList(List<PayOrderResponseVO> payOrderVOList) {
        // 0.准备支付单中的订单ID集合，过滤掉尾款订单
        List<String> tradeIds = payOrderVOList.stream()
                .map(PayOrderResponseVO::getOrderCode)
                .filter(v-> !v.startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID))
                .collect(Collectors.toList());
        if (WmCollectionUtils.isNotEmpty(tradeIds)) {
            // 1.查询订单，并转为方便查询交易记录的集合和父订单映射对象
            List<Trade> tradeList = tradeSimpleQueryService.findTradePayInfoByIds(tradeIds);
            Map<String, String> parentLinkMap = new HashMap<>();
            List<String> tradeIdOrParentIds = new ArrayList<>(tradeIds.size());
            for (Trade trade : tradeList) {
                if (trade.getPayInfo().isMergePay()) {
                    tradeIdOrParentIds.add(trade.getParentId());
                    parentLinkMap.put(trade.getId(), trade.getParentId());
                } else {
                    tradeIdOrParentIds.add(trade.getId());
                }
            }
            // 1.1 加入所有尾款订单
            List<String> tailIdList = payOrderVOList.stream()
                    .map(PayOrderResponseVO::getOrderCode)
                    .filter(v -> v.startsWith(GeneratorService._PREFIX_TRADE_TAIL_ID))
                    .collect(Collectors.toList());
            if (WmCollectionUtils.isNotEmpty(tailIdList)) {
                tradeIdOrParentIds.addAll(tailIdList);
            }

            // 2.查询结果Map，给支付单对应记录赋值
            Map<String, String> tradeNoMap = payTradeRecordService.queryTradeNoMapByBusinessIds(tradeIdOrParentIds);
            if (WmCollectionUtils.isNotEmpty(tradeNoMap)) {
                payOrderVOList.forEach(payOrder -> {
                    String id = parentLinkMap.containsKey(payOrder.getOrderCode())
                            ? parentLinkMap.get(payOrder.getOrderCode()) : payOrder.getOrderCode();
                    if (tradeNoMap.containsKey(id)) {
                        payOrder.setTradeNo(tradeNoMap.get(id));
                    }
                });
            }
        }
    }

    /**
     * @description ID查询财务支付单
     * @author  edz
     * @date: 2022/7/12 14:25
     * @param findPayOrderRequest
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.order.bean.vo.PayOrderVO>
     */
    public BaseResponse<PayOrderVO> findPayOrderByOrderId(@RequestBody @Valid FindPayOrderRequest findPayOrderRequest){
        PayOrder payOrder = payOrderService.findPayOrderByOrderId(findPayOrderRequest.getValue());
        return BaseResponse.success(KsBeanUtil.convert(payOrder, PayOrderVO.class));
    }
}
