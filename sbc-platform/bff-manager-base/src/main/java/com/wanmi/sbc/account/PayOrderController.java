package com.wanmi.sbc.account;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderProvider;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.payorder.*;
import com.wanmi.sbc.order.api.request.trade.TradeAddReceivableRequest;
import com.wanmi.sbc.order.api.request.trade.TradeConfirmPayOrderRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrderByPayOrderIdsResponse;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrderResponse;
import com.wanmi.sbc.order.api.response.payorder.FindPayOrdersResponse;
import com.wanmi.sbc.order.api.response.payorder.SumPayOrderPriceResponse;
import com.wanmi.sbc.order.bean.dto.PayOrderDTO;
import com.wanmi.sbc.order.bean.dto.ReceivableAddDTO;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.order.bean.vo.PayOrderResponseVO;
import com.wanmi.sbc.order.bean.vo.PayOrderVO;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.jsonwebtoken.Claims;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * 支付单
 * Created by zhangjin on 2017/4/20.
 */
@Slf4j
@Tag(name = "PayOrderController", description = "支付单")
@RestController
@RequestMapping("/account")
@Validated
public class PayOrderController {

    @Autowired
    private PayOrderProvider payOrderProvider;

    @Autowired
    private PayOrderQueryProvider payOrderQueryProvider;

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerCacheService customerCacheService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private ExportCenter exportCenter;

