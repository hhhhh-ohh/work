package com.wanmi.sbc.order.trade.service;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DistributionCommissionUtils;
import com.wanmi.sbc.common.util.IteratorUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributorLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerEnableByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributorLevelByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelByCustomerIdAndStoreIdRequest;
import com.wanmi.sbc.customer.api.response.distribution.DistributionCustomerEnableByCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributorLevelByCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.level.CustomerLevelByCustomerIdAndStoreIdResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerSimplifyOrderCommitVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;
import com.wanmi.sbc.goods.api.provider.goodsrestrictedsale.GoodsRestrictedSaleQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceProvider;
import com.wanmi.sbc.goods.api.request.goodsrestrictedsale.GoodsRestrictedBatchValidateRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoPartColsByIdsRequest;
import com.wanmi.sbc.goods.api.request.price.GoodsIntervalPriceByCustomerIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdsResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleVO;
import com.wanmi.sbc.goods.bean.vo.BookingSaleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedValidateVO;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingLevelPluginProvider;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleInProgressRequest;
import com.wanmi.sbc.marketing.api.request.appointmentsale.RushToAppointmentSaleGoodsRequest;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingSaleIsInProgressRequest;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingLevelGoodsListFilterRequest;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingPluginGoodsListFilterRequest;
import com.wanmi.sbc.marketing.api.response.appointmentsale.AppointmentSaleInProcessResponse;
import com.wanmi.sbc.marketing.api.response.bookingsale.BookingSaleIsInProgressResponse;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.order.api.request.appointmentrecord.AppointmentRecordQueryRequest;
import com.wanmi.sbc.order.api.request.purchase.Purchase4DistributionSimplifyRequest;
import com.wanmi.sbc.order.api.response.purchase.Purchase4DistributionResponse;
import com.wanmi.sbc.order.appointmentrecord.model.root.AppointmentRecord;
import com.wanmi.sbc.order.appointmentrecord.service.AppointmentRecordService;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.TradeGoodsListVO;
import com.wanmi.sbc.order.cache.DistributionCacheService;
import com.wanmi.sbc.order.purchase.PurchaseService;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Buyer;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.request.TradeParams;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 存放订单与商品服务相关的接口方法
 * @author wanggang
 */
@Service
public class TradeGoodsService {

    @Autowired
    private GoodsIntervalPriceProvider goodsIntervalPriceProvider;

    @Autowired
    private MarketingPluginProvider marketingPluginProvider;

    @Autowired
    private TradeCacheService tradeCacheService;

    @Autowired
    private AppointmentSaleQueryProvider appointmentSaleQueryProvider;

    @Autowired
    private BookingSaleQueryProvider bookingSaleQueryProvider;

    @Autowired
    private GoodsRestrictedSaleQueryProvider goodsRestrictedSaleQueryProvider;

    @Autowired
    private AppointmentRecordService appointmentRecordService;

    @Autowired
    private VerifyService verifyService;

    @Autowired
    private MarketingLevelPluginProvider marketingLevelPluginProvider;

    @Autowired
    private DistributionCacheService distributionCacheService;

    @Autowired
    private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private DistributorLevelQueryProvider distributorLevelQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    /**
     * 1.根据 订单项List 获取商品信息List
     * 2.设置该商品信息List中会用到的区间价信息
     * 3.修改商品信息List中的会员价(salePrice)
     * @param trade 订单
     * @return 商品信息List
     */
    public TradeGoodsListVO getGoodsInfoResponse(Trade trade, TradeParams tradeParams) {
        return getGoodsInfoResponse(trade,tradeParams.getCustomer(),tradeParams.getGoodsInfoViewByIdsResponse());
    }

    /**
     * 1.根据 订单项List 获取商品信息List
     * 2.设置该商品信息List中会用到的区间价信息
     * 3.修改商品信息List中的会员价(salePrice)
     * @param trade 订单
     * @return 商品信息List
     */
    public TradeGoodsListVO getGoodsInfoResponse(Trade trade) {
        //1. 获取sku
        Buyer b = trade.getBuyer();
        GoodsInfoViewByIdsResponse idsResponse = tradeCacheService.getGoodsInfoViewByIds(IteratorUtils.collectKey(trade.getTradeItems(), TradeItem::getSkuId));
        CustomerSimplifyOrderCommitVO customerVO = verifyService.simplifyById(b.getId());

        return getGoodsInfoResponse(trade,customerVO,idsResponse);
    }


