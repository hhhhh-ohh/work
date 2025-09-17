package com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.constant.RedisKeyConstant;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DeflaterUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreCustomerRelaListByConditionRequest;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerRelaVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.TradeConfirmGoodsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.api.response.info.TradeConfirmGoodsResponse;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.GoodsPriceType;
import com.wanmi.sbc.goods.bean.vo.AppointmentSaleVO;
import com.wanmi.sbc.goods.bean.vo.BookingSaleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.marketing.api.provider.appointmentsale.AppointmentSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.bookingsale.BookingSaleQueryProvider;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionSettingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.newplugin.NewMarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.appointmentsale.AppointmentSaleInProgressRequest;
import com.wanmi.sbc.marketing.api.request.bookingsale.BookingSaleInProgressRequest;
import com.wanmi.sbc.marketing.api.request.distribution.DistributionStoreSettingGetByStoreIdRequest;
import com.wanmi.sbc.marketing.api.request.newplugin.GoodsListPluginRequest;
import com.wanmi.sbc.marketing.api.response.appointmentsale.AppointmentSaleInProcessResponse;
import com.wanmi.sbc.marketing.api.response.bookingsale.BookingSaleListResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionSettingGetResponse;
import com.wanmi.sbc.marketing.api.response.distribution.DistributionStoreSettingGetByStoreIdResponse;
import com.wanmi.sbc.marketing.api.response.newplugin.GoodsTradePluginResponse;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.optimization.trade1.snapshot.ParamsDataVO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.QueryDataInterface;
import com.wanmi.sbc.setting.api.provider.SystemPointsConfigQueryProvider;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author edz
 * @className QueryDataService
 * @description TODO
 * @date 2022/2/25 11:35
 */
@Service
@Slf4j
public class QueryDataService implements QueryDataInterface {

    @Autowired private CustomerQueryProvider customerQueryProvider;

    @Autowired private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired private StoreQueryProvider storeQueryProvider;

    @Autowired private AppointmentSaleQueryProvider appointmentSaleQueryProvider;

    @Autowired private BookingSaleQueryProvider bookingSaleQueryProvider;

    @Autowired private StoreCustomerQueryProvider storeCustomerQueryProvider;

    @Autowired private RedisUtil redisService;

    @Autowired private DistributionSettingQueryProvider distributionSettingQueryProvider;

    @Autowired private SystemPointsConfigQueryProvider systemPointsConfigQueryProvider;

    @Autowired private NewMarketingPluginProvider newMarketingPluginProvider;