    /**
     * 导出文件名后的时间后缀
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    /**
     * 查询订单收款
     *
     * @param payOrderRequest payOrderRequest
     * @return status
     */
    @Operation(summary = "查询订单收款")
    @EmployeeCheck(customerDetailIdField = "employeeCustomerDetailIds")
    @RequestMapping(value = "/payOrders", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_find_pay_order_sign_word")
    public ResponseEntity<FindPayOrdersResponse> findPayOrder(@RequestBody FindPayOrdersRequest payOrderRequest) {
        FindPayOrdersResponse response = payOrderQueryProvider.findPayOrders(payOrderRequest).getContext();

        List<String> customerIds = response.getPayOrderResponses().stream()
                .map(PayOrderResponseVO::getCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);

        response.getPayOrderResponses().forEach(payOrderResponseVO -> {
            //判断订单会员是否注销
            payOrderResponseVO.setLogOutStatus(map.get(payOrderResponseVO.getCustomerId()));

            if (GiftCardType.PICKUP_CARD.equals(payOrderResponseVO.getGiftCardType())){
                payOrderResponseVO.setPayOrderPoints(0L);
            }
        });
        return ResponseEntity.ok(response);
    }

    /**
     * 查询订单收款（商家）
     *
     * @param payOrderRequest payOrderRequest
     * @return status
     */
    @Operation(summary = "查询订单收款（商家）")
    @RequestMapping(value = "/store/payOrders", method = RequestMethod.POST)
    public ResponseEntity<FindPayOrdersResponse> findStorePayOrder(@RequestBody FindPayOrdersRequest payOrderRequest) {
        Long companyInfoId = commonUtil.getCompanyInfoId();
        payOrderRequest.setCompanyInfoId(isNull(companyInfoId) ? "0" : companyInfoId.toString());
        FindPayOrdersResponse response = payOrderQueryProvider.findPayOrders(payOrderRequest).getContext();
        return ResponseEntity.ok(response);
    }

    /**
     * 根据订单编号查询收款单
     *
     * @param orderNo orderNo 订单编号
     * @return ResponseEntity<PayOrderResponse>
     */
    @Operation(summary = "根据订单编号查询收款单")
    @Parameter(name = "orderNo", description = "订单编号", required = true)
    @RequestMapping(value = "/payOrder/{orderNo}", method = RequestMethod.GET)
    public ResponseEntity<FindPayOrderResponse> findPayOrderByOrderNo(@PathVariable("orderNo") String orderNo) {
        BaseResponse<FindPayOrderResponse> response =
                payOrderQueryProvider.findPayOrder(FindPayOrderRequest.builder().value(orderNo).build());
        return ResponseEntity.ok(response.getContext());
    }



    /**
     * 确认订单收款
     *
     * @param payOrderOperateRequest payOrderIds
     * @return status
     */
    @Operation(summary = "确认订单收款")
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> confirm(@RequestBody TradeConfirmPayOrderRequest payOrderOperateRequest,
                                                HttpServletRequest request) {
        Operator operator = buildEmployee(request);
        payOrderOperateRequest.setOperator(operator);
        tradeProvider.confirmPayOrder(payOrderOperateRequest);

        // 操作日志记录
        List<String> payOrderIds = payOrderOperateRequest.getPayOrderIds();
        if (CollectionUtils.size(payOrderIds) == 1) {
            Optional<PayOrderVO> payOrderOptional = this.getPayOrderCode(payOrderIds.get(0));
            payOrderOptional.ifPresent(payOrder -> operateLogMQUtil.convertAndSend("财务", "确认收款",
                    "确认收款：订单编号" + payOrder.getOrderCode()));
        } else {
            operateLogMQUtil.convertAndSend("财务", "批量确认", "批量确认");
        }

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 作废订单收款
     *
     * @param payOrderId payOrderIds
     * @return status
     */
    @Operation(summary = "作废订单收款")
    @Parameter(name = "payOrderId", description = "支付单ID",
            required = true)
    @RequestMapping(value = "/payOrder/destory/{payOrderId}", method = RequestMethod.PUT)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> destoryByPayOrderId(@PathVariable("payOrderId") String payOrderId,
                                                            HttpServletRequest request) {
        Operator operator = this.buildEmployee(request);

        BaseResponse<FindPayOrderByPayOrderIdsResponse> responseBaseResponse =
                payOrderQueryProvider.findPayOrderByPayOrderIds(FindPayOrderByPayOrderIdsRequest.builder().payOrderIds(Lists.newArrayList(payOrderId)).build());

        //判断订单是否计入了账期，如果计入了账期，不允许作废
        List<PayOrderVO> payOrderList = responseBaseResponse.getContext().getOrders();
        if (payOrderList != null && !payOrderList.isEmpty()) {
            payOrderList.stream().forEach(payOrder -> {
                Boolean settled =
                        tradeQueryProvider.getById(TradeGetByIdRequest.builder().tid(payOrder.getOrderCode()).build()).getContext().getTradeVO().getHasBeanSettled();
                if (settled != null && settled) {
                    throw new SbcRuntimeException(OrderErrorCodeEnum.K050041);
                }
            });
        }

        List<PayOrderDTO> payOrderDtos = KsBeanUtil.convert(payOrderList, PayOrderDTO.class);

        payOrderProvider.destoryPayOrder(DestoryPayOrderRequest.builder().payOrders(payOrderDtos).operator(operator).build());

        //操作日志记录
        Optional<PayOrderVO> payOrderOptional = this.getPayOrderCode(payOrderId);
        payOrderOptional.ifPresent(payOrder -> operateLogMQUtil.convertAndSend("财务", "作废收款单",
                "作废收款单：订单编号" + payOrder.getOrderCode()));

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 新增收款单
     *
     * @param receivableAddRequest receivableAddRequest
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "新增收款单")
    @RequestMapping(value = "/receivable", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> addReceivable(@RequestBody @Valid ReceivableAddDTO receivableAddRequest,
                                                      HttpServletRequest request) {
        Operator operator = buildEmployee(request);

        TradeAddReceivableRequest tradeAddReceivableRequest = TradeAddReceivableRequest.builder()
                .receivableAddDTO(receivableAddRequest)
                .platform(operator.getPlatform())
                .operator(commonUtil.getOperator())
                .build();

        tradeProvider.addReceivable(tradeAddReceivableRequest);

        //操作日志记录
        if (Objects.nonNull(receivableAddRequest.getPayOrderId())) {
            Optional<PayOrderVO> payOrderOptional = this.getPayOrderCode(receivableAddRequest.getPayOrderId());
            payOrderOptional.ifPresent(payOrder -> operateLogMQUtil.convertAndSend("财务", "确认收款",
                    "确认收款：订单编号" + payOrder.getOrderCode()));
        }
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }


    /**
     * 查询所有收款单价格
     *
     * @return 收款单价格
     */
    @Operation(summary = "查询所有收款单价格")
    @RequestMapping(value = "/sumPayOrderPrice", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse<BigDecimal>> sumPayOrderPrice(@RequestBody SumPayOrderPriceRequest payOrderRequest) {

        BaseResponse<SumPayOrderPriceResponse> responseBaseResponse =
                payOrderQueryProvider.sumPayOrderPrice(payOrderRequest);

        return ResponseEntity.ok(BaseResponse.success(responseBaseResponse.getContext().getValue()));
    }


    /**
     * 导出订单收款
     * @return
     */
    @Operation(summary = "订单收款")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse export(@PathVariable String encrypted ) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        FindPayOrdersRequest payOrderRequest = JSON.parseObject(decrypted, FindPayOrdersRequest.class);
        Operator operator = commonUtil.getOperator();

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setTypeCd(ReportType.BUSINESS_PAY_ORDER);
        exportDataRequest.setParam(JSONObject.toJSONString(payOrderRequest));
        exportDataRequest.setOperator(operator);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    private Operator buildEmployee(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String employeeId = claims.get("employeeId").toString();
        String employeeName = claims.get("EmployeeName").toString();
        String ip = claims.get("ip").toString();
        return Operator.builder()
                .ip(ip)
                .platform(Platform.forValue(ObjectUtils.toString(claims.get("platform"))))
                .userId(employeeId).name(employeeName)
                .account(employeeName)
                .build();
    }

    /**
     * 根据payOrderId获取PayOrder对象
     *
     * @param payOrderId
     * @return
     */
    public Optional<PayOrderVO> getPayOrderCode(String payOrderId) {
        FindPayOrderByPayOrderIdsResponse response =
                payOrderQueryProvider.findPayOrderByPayOrderIds(new FindPayOrderByPayOrderIdsRequest(Lists.newArrayList(payOrderId))).getContext();
        if (Objects.isNull(response)) {
            return Optional.empty();
        }
        return Optional.of(response.getOrders().get(0));
    }
}
