package com.wanmi.sbc.job.service;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsProvider;
import com.wanmi.sbc.account.api.provider.ledgerfunds.LedgerFundsProvider;
import com.wanmi.sbc.account.api.request.funds.GrantAmountRequest;
import com.wanmi.sbc.account.api.request.ledgerfunds.LedgerFundsAmountRequest;
import com.wanmi.sbc.account.bean.enums.FundsSubType;
import com.wanmi.sbc.account.bean.enums.FundsType;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerSaveProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionInviteNewProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionInviteNewQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.customer.DistributionInviteNewModifyRegisterRequest;
import com.wanmi.sbc.customer.api.request.customer.DistributionInviteNewPageRequest;
import com.wanmi.sbc.customer.api.request.distribution.AfterSettleUpdateDistributorRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerBatchModifyCommissionByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerByIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.customer.DistributionInviteNewPageResponse;
import com.wanmi.sbc.customer.api.response.customer.DistributionInviteNewUpdateResponse;
import com.wanmi.sbc.customer.api.response.distribution.EsDistributionCustomerByCustomerIdResponse;
import com.wanmi.sbc.customer.api.response.distribution.EsDistributionCustomerByIdResponse;
import com.wanmi.sbc.customer.bean.dto.DistributorDTO;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerShowPhoneVO;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewRecordVO;
import com.wanmi.sbc.distribution.DistributionCacheService;
import com.wanmi.sbc.elastic.api.provider.customer.EsDistributionCustomerProvider;
import com.wanmi.sbc.elastic.api.provider.distributioninvitenew.EsDistributionInviteNewProvider;
import com.wanmi.sbc.elastic.api.provider.distributionrecord.EsDistributionRecordProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerAddRequest;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewSaveRequest;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordAddRequest;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordByTradeIdRequest;
import com.wanmi.sbc.marketing.api.provider.distribution.DistributionSettingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.distributionrecord.DistributionRecordQueryProvider;
import com.wanmi.sbc.marketing.api.provider.distributionrecord.DistributionRecordSaveProvider;
import com.wanmi.sbc.marketing.api.request.distributionrecord.DistributionRecordByTradeIdRequest;
import com.wanmi.sbc.marketing.api.request.distributionrecord.DistributionRecordListRequest;
import com.wanmi.sbc.marketing.api.request.distributionrecord.DistributionRecordModifyRequest;
import com.wanmi.sbc.marketing.api.request.distributionrecord.DistributionRecordUpdateInfo;
import com.wanmi.sbc.marketing.api.response.distributionrecord.DistributionRecordListResponse;
import com.wanmi.sbc.marketing.bean.enums.DistributionLimitType;
import com.wanmi.sbc.marketing.bean.enums.RecruitApplyType;
import com.wanmi.sbc.marketing.bean.enums.RewardCashType;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.marketing.bean.vo.DistributionRecordVO;
import com.wanmi.sbc.marketing.bean.vo.DistributionSettingVO;
import com.wanmi.sbc.order.api.provider.distribution.ConsumeRecordProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.distribution.ConsumeRecordModifyRequest;
import com.wanmi.sbc.order.api.request.distribution.DistributionLedgerRequest;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.UseType;
import com.wanmi.sbc.order.bean.vo.*;

import io.seata.spring.annotation.GlobalTransactional;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 定时任务Handler @see com.wanmi.sbc.job.DistributionTempJobHandler
 * 处理分销临时订单发放邀新奖励、分销佣金
 */
@Component
@Slf4j
public class DistributionTempJobService {

    @Value("${distribution.temp.task.bath.num}")
    int num;
    @Autowired
    private DistributionTaskTempService distributionTaskTempService;
    @Autowired
    private TradeQueryProvider tradeQueryProvider;
    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;
    @Autowired
    private DistributionRecordSaveProvider distributionRecordSaveProvider;
    @Autowired
    private DistributionInviteNewProvider distributionInviteNewProvider;
    @Autowired
    private DistributionInviteNewQueryProvider distributionInviteNewQueryProvider;
    @Autowired
    private ConsumeRecordProvider consumeRecordProvider;
    @Autowired
    private CustomerFundsProvider customerFundsProvider;
    @Autowired
    private DistributionSettingQueryProvider distributionSettingQueryProvider;
    @Autowired
    private DistributionCustomerSaveProvider distributionCustomerSaveProvider;
    @Autowired
    private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Autowired
    private EsDistributionInviteNewProvider esDistributionInviteNewProvider;

