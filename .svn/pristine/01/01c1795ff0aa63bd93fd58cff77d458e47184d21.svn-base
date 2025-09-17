package com.wanmi.sbc.order.trade.service.commit;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.goodscommission.GoodsCommissionConfigProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.goodscommission.GoodsCommissionConfigQueryRequest;
import com.wanmi.sbc.goods.api.request.info.ProviderGoodsInfoRequest;
import com.wanmi.sbc.goods.api.response.info.ProviderGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.CommissionFreightBearFlag;
import com.wanmi.sbc.goods.bean.enums.ConditionType;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.goods.bean.enums.ValuationType;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.provider.countPrice.TradeCountMarketingPriceProvider;
import com.wanmi.sbc.marketing.api.request.countprice.TradeCountCouponPriceRequest;
import com.wanmi.sbc.marketing.api.response.countprice.TradeCountCouponPriceResponse;
import com.wanmi.sbc.marketing.bean.dto.CountCouponPriceGoodsInfoDTO;
import com.wanmi.sbc.marketing.bean.vo.CountCouponPriceVO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.FreightTemplateGroupVO;
import com.wanmi.sbc.order.bean.vo.TradeFreightTemplateVO;
import com.wanmi.sbc.order.optimization.trade1.commit.bean.Trade1CommitParam;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.*;
import com.wanmi.sbc.order.trade.service.TradeCacheService;
import com.wanmi.sbc.order.util.mapper.OrderMapper;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;
import com.wanmi.sbc.vas.api.provider.channel.order.ChannelTradeProvider;
import com.wanmi.sbc.vas.api.request.channel.ChannelOrderFreightRequest;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderFreightResponse;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelGoodsInfoDTO;

import jakarta.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Bob
 * @className FreightService
 * @description 运费计算
 * @date 2021/8/18 14:26
 */
@Service
public class FreightService {
    @Autowired private TradeCacheService tradeCacheService;

    @Autowired private ChannelTradeProvider channelTradeProvider;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private StoreQueryProvider storeQueryProvider;

    @Autowired private GoodsCommissionConfigProvider goodsCommissionConfigProvider;

    @Autowired private RedisUtil redisService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    private TradeCountMarketingPriceProvider tradeCountMarketingPriceProvider;

    @Resource
    private FreightService _self;

    private final static String VOP_FREIGHT_KEY = "ORDER:VOP_FREIGHT_KEY:";


