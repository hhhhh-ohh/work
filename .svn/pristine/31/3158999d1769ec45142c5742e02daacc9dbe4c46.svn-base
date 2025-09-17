package com.wanmi.sbc.returnorder;

import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.account.CustomerAccountQueryProvider;
import com.wanmi.sbc.customer.api.request.account.CustomerAccountRequest;
import com.wanmi.sbc.customer.api.response.account.CustomerAccountResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.customer.bean.vo.CustomerAccountVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.empower.api.provider.pay.PaySettingQueryProvider;
import com.wanmi.sbc.empower.api.request.pay.channelItem.ChannelItemByIdRequest;
import com.wanmi.sbc.empower.api.response.pay.channelItem.PayChannelItemResponse;
import com.wanmi.sbc.miniprogramsubscribe.MiniProgramSubscribeService;
import com.wanmi.sbc.order.api.provider.paytraderecord.PayTradeRecordQueryProvider;
import com.wanmi.sbc.order.api.provider.refund.RefundOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderQueryProvider;
import com.wanmi.sbc.order.api.request.areas.ReturnOrderRequest;
import com.wanmi.sbc.order.api.request.paytraderecord.TradeRecordByOrderCodeRequest;
import com.wanmi.sbc.order.api.request.refund.RefundOrderByReturnOrderCodeRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderByIdRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderOfflineRefundForBossRequest;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderPageRequest;
import com.wanmi.sbc.order.api.response.paytraderecord.PayTradeRecordResponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderByReturnCodeResponse;
import com.wanmi.sbc.order.api.response.returnorder.ReturnOrderByIdResponse;
import com.wanmi.sbc.order.bean.dto.RefundBillDTO;
import com.wanmi.sbc.order.bean.dto.ReturnOrderDTO;
import com.wanmi.sbc.order.bean.enums.CreditPayState;
import com.wanmi.sbc.order.bean.enums.ReturnOrderType;
import com.wanmi.sbc.order.bean.vo.CreditPayInfoVO;
import com.wanmi.sbc.order.bean.vo.RefundBillVO;
import com.wanmi.sbc.order.bean.vo.ReturnOrderVO;
import com.wanmi.sbc.order.bean.vo.TradeVO;
import com.wanmi.sbc.returnorder.request.ReturnOfflineRefundRequest;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 退单
 * Created by sunkun on 2017/11/17.
 */
@RestController
@Validated
@RequestMapping("/return")
@Tag(name =  "退单", description =  "S2bReturnOrderController")
public class S2bReturnOrderController {

    @Autowired
    private ReturnOrderProvider returnOrderProvider;

    @Autowired
    private ReturnOrderQueryProvider returnOrderQueryProvider;

    @Resource
    private CommonUtil commonUtil;

    @Resource
    private RefundOrderQueryProvider refundOrderQueryProvider;

    @Resource
    private CustomerAccountQueryProvider customerAccountQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private PaySettingQueryProvider paySettingQueryProvider;

    @Autowired
    private PayTradeRecordQueryProvider payTradeRecordQueryProvider;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Autowired
    private MiniProgramSubscribeService miniProgramSubscribeService;

