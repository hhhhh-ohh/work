package com.wanmi.sbc.order.thirdplatformtrade.service;

import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.BeanUtils;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelOrderProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderCreateRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelOrderPayRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelOrderCreateResponse;
import com.wanmi.sbc.empower.bean.dto.channel.base.ChannelTradeDTO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByConditionRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoBaseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.order.api.request.trade.ThirdPlatformTradeUpdateRequest;
import com.wanmi.sbc.order.bean.dto.ThirdPlatformTradeUpdateStateDTO;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.order.common.OrderCommonService;
import com.wanmi.sbc.order.mq.OrderProducerService;
import com.wanmi.sbc.order.orderinvoice.service.OrderInvoiceService;
import com.wanmi.sbc.order.purchase.mapper.GoodsInfoMapper;
import com.wanmi.sbc.order.returnorder.service.ThirdPlatformReturnOrderService;
import com.wanmi.sbc.order.thirdplatformtrade.model.entity.ThirdPlatformTradeResult;
import com.wanmi.sbc.order.thirdplatformtrade.model.root.ThirdPlatformTrade;
import com.wanmi.sbc.order.thirdplatformtrade.repository.ThirdPlatformTradeRepository;
import com.wanmi.sbc.order.thirdplatformtrade.request.ThirdPlatformTradeQueryRequest;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.TradeState;
import com.wanmi.sbc.order.trade.model.entity.value.Consignee;
import com.wanmi.sbc.order.trade.model.entity.value.TradeEventLog;
import com.wanmi.sbc.order.trade.model.root.ProviderTrade;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.request.TradeQueryRequest;
import com.wanmi.sbc.order.trade.service.ProviderTradeService;
import com.wanmi.sbc.order.trade.service.TradeService;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelGoodsProvider;
import com.wanmi.sbc.vas.api.provider.channel.order.ChannelTradeProvider;
import com.wanmi.sbc.vas.api.request.channel.*;
import com.wanmi.sbc.vas.api.response.channel.*;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelGoodsInfoDTO;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelOrderDTO;
import com.wanmi.sbc.vas.bean.vo.channel.ChannelGoodsInfoVO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description 渠道订单服务
 * @author daiyitian
 * @date 2021/5/12 18:09
 */
@Service
@Slf4j
public class ThirdPlatformTradeService {

    @Autowired private ThirdPlatformTradeRepository thirdPlatformTradeRepository;

    @Autowired private TradeService tradeService;

    @Autowired private ThirdPlatformReturnOrderService thirdPlatformReturnOrderService;

    @Autowired private OrderCommonService orderCommonService;

    @Autowired private ProviderTradeService providerTradeService;

    @Autowired private OrderInvoiceService orderInvoiceService;

    @Autowired private RedisUtil redisService;

    @Autowired private ChannelOrderProvider channelOrderProvider;

    @Autowired private ChannelGoodsProvider channelGoodsProvider;

    @Autowired private ChannelTradeProvider channelTradeProvider;

    @Autowired private OrderProducerService orderProducerService;

    @Autowired private MongoTemplate mongoTemplate;

    @Autowired private GoodsInfoMapper goodsInfoMapper;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    /** 补偿监听KEY */
    private static final String LISTEN_KEY =
            RedisKeyConstant.THIRD_PLATFORM_CHECKED_PEY_AUTO_REFUND;

    /**
     * @description 填充购物车
     * @author daiyitian
     * @date 2021/5/13 15:13
     * @param goodsInfoList 商品信息
     * @param platformAddress 本地地址
     */
    public void fillCart(List<GoodsInfoVO> goodsInfoList, PlatformAddress platformAddress) {
        if (this.isClose()) {
            // 未开启，则失效
            if (CollectionUtils.isNotEmpty(goodsInfoList)) {
                goodsInfoList.stream()
                        .filter(i -> Objects.nonNull(i.getThirdPlatformType()))
                        .forEach(i -> i.setGoodsStatus(GoodsStatus.INVALID));
            }
            return;
        }
        List<ChannelGoodsInfoDTO> sku =
                KsBeanUtil.convert(goodsInfoList, ChannelGoodsInfoDTO.class);
        ChannelGoodsStatusGetRequest getRequest =
                ChannelGoodsStatusGetRequest.builder()
                        .goodsInfoList(sku)
                        .address(platformAddress)
                        .build();
        ChannelGoodsStatusGetResponse getResponse =
                channelGoodsProvider.getGoodsStatus(getRequest).getContext();
        if (CollectionUtils.isNotEmpty(getResponse.getOffAddedSkuId())) {
            // 发起MQ请求下架商品MQ，刷ES
            orderProducerService.sendThirdPlatformSkuOffAddedSync(getResponse.getOffAddedSkuId());
        }
        Map<String, ChannelGoodsInfoVO> skuMap =
                getResponse.getGoodsInfoList().stream()
                        .collect(
                                Collectors.toMap(
                                        ChannelGoodsInfoVO::getGoodsInfoId, Function.identity()));
        goodsInfoList.stream()
                .filter(i -> skuMap.containsKey(i.getGoodsInfoId()))
                .forEach(
                        i -> {
                            ChannelGoodsInfoVO skuVo = skuMap.get(i.getGoodsInfoId());
                            i.setGoodsStatus(
                                    GoodsStatus.fromValue(skuVo.getGoodsStatus().toValue()));
                            i.setAddedFlag(skuVo.getAddedFlag());
                            i.setVendibility(skuVo.getVendibility());
                            i.setStock(skuVo.getStock());
                        });
    }

    /**
     * @description 填充购物车
     * @author daiyitian
     * @date 2021/5/13 15:13
     * @param goodsInfoList 商品信息
     * @param platformAddress 本地地址
     */
    public List<? extends GoodsInfoBaseVO> cartStatus(
            List<? extends GoodsInfoBaseVO> goodsInfoList, PlatformAddress platformAddress) {
        if (this.isClose()) {
            // 未开启，则失效
            if (CollectionUtils.isNotEmpty(goodsInfoList)) {
                goodsInfoList.stream()
                        .filter(i -> Objects.nonNull(i.getThirdPlatformType()))
                        .forEach(i -> i.setGoodsStatus(GoodsStatus.INVALID));
            }
            return goodsInfoList;
        }
        //只处理第三方数据
        List<ChannelGoodsInfoDTO> sku =
                goodsInfoMapper.goodsInfoBaseVOsToChannelGoodsInfoDTOs(
                        (List<GoodsInfoBaseVO>) goodsInfoList.stream().filter(goodsInfoBaseVO -> Objects.nonNull(goodsInfoBaseVO.getThirdPlatformType())).collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(sku)) {
            return goodsInfoList;
        }
        ChannelGoodsStatusGetRequest getRequest =
                ChannelGoodsStatusGetRequest.builder()
                        .goodsInfoList(sku)
                        .address(platformAddress)
                        .build();
        ChannelGoodsStatusGetResponse getResponse =
                channelGoodsProvider.getGoodsStatus(getRequest).getContext();
        if (CollectionUtils.isNotEmpty(getResponse.getOffAddedSkuId())) {
            // 发起MQ请求下架商品MQ，刷ES
            orderProducerService.sendThirdPlatformSkuOffAddedSync(getResponse.getOffAddedSkuId());
        }
        Map<String, ChannelGoodsInfoVO> skuMap =
                getResponse.getGoodsInfoList().stream()
                        .collect(
                                Collectors.toMap(
                                        ChannelGoodsInfoVO::getGoodsInfoId, Function.identity()));
        goodsInfoList.stream()
                .filter(i -> skuMap.containsKey(i.getGoodsInfoId()))
                .forEach(
                        i -> {
                            ChannelGoodsInfoVO skuVo = skuMap.get(i.getGoodsInfoId());
                            i.setGoodsStatus(
                                    GoodsStatus.fromValue(skuVo.getGoodsStatus().toValue()));
                            i.setVendibility(skuVo.getVendibility());
                            i.setStock(skuVo.getStock());
                        });
        return goodsInfoList;
    }

