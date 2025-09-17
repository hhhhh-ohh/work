package com.wanmi.sbc.marketing.service;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByNameRequest;
import com.wanmi.sbc.customer.bean.vo.StoreSimpleInfo;
import com.wanmi.sbc.goods.api.provider.buycyclegoodsinfo.BuyCycleGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.request.buycyclegoodsinfo.BuyCycleGoodsInfoValidateRequest;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoValidateRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsValidateRequest;
import com.wanmi.sbc.marketing.api.provider.appointmentsalegoods.AppointmentSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bargaingoods.BargainGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsalegoods.BookingSaleGoodsQueryProvider;
import com.wanmi.sbc.marketing.api.provider.communitysku.CommunitySkuQueryProvider;
import com.wanmi.sbc.marketing.api.provider.grouponactivity.GrouponActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.market.MarketingScopeQueryProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsalegoods.AppointmentSaleGoodsValidateRequest;
import com.wanmi.sbc.marketing.api.request.bargaingoods.BargainGoodsValidateRequest;
import com.wanmi.sbc.marketing.api.request.bookingsalegoods.BookingSaleGoodsValidateRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityValidateRequest;
import com.wanmi.sbc.marketing.api.request.grouponactivity.GrouponGoodsValidateRequest;
import com.wanmi.sbc.marketing.api.request.market.MarketingScopeValidateRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.MarketingPageVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingVO;
import com.wanmi.sbc.marketing.request.MarketingMutexValidateRequest;
import com.wanmi.sbc.marketing.request.MarketingPageListRequest;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.ConfigQueryRequest;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;
import com.wanmi.sbc.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 黄昭
 * @className MarketingBaseService
 * @description TODO
 * @date 2022/2/25 17:16
 **/

@Service
public class MarketingBaseService {

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired public MarketingScopeQueryProvider marketingScopeQueryProvider;
    @Autowired public GrouponActivityQueryProvider grouponActivityQueryProvider;
    @Autowired public FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;
    @Autowired public AppointmentSaleGoodsQueryProvider appointmentSaleGoodsQueryProvider;
    @Autowired public BookingSaleGoodsQueryProvider bookingSaleGoodsQueryProvider;
    @Autowired public SystemConfigQueryProvider systemConfigQueryProvider;
    @Autowired public CommunitySkuQueryProvider communitySkuQueryProvider;
    @Autowired public BargainGoodsQueryProvider bargainGoodsQueryProvider;
    @Autowired public BuyCycleGoodsInfoQueryProvider buyCycleGoodsInfoQueryProvider;
    @Autowired public DistributorGoodsInfoQueryProvider distributorGoodsInfoQueryProvider;


    @Autowired
    private CommonUtil commonUtil;

    /**
     * 模糊查询店铺名称
     * @param marketingPageListRequest
     */
    public BaseResponse<MicroServicePage<MarketingPageVO>> getMicroServicePageBaseResponse(MarketingPageListRequest marketingPageListRequest) {
        if(StringUtils.isNotBlank(marketingPageListRequest.getStoreName())){
            List<StoreSimpleInfo> storeList = storeQueryProvider.listByStoreName(ListStoreByNameRequest.builder()
                    .storeName(marketingPageListRequest.getStoreName()).build()).getContext().getStoreSimpleInfos();
            if(CollectionUtils.isEmpty(storeList)){
                return BaseResponse.success(new MicroServicePage<MarketingPageVO>());
            }
            marketingPageListRequest.setStoreIds(storeList.stream().map(StoreSimpleInfo::getStoreId).collect(Collectors.toList()));
        }
        marketingPageListRequest.setShowStoreNameFlag(Boolean.TRUE);
        return null;
    }