    /**
     * 分页查询 from ES
     *
     * @param request
     * @return
     */
    @Operation(summary = "分页查询 from ES")
    @EmployeeCheck
    @RequestMapping(method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_return_order_page_sign_word")
    public BaseResponse<MicroServicePage<ReturnOrderVO>> page(@RequestBody ReturnOrderPageRequest request) {
        request.setReturnOrderType(ReturnOrderType.GENERAL_TRADE);
        MicroServicePage<ReturnOrderVO> page = returnOrderQueryProvider.page(request).getContext().getReturnOrderPage();

        List<String> customerIds = page.getContent().stream().map(v -> v.getBuyer().getId()).collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        page.getContent().forEach(returnOrder -> {
            TradeVO tradeVO = returnOrder.getTradeVO();
            if(null != tradeVO){
                TradeRecordByOrderCodeRequest record = new TradeRecordByOrderCodeRequest();
                record.setOrderId(tradeVO.getId());
                PayTradeRecordResponse recordResponse = payTradeRecordQueryProvider.getTradeRecordByOrderCode(record).getContext();
                if(null == recordResponse && StringUtils.isNotBlank(tradeVO.getParentId())){
                    record.setOrderId(tradeVO.getParentId());
                    recordResponse = payTradeRecordQueryProvider.getTradeRecordByOrderCode(record).getContext();
                }

                if(null != recordResponse){
                    Long channelId = recordResponse.getChannelItemId();
                    PayChannelItemResponse payChannelItemResponse = paySettingQueryProvider.getChannelItemById(
                            ChannelItemByIdRequest.builder().channelItemId(channelId).build()).getContext();

                    if(null != payChannelItemResponse && null != payChannelItemResponse.getGateway()){
                        returnOrder.setWay(payChannelItemResponse.getGateway().getName().toValue());
                    }

                }
            }
            //判断订单会员是否注销
            returnOrder.setLogOutStatus(
                    map.get(returnOrder.getBuyer().getId())
            );
        });
        return BaseResponse.success(page);
    }

    /**
     * 线下退款
     *
     * @param rid
     * @param request
     * @return
     */
    @Operation(summary = "线下退款")
    @Parameters({
            @Parameter(name = "rid",
                    description = "退单Id", required = true),
            @Parameter(name = "request",
                    description = "线下退款", required = true),
    })
    @RequestMapping(value = "/refund/{rid}/offline", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> refundOffline(@PathVariable String rid,
                                                      @RequestBody ReturnOfflineRefundRequest request) {
        ReturnOrderVO returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid)
                .build()).getContext();
        RefundOrderByReturnCodeResponse refundOrder = refundOrderQueryProvider.getByReturnOrderCode(new RefundOrderByReturnOrderCodeRequest(returnOrder.getId())).getContext();

        //如果是退定金预售订单 且只有一笔是授信支付且已还款 则不应该走线下退款
        CreditPayInfoVO creditPayInfoVO = returnOrder.getCreditPayInfo();
        if(Objects.nonNull(creditPayInfoVO)){
            if(creditPayInfoVO.getHasRepaid()
                    && (creditPayInfoVO.getCreditPayState().equals(CreditPayState.DEPOSIT)
                    || creditPayInfoVO.getCreditPayState().equals(CreditPayState.BALANCE))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }

        //退款流水
        RefundBillVO refundBill = refundOrder.getRefundBill();
        if (Objects.isNull(refundBill)) {
            refundBill = new RefundBillVO();
            refundBill.setRefundId(refundOrder.getRefundId());
            refundBill.setActualReturnPrice(refundOrder.getReturnPrice());
            refundBill.setActualReturnPoints(refundOrder.getReturnPoints());
        }
        refundBill.setOfflineAccountId(request.getOfflineAccountId());
        refundBill.setCreateTime(DateUtil.parseDate(request.getCreateTime()));
        returnOrderProvider.offlineRefundForBoss(ReturnOrderOfflineRefundForBossRequest.builder().rid(rid)
                .refundBill(KsBeanUtil.convert(refundBill, RefundBillDTO.class))
                .operator(commonUtil.getOperator()).tid(returnOrder.getTid()).build());

        ReturnOrderByIdResponse returnOrderByIdResponse = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid).build()).getContext();

        ReturnOrderDTO requestDto = KsBeanUtil.convert(returnOrderByIdResponse, ReturnOrderDTO.class);

        ReturnOrderRequest providerrequest = new ReturnOrderRequest();

        providerrequest.setReturnOrderDTO(requestDto);

        //操作日志记录
        operateLogMQUtil.convertAndSend("财务", "确认退款", "确认退款：退单编号" + returnOrder.getId());

        miniProgramSubscribeService.dealReturnOnlineMiniProgramSubscribeMsg(returnOrderByIdResponse);

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 根据退单查询客户收款账户
     *
     * @param rid
     * @return
     */
    @Operation(summary = "根据退单查询客户收款账户")
    @Parameter(name = "rid",
            description = "退单Id", required = true)
    @RequestMapping(value = "/customer/account/{rid}", method = RequestMethod.GET)
    public BaseResponse<CustomerAccountVO> findCustomerAccountBy(@PathVariable String rid) {
        ReturnOrderVO returnOrder = returnOrderQueryProvider.getById(ReturnOrderByIdRequest.builder().rid(rid)
                .build()).getContext();
        if (Objects.isNull(returnOrder)) {
            //退单不存在
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomerAccountVO customerAccount = new CustomerAccountVO();
        if (Objects.isNull(returnOrder.getCustomerAccount())) {
            //不存在临时账号，从流水获取客户收款账号
            RefundOrderByReturnCodeResponse refundOrder = refundOrderQueryProvider.getByReturnOrderCode(new RefundOrderByReturnOrderCodeRequest(rid)).getContext();
            if (Objects.isNull(refundOrder.getRefundBill()) || Objects.isNull(refundOrder.getRefundBill().getCustomerAccountId())) {
                // 没有流水 活着
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            refundOrder.getRefundBill().getCustomerAccountId();
            CustomerAccountRequest customerAccountRequest = new CustomerAccountRequest();
            customerAccountRequest.setCustomerAccountId(refundOrder.getRefundBill().getCustomerAccountId());
            BaseResponse<CustomerAccountResponse> baseResponse = customerAccountQueryProvider.getByCustomerAccountId(customerAccountRequest);
            CustomerAccountResponse customerAccountResponse = baseResponse.getContext();
            KsBeanUtil.copyPropertiesThird(customerAccountResponse, customerAccount);
        } else {
            customerAccount = returnOrder.getCustomerAccount();
        }
        return BaseResponse.success(customerAccount);
    }
}