    /**
     * 设置订单运费,并追加到订单原价/应付金额中 若商家没有单独填写订单运费,则根据订单商品,赠品按照运费模板进行计算
     *
     * @param consignee 收货地址 - 省id,市id
     * @param supplier 店铺信息 - 店铺id-使用运费模板类型
     * @param deliverWay 配送方式
     * @param totalPrice 订单总价(扣除营销优惠后)
     * @param goodsList 订单商品List - 均摊价(计算营销后),件数 ,体积,重量,使用的运费模板id
     * @param giftList 订单赠品List - 价格为0,件数 ,体积,重量,使用的运费模板id
     * @return freight 订单应付运费
     */
    public Freight calcTradeFreight(
            Consignee consignee,
            Supplier supplier,
            DeliverWay deliverWay,
            BigDecimal totalPrice,
            List<TradeItem> goodsList,
            List<TradeItem> giftList,
            List<TradeItem> preferentialList) {
        if (preferentialList == null){
            preferentialList = new ArrayList<>();
        }
        BigDecimal freight = BigDecimal.ZERO; //用户承担的费用
        if(CollectionUtils.isEmpty(goodsList)){
            return Freight.builder().freight(freight).build();
        }
        //如果不是实体商品
//        String skuId = goodsList.get(0).getSkuId();
//        Long storeId = goodsList.get(0).getStoreId();
//        Integer goodsType = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder()
//                .goodsInfoId(skuId)
//                .storeId(storeId)
//                .build()).getContext().getGoodsType();
        Integer goodsType = goodsList.get(0).getGoodsType();
        if (Objects.nonNull(goodsType) && (!NumberUtils.INTEGER_ZERO.equals(goodsType))) {
            return Freight.builder().freight(freight).build();
        }
        List<TradeItem> temp = Lists.newArrayList();
        //周期购购买数量的变更,不能变更原来的num
        goodsList.forEach(tradeItem -> {
            TradeItem newItem = new TradeItem();
            KsBeanUtil.copyProperties(tradeItem,newItem);
            Integer buyCycleNum = tradeItem.getBuyCycleNum();
            if (Objects.nonNull(buyCycleNum)) {
                Long num = tradeItem.getNum();
                newItem.setNum(num * buyCycleNum);
            }
            temp.add(newItem);
        });

        Freight freightVo = new Freight();
        List<TradeItem> supplierGoodsList = temp.stream().filter(tradeItem -> Objects.isNull(tradeItem.getProviderId())).collect(Collectors.toList());
        List<TradeItem> providerGoodsList = temp.stream().filter(tradeItem -> Objects.nonNull(tradeItem.getProviderId())).collect(Collectors.toList());

        List<TradeItem> supplierGiftList = giftList.stream().filter(tradeItem -> Objects.isNull(tradeItem.getProviderId())).collect(Collectors.toList());
        List<TradeItem> providerGiftList = giftList.stream().filter(tradeItem -> Objects.nonNull(tradeItem.getProviderId())).collect(Collectors.toList());

        List<TradeItem> supplierPreferentialList =
                preferentialList.stream().filter(tradeItem -> Objects.isNull(tradeItem.getProviderId())).collect(Collectors.toList());
        List<TradeItem> providerPreferentialList =
                preferentialList.stream().filter(tradeItem -> Objects.nonNull(tradeItem.getProviderId())).collect(Collectors.toList());

        //计算商家商品的运费
        if(CollectionUtils.isNotEmpty(supplierGoodsList) || CollectionUtils.isNotEmpty(supplierGiftList)
                || CollectionUtils.isNotEmpty(supplierPreferentialList)) {
            StoreVO storeVO = new StoreVO();
            storeVO.setFreightTemplateType(supplier.getFreightTemplateType());
            storeVO.setStoreId(supplier.getStoreId());
            storeVO.setStoreType(supplier.getStoreType());
            BigDecimal goodsPrice = supplierGoodsList.stream()
                    .filter(tradeItem -> tradeItem.getProviderId() == null)
                    .map(tradeItem ->
                            tradeItem.getSplitPrice() == null ? BigDecimal.ZERO : tradeItem.getSplitPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal supplierFreight = this.getStoreFreight(consignee, storeVO, supplierGoodsList, supplierGiftList,
                    supplierPreferentialList, deliverWay, goodsPrice, null, freightVo);
            freight = freight.add(supplierFreight);
            freightVo.setSupplierFreight(supplierFreight);
        }

        //计算供应商商品的运费
        if(CollectionUtils.isNotEmpty(providerGoodsList) || CollectionUtils.isNotEmpty(providerGiftList)
                || CollectionUtils.isNotEmpty(providerPreferentialList)) {
            //获取商家分销设置
            GoodsCommissionConfigQueryRequest configQueryRequest = new GoodsCommissionConfigQueryRequest();
            configQueryRequest.setBaseStoreId(supplier.getStoreId());
            GoodsCommissionConfigVO commissionConfigVO = goodsCommissionConfigProvider.query(configQueryRequest).getContext().getCommissionConfigVO();
            //没有代销设置则不处理 代销商品运费
            if (Objects.nonNull(commissionConfigVO)) {
                List<ProviderFreight> providerFreightList = this.getProviderGoodsFreight(consignee, providerGoodsList, providerGiftList,
                        providerPreferentialList, deliverWay, commissionConfigVO.getFreightBearFlag(), freightVo);
                if (CollectionUtils.isNotEmpty(providerFreightList)) {
                    freightVo.setProviderFreightList(providerFreightList);
                    BigDecimal providerSumFreight = providerFreightList.stream().map(providerFreight ->
                            providerFreight.getSupplierFreight() == null ? BigDecimal.ZERO : providerFreight.getSupplierFreight())
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    freightVo.setProviderFreight(providerSumFreight);
                    //买家承担
                    if(CommissionFreightBearFlag.BUYER_BEAR.toValue() == commissionConfigVO.getFreightBearFlag().toValue()) {
                        freight = freight.add(providerSumFreight);
                    }
                    //卖家承担
                    if(CommissionFreightBearFlag.SELLER_BEAR.toValue() == commissionConfigVO.getFreightBearFlag().toValue()) {
                        freightVo.setSupplierBearFreight(providerSumFreight);
                    }
                }
            }
        }
        freightVo.setFreight(freight);
        return freightVo;
    }

    /**
     * @description  计算供应商运费
     * @author  wur
     * @date: 2021/9/26 10:01
     * @param consignee   收货人信息
     * @param storeVo    供应商
     * @param tradeItemList    订单中供应商商品
     * @param deliverWay       供应商商品信息
     * @param skuList
     * @return
     **/
    public BigDecimal getProvideStoreFreight(Consignee consignee, StoreVO storeVo, List<TradeItem> tradeItemList,
                                             List<TradeItem> preferentialTradeItemList, DeliverWay deliverWay,
                                             List<GoodsInfoVO> skuList, CommissionFreightBearFlag bearFlag, Freight freightVo) {
        //验证供应商是否时京东VOP
        if (Objects.nonNull(storeVo.getCompanySourceType())
                && CompanySourceType.JD_VOP.toValue() == storeVo.getCompanySourceType().toValue()) {
            TradeFreightTemplateVO templateVO = new TradeFreightTemplateVO();
            templateVO.setFreightTemplateType(DefaultFlag.NO);
            Set<String> skuIdList = tradeItemList.stream().map(TradeItem::getSkuId).collect(Collectors.toSet());
            skuIdList.addAll(preferentialTradeItemList.stream().map(TradeItem::getSkuId).collect(Collectors.toSet()));
            templateVO.setSkuIdList(new ArrayList<>(skuIdList));
            templateVO.setCollectFlag(Boolean.FALSE);
            templateVO.setFeright(BigDecimal.ZERO);
            templateVO.setFreightDescribe("免运费");
            // 封装VOP运费请求参数
            List<ChannelGoodsInfoDTO> goodsInfoList =
                    KsBeanUtil.convert(skuList, ChannelGoodsInfoDTO.class);
            for (ChannelGoodsInfoDTO channelGoodsInfoDTO : goodsInfoList) {
                Optional<TradeItem> opt = tradeItemList.stream()
                        .filter(v -> Objects.equals(v.getThirdPlatformSkuId(), channelGoodsInfoDTO.getThirdPlatformSkuId()))
                        .findFirst();
                opt.ifPresent(tradeItem -> channelGoodsInfoDTO.setNum(tradeItem.getNum()));
                Optional<TradeItem> opt1 = preferentialTradeItemList.stream()
                        .filter(v -> Objects.equals(v.getThirdPlatformSkuId(), channelGoodsInfoDTO.getThirdPlatformSkuId()))
                        .findFirst();
                opt1.ifPresent(tradeItem -> channelGoodsInfoDTO.setNum(channelGoodsInfoDTO.getNum() + tradeItem.getNum()));
            }

            //验证缓存是否有
            String key = this.getVopFreightKey(consignee, goodsInfoList);
            String vopFreight = redisService.getString(key);
            if (StringUtils.isNotBlank(vopFreight)) {
                BigDecimal freight = new BigDecimal(vopFreight);
                if (CommissionFreightBearFlag.BUYER_BEAR.equals(bearFlag)) {
                    templateVO.setFeright(freight);
                    if (BigDecimal.ZERO.compareTo(freight) >= 0) {
                        templateVO.setFreightDescribe("免运费");
                    } else {
                        templateVO.setFreightDescribe(String.format("运费%s元",freight));
                    }
                }
                freightVo.getTemplateVOList().add(templateVO);
                return freight;
            }

            BaseResponse<ChannelOrderFreightResponse> baseResponse =
                    channelTradeProvider.queryFreight(
                            ChannelOrderFreightRequest.builder()
                                    .goodsInfoList(goodsInfoList)
                                    .address(PlatformAddress.builder()
                                            .provinceId(Objects.toString(consignee.getProvinceId()))
                                            .cityId(Objects.toString(consignee.getCityId()))
                                            .areaId(Objects.toString(consignee.getAreaId()))
                                            .streetId(Objects.toString(consignee.getStreetId()))
                                            .build())
                                    .build());
            if (CommonErrorCodeEnum.K000000.getCode().equals(baseResponse.getCode())) {
                // 运费加入缓存
                redisService.setString(key, baseResponse.getContext().getFreight().toString(), 7200L);
                BigDecimal freight = baseResponse.getContext().getFreight();
                if (CommissionFreightBearFlag.BUYER_BEAR.equals(bearFlag)) {
                    templateVO.setFeright(freight);
                    if (BigDecimal.ZERO.compareTo(freight) >= 0) {
                        templateVO.setFreightDescribe("免运费");
                    } else {
                        templateVO.setFreightDescribe(String.format("运费%s元",freight));
                    }
                }
                freightVo.getTemplateVOList().add(templateVO);
                return freight;
            } else {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050049);
            }
        } else {
            BigDecimal goodsPrice = tradeItemList.stream().map(tradeItem ->
                    tradeItem.getSplitPrice() == null ? BigDecimal.ZERO : tradeItem.getSplitPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            goodsPrice = goodsPrice.add(preferentialTradeItemList.stream().map(tradeItem ->
                            tradeItem.getSplitPrice() == null ? BigDecimal.ZERO : tradeItem.getSplitPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
            return this.getStoreFreight(consignee, storeVo, tradeItemList, new ArrayList<>(),
                    preferentialTradeItemList, deliverWay, goodsPrice
                    , bearFlag, freightVo);
        }
    }

    /**
     * @description      计算订单中供应商商品的运费
     * @author  wur
     * @date: 2021/9/24 15:02
     * @param consignee
     * @param oldGoodsList
     * @param giftList
     * @param deliverWay
     * @return
     **/
    public List<ProviderFreight> getProviderGoodsFreight(Consignee consignee, List<TradeItem> oldGoodsList, List<TradeItem> giftList,
                                                         List<TradeItem> preferentialList,
                                                         DeliverWay deliverWay, CommissionFreightBearFlag bearFlag, Freight freightVo) {
        List<ProviderFreight> providerFreightList = new ArrayList<>();
        List<TradeItem> goodsList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(oldGoodsList)) {
            goodsList = KsBeanUtil.convertList(oldGoodsList, TradeItem.class);
        }
        if (CollectionUtils.isNotEmpty(giftList)) {
            if (CollectionUtils.isNotEmpty(goodsList)) {
                for (TradeItem gift : giftList) {
                    boolean addFlag = true;
                    for (TradeItem goods : goodsList) {
                        if (Objects.equals(gift.getSkuId(), goods.getSkuId())) {
                            addFlag = false;
                            goods.setNum(goods.getNum() + gift.getNum());
                        }
                    }
                    if (addFlag) {
                        TradeItem newGoods = new TradeItem();
                        KsBeanUtil.copyPropertiesThird(gift, newGoods);
                        goodsList.add(newGoods);
                    }
                }
            } else {
                goodsList.addAll(giftList);
            }
        }

        List<String> skuIdList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(goodsList)){
            skuIdList.addAll(goodsList.stream().map(TradeItem :: getSkuId).collect(Collectors.toList()));
        }

        if(CollectionUtils.isNotEmpty(preferentialList)){
            skuIdList.addAll(preferentialList.stream().map(TradeItem :: getSkuId).collect(Collectors.toList()));
        }

        if(CollectionUtils.isEmpty(skuIdList)){
            return providerFreightList;
        }

        // 根据商家商品差到对应的供应商商品
        BaseResponse<ProviderGoodsInfoResponse> baseResponse = goodsInfoQueryProvider.getProviderSku(ProviderGoodsInfoRequest.builder().goodsInfoIdList(skuIdList).build());
        if(CollectionUtils.isEmpty(baseResponse.getContext().getGoodsInfos())
                || CollectionUtils.isEmpty(baseResponse.getContext().getProviderGoodsInfos())) {
            return providerFreightList;
        }
        Map<Long, List<GoodsInfoVO>> goodsMap = baseResponse.getContext().getGoodsInfos()
                .stream().collect(Collectors.groupingBy(GoodsInfoVO :: getProviderId));
        Map<Long, List<GoodsInfoVO>> providerGoodsMap = baseResponse.getContext().getProviderGoodsInfos()
                .stream().collect(Collectors.groupingBy(GoodsInfoVO :: getStoreId));

        //查询供应商商家信息
        List<Long> storeIdList = baseResponse.getContext().getProviderGoodsInfos().stream().map(GoodsInfoVO :: getStoreId).collect(Collectors.toList());
        List<StoreVO> storeVOList = storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIdList).build()).getContext().getStoreVOList();

