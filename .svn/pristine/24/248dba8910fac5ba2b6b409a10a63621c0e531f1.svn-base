package com.wanmi.sbc.customer;

import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.BaseResUtils;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.IteratorUtils;
import com.wanmi.sbc.common.util.Nutils;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.customer.api.provider.account.CustomerAccountQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailProvider;
import com.wanmi.sbc.customer.api.provider.enterpriseinfo.EnterpriseInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.storelevel.StoreLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.CustomerEditRequest;
import com.wanmi.sbc.customer.api.request.account.CustomerAccountListRequest;
import com.wanmi.sbc.customer.api.request.company.CompanyInfoQueryRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerCheckStateModifyRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerDelFlagGetRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerDetailListByConditionRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerDetailListForOrderRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerListByConditionRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomersDeleteRequest;
import com.wanmi.sbc.customer.api.request.detail.CustomerStateBatchModifyRequest;
import com.wanmi.sbc.customer.api.request.enterpriseinfo.EnterpriseInfoByCustomerIdRequest;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelByIdsRequest;
import com.wanmi.sbc.customer.api.request.level.CustomerLevelWithDefaultByIdRequest;
import com.wanmi.sbc.customer.api.request.store.StoreCustomerQueryByEmployeeRequest;
import com.wanmi.sbc.customer.api.request.storelevel.StoreLevelByIdRequest;
import com.wanmi.sbc.customer.api.request.storelevel.StoreLevelListRequest;
import com.wanmi.sbc.customer.api.response.account.CustomerAccountListResponse;
import com.wanmi.sbc.customer.api.response.company.CompanyInfoGetResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetForSupplierResponse;
import com.wanmi.sbc.customer.api.response.enterpriseinfo.EnterpriseInfoByCustomerIdResponse;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.vo.CustomerAccountVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.CustomerLevelVO;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerRelaVO;
import com.wanmi.sbc.customer.bean.vo.StoreCustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreLevelVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.customer.validator.CustomerValidator;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerCheckStateModifyRequest;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerStateBatchModifyRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.jsonwebtoken.Claims;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

/**
 * 会员
 * Created by CHENLI on 2017/4/19.
 */
@Tag(name = "CustomerController", description = "会员 Api")
@RestController
@Validated
public class CustomerController {

    @Autowired
    private CustomerProvider customerProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerDetailProvider customerDetailProvider;

    @Autowired
    private CustomerAccountQueryProvider customerAccountQueryProvider;

    @Autowired
    private CustomerLevelQueryProvider customerLevelQueryProvider;

    @Autowired
    private StoreLevelQueryProvider storeLevelQueryProvider;

    @Autowired
    private CustomerValidator customerValidator;

    @Autowired
    private StoreCustomerQueryProvider storeCustomerQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EnterpriseInfoQueryProvider enterpriseInfoQueryProvider;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    @Autowired
    private CustomerCacheService customerCacheService;
    @Resource
    private RedisUtil redisUtil;

    @InitBinder
    public void initBinder(DataBinder binder) {
        if (binder.getTarget() instanceof CustomerEditRequest) {
            binder.setValidator(customerValidator);
        }
    }


    /**
     * 多条件查询会员详细信息
     *
     * @param request
     * @return 会员详细信息
     */
    @Operation(summary = "多条件查询会员详细信息")
    @RequestMapping(value = "/customer/customerDetails", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_supplier_customer_details_sign_word")
    public List<CustomerDetailVO> findCustomerDetailList(@RequestBody CustomerDetailListByConditionRequest request) {
        return customerQueryProvider.listCustomerDetailByCondition(request).getContext().getDetailResponseList();
    }

    /**
     * 多条件查询会员信息
     *
     * @param request
     * @return 会员信息
     */
    @Operation(summary = "多条件查询会员信息")
    @RequestMapping(value = "/customerList", method = RequestMethod.POST)
    public List<CustomerVO> findCustomerList(@RequestBody CustomerListByConditionRequest request) {
        return customerQueryProvider.listCustomerByCondition(request).getContext().getCustomerVOList();
    }

    /**
     * 查询单条会员信息
     *
     * @param customerId
     * @return
     */
    @Operation(summary = "查询单条会员信息")
    @Parameter(name = "customerId", description = "会员ID", required = true)
    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET)
    @ReturnSensitiveWords(functionName = "f_boss_find_customer_by_id_sign_word")
    public ResponseEntity<CustomerGetForSupplierResponse> findById(@PathVariable String customerId) {
        CustomerGetByIdResponse customer =
                customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(customerId)).getContext();
        CustomerGetForSupplierResponse customerResponse = new CustomerGetForSupplierResponse();
        BeanUtils.copyProperties(customer, customerResponse);
        CustomerType customerType = customerResponse.getCustomerType();

