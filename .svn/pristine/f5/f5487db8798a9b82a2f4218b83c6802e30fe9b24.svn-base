package com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.impl;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerRelaVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdRequest;
import com.wanmi.sbc.goods.api.request.info.TradeConfirmGoodsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsType;
import com.wanmi.sbc.goods.bean.enums.MarketingType;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSettingGetResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionStoreSettingGetByStoreIdResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.marketing.bean.dto.CountPriceMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.optimization.trade1.snapshot.ParamsDataVO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.*;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.TradeState;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.trade.request.TradeQueryRequest;
import com.wanmi.sbc.order.trade.service.TradeItemSnapshotService;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author edz
 * @className TradeBuyService
 * @description TODO
 * @date 2022/2/23 16:31
 */
@Service
public class TradeBuyService implements TradeBuyInterface {

    public static final Set<MarketingPluginType> MULTI_TYPE_MAP = new HashSet<>();

    static {
        MULTI_TYPE_MAP.add(MarketingPluginType.REDUCTION);
        MULTI_TYPE_MAP.add(MarketingPluginType.DISCOUNT);
        MULTI_TYPE_MAP.add(MarketingPluginType.GIFT);
        MULTI_TYPE_MAP.add(MarketingPluginType.HALF_PRICE_SECOND_PIECE);
        MULTI_TYPE_MAP.add(MarketingPluginType.BUYOUT_PRICE);
    }

    @Autowired
    @Qualifier("queryDataService")
    private QueryDataInterface queryDataInterface;

    @Autowired
    @Qualifier("tradeBuyCheckService")
    private TradeBuyCheckInterface tradeBuyCheckInterface;

    @Autowired
    @Qualifier("tradeBuyAssembleService")
    private TradeBuyAssembleInterface tradeBuyAssembleInterface;

    @Autowired private TradeBuyMarketingInterface tradeMarketingInterface;

