package com.wanmi.sbc.account;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.account.api.provider.invoice.InvoiceProjectQueryProvider;
import com.wanmi.sbc.account.api.request.invoice.InvoiceProjectByIdRequest;
import com.wanmi.sbc.account.api.response.invoice.InvoiceProjectByIdResponse;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.account.bean.enums.InvoiceState;
import com.wanmi.sbc.account.bean.enums.InvoiceType;
import com.wanmi.sbc.account.request.OrderInvoiceSaveRequest;
import com.wanmi.sbc.account.response.OrderInvoiceDetailResponse;
import com.wanmi.sbc.account.response.OrderInvoiceViewResponse;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseQueryResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.invoice.CustomerInvoiceQueryProvider;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateRequest;
import com.wanmi.sbc.customer.api.response.invoice.CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.elastic.api.provider.orderinvoice.EsOrderInvoiceProvider;
import com.wanmi.sbc.elastic.api.provider.orderinvoice.EsOrderInvoiceQueryProvider;
import com.wanmi.sbc.elastic.api.request.orderinvoice.EsOrderInvoiceDeleteRequest;
import com.wanmi.sbc.elastic.api.request.orderinvoice.EsOrderInvoiceFindAllRequest;
import com.wanmi.sbc.elastic.api.request.orderinvoice.EsOrderInvoiceGenerateRequest;
import com.wanmi.sbc.elastic.api.request.orderinvoice.EsOrderInvoiceModifyStateRequest;
import com.wanmi.sbc.elastic.api.response.orderinvoice.EsOrderInvoiceResponse;
import com.wanmi.sbc.order.api.provider.orderinvoice.OrderInvoiceProvider;
import com.wanmi.sbc.order.api.provider.orderinvoice.OrderInvoiceQueryProvider;
import com.wanmi.sbc.order.api.provider.payorder.PayOrderQueryProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.orderinvoice.*;
import com.wanmi.sbc.order.api.request.payorder.FindPayOrderByOrderCodeRequest;
import com.wanmi.sbc.order.api.request.trade.TradeAddInvoiceRequest;
import com.wanmi.sbc.order.api.request.trade.TradeGetByIdRequest;
import com.wanmi.sbc.order.api.response.orderinvoice.OrderInvoiceFindByOrderInvoiceIdAndDelFlagResponse;
import com.wanmi.sbc.order.bean.dto.GeneralInvoiceDTO;
import com.wanmi.sbc.order.bean.dto.InvoiceDTO;
import com.wanmi.sbc.order.bean.dto.OrderInvoiceDTO;
import com.wanmi.sbc.order.bean.dto.SpecialInvoiceDTO;
import com.wanmi.sbc.order.bean.vo.*;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;

import io.jsonwebtoken.Claims;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单开票服务
 * Created by CHENLI on 2017/5/5.
 */
@Tag(name = "OrderInvoiceController", description = "订单开票服务 Api")
@RestController
@Validated
@RequestMapping("/account")
public class OrderInvoiceController {

    @Autowired
    private OrderInvoiceQueryProvider orderInvoiceQueryProvider;

    @Autowired
    private OrderInvoiceProvider orderInvoiceProvider;

    @Autowired
    private OrderInvoiceDetailService orderInvoiceDetailService;

    @Autowired
    private CustomerInvoiceQueryProvider customerInvoiceQueryProvider;

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private InvoiceProjectQueryProvider invoiceProjectQueryProvider;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private PayOrderQueryProvider payOrderQueryProvider;

    @Autowired
    private EsOrderInvoiceProvider esOrderInvoiceProvider;

    @Autowired
    private EsOrderInvoiceQueryProvider esOrderInvoiceQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerCacheService customerCacheService;