    /**
     * 注入客户查询provider
     */
    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    /**
     * 注入分销设置缓存service
     */
    @Autowired
    private DistributionCacheService distributionCacheService;

    @Autowired
    private EsDistributionCustomerProvider esDistributionCustomerProvider;

    @Autowired
    private EsDistributionRecordProvider esDistributionRecordProvider;

    @Autowired
    private DistributionRecordQueryProvider distributionRecordQueryProvider;

    @Autowired
    private LedgerFundsProvider ledgerFundsProvider;


    // 后面如果要加分布式事务，请在这边加！！！别乱加！！！
    @GlobalTransactional
    public void deal(DistributionTaskTempVO taskTemp, TradeVO trade, List<ReturnOrderVO> returnOrderList) {
        //2.1、判断订单状态，如作果是已废订单，则删除临时表、如果是分销订单则删除对应的分销记录数据（以订单id删除）
        if (this.filterInvalidateOrder(trade)) {
            distributionTaskTempService.deleteById(taskTemp.getId());
            return;
        }

        //有效消费金额、分销佣金的计算
        this.count(taskTemp, trade, returnOrderList);

        distributionTaskTempService.deleteById(taskTemp.getId());

    }

    /**
     * 如果是已作废订单，并且如果是分销订单则删除对应的分销记录数据（以订单id删除）
     */
    private boolean filterInvalidateOrder(final TradeVO trade) {
        FlowState flowState = trade.getTradeState().getFlowState();
        if (flowState != FlowState.VOID) {
            return false;
        }
        //如果有分销员id，并且有分销商品就算分销订单
        if (trade.getDistributeItems().size() > 0 && StringUtils.isNotBlank(trade.getDistributorId())) {
            // 删除对应的分销记录数据
            DistributionRecordByTradeIdRequest request = new DistributionRecordByTradeIdRequest();
            request.setTradeId(trade.getId());
            distributionRecordSaveProvider.deleteByTradeId(request);

            //同步es
            EsDistributionRecordByTradeIdRequest delRequest = new EsDistributionRecordByTradeIdRequest(trade.getId());
            esDistributionRecordProvider.deleteByTradeId(delRequest);

            // 分销员表要 订单数量-1，-销售额，-未入账的分销佣金

            BigDecimal changeSaleAmount =
                    trade.getDistributeItems().stream().map(TradeDistributeItemVO::getActualPaidPrice).reduce(BigDecimal.ZERO
                            , BigDecimal::add);

            this.updateDistributor(trade, changeSaleAmount, BigDecimal.ZERO, BigDecimal.ZERO, 0, 1);
        }

        return true;
    }