        //根据每个分组查询供应商运费
        for(StoreVO storeVO : storeVOList) {
            List<GoodsInfoVO> providerGoodsInfoVOList = providerGoodsMap.get(storeVO.getStoreId());
            //根据代理商商品信息查询 所对应的 tradeItem
            List<String> goodsInfoVOList = goodsMap.get(storeVO.getStoreId()).stream().map(GoodsInfoVO :: getGoodsInfoId).collect(Collectors.toList());
            List<TradeItem>  tradeItemList = goodsList.stream().filter(tradeItem -> goodsInfoVOList.contains(tradeItem.getSkuId())).collect(Collectors.toList());
            List<TradeItem>  preferentialTradeItemList =
                    preferentialList.stream().filter(tradeItem -> goodsInfoVOList.contains(tradeItem.getSkuId())).collect(Collectors.toList());
            Map<String, GoodsInfoVO> goodsInfoVOMap = providerGoodsInfoVOList.stream().collect(Collectors.toMap(GoodsInfoVO :: getGoodsInfoId, Function.identity()));
            //封装TradeItem的 freightTempId 方便单品运费模板使用
            goodsMap.get(storeVO.getStoreId()).forEach(goodsInfoVO -> {
                for (TradeItem tradeItem : tradeItemList) {
                    if (goodsInfoVO.getGoodsInfoId().equals(tradeItem.getSkuId())) {
                        GoodsInfoVO goodsInfo = goodsInfoVOMap.get(goodsInfoVO.getProviderGoodsInfoId());
                        if (Objects.nonNull(goodsInfo)) {
                            tradeItem.setFreightTempId(goodsInfo.getFreightTempId());
                            tradeItem.setGoodsWeight(goodsInfo.getGoodsWeight());
                            tradeItem.setGoodsCubage(goodsInfo.getGoodsCubage());
                        }
                    }
                }
                for (TradeItem tradeItem : preferentialTradeItemList) {
                    if (goodsInfoVO.getGoodsInfoId().equals(tradeItem.getSkuId())) {
                        GoodsInfoVO goodsInfo = goodsInfoVOMap.get(goodsInfoVO.getProviderGoodsInfoId());
                        if (Objects.nonNull(goodsInfo)) {
                            tradeItem.setFreightTempId(goodsInfo.getFreightTempId());
                            tradeItem.setGoodsWeight(goodsInfo.getGoodsWeight());
                            tradeItem.setGoodsCubage(goodsInfo.getGoodsCubage());
                        }
                    }
                }
            });
            BigDecimal storeFreight = this.getProvideStoreFreight(consignee, storeVO, tradeItemList,
                    preferentialTradeItemList, deliverWay,
                    providerGoodsInfoVOList, bearFlag, freightVo);
            providerFreightList.add(ProviderFreight.builder().providerId(storeVO.getStoreId()).supplierFreight(storeFreight).bearFreight(bearFlag.toValue()).build());
        }

