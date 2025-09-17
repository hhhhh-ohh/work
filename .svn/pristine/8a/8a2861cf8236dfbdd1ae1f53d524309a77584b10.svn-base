package com.wanmi.sbc.customer;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.growthvalue.CustomerGrowthValueQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreCustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.CustomerEditRequest;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoQueryRequest;
import com.wanmi.sbc.customer.api.request.customer.*;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValuePageRequest;
import com.wanmi.sbc.customer.api.response.company.CompanyInfoGetResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerDetailPageForSupplierResponse;
import com.wanmi.sbc.customer.api.response.customer.NoDeleteCustomerGetByAccountResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailForPageVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailToEsVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.CustomerGrowthValueVO;
import com.wanmi.sbc.customer.validator.CustomerValidator;
import com.wanmi.sbc.elastic.api.provider.coupon.EsCouponInfoQueryProvider;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailQueryProvider;
import com.wanmi.sbc.elastic.api.provider.customer.EsDistributionCustomerProvider;
import com.wanmi.sbc.elastic.api.request.coupon.EsCouponInfoPageRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailAddRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailModifyRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailPageRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsDistributionCustomerModifyRequest;
import com.wanmi.sbc.elastic.api.response.customer.EsCustomerDetailPageResponse;
import com.wanmi.sbc.elastic.bean.dto.customer.EsCustomerDetailDTO;
import com.wanmi.sbc.elastic.bean.dto.customer.EsStoreCustomerRelaDTO;
import com.wanmi.sbc.elastic.bean.vo.coupon.EsCouponInfoVO;
import com.wanmi.sbc.marketing.api.provider.coupon.CouponCodeQueryProvider;
import com.wanmi.sbc.marketing.api.request.coupon.CouponCodePageRequest;
import com.wanmi.sbc.marketing.api.response.coupon.CouponCodePageResponse;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;
import com.wanmi.sbc.marketing.bean.enums.CouponStatus;
import com.wanmi.sbc.marketing.bean.enums.ScopeType;
import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;
import com.wanmi.sbc.order.api.provider.returnorder.ReturnOrderProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeProvider;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.returnorder.ReturnOrderModifyEmployeeIdRequest;
import com.wanmi.sbc.order.api.request.trade.TradeQueryFirstCompleteRequest;
import com.wanmi.sbc.order.api.request.trade.TradeUpdateEmployeeIdRequest;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.jsonwebtoken.Claims;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.*;

/**
 * 会员
 * Created by hht on 2017/4/19.
 */
@Tag(name =  "平台会员API", description =  "BossCustomerController")
@RestController
@Validated
@RequestMapping(value = "/customer")
public class BossCustomerController {

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerProvider customerProvider;

    @Autowired
    private CustomerValidator customerValidator;

    @Autowired
    private StoreCustomerQueryProvider storeCustomerQueryProvider;

    @Autowired
    private TradeProvider tradeProvider;

    @Autowired
    private ReturnOrderProvider returnOrderProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private CustomerGrowthValueQueryProvider customerGrowthValueQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    @Autowired
    private EsCustomerDetailQueryProvider esCustomerDetailQueryProvider;

    @Autowired
    private EsDistributionCustomerProvider esDistributionCustomerProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private CouponCodeQueryProvider couponCodeQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private EsCouponInfoQueryProvider esCouponInfoQueryProvider;

    @InitBinder
    public void initBinder(DataBinder binder) {
        if (binder.getTarget() instanceof CustomerEditRequest) {
            binder.setValidator(customerValidator);
        }
    }


