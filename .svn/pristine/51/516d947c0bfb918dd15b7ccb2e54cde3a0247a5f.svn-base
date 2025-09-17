package com.wanmi.sbc.distribute;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.DistributeChannel;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DistributionCommissionUtils;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributorLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerPointsAvailableByIdRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerCheckRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerEnableByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerListForOrderCommitRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributorLevelByCustomerIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailGetCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributionCustomerEnableByCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributorLevelByCustomerIdResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CommissionUnhookType;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerSimVO;
import com.wanmi.sbc.customer.bean.vo.DistributorLevelVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdsResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionCacheQueryProvider;
import com.wanmi.sbc.marketing.api.response.distribution.MultistageSettingGetResponse;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.vo.DistributionSettingVO;
import com.wanmi.sbc.order.api.request.trade.TradeCommitRequest;
import com.wanmi.sbc.order.api.response.trade.TradeConfirmResponse;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.order.request.GetCommissionTradeParams;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.wanmi.sbc.setting.bean.enums.PointsUsageFlag;
import com.wanmi.sbc.system.service.SystemPointsConfigService;
import com.wanmi.sbc.util.CommonUtil;

import jakarta.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 分销业务逻辑
 */
@Slf4j
@Service
public class DistributionService {


    @Autowired
    private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Autowired
    private DistributionCacheService distributionCacheService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private DistributorLevelQueryProvider distributorLevelQueryProvider;

    @Autowired
    private DistributionCacheQueryProvider distributionCacheQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;


    @Resource
    private GoodsIntervalPriceService goodsIntervalPriceService;

    @Autowired
    private SystemPointsConfigService systemPointsConfigService;

