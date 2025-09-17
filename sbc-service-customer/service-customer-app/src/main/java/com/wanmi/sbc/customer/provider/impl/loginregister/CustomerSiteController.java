package com.wanmi.sbc.customer.provider.impl.loginregister;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.wanmi.sbc.account.bean.enums.AccountErrorCodeEnum;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.OsUtil;
import com.wanmi.sbc.customer.api.provider.loginregister.CustomerSiteProvider;
import com.wanmi.sbc.customer.api.request.loginregister.*;
import com.wanmi.sbc.customer.api.response.loginregister.*;
import com.wanmi.sbc.customer.bean.dto.CustomerDTO;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetail;
import com.wanmi.sbc.customer.employee.model.root.Employee;
import com.wanmi.sbc.customer.employee.repository.EmployeeRepository;
import com.wanmi.sbc.customer.level.service.CustomerLevelService;
import com.wanmi.sbc.customer.model.root.Customer;
import com.wanmi.sbc.customer.quicklogin.model.root.ThirdLoginRelation;
import com.wanmi.sbc.customer.service.CustomerSiteService;
import com.wanmi.sbc.setting.api.provider.AuditQueryProvider;
import com.wanmi.sbc.setting.api.provider.PointsBasicRuleQueryProvider;
import com.wanmi.sbc.setting.api.provider.SystemPointsConfigQueryProvider;
import com.wanmi.sbc.setting.api.response.CustomerAuditResponse;
import com.wanmi.sbc.setting.api.response.CustomerInfoPerfectResponse;
import com.wanmi.sbc.setting.bean.vo.ConfigVO;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: wanggang
 * @CreateDate: 2018/9/17 14:40
 * @Version: 1.0
 */
@Slf4j
@Validated
@RestController
public class CustomerSiteController implements CustomerSiteProvider {

    @Autowired
    private CustomerSiteService customerSiteService;

    @Autowired
    private AuditQueryProvider auditQueryProvider;

    @Autowired
    private CustomerLevelService customerLevelService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SystemPointsConfigQueryProvider systemPointsConfigQueryProvider;

    @Autowired
    private PointsBasicRuleQueryProvider pointsBasicRuleQueryProvider;

    @Autowired
    private OsUtil osUtil;

    /**
     * 注册时每个客户默认负责业务员是系统默认system
     */
    private static final String DEFAULT_EMPLOYEE = "system";


    /**
     * 会员注册
     *
     * @param customerRegisterRequest
     * @return
     */

    @Override
    public BaseResponse<CustomerRegisterResponse> register(@RequestBody @Valid CustomerRegisterRequest customerRegisterRequest) {
        CustomerInfoPerfectResponse perfectResponse = auditQueryProvider.isPerfectCustomerInfo().getContext();
        CustomerAuditResponse auditResponse = auditQueryProvider.isCustomerAudit().getContext();
        CustomerDTO customerDTO = customerRegisterRequest.getCustomerDTO();
        Customer customer = KsBeanUtil.convert(customerDTO, Customer.class);
        boolean checkStatus = CheckState.CHECKED == customer.getCheckState() ||
                (!perfectResponse.isPerfect()
                        && !auditResponse.isAudit());
        Customer customerRegister = customerSiteService.register(customer,customerRegisterRequest.getEmployeeId(),checkStatus);
        if (Objects.isNull(customerRegister)){
            return BaseResponse.SUCCESSFUL();
        }
        CustomerRegisterResponse customerRegisterResponse =  KsBeanUtil.convert(customerRegister,CustomerRegisterResponse.class);
        return BaseResponse.success(customerRegisterResponse);
    }

