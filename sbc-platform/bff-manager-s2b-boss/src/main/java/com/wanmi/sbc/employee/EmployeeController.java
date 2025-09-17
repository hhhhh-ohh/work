package com.wanmi.sbc.employee;

import static java.util.Objects.nonNull;

import com.wanmi.sbc.aop.DepartmentIsolation;
import com.wanmi.sbc.base.verifycode.VerifyCodeService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.EmployeeResponse;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.*;
import com.wanmi.sbc.customer.api.response.employee.*;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.enums.CustomerType;
import com.wanmi.sbc.customer.bean.enums.SmsTemplate;
import com.wanmi.sbc.customer.bean.vo.EmployeeDisableOrEnableByCompanyIdVO;
import com.wanmi.sbc.customer.bean.vo.EmployeePageVO;
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
import com.wanmi.sbc.setting.bean.enums.VerifyType;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import com.wanmi.sbc.util.sms.SmsSendUtil;

import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: songhanlin
 * @Date: Created In 下午5:26 2017/11/3
 * @Description: 商家账号/员工 Controller
 */
@Tag(name =  "平台员工API", description =  "EmployeeController")
@RestController("bossEmployeeController")
@Validated
@RequestMapping("/customer")
public class EmployeeController {

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private EmployeeProvider employeeProvider;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMqUtil;

    @Autowired
    private SmsSendUtil smsSendUtil;

    @Autowired
    private EmployeeExcelService employeeExcelService;
    @Autowired
    private EsEmployeeQueryProvider esEmployeeQueryProvider;
    @Autowired
    private EsEmployeeProvider esEmployeeProvider;
    @Resource
    private EmployeeService employeeService;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    /**
     * 分页查询员工
     *
     * @param pageRequest
     * @return 会员信息
     */
    @DepartmentIsolation(isIncluedeParentDepartment = false)
    @Operation(summary = "分页查询员工")
    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<EmployeePageVO>> findEmployees(@RequestBody EmployeePageRequest pageRequest) {
        pageRequest.setAccountType(AccountType.s2bBoss);
        pageRequest.putSort("isMasterAccount", SortType.DESC.toValue());
        //pageRequest.putSort("isLeader", SortType.DESC.toValue());
        pageRequest.putSort("createTime", SortType.DESC.toValue());
        return BaseResponse.success(employeeQueryProvider.page(pageRequest).getContext()
                .getEmployeePageVOPage());
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
        employeeRequest.setAccountType(AccountType.s2bBoss);
        employeeRequest.putSort("isMasterAccount", SortType.DESC.toValue());
        employeeRequest.putSort("isLeader", SortType.DESC.toValue());
        employeeRequest.putSort("createTime", SortType.DESC.toValue());
        return ResponseEntity.ok(BaseResponse.success(esEmployeeQueryProvider.page(employeeRequest).getContext()
                .getEmployeePageVOPage()));
    }

