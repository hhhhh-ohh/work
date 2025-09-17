package com.wanmi.sbc.order.optimization.trade1.snapshot.cyclebuy.service;

import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.info.TradeConfirmGoodsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.DeliveryCycleType;
import com.wanmi.sbc.goods.bean.vo.BuyCycleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSettingGetResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionStoreSettingGetByStoreIdResponse;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.dto.CycleDeliveryPlanDTO;
import com.wanmi.sbc.order.bean.dto.TradeBuyCycleDTO;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.optimization.trade1.snapshot.ParamsDataVO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.QueryDataInterface;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.TradeBuyAssembleInterface;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.TradeBuyCheckInterface;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.impl.TradeBuyService;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.trade.service.TradeBuyCycleService;
import com.wanmi.sbc.order.trade.service.TradeItemSnapshotService;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xuyunpeng
 * @className CycleBuyService
 * @description
 * @date 2022/10/17 11:32 AM
 **/
@Service("cycleBuy")
public class CycleBuyService extends TradeBuyService {
    @Autowired
    @Qualifier("queryDataService")
    private QueryDataInterface queryDataInterface;

    @Autowired
    @Qualifier("tradeBuyCheckService")
    private TradeBuyCheckInterface tradeBuyCheckInterface;

    @Autowired
    @Qualifier("tradeBuyAssembleService")
    private TradeBuyAssembleInterface tradeBuyAssembleInterface;

    @Autowired
    private TradeItemSnapshotService tradeItemSnapshotService;

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private TradeBuyCycleService tradeBuyCycleService;

    @Override
    public void queryData(ParamsDataVO paramsDataVO) {
        TradeBuyRequest request = paramsDataVO.getRequest();
        paramsDataVO.setCustomerVO(queryDataInterface.getCustomerInfo(request.getCustomerId()));

        // 周期购信息
        BuyCycleVO buyCycleVO = redisService.getObj(
                RedisKeyConstant.BUY_CYCLE_TRADE_INFO + request.getTerminalToken(),
                BuyCycleVO.class);
        if (buyCycleVO == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        paramsDataVO.setBuyCycleVO(buyCycleVO);

        List<String> skuIds =
                request.getTradeItemRequests().stream()
                        .map(TradeItemRequest::getSkuId)
                        .collect(Collectors.toList());
        TradeConfirmGoodsRequest goodsRequest =
                TradeConfirmGoodsRequest.builder()
                        .skuIds(skuIds)
                        .isHavSpecText(Boolean.TRUE)
                        .isHavIntervalPrice(Boolean.FALSE)
                        .showLabelFlag(Boolean.FALSE)
                        .showSiteLabelFlag(Boolean.FALSE)
                        .isHavRedisStock(Boolean.FALSE)
                        .build();
        GoodsInfoResponse goodsInfoResponse = queryDataInterface.getGoodsInfo(goodsRequest);
        paramsDataVO.setGoodsInfoResponseSourceData(goodsInfoResponse);
        GoodsInfoResponse infoResponse =
                KsBeanUtil.convert(goodsInfoResponse, GoodsInfoResponse.class);
        paramsDataVO.setGoodsInfoResponse(infoResponse);
        // 查询店铺
        StoreVO storeVO =
                queryDataInterface.getStoreInfo(
                        goodsInfoResponse.getGoodsInfos().get(0).getStoreId(),
                        request.getCustomerId());
        paramsDataVO.setStoreVO(storeVO);

        // 分销开关
        DistributionSettingGetResponse distributionSettingGetResponse =
                queryDataInterface.querySettingCache();
        paramsDataVO.setDistributionSettingGetResponse(distributionSettingGetResponse);

        // 分销开关
        DistributionStoreSettingGetByStoreIdResponse distributionStoreSettingGetByStoreIdResponse =
                queryDataInterface.queryStoreSettingCache(storeVO.getStoreId() + "");
        paramsDataVO.setDistributionStoreSettingGetByStoreIdResponse(
                distributionStoreSettingGetByStoreIdResponse);

        // 积分设置
        SystemPointsConfigQueryResponse systemPointsConfigQueryResponse =
                queryDataInterface.querySystemPointsConfig();
        paramsDataVO.setSystemPointsConfigQueryResponse(systemPointsConfigQueryResponse);

        // 商品营销
        queryDataInterface.getGoodsMarketing(paramsDataVO);
    }

    @Override
    public void check(ParamsDataVO paramsDataVO) {
        //周期购信息校验
        TradeBuyRequest request = paramsDataVO.getRequest();
        BuyCycleVO buyCycleVO = paramsDataVO.getBuyCycleVO();
        paramsDataVO.getGoodsInfoResponseSourceData().getGoodses().forEach(goodsVO -> {
            if (Objects.nonNull(goodsVO.getThirdPlatformType())) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050137);
            }
        });

