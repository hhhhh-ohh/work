package com.wanmi.sbc.order;

import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.constants.WebBaseErrorCode;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressByIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerByCustomerIdRequest;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressByIdResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.distribute.DistributionService;
import com.wanmi.sbc.goods.api.provider.distributor.goods.DistributorGoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.distributor.goods.DistributorGoodsInfoVerifyRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.RecruitApplyType;
import com.wanmi.sbc.marketing.bean.vo.DistributionSettingVO;
import com.wanmi.sbc.order.api.provider.trade.TradeItemQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.api.request.trade.TradeItemByCustomerIdRequest;
import com.wanmi.sbc.order.bean.vo.TradeItemGroupVO;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressVerifyRequest;
import com.wanmi.sbc.system.service.SystemConfigService;
import com.wanmi.sbc.util.CommonUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author edz
 * @className ValidService
 * @description TODO
 * @date 2021/8/9 16:42
 */
@Service
public class VerifyService {

    @Autowired private SystemConfigService systemConfigService;

    @Autowired private CommonUtil commonUtil;

    @Autowired private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    @Autowired private PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired private TradeItemQueryProvider tradeItemQueryProvider;

    @Autowired private DistributionService distributionService;

    @Autowired private DistributionCacheService distributionCacheService;

    @Autowired private DistributorGoodsInfoQueryProvider distributorGoodsInfoQueryProvider;

    @Autowired private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    /**
     * @description 订单校验总入口
     * @author edz
     * @date: 2021/8/9 17:04
     * @param tradeCommitRequest
     * @return void
     */
    public void check(TradeCommitRequest tradeCommitRequest,boolean isVirtual) {
        // 校验线下支付方式是否关闭
        systemConfigService.validOfflinePayType(
                tradeCommitRequest.getStoreCommitInfoList().get(0).getPayType());
        if (!isVirtual) {
            checkAddress(tradeCommitRequest);
        }
        checkDistribution(tradeCommitRequest);
    }

