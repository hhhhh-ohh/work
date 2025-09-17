package com.wanmi.sbc.employee;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.wanmi.sbc.aop.DepartmentIsolation;
import com.wanmi.sbc.base.verifycode.VerifyCodeService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.customer.EmployeeResponse;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeActivateAccountRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeAddRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeBatchDeleteByIdsRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByMobileRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByNameRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListByAccountTypeRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListByCompanyIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListByIdsRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeMobileExistsRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeNumRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeePageRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeRequest;
import com.wanmi.sbc.customer.api.response.employee.EmployeeActivateAccountResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeAddResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeByIdResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeByMobileResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeByNameResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeListResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeMobileExistsResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeNumResponse;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.customer.bean.vo.EmployeeListByIdsVO;
import com.wanmi.sbc.customer.bean.vo.EmployeeListVO;
import com.wanmi.sbc.customer.request.EmployeeExcelImportRequest;
import com.wanmi.sbc.customer.service.EmployeeExcelService;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.provider.employee.EsEmployeeProvider;
import com.wanmi.sbc.elastic.api.provider.employee.EsEmployeeQueryProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeActivateAccountRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeBatchDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeePageRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeSaveRequest;
import com.wanmi.sbc.setting.api.provider.pickupemployeerela.PickupSettingRelaProvider;
import com.wanmi.sbc.setting.api.request.pickupemployeerela.PickupSettingRelaDelByEmployeesRequest;
import com.wanmi.sbc.setting.bean.enums.VerifyType;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import com.wanmi.sbc.util.sms.SmsSendUtil;

import io.jsonwebtoken.Claims;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: songhanlin
 * @Date: Created In 下午5:31 2017/11/2
 * @Description: 商家Controller
 */