    /**
     * @description    验证商品
     * @author  wur
     * @date: 2022/5/28 16:25
     * @param goodsInfoList
     * @param consignee
     * @return
     **/
    public void verifyGoods(List<TradeItem> goodsInfoList, Consignee consignee) {
        List<TradeItem> thirdGoodsInfo = goodsInfoList.stream().filter(i -> Objects.nonNull(i.getThirdPlatformType())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(thirdGoodsInfo)) {
            return;
        }
        List<String> skuIds = thirdGoodsInfo.stream().map(TradeItem :: getSkuId).collect(Collectors.toList());
        Map<String, Long> skuMap = thirdGoodsInfo.stream().collect(Collectors.toMap(TradeItem::getSkuId,
                TradeItem::getNum, Long::sum));

        GoodsInfoListByConditionRequest goodsRequest = new GoodsInfoListByConditionRequest();
        goodsRequest.setGoodsInfoIds(skuIds);
        List<GoodsInfoVO> goodsList =
                goodsInfoQueryProvider.listByCondition(goodsRequest).getContext().getGoodsInfos();
        if (CollectionUtils.isEmpty(goodsList)) {
            return;
        }

        PlatformAddress address = new PlatformAddress();
        address.setProvinceId(Objects.toString(consignee.getProvinceId(), StringUtils.EMPTY));
        address.setCityId(Objects.toString(consignee.getCityId(), StringUtils.EMPTY));
        address.setAreaId(Objects.toString(consignee.getAreaId(), StringUtils.EMPTY));
        address.setStreetId(Objects.toString(consignee.getStreetId(), StringUtils.EMPTY));
        // 填充购买数量，主要验证库存
        goodsList.forEach(
                sku -> {
                    Long num = skuMap.getOrDefault(sku.getGoodsInfoId(), 0L);
                    sku.setBuyCount(num);
                    sku.setGoodsStatus(GoodsStatus.OK);
                });
        this.verifyGoods(goodsList, address);
    }

    /**
     * @description 去确认订单事件
     * @author daiyitian
     * @date 2021/5/13 15:13
     * @param goodsInfoList 商品信息
     * @param platformAddress 本地地址
     */
    public void verifyGoods(List<GoodsInfoVO> goodsInfoList, PlatformAddress platformAddress) {
        if (this.isClose()) {
            //含有第三方商品
            if (CollectionUtils.isNotEmpty(goodsInfoList)
                    && goodsInfoList.stream()
                            .anyMatch(i -> Objects.nonNull(i.getThirdPlatformType()))) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
            }
            return;
        }
        List<ChannelGoodsInfoDTO> sku =
                KsBeanUtil.convert(goodsInfoList, ChannelGoodsInfoDTO.class);
        ChannelGoodsVerifyRequest getRequest =
                ChannelGoodsVerifyRequest.builder()
                        .goodsInfoList(sku)
                        .address(platformAddress)
                        .build();
        ChannelGoodsVerifyResponse getResponse =
                channelGoodsProvider.verifyGoods(getRequest).getContext();
        if (CollectionUtils.isNotEmpty(getResponse.getOffAddedSkuId())) {
            // 发起MQ请求下架商品MQ，刷ES
            orderProducerService.sendThirdPlatformSkuOffAddedSync(getResponse.getOffAddedSkuId());
        }
        if (Boolean.TRUE.equals(getResponse.getInvalidGoods())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
        }