        // 如果
        if ((DeliveryCycleType.WEEK_OPT_MANY.toValue() == buyCycleVO.getDeliveryCycle()
                || DeliveryCycleType.MONTH_OPT_MANY.toValue() == buyCycleVO.getDeliveryCycle())
                && CollectionUtils.isEmpty(request.getDeliveryDateList())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050186);
        }
        List<LocalDate> deliveryDateList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(request.getDeliveryDateList())) {
            request.getDeliveryDateList().forEach(date->{
                deliveryDateList.add(DateUtil.parseLocalDate(date));
            });
        }
        tradeBuyCycleService.check(request.getDeliveryCycleNum(), request.getDeliveryDate(), buyCycleVO, deliveryDateList);

        tradeBuyCheckInterface.validateGoodsStock(paramsDataVO);
    }

    @Override
    public void assembleTrade(ParamsDataVO paramsDataVO) {
        // <sku, goodsInfo> goodsInfo是数据库查询出来的
        Map<String, GoodsInfoVO> skuGoodsInfoMap =
                paramsDataVO.getGoodsInfoResponse().getGoodsInfos().stream()
                        .collect(
                                Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));

        // <spu, goods> goods是数据库查询出来的
        Map<String, GoodsVO> spuGoodsMap =
                paramsDataVO.getGoodsInfoResponse().getGoodses().stream()
                        .collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));
        // 前端的请求参数
        List<TradeItemRequest> tradeItemRequests = paramsDataVO.getRequest().getTradeItemRequests();
        // 开始构建订单快照的TradeItemDTO
        List<TradeItemDTO> tradeItems = new ArrayList<>();
        Integer deliveryCycleNum = paramsDataVO.getRequest().getDeliveryCycleNum();
        tradeItemRequests.forEach(
                item -> {
                    TradeItemDTO tradeItemDTO = new TradeItemDTO();
                    BuyCycleVO buyCycleVO = paramsDataVO.getBuyCycleVO();
                    // 前端传过来的购买数量
                    tradeItemDTO.setNum(item.getNum());
                    GoodsInfoVO goodsInfoVO = skuGoodsInfoMap.get(item.getSkuId());
                    // 构建tradeItem基础数据
                    tradeBuyAssembleInterface.tradeItemBaseBuilder(
                            tradeItemDTO,
                            goodsInfoVO,
                            spuGoodsMap.get(goodsInfoVO.getGoodsId()),
                            paramsDataVO.getStoreVO().getStoreId());
                    tradeItemDTO.setBuyCycleNum(paramsDataVO.getRequest().getDeliveryCycleNum());
                    // 构建tradeItem价格信息
                    tradeItemDTO.setLevelPrice(buyCycleVO.getCyclePrice());
                    tradeItemDTO.setOriginalPrice(buyCycleVO.getCyclePrice());
                    tradeItemDTO.setPrice(buyCycleVO.getCyclePrice());
                    tradeItemDTO.setSplitPrice(
                            buyCycleVO
                                    .getCyclePrice()
                                    .multiply(new BigDecimal(tradeItemDTO.getNum()))
                                    .multiply(new BigDecimal(deliveryCycleNum))
                                    .setScale(2, RoundingMode.HALF_UP));
                    tradeItems.add(tradeItemDTO);
                });
        OrderTag orderTag = new OrderTag();
        orderTag.setVirtualFlag(false);
        orderTag.setElectronicCouponFlag(false);
        orderTag.setBuyCycleFlag(true);
        paramsDataVO.setTradeItemDTOS(tradeItems);
        paramsDataVO.setOrderTag(orderTag);
    }

    @Override
    public void saveTrade(ParamsDataVO paramsDataVO) {
        TradeBuyRequest request = paramsDataVO.getRequest();
        List<TradeItemDTO> tradeItems = paramsDataVO.getTradeItemDTOS();
        TradeItemGroup tradeItemGroup = new TradeItemGroup();
        tradeItemGroup.setTradeItems(KsBeanUtil.convert(tradeItems, TradeItem.class));
        StoreVO store = paramsDataVO.getStoreVO();
        Supplier supplier =
                Supplier.builder()
                        .storeId(store.getStoreId())
                        .storeName(store.getStoreName())
                        .isSelf(store.getCompanyType() == BoolFlag.NO)
                        .supplierCode(store.getCompanyInfo().getCompanyCode())
                        .supplierId(store.getCompanyInfo().getCompanyInfoId())
                        .supplierName(store.getCompanyInfo().getSupplierName())
                        .freightTemplateType(store.getFreightTemplateType())
                        .storeType(store.getStoreType())
                        .build();
        tradeItemGroup.setSupplier(supplier);
        tradeItemGroup.setTradeMarketingList(
                paramsDataVO.getTradeMarketingList() == null
                        ? new ArrayList<>()
                        : paramsDataVO.getTradeMarketingList());
        tradeItemGroup.setStoreBagsFlag(DefaultFlag.NO);
        tradeItemGroup.setSuitMarketingFlag(Boolean.FALSE);
        tradeItemGroup.setOrderTag(paramsDataVO.getOrderTag());

        //配送计划
        TradeBuyCycleDTO tradeBuyCycleDTO = KsBeanUtil.convert(paramsDataVO.getBuyCycleVO(), TradeBuyCycleDTO.class);
        tradeBuyCycleDTO.setDeliveryCycleNum(request.getDeliveryCycleNum());
        Integer deliveryCycle = tradeBuyCycleDTO.getDeliveryCycle();
        LocalDate deliveryDate = request.getDeliveryDate();
        List<LocalDate> deliveryDateList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(request.getDeliveryDateList())) {
            request.getDeliveryDateList().forEach(date->{
                deliveryDateList.add(DateUtil.parseLocalDate(date));
            });
        }
        List<CycleDeliveryPlanDTO> plans = tradeBuyCycleService.createPlans(
                request.getDeliveryDate(),
                request.getDeliveryCycleNum(),
                paramsDataVO.getBuyCycleVO(),
                deliveryDateList);
        tradeBuyCycleDTO.setDeliveryPlanS(plans);
        tradeBuyCycleDTO.setBuyCycleNextPlanDate(plans.get(Constants.ZERO).getDeliveryDate());
        String deliveryCycleDay;
        //每周一期
        if (Objects.equals(deliveryCycle, DeliveryCycleType.WEEK_ONE.toValue())) {
            deliveryCycleDay = String.valueOf(deliveryDate.getDayOfWeek().getValue());
        } else if (Objects.equals(deliveryCycle, DeliveryCycleType.MONTH_ONE.toValue())) {
            deliveryCycleDay = String.valueOf(deliveryDate.getDayOfMonth());
        } else {
            deliveryCycleDay = tradeBuyCycleDTO.getDeliveryDate();
            if (!Objects.equals(deliveryCycle, DeliveryCycleType.DAY.toValue())) {
                List<String> deliveryCycleDayList = plans.stream().map(cycleDeliveryPlanDTO -> {
                    Integer integer = null;
                    LocalDate localDate = cycleDeliveryPlanDTO.getDeliveryDate();
                    if (Objects.equals(deliveryCycle, DeliveryCycleType.WEEK_MANY.toValue())
                            || Objects.equals(deliveryCycle, DeliveryCycleType.WEEK_OPT_MANY.toValue())) {
                        integer =  localDate.getDayOfWeek().getValue();
                    }
                    if (Objects.equals(deliveryCycle, DeliveryCycleType.MONTH_MANY.toValue())
                            || Objects.equals(deliveryCycle, DeliveryCycleType.MONTH_OPT_MANY.toValue())) {
                        integer =  localDate.getDayOfMonth();
                    }
                    return integer;
                }).filter(Objects::nonNull).sorted().map(String::valueOf).distinct().collect(Collectors.toList());
                deliveryCycleDay = StringUtils.join(deliveryCycleDayList,",");
            }
        }
        tradeBuyCycleDTO.setDeliveryCycleDay(deliveryCycleDay);
        tradeItemGroup.setTradeBuyCycleDTO(tradeBuyCycleDTO);

        TradeItemSnapshot snapshot =
                TradeItemSnapshot.builder()
                        .id(UUIDUtil.getUUID())
                        .buyerId(paramsDataVO.getCustomerVO().getCustomerId())
                        .itemGroups(Collections.singletonList(tradeItemGroup))
                        .terminalToken(paramsDataVO.getTerminalToken())
                        .purchaseBuy(Boolean.FALSE)
                        .build();
        tradeItemSnapshotService.addTradeItemSnapshot(snapshot);
    }
}
