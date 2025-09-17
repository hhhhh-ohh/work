package com.wanmi.sbc.job;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.TriggerNodeType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.empower.api.request.sellplatform.goods.PlatformSendMiniMsgRequest;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponInfoQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeQueryRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodeReturnByIdRequest;
import com.wanmi.sbc.marketing.api.request.coupon.CouponInfoByIdRequest;
import com.wanmi.sbc.marketing.bean.dto.CouponCodeDTO;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.marketing.bean.enums.GrouponOrderStatus;
import com.wanmi.sbc.marketing.bean.vo.CouponInfoVO;
import com.wanmi.sbc.mq.producer.ManagerBaseProducerService;
import com.wanmi.sbc.order.api.provider.payingmemberrecord.PayingMemberRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.GrouponInstanceProvider;
import com.wanmi.sbc.order.api.provider.trade.GrouponInstanceQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeSettingProvider;
import com.wanmi.sbc.order.api.request.payingmemberrecord.PayingMemberRecordPageRequest;
import com.wanmi.sbc.order.api.request.trade.GrouponInstanceByGrouponNoRequest;
import com.wanmi.sbc.order.api.request.trade.GrouponInstancePageRequest;
import com.wanmi.sbc.order.api.request.trade.OrderModifySendFlagRequest;
import com.wanmi.sbc.order.api.request.trade.TradeListAllRequest;
import com.wanmi.sbc.order.api.request.trade.TradeListByGrouponNoRequest;
import com.wanmi.sbc.order.api.response.payingmemberrecord.PayingMemberRecordPageResponse;
import com.wanmi.sbc.order.api.response.trade.GrouponInstancePageResponse;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.dto.TradeStateDTO;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.GrouponInstanceVO;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.xxl.job.core.handler.annotation.XxlJob;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时任务Handler（Bean模式）
 * 小程序订阅消息定时任务
 */
@Component
@Slf4j
public class MiniProgramSubscibeMsgJobHandler{
    @Autowired
    private TradeSettingProvider tradeSettingProvider;

    @Autowired
    private CouponCodeQueryProvider couponCodeQueryProvider;

    @Autowired
    private ManagerBaseProducerService managerBaseProducerService;

    @Autowired
    private CouponInfoQueryProvider couponInfoQueryProvider;

    @Autowired
    private GrouponInstanceProvider grouponInstanceProvider;

    @Autowired
    private CouponCodeProvider couponCodeProvider;

    @Autowired
    private GrouponInstanceQueryProvider grouponInstanceQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private PayingMemberRecordQueryProvider payingMemberRecordQueryProvider;