    /**
     * 1.根据 订单项List 获取商品信息List
     * 2.设置该商品信息List中会用到的区间价信息
     * 3.修改商品信息List中的会员价(salePrice)
     * @param trade 订单
     * @return 商品信息List
     */
    public TradeGoodsListVO getGoodsInfoResponse(Trade trade, CustomerSimplifyOrderCommitVO customerVO, GoodsInfoViewByIdsResponse idsResponse ) {
        TradeGoodsListVO response = new TradeGoodsListVO();
        response.setGoodsInfos(idsResponse.getGoodsInfos());
        response.setGoodses(idsResponse.getGoodses());
        List<GoodsInfoDTO> goodsInfoDTOList = KsBeanUtil.convert(response.getGoodsInfos(), GoodsInfoDTO.class);
        GoodsIntervalPriceByCustomerIdResponse intervalPriceResponse =
                goodsIntervalPriceProvider.putByCustomerId(
                        GoodsIntervalPriceByCustomerIdRequest.builder().goodsInfoDTOList(goodsInfoDTOList)
                                .customerId(customerVO.getCustomerId()).build()).getContext();
        //计算区间价
        response.setGoodsIntervalPrices(intervalPriceResponse.getGoodsIntervalPriceVOList());
        response.setGoodsInfos(intervalPriceResponse.getGoodsInfoVOList());
        // 商品是否可以分销状态-不要覆盖了
        response.getGoodsInfos().forEach(goodsInfoVO -> {
            goodsInfoVO.setDistributionGoodsAudit(trade.getTradeItems().stream().filter(tradeItem -> Objects.equals(goodsInfoVO.getGoodsInfoId(), tradeItem.getSkuId())).findFirst().orElseGet(() -> new TradeItem()).getDistributionGoodsAudit());
        });

        //填充实时的校验字段
        this.fillPartCols(response.getGoodsInfos());

        //目前只计算商品的客户级别价格/客户指定价
        MarketingPluginGoodsListFilterRequest filterRequest = new MarketingPluginGoodsListFilterRequest();
        filterRequest.setGoodsInfos(KsBeanUtil.convert(response.getGoodsInfos(), GoodsInfoDTO.class));
        filterRequest.setCustomerId(customerVO.getCustomerId());
        filterRequest.setCommitFlag(Boolean.TRUE);
        //秒杀从购物车走普通商品提交
        if (Objects.isNull(trade.getIsFlashSaleGoods()) || !trade.getIsFlashSaleGoods()) {
            filterRequest.setIsFlashSaleMarketing(Boolean.TRUE);
        }
        //秒杀从购物车走普通商品提交
        if (Objects.isNull(trade.getIsFlashPromotionGoods()) || !trade.getIsFlashPromotionGoods()) {
            filterRequest.setIsFlashSaleMarketing(Boolean.TRUE);
        }

        response.setGoodsInfos(marketingPluginProvider.goodsListFilter(filterRequest).getContext().getGoodsInfoVOList());
        return response;
    }