    /**
     * 计算有效消费金额以及分销佣金
     */
    private void count(final DistributionTaskTempVO distributionTaskTemp, final TradeVO trade,
                       final List<ReturnOrderVO> returnOrderList) {
        UseType useType = UseType.fromValue(distributionTaskTemp.getUseType());
        DistributionLedgerRequest ledgerRequest = UseType.LEDGER.toValue() == distributionTaskTemp.getUseType()
                ? JSONObject.parseObject(distributionTaskTemp.getParams(), DistributionLedgerRequest.class)
                : null;
        log.info("订单id:{} 计算有效消费金额以及分销佣金", trade.getId());
        //2.2.0、计算有效消费金额（订单实付金额（不包括运费）-退单金额总和）
        BigDecimal changeSaleAmount = this.dealValidAmount(trade, returnOrderList);
        //2.3.0、如果不是分销订单，则不计算分销佣金,流程结束
        if (distributionTaskTemp.getDistributionOrder() == BoolFlag.NO) {
            log.info("订单id:{} 不是分销订单，不计算分销佣金", trade.getId());
            DistributionInviteNewUpdateResponse result = this.modifyDistributionInviteNewRecord(distributionTaskTemp,
                    trade, LocalDateTime.now());
            this.updateDistributor(trade, changeSaleAmount, new BigDecimal(0), result.getInviteAmount(),
                    result.getInviteNum(), 0);
            return;
        }
        //2.3.1、获取订单里的分销商品数据A列表；
        List<TradeDistributeItemVO> distributeItemVOS = trade.getDistributeItems();
        //2.3.2、计算订单里的分销商品A列表中的商品-退单里对应的商品的数量
        //根据渠道获取佣金
        BigDecimal amount = Objects.nonNull(ledgerRequest)
                ? ledgerRequest.getCommission() : this.dealDistributeGoods(trade, distributeItemVOS, returnOrderList);
        log.info("订单id:{} 计算分销佣金为：{}", trade.getId(), amount);

        LocalDateTime accountingDate = LocalDateTime.now();
        // 2.3.4、分销记录表
        this.updateDistributeRecord(distributeItemVOS, trade, accountingDate, amount, useType);
        // 更新邀新记录表
        DistributionInviteNewUpdateResponse result = this.modifyDistributionInviteNewRecord(distributionTaskTemp,
                trade, accountingDate);

        // 2.3.5、更新分销员表
        this.updateDistributor(trade, changeSaleAmount, amount, result.getInviteAmount(), result.getInviteNum(), 0);

        List<TradeDistributeItemCommissionVO> commissions;
        if (Objects.nonNull(ledgerRequest)) {
            commissions = KsBeanUtil.convertList(ledgerRequest.getCommissions(), TradeDistributeItemCommissionVO.class);
        } else {
            commissions = distributeItemVOS.stream().flatMap(tradeDistributeItemVO -> tradeDistributeItemVO.getCommissions().stream()).collect(Collectors.toList());
        }
        log.info("订单id:{},发放分销员佣金提成，详情信息如下：{}", trade.getId(), commissions);
        if (CollectionUtils.isEmpty(commissions)) {
            return;
        }
        //2.3.6、更新分销员提成
        List<DistributorDTO> distributorDTOList = commissions.stream().map(vo -> new DistributorDTO(vo.getCustomerId(), vo.getCommission())).collect(Collectors.toList());
        BaseResponse baseResponse = distributionCustomerSaveProvider.batchModifyCommissionByCustomerId(new DistributionCustomerBatchModifyCommissionByCustomerIdRequest(distributorDTOList));

        //（单笔同步，可优化）同步es,目前es插入数据和mysql更新时顺序不同，要想保证数据有序,请把unordered()拿掉，并forEach改为forEachOrdered()
        distributorDTOList.parallelStream().unordered()
                .map(DistributorDTO::getCustomerId)
                .forEach(id -> {
                    DistributionCustomerByCustomerIdRequest idRequest = new DistributionCustomerByCustomerIdRequest();
                    idRequest.setCustomerId(id);
                    EsDistributionCustomerByCustomerIdResponse idResponse = distributionCustomerQueryProvider.getByCustomerIdShowPhone(idRequest).getContext();
                    DistributionCustomerShowPhoneVO distributionCustomerVO = idResponse.getDistributionCustomerVO();
                    if (Objects.nonNull(distributionCustomerVO)) {
                        EsDistributionCustomerAddRequest addRequest = new EsDistributionCustomerAddRequest(Collections.singletonList(distributionCustomerVO));
                        esDistributionCustomerProvider.add(addRequest);
                    }
                });


        log.info("订单id:{},更新分销员提成是否成功：{}", trade.getId(), baseResponse.getContext().equals(commissions.size()));
        //2.3.6、发放分销提成
        for (TradeDistributeItemCommissionVO commissionVO : commissions) {
            this.dealAmount(commissionVO.getCustomerId(), commissionVO.getCommission(), LocalDateTime.now(), FundsType.COMMISSION_COMMISSION, FundsSubType.PROMOTION_COMMISSION,
                    trade.getId(), useType);
        }
    }

