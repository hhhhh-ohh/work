package com.wanmi.sbc.order.trade.service;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.order.api.request.trade.CreditTradePageRequest;
import com.wanmi.sbc.order.api.response.trade.CreditTradeVOPageResponse;
import com.wanmi.sbc.order.bean.enums.FlowState;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.bean.vo.BuyerVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.order.returnorder.model.root.ReturnOrder;
import com.wanmi.sbc.order.trade.model.root.Trade;
import com.wanmi.sbc.order.trade.request.CreditTradeWhereCriteriaBuilder;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author houshuai
 * @date 2021/3/2 11:10
 * @description <p> 授信订单查询service </p>
 */
@Slf4j
@Service
public class CreditTradeQueryService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    private static final String COLLECTION_NAME = "trade";


    /**
     * 使用记录
     *
     * @param request
     * @return
     */
    public CreditTradeVOPageResponse findCreditUsedTrade(CreditTradePageRequest request) {
        CreditTradeVOPageResponse response = CreditTradeVOPageResponse.builder().build();
        //查询条件
        Criteria repayCriteria = CreditTradeWhereCriteriaBuilder.getRepayCriteria(request);
        Aggregation aggregationResult = this.getAggregationResult(request, repayCriteria);
        //取数据
        List<Trade> tradeList = mongoTemplate.aggregate(aggregationResult, COLLECTION_NAME, Trade.class).getMappedResults();
        if (CollectionUtils.isEmpty(tradeList)) {
            return response;
        }
        List<TradeVO> tradeVOList = KsBeanUtil.convert(tradeList, TradeVO.class);

        //取总数
        Aggregation aggregationCount = this.getAggregationCount(request);
        Map totalMap = mongoTemplate.aggregate(aggregationCount, COLLECTION_NAME, Map.class).getUniqueMappedResult();
        long total = NumberUtils.toLong(Objects.requireNonNull(totalMap).get("total").toString());
        MicroServicePage<TradeVO> microServicePage = new MicroServicePage<>(tradeVOList, request.getPageable(), total);
        response.setTradeVOList(microServicePage);
        return response;
    }

    /**
     * 待还款记录
     *
     * @param request
     * @return
     */
    public CreditTradeVOPageResponse findCreditRepayOrder(CreditTradePageRequest request) {
        Criteria repayCriteria = CreditTradeWhereCriteriaBuilder.getRepayCriteria(request);
        Query repayQuery = Query.query(repayCriteria);
        List<Trade> tradeList = mongoTemplate.find(repayQuery, Trade.class);
        if (CollectionUtils.isEmpty(tradeList)) {
            MicroServicePage<TradeVO> servicePage =
                    new MicroServicePage<>(Collections.emptyList(), request.getPageable(), NumberUtils.LONG_ZERO);
            return CreditTradeVOPageResponse.builder()
                    .tradeVOList(servicePage)
                    .build();
        }
        List<String> tidList = tradeList.stream().map(Trade::getId).collect(Collectors.toList());
        //根据订单查询退单
        List<ReturnOrder> returnOrderList = mongoTemplate.find(this.getQuery(tidList), ReturnOrder.class);
        if (CollectionUtils.isEmpty(returnOrderList)) {
            Criteria criteria = this.getCriteria(tidList);
            return this.getRepayResult(request, criteria);
        }
        //取订单中不包含全部退货退款数据
        Collection<String> repayTidList = this.repayTidList(tradeList, returnOrderList);
        Criteria criteria = this.getCriteria(repayTidList);
        return this.getRepayResult(request, criteria);
    }

    /**
     * 查询结果和total
     *
     * @param request
     * @param criteria
     * @return
     */
    private CreditTradeVOPageResponse getRepayResult(CreditTradePageRequest request, Criteria criteria) {
        Aggregation aggregationResult = this.getAggregationResult(request, criteria);
        List<Trade> tradeList = mongoTemplate.aggregate(aggregationResult, COLLECTION_NAME, Trade.class).getMappedResults();
        long total = mongoTemplate.count(Query.query(criteria), Trade.class);
        List<TradeVO> tradeVOList = KsBeanUtil.convert(tradeList, TradeVO.class);
        //获取会员账号
        String customerAccount = this.getCustomerAccount(request);
        List<TradeVO> newList = tradeVOList.stream()
                .peek(trade -> {
                    BuyerVO buyer = trade.getBuyer();
                    //不需要脱敏展示
                    buyer.setAccount(customerAccount);
                }).collect(Collectors.toList());
        MicroServicePage<TradeVO> microServicePage = new MicroServicePage<>(newList, request.getPageable(), total);
        return CreditTradeVOPageResponse.builder()
                .tradeVOList(microServicePage)
                .build();
    }

    /**
     * 取订单中不包含全部退货退款数据
     *
     * @param tradeList
     * @param returnOrderList
     * @return
     */
    private Collection<String> repayTidList(List<Trade> tradeList, List<ReturnOrder> returnOrderList) {

        Set<String> ids = new HashSet<>();

        if (CollectionUtils.isNotEmpty(returnOrderList)) {
            Map<String, List<ReturnOrder>> map =
                    returnOrderList.stream().collect(Collectors.groupingBy(ReturnOrder::getTid));
            tradeList.forEach(tradeVO -> {
                Boolean canRepay = Boolean.FALSE;
                List<ReturnOrder> returnOrderVOList = map.get(tradeVO.getId());
                if (CollectionUtils.isNotEmpty(returnOrderVOList)) {
                    // 查询是否存在已作废、拒绝退款、拒绝退货、退款失败的退单
                    Optional<ReturnOrder> canCheckObj = returnOrderVOList.stream().filter(item ->
                            item.getReturnFlowState() == ReturnFlowState.VOID
                                    || item.getReturnFlowState() == ReturnFlowState.REJECT_REFUND
                                    // 退款失败,拒绝收货
                                    || item.getReturnFlowState() == ReturnFlowState.REFUND_FAILED
                                    || item.getReturnFlowState() == ReturnFlowState.REJECT_RECEIVE
                    ).findFirst();

                    // 查询是否存在已完成的退单
                    Optional<ReturnOrder> returnComplete = returnOrderVOList.stream().filter(item ->
                            item.getReturnFlowState() == ReturnFlowState.COMPLETED
                    ).findFirst();
                    // 如果订单状态为已完成 如果存在符合条件的退单
                    if (tradeVO.getTradeState().getFlowState() == FlowState.COMPLETED || tradeVO.getTradeState().getFlowState() == FlowState.VOID) {
                        // 如果存在符合条件的退单
                        if (canCheckObj.isPresent()) {
                            // 则可以选
                            canRepay = Boolean.TRUE;
                        }
                        // 订单已完成 且 退单已完成
                        if (returnComplete.isPresent()) {
                            // 退单总金额
                            BigDecimal returnPrice = returnOrderVOList.stream().filter(returnOrderVO -> returnOrderVO.getReturnFlowState() == ReturnFlowState.COMPLETED)
                                    .map(returnOrder -> returnOrder.getReturnPrice().getActualReturnPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                            // 退单件数
//                            Long returnNums = returnOrderVOList.stream().filter(returnOrderVO -> returnOrderVO.getReturnFlowState() == ReturnFlowState.COMPLETED)
//                                    .mapToLong(returnOrderVO -> returnOrderVO.getReturnItems().stream().map(ReturnItemVO::getNum).reduce(0, (a, b) -> a + b)).sum();
                            // 订单总金额
                            BigDecimal orderPrice = tradeVO.getTradePrice().getTotalPrice();
                            // 订单件数
//                            Long orderNums = tradeVO.getTradeItems().stream().map(TradeItemVO::getNum).reduce(0L, (a, b) -> a + b);
                            // 金额差异：订单金额-退单金额
                            BigDecimal canRepayPrice = orderPrice.subtract(returnPrice).setScale(2, RoundingMode.DOWN);
                            // 可还款金额
                            tradeVO.setCanRepayPrice(canRepayPrice);

                            // 订单总金额 大于 退单总金额 则是 可以选择
                            if (canRepayPrice.compareTo(BigDecimal.ZERO) > 0) {
                                // 则可以选
                                canRepay = Boolean.TRUE;
                            }else{
                                canRepay = Boolean.FALSE;
                            }
                        }
                    }

                    // 查询是否存在正在进行中的退单(不是作废,不是拒绝退款,不是已结束)
                    Optional<ReturnOrder> returningOrder = returnOrderVOList.stream().filter(item ->
                            item.getReturnFlowState() != ReturnFlowState.VOID
                                    && item.getReturnFlowState() != ReturnFlowState.REJECT_REFUND
                                    && item.getReturnFlowState() != ReturnFlowState.COMPLETED
                                    // 不是退款失败,不是拒绝收货
                                    && item.getReturnFlowState() != ReturnFlowState.REFUND_FAILED
                                    && item.getReturnFlowState() != ReturnFlowState.REJECT_RECEIVE
                    ).findFirst();
                    if (returningOrder.isPresent()) {
                        canRepay = Boolean.TRUE;
                    }
                } else {
                    canRepay = Boolean.TRUE;
                }
                if(canRepay){
                    ids.add(tradeVO.getId());
                }
            });
        }

        return ids;
    }

    /**
     * 获取会员账号
     * @param pageRequest
     * @return
     */
    private String getCustomerAccount(CreditTradePageRequest pageRequest) {
        CustomerGetByIdRequest request = new CustomerGetByIdRequest();
        request.setCustomerId(pageRequest.getCustomerId());
        CustomerGetByIdResponse response = customerQueryProvider.getCustomerById(request).getContext();
        return response.getCustomerAccount();
    }

    /**
     * mongo 查询全部订单条件
     *
     * @param tidList
     * @return
     */
    private Query getQuery(Collection<String> tidList) {
        Criteria returnCriteria = new Criteria();
        returnCriteria.and("tid").in(tidList);
        return Query.query(returnCriteria);
    }

    /**
     * Criteria
     *
     * @param tidList
     * @return
     */
    private Criteria getCriteria(Collection<String> tidList) {
        Criteria returnCriteria = new Criteria();
        returnCriteria.and(Fields.UNDERSCORE_ID).in(tidList);
        return returnCriteria;
    }


    /**
     * 两表联查数据
     *
     * @param request
     * @return
     */
    private Aggregation getAggregationResult(CreditTradePageRequest request, Criteria criteria) {
        //以下Aggregation操作请勿更换顺序
        long skip = request.getPageNum() * request.getPageSize();
        return Aggregation.newAggregation(
              /*  //关联表
                Aggregation.lookup("returnOrder", Fields.UNDERSCORE_ID, "tid", "returnOrder"),*/
                //查询条件
                Aggregation.match(criteria),
                //排序
                Aggregation.sort(Sort.Direction.DESC, "tradeState.payTime"),
                //分页
                Aggregation.skip(skip),

                Aggregation.limit(request.getPageSize())
        );
    }

    /**
     * 两表联查总量
     *
     * @param request
     * @return
     */
    private Aggregation getAggregationCount(CreditTradePageRequest request) {
        Criteria criteria = CreditTradeWhereCriteriaBuilder.getRepayCriteria(request);
        //以下Aggregation操作请勿更换顺序
        return Aggregation.newAggregation(
                //查询条件
                Aggregation.match(criteria),
                Aggregation.count().as("total")
        );
    }

}
