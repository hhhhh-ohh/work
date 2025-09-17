package com.wanmi.sbc.returnorder;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CreditOrderQueryRequest;
import com.wanmi.sbc.account.api.response.credit.CreditRepayPageResponse;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByCompanyIdRequest;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelRefundProvider;
import com.wanmi.sbc.empower.api.provider.channel.linkedmall.refund.LinkedMallRefundProvider;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelRefundQueryStatusRequest;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallInitApplyRefundRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelRefundQueryStatusResponse;
import com.wanmi.sbc.empower.api.response.channel.linkedmall.LinkedMallInitApplyRefundResponse;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.empower.bean.enums.TradeStatus;
import com.wanmi.sbc.goods.api.provider.goodstobeevaluate.GoodsTobeEvaluateSaveProvider;
import com.wanmi.sbc.goods.api.provider.storetobeevaluate.StoreTobeEvaluateSaveProvider;
import com.wanmi.sbc.goods.api.request.goodstobeevaluate.GoodsTobeEvaluateQueryRequest;
import com.wanmi.sbc.goods.api.request.storetobeevaluate.StoreTobeEvaluateQueryRequest;
import com.wanmi.sbc.miniprogramsubscribe.MiniProgramSubscribeService;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.refund.RefundOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.small.SmallProvider;
import com.wanmi.sbc.order.api.provider.trade.ProviderTradeQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.paytraderecord.RefundResultByOrdercodeRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderByReturnOrderCodeRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderResponseByReturnOrderCodeRequest;
import com.wanmi.sbc.order.api.request.returnorder.*;
import com.wanmi.sbc.order.api.request.small.ReturnSmallOrderRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.response.refund.RefundOrderByReturnCodeResponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderListReponse;
import com.wanmi.sbc.order.api.response.returnorder.ReturnOrderByIdResponse;
import com.wanmi.sbc.order.bean.dto.RefundOrderDTO;
import com.wanmi.sbc.order.bean.dto.ReturnLogisticsDTO;
import com.wanmi.sbc.order.bean.dto.ReturnOrderDTO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.enums.ReturnFlowState;
import com.wanmi.sbc.order.bean.enums.ReturnReason;
import com.wanmi.sbc.order.bean.enums.ReturnWay;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.returnorder.convert.Remedy2ReturnOrder;
import com.wanmi.sbc.returnorder.request.*;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.refundcause.RefundCauseQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.request.refundcause.RefundCauseQueryRequest;
import com.wanmi.sbc.setting.api.response.TradeConfigGetByTypeResponse;
import com.wanmi.sbc.setting.api.response.refundcause.RefundCauseQueryOneResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.setting.bean.enums.SettingErrorCodeEnum;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 退货
 * Created by jinwei on 20/4/2017.
 */
@Slf4j
@Tag(name = "ReturnOrderController", description = "退货 Api")
@RestController
@Validated
@RequestMapping("/return")
public class ReturnOrderController {

    @Autowired
    private ReturnOrderProvider returnOrderProvider;

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private RefundOrderQueryProvider refundOrderQueryProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private ProviderTradeQueryProvider providerTradeQueryProvider;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GoodsTobeEvaluateSaveProvider goodsTobeEvaluateSaveProvider;

    @Autowired
    private StoreTobeEvaluateSaveProvider storeTobeEvaluateSaveProvider;

    @Autowired
    private ChannelRefundProvider linkedMallReturnOrderQueryProvider;

    @Autowired
    private LinkedMallRefundProvider linkedMallRefundProvider;

    @Resource
    private CreditRepayQueryProvider repayQueryProvider;

