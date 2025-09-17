package com.wanmi.sbc.order.trade.service;


import com.wanmi.sbc.account.api.provider.invoice.InvoiceProjectSwitchQueryProvider;
import com.wanmi.sbc.account.api.request.invoice.InvoiceProjectSwitchByCompanyInfoIdRequest;
import com.wanmi.sbc.account.api.response.invoice.InvoiceProjectSwitchByCompanyInfoIdResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressByIdRequest;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerPointsAvailableByIdRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerSimplifyByIdRequest;
import com.wanmi.sbc.customer.api.request.store.NoDeleteStoreByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreCheckRequest;
import com.wanmi.sbc.customer.api.request.store.StoreCustomerRelaQueryRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerSimplifyByIdResponse;
import com.wanmi.sbc.customer.api.response.store.StoreCustomerRelaResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.CommonLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDeliveryAddressVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.constant.GoodsRestrictedErrorTips;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsSaveProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.*;
import com.wanmi.sbc.goods.api.response.cate.ContractCateListResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdsResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoMinusStockDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoPlusStockDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsMinusStockDTO;
import com.wanmi.sbc.goods.bean.dto.GoodsPlusStockDTO;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.buyoutprice.MarketingBuyoutPriceQueryProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponInfoQueryProvider;
import com.wanmi.sbc.marketing.api.provider.discount.MarketingFullDiscountQueryProvider;
import com.wanmi.sbc.marketing.api.provider.discount.MarketingFullReductionQueryProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCardQueryProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponProvider;
import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponQueryProvider;
import com.wanmi.sbc.marketing.api.provider.fullreturn.FullReturnQueryProvider;
import com.wanmi.sbc.marketing.api.provider.gift.FullGiftQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingScopeQueryProvider;
import com.wanmi.sbc.marketing.api.provider.preferential.PreferentialQueryProvider;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingSaleIsInProgressRequest;
import com.wanmi.sbc.marketing.api.request.buyoutprice.MarketingBuyoutPriceIdRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeValidOrderCommitRequest;
import com.wanmi.sbc.marketing.api.request.discount.MarketingFullDiscountByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.request.discount.MarketingFullReductionByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCardNumByOrderNoRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponByIdRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponUpdateFreezeStockRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnLevelListByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.request.gift.FullGiftDetailListByMarketingIdsAndLevelIdsRequest;
import com.wanmi.sbc.marketing.api.request.gift.FullGiftLevelListByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingGetByIdRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingScopeListInvalidMarketingRequest;
import com.wanmi.sbc.marketing.api.request.preferential.DetailByMIdsAndLIdsRequest;
import com.wanmi.sbc.marketing.api.response.bookingsale.BookingSaleIsInProgressResponse;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCodeValidOrderCommitResponse;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.bean.vo.*;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.api.request.trade.TradeVerifyPurchaseRequest;
import com.wanmi.sbc.order.api.response.trade.TradeGetGoodsResponse;
import com.wanmi.sbc.order.bean.enums.DeliverStatus;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.TradeGoodsListVO;
import com.wanmi.sbc.order.common.GoodsStockService;
import com.wanmi.sbc.order.thirdplatformtrade.service.ThirdPlatformTradeService;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Invoice;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.setting.api.provider.pickupsetting.PickupSettingQueryProvider;
import com.wanmi.sbc.setting.api.provider.thirdaddress.ThirdAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.thirdaddress.ThirdAddressListRequest;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;
import com.wanmi.sbc.setting.bean.vo.ThirdAddressVO;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;

import io.seata.spring.annotation.GlobalTransactional;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 校验Service
 * Created by jinwei on 22/3/2017.
 */
@Slf4j
@Service
@Primary
public class VerifyService implements VerifyServiceInterface {

    @Autowired
    private GoodsInfoProvider goodsInfoProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private StoreCustomerQueryProvider storeCustomerQueryProvider;

    @Autowired
    private StoreProvider storeProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private InvoiceProjectSwitchQueryProvider invoiceProjectSwitchQueryProvider;

    @Autowired
    private MarketingFullDiscountQueryProvider marketingFullDiscountQueryProvider;

    @Autowired
    private MarketingFullReductionQueryProvider marketingFullReductionQueryProvider;

    @Autowired
    private MarketingBuyoutPriceQueryProvider marketingBuyoutPriceQueryProvider;

    @Autowired
    private FullGiftQueryProvider fullGiftQueryProvider;

    @Autowired
    private PreferentialQueryProvider preferentialQueryProvider;

    @Autowired
    private FullReturnQueryProvider fullReturnQueryProvider;

    @Autowired
    private MarketingScopeQueryProvider marketingScopeQueryProvider;

    @Autowired
    private MarketingQueryProvider marketingQueryProvider;

    @Autowired
    private CouponCodeQueryProvider couponCodeQueryProvider;

    @Autowired
    private CouponInfoQueryProvider couponInfoQueryProvider;

    @Autowired
    private OsUtil osUtil;

    @Autowired
    private FlashSaleGoodsSaveProvider flashSaleGoodsSaveProvider;

    @Autowired
    private TradeCacheService tradeCacheService;

    @Autowired
    private GoodsStockService goodsStockService;

    @Autowired
    private TradeCustomerService tradeCustomerService;

    @Autowired
    private LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired
    private ThirdAddressQueryProvider thirdAddressQueryProvider;

    @Autowired
    private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    @Autowired
    private TradeItemService tradeItemService;

    @Autowired
    private ThirdPlatformTradeService thirdPlatformTradeService;

    @Autowired
    private TradeCommitIncision tradeCommitIncision;

    @Autowired
    private StockService stockService;

    @Autowired
    private PickupSettingQueryProvider pickupSettingQueryProvider;

    @Autowired
    private ElectronicCouponQueryProvider electronicCouponQueryProvider;

    @Autowired
    private ElectronicCouponProvider electronicCouponProvider;

    @Autowired
    private ElectronicCardQueryProvider electronicCardQueryProvider;