    /**
     * 根据开关重新设置分销商品标识
     * @param goodsInfoList
     */
    public void checkDistributionSwitch(List<GoodsInfoVO> goodsInfoList) {
        //需要叠加访问端Pc\app不体现分销业务
        DefaultFlag openFlag = distributionCacheService.queryOpenFlag();
        goodsInfoList.forEach(goodsInfoVO -> {
            Boolean distributionFlag = Objects.equals(ChannelType.PC_MALL, commonUtil.getDistributeChannel().getChannelType()) || DefaultFlag.NO.equals(openFlag) || DefaultFlag.NO.equals(distributionCacheService.queryStoreOpenFlag(String.valueOf(goodsInfoVO.getStoreId())));
            boolean pcDistributionFlag = Objects.equals(ChannelType.PC_MALL,
                    commonUtil.getDistributeChannel().getChannelType()) && DefaultFlag.YES.equals(openFlag)
                    && DefaultFlag.YES.equals(distributionCacheService.queryStoreOpenFlag(String.valueOf(goodsInfoVO.getStoreId())))
                    && DistributionGoodsAudit.CHECKED.equals(goodsInfoVO.getDistributionGoodsAudit());
            if (pcDistributionFlag){
                goodsInfoVO.setMarketingLabels(new ArrayList<>());
            }
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

    /**
     * 社交分销
     * shop渠道 店铺状态验证
     * @return
     */
    public boolean checkInviteeIdEnable() {
        boolean enable = true;
        //获取分销类型 和 邀请人Id
        DistributeChannel channel = commonUtil.getDistributeChannel();
        //如果是小店模式
        if (null != channel && Objects.equals(channel.getChannelType(), ChannelType.SHOP)) {
            String inviteeId = channel.getInviteeId();
            DistributionSettingVO distributionSettingVO = distributionCacheService.queryDistributionSetting();
            //查询当前是否开启了小店模式
            DefaultFlag queryShopOpenFlag = distributionSettingVO.getShopOpenFlag();
            DefaultFlag openFlag = distributionSettingVO.getOpenFlag();

            if (!(DefaultFlag.YES.equals(queryShopOpenFlag) && this.checkIsDistributor(openFlag,inviteeId))) {
                enable = false;
            }
        }
        return enable;
    }

    /**
     * 社交分销 ： shop渠道 店铺状态验证
     * @param channel
     * @param checkInviteeIdIsDistributor
     * @param queryShopOpenFlag
     * @return
     */
    public boolean checkInviteeIdEnable(DistributeChannel channel,Boolean checkInviteeIdIsDistributor,DefaultFlag queryShopOpenFlag ) {
        boolean enable = true;
        //如果是小店模式
        if (Objects.nonNull(channel) && Objects.equals(channel.getChannelType(), ChannelType.SHOP)
                && !(DefaultFlag.YES.equals(queryShopOpenFlag) && checkInviteeIdIsDistributor)) {
            enable = false;
        }
        return enable;
    }

    /**
     * 是否是分销员
     * @return
     */
    public boolean isDistributor(String customerId) {
        boolean enable = false;
        //查询会员基础表信息
        CustomerGetByIdRequest customerGetByIdRequest = new CustomerGetByIdRequest();
        customerGetByIdRequest.setCustomerId(customerId);
        BaseResponse<CustomerGetByIdResponse> customerGetByIdResponse = customerQueryProvider.getCustomerInfoById(customerGetByIdRequest);
        //如果会员基础信息是null 或者不是审核通过状态
        if (Objects.isNull(customerGetByIdResponse.getContext()) || !CheckState.CHECKED.equals(customerGetByIdResponse.getContext().getCheckState())) {
            return false;
        }
        //查询会员detail表
        BaseResponse<CustomerDetailGetCustomerIdResponse> customerDetailGetCustomerIdResponseBaseResponse =
                customerDetailQueryProvider.getCustomerDetailByCustomerId(
                        CustomerDetailByCustomerIdRequest.builder().customerId(customerId).build());
        //如果会员detail表信息是null 或者不是启用状态
        if (Objects.isNull(customerDetailGetCustomerIdResponseBaseResponse.getContext()) ||
                !customerDetailGetCustomerIdResponseBaseResponse.getContext().getCustomerStatus().equals(CustomerStatus.ENABLE)) {
            return false;
        }

        //查询是否开启社交分销
        DefaultFlag openFlag = distributionCacheService.queryOpenFlag();
        //验证分销员状态
        DistributionCustomerEnableByCustomerIdResponse distributionCustomerEnableByIdResponse = distributionCustomerQueryProvider.checkEnableByCustomerId(
                new DistributionCustomerEnableByCustomerIdRequest(customerId)).getContext();
        if (DefaultFlag.YES.equals(openFlag) && Objects.nonNull(distributionCustomerEnableByIdResponse) && distributionCustomerEnableByIdResponse.getDistributionEnable()) {
            enable = true;
        }
        return enable;
    }

    /**
     * 是否分销员
     * @param customerId
     * @return
     */
    public Boolean checkIsDistributor(DefaultFlag openFlag ,String customerId) {
        return distributionCustomerQueryProvider.checkIsDistributor(new DistributionCustomerCheckRequest(customerId,openFlag)).getContext().getEnable();
    }



    /**
     * 根据会员ID查询分销员等级信息
     * @param customerId
     * @return
     */
    public BaseResponse<DistributorLevelByCustomerIdResponse> getByCustomerId(String customerId) {
        return distributorLevelQueryProvider.getByCustomerId(new DistributorLevelByCustomerIdRequest(customerId));
    }

    /**
     * 计算分销分销佣金
     * @param goodsInfoCommission 商品佣金
     * @param commissionRate      佣金比例-分销员等级
     * @return
     */
    public BigDecimal calDistributionCommission(BigDecimal goodsInfoCommission, BigDecimal commissionRate) {
        return DistributionCommissionUtils.calDistributionCommission(goodsInfoCommission, commissionRate);
    }


    /**
     * 处理分销
     * @param tradeItemGroupVOS
     * @param tradeConfirmResponse
     * @return
     */
    public BigDecimal dealDistribution(List<TradeItemGroupVO> tradeItemGroupVOS, TradeConfirmResponse tradeConfirmResponse){
        BigDecimal totalCommission = BigDecimal.ZERO;
        Operator operator = commonUtil.getOperator();
        if (this.isDistributor(operator.getUserId())) {
            tradeConfirmResponse.setIsDistributor(DefaultFlag.YES);
        }
        if(CollectionUtils.isNotEmpty(tradeItemGroupVOS)){
            for(TradeItemGroupVO tradeItemGroupVO : tradeItemGroupVOS){
                if(DefaultFlag.NO.equals(tradeItemGroupVO.getStoreBagsFlag())){
                    DistributeChannel channel = commonUtil.getDistributeChannel();
                    tradeItemGroupVO.getTradeItems().forEach(item -> {
                        DefaultFlag storeOpenFlag = distributionCacheService.queryStoreOpenFlag(item.getStoreId().toString());
                        if (DistributionGoodsAudit.CHECKED == item.getDistributionGoodsAudit()
                                && DefaultFlag.YES.equals(storeOpenFlag)
                                && ChannelType.PC_MALL != channel.getChannelType()) {
                            // 初步计算分销佣金
                            item.setDistributionCommission(item.getSplitPrice().multiply(item.getCommissionRate()));
                            item.setPrice(item.getOriginalPrice());
                            item.setLevelPrice(item.getOriginalPrice());

                        } else {
                            item.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                        }
                    });

                    List<TradeItemVO> distributionTradeItems = tradeItemGroupVO.getTradeItems().stream()
                            .filter(item -> DistributionGoodsAudit.CHECKED.equals(item.getDistributionGoodsAudit())).collect(Collectors.toList());

                    // 2.设置分销相关字段
                    if (distributionTradeItems.size() != 0) {

                        MultistageSettingGetResponse multistageSetting =
                                distributionCacheQueryProvider.getMultistageSetting().getContext();

                        // 2.1.查询佣金受益人列表
                        DistributionCustomerListForOrderCommitRequest request =
                                new DistributionCustomerListForOrderCommitRequest();
                        request.setBuyerId(commonUtil.getOperatorId());
                        request.setCommissionPriorityType(
                                com.wanmi.sbc.customer.bean.enums.CommissionPriorityType.fromValue(multistageSetting.getCommissionPriorityType().toValue())
                        );
                        request.setCommissionUnhookType(
                                CommissionUnhookType.fromValue(multistageSetting.getCommissionUnhookType().toValue())
                        );
                        request.setDistributorLevels(multistageSetting.getDistributorLevels());
                        request.setInviteeId(channel.getInviteeId());
                        request.setIsDistributor(tradeConfirmResponse.getIsDistributor());

                        List<DistributionCustomerSimVO> inviteeCustomers = distributionCustomerQueryProvider
                                .listDistributorsForOrderCommit(request).getContext().getDistributorList();

                        List<TradeDistributeItemVO> distributeItems = new ArrayList<>();

                        // 商品分销佣金map(记录每个分销商品基础分销佣金)
                        Map<String, BigDecimal> skuBaseCommissionMap = new HashMap<>();
                        distributionTradeItems.forEach(item ->
                                skuBaseCommissionMap.put(item.getSkuId(), item.getDistributionCommission())
                        );

                        // 2.2.根据受益人列表设置分销相关字段
                        if (CollectionUtils.isNotEmpty(inviteeCustomers)) {

                            for (int idx = 0; idx < inviteeCustomers.size(); idx++) {

                                DistributionCustomerSimVO customer = inviteeCustomers.get(idx);

                                DistributorLevelVO level = multistageSetting.getDistributorLevels().stream()
                                        .filter(l -> l.getDistributorLevelId().equals(customer.getDistributorLevelId()))
                                        .findFirst()
                                        .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000003));//修复sonar
                                // 检测出的bug

                                if (idx == 0) {
                                    // 2.2.1.设置返利人信息
                                    distributionTradeItems.forEach(item -> {
                                        // 设置trade.tradeItems
                                        item.setDistributionCommission(
                                                DistributionCommissionUtils.calDistributionCommission(
                                                        item.getDistributionCommission(), level.getCommissionRate())
                                        );
                                        item.setCommissionRate(item.getCommissionRate().multiply(level.getCommissionRate()));

                                        // 设置trade.distributeItems
                                        TradeDistributeItemVO distributeItem = new TradeDistributeItemVO();
                                        distributeItem.setGoodsInfoId(item.getSkuId());
                                        distributeItem.setNum(item.getNum());
                                        distributeItem.setActualPaidPrice(item.getSplitPrice());
                                        distributeItem.setCommissionRate(item.getCommissionRate());
                                        distributeItem.setCommission(item.getDistributionCommission());
                                        distributeItems.add(distributeItem);
                                    });

                                    // 设置trade.[inviteeId,distributorId,distributorName,commission]
                                    tradeItemGroupVO.setCommission(
                                            distributeItems.stream().map(TradeDistributeItemVO::getCommission)
                                                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                                    );
                                    // 累加返利人佣金至总佣金
                                    totalCommission = totalCommission.add(tradeItemGroupVO.getCommission());

                                } else {
                                    // 2.2.2.设置提成人信息
                                    BigDecimal percentageTotal = BigDecimal.ZERO;
                                    for (int i = 0; i < distributeItems.size(); i++) {
                                        // 设置trade.distributeItems.commissions
                                        if(level != null && level.getPercentageRate() != null) {
                                            TradeDistributeItemVO item = distributeItems.get(i);
                                            TradeDistributeItemCommissionVO itemCommission = new TradeDistributeItemCommissionVO();
                                            itemCommission.setCustomerId(customer.getCustomerId());
                                            itemCommission.setDistributorId(customer.getDistributionId());
                                            itemCommission.setCommission(
                                                    skuBaseCommissionMap.get(item.getGoodsInfoId()).multiply(
                                                            level.getPercentageRate()).setScale(2, RoundingMode.DOWN));
                                            if(CollectionUtils.isEmpty(item.getCommissions())){
                                                item.setCommissions(new ArrayList<>());
                                            }
                                            item.getCommissions().add(itemCommission);
                                            percentageTotal = percentageTotal.add(itemCommission.getCommission());
                                        }
                                    }

                                    // 设置trade.commissions
                                    TradeCommissionVO tradeCommission = new TradeCommissionVO();
                                    tradeCommission.setCustomerId(customer.getCustomerId());
                                    tradeCommission.setCommission(percentageTotal);
                                    tradeCommission.setDistributorId(customer.getDistributionId());
                                    tradeCommission.setCustomerName(customer.getCustomerName());
                                    tradeItemGroupVO.getCommissions().add(tradeCommission);

                                    // 累加提成人佣金至总佣金
                                    // totalCommission = totalCommission.add(tradeCommission.getCommission());
                                }

                            }
                        }
                    }
                }
            }
        }
        return totalCommission;
    }

    /**
     * @description 确认订单页 - 计算用户预估佣金
     * @author  wur
     * @date: 2023/1/5 12:21
     * @param tradeParams
     * @return
     **/
    public BigDecimal dealDistribution(List<GetCommissionTradeParams> tradeParams){
        BigDecimal totalCommission = BigDecimal.ZERO;
        Operator operator = commonUtil.getOperator();
        DistributeChannel channel = commonUtil.getDistributeChannel();
        //验证是不是PC  PC不支持分销
        if (ChannelType.PC_MALL == channel.getChannelType()) {
            return totalCommission;
        }
        // 获取当前用户是否是分销员
        DefaultFlag isDistributor = this.isDistributor(operator.getUserId()) ? DefaultFlag.YES : DefaultFlag.NO;
        for(GetCommissionTradeParams params : tradeParams){
            // 验证是不是开店礼包
            if(DefaultFlag.YES.equals(params.getStoreBagsFlag())){
                continue;
            }
            // 获取审核通过的分销商品
            List<TradeItemDTO> distributionTradeItems = params.getOldTradeItems().stream()
                    .filter(item -> DistributionGoodsAudit.CHECKED.equals(item.getDistributionGoodsAudit())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(distributionTradeItems)) {
                continue;
            }
            //验证店铺是否已开启社交分销
            DefaultFlag storeOpenFlag = distributionCacheService.queryStoreOpenFlag(params.getSupplier().getStoreId().toString());
            if (DefaultFlag.NO.equals(storeOpenFlag)) {
                continue;
            }
            // 计算每个商品的佣金
            distributionTradeItems.forEach(item -> {
                if (Objects.nonNull(item.getCommissionRate()) && Objects.nonNull(item.getSplitPrice())) {
                    // 初步计算分销佣金
                    item.setDistributionCommission(item.getSplitPrice().multiply(item.getCommissionRate()));
                    item.setPrice(item.getOriginalPrice());
                    item.setLevelPrice(item.getOriginalPrice());

                } else {
                    item.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
                }
            });


            // 2.设置分销相关字段
            if (distributionTradeItems.size() != 0) {

                MultistageSettingGetResponse multistageSetting =
                        distributionCacheQueryProvider.getMultistageSetting().getContext();

                // 2.1.查询佣金受益人列表
                DistributionCustomerListForOrderCommitRequest request =
                        new DistributionCustomerListForOrderCommitRequest();
                request.setBuyerId(commonUtil.getOperatorId());
                request.setCommissionPriorityType(
                        com.wanmi.sbc.customer.bean.enums.CommissionPriorityType.fromValue(multistageSetting.getCommissionPriorityType().toValue())
                );
                request.setCommissionUnhookType(
                        CommissionUnhookType.fromValue(multistageSetting.getCommissionUnhookType().toValue())
                );
                request.setDistributorLevels(multistageSetting.getDistributorLevels());
                request.setInviteeId(channel.getInviteeId());
                request.setIsDistributor(isDistributor);
                List<DistributionCustomerSimVO> inviteeCustomers = distributionCustomerQueryProvider
                        .listDistributorsForOrderCommit(request).getContext().getDistributorList();
                if (CollectionUtils.isEmpty(inviteeCustomers)) {
                    continue;
                }

                List<TradeDistributeItemVO> distributeItems = new ArrayList<>();

                // 商品分销佣金map(记录每个分销商品基础分销佣金)
                Map<String, BigDecimal> skuBaseCommissionMap = new HashMap<>();
                distributionTradeItems.forEach(item ->
                        skuBaseCommissionMap.put(item.getSkuId(), item.getDistributionCommission())
                );

                DistributionCustomerSimVO customer = inviteeCustomers.get(0);

                DistributorLevelVO level = multistageSetting.getDistributorLevels().stream()
                        .filter(l -> l.getDistributorLevelId().equals(customer.getDistributorLevelId()))
                        .findFirst()
                        .orElseThrow(() -> new SbcRuntimeException(CommonErrorCodeEnum.K000003));//修复sonar

                // 2.2.1.设置返利人信息
                distributionTradeItems.forEach(item -> {
                    // 设置trade.tradeItems
                    item.setDistributionCommission(
                            DistributionCommissionUtils.calDistributionCommission(
                                    item.getDistributionCommission(), level.getCommissionRate())
                    );
                    item.setCommissionRate(item.getCommissionRate().multiply(level.getCommissionRate()));

                    // 设置trade.distributeItems
                    TradeDistributeItemVO distributeItem = new TradeDistributeItemVO();
                    distributeItem.setGoodsInfoId(item.getSkuId());
                    distributeItem.setNum(item.getNum());
                    distributeItem.setActualPaidPrice(item.getSplitPrice());
                    distributeItem.setCommissionRate(item.getCommissionRate());
                    distributeItem.setCommission(item.getDistributionCommission());
                    distributeItems.add(distributeItem);
                });

                // 累加返利人佣金至总佣金
                totalCommission = totalCommission.add(distributeItems.stream().map(TradeDistributeItemVO::getCommission)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
            }
        }
        return totalCommission;
    }


    /**
     * 获取订单商品详情,包含区间价，会员级别价
     */
    public GoodsInfoResponse getGoodsResponse(List<String> skuIds, String customerId) {
        //性能优化，原来从order服务绕道，现在直接从goods服务直行
        GoodsInfoViewByIdsRequest goodsInfoRequest = GoodsInfoViewByIdsRequest.builder()
                .goodsInfoIds(skuIds)
                .isHavSpecText(Constants.yes)
                .build();
        GoodsInfoViewByIdsResponse response = goodsInfoQueryProvider.listViewByIds(goodsInfoRequest).getContext();

        //计算区间价
        GoodsIntervalPriceByCustomerIdResponse priceResponse = goodsIntervalPriceService.getGoodsIntervalPriceVOList
                (response.getGoodsInfos(), customerId);
        response.setGoodsInfos(priceResponse.getGoodsInfoVOList());

        return GoodsInfoResponse.builder().goodsInfos(response.getGoodsInfos())
                .goodses(response.getGoodses())
                .goodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList())
                .build();
    }


    public void dealPoints(List<TradeItemGroupVO>  trades, TradeCommitRequest tradeCommitRequest) {
        List<TradeItemVO>  tradeItemVOList = trades.stream().flatMap(tradeItemGroupVO -> tradeItemGroupVO.getTradeItems().stream())
                .filter(tradeItemVO -> DistributionGoodsAudit.CHECKED == tradeItemVO.getDistributionGoodsAudit()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tradeItemVOList)){
            return;
        }
        SystemPointsConfigQueryResponse pointsConfig = systemPointsConfigService.querySettingCache();
        final BigDecimal pointWorth = BigDecimal.valueOf(pointsConfig.getPointsWorth());
        /*
         * 商品积分设置
         */
        if (EnableStatus.ENABLE.equals(pointsConfig.getStatus())
                && PointsUsageFlag.GOODS.equals(pointsConfig.getPointsUsageFlag())) {
            //合计商品
            Long sumPoints = trades.stream().flatMap(trade -> trade.getTradeItems().stream())
                    .filter(k -> Objects.nonNull(k.getBuyPoint()))
                    .mapToLong(k -> k.getBuyPoint() * k.getNum()).sum();
            if (sumPoints <= 0) {
                return;
            }
            tradeCommitRequest.setPoints(sumPoints);

            // 如果使用积分 校验可使用积分
            this.verifyPoints(trades, tradeCommitRequest, pointsConfig);
            //积分均摊，积分合计，不需要平滩价
            trades.forEach(trade -> {
                //积分均摊
                trade.getTradeItems().stream()
                        .filter(tradeItem -> Objects.nonNull(tradeItem.getBuyPoint()))
                        .forEach(tradeItem -> {
                            tradeItem.setPoints(tradeItem.getBuyPoint() * tradeItem.getNum());
                            tradeItem.setPointsPrice(BigDecimal.valueOf(tradeItem.getPoints()).divide(pointWorth, 2,
                                    RoundingMode.HALF_UP));
                        });
            });
        } else {
            /*
             * 订单积分设置
             */
            //将buyPoint置零
            trades.stream().flatMap(trade -> trade.getTradeItems().stream()).forEach(tradeItem -> tradeItem.setBuyPoint(0L));

            if (tradeCommitRequest.getPoints() == null || tradeCommitRequest.getPoints() <= 0) {
                return;
            }
            //商城积分体系未开启
            if (!EnableStatus.ENABLE.equals(pointsConfig.getStatus())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000018);
            }
            // 如果使用积分 校验可使用积分
            this.verifyPoints(trades, tradeCommitRequest, pointsConfig);

            List<TradeItemVO> items =
                    trades.stream().flatMap(trade -> trade.getTradeItems().stream()).collect(Collectors.toList());

            // 设置关联商品的积分均摊
            BigDecimal pointsTotalPrice = BigDecimal.valueOf(tradeCommitRequest.getPoints()).divide(pointWorth, 2,
                    RoundingMode.HALF_UP);
            this.calcPoints(items, pointsTotalPrice, tradeCommitRequest.getPoints(), pointWorth);

            // 设置关联商品的均摊价
            BigDecimal total = this.calcSkusTotalPrice(items);
            this.calcSplitPrice(items, total.subtract(pointsTotalPrice), total);


        }
    }

    public BigDecimal calcSkusTotalPrice(List<TradeItemVO> tradeItems) {
        if (Objects.nonNull(tradeItems.get(0).getIsBookingSaleGoods()) && tradeItems.get(0).getIsBookingSaleGoods()) {
            TradeItemVO tradeItem = tradeItems.get(0);
            if (tradeItem.getBookingType() == BookingType.EARNEST_MONEY && Objects.nonNull(tradeItem.getTailPrice())) {
                return tradeItem.getTailPrice();
            }
        }
        return tradeItems.stream()
                .filter(tradeItem -> tradeItem.getSplitPrice() != null && tradeItem.getSplitPrice().compareTo(BigDecimal.ZERO) > 0)
                .map(TradeItemVO::getSplitPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    /**
     * 计算商品均摊价
     *
     * @param tradeItems 待计算的商品列表
     * @param newTotal   新的总价
     * @param total      旧的商品总价
     */
    void calcSplitPrice(List<TradeItemVO> tradeItems, BigDecimal newTotal, BigDecimal total) {
        //内部总价为零或相等不用修改
        if (total.compareTo(newTotal) == 0?true:false) {
            return;
        }
        // 尾款情况重新计算实际总价
        if (CollectionUtils.isNotEmpty(tradeItems)) {
            TradeItemVO tradeItem = tradeItems.get(0);
            if (Objects.nonNull(tradeItem.getIsBookingSaleGoods()) && tradeItem.getIsBookingSaleGoods() && tradeItem.getBookingType() == BookingType.EARNEST_MONEY
                    && total.compareTo(tradeItem.getTailPrice()) == 0?true:false) {
                newTotal = tradeItem.getEarnestPrice().add(newTotal);
                total = tradeItem.getEarnestPrice().add(total);
            }
        }

        int size = tradeItems.size();
        BigDecimal splitPriceTotal = BigDecimal.ZERO;//累积平摊价，将剩余扣给最后一个元素
        Long totalNum = tradeItems.stream().map(TradeItemVO::getNum).reduce(0L, Long::sum);

        for (int i = 0; i < size; i++) {
            TradeItemVO tradeItem = tradeItems.get(i);
            if (i == size - 1) {
                tradeItem.setSplitPrice(newTotal.subtract(splitPriceTotal));
            } else {
                BigDecimal splitPrice = tradeItem.getSplitPrice() != null ? tradeItem.getSplitPrice() : BigDecimal.ZERO;
                //全是零元商品按数量均摊
                if (BigDecimal.ZERO.compareTo(total) == 0?true:false) {
                    tradeItem.setSplitPrice(
                            newTotal.multiply(BigDecimal.valueOf(tradeItem.getNum()))
                                    .divide(BigDecimal.valueOf(totalNum), 2, RoundingMode.HALF_UP));
                } else {
                    tradeItem.setSplitPrice(
                            splitPrice
                                    .divide(total, 10, RoundingMode.DOWN)
                                    .multiply(newTotal)
                                    .setScale(2, RoundingMode.HALF_UP));
                }
                splitPriceTotal = splitPriceTotal.add(tradeItem.getSplitPrice());
            }
        }
    }

    /**
     * 计算积分抵扣均摊价、均摊数量
     *
     * @param tradeItems       待计算的商品列表
     * @param pointsPriceTotal 积分抵扣总额
     * @param pointsTotal      积分抵扣总数
     */
    void calcPoints(List<TradeItemVO> tradeItems, BigDecimal pointsPriceTotal, Long pointsTotal, BigDecimal pointWorth) {
        BigDecimal totalPrice = tradeItems.stream()
                .filter(tradeItem -> tradeItem.getSplitPrice() != null && tradeItem.getSplitPrice().compareTo(BigDecimal.ZERO) > 0)
                .map(TradeItemVO::getSplitPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        int size = tradeItems.size();
        //累积积分平摊价，将剩余扣给最后一个元素
        BigDecimal splitPriceTotal = BigDecimal.ZERO;
        //累积积分数量，将剩余扣给最后一个元素
        Long splitPointsTotal = 0L;

        for (int i = 0; i < size; i++) {
            TradeItemVO tradeItem = tradeItems.get(i);
            if (i == size - 1) {
                tradeItem.setPointsPrice(pointsPriceTotal.subtract(splitPriceTotal));
                tradeItem.setPoints(pointsTotal - splitPointsTotal);
            } else {
                BigDecimal splitPrice = tradeItem.getSplitPrice() != null ? tradeItem.getSplitPrice() : BigDecimal.ZERO;
                tradeItem.setPointsPrice(
                        splitPrice
                                .divide(totalPrice, 10, RoundingMode.DOWN)
                                .multiply(pointsPriceTotal)
                                .setScale(2, RoundingMode.HALF_UP));
                splitPriceTotal = splitPriceTotal.add(tradeItem.getPointsPrice());

                tradeItem.setPoints(tradeItem.getPointsPrice().multiply(pointWorth).longValue());
                splitPointsTotal = splitPointsTotal + tradeItem.getPoints();
            }
        }
    }



    /**
     * 校验可使用积分
     *
     * @param tradeCommitRequest
     */
    public SystemPointsConfigQueryResponse verifyPoints(List<TradeItemGroupVO> trades, TradeCommitRequest tradeCommitRequest, SystemPointsConfigQueryResponse pointsConfig) {
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

        return pointsConfig;
    }

}