        if (Boolean.TRUE.equals(getResponse.getNoAuthGoods())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050053);
        }

        if (Boolean.TRUE.equals(getResponse.getNoOutStockGoods())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050026);
        }

        Map<String, ChannelGoodsInfoVO> skuMap =
                getResponse.getGoodsInfoList().stream()
                        .collect(
                                Collectors.toMap(
                                        ChannelGoodsInfoVO::getGoodsInfoId, Function.identity()));
        goodsInfoList.stream()
                .filter(i -> skuMap.containsKey(i.getGoodsInfoId()))
                .forEach(
                        i -> {
                            ChannelGoodsInfoVO skuVo = skuMap.get(i.getGoodsInfoId());
                            i.setGoodsStatus(
                                    GoodsStatus.fromValue(skuVo.getGoodsStatus().toValue()));
                            i.setAddedFlag(skuVo.getAddedFlag());
                            i.setVendibility(skuVo.getVendibility());
                            i.setStock(skuVo.getStock());
                        });
    }

    /**
     * @description 订单提交
     * @author daiyitian
     * @date 2021/5/13 15:13
     * @param trades 订单信息
     */
    public void verifyTrade(List<Trade> trades) {
        if (this.isClose()) {
            if (CollectionUtils.isNotEmpty(trades)) {
                //含有第三方商品
                if (trades.stream()
                        .flatMap(i -> i.getTradeItems().stream())
                        .anyMatch(i -> Objects.nonNull(i.getThirdPlatformType()))) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
                }
                //含有第三方赠品
                if (trades.stream()
                        .filter(i -> CollectionUtils.isNotEmpty(i.getGifts()))
                        .flatMap(i -> i.getGifts().stream())
                        .anyMatch(i -> Objects.nonNull(i.getThirdPlatformType()))) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
                }

                if (trades.stream()
                        .filter(i -> CollectionUtils.isNotEmpty(i.getPreferential()))
                        .flatMap(i -> i.getPreferential().stream())
                        .anyMatch(i -> Objects.nonNull(i.getThirdPlatformType()))) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
                }
            }
            return;
        }

        List<ChannelOrderDTO> orderList = KsBeanUtil.convert(trades, ChannelOrderDTO.class);
        ChannelOrderVerifyRequest getRequest =
                ChannelOrderVerifyRequest.builder().orderList(orderList).build();
        ChannelOrderVerifyResponse getResponse =
                channelTradeProvider.verifyOrder(getRequest).getContext();
        if (CollectionUtils.isNotEmpty(getResponse.getOffAddedSkuId())) {
            // 发起MQ请求下架商品MQ，刷ES
            orderProducerService.sendThirdPlatformSkuOffAddedSync(getResponse.getOffAddedSkuId());
        }
        if (Boolean.TRUE.equals(getResponse.getInvalidGoods())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050027);
        }

        if (Boolean.TRUE.equals(getResponse.getNoAuthGoods())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050053);
        }
        if (Boolean.TRUE.equals(getResponse.getNoOutStockGoods())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050026);
        }

        // 验证通过，填充Trade的渠道标识
        trades.forEach(
                t -> {
                    if (Objects.isNull(t.getThirdPlatformTypes())) {
                        t.setThirdPlatformTypes(new ArrayList<>());
                    }
                    Set<ThirdPlatformType> thirdPlatformTypeSet = new HashSet<>();
                    if (CollectionUtils.isNotEmpty(t.getTradeItems())
                            && t.getTradeItems().stream()
                                    .anyMatch(g -> Objects.nonNull(g.getThirdPlatformType()))) {
                        thirdPlatformTypeSet.addAll(
                                t.getTradeItems().stream()
                                        .filter(i -> Objects.nonNull(i.getThirdPlatformType()))
                                        .map(TradeItem::getThirdPlatformType)
                                        .collect(Collectors.toSet()));
                    }
                    if (CollectionUtils.isNotEmpty(t.getGifts())
                            && t.getGifts().stream()
                                    .anyMatch(g -> Objects.nonNull(g.getThirdPlatformType()))) {
                        thirdPlatformTypeSet.addAll(
                                t.getGifts().stream()
                                        .filter(i -> Objects.nonNull(i.getThirdPlatformType()))
                                        .map(TradeItem::getThirdPlatformType)
                                        .collect(Collectors.toSet()));
                    }
                    if (CollectionUtils.isNotEmpty(t.getPreferential())
                            && t.getPreferential().stream()
                            .anyMatch(g -> Objects.nonNull(g.getThirdPlatformType()))) {
                        thirdPlatformTypeSet.addAll(
                                t.getPreferential().stream()
                                        .map(TradeItem::getThirdPlatformType)
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toSet()));
                    }
                    if (CollectionUtils.isNotEmpty(thirdPlatformTypeSet)) {
                        t.setThirdPlatformTypes(new ArrayList<>(thirdPlatformTypeSet));
                    }
                });
    }

    /**
     * @description 拆分订单
     * @author daiyitian
     * @date 2021/5/13 15:13
     * @param providerTrade 供应商订单信息
     */
    public void splitTrade(ProviderTrade providerTrade) {
        ChannelOrderSplitRequest splitRequest =
                ChannelOrderSplitRequest.builder()
                        .order(KsBeanUtil.convert(providerTrade, ChannelOrderDTO.class))
                        .build();
        ChannelOrderSplitResponse splitResponse =
                channelTradeProvider.splitOrder(splitRequest).getContext();
        if (CollectionUtils.isNotEmpty(splitResponse.getChannelTradeList())) {
            List<ThirdPlatformTrade> tradeList =
                    splitResponse.getChannelTradeList().stream()
                            .map(t -> {
                                ThirdPlatformTrade thirdPlatformTrade = KsBeanUtil.convert(t, ThirdPlatformTrade.class);
                                if(Objects.isNull(thirdPlatformTrade.getTradeState())){
                                  thirdPlatformTrade.setTradeState(new TradeState());
                                }
                                thirdPlatformTrade.setThirdPlatformPayErrorFlag(Boolean.FALSE);
                                thirdPlatformTrade.getTradeState().setPayState(PayState.NOT_PAID);
                                return thirdPlatformTrade;
                            })
                            .collect(Collectors.toList());
            thirdPlatformTradeRepository.saveAll(tradeList);
            if(providerTrade.getThirdPlatformType().toValue() == ThirdPlatformType.LINKED_MALL.toValue()) {
                // 计算第三方重量
                BigDecimal thirdFreight =
                        tradeList.stream()
                                .filter(t -> Objects.nonNull(t.getThirdPlatFormFreight()))
                                .map(ThirdPlatformTrade::getThirdPlatFormFreight)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                providerTrade.setThirdPlatFormFreight(thirdFreight);
            }
        }
    }

    /**
     * @description 支付
     * @author daiyitian
     * @date 2021/5/18 11:38
     * @param thirdTrade 第三方订单
     */
    public void pay(ThirdPlatformTrade thirdTrade) {
        log.info("渠道订单开始pay,businessId="+thirdTrade.getId());
        ChannelOrderPayRequest request = new ChannelOrderPayRequest();
        request.setChannelTrade(KsBeanUtil.convert(thirdTrade, ChannelTradeDTO.class));
        channelOrderProvider.pay(request);
    }

    /**
     * 根据业务id新增“下单并支付”的linkedmall订单 此处不能加事务，里面每个子方法事务的
     *
     * @param businessId 业务id
     */
    public void createOrPay(String businessId) {
        log.info("渠道订单开始createOrPay+businessId="+businessId);
        String now = DateUtil.nowTime();
        ThirdPlatformTradeResult result = new ThirdPlatformTradeResult();
        result.setAutoRefundTrades(new ArrayList<>());
        result.setSuccessTrades(new ArrayList<>());
        try {
            this.createOrder(businessId, result);
        } catch (Exception e) {
            log.error("订单业务id：" + businessId + "，第三方订单创建处理异常，自动退款! ", e);
            // 其他异常，整笔含linkedMall交易全退
            thirdPlatformReturnOrderService.autoOrderRefundByBusinessId(businessId);
            return;
        }
        // 只要出现提交失败，直接全退
        if (CollectionUtils.isNotEmpty(result.getAutoRefundTrades())) {
            // 更新作废原因
            if (StringUtils.isNotBlank(result.getAutoRefundErrorMessage())) {
                tradeService.updateObsoleteReason(result.getAutoRefundTrades().get(0).getId(), result.getAutoRefundErrorMessage());
            }
            thirdPlatformReturnOrderService.autoOrderRefund(result.getAutoRefundTrades());
        }

        // 创建成功的订单
        if (CollectionUtils.isNotEmpty(result.getSuccessTrades())) {
            for (Trade trade : result.getSuccessTrades()) {
                try {
                    int res = this.payOrder(trade.getId());
                    // 0：完成  1:自动退款 2：标记支付失败并定时观察 3标记支付失败
                    if (res == 1) {
                        thirdPlatformReturnOrderService.autoOrderRefund(
                                Collections.singletonList(trade));
                    } else if (res == 2) {
                        tradeService.updateThirdPlatformPay(trade.getId(), Boolean.TRUE);
                        redisService.hset(LISTEN_KEY, trade.getId(), now);
                    } else if (res == 3) {
                        tradeService.updateThirdPlatformPay(trade.getId(), Boolean.TRUE);
                    }
                } catch (Exception e) {
                    log.error("订单id：" + trade.getId() + "，第三方订单支付处理异常，继续观察! ", e);
                    redisService.hset(LISTEN_KEY, trade.getId(), now);
                }
            }
        }
    }

    /**
     * 去渠道补偿
     * @param tradeId 订单id
     */
    public void toCompensate(String tradeId){
        tradeService.updateThirdPlatformPay(tradeId, Boolean.TRUE);
        redisService.hset(LISTEN_KEY, tradeId, DateUtil.nowTime());
    }

    /**
     * 创建接口
     *
     * @param businessId 业务id
     */
    public void createOrder(String businessId, ThirdPlatformTradeResult result) {
        log.info("渠道订单开始createOrder,businessId="+businessId);
        List<Trade> trades =
                orderCommonService.findTradesByBusinessId(businessId).stream()
                        .filter(t -> CollectionUtils.isNotEmpty(t.getThirdPlatformTypes()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(trades)) {
            log.info("trades is empty");
            return;
        }
        Set<String> errorTrade = new HashSet<>();
        List<String> tradeIds = trades.stream().map(Trade::getId).collect(Collectors.toList());
        Map<String, List<ThirdPlatformTrade>> thirdTrades =
                thirdPlatformTradeRepository.findListByTradeIdIn(tradeIds).stream()
                        .collect(Collectors.groupingBy(ThirdPlatformTrade::getTradeId));
        if(MapUtils.isEmpty(thirdTrades)){
                log.info("thirdTrades is empty");
          return;
        }
        for (Trade trade : trades) {
            List<ThirdPlatformTrade> thirdPlatformTrades =
                    thirdTrades.getOrDefault(trade.getId(), Collections.emptyList());
            // 更新订单编号
            for (ThirdPlatformTrade thirdTrade : thirdPlatformTrades) {
                if (Objects.isNull(thirdTrade.getTradeState())) {
                    thirdTrade.setTradeState(new TradeState());
                }
                // 如果已经创建过，则跳过
                if (CollectionUtils.isNotEmpty(thirdTrade.getThirdPlatformOrderIds())) {
                    continue;
                }
                try {
                    ChannelOrderCreateRequest request = new ChannelOrderCreateRequest();
                    request.setChannelTrade(KsBeanUtil.convert(thirdTrade, ChannelTradeDTO.class));
                    ChannelOrderCreateResponse response =
                            channelOrderProvider.create(request).getContext();
                    thirdTrade.setThirdPlatformOrderIds(response.getChannelOrderIds());
                    thirdTrade.setOutOrderIds(response.getOutOrderIds());
                    thirdTrade.getTradeState().setPayState(PayState.NOT_PAID);
                    thirdPlatformTradeRepository.save(thirdTrade);
                } catch (SbcRuntimeException e) {
                    // 其中一个抛错，退出当前循环
                    log.error("创建第三方平台订单抛错", e);
                    errorTrade.add(trade.getId());
                    // 业务错误问题
                    String message = StringUtils.isNotBlank(e.getResult()) ? e.getResult() : e.getMessage();
                    if (CommonErrorCodeEnum.K999999.getCode().equals(e.getErrorCode())) {
                        result.setAutoRefundErrorMessage(message);
                    }
                    break;
                } catch (Exception e) {
                    // 其中一个抛错，退出当前循环
                    log.error("创建第三方平台订单抛错", e);
                    errorTrade.add(trade.getId());
                    break;
                }
            }
        }
        result.getAutoRefundTrades()
                .addAll(
                        trades.stream()
                                .filter(t -> errorTrade.contains(t.getId()))
                                .collect(Collectors.toList()));
        result.getSuccessTrades()
                .addAll(
                        trades.stream()
                                .filter(t -> !errorTrade.contains(t.getId()))
                                .collect(Collectors.toList()));
    }

    /**
     * 支付
     *
     * @param tradeId 订单id
     * @return 0：完成 1:自动退款 2：定时观察 3标记支付失败
     */
    public int payOrder(String tradeId) {
        log.info("渠道订单开始payOrder,businessId="+tradeId);
        List<ThirdPlatformTrade> thirdTrades =
                thirdPlatformTradeRepository.findListByTradeId(tradeId);
        if (CollectionUtils.isEmpty(thirdTrades)) {
            return 1;
        }
        int res = 0;
        // 外部异常，每笔重试2次，2次之后停止并进入定时观察
        int netErr = 0;
        int len = thirdTrades.size();
        for (int i = 0; i < len; i++) {
            ThirdPlatformTrade thirdTrade = thirdTrades.get(i);
            if (thirdTrade.getTradeState() == null) {
                thirdTrade.setTradeState(new TradeState());
            }
            try {
                this.pay(thirdTrade);
                thirdTrade.setThirdPlatformPayErrorFlag(Boolean.FALSE);
                thirdTrade.getTradeState().setPayState(PayState.PAID);
                thirdPlatformTradeRepository.save(thirdTrade);
            } catch (SbcRuntimeException e) {
                // 业务错误问题
                if (CommonErrorCodeEnum.K999999.getCode().equals(e.getErrorCode())) {
                    log.error(
                            "第三方子订单id：{}，第三方订单支付失败! tradeId={} error={}",
                            thirdTrade.getId(),
                            tradeId,
                            StringUtils.isNotBlank(e.getResult()) ? e.getResult() : e.getMessage());
                    if (e.getResult().contains("订单未找到")) {
                        i--;
                        continue;
                    }
                    // 如果第一个抛错，直接自动退款
                    if (i == 0) {
                        return 1;
                    }
                    thirdTrade.setThirdPlatformPayErrorFlag(Boolean.TRUE);
                    thirdPlatformTradeRepository.save(thirdTrade);
                } else {
                    // 网络超出2次，定时观察
                    if (netErr > 0) {
                        netErr = 0;
                        this.setUnconfirmed(thirdTrade);
                    } else {
                        log.error(
                                "第三方子订单id：{}，第三方订单网络异常! tradeId={} error={}",
                                thirdTrade.getId(),
                                tradeId,
                                e.getMessage());
                        i--;
                        netErr++;
                    }
                }
            } catch (Exception e) {
                // 网络超出2次，定时观察
                if (netErr > 1) {
                    netErr = 0;
                    this.setUnconfirmed(thirdTrade);
                } else {
                    i--;
                    netErr++;
                    log.error("第三方子订单id：{}，第三方订单网络异常! tradeId={}", thirdTrade.getId(), tradeId, e);
                }
            }
        }

        // 设定providerTrade待确认状态
        if (thirdTrades.stream()
                .anyMatch(
                        t ->
                                t.getTradeState() != null
                                        && PayState.UNCONFIRMED
                                                == t.getTradeState().getPayState())) {
            thirdTrades.stream()
                    .filter(
                            t ->
                                    t.getTradeState() != null
                                            && PayState.UNCONFIRMED
                                                    == t.getTradeState().getPayState())
                    .map(ThirdPlatformTrade::getParentId)
                    .distinct()
                    .forEach(
                            providerTradeId ->
                                    providerTradeService.updateThirdPlatformPayState(
                                            providerTradeId, PayState.UNCONFIRMED));
        }

        // 设定providerTrade付款错误状态
        if (thirdTrades.stream()
                .anyMatch(t -> Boolean.TRUE.equals(t.getThirdPlatformPayErrorFlag()))) {
            thirdTrades.stream()
                    .filter(t -> Boolean.TRUE.equals(t.getThirdPlatformPayErrorFlag()))
                    .map(ThirdPlatformTrade::getParentId)
                    .distinct()
                    .forEach(
                            providerTradeId ->
                                    providerTradeService.updateThirdPlatformPayFlag(
                                            providerTradeId, Boolean.TRUE));
        }

        long errCount =
                thirdTrades.stream()
                        .filter(t -> Boolean.TRUE.equals(t.getThirdPlatformPayErrorFlag()))
                        .count();
        if (thirdTrades.stream()
                .anyMatch(
                        t ->
                                t.getTradeState() != null
                                        && PayState.UNCONFIRMED
                                                == t.getTradeState().getPayState())) {
            res = 2;
        } else if (errCount == thirdTrades.size()) {
            res = 1;
        } else if (errCount > 0) {
            res = 3;
        }
        return res;
    }

    /**
     * @description 根据子订单重新拆分订单明细
     * @author daiyitian
     * @date 2021/5/13 15:13
     * @param trades 第三方订单信息
     * @return 第三方订单信息
     */
    public List<ThirdPlatformTrade> detailSplit(List<ThirdPlatformTrade> trades) {
        List<ThirdPlatformTrade> res = new ArrayList<>();
        //先放无子订单
        trades.stream().filter(t -> CollectionUtils.isEmpty(t.getSuborderList())).forEach(res::add);
        //放含子订单
        trades.stream()
                .filter(t -> CollectionUtils.isNotEmpty(t.getSuborderList()))
                .forEach(
                        t -> {
                            Map<String, TradeItem> itemMap = new HashMap<>();
                            Map<String, TradeItem> giftMap = new HashMap<>();
                            Map<String, List<TradeItem>> preferentialMap = new HashMap<>();
                            if (CollectionUtils.isNotEmpty(t.getTradeItems())) {
                                itemMap.putAll(
                                        t.getTradeItems().stream()
                                                .filter(
                                                        i ->
                                                                StringUtils.isNotBlank(
                                                                        i.getThirdPlatformSkuId()))
                                                .collect(
                                                        Collectors.toMap(
                                                                TradeItem::getThirdPlatformSkuId,
                                                                Function.identity())));
                            }
                            if (CollectionUtils.isNotEmpty(t.getGifts())) {
                                giftMap.putAll(
                                        t.getGifts().stream()
                                                .filter(
                                                        i ->
                                                                StringUtils.isNotBlank(
                                                                        i.getThirdPlatformSkuId()))
                                                .collect(
                                                        Collectors.toMap(
                                                                TradeItem::getThirdPlatformSkuId,
                                                                Function.identity())));
                            }

                            if (CollectionUtils.isNotEmpty(t.getPreferential())) {
                                preferentialMap.putAll(
                                        t.getPreferential().stream()
                                                .filter(
                                                        i ->
                                                                StringUtils.isNotBlank(
                                                                        i.getThirdPlatformSkuId()))
                                                .collect(Collectors.groupingBy(TradeItem::getSkuId)));
                            }

                            t.getSuborderList()
                                    .forEach(
                                            s -> {
                                                ThirdPlatformTrade trade =
                                                        BeanUtils.beanCopy(
                                                                t, ThirdPlatformTrade.class);
                                                if (trade != null) {
                                                    List<TradeItem> items = new ArrayList<>();
                                                    List<TradeItem> gifts = new ArrayList<>();
                                                    List<TradeItem> preferentialList = new ArrayList<>();
                                                    s.getItemList()
                                                            .forEach(
                                                                    i -> {
                                                                        TradeItem item =
                                                                                itemMap.get(
                                                                                        i
                                                                                                .getSkuId());
                                                                        TradeItem gift =
                                                                                giftMap.get(
                                                                                        i
                                                                                                .getSkuId());
                                                                        List<TradeItem> preferential =
                                                                                preferentialMap.get(i.getSkuId());
                                                                        if (item != null) {
                                                                            items.add(item);
                                                                        }
                                                                        if (gift != null) {
                                                                            gifts.add(item);
                                                                        }
                                                                        if (CollectionUtils.isNotEmpty(preferential)){
                                                                            preferentialList.addAll(preferential);
                                                                        }
                                                                    });
                                                    trade.setTradeItems(items);
                                                    trade.setGifts(gifts);
                                                    trade.setPreferential(preferentialList);
                                                    trade.setOutOrderIds(
                                                            Collections.singletonList(
                                                                    s.getSuborderId()));
                                                    res.add(trade);
                                                }
                                            });
                        });
        return res;
    }

    /** 补偿LinkedMall订单 此处不能加事务，里面每个子方法事务的 */
    public void compensate() {
        Map<String, Object> keyMap = redisService.hgetAllObj(LISTEN_KEY);
            if (MapUtils.isNotEmpty(keyMap)) {
            for (Map.Entry<String, Object> entry : keyMap.entrySet()) {
                String key = entry.getKey();
                int res;
                ThirdPlatformTradeResult result = new ThirdPlatformTradeResult();
                try {
                    this.compensate(key, result);
                    res = result.getStatus();
                } catch (Exception e) {
                    log.error("订单id：" + key + "，第三方订单补偿处理异常! ", e);
                    continue;
                }
                // 0：完成  1:自动退款 2：继续观察 3标记支付失败
                if (res == 0) {
                    tradeService.updateThirdPlatformPay(key, Boolean.FALSE);
                    redisService.hdelete(LISTEN_KEY, key);
                } else if (res == 1) {
                    if(CollectionUtils.isNotEmpty(result.getAutoRefundTrades())) {
                        thirdPlatformReturnOrderService.autoOrderRefund(result.getAutoRefundTrades());
                    }
                    redisService.hdelete(LISTEN_KEY, key);
                } else if (res == 2) {
                    tradeService.updateThirdPlatformPay(key, Boolean.TRUE);
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime time =
                            entry.getValue() != null
                                    ? DateUtil.parse(
                                                    Objects.toString(entry.getValue()),
                                                    DateUtil.FMT_TIME_1)
                                            .plusMinutes(30)
                                    : null;
                    // 如果超时30秒，还处于网络异常就停止继续观察
                    if (time == null || time.isBefore(now)) {
                        redisService.hdelete(LISTEN_KEY, key);
                    }
                } else if (res == 3) {
                    tradeService.updateThirdPlatformPay(key, Boolean.TRUE);
                    redisService.hdelete(LISTEN_KEY, key);
                }
            }
        }
    }

    /**
     * 补偿性判断
     *
     * @param tradeId 订单id
     * @param result 结果参数
     */
    private void compensate(String tradeId, ThirdPlatformTradeResult result) {
        result.setErrProviderTradeIds(new ArrayList<>());
        result.setNetErrProviderTradeIds(new ArrayList<>());
        Trade trade = tradeService.detail(tradeId);
        int res = 0;
        if (trade != null) {
            List<ThirdPlatformTrade> thirdTrades =
                    thirdPlatformTradeRepository.findListByTradeId(tradeId);
            if (CollectionUtils.isNotEmpty(thirdTrades)) {
                ChannelOrderCompensateRequest compensateRequest =
                        new ChannelOrderCompensateRequest();
                compensateRequest.setOrderList(
                        KsBeanUtil.convert(thirdTrades, ChannelOrderDTO.class));
                // 渠道补偿
                try {
                    ChannelOrderCompensateResponse tmpResponse =
                            channelTradeProvider.compensateOrder(compensateRequest).getContext();
                    result.getErrProviderTradeIds().addAll(tmpResponse.getPayErrProviderTradeIds());
                    result.getNetErrProviderTradeIds()
                            .addAll(tmpResponse.getUnconfirmedProviderTradeIds());
                    if (CollectionUtils.isNotEmpty(tmpResponse.getPaySuccessThirdTradeId())) {
                        thirdTrades.stream()
                                .filter(
                                        t ->
                                                tmpResponse
                                                        .getPaySuccessThirdTradeId()
                                                        .contains(t.getId()))
                                .forEach(
                                        t -> {
                                            if (t.getTradeState() == null) {
                                                t.setTradeState(new TradeState());
                                            }
                                            t.getTradeState().setPayState(PayState.PAID);
                                            t.setThirdPlatformPayErrorFlag(Boolean.FALSE);
                                            thirdPlatformTradeRepository.save(t);
                                        });
                    }

                    // 第三方订单支付错误
                    if (CollectionUtils.isNotEmpty(tmpResponse.getPayErrThirdTradeId())) {
                        thirdTrades.stream()
                                .filter(
                                        t ->
                                                tmpResponse
                                                        .getPayErrThirdTradeId()
                                                        .contains(t.getId()))
                                .forEach(
                                        t -> {
                                            if (t.getTradeState() == null) {
                                                t.setTradeState(new TradeState());
                                            }
                                            t.setThirdPlatformPayErrorFlag(Boolean.TRUE);
                                            t.getTradeState().setPayState(PayState.NOT_PAID);
                                            thirdPlatformTradeRepository.save(t);
                                        });
                    }
                    // 第三方订单待确认
                    if (CollectionUtils.isNotEmpty(tmpResponse.getUnconfirmedThirdTradeId())) {
                        thirdTrades.stream()
                                .filter(
                                        t ->
                                                tmpResponse
                                                        .getUnconfirmedThirdTradeId()
                                                        .contains(t.getId()))
                                .forEach(
                                        t -> {
                                            if (t.getTradeState() == null) {
                                                t.setTradeState(new TradeState());
                                            }
                                            t.getTradeState().setPayState(PayState.UNCONFIRMED);
                                            thirdPlatformTradeRepository.save(t);
                                        });
                    }
                } catch (SbcRuntimeException e) {
                    // VAS服务挂了产生异常
                    thirdTrades.forEach(
                            t -> {
                                if (t.getTradeState() == null) {
                                    t.setTradeState(new TradeState());
                                }
                                t.getTradeState().setPayState(PayState.UNCONFIRMED);
                                thirdPlatformTradeRepository.save(t);
                                result.getNetErrProviderTradeIds().add(t.getParentId());
                            });
                }

                // 设定providerTrade状态
                if (CollectionUtils.isNotEmpty(result.getNetErrProviderTradeIds())) {
                    result.getNetErrProviderTradeIds().stream()
                            .distinct()
                            .forEach(
                                    providerTradeId ->
                                            providerTradeService.updateThirdPlatformPayState(
                                                    providerTradeId, PayState.UNCONFIRMED));
                }

                // 设定providerTrade的付款错误状态
                if (CollectionUtils.isNotEmpty(result.getErrProviderTradeIds())) {
                    result.getErrProviderTradeIds().stream()
                            .distinct()
                            .forEach(
                                    providerTradeId ->
                                            providerTradeService.updateThirdPlatformPayFlag(
                                                    providerTradeId, Boolean.TRUE));
                }

                // 设定providerTrade的付款成功状态
                if (CollectionUtils.isEmpty(result.getNetErrProviderTradeIds())
                        && CollectionUtils.isEmpty(result.getErrProviderTradeIds())) {
                    thirdTrades.stream()
                            .map(ThirdPlatformTrade::getParentId)
                            .distinct()
                            .forEach(
                                    providerTradeId -> {
                                        providerTradeService.updateThirdPlatformPayState(
                                                providerTradeId, PayState.PAID);
                                        providerTradeService.updateThirdPlatformPayFlag(
                                                providerTradeId, Boolean.FALSE);
                                    });
                }

                // 比较  错误字段
                long errCount =
                        thirdTrades.stream()
                                .filter(t -> Boolean.TRUE.equals(t.getThirdPlatformPayErrorFlag()))
                                .count();
                // 存在待确认，继续观察
                if (thirdTrades.stream()
                        .anyMatch(
                                t ->
                                        t.getTradeState() != null
                                                && PayState.UNCONFIRMED
                                                        == t.getTradeState().getPayState())) {
                    res = 2;
                } else if (errCount == thirdTrades.size()) {
                    // 完全错误，自动退款
                    res = 1;
                    result.setAutoRefundTrades(Collections.singletonList(trade));
                } else if (errCount > 0) {
                    res = 3;
                }
            }

            //积分订单没有退款
            if(OrderType.POINTS_ORDER.equals(trade.getOrderType())) {
                //成功，则扣除会员积分
                if (res == 0) {
                    tradeService.payByPointTrade(trade);
                }
                result.setAutoRefundTrades(Collections.emptyList());
            }
        }
        result.setStatus(res);
    }

    /**
     * @description 设定为待确认
     * @author daiyitian
     * @date 2021/5/17 16:55
     * @param thirdTrade 第三方平台订单
     */
    private void setUnconfirmed(ThirdPlatformTrade thirdTrade) {
        thirdTrade.getTradeState().setPayState(PayState.UNCONFIRMED);
        thirdPlatformTradeRepository.save(thirdTrade);
        log.error(
                "第三方子订单id：{}，第三方订单网络异常超出2次，定时观察! tradeId={}",
                thirdTrade.getId(),
                thirdTrade.getTradeId());
    }

    /**
     * 根据订单号查询供货商第三方平台订单
     *
     * @param tradeIds 批量订单号
     */
    public List<ThirdPlatformTrade> listByTradeIds(List<String> tradeIds) {
        return thirdPlatformTradeRepository.findListByTradeIdIn(tradeIds);
    }


    /**
     * 修改文档
     * 专门用于数据修改服务,不允许数据新增的时候调用
     *
     * @param trade
     */
    public void updateThirdPlatformTrade(ThirdPlatformTrade trade) {
        thirdPlatformTradeRepository.save(trade);
    }

    /**
     * 根据父订单号批量查询第三方订单
     *
     * @param parentTids
     */
    public List<ThirdPlatformTrade> findListByParentIds(List<String> parentTids ) {
        return thirdPlatformTradeRepository.findListByParentIdIn(parentTids);
    }

    /**
     * 根据主订单号查询第三方订单
     *
     * @param tradeId
     */
    public List<ThirdPlatformTrade> findListByTradeId(String tradeId) {
        return thirdPlatformTradeRepository.findListByTradeId(tradeId);
    }


    /**
     * 更新订单
     *
     * @param tradeUpdateRequest
     */
    @GlobalTransactional
    @Transactional
    public void updateThirdPlatformTrade(ThirdPlatformTradeUpdateRequest tradeUpdateRequest) {
        this.updateThirdPlatformTrade(KsBeanUtil.convert(tradeUpdateRequest.getTrade(), ThirdPlatformTrade.class));
    }

    /**
     * 更新订单状态,同时更新父订单、主订单状态
     *
     * @param tradeUpdateStateDTO
     */
    @Transactional
    public void updateThirdPlatformTradeState(ThirdPlatformTradeUpdateStateDTO tradeUpdateStateDTO) {
        // 更新后的状态
        FlowState newFlowState = tradeUpdateStateDTO.getFlowState();
        DeliverStatus newDeliverStatus = tradeUpdateStateDTO.getDeliverStatus();
        PayState newPayState = tradeUpdateStateDTO.getPayState();
        // 获取数据库订单详情
        ThirdPlatformTrade thirdPlatformTrade = thirdPlatformTradeRepository.findFirstById(tradeUpdateStateDTO.getId());
        // 当前数据库中订单状态
        FlowState oldFlowState= thirdPlatformTrade.getTradeState().getFlowState();
        DeliverStatus oldDeliverStatus = thirdPlatformTrade.getTradeState().getDeliverStatus();
        PayState oldPatState = thirdPlatformTrade.getTradeState().getPayState();
        if(FlowState.VOID == oldFlowState || FlowState.COMPLETED == oldFlowState) {
            return;
        }
        // 拼装日志详情,更新状态
        StringBuilder eventDetail = new StringBuilder("同步linkedmall订单").append(tradeUpdateStateDTO.getId()).append("状态");
        if(Objects.nonNull(newFlowState) && !(newFlowState == oldFlowState)) {
            eventDetail.append(",订单状态从【").append(oldFlowState.getDescription())
                    .append("】扭转为【").append(newFlowState.getDescription()).append('】');
            thirdPlatformTrade.getTradeState().setFlowState(newFlowState);
        }
        if(Objects.nonNull(newDeliverStatus) && !(newDeliverStatus == oldDeliverStatus)) {
            eventDetail.append(",发货状态从【").append(oldDeliverStatus.getDescription())
                    .append("】扭转为【").append(newDeliverStatus.getDescription()).append('】');
            thirdPlatformTrade.getTradeState().setDeliverStatus(newDeliverStatus);
        }
        if(PayState.UNCONFIRMED == oldPatState && Objects.nonNull(newPayState) && !(newPayState == oldPatState)) {
            eventDetail.append(",支付状态从【").append(oldPatState.getDescription())
                    .append("】扭转为【").append(newPayState.getDescription()).append('】');
            thirdPlatformTrade.getTradeState().setPayState(newPayState);
        }

        // 1、更新三级订单状态
        Operator system = Operator.builder().name("system").account("system").platform(Platform.PLATFORM).build();
        TradeEventLog tradeEventLog = TradeEventLog
                .builder()
                .operator(system)
                .eventType("同步linkedmall订单状态")
                .eventDetail(eventDetail.toString())
                .eventTime(LocalDateTime.now())
                .build();
        thirdPlatformTrade.appendTradeEventLog(tradeEventLog);
        thirdPlatformTradeRepository.save(thirdPlatformTrade);

        String parentId = tradeUpdateStateDTO.getParentId();
        String tradeId = tradeUpdateStateDTO.getTradeId();

        // 2、获取父订单所有子订单
        ProviderTrade providerTrade = providerTradeService.providerDetail(parentId);
        if(FlowState.VOID == providerTrade.getTradeState().getFlowState() ||
                FlowState.COMPLETED == providerTrade.getTradeState().getFlowState()) {
            return;
        }
        List<ThirdPlatformTrade> thirdPlatformTrades = thirdPlatformTradeRepository.findListByParentId(parentId);
        // 防止事务未提交，查出来的数据未更新
        thirdPlatformTrades.forEach(trade -> {
            if (trade.getId().equals(tradeUpdateStateDTO.getId())) {
                if(Objects.nonNull(newFlowState)) {
                    trade.getTradeState().setFlowState(newFlowState);
                }
                if(Objects.nonNull(newDeliverStatus)) {
                    trade.getTradeState().setDeliverStatus(newDeliverStatus);
                }
            }
        });
        TradeVO tradeVO = KsBeanUtil.convert(providerTrade, TradeVO.class);
        boolean updateProviderFlag = changeParentTradeState(tradeVO, KsBeanUtil.convert(thirdPlatformTrades,
                TradeVO.class),tradeEventLog);
        //3、更新二级父订单状态
        if(updateProviderFlag) {
            providerTrade = KsBeanUtil.convert(tradeVO, ProviderTrade.class);
            providerTrade.appendTradeEventLog(tradeEventLog);
            providerTradeService.updateProviderTrade(providerTrade);

            //4、获取一级主订单所有子订单
            Trade trade = tradeService.detail(tradeId);
            if(FlowState.VOID == trade.getTradeState().getFlowState() ||
                    FlowState.COMPLETED == trade.getTradeState().getFlowState()) {
                return;
            }
            List<ProviderTrade> providerTrades = providerTradeService.findListByParentId(tradeId);
            // 防止事务未提交，查出来的数据未更新
            providerTrades.forEach(pTrade -> {
                if (pTrade.getId().equals(parentId)) {
                    if(Objects.nonNull(newFlowState)) {
                        pTrade.getTradeState().setFlowState(newFlowState);
                    }
                    if(Objects.nonNull(newDeliverStatus)) {
                        pTrade.getTradeState().setDeliverStatus(newDeliverStatus);
                    }
                }
            });
            TradeVO convert = KsBeanUtil.convert(trade, TradeVO.class);
            boolean updateTradeFlag = changeParentTradeState(convert, KsBeanUtil.convert(providerTrades,
                    TradeVO.class),tradeEventLog);
            // 5、更新一级主订单状态
            if(updateTradeFlag) {
                trade = KsBeanUtil.convert(convert, Trade.class);
                trade.appendTradeEventLog(tradeEventLog);
                tradeService.updateTrade(trade);
                // 同步订单状态至ES的订单开票索引
                orderInvoiceService.syncStateToInvoice(trade);
            }
        }
    }

    // 根据子订单状态判断是否更新父订单状态,子订单状态一致时更改父订单状态
    private boolean changeParentTradeState(TradeVO parentTrade, List<TradeVO> sonTrades,TradeEventLog tradeEventLog) {
        boolean updateFlag = false;
        // 1、获取父订单状态
        FlowState pFlowState = parentTrade.getTradeState().getFlowState();
        DeliverStatus pDeliverStatus = parentTrade.getTradeState().getDeliverStatus();
        PayState pPayState = parentTrade.getTradeState().getPayState();
        if(CollectionUtils.isNotEmpty(sonTrades)) {
            // 获取所有子订单去重后的 订单状态集合
            List<FlowState> flowStateList =
                    sonTrades.stream().map(v -> v.getTradeState().getFlowState()).distinct().collect(Collectors.toList());
            // 获取所有子订单去重后的 配送状态集合
            List<DeliverStatus> deliverStatusList =
                    sonTrades.stream().map(v -> v.getTradeState().getDeliverStatus()).distinct().collect(Collectors.toList());
            // 拼装日志详情,更新状态
            StringBuilder eventDetail = new StringBuilder("同步linkedmall订单").append(parentTrade.getId()).append("状态");
            // 所有子订单状态一致且与父订单状态不一致时，更改父订单状态
            if(CollectionUtils.isNotEmpty(flowStateList) && flowStateList.size() == 1 && !(flowStateList.get(0) == pFlowState)) {
                eventDetail.append(",订单状态从【").append(pFlowState.getDescription())
                        .append("】扭转为【").append(flowStateList.get(0).getDescription()).append('】');
                parentTrade.getTradeState().setFlowState(flowStateList.get(0));
                updateFlag = true;
            }
            if(CollectionUtils.isNotEmpty(deliverStatusList) && deliverStatusList.size() == 1 && !(deliverStatusList.get(0) == pDeliverStatus)) {
                eventDetail.append(",发货状态从【").append(pDeliverStatus.getDescription())
                        .append("】扭转为【").append(deliverStatusList.get(0).getDescription()).append('】');
                parentTrade.getTradeState().setDeliverStatus(deliverStatusList.get(0));
                updateFlag = true;
            }
            // 如果支付状态为未确认，
            if(PayState.UNCONFIRMED == pPayState && ThirdPlatformType.LINKED_MALL == parentTrade.getThirdPlatformType()) {
                // 获取所有子订单去重后的 支付状态集合
                List<PayState> payStateList =
                        sonTrades.stream().map(v -> v.getTradeState().getPayState()).distinct().collect(Collectors.toList());
                if(CollectionUtils.isNotEmpty(payStateList) && payStateList.size() == 1 && !(payStateList.get(0) == pPayState)) {
                    eventDetail.append(",支付状态从【").append(pPayState.getDescription())
                            .append("】扭转为【").append(payStateList.get(0).getDescription()).append('】');
                    parentTrade.getTradeState().setPayState(payStateList.get(0));
                    updateFlag = true;
                }
            }
            tradeEventLog.setEventDetail(eventDetail.toString());
        }
        return updateFlag;
    }

    /**
     * 订单分页
     *
     * @param whereCriteria 条件
     * @param request       参数
     * @return
     */
    public Page<ThirdPlatformTrade> page(Criteria whereCriteria, ThirdPlatformTradeQueryRequest request) {
        long totalSize = this.countNum(whereCriteria, request);
        if (totalSize < 1) {
            return new PageImpl<>(new ArrayList<>(), request.getPageRequest(), totalSize);
        }
        request.putSort(request.getSortColumn(), request.getSortRole());
        Query query = new Query(whereCriteria);
        return new PageImpl<>(mongoTemplate.find(query.with(request.getPageRequest()), ThirdPlatformTrade.class), request
                .getPageable(), totalSize);
    }

    /**
     * 统计数量
     *
     * @param whereCriteria
     * @param request
     * @return
     */
    public long countNum(Criteria whereCriteria, ThirdPlatformTradeQueryRequest request) {
        request.putSort(request.getSortColumn(), request.getSortRole());
        Query query = new Query(whereCriteria);
        long totalSize = mongoTemplate.count(query, ThirdPlatformTrade.class);
        return totalSize;
    }

    public ThirdPlatformTrade queryByThirdPlatformOrderId(ThirdPlatformTradeQueryRequest thirdPlatformTradeQueryRequest ) {
        List<ThirdPlatformTrade> thirdPlatformTrades =
                mongoTemplate.find(new Query(thirdPlatformTradeQueryRequest.getWhereCriteria()), ThirdPlatformTrade.class);
        if (CollectionUtils.isNotEmpty(thirdPlatformTrades)){
            return thirdPlatformTrades.get(0);
        }
        return null;
    }

	public void voidTrade(String providerTradeId){
		mongoTemplate.updateMulti(new Query(Criteria.where("parentId").is(providerTradeId)), new Update().set("tradeState.flowState", FlowState.VOID), ThirdPlatformTrade.class);
	}

    public void reAdd(List<String> tradeIds) {
        log.warn("重新reAdd第三方渠道信息开始");
        ThirdPlatformTradeQueryRequest tradeQueryRequest = new ThirdPlatformTradeQueryRequest();
        tradeQueryRequest.setTradeIds(tradeIds);
        tradeQueryRequest.setEmptyThirdPlatformOrderId(Boolean.TRUE);
        Query query = new Query(tradeQueryRequest.getWhereCriteria());
        List<ThirdPlatformTrade> thirdPlatformTrades = mongoTemplate.find(query, ThirdPlatformTrade.class);
        if (CollectionUtils.isEmpty(thirdPlatformTrades)) {
            log.warn("重新reAdd第三方渠道信息不存在");
            return;
        }

        TradeQueryRequest queryRequest = new TradeQueryRequest();
        queryRequest.setIds(thirdPlatformTrades.stream().map(ThirdPlatformTrade::getTradeId).toArray(String[]::new));
        // 如果未指定，不支持拼团订单做重新同步
        if (CollectionUtils.isEmpty(tradeIds)) {
            queryRequest.setGrouponFlag(Boolean.FALSE);
        }
        TradeState state = new TradeState();
        state.setPayState(PayState.PAID);
        queryRequest.setTradeState(state);
        queryRequest.setNotFlowStates(Collections.singletonList(FlowState.VOID));
        List<Trade> trades = tradeService.queryAll(queryRequest);
        if (CollectionUtils.isNotEmpty(trades)) {
            for (Trade trade : trades) {
                this.createOrPay(trade.getId());
            }
        }
        log.warn("重新reAdd第三方渠道信息结束");
    }

    /**
     * @description 判断是否关闭vas服务
     * @author daiyitian
     * @date 2021/5/29 14:48
     * @return boolean true:关闭 false:开启
     */
    public boolean isClose() {
        boolean vasFlag = true;
        // 是否开启LM
        if (VASStatus.ENABLE
                .toValue()
                .equals(
                        redisService.hget(
                                ConfigKey.VALUE_ADDED_SERVICES.toString(),
                                VASConstants.THIRD_PLATFORM_LINKED_MALL.toValue()))) {
            vasFlag = false;
        }
        // 是否开启VOP
        if (VASStatus.ENABLE
                .toValue()
                .equals(
                        redisService.hget(
                                ConfigKey.VALUE_ADDED_SERVICES.toString(),
                                VASConstants.THIRD_PLATFORM_VOP.toValue()))) {
            vasFlag = false;
        }
        return vasFlag;
    }
}
