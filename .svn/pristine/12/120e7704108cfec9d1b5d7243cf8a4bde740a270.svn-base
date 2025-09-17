package com.wanmi.sbc.customer.provider.impl.employee;

import com.alibaba.fastjson2.JSONWriter;
import com.wanmi.sbc.common.base.BaseQueryResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.*;
import com.wanmi.sbc.customer.api.response.employee.*;
import com.wanmi.sbc.customer.bean.vo.*;
import com.wanmi.sbc.customer.company.service.CompanyInfoService;
import com.wanmi.sbc.customer.employee.model.root.Employee;
import com.wanmi.sbc.customer.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Validated
public class EmployeeQueryController implements EmployeeQueryProvider {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyInfoService companyInfoService;

    @Override
    public BaseResponse<EmployeeAccountNameExistsResponse> accountNameIsExists(@RequestBody @Valid
                                                                                       EmployeeAccountNameExistsRequest request) {

        return BaseResponse.success(employeeService.accountNameIsExists(request.getAccountName(), request.getAccountType(), request.getCompanyInfoId()));
    }

    @Override
    public BaseResponse<EmployeeMobileExistsResponse> mobileIsExists(@RequestBody @Valid EmployeeMobileExistsRequest
                                                                             request) {
        return BaseResponse.success(employeeService.mobileIsExists(request.getMobile(), request.getAccountType(), request.getCompanyInfoId()));
    }

    @Override
    public BaseResponse<EmployeeNameExistsResponse> nameIsExists(@RequestBody @Valid EmployeeNameExistsRequest
                                                                         request) {
        EmployeeNameExistsResponse response = new EmployeeNameExistsResponse();
        boolean existsName = employeeService.employeeNameIsExist(request.getEmployeeName(), request.getAccountType());
        response.setExists(existsName);
        return BaseResponse.success(response);
    }

    @Override

