package com.wanmi.sbc.customer;

import com.google.common.collect.Lists;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseQueryResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.invoice.CustomerInvoiceProvider;
import com.wanmi.sbc.customer.api.provider.invoice.CustomerInvoiceQueryProvider;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceAddRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceAuditingRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceByCustomerIdAndDelFlagAndCheckStateRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceByCustomerIdAndDelFlagRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceByIdAndDelFlagRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceByTaxpayerNumberRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceConfigAddRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceDeleteRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceInvalidRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceModifyRequest;
import com.wanmi.sbc.customer.api.request.invoice.CustomerInvoiceRejectRequest;
import com.wanmi.sbc.customer.api.response.invoice.*;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.InvoiceStyle;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.CustomerInvoiceVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.elastic.api.provider.customerInvoice.EsCustomerInvoiceProvider;
import com.wanmi.sbc.elastic.api.provider.customerInvoice.EsCustomerInvoiceQueryProvider;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceAddRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceAuditingRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceDeleteRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceInvalidRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceModifyRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoicePageRequest;
import com.wanmi.sbc.elastic.api.request.customerInvoice.EsCustomerInvoiceRejectRequest;
import com.wanmi.sbc.elastic.api.response.customerInvoice.EsCustomerInvoicePageResponse;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.response.InvoiceConfigGetResponse;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.jsonwebtoken.Claims;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * 会员增专票bff
 * Created by CHENLI on 2017/4/21.
 */
@Tag(name = "CustomerInvoiceController", description = "会员增专票")
@RestController
@Validated
@RequestMapping("/customer")
public class CustomerInvoiceController {

    @Autowired
    private CustomerInvoiceQueryProvider customerInvoiceQueryProvider;

    @Autowired
    private CustomerInvoiceProvider customerInvoiceProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsCustomerInvoiceQueryProvider esCustomerInvoiceQueryProvider;