    /**
     * S2b-Boss端修改会员
     * 修改会员表，修改会员详细信息
     *
     * @return
     */
    @Operation(summary = "平台端修改会员")
    @RequestMapping(method = RequestMethod.PUT)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> updateCustomerAll(@RequestBody CustomerModifyRequest customerModifyRequest) {
        String employeeId = commonUtil.getOperatorId();
        customerModifyRequest.setOperator(employeeId);
        //获取原业务员
        CustomerDetailVO detail = customerDetailQueryProvider.getCustomerDetailByCustomerId(
                CustomerDetailByCustomerIdRequest.builder().customerId(customerModifyRequest.getCustomerId()).build()
        ).getContext();
        String oldEmployeeId = Objects.nonNull(detail) ? detail.getEmployeeId() : null;
        CustomerDetailToEsVO customerDetailToEsVO = customerProvider.modifyCustomer(customerModifyRequest).getContext().getCustomerDetailToEsVO();
        //如更换业务员，将历史订单和历史退单的负责业务员更新为新业务员
        if(StringUtils.isNotBlank(customerModifyRequest.getEmployeeId()) && (!customerModifyRequest.getEmployeeId().equals(oldEmployeeId))){
            tradeProvider.updateEmployeeId(TradeUpdateEmployeeIdRequest.builder()
                    .employeeId(customerModifyRequest.getEmployeeId())
                    .customerId(customerModifyRequest.getCustomerId())
                    .build());
            returnOrderProvider.modifyEmployeeId(ReturnOrderModifyEmployeeIdRequest.builder()
                    .employeeId(customerModifyRequest.getEmployeeId())
                    .customerId(customerModifyRequest.getCustomerId()).build());
        }

        //操作日志记录
        operateLogMQUtil.convertAndSend("客户", "编辑客户",
                "编辑客户：" + customerDetailToEsVO.getCustomerAccount());

        EsCustomerDetailDTO esCustomerDetailDTO =  KsBeanUtil.convert(customerDetailToEsVO, EsCustomerDetailDTO.class);
        esCustomerDetailProvider.modify(new EsCustomerDetailModifyRequest(esCustomerDetailDTO));
        esDistributionCustomerProvider.modify(EsDistributionCustomerModifyRequest.builder()
                .customerId(esCustomerDetailDTO.getCustomerId())
                .customerName(esCustomerDetailDTO.getCustomerName())
                .build());
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }


    /**
     * 分页查询会员
     *
     * @param customerDetailQueryRequest
     * @return 会员信息
     */
    @Operation(summary = "分页查询会员")
    @EmployeeCheck
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_customer_page_sign_word")
    public ResponseEntity<BaseResponse<EsCustomerDetailPageResponse>> page(@RequestBody EsCustomerDetailPageRequest customerDetailQueryRequest) {
        return ResponseEntity.ok(esCustomerDetailQueryProvider.page(customerDetailQueryRequest));
    }