    /**
     * 分页查询订单开票
     *
     * @param queryRequest
     * @return
     */
    @Operation(summary = "分页查询订单开票")
    @EmployeeCheck(customerIdField = "employeeCustomerIds")
    @RequestMapping(value = "/orderInvoices", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_account_order_invoices_sign_word")
    public ResponseEntity<BaseResponse> page(@RequestBody EsOrderInvoiceFindAllRequest queryRequest) {
        //这里有个问题
        queryRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        queryRequest.setStoreId(commonUtil.getStoreId());
        BaseResponse<BaseQueryResponse<EsOrderInvoiceResponse>> orderInvoicePage = esOrderInvoiceQueryProvider.findOrderInvoicePage(queryRequest);

        List<EsOrderInvoiceResponse> data = orderInvoicePage.getContext().getData();

        //判断客户是否已经注销
        List<String> customerIds = data
                .stream()
                .map(EsOrderInvoiceResponse::getCustomerId)
                .distinct()
                .collect(Collectors.toList());

        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);

        data.forEach(v-> v.setLogOutStatus(map.get(v.getCustomerId())));

        return ResponseEntity.ok(orderInvoicePage);
    }

    /**
     * 新增订单开票信息
     *
     * @param saveRequest
     * @return
     */
    @Operation(summary = "新增订单开票信息")
    @RequestMapping(value = "/orderInvoice", method = RequestMethod.POST)
    @Transactional
    @GlobalTransactional
    public ResponseEntity<BaseResponse> save(@Valid @RequestBody OrderInvoiceSaveRequest saveRequest, HttpServletRequest request) {
        Long storeId = commonUtil.getStoreId();
        //订单开票前判断订单的状态
        orderInvoiceDetailService.findOrderCheckState(saveRequest.getOrderNo(), storeId);

        OrderInvoiceGetByOrderNoRequest orderInvoiceGetByOrderNoRequest = OrderInvoiceGetByOrderNoRequest.builder().orderNo(saveRequest.getOrderNo()).build();

        //订单已开票
        if (orderInvoiceQueryProvider.getByOrderNo(orderInvoiceGetByOrderNoRequest).getContext().getOrderInvoiceVO() != null) {
            return ResponseEntity.ok(BaseResponse.error("该订单已开过票"));
        }
        //如果开的是增票，判断客户是否具备增票资质
        CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateRequest customerInvoiceByCustomerIdAndDelFlagAndCheckStateRequest = new CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateRequest();
        customerInvoiceByCustomerIdAndDelFlagAndCheckStateRequest.setCustomerId(saveRequest.getCustomerId());
        BaseResponse<CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateResponse> customerInvoiceByCustomerIdAndDelFlagAndCheckStateResponseBaseResponse = customerInvoiceQueryProvider.getByCustomerIdAndDelFlagAndCheckState(customerInvoiceByCustomerIdAndDelFlagAndCheckStateRequest);
        CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateResponse response = customerInvoiceByCustomerIdAndDelFlagAndCheckStateResponseBaseResponse.getContext();
        if (saveRequest.getInvoiceType() == InvoiceType.SPECIAL && Objects.isNull(response)) {
            return ResponseEntity.ok(BaseResponse.error("增票资质审核未通过"));
        }
        String employeeId = ((Claims) request.getAttribute("claims")).get("employeeId").toString();
        saveRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        saveRequest.setStoreId(storeId);

        OrderInvoiceGenerateRequest orderInvoiceGenerateRequest = OrderInvoiceGenerateRequest.builder()
                .employeeId(employeeId).invoiceState(InvoiceState.ALREADY)
                .orderInvoiceDTO(KsBeanUtil.convert(saveRequest, OrderInvoiceDTO.class)).build();

        Optional<OrderInvoiceVO> remotecall =
                Optional.ofNullable(
                        orderInvoiceProvider.generateOrderInvoice(orderInvoiceGenerateRequest).getContext().getOrderInvoiceVO());

        if (remotecall.isPresent()) {

            OrderInvoiceVO orderInvoice = remotecall.get();
            InvoiceProjectByIdRequest invoiceProjectByIdRequest = new InvoiceProjectByIdRequest();
            invoiceProjectByIdRequest.setProjcetId(orderInvoice.getProjectId());
            BaseResponse<InvoiceProjectByIdResponse> baseResponse = invoiceProjectQueryProvider.getById(invoiceProjectByIdRequest);
            InvoiceProjectByIdResponse invoiceProjectByIdResponse = baseResponse.getContext();
            InvoiceDTO invoice = InvoiceDTO.builder()
                    .orderInvoiceId(orderInvoice.getOrderInvoiceId())
                    .type(orderInvoice.getInvoiceType().toValue())
                    .contacts(saveRequest.getContacts())
                    .projectId(orderInvoice.getProjectId())
                    .projectName(invoiceProjectByIdResponse.getProjectName())
                    .generalInvoice(orderInvoice.getInvoiceType() == InvoiceType.NORMAL ? StringUtils.isBlank(orderInvoice.getInvoiceTitle()) ?
                            GeneralInvoiceDTO.builder().title("").flag(0).build()
                            : GeneralInvoiceDTO.builder().title(orderInvoice.getInvoiceTitle()).flag(1).identification(orderInvoice.getTaxpayerNumber()).build() : null)
                    .phone(saveRequest.getPhone())
                    .address(saveRequest.getAddress())
                    .taxNo(orderInvoice.getTaxpayerNumber())
                    .projectUpdateTime(DateUtil.format(invoiceProjectByIdResponse.getUpdateTime() != null ? invoiceProjectByIdResponse.getUpdateTime() : LocalDateTime.now(), DateUtil.FMT_TIME_1))
                    .addressId(saveRequest.getAddressInfoId())
                    .sperator(true)
                    .build();
            if (orderInvoice.getInvoiceType() == InvoiceType.SPECIAL) {
                BaseResponse<CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateResponse> invoiceByCustomerIdAndDelFlagAndCheckStateResponseBaseResponse =
                        customerInvoiceQueryProvider.getByCustomerIdAndDelFlagAndCheckState(
                                CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateRequest.builder()
                                        .customerId(saveRequest.getCustomerId()).build()
                        );
                CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateResponse invoiceByCustomerIdAndDelFlagAndCheckStateResponse = invoiceByCustomerIdAndDelFlagAndCheckStateResponseBaseResponse.getContext();
                if (Objects.nonNull(invoiceByCustomerIdAndDelFlagAndCheckStateResponse)) {
                    SpecialInvoiceDTO spInvoice = new SpecialInvoiceDTO();
                    if (invoiceByCustomerIdAndDelFlagAndCheckStateResponse.getCheckState() != CheckState.CHECKED) {
                        throw new SbcRuntimeException(CustomerErrorCodeEnum.K010015);
                    }
                    spInvoice.setId(invoiceByCustomerIdAndDelFlagAndCheckStateResponse.getCustomerInvoiceId());
                    spInvoice.setAccount(invoiceByCustomerIdAndDelFlagAndCheckStateResponse.getBankNo());
                    spInvoice.setIdentification(invoiceByCustomerIdAndDelFlagAndCheckStateResponse.getTaxpayerNumber());
                    spInvoice.setAddress(invoiceByCustomerIdAndDelFlagAndCheckStateResponse.getCompanyAddress());
                    spInvoice.setBank(invoiceByCustomerIdAndDelFlagAndCheckStateResponse.getBankName());
                    spInvoice.setCompanyName(invoiceByCustomerIdAndDelFlagAndCheckStateResponse.getCompanyName());
                    spInvoice.setPhoneNo(invoiceByCustomerIdAndDelFlagAndCheckStateResponse.getCompanyPhone());
                    invoice.setSpecialInvoice(spInvoice);
                }
            }

            TradeAddInvoiceRequest tradeAddInvoiceRequest = TradeAddInvoiceRequest.builder()
                    .tid(orderInvoice.getOrderNo())
                    .invoiceDTO(invoice)
                    .build();
            tradeProvider.saveInvoice(tradeAddInvoiceRequest);
            // tradeService.saveInvoice(orderInvoice.getOrderNo(), invoice);
            operateLogMQUtil.convertAndSend("财务", "新增开票",
                    "新增开票：订单号" + orderInvoice.getOrderNo());

            //数据同步es
            if (Objects.isNull(saveRequest.getInvoiceTitle())){
                saveRequest.setInvoiceTitle(orderInvoice.getInvoiceTitle());
            }
            EsOrderInvoiceGenerateRequest invoiceSaveRequest = this.initOrderInvoiceSaveRequest(saveRequest, orderInvoice.getOrderInvoiceId());

            esOrderInvoiceProvider.addEsOrderInvoice(invoiceSaveRequest);
        }


        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 初始化需要展示在b端列表的数据，一同入到es索引中
     *
     * @param saveRequest
     * @return
     */
    private EsOrderInvoiceGenerateRequest initOrderInvoiceSaveRequest(OrderInvoiceSaveRequest saveRequest, String id) {
        EsOrderInvoiceGenerateRequest request = EsOrderInvoiceGenerateRequest.builder().build();
        BeanUtils.copyProperties(saveRequest, request);
        request.setInvoiceType(saveRequest.getInvoiceType().toValue());
        request.setInvoiceState(InvoiceState.ALREADY.toValue());
        request.setOrderInvoiceId(id);
        TradeGetByIdRequest tradeGetByIdRequest = TradeGetByIdRequest.builder()
                .tid(saveRequest.getOrderNo())
                .build();
        //订单相关信息
        TradeVO trade = tradeQueryProvider.getById(tradeGetByIdRequest).getContext().getTradeVO();
        if (Objects.nonNull(trade)) {
            saveRequest.setOrderNo(trade.getId());
            if (Objects.nonNull(trade.getTradePrice())) {
                request.setOrderPrice(trade.getTradePrice().getTotalPrice());
            }
            if (Objects.nonNull(trade.getBuyer())) {
                //客户相关信息
                request.setCustomerId(trade.getBuyer().getId());
                request.setCustomerName(trade.getBuyer().getName());
            }
            SupplierVO supplier = trade.getSupplier();
            if (Objects.nonNull(supplier)) {
                request.setSupplierName(supplier.getSupplierName());
                //如何是o2o商家，缓存门店名称
                if (supplier.getStoreType() == StoreType.O2O) {
                    request.setStoreName(supplier.getStoreName());
                }
            }
            //流程状态（订单订单状态）
            TradeStateVO tradeState = trade.getTradeState();
            if(Objects.nonNull(tradeState)){
                request.setFlowState(tradeState.getFlowState());
            }
        }
        FindPayOrderByOrderCodeRequest requeststr = FindPayOrderByOrderCodeRequest.builder().value(saveRequest.getOrderNo()).build();
        PayOrderVO value = payOrderQueryProvider.findPayOrderByOrderCode(requeststr).getContext().getValue();
        if (Objects.nonNull(value)) {
            request.setPayOrderStatus(value.getPayOrderStatus().toValue());
        }
        return request;
    }


    /**
     * 订单开票详情/新增订单开票时
     *
     * @param orderNo
     * @return
     */
    @Operation(summary = "订单开票详情/新增订单开票时")
    @Parameter(name = "orderNo", description = "订单编号", required = true)
    @RequestMapping(value = "/orderInvoiceDetail/{orderNo}", method = RequestMethod.GET)
    public ResponseEntity<BaseResponse<OrderInvoiceDetailResponse>> findOrderInvoiceDetail(@PathVariable String orderNo) {
        //新增订单开票时判断订单的状态
        orderInvoiceDetailService.findOrderCheckState(orderNo, commonUtil.getStoreId());
        OrderInvoiceGetByOrderNoRequest orderInvoiceGetByOrderNoRequest = OrderInvoiceGetByOrderNoRequest.builder()
                .orderNo(orderNo).build();

        //订单已开票
        if (orderInvoiceQueryProvider.getByOrderNo(orderInvoiceGetByOrderNoRequest).getContext().getOrderInvoiceVO() != null) {
            return ResponseEntity.ok(BaseResponse.error("该订单已开过票"));
        }
        OrderInvoiceDetailResponse response = orderInvoiceDetailService.findOrderInvoiceDetail(orderNo);
        return ResponseEntity.ok(BaseResponse.success(response));
    }


    /**
     * 根据开票单号查询
     *
     * @param orderInvoiceId orderInvoiceId
     * @return ResponseEntity<BaseResponse                                                                                                                                                                                                                                                               <                                                                                                                                                                                                                                                               OrderInvoiceViewResponse>>
     */
    @Operation(summary = "根据开票单号查询")
    @Parameter(name = "orderInvoiceId", description = "订单发票ID",
            required = true)
    @RequestMapping(value = "/orderInvoice/{orderInvoiceId}", method = RequestMethod.GET)
    @ReturnSensitiveWords(functionName = "f_boss_find_order_invoice_view_sign_word")
    public ResponseEntity<BaseResponse<OrderInvoiceViewResponse>> findOrderInvoiceView(@PathVariable("orderInvoiceId") String orderInvoiceId) {
        OrderInvoiceViewResponse response = orderInvoiceDetailService.findByOrderInvoiceId(orderInvoiceId);
        response.setLogOutStatus(customerCacheService.getCustomerLogOutStatus(response.getCustomerId()));
        commonUtil.checkCompanyInfoId(response.getCompanyInfoId());
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    /**
     * 订单批量/单个开票
     *
     * @param editRequest
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "订单批量/单个开票")
    @RequestMapping(value = "/orderInvoiceState", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> updateOrderInvoiceState(@Valid @RequestBody OrderInvoiceModifyStateRequest editRequest) {
        orderInvoiceProvider.modifyOrderInvoiceState(editRequest);

        //同步es
        List<String> orderInvoiceIds = editRequest.getOrderInvoiceIds();
        EsOrderInvoiceModifyStateRequest stateRequest = new EsOrderInvoiceModifyStateRequest(orderInvoiceIds,1);
        esOrderInvoiceProvider.modifyState(stateRequest);

        //
        //记录日志
        if (1 == editRequest.getOrderInvoiceIds().size()) {
            OrderInvoiceFindByOrderInvoiceIdAndDelFlagResponse response =
                    orderInvoiceQueryProvider.findByOrderInvoiceIdAndDelFlag(OrderInvoiceFindByOrderInvoiceIdAndDelFlagRequest.builder().id(editRequest.getOrderInvoiceIds().get(0)).flag(DeleteFlag.NO).build()).getContext();
            String orderNo = Objects.nonNull(response) && Objects.nonNull(response.getOrderInvoiceVO()) ?
                    response.getOrderInvoiceVO().getOrderNo() : "";
            operateLogMQUtil.convertAndSend("财务", "开票", "开票：订单号" + orderNo);
            //开票信息不存在，则删除ES对应开票信息
            if(response == null || response.getOrderInvoiceVO() == null){
                esOrderInvoiceProvider.delete(EsOrderInvoiceDeleteRequest.builder().orderInvoiceId(editRequest.getOrderInvoiceIds().get(0)).build());
            }
        } else {
            operateLogMQUtil.convertAndSend("财务", "批量开票", "批量开票");
        }

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 订单发票作废
     *
     * @param orderInvoiceId
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "订单发票作废")
    @Parameter(name = "orderInvoiceId", description = "订单发票ID",
            required = true)
    @RequestMapping(value = "/orderInvoice/{orderInvoiceId}", method = RequestMethod.PUT)
    public ResponseEntity<BaseResponse> invalidInvoice(@PathVariable String orderInvoiceId) {
        OrderInvoiceFindByOrderInvoiceIdAndDelFlagResponse response =
                orderInvoiceQueryProvider.findByOrderInvoiceIdAndDelFlag(OrderInvoiceFindByOrderInvoiceIdAndDelFlagRequest.builder().id(orderInvoiceId).flag(DeleteFlag.NO).build()).getContext();

        String orderNo = Objects.nonNull(response) && Objects.nonNull(response.getOrderInvoiceVO()) ?
                response.getOrderInvoiceVO().getOrderNo() : "";
        //开票信息不存在，则删除ES对应开票信息
        if(response == null || response.getOrderInvoiceVO() == null){
            esOrderInvoiceProvider.delete(EsOrderInvoiceDeleteRequest.builder().orderInvoiceId(orderInvoiceId).build());
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020022);
        }

        OrderInvoiceInvalidRequest request = OrderInvoiceInvalidRequest.builder().orderInvoiceId(orderInvoiceId).build();
        orderInvoiceProvider.invalid(request);

        //同步es
        EsOrderInvoiceModifyStateRequest stateRequest = new EsOrderInvoiceModifyStateRequest(Collections.singletonList(orderInvoiceId),0);
        esOrderInvoiceProvider.modifyState(stateRequest);

        // 记录日志
        operateLogMQUtil.convertAndSend("财务", "作废", "作废：订单号" + orderNo);

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 导出订单开票
     *
     * @return
     */
    @Operation(summary = "导出订单开票")
    @EmployeeCheck(customerIdField = "employeeCustomerIds")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/export/orderInvoices/{encrypted}", method = RequestMethod.GET)
    public BaseResponse exportByParams(@PathVariable String encrypted, OrderInvoiceFindAllRequest request) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        OrderInvoiceFindAllRequest orderInvoiceQueryRequest = JSON.parseObject(decrypted, OrderInvoiceFindAllRequest.class);
        logger.info("/export/orderInvoices/*, employeeId={}", commonUtil.getOperatorId());
        orderInvoiceQueryRequest.setEmployeeCustomerIds(request.getEmployeeCustomerIds());
        orderInvoiceQueryRequest.setStoreId(commonUtil.getStoreId());

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(JSONObject.toJSONString(orderInvoiceQueryRequest));
        exportDataRequest.setTypeCd(ReportType.BUSINESS_ORDER_TICKET);
        exportDataRequest.setOperator(commonUtil.getOperator());
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 删除订单开票信息
     *
     * @param orderInvoiceId
     * @return
     */
    @GlobalTransactional
    @Operation(summary = "删除订单开票信息")
    @Parameter(name = "orderInvoiceId", description = "订单发票ID",
            required = true)
    @RequestMapping(value = "/orderInvoice/{orderInvoiceId}", method = RequestMethod.DELETE)
    public ResponseEntity<BaseResponse> deleteOrderInvoice(@PathVariable String orderInvoiceId) {
        OrderInvoiceFindByOrderInvoiceIdAndDelFlagResponse response =
                orderInvoiceQueryProvider.findByOrderInvoiceIdAndDelFlag(OrderInvoiceFindByOrderInvoiceIdAndDelFlagRequest.builder().id(orderInvoiceId).flag(DeleteFlag.NO).build()).getContext();

        if(Objects.isNull(response.getOrderInvoiceVO())){
            throw new SbcRuntimeException(AccountErrorCodeEnum.K020022);
        }

        orderInvoiceProvider.delete(OrderInvoiceDeleteRequest.builder().orderInvoiceId(orderInvoiceId).build());

        //同步es
        esOrderInvoiceProvider.delete(EsOrderInvoiceDeleteRequest.builder().orderInvoiceId(orderInvoiceId).build());

        // 记录日志
        operateLogMQUtil.convertAndSend("财务", "删除开票项目",
                "删除开票项目：项目名称" +
                        (Objects.nonNull(response.getOrderInvoiceVO()) && Objects.nonNull(response.getOrderInvoiceVO().getInvoiceProject()) ?
                        response.getOrderInvoiceVO().getInvoiceProject().getProjectName() : ""));

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }
}