    /**
     * 验证营销互斥
     * @param request
     */
    public void mutexValidate(MarketingMutexValidateRequest request) {
        ConfigQueryRequest configQueryRequest = new ConfigQueryRequest();
        configQueryRequest.setConfigType(ConfigType.MARKETING_MUTEX.toValue());
        ConfigVO configVO = systemConfigQueryProvider.findByConfigTypeAndDelFlag(configQueryRequest).getContext().getConfig();
        //营销互斥不验证标识
        if (configVO == null || NumberUtils.INTEGER_ZERO.equals(configVO.getStatus())) {
            return;
        }
        //验证分销
        DistributorGoodsInfoValidateRequest distributorReq = KsBeanUtil.copyPropertiesThird(request, DistributorGoodsInfoValidateRequest.class);
        distributorGoodsInfoQueryProvider.validate(distributorReq);
        //验证秒杀or限时购
        FlashSaleGoodsValidateRequest flashReq = KsBeanUtil.copyPropertiesThird(request, FlashSaleGoodsValidateRequest.class);
        if (Boolean.TRUE.equals(request.getFlashIdFlag())) {
            flashReq.setNotId(request.getNotSelfId());
        }
        flashSaleGoodsQueryProvider.validate(flashReq);
        //验证满系营销
        //未指定则全满系营销验证
        if (CollectionUtils.isEmpty(request.getMarketingTypes())) {
            request.setAllMarketing();
        }
        MarketingScopeValidateRequest marketingReq = KsBeanUtil.copyPropertiesThird(request, MarketingScopeValidateRequest.class);
        if (Boolean.TRUE.equals(request.getMarketingIdFlag())) {
            marketingReq.setNotId(Long.valueOf(request.getNotSelfId()));
        }
        marketingScopeQueryProvider.validate(marketingReq);
        //验证预约营销
        AppointmentSaleGoodsValidateRequest appointmentReq = KsBeanUtil.copyPropertiesThird(request, AppointmentSaleGoodsValidateRequest.class);
        if (Boolean.TRUE.equals(request.getAppointmentIdFlag())) {
            appointmentReq.setNotId(Long.valueOf(request.getNotSelfId()));
        }
        appointmentSaleGoodsQueryProvider.validate(appointmentReq);
        //验证预售营销
        BookingSaleGoodsValidateRequest bookingReq = KsBeanUtil.copyPropertiesThird(request, BookingSaleGoodsValidateRequest.class);
        if (Boolean.TRUE.equals(request.getBookingIdFlag())) {
            bookingReq.setNotId(Long.valueOf(request.getNotSelfId()));
        }
        bookingSaleGoodsQueryProvider.validate(bookingReq);
        //验证拼团营销
        GrouponGoodsValidateRequest grouponReq = KsBeanUtil.copyPropertiesThird(request, GrouponGoodsValidateRequest.class);
        if (Boolean.TRUE.equals(request.getGrouponIdFlag())) {
            grouponReq.setNotId(request.getNotSelfId());
        }
        grouponActivityQueryProvider.validate(grouponReq);
        //验证社区团购
        CommunityActivityValidateRequest communityReq =  KsBeanUtil.copyPropertiesThird(request, CommunityActivityValidateRequest.class);
        if(Boolean.TRUE.equals(request.getCommunityIdFlag())) {
            communityReq.setNotId(request.getNotSelfId());
        }
        communitySkuQueryProvider.validate(communityReq);
        //验证砍价
        BargainGoodsValidateRequest bargainGoodsReq = KsBeanUtil.copyPropertiesThird(request, BargainGoodsValidateRequest.class);
        if(Boolean.TRUE.equals(request.getBargainIdFlag())) {
            bargainGoodsReq.setNotId(Long.valueOf(request.getNotSelfId()));
        }
        bargainGoodsQueryProvider.validate(bargainGoodsReq);
        //验证周期购，没有全编辑功能
        BuyCycleGoodsInfoValidateRequest buyCycleReq = KsBeanUtil.copyPropertiesThird(request, BuyCycleGoodsInfoValidateRequest.class);
        buyCycleGoodsInfoQueryProvider.validate(buyCycleReq);
    }

    /**
     * 新增时验证
     * @param storeId 店铺id
     * @param crossBeginTime 开始时间
     * @param crossEndTime 结束时间
     * @param skuIds 商品ids
     */
    public void mutexValidateByAdd(Long storeId, LocalDateTime crossBeginTime, LocalDateTime crossEndTime, List<String> skuIds){
        MarketingMutexValidateRequest validateRequest = new MarketingMutexValidateRequest();
        validateRequest.setStoreId(storeId);
        validateRequest.setCrossBeginTime(crossBeginTime);
        validateRequest.setCrossEndTime(crossEndTime);
        validateRequest.setSkuIds(skuIds);
        this.mutexValidate(validateRequest);
    }

