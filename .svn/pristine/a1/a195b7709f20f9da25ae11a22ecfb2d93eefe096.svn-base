package com.wanmi.sbc.returnorder;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.account.api.provider.credit.CreditRepayQueryProvider;
import com.wanmi.sbc.account.api.request.credit.CreditOrderQueryRequest;
import com.wanmi.sbc.account.api.response.credit.CreditRepayPageResponse;
import com.wanmi.sbc.account.bean.enums.CreditRepayStatus;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoByIdRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoByIdResponse;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.small.SmallProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderRequest;
import com.wanmi.sbc.order.api.request.returnorder.*;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrderResponse;
import com.wanmi.sbc.order.api.response.returnorder.ReturnOrderListByTidResponse;
import com.wanmi.sbc.order.api.response.returnorder.ReturnOrderTransferByUserIdResponse;
import com.wanmi.sbc.order.bean.dto.*;
import com.wanmi.sbc.order.bean.enums.*;
import com.wanmi.sbc.order.bean.vo.OrderTagVO;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import com.wanmi.sbc.order.bean.vo.TradeStateVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.request.TradeConfigGetByTypeRequest;
import com.wanmi.sbc.setting.api.response.TradeConfigGetByTypeResponse;
import com.wanmi.sbc.setting.bean.enums.ConfigType;
import com.wanmi.sbc.util.CommonUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


/**
 * Created by sunkun on 2017/7/10.
 */

@RestController
@Validated
@RequestMapping("/return")
@Tag(name = "ReturnOrderController", description = "mobile退单Api")
@Slf4j
public class ReturnOrderController {

    @Autowired
    private ReturnOrderProvider returnOrderProvider;

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private PayOrderQueryProvider payOrderQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;
    @Resource
    private CreditRepayQueryProvider repayQueryProvider;
    @Autowired
    SmallProvider smallProvider;