    /**
     * 批量注册会员
     * @param registerRequest {@link CustomerBatchRegisterRequest}
     * @return
     */
    @Override
    @Transactional
    public BaseResponse<CustomerBatchRegisterResponse> batchRegister(@RequestBody @Valid CustomerBatchRegisterRequest registerRequest) {
        //存储详情信息
        AccountType accountType = AccountType.b2bBoss;
        if (osUtil.isS2b()) {
            accountType = AccountType.s2bBoss;
        }
        Optional<Employee> employeeOptional = employeeRepository
                .findByAccountNameAndDelFlagAndAccountType(DEFAULT_EMPLOYEE, DeleteFlag.NO, accountType);
        CustomerInfoPerfectResponse perfectResponse = auditQueryProvider.isPerfectCustomerInfo().getContext();
        CustomerAuditResponse auditResponse = auditQueryProvider.isCustomerAudit().getContext();
        Long customerLevelId = customerLevelService.getDefaultLevel().getCustomerLevelId();
        boolean isPointsOpen = systemPointsConfigQueryProvider.isPointsOpen().getContext().isOpen();
        List<ConfigVO> configs = pointsBasicRuleQueryProvider.listPointsBasicRule().getContext().getConfigVOList();
        List<List<CustomerRegisterRequest>> requestList = registerRequest.getCustomerRegisterRequestList();
        ExecutorService executorService = this.newThreadPoolExecutor(requestList.size());
        List<CompletableFuture<List<Customer>>> futureList = requestList.stream()
                .map(customerRegisterRequestList ->
                        CompletableFuture.supplyAsync(() -> {
            Map<String, Employee> employeeMap = this.employeeMap(customerRegisterRequestList);
            List<Long> companyInfoIdList = employeeMap.values().stream()
                    .map(Employee::getCompanyInfoId)
                    .collect(Collectors.toList());

            List<Employee> mainEmployeeList = employeeRepository.findMainEmployeeList(companyInfoIdList, DeleteFlag.NO);
            Map<Long, Employee> longEmployeeMap = mainEmployeeList.stream()
                    .collect(Collectors.toMap(Employee::getCompanyInfoId, Function.identity()));

            List<Customer> customerList = customerRegisterRequestList.stream().map(customerVO -> {
                CustomerDTO customerDTO = customerVO.getCustomerDTO();
                Customer customer = KsBeanUtil.convert(customerDTO, Customer.class);
                boolean checkStatus = CheckState.CHECKED == customer.getCheckState() ||
                        (!perfectResponse.isPerfect()
                                && !auditResponse.isAudit());
                return customerSiteService.register(customer,
                        employeeMap.get(customerVO.getEmployeeId()),
                        checkStatus,
                        customerLevelId,
                        employeeOptional,
                        longEmployeeMap,isPointsOpen,configs,customer.getPointsAvailable());
            }).collect(Collectors.toList());
            return customerList;
        },executorService)).collect(Collectors.toList());

        List<List<CustomerVO>> finalList = futureList.stream()
                .map(CompletableFuture::join)
                .map(list -> KsBeanUtil.convert(list, CustomerVO.class))
                .collect(Collectors.toList());
        CustomerBatchRegisterResponse response = CustomerBatchRegisterResponse.builder()
                .customerVOList(finalList)
                .build();
        executorService.shutdown();
        return BaseResponse.success(response);
    }

    /**
     * 创建线程池
     * @return
     */
    private ExecutorService newThreadPoolExecutor(int corePoolSize){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("会员导入-%d").build();
        int maximumPoolSize = corePoolSize * 2;
        int capacity = 10001;
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                NumberUtils.LONG_ZERO, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(capacity), namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 获取员工信息
     * @param customerRegisterRequestList
     * @return
     */
    private Map<String, Employee> employeeMap(List<CustomerRegisterRequest> customerRegisterRequestList){
        List<String> employeeIdList = customerRegisterRequestList.stream()
                .map(CustomerRegisterRequest::getEmployeeId)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(employeeIdList)){
            return Collections.emptyMap();
        }
        List<Employee> byEmployeeIds = employeeRepository.findByEmployeeIds(employeeIdList);
        if(CollectionUtils.isEmpty(byEmployeeIds)){
            return Collections.emptyMap();
        }
        return byEmployeeIds.stream()
                .collect(Collectors.toMap(Employee::getEmployeeId, Function.identity()));
    }

    /**
     * 企业会员注册
     *
     * @param customerRegisterRequest
     * @return
     */

    @Override
    public BaseResponse<CustomerRegisterResponse> registerEnterprise(@RequestBody @Valid CustomerRegisterRequest customerRegisterRequest) {
        CustomerDTO customerDTO = customerRegisterRequest.getCustomerDTO();
        Customer customer = KsBeanUtil.convert(customerDTO, Customer.class);
        Customer customerRegister = customerSiteService.registerEnterprise(customer,customerRegisterRequest);
        if (Objects.isNull(customerRegister)){
            return BaseResponse.SUCCESSFUL();
        }
        CustomerRegisterResponse customerRegisterResponse =  KsBeanUtil.convert(customerRegister,CustomerRegisterResponse.class);
        return BaseResponse.success(customerRegisterResponse);
    }

    /**
     * 企业会员审核被驳回后重新注册
     *
     * @param request
     * @return
     */

    @Override
    public BaseResponse<CustomerRegisterResponse> registerEnterpriseAgain(@RequestBody @Valid CustomerRegisterRequest request) {
        Customer customer = customerSiteService.registerEnterpriseAgain(request);
        CustomerRegisterResponse customerRegisterResponse =  KsBeanUtil.convert(customer,CustomerRegisterResponse.class);
        return BaseResponse.success(customerRegisterResponse);
    }