    /**
     * 五分钟执行一次
     */
    @XxlJob(value = "MiniProgramSubscibeMsgJobHandler")
    public void execute(String param) throws Exception {
        log.info("发送小程序订阅消息定时任务执行 {}", LocalDateTime.now());
        // 自动确认收货前24小时
        try {
            log.info("发送小程序订阅消息-自动确认收货前24小时开始");
            tradeSettingProvider.orderAutoReceiveSendMiniProgramMsg();
            log.info("发送小程序订阅消息-自动确认收货前24小时结束");
        } catch (Exception e){
            log.error("小程序订阅消息-自动确认收货前24小时", e);
        }

        // 优惠券过期前24小时
        try {
            log.info("发送小程序订阅消息-优惠券过期前24小时开始");
            CouponCodeQueryRequest couponCodeQueryRequest = CouponCodeQueryRequest.builder()
                    .endTime(LocalDate.now().atTime(23,59,59))
                    .useStatus(DefaultFlag.NO)
                    .delFlag(DeleteFlag.NO)
                    .couponExpiredSendFlag(Boolean.FALSE)
                    .build();
            couponCodeQueryRequest.setPageSize(Constants.NUM_1000);
            handleCoupon(couponCodeQueryRequest);
            log.info("发送小程序订阅消息-优惠券过期前24小时结束");
        } catch (Exception e) {
            log.error("小程序订阅消息-优惠券过期前24小时", e);
        }

        // 距离拼团结束还剩3小时，且未成团
        try {
            log.info("发送小程序订阅消息-距离拼团结束还剩3小时开始");
            GrouponInstancePageRequest request = GrouponInstancePageRequest.builder()
                    .grouponStatus(GrouponOrderStatus.WAIT).grouponExpiredSendFlag(Boolean.FALSE)
                    .endTime(LocalDateTime.now().plusHours(Constants.THREE)).build();
            request.setPageSize(Constants.NUM_1000);
            handleGroupon(request);
            log.info("发送小程序订阅消息-距离拼团结束还剩3小时结束");
        } catch (Exception e){
            log.error("小程序订阅消息-距离拼团结束还剩3小时", e);
        }

        // 尾款开始支付
        try {
            log.info("发送小程序订阅消息-尾款开始支付开始");
            TradeListAllRequest request = TradeListAllRequest.builder()
                    .tradeQueryDTO(TradeQueryDTO.builder()
                            .bookingTailTime(LocalDateTime.now())
                            .tradeState(TradeStateDTO.builder().payState(PayState.PAID_EARNEST).build())
                            .isBookingSaleGoods(Boolean.TRUE)
                            .bookingType(BookingType.EARNEST_MONEY)
                            .bookingStartSendFlag(Boolean.FALSE)
                            .build()).build();
            request.getTradeQueryDTO().setPageSize(Constants.NUM_1000);
            handleBookingStart(request);
            log.info("发送小程序订阅消息-尾款开始支付结束");
        } catch (Exception e){
            log.error("小程序订阅消息-尾款开始支付", e);
        }

        // 尾款支付超时提醒
        try {
            log.info("发送小程序订阅消息-尾款支付超时提醒开始");
            TradeListAllRequest request = TradeListAllRequest.builder()
                    .tradeQueryDTO(TradeQueryDTO.builder()
                            .bookingTailTime(LocalDateTime.now())
                            .tradeState(TradeStateDTO.builder().payState(PayState.PAID_EARNEST).build())
                            .isBookingSaleGoods(Boolean.TRUE)
                            .bookingType(BookingType.EARNEST_MONEY)
                            .bookingEndSendFlag(Boolean.FALSE)
                            .build()).build();
            request.getTradeQueryDTO().setPageSize(Constants.NUM_1000);
            handleBookingEnd(request);
            log.info("发送小程序订阅消息-尾款支付超时提醒结束");
        } catch (Exception e){
            log.error("小程序订阅消息-尾款支付超时提醒", e);
        }

        // 会员到期提醒
        try {
            log.info("发送小程序订阅消息-会员到期提醒开始");
            PayingMemberRecordPageRequest request = PayingMemberRecordPageRequest.builder()
                    .delFlag(DeleteFlag.NO).levelState(Constants.ZERO).expirationDateBegin(LocalDate.now().plusDays(Constants.ONE))
                    .expirationDateEnd(LocalDate.now().plusDays(Constants.ONE)).build();
            request.setPageSize(Constants.NUM_1000);
            handlePayingMember(request);
            log.info("发送小程序订阅消息-会员到期提醒结束");
        } catch (Exception e){
            log.error("小程序订阅消息-会员到期提醒", e);
        }
        log.info("发送小程序订阅消息定时任务执行结束 {}", LocalDateTime.now());
    }

    /**
     * 会员到期提醒
     */
    private void handlePayingMember(PayingMemberRecordPageRequest request){
        PayingMemberRecordPageResponse response = payingMemberRecordQueryProvider.page(request).getContext();
        MicroServicePage<PayingMemberRecordVO> payingMemberRecordVOPage = response.getPayingMemberRecordVOPage();
        List<PayingMemberRecordVO> payingMemberRecordVOList = payingMemberRecordVOPage.getContent();
        if(CollectionUtils.isNotEmpty(payingMemberRecordVOList)){
            payingMemberRecordVOList.forEach(payingMemberRecordVO -> {
                try {
                    // 服务名称
                    String payingMemberName = "会员到期提醒";
                    // 到期时间
                    String expirationDate = String.valueOf(payingMemberRecordVO.getExpirationDate());
                    managerBaseProducerService.sendMiniProgramSubscribeMessage(PlatformSendMiniMsgRequest.builder()
                            .triggerNodeId(TriggerNodeType.MEMBER_EXPIRATION).payingMemberName(payingMemberName)
                            .expirationDate(expirationDate).customerId(payingMemberRecordVO.getCustomerId()).build());
                }catch (Exception e){
                    log.error("会员到期提醒，订阅消息发送失败，e={}",  e.getMessage());
                }
            });
        }
        // 如果不是最后一页，设置下一页后递归该方法继续执行
        if (CollectionUtils.isNotEmpty(payingMemberRecordVOList)) {
            if (payingMemberRecordVOList.size() == request.getPageSize()){
                request.setPageNum(request.getPageNum()+Constants.ONE);
                handlePayingMember(request);
            }
        }
    }