    @Operation(summary = "从旧平台获取礼包数")
    @Parameter(name = "phone", description = "会员手机号", required = true)
    @RequestMapping(value = "/getFullGiftNum", method = RequestMethod.POST)
    public BaseResponse getFullGiftNum(@RequestParam(name = "phone") String phone) {
        return smallProvider.getFullGiftNum(phone);
    }
    @Operation(summary = "旧平台激活礼包")
    @Parameter(name = "phone", description = "会员手机号", required = true)
    @RequestMapping(value = "/checkFullGift", method = RequestMethod.POST)
    public BaseResponse checkFullGift(@RequestParam(name = "phone") String phone) {
        return smallProvider.checkFullGift(phone);
    }
    /**
     * 创建退单
     *
     * @param returnOrder
     * @return
     */
    @Operation(summary = "创建退单")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse<String> create(@RequestBody @Valid ReturnOrderDTO returnOrder) {
        verifyIsReturnable(returnOrder.getTid(),returnOrder.getReturnType(),returnOrder.getGoodsInfoState());
        //验证用户
        String userId = commonUtil.getOperatorId();
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (userId)).getContext();
        if (!verifyTradeByCustomerId(returnOrder.getTid(), userId)) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050066);
        }

        // 判断是否还款中订单
        BaseResponse<CreditRepayPageResponse> repayRes = repayQueryProvider
                .findRepayOrderByOrderId(CreditOrderQueryRequest.builder().orderId(returnOrder.getTid()).build());
        if (Objects.nonNull(repayRes.getContext())) {
            if(CreditRepayStatus.getCheckStatus().contains(repayRes.getContext().getRepayStatus())) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050076);
            }
        } else {
            log.error("ReturnOrderBaseController findByTid called findRepayOrderByOrderId error,params:{},return:{}"
                    , returnOrder.getTid(), JSONObject.toJSONString(repayRes));
        }

        ReturnOrderVO oldReturnOrderTemp = returnOrderQueryProvider.getTransferByUserId(
                ReturnOrderTransferByUserIdRequest.builder().userId(userId).build()).getContext();
        if (oldReturnOrderTemp == null) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050030);
        }

        String tid = oldReturnOrderTemp.getTid();
        if (!tid.equals(returnOrder.getTid())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        ReturnOrderDTO oldReturnOrder = KsBeanUtil.convert(oldReturnOrderTemp, ReturnOrderDTO.class);

        oldReturnOrder.setReturnReason(returnOrder.getReturnReason());
        oldReturnOrder.setDescription(returnOrder.getDescription());
        oldReturnOrder.setImages(returnOrder.getImages());
        oldReturnOrder.setReturnLogistics(returnOrder.getReturnLogistics());
        oldReturnOrder.setReturnWay(returnOrder.getReturnWay());
        oldReturnOrder.setReturnPrice(returnOrder.getReturnPrice());
        oldReturnOrder.setGoodsInfoState(returnOrder.getGoodsInfoState());
        TradeVO trade =
                tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(returnOrder.getTid()).build()).getContext().getTradeVO();
        OrderTagVO orderTag = trade.getOrderTag();
        //是否是虚拟订单或者卡券订单
        boolean isVirtual = Objects.nonNull(orderTag) && (orderTag.getVirtualFlag() || orderTag.getElectronicCouponFlag());
        if (isVirtual) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999998);
        }
        oldReturnOrder.setCompany(CompanyDTO.builder().companyInfoId(trade.getSupplier().getSupplierId())
                .companyCode(trade.getSupplier().getSupplierCode()).supplierName(trade.getSupplier().getSupplierName())
                .storeId(trade.getSupplier().getStoreId()).storeName(trade.getSupplier().getStoreName()).companyType(trade.getSupplier().getIsSelf() ? BoolFlag.NO : BoolFlag.YES).build());
        oldReturnOrder.setChannelType(trade.getChannelType());
        oldReturnOrder.setDistributorId(trade.getDistributorId());
        oldReturnOrder.setInviteeId(trade.getInviteeId());
        oldReturnOrder.setShopName(trade.getShopName());
        oldReturnOrder.setDistributorName(trade.getDistributorName());
        oldReturnOrder.setDistributeItems(trade.getDistributeItems());
        oldReturnOrder.setReturnGift(returnOrder.getReturnGift());
        oldReturnOrder.setTerminalSource(commonUtil.getTerminal());
        oldReturnOrder.setRefundCause(returnOrder.getRefundCause());
        oldReturnOrder.setReturnPreferentialIds(returnOrder.getReturnPreferentialIds());
        String rid = returnOrderProvider.add(ReturnOrderAddRequest.builder().returnOrder(oldReturnOrder)
                .operator(commonUtil.getOperator()).build()).getContext().getReturnOrderId();
        returnOrderProvider.deleteTransfer(ReturnOrderTransferDeleteRequest.builder().userId(userId).build());

        // 极速退款逻辑
        //是否仅退款
        boolean isRefund = oldReturnOrder.getReturnType() == ReturnType.REFUND;
        Boolean fastRefundFlag = checkFastRefundFlag(returnOrder, trade);
        if (fastRefundFlag && isRefund) {
            BaseResponse<ReturnOrderListByTidResponse> byTidResponseBaseResponse = returnOrderQueryProvider.listByTid(ReturnOrderListByTidRequest.builder().tid(returnOrder.getTid()).build());
            if (CollectionUtils.isNotEmpty(byTidResponseBaseResponse.getContext().getReturnOrderList())) {
                List<String> ridList = byTidResponseBaseResponse.getContext().getReturnOrderList().stream().map(ReturnOrderVO::getId).collect(Collectors.toList());
                Operator operator = commonUtil.getOperator();
                for (String returnOrderId: ridList) {
                    ReturnOrderOnlineRefundByTidRequest request = ReturnOrderOnlineRefundByTidRequest.builder()
                            .returnOrderCode(returnOrderId)
                            .operator(operator)
                            .build();
                    log.info("开始处理极速退款,退款单号为: {}", request);
                    returnOrderProvider.sendFastRefundMessage(request);
                    log.info("结束处理极速退款,退款单号为: {}", request);
                }
            }
        }
        return BaseResponse.success(rid);
    }


    public Boolean checkFastRefundFlag(ReturnOrderDTO returnOrder, TradeVO trade) {

        // 仅退款的条件 暂定为为未发货 已支付的订单
        if (trade.getTradeState().getFlowState() != FlowState.AUDIT) {
            return Boolean.FALSE;
        }
        if (trade.getTradeState().getPayState() != PayState.PAID) {
            return Boolean.FALSE;
        }

        // 实付金额校验
        BigDecimal totalPrice = returnOrder.getReturnPrice().getTotalPrice();
        BigDecimal totalPayCash = Optional.ofNullable(trade.getTradePrice().getTotalPayCash()).orElse(BigDecimal.ZERO);
        if (totalPrice.compareTo(totalPayCash) > 0) {
            return Boolean.FALSE;
        }

        // 物流信息判断, 有物流不退
        if (CollectionUtils.isNotEmpty(trade.getTradeDelivers())) {
            boolean hasValidLogistic = trade.getTradeDelivers().stream()
                    .filter(Objects::nonNull)
                    .anyMatch(td -> td.getLogistics() != null
                            && td.getLogistics().getLogisticNo() != null);
            if (hasValidLogistic) {
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }

    /**
     * 创建退款单（废弃）
     *
     * @param returnOrder
     * @return
     */
    @Operation(summary = "创建退款单")
    @RequestMapping(value = "/addRefund", method = RequestMethod.POST)
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse<String> createRefund(@RequestBody @Valid ReturnOrderDTO returnOrder) {
        verifyIsReturnable(returnOrder.getTid(),returnOrder.getReturnType(),returnOrder.getGoodsInfoState());
        if (!verifyTradeByCustomerId(returnOrder.getTid(), commonUtil.getOperatorId())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050066);
        }
        TradeVO trade =
                tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(returnOrder.getTid()).build()).getContext().getTradeVO();
        returnOrder.setCompany(CompanyDTO.builder().companyInfoId(trade.getSupplier().getSupplierId())
                .companyCode(trade.getSupplier().getSupplierCode()).supplierName(trade.getSupplier().getSupplierName())
                .storeId(trade.getSupplier().getStoreId()).storeName(trade.getSupplier().getStoreName())
                .companyType(trade.getSupplier().getIsSelf() ? BoolFlag.NO : BoolFlag.YES)
                .build());
        returnOrder.setChannelType(trade.getChannelType());
        returnOrder.setDistributorId(trade.getDistributorId());
        returnOrder.setInviteeId(trade.getInviteeId());
        returnOrder.setShopName(trade.getShopName());
        returnOrder.setDistributorName(trade.getDistributorName());
        returnOrder.setDistributeItems(trade.getDistributeItems());
        returnOrder.setTerminalSource(commonUtil.getTerminal());
        if(trade.getTradeState().getDeliverStatus() == DeliverStatus.SHIPPED){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        String rid = returnOrderProvider.add(ReturnOrderAddRequest.builder().returnOrder(returnOrder)
                .operator(commonUtil.getOperator()).build()).getContext().getReturnOrderId();
        return BaseResponse.success(rid);
    }

    /**
     * 创建退单快照
     *
     * @param returnOrder
     * @return
     */
    @Operation(summary = "创建退单快照")
    @RequestMapping(value = "/transfer", method = RequestMethod.POST)
    @GlobalTransactional
    @MultiSubmit
    public BaseResponse transfer(@RequestBody @Valid ReturnOrderDTO returnOrder) {
        BaseResponse<FindPayOrderResponse> response =
                payOrderQueryProvider.findPayOrder(FindPayOrderRequest.builder().value(returnOrder.getTid()).build());

        FindPayOrderResponse payOrderResponse = response.getContext();
        if (Objects.isNull(payOrderResponse) || Objects.isNull(payOrderResponse.getPayOrderStatus()) || payOrderResponse.getPayOrderStatus() != PayOrderStatus.PAYED) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050013);
        }
        verifyIsReturnable(returnOrder.getTid(),returnOrder.getReturnType(),returnOrder.getGoodsInfoState());
        if (!verifyTradeByCustomerId(returnOrder.getTid(), commonUtil.getOperatorId())) {
            throw new SbcRuntimeException(OrderErrorCodeEnum.K050066);
        }
        returnOrderProvider.addTransfer(ReturnOrderTransferAddRequest.builder().returnOrder(returnOrder)
                .operator(commonUtil.getOperator()).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 校验是否可退
     *
     * @param tid
     */
    private void verifyIsReturnable(String tid, ReturnType returnType,GoodsInfoState goodsInfoState) {
        TradeVO trade =
                tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(tid).build()).getContext().getTradeVO();
        OrderTagVO orderTag = trade.getOrderTag();
        boolean buyCycleFlag = Objects.nonNull(orderTag) && orderTag.getBuyCycleFlag();
        //周期购订单
        if (buyCycleFlag) {
            TradeStateVO tradeState = trade.getTradeState();
            DeliverStatus deliverStatus = tradeState.getDeliverStatus();
            //已经发货或者部分发货不能申请退单
            if (Objects.equals(DeliverStatus.SHIPPED,deliverStatus)||Objects.equals(DeliverStatus.PART_SHIPPED,deliverStatus)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050070);
            }
            //周期购只能全额退款
            if (Objects.nonNull(returnType) && !Objects.equals(ReturnType.REFUND,returnType)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            //周期购商品只能选择未收到货
            if (Objects.nonNull(goodsInfoState) && !Objects.equals(GoodsInfoState.NOT_RECEIVED,goodsInfoState)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            Boolean isReturn = trade.getIsReturn();
            //周期购只能一次全退，有售后就不能再次申请
            if (isReturn) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050070);
            }
        }
        //部分发货或者待确认收货
        if(trade.getTradeState().getFlowState() == FlowState.DELIVERED_PART || trade.getTradeState().getFlowState() == FlowState.DELIVERED) {
            Boolean transitReturn = trade.getTransitReturn();
            //排除周期购订单
            if (!buyCycleFlag && Boolean.FALSE.equals(transitReturn)) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050070);
            }
        } else if (Objects.nonNull(trade.getTradeState().getDeliverStatus()) && (trade.getTradeState().getDeliverStatus() == DeliverStatus.SHIPPED)) {
            TradeConfigGetByTypeRequest request = new TradeConfigGetByTypeRequest();
            request.setConfigType(ConfigType.ORDER_SETTING_APPLY_REFUND);
            TradeConfigGetByTypeResponse config = auditQueryProvider.getTradeConfigByType(request).getContext();
            boolean flag = config.getStatus() == 0;
            //申请退单状态数据库状态优先
            if (Objects.nonNull(trade.getTradeState().getRefundStatus())){
                flag = trade.getTradeState().getRefundStatus() == 0;
            }
            if (flag) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050070);
            }
            JSONObject content = JSON.parseObject(config.getContext());
            Integer day = content.getObject("day", Integer.class);

            if (Objects.isNull(trade.getTradeState().getEndTime())) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050005);
            }
            if (trade.getTradeState().getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() < LocalDateTime.now().minusDays(day).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050070);
            }
        }
    }

    /**
     * 查询退单快照
     *
     * @return
     */
    @Operation(summary = "查询退单快照")
    @RequestMapping(value = "/findTransfer", method = RequestMethod.GET)
    public BaseResponse<ReturnOrderTransferByUserIdResponse> transfer() {
        ReturnOrderTransferByUserIdResponse response = returnOrderQueryProvider.getTransferByUserId(
                ReturnOrderTransferByUserIdRequest.builder()
                        .userId(commonUtil.getOperatorId()).build()).getContext();
        if (Objects.nonNull(response)
                && Objects.nonNull(response.getCompany())) {
            if(response.getTradeVO()!=null && CollectionUtils.isNotEmpty(response.getTradeVO().getTradeItems())){
                response.getTradeVO().getTradeItems().forEach(tradeItemVO -> {
                    String skuId = tradeItemVO.getSkuId();
                    if(StringUtils.isNotBlank(skuId)){
                        GoodsInfoByIdResponse providerGoodsInfoVo = goodsInfoQueryProvider.getById(GoodsInfoByIdRequest.builder().goodsInfoId(skuId).build()).getContext();
                        tradeItemVO.setProviderId(providerGoodsInfoVo.getProviderId());
                    }
                });
            }
        }
        return BaseResponse.success(response);
    }

    private boolean verifyTradeByCustomerId(String tid, String customerId) {
        TradeVO trade =
                tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(tid).build()).getContext().getTradeVO();
        return trade.getBuyer().getId().equals(customerId);
    }


    /**
     * 是否可创建退单
     *
     * @return
     */
    @Operation(summary = "是否可创建退单")
    @RequestMapping(value = "/returnable/{tid}", method = RequestMethod.GET)
    public BaseResponse isReturnable(@PathVariable String tid) {
        verifyIsReturnable(tid,null,null);
        return BaseResponse.SUCCESSFUL();
    }
}