    /**
     * 校验购买商品,计算商品价格
     * 1.校验商品库存，删除，上下架状态
     * 2.验证购买商品的起订量,限定量
     * 3.根据isFull,填充商品信息与levelPrice(第一步价格计算)
     *
     * @param tradeItems        订单商品数据，仅包含skuId与购买数量
     * @param oldTradeItems     旧订单商品数据，可以为emptyList，用于编辑订单的场景，由于旧订单商品库存已先还回但事务未提交，因此在处理中将库存做逻辑叠加
     * @param goodsInfoResponse 关联商品信息
     * @param isFull            是否填充订单商品信息与设价(区间价/已经算好的会员价)
     * @param areaId            区域地址码，查区域库存
     */
    public List<TradeItem> verifyGoods(List<TradeItem> tradeItems, List<TradeItem> oldTradeItems, TradeGoodsListVO goodsInfoResponse, Long storeId,
                                       boolean isFull,String areaId) {
        List<GoodsInfoVO> goodsInfos = goodsInfoResponse.getGoodsInfos();
        Map<String, GoodsVO> goodsMap = goodsInfoResponse.getGoodses().stream().collect(Collectors.toMap
                (GoodsVO::getGoodsId, Function.identity()));
        Map<String, GoodsInfoVO> goodsInfoMap = goodsInfos.stream().collect(Collectors.toMap
                (GoodsInfoVO::getGoodsInfoId, Function.identity()));
        Map<String, Long> oldTradeItemMap = oldTradeItems.stream().collect(Collectors.toMap(TradeItem::getSkuId, TradeItem::getNum));
        tradeItems
                .forEach(tradeItem -> {
                    GoodsInfoVO goodsInfo = goodsInfoMap.get(tradeItem.getSkuId());
                    if(Objects.nonNull(goodsInfo)&&StringUtils.isNotBlank(goodsInfo.getGoodsId())){
                        //商品条形码存储
                        tradeItem.setGoodsInfoBarcode(goodsInfo.getGoodsInfoBarcode());
                        GoodsVO goods = goodsMap.get(goodsInfo.getGoodsId());
                        Long oldNum = oldTradeItemMap.getOrDefault(tradeItem.getSkuId(), 0L);
                        //1. 校验商品库存，删除，上下架状态
                        if (goodsInfo.getDelFlag().equals(DeleteFlag.YES) || goodsInfo.getAddedFlag().equals(0)
                                || goods.getAuditStatus().equals(CheckStatus.FORBADE) || Objects.equals(DefaultFlag.NO.toValue(),
                                buildGoodsInfoVendibility(goodsInfo))) {
                            throw new SbcRuntimeException(Arrays.asList(goodsInfo.getGoodsInfoId()), OrderErrorCodeEnum.K050027);
                        }
                        // 秒杀不校验商品库存
                        if (!(Objects.nonNull(tradeItem.getIsFlashSaleGoods()) && tradeItem.getIsFlashSaleGoods())) {
                            //校验供应商商品库存
                            if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId()) && Objects.isNull(goodsInfo.getThirdPlatformType())) {
                                GoodsInfoByIdResponse providerGoodsInfo = goodsInfoQueryProvider.getById(new GoodsInfoByIdRequest(goodsInfo.getProviderGoodsInfoId(), null)).getContext();
                                //购买数量大于供应商库存
                                if (tradeItem.getNum() > (providerGoodsInfo.getStock() + oldNum)) {
                                    throw new SbcRuntimeException(Arrays.asList(goodsInfo.getGoodsInfoId()), OrderErrorCodeEnum.K050026);
                                }
                            }
                        }
                        //如果是linkedmall商品，实时查库存,根据区域码查库存
                        if (ThirdPlatformType.LINKED_MALL.equals(goodsInfo.getThirdPlatformType())) {
                            String thirdAddrId = null;
                            if (areaId != null) {
                                List<ThirdAddressVO> thirdAddressList = thirdAddressQueryProvider.list(ThirdAddressListRequest.builder()
                                        .platformAddrIdList(Collections.singletonList(Objects.toString(areaId)))
                                        .thirdFlag(ThirdPlatformType.LINKED_MALL)
                                        .build()).getContext().getThirdAddressList();
                                if (CollectionUtils.isNotEmpty(thirdAddressList)) {
                                    thirdAddrId = thirdAddressList.get(0).getThirdAddrId();
                                }
                            }
                            String thirdPlatformSpuId = goodsInfo.getThirdPlatformSpuId();
                            List<LinkedMallStockVO> stocks =
                                    linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(new LinkedMallStockGetRequest(Collections.singletonList(Long.valueOf(thirdPlatformSpuId)), thirdAddrId == null ? "0" : thirdAddrId, null)).getContext();
                            if (stocks != null) {
                                Optional<LinkedMallStockVO> optional = stocks.stream()
                                        .filter(v -> String.valueOf(v.getItemId()).equals(thirdPlatformSpuId)).findFirst();
                                if (optional.isPresent()) {
                                    String thirdPlatformSkuId = goodsInfo.getThirdPlatformSkuId();
                                    Optional<LinkedMallStockVO.SkuStock> skuStock = optional.get().getSkuList().stream()
                                            .filter(v -> String.valueOf(v.getSkuId()).equals(thirdPlatformSkuId)).findFirst();
                                    if (skuStock.isPresent()) {
                                        Long quantity = skuStock.get().getStock();
                                        if (quantity < 1) {
                                            throw new SbcRuntimeException(OrderErrorCodeEnum.K050052);
                                        }
                                    } else {
                                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050052);
                                    }
                                }

                            }
                        }
                        // 秒杀不校验商品库存
                        if (!(Objects.nonNull(tradeItem.getIsFlashSaleGoods()) && tradeItem.getIsFlashSaleGoods())) {
                            verifyGoodsInternal(tradeItem, goodsInfo, oldNum, storeId);
                        }
                        //预售商品
                        if (Objects.nonNull(tradeItem.getIsBookingSaleGoods()) && tradeItem.getIsBookingSaleGoods()) {
                            BookingSaleIsInProgressResponse response = bookingSaleQueryProvider.isInProgress(BookingSaleIsInProgressRequest
                                    .builder().goodsInfoId(tradeItem.getSkuId()).build()).getContext();
                            if (Objects.isNull(response) || Objects.isNull(response.getBookingSaleVO())) {
                                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                            }
                            BookingSaleVO bookingSaleVO = response.getBookingSaleVO();
                            if (!bookingSaleVO.getId().equals(tradeItem.getBookingSaleId())) {
                                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                            }
                            if (Objects.nonNull(bookingSaleVO.getBookingSaleGoods().getBookingCount())
                                    && Objects.nonNull(bookingSaleVO.getBookingSaleGoods().getCanBookingCount())) {
                                if (tradeItem.getNum() > (bookingSaleVO.getBookingSaleGoods().getCanBookingCount())) {
                                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030169,
                                           new Object[] {String.format(GoodsRestrictedErrorTips.GOODS_PURCHASE_MOST_NUMBER,
                                                   bookingSaleVO.getBookingSaleGoods().getCanBookingCount())} );
                                }
                            }
                        }
                        if (isFull) {
                            //3. 填充订单项基本数据
                            merge(tradeItem, goodsInfo, goods, storeId);
                            //4. 填充订单项区间价->levelPrice 会员等级价
                            calcPrice(tradeItem, goodsInfo, goods, goodsInfoResponse.getGoodsIntervalPrices());
                        }
                        // 商品类型
                        tradeCommitIncision.tradeItemPluginType(tradeItem,goodsInfo);
                        tradeItem.setPluginType(goodsInfo.getPluginType());
                        tradeItem.setSpuId(goods.getGoodsId());
                        tradeItem.setStoreId(goodsInfo.getStoreId());
                        tradeItem.setGoodsType(goods.getGoodsType());
                        tradeItem.setElectronicCouponsId(goodsInfo.getElectronicCouponsId());
                        stockService.getFreightTempId(tradeItem);
                    } else {
                        throw new SbcRuntimeException(Arrays.asList(tradeItem.getSkuId()), OrderErrorCodeEnum.K050027);
                    }
                });
        return tradeItems;
    }

    public Integer buildGoodsInfoVendibility(GoodsInfoVO goodsInfo) {

        Integer vendibility = Constants.yes;

        String providerGoodsInfoId = goodsInfo.getProviderGoodsInfoId();

        if (StringUtils.isNotBlank(providerGoodsInfoId)) {
            if (Constants.no.equals(goodsInfo.getVendibility())) {
                vendibility = Constants.no;
            }
        }
        return vendibility;
    }

    @Autowired
    private BookingSaleQueryProvider bookingSaleQueryProvider;

    /**
     * * 校验购买积分商品
     * 1.校验积分商品库存，删除，上下架状态
     *
     * @param goodsInfoResponse 关联商品信息
     * @param storeId           店铺ID
     * @return
     */
    public TradeItem verifyPointsGoods(TradeItem tradeItem, TradeGoodsListVO goodsInfoResponse, PointsGoodsVO
            pointsGoodsVO, Long storeId) {
        GoodsInfoVO goodsInfo = goodsInfoResponse.getGoodsInfos().get(0);
        GoodsVO goods = goodsInfoResponse.getGoodses().get(0);

        // 1.验证积分商品(校验积分商品库存，删除，启用停用状态，兑换时间)
        if (DeleteFlag.YES.equals(pointsGoodsVO.getDelFlag()) || EnableStatus.DISABLE.equals(pointsGoodsVO.getStatus())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030162);
        }
        if (pointsGoodsVO.getStock() < tradeItem.getNum() || goodsInfo.getStock() < tradeItem.getNum()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030163);
        }
        if (pointsGoodsVO.getEndTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030164);
        }
        // 2.填充订单商品信息
        merge(tradeItem, goodsInfo, goods, storeId);
        if(Objects.isNull(tradeItem.getPrice())){
            tradeItem.setPrice(goodsInfo.getMarketPrice());
        }
        return tradeItem;
    }


    /**
     * 为tradeItem 填充商品基本信息
     *
     * @param tradeItems        订单商品数据，仅包含skuId/价格
     * @param goodsInfoResponse 关联商品信息
     */
    public List<TradeItem> mergeGoodsInfo(List<TradeItem> tradeItems, TradeGetGoodsResponse goodsInfoResponse) {
        if (CollectionUtils.isEmpty(tradeItems)) {
            return Collections.emptyList();
        }
        List<GoodsInfoVO> goodsInfos = goodsInfoResponse.getGoodsInfos();
        Map<String, GoodsVO> goodsMap = goodsInfoResponse.getGoodses().stream().collect(Collectors.toMap
                (GoodsVO::getGoodsId, Function.identity()));
        Map<String, GoodsInfoVO> goodsInfoMap = goodsInfos.stream().collect(Collectors.toMap
                (GoodsInfoVO::getGoodsInfoId, Function.identity()));
        tradeItems
                .forEach(tradeItem -> {
                    GoodsInfoVO goodsInfo = goodsInfoMap.get(tradeItem.getSkuId());
                    GoodsVO goods = goodsMap.get(goodsInfo.getGoodsId());
                    //为tradeItem填充商品基本信息
                    merge(tradeItem, goodsInfo, goods, null);
                });
        return tradeItems;
    }

    public void verifyStore(List<Long> storeIds) {
        StoreCheckRequest request = new StoreCheckRequest();
        request.setIds(storeIds);
        if (!storeProvider.checkStore(request).getContext().getResult()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010105);
        }
    }

    /**
     * 批量校验店铺是否全部有效（审核状态|开关店|删除状态|签约失效时间）
     *
     * @return true|false:有效|无效,只要有一个失效，则返回false
     */
    public boolean checkStore(List<StoreVO> stores) {
        return CollectionUtils.isNotEmpty(stores)
                && stores.stream().noneMatch(
                s -> s.getDelFlag() == DeleteFlag.YES
                        || s.getAuditState() != CheckState.CHECKED
                        || s.getStoreState() == StoreState.CLOSED
                        || (!StoreType.O2O.equals(s.getStoreType())
                        && s.getContractEndDate().isBefore(LocalDateTime.now()))
        );
    }

    /**
     * 为tradeItem填充商品基本信息
     * 主要是合并价格和名称这些字段
     *
     * @param tradeItem
     * @param goodsInfo
     * @param goods
     */
    void merge(TradeItem tradeItem, GoodsInfoVO goodsInfo, GoodsVO goods, Long storeId) {
        tradeItem.setSkuName(goodsInfo.getGoodsInfoName());
        tradeItem.setSpuName(goods.getGoodsName());
        tradeItem.setPic(goodsInfo.getGoodsInfoImg());
        if(StringUtils.isEmpty(goodsInfo.getGoodsInfoImg())){
            tradeItem.setPic(goods.getGoodsImg());
        }
        tradeItem.setBrand(goods.getBrandId());
        tradeItem.setDeliverStatus(DeliverStatus.NOT_YET_SHIPPED);
        tradeItem.setCateId(goods.getCateId());
        tradeItem.setSkuNo(goodsInfo.getGoodsInfoNo());
        tradeItem.setSpuId(goods.getGoodsId());
        tradeItem.setUnit(goods.getGoodsUnit());
        tradeItem.setGoodsWeight(goodsInfo.getGoodsWeight());
        tradeItem.setGoodsCubage(goodsInfo.getGoodsCubage());
        tradeItem.setFreightTempId(goods.getFreightTempId());
        tradeItem.setStoreId(storeId);
        tradeItem.setDistributionGoodsAudit(goodsInfo.getDistributionGoodsAudit());
        tradeItem.setCommissionRate(goodsInfo.getCommissionRate());
        tradeItem.setDistributionCommission(goodsInfo.getDistributionCommission());
        tradeItem.setOriginalPrice(BigDecimal.ZERO);
        tradeItem.setLevelPrice(BigDecimal.ZERO);
        tradeItem.setSplitPrice(BigDecimal.ZERO);
        tradeItem.setCateTopId(goodsInfo.getCateTopId());
        tradeItem.setEnterPriseAuditState(goodsInfo.getEnterPriseAuditState());
        tradeItem.setEnterPrisePrice(goodsInfo.getEnterPrisePrice());
        if (!(Objects.nonNull(tradeItem.getIsFlashSaleGoods()) && tradeItem.getIsFlashSaleGoods())){
            tradeItem.setBuyPoint(goodsInfo.getBuyPoint());
        }
        tradeItem.setGoodsStatus(goodsInfo.getGoodsStatus());
        tradeItem.setThirdPlatformSpuId(goodsInfo.getThirdPlatformSpuId());
        tradeItem.setThirdPlatformSkuId(goodsInfo.getThirdPlatformSkuId());
        tradeItem.setGoodsSource(goodsInfo.getGoodsSource());
        tradeItem.setProviderId(goodsInfo.getProviderId());
        tradeItem.setThirdPlatformType(goodsInfo.getThirdPlatformType());
        tradeItem.setSupplyPrice(goodsInfo.getSupplyPrice());
        tradeItem.setProviderSkuId(goodsInfo.getProviderGoodsInfoId());
        tradeItem.setPluginType(goodsInfo.getPluginType());
        tradeItem.setGoodsType(goodsInfo.getGoodsType());
        if (StringUtils.isBlank(tradeItem.getSpecDetails())) {
            tradeItem.setSpecDetails(goodsInfo.getSpecText());
        }
        if (osUtil.isS2b() && storeId != null) {
            BaseResponse<ContractCateListResponse> baseResponse = tradeCacheService.queryContractCateList(storeId, goods.getCateId());
            ContractCateListResponse contractCateListResponse = baseResponse.getContext();
            if (Objects.nonNull(contractCateListResponse)) {
                List<ContractCateVO> cates = contractCateListResponse.getContractCateList();
                if (CollectionUtils.isNotEmpty(cates)) {
                    ContractCateVO cateResponse = cates.get(0);
                    tradeItem.setCateName(cateResponse.getCateName());
                    tradeItem.setCateRate(cateResponse.getCateRate() != null ? cateResponse.getCateRate() : cateResponse.getPlatformCateRate());
                }

            }
        }
    }

    /**
     * 设置订单项商品的订货区间价
     * 若无区间价,则设置为会员价(在前面使用插件已经算好的salePrice)
     * 【商品价格计算第①步】: 商品的 客户级别价格 (完成客户级别价格/客户指定价/订货区间价计算) -> levelPrice
     *
     * @param tradeItem
     * @param goodsInfo
     * @param goods
     * @param goodsIntervalPrices
     */
    void calcPrice(TradeItem tradeItem, GoodsInfoVO goodsInfo, GoodsVO goods, List<GoodsIntervalPriceVO>
            goodsIntervalPrices) {
        // 订货区间设价
        if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())) {
            Long buyNum = tradeItem.getNum();
            Optional<GoodsIntervalPriceVO> first = goodsIntervalPrices.stream()
                    .filter(item -> item.getGoodsInfoId().equals(tradeItem.getSkuId()))
                    .filter(intervalPrice -> buyNum >= intervalPrice.getCount())
                    .max(Comparator.comparingLong(GoodsIntervalPriceVO::getCount));
            if (first.isPresent()) {
                GoodsIntervalPriceVO goodsIntervalPrice = first.get();
                tradeItem.setLevelPrice(goodsIntervalPrice.getPrice());
                tradeItem.setOriginalPrice(goodsInfo.getMarketPrice());
                //判断是否为秒杀抢购商品
                if (!(Objects.nonNull(tradeItem.getIsFlashSaleGoods()) && tradeItem.getIsFlashSaleGoods())
                        && !(Objects.nonNull(tradeItem.getIsAppointmentSaleGoods()) && tradeItem.getIsAppointmentSaleGoods())
                        && !(Objects.nonNull(tradeItem.getIsBookingSaleGoods()) && tradeItem.getIsBookingSaleGoods())) {
                    tradeItem.setPrice(goodsIntervalPrice.getPrice());
                    tradeItem.setSplitPrice(tradeItem.getLevelPrice().multiply(
                            new BigDecimal(tradeItem.getNum())).setScale(2, RoundingMode.HALF_UP)
                    );
                } else {
                    tradeItem.setSplitPrice(tradeItem.getPrice().multiply(
                            new BigDecimal(tradeItem.getNum())).setScale(2, RoundingMode.HALF_UP));
                }
                return;
            }
        }
        tradeItem.setLevelPrice(ObjectUtils.defaultIfNull(goodsInfo.getSalePrice(), goodsInfo.getMarketPrice()));
        tradeItem.setOriginalPrice(ObjectUtils.defaultIfNull(goodsInfo.getMarketPrice(), BigDecimal.ZERO));
        //判断是否为秒杀抢购商品
        if (!(Objects.nonNull(tradeItem.getIsFlashSaleGoods()) && tradeItem.getIsFlashSaleGoods())
                && !(Objects.nonNull(tradeItem.getIsAppointmentSaleGoods()) && tradeItem.getIsAppointmentSaleGoods())
                // 全款预售指定预售价、定金预售价格以市场价计算
                && !(Objects.nonNull(tradeItem.getIsBookingSaleGoods()) && tradeItem.getIsBookingSaleGoods())
                ) {
            //分销商品用原价
            if(DistributionGoodsAudit.CHECKED == tradeItem.getDistributionGoodsAudit() && Objects.nonNull(goodsInfo.getMarketPrice())) {
                tradeItem.setPrice(goodsInfo.getMarketPrice());
                tradeItem.setSplitPrice(tradeItem.getPrice().multiply(
                        new BigDecimal(tradeItem.getNum())).setScale(2, RoundingMode.HALF_UP)
                );
            }else {
                tradeItem.setPrice(goodsInfo.getSalePrice());
                tradeItem.setSplitPrice(tradeItem.getLevelPrice().multiply(
                        new BigDecimal(tradeItem.getNum())).setScale(2, RoundingMode.HALF_UP)
                );
            }
        } else {
            tradeItem.setSplitPrice(tradeItem.getPrice().multiply(
                    new BigDecimal(tradeItem.getNum())).setScale(2, RoundingMode.HALF_UP));
        }
    }

    /**
     * 校验起订量,限定量
     *
     * @param tradeItem
     * @param goodsInfo
     * @param oldNum
     * @return
     */
    public void verifyGoodsInternal(TradeItem tradeItem, GoodsInfoVO goodsInfo, Long oldNum,Long storeId) {
        // o2o 切面方法 重新设置o2o 商品库存
        if(goodsInfo.getPluginType() == PluginType.O2O) {
            goodsInfo.setStoreId(storeId);
            goodsInfo = stockService.setStock(goodsInfo);
        }

        if (!(StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId()) && Objects.isNull(goodsInfo.getThirdPlatformType()))) {
            //非供应商商品，校验购买数量大于库存
            if (tradeItem.getNum() > (goodsInfo.getStock() + oldNum)) {
                throw new SbcRuntimeException(Arrays.asList(goodsInfo.getGoodsInfoId()), OrderErrorCodeEnum.K050026);
            }
        }
        // 起订量
        if (goodsInfo.getCount() != null) {
            //起订量大于库存
            if (goodsInfo.getCount() > goodsInfo.getStock()) {
                throw new SbcRuntimeException(Arrays.asList(goodsInfo.getGoodsInfoId()), OrderErrorCodeEnum.K050026);
            }
            //购买数量小于起订量
            if (goodsInfo.getCount() > tradeItem.getNum()) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050049, new Object[]{goodsInfo.getGoodsInfoName(), goodsInfo.getCount(), tradeItem.getNum()});
            }
        }

        // 限定量
        if (goodsInfo.getMaxCount() != null && goodsInfo.getMaxCount() < tradeItem.getNum()) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050049, new Object[]{goodsInfo.getGoodsInfoName(), goodsInfo.getMaxCount(), tradeItem.getNum()});
        }

    }

    /**
     * 减库存
     *
     * @param tradeItems
     */
    @Transactional
    @GlobalTransactional
    public void subSkuListStock(List<TradeItem> tradeItems, String orderNo) {
        updateSkuListStock(tradeItems, true,orderNo);
    }

    /**
     * 加库存
     *
     * @param tradeItems
     */
    public void addSkuListStock(List<TradeItem> tradeItems, String orderNo) {
        updateSkuListStock(tradeItems, false,orderNo);
    }


    /**
     * 库存变动
     *
     * @param tradeItems 订单项
     * @param subFlag    扣库存标识 true:减库存  false:加库存
     */
    private void updateSkuListStock(List<TradeItem> tradeItems, boolean subFlag, String orderNo) {
        // 1.判断订单商品/去除三方商品后的订单商品如果为空，直接返回
        if (CollectionUtils.isEmpty(tradeItems)) {
            return;
        }
        tradeItems = tradeItems.stream()
                .filter(v -> !ThirdPlatformType.LINKED_MALL.equals(v.getThirdPlatformType()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tradeItems)) {
            return;
        }
        // 2.根据SKUID批量查询商品，并转为 GoodsInfoId: GoodsInfo结构的Map
        List<String> skuIds = tradeItems.stream().map(TradeItem::getSkuId).collect(Collectors.toList());
        Map<String, GoodsInfoVO> goodsInfoMap = goodsInfoQueryProvider.listByIds(new GoodsInfoListByIdsRequest(skuIds, null))
                .getContext().getGoodsInfos().stream()
                .collect(Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));
        // 查询卡券现在关联的商品
        List<Long> electronicCouponIds = tradeItems.stream()
                .map(TradeItem::getElectronicCouponsId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        Map<Long, GoodsInfoVO> couponGoodsInfosMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(electronicCouponIds)) {
            couponGoodsInfosMap = goodsInfoQueryProvider
                    .findByElectronicCouponIds(GoodsInfoByElectronicCouponIdsRequest.builder()
                            .electronicCouponIds(electronicCouponIds).build())
                    .getContext().getGoodsInfoVOS()
                    .stream().collect(Collectors.toMap(GoodsInfoVO::getElectronicCouponsId, Function.identity()));
        }
        if (goodsInfoMap.size() > 0) {
            // 判断是减库存还是增加库存
            if (subFlag) {
                List<GoodsMinusStockDTO> spuStockList = new ArrayList<>();
                List<GoodsInfoMinusStockDTO> stockList = tradeItems.stream().map(tradeItem -> {
                    GoodsInfoVO goodsInfoVO = goodsInfoMap.get(tradeItem.getSkuId());

                    //判断是否是卡券商品，如果是，则冻结库存
                    if (Objects.nonNull(tradeItem.getGoodsType()) &&
                            tradeItem.getGoodsType() == GoodsType.ELECTRONIC_COUPON_GOODS.toValue()) {
                        electronicCouponProvider.updateFreezeStock(ElectronicCouponUpdateFreezeStockRequest.builder()
                                .freezeStock(tradeItem.getNum())
                                .id(tradeItem.getElectronicCouponsId())
                                .orderNo(orderNo)
                                .unBindOrderFlag(Boolean.TRUE)
                                .build());
                    }

                    GoodsInfoMinusStockDTO dto = new GoodsInfoMinusStockDTO();
                    dto.setStock(Objects.nonNull(tradeItem.getBuyCycleNum()) ? tradeItem.getBuyCycleNum() * tradeItem.getNum() : tradeItem.getNum());
                    //是供应商商品扣减供应商商品库存
                    boolean isProviderSku = StringUtils.isNotBlank(goodsInfoVO.getProviderGoodsInfoId()) && Objects.isNull(
                            goodsInfoVO.getThirdPlatformType());
                    String spuId = isProviderSku ? goodsInfoVO.getGoodsId() : tradeItem.getSpuId();
                    spuStockList.add(GoodsMinusStockDTO.builder()
                            .stock(Objects.nonNull(tradeItem.getBuyCycleNum()) ? tradeItem.getBuyCycleNum() * tradeItem.getNum() : tradeItem.getNum())
                            .goodsId(spuId)
                            .build());
                    dto.setGoodsInfoId(isProviderSku
                            ? goodsInfoVO.getProviderGoodsInfoId() : tradeItem.getSkuId());
                    dto.setStoreId(tradeItem.getStoreId());
                    return dto;
                }).collect(Collectors.toList());
                TradeItem tradeItem = tradeItems.stream().findFirst().orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000001));

                if(Objects.nonNull(tradeItem.getStoreId())) {
                    StoreType storeType = storeQueryProvider.getNoDeleteStoreById(NoDeleteStoreByIdRequest.builder().storeId(tradeItem.getStoreId()).build())
                            .getContext().getStoreVO().getStoreType();
                    if (storeType == StoreType.O2O) {
                        stockService.batchMinusStock(GoodsInfoBatchMinusStockRequest.builder().stockList(stockList).build());
                    } else {
                        goodsInfoProvider.batchMinusStock(GoodsInfoBatchMinusStockRequest.builder().stockList(stockList).build());
                        //spu减库存
                   //     goodsStockService.batchSubStock(spuStockList);
                    }
                } else {
                    goodsInfoProvider.batchMinusStock(GoodsInfoBatchMinusStockRequest.builder().stockList(stockList).build());
                    //spu减库存
               //     goodsStockService.batchSubStock(spuStockList);
                }
            } else {
                List<GoodsPlusStockDTO> spuStockList = new ArrayList<>();
                List<GoodsInfoPlusStockDTO> stockList = tradeItems.stream().map(tradeItem -> {
                    GoodsInfoVO goodsInfoVO = goodsInfoMap.get(tradeItem.getSkuId());
                    Long stock = tradeItem.getNum();
                    // 如果是电子卡券商品，则释放冻结库存
                    if (goodsInfoVO.getGoodsType() == Constants.TWO) {
                        ElectronicCouponVO electronicCouponVO = electronicCouponQueryProvider.getById(ElectronicCouponByIdRequest.builder()
                                .id(goodsInfoVO.getElectronicCouponsId())
                                .build()).getContext().getElectronicCouponVO();
                        // 查询有效的卡密数量，只返回有效的卡密数量（卡密可能删除或失效）
                        Integer cardNum = electronicCardQueryProvider.countByOrderNoAndCouponId(ElectronicCardNumByOrderNoRequest.builder()
                                .couponId(tradeItem.getElectronicCouponsId())
                                .orderNo(orderNo).build()).getContext().getNum();
                        electronicCouponProvider.updateFreezeStock(ElectronicCouponUpdateFreezeStockRequest.builder()
                                .freezeStock(-tradeItem.getNum())
                                .id(electronicCouponVO.getId())
                                .orderNo(orderNo)
                                .build());
                        stock = (long) cardNum;
                    }
                    GoodsInfoPlusStockDTO dto = new GoodsInfoPlusStockDTO();
                    dto.setStock(stock);
                    boolean isProviderSku = StringUtils.isNotBlank(goodsInfoVO.getProviderGoodsInfoId()) && Objects.isNull(
                            goodsInfoVO.getThirdPlatformType());
                    String spuId = isProviderSku ? goodsInfoVO.getGoodsId() : tradeItem.getSpuId();
                    spuStockList.add(GoodsPlusStockDTO.builder()
                            .stock(Objects.nonNull(tradeItem.getBuyCycleNum()) ? tradeItem.getBuyCycleNum() * tradeItem.getNum() : tradeItem.getNum())
                            .goodsId(spuId)
                            .storeId(tradeItem.getStoreId())
                            .build());
                    dto.setGoodsInfoId(isProviderSku
                            ? goodsInfoVO.getProviderGoodsInfoId() : tradeItem.getSkuId());
                    dto.setStoreId(tradeItem.getStoreId());
                    return dto;
                }).collect(Collectors.toList());
                TradeItem tradeItem = tradeItems.stream().findFirst().orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000001));
                if (tradeItem.getPluginType() == PluginType.O2O) {
                    stockService.batchPlusStock(GoodsInfoBatchPlusStockRequest.builder().stockList(stockList).build());
                } else {
                    goodsInfoProvider.batchPlusStock(GoodsInfoBatchPlusStockRequest.builder().stockList(stockList).build());
                    //spu加库存
                  //  goodsStockService.batchAddStock(spuStockList);
                }
            }
        }
    }

    public CustomerGetByIdResponse verifyCustomer(String customerId) {
        // 客户信息
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (customerId)).getContext();

        if (customer == null || LogOutStatus.LOGGING_OFF==customer.getLogOutStatus()
                || LogOutStatus.LOGGED_OUT==customer.getLogOutStatus()) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010017);
        }

        if (customer.getCustomerDetail().getCustomerStatus() == CustomerStatus.DISABLE) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010018);
        }
        return customer;
    }

    public CustomerSimplifyByIdResponse simplifyById(String customerId) {
        // 客户信息
        CustomerSimplifyByIdResponse customer = customerQueryProvider.simplifyById(new CustomerSimplifyByIdRequest(customerId)).getContext();

        if (customer == null || LogOutStatus.LOGGING_OFF==customer.getLogOutStatus()
                || LogOutStatus.LOGGED_OUT==customer.getLogOutStatus()) {
            throw new SbcRuntimeException(this.getCustomerDeleteIndex(customerId), CustomerErrorCodeEnum.K010017);
        }

        if (customer.getCustomerDetail().getCustomerStatus() == CustomerStatus.DISABLE) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010018);
        }
        return customer;
    }

    /**
     * 校验可使用积分
     *
     * @param tradeCommitRequest
     */
    public SystemPointsConfigQueryResponse verifyPoints(List<Trade> trades, TradeCommitRequest tradeCommitRequest, SystemPointsConfigQueryResponse pointsConfig) {
        //积分抵扣使用起始值
        Long overPointsAvailable = pointsConfig.getOverPointsAvailable();

        String customerId = tradeCommitRequest.getCustomer().getCustomerId();
        //订单使用积分
        Long points = tradeCommitRequest.getPoints();

        //查询会员可用积分
        Long pointsAvailable = customerQueryProvider.getPointsAvailable(new CustomerPointsAvailableByIdRequest
                (customerId)).getContext().getPointsAvailable();
        if (pointsAvailable == null) {
            pointsAvailable = 0L;
        }

        //你的积分不足无法下单
        if (PointsUsageFlag.GOODS.equals(pointsConfig.getPointsUsageFlag())) {
            if (points > pointsAvailable) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050089);
            }
        }

        //订单使用积分超出会员可用积分
        if (points.compareTo(pointsAvailable) > 0) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050086);
        }

        //会员可用积分未满足积分抵扣使用值
        if (pointsAvailable.compareTo(overPointsAvailable) < 0) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050087);
        }

        //订单使用积分超出积分抵扣限额
        //订单总金额
        BigDecimal totalTradePrice;
        Trade trade = trades.get(0);
        // 预售尾款结算时候积分处理
        if (Objects.nonNull(trade.getIsBookingSaleGoods()) && trade.getIsBookingSaleGoods() && trade.getBookingType() == BookingType.EARNEST_MONEY
                && Objects.nonNull(trade.getTradePrice().getTailPrice())) {
            totalTradePrice = trade.getTradePrice().getTailPrice();
        } else {
            totalTradePrice = trades.stream().map(t -> t.getTradePrice().getTotalPrice()).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        }
        //积分抵扣限额只考虑按照订单抵扣的
        if (PointsUsageFlag.ORDER.equals(pointsConfig.getPointsUsageFlag())) {
            BigDecimal pointWorth = BigDecimal.valueOf(pointsConfig.getPointsWorth());
            BigDecimal maxDeductionRate = pointsConfig.getMaxDeductionRate().divide(BigDecimal.valueOf(100), 4, RoundingMode.DOWN);
            Long maxPoints = totalTradePrice.multiply(maxDeductionRate).multiply(pointWorth).setScale(0,
                    RoundingMode.DOWN).longValue();
            if (points.compareTo(maxPoints) > 0) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050088);
            }
        }
        return pointsConfig;
    }

    /***
     * 验证失效的营销信息(目前包括失效的赠品、满系活动、优惠券)
     * @param tradeCommitRequest
     * @param tradeItemGroups
     * @param storeLevelMap
     */
    @Override
    public void verifyInvalidMarketings(TradeCommitRequest tradeCommitRequest, List<TradeItemGroup> tradeItemGroups,Map<Long, CommonLevelVO> storeLevelMap) {
        String customerId = tradeCommitRequest.getCustomer().getCustomerId();

        // 1.验证赠品、满系活动
        // 1.1.从订单快照中获取下单时选择的营销、商品信息
        List<TradeMarketingDTO> tradeMarketings = tradeItemGroups.stream()
                .flatMap(group -> group.getTradeMarketingList().stream()).collect(Collectors.toList());
        List<TradeItem> tradeItems = tradeItemGroups.stream()
                .flatMap(group -> group.getTradeItems().stream()).collect(Collectors.toList());

        // 1.2.验证失效赠品、满系活动
        // 1.2.1 获得订单快照中的门店ID
        TradeItemGroup tradeItemGroup = WmCollectionUtils.findFirst(tradeItemGroups);
        String validInfo = this.verifyTradeMarketing(
                tradeMarketings, Collections.emptyList(), tradeItems,
                customerId, storeLevelMap, tradeItemGroup.getSupplier().getStoreId());

        // 1.3.将失效内容更新至request中的订单快照
        List<Long> tradeMarketingIds = tradeMarketings.stream()
                .map(TradeMarketingDTO::getMarketingId).collect(Collectors.toList());
        tradeItemGroups.stream().forEach(group -> {
            group.setTradeMarketingList(
                    group.getTradeMarketingList().stream()
                            .filter(item -> tradeMarketingIds.contains(item.getMarketingId()))
                            .collect(Collectors.toList())
            );
        });

        // 2.6.如果存在提示信息、且为非强制提交，则抛出异常
        if (StringUtils.isNotEmpty(validInfo) && !tradeCommitRequest.isForceCommit()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "很抱歉，" + validInfo);
        }

        // 2.验证优惠券
        List<String> couponCodeIds = new ArrayList<>();
        tradeCommitRequest.getStoreCommitInfoList().forEach(item -> {
            if (Objects.nonNull(item.getCouponCodeId())) {
                couponCodeIds.add(item.getCouponCodeId());
            }
        });
        if (Objects.nonNull(tradeCommitRequest.getCommonCodeId())) {
            couponCodeIds.add(tradeCommitRequest.getCommonCodeId());
        }

        if (CollectionUtils.isNotEmpty(couponCodeIds)) {
            CouponCodeValidOrderCommitRequest validOrderCommitRequest = CouponCodeValidOrderCommitRequest.builder()
                    .customerId(customerId).couponCodeIds(couponCodeIds).build();
            CouponCodeValidOrderCommitResponse validOrderCommitResponse = couponCodeQueryProvider.validOrderCommit(validOrderCommitRequest).getContext();

            // 2.5.从request对象中移除过期的优惠券
            List<String> invalidCodeIds = validOrderCommitResponse.getInvalidCodeIds();
            validInfo = validInfo + validOrderCommitResponse.getValidInfo();
            if (invalidCodeIds.contains(tradeCommitRequest.getCommonCodeId())) {
                tradeCommitRequest.setCommonCodeId(null);
            }
            tradeCommitRequest.getStoreCommitInfoList().forEach(item -> {
                if (invalidCodeIds.contains(item.getCouponCodeId())) {
                    item.setCouponCodeId(null);
                }
            });
        }

        // 2.6.如果存在提示信息、且为非强制提交，则抛出异常
        if (StringUtils.isNotEmpty(validInfo) && !tradeCommitRequest.isForceCommit()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "很抱歉，" + validInfo);
        }

    }

    /**
     * 带客下单校验customer跟supplier的关系
     *
     * @param customerId customerId
     * @param companyId  companyId
     */
    @Override
    public void verifyCustomerWithSupplier(String customerId, Long companyId) {
        StoreCustomerRelaQueryRequest request = new StoreCustomerRelaQueryRequest();
        request.setCustomerId(customerId);
        request.setCompanyInfoId(companyId);
        request.setQueryPlatform(Boolean.FALSE);

        StoreCustomerRelaResponse storeCustomerRela = storeCustomerQueryProvider.getCustomerRelated(request).getContext();

        if (Objects.isNull(storeCustomerRela)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010029);
        }
    }

    /**
     * 营销活动校验（通过抛出异常返回结果）
     */
    @Override
    public void verifyTradeMarketing(List<TradeMarketingDTO> tradeMarketingList, List<TradeItem> oldGifts, List<TradeItem> tradeItems,
                                     String customerId, boolean isFoceCommit, PluginType pluginType, Long storeId) {
        List<Long> storeIds = tradeItems.stream().map(TradeItem::getStoreId).distinct().collect(Collectors.toList());
        Map<Long, CommonLevelVO> storeLevelMap = tradeCustomerService.listCustomerLevelMapByCustomerIdAndIds(storeIds,customerId);
        String validInfo = this.verifyTradeMarketing(tradeMarketingList, oldGifts, tradeItems, customerId,storeLevelMap, storeId);
        if (StringUtils.isNotEmpty(validInfo) && !isFoceCommit) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "很抱歉，" + validInfo);
        }
    }

    /**
     * 营销活动校验（通过字符串方式返回结果）
     *
     * @param tradeMarketingList
     * @param oldGifts           旧订单赠品数据，用于编辑订单的场景，由于旧订单赠品库存已先还回但事务未提交，因此在处理中将库存做逻辑叠加
     * @param tradeItems
     * @param customerId
     */
    @Override
    public String verifyTradeMarketing(List<TradeMarketingDTO> tradeMarketingList, List<TradeItem> oldGifts,
                                       List<TradeItem> tradeItems, String customerId, Map<Long, CommonLevelVO> storeLevelMap, Long storeId) {
        //校验营销活动
        List<MarketingVO> invalidMarketings = verifyMarketing(tradeMarketingList, customerId,storeLevelMap);
        List<TradeMarketingDTO> tpMarketingList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(invalidMarketings)) {
            List<Long> invalidIds = invalidMarketings.stream().map(MarketingVO::getMarketingId).collect(Collectors.toList());
            tpMarketingList = tradeMarketingList.stream().filter(i -> invalidIds.contains(i.getMarketingId()))
                    .collect(Collectors.toList());
            tradeMarketingList.removeAll(tpMarketingList);
        }

        //校验无效赠品
        List<String> giftIds = tradeMarketingList.stream().flatMap(r -> r.getGiftSkuIds().stream()).distinct()
                .collect(Collectors.toList());
        List<GoodsInfoVO> invalidGifts = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(giftIds)) {
            GoodsInfoViewByIdsResponse gifts = getGoodsResponse(giftIds);
            //与赠品相同的商品列表
            List<TradeItem> sameItems = tradeItems.stream().filter(i -> giftIds.contains(i.getSkuId())).collect(Collectors.toList());
            invalidGifts = verifyGiftSku(giftIds, sameItems, gifts, oldGifts, storeId);
            if (!invalidGifts.isEmpty()) {
                final List<String> tpGiftList = invalidGifts.stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
                tradeMarketingList.forEach(
                        i -> {
                            List<String> ids = i.getGiftSkuIds().stream().filter(tpGiftList::contains).collect(Collectors.toList());
                            i.getGiftSkuIds().removeAll(ids);
                        }
                );
            }
        }

        String info = "";
        if (!invalidGifts.isEmpty()) {
            //无效赠品提示
            String tmp = "赠品%s";
            List<String> skuInfo = new ArrayList<>();
            invalidGifts.forEach(
                    i -> skuInfo.add(String.format(tmp, i.getGoodsInfoName() + (i.getSpecText() == null ? "" : i.getSpecText())))
            );
            info = info + StringUtils.join(skuInfo, "、") + "已失效或缺货！";
        }
        if (!tpMarketingList.isEmpty()) {
            //无效营销活动提示
            info = info + wraperInvalidMarketingInfo(tpMarketingList, invalidMarketings);
        }

        return info;
    }

    /**
     * @description 校验订单商品信息
     * @author daiyitian
     * @date 2021/5/13 16:45
     * @param request 验证入参
     * @return void
     */
    public void verifyPurchase(TradeVerifyPurchaseRequest request) {
        // 获取会员的收货地址
        CustomerDeliveryAddressVO response;
        if (StringUtils.isNotBlank(request.getAddressId())) {
            response =
                    customerDeliveryAddressQueryProvider
                            .getById(
                                    CustomerDeliveryAddressByIdRequest.builder()
                                            .deliveryAddressId(request.getAddressId())
                                            .build())
                            .getContext();
            if (Objects.isNull(response)
                    || (!request.getCustomerId().equals(response.getCustomerId()))) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        } else {
            CustomerDeliveryAddressRequest addressRequest = new CustomerDeliveryAddressRequest();
            addressRequest.setCustomerId(request.getCustomerId());
            response =
                    customerDeliveryAddressQueryProvider
                            .getDefaultOrAnyOneByCustomerId(addressRequest)
                            .getContext();
        }
        PlatformAddress address = null;
        // 有地址的情况，填充地区
        if (Objects.nonNull(response) && StringUtils.isNotBlank(response.getCustomerId())) {
            address = new PlatformAddress();
            address.setProvinceId(Objects.toString(response.getProvinceId(), StringUtils.EMPTY));
            address.setCityId(Objects.toString(response.getCityId(), StringUtils.EMPTY));
            address.setAreaId(Objects.toString(response.getAreaId(), StringUtils.EMPTY));
            address.setStreetId(Objects.toString(response.getStreetId(), StringUtils.EMPTY));
        }

        // 获取订单商品确认信息
        TradeItemSnapshot tradeItemSnapshot =
                tradeItemService.findByTerminalToken(request.getTerminalToken());

        // 提取商品，做成<skuId,购买数量>
        Map<String, Long> skuMap =
                tradeItemSnapshot.getItemGroups().stream()
                        .flatMap(i -> i.getTradeItems().stream())
                        .collect(Collectors.toMap(TradeItem::getSkuId, TradeItem::getNum));

        // 提取赠品，做成<skuId,赠送数量>
        Map<String, Long> giftMap = new HashMap<>();
        List<TradeMarketingDTO> marketingList =
                tradeItemSnapshot.getItemGroups().stream()
                        .filter(i -> CollectionUtils.isNotEmpty(i.getTradeMarketingList()))
                        .flatMap(i -> i.getTradeMarketingList().stream())
                        .filter(i -> CollectionUtils.isNotEmpty(i.getGiftSkuIds()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(marketingList)) {
            List<Long> marketingIds =
                    marketingList.stream()
                            .map(TradeMarketingDTO::getMarketingId)
                            .collect(Collectors.toList());
            List<Long> marketingLevelIds =
                    marketingList.stream()
                            .map(TradeMarketingDTO::getMarketingLevelId)
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(marketingIds)
                    && CollectionUtils.isNotEmpty(marketingLevelIds)) {
                List<MarketingFullGiftDetailVO> voList =
                        fullGiftQueryProvider
                                .listDetailByMarketingIdsAndLevelIds(
                                        FullGiftDetailListByMarketingIdsAndLevelIdsRequest.builder()
                                                .marketingIds(marketingIds)
                                                .giftLevelIds(marketingLevelIds)
                                                .build())
                                .getContext()
                                .getFullGiftDetailVOList();
                // 考虑赠品也有重复的情况
                giftMap.putAll(
                        voList.stream()
                                .collect(
                                        Collectors.toMap(
                                                MarketingFullGiftDetailVO::getProductId,
                                                MarketingFullGiftDetailVO::getProductNum,
                                                (a, b) -> a + b)));
            }
        }

        Map<String, Long> preferentialMap = new HashMap<>();
        List<TradeMarketingDTO> preferentialMarketingList =
                tradeItemSnapshot.getItemGroups().stream()
                        .filter(i -> CollectionUtils.isNotEmpty(i.getTradeMarketingList()))
                        .flatMap(i -> i.getTradeMarketingList().stream())
                        .filter(i -> CollectionUtils.isNotEmpty(i.getPreferentialSkuIds()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(preferentialMarketingList)) {
            List<Long> marketingIds =
                    preferentialMarketingList.stream()
                            .map(TradeMarketingDTO::getMarketingId)
                            .collect(Collectors.toList());
            List<Long> marketingLevelIds =
                    preferentialMarketingList.stream()
                            .map(TradeMarketingDTO::getMarketingLevelId)
                            .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(marketingIds)
                    && CollectionUtils.isNotEmpty(marketingLevelIds)) {
                List<MarketingPreferentialGoodsDetailVO> voList =
                        preferentialQueryProvider
                                .listDetailByMarketingIdsAndLevelIds(
                                        DetailByMIdsAndLIdsRequest.builder()
                                                .marketingIds(marketingIds)
                                                .levelIds(marketingLevelIds)
                                                .build())
                                .getContext()
                                .getPreferentialGoodsDetailVOS();
                // 考虑赠品也有重复的情况
                preferentialMap.putAll(
                        voList.stream()
                                .collect(
                                        Collectors.toMap(
                                                MarketingPreferentialGoodsDetailVO::getGoodsInfoId,
                                                v -> NumberUtils.LONG_ONE,
                                                Long::sum)));
            }
        }

        // 共同查询商品
        List<String> skuIds = new ArrayList<>(skuMap.keySet());
        skuIds.addAll(giftMap.keySet());
        skuIds.addAll(preferentialMap.keySet());
        GoodsInfoListByConditionRequest goodsRequest = new GoodsInfoListByConditionRequest();
        goodsRequest.setGoodsInfoIds(skuIds);
        List<GoodsInfoVO> goodsInfoList =
                goodsInfoQueryProvider.listByCondition(goodsRequest).getContext().getGoodsInfos();
        if (CollectionUtils.isEmpty(goodsInfoList)) {
            return;
        }

        // 填充购买数量，主要验证库存
        goodsInfoList.forEach(
                sku -> {
                    Long num = skuMap.getOrDefault(sku.getGoodsInfoId(), 0L);
                    num += giftMap.getOrDefault(sku.getGoodsInfoId(), 0L);
                    num += preferentialMap.getOrDefault(sku.getGoodsInfoId(), 0L);
                    sku.setBuyCount(num);
                    sku.setGoodsStatus(GoodsStatus.OK);
                });
        // 渠道接口
        thirdPlatformTradeService.verifyGoods(goodsInfoList, address);
    }

    /**
     * 获取订单商品详情,不包含区间价，会员级别价信息
     */
    private GoodsInfoViewByIdsResponse getGoodsResponse(List<String> skuIds) {
        GoodsInfoViewByIdsRequest goodsInfoRequest = GoodsInfoViewByIdsRequest.builder()
                .goodsInfoIds(skuIds)
                .isHavSpecText(Constants.yes)
                .isMarketing(Boolean.TRUE)
                .build();

        return goodsInfoQueryProvider.listViewByIds(goodsInfoRequest).getContext();
    }

    /**
     * 订单营销信息校验，返回失效的营销活动
     *
     * @param tradeMarketingList 订单营销信息
     */
    private List<MarketingVO> verifyMarketing(List<TradeMarketingDTO> tradeMarketingList, String customerId,Map<Long, CommonLevelVO> storeLevelMap) {
        if (CollectionUtils.isEmpty(tradeMarketingList)) {
            return Collections.emptyList();
        }
        // 验证营销活动是否重复
        Set<Long> set = new HashSet<>();
        Set<String> skuSet = new HashSet<>();
        for(TradeMarketingDTO tradeMarketingDTO : tradeMarketingList){
            if(!set.contains(tradeMarketingDTO.getMarketingId())) {
                set.add(tradeMarketingDTO.getMarketingId());
            }else{
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
            }
            if(CollectionUtils.isNotEmpty(tradeMarketingDTO.getSkuIds())){
                for (String skuId : tradeMarketingDTO.getSkuIds()){
                    if(!skuSet.add(skuId)) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050084);
                    }
                }
            }
        }
        //获取商户设置的营销活动信息
       /* List<Long> marketingIds = tradeMarketingList.stream().map(TradeMarketingDTO::getMarketingId).distinct()
                .collect(Collectors.toList());*/
        List<Long> marketingIds = new ArrayList<>(set);


        //请求信息根据营销活动分组
        Map<Long, List<TradeMarketingDTO>> marketingGroup = tradeMarketingList.stream().collect(Collectors.groupingBy
                (TradeMarketingDTO::getMarketingId));
        Map<Long, List<String>> skuGroup = marketingGroup.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                i -> i.getValue().stream().flatMap(m -> m.getSkuIds().stream()).collect(Collectors.toList())));
        Map<Long, Long> levelMap = new HashMap<>();
        storeLevelMap.forEach((storeId, commonLevelVO) -> levelMap.put(storeId, commonLevelVO.getLevelId()));


        MarketingScopeListInvalidMarketingRequest request = new MarketingScopeListInvalidMarketingRequest();
        request.setMarketingIds(marketingIds);
        request.setSkuGroup(skuGroup);
        request.setCustomerId(customerId);
        request.setLevelMap(levelMap);
        return marketingScopeQueryProvider.listInvalidMarketing(request).getContext().getMarketingList();
    }

    /**
     * 校验营销赠品是否有效，返回无效赠品信息
     *
     * @param giftIds           赠品id集合
     * @param sameItems         与赠品有重复的商品列表
     * @param goodsInfoResponse 赠品基础信息
     * @param oldGifts          旧订单赠品数据，用于编辑订单的场景，由于旧订单赠品库存已先还回但事务未提交，因此在处理中将库存做逻辑叠加
     * @return
     */
    protected List<GoodsInfoVO> verifyGiftSku(List<String> giftIds, List<TradeItem> sameItems,
                                            GoodsInfoViewByIdsResponse goodsInfoResponse, List<TradeItem> oldGifts, Long storeId) {
        List<GoodsInfoVO> result = new ArrayList<>();
        List<GoodsInfoVO> goodsInfos = goodsInfoResponse.getGoodsInfos();
        Map<String, GoodsVO> goodsMap = goodsInfoResponse.getGoodses().stream().collect(Collectors.toMap
                (GoodsVO::getGoodsId, Function.identity()));
        Map<String, GoodsInfoVO> goodsInfoMap = goodsInfos.stream().collect(Collectors.toMap
                (GoodsInfoVO::getGoodsInfoId, Function.identity()));
        Map<String, List<TradeItem>> sameItemMap = sameItems.stream().collect(Collectors.groupingBy(TradeItem::getSkuId));
        Map<String, Long> oldGiftMap = oldGifts.stream().collect(Collectors.toMap(TradeItem::getSkuId, TradeItem::getNum));
        giftIds.forEach(id -> {
            GoodsInfoVO goodsInfo = goodsInfoMap.get(id);
            GoodsVO goods = goodsMap.get(goodsInfo.getGoodsId());
            List<TradeItem> sameList = sameItemMap.get(id);
            Long oldNum = oldGiftMap.getOrDefault(id, 0L);
            long num = 0;
            if (sameList != null) {
                num = sameList.stream().map(TradeItem::getNum).reduce(0L, (a, b) -> a + b);
            }
            //校验赠品库存，删除，上下架状态
            if (goodsInfo.getDelFlag() == DeleteFlag.YES || goodsInfo.getAddedFlag() == 0
                    || goods.getAuditStatus() == CheckStatus.FORBADE || Objects.equals(DefaultFlag.NO.toValue(), buildGoodsInfoVendibility(goodsInfo))) {
                result.add(goodsInfo);
            }
            Long giftStock = 0L;
            //校验商品库存
            if (StringUtils.isNotBlank(goodsInfo.getProviderGoodsInfoId()) && Objects.isNull(goodsInfo.getThirdPlatformType())) {
                GoodsInfoByIdResponse providerGoodsInfo = goodsInfoQueryProvider.getById(new GoodsInfoByIdRequest(goodsInfo.getProviderGoodsInfoId()
                        , null)).getContext();
                //供应商商品，取供应商商品库存
                giftStock = providerGoodsInfo.getStock();
            } else {
                //商家商品，取商家商品库存
                giftStock = goodsInfo.getStock();
            }
            //订单商品和赠品存在重复商品，且减完订单商品数量后库存为0，优先订单商品，赠品不送
            if (giftStock + oldNum - num == 0) {
                result.add(goodsInfo);
            }
        });

        return result.stream().filter(IteratorUtils.distinctByKey(GoodsInfoVO::getGoodsInfoId)).collect(Collectors
                .toList());
    }

    /**
     * 封装并返回无效营销活动描述信息
     *
     * @param tradeMarketingList
     * @param invalidMarketings
     * @return
     */
    protected String wraperInvalidMarketingInfo(List<TradeMarketingDTO> tradeMarketingList, List<MarketingVO> invalidMarketings) {
        Map<Long, List<TradeMarketingDTO>> marketingMap = tradeMarketingList.stream().collect(
                Collectors.groupingBy(TradeMarketingDTO::getMarketingId));
        List<String> infoList = new ArrayList<>();
        invalidMarketings.forEach(
                i -> {
                    List<Long> reqLevelList = marketingMap.get(i.getMarketingId()).stream().map(TradeMarketingDTO::getMarketingLevelId)
                            .distinct().collect(Collectors.toList());
                    MarketingSubType subType = i.getSubType();
                    reqLevelList.forEach(
                            l -> {
                                String info = "";
                                DecimalFormat fm = new DecimalFormat("#.##");
                                switch (i.getMarketingType()) {
                                    case REDUCTION:
                                        Map<Long, MarketingFullReductionLevelVO> reductionMap = marketingFullReductionQueryProvider.listByMarketingId
                                                (new MarketingFullReductionByMarketingIdRequest(i.getMarketingId())).getContext().getMarketingFullReductionLevelVOList().stream().collect(Collectors.toMap(MarketingFullReductionLevelVO::
                                                getReductionLevelId, Function.identity()));

                                        MarketingFullReductionLevelVO reductionLevel = reductionMap.get(l);
                                        if (reductionLevel == null) {
                                            throw new SbcRuntimeException(OrderErrorCodeEnum.K050079);
                                        }
                                        info = "满%s减%s活动";
                                        if (subType == MarketingSubType.REDUCTION_FULL_AMOUNT) {
                                            info = String.format(info, fm.format(reductionLevel.getFullAmount()) + "元", reductionLevel.getReduction());
                                        } else if (subType == MarketingSubType.REDUCTION_FULL_COUNT) {
                                            info = String.format(info, reductionLevel.getFullCount() + "件", reductionLevel.getReduction());
                                        }
                                        break;
                                    case DISCOUNT:
                                        Map<Long, MarketingFullDiscountLevelVO> discountMap = marketingFullDiscountQueryProvider.listByMarketingId
                                                (new MarketingFullDiscountByMarketingIdRequest(i.getMarketingId())).getContext().getMarketingFullDiscountLevelVOList().stream().collect(Collectors.toMap(MarketingFullDiscountLevelVO::
                                                getDiscountLevelId, Function.identity()));
                                        MarketingFullDiscountLevelVO discountLevel = discountMap.get(l);
                                        if (discountLevel == null) {
                                            throw new SbcRuntimeException(OrderErrorCodeEnum.K050079);
                                        }
                                        info = "满%s享%s折活动";
                                        BigDecimal discount = discountLevel.getDiscount().multiply(BigDecimal.TEN)
                                                .setScale(1, RoundingMode.HALF_UP);
                                        if (subType == MarketingSubType.DISCOUNT_FULL_AMOUNT) {
                                            info = String.format(info, fm.format(discountLevel.getFullAmount()) + "元", discount);
                                        } else if (subType == MarketingSubType.DISCOUNT_FULL_COUNT) {
                                            info = String.format(info, discountLevel.getFullCount() + "件", discount);
                                        }
                                        break;
                                    case GIFT:
                                        FullGiftLevelListByMarketingIdRequest fullGiftLevelListByMarketingIdRequest = new FullGiftLevelListByMarketingIdRequest();
                                        fullGiftLevelListByMarketingIdRequest.setMarketingId(i.getMarketingId());
                                        Map<Long, MarketingFullGiftLevelVO> giftMap = fullGiftQueryProvider.listLevelByMarketingId
                                                (fullGiftLevelListByMarketingIdRequest).getContext().getFullGiftLevelVOList().stream().collect(Collectors.toMap(MarketingFullGiftLevelVO::
                                                getGiftLevelId, Function.identity()));
                                        MarketingFullGiftLevelVO giftLevel = giftMap.get(l);
                                        if (giftLevel == null) {
                                            throw new SbcRuntimeException(OrderErrorCodeEnum.K050079);
                                        }
                                        info = "满%s获赠品活动";
                                        if (subType == MarketingSubType.GIFT_FULL_AMOUNT) {
                                            info = String.format(info, fm.format(giftLevel.getFullAmount()) + "元");
                                        } else if (subType == MarketingSubType.GIFT_FULL_COUNT) {
                                            info = String.format(info, giftLevel.getFullCount() + "件");
                                        }
                                        break;
                                    case RETURN:
                                        FullReturnLevelListByMarketingIdRequest fullReturnLevelListByMarketingIdRequest = new FullReturnLevelListByMarketingIdRequest();
                                        fullReturnLevelListByMarketingIdRequest.setMarketingId(i.getMarketingId());
                                        Map<Long, MarketingFullReturnLevelVO> returnMap = fullReturnQueryProvider.listLevelByMarketingId
                                                (fullReturnLevelListByMarketingIdRequest).getContext().getFullReturnLevelVOList().stream().collect(Collectors.toMap(MarketingFullReturnLevelVO::
                                                getReturnLevelId, Function.identity()));
                                        MarketingFullReturnLevelVO returnLevel = returnMap.get(l);
                                        if (returnLevel == null) {
                                            throw new SbcRuntimeException(OrderErrorCodeEnum.K050079);
                                        }
                                        info = String.format("满%s元获赠优惠券活动", fm.format(returnLevel.getFullAmount()));
                                        break;
                                    case BUYOUT_PRICE:
                                        MarketingBuyoutPriceIdRequest request = new MarketingBuyoutPriceIdRequest();
                                        request.setMarketingId(i.getMarketingId());
                                        Map<Long, MarketingBuyoutPriceLevelVO> levelMap =
                                                marketingBuyoutPriceQueryProvider.details(request).getContext().getMarketingBuyoutPriceLevelVO().stream().collect(Collectors.toMap(MarketingBuyoutPriceLevelVO::getReductionLevelId, c -> c));
                                        MarketingBuyoutPriceLevelVO level = levelMap.get(l);
                                        if (level == null) {
                                            throw new SbcRuntimeException(OrderErrorCodeEnum.K050079);
                                        }
                                        info = "%s件%s元";
                                        info = String.format(info, level.getChoiceCount(), fm.format(level.getFullAmount()));
                                        break;
                                    case HALF_PRICE_SECOND_PIECE:
                                        MarketingGetByIdRequest marketingGetByIdRequest = new MarketingGetByIdRequest();
                                        marketingGetByIdRequest.setMarketingId(i.getMarketingId());
                                        Map<Long, MarketingHalfPriceSecondPieceLevelVO> halfPriceSecondPieceLevelMap = marketingQueryProvider.getByIdForSupplier(marketingGetByIdRequest).getContext().getMarketingForEndVO().getHalfPriceSecondPieceLevel().stream().collect(Collectors.toMap(MarketingHalfPriceSecondPieceLevelVO::getId, c -> c));
                                        MarketingHalfPriceSecondPieceLevelVO halfPriceSecondPieceLevel = halfPriceSecondPieceLevelMap.get(l);
                                        if (halfPriceSecondPieceLevel == null) {
                                            throw new SbcRuntimeException(OrderErrorCodeEnum.K050079);
                                        }
                                        if (halfPriceSecondPieceLevel.getDiscount().intValue() == 0) {
                                            info = "买" + (halfPriceSecondPieceLevel.getNumber() - 1) + "送1";
                                        } else {
                                            info = "第" + halfPriceSecondPieceLevel.getNumber() + "件" + halfPriceSecondPieceLevel.getDiscount() + "折";
                                        }
                                        break;
                                    case SUITS:
                                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050161);
                                    default:
                                        break;
                                }
                                infoList.add(info);
                            }
                    );

                });
        return infoList.isEmpty() ? "" : StringUtils.join(infoList, "、") + "已失效！";
    }

    /**
     * 校验订单开票规则
     *
     * @param invoice  订单开票信息
     * @param supplier 商家店铺信息
     */
    void verifyInvoice(Invoice invoice, Supplier supplier) {
        InvoiceProjectSwitchByCompanyInfoIdRequest request = new InvoiceProjectSwitchByCompanyInfoIdRequest();
        request.setCompanyInfoId(supplier.getSupplierId());
        BaseResponse<InvoiceProjectSwitchByCompanyInfoIdResponse> baseResponse = invoiceProjectSwitchQueryProvider.getByCompanyInfoId(request);
        InvoiceProjectSwitchByCompanyInfoIdResponse response = baseResponse.getContext();
        log.info("InvoiceProjectSwitchByCompanyInfoIdResponse===>{},invoice=========>{}",response,invoice);
        if ((response.getIsSupportInvoice().equals(DefaultFlag.NO) && !invoice.getType().equals(-1)) ||
                (response.getIsPaperInvoice().equals(DefaultFlag.NO) && invoice.getType().equals(0)) ||
                (response.getIsValueAddedTaxInvoice().equals(DefaultFlag.NO) && invoice.getType().equals(1))) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050071, new String[]{supplier.getStoreName()});
        }
    }


    /**
     * 验证商品抵扣性积分
     *
     * @param points
     * @param customerId
     */
    public boolean verifyBuyPoints(Long points, String customerId) {
        //查询会员可用积分
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(customerId))
                .getContext();
        //会员积分余额
        Long pointsAvailable = Objects.isNull(customer.getPointsAvailable()) ? Long.valueOf('0') : customer.getPointsAvailable();
        //订单使用积分超出会员可用积分
        return pointsAvailable.compareTo(points) >= 0;
    }

    /**
     * 拼凑删除会员es-提供给findOne去调
     * @param id 编号
     * @return "es_customer_detail:{id}"
     */
    private Object getCustomerDeleteIndex(String id){
        return String.format(EsConstants.DELETE_SPLIT_CHAR, EsConstants.DOC_CUSTOMER_DETAIL, id);
    }
}