    @Autowired private TradeItemSnapshotService tradeItemSnapshotService;
    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;
    @Autowired
    protected MongoTemplate mongoTemplate;
    @Override
    public void queryData(ParamsDataVO paramsDataVO) {
        TradeBuyRequest request = paramsDataVO.getRequest();
        // 查询会员信息
        CustomerVO customerVO = queryDataInterface.getCustomerInfo(request.getCustomerId());
        paramsDataVO.setCustomerVO(customerVO);

        // 查询商品信息
        List<String> skuIds =
                request.getTradeItemRequests().stream()
                        .map(TradeItemRequest::getSkuId)
                        .collect(Collectors.toList());
        TradeConfirmGoodsRequest goodsRequest =
                TradeConfirmGoodsRequest.builder()
                        .skuIds(skuIds)
                        .isHavSpecText(Boolean.TRUE)
                        .isHavIntervalPrice(Boolean.TRUE)
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

        // 是否都是实体商品
        boolean isRealGoods = goodsInfoResponse.getGoodsInfos().stream().allMatch(g -> g.getGoodsType() == 0);
        // 第三方商家商品库存需要省市区信息
        if (isRealGoods && StringUtils.isBlank(paramsDataVO.getRequest().getAddressId())) {
            CustomerDeliveryAddressResponse customerDeliveryAddressResponse =
                    queryDataInterface.getDefaultAddress(paramsDataVO);
            String provinceId = customerDeliveryAddressResponse.getProvinceId().toString();
            String cityId = customerDeliveryAddressResponse.getCityId().toString();
            String areaId = customerDeliveryAddressResponse.getAreaId().toString();
            String streetId = "";
            if (customerDeliveryAddressResponse.getStreetId() != null) {
                streetId = customerDeliveryAddressResponse.getStreetId().toString();
            }
            paramsDataVO
                    .getRequest()
                    .setAddressId(provinceId + "|" + cityId + "|" + areaId + "|" + streetId);
        }

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

        GoodsTradePluginResponse goodsTradePluginResponse =
                paramsDataVO.getGoodsTradePluginResponse();

        //周期购商品
        paramsDataVO.getGoodsInfoResponseSourceData().getGoodsInfos().forEach(goodsInfoVO -> {
            if (Constants.yes.equals(goodsInfoVO.getIsBuyCycle())) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050133);
            }
        });
        // 订单中是否包含预售商品
        boolean bookingSaleFlag =
                goodsTradePluginResponse.getSkuMarketingLabelMap().values().stream()
                        .flatMap(Collection::stream)
                        .anyMatch(
                                marketingPluginLabelDetailVO ->
                                        marketingPluginLabelDetailVO.getMarketingType()
                                                == MarketingPluginType.BOOKING_SALE.getId());

        // 订单中是否包含预约商品
        boolean appointmentSaleFlag =
                goodsTradePluginResponse.getSkuMarketingLabelMap().values().stream()
                        .flatMap(Collection::stream)
                        .anyMatch(
                                marketingPluginLabelDetailVO ->
                                        marketingPluginLabelDetailVO.getMarketingType()
                                                == MarketingPluginType.APPOINTMENT_SALE.getId());

        boolean appointmentSaleLevelFlag = false;
        if (appointmentSaleFlag) {
            if (request.getTradeItemRequests().stream().noneMatch(TradeItemRequest::getIsAppointmentSaleGoods)){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050133);
            }
            // 订单中包含预约活动商品，查询预约活动信息
            List<AppointmentSaleVO> appointmentSaleVOList =
                    queryDataInterface.getAppointmentSaleRelaInfo(request.getTradeItemRequests());
            paramsDataVO.setAppointmentSaleVOS(appointmentSaleVOList); // 预约活动等级是否是指定等级
            appointmentSaleLevelFlag =
                    paramsDataVO.getAppointmentSaleVOS().stream()
                            .anyMatch(
                                    appointmentSaleVO ->
                                            !"-1".equals(appointmentSaleVO.getJoinLevel()));
        }

        boolean bookingSaleLevelFlag = false;
        if (bookingSaleFlag) {
            if (request.getTradeItemRequests().stream().noneMatch(TradeItemRequest::getIsBookingSaleGoods)){
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050133);
            }
            // 订单中包含预售活动商品，查询预售活动信息
            List<BookingSaleVO> bookingSaleVOS = queryDataInterface.getBookingSaleVOList(skuIds);
            paramsDataVO.setBookingSaleVOS(bookingSaleVOS);
            // 预售活动等级是否是指定等级
            bookingSaleLevelFlag =
                    paramsDataVO.getBookingSaleVOS().stream()
                            .anyMatch(bookingSaleVO -> !"-1".equals(bookingSaleVO.getJoinLevel()));
        }

        if ((appointmentSaleFlag || bookingSaleFlag)
                && (appointmentSaleLevelFlag || bookingSaleLevelFlag)
                && BoolFlag.YES.equals(storeVO.getCompanyType())) {
            // 预约、预售活动与会员等级挂钩，查询会员等级
            List<StoreCustomerRelaVO> storeCustomerRelaVOS =
                    queryDataInterface.listByCondition(
                            request.getCustomerId(), storeVO.getStoreId());
            paramsDataVO.setStoreCustomerRelaVOS(storeCustomerRelaVOS);
        }
    }

    @Override
    public void check(ParamsDataVO paramsDataVO) {
        // 商品校验
        tradeBuyCheckInterface.validateGoodsStock(paramsDataVO);

        // 商品渠道检验
        tradeBuyCheckInterface.verifyChannelGoods(paramsDataVO);
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
        tradeItemRequests.forEach(
                item -> {
                    TradeItemDTO tradeItemDTO = new TradeItemDTO();
                    // 前端传过来的购买数量
                    tradeItemDTO.setNum(item.getNum());
                    GoodsInfoVO goodsInfoVO = skuGoodsInfoMap.get(item.getSkuId());
                    // 构建tradeItem基础数据
                    tradeBuyAssembleInterface.tradeItemBaseBuilder(
                            tradeItemDTO,
                            goodsInfoVO,
                            spuGoodsMap.get(goodsInfoVO.getGoodsId()),
                            paramsDataVO.getStoreVO().getStoreId());
                    // 构建tradeItem价格信息
                    tradeBuyAssembleInterface.tradeItemPriceBuilder(
                            item,
                            tradeItemDTO,
                            goodsInfoVO,
                            spuGoodsMap.get(goodsInfoVO.getGoodsId()),
                            paramsDataVO.getGoodsInfoResponse().getGoodsIntervalPrices());
                    tradeItems.add(tradeItemDTO);
                });
        OrderTag orderTag = new OrderTag();
        boolean virtualFlag =
                paramsDataVO.getGoodsInfoResponse().getGoodsInfos().stream()
                        .anyMatch(
                                goodsInfoVO ->
                                        NumberUtils.INTEGER_ONE.equals(goodsInfoVO.getGoodsType()));
        orderTag.setVirtualFlag(virtualFlag);
        boolean electronicCouponFlag =
                paramsDataVO.getGoodsInfoResponse().getGoodsInfos().stream()
                        .anyMatch(
                                goodsInfoVO ->
                                        Constants.TWO == (goodsInfoVO.getGoodsType()));
        orderTag.setElectronicCouponFlag(electronicCouponFlag);
        paramsDataVO.setTradeItemDTOS(tradeItems);
        paramsDataVO.setOrderTag(orderTag);
    }

    @Override
    public void getMarketing(ParamsDataVO paramsDataVO) {
        //下单指定营销活动 <skuId,marketingId>
        Map<String, Long> marketingIdMap = paramsDataVO.getRequest().getTradeItemRequests().stream()
                .filter(tradeItemRequest -> tradeItemRequest.getMarketingId() != null)
                .collect(Collectors.toMap(TradeItemRequest::getSkuId, TradeItemRequest::getMarketingId));



        List<TradeItemDTO> tradeItems = paramsDataVO.getTradeItemDTOS();
        // 获取商品营销
        GoodsTradePluginResponse goodsTradePluginResponse =
                paramsDataVO.getGoodsTradePluginResponse();


        //获取虚拟和周期购商品
        List<GoodsInfoVO> virtualGoods = paramsDataVO.getGoodsInfoResponse()
                .getGoodsInfos()
                .stream()
                .filter(goodsInfoVO ->
                        goodsInfoVO.getGoodsType() == GoodsType.VIRTUAL_GOODS.toValue() ||
                                goodsInfoVO.getIsBuyCycle().equals(NumberUtils.INTEGER_ONE)
                                || Objects.equals(goodsInfoVO.getGoodsType(), GoodsType.ELECTRONIC_COUPON_GOODS.toValue())
                )
                .collect(Collectors.toList());


        if (CollectionUtils.isNotEmpty(virtualGoods)){
            //如果是虚拟商品，且没有选择营销，则把满减和满折过滤掉
            if (marketingIdMap.isEmpty()){
                for (GoodsInfoVO goodsInfoVO : virtualGoods){
                    Map<String, List<MarketingPluginLabelDetailVO>> labelMap = goodsTradePluginResponse.getSkuMarketingLabelMap();
                    List<MarketingPluginLabelDetailVO> marketingPluginLabelDetailVOS = labelMap.get(goodsInfoVO.getGoodsInfoId());
                    if (CollectionUtils.isNotEmpty(marketingPluginLabelDetailVOS)){
                        marketingPluginLabelDetailVOS = marketingPluginLabelDetailVOS.stream().filter(marketingPluginLabelDetailVO -> {
                            if (marketingPluginLabelDetailVO.getMarketingType() != MarketingType.DISCOUNT.toValue()
                                    && marketingPluginLabelDetailVO.getMarketingType() != MarketingType.REDUCTION.toValue()){
                                return true;
                            }
                            return false;
                        }).collect(Collectors.toList());
                        goodsTradePluginResponse.getSkuMarketingLabelMap().put(goodsInfoVO.getGoodsInfoId(),marketingPluginLabelDetailVOS);
                    }
                }
            }
        }


        // 订单中是否包含营销活动
        if (goodsTradePluginResponse.getSkuMarketingLabelMap().size() != 0) {
            Map<String, TradeItemDTO> skuTradeItemDTOMap =
                    tradeItems.stream()
                            .collect(Collectors.toMap(TradeItemDTO::getSkuId, Function.identity()));

            Map<String, CountPriceMarketingDTO> marketingIdCountPriceMarketingDTOMap = new HashMap<>();
            // 遍历营销插件返回的数据
            goodsTradePluginResponse
                    .getSkuMarketingLabelMap()
                    // k: skuId v:封装的对应营销信息
                    .forEach(
                            (k, v) -> {
                                /*
                                  for循环主要做了这些：
                                   1 MULTI_TYPE_MAP 是满系活动，一个商品可能存在多个营销活动，这里只取插件返回的第一个即可（插件对营销优先级做了排序）
                                   2 预售活动单独处理是因为预售需要填充一些特有属性
                                   3 其他活动插件会计算好价格(pluginPrice),直接填充
                                */
                                boolean flag = true;
                                //指定营销活动
                                Long assignMarketingId = marketingIdMap.get(k);
                                Boolean present = Boolean.FALSE;
                                for (MarketingPluginLabelDetailVO marketingPluginLabelDetailVO :
                                        v) {
                                    MarketingPluginType marketingPluginType =
                                            MarketingPluginType.fromId(
                                                    marketingPluginLabelDetailVO
                                                            .getMarketingType());
//                                    if (MarketingPluginType.DISTRIBUTION.equals(marketingPluginType)) break;
                                    // MULTI_TYPE_MAP中的营销需要再次计算
                                    if (MULTI_TYPE_MAP.contains(marketingPluginType)
                                            && flag) { // 接口返回的营销信息是按照营销优先级排过序的。立即下单无法手动选择营销，所以默认选择第一个
                                        if (Long.valueOf(marketingPluginLabelDetailVO.getMarketingId().toString()).equals(assignMarketingId))  {
                                            present = Boolean.TRUE;
                                        }
                                        flag =
                                                tradeMarketingInterface.multiTypeMapHandle(
                                                        k,
                                                        marketingPluginLabelDetailVO,
                                                        skuTradeItemDTOMap,
                                                        marketingPluginType,
                                                        marketingIdCountPriceMarketingDTOMap,
                                                        assignMarketingId);
                                    } else if (MarketingPluginType.BOOKING_SALE.equals(
                                            marketingPluginType)) {
                                        // 预售处理
                                        tradeMarketingInterface.bookingSaleHandle(
                                                paramsDataVO,
                                                k,
                                                skuTradeItemDTOMap,
                                                marketingPluginLabelDetailVO);
                                    } else if (marketingPluginLabelDetailVO.getPluginPrice()
                                            != null) {
                                        tradeMarketingInterface.otherMarketingHandle(
                                                k,
                                                skuTradeItemDTOMap,
                                                marketingPluginLabelDetailVO,
                                                marketingPluginType);
                                    }
                                }
                                //指定活动不存在提示
                                if (Objects.nonNull(assignMarketingId) && !present) {
                                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050132);
                                }
                            });
            tradeMarketingInterface.marketingHandle(
                    paramsDataVO, skuTradeItemDTOMap, marketingIdCountPriceMarketingDTOMap);
        }
    }

    @Override
    public void saveTrade(ParamsDataVO paramsDataVO) {
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

        TradeItemSnapshot snapshot =
                TradeItemSnapshot.builder()
                        .id(UUIDUtil.getUUID())
                        .buyerId(paramsDataVO.getCustomerVO().getCustomerId())
                        .itemGroups(Collections.singletonList(tradeItemGroup))
                        .terminalToken(paramsDataVO.getTerminalToken())
                        .purchaseBuy(Boolean.FALSE)
                        .build();
        int num = 0;
        for (TradeItemGroup tradeConfirmItem :  snapshot.getItemGroups()) {
            List<TradeItem> tradeItems2 = tradeConfirmItem.getTradeItems();
            for (TradeItem tradeItem : tradeItems2) {
                if (tradeItem.getSkuName().contains("校服")){
                    if (tradeItem.getSkuNo().length()>10){
                        num = Math.toIntExact(num + (tradeItem.getNum() * 2));
                    }else {
                        num = Math.toIntExact(num + (tradeItem.getNum()));
                    }
                }
            }
        }
        if(num>0){
            //获取当前年订单内校服件数
            int schoolGoodsNumByOrder = getSchoolGoodsNumByOrder2(paramsDataVO.getRequest().getCustomerId());
            //限售
            replacePriceToLine2(snapshot, schoolGoodsNumByOrder,num);
        }
        tradeItemSnapshotService.addTradeItemSnapshot(snapshot);
    }
    void replacePriceToLine2(TradeItemSnapshot tradeConfirmResponse,int schoolGoodsNumByOrder,int num){
        List<TradeItemGroup> tradeConfirmItems = tradeConfirmResponse.getItemGroups();
        boolean flag = (num + schoolGoodsNumByOrder) > 24;
//        tradeConfirmResponse.setSchoolNum(num + schoolGoodsNumByOrder);
        if (!flag){
            return;
        }
        for (TradeItemGroup tradeConfirmItem : tradeConfirmItems) {
            List<TradeItem> tradeItems = tradeConfirmItem.getTradeItems();
            BigDecimal splitPrices = new BigDecimal(0);
            BigDecimal prices = new BigDecimal(0);
            BigDecimal originalPrices = new BigDecimal(0);
            for (TradeItem tradeItem : tradeItems) {
                if (tradeItem.getSkuName().contains("校服")){
                    if (flag){
                        GoodsInfoViewByIdRequest goodsInfoViewByIdRequest = new GoodsInfoViewByIdRequest();
                        goodsInfoViewByIdRequest.setGoodsInfoId(tradeItem.getSkuId());
                        BaseResponse<GoodsInfoViewByIdResponse> viewById = goodsInfoQueryProvider.getViewById(goodsInfoViewByIdRequest);
                        GoodsInfoViewByIdResponse context = viewById.getContext();
                        tradeItem.setSplitPrice(context.getGoodsInfo().getLinePrice().multiply(BigDecimal.valueOf(tradeItem.getNum())).setScale(2, RoundingMode.HALF_UP));
                        tradeItem.setPrice(context.getGoodsInfo().getLinePrice());
                        tradeItem.setLevelPrice(context.getGoodsInfo().getLinePrice());
                        tradeItem.setOriginalPrice(context.getGoodsInfo().getLinePrice());
                    }
                }
                splitPrices = splitPrices.add(tradeItem.getSplitPrice());
                prices = prices.add(tradeItem.getPrice());
                originalPrices = originalPrices.add(tradeItem.getOriginalPrice());
            }
//            tradeConfirmItem.getTradePrice().setGoodsPrice(splitPrices);
//            tradeConfirmItem.getTradePrice().setOriginPrice(splitPrices);
//            tradeConfirmItem.getTradePrice().setTotalPrice(splitPrices);
        }
    }
    int getSchoolGoodsNumByOrder2(String customerId){
        TradeQueryRequest request = new TradeQueryRequest();
        request.setBuyerId(customerId);
        LocalDate localDate = DateUtil.firstDayOfYear();
        String format = DateUtil.format(localDate, "yyyy-MM-dd");
//        format=format+" 00:00:00";
        List<FlowState> notFlowStates = new ArrayList<>();
        notFlowStates.add(FlowState.REFUND);
        notFlowStates.add(FlowState.VOID);
        notFlowStates.add(FlowState.CANCEL_DELIVERED);
        request.setNotFlowStates(notFlowStates);
        request.setBeginTime(format);
        TradeState tradeState = new TradeState();
        tradeState.setPayState(PayState.PAID);
        request.setTradeState(tradeState);
        Criteria whereCriteria = request.getWhereCriteria();
        Query query = new Query(whereCriteria);
        List<Trade> trades = mongoTemplate.find(query, Trade.class);
        int num = 0;
        for (Trade trade : trades) {
            List<TradeItem> tradeItems = trade.getTradeItems();
            for (TradeItem tradeItem : tradeItems) {
                if (tradeItem.getSkuName().contains("校服")){
                    if (tradeItem.getSkuNo().length()>10){
                        num = Math.toIntExact(num + (tradeItem.getNum() * 2));
                    }else {
                        num = Math.toIntExact(num + (tradeItem.getNum()));
                    }
                }
            }
        }
        return num;
    }
}