    /**
     * 尾款支付超时提醒
     */
    private void handleBookingEnd(TradeListAllRequest request){
        List<TradeVO> tradeList = tradeQueryProvider.listAll(request).getContext().getTradeVOList();
        if (CollectionUtils.isEmpty(tradeList)) {
            log.warn("=========暂无需要通知的预售活动信息===========");
            return;
        }
        tradeList.forEach(tradeVO -> {
            try {
                // 更新发送状态
                tradeProvider.modifySendFlagById(OrderModifySendFlagRequest.builder().tid(tradeVO.getId()).bookingEndSendFlag(Boolean.TRUE).build());
                // 商品id
                String skuId = tradeVO.getTradeItems().get(0).getSkuId();
                // 商品名称
                String skuName = tradeVO.getTradeItems().get(0).getSkuName();
                // 付款金额
                String tailPrice = String.valueOf(tradeVO.getTradePrice().getTailPrice());
                // 尾款支付结束
                String tailEndTime = DateUtil.format(tradeVO.getTradeState().getTailEndTime(), DateUtil.FMT_TIME_1);

                managerBaseProducerService.sendMiniProgramSubscribeMessage(PlatformSendMiniMsgRequest.builder()
                        .triggerNodeId(TriggerNodeType.BALANCE_PAYMENT_OVERTIME).skuName(skuName).skuId(skuId).tradeId(tradeVO.getId())
                        .tailPrice(tailPrice).tailEndTime(tailEndTime).customerId(tradeVO.getBuyer().getId()).build());
            } catch (Exception e) {
                log.error("消息处理失败,订单号:" + tradeVO.getId(), e);
            }
        });

        // 如果不是最后一页，设置下一页后递归该方法继续执行
        if (CollectionUtils.isNotEmpty(tradeList)) {
            if (tradeList.size() == request.getTradeQueryDTO().getPageSize()){
                request.getTradeQueryDTO().setPageNum(request.getTradeQueryDTO().getPageNum()+Constants.ONE);
                handleBookingEnd(request);
            }
        }
    }

    /**
     * 尾款开始支付
     */
    private void handleBookingStart(TradeListAllRequest request){
        List<TradeVO> tradeList = tradeQueryProvider.listAll(request).getContext().getTradeVOList();
        if (CollectionUtils.isEmpty(tradeList)) {
            log.warn("=========暂无需要通知的预售活动信息===========");
            return;
        }
        tradeList.forEach(tradeVO -> {
            try {
                // 更新发送状态
                tradeProvider.modifySendFlagById(OrderModifySendFlagRequest.builder().tid(tradeVO.getId()).bookingStartSendFlag(Boolean.TRUE).build());
                // 商品id
                String skuId = tradeVO.getTradeItems().get(0).getSkuId();
                // 商品名称
                String skuName = tradeVO.getTradeItems().get(0).getSkuName();
                // 付款金额
                String tailPrice = String.valueOf(tradeVO.getTradePrice().getTailPrice());
                // 尾款支付结束
                String tailEndTime = DateUtil.format(tradeVO.getTradeState().getTailEndTime(), DateUtil.FMT_TIME_1);

                managerBaseProducerService.sendMiniProgramSubscribeMessage(PlatformSendMiniMsgRequest.builder()
                        .triggerNodeId(TriggerNodeType.BALANCE_PAYMENT).skuName(skuName).skuId(skuId).tradeId(tradeVO.getId())
                        .tailPrice(tailPrice).tailEndTime(tailEndTime).customerId(tradeVO.getBuyer().getId()).build());
            } catch (Exception e) {
                log.error("消息处理失败,订单号:" + tradeVO.getId(), e);
            }
        });

        // 如果不是最后一页，设置下一页后递归该方法继续执行
        if (CollectionUtils.isNotEmpty(tradeList)) {
            if (tradeList.size() == request.getTradeQueryDTO().getPageSize()){
                request.getTradeQueryDTO().setPageNum(request.getTradeQueryDTO().getPageNum()+Constants.ONE);
                handleBookingStart(request);
            }
        }
    }

