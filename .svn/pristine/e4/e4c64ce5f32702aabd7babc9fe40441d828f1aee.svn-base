package com.wanmi.sbc.account;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.excel.Column;
import com.wanmi.sbc.common.util.excel.ExcelHelper;
import com.wanmi.sbc.common.util.excel.impl.SpelColumnRender;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailListByConditionRequest;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailListByConditionResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.order.api.provider.refund.RefundBillProvider;
import com.wanmi.sbc.order.api.provider.refund.RefundOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderProvider;
import com.wanmi.sbc.order.api.request.refund.*;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderRejectRefundRequest;
import com.wanmi.sbc.order.api.response.refund.RefundOrderListReponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderPageResponse;
import com.wanmi.sbc.order.api.response.refund.RefundOrderWithoutPageResponse;
import com.wanmi.sbc.order.bean.vo.RefundOrderResponse;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;
import io.jsonwebtoken.Claims;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 退款单
 * Created by zhangjin on 2017/4/21.
 */
@Tag(name = "RefundOrderController", description = "退款单")
@RestController
@RequestMapping("/account")
@Validated
public class RefundOrderController {

    @Autowired
    private RefundOrderQueryProvider refundOrderQueryProvider;

    @Autowired
    private RefundBillProvider refundBillProvider;

    @Autowired
    private ReturnOrderProvider returnOrderProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Autowired
    private ExportCenter exportCenter;

    /**
     * 导出文件名后的时间后缀
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    /**
     * 查询订单退款
     *
     * @param refundOrderRequest refundOrderRequest
     * @return status
     */
    @Operation(summary = "查询订单退款")
    @EmployeeCheck
    @RequestMapping(value = "/refundOrders", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_find_return_pay_order_sign_word")
    public BaseResponse<RefundOrderPageResponse> findPayOrder(@RequestBody RefundOrderPageRequest refundOrderRequest) {
        refundOrderRequest.setSupplierId(commonUtil.getCompanyInfoId());
        BaseResponse<RefundOrderPageResponse> response = refundOrderQueryProvider.page(refundOrderRequest);
        List<RefundOrderResponse> responseList = response.getContext().getData();
        if (CollectionUtils.isEmpty(responseList)) {
            return response;
        }

        List<String> detailIdList = responseList.stream().map(RefundOrderResponse::getCustomerDetailId).collect(Collectors.toList());
        CustomerDetailListByConditionRequest request = CustomerDetailListByConditionRequest.builder()
                .customerDetailIds(detailIdList)
                .build();

        CustomerDetailListByConditionResponse detailResponse =
                customerDetailQueryProvider.listCustomerDetailByCondition(request).getContext();
        List<CustomerDetailVO> customerDetailVOList = detailResponse.getCustomerDetailVOList();
        Map<String, String> customerNameMap = customerDetailVOList.stream()
                .collect(Collectors.toMap(CustomerDetailVO::getCustomerDetailId, CustomerDetailVO::getCustomerName));

        List<String> customerIds = responseList.stream().map(RefundOrderResponse::getCustomerId).collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        List<RefundOrderResponse> newList = responseList.stream().peek(resp -> {
            String customerName = customerNameMap.get(resp.getCustomerDetailId());
            resp.setCustomerName(customerName);
            resp.setLogOutStatus(map.get(resp.getCustomerId()));
        }).collect(Collectors.toList());
        response.getContext().setData(newList);
        return response;

    }

    /**
     * 拒绝退款
     *
     * @param refuseReasonRequest refuseReasonRequest
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "拒绝退款")
    @RequestMapping(value = "/refundOrders/refuse", method = RequestMethod.PUT)
    public ResponseEntity<BaseResponse> refuse(@RequestBody RefuseReasonRequest refuseReasonRequest) {
        returnOrderProvider.rejectRefundAndRefuse(ReturnOrderRejectRefundRequest.builder()
                .rid(refuseReasonRequest.getRefundId()).reason(refuseReasonRequest.getRefuseReason())
                .operator(commonUtil.getOperator()).build());
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 新增退单流水
     *
     * @param refundBillRequest refundBillRequest
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "新增退单流水")
    @RequestMapping(value = "/refundBill", method = RequestMethod.POST)
    @Transactional
    @GlobalTransactional
    public ResponseEntity<BaseResponse> addRefundBill(@RequestBody RefundBillAddRequest refundBillRequest) {
        Operator operator = commonUtil.getOperator();
        refundBillRequest.setOperator(operator);
        refundBillProvider.add(refundBillRequest);
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 根据退单号查询
     *
     * @return BaseResponse<RefundBill>
     */
    @Operation(summary = "根据退单号查询")
    @Parameter(name = "returnOrderNo", description = "退单号", required = true)
    @GetMapping(value = "/refundOrders/{returnOrderNo}")
    @ReturnSensitiveWords(functionName = "f_supplier_query_refund_by_return_order_sign_word")
    public BaseResponse<RefundOrderListReponse> queryRefundByReturnOrderNo(@PathVariable("returnOrderNo") String returnOrderNo) {

        BaseResponse<RefundOrderListReponse> refundOrderListReponse = refundOrderQueryProvider
                .getRefundOrderRespByReturnOrderCode(new RefundOrderResponseByReturnOrderCodeRequest(returnOrderNo));

        List<String> customerIds = refundOrderListReponse.getContext().getRefundOrderResponseList()
                .stream()
                .map(com.wanmi.sbc.order.api.response.refund.RefundOrderResponse::getCustomerId)
                .collect(Collectors.toList());

        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);

        refundOrderListReponse.getContext().getRefundOrderResponseList().forEach(refundOrderResponse -> {
            //判断订单会员是否注销
            refundOrderResponse.setLogOutStatus(map.get(refundOrderResponse.getCustomerId()));
            //越权校验
            commonUtil.checkStoreId(refundOrderResponse.getStoreId());
        });
        return refundOrderListReponse;
    }

    /**
     * 求和退款金额
     *
     * @return
     */
    @Operation(summary = "求和退款金额")
    @RequestMapping(value = "/sumReturnPrice", method = RequestMethod.POST)
    public BaseResponse<BigDecimal> sumReturnPrice(@RequestBody RefundOrderRequest refundOrderRequest) {
        return BaseResponse.success(refundOrderQueryProvider.getSumReturnPrice(refundOrderRequest).getContext().getResult());
    }

    /**
     * 退单退款导出
     */
    @Operation(summary = "退单退款导出")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/export/refund/{encrypted}", method = RequestMethod.GET)
    public BaseResponse exportByParams(@PathVariable String encrypted){
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes()));
        RefundOrderRequest refundOrderRequest = JSON.parseObject(decrypted, RefundOrderRequest.class);
        Operator operator = commonUtil.getOperator();

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setTypeCd(ReportType.BUSINESS_REFUND_ORDER);
        exportDataRequest.setParam(JSONObject.toJSONString(refundOrderRequest));
        exportDataRequest.setOperator(operator);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

}
