package com.wanmi.sbc.customer;

import static java.util.Objects.nonNull;

import com.wanmi.sbc.base.verifycode.VerifyCodeService;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeProvider;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.RoleInfoQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeAccountByIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeBatchDimissionByIdsRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeBatchDisableByIdsRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeBatchEnableByIdsRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeChangeDepartmentRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeDisableByIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeHandoverRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListByAccountTypeRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListByIdsRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeMobileModifyByIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeModifyRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeNameModifyByIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeOptionalByIdRequest;
import com.wanmi.sbc.customer.api.request.employee.EmployeeRequest;
import com.wanmi.sbc.customer.api.request.employee.RoleInfoListRequest;
import com.wanmi.sbc.customer.api.response.employee.EmployeeAccountByIdResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeByIdResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeModifyResponse;
import com.wanmi.sbc.customer.api.response.employee.EmployeeOptionalByIdResponse;
import com.wanmi.sbc.customer.api.response.employee.RoleInfoListResponse;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.EmployeeListByAccountTypeVO;
import com.wanmi.sbc.customer.bean.vo.RoleInfoVO;
import com.wanmi.sbc.customer.request.EmployeeExcelImportRequest;
import com.wanmi.sbc.customer.service.EmployeeExcelService;
import com.wanmi.sbc.customer.validator.EmployeeEditValidator;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.provider.employee.EsEmployeeProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeBatchDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeBatchDimissionByIdsRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeBatchDisableByIdsRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeBatchEnableByIdsRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeChangeDepartmentRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeDisableByIdRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeHandoverRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeListByIdsRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeModifyNameByIdRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeSaveRequest;
import com.wanmi.sbc.setting.bean.enums.VerifyType;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;

import io.jsonwebtoken.Claims;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.DataBinder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 员工bff
 * Created by zhangjin on 2017/4/18.
 */
@Tag(name = "EmployeeBaseController", description = "员工 Api")
@RestController
@Validated
@RequestMapping("/customer")
public class EmployeeBaseController {

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private EmployeeProvider employeeProvider;

    @Autowired
    private RoleInfoQueryProvider roleInfoQueryProvider;

    @Autowired
    private EmployeeEditValidator employeeEditValidator;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EmployeeExcelService employeeExcelService;

    @Autowired
    private EsEmployeeProvider esEmployeeProvider;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;

    @InitBinder
    public void initBinder(DataBinder binder) {
        if (binder.getTarget() instanceof EmployeeModifyRequest) {
            binder.setValidator(employeeEditValidator);
        }
    }