    /**
     * 距离拼团结束还剩3小时，且未成团
     */
    private void handleGroupon(GrouponInstancePageRequest request){
        GrouponInstancePageResponse response = grouponInstanceQueryProvider.pageCriteria(request).getContext();
        MicroServicePage<GrouponInstanceVO> grouponInstanceVOS = response.getGrouponInstanceVOS();
        List<GrouponInstanceVO> grouponInstanceVOList = grouponInstanceVOS.getContent();
        if(CollectionUtils.isNotEmpty(grouponInstanceVOList)){
            grouponInstanceVOList.forEach(grouponInstanceVO -> {
                try {
                    String grouponNo = grouponInstanceVO.getGrouponNo();
                    grouponInstanceProvider.modifyGrouponExpiredSendFlagByGrouponNo(GrouponInstanceByGrouponNoRequest.builder().grouponNo(grouponNo).build());
                    List<TradeVO> tradeVOS =
                            tradeQueryProvider.getTradeByGrouponNo(TradeListByGrouponNoRequest.builder().grouponNo(grouponNo).build()).getContext().getTradeVOList();
                    if (CollectionUtils.isNotEmpty(tradeVOS)){
                        TradeVO tradeVO = tradeVOS.get(0);
                        String skuId = tradeVO.getTradeItems().get(0).getSkuId();
                        // 商品名称
                        String skuName = tradeVO.getTradeItems().get(0).getSkuName();
                        // 拼团价格
                        String price = String.valueOf(tradeVO.getTradeItems().get(0).getPrice());
                        // 剩余人数
                        String remainNum = String.valueOf(grouponInstanceVO.getGrouponNum() - grouponInstanceVO.getJoinNum());
                        // 剩余时间
                        long seconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), grouponInstanceVO.getEndTime());
                        String remainDate = getDate(seconds);
                        managerBaseProducerService.sendMiniProgramSubscribeMessage(PlatformSendMiniMsgRequest.builder()
                                .triggerNodeId(TriggerNodeType.BEFORE_GROUPON_END).skuName(skuName).skuId(skuId)
                                .price(price).remainNum(remainNum).remainDate(remainDate).customerId(grouponInstanceVO.getCustomerId()).build());
                    }
                }catch (Exception e){
                    log.error("距离拼团结束还剩3小时，订阅消息发送失败，e={}",  e.getMessage());
                }
            });
        }
        // 如果不是最后一页，设置下一页后递归该方法继续执行
        if (CollectionUtils.isNotEmpty(grouponInstanceVOList)) {
            if (grouponInstanceVOList.size() == request.getPageSize()){
                request.setPageNum(request.getPageNum()+Constants.ONE);
                handleGroupon(request);
            }
        }
    }

    /**
     * 转换秒为时分秒的形式
     *
     * @param seconds 待转换的秒
     * @return
     */
    public static String getDate(long seconds) {
        int h = (int) (seconds / 3600);
        int m = (int) ((seconds % 3600) / 60);
        int s = (int) ((seconds % 3600) % 60);
        return h + "小时" + m + "分" + s + "秒";
    }

    /**
     * 优惠券过期前24小时
     */
    private void handleCoupon(CouponCodeQueryRequest request){
        List<CouponCodeDTO> couponCodeDTOList =
                couponCodeQueryProvider.pageCouponCodeByCondition(request).getContext().getCouponCodeDTOPage();
        if(CollectionUtils.isNotEmpty(couponCodeDTOList)){
            //按照会员分组
            Map<String, List<CouponCodeDTO>> map = couponCodeDTOList.stream().collect(Collectors.groupingBy(CouponCodeDTO::getCustomerId));
            for (Map.Entry<String, List<CouponCodeDTO>> entry : map.entrySet()){
                List<CouponCodeDTO> couponCodeDTOS = entry.getValue();
                if(CollectionUtils.isNotEmpty(couponCodeDTOS)){
                    // 优惠券过期前24小时，发送订阅消息
                    try {
                        CouponCodeDTO couponCodeDTO = couponCodeDTOS.get(0);
                        couponCodeProvider.updateCouponExpiredSendFlagById(CouponCodeReturnByIdRequest.builder().couponCodeId(couponCodeDTO.getCouponCodeId()).build());
                        CouponInfoVO vo =
                                couponInfoQueryProvider.getById(CouponInfoByIdRequest.builder().couponId(couponCodeDTO.getCouponId()).build())
                                        .getContext();
                        // 有效期
                        String effectiveDate = DateUtil.format(couponCodeDTO.getEndTime(), DateUtil.FMT_TIME_1);
                        // 优惠力度
                        String denomination = String.valueOf(vo.getDenomination());
                        managerBaseProducerService.sendMiniProgramSubscribeMessage(PlatformSendMiniMsgRequest.builder()
                                .triggerNodeId(TriggerNodeType.COUPON_USAGE).couponName(vo.getCouponName())
                                .effectiveDate(effectiveDate).denomination(denomination).customerId(couponCodeDTO.getCustomerId()).build());
                    }catch (Exception e){
                        log.error("优惠券过期前24小时，订阅消息发送失败，e={}",  e.getMessage());
                    }
                }
            }
        }
        // 如果不是最后一页，设置下一页后递归该方法继续执行
        if (CollectionUtils.isNotEmpty(couponCodeDTOList)) {
            if(couponCodeDTOList.size() == request.getPageSize()){
                request.setPageNum(request.getPageNum()+ Constants.ONE);
                handleCoupon(request);
            }
        }
    }
}