    /**
     * 计算最后的分销商品数量,算出分销佣金总和
     */
    private BigDecimal dealDistributeGoods(TradeVO trade, List<TradeDistributeItemVO> distributeItemVOS,
                                           List<ReturnOrderVO> returnOrderList) {
        log.info("根据订单id:{} 计算分销佣金", trade.getId());
        //处理退单 组装数据
        List<ReturnItemVO> returnItemVOList = new LinkedList<>();
        returnOrderList.forEach(item -> returnItemVOList.addAll(item.getReturnItems()));
        distributeItemVOS.forEach(item -> {
            String goodInfoId = item.getGoodsInfoId();
            List<ReturnItemVO> newReturnList =
                    returnItemVOList.stream().filter(returnItem -> goodInfoId.equals(returnItem.getSkuId())).collect(Collectors.toList());
            long returnNum = 0L;
            if (newReturnList.size() > 0) {
                returnNum = newReturnList.stream().mapToLong(i -> i.getNum().longValue()).sum();
            }
            long num = item.getNum();
            //真实购买数量
            long realBuyNum = num - returnNum;
            if (realBuyNum < 0) {
                log.error("计算分销商品数量出错,订单id：{} 商品skuId:{}", trade.getId(), goodInfoId);
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,new Object[] {String.format("计算分销商品数量出错,订单id：%s 商品skuId:%s", trade.getId(), goodInfoId)});
            }
            //重新计算佣金
            BigDecimal commission = item.getCommission();
            BigDecimal realCommission = commission.multiply(BigDecimal.valueOf(realBuyNum)).divide(BigDecimal.valueOf(num), 2, RoundingMode.DOWN);
            item.setCommission(realCommission);
            item.setNum(realBuyNum);

            //重新计算分销提成
            List<TradeDistributeItemCommissionVO> itemCommissionVOList = item.getCommissions();
            if (CollectionUtils.isNotEmpty(itemCommissionVOList)) {
                itemCommissionVOList.forEach(itemCommission -> {
                    BigDecimal oldCommission = itemCommission.getCommission();
                    BigDecimal newCommission = oldCommission.multiply(BigDecimal.valueOf(realBuyNum)).divide(BigDecimal.valueOf(num), 2, RoundingMode.DOWN);
                    itemCommission.setCommission(newCommission);
                });
            }
        });
        //计算分销佣金
        BigDecimal totalAmount = BigDecimal.ZERO;
        // 如果本次分销订单返利
        if (trade.getCommissionFlag()) {
            totalAmount = distributeItemVOS.stream().map(
                    TradeDistributeItemVO::getCommission)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return totalAmount;
    }

    /**
     * 更新分销记录表
     */
    private void updateDistributeRecord(List<TradeDistributeItemVO> distributeItemVOS, TradeVO trade,
                                        LocalDateTime dateTime, BigDecimal amount, UseType useType) {
        DistributionRecordModifyRequest distributionRecordModifyRequest = new DistributionRecordModifyRequest();
        List<String> tradeIdList = new ArrayList<>();
        List<DistributionRecordUpdateInfo> updateInfos = distributeItemVOS.stream().map(item -> {
            DistributionRecordUpdateInfo info = new DistributionRecordUpdateInfo();
            info.setFinishTime(this.getOrderFinishTime(trade));
            info.setGoodsCount(item.getNum());
            info.setGoodsInfoId(item.getGoodsInfoId());
            info.setMissionReceivedTime(dateTime);
            info.setTradeId(trade.getId());
            tradeIdList.add(trade.getId());
            return info;
        }).collect(Collectors.toList());
        distributionRecordModifyRequest.setUpdateInfos(updateInfos);
        distributionRecordSaveProvider.modify(distributionRecordModifyRequest);


        //同步es
        DistributionRecordListRequest request = new DistributionRecordListRequest();
        request.setTradeIdList(tradeIdList);
        DistributionRecordListResponse context = distributionRecordQueryProvider.list(request).getContext();
        List<DistributionRecordVO> distributionRecordVOList = context.getDistributionRecordVOList();
        if(CollectionUtils.isNotEmpty(distributionRecordVOList)){
            EsDistributionRecordAddRequest addRequest = new EsDistributionRecordAddRequest(distributionRecordVOList);
            esDistributionRecordProvider.add(addRequest);
        }

        FundsSubType subType = FundsSubType.DISTRIBUTION_COMMISSION;
        // 是否自购
        if (trade.getBuyer().getId().equals(trade.getInviteeId())) {
            subType = FundsSubType.SELFBUY_COMMISSION;
        }
        //发佣金
        this.dealAmount(trade.getInviteeId(), amount, dateTime, FundsType.DISTRIBUTION_COMMISSION, subType,
                trade.getId(), useType);
    }