    /**
     * 校验商品限售信息
     *
     * @param tradeItemGroupVO
     */
    public void validateRestrictedGoods(TradeItemGroup tradeItemGroupVO, CustomerSimplifyOrderCommitVO customer,
                                        String addressId) {
        Boolean openGroup = Boolean.FALSE;
        if (Objects.nonNull(tradeItemGroupVO.getGrouponForm()) && Objects.nonNull(tradeItemGroupVO.getGrouponForm().getOpenGroupon())) {
            openGroup = tradeItemGroupVO.getGrouponForm().getOpenGroupon();
        }
        Boolean storeBagsFlag = Boolean.FALSE;
        if (DefaultFlag.YES.equals(tradeItemGroupVO.getStoreBagsFlag())) {
            storeBagsFlag = Boolean.TRUE;
        }
        //组装请求的数据
        List<TradeItem> tradeItemVOS = tradeItemGroupVO.getTradeItems();
        List<GoodsRestrictedValidateVO> list = KsBeanUtil.convert(tradeItemVOS, GoodsRestrictedValidateVO.class);

        CustomerVO customerVO = KsBeanUtil.convert(customer,CustomerVO.class);
        Supplier supplier = tradeItemGroupVO.getSupplier();
        Long storeId = null;
        if (Objects.nonNull(supplier) && StoreType.O2O == supplier.getStoreType()){
            storeId = supplier.getStoreId();
        }
        goodsRestrictedSaleQueryProvider.validateOrderRestricted(GoodsRestrictedBatchValidateRequest.builder()
                .goodsRestrictedValidateVOS(list)
                .snapshotType(tradeItemGroupVO.getSnapshotType())
                .customerVO(customerVO)
                .openGroupon(openGroup)
                .storeId(storeId)
                .storeBagsFlag(storeBagsFlag)
                .addressId(addressId)
                .pickUpSkuIds(tradeItemGroupVO.getPickUpSkuIds())
                .buyCycleFlag(Objects.nonNull(tradeItemGroupVO.getTradeBuyCycleDTO()))
                .build());
    }