    @Autowired
    private EsCustomerInvoiceProvider esCustomerInvoiceProvider;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * 分页查询会员增专票信息
     *
     * @param queryRequest
     * @return
     */
    @Operation(summary = "分页查询会员增专票信息")
    @EmployeeCheck(customerIdField = "employeeCustomerIds")
    @RequestMapping(value = "/invoices", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_customer_invoices_sign_word")
    public ResponseEntity<BaseResponse> page(@RequestBody EsCustomerInvoicePageRequest queryRequest){
        queryRequest.putSort("createTime", SortType.DESC.toValue());
        queryRequest.setInvoiceStyle(InvoiceStyle.SPECIAL);
        BaseResponse<EsCustomerInvoicePageResponse> page = esCustomerInvoiceQueryProvider.page(queryRequest);
        EsCustomerInvoicePageResponse esCustomerInvoicePageResponse = page.getContext();
        BaseQueryResponse<CustomerInvoiceVO> baseQueryResponse = new BaseQueryResponse<>();
        if (Objects.isNull(esCustomerInvoicePageResponse)){
            baseQueryResponse.setData(Lists.newArrayList());
            baseQueryResponse.setTotal(0L);
            baseQueryResponse.setPageNum(queryRequest.getPageNum());
            baseQueryResponse.setPageSize(queryRequest.getPageSize());
            return ResponseEntity.ok(BaseResponse.success(baseQueryResponse));
        }
        MicroServicePage<CustomerInvoiceVO> microServicePage =  esCustomerInvoicePageResponse.getCustomerInvoiceVOPage();
        if (Objects.nonNull(microServicePage)){
            //获取会员注销状态
            List<String> customerIds = microServicePage.getContent()
                    .stream()
                    .map(CustomerInvoiceVO::getCustomerId)
                    .collect(Collectors.toList());
            Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
            microServicePage.getContent().forEach(v -> v.setLogOutStatus(map.get(v.getCustomerId())));

            baseQueryResponse.setData(microServicePage.getContent());
            baseQueryResponse.setTotal(microServicePage.getTotal());
            baseQueryResponse.setPageNum(microServicePage.getNumber());
            baseQueryResponse.setPageSize(microServicePage.getSize());
        }
        return ResponseEntity.ok(BaseResponse.success(baseQueryResponse));
    }

    /**
     * 根据会员ID查询会员增专票
     *
     * @param customerId
     * @return ResponseEntity<CustomerInvoice>
     */
    @Operation(summary = "根据会员ID查询会员增专票")
    @Parameter(name = "customerId", description = "会员Id", required = true)
    @RequestMapping(value = "/invoice/{customerId}",method = RequestMethod.GET)
    public ResponseEntity<CustomerInvoiceByCustomerIdAndDelFlagResponse> findByCustomerIdAndDelFlag(@PathVariable("customerId") String customerId) {
        CustomerInvoiceByCustomerIdAndDelFlagRequest customerInvoiceByCustomerIdAndDelFlagRequest = new CustomerInvoiceByCustomerIdAndDelFlagRequest();
        customerInvoiceByCustomerIdAndDelFlagRequest.setCustomerId(customerId);
        BaseResponse<CustomerInvoiceByCustomerIdAndDelFlagResponse> customerInvoiceByCustomerIdAndDelFlagResponseBaseResponse = customerInvoiceQueryProvider.getSpecialByCustomerIdAndDelFlag(customerInvoiceByCustomerIdAndDelFlagRequest);
        CustomerInvoiceByCustomerIdAndDelFlagResponse response = customerInvoiceByCustomerIdAndDelFlagResponseBaseResponse.getContext();
        if (Objects.equals(Platform.PLATFORM,commonUtil.getOperator().getPlatform())){
            //平台端查看会员赠票资质判断是否审核通过
            if (Objects.nonNull(response) && Objects.equals(CheckState.NOT_PASS,response.getCheckState())){
                return ResponseEntity.ok(CustomerInvoiceByCustomerIdAndDelFlagResponse.builder().build());
            }
        }
        return ResponseEntity.ok(Objects.nonNull(response) ? response : CustomerInvoiceByCustomerIdAndDelFlagResponse.builder().build());
    }

    /**
     * 根据会员ID查询会员增专票
     *
     * @param customerId
     * @return ResponseEntity<CustomerInvoice>
     */
    @Operation(summary = "根据会员ID查询会员增专票")
    @Parameter(name = "customerId", description = "会员Id", required = true)
    @RequestMapping(value = "/invoiceInfos/{customerId}",method = RequestMethod.GET)
    public BaseResponse<CustomerInvoiceByCustomerIdResponse> findInfoByCustomerId(@PathVariable("customerId") String customerId) {
        CustomerInvoiceByCustomerIdRequest request = new CustomerInvoiceByCustomerIdRequest();
        request.setCustomerId(customerId);
        BaseResponse<CustomerInvoiceByCustomerIdResponse> customerInvoiceByCustomerIdResponseBaseResponse = customerInvoiceQueryProvider.getByCustomerId(request);
        CustomerInvoiceByCustomerIdResponse customerInvoiceByCustomerIdResponse = customerInvoiceByCustomerIdResponseBaseResponse.getContext();
        return BaseResponse.success(customerInvoiceByCustomerIdResponse);
    }

    /**
     * 根据增专票ID查询会员增专票
     *
     * @param customerInvoiceId
     * @return ResponseEntity<CustomerInvoice>
     */
    @Operation(summary = "根据会员ID查询会员增专票")
    @Parameter(name = "customerInvoiceId", description = "会员增票Id",
            required = true)
    @RequestMapping(value = "/invoiceInfo/{customerInvoiceId}",method = RequestMethod.GET)
    public ResponseEntity<CustomerInvoiceByIdAndDelFlagResponse> findByCustomerInvoiceIdAndDelFlag(@PathVariable("customerInvoiceId") Long customerInvoiceId) {
        CustomerInvoiceByIdAndDelFlagRequest customerInvoiceByIdAndDelFlagRequest = new CustomerInvoiceByIdAndDelFlagRequest();
        customerInvoiceByIdAndDelFlagRequest.setCustomerInvoiceId(customerInvoiceId);
        BaseResponse<CustomerInvoiceByIdAndDelFlagResponse> customerInvoiceByIdAndDelFlagResponseBaseResponse = customerInvoiceQueryProvider.getByIdAndDelFlag(customerInvoiceByIdAndDelFlagRequest);
        CustomerInvoiceByIdAndDelFlagResponse customerInvoiceByIdAndDelFlagResponse = customerInvoiceByIdAndDelFlagResponseBaseResponse.getContext();
        return ResponseEntity.ok(customerInvoiceByIdAndDelFlagResponse);
    }

    /**
     * 新增会员增专票
     *
     * @param request
     * @return employee
     */
    @Operation(summary = "新增会员增专票")
    @RequestMapping(value = "/invoice", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> saveCustomerInvoice(@Valid @RequestBody CustomerInvoiceAddRequest saveRequest, HttpServletRequest request) {
        if(saveRequest.getCompanyName().length()>50){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(saveRequest.getInvoiceStyle().equals(InvoiceStyle.SPECIAL) && StringUtils.isBlank(saveRequest.getBusinessLicenseImg())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(saveRequest.getInvoiceStyle().equals(InvoiceStyle.SPECIAL) || saveRequest.getInvoiceStyle().equals(InvoiceStyle.COMPANY)){
            if(Objects.isNull(saveRequest.getTaxpayerNumber()) || (Objects.nonNull(saveRequest.getTaxpayerNumber()) &&
                    (saveRequest.getTaxpayerNumber().length() > 20 || saveRequest.getTaxpayerNumber().length() < 15))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        CustomerInvoiceByCustomerIdAndDelFlagRequest byCustomerIdAndDelFlagRequest = new CustomerInvoiceByCustomerIdAndDelFlagRequest();
        byCustomerIdAndDelFlagRequest.setCustomerId(saveRequest.getCustomerId());
        byCustomerIdAndDelFlagRequest.setInvoiceStyle(saveRequest.getInvoiceStyle());
        CustomerInvoiceListResponse customerInvoiceListResponse =  customerInvoiceQueryProvider.getByCustomerIdAndDelFlagAndStyle(byCustomerIdAndDelFlagRequest).getContext();
        if(CollectionUtils.isNotEmpty(customerInvoiceListResponse.getCustomerInvoiceVOList())){
            return ResponseEntity.ok(BaseResponse.error("每个客户只可保存一条增票资质"));
        }
        CustomerInvoiceByTaxpayerNumberRequest customerInvoiceByTaxpayerNumberRequest = new CustomerInvoiceByTaxpayerNumberRequest();
        customerInvoiceByTaxpayerNumberRequest.setTaxpayerNumber(saveRequest.getTaxpayerNumber());
        BaseResponse<CustomerInvoiceByTaxpayerNumberResponse> invoiceByTaxpayerNumberResponseBaseResponse = customerInvoiceQueryProvider.getByTaxpayerNumber(customerInvoiceByTaxpayerNumberRequest);
        CustomerInvoiceByTaxpayerNumberResponse customerInvoiceByTaxpayerNumberResponse = invoiceByTaxpayerNumberResponseBaseResponse.getContext();
        if(Objects.nonNull(customerInvoiceByTaxpayerNumberResponse)){
            return ResponseEntity.ok(BaseResponse.error("纳税人识别号不允许重复"));
        }
        String employeeId = ((Claims) request.getAttribute("claims")).get("employeeId").toString();
        saveRequest.setInvoiceStyle(InvoiceStyle.SPECIAL);
        saveRequest.setEmployeeId(employeeId);
        BaseResponse<CustomerInvoiceAddResponse> customerInvoiceAddBaseResponse= customerInvoiceProvider.add(saveRequest);
        CustomerInvoiceAddResponse customerInvoiceAddResponse = customerInvoiceAddBaseResponse.getContext();
        //操作日志记录
        if (nonNull(customerInvoiceAddResponse)) {
            CustomerDetailVO customerDetail = customerDetailQueryProvider.getCustomerDetailByCustomerId(
                    CustomerDetailByCustomerIdRequest.builder().customerId(customerInvoiceAddResponse.getCustomerId()).build()).getContext();
            if (nonNull(customerDetail)) {
                operateLogMQUtil.convertAndSend("财务", "新增资质",
                        "新增资质：" + customerDetail.getCustomerName());
            }
        }
        EsCustomerInvoiceAddRequest esCustomerInvoiceAddRequest = KsBeanUtil.convert(saveRequest, EsCustomerInvoiceAddRequest.class);
        esCustomerInvoiceAddRequest.setCustomerInvoiceId(customerInvoiceAddBaseResponse.getContext().getCustomerInvoiceId());
        esCustomerInvoiceProvider.add(esCustomerInvoiceAddRequest);
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 修改会员增专票
     *
     * @param saveRequest
     * @return employee
     */
    @Operation(summary = "修改会员增专票")
    @RequestMapping(value = "/invoice", method = RequestMethod.PUT)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> updateCustomerInvoice(@Valid @RequestBody CustomerInvoiceModifyRequest saveRequest, HttpServletRequest request) {
        final boolean[] flag = {false};
        if(saveRequest.getCompanyName().length()>50){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CustomerInvoiceByTaxpayerNumberRequest customerInvoiceByTaxpayerNumberRequest = new CustomerInvoiceByTaxpayerNumberRequest();
        customerInvoiceByTaxpayerNumberRequest.setTaxpayerNumber(saveRequest.getTaxpayerNumber());
        BaseResponse<CustomerInvoiceByTaxpayerNumberResponse> invoiceByTaxpayerNumberResponseBaseResponse = customerInvoiceQueryProvider.getByTaxpayerNumber(customerInvoiceByTaxpayerNumberRequest);
        CustomerInvoiceByTaxpayerNumberResponse customerInvoiceByTaxpayerNumberResponse = invoiceByTaxpayerNumberResponseBaseResponse.getContext();
        if (Objects.nonNull(customerInvoiceByTaxpayerNumberResponse)){
            if(!customerInvoiceByTaxpayerNumberResponse.getCustomerInvoiceId().equals(saveRequest.getCustomerInvoiceId())){
                flag[0] = true;
            }
        }
        if(flag[0]){
            return ResponseEntity.ok(BaseResponse.error("纳税人识别号不允许重复"));
        }
        String employeeId = ((Claims) request.getAttribute("claims")).get("employeeId").toString();
        saveRequest.setEmployeeId(employeeId);
        customerInvoiceProvider.modify(saveRequest);
        esCustomerInvoiceProvider.modify(KsBeanUtil.convert(saveRequest,EsCustomerInvoiceModifyRequest.class));
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 单条 / 批量审核 增专票信息
     *
     * @param invoiceBatchRequest invoiceBatchRequest
     * @return
     */
    @Operation(summary = "单条 / 批量审核 增专票信息")
    @RequestMapping(value = "/invoice/checklist", method = RequestMethod.PUT)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> checkCustomerInvoice(@RequestBody CustomerInvoiceAuditingRequest invoiceBatchRequest){
        customerInvoiceProvider.auditing(invoiceBatchRequest);
        if (CollectionUtils.size(invoiceBatchRequest.getCustomerInvoiceIds()) == 1) {
            CustomerDetailVO detail = this.queryCustomerDetail(invoiceBatchRequest.getCustomerInvoiceIds().get(0));
            if(nonNull(detail)){
                operateLogMQUtil.convertAndSend("财务", "审核资质",
                        "审核资质：" + detail.getCustomerName());

            }
        } else {
            operateLogMQUtil.convertAndSend("财务", "批量审核", "批量审核");
        }
        esCustomerInvoiceProvider.auditing(KsBeanUtil.convert(invoiceBatchRequest,EsCustomerInvoiceAuditingRequest.class));
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 驳回 增专票信息
     *
     * @param invoiceBatchRequest invoiceBatchRequest
     * @return
     */
    @Operation(summary = "驳回 增专票信息")
    @RequestMapping(value = "/invoice/reject", method = RequestMethod.PUT)
    @GlobalTransactional
    public BaseResponse rejectInvoice(@RequestBody CustomerInvoiceRejectRequest invoiceBatchRequest){
        if (Objects.isNull(invoiceBatchRequest.getCustomerInvoiceId()) || StringUtils.isBlank(invoiceBatchRequest.getRejectReason())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        customerInvoiceProvider.reject(invoiceBatchRequest);
        //操作日志记录
        CustomerDetailVO customerDetail = this.queryCustomerDetail(invoiceBatchRequest.getCustomerInvoiceId());
        if (nonNull(customerDetail)) {
                operateLogMQUtil.convertAndSend("财务", "驳回资质", "驳回资质：" + customerDetail.getCustomerName());
        }
        esCustomerInvoiceProvider.reject(KsBeanUtil.convert(invoiceBatchRequest, EsCustomerInvoiceRejectRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 根据customerInvoiceId 查询CustomerDetail
     *
     * @param customerInvoiceId
     * @return
     */
    private CustomerDetailVO queryCustomerDetail(Long customerInvoiceId) {
        CustomerInvoiceByIdAndDelFlagRequest customerInvoiceByIdAndDelFlagRequest = new CustomerInvoiceByIdAndDelFlagRequest();
        customerInvoiceByIdAndDelFlagRequest.setCustomerInvoiceId(customerInvoiceId);
        BaseResponse<CustomerInvoiceByIdAndDelFlagResponse> customerInvoiceByIdAndDelFlagResponseBaseResponse = customerInvoiceQueryProvider.getByIdAndDelFlag(customerInvoiceByIdAndDelFlagRequest);
        CustomerInvoiceByIdAndDelFlagResponse customerInvoiceByIdAndDelFlagResponse = customerInvoiceByIdAndDelFlagResponseBaseResponse.getContext();

        if (Objects.isNull(customerInvoiceByIdAndDelFlagResponse)) {
            return null;
        }
        //获取会员
        CustomerDetailVO customerDetail = customerDetailQueryProvider.getCustomerDetailByCustomerId(
                CustomerDetailByCustomerIdRequest.builder().customerId(customerInvoiceByIdAndDelFlagResponse.getCustomerId()).build()).getContext();

        if (nonNull(customerDetail))  {
            //如果存在
            return customerDetail;
        }
        return null;
    }

    /**
     * 作废 增专票信息
     *
     * @param request
     * @return
     */
    @Operation(summary = "作废 增专票信息")
    @RequestMapping(value = "/invalidInvoice", method = RequestMethod.PUT)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> invalidCustomerInvoice(@RequestBody CustomerInvoiceInvalidRequest request){
        customerInvoiceProvider.invalid(request);
        List<Long> customerInvoiceIds = request.getCustomerInvoiceIds();
        //操作日志记录
        if (CollectionUtils.isNotEmpty(customerInvoiceIds)) {
            for (Long customerInvoiceId : customerInvoiceIds) {
                CustomerDetailVO customerDetail = this.queryCustomerDetail(customerInvoiceId);
                if (nonNull(customerDetail)) {
                    operateLogMQUtil.convertAndSend("财务", "作废资质", "作废资质：" + customerDetail.getCustomerName());
                }
            }
        }
        esCustomerInvoiceProvider.invalid(KsBeanUtil.convert(request, EsCustomerInvoiceInvalidRequest.class));
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 删除 增专票信息
     *
     * @param request
     * @return
     */
    @Operation(summary = "删除 增专票信息")
    @RequestMapping(value = "/invoices", method = RequestMethod.DELETE)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> deleteCustomerInvoice(@RequestBody CustomerInvoiceDeleteRequest request){
        List<Long> customerInvoiceIds = request.getCustomerInvoiceIds();
        //查询客户详情
        List<CustomerDetailVO> detailList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(customerInvoiceIds)) {
            for (Long customerInvoiceId : customerInvoiceIds) {
                CustomerDetailVO customerDetail = this.queryCustomerDetail(customerInvoiceId);
                if (nonNull(customerDetail)) {
                    detailList.add(customerDetail);
                }
            }
        }

        customerInvoiceProvider.delete(request);
        //操作日志记录
        for (CustomerDetailVO customerDetail : detailList) {
            operateLogMQUtil.convertAndSend("财务", "删除资质", "删除资质：" + customerDetail.getCustomerName());
        }
        esCustomerInvoiceProvider.delete(KsBeanUtil.convert(request, EsCustomerInvoiceDeleteRequest.class));
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 保存订单开票配置
     *
     * @param status status
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "保存订单开票配置")
    @RequestMapping(value = "/invoiceConfig", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> saveInvoiceConfig(Integer status){
        CustomerInvoiceConfigAddRequest request = new CustomerInvoiceConfigAddRequest();
        request.setStatus(status);
        customerInvoiceProvider.addCustomerInvoiceConfig(request);
        operateLogMQUtil.convertAndSend("设置", "修改审核开关", "修改审核开关：增专资质审核设为" + (status == 1 ? "开" : "关"));
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 查询增专资质审核状态按钮
     *
     * @return ResponseEntity
     */
    @Operation(summary = "查询增专资质审核状态按钮")
    @RequestMapping(value = "/invoiceConfig", method = RequestMethod.GET)
    public ResponseEntity<BaseResponse<InvoiceConfigGetResponse>> queryInvoiceConfig() {
        BaseResponse<InvoiceConfigGetResponse> customerInvoiceConfigResponseBaseResponse = auditQueryProvider.getInvoiceConfig();
        return ResponseEntity.ok(customerInvoiceConfigResponseBaseResponse);
    }
}