    /**
     * 发放分销佣金或者邀新奖励
     */
    private void dealAmount(String distributeCustomerId, BigDecimal amount, LocalDateTime dateTime, FundsType type,
                            FundsSubType subType, String businessId, UseType useType) {
        if (BigDecimal.ZERO.compareTo(amount) == 0) {
            return;
        }
        //分账资金提现
        if (UseType.LEDGER.equals(useType)) {
            ledgerFundsProvider.grantAmount(LedgerFundsAmountRequest.builder()
                    .customerId(distributeCustomerId)
                    .amount(amount)
                    .build());
            return;
        }
        // 根据邀请人id查询客户信息
        CustomerGetByIdRequest requestCustomerRequest = new CustomerGetByIdRequest(distributeCustomerId);
        CustomerGetByIdResponse requestCustomerResponse =
                customerQueryProvider.getCustomerById(requestCustomerRequest).getContext();
        if (Objects.nonNull(requestCustomerResponse)) {
            GrantAmountRequest request = new GrantAmountRequest();
            request.setAmount(amount);
            request.setBusinessId(businessId);
            request.setCustomerId(distributeCustomerId);
            request.setDateTime(dateTime);
            request.setType(type);
            request.setSubType(subType);
            request.setCustomerAccount(requestCustomerResponse.getCustomerAccount());
            request.setCustomerName(requestCustomerResponse.getCustomerAccount());
            request.setDistributor(DefaultFlag.YES.toValue());
            customerFundsProvider.grantAmount(request);
        }

    }

    /**
     * 更新邀新记录-有效邀新
     *
     * @param distributionTaskTemp
     * @param trade
     * @param dateTime
     * @return
     */
    private DistributionInviteNewUpdateResponse modifyDistributionInviteNewRecord(final DistributionTaskTempVO distributionTaskTemp, final TradeVO trade, LocalDateTime dateTime) {
        DistributionInviteNewUpdateResponse response =
                DistributionInviteNewUpdateResponse.builder().inviteAmount(BigDecimal.ZERO).inviteNum(NumberUtils.INTEGER_ZERO).build();
        // 不更新邀新记录
        if (distributionTaskTemp.getFirstValidBuy() == BoolFlag.NO) {
            log.info("============更新邀新记录失败！第一次有效购买:{}===============",
                    distributionTaskTemp.getFirstValidBuy() == BoolFlag.NO);
            return response;
        }

        //查询是否开启奖励现金开关
        DefaultFlag rewardCashFlag = distributionCacheService.getRewardCashFlag();
        //奖励上限类型设置
        RewardCashType rewardCashType = distributionCacheService.queryRewardCashType();
        //奖励现金上限(人数)
        Integer tempCount = distributionCacheService.queryRewardCashCount();
        Long rewardCashCount = Objects.isNull(tempCount) ? null : tempCount.longValue();
        // 是否开启奖励优惠券
        DefaultFlag rewardCouponFlag = distributionCacheService.getRewardCouponFlag();
        //"奖励优惠券上限(组数)
        Integer rewardCouponCount = distributionCacheService.getRewardCouponCount();
        //订单完成时间
        LocalDateTime orderFinishTime = trade.getTradeState().getEndTime();
        //订单下单时间
        LocalDateTime orderCreateTime = trade.getTradeState().getCreateTime();
        //订单编号
        String orderCode = trade.getId();
        //购买人编号
        String orderBuyCustomerId = trade.getBuyer().getId();

        //优惠券信息
        List<CouponInfoVO> couponInfos = distributionCacheService.getCouponInfos();
        BigDecimal denominationSum = BigDecimal.ZERO;
        if (CollectionUtils.isNotEmpty(couponInfos) && DefaultFlag.YES.equals(rewardCouponFlag)) {
            denominationSum = couponInfos.stream().map(CouponInfoVO::getDenomination)
                    .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        }

        DistributionInviteNewModifyRegisterRequest modifyRegisterRequest =
                new DistributionInviteNewModifyRegisterRequest();
        modifyRegisterRequest.setOrderBuyCustomerId(orderBuyCustomerId);
        modifyRegisterRequest.setOrderFinishTime(orderFinishTime);
        modifyRegisterRequest.setOrderCreateTime(orderCreateTime);
        modifyRegisterRequest.setDateTime(dateTime);
        modifyRegisterRequest.setOrderCode(orderCode);
        modifyRegisterRequest.setRewardCashFlag(rewardCashFlag);
        modifyRegisterRequest.setRewardCashType(Objects.nonNull(rewardCashType) ?
                DefaultFlag.fromValue(rewardCashType.toValue()) : null);
        modifyRegisterRequest.setRewardCashCount(rewardCashCount);
        modifyRegisterRequest.setRewardCouponFlag(rewardCouponFlag);
        modifyRegisterRequest.setRewardCouponCount(rewardCouponCount);
        modifyRegisterRequest.setDenominationSum(denominationSum);

        log.info("===================更新邀新记录DistributionInviteNewModifyRegisterRequest对象组装完成：{}================ ",
                modifyRegisterRequest);

        BaseResponse<DistributionInviteNewUpdateResponse> baseResponse =
                distributionInviteNewProvider.modify(modifyRegisterRequest);
        List<DistributionInviteNewRecordVO> inviteNewRecordVOList = baseResponse.getContext().getInviteNewRecordVOList();
        //同步es
        if (CollectionUtils.isNotEmpty(inviteNewRecordVOList)) {
            EsDistributionInviteNewSaveRequest saveRequest = EsDistributionInviteNewSaveRequest.builder()
                    .inviteNewRecordVOList(inviteNewRecordVOList)
                    .build();
            esDistributionInviteNewProvider.addInviteNewRecord(saveRequest);
        }
        return baseResponse.getContext();
    }


