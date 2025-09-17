package com.wanmi.sbc.job;

import com.wanmi.sbc.job.service.DistributionTaskTempService;
import com.wanmi.sbc.job.service.DistributionTempJobService;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderByConditionRequest;
import com.wanmi.sbc.order.api.request.trade.TradePageCriteriaRequest;
import com.wanmi.sbc.order.api.response.returnorder.ReturnOrderByConditionResponse;
import com.wanmi.sbc.order.api.response.trade.TradePageCriteriaResponse;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.bean.vo.DistributionTaskTempVO;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 定时任务Handler
 * 处理分销临时订单发放邀新奖励、分销佣金
 */
@Component
@Slf4j
public class DistributionTempJobHandler {

    @Value("${distribution.temp.task.bath.num}")
    int num;
    @Autowired
    private DistributionTaskTempService distributionTaskTempService;
    @Autowired
    private TradeQueryProvider tradeQueryProvider;
    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Autowired
    private DistributionTempJobService distributionTempJobService;


    /**
     * 凌晨两点执行
     */
    @XxlJob(value = "distributionTempJobHandler")
    public void execute() throws Exception {
        XxlJobHelper.log("DistributionTempTask定时任务执行开始： " + LocalDateTime.now());
        log.info("DistributionTempTask定时任务执行开始：{}", LocalDateTime.now());
        boolean flag = false;//是否循环
        int total = 0;
        do {
            //1、扫描临时表，获取数据；
            List<DistributionTaskTempVO> list = distributionTaskTempService.queryData(num);
            total += list.size();
            if (CollectionUtils.isEmpty(list)) {
                log.info("DistributionTempTask定时任务执行结束：{}", LocalDateTime.now());
                return;
            } else if (list.size() >= num) {
                flag = true;
            }
            log.info("DistributionTempTask定时任务执行： 1、扫描临时表，获取数据；数据大小为--->{}", list.size());
            //2、获得对应的订单数据
            Map<String, TradeVO> orderMap = this.getOrderList(list);
            log.info("获得对应的订单数据:{}", orderMap);

            for (DistributionTaskTempVO taskTemp : list) {
                String orderId = taskTemp.getOrderId();
                TradeVO trade = orderMap.get(orderId);
                if (Objects.nonNull(trade)) {
                    //2.1.2、获取订单关联的退单信息（退单状态为：已完成）
                    List<ReturnOrderVO> returnOrderList = this.getReturnOrderList(trade.getId());
                    //为了使分布式事务生效，这里需要走代理调用
                    try {
                        distributionTempJobService.deal(taskTemp, trade, returnOrderList);
                    } catch (Exception e) {
                        log.error("订单id:{}执行邀新奖励、分销佣金失败", trade.getId(), e);
                    }
                } else {
                    distributionTaskTempService.deleteById(taskTemp.getId()); // 清除有问题数据，防止死循环
                }
            }
        } while (flag);
        log.info("DistributionTempTask定时任务执行结束：{}, 处理总数为：{}", LocalDateTime.now(), total);
        XxlJobHelper.log("DistributionTempTask定时任务执行结束：{}, 处理总数为：{}", LocalDateTime.now(), total);
    }

    /**
     * 获取订单列表
     */
    private Map<String, TradeVO> getOrderList(List<DistributionTaskTempVO> list) {
        List<String> orderIds = list.stream().map(DistributionTaskTempVO::getOrderId).distinct().collect(Collectors.toList());
        TradeQueryDTO queryDTO = new TradeQueryDTO();
        queryDTO.setIds(orderIds.stream().toArray(String[]::new));
        queryDTO.setPageSize(orderIds.size());
        queryDTO.setPageNum(0);
        TradePageCriteriaResponse response =
                tradeQueryProvider.pageCriteria(TradePageCriteriaRequest.builder().tradePageDTO(queryDTO).build()).getContext();
        List<TradeVO> tradeResponse = response.getTradePage().getContent();
        Map<String, TradeVO> result = tradeResponse.stream().collect(
                Collectors.toMap(TradeVO::getId, tradeVO -> tradeVO, (oldValue, newValue) -> oldValue));
        return result;
    }

    /**
     * 根据订单id获取退单列表
     */
    private List<ReturnOrderVO> getReturnOrderList(String orderId) {
        log.info("根据订单id:{} 获取退单列表", orderId);
        ReturnOrderByConditionRequest request = new ReturnOrderByConditionRequest();
        request.setTid(orderId);
        ReturnOrderByConditionResponse response = returnOrderQueryProvider.listByCondition(request).getContext();
        List<ReturnOrderVO> returnOrderList = response.getReturnOrderList();
        log.info("根据订单id:{} 获取退单列表:{}", orderId, returnOrderList);
        //过滤掉已作废的退单
        List<ReturnOrderVO> newReturnOrderList =
                returnOrderList.stream().filter(item -> item.getReturnFlowState() != ReturnFlowState.VOID).collect(Collectors.toList());
        log.info("根据订单id:{} (过滤)获取退单列表:{}", orderId, newReturnOrderList);
        return newReturnOrderList;
    }

}
