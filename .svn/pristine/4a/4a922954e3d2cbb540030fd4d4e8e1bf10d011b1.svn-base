package com.wanmi.sbc.job;

import com.wanmi.sbc.common.enums.NodeType;
import com.wanmi.sbc.common.enums.node.OrderProcessType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.marketing.bean.enums.BookingType;
import com.wanmi.sbc.message.notice.NoticeService;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.TradeListAllRequest;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.dto.TradeStateDTO;
import com.wanmi.sbc.order.bean.enums.PayState;
import com.wanmi.sbc.order.bean.vo.TradeItemVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 预售订单尾款支付通知定时任务
 */
@Component
@Slf4j
public class BookingSaleActivityJobController {

    @Autowired
    private TradeQueryProvider tradeQueryProvider;
    @Resource
    private NoticeService noticeService;

    @XxlJob(value = "bookingSaleActivityJobController")
    public void execute() throws Exception {
        TradeListAllRequest tradeListAllRequest = TradeListAllRequest.builder()
                .tradeQueryDTO(TradeQueryDTO.builder()
                        .bookingTailTime(LocalDateTime.now())
                        .tradeState(TradeStateDTO.builder().payState(PayState.PAID_EARNEST).build())
                        .isBookingSaleGoods(Boolean.TRUE)
                        .bookingType(BookingType.EARNEST_MONEY)
                        .build()).build();
        List<TradeVO> tradeVOS = tradeQueryProvider.listAll(tradeListAllRequest).getContext().getTradeVOList();
        if (CollectionUtils.isEmpty(tradeVOS)) {
            log.warn("=========暂无需要通知的预售活动信息===========");
            return;
        }

        List<TradeVO> tradeList = tradeVOS.stream().filter(a -> judgeHalfHourActivity(a.getTradeState().getTailStartTime()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tradeList)) {
            return;
        }

        tradeList.forEach(a -> {
            try {
                TradeItemVO tradeItem = a.getTradeItems().get(0);
                String customerId = a.getBuyer().getId();
                noticeService.sendMessage(NodeType.ORDER_PROGRESS_RATE.toValue(),
                        OrderProcessType.BOOKING_SALE.toValue(),
                        OrderProcessType.BOOKING_SALE.getType(), a.getId(), tradeItem.getSkuId(),
                        tradeItem.getSkuName(), customerId, tradeItem.getPic(), a.getTailNoticeMobile());
            } catch (Exception e) {
                log.error("消息处理失败,订单号:" + a.getId(), e);
            }
        });
    }

    public boolean judgeHalfHourActivity(LocalDateTime bookingTailStartTime) {
        return Duration.between(bookingTailStartTime, LocalDateTime.now()).toMinutes() < Constants.NUM_30;
    }
}