    /**
     * 计算有效消费金额,返回减少的消费额（改变分销员的销售额字段，分销员自己买的不计算）
     */
    private BigDecimal dealValidAmount(final TradeVO trade, final List<ReturnOrderVO> returnOrderList) {
        log.info("根据订单id:{} 计算有效消费金额", trade.getId());
        BigDecimal deliveryPrice = Objects.isNull(trade.getTradePrice().getDeliveryPrice()) ? BigDecimal.ZERO : trade.getTradePrice().getDeliveryPrice();
        BigDecimal totalAmount =
                trade.getTradePrice().getTotalPrice().subtract(deliveryPrice);
        BigDecimal returnOrderTotalAmount = new BigDecimal(0);
        BigDecimal returnOrderSaleTotalAmount = new BigDecimal(0);
        if (returnOrderList.size() > 0) {
            returnOrderList.forEach(item -> returnOrderTotalAmount.add(Objects.isNull(item.getReturnPrice().getActualReturnPrice()) ? BigDecimal.ZERO : item.getReturnPrice().getActualReturnPrice()));
            returnOrderSaleTotalAmount = returnOrderList.stream().map(item ->
                    item.getDistributeItems().stream().map(i ->
                            i.getActualPaidPrice())
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
            ).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        BigDecimal validAmount = totalAmount.subtract(returnOrderTotalAmount);
        log.info("根据订单id:{} 计算有效消费金额为：{}", trade.getId(), validAmount);
        // 更新消费记录表（以订单id，更新有效消费金额）
        ConsumeRecordModifyRequest request = new ConsumeRecordModifyRequest();
        request.setOrderId(trade.getId());
        request.setValidConsumeSum(validAmount);
        request.setFlowState(FlowState.AVAILABLE_ORDER.toValue());
        consumeRecordProvider.modifyConsumeRecord(request);
//        if (trade.getBuyer().getId().equals(trade.getInviteeId())) {
//            return new BigDecimal(0);
//        }
        return returnOrderSaleTotalAmount;
    }

    /**
     * 获得订单的完成时间
     */
    private LocalDateTime getOrderFinishTime(TradeVO trade) {
        List<TradeEventLogVO> tradeEventLogVOS =
                trade.getTradeEventLogs().stream()
                        .filter(i -> FlowState.COMPLETED.getDescription().equals(i.getEventType()))
                        .collect(Collectors.toList());
        return CollectionUtils.isEmpty(tradeEventLogVOS) ? null : tradeEventLogVOS.get(0).getEventTime();
    }


    /**
     * 更新分销员表
     * 1.注册邀新的分销员
     * 2.订单下单的邀请分销员
     */
    private void updateDistributor(TradeVO tradeVO, BigDecimal amount, BigDecimal distributeAmount,
                                   BigDecimal inviteAmount, Integer inviteNum, Integer orderNum) {
        // 1.注册邀新的分销员
        updateRegisterDistributor(tradeVO, inviteAmount, inviteNum);
        // 2.订单下单的邀请分销员
        updateInviteDistributor(tradeVO, amount, distributeAmount, orderNum);
    }

    /**
     * 更新注册邀新的分销员信息
     *
     * @param tradeVO
     * @param inviteAmount
     * @param inviteNum
     */
    private void updateRegisterDistributor(TradeVO tradeVO, BigDecimal inviteAmount, Integer inviteNum) {

        //查询1.注册邀新的分销员
        DistributionInviteNewPageRequest inviteNewPageRequest = new DistributionInviteNewPageRequest();
        inviteNewPageRequest.setInvitedNewCustomerId(tradeVO.getBuyer().getId());
        DistributionInviteNewPageResponse inviteNewPage =
                distributionInviteNewQueryProvider.findDistributionInviteNewRecord(inviteNewPageRequest).getContext();
        if (inviteNewPage.getTotal() == 0 || CollectionUtils.isEmpty(inviteNewPage.getRecordList())) {
            return;
        }

        String customerId = inviteNewPage.getRecordList().get(0).getRequestCustomerId();
        // 1.查询分销员信息-获取分销员id
        DistributionCustomerVO distributionCustomer = distributionCustomerQueryProvider.getByCustomerId(
                DistributionCustomerByCustomerIdRequest.builder().customerId(customerId).build()
        ).getContext().getDistributionCustomerVO();

        if (Objects.isNull(distributionCustomer)) {
            log.info("会员ID:{},分销员信息不存在！", customerId);
            return;
        }

        // 1.查询分销员信息、分销设置
        DistributionSettingVO setting =
                distributionSettingQueryProvider.getSetting().getContext().getDistributionSetting();
        log.info("邀请人客户id:{} 尝试升级分销员。分销员的已经有效邀请了{}客户，分销设置是：{} 限制人数为：{}"
            ,distributionCustomer.getCustomerId(),distributionCustomer.getInviteAvailableCount(),setting.getLimitType(),setting.getInviteCount()
        );
        Integer inviteCount = setting.getInviteCount();
        //成为分销员
        boolean becomeDistributor = DistributionLimitType.EFFECTIVE == setting.getLimitType() && DefaultFlag.NO == distributionCustomer.getDistributorFlag()
                && (setting.getApplyType() == RecruitApplyType.REGISTER && inviteCount <= Integer.sum(distributionCustomer.getInviteAvailableCount(), inviteNum));


        // 更新1.注册邀新的分销员信息
        AfterSettleUpdateDistributorRequest request = initAfterSettleUpdateDistributorRequest();
        request.setDistributeId(distributionCustomer.getDistributionId());
        //邀新奖励金额
        request.setInviteAmount(inviteAmount);
        //邀新人数
        request.setInviteNum(inviteNum);
        DistributionLimitType distributionLimitType = distributionCacheService.getBaseLimitType();
        request.setBaseLimitType(Objects.nonNull(distributionLimitType) ? DefaultFlag.fromValue(distributionLimitType.toValue()) : null);
        request.setDistributorLevelVOList(distributionCacheService.getDistributorLevels());
        Boolean result = DefaultFlag.YES == distributionCustomer.getDistributorFlag() || becomeDistributor;
        request.setDistributorFlag(result);
        distributionCustomerSaveProvider.afterSettleUpdate(request);
        //同步es
        DistributionCustomerByIdRequest idRequest = DistributionCustomerByIdRequest.builder()
                .distributionId(distributionCustomer.getDistributionId())
                .build();
        EsDistributionCustomerByIdResponse idResponse = distributionCustomerQueryProvider.getByIdShowPhone(idRequest).getContext();
        DistributionCustomerShowPhoneVO distributionCustomerVO = idResponse.getDistributionCustomerVO();
        if (Objects.nonNull(distributionCustomerVO)) {
            List<DistributionCustomerShowPhoneVO> showPhoneVOList = Collections.singletonList(distributionCustomerVO);
            esDistributionCustomerProvider.add(new EsDistributionCustomerAddRequest(showPhoneVOList));
        }
        // distributionCustomer.setInviteAvailableCount(inviteNum);
        // this.checkAndUpgrade(distributionCustomer);
    }

    /**
     * 更新订单下单的邀请分销员信息
     *
     * @param tradeVO
     * @param amount
     * @param distributeAmount
     * @param orderNum
     */
    private void updateInviteDistributor(TradeVO tradeVO, BigDecimal amount, BigDecimal distributeAmount,
                                         Integer orderNum) {

        if (StringUtils.isBlank(tradeVO.getDistributorId())) {
            return;
        }

        // 更新2.订单下单的邀请分销员信息
        AfterSettleUpdateDistributorRequest request = initAfterSettleUpdateDistributorRequest();
        request.setDistributeId(tradeVO.getDistributorId());
        //减少的销售额
        request.setAmount(amount);
        // 如果本次分销订单返利
        if (tradeVO.getCommissionFlag()) {
            //发放的分销佣金
            request.setGrantAmount(distributeAmount);
            //原来预计的分销佣金
            request.setTotalDistributeAmount(tradeVO.getCommission());
        }
        //订单数 1或0
        request.setOrderNum(orderNum);
        DistributionLimitType distributionLimitType = distributionCacheService.getBaseLimitType();
        request.setBaseLimitType(Objects.nonNull(distributionLimitType) ? DefaultFlag.fromValue(distributionLimitType.toValue()) : null);
        request.setDistributorLevelVOList(distributionCacheService.getDistributorLevels());
        request.setDistributorFlag(Boolean.TRUE);
        distributionCustomerSaveProvider.afterSettleUpdate(request);


        //同步es
        DistributionCustomerByIdRequest idRequest = DistributionCustomerByIdRequest.builder()
                .distributionId(tradeVO.getDistributorId())
                .build();
        EsDistributionCustomerByIdResponse idResponse = distributionCustomerQueryProvider.getByIdShowPhone(idRequest).getContext();
        DistributionCustomerShowPhoneVO distributionCustomerVO = idResponse.getDistributionCustomerVO();
        if (Objects.nonNull(distributionCustomerVO)) {
            List<DistributionCustomerShowPhoneVO> showPhoneVOList = Collections.singletonList(distributionCustomerVO);
            esDistributionCustomerProvider.add(new EsDistributionCustomerAddRequest(showPhoneVOList));
        }
//        DistributionCustomerVO distributionCustomer = distributionCustomerQueryProvider.getByCustomerId(
//                DistributionCustomerByCustomerIdRequest.builder().customerId(tradeVO.getInviteeId()).build()
//        ).getContext().getDistributionCustomerVO();
//
//        if (Objects.isNull(distributionCustomer)) {
//            log.info("会员ID:{},分销员信息不存在！",tradeVO.getInviteeId());
//            return;
//        }
//
//        distributionCustomer.setSales(request.getAmount());
//        distributionCustomer.setCommission(request.getGrantAmount());
//
//        this.checkAndUpgrade(distributionCustomer);
    }


    /**
     * 初始化更新分销员表数据
     *
     * @return
     */
    private AfterSettleUpdateDistributorRequest initAfterSettleUpdateDistributorRequest() {
        AfterSettleUpdateDistributorRequest request = new AfterSettleUpdateDistributorRequest();
        //减少的销售额
        request.setAmount(BigDecimal.ZERO);
        //发放的分销佣金
        request.setGrantAmount(BigDecimal.ZERO);
        //原来预计的分销佣金
        request.setTotalDistributeAmount(BigDecimal.ZERO);
        //邀新奖励金额
        request.setInviteAmount(BigDecimal.ZERO);
        //邀新人数
        request.setInviteNum(0);
        //订单数 1或0
        request.setOrderNum(0);
        return request;
    }

}