    /**
     * @description 收货地址校验
     * @author edz
     * @date: 2021/8/9 17:23
     * @param tradeCommitRequest
     * @return void
     */
    private void checkAddress(TradeCommitRequest tradeCommitRequest) {
        // 限售地址
        String addressId = StringUtils.EMPTY;
        // 校验是否需要完善地址信息
        Operator operator = tradeCommitRequest.getOperator();
        CustomerDeliveryAddressByIdResponse address =
                customerDeliveryAddressQueryProvider
                        .getById(
                                new CustomerDeliveryAddressByIdRequest(
                                        tradeCommitRequest.getConsigneeId()))
                        .getContext();
        if (address != null) {
            if (!operator.getUserId().equals(address.getCustomerId())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030130);
            }
            PlatformAddressVerifyRequest platformAddressVerifyRequest =
                    new PlatformAddressVerifyRequest();
            if (Objects.nonNull(address.getProvinceId())) {
                addressId += address.getProvinceId() + "|";
                platformAddressVerifyRequest.setProvinceId(String.valueOf(address.getProvinceId()));
            }
            if (Objects.nonNull(address.getCityId())) {
                addressId += address.getCityId() + "|";
                platformAddressVerifyRequest.setCityId(String.valueOf(address.getCityId()));
            }
            if (Objects.nonNull(address.getAreaId())) {
                addressId += address.getAreaId() + "|";
                platformAddressVerifyRequest.setAreaId(String.valueOf(address.getAreaId()));
            }
            if (Objects.nonNull(address.getStreetId())) {
                addressId += address.getStreetId() + "|";
                platformAddressVerifyRequest.setStreetId(String.valueOf(address.getStreetId()));
            }
            if (Boolean.TRUE.equals(
                    platformAddressQueryProvider
                            .verifyAddress(platformAddressVerifyRequest)
                            .getContext())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030129);
            }
        }
        tradeCommitRequest.setAddressId(addressId);
    }

    /**
     * @description 分销校验
     * @author  edz
     * @date: 2021/8/9 18:06
     * @param tradeCommitRequest
     * @return void
     **/
    private void checkDistribution(TradeCommitRequest tradeCommitRequest) {
        DistributeChannel distributeChannel = tradeCommitRequest.getDistributeChannel();
        DistributionSettingVO distributionSettingVO =
                distributionCacheService.queryDistributionSetting();
        DefaultFlag openFlag = distributionSettingVO.getOpenFlag();
        Boolean checkInviteeIdIsDistributor = Boolean.FALSE;
        if (Objects.nonNull(distributeChannel)
                && StringUtils.isNotEmpty(distributeChannel.getInviteeId())) {
            checkInviteeIdIsDistributor =
                    distributionService.checkIsDistributor(
                            openFlag, distributeChannel.getInviteeId());
        }
        DefaultFlag queryShopOpenFlag = distributionSettingVO.getShopOpenFlag();
        List<TradeItemGroupVO> tradeItemGroups =
                tradeItemQueryProvider
                        .listByTerminalToken(
                                TradeItemByCustomerIdRequest.builder()
                                        .terminalToken(commonUtil.getTerminalToken())
                                        .build())
                        .getContext()
                        .getTradeItemGroupList();
        DefaultFlag storeBagsFlag = tradeItemGroups.get(0).getStoreBagsFlag();
        if (DefaultFlag.NO.equals(storeBagsFlag)
                && !distributionService.checkInviteeIdEnable(
                        distributeChannel, checkInviteeIdIsDistributor, queryShopOpenFlag)) {
            // 非开店礼包情况下，判断小店状态不可用
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080181);
        }

        // 邀请人不是分销员时，清空inviteeId[不用缓存的原因是，如果用户先扫描分销的二维码进来，这里取出来的邀请人就是分销员，而不是真正的邀请人]
        Operator operator = tradeCommitRequest.getOperator();
        DistributionCustomerVO distributionCustomerVO =
                distributionCustomerQueryProvider.getByCustomerId(DistributionCustomerByCustomerIdRequest.builder().customerId(operator.getUserId()).build()).getContext().getDistributionCustomerVO();
        if (Objects.nonNull(distributionCustomerVO) && StringUtils.isNotBlank(distributionCustomerVO.getInviteCustomerIds())) {
            //目前一个用户只会有一个邀请人
            String[] invites = distributionCustomerVO.getInviteCustomerIds().split(",");
            if (invites.length > 0 && !distributionService.isDistributor(invites[0])) {
                if (Objects.nonNull(distributeChannel)){
                    distributeChannel.setInviteeId(null);
                }
            }
        }
        // 设置下单用户，是否分销员
        if (distributionService.checkIsDistributor(openFlag, operator.getUserId())) {
            tradeCommitRequest.setIsDistributor(DefaultFlag.YES);
        }
        tradeCommitRequest.setDistributeChannel(distributeChannel);
        tradeCommitRequest.setShopName(distributionSettingVO.getShopName());

        // 设置分销设置开关
        tradeCommitRequest.setOpenFlag(openFlag);
        tradeCommitRequest.getStoreCommitInfoList().forEach(item ->
                item.setStoreOpenFlag(distributionCacheService.queryStoreOpenFlag(item.getStoreId().toString()))
        );

        if (DefaultFlag.NO.equals(storeBagsFlag)) {
            if (Objects.nonNull(distributeChannel) && distributeChannel.getChannelType() == ChannelType.SHOP) {
                // 1.验证商品是否是小店商品
                List<String> skuIds = tradeItemGroups.stream().flatMap(i -> i.getTradeItems().stream())
                        .map(TradeItemVO::getSkuId).collect(Collectors.toList());
                DistributorGoodsInfoVerifyRequest verifyRequest = new DistributorGoodsInfoVerifyRequest();
                verifyRequest.setDistributorId(distributeChannel.getInviteeId());
                verifyRequest.setGoodsInfoIds(skuIds);
                List<String> invalidIds = distributorGoodsInfoQueryProvider
                        .verifyDistributorGoodsInfo(verifyRequest).getContext().getInvalidIds();
                if (CollectionUtils.isNotEmpty(invalidIds)) {
                    throw new SbcRuntimeException(GoodsErrorCodeEnum.K030151);
                }

                // 2.验证商品对应商家的分销开关有没有关闭
                tradeItemGroups.stream().flatMap(i -> i.getTradeItems().stream().peek(item -> {
                    item.setStoreId(i.getSupplier().getStoreId());
                })).forEach(item -> {
                    if (DefaultFlag.NO.equals(
                            distributionCacheService.queryStoreOpenFlag(String.valueOf(item.getStoreId())))) {
                        throw new SbcRuntimeException(GoodsErrorCodeEnum.K030151);
                    }
                });
            }
        } else {
            // 开店礼包商品校验
            RecruitApplyType applyType = distributionCacheService.queryDistributionSetting().getApplyType();
            if (RecruitApplyType.REGISTER.equals(applyType)) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030151);
            }
            TradeItemVO tradeItem = tradeItemGroups.get(0).getTradeItems().get(0);
            List<String> goodsInfoIds = distributionCacheService.queryStoreBags()
                    .stream().map(GoodsInfoVO::getGoodsInfoId).collect(Collectors.toList());
            if (!goodsInfoIds.contains(tradeItem.getSkuId())) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030151);
            }
        }
    }

}