    /**
     *
     * @param skuIds
     * @param customer
     * @param storeId   门店ID，O2O确认订单时使用，不可删除
     * @return
     */
    public GoodsInfoResponse getGoodsResponse(List<String> skuIds, CustomerSimplifyOrderCommitVO customer, Long storeId) {
        GoodsInfoViewByIdsResponse response = tradeCacheService.getGoodsInfoViewByIds(skuIds);
        List<GoodsInfoVO> goodsInfoVOList = response.getGoodsInfos();
        List<GoodsInfoDTO> goodsInfoDTOList = KsBeanUtil.convert(goodsInfoVOList, GoodsInfoDTO.class);
        GoodsIntervalPriceByCustomerIdResponse priceResponse =
                goodsIntervalPriceProvider.putByCustomerId(
                        GoodsIntervalPriceByCustomerIdRequest.builder().goodsInfoDTOList(goodsInfoDTOList)
                                .customerId(customer.getCustomerId()).build()).getContext();
        response.setGoodsInfos(priceResponse.getGoodsInfoVOList());

        //填充实时的校验字段
        this.fillPartCols(response.getGoodsInfos());

        if (StringUtils.isNotBlank(customer.getCustomerId())) {
            //计算会员价
            response.setGoodsInfos(
                    marketingLevelPluginProvider.goodsListFilter(MarketingLevelGoodsListFilterRequest.builder()
                            .goodsInfos(KsBeanUtil.convert(response.getGoodsInfos(), GoodsInfoDTO.class))
                            .customerId(customer.getCustomerId()).build())
                            .getContext().getGoodsInfoVOList());
        }

        return GoodsInfoResponse.builder().goodsInfos(response.getGoodsInfos())
                .goodses(response.getGoodses())
                .goodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList())
                .build();

    }

    /**
     * 根据开关重新设置分销商品标识
     * @param goodsInfoList
     */
    public void checkDistributionSwitch(List<GoodsInfoVO> goodsInfoList,ChannelType channelType) {
        //需要叠加访问端Pc\app不体现分销业务
        DefaultFlag openFlag = distributionCacheService.queryOpenFlag();
        goodsInfoList.forEach(goodsInfoVO -> {
            Boolean distributionFlag = Objects.equals(ChannelType.PC_MALL, channelType) || DefaultFlag.NO.equals(openFlag) || DefaultFlag.NO.equals(distributionCacheService.queryStoreOpenFlag(String.valueOf(goodsInfoVO.getStoreId())));
            // 排除积分价商品
            Boolean pointsFlag = !(Objects.isNull(goodsInfoVO.getBuyPoint()) || (goodsInfoVO.getBuyPoint().compareTo(0L) == 0));
            // 排除预售、预约
            Boolean appointmentFlag = Objects.nonNull(goodsInfoVO.getAppointmentSaleVO());
            Boolean bookingFlag = Objects.nonNull(goodsInfoVO.getBookingSaleVO());
            if (distributionFlag||pointsFlag||appointmentFlag||bookingFlag) {
                goodsInfoVO.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
            }
        });
    }

    public Purchase4DistributionResponse distribution(@RequestBody @Valid Purchase4DistributionSimplifyRequest
                                                                            request) {
        DistributeChannel channel = request.getDistributeChannel();
        List<GoodsInfoVO> goodsInfoVOList = request.getGoodsInfos();
        List<GoodsInfoVO> goodsInfoComList = request.getGoodsInfos();
        List<GoodsIntervalPriceVO> goodsIntervalPrices = request.getGoodsIntervalPrices();
        CustomerSimplifyOrderCommitVO customer = request.getCustomer();
        Purchase4DistributionResponse response = Purchase4DistributionResponse.builder().goodsInfos(goodsInfoVOList)
                .goodsInfoComList(goodsInfoComList).build();

        //1.如果为社交分销渠道
        if (null != channel && !Objects.equals(channel.getChannelType(), ChannelType.PC_MALL)) {
            response.setSelfBuying(false);
            //分销商品
            List<GoodsInfoVO> goodsInfoDistributionList = goodsInfoVOList.stream().filter(goodsInfo -> Objects
                    .equals(goodsInfo.getDistributionGoodsAudit(), DistributionGoodsAudit.CHECKED)).collect
                    (Collectors.toList());
            //验证自购
            if ((Objects.equals(channel.getInviteeId(), Constants.PURCHASE_DEFAULT) || Objects.isNull(channel
                    .getInviteeId())) && Objects.nonNull(customer)) {
                DistributionCustomerEnableByCustomerIdResponse customerEnableByCustomerIdResponse =
                        distributionCustomerQueryProvider.checkEnableByCustomerId
                                (DistributionCustomerEnableByCustomerIdRequest.builder().customerId(customer
                                        .getCustomerId()).build()).getContext();
                response.setSelfBuying(customerEnableByCustomerIdResponse.getDistributionEnable() && CollectionUtils
                        .isNotEmpty(goodsInfoDistributionList));
            }
            //排除分销商品
            if (channel.getChannelType() == ChannelType.SHOP) {
                goodsInfoComList = new ArrayList<>();
            } else {
                goodsInfoComList = goodsInfoVOList.stream().filter(goodsInfo -> !Objects.equals(goodsInfo
                        .getDistributionGoodsAudit(), DistributionGoodsAudit.CHECKED)).collect(Collectors.toList());
            }
            //3.分销商品去除阶梯价等信息
            goodsIntervalPrices = setDistributorPrice(goodsInfoVOList, goodsIntervalPrices);

            //2.如果为店铺精选购买
            purchaseService.verifyDistributorGoodsInfo(channel, goodsInfoVOList);

            //4.分销价叠加分销员等级
            if (Objects.nonNull(customer)) {
                BaseResponse<DistributorLevelByCustomerIdResponse> resultBaseResponse =
                        distributorLevelQueryProvider.getByCustomerId(new DistributorLevelByCustomerIdRequest
                                (customer.getCustomerId()));
                DistributorLevelVO distributorLevelVO = Objects.isNull(resultBaseResponse) ? null :
                        resultBaseResponse.getContext().getDistributorLevelVO();
                if (Objects.nonNull(distributorLevelVO) && Objects.nonNull(distributorLevelVO.getCommissionRate())) {
                    goodsInfoVOList.stream().forEach(goodsInfoVO -> {
                        if (DistributionGoodsAudit.CHECKED.equals(goodsInfoVO.getDistributionGoodsAudit())) {
                            BigDecimal commissionRate = distributorLevelVO.getCommissionRate();
                            BigDecimal distributionCommission = goodsInfoVO.getDistributionCommission();
                            distributionCommission = DistributionCommissionUtils.calDistributionCommission(distributionCommission,commissionRate);
                            goodsInfoVO.setDistributionCommission(distributionCommission);
                        }
                    });
                }
            }
        }
        response.setGoodsInfoComList(goodsInfoComList);
        response.setGoodsInfos(goodsInfoVOList);
        response.setGoodsIntervalPrices(goodsIntervalPrices);
        return response;
    }

    /**
     * 分销商品去除阶梯价等信息
     *
     * @param goodsInfoVOList
     */
    private List<GoodsIntervalPriceVO> setDistributorPrice(List<GoodsInfoVO> goodsInfoVOList,
                                                           List<GoodsIntervalPriceVO> goodsIntervalPrices) {
        //        分销商品
        List<GoodsInfoVO> goodsInfoDistributionList = goodsInfoVOList.stream().filter(goodsInfo -> Objects.equals
                (goodsInfo.getDistributionGoodsAudit(), DistributionGoodsAudit.CHECKED)).collect(Collectors.toList());
        List<String> skuIdList = goodsInfoDistributionList.stream().map(GoodsInfoVO::getGoodsInfoId).collect
                (Collectors.toList());

        goodsInfoVOList.forEach(goodsInfo -> {
            if (skuIdList.contains(goodsInfo.getGoodsInfoId())) {
                goodsInfo.setIntervalPriceIds(null);
                goodsInfo.setIntervalMinPrice(null);
                goodsInfo.setIntervalMaxPrice(null);
                goodsInfo.setCount(null);
                goodsInfo.setMaxCount(null);
            }
        });
        return goodsIntervalPrices.stream().filter(intervalPrice -> !skuIdList.contains(intervalPrice.getGoodsInfoId
                ())).collect(Collectors.toList());
    }

    protected void validShopGoods(List<GoodsInfoVO> goodsInfoVOS) {
        List<String> goodsInfoIds = goodsInfoVOS.stream().filter(goodsInfo -> goodsInfo.getGoodsStatus() == GoodsStatus.INVALID).map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(goodsInfoIds)) {
            throw new SbcRuntimeException(goodsInfoIds, OrderErrorCodeEnum.K050027);
        }
    }

    /**
     * 校验活动初始化价格
     *
     * @param tradeItems
     * @return
     */
    public List<TradeItem> fillActivityPrice(List<TradeItem> tradeItems, List<GoodsInfoVO> goodsInfoVOList,String customerId) {
        Map<String, BigDecimal> skuMap = goodsInfoVOList.stream()
                .collect(HashMap::new, (m, v)->m.put(v.getGoodsInfoId(), v.getMarketPrice()), HashMap::putAll);
        return tradeItems.stream().map(item -> {
            GoodsInfoVO infoVO = goodsInfoVOList.parallelStream().filter(goodsInfoVO -> StringUtils.equals(goodsInfoVO.getGoodsInfoId(), item.getSkuId())).findFirst().orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000001));
            item.setSpuId(infoVO.getGoodsId());
            if (item.getIsAppointmentSaleGoods()) {
                AppointmentSaleVO appointmentSaleVO =
                        appointmentSaleQueryProvider.getAppointmentSaleRelaInfo(RushToAppointmentSaleGoodsRequest.builder().appointmentSaleId(item.getAppointmentSaleId()).
                                skuId(item.getSkuId()).build()).getContext().getAppointmentSaleVO();
                if (Objects.isNull(appointmentSaleVO)) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
                }
                if (appointmentSaleVO.getSnapUpEndTime().isAfter(LocalDateTime.now()) && appointmentSaleVO.getSnapUpStartTime().isBefore(LocalDateTime.now())) {
                    item.setPrice(Objects.isNull(appointmentSaleVO.getAppointmentSaleGood().getPrice()) ?
                            appointmentSaleVO.getAppointmentSaleGood().getGoodsInfoVO().getMarketPrice()
                            : appointmentSaleVO.getAppointmentSaleGood().getPrice());
                }
                return item;
            }
            if (item.getIsBookingSaleGoods()) {
                BookingSaleIsInProgressResponse bookingResponse =
                        bookingSaleQueryProvider.isInProgress(BookingSaleIsInProgressRequest.builder().goodsInfoId(item.getSkuId()).build()).getContext();
                if (Objects.isNull(bookingResponse) || Objects.isNull(bookingResponse.getBookingSaleVO())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                BookingSaleVO bookingSaleVO = bookingResponse.getBookingSaleVO();
                if (!bookingSaleVO.getId().equals(item.getBookingSaleId())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
                if (bookingSaleVO.getPauseFlag() == 1) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
                }
                if (bookingSaleVO.getBookingType().equals(NumberUtils.INTEGER_ONE)) {
                    if (bookingSaleVO.getHandSelEndTime().isBefore(LocalDateTime.now()) || bookingSaleVO.getHandSelStartTime().isAfter(LocalDateTime.now())) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
                    }
                    item.setPrice(skuMap.get(bookingSaleVO.getBookingSaleGoods().getGoodsInfoId()));
                    item.setBookingType(BookingType.EARNEST_MONEY);
                    BigDecimal handSelPrice = bookingSaleVO.getBookingSaleGoods().getHandSelPrice();
                    BigDecimal inflationPrice = bookingSaleVO.getBookingSaleGoods().getInflationPrice();
                    item.setEarnestPrice(handSelPrice.multiply(BigDecimal.valueOf(item.getNum())));
                    if (Objects.nonNull(inflationPrice)) {
                        item.setSwellPrice(inflationPrice.multiply(BigDecimal.valueOf(item.getNum())));
                    } else {
                        item.setSwellPrice(item.getEarnestPrice());
                    }
                    item.setHandSelStartTime(bookingSaleVO.getHandSelStartTime());
                    item.setHandSelEndTime(bookingSaleVO.getHandSelEndTime());
                    item.setTailStartTime(bookingSaleVO.getTailStartTime());
                    item.setTailEndTime(bookingSaleVO.getTailEndTime());
                }
                if (bookingSaleVO.getBookingType().equals(NumberUtils.INTEGER_ZERO)) {
                    if (bookingSaleVO.getBookingEndTime().isBefore(LocalDateTime.now()) || bookingSaleVO.getBookingStartTime().isAfter(LocalDateTime.now())) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080127);
                    }
                    item.setBookingType(BookingType.FULL_MONEY);
                    item.setPrice(Objects.isNull(bookingSaleVO.getBookingSaleGoods().getBookingPrice()) ?
                            skuMap.get(bookingSaleVO.getBookingSaleGoods().getGoodsInfoId())
                            : bookingSaleVO.getBookingSaleGoods().getBookingPrice());
                }
                // 判断活动是否是全平台客户还是店铺内客户
                if (!bookingSaleVO.getJoinLevel().equals(Constants.STR_MINUS_1)) {
                    CustomerLevelByCustomerIdAndStoreIdResponse levelResponse = customerLevelQueryProvider
                            .getCustomerLevelByCustomerIdAndStoreId(CustomerLevelByCustomerIdAndStoreIdRequest.builder().customerId(customerId).storeId(bookingSaleVO.getStoreId()).build())
                            .getContext();
                    if (Objects.nonNull(levelResponse) && Objects.nonNull(levelResponse.getLevelId())) {
                        if (!bookingSaleVO.getJoinLevel().equals(Constants.STR_0)
                                && !Arrays.asList(bookingSaleVO.getJoinLevel().split(",")).contains(levelResponse.getLevelId().toString())) {
                            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080122);
                        }
                    } else {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080123);
                    }
                }
                return item;
            }
            return item;
        }).collect(Collectors.toList());
    }

    /**
     * 预约活动校验是否有资格
     *
     * @param tradeItemGroups
     */
     void validateAppointmentQualification(List<TradeItemGroup> tradeItemGroups,String customerId) {
        Boolean suitMarketingFlag =
                tradeItemGroups.stream().anyMatch(tradeItemGroupVO -> Objects.nonNull(tradeItemGroupVO.getSuitMarketingFlag()) && tradeItemGroupVO.getSuitMarketingFlag().equals(Boolean.TRUE));
        Boolean isGrouponOrder =
                tradeItemGroups.stream().anyMatch(tradeItemGroupVO -> Objects.nonNull(tradeItemGroupVO.getGrouponForm()) && Objects.nonNull(tradeItemGroupVO.getGrouponForm().getOpenGroupon()));
        if (suitMarketingFlag || isGrouponOrder) {
            return;
        }
        List<String> appointmentSaleSkuIds = new ArrayList<>();
        List<String> allSkuIds = new ArrayList<>();

        tradeItemGroups.forEach(tradeItemGroup -> {
            appointmentSaleSkuIds.addAll(tradeItemGroup.getTradeItems().stream()
                    .filter(i -> Objects.nonNull(i.getIsAppointmentSaleGoods()) && i.getIsAppointmentSaleGoods())
                    .map(TradeItem::getSkuId).collect(Collectors.toList()));
            allSkuIds.addAll(tradeItemGroup.getTradeItems().stream()
                    .filter(i -> Objects.isNull(i.getBuyPoint()) || i.getBuyPoint() == 0)
                    .map(TradeItem::getSkuId).collect(Collectors.toList()));
        });
        if (CollectionUtils.isEmpty(allSkuIds)) {
            return;
        }
        AppointmentSaleInProcessResponse response =
                appointmentSaleQueryProvider.inProgressAppointmentSaleInfoByGoodsInfoIdList(
                        AppointmentSaleInProgressRequest.builder().goodsInfoIdList(allSkuIds)
                                .build()).getContext();
        int purchaseNum = appointmentSaleSkuIds.size();
        int actualNum = 0;
        if (Objects.nonNull(response) &&  CollectionUtils.isNotEmpty(response.getAppointmentSaleVOList())) {
            actualNum = response.getAppointmentSaleVOList().size();
        }

        //包含预约中商品, 校验不通过
        if (actualNum > purchaseNum) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080170);
        }

        //预约活动失效
        if (purchaseNum > actualNum) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050150);
        }

        if (Objects.nonNull(response) && CollectionUtils.isNotEmpty(response.getAppointmentSaleVOList())) {
            response.getAppointmentSaleVOList().forEach(a -> {
                if (!(a.getSnapUpStartTime().isBefore(LocalDateTime.now()) && a.getSnapUpEndTime().isAfter(LocalDateTime.now()))) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050150);
                }
                if (a.getAppointmentType().equals(NumberUtils.INTEGER_ONE)) {
                    // 判断活动是否是全平台客户还是店铺内客户
                    if (!a.getJoinLevel().equals(Constants.STR_MINUS_1)) {
                        CustomerLevelByCustomerIdAndStoreIdResponse levelResponse = customerLevelQueryProvider
                                .getCustomerLevelByCustomerIdAndStoreId(CustomerLevelByCustomerIdAndStoreIdRequest.builder().customerId(customerId).storeId(a.getStoreId()).build())
                                .getContext();
                        if (Objects.nonNull(levelResponse) && Objects.nonNull(levelResponse.getLevelId())) {
                            if (!a.getJoinLevel().equals(Constants.STR_0) && !Arrays.asList(a.getJoinLevel().split(",")).contains(levelResponse.getLevelId().toString())) {
                                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080122);
                            }
                        } else {
                            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080123);
                        }
                    }
                } else {
                    AppointmentRecord recordResponse =
                            appointmentRecordService.getAppointmentInfo(AppointmentRecordQueryRequest.builder().
                                    buyerId(customerId)
                                    .goodsInfoId(a.getAppointmentSaleGood().getGoodsInfoId()).appointmentSaleId(a.getId()).build());
                    if (Objects.isNull(recordResponse)) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080134);
                    }
                }
            });
        }
    }

    /**
     * 填充实时的部分字段，上下架状态、删除状态、可售性、审核状态
     * @param skuList
     */
    public void fillPartCols(List<GoodsInfoVO> skuList) {
        List<GoodsInfoVO> skus = goodsInfoQueryProvider.listPartColsByIds(GoodsInfoPartColsByIdsRequest.builder()
                .goodsInfoIds(skuList.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList())).build()).getContext().getGoodsInfos();
        if (CollectionUtils.isNotEmpty(skus)) {
            Map<String, GoodsInfoVO> skuMap = skus.stream().collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
            skuList.stream().filter(i -> skuMap.containsKey(i.getGoodsInfoId()))
                    .forEach(i -> {
                        GoodsInfoVO vo = skuMap.get(i.getGoodsInfoId());
                        i.setAddedFlag(vo.getAddedFlag());
                        i.setDelFlag(vo.getDelFlag());
                        i.setVendibility(vo.getVendibility());
                        i.setAuditStatus(vo.getAuditStatus());
                        i.setMarketPrice(vo.getMarketPrice());
                        i.setSupplyPrice(vo.getSupplyPrice());
                        i.setBuyPoint(vo.getBuyPoint());
                    });
        }
    }
}