    /**
     * 满系通用互斥
     * @param marketingType 营销类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param scopeType 范围
     * @param scopeIds 范围值
     * @param notId 当前id
     */
    public void mutexValidate(MarketingType marketingType, LocalDateTime startTime, LocalDateTime endTime,
                              MarketingScopeType scopeType, List<String> scopeIds, Long storeId, Long notId) {
        MarketingMutexValidateRequest validateRequest = new MarketingMutexValidateRequest();
        validateRequest.setCrossBeginTime(startTime);
        validateRequest.setCrossEndTime(endTime);
        validateRequest.setStoreId(storeId);
        // 满减 ->  满系验证：满减、满折、满赠、一口价、第二件半价
        if (MarketingType.REDUCTION.equals(marketingType)) {
            validateRequest.setMarketingTypes(Arrays.asList(MarketingType.REDUCTION, MarketingType.DISCOUNT, MarketingType.GIFT, MarketingType.BUYOUT_PRICE, MarketingType.HALF_PRICE_SECOND_PIECE));
        } else if (MarketingType.DISCOUNT.equals(marketingType)) {
            // 满折 ->  满系验证：满减、满折、满赠、一口价、第二件半价
            validateRequest.setMarketingTypes(Arrays.asList(MarketingType.REDUCTION, MarketingType.DISCOUNT, MarketingType.GIFT, MarketingType.BUYOUT_PRICE, MarketingType.HALF_PRICE_SECOND_PIECE));
        } else if (MarketingType.GIFT.equals(marketingType)) {
            // 满赠 ->  满系验证：满减、满折、满赠、一口价、第二件半价
            validateRequest.setMarketingTypes(Arrays.asList(MarketingType.REDUCTION, MarketingType.DISCOUNT, MarketingType.GIFT, MarketingType.BUYOUT_PRICE, MarketingType.HALF_PRICE_SECOND_PIECE));
        } else if (MarketingType.PREFERENTIAL.equals(marketingType)) {
            // 加价购 -> 验证：加价购、一口价、第二件半价
            validateRequest.setMarketingTypes(Arrays.asList(MarketingType.PREFERENTIAL, MarketingType.BUYOUT_PRICE, MarketingType.HALF_PRICE_SECOND_PIECE));
        }
        // 设定满系范围
        if (MarketingScopeType.SCOPE_TYPE_ALL.equals(scopeType)) {
            validateRequest.setAllFlag(Boolean.TRUE);
        } else if (MarketingScopeType.SCOPE_TYPE_STORE_CATE.equals(scopeType)) {
            // 店铺分类 -> 只验证 所有商品、店铺分类、自定义
            validateRequest.setScopeTypes(Arrays.asList(MarketingScopeType.SCOPE_TYPE_ALL,
                    MarketingScopeType.SCOPE_TYPE_STORE_CATE, MarketingScopeType.SCOPE_TYPE_CUSTOM));
            validateRequest.setStoreCateIds(scopeIds.stream().map(NumberUtils::toLong).collect(Collectors.toList()));
        } else if (MarketingScopeType.SCOPE_TYPE_BRAND.equals(scopeType)) {
            // 品牌 -> 只验证 所有商品、品牌、自定义
            validateRequest.setScopeTypes(Arrays.asList(MarketingScopeType.SCOPE_TYPE_ALL,
                    MarketingScopeType.SCOPE_TYPE_BRAND, MarketingScopeType.SCOPE_TYPE_CUSTOM));
            validateRequest.setBrandIds(scopeIds.stream().map(NumberUtils::toLong).collect(Collectors.toList()));
        } else {
            validateRequest.setSkuIds(scopeIds);
        }
        if(Objects.nonNull(notId)) {
            validateRequest.setNotSelfId(String.valueOf(notId));
            validateRequest.setMarketingIdFlag(true);
        }
        this.mutexValidate(validateRequest);
    }
    /**
     * 校验登录人权限
     * @param marketing
     */
    public void checkPlatForm(MarketingVO marketing) {
        if (Objects.equals(Platform.PLATFORM,commonUtil.getOperator().getPlatform())
                && Objects.equals(BoolFlag.NO, marketing.getIsBoss())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }

        if (Objects.equals(Platform.SUPPLIER,commonUtil.getOperator().getPlatform())
                && !Objects.equals(commonUtil.getStoreId(), marketing.getStoreId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
        }
    }

}