package com.wanmi.sbc.returnorder;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CreditOrderQueryRequest;
import com.wanmi.sbc.account.api.response.credit.CreditRepayPageResponse;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.empower.api.provider.channel.linkedmall.refund.LinkedMallRefundProvider;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallInitApplyRefundRequest;
import com.wanmi.sbc.empower.api.response.channel.linkedmall.LinkedMallInitApplyRefundResponse;
import com.wanmi.sbc.message.StoreMessageBizService;
import com.wanmi.sbc.order.api.provider.refund.RefundOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.refund.RefundOrderResponseByReturnOrderCodeRequest;
import com.wanmi.sbc.order.api.request.returnorder.*;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.response.refund.RefundOrderListReponse;
import com.wanmi.sbc.order.bean.dto.ReturnLogisticsDTO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.bean.enums.ReturnReason;
import com.wanmi.sbc.order.bean.enums.ReturnWay;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by sunkun on 2017/7/11.
 */
@Slf4j
@Tag(name = "ReturnOrderBaseController", description = "退单基本服务API")
@RestController
@Validated
@RequestMapping("/return")
public class ReturnOrderBaseController {

    @Autowired
    private ReturnOrderProvider returnOrderProvider;

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Autowired
    private RefundOrderQueryProvider refundOrderQueryProvider;

    @Autowired
    private LinkedMallRefundProvider linkedMallRefundProvider;

    @Autowired
    private CommonUtil commonUtil;
    @Resource
    private CreditRepayQueryProvider repayQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private StoreMessageBizService storeMessageBizService;


    /**
     * 分页查询 from ES
     *
     * @param request
     * @return
     */
    @Operation(summary = "分页查询 from ES")
    @RequestMapping(value = "page", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<ReturnOrderVO>> page(@RequestBody ReturnOrderPageRequest request) {
        request.setBuyerId(commonUtil.getOperatorId());
        Platform platform = commonUtil.getOperator().getPlatform();
        request.setPlatform(platform);
        if (TerminalSource.PC.equals(commonUtil.getTerminal())) {
            request.setFilterBuyCycle(Boolean.TRUE);
        }
        MicroServicePage<ReturnOrderVO> returnPage =
                returnOrderQueryProvider.page(request).getContext().getReturnOrderPage();
        return BaseResponse.success(returnPage);
    }

    /**
     * 查看退单详情
     *
     * @param rid/trade/todo/
     * @return
     */
    @Operation(summary = "查看退单详情")
    @Parameter( name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/{rid}", method = {RequestMethod.POST, RequestMethod.GET})
    public BaseResponse<ReturnOrderVO> findById(@PathVariable String rid) {
        ReturnOrderVO returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid).build())
                .getContext();
        CompanyVO company = returnOrder.getCompany();
        Long storeId = company.getStoreId();
        StoreVO store = storeQueryProvider.getById(StoreByIdRequest.builder()
                .storeId(storeId)
                .build()).getContext().getStoreVO();
        if (StoreType.O2O == store.getStoreType()) {
            company.setStoreType(store.getStoreType());
            returnOrder.setCompany(company);
            returnOrder.setReturnItems(returnOrder.getReturnItems().parallelStream()
                    .peek(returnItemVO -> returnItemVO.setPluginType(PluginType.O2O))
                    .collect(Collectors.toList()));
        }
        checkUnauthorized(rid, returnOrder);
        return BaseResponse.success(returnOrder);
    }

