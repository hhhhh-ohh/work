package com.wanmi.sbc.elastic.provider.impl.employee;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.elastic.api.provider.employee.EsEmployeeProvider;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeActivateAccountRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeBatchDeleteByIdsRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeBatchDimissionByIdsRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeBatchDisableByIdsRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeBatchEnableByIdsRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeChangeDepartmentRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeDisableByIdRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeHandoverRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeImportRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeInitRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeListByIdsRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeModifyLeaderByIdRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeModifyNameByIdRequest;
import com.wanmi.sbc.elastic.api.request.employee.EsEmployeeSaveRequest;
import com.wanmi.sbc.elastic.employee.mapper.EsEmployeeMapper;
import com.wanmi.sbc.elastic.employee.model.root.EsEmployee;
import com.wanmi.sbc.elastic.employee.service.EsEmployeeService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EsEmployeeProviderImpl implements EsEmployeeProvider {
    @Autowired
    EsEmployeeService esEmployeeService;

    @Autowired
    EsEmployeeMapper esEmployeeMapper;

    @Override
    public BaseResponse save(@RequestBody @Valid EsEmployeeSaveRequest esEmployeeSaveRequest) {
        EsEmployee esEmployee = esEmployeeMapper.esEmployeeAddRequestToEsEmployee(esEmployeeSaveRequest);
        if(esEmployee.getIsLeader() == null) {
            esEmployee.setIsLeader(Constants.ZERO);
        }
        if(esEmployee.getBecomeMember() == null) {
            esEmployee.setBecomeMember(Constants.ZERO);
        }
        esEmployeeService.save(esEmployee);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse batchEnableByIds(@RequestBody @Valid EsEmployeeBatchEnableByIdsRequest esEmployeeBatchEnableByIdsRequest) {
        List<String> employeeIds = esEmployeeBatchEnableByIdsRequest.getEmployeeIds();
        esEmployeeService.batchEnableByIds(employeeIds);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse disableById(@RequestBody @Valid EsEmployeeDisableByIdRequest esEmployeeDisableByIdRequest) {
        esEmployeeService.disableById(esEmployeeDisableByIdRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyLeaderById(@RequestBody @Valid EsEmployeeModifyLeaderByIdRequest esEmployeeDisableByIdRequest) {
        esEmployeeService.modifyLeaderById(esEmployeeDisableByIdRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse batchDisableByIds(@RequestBody @Valid EsEmployeeBatchDisableByIdsRequest esEmployeeBatchDisableByIdsRequest) {
        esEmployeeService.batchDisableByIds(esEmployeeBatchDisableByIdsRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse batchDeleteByIds(@RequestBody @Valid EsEmployeeBatchDeleteByIdsRequest esEmployeeBatchDeleteByIdsRequest) {
        esEmployeeService.batchDeleteByIds(esEmployeeBatchDeleteByIdsRequest.getEmployeeIds());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse changeDepartment(@RequestBody @Valid EsEmployeeChangeDepartmentRequest eSEmployeeChangeDepartmentRequest) {
        esEmployeeService.changeDepartment(eSEmployeeChangeDepartmentRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse batchSetEmployeeByIds(@RequestBody @Valid EsEmployeeListByIdsRequest esEmployeeListByIdsRequest) {
        esEmployeeService.batchSetEmployeeByIds(esEmployeeListByIdsRequest.getEmployeeIds());
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse batchDimissionByIds(@RequestBody @Valid EsEmployeeBatchDimissionByIdsRequest esEmployeeBatchDimissionByIdsRequest) {
        esEmployeeService.batchDimissionByIds(esEmployeeBatchDimissionByIdsRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse activateAccount(@RequestBody @Valid EsEmployeeActivateAccountRequest esEsEmployeeActivateAccountRequest) {
        esEmployeeService.activateAccount(esEsEmployeeActivateAccountRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse handoverEmployee(@RequestBody @Valid EsEmployeeHandoverRequest esEmployeeHandoverRequest) {
        esEmployeeService.handoverEmployee(esEmployeeHandoverRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse importEmployee(@RequestBody @Valid EsEmployeeImportRequest esEmployeeImportRequest) {
        esEmployeeService.saveAll(esEmployeeMapper.
                esEmployeeAddRequestListToEsEmployeeList(esEmployeeImportRequest.getEmployeeList()));
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse init(@RequestBody @Valid EsEmployeeInitRequest employeePageRequest) {
        esEmployeeService.init(employeePageRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse modifyNameById(@RequestBody @Valid EsEmployeeModifyNameByIdRequest esEmployeeModifyNameByIdRequest) {
        esEmployeeService.modifyNameById(esEmployeeModifyNameByIdRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