        return providerFreightList;
    }

    /**
     * @description      获取商家商品的运费
     * @author  wur
     * @date: 2021/9/24 14:48
     * @param consignee    收货地址
     * @param storeVo      商家信息
     * @param tradeItemList   商品信息
     * @param giftItemList    赠品信息
     * @param preferentialItemList    加价购商品信息
     * @param deliverWay
     * @return
     **/
    public BigDecimal getStoreFreight(Consignee consignee, StoreVO storeVo, List<TradeItem> tradeItemList,
                                      List<TradeItem> giftItemList, List<TradeItem> preferentialItemList,
                                      DeliverWay deliverWay,
                                      BigDecimal goodsPrice, CommissionFreightBearFlag bearFlag, Freight freightVo) {

        BigDecimal freight = BigDecimal.ZERO;
        //商家商品数量
        long supplierGoodsCount = tradeItemList.stream().count();
        long supplierGiftsCount = giftItemList.stream().count();
        long supplierPreferentialCount = preferentialItemList.stream().count();

        if (DefaultFlag.NO.equals(storeVo.getFreightTemplateType()) && supplierGoodsCount + supplierGiftsCount + supplierPreferentialCount > 0) {
            //1. 店铺运费模板计算
            FreightTemplateStoreVO templateStore;
            List<FreightTemplateStoreVO> storeTemplateList =
                    tradeCacheService.listStoreTemplateByStoreIdAndDeleteFlag(storeVo.getStoreId(), DeleteFlag.NO);

            //1.1. 配送地匹配运费模板(若匹配不上则使用默认运费模板)
            Optional<FreightTemplateStoreVO> tempOptional = storeTemplateList.stream().filter(temp -> matchArea(
                    temp.getDestinationArea(), consignee.getProvinceId(), consignee.getCityId())).findFirst();
            templateStore = tempOptional.orElseGet(() -> storeTemplateList.stream().filter(temp ->
                    DefaultFlag.YES.equals(temp.getDefaultFlag())).findFirst().orElse(null));

            if (Objects.nonNull(templateStore)) {
                if (DefaultFlag.NO.equals(templateStore.getFreightType())) {
                    //1.2. 满金额包邮情况
                    if (goodsPrice.compareTo(templateStore.getSatisfyPrice()) < 0) {
                        freight = templateStore.getSatisfyFreight();
                    }
                } else {
                    //1.3. 固定运费情况
                    freight = templateStore.getFixedFreight();
                }
                freightVo.getTemplateVOList().add(getFreightTemplateStore(templateStore, tradeItemList, giftItemList,
                        preferentialItemList, bearFlag, freight, storeVo, goodsPrice));
            }
        } else if (DefaultFlag.YES.equals(storeVo.getFreightTemplateType())) {
            // 2.单品运费模板计算
            // 2.1.根据templateId分组聚合总件数,重量,体积,价格, 并查询各运费模板信息
            Map<Long, TradeItem> templateGoodsMap = new LinkedHashMap<>();
            _self.setGoodsSumMap(templateGoodsMap, tradeItemList, storeVo);
            _self.setGoodsSumMap(templateGoodsMap, giftItemList, storeVo);
            _self.setGoodsSumMap(templateGoodsMap, preferentialItemList, storeVo);
            Map<Long, List<TradeItem>> templateTradeItemMap = new LinkedHashMap<>();
            _self.setTemplateTradeItemMap(templateTradeItemMap, tradeItemList);
            _self.setTemplateTradeItemMap(templateTradeItemMap, giftItemList);
            _self.setTemplateTradeItemMap(templateTradeItemMap, preferentialItemList);
            List<Long> tempIdList = new ArrayList<>(templateGoodsMap.keySet());
            List<FreightTemplateGoodsVO> templateList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(tempIdList)) {
                templateList = tradeCacheService.queryFreightTemplateGoodsListByIds(tempIdList);

                // 2.2.剔除满足指定条件包邮的运费模板(即剔除运费为0的)
                List<FreightTemplateGoodsVO> filterTemplateList = templateList.stream().filter(temp ->
                        getFreeFreightFlag(temp, templateGoodsMap, deliverWay, consignee.getProvinceId(), consignee
                                .getCityId()))
                        .collect(Collectors.toList());

                // 2.3.遍历单品运费模板List,设置匹配上收货地的配送地信息,同时计算出最大首运费的模板
                FreightTemplateGoodsExpressVO maxTemplate = new FreightTemplateGoodsExpressVO();
                for (int i = 0; i < filterTemplateList.size(); i++) {
                    FreightTemplateGoodsVO temp = filterTemplateList.get(i);
                    FreightTemplateGoodsExpressVO freExp =
                            getMatchFreightTemplate(temp.getFreightTemplateGoodsExpresses(),
                                    consignee.getProvinceId(), consignee.getCityId());
                    temp.setExpTemplate(freExp);
                    if (i == 0) {
                        maxTemplate = freExp;
                    } else {
                        maxTemplate = maxTemplate.getFreightStartPrice().compareTo(freExp.getFreightStartPrice()) < 0 ?
                                freExp : maxTemplate;
                    }
                }

                // 2.4.计算剩余的每个模板的运费
                FreightTemplateGroupVO groupVO = new FreightTemplateGroupVO();
                final Long tempId = maxTemplate.getFreightTempId();
                groupVO.setFirstFreightTemplateId(tempId);
                Map<Long, FreightTemplateGoodsVO> filterTemplateMap = filterTemplateList.stream().collect(Collectors.toMap(FreightTemplateGoodsVO::getFreightTempId, g -> g));
                for(FreightTemplateGoodsVO templateGoodsVO : templateList) {
                    Long freightTempId = templateGoodsVO.getFreightTempId();
                    BigDecimal oneFreight = BigDecimal.ZERO;
                    FreightTemplateGoodsExpressVO expTemplate = null;
                    if (filterTemplateMap.containsKey(freightTempId)) {
                        expTemplate = filterTemplateMap.get(templateGoodsVO.getFreightTempId()).getExpTemplate();
                        oneFreight = getSingleTemplateFreight(filterTemplateMap.get(templateGoodsVO.getFreightTempId()), tempId, templateGoodsMap);
                        oneFreight = Objects.isNull(oneFreight) ? BigDecimal.ZERO : oneFreight;
                    }
                    TradeFreightTemplateVO freightTemplateVO =
                            getFreightTemplateGoods(
                                    templateGoodsVO,
                                    expTemplate,
                                    templateTradeItemMap.get(freightTempId),
                                    oneFreight,
                                    templateGoodsMap.get(freightTempId),
                                    consignee.getProvinceId(),
                                    consignee.getCityId(),
                                    bearFlag,
                                    storeVo,
                                    tempId);
                    freightVo.getTemplateVOList().add(freightTemplateVO);
                    //组合 续x件 x元的单品运费模板
                    if (!Objects.equals(Boolean.TRUE, freightTemplateVO.getCollectFlag()) && !Objects.equals(BigDecimal.ZERO,freightTemplateVO.getFeright())) {
                        groupVO.getFreightTemplateIds().add(freightTempId);
                    }
                    freight = freight.add(oneFreight);
                }
                freightVo.getGroupVO().add(groupVO);
            }
        }

        return freight;
    }

    /**
     * @description  封装店铺运费模板
     * @author  wur
     * @date: 2022/7/15 13:53
     * @param templateStore 店铺运费模板
     * @param tradeItemList
     * @param giftItemList
     * @param freight       运费
     * @return
     **/
    private TradeFreightTemplateVO getFreightTemplateStore(
            FreightTemplateStoreVO templateStore,
            List<TradeItem> tradeItemList,
            List<TradeItem> giftItemList,
            List<TradeItem> preferentialItemList,
            CommissionFreightBearFlag bearFlag,
            BigDecimal freight,
            StoreVO storeVo,
            BigDecimal goodsPrice) {
        TradeFreightTemplateVO templateVO = new TradeFreightTemplateVO();
        List<String> skuIdList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(tradeItemList)) {
            skuIdList.addAll(tradeItemList.stream().map(TradeItem::getSkuId).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(giftItemList)) {
            skuIdList.addAll(giftItemList.stream().map(TradeItem::getSkuId).collect(Collectors.toList()));
        }

        if (CollectionUtils.isNotEmpty(preferentialItemList)) {
            skuIdList.addAll(preferentialItemList.stream().map(TradeItem::getSkuId).collect(Collectors.toList()));
        }
        templateVO.setFreightTemplateType(DefaultFlag.NO);
        templateVO.setSkuIdList(skuIdList);
        //代销商品商家承担
        if (Objects.nonNull(bearFlag) && CommissionFreightBearFlag.SELLER_BEAR.equals(bearFlag)) {
            templateVO.setFeright(BigDecimal.ZERO);
            templateVO.setCollectFlag(Boolean.FALSE);
            templateVO.setFreightDescribe("免运费");
            return templateVO;
        }
        templateVO.setFreightTemplateId(templateStore.getFreightTempId());
        templateVO.setFeright(freight);
        if (StoreType.PROVIDER.equals(storeVo.getStoreType())) {
            templateVO.setProviderStoreId(storeVo.getStoreId());
        }

        //免运费
        if (BigDecimal.ZERO.compareTo(freight) >= 0) {
            templateVO.setCollectFlag(Boolean.FALSE);
            templateVO.setFreightDescribe("免运费");
            return templateVO;
        }
        if (DefaultFlag.NO.equals(templateStore.getFreightType())) {
            templateVO.setCollectFlag(Boolean.TRUE);
            templateVO.setFreightDescribe(
                    String.format("运费%s元，满%s元免运费，还差%s元",
                            templateStore.getSatisfyFreight(),
                            templateStore.getSatisfyPrice(),
                            templateStore.getSatisfyPrice().subtract(goodsPrice)));
        } else {
            templateVO.setCollectFlag(Boolean.FALSE);
            templateVO.setFreightDescribe(String.format(String.format("运费%s元", templateStore.getFixedFreight())));
        }
        return templateVO;
    }

    /**
     * @description   封装单品运费模板信息
     * @author  wur
     * @date: 2022/7/15 15:35
     * @param templateGoodsVO  运费模板
     * @param expTemplate      用于运费计算的规则
     * @param itemList         目标商品
     * @param freight          运费
     * @param tradeItem        计算运费的目标资源
     * @param provId          收货地址
     * @param cityId          收货地址
     * @return
     **/
    private TradeFreightTemplateVO getFreightTemplateGoods(
            FreightTemplateGoodsVO templateGoodsVO,
            FreightTemplateGoodsExpressVO expTemplate,
            List<TradeItem> itemList,
            BigDecimal freight,
            TradeItem tradeItem,
            Long provId,
            Long cityId,
            CommissionFreightBearFlag bearFlag,
            StoreVO storeVo, Long firstFreightId) {
        TradeFreightTemplateVO templateVO = new TradeFreightTemplateVO();
        List<String> skuIdList = itemList.stream().map(TradeItem::getSkuId).collect(Collectors.toList());
        templateVO.setFreightTemplateType(DefaultFlag.YES);
        templateVO.setSkuIdList(skuIdList);
        //代销商品商家承担
        if (Objects.nonNull(bearFlag) && CommissionFreightBearFlag.SELLER_BEAR.equals(bearFlag)) {
            templateVO.setFeright(BigDecimal.ZERO);
            templateVO.setCollectFlag(Boolean.FALSE);
            templateVO.setFreightDescribe("免运费");
            return templateVO;
        }
        templateVO.setFeright(freight);
        templateVO.setFreightTemplateId(templateGoodsVO.getFreightTempId());
        if (StoreType.PROVIDER.equals(storeVo.getStoreType())) {
            templateVO.setProviderStoreId(storeVo.getStoreId());
        }

        //免运费
        if (BigDecimal.ZERO.compareTo(freight) >= 0) {
            templateVO.setCollectFlag(Boolean.FALSE);
            templateVO.setFreightDescribe("免运费");
            return templateVO;
        }

        //验证目标商品是否只有赠品   只有赠品则不支持凑单
        String head = String.format("运费%s元，",freight);
        //验证是否有命中免运费
        String freightDescribe = null;
        if (CollectionUtils.isNotEmpty(templateGoodsVO.getFreightTemplateGoodsFrees())) {
            Optional<FreightTemplateGoodsFreeVO> freeOptional =
                    templateGoodsVO.getFreightTemplateGoodsFrees().stream()
                            .filter(free -> matchArea(free.getDestinationArea(), provId, cityId))
                            .findFirst();
            if (freeOptional.isPresent()) {
                freightDescribe = getFreeDescribe(freeOptional.get(), tradeItem);
                templateVO.setFreeId(freeOptional.get().getId());
            }
            if (StringUtils.isNotBlank(freightDescribe)) {
                templateVO.setCollectFlag(Boolean.TRUE);
                templateVO.setFreightDescribe(head+freightDescribe);
                return templateVO;
            }
        }

        if (Objects.isNull(expTemplate)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
        }
        freightDescribe = getExpressDescribe(expTemplate, firstFreightId);
        templateVO.setCollectFlag(Boolean.FALSE);
        templateVO.setFreightDescribe(freightDescribe);
        //获取
        return templateVO;
    }

    /**
     * @description 获取规则文案
     * @author wur
     * @date: 2022/7/13 16:17
     * @param expTemp
     * @return
     */
    private String getExpressDescribe(FreightTemplateGoodsExpressVO expTemp, Long firstFreightTempId) {
        String unit = "";
        switch (expTemp.getValuationType()) {
            case NUMBER:
                unit = "件";
                break;
            case WEIGHT:
                unit = "kg";
                break;
            case VOLUME:
                unit = "m³";
                break;
            default:
                break;
        }
        if (!Objects.equals(expTemp.getFreightTempId(), firstFreightTempId)) {
            return String.format(
                    "每续%s%s，增加%s元运费",
                    expTemp.getFreightPlusNum(),
                    unit,
                    expTemp.getFreightPlusPrice());
        }
        return String.format(
                "%s%s内运费%s元，每续%s%s，增加%s元运费",
                expTemp.getFreightStartNum(),
                unit,
                expTemp.getFreightStartPrice(),
                expTemp.getFreightPlusNum(),
                unit,
                expTemp.getFreightPlusPrice());
    }

    /**
     * @description 获取免邮规则文案
     * @author wur
     * @date: 2022/7/13 16:18
     * @param expTemp
     * @return
     */
    private String getFreeDescribe(FreightTemplateGoodsFreeVO expTemp, TradeItem tradeItem) {
        switch (expTemp.getConditionType()) {
            case MONEY:
                return String.format("满%s元免运费，还差%s元", expTemp.getConditionTwo(), expTemp.getConditionTwo().subtract(tradeItem.getSplitPrice()));
            case VALUATION:
                switch (expTemp.getValuationType()) {
                    case NUMBER:
                        return String.format(
                                "满%s件免运费，还差%s件",
                                expTemp.getConditionOne().setScale(0, RoundingMode.DOWN),
                                expTemp.getConditionOne()
                                        .subtract(new BigDecimal(tradeItem.getNum()))
                                        .setScale(0, RoundingMode.DOWN));
                    default:
                        return null;
                }
            case VALUATIONANDMONEY:
                switch (expTemp.getValuationType()) {
                    case NUMBER:
                        StringBuilder sb = new StringBuilder();
                        sb.append(String.format(
                                "满%s件且满%s元免运费",
                                expTemp.getConditionOne().setScale(0, RoundingMode.DOWN), expTemp.getConditionTwo()));
                        if (expTemp.getConditionOne().compareTo(new BigDecimal(tradeItem.getNum())) > 0) {
                            sb.append(String.format(
                                    "，还差%s件",
                                    expTemp.getConditionOne().subtract(new BigDecimal(tradeItem.getNum())).setScale( 0, RoundingMode.DOWN )));
                        }
                        if (expTemp.getConditionTwo().compareTo(tradeItem.getSplitPrice()) > 0) {
                            sb.append(String.format(
                                    "，还差%s元",
                                    expTemp.getConditionTwo().subtract(tradeItem.getSplitPrice()).setScale(2, RoundingMode.HALF_UP)));
                        }
                        return sb.toString();
                    case WEIGHT:
                        if (tradeItem.getGoodsWeight().compareTo(expTemp.getConditionOne()) <= 0) {
                            StringBuilder des = new StringBuilder();
                            des.append(String.format(
                                    "在%skg内且满%s元免运费",
                                    expTemp.getConditionOne(), expTemp.getConditionTwo()));
                            if (expTemp.getConditionTwo().compareTo(tradeItem.getSplitPrice()) > 0) {
                                des.append(String.format(
                                        "，还差%s元",
                                        expTemp.getConditionTwo().subtract(tradeItem.getSplitPrice()).setScale(2, RoundingMode.HALF_UP)));
                            }
                            return des.toString();
                        }
                        break;
                    case VOLUME:
                        if (tradeItem.getGoodsWeight().compareTo(expTemp.getConditionOne()) <= 0) {
                            StringBuilder des = new StringBuilder();
                            des.append(String.format(
                                    "在%sm³内且满%s元免运费",
                                    expTemp.getConditionOne(), expTemp.getConditionTwo()));
                            if (expTemp.getConditionTwo().compareTo(tradeItem.getSplitPrice()) > 0) {
                                des.append(String.format(
                                        "，还差%s元",
                                        expTemp.getConditionTwo().subtract(tradeItem.getSplitPrice()).setScale(2, RoundingMode.HALF_UP)));
                            }
                            return des.toString();
                        }
                        break;
                    default:
                        return null;
                }
                break;
            default:
                return null;
        }
        return null;
    }


    /**
     * 匹配配送地区
     *
     * @param areaStr 存储的逗号相隔的areaId(provId,cityId都有可能)
     * @param provId 收货省份id
     * @param cityId 收货城市id
     * @return 是否匹配上
     */
    private boolean matchArea(String areaStr, Long provId, Long cityId) {
        String[] arr = areaStr.split(",");
        return Arrays.stream(arr).anyMatch(area -> area.equals(String.valueOf(provId)))
                || Arrays.stream(arr).anyMatch(area -> area.equals(String.valueOf(cityId)));
    }

    /**
     * 按模板id分组的商品汇总信息(模板Id,件数,重量,体积,小计均摊价)
     *
     * @param templateGoodsMap
     * @param items
     * @param storeVo 店铺VO对象，切面中使用，不可删除
     */
    public void setGoodsSumMap(Map<Long, TradeItem> templateGoodsMap, List<TradeItem> items, StoreVO storeVo) {
        if (items != null) {
            // 下单商品的运费
            items.stream().forEach(goods -> {
                TradeItem item = templateGoodsMap.get(goods.getFreightTempId());
                if (item == null) {
                    templateGoodsMap.put(goods.getFreightTempId(),
                            TradeItem.builder()
                                    .freightTempId(goods.getFreightTempId())
                                    .num(goods.getNum())
                                    .goodsWeight(goods.getGoodsWeight().multiply(BigDecimal.valueOf(goods.getNum())))
                                    .goodsCubage(goods.getGoodsCubage().multiply(BigDecimal.valueOf(goods.getNum())))
                                    .splitPrice(Nutils.defaultVal(goods.getSplitPrice(), BigDecimal.ZERO))
                                    .build());
                } else {
                    item.setNum(item.getNum() + goods.getNum());
                    item.setGoodsWeight(item.getGoodsWeight()
                            .add(goods.getGoodsWeight().multiply(BigDecimal.valueOf(goods.getNum()))));
                    item.setGoodsCubage(item.getGoodsCubage().add(goods.getGoodsCubage()
                            .multiply(BigDecimal.valueOf(goods.getNum()))));
                    item.setSplitPrice(item.getSplitPrice().add(Nutils.defaultVal(goods.getSplitPrice(),BigDecimal.ZERO)));
                }
            });
        }
    }

    /**
     * 按照运费模板Id分组
     * @param templateTradeItemMap
     * @param items
     */
    public void setTemplateTradeItemMap(Map<Long, List<TradeItem>> templateTradeItemMap, List<TradeItem> items) {
        if (items != null) {
            // 下单商品的运费
            items.stream().forEach(goods -> {
                List<TradeItem> tradeItemList = null;
                if (templateTradeItemMap.containsKey(goods.getFreightTempId())) {
                    tradeItemList = templateTradeItemMap.get(goods.getFreightTempId());
                } else {
                    tradeItemList = new ArrayList<>();
                }
                tradeItemList.add(goods);
                templateTradeItemMap.put(goods.getFreightTempId(), tradeItemList);
            });
        }
    }

    /**
     * 是否包邮
     *
     * @param temp 单品运费模板
     * @param templateGoodsMap 按模板id分组的商品汇总信息
     * @param deliverWay 运送方式
     * @param provId 省份id
     * @param cityId 城市id
     * @return
     */
    private boolean getFreeFreightFlag(
            FreightTemplateGoodsVO temp,
            Map<Long, TradeItem> templateGoodsMap,
            DeliverWay deliverWay,
            Long provId,
            Long cityId) {
        if (DefaultFlag.YES.equals(temp.getSpecifyTermFlag())) {
            ValuationType valuationType = temp.getValuationType();
            List<FreightTemplateGoodsFreeVO> freeTemplateList = temp.getFreightTemplateGoodsFrees();
            Optional<FreightTemplateGoodsFreeVO> freeOptional =
                    freeTemplateList.stream()
                            .filter(free -> matchArea(free.getDestinationArea(), provId, cityId))
                            .findFirst();

            // 2.3.1. 找到收货地匹配的 并且 运送方式一致的指定包邮条件
            if (freeOptional.isPresent() && deliverWay.equals(freeOptional.get().getDeliverWay())) {
                FreightTemplateGoodsFreeVO freeObj = freeOptional.get();
                ConditionType conditionType = freeObj.getConditionType();

                // 2.3.2. 根据计价方式,计算包邮条件是否满足
                switch (valuationType) {
                    case NUMBER: // 按件数
                        switch (conditionType) {
                            case VALUATION:
                                if (BigDecimal.valueOf(
                                                        templateGoodsMap
                                                                .get(temp.getFreightTempId())
                                                                .getNum())
                                                .compareTo(freeObj.getConditionOne())
                                        >= 0) { // 件数高于-包邮
                                    return false;
                                }
                                break;
                            case MONEY:
                                if (templateGoodsMap
                                                .get(temp.getFreightTempId())
                                                .getSplitPrice()
                                                .compareTo(freeObj.getConditionTwo())
                                        >= 0) { // 金额高于-包邮
                                    return false;
                                }
                                break;
                            case VALUATIONANDMONEY:
                                if (BigDecimal.valueOf(
                                                                templateGoodsMap
                                                                        .get(
                                                                                temp
                                                                                        .getFreightTempId())
                                                                        .getNum())
                                                        .compareTo(freeObj.getConditionOne())
                                                >= 0
                                        && templateGoodsMap
                                                        .get(temp.getFreightTempId())
                                                        .getSplitPrice()
                                                        .compareTo(freeObj.getConditionTwo())
                                                >= 0) { // 件数高于,金额高于-包邮
                                    return false;
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    case WEIGHT: // 按重量
                        switch (conditionType) {
                            case VALUATION:
                                if (templateGoodsMap
                                                .get(temp.getFreightTempId())
                                                .getGoodsWeight()
                                                .compareTo(freeObj.getConditionOne())
                                        <= 0) { // 重量低于-包邮
                                    return false;
                                }
                                break;
                            case MONEY:
                                if (templateGoodsMap
                                                .get(temp.getFreightTempId())
                                                .getSplitPrice()
                                                .compareTo(freeObj.getConditionTwo())
                                        >= 0) { // 金额高于-包邮
                                    return false;
                                }
                                break;
                            case VALUATIONANDMONEY:
                                if (templateGoodsMap
                                                        .get(temp.getFreightTempId())
                                                        .getGoodsWeight()
                                                        .compareTo(freeObj.getConditionOne())
                                                <= 0
                                        && templateGoodsMap
                                                        .get(temp.getFreightTempId())
                                                        .getSplitPrice()
                                                        .compareTo(freeObj.getConditionTwo())
                                                >= 0) { // 重量低于,金额高于-包邮
                                    return false;
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    case VOLUME: // 按体积
                        switch (conditionType) {
                            case VALUATION:
                                if (templateGoodsMap
                                                .get(temp.getFreightTempId())
                                                .getGoodsCubage()
                                                .compareTo(freeObj.getConditionOne())
                                        <= 0) { // 体积低于-包邮
                                    return false;
                                }
                                break;
                            case MONEY:
                                if (templateGoodsMap
                                                .get(temp.getFreightTempId())
                                                .getSplitPrice()
                                                .compareTo(freeObj.getConditionTwo())
                                        >= 0) { // 金额高于-包邮
                                    return false;
                                }
                                break;
                            case VALUATIONANDMONEY:
                                if (templateGoodsMap
                                                        .get(temp.getFreightTempId())
                                                        .getGoodsCubage()
                                                        .compareTo(freeObj.getConditionOne())
                                                <= 0
                                        && templateGoodsMap
                                                        .get(temp.getFreightTempId())
                                                        .getSplitPrice()
                                                        .compareTo(freeObj.getConditionTwo())
                                                >= 0) { // 体积低于,金额高于-包邮
                                    return false;
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return true;
    }

    /**
     * 获取匹配的单品运费模板-用于计算运费
     *
     * @param temps 多个收货的运费模板
     * @param provId 省份id
     * @param cityId 地市id
     * @return 匹配上的运费模板
     */
    private FreightTemplateGoodsExpressVO getMatchFreightTemplate(
            List<FreightTemplateGoodsExpressVO> temps, Long provId, Long cityId) {
        Optional<FreightTemplateGoodsExpressVO> expOpt =
                temps.stream()
                        .filter(exp -> matchArea(exp.getDestinationArea(), provId, cityId))
                        .findFirst();
        FreightTemplateGoodsExpressVO expTemp;
        expTemp =
                expOpt.orElseGet(
                        () ->
                                temps.stream()
                                        .filter(exp -> DefaultFlag.YES.equals(exp.getDefaultFlag()))
                                        .findFirst()
                                        .orElse(null));
        return expTemp;
    }

    /**
     * 计算某个单品模板的运费
     *
     * @param temp 单品运费模板
     * @param freightTempId 需要计算首件运费的配送地模板id
     * @param templateGoodsMap 按模板id分组的商品汇总信息
     * @return 模板的总运费
     */
    private BigDecimal getSingleTemplateFreight(
            FreightTemplateGoodsVO temp,
            Long freightTempId,
            Map<Long, TradeItem> templateGoodsMap) {
        // 是否需要计算首件运费标识
        boolean startFlag = temp.getFreightTempId().equals(freightTempId);
        TradeItem traItem = templateGoodsMap.get(temp.getFreightTempId());

        switch (temp.getValuationType()) {
            case NUMBER: // 按件数
                return startFlag
                        ? getStartAndPlusFreight(
                                BigDecimal.valueOf(traItem.getNum()), temp.getExpTemplate())
                        : getPlusFreight(
                                BigDecimal.valueOf(traItem.getNum()), temp.getExpTemplate());
            case WEIGHT: // 按重量
                return startFlag
                        ? getStartAndPlusFreight(traItem.getGoodsWeight(), temp.getExpTemplate())
                        : getPlusFreight(traItem.getGoodsWeight(), temp.getExpTemplate());
            case VOLUME: // 按体积
                return startFlag
                        ? getStartAndPlusFreight(traItem.getGoodsCubage(), temp.getExpTemplate())
                        : getPlusFreight(traItem.getGoodsCubage(), temp.getExpTemplate());
            default:
                return BigDecimal.ZERO;
        }
    }

    /**
     * 计算 首件 + 续件 总费用
     *
     * @param itemCount
     * @param expTemplate
     * @return
     */
    private BigDecimal getStartAndPlusFreight(
            BigDecimal itemCount, FreightTemplateGoodsExpressVO expTemplate) {
        if (itemCount.compareTo(expTemplate.getFreightStartNum()) <= 0) {
            return expTemplate.getFreightStartPrice(); // 首件数以内,则只算首运费
        } else {
            // 总费用 = 首件费用 + 续件总费用
            BigDecimal sumFreight = expTemplate
                    .getFreightStartPrice()
                    .add(getPlusFreight(itemCount.subtract(expTemplate.getFreightStartNum()),expTemplate));
            return sumFreight;
        }
    }

    /**
     * 计算续件总费用
     *
     * @param itemCount 商品数量
     * @param expTemplate 匹配的运费模板
     * @return 续件总费用
     */
    private BigDecimal getPlusFreight(
            BigDecimal itemCount, FreightTemplateGoodsExpressVO expTemplate) {
        // 商品数量/续件数量 * 续件金额
        BigDecimal plusFreight = itemCount.divide(expTemplate.getFreightPlusNum(), 0, RoundingMode.UP)
                .multiply(expTemplate.getFreightPlusPrice());
        return plusFreight;
    }

    /**
     * @description    生成VOP运费缓存key
     * @author  wur
     * @date: 2021/9/26 14:51
     * @param consignee    收货人信息
     * @param goodsInfoList      商品信息
     * @return
     **/
    private String getVopFreightKey (Consignee consignee, List<ChannelGoodsInfoDTO> goodsInfoList) {
        StringBuilder key = new StringBuilder();
        key.append(VOP_FREIGHT_KEY);
        for (int i=0 ; i < goodsInfoList.size(); i++) {
            ChannelGoodsInfoDTO goodsInfoDTO = goodsInfoList.get(i);
            if (i == 0){
                key.append(goodsInfoDTO.getThirdPlatformSkuId());
                key.append("N");
                key.append(Objects.isNull(goodsInfoDTO.getNum()) ? "1" : goodsInfoDTO.getNum().toString());
            } else {
                key.append("_");
                key.append(goodsInfoDTO.getThirdPlatformSkuId());
                key.append("N");
                key.append(Objects.isNull(goodsInfoDTO.getNum()) ? "1" : goodsInfoDTO.getNum().toString());
            }
        }
        key.append(":").append(consignee.getProvinceId().toString()).append("_").append(consignee.getCityId().toString());
        if (Objects.nonNull(consignee.getAreaId())) {
            key.append("_").append(consignee.getAreaId().toString());
        }
        if (Objects.nonNull(consignee.getStreetId())) {
            key.append("_").append(consignee.getStreetId().toString());
        }
        return key.toString();
    }

    /**
     * @description  运费券处理
     * @author  wur
     * @date: 2022/9/30 14:52
     * @param freightCouponCodeId    运费券码Id
     * @param tradeItems             提交订单商品信息
     * @param buyer             当前登录得用户
     * @param freightPrice           运费金额
     * @param totalPrice             目标商品总金额  谨慎使用目前只用于 预售支付尾款
     * @return
     **/
    public CountCouponPriceVO freightCoupon(String freightCouponCodeId, List<TradeItem> tradeItems, Buyer buyer, BigDecimal freightPrice, Trade1CommitParam param, BigDecimal totalPrice) {
        if (Objects.isNull(buyer)) {
            return null;
        }

        List<CountCouponPriceGoodsInfoDTO> couponPriceGoodsInfoDTOList = tradeItemListToCountCouponPriceGoodsInfoDTO(tradeItems, param);
        TradeCountCouponPriceRequest request =
                TradeCountCouponPriceRequest.builder()
                        .couponCodeId(freightCouponCodeId)
                        .freightPrice(freightPrice)
                        .customerId(buyer.getId())
                        .countPriceGoodsInfoDTOList(couponPriceGoodsInfoDTOList)
                        .forceCommit(Boolean.FALSE)
                        .totalPrice(totalPrice)
                        .build();
        TradeCountCouponPriceResponse tradeCountCouponPriceResponse = tradeCountMarketingPriceProvider.tradeCountCouponPrice(request).getContext();
        return Objects.isNull(tradeCountCouponPriceResponse) ? null : tradeCountCouponPriceResponse.getCountCouponPriceVO();
    }

    private List<CountCouponPriceGoodsInfoDTO> tradeItemListToCountCouponPriceGoodsInfoDTO(
            List<TradeItem> tradeItems, Trade1CommitParam param) {
        List<CountCouponPriceGoodsInfoDTO> resultList = new ArrayList<>();
        SystemPointsConfigQueryResponse pointsConfig = param.getSystemPointsConfigQueryResponse();
        tradeItems.forEach(
                tradeItem -> {
                    CountCouponPriceGoodsInfoDTO couponPriceGoodsInfoDTO =
                            this.orderMapper.tradeItemToCountPriceGoodsInfoDTO(tradeItem);
                    // 处理积分优惠金额   如果积分是订单抵扣使用运费券需要将积分抵扣的金额还原
                    if (Objects.nonNull(couponPriceGoodsInfoDTO)
                            && Objects.nonNull(couponPriceGoodsInfoDTO.getSplitPrice())
                            && Objects.nonNull(tradeItem.getPointsPrice())
                            && PointsUsageFlag.ORDER.equals(pointsConfig.getPointsUsageFlag())) {
                        couponPriceGoodsInfoDTO.setSplitPrice(
                                couponPriceGoodsInfoDTO
                                        .getSplitPrice()
                                        .add(tradeItem.getPointsPrice()));
                    }
                    resultList.add(couponPriceGoodsInfoDTO);
                });
        return resultList;
    }
}