    @Autowired
    private PayTradeRecordQueryProvider payTradeRecordQueryProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private RefundCauseQueryProvider refundCauseQueryProvider;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Autowired
    private MiniProgramSubscribeService miniProgramSubscribeService;
    @Operation(summary = "查询退单")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/{rid}", method = {RequestMethod.GET, RequestMethod.POST})
    @ReturnSensitiveWords(functionName = "f_return_order_detail_sign_word")
    public BaseResponse<ReturnOrderVO> findById(@PathVariable String rid) {
        ReturnOrderVO returnOrder;
        if (commonUtil.getOperator().getPlatform() == Platform.PLATFORM) {
            returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid)
                    .build()).getContext();
        } else {
            returnOrder = checkOperatorByReturnOrder(rid);
        }
        String accountName = employeeQueryProvider.getByCompanyId(
                EmployeeByCompanyIdRequest.builder().companyInfoId(returnOrder.getCompany().getCompanyInfoId()).build()
        ).getContext().getAccountName();
        //如果退单是待审核，查询boss退单配置
        if (returnOrder.getReturnFlowState() == ReturnFlowState.INIT) {
            TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
            request.setConfigType(ConfigType.ORDER_SETTING_REFUND_AUTO_AUDIT);
            TradeConfigGetByTypeResponse config  = auditQueryProvider.getTradeConfigByType(request).getContext();
            JSONObject content = JSON.parseObject(config.getContext());
            Integer day = content.getObject("day", Integer.class);
            Period period= Period.between(LocalDate.now(), returnOrder.getCreateTime().toLocalDate().plusDays(day));
            returnOrder.setAuditDays(period.getDays());
        }
        returnOrder.getCompany().setAccountName(accountName);
        //判断订单会员是否注销
        returnOrder.setLogOutStatus(
                customerCacheService.getCustomerLogOutStatus(returnOrder.getBuyer().getId())
        );

        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(returnOrder.getReturnEventLogs())){
            returnOrder.getReturnEventLogs().forEach(returnEventLogVO -> {
                if (Objects.equals(Platform.CUSTOMER,returnEventLogVO.getOperator().getPlatform())){
                    returnEventLogVO.setLogOutStatus(
                            customerCacheService
                                    .getCustomerLogOutStatus(returnEventLogVO.getOperator().getUserId()
                            )
                    );
                }
            });
        }

        return BaseResponse.success(returnOrder);
    }

    /**
     * 导出退单
     *
     * @param encrypted
     */
    @Operation(summary = "导出退单")
    @EmployeeCheck
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse exportByParams(@PathVariable String encrypted,
                               ReturnOrderPageRequest request) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted));
        ReturnExportRequest returnExportRequest = JSON.parseObject(decrypted, ReturnExportRequest.class);
        returnExportRequest.setEmployeeIds(request.getEmployeeIds());

        Operator operator = commonUtil.getOperator();
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setAdminId(operator.getAdminId());
        exportDataRequest.setPlatform(commonUtil.getOperator().getPlatform());
        exportDataRequest.setParam(JSONObject.toJSONString(returnExportRequest));
        exportDataRequest.setTypeCd(ReportType.BUSINESS_RETURN_ORDER);
        exportDataRequest.setBuyAnyThirdChannelOrNot(commonUtil.buyAnyThirdChannelOrNot());
        exportDataRequest.setOperator(operator);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "退单方式查询")
    @RequestMapping(value = "/ways", method = RequestMethod.GET)
    public BaseResponse<List<ReturnWay>> findReturnWay() {
        return BaseResponse.success(returnOrderQueryProvider.listReturnWay().getContext().getReturnWayList());
    }

    @Operation(summary = "退单原因查询")
    @RequestMapping(value = "/reasons", method = RequestMethod.GET)
    public BaseResponse<List<ReturnReason>> findReturnReason() {
        return BaseResponse.success(returnOrderQueryProvider.listReturnReason().getContext().getReturnReasonList());
    }


    @Operation(summary = "linkedmall订单逆向渲染接口")
    @GetMapping("/findLinkedMallInitApplyRefundData")
    public BaseResponse<LinkedMallInitApplyRefundResponse> findLinkedMallInitApplyRefundData(LinkedMallInitApplyRefundRequest request) {
        return linkedMallRefundProvider.initApplyRefund(request);
    }

    /**
     * 审核
     *
     * @param rid
     * @return
     */
    @Operation(summary = "审核")
    @Parameters({
            @Parameter(name = "rid", description = "退单Id", required = true),
            @Parameter(name = "addressId", description = "退货收货地址Id")
    })
    @RequestMapping(value = {"/audit/{rid}", "/audit/{rid}/{addressId}"}, method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse audit(@PathVariable String rid, @PathVariable(value = "addressId", required = false) String addressId) {
        List<ReturnOrderVO> returnOrderVOList = returnOrderQueryProvider.listByCondition(
                ReturnOrderByConditionRequest.builder().rids(new String[]{rid}).build()).getContext().getReturnOrderList();
        if (CollectionUtils.isEmpty(returnOrderVOList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ReturnOrderVO returnOrderVO = returnOrderVOList.get(0);
        //第三方渠道订单，且未支付失败
        if ((!Boolean.TRUE.equals(returnOrderVO.getThirdPlatformPayErrorFlag()))
                && Objects.nonNull(returnOrderVO.getThirdPlatformType())
                && CollectionUtils.isNotEmpty(returnOrderVO.getReturnItems())) {
            String subLmOrderId = returnOrderVO.getReturnItems().get(0).getThirdPlatformSubOrderId();
            if (StringUtils.isNotBlank(subLmOrderId)) {
                ChannelRefundQueryStatusRequest detailRequest = new ChannelRefundQueryStatusRequest();
                detailRequest.setBizUid(returnOrderVO.getBuyer().getId());
                detailRequest.setSubChannelOrderId(subLmOrderId);
                detailRequest.setThirdPlatformType(returnOrderVO.getThirdPlatformType());
                ChannelRefundQueryStatusResponse detail =
                        linkedMallReturnOrderQueryProvider.queryRefundStatus(detailRequest).getContext();
                if (detail != null && !Boolean.TRUE.equals(detail.getSellerAgreed())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[]{"linkedMall商家没有同意退款"});
                }
            }
        }

        BaseResponse response =
                returnOrderProvider.audit(ReturnOrderAuditRequest.builder().rid(rid).addressId(addressId).operator(commonUtil.getOperator()).build());

        // 异步处理小程序订阅消息发送
        miniProgramSubscribeService.dealAuditMiniProgramSubscribeMsg(returnOrderVO, Boolean.TRUE);

        return response;
    }

    /**
     * 批量审核
     *
     * @param returnRequest
     * @return
     */
    @Operation(summary = "批量审核")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> batchAudit(@RequestBody ReturnRequest returnRequest) {
        returnRequest.getRids().forEach(rid -> audit(rid, returnRequest.getAddressId()));
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 校验退单退款状态
     *
     * @param rid
     * @return
     */
    @Operation(summary = "校验退单退款状态")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/verifyRefundStatus/{rid}", method = RequestMethod.GET)
    @GlobalTransactional
    public BaseResponse verifyRefundStatus(@PathVariable String rid) {
        ReturnOrderVO returnOrder;
        if (commonUtil.getOperator().getPlatform().equals(Platform.PLATFORM)) {
            returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid)
                    .build()).getContext();
        } else {
            returnOrder = checkOperatorByReturnOrder(rid);
        }
        TradeStatus tradeStatus = payTradeRecordQueryProvider.getRefundResponseByOrdercode(new RefundResultByOrdercodeRequest
                (returnOrder.getTid(), returnOrder.getId())).getContext().getTradeStatus();
        if (tradeStatus != null) {
            if (tradeStatus == TradeStatus.PROCESSING) {
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060033);
            } else if (tradeStatus == TradeStatus.SUCCEED) {
                RefundOrderByReturnCodeResponse refundOrder =
                        refundOrderQueryProvider.getByReturnOrderCode(new RefundOrderByReturnOrderCodeRequest(returnOrder.getId())).getContext();
                Operator operator = Operator.builder().ip(HttpUtil.getIpAddr()).adminId("1").name("system")
                        .account("system").platform(Platform.BOSS).build();
                returnOrderProvider.onlineRefund(ReturnOrderOnlineRefundRequest.builder().operator(operator)
                        .returnOrder(KsBeanUtil.convert(returnOrder, ReturnOrderDTO.class))
                        .refundOrder(KsBeanUtil.convert(refundOrder, RefundOrderDTO.class)).build());
                throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060032);
            }
        }
        return BaseResponse.SUCCESSFUL();
    }


    @Operation(summary = "退单派送")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/deliver/{rid}", method = RequestMethod.POST)
    @MultiSubmit
    @GlobalTransactional
    public BaseResponse deliver(@PathVariable String rid, @RequestBody ReturnLogisticsDTO logistics) {
        checkOperatorByReturnOrder(rid);
        return returnOrderProvider.deliver(ReturnOrderDeliverRequest.builder().rid(rid).logistics(logistics)
        .operator(commonUtil.getOperator()).build());
    }

    /**
     * 收货
     *
     * @param rid
     * @return
     */
    @Operation(summary = "收货")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/receive/{rid}", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse receive(@PathVariable String rid) {
        ReturnOrderVO returnOrder = checkOperatorByReturnOrder(rid);

        returnOrder.getReturnItems().forEach(goodsInfo -> {
            goodsTobeEvaluateSaveProvider.deleteByOrderAndSku(GoodsTobeEvaluateQueryRequest.builder()
                    .orderNo(returnOrder.getTid()).goodsInfoId(goodsInfo.getSkuId()).build());
        });

        //linkedMall订单，且未支付失败
        if ((!Boolean.TRUE.equals(returnOrder.getThirdPlatformPayErrorFlag()))
                && Objects.nonNull(returnOrder.getThirdPlatformType())
                && CollectionUtils.isNotEmpty(returnOrder.getReturnItems())) {
            String subLmOrderId = returnOrder.getReturnItems().get(0).getThirdPlatformSubOrderId();
            if (StringUtils.isNotBlank(subLmOrderId)) {
                ChannelRefundQueryStatusRequest detailRequest = new ChannelRefundQueryStatusRequest();
                detailRequest.setBizUid(returnOrder.getBuyer().getId());
                detailRequest.setSubChannelOrderId(subLmOrderId);
                detailRequest.setThirdPlatformType(returnOrder.getThirdPlatformType());
                ChannelRefundQueryStatusResponse detail =
                        linkedMallReturnOrderQueryProvider.queryRefundStatus(detailRequest).getContext();
                if (detail != null && !Boolean.TRUE.equals(detail.getSellerReceived())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[]{"linkedMall商家没有同意收货"});
                }
            }
        }

        storeTobeEvaluateSaveProvider.deleteByOrderAndStoreId(StoreTobeEvaluateQueryRequest.builder()
                .storeId(returnOrder.getCompany().getStoreId()).orderNo(returnOrder.getTid()).build());
        return returnOrderProvider.receive(ReturnOrderReceiveRequest.builder().operator(commonUtil.getOperator())
                .rid(rid).build());
    }

    /**
     * 批量收货
     *
     * @param returnRequest
     * @return
     */
    @Operation(summary = "批量收货")
    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public BaseResponse batchReceive(@RequestBody ReturnRequest returnRequest) {
        returnRequest.getRids().forEach(this::receive);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "退单拒绝收货")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/receive/{rid}/reject", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse rejectReceive(@PathVariable String rid, @RequestBody RejectRequest request) {
        List<ReturnOrderVO> returnOrderVOList = returnOrderQueryProvider.listByCondition(
                ReturnOrderByConditionRequest.builder().rids(new String[]{rid}).build()).getContext().getReturnOrderList();
        if (CollectionUtils.isEmpty(returnOrderVOList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ReturnOrderVO returnOrder = returnOrderVOList.get(0);
        //越权校验
        if (Objects.equals(Platform.PROVIDER,commonUtil.getOperator().getPlatform())){
            commonUtil.checkStoreId(Long.valueOf(returnOrder.getProviderId()));
        }else if (Objects.equals(Platform.SUPPLIER,commonUtil.getOperator().getPlatform())){
            commonUtil.checkStoreId(returnOrder.getCompany().getStoreId());
        }

        //验证订单是否是视频号订单
        if(Objects.nonNull(returnOrder.getSellPlatformType()) && SellPlatformType.WECHAT_VIDEO.equals(returnOrder.getSellPlatformType())) {
            throw new SbcRuntimeException(SettingErrorCodeEnum.K070097);
        }
        //linkedMall订单，且未支付失败
        if ((!Boolean.TRUE.equals(returnOrder.getThirdPlatformPayErrorFlag()))
                && Objects.nonNull(returnOrder.getThirdPlatformType())
                && CollectionUtils.isNotEmpty(returnOrder.getReturnItems())) {
            String subLmOrderId = returnOrder.getReturnItems().get(0).getThirdPlatformSubOrderId();
            if (StringUtils.isNotBlank(subLmOrderId)) {
                ChannelRefundQueryStatusRequest detailRequest = new ChannelRefundQueryStatusRequest();
                detailRequest.setBizUid(returnOrder.getBuyer().getId());
                detailRequest.setSubChannelOrderId(subLmOrderId);
                detailRequest.setThirdPlatformType(returnOrder.getThirdPlatformType());
                ChannelRefundQueryStatusResponse detail =
                        linkedMallReturnOrderQueryProvider.queryRefundStatus(detailRequest).getContext();
                if (detail != null && !Boolean.FALSE.equals(detail.getSellerAgreed())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[]{"linkedMall商家没有拒绝收货"});
                }
            }
        }

        returnOrderProvider.rejectReceive(ReturnOrderRejectReceiveRequest.builder().rid(rid).reason(request.getReason())
                .operator(commonUtil.getOperator()).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量拒绝收货
     *
     * @param returnRequest
     * @return
     */
    @Operation(summary = "批量拒绝收货")
    @RequestMapping(value = "/receive/reject", method = RequestMethod.POST)
    public BaseResponse rejectReceive(@RequestBody ReturnRequest returnRequest) {
        RejectRequest request = new RejectRequest();
        request.setReason(StringUtils.EMPTY);
        returnRequest.getRids().forEach(rid -> this.rejectReceive(rid, request));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 在线退款
     *
     * @param rid
     * @return
     */
    @Operation(summary = "在线退款")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/refund/{rid}/online", method = RequestMethod.POST)
    @MultiSubmit
    public BaseResponse<Object> refundOnline(@PathVariable String rid,
                                             @RequestBody ReturnOfflineRefundRequest request) {
        Operator operator = commonUtil.getOperatorWithNull();
        ReturnOrderVO returnOrder = null;
        // 判断是否为退单尾款单号
        if (rid.startsWith(GeneratorService._PREFIX_RETURN_TRADE_TAIL_ID)) {
            returnOrder = returnOrderQueryProvider.getByReturnTailId(ReturnOrderByIdRequest.builder().rid(rid)
                    .build()).getContext();
            rid = returnOrder.getId();
        }

        log.info("在线退款 returnOrderCode = {} ,offlineAccountId = {}",rid,request.getOfflineAccountId());

        BaseResponse<String> res = returnOrderProvider.refundOnlineByTid(ReturnOrderOnlineRefundByTidRequest.builder()
                .returnOrderCode(rid)
                .operator(operator)
                .offlineAccountId(request.getOfflineAccountId())
                .build());
        Object data = JSON.parse(res.getContext());
        if (Objects.isNull(data)) {
            //无返回信息，追踪退单退款状态
            ReturnFlowState state = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid).build()).getContext().getReturnFlowState();
            if (state.equals(ReturnFlowState.REFUND_FAILED)) {
                return BaseResponse.FAILED();
            }
        }
        return BaseResponse.success(data);
    }

    /**
     * 根据订单id查询退单(过滤拒绝退款、拒绝收货、已作废)
     *
     * @param tid
     * @return
     */
    @Operation(summary = "根据订单id查询退单(过滤拒绝退款、拒绝收货、已作废)")
    @Parameter(name = "tid", description = "退单Id", required = true)
    @RequestMapping(value = "/findByTid/{tid}", method = RequestMethod.GET)
    public BaseResponse<List<ReturnOrderVO>> findByTid(@PathVariable String tid) {
        checkOperatorByTrade(tid);
        // 查询还款单
        BaseResponse<CreditRepayPageResponse> repayRes = repayQueryProvider
                .findRepayOrderByOrderId(CreditOrderQueryRequest.builder().orderId(tid).build());
        if (Objects.nonNull(repayRes.getContext())) {
            if (CreditRepayStatus.getCheckStatus().contains(repayRes.getContext().getRepayStatus())) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050076);
            }
        } else {
            log.error("ReturnOrderController findByTid called findRepayOrderByOrderId error,params:{},return:{}"
                    , tid, JSON.toJSONString(repayRes));
        }
        List<ReturnOrderVO> returnOrders = returnOrderQueryProvider.listByTid(ReturnOrderListByTidRequest.builder()
                .tid(tid).build()).getContext().getReturnOrderList();
        List<ReturnOrderVO> returnOrderVOList = returnOrders.stream().filter(o -> o.getReturnFlowState() != ReturnFlowState.REJECT_REFUND
                && o.getReturnFlowState() != ReturnFlowState.REJECT_RECEIVE
                && o.getReturnFlowState() != ReturnFlowState.VOID).collect(Collectors.toList());
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

    /**
     * 根据订单id查询已完成的退单
     *
     * @param tid
     * @return
     */
    @Operation(summary = "根据订单id查询已完成的退单")
    @Parameter(name = "tid", description = "退单Id", required = true)
    @RequestMapping(value = "/findCompletedByTid/{tid}", method = RequestMethod.GET)
    public BaseResponse<List<ReturnOrderVO>> findCompletedByTid(@PathVariable String tid) {
        checkOperatorByTrade(tid);
        List<ReturnOrderVO> returnOrders = returnOrderQueryProvider.listNotVoidByTid(ReturnOrderNotVoidByTidRequest
                .builder().tid(tid).build()).getContext().getReturnOrderList();
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
        checkOperatorByTrade(tid);
        //判断订单是否计入了账期，如果计入了账期，不允许作废
        Boolean settled =
                tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(tid).build()).getContext().getTradeVO().getHasBeanSettled();
        if (settled != null && settled) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050041);
        }
        return BaseResponse.success(returnOrderQueryProvider.listByTid(ReturnOrderListByTidRequest.builder().tid(tid)
                .build()).getContext().getReturnOrderList());
    }

    /**
     * 拒绝收款
     *
     * @param rid
     * @return
     */
    @Operation(summary = "拒绝收款")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/refund/{rid}/reject", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse refundReject(@PathVariable String rid, @RequestBody RejectRequest request) {
        return returnOrderProvider.rejectRefund(ReturnOrderRejectRefundRequest.builder().operator(commonUtil.getOperator())
                .rid(rid).reason(request.getReason()).build());
    }

    /**
     * 批量拒绝退款
     *
     * @param returnRequest
     * @return
     */
    @Operation(summary = "批量拒绝退款")
    @RequestMapping(value = "/refund/reject", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> refundReject(@RequestBody ReturnRequest returnRequest) {
        RejectRequest rejectRequest = new RejectRequest();
        rejectRequest.setReason(StringUtils.EMPTY);
        returnRequest.getRids().forEach(rid -> this.refundReject(rid, rejectRequest));
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    @Operation(summary = "取消退单")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/cancel/{rid}", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse cancel(@PathVariable String rid, @RequestParam("reason") String reason) {
        List<ReturnOrderVO> returnOrderVOList = returnOrderQueryProvider.listByCondition(
                ReturnOrderByConditionRequest.builder().rids(new String[]{rid}).build()).getContext().getReturnOrderList();
        if (CollectionUtils.isEmpty(returnOrderVOList)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ReturnOrderVO returnOrderVO = returnOrderVOList.get(0);
        //linkedMall订单，且未支付失败
        if ((!Boolean.TRUE.equals(returnOrderVO.getThirdPlatformPayErrorFlag()))
                && Objects.nonNull(returnOrderVO.getThirdPlatformType())
                && CollectionUtils.isNotEmpty(returnOrderVO.getReturnItems())) {
            String subLmOrderId = returnOrderVO.getReturnItems().get(0).getThirdPlatformSubOrderId();
            if (StringUtils.isNotBlank(subLmOrderId)) {
                ChannelRefundQueryStatusRequest detailRequest = new ChannelRefundQueryStatusRequest();
                detailRequest.setBizUid(returnOrderVO.getBuyer().getId());
                detailRequest.setSubChannelOrderId(subLmOrderId);
                detailRequest.setThirdPlatformType(returnOrderVO.getThirdPlatformType());
                ChannelRefundQueryStatusResponse detail =
                        linkedMallReturnOrderQueryProvider.queryRefundStatus(detailRequest).getContext();
                if (detail != null && !Boolean.FALSE.equals(detail.getSellerAgreed())) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, new Object[]{"linkedMall商家没有拒绝退款"});
                }
            }
        }

        BaseResponse response =
                returnOrderProvider.cancel(ReturnOrderCancelRequest.builder().operator(commonUtil.getOperator()).rid(rid)
                .remark(reason).build());

        // 异步处理小程序订阅消息发送
        miniProgramSubscribeService.dealAuditMiniProgramSubscribeMsg(returnOrderVO, Boolean.FALSE);

        return response;
    }

    @Operation(summary = "修改退单")
    @RequestMapping(value = "/remedy", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse remedy(@RequestBody RemedyReturnRequest request) {
        String id = request.getRefundCause().getId();

        //查询原退单信息  如果原退单金额不支持修改 则不支持修改
        ReturnOrderByIdResponse returnOrderVO = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(request.getRid()).build()).getContext();
        if (Objects.isNull(returnOrderVO)){
            return BaseResponse.SUCCESSFUL();
        }
        request.getReturnPriceRequest().setApplyStatus(returnOrderVO.getReturnPrice().getApplyStatus());
        request.getReturnPriceRequest().setApplyPrice(returnOrderVO.getReturnPrice().getApplyPrice());
        request.getReturnPriceRequest().setTotalPrice(returnOrderVO.getReturnPrice().getTotalPrice());
        request.getReturnPointsRequest().setApplyPoints(returnOrderVO.getReturnPoints().getApplyPoints());
        RefundCauseQueryOneResponse context = refundCauseQueryProvider.findById(RefundCauseQueryRequest.builder().id(id).build()).getContext();
        request.setRefundCause(context);
        return returnOrderProvider.remedy(ReturnOrderRemedyRequest.builder().operator(commonUtil.getOperator())
                .newReturnOrder(Remedy2ReturnOrder.convert(request)).build());
    }

    /**
     * 查看退货订单详情和可退商品数
     *
     * @param tid
     * @return
     */
    @Operation(summary = "查看退货订单详情和可退商品数")
    @Parameter(name = "tid", description = "退单Id", required = true)
    @RequestMapping(value = "/trade/{tid}", method = RequestMethod.GET)
    public BaseResponse<TradeVO> tradeDetails(@PathVariable String tid) {
        checkOperatorByTrade(tid);
        TradeVO trade = returnOrderQueryProvider.queryCanReturnItemNumByTid(CanReturnItemNumByTidRequest.builder()
                .tid(tid).build()).getContext();
        return BaseResponse.success(trade);
    }

    /**
     * 查询退货退单及可退商品数
     *
     * @param rid
     * @return
     */
    @Operation(summary = "查询退货退单及可退商品数")
    @Parameter(name = "rid", description = "退单Id", required = true)
    @RequestMapping(value = "/detail/{rid}", method = RequestMethod.GET)
    public ResponseEntity<ReturnOrderVO> returnDetail(@PathVariable String rid) {
        checkOperatorByReturnOrder(rid);
        ReturnOrderVO returnOrder = returnOrderQueryProvider.queryCanReturnItemNumById(CanReturnItemNumByIdRequest
                .builder().rid(rid).build()).getContext();
        return ResponseEntity.ok(returnOrder);
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
        checkOperatorByReturnOrder(rid);
        return refundOrderQueryProvider.getRefundOrderRespByReturnOrderCode(new RefundOrderResponseByReturnOrderCodeRequest(rid));
    }

    /**
     * 关闭退款
     *
     * @param rid
     * @return
     */
    @RequestMapping(value = "/refund/{rid}/closeRefund", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse closeRefund(@PathVariable String rid) {
        Operator operator = commonUtil.getOperator();
        if (Platform.PLATFORM != operator.getPlatform()){
            checkOperatorByReturnOrder(rid);
        }
        return returnOrderProvider.closeRefund(ReturnOrderCloseRequest.builder()
                .operator(operator)
                .rid(rid).build());
    }

    /**
     * 退单修改卖家备注
     *
     * @param rid
     * @param request
     * @return
     */
    @Operation(summary = "修改卖家备注")
    @Parameter(name = "rid", description = "退单id", required = true)
    @RequestMapping(value = "/remark/{rid}", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> sellerRemark(@PathVariable String rid, @RequestBody RemedyReturnRequest
            request) {
        ReturnOrderRemedySellerRemarkRequest remedySellerRemarkRequest = ReturnOrderRemedySellerRemarkRequest.builder()
                .sellerRemark(request.getSellerRemark())
                .rid(rid)
                .operator(commonUtil.getOperator())
                .build();

        returnOrderProvider.remedySellerRemark(remedySellerRemarkRequest);

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /***
     * 校验退单是否可以批量审核
     * @param request
     * @return
     */
    @Operation(summary = "校验退单是否可以批量审核")
    @PostMapping(value = "/checkBatchAuditReturnOrder")
    public ResponseEntity<BaseResponse> checkBatchAuditReturnOrder(@RequestBody @Valid ReturnOrderCheckRequest request) {
        returnOrderProvider.checkBatchAuditReturnOrder(request);
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    private TradeVO checkOperatorByTrade(String tid) {
        TradeVO trade = null;
        Operator operator = commonUtil.getOperator();
        if (operator.getPlatform() == Platform.SUPPLIER) {
            if (tid.startsWith(GeneratorService._PREFIX_TRADE_ID) || tid.startsWith(GeneratorService.NEW_PREFIX_TRADE_ID)) {
                trade =
                        tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(tid).build()).getContext().getTradeVO();
            } else if (tid.startsWith("S")) {
                trade =
                        providerTradeQueryProvider.providerGetById(TradeGetByIdRequest.builder().tid(tid).build()).getContext().getTradeVO();
            }
            if (Objects.nonNull(trade) && !Objects.equals(commonUtil.getStoreId(), trade.getSupplier().getStoreId())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
            }
        }
        return trade;
    }

    private ReturnOrderVO checkOperatorByReturnOrder(String rid) {
        ReturnOrderVO returnOrder = null;
        Operator operator = commonUtil.getOperator();
        if (operator.getPlatform() == Platform.SUPPLIER || operator.getPlatform() == Platform.STOREFRONT) {
            returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid)
                    .build()).getContext();
            if (!Objects.equals(commonUtil.getStoreId(), returnOrder.getCompany().getStoreId())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
            }
        } else if (operator.getPlatform() == Platform.PROVIDER) {
            returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid)
                    .build()).getContext();
            if (Objects.isNull(returnOrder.getTradeVO()) || !Objects.equals(commonUtil.getStoreId(), returnOrder.getTradeVO().getSupplier().getStoreId())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
            }
        }
        if (Objects.isNull(returnOrder)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000003);
        }
        return returnOrder;
    }
}