    /**
     * 修改员工
     *
     * @param employeeSaveRequest employeeSaveRequest
     * @return employee
     *
     */
    @Operation(summary = "修改员工")
    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    @GlobalTransactional
    public BaseResponse<EmployeeModifyResponse> update(@RequestBody @Valid EmployeeModifyRequest employeeSaveRequest) {
        //校验当前修改员工是否为当前店铺员工
        EmployeeByIdResponse context = employeeQueryProvider.getById(EmployeeByIdRequest.builder().employeeId(employeeSaveRequest.getEmployeeId()).build()).getContext();
        if (Objects.isNull(context)){
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010135);
        }
        commonUtil.checkCompanyInfoId(context.getCompanyInfoId());
        employeeSaveRequest.setAccountType(getAccountType());
        employeeSaveRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        Claims claims = (Claims) HttpUtil.getRequest().getAttribute("claims");
        String opContext = "编辑员工：" + employeeSaveRequest.getEmployeeName();
        if (nonNull(claims)) {
            String platform = Objects.toString(claims.get("platform"), "");
            if (Platform.SUPPLIER.toValue().equals(platform)) {
                opContext = "编辑员工：员工账户名 " + employeeSaveRequest.getEmployeeName();
            }
        }
        operateLogMQUtil.convertAndSend("设置", "编辑员工", opContext);
        BaseResponse<EmployeeModifyResponse> modifyEmployee = employeeProvider.modify(employeeSaveRequest);
        EmployeeModifyResponse employeeModifyResponse = modifyEmployee.getContext();
        EsEmployeeSaveRequest esEmployeeSaveRequest = KsBeanUtil.convert(employeeModifyResponse, EsEmployeeSaveRequest.class);
        esEmployeeProvider.save(esEmployeeSaveRequest);
        return modifyEmployee;
    }

    /**
     * 查询会员
     *
     * @param employeeId employeeId
     * @return ResponseEntity<Employee>
     */
    @Operation(summary = "查询会员")
    @Parameter(name = "employeeId", description = "员工Id", required = true)
    @RequestMapping(value = "/employee/info/{employeeId}")
    public BaseResponse<EmployeeByIdResponse> findByEmployeeId(@PathVariable("employeeId") String employeeId) {
        EmployeeByIdResponse response = employeeQueryProvider.getById(EmployeeByIdRequest.builder().employeeId(employeeId)
                .build()).getContext();
        if(Objects.isNull(response)){
            //员工为空时，删除es
            esEmployeeProvider.batchDeleteByIds(EsEmployeeBatchDeleteByIdsRequest.builder()
                    .employeeIds(Collections.singletonList(employeeId)).build());
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010133);
        }
        return BaseResponse.success(response);
    }

    /**
     * 启用员工
     *
     * @param employeeRequest employeeRequest
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "启用员工")
    @RequestMapping(value = "/employee/enable", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> enableEmployees(@RequestBody EmployeeRequest employeeRequest) {
        List<String> employeeIds = employeeRequest.getEmployeeIds();
        Claims claims = (Claims) HttpUtil.getRequest().getAttribute("claims");
        employeeProvider.batchEnableByIds(EmployeeBatchEnableByIdsRequest.builder()
                .employeeIds(employeeIds).build());
        //操作日志记录
        if (1 == CollectionUtils.size(employeeIds)) {
            EmployeeByIdResponse employee = employeeQueryProvider.getById(EmployeeByIdRequest.builder()
                    .employeeId(employeeIds.get(0)).build()).getContext();
            if (nonNull(employee)) {
                String opContext = "启用员工：" + employee.getAccountName();
                if (nonNull(claims)) {
                    String platform = Objects.toString(claims.get("platform"), "");
                    if (Platform.SUPPLIER.toValue().equals(platform)) {
                        opContext = "启用员工：员工账户名 " + employee.getAccountName();
                    }
                }
                operateLogMQUtil.convertAndSend("设置", "启用员工", opContext);
            } else {
                //员工为空时，删除es
                esEmployeeProvider.batchDeleteByIds(EsEmployeeBatchDeleteByIdsRequest.builder()
                        .employeeIds(Collections.singletonList(employeeIds.get(0))).build());
            }
        } else {
            operateLogMQUtil.convertAndSend("设置", "批量启用", "批量启用");
        }
        return ResponseEntity.ok(esEmployeeProvider.
                batchEnableByIds(EsEmployeeBatchEnableByIdsRequest.builder().employeeIds(employeeIds)
                        .build()));
    }

    /**
     * 禁用员工
     *
     * @param employeeRequest employeeRequest
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "禁用员工")
    @RequestMapping(value = "/employee/disable", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> disableEmployees(@RequestBody EmployeeRequest employeeRequest) {
        if (Objects.isNull(employeeRequest.getEmployeeId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        EmployeeDisableByIdRequest request = new EmployeeDisableByIdRequest();
        KsBeanUtil.copyPropertiesThird(employeeRequest, request);
        employeeProvider.disableById(request);
        Claims claims = (Claims) HttpUtil.getRequest().getAttribute("claims");
        EmployeeByIdResponse employee = employeeQueryProvider.getById(EmployeeByIdRequest.builder()
                .employeeId(employeeRequest.getEmployeeId()).build()).getContext();
        if (nonNull(employee)) {
            if(Objects.equals(Constants.SYSTEM,employee.getAccountName())){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            //越权校验
            commonUtil.checkCompanyInfoId(employee.getCompanyInfoId());
            String opContext = "禁用员工：" + employee.getAccountName();
            if (nonNull(claims)) {
                String platform = Objects.toString(claims.get("platform"), "");
                if (Platform.SUPPLIER.toValue().equals(platform)) {
                    opContext = "禁用员工：员工账户名 " + employee.getAccountName();
                }
            }
            operateLogMQUtil.convertAndSend("设置", "禁用员工", opContext);
        } else {
            //员工为空时，删除es
            esEmployeeProvider.batchDeleteByIds(EsEmployeeBatchDeleteByIdsRequest.builder()
                    .employeeIds(Collections.singletonList(employeeRequest.getEmployeeId())).build());
        }
        EsEmployeeDisableByIdRequest esEmployeeDisableByIdRequest = new EsEmployeeDisableByIdRequest();
        KsBeanUtil.copyPropertiesThird(request, esEmployeeDisableByIdRequest);
        return ResponseEntity.ok(esEmployeeProvider.disableById(esEmployeeDisableByIdRequest));
    }

    /**
     * 批量禁用员工
     *
     * @param employeeRequest employeeRequest
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "批量禁用员工")
    @RequestMapping(value = "/employee/batch/disable", method = RequestMethod.POST)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> batchDisableEmployees(@RequestBody EmployeeRequest employeeRequest) {
        EmployeeBatchDisableByIdsRequest idsRequest = new EmployeeBatchDisableByIdsRequest();
        KsBeanUtil.copyPropertiesThird(employeeRequest, idsRequest);
        operateLogMQUtil.convertAndSend("设置", "批量禁用", "批量禁用");
        employeeProvider.batchDisableByIds(idsRequest);
        EsEmployeeBatchDisableByIdsRequest request = new EsEmployeeBatchDisableByIdsRequest();
        KsBeanUtil.copyPropertiesThird(idsRequest, request);
        return ResponseEntity.ok(esEmployeeProvider.batchDisableByIds(request));
    }

    /**
     * 查询所有角色
     *
     * @return ResponseEntity<List < RoleInfo>>
     */
    @Operation(summary = "查询所有角色")
    @RequestMapping(value = "/employee/roles", method = RequestMethod.GET)
    public ResponseEntity<List<RoleInfoVO>> findAllRoles() {
        RoleInfoListRequest roleInfoListRequest = new RoleInfoListRequest();
        roleInfoListRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        if(StoreType.O2O.equals(commonUtil.getStoreType()) && BoolFlag.NO.equals(commonUtil.getCompanyType())){
            roleInfoListRequest.setCompanyInfoId(-1L);
        }
        BaseResponse<RoleInfoListResponse> roleInfoListResponseBaseResponse =
                roleInfoQueryProvider.listByCompanyInfoId(roleInfoListRequest);
        RoleInfoListResponse roleInfoListResponse = roleInfoListResponseBaseResponse.getContext();
        if (Objects.nonNull(roleInfoListResponse)) {
            return ResponseEntity.ok(roleInfoListResponse.getRoleInfoVOList());
        }
        return ResponseEntity.ok(Collections.emptyList());
    }


    /**
     * 查询员工
     *
     * @return ResponseEntity<EmployeeAccountResponse>
     */
    @Operation(summary = "查询员工")
    @RequestMapping(value = "/employee/info", method = RequestMethod.GET)
    public ResponseEntity<EmployeeAccountByIdResponse> findEmployee() {
        return ResponseEntity.ok(employeeQueryProvider.getAccountById(
                EmployeeAccountByIdRequest.builder().employeeId(commonUtil.getOperatorId()).build()
        ).getContext());
    }


    /**
     * 根据员工名称修改员工
     *
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "根据员工名称修改员工")
    @RequestMapping(value = "/employeeName", method = RequestMethod.PUT)
    @GlobalTransactional
    public ResponseEntity<BaseResponse> findEmployee(@RequestBody EmployeeModifyRequest employeeSaveRequest) {
        if(StringUtils.isBlank(employeeSaveRequest.getEmployeeName())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        operateLogMQUtil.convertAndSend("账户管理", "账号管理", "修改员工姓名");
        String employeeId = commonUtil.getOperatorId();
        employeeProvider.modifyNameById(
                EmployeeNameModifyByIdRequest.builder()
                        .employeeId(employeeId)
                        .employeeName(employeeSaveRequest.getEmployeeName())
                        .build()
        );
        EsEmployeeModifyNameByIdRequest esEmployeeModifyNameByIdRequest = new EsEmployeeModifyNameByIdRequest();
        esEmployeeModifyNameByIdRequest.setEmployeeId(employeeId);
        esEmployeeModifyNameByIdRequest.setEmployeeName(employeeSaveRequest.getEmployeeName());
        return ResponseEntity.ok(esEmployeeProvider.modifyNameById(esEmployeeModifyNameByIdRequest));
    }


    /**
     * 校验手机验证码
     *
     * @param phone phone
     * @param code  code
     * @return BaseResponse
     */
    @Operation(summary = "校验手机验证码")
    @Parameter(name = "phone", description = "电话", required = true)
    @Parameter(name = "code", description = "验证码", required = true)
    @RequestMapping(value = "/sms/valid", method = RequestMethod.POST)
    public BaseResponse validSms(@Pattern(regexp = "1\\d{10}") @RequestParam("phone") String phone, String code) {
        return verifyCodeService.validateSmsVerifyCode(phone, code, VerifyType.EMPLOYEE_CHANGE_PHONE, 1,
                TimeUnit.DAYS) ?
                BaseResponse.SUCCESSFUL() : BaseResponse.FAILED();
    }

    /**
     * 绑定手机号
     *
     * @param phone phone
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "绑定手机号")
    @Parameters({
            @Parameter(name = "phone", description = "电话", required = true),
            @Parameter(name = "code", description = "验证码", required = true)
    })
    @RequestMapping(value = "/send/bind", method = RequestMethod.POST)
    public ResponseEntity<BaseResponse> bindUserPhone(@jakarta.validation.constraints.Pattern(regexp = "1\\d{10}") @RequestParam("phone") String phone, String code) {
        if(verifyCodeService.validateSmsVerifyCode(phone, code, VerifyType.EMPLOYEE_CHANGE_PHONE, 1, TimeUnit.DAYS)) {
            operateLogMQUtil.convertAndSend("账户管理", "账号管理", "修改绑定手机");
            if (verifyCodeService.validatePhoneCertificate(phone, VerifyType.EMPLOYEE_CHANGE_PHONE)) {
                return ResponseEntity.ok(employeeProvider.modifyMobileById(
                        EmployeeMobileModifyByIdRequest.builder()
                                .employeeId(commonUtil.getOperatorId())
                                .mobile(phone).build()
                ));
            } else {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010134);
            }
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000010);
        }
    }

    /**
     * 查询boss所有员工，用于商家端调用显示企业会员列表
     *
     * @return <List<EmployeeResponse>>
     */
    @Operation(summary = "查询boos所有员工")
    @GetMapping(value = "/boss/allEmployees")
    @ReturnSensitiveWords(functionName = "f_find_boss_all_employees_sign_word")
    public ResponseEntity<List<EmployeeResponse>> findAllEmployees() {
        List<EmployeeListByAccountTypeVO> employeeList = employeeQueryProvider
                .listByAccountType(EmployeeListByAccountTypeRequest.builder().accountType(AccountType.s2bBoss).build())
                .getContext().getEmployeeList();
        List<EmployeeResponse> responses = employeeList.stream().filter(Objects::nonNull).map(employee -> {
            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setEmployeeId(employee.getEmployeeId());
            employeeResponse.setEmployeeName(employee.getEmployeeName());
            return employeeResponse;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * 查询supplier所有非主账号员工，兼容O2O
     *
     * @return <List<EmployeeResponse>>
     */
    @Operation(summary = "查询supplier所有非主账号员工，兼容O2O")
    @GetMapping(value = "/supplier/employees")
    public BaseResponse<List<EmployeeResponse>> findSupplierEmployees() {
        Platform platform = commonUtil.getOperator().getPlatform();
        EmployeeListRequest employeeListRequest = new EmployeeListRequest();
        employeeListRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        employeeListRequest.setIsMasterAccount(0);
        if(platform != null && platform == Platform.STOREFRONT){
            employeeListRequest.setAccountType(AccountType.O2O);
        }else {
            employeeListRequest.setAccountType(AccountType.s2bSupplier);
        }
        employeeListRequest.setAccountState(AccountState.ENABLE);

        return BaseResponse.success(
                employeeQueryProvider.list(employeeListRequest).getContext()
                        .getEmployeeList().stream().filter(Objects::nonNull).map(employee -> {
                    EmployeeResponse employeeResponse = new EmployeeResponse();
                    employeeResponse.setEmployeeId(employee.getEmployeeId());
                    employeeResponse.setEmployeeName(employee.getEmployeeName());
                    employeeResponse.setEmployeeMobile(employee.getEmployeeMobile());
                    return employeeResponse;
                }).collect(Collectors.toList()));
    }

    /**
     * 查询员工自己的信息
     *
     * @return 员工信息
     */
    @Operation(summary = "查询员工自己的信息")
    @GetMapping("/employee/myself")
    @ReturnSensitiveWords(functionName = "f_boss_employee_find_myself_sign_word")
    public BaseResponse<EmployeeOptionalByIdResponse> findMyself() {
        return employeeQueryProvider.getOptionalById(EmployeeOptionalByIdRequest.builder()
                .employeeId(commonUtil.getOperatorId()).build());
    }
    /**
     * 批量离职员工
     *
     * @param request employeeRequest
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "批量离职员工")
    @RequestMapping(value = "/employee/batch/dimission", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse batchDimissionEmployees(@RequestBody EmployeeBatchDimissionByIdsRequest request) {
        employeeProvider.batchDimissionByIds(request);
        operateLogMQUtil.convertAndSend("设置", "批量离职", "批量离职");
        esEmployeeProvider.batchDimissionByIds(KsBeanUtil.convert(request,EsEmployeeBatchDimissionByIdsRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 批量设为业务员
     *
     * @param request employeeRequest
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "批量设为业务员")
    @RequestMapping(value = "/employee/batch/setEmployee", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse batchSetEmployee(@RequestBody EmployeeListByIdsRequest request) {
        Integer num = employeeProvider.batchSetEmployeeByIds(request).getContext();
        operateLogMQUtil.convertAndSend("设置", "批量设置业务员", "批量设置业务员数" + num);
        esEmployeeProvider.batchSetEmployeeByIds(KsBeanUtil.convert(request,EsEmployeeListByIdsRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 调整部门
     *
     * @param request employeeRequest
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "调整部门")
    @RequestMapping(value = "/employee/adjustDepartment", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse adjustDepartment(@RequestBody EmployeeChangeDepartmentRequest request) {
        employeeProvider.changeDepartment(request);
        operateLogMQUtil.convertAndSend("设置", "批量调整部门", "批量调整部门");
        esEmployeeProvider.changeDepartment(KsBeanUtil.convert(request, EsEmployeeChangeDepartmentRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 业务员交接
     *
     * @param request employeeRequest
     * @return ResponseEntity<BaseResponse>
     */
    @Operation(summary = "业务员交接")
    @RequestMapping(value = "/employee/handoverEmployee", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse employeeHandover(@RequestBody EmployeeHandoverRequest request) {
        List<String> num = employeeProvider.handoverEmployee(request).getContext();
        operateLogMQUtil.convertAndSend("设置", "业务员交接", "业务员交接数：" + num.size());
        if(CollectionUtils.isNotEmpty(num)){
            esEmployeeProvider.handoverEmployee(KsBeanUtil.convert(request,EsEmployeeHandoverRequest.class));

            //初始化会员es信息
            esCustomerDetailProvider.init(EsCustomerDetailInitRequest.builder()
                    .idList(num)
                    .build());
        }

        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 下载员工导入模版
     *
     */
    @Operation(summary = "下载员工导入模版")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/employee/excel/template/{encrypted}", method = RequestMethod.GET)
    public void downloadTemplate(@PathVariable String encrypted) {
        String file = employeeProvider.exportTemplate().getContext().getFile();
        if (StringUtils.isNotBlank(file)) {
            try {
                String fileName = URLEncoder.encode("员工导入模板.xls", StandardCharsets.UTF_8.name());
                HttpUtil.getResponse().setHeader("Content-Disposition", String.format("attachment;filename=\"%s\";" +
                        "filename*=\"utf-8''%s\"", fileName, fileName));
                HttpUtil.getResponse().getOutputStream().write(Base64.getDecoder().decode(file));
            } catch (Exception e) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
            }
        }
    }

    /**
     * 下载错误文档
     */
    @Operation(summary = "下载错误文档")
    @RequestMapping(value = "/employee/excel/err/{ext}/{decrypted}", method = RequestMethod.GET)
    public void downErrExcel(@PathVariable String ext, @PathVariable String decrypted) {
        if (!(Constants.XLS.equalsIgnoreCase(ext) || Constants.XLSX.equalsIgnoreCase(ext))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        employeeExcelService.downErrExcel(commonUtil.getOperatorId(), ext);
    }

    /**
     * 上传文件
     */
    @Operation(summary = "上传文件")
    @RequestMapping(value = "/employee/excel/upload", method = RequestMethod.POST)
    public BaseResponse<String> upload(@RequestParam("uploadFile") MultipartFile uploadFile) {
        Platform platform = commonUtil.getOperator().getPlatform();
        boolean isO2o = platform == Platform.STOREFRONT;
        AccountType accountType = Platform.SUPPLIER == platform ?
                AccountType.s2bSupplier : isO2o ? AccountType.O2O : AccountType.s2bProvider;

        EmployeeExcelImportRequest request = new EmployeeExcelImportRequest();
        Long companyInfoId = null;
        if(isO2o){
            companyInfoId = -1L;
        }else {
            companyInfoId = commonUtil.getCompanyInfoId();
        }
        request.setCompanyInfoId(companyInfoId);
        request.setUserId(commonUtil.getOperatorId());
        request.setAccountType(accountType);
        return BaseResponse.success(employeeExcelService.upload(uploadFile, request));
    }

    /**
     *获取客户类型
     */
    private AccountType getAccountType(){
        Platform platform = commonUtil.getOperator().getPlatform();
        AccountType accountType = null;
        switch (platform){
            case PLATFORM:
                accountType = AccountType.s2bBoss;
                break;
            case SUPPLIER:
                accountType = AccountType.s2bSupplier;
                break;
            case PROVIDER:
                accountType = AccountType.s2bProvider;
                break;
            case STOREFRONT:
                accountType = AccountType.O2O;
                break;
            default:
                break;
        }
        return accountType;
    }

}