    /**
     * 完善会员注册信息
     *
     * @param customerConsummateRegisterRequest
     * @return
     */

    @Override
    public BaseResponse<CustomerConsummateRegisterResponse> registerConsummate(@RequestBody @Valid CustomerConsummateRegisterRequest customerConsummateRegisterRequest) {
        CustomerDetail customerDetail = new CustomerDetail();
        KsBeanUtil.copyPropertiesThird(customerConsummateRegisterRequest,customerDetail);
        Customer perfectCustomer = customerSiteService.perfectCustomer(customerDetail);
        return BaseResponse.success(KsBeanUtil.convert(perfectCustomer,CustomerConsummateRegisterResponse.class));
    }

    /**
     * 修改密码
     *
     * @param customerModifyRequest
     * @return
     */

    @Override
    public BaseResponse modifyCustomerPwd(@RequestBody @Valid CustomerModifyRequest customerModifyRequest) {
        Customer customer = new Customer();
        KsBeanUtil.copyPropertiesThird(customerModifyRequest,customer);
        customer.setStoreCustomerRelaListByAll(null);
        customerSiteService.editPassword(customer);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 发送手机验证码
     *
     * @param customerSendMobileCodeRequest
     * @return
     */

    @Override
    public BaseResponse<CustomerSendMobileCodeResponse> sendMobileCode(@RequestBody @Valid CustomerSendMobileCodeRequest customerSendMobileCodeRequest) {
        Integer result = customerSiteService.doMobileSms(customerSendMobileCodeRequest.getRedisKey(),customerSendMobileCodeRequest.getMobile(),customerSendMobileCodeRequest.getSmsTemplate());
        return BaseResponse.success(new CustomerSendMobileCodeResponse(result));
    }

    /**
     * 是否可以发送验证码
     *
     * @param customerValidateSendMobileCodeRequest
     * @return
     */

    @Override
    public BaseResponse<CustomerValidateSendMobileCodeResponse> validateSendMobileCode(@RequestBody @Valid CustomerValidateSendMobileCodeRequest customerValidateSendMobileCodeRequest) {
        Boolean result = customerSiteService.isSendSms(customerValidateSendMobileCodeRequest.getMobile());
        return BaseResponse.success(new CustomerValidateSendMobileCodeResponse(result));
    }

    /**
     * 绑定第三方账号信息
     *
     * @param customerBindThirdAccountRequest
     * @return
     */

    @Override
    public BaseResponse bindThirdAccount(@RequestBody @Valid CustomerBindThirdAccountRequest customerBindThirdAccountRequest) {
        ThirdLoginRelation thirdLoginRelation = new ThirdLoginRelation();
        KsBeanUtil.copyPropertiesThird(customerBindThirdAccountRequest,thirdLoginRelation);
        customerSiteService.bindThird(thirdLoginRelation);
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 修改会员支付密码
     * @param customerModifyRequest
     * @return
     */

    @Override
    public BaseResponse modifyCustomerPayPwd(@RequestBody @Valid CustomerModifyRequest customerModifyRequest) {
        customerSiteService.editPayPassword(customerModifyRequest.getCustomerId(), customerModifyRequest.getCustomerPayPassword());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 校验会员输入支付密码是否正确
     * @param customerCheckPayPasswordRequest
     * @return
     */
    @Override
    public BaseResponse checkCustomerPayPwd(@RequestBody @Valid CustomerCheckPayPasswordRequest customerCheckPayPasswordRequest){
        try{
            customerSiteService.checkCustomerPayPwd(customerCheckPayPasswordRequest);
        }catch (SbcRuntimeException e){
            if (e.getErrorCode() != null && !e.getErrorCode().equals(AccountErrorCodeEnum.K020023.getCode())) {
                //输入密码错误处理逻辑
                customerSiteService.checkCustomerPayPwdErrorEvent(customerCheckPayPasswordRequest);
            }
            throw e;
//            throw new SbcRuntimeException(e.getErrorCode(), e.getParams());
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse updateInfo(@RequestBody @Valid CustomerBindThirdAccountRequest customerBindThirdAccountRequest){
        ThirdLoginRelation thirdLoginRelation = new ThirdLoginRelation();
        KsBeanUtil.copyPropertiesThird(customerBindThirdAccountRequest,thirdLoginRelation);
        customerSiteService.updateInfo(thirdLoginRelation);
        return BaseResponse.SUCCESSFUL();
    }
}