    public BaseResponse<EmployeeByMobileResponse> getByMobile(@RequestBody @Valid EmployeeByMobileRequest request) {
        Optional<Employee> employeeOptional =
                employeeService.findByPhone(request.getMobile(), request.getAccountType());

        EmployeeByMobileResponse response = new EmployeeByMobileResponse();
        if (employeeOptional.isPresent()) {
            KsBeanUtil.copyPropertiesThird(employeeOptional.get(), response);

            if (employeeOptional.get().getCompanyInfo() != null) {
                CompanyInfoVO vo = new CompanyInfoVO();
                KsBeanUtil.copyPropertiesThird(employeeOptional.get().getCompanyInfo(), vo);
                response.setCompanyInfo(vo);
            }
        } else {
            response = null;
        }
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<EmployeeByMobileResponse> getByPhone(@RequestBody @Valid EmployeeByPhoneRequest request) {
        Optional<Employee> employeeOptional =
                employeeService.findByPhoneNumber(request.getMobile());

        EmployeeByMobileResponse response = new EmployeeByMobileResponse();
        if (employeeOptional.isPresent()) {
            KsBeanUtil.copyPropertiesThird(employeeOptional.get(), response);

            if (employeeOptional.get().getCompanyInfo() != null) {
                CompanyInfoVO vo = new CompanyInfoVO();
                KsBeanUtil.copyPropertiesThird(employeeOptional.get().getCompanyInfo(), vo);
                response.setCompanyInfo(vo);
            }
        } else {
            response = null;
        }
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<EmployeePageResponse> page(@RequestBody @Valid EmployeePageRequest request) {
        Optional<Page<Employee>> optionalPage = employeeService.findByCiretira(request);
        EmployeePageResponse employeePageResponse = new EmployeePageResponse();
        optionalPage.ifPresent(page -> {
            BaseQueryResponse<Employee> baseQueryResponse = new BaseQueryResponse<>(page);
            List<EmployeePageVO> employeePageVOList = baseQueryResponse.getData().stream().
                    map(employee -> KsBeanUtil.convert(employee, EmployeePageVO.class, JSONWriter.Feature.ReferenceDetection)).collect(Collectors.toList());
            employeePageResponse.setEmployeePageVOPage(new MicroServicePage<>(employeePageVOList, request.getPageable
                    (), page.getTotalElements()));
        });
        return BaseResponse.success(employeePageResponse);
    }

    @Override
    public BaseResponse<EmployeePageListResponse> pageList(@RequestBody @Valid EmployeePageRequest request) {
        Optional<List<Employee>> optionalList = employeeService.findByCiretiraList(request);
        EmployeePageListResponse employeePageListResponse = new EmployeePageListResponse();
        List<EmployeePageVO> employeeListVOS = new ArrayList<>();
        if (optionalList.isPresent()) {
            employeeListVOS = KsBeanUtil.convertList(optionalList.get(), EmployeePageVO.class);
        }
        employeePageListResponse.setEmployeeList(employeeListVOS);
        return BaseResponse.success(employeePageListResponse);
    }

    @Override
    public BaseResponse<EmployeeListResponse> list(@RequestBody @Valid EmployeeListRequest request) {
        Optional<List<Employee>> optionalEmployeeList = employeeService.find(request);

        EmployeeListResponse response = optionalEmployeeList
                .map(employeeList -> {
                    employeeList = employeeList.stream()
                            .filter(employee -> !Objects.isNull(employee))
                            .collect(Collectors.toList());

                    EmployeeListResponse employeeListResponse = new EmployeeListResponse();
                    List<EmployeeListVO> employeeListVOList = KsBeanUtil.convertList(employeeList, EmployeeListVO.class);
                    employeeListResponse.setEmployeeList(employeeListVOList);
                    return employeeListResponse;
                })
                .orElse(null);

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<EmployeeOptionalByIdResponse> getOptionalById(@RequestBody @Valid
                                                                              EmployeeOptionalByIdRequest request) {
        Optional<Employee> employeeOptional = employeeService.findEmployeeById(request.getEmployeeId());
        EmployeeOptionalByIdResponse response = employeeOptional
                .map(employee -> {
                    EmployeeOptionalByIdResponse employeeOptionalByIdResponse = new EmployeeOptionalByIdResponse();
                    KsBeanUtil.copyPropertiesThird(employee, employeeOptionalByIdResponse);
                    return employeeOptionalByIdResponse;
                })
                .orElse(null);

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<EmployeeByIdResponse> getById(@RequestBody @Valid EmployeeByIdRequest request) {
        Optional<Employee> optionalEmployee = employeeService.findEmployeeInfoById(request.getEmployeeId());

        EmployeeByIdResponse response = optionalEmployee
                .map(emp -> {
                    EmployeeByIdResponse employeeByIdResponse = new EmployeeByIdResponse();
                    KsBeanUtil.copyPropertiesThird(emp, employeeByIdResponse);
                    return employeeByIdResponse;
                })
                .orElse(null);

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<EmployeeListByAccountTypeResponse> listByAccountType(@RequestBody @Valid
                                                                                     EmployeeListByAccountTypeRequest request) {

        Optional<List<Employee>> optionalEmployeeList = employeeService.findAllEmployees(request.getAccountType());

        EmployeeListByAccountTypeResponse response = optionalEmployeeList
                .map(employeeList -> {
                    employeeList = employeeList.stream()
                            .filter(employee -> !Objects.isNull(employee))
                            .collect(Collectors.toList());

                    EmployeeListByAccountTypeResponse employeeListByAccountTypeResponse =
                            new EmployeeListByAccountTypeResponse();
                    List<EmployeeListByAccountTypeVO> list = KsBeanUtil.convertList(employeeList, EmployeeListByAccountTypeVO.class);
                    employeeListByAccountTypeResponse.setEmployeeList(list);
                    return employeeListByAccountTypeResponse;
                })
                .orElse(null);

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<EmployeeListByCompanyIdResponse> listByCompanyId(@RequestBody @Valid
                                                                                 EmployeeListByCompanyIdRequest request) {
        Optional<List<Employee>> optionalEmployeeList =
                employeeService.findAllEmployeesForCompany(request.getCompanyInfoId());

        EmployeeListByCompanyIdResponse response = optionalEmployeeList
                .map(employeeList -> {
                    employeeList = employeeList.stream()
                            .filter(employee -> !Objects.isNull(employee))
                            .collect(Collectors.toList());

                    EmployeeListByCompanyIdResponse employeeListByCompanyIdResponse = new EmployeeListByCompanyIdResponse();
                    List<EmployeeListByCompanyIdVO> list = KsBeanUtil.convertList(employeeList, EmployeeListByCompanyIdVO.class);
                    employeeListByCompanyIdResponse.setEmployeeList(list);
                    return employeeListByCompanyIdResponse;
                })
                .orElse(null);

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<EmployeeListByIdsResponse> listByIds(@RequestBody @Valid EmployeeListByIdsRequest request) {
        Optional<List<Employee>> optionalEmployeeList = employeeService.findByEmployeeIds(request.getEmployeeIds());

        EmployeeListByIdsResponse response = optionalEmployeeList
                .map(employeeList -> {
                    employeeList = employeeList.stream()
                            .filter(employee -> !Objects.isNull(employee))
                            .collect(Collectors.toList());

                    EmployeeListByIdsResponse employeeListByIdsResponse = new EmployeeListByIdsResponse();
                    List<EmployeeListByIdsVO> list = KsBeanUtil.convertList(employeeList, EmployeeListByIdsVO.class);
                    employeeListByIdsResponse.setEmployeeList(list);
                    return employeeListByIdsResponse;
                })
                .orElse(null);

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<EmployeeByAccountNameResponse> getByAccountName(@RequestBody @Valid
                                                                                EmployeeByAccountNameRequest request) {

        Optional<Employee> employeeOptional = null;
        if (WmCollectionUtils.isEmpty(request.getAccountTypes())) {
            employeeOptional = employeeService.findByAccountName(request.getAccountName(), request.getAccountType());
        } else {
            employeeOptional = employeeService.findByAccountName(request.getAccountName(), request.getAccountType(),
                    request.getAccountTypes());
        }

        EmployeeByAccountNameResponse response = new EmployeeByAccountNameResponse();
        EmployeeVO employeeVO = null;
        if (employeeOptional.isPresent()) {
            employeeVO = new EmployeeVO();
            KsBeanUtil.copyPropertiesThird(employeeOptional.get(), employeeVO);

            if (employeeOptional.get().getCompanyInfo() != null) {
                CompanyInfoVO vo = new CompanyInfoVO();
                KsBeanUtil.copyPropertiesThird(employeeOptional.get().getCompanyInfo(), vo);
                employeeVO.setCompanyInfo(vo);
            }
        }
        response.setEmployee(employeeVO);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<EmployeeAccountByIdResponse> getAccountById(@RequestBody @Valid
                                                                            EmployeeAccountByIdRequest request) {

        Optional<EmployeeAccountResponse> optionalEmployeeAccountResponse =
                employeeService.findByEmployeeId(request.getEmployeeId());

        EmployeeAccountByIdResponse response = optionalEmployeeAccountResponse
                .map(emp -> {
                    EmployeeAccountByIdResponse employeeAccountByIdResponse = new EmployeeAccountByIdResponse();
                    KsBeanUtil.copyPropertiesThird(emp, employeeAccountByIdResponse);
                    return employeeAccountByIdResponse;
                })
                .orElse(null);

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<EmployeeByCompanyIdResponse> getByCompanyId(@RequestBody @Valid
                                                                            EmployeeByCompanyIdRequest request) {
        Optional<Employee> optionalEmployee = employeeService.findByComanyId(request.getCompanyInfoId());
        EmployeeByCompanyIdResponse response = optionalEmployee
                .map(emp -> {
                    EmployeeByCompanyIdResponse employeeByCompanyIdResponse = new EmployeeByCompanyIdResponse();
                    KsBeanUtil.copyPropertiesThird(emp, employeeByCompanyIdResponse);
                    return employeeByCompanyIdResponse;
                })
                .orElse(null);

        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<EmployeeSimpleByCompanyIdResponse> getSimpleByCompanyId(
            @RequestBody @Valid EmployeeSimpleByCompanyIdRequest request) {
        EmployeeSimpleVO vo =
                employeeService.findSimpleMainEmployeeByCompanyId(request.getCompanyInfoId());
        if (Objects.nonNull(vo)) {
            EmployeeSimpleByCompanyIdResponse response = new EmployeeSimpleByCompanyIdResponse();
            KsBeanUtil.copyPropertiesThird(vo, response);
            return BaseResponse.success(response);
        }
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<EmployeeMobileSmsResponse> mobileIsSms(@RequestBody @Valid
                                                                       EmployeeMobileSmsRequest
                                                                       employeeMobileSmsRequest) {
        EmployeeMobileSmsResponse response = new EmployeeMobileSmsResponse();
        response.setSendSms(employeeService.isSendSms(employeeMobileSmsRequest.getMobile()));
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<RoleIdByEmployeeIdResponse> getRoleIdByEmployeeId(@RequestBody @Valid RoleIdByEmployeeIdRequest
                                                                                  roleIdByEmployeeIdRequest) {
        RoleIdByEmployeeIdResponse response = new RoleIdByEmployeeIdResponse();
        response.setRoleId(employeeService.findUserRole(roleIdByEmployeeIdRequest.getEmployeeId()));
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<EmployeeByNameResponse> pageForEmployee(@Valid EmployeePageRequest employeePageRequest) {
        Optional<Page<Employee>> optionalPage = employeeService.findByCiretira(employeePageRequest);
        List<Map<String, String>> list = new ArrayList<>();
        if (optionalPage.isPresent()) {
            List<Employee> employeeList = optionalPage.get().getContent();
            if (CollectionUtils.isNotEmpty(employeeList)) {
                list = employeeList.stream().map(employeePageVO -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("employeeId", employeePageVO.getEmployeeId());
                    map.put("employeeMobile", employeePageVO.getEmployeeMobile());
                    map.put("employeeName", employeePageVO.getEmployeeName());
                    return map;
                }).collect(Collectors.toList());
            }
        }
        return BaseResponse.success(new EmployeeByNameResponse(list));
    }

    @Override
    public BaseResponse<EmployeeListByManageDepartmentIdsResponse> listByManageDepartmentIds(@Valid EmployeeListByManageDepartmentIdsRequest request) {
        List<Employee> employeeList = employeeService.findByManageDepartmentIds(request.getManageDepartmentIds());
        return BaseResponse.success(new EmployeeListByManageDepartmentIdsResponse(employeeList.stream()
                .map(Employee::getEmployeeId).collect(Collectors.toList())));
    }

    @Override
    public BaseResponse<EmployeeNumResponse> countEmployeeNum(@RequestBody @Valid EmployeeNumRequest request) {
        Integer num = employeeService.countEmployeeNum(request.getCompanyInfoId(), request.getAccountType());
        return BaseResponse.success(new EmployeeNumResponse(num));
    }

    @Override
    public BaseResponse<EmployeeJobNoExistsResponse> jobNoIsExist(@RequestBody @Valid EmployeeJobNoExistsRequest request) {
        EmployeeJobNoExistsResponse response = new EmployeeJobNoExistsResponse();
        Boolean exist = employeeService.employeeJobNoIsExist(request.getJobNo(), request.getAccountType(), request.getCompanyInfoId());
        response.setExists(exist);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<EmployeeListByHeirEmployeeIdResponse> listByHeirEmployeeId(@RequestBody @Valid EmployeeListByHeirEmployeeIdRequest request) {
        List<String> list = employeeService.findEmployeeIdByHeirEmployeeIdIn(request.getHeirEmployeeId());
        return BaseResponse.success(new EmployeeListByHeirEmployeeIdResponse(list));
    }

    @Override
    public BaseResponse<EmployeeFindTodoFunctionIdsResponse> findTodoFunctionIds(@Valid EmployeeFindTodoFunctionIdsRequest request) {
        return employeeService.findTodoFunctionIds(request);
    }

}