@Tag(name = "EmployeeController", description = "商家服务 API")
@RestController("supplierEmployeeController")
@Validated
@RequestMapping("/customer")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private EmployeeProvider employeeProvider;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private SmsSendUtil smsSendUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EmployeeExcelService employeeExcelService;

    @Autowired
    private EsEmployeeQueryProvider esEmployeeQueryProvider;


    @Autowired
    private EsEmployeeProvider esEmployeeProvider;

    @Autowired
    private PickupSettingRelaProvider pickupSettingRelaProvider;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;
    /**
     * 查询员工是否存在
     *
     * @param phone phone
     * @return boolean
     */
    @Operation(summary = "查询员工是否存在")
    @Parameter(name = "phone", description = "手机号", required = true)
    @RequestMapping(value = "/{phone}/valid", method = RequestMethod.GET)
    public BaseResponse<Boolean> findEmployeeExist(@PathVariable String phone, HttpServletRequest request) {

        if (!ValidateUtil.isPhone(phone)) {
            logger.error("手机号码:{}格式错误", phone);
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        Claims empInfo = (Claims) request.getAttribute("claims");
        if (isNull(empInfo)) {
            AccountType accountType = Platform.SUPPLIER.equals(commonUtil.getOperator().getPlatform()) ?
                    AccountType.s2bSupplier : AccountType.s2bProvider;
            EmployeeByMobileResponse response = employeeQueryProvider.getByMobile(
                    EmployeeByMobileRequest.builder().mobile(phone).accountType(accountType).build()
            ).getContext();
            if (response == null || response.getEmployeeId() == null) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010135);
            }
            return BaseResponse.SUCCESSFUL();
        } else {
            String employeeId = empInfo.get("employeeId").toString();

            EmployeeByIdResponse response = employeeQueryProvider.getById(
                    EmployeeByIdRequest.builder().employeeId(employeeId).build()
            ).getContext();
            if (response == null || response.getEmployeeId() == null || !phone.equals(response.getEmployeeMobile())) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010078);
            }
            return BaseResponse.success(true);
        }
    }

    /**
     * 查询商家员工是否存在
     *
     * @param phone phone
     * @return boolean
     */
    @Operation(summary = "查询商家员工是否存在")
    @Parameter(name = "phone", description = "手机号", required = true)
    @RequestMapping(value = "/{phone}/supplier", method = RequestMethod.GET)
    public BaseResponse findEmployeeExist(@jakarta.validation.constraints.Pattern(regexp = "1\\d{10}") @PathVariable(
            "phone") String phone) {
        commonValidEmployee(phone, AccountType.s2bSupplier);
        return BaseResponse.SUCCESSFUL();
    }


    @Operation(summary = "查询供应商商员工是否存在")
    @Parameter(name = "phone", description = "手机号", required = true)
    @RequestMapping(value = "/{phone}/provider", method = RequestMethod.GET)
    public BaseResponse findProviderEmployeeExist(@jakarta.validation.constraints.Pattern(regexp = "1\\d{10}") @PathVariable(
            "phone") String phone) {
        commonValidEmployee(phone,AccountType.s2bProvider);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 统一校验
     * @param phone
     * @param type
     */
    public void commonValidEmployee(String phone,AccountType type){
        EmployeeByMobileResponse employee = employeeQueryProvider.getByMobile(
                EmployeeByMobileRequest.builder().mobile(phone).accountType(type).build()
        ).getContext();

        if (employee == null || employee.getEmployeeId() == null) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010093);
        }
        //被禁用
        if (AccountState.DISABLE.equals(employee.getAccountState())) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010087,
                    new Object[]{"，原因为：" + employee.getAccountDisableReason() + "，如有疑问请联系平台"});
        }
    }


    /**
     * 查询所有员工
     *
     * @return <List<EmployeeResponse>>
     */
    @Operation(summary = "查询所有员工")
    @GetMapping(value = "/employee/allEmployees")
    public ResponseEntity<List<EmployeeResponse>> findAllEmployees() {
        return ResponseEntity.ok(
                employeeQueryProvider.listByCompanyId(
                        EmployeeListByCompanyIdRequest.builder().companyInfoId(commonUtil.getCompanyInfoId()).build()
                ).getContext().getEmployeeList()
                        .stream()
                        .filter(Objects::nonNull)
                        .map(employee -> {
                            EmployeeResponse employeeResponse = new EmployeeResponse();
                            employeeResponse.setEmployeeId(employee.getEmployeeId());
                            employeeResponse.setEmployeeName(StringUtils.isEmpty(employee.getEmployeeName()) ? employee
                                    .getEmployeeMobile() : employee.getEmployeeName());
                            employeeResponse.setEmployeeMobile(employee.getEmployeeMobile());
                            return employeeResponse;
                        }).collect(Collectors.toList()));
    }

    /**
     * 查询平台所有员工
     *
     * @return <List<EmployeeResponse>>
     */
    @Operation(summary = "查询平台所有员工")
    @GetMapping(value = "/employee/allBossEmployees")
    @ReturnSensitiveWords(functionName = "f_supplier_find_all_employees_sign_word")
    public ResponseEntity<List<EmployeeResponse>> findAllBossEmployees() {
        return ResponseEntity.ok(
                employeeQueryProvider.listByAccountType(
                        EmployeeListByAccountTypeRequest.builder().accountType(AccountType.s2bBoss).build()
                ).getContext().getEmployeeList().stream().filter(Objects::nonNull).map(employee -> {
                    EmployeeResponse employeeResponse = new EmployeeResponse();
                    employeeResponse.setEmployeeId(employee.getEmployeeId());
                    employeeResponse.setEmployeeName(employee.getEmployeeName());
                    return employeeResponse;
                }).collect(Collectors.toList()));
    }

    /**
     * 分页查询员工
     *
     * @param employeeRequest employeeRequest
     * @return 会员信息
     */
    @DepartmentIsolation(isIncluedeParentDepartment = false)
    @Operation(summary = "分页查询员工")
    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> findEmployees(@RequestBody EmployeePageRequest employeeRequest) {
        employeeRequest.setAccountType(getAccountType());
        employeeRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        employeeRequest.putSort("isMasterAccount", SortType.DESC.toValue());
        employeeRequest.putSort("manageDepartmentIds", SortType.DESC.toValue());
        employeeRequest.putSort("createTime", SortType.DESC.toValue());
        return ResponseEntity.ok(BaseResponse.success(employeeQueryProvider.page(employeeRequest).getContext()
                .getEmployeePageVOPage()));
    }


    /**
     * ES分页查询员工
     *
     * @param employeeRequest employeeRequest
     * @return 会员信息
     */
    @DepartmentIsolation(isIncluedeParentDepartment = false)
    @Operation(summary = "分页查询员工")
    @RequestMapping(value = "/es/employees", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> findEsEmployees(@RequestBody EsEmployeePageRequest employeeRequest) {
        employeeRequest.setAccountType(getAccountType());
        employeeRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        employeeRequest.putSort("isMasterAccount", SortType.DESC.toValue());
        employeeRequest.putSort("manageDepartmentIds", SortType.DESC.toValue());
        employeeRequest.putSort("createTime", SortType.DESC.toValue());
        return ResponseEntity.ok(BaseResponse.success(esEmployeeQueryProvider.page(employeeRequest).getContext()
                .getEmployeePageVOPage()));
    }

    /**
     * 新增员工
     *
     * @param employeeSaveRequest employeeSaveRequest
     * @return employee
     */
    @Operation(summary = "新增员工")
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> saveEmployee(@Valid @RequestBody EmployeeAddRequest employeeSaveRequest) {
        employeeSaveRequest.setAccountType(getAccountType());
        employeeSaveRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());

        EmployeeAddResponse employeeAddResponse = employeeProvider.add(employeeSaveRequest).getContext();
        if (StringUtils.isNotEmpty(employeeSaveRequest.getEmployeeName()) && CollectionUtils.isEmpty(employeeSaveRequest.getRoleIdList())
                && nonNull(employeeSaveRequest.getRoleName())) {
            operateLogMQUtil.convertAndSend("设置", "新增角色",
                    "新增角色： " + employeeSaveRequest.getRoleName());
        }
        operateLogMQUtil.convertAndSend("设置", "新增员工",
                "新增员工：员工账户名 " + employeeSaveRequest.getEmployeeName());
        EsEmployeeSaveRequest esEmployeeSaveRequest = KsBeanUtil.convert(employeeAddResponse, EsEmployeeSaveRequest.class);
        esEmployeeProvider.save(esEmployeeSaveRequest);
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }


    /**
     * 批量删除
     *
     * @param employeeRequest employeeRequest
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "批量删除")
    @RequestMapping(value = "/employee", method = RequestMethod.DELETE)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> deleteEmployees(@RequestBody EmployeeRequest employeeRequest) {

        List<String> employeeIds = Objects.nonNull(employeeRequest.getEmployeeIds()) ? employeeRequest.getEmployeeIds() : new ArrayList<>();

        if (StringUtils.isNotBlank(employeeRequest.getEmployeeId())) {
            employeeIds.add(employeeRequest.getEmployeeId());
        }

        if (CollectionUtils.isEmpty(employeeIds)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        List<EmployeeListByIdsVO> employeeList = employeeQueryProvider
                .listByIds(EmployeeListByIdsRequest.builder().employeeIds(employeeIds).build())
                .getContext()
                .getEmployeeList();

        if (CollectionUtils.isEmpty(employeeList)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010086);
        }

        List<Long> companyIds = employeeList
                .stream()
                .map(EmployeeListByIdsVO::getCompanyInfoId)
                .distinct()
                .collect(Collectors.toList());
        if (!Objects.equals(Platform.PLATFORM,commonUtil.getOperator().getPlatform())
            && CollectionUtils.isNotEmpty(companyIds)){
            if (companyIds.size() != Constants.ONE || !Objects.equals(commonUtil.getCompanyInfoId(),companyIds.get(0))){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000014);
            }
        }

        if (1 == CollectionUtils.size(employeeIds)) {
            EmployeeByIdResponse employeeByIdResponse = employeeQueryProvider.getById(
                    EmployeeByIdRequest.builder().employeeId(employeeIds.get(0)).build()).getContext();
            operateLogMQUtil.convertAndSend("设置", "删除员工",
                    "删除员工：员工账户名" + employeeByIdResponse.getAccountName());
        }
        if (CollectionUtils.size(employeeIds) > 1) {
            operateLogMQUtil.convertAndSend("设置", "批量删除", "批量删除");
        }

        employeeProvider.batchDeleteByIds(
                EmployeeBatchDeleteByIdsRequest.builder()
                        .employeeIds(employeeIds)
                        .accountType(getAccountType())
                        .build()
        );
        esEmployeeProvider.batchDeleteByIds(EsEmployeeBatchDeleteByIdsRequest.builder()
                .employeeIds(employeeRequest.getEmployeeIds())
                .accountType(getAccountType())
                .build());
        // 删除自提员工
        pickupSettingRelaProvider.deleteByEmployeeIds(
                PickupSettingRelaDelByEmployeesRequest.builder()
                .employeeIds(employeeIds)
                .build());
        return ResponseEntity.ok(BaseResponse.SUCCESSFUL());
    }

    /**
     * 发送验证码
     *
     * @param phone phone
     * @return BaseResponse
     */
    @Operation(summary = "发送验证码")
    @RequestMapping(value = "/send/sms", method = RequestMethod.POST)
    public BaseResponse sendSms(@jakarta.validation.constraints.Pattern(regexp = "1\\d{10}") @RequestParam("phone")
                                        String phone, @RequestParam("uuid") String uuid) {

        EmployeeByIdResponse employee = employeeQueryProvider.getById(
                EmployeeByIdRequest.builder().employeeId(commonUtil.getOperatorId()).build()
        ).getContext();
        if (employee != null && !phone.equals(employee.getEmployeeMobile())) {
            AccountType accountType = Platform.SUPPLIER.equals(commonUtil.getOperator().getPlatform()) ?
                    AccountType.s2bSupplier : AccountType.s2bProvider;
            EmployeeMobileExistsResponse existsResponse = employeeQueryProvider.mobileIsExists(
                    EmployeeMobileExistsRequest.builder()
                            .mobile(phone)
                            .accountType(accountType).build()
            ).getContext();
            if (existsResponse.isExists()) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010076);
            }
        }

        String regexp = "1\\d{10}";
        Pattern p = Pattern.compile(regexp);
        if (p.matcher(phone).find() && verifyCodeService.validateCaptchaCertificate(uuid)) {
            if (verifyCodeService.validSmsCertificate(phone, VerifyType.EMPLOYEE_CHANGE_PHONE)) {
                String verifyCode = verifyCodeService.generateSmsVerifyCode(phone, VerifyType.EMPLOYEE_CHANGE_PHONE, 5,
                        TimeUnit.MINUTES);
                smsSendUtil.send(SmsTemplate.VERIFICATION_CODE, new String[]{phone}, verifyCode);
            } else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000012);
            }
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000013);
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 查询业务员绑定的手机号
     *
     * @return
     */
    @Operation(summary = "查询业务员绑定的手机号")
    @RequestMapping(value = "/employeesMobile", method = RequestMethod.GET)
    public BaseResponse<EmployeeResponse> findEmployeesMobile() {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        EmployeeByIdResponse employee = employeeQueryProvider.getById(
                EmployeeByIdRequest.builder().employeeId(commonUtil.getOperatorId()).build()
        ).getContext();
        if (employee != null) {
            BeanUtils.copyProperties(employee, employeeResponse);
        }
        if (StringUtils.isNotBlank(employeeResponse.getEmployeeMobile())) {
            return BaseResponse.success(employeeResponse);
        }
        return BaseResponse.FAILED();
    }

    /**
     * 根据业务员名称模糊查询
     *
     * @param  employeeByNameRequest
     * @return
     */
    @DepartmentIsolation(isIncluedeParentDepartment = false)
    @Operation(summary = "根据业务员名称模糊查询")
    @RequestMapping(value = "/employee/name", method = RequestMethod.POST)
    public BaseResponse<EmployeeByNameResponse> findBossEmployeeByName(@RequestBody EmployeeByNameRequest employeeByNameRequest) {

        EmployeePageRequest request = new EmployeePageRequest();
        request.setIsEmployeeSearch(Boolean.TRUE);
        request.setEmployeeName(employeeByNameRequest.getName());
        request.setCompanyInfoId(commonUtil.getCompanyInfoId());
        request.setAccountType(getAccountType());
        request.setIsEmployee(0);
        request.setPageNum(0);
        request.setPageSize(5);
        request.putSort("createTime", SortType.DESC.toValue());
        EmployeeByNameResponse response = employeeQueryProvider.pageForEmployee(request).getContext();
        return BaseResponse.success(response);
    }

    /**
     * 会员账号激活
     *
     * @param idsRequest
     * @return 会员信息
     */
    @Operation(summary = "会员账号激活")
    @RequestMapping(value = "/activateAccount", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse findEmployees(@RequestBody @Valid EmployeeListByIdsRequest idsRequest) {
        EmployeeActivateAccountRequest request = new EmployeeActivateAccountRequest();
        boolean isO2o = commonUtil.getOperator().getPlatform() == Platform.STOREFRONT;
        request.setEmployeeIds(idsRequest.getEmployeeIds());
        request.setCustomerType(isO2o ? CustomerType.STOREFRONT : CustomerType.SUPPLIER);
        request.setOperator(commonUtil.getOperatorId());
        request.setS2bSupplier(!isO2o);
        request.setCompanyInfoId(commonUtil.getCompanyInfoId());
        request.setStoreId(commonUtil.getStoreId());
        EmployeeActivateAccountResponse response = employeeProvider.activateAccount(request).getContext();
        operateLogMQUtil.convertAndSend("会员", "账户激活", "账户激活数：" + response.getNum());
        esEmployeeProvider.activateAccount(KsBeanUtil.convert(request, EsEmployeeActivateAccountRequest.class));
        //初始化es会员信息
        esCustomerDetailProvider.init(EsCustomerDetailInitRequest.builder().idList(response.getCustomerIds()).build());
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 确认导入员工
     *
     * @param ext 文件格式 {@link String}
     * @return
     */
    @Operation(summary = "确认导入员工")
    @Parameter(name = "ext", description = "文件名后缀", required = true)
    @RequestMapping(value = "/employee/import/{ext}", method = RequestMethod.GET)
    public BaseResponse<Boolean> implGoods(@PathVariable String ext) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        EmployeeExcelImportRequest request = new EmployeeExcelImportRequest();
        request.setExt(ext);
        request.setCompanyInfoId(commonUtil.getCompanyInfoId());
        request.setUserId(commonUtil.getOperatorId());
        request.setAccountType(getAccountType());
        employeeExcelService.importEmployee(request);
        //操作日志记录
        operateLogMQUtil.convertAndSend("员工", "批量导入", "批量导入");
        return BaseResponse.success(Boolean.TRUE);
    }

    /**
     * 统计部门人数
     *
     * @param
     * @return EmployeeNumResponse
     */
    @Operation(summary = "统计部门人数")
    @RequestMapping(value = "/employee/countNum", method = RequestMethod.GET)
    public BaseResponse<EmployeeNumResponse> countEmployeeNum() {
        EmployeeNumRequest request = EmployeeNumRequest.builder()
                .companyInfoId(commonUtil.getCompanyInfoId())
                .accountType(getAccountType()).build();
        return employeeQueryProvider.countEmployeeNum(request);
    }

    /**
     *获取客户类型
     */
    private AccountType getAccountType(){
        Platform platform = commonUtil.getOperator().getPlatform();
        AccountType accountType = Platform.SUPPLIER == platform
                ? AccountType.s2bSupplier : Platform.STOREFRONT == platform
                ? AccountType.O2O : AccountType.s2bProvider;
        return accountType;
    }

    /**
     * 查询员工是否存在
     *
     * @param phone phone
     * @return boolean
     */
    @Operation(summary = "查询员工手机号是否存在")
    @Parameter(name = "phone", description = "手机号", required = true)
    @RequestMapping(value = "/exist/{phone}", method = RequestMethod.GET)
    public BaseResponse checkEmployeeExist(@jakarta.validation.constraints.Pattern(regexp = "1\\d{10}") @PathVariable(
            "phone") String phone) {
        EmployeeListRequest employeeListRequest = new EmployeeListRequest();
        employeeListRequest.setUserPhone(phone);
        EmployeeListResponse response =
                employeeQueryProvider.list(employeeListRequest).getContext();

        if (Objects.isNull(response) || CollectionUtils.isEmpty(response.getEmployeeList())) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010093);
        }
        EmployeeListVO employee = response.getEmployeeList().get(0);
        //被禁用
        if (AccountState.DISABLE.equals(employee.getAccountState())) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010087,
                    new Object[]{"，原因为：" + employee.getAccountDisableReason() + "，如有疑问请联系平台"});
        }
        return BaseResponse.SUCCESSFUL();
    }
}