    /**
     * 查询所有员工
     *
     * @return <List<EmployeeResponse>>
     */
    @Operation(summary = "查询所有员工")
    @RequestMapping(value = "/employee/allEmployees", method = RequestMethod.GET)
    @ReturnSensitiveWords(functionName = "f_boss_find_all_employees_sign_word")
    public ResponseEntity<List<EmployeeResponse>> findAllEmployees() {
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
     * 新增员工
     *
     * @param employeeSaveRequest employeeSaveRequest
     * @return employee
     */
    @Operation(summary = "新增员工")
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> saveEmployee(@Valid @RequestBody EmployeeAddRequest employeeSaveRequest) {
        employeeSaveRequest.setAccountType(AccountType.s2bBoss);

        if (StringUtils.isNotEmpty(employeeSaveRequest.getEmployeeName()) && CollectionUtils.isEmpty(employeeSaveRequest.getRoleIdList())
                && nonNull(employeeSaveRequest.getRoleName())) {
            operateLogMqUtil.convertAndSend("设置", "新增角色", "新增角色：" + employeeSaveRequest.getRoleName());
        }

        operateLogMqUtil.convertAndSend("设置", "新增员工",
                "新增员工：" + employeeSaveRequest.getEmployeeName());
        EmployeeAddResponse employeeAddResponse = employeeProvider.add(employeeSaveRequest).getContext();
        EsEmployeeSaveRequest esEmployeeSaveRequest = KsBeanUtil.convert(employeeAddResponse, EsEmployeeSaveRequest.class);
        return ResponseEntity.ok(esEmployeeProvider.save(esEmployeeSaveRequest));
    }


    /**
     * 查询s2b平台员工是否存在
     *
     * @param phone phone
     * @return boolean
     */
    @Operation(summary = "查询平台员工是否存在")
    @Parameter(name = "phone", description = "手机号", required = true)
    @RequestMapping(value = "/{phone}/exist", method = RequestMethod.GET)
    public BaseResponse findBossEmployeeExist(@jakarta.validation.constraints.Pattern(regexp = "1\\d{10}") @PathVariable("phone") String phone) {
        EmployeeByMobileResponse employee =
                Optional.ofNullable(
                        employeeQueryProvider.getByMobile(
                                EmployeeByMobileRequest.builder().accountType(AccountType.s2bBoss).mobile(phone).build()
                        ).getContext()).orElseThrow(() -> new SbcRuntimeException(CustomerErrorCodeEnum.K010093));
        //被禁用
        if (AccountState.DISABLE.equals(employee.getAccountState())) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010087,
                    new Object[]{""});
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 启用/禁用 账号
     *
     * @param request
     * @return
     */
    @Operation(summary = "启用/禁用员工账号")
    @PutMapping(value = "/switch")
    @GlobalTransactional
    public BaseResponse<List<EmployeeDisableOrEnableByCompanyIdVO>> switchEmp(@Valid @RequestBody
                                                                        EmployeeDisableOrEnableByCompanyIdRequest request) {
        return employeeService.switchEmp(request);
    }

    /**
     * 批量删除
     *
     * @param employeeRequest employeeRequest
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "批量删除员工")
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

        if (CollectionUtils.size(employeeIds) == 1) {
            EmployeeByIdResponse employee = employeeQueryProvider.getById(
                    EmployeeByIdRequest.builder().employeeId(employeeIds.get(0)).build()).getContext();
            if (Objects.isNull(employee)) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010086);
            }
            operateLogMqUtil.convertAndSend("设置", "删除员工",
                    "删除员工：" + employee.getAccountName());

        }
        if (CollectionUtils.size(employeeIds) > 1) {
            operateLogMqUtil.convertAndSend("设置", "批量删除", "批量删除");
        }

        employeeProvider.batchDeleteByIds(
                EmployeeBatchDeleteByIdsRequest.builder()
                        .employeeIds(employeeIds)
                        .accountType(AccountType.s2bBoss).build());
        return ResponseEntity.ok(esEmployeeProvider.batchDeleteByIds(EsEmployeeBatchDeleteByIdsRequest.builder()
                .employeeIds(employeeIds)
                .accountType(AccountType.s2bBoss).build()));
    }

    /**
     * 发送验证码
     *
     * @param phone phone
     * @return BaseResponse
     */
    @Operation(summary = "发送验证码")
    @Parameters({
            @Parameter(name = "phone", description = "手机号", required = true),
            @Parameter(name = "uuid", description = "uuid", required = true)
    })
    @RequestMapping(value = "/send/sms", method = RequestMethod.POST)
    public BaseResponse sendSms(@jakarta.validation.constraints.Pattern(regexp = "1\\d{10}") @RequestParam("phone")
                                        String phone, @RequestParam("uuid") String uuid) {

        EmployeeByIdResponse employee = employeeQueryProvider.getById(
                EmployeeByIdRequest.builder().employeeId(commonUtil.getOperatorId()).build())
                .getContext();
        if (employee != null && !phone.equals(employee.getEmployeeMobile())) {
            EmployeeMobileExistsResponse existsResponse = employeeQueryProvider.mobileIsExists(
                    EmployeeMobileExistsRequest.builder()
                            .mobile(phone)
                            .accountType(AccountType.s2bSupplier)
                            .build())
                    .getContext();
            EmployeeMobileExistsResponse existsMallResponse = employeeQueryProvider.mobileIsExists(
                    EmployeeMobileExistsRequest.builder()
                            .mobile(phone)
                            .accountType(AccountType.s2bProvider)
                            .build())
                    .getContext();
            if (existsResponse.isExists() || existsMallResponse.isExists()) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010076);
            }
        }

        String regexp = "1\\d{10}";
        Pattern p = Pattern.compile(regexp);
        if (p.matcher(phone).find() && verifyCodeService.validateCaptchaCertificate(uuid)) {
            if (verifyCodeService.validSmsCertificate(phone, VerifyType.EMPLOYEE_CHANGE_PHONE)) {
                String verifyCode = verifyCodeService.generateSmsVerifyCode(phone, VerifyType.EMPLOYEE_CHANGE_PHONE, 5, TimeUnit.MINUTES);
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
     * 根据业务员名称模糊查询
     *
     * @param  employeeByNameRequest
     * @return boolean
     */
    @DepartmentIsolation(isIncluedeParentDepartment = false)
    @Operation(summary = "根据业务员名称模糊查询")
    @RequestMapping(value = "/employee/name", method = RequestMethod.POST)
    public BaseResponse<EmployeeByNameResponse> findBossEmployeeByName(@RequestBody EmployeeByNameRequest employeeByNameRequest) {

        EmployeePageRequest request = new EmployeePageRequest();
        request.setDepartmentIds(employeeByNameRequest.getDepartmentIds());
        request.setIsEmployeeSearch(Boolean.TRUE);
        request.setEmployeeName(employeeByNameRequest.getName());
        request.setAccountType(AccountType.s2bBoss);
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
    public BaseResponse findEmployees(@RequestBody @Valid EmployeeListByIdsRequest idsRequest) {
        EmployeeActivateAccountRequest request = new EmployeeActivateAccountRequest();
        request.setEmployeeIds(idsRequest.getEmployeeIds());
        request.setCustomerType(CustomerType.PLATFORM);
        request.setOperator(commonUtil.getOperatorId());
        request.setStoreId(commonUtil.getStoreId());
        EmployeeActivateAccountResponse response = employeeProvider.activateAccount(request).getContext();
        operateLogMqUtil.convertAndSend("会员", "账户激活", "账户激活数：" + response.getNum());
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
        request.setAccountType(AccountType.s2bBoss);
        employeeExcelService.importEmployee(request);
        //操作日志记录
        operateLogMqUtil.convertAndSend("员工", "批量导入", "批量导入");
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
        EmployeeNumRequest request = EmployeeNumRequest.builder().accountType(AccountType.s2bBoss).build();
        return employeeQueryProvider.countEmployeeNum(request);
    }

}