    /**
     * 分页查询会员
     *
     * @param customerDetailQueryRequest
     * @return 会员信息
     */
    @Operation(summary = "分页查询会员")
    @EmployeeCheck
    @RequestMapping(value = "/page/supplierLevel", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> page(@RequestBody CustomerDetailPageForSupplierRequest customerDetailQueryRequest) {
        customerDetailQueryRequest.putSort("createTime", SortType.DESC.toValue());
        customerDetailQueryRequest.setShowAreaFlag(Boolean.TRUE);
        BaseResponse<CustomerDetailPageForSupplierResponse> customerDetailPageForSupplierResponseBaseResponse =
                customerQueryProvider.pageForS2bSupplier(customerDetailQueryRequest);
        return ResponseEntity.ok(customerDetailPageForSupplierResponseBaseResponse);
    }

    /**
     * Boss端保存会员
     *
     * @return
     */
    @Operation(summary = "平台端保存会员")
    @EmployeeCheck
    @RequestMapping(method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> addCustomerAll(@Valid @RequestBody CustomerAddRequest customerAddRequest,
                                                       HttpServletRequest request) {
        //账号已存在
        NoDeleteCustomerGetByAccountResponse customer = customerQueryProvider.getNoDeleteCustomerByAccount(new NoDeleteCustomerGetByAccountRequest
                (customerAddRequest.getCustomerAccount())).getContext();
        if (customer != null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010003);
        }
        String employeeId = ((Claims) request.getAttribute("claims")).get("employeeId").toString();
        customerAddRequest.setOperator(employeeId);
        customerAddRequest.setCustomerType(CustomerType.PLATFORM);
        CustomerDetailToEsVO customerDetailToEsVO = customerProvider.saveCustomer(customerAddRequest).getContext().getCustomerDetailToEsVO();
        //操作日志记录
        operateLogMQUtil.convertAndSend("客户", "新增客户",
                "新增客户：" + customerAddRequest.getCustomerAccount());

        EsCustomerDetailDTO esCustomerDetailDTO =  KsBeanUtil.convert(customerDetailToEsVO, EsCustomerDetailDTO.class);
        if (Objects.nonNull(customerDetailToEsVO.getStoreCustomerRela())) {
            List<EsStoreCustomerRelaDTO> esStoreCustomerRelaList = new ArrayList<>();
            esStoreCustomerRelaList.add(KsBeanUtil.convert(customerDetailToEsVO.getStoreCustomerRela(), EsStoreCustomerRelaDTO.class));
            esCustomerDetailDTO.setEsStoreCustomerRelaList(esStoreCustomerRelaList);
            esCustomerDetailDTO.setLogOutStatus((long)LogOutStatus.NORMAL.toValue());
        }
        esCustomerDetailProvider.add(new EsCustomerDetailAddRequest(esCustomerDetailDTO));

        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }


    /**
     * 获取客户归属商家的商家名称
     *
     * @param customerId
     * @return
     */
    @Operation(summary = "获取客户归属商家的商家名称")
    @Parameter(name = "customerId", description = "客户id", required = true)
    @RequestMapping(value = "/supplier/name/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<BaseResponse<String>> getBelongSupplier(@PathVariable("customerId")String customerId){
        CompanyInfoQueryRequest request = new CompanyInfoQueryRequest();
        request.setCustomerId(customerId);
        CompanyInfoGetResponse companyInfo = storeCustomerQueryProvider.getCompanyInfoBelongByCustomerId(request).getContext();
        return ResponseEntity.ok(BaseResponse.success(companyInfo.getSupplierName()));
    }

    @Operation(summary = "分页查询会员成长值")
    @RequestMapping(value = "/queryToGrowthValue", method = RequestMethod.POST)
    public ResponseEntity<MicroServicePage<CustomerGrowthValueVO>> queryGrowthValue(@RequestBody CustomerGrowthValuePageRequest customerGrowthValuePageRequest) {
        return ResponseEntity.ok(customerGrowthValueQueryProvider.page(customerGrowthValuePageRequest).getContext()
                .getCustomerGrowthValueVOPage());
    }

    /**
     * 分页查询已注销会员
     * @return
     */
    @Operation(summary = "分页查询已注销会员")
    @EmployeeCheck
    @RequestMapping(value = "/loggedOut/page", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> loggedOutPage(@RequestBody CustomerDetailPageRequest request) {
        request.setLogOutStatus(LogOutStatus.LOGGED_OUT);
        return ResponseEntity.ok(customerQueryProvider.page(request));
    }


    /**
     * 是否是新人状态初始化
     *
     * @param
     * @return
     */
    @Operation(summary = "是否是新人状态初始化")
    @RequestMapping(value = "/isNewInit", method = RequestMethod.GET)
    public BaseResponse isNewInit() {
        CustomerDetailPageRequest customerDetailPageRequest = new CustomerDetailPageRequest();
        customerDetailPageRequest.setPageNum(Constants.ZERO);
        customerDetailPageRequest.setPageSize(Constants.NUM_1000);
        while (true) {
            List<CustomerDetailForPageVO> detailResponseList = customerQueryProvider.page(customerDetailPageRequest).getContext().getDetailResponseList();
            detailResponseList.forEach(customerDetailForPageVO -> {
                String customerId = customerDetailForPageVO.getCustomerId();
                String tradeId = tradeQueryProvider.queryFirstPayTrade(new TradeQueryFirstCompleteRequest(customerId))
                        .getContext().getTradeId();
                //不存在已经支付订单
                if (StringUtils.isEmpty(tradeId)) {
                    //更新新人状态
                    customerProvider.modifyNewCustomerState(CustomerAccountModifyStateRequest.builder()
                            .customerId(customerId)
                            .isNew(Constants.ZERO)
                            .build());
                } else {
                    //更新新人状态
                    customerProvider.modifyNewCustomerState(CustomerAccountModifyStateRequest.builder()
                            .customerId(customerId)
                            .isNew(Constants.ONE)
                            .build());
                }
            });
            // 如果查询出来少于1000，则已经最后一页，退出循环
            if (detailResponseList.size() < Constants.NUM_1000) {
                break;
            } else {
                customerDetailPageRequest.setPageNum(customerDetailPageRequest.getPageNum() + 1);
            }
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 查询客户优惠券
     * @param request
     * @return
     */
    @Operation(summary = "客户优惠券")
    @RequestMapping(value = "/my-coupon", method = RequestMethod.POST)
    public BaseResponse<CouponCodePageResponse> listMyCouponList(@RequestBody CouponCodePageRequest request){
        if(StringUtils.isBlank(request.getCustomerId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if(request.getCouponStatus() != null) {
            request.setCouponStatusList(Collections.singletonList(CouponStatus.fromValue(request.getCouponStatus())));
        }else{
            request.setCouponStatusList(Arrays.asList(CouponStatus.NOT_START, CouponStatus.STARTED));
        }

        CouponCodePageResponse couponCodePageResponse = couponCodeQueryProvider.page(request).getContext();
        List<String> couponIdList = new ArrayList<>();
        couponCodePageResponse.getCouponCodeVos().getContent().forEach(couponCodeVO -> {
            couponIdList.add(couponCodeVO.getCouponId());
        });
        List<EsCouponInfoVO> esCouponInfoVOList = esCouponInfoQueryProvider.page(EsCouponInfoPageRequest.builder().couponIds(couponIdList).build()).getContext().getCouponInfos().getContent();
        Map<String,EsCouponInfoVO> esCouponInfoVOMap = new HashMap<>();
        esCouponInfoVOList.forEach(esCouponInfoVO -> {
            esCouponInfoVOMap.put(esCouponInfoVO.getCouponId(),esCouponInfoVO);
        });
        couponCodePageResponse.getCouponCodeVos().getContent().forEach(couponCodeVO -> {
            EsCouponInfoVO esCouponInfoVO = esCouponInfoVOMap.get(couponCodeVO.getCouponId());
            if(Objects.nonNull(esCouponInfoVO)){
                couponCodeVO.setScopeNames(esCouponInfoVO.getScopeNames());
            }
        });
        return BaseResponse.success(couponCodePageResponse);
    }

    /**
     * 导出客户优惠券详情
     */
    @Operation(summary = "导出客户优惠券详情")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/coupon/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse exportCoupon(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes()));
        CouponCodePageRequest couponCodePageRequest = JSON.parseObject(decrypted, CouponCodePageRequest.class);
        if(Objects.nonNull(JSON.parseObject(decrypted).get("couponMarketingType"))){
            String couponMarketingType = JSON.parseObject(decrypted).get("couponMarketingType").toString();
            if(StringUtils.isNotBlank(couponMarketingType)){
                couponCodePageRequest.setCouponMarketingType(CouponMarketingType.fromValue(Integer.valueOf(couponMarketingType)));
            }
        }
        if(Objects.nonNull(JSON.parseObject(decrypted).get("platformFlag"))){
            String platformFlag = JSON.parseObject(decrypted).get("platformFlag").toString();
            if(StringUtils.isNotBlank(platformFlag)){
                couponCodePageRequest.setPlatformFlag(DefaultFlag.fromValue(Integer.valueOf(platformFlag)));
            }
        }
        if(Objects.nonNull(JSON.parseObject(decrypted).get("scopeType"))){
            String scopeType = JSON.parseObject(decrypted).get("scopeType").toString();
            if(StringUtils.isNotBlank(scopeType)){
                couponCodePageRequest.setScopeType(ScopeType.fromValue(Integer.valueOf(scopeType)));
            }
        }

        if(couponCodePageRequest.getCouponStatus() != null) {
            couponCodePageRequest.setCouponStatusList(Collections.singletonList(CouponStatus.fromValue(couponCodePageRequest.getCouponStatus())));
        }else{
            couponCodePageRequest.setCouponStatusList(Arrays.asList(CouponStatus.NOT_START, CouponStatus.STARTED));
        }

        ExportDataRequest request = new ExportDataRequest();
        request.setTypeCd(ReportType.COUPON_DETAIL);
        request.setParam(JSONObject.toJSONString(couponCodePageRequest));
        exportCenter.sendExport(request);
        return BaseResponse.SUCCESSFUL();
    }

}