    /**
     * 查看退单附件
     *
     * @param rid
     * @return
     */
    @Operation(summary = "查看退单附件")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/images/{rid}", method = RequestMethod.GET)
    public BaseResponse<List<String>> images(@PathVariable String rid) {
        ReturnOrderVO returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid).build())
                .getContext();
        checkUnauthorized(rid, returnOrder);
        return BaseResponse.success(returnOrder.getImages());
    }

    /**
     * 查看退单商品清单
     *
     * @param rid
     * @return
     */
    @Operation(summary = "查看退单商品清单")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/returnItems/{rid}", method = RequestMethod.GET)
    public BaseResponse<List<ReturnItemVO>> returnItems(@PathVariable String rid) {
        ReturnOrderVO returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid).build())
                .getContext();
        checkUnauthorized(rid, returnOrder);
        return BaseResponse.success(returnOrder.getReturnItems());
    }

    /**
     * 查询退款物流
     *
     * @param rid
     * @return
     */
    @Operation(summary = "查询退款物流")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/returnLogistics/{rid}", method = RequestMethod.GET)
    public BaseResponse<ReturnLogisticsVO> returnLogistics(@PathVariable String rid) {
        ReturnOrderVO returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid).build())
                .getContext();
        checkUnauthorized(rid, returnOrder);
        return BaseResponse.success(returnOrder.getReturnLogistics());
    }

    /**
     * 查询退单退款记录
     *
     * @param rid
     * @return
     */
    @Operation(summary = "查询退单退款记录")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/refundOrder/{rid}", method = RequestMethod.GET)
    public BaseResponse<RefundOrderListReponse> refundOrder(@PathVariable String rid) {
        ReturnOrderVO returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid).build())
                .getContext();
        checkUnauthorized(rid, returnOrder);
        return refundOrderQueryProvider.getRefundOrderRespByReturnOrderCode(new RefundOrderResponseByReturnOrderCodeRequest(rid));

    }


    /**
     * 查找所有退货方式
     *
     * @return
     */
    @Operation(summary = "查找所有退货方式")
    @RequestMapping(value = "/ways", method = RequestMethod.GET)
    public BaseResponse<List<ReturnWay>> findReturnWay() {
        return BaseResponse.success(returnOrderQueryProvider.listReturnWay().getContext().getReturnWayList());
    }

    /**
     * 所有退货原因
     *
     * @return
     */
    @Operation(summary = "所有退货原因")
    @RequestMapping(value = "/reasons", method = RequestMethod.GET)
    public BaseResponse<List<ReturnReason>> findReturnReason() {
        return BaseResponse.success(returnOrderQueryProvider.listReturnReason().getContext().getReturnReasonList());
    }


    @Operation(summary = "linkedmall订单逆向渲染接口")
    @GetMapping("/findLinkedMallInitApplyRefundData")
    public BaseResponse<LinkedMallInitApplyRefundResponse> findLinkedMallInitApplyRefundData(LinkedMallInitApplyRefundRequest sbcInitApplyRefundRequest) {
        return linkedMallRefundProvider.initApplyRefund(sbcInitApplyRefundRequest);
    }


    /**
     * 查看退货订单详情和可退商品数
     *
     * @param tid
     * @return
     */
    @Operation(summary = "查看退货订单详情和可退商品数")
    @Parameter(name = "tid", description = "订单Id", required = true)
    @RequestMapping(value = "/trade/{tid}", method = RequestMethod.GET)
    public BaseResponse<TradeVO> tradeDetails(@PathVariable String tid) {
        TradeVO trade = returnOrderQueryProvider.queryCanReturnItemNumByTid(CanReturnItemNumByTidRequest.builder()
                .tid(tid).build()).getContext();
        if (!trade.getBuyer().getId().equals(commonUtil.getOperatorId())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050010, new Object[]{tid});
        }
        return BaseResponse.success(trade);
    }

    /**
     * 根据订单id查询已完成的退单
     *
     * @param tid
     * @return
     */
    @Operation(summary = "根据订单id查询已完成的退单")
    @Parameter(name = "tid", description = "订单Id", required = true)
    @RequestMapping(value = "/findCompletedByTid/{tid}", method = RequestMethod.GET)
    public BaseResponse<List<ReturnOrderVO>> findCompletedByTid(@PathVariable String tid) {
        String customerId = commonUtil.getOperatorId();
        List<ReturnOrderVO> returnOrders = returnOrderQueryProvider.listNotVoidByTid(ReturnOrderNotVoidByTidRequest
                .builder().tid(tid).build()).getContext().getReturnOrderList();
        if (returnOrders.stream().anyMatch(r -> !r.getBuyer().getId().equals(customerId))) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050006);
        }
        return BaseResponse.success(returnOrders);
    }

    /**
     * 根据订单id查询全量退单
     *
     * @param tid
     * @return
     */
    @Operation(summary = "根据订单id查询全量退单")
    @Parameter(name = "tid", description = "退单Id", required = true)
    @RequestMapping(value = "/find-all/{tid}", method = RequestMethod.GET)
    public BaseResponse<List<ReturnOrderVO>> findAllByTid(@PathVariable String tid) {
        //判断订单是否计入了账期，如果计入了账期，不允许作废
        Boolean settled =
                tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(tid).build()).getContext().getTradeVO().getHasBeanSettled();
        if (settled != null && settled) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050041);
        }
        String customerId = commonUtil.getOperatorId();
        List<ReturnOrderVO> returnOrders = returnOrderQueryProvider.listByTid(ReturnOrderListByTidRequest.builder().tid(tid)
                .build()).getContext().getReturnOrderList();
        if (returnOrders.stream().anyMatch(r -> !r.getBuyer().getId().equals(customerId))) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050006);
        }
        return BaseResponse.success(returnOrders);
    }

    /**
     * 填写物流信息
     *
     * @param rid
     * @param logistics
     * @return
     */
    @Operation(summary = "填写物流信息")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/deliver/{rid}", method = RequestMethod.POST)
    @MultiSubmit
    @GlobalTransactional
    public BaseResponse deliver(@PathVariable String rid, @RequestBody ReturnLogisticsDTO logistics) {
        if (StringUtils.isBlank(logistics.getCompany()) ||
                StringUtils.isBlank(logistics.getNo())) {
            return BaseResponse.builder().code(CommonErrorCodeEnum.K000009.getCode()).message("参数不正确").build();
        }
        ReturnOrderVO returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid).build())
                .getContext();
        checkUnauthorized(rid, returnOrder);
        BaseResponse response =
                returnOrderProvider.deliver(ReturnOrderDeliverRequest.builder().rid(rid).logistics(logistics)
                .operator(commonUtil.getOperator()).build());
        // ============= 处理商家/供应商的消息发送：待商家收货提醒 START =============
        storeMessageBizService.handleForReturnOrderWaitReceive(returnOrder);
        // ============= 处理商家/供应商的消息发送：待商家收货提醒 END =============
        return response;
    }


    /**
     * 取消退单
     *
     * @param rid
     * @param reason
     * @return
     */
    @Operation(summary = "取消退单")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/cancel/{rid}", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse cancel(@PathVariable String rid, @RequestParam("reason") String reason) {
        ReturnOrderVO returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid).build())
                .getContext();
        checkUnauthorized(rid, returnOrder);
        if (returnOrder.getReturnFlowState() != ReturnFlowState.INIT) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050012);
        }
        return returnOrderProvider.cancel(ReturnOrderCancelRequest.builder().rid(rid).remark(reason)
                .operator(commonUtil.getOperator()).build());
    }

    /**
     * 校验是否是授信还款中
     *
     * @param tid
     * @return
     */
    @Operation(summary = "校验是否是授信还款中")
    @Parameter(name = "tid", description = "订单Id", required = true)
    @RequestMapping(value = "/checkRepay/{tid}", method = RequestMethod.GET)
    public BaseResponse checkRepay(@PathVariable String tid) {
        String customerId = commonUtil.getOperatorId();
        List<ReturnOrderVO> returnOrders = returnOrderQueryProvider.listByTid(ReturnOrderListByTidRequest.builder()
                .tid(tid).build()).getContext().getReturnOrderList();
        if (returnOrders.stream().anyMatch(r -> !r.getBuyer().getId().equals(customerId))) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050006);
        }
        // 判断是否还款中订单
        BaseResponse<CreditRepayPageResponse> repayRes = repayQueryProvider
                .findRepayOrderByOrderId(CreditOrderQueryRequest.builder().orderId(tid).build());
        if (Objects.nonNull(repayRes.getContext())) {
            if(CreditRepayStatus.getCheckStatus().contains(repayRes.getContext().getRepayStatus())) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050076);
            }
        } else {
            log.error("ReturnOrderBaseController checkRepay called findRepayOrderByOrderId error,params:{},return:{}"
                    , tid, JSONObject.toJSONString(repayRes));
        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 根据订单id查询退单(过滤拒绝退款、拒绝收货、已作废)
     *
     * @param tid
     * @return
     */
    @Operation(summary = "根据订单id查询退单(过滤拒绝退款、拒绝收货、已作废)")
    @Parameter(name = "tid", description = "订单Id", required = true)
    @RequestMapping(value = "/findByTid/{tid}", method = RequestMethod.GET)
    public BaseResponse<List<ReturnOrderVO>> findByTid(@PathVariable String tid) {
        String customerId = commonUtil.getOperatorId();
        List<ReturnOrderVO> returnOrders = returnOrderQueryProvider.listByTid(ReturnOrderListByTidRequest.builder()
                .tid(tid).build()).getContext().getReturnOrderList();
        if (returnOrders.stream().anyMatch(r -> !r.getBuyer().getId().equals(customerId))) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050006);
        }
        // 判断是否还款中订单
        BaseResponse<CreditRepayPageResponse> repayRes = repayQueryProvider
                .findRepayOrderByOrderId(CreditOrderQueryRequest.builder().orderId(tid).build());
        if (Objects.nonNull(repayRes.getContext())) {
            if(CreditRepayStatus.getCheckStatus().contains(repayRes.getContext().getRepayStatus())) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050076);
            }
        } else {
            log.error("ReturnOrderBaseController findByTid called findRepayOrderByOrderId error,params:{},return:{}"
                    , tid, JSONObject.toJSONString(repayRes));
        }

        List<ReturnOrderVO> returnOrderVOList = returnOrders.stream().filter(o -> o.getReturnFlowState() != ReturnFlowState.REJECT_REFUND
                && o.getReturnFlowState() != ReturnFlowState.REJECT_RECEIVE && o.getReturnFlowState() != ReturnFlowState.VOID)
                .collect(Collectors.toList());
        //如果是拆分的退单，有部分完成退款，则退款完成的退单也过滤掉（避免拆分的退单中有的被驳回，有的是退款完成，导致前端不能再次申请退款）
        if (CollectionUtils.isNotEmpty(returnOrders) && StringUtils.isNotBlank(returnOrders.get(0).getPtid())) {
            List<ReturnOrderVO> completedReturnOrderList
                    = returnOrders.stream().filter(o -> o.getReturnFlowState() == ReturnFlowState.COMPLETED).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(completedReturnOrderList) && completedReturnOrderList.size() < returnOrders.size()) {
                returnOrderVOList = returnOrderVOList.stream().filter(o -> o.getReturnFlowState() != ReturnFlowState.COMPLETED).collect(Collectors.toList());
            }
        }
        return BaseResponse.success(returnOrderVOList);
    }

    private void checkUnauthorized(@PathVariable String rid, ReturnOrderVO returnOrder) {
        if (!returnOrder.getBuyer().getId().equals(commonUtil.getOperatorId())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050006);
        }
    }

}