    @Autowired private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    @Override
    public GoodsInfoResponse getGoodsInfo(TradeConfirmGoodsRequest goodsRequest) {
        TradeConfirmGoodsResponse response =
                goodsInfoQueryProvider.tradeConfirmGoodsInfo(goodsRequest).getContext();
        List<GoodsInfoVO> goodsInfos = response.getGoodsInfos();
        goodsInfos.forEach(
                goodsInfoVO -> {
                    if (DeleteFlag.YES.equals(goodsInfoVO.getDelFlag())) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050118);
                    } else if (AddedFlag.NO.equals(
                            AddedFlag.fromValue(goodsInfoVO.getAddedFlag()))) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050119);
                    } else if (Objects.equals(
                            DefaultFlag.NO.toValue(), buildGoodsInfoVendibility(goodsInfoVO))) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050120);
                    } else if (!CheckStatus.CHECKED.equals(goodsInfoVO.getAuditStatus())) {
                        throw new SbcRuntimeException(OrderErrorCodeEnum.K050121);
                    }
                });
        List<GoodsVO> goodsVOS = response.getGoodses();
        Map<String, GoodsVO> goodsIdGoodsMap =
                goodsVOS.stream()
                        .collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));
        Map<String, List<GoodsInfoSpecDetailRelVO>> specDetailMap =
                response.getGoodsInfoSpecDetailRelVOS().stream()
                        .collect(Collectors.groupingBy(GoodsInfoSpecDetailRelVO::getGoodsInfoId));
        response.getGoodsInfos()
                .forEach(
                        goodsInfoVO -> {
                            GoodsVO goodsVO = goodsIdGoodsMap.get(goodsInfoVO.getGoodsId());
                            goodsInfoVO.setPriceType(goodsVO.getPriceType());
                            goodsInfoVO.setSpecText(
                                    StringUtils.join(
                                            specDetailMap
                                                    .getOrDefault(
                                                            goodsInfoVO.getGoodsInfoId(),
                                                            new ArrayList<>())
                                                    .stream()
                                                    .map(GoodsInfoSpecDetailRelVO::getDetailName)
                                                    .collect(Collectors.toList()),
                                            " "));
                        });
        return GoodsInfoResponse.builder()
                .goodsInfos(response.getGoodsInfos())
                .goodses(response.getGoodses())
                .goodsIntervalPrices(response.getGoodsIntervalPriceVOList())
                .build();
    }

    private Integer buildGoodsInfoVendibility(GoodsInfoVO goodsInfo) {
        Integer vendibility = Constants.yes;
        String providerGoodsInfoId = goodsInfo.getProviderGoodsInfoId();
        if (StringUtils.isNotBlank(providerGoodsInfoId)) {
            if (Constants.no.equals(goodsInfo.getVendibility())) {
                vendibility = Constants.no;
            }
        }
        return vendibility;
    }

    @Override
    public StoreVO getStoreInfo(long storeId, String customerId) {
        StoreVO storeVO =
                storeQueryProvider
                        .getById(StoreByIdRequest.builder().storeId(storeId).build())
                        .getContext()
                        .getStoreVO();
        if (storeVO.getContractEndDate().isBefore(LocalDateTime.now())) {
            log.error("会员ID：{}，订单异常：店铺签约到期", customerId);
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050130);
        } else if (!CheckState.CHECKED.equals(storeVO.getAuditState())) {
            log.error("会员ID：{}，订单异常：店铺未审核", customerId);
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050130);
        } else if (StoreState.CLOSED.equals(storeVO.getStoreState())) {
            log.error("会员ID：{}，订单异常：店铺关店", customerId);
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050130);
        } else if (DeleteFlag.YES.equals(storeVO.getDelFlag())) {
            log.error("会员ID：{}，订单异常：店铺删除", customerId);
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050130);
        }
        return storeVO;
    }

    @Override
    public CustomerVO getCustomerInfo(String customerId) {
        CustomerGetByIdResponse customerGetByIdResponse =
                customerQueryProvider
                        .getCustomerInfoById(
                                CustomerGetByIdRequest.builder().customerId(customerId).build())
                        .getContext();
        // 会员账号被删除
        if (DeleteFlag.YES.equals(customerGetByIdResponse.getDelFlag())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050001);
        } else if (CheckState.WAIT_CHECK.equals(customerGetByIdResponse.getCheckState())) {
            // 会员账号在审核阶段
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050002);
        } else if (CustomerStatus.DISABLE.equals(
                customerGetByIdResponse.getCustomerDetail().getCustomerStatus())) {
            // 会员账号被禁用
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050003);
        }
        return customerGetByIdResponse;
    }

    public List<AppointmentSaleVO> getAppointmentSaleRelaInfo(
            List<TradeItemRequest> tradeItemRequests) {
        List<String> appointmentSaleSkuIds =
                tradeItemRequests.stream()
                        .filter(
                                i ->
                                        Objects.nonNull(i.getIsAppointmentSaleGoods())
                                                && i.getIsAppointmentSaleGoods())
                        .map(TradeItemRequest::getSkuId)
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(appointmentSaleSkuIds)) {
            List<String> allSkuIds =
                    tradeItemRequests.stream()
                            .filter(i -> Objects.isNull(i.getBuyPoint()) || i.getBuyPoint() == 0)
                            .map(TradeItemRequest::getSkuId)
                            .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(allSkuIds)) {
                return null;
            }
            AppointmentSaleInProcessResponse response =
                    appointmentSaleQueryProvider
                            .inProgressAppointmentSaleInfoByGoodsInfoIdList(
                                    AppointmentSaleInProgressRequest.builder()
                                            .goodsInfoIdList(allSkuIds)
                                            .build())
                            .getContext();
            int purchaseNum = appointmentSaleSkuIds.size();
            int actualNum = 0;
            if (Objects.nonNull(response)
                    && CollectionUtils.isNotEmpty(response.getAppointmentSaleVOList())) {
                actualNum = response.getAppointmentSaleVOList().size();
            } else {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050125);
            }

            // 包含预约中商品, 校验不通过
            if (actualNum > purchaseNum) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050126);
            }

            // 预约活动失效
            if (purchaseNum > actualNum) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050125);
            }
            return response.getAppointmentSaleVOList();
        }
        return null;
    }

    public List<BookingSaleVO> getBookingSaleVOList(List<String> goodsInfoIds) {
        BookingSaleListResponse bookingSaleListResponse =
                bookingSaleQueryProvider
                        .inProgressBookingSaleInfoByGoodsInfoIdList(
                                BookingSaleInProgressRequest.builder()
                                        .goodsInfoIdList(goodsInfoIds)
                                        .build())
                        .getContext();
        if (bookingSaleListResponse.getBookingSaleVOList().size() != goodsInfoIds.size()) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050128);
        }
        return bookingSaleListResponse.getBookingSaleVOList();
    }

    public List<StoreCustomerRelaVO> listByCondition(String customerId, Long storeId) {
        StoreCustomerRelaListByConditionRequest listByConditionRequest =
                new StoreCustomerRelaListByConditionRequest();
        listByConditionRequest.setCustomerId(customerId);
        listByConditionRequest.setStoreId(storeId);
        return storeCustomerQueryProvider
                .listByCondition(listByConditionRequest)
                .getContext()
                .getRelaVOList();
    }

    public DistributionSettingGetResponse querySettingCache() {
//        boolean hasKey = redisService.hasKey(RedisKeyConstant.DIS_SETTING);
        String val = redisService.getString(RedisKeyConstant.DIS_SETTING);
        if (StringUtils.isNotBlank(val)) {
            return JSONObject.parseObject(
                    DeflaterUtil.unzipString(val),
                    DistributionSettingGetResponse.class);
        } else {
            DistributionSettingGetResponse setting =
                    distributionSettingQueryProvider.getSetting().getContext();
            // 压缩存入redis
            String value = DeflaterUtil.zipString(JSONObject.toJSONString(setting));
            if (!Constants.FAIL.equals(value)) {
                redisService.setString(RedisKeyConstant.DIS_SETTING, value);
            }
            return setting;
        }
    }

    public DistributionStoreSettingGetByStoreIdResponse queryStoreSettingCache(String storeId) {
        String key = "DIS_STORE_SETTING:" + storeId;
//        boolean hasKey = redisService.hasKey(key);
        String val = redisService.getString(key);
        if (StringUtils.isNotBlank(val)) {
            return JSONObject.parseObject(
                    val,
                    DistributionStoreSettingGetByStoreIdResponse.class);
        } else {
            DistributionStoreSettingGetByStoreIdResponse setting =
                    distributionSettingQueryProvider
                            .getStoreSettingByStoreId(
                                    new DistributionStoreSettingGetByStoreIdRequest(storeId))
                            .getContext();
            redisService.setString(key, JSONObject.toJSONString(setting));
            return setting;
        }
    }

    public SystemPointsConfigQueryResponse querySystemPointsConfig() {
        return systemPointsConfigQueryProvider.querySystemPointsConfig().getContext();
    }

    public void getGoodsMarketing(ParamsDataVO paramsDataVO) {
        // 分销开关判断
        DistributionSettingGetResponse distributionSettingGetResponse =
                paramsDataVO.getDistributionSettingGetResponse();
        DistributionStoreSettingGetByStoreIdResponse distributionStoreSettingGetByStoreIdResponse =
                paramsDataVO.getDistributionStoreSettingGetByStoreIdResponse();
        boolean distributionFlag =
                DefaultFlag.YES.equals(
                                distributionSettingGetResponse
                                        .getDistributionSetting()
                                        .getOpenFlag())
                        && DefaultFlag.YES.equals(
                                distributionStoreSettingGetByStoreIdResponse.getOpenFlag());

        List<GoodsInfoVO> goodsInfoVOS = paramsDataVO.getGoodsInfoResponse().getGoodsInfos();

        // 积分开关判断
        SystemPointsConfigQueryResponse systemPointsConfigQueryResponse =
                paramsDataVO.getSystemPointsConfigQueryResponse();
        boolean pointFlag =
                EnableStatus.ENABLE.equals(systemPointsConfigQueryResponse.getStatus())
                        && PointsUsageFlag.GOODS.equals(
                                systemPointsConfigQueryResponse.getPointsUsageFlag());

        for (GoodsInfoVO goodsInfoVO : goodsInfoVOS) {
            if (!distributionFlag) {
                goodsInfoVO.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                goodsInfoVO.setDistributionCommission(new BigDecimal("0"));
            }

            if (!pointFlag) {
                goodsInfoVO.setBuyPoint(0L);
            }

//            if (!paramsDataVO.getRequest().getIepCustomerFlag()) {
//                goodsInfoVO.setEnterPriseAuditState(EnterpriseAuditState.INIT);
//            }
        }

        GoodsListPluginRequest goodsListPluginRequest = new GoodsListPluginRequest();
        // 预处理客户阶梯价，避免出现营销插件中错误使用市场价导致粉丝价出错问题
        WmCollectionUtils.notEmpty2Loop(goodsInfoVOS, goodsInfo -> {
            // 订货区间设价
            if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goodsInfo.getPriceType())) {
                if (Objects.nonNull(paramsDataVO.getGoodsInfoResponse())
                        && WmCollectionUtils.isNotEmpty(paramsDataVO.getGoodsInfoResponse().getGoodsIntervalPrices())
                        && Objects.nonNull(paramsDataVO.getRequest())
                        && WmCollectionUtils.isNotEmpty(paramsDataVO.getRequest().getTradeItemRequests())) {
                    // 映射Map
                    Map<String, Long> skuNumMap = WmCollectionUtils.notEmpty2Map(paramsDataVO.getRequest().getTradeItemRequests(),
                            TradeItemRequest::getSkuId, TradeItemRequest::getNum);

                    Optional<GoodsIntervalPriceVO> first =
                            paramsDataVO.getGoodsInfoResponse().getGoodsIntervalPrices().stream()
                                    .filter(item -> skuNumMap.containsKey(item.getGoodsInfoId()))
                                    .filter(item -> item.getGoodsInfoId().equals(goodsInfo.getGoodsInfoId()))
                                    .filter(item -> skuNumMap.get(item.getGoodsInfoId()) >= item.getCount())
                                    .max(Comparator.comparingLong(GoodsIntervalPriceVO::getCount));

                    if (first.isPresent()) {
                        GoodsIntervalPriceVO goodsIntervalPrice = first.get();
                        if (Objects.nonNull(goodsInfo.getSalePrice())) {
                            goodsInfo.setSalePrice(goodsIntervalPrice.getPrice());
                        }
                        goodsInfo.setMarketPrice(goodsIntervalPrice.getPrice());
                    }
                }
            }
        });
        // 预处理客户阶梯价结束

        List<GoodsInfoSimpleVO> goodsInfoSimpleVOS = KsBeanUtil.convert(goodsInfoVOS, GoodsInfoSimpleVO.class);
        goodsListPluginRequest.setGoodsInfoPluginRequests(goodsInfoSimpleVOS);
        goodsListPluginRequest.setCustomerId(paramsDataVO.getCustomerVO().getCustomerId());
        if (paramsDataVO.getCustomerVO().getEnterpriseCheckState() != null
                && paramsDataVO.getCustomerVO().getEnterpriseCheckState()
                .equals(EnterpriseCheckState.CHECKED)) {
            goodsListPluginRequest.setIepCustomerFlag(true);
        }
        // 获取商品营销
        GoodsTradePluginResponse response =
                newMarketingPluginProvider.immediateBuyPlugin(goodsListPluginRequest).getContext();
        paramsDataVO.setGoodsTradePluginResponse(response);
    }

    @Override
    public CustomerDeliveryAddressResponse getDefaultAddress(ParamsDataVO paramsDataVO) {
        CustomerDeliveryAddressRequest queryRequest = new CustomerDeliveryAddressRequest();
        queryRequest.setCustomerId(paramsDataVO.getRequest().getCustomerId());
        BaseResponse<CustomerDeliveryAddressResponse> customerDeliveryAddressResponseBaseResponse =
                customerDeliveryAddressQueryProvider.getDefaultOrAnyOneByCustomerId(queryRequest);
        CustomerDeliveryAddressResponse customerDeliveryAddressResponse =
                customerDeliveryAddressResponseBaseResponse.getContext();
        if (Objects.isNull(customerDeliveryAddressResponse)) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050131);
        } else {
            return customerDeliveryAddressResponse;
        }
    }
}