        if (customerType == CustomerType.SUPPLIER || customerType == CustomerType.STOREFRONT) {
            CompanyInfoQueryRequest request = new CompanyInfoQueryRequest();
            request.setCustomerId(customerId);
            request.setCustomerType(customerType);
            CompanyInfoGetResponse companyInfo =
                    storeCustomerQueryProvider.getCompanyInfoBelongByCustomerId(request).getContext();
            customerResponse.setSupplierName(companyInfo.getSupplierName());
        }
        CustomerLevelVO customerLevel = customerLevelQueryProvider.getCustomerLevelWithDefaultById(
                CustomerLevelWithDefaultByIdRequest.builder().customerLevelId(customer.getCustomerLevelId()).build())
                .getContext();
        customerResponse.setCustomerLevelName(customerLevel.getCustomerLevelName());
        //查询企业信息
        if(commonUtil.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING)){
            BaseResponse<EnterpriseInfoByCustomerIdResponse> enterpriseInfo = enterpriseInfoQueryProvider.getByCustomerId(EnterpriseInfoByCustomerIdRequest.builder()
                    .customerId(customerId)
                    .build());
            if(Objects.nonNull(enterpriseInfo.getContext())){
                customerResponse.setEnterpriseInfo(enterpriseInfo.getContext().getEnterpriseInfoVO());
            }
        }
        return ResponseEntity.ok(customerResponse);
    }

    /**
     * 查询客户是否被删除了
     *
     * @param customerId
     * @return
     */
    @Operation(summary = "查询客户是否被删除了，true: 删除, false: 未删除")
    @Parameter(name = "customerId", description = "会员ID", required = true)
    @RequestMapping(value = "/customer/customerDelFlag/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> findCustomerDelFlag(@PathVariable String customerId) {
        return ResponseEntity.ok(customerQueryProvider.getCustomerDelFlag(new CustomerDelFlagGetRequest(customerId))
                .getContext().getDelFlag());
    }

    /**
     * 审核客户状态
     *
     * @param request
     * @return
     */
    @Operation(summary = "审核客户状态")
    @RequestMapping(value = "/customer/customerState", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> updateCheckState(@RequestBody CustomerCheckStateModifyRequest request) {
        if (null == request.getCheckState() || StringUtils.isEmpty(request.getCustomerId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        customerProvider.modifyCustomerCheckState(request);

        //获取会员
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (request.getCustomerId())).getContext();



        if (nonNull(customer)) {
            //用户列表不能操作企业会员，请至企业会员列表,校验权限
            checkCustomerPermission(customer);

            if (request.getCheckState() == 1) {
                operateLogMQUtil.convertAndSend("客户", "审核客户", "审核客户：" + customer.getCustomerAccount());
            } else {
                operateLogMQUtil.convertAndSend("客户", "驳回客户", "驳回客户：" + customer.getCustomerAccount());
            }
        }
        EsCustomerCheckStateModifyRequest modifyRequest =  EsCustomerCheckStateModifyRequest.builder()
                .checkState(request.getCheckState()).customerId(request.getCustomerId()).rejectReason(request.getRejectReason()).build();
        esCustomerDetailProvider.modifyCustomerCheckState(modifyRequest);
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 批量删除会员
     *
     * @param customerQueryRequest
     * @return
     */
    @Operation(summary = "批量删除会员")
    @RequestMapping(value = "/customer", method = RequestMethod.DELETE)
    public ResponseEntity<BaseResponse> delete(@RequestBody @Valid CustomersDeleteRequest customerQueryRequest) {
        if (CollectionUtils.isEmpty(customerQueryRequest.getCustomerIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        customerProvider.deleteCustomers(customerQueryRequest);
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 批量启用/禁用会员详情
     *
     * @param queryRequest
     * @return
     */
    @Operation(summary = "批量启用/禁用会员详情")
    @RequestMapping(value = "/customer/detailState", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse updateCustomerState(@RequestBody CustomerStateBatchModifyRequest queryRequest) {
        if (null == queryRequest.getCustomerStatus() || CollectionUtils.isEmpty(queryRequest.getCustomerIds())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //获取会员
        CustomerGetByIdResponse customerById = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (queryRequest.getCustomerIds().get(0))).getContext();
        //用户列表不能操作企业会员，请至企业会员列表
        if (nonNull(customerById) && Objects.nonNull(queryRequest.getCustomerListFlag()) && queryRequest.getCustomerListFlag()){
            //如果是客户列表
            checkCustomerPermission(customerById);
        }

        Claims claims = (Claims) HttpUtil.getRequest().getAttribute("claims");
        //操作日志记录
        if (CustomerStatus.DISABLE.equals(queryRequest.getCustomerStatus())) {
            if (nonNull(customerById)) {
                String opContext = "禁用客户：";
                if (nonNull(claims)) {
                    String platform = Objects.toString(claims.get("platform"), "");
                    if (Platform.SUPPLIER.toValue().equals(platform)) {
                        opContext += "禁用客户：客户账号";
                    }
                }

                operateLogMQUtil.convertAndSend("客户", "禁用客户",
                        opContext + customerById.getCustomerAccount());
            }
        } else {
            operateLogMQUtil.convertAndSend("客户", "批量启用客户", "批量启用客户");
        }

        customerDetailProvider.modifyCustomerStateByCustomerId(
                CustomerStateBatchModifyRequest.builder()
                        .customerIds(queryRequest.getCustomerIds())
                        .customerStatus(queryRequest.getCustomerStatus())
                        .forbidReason(queryRequest.getForbidReason()).build());

        esCustomerDetailProvider.modifyCustomerStateByCustomerId(EsCustomerStateBatchModifyRequest.builder()
                .customerIds(queryRequest.getCustomerIds())
                .customerStatus(queryRequest.getCustomerStatus())
                .forbidReason(queryRequest.getForbidReason()).build());

        // 根据启用/禁用更改用户Redis注销状态
        // 以此限制已登录用户访问
        WmCollectionUtils.notEmpty2Loop(queryRequest.getCustomerIds(), id -> {
            if (CustomerStatus.DISABLE == queryRequest.getCustomerStatus()) {
                redisUtil.setString(CacheKeyConstant.LOG_OUT_STATUS.concat(id),
                        id.concat(":").concat(Nutils.toStr(LogOutStatus.LOGGING_OFF.toValue())));
            } else {
                CustomerGetByIdResponse customer = BaseResUtils.getContextFromRes(customerQueryProvider
                        .getCustomerById(new CustomerGetByIdRequest(id)));
                if(Objects.nonNull(customer) && LogOutStatus.NORMAL == customer.getLogOutStatus()) {
                    redisUtil.delete(CacheKeyConstant.LOG_OUT_STATUS.concat(id));
                }
            }
        });
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description 检验用户操作权限
     * @Author qiyuanzhao
     * @Date 2022/8/16 14:38
     **/
    private void checkCustomerPermission(CustomerGetByIdResponse customerById) {
        //如果是企业会员
        EnterpriseCheckState enterpriseCheckState = customerById.getEnterpriseCheckState();
        if (enterpriseCheckState == EnterpriseCheckState.WAIT_CHECK ||
                enterpriseCheckState == EnterpriseCheckState.CHECKED ||
                enterpriseCheckState == EnterpriseCheckState.NOT_PASS){
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030190);
        }
    }

    /**
     * 根据customerId查询会员账号
     *
     * @param customerId customerId
     * @return 会员账号
     */
    @Operation(summary = "根据customerId查询会员账号")
    @Parameter(name = "customerId", description = "会员ID", required = true)
    @RequestMapping(value = "/customerAccounts/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerAccountVO>> findCustomerAccountById(@PathVariable("customerId") String customerId) {
        CustomerAccountListRequest customerAccountListRequest = new CustomerAccountListRequest();
        customerAccountListRequest.setCustomerId(customerId);
        BaseResponse<CustomerAccountListResponse> customerAccountListResponseBaseResponse =
                customerAccountQueryProvider.listByCustomerId(customerAccountListRequest);
        CustomerAccountListResponse customerAccountListResponse = customerAccountListResponseBaseResponse.getContext();
        if (Objects.nonNull(customerAccountListResponse)) {
            return ResponseEntity.ok(customerAccountListResponse.getCustomerAccountVOList());
        }
        return ResponseEntity.ok(Collections.emptyList());
    }


    /**
     * 查询所有的有效的会员的id和accoutName，给前端autocomplete
     *
     * @return
     */
    @Operation(summary = "查询所有的有效的会员的id和accoutName，给前端autocomplete,customerId: 会员Id, customerAccount: 账号, customerName: 会员名称, customerLevelId: 等级Id")
    @EmployeeCheck
    @RequestMapping(value = "/customer/customerAccount/list", method = RequestMethod.POST)
    public ResponseEntity<List<Map<String, Object>>> findAllCustomers(@RequestBody StoreCustomerQueryByEmployeeRequest queryRequest) {
        if (StringUtils.isBlank(queryRequest.getCustomerAccount())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        List<Map<String, Object>> mapList = getCustomerAccount(queryRequest.getCustomerAccount(), commonUtil.getOperatorId());
        List<String> customerId = mapList.stream().map(v -> String.valueOf(v.get("customerId"))).collect(toList());
        Set<String> logOutCustomerIds = customerCacheService.getLogOutStatus(customerId).keySet();
        List<Map<String, Object>> result = mapList.stream().filter(v -> !logOutCustomerIds.contains(String.valueOf(v.get("customerId")))).collect(toList());
        return ResponseEntity.ok(result);
    }

    /**
     * 查询所有的有效的会员的id和accoutName，给前端autocomplete
     *
     * @return
     */
    @Operation(summary = "查询所有的有效的会员的id和accoutName，给前端autocomplete,customerId: 会员Id, customerAccount: 账号, customerName: 会员名称, customerLevelId: 等级Id")
    @Parameter(name = "customerAccount", description = "账号", required = true)
    @RequestMapping(value = "/customer/list/{customerAccount}", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> findAllCustomers(@PathVariable String customerAccount) {
        return ResponseEntity.ok(getCustomerAccount(customerAccount, null));
    }

    /**
     * 代客下单-搜索客户
     *
     * @param customerAccount 账号
     * @param employeeId      业务员
     * @return
     */
    private List<Map<String, Object>> getCustomerAccount(String customerAccount, String employeeId) {
        List<Map<String, Object>> collect = new ArrayList<>();
        //查询所有的合法的会员账户
        //非自营店铺查询店铺会员 自营店铺查询平台所有会员
        List<StoreCustomerVO> customerByCondition;
        //已审核
        StoreCustomerQueryByEmployeeRequest request = new StoreCustomerQueryByEmployeeRequest();
        request.setCustomerAccount(customerAccount);
        request.setEmployeeId(employeeId);
        request.setPageSize(5);
        if (commonUtil.getCompanyType().equals(BoolFlag.YES)) {
            request.setStoreId(commonUtil.getStoreId());

            customerByCondition = storeCustomerQueryProvider.listCustomer(request).getContext().getStoreCustomerVOList();
        } else {
            customerByCondition = storeCustomerQueryProvider.listBossCustomer(request).getContext().getStoreCustomerVOList();
        }

        if (CollectionUtils.isEmpty(customerByCondition)) {
            return collect;
        }

        collect = customerByCondition.stream()
                .map(v -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("customerId", v.getCustomerId());
                    map.put("customerAccount", v.getCustomerAccount());
                    map.put("customerName", v.getCustomerName());
                    map.put("customerLevelId", v.getCustomerLevelId());
                    return map;
                }).collect(toList());

        List<Long> levelIds = collect.stream().filter(v -> v.get("customerLevelId") != null).map(v -> (Long) v.get(
                "customerLevelId")).collect(toList());
        if (levelIds != null && !levelIds.isEmpty()) {
            List<CustomerLevelVO> customerLevels = customerLevelQueryProvider.listCustomerLevelByIds(
                    CustomerLevelByIdsRequest.builder().customerLevelIds(levelIds).build()).getContext().getCustomerLevelVOList();
            IteratorUtils.zip(collect, customerLevels
                    , (collect1, levels1) -> collect1.get("customerLevelId") != null && collect1.get("customerLevelId"
                    ).equals(levels1.getCustomerLevelId())
                    , (collect2, levels2) -> {
                        if (levels2.getCustomerLevelName() != null) {
                            collect2.put("customerLevelName", levels2.getCustomerLevelName());
                        }
                    }
            );

            if (commonUtil.getCompanyType().equals(BoolFlag.YES)) {
                List<StoreLevelVO> storeLevels = storeLevelQueryProvider.list(StoreLevelListRequest.builder()
                        .storeLevelIdList(levelIds)
                        .build()).getContext().getStoreLevelVOList();

                IteratorUtils.zip(collect, storeLevels,
                        (collect1, levels1) -> Objects.nonNull(collect1.get("customerLevelId")) &&
                                collect1.get("customerLevelId").equals(levels1.getStoreLevelId()),
                        (collect2, levels2) -> {
                            collect2.put("customerLevelName", levels2.getLevelName());
                        }
                );
            }
        }
        return collect;
    }

    /**
     * 根据客户ID查询相关信息，编辑代客下单用
     * add Transactional的意思是为了hibernate懒加载，后期重构要放到service
     *
     * @param customerId
     * @return
     */
    @Operation(summary = "根据客户ID查询相关信息，编辑代客下单用,customerId: 用户Id, customerAccount: 账号, customerName: 会员名称, customerLevelName: 等级名称")
    @Parameter(name = "customerId", description = "会员ID", required = true)
    @RequestMapping(value = "/customer/single/{customerId}", method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<Map<String, String>> findCustomerById(@PathVariable("customerId") String customerId) {
        Map<String, String> resultMap = new HashMap<>();
        CustomerGetByIdResponse customer =
                customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(customerId)).getContext();
        String companyInfoId = commonUtil.getOperator().getAdminId();
        if (null != customer && nonNull(companyInfoId)) {
            resultMap.put("customerId", customer.getCustomerId());
            resultMap.put("customerAccount", customer.getCustomerAccount());
            resultMap.put("customerName", customer.getCustomerDetail().getCustomerName());
            List<Long> levelIds = customer.getStoreCustomerRelaListByAll()
                    .parallelStream()
                    .filter(storeCustomerRela -> companyInfoId.equals(storeCustomerRela.getCompanyInfoId().toString()))
                    .map(StoreCustomerRelaVO::getStoreLevelId)
                    .collect(toList());
            // 非自营店铺
            if (CollectionUtils.isNotEmpty(levelIds)) {
                StoreLevelVO storeLevelVO = storeLevelQueryProvider.getById(StoreLevelByIdRequest.builder().storeLevelId(levelIds.get(0)).build()).getContext().getStoreLevelVO();
                resultMap.put("customerLevelName", storeLevelVO.getLevelName());
            } else {// 自营店铺
                CustomerLevelVO customerLevel = customerLevelQueryProvider.getCustomerLevelWithDefaultById(
                        CustomerLevelWithDefaultByIdRequest.builder().customerLevelId(customer.getCustomerLevelId()).build()).getContext();
                resultMap.put("customerLevelName", customerLevel.getCustomerLevelName());
            }
        }
        return ResponseEntity.ok(resultMap);
    }


    /**
     * 查询所有的有效的会员的id和accoutName，给前端autocomplete
     *
     * @return
     */
    @Operation(summary = "查询所有的有效的会员的id和accoutName，给前端autocomplete,customerId: 用户Id, customerAccount: 账号, customerName: 会员名称")
    @Parameter(name = "customerId", description = "会员ID", required = true)
    @RequestMapping(value = "/customer/all/{customerAccount}", method = RequestMethod.GET)
    public ResponseEntity<List<Map<String, Object>>> autoCustomerInfo(@PathVariable String customerAccount) {
        List<Map<String, Object>> collect = new ArrayList<>();
        //查询所有的合法的会员账户
        CustomerDetailListForOrderRequest cqr = new CustomerDetailListForOrderRequest();
        //已审核
        cqr.setCheckState(CheckState.CHECKED.toValue());
        cqr.setCustomerStatus(CustomerStatus.ENABLE);
        cqr.setCustomerAccount(customerAccount);
        //未注销
        cqr.setLogOutStatus(LogOutStatus.NORMAL);
//        List<CustomerDetail> customerByCondition = customerService.findDetailForOrder(cqr);
        List<CustomerDetailVO> customerByCondition = customerQueryProvider.listCustomerDetailForOrder(cqr).getContext()
                .getDetailResponseList();

        if (CollectionUtils.isEmpty(customerByCondition)) {
            return ResponseEntity.ok(collect);
        }

        collect = customerByCondition.stream()
                .map(v -> {
                    CustomerGetByIdRequest request = new CustomerGetByIdRequest();
                    request.setCustomerId(v.getCustomerId());
                    String account = customerQueryProvider.getCustomerById(request).getContext().getCustomerAccount();
                    Map<String, Object> map = new HashMap<>();
                    map.put("customerId", v.getCustomerId());
                    map.put("customerAccount", account);
                    map.put("customerName", v.getCustomerName());
                    return map;
                }).collect(toList());

        return ResponseEntity.ok(collect);
    }


}
