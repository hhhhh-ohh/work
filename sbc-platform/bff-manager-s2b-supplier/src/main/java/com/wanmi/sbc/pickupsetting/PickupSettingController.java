package com.wanmi.sbc.pickupsetting;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.EmployeeResponse;
import com.wanmi.sbc.customer.api.constant.EmployeeErrorCode;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeListRequest;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.setting.api.provider.pickupsetting.PickupSettingProvider;
import com.wanmi.sbc.setting.api.provider.pickupsetting.PickupSettingQueryProvider;
import com.wanmi.sbc.setting.api.request.pickupsetting.*;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingByIdResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingConfigResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingListResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingPageResponse;
import com.wanmi.sbc.setting.bean.vo.PickupEmployeeRelaVO;
import com.wanmi.sbc.setting.bean.vo.PickupSettingVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Tag(name =  "pickup_setting管理API", description =  "PickupSettingController")
@RestController
@Validated
@RequestMapping(value = "/pickupsetting")
public class PickupSettingController {

    @Autowired
    private PickupSettingQueryProvider pickupSettingQueryProvider;

    @Autowired
    private PickupSettingProvider pickupSettingProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private PickupSettingService pickupSettingService;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Operation(summary = "分页查询")
    @PostMapping("/page")
    public BaseResponse<PickupSettingPageResponse> getPage(@RequestBody @Valid PickupSettingPageRequest pageReq) {
        pageReq.setStoreId(commonUtil.getStoreId());
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("isDefaultAddress", "desc");
        pageReq.putSort("createTime","desc");
        System.out.println(JSON.toJSONString(pageReq));
        BaseResponse<PickupSettingPageResponse> page = pickupSettingQueryProvider.page(pageReq);
        if (page.getContext().getPickupSettingVOS()!=null){
            List<PickupSettingVO> content = page.getContext().getPickupSettingVOS().getContent();
            if (CollectionUtils.isNotEmpty(content)){
                pickupSettingService.getInfo(content);
                pickupSettingService.phoneDesensitization(content);
            }
        }
        return page;
    }

    @Operation(summary = "根据id查询")
    @GetMapping("/{id}")
    public BaseResponse<PickupSettingByIdResponse> getById(@PathVariable Long id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        PickupSettingByIdRequest idReq = new PickupSettingByIdRequest();
        idReq.setId(id);
        PickupSettingByIdResponse response = pickupSettingQueryProvider.getById(idReq).getContext();
        pickupSettingService.getInfo(Collections.singletonList(response.getPickupSettingVO()));
        List<PickupEmployeeRelaVO> vos = pickupSettingService.getEmployeeInfo(response.getPickupEmployeeRelaVOS());
        response.setPickupEmployeeRelaVOS(vos);
        return BaseResponse.success(response);
    }

    @Operation(summary = "新增pickup_setting")
    @MultiSubmit
    @PostMapping("/add")
    public BaseResponse add(@RequestBody @Valid PickupSettingAddRequest addReq) {
        addReq.setStoreId(commonUtil.getStoreId());
        addReq.setCreatePerson(commonUtil.getOperatorId());
        addReq.setCreateTime(LocalDateTime.now());
        if (commonUtil.getOperator().getPlatform() == Platform.STOREFRONT){
            addReq.setStoreType(StoreType.O2O);
        }else {
            addReq.setStoreType(StoreType.SUPPLIER);
        }
        // 校验是否存在停用员工
        validatedEmployees(addReq.getEmployeeIds());
        pickupSettingProvider.add(addReq);
        operateLogMQUtil.convertAndSend("supplier自提点配置", "新增自提点", "新增自提点");
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "修改pickup_setting")
    @PutMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid PickupSettingModifyRequest modifyReq) {
        modifyReq.setStoreId(commonUtil.getStoreId());
        modifyReq.setUpdatePerson(commonUtil.getOperatorId());
        modifyReq.setUpdateTime(LocalDateTime.now());
        // 校验是否存在停用员工
        validatedEmployees(modifyReq.getEmployeeIds());
        pickupSettingProvider.modify(modifyReq);
        operateLogMQUtil.convertAndSend("supplier自提点配置", "修改自提点", "修改自提点");
        return BaseResponse.SUCCESSFUL();
    }

    private void validatedEmployees(List<String> employeeIds) {
        if (CollectionUtils.isNotEmpty(employeeIds)){
            EmployeeListRequest employeeListRequest = new EmployeeListRequest();
            employeeListRequest.setEmployeeIds(employeeIds);
            List<EmployeeResponse> employeeResponses = employeeQueryProvider.list(employeeListRequest).getContext()
                    .getEmployeeList().stream()
                    .filter(Objects::nonNull)
                    .filter(employee -> !employee.getAccountState().equals(AccountState.ENABLE))
                    .map(employee -> {
                        EmployeeResponse employeeResponse = new EmployeeResponse();
                        employeeResponse.setEmployeeId(employee.getEmployeeId());
                        employeeResponse.setEmployeeName(employee.getEmployeeName());
                        return employeeResponse;
                    }).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(employeeResponses)){
                StringBuilder disabledEmployee = new StringBuilder(StringUtils.EMPTY);
                for (EmployeeResponse employeeResponse:employeeResponses){
                    disabledEmployee.append(employeeResponse.getEmployeeName()).append("|");
                }
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050170,
                       new Object[] {String.format(EmployeeErrorCode.PICKUP_EMPLOYEE_DISABLED_TIPS, disabledEmployee)} );
            }
        }
    }

    @Operation(summary = "根据id删除pickup_setting")
    @DeleteMapping("/{id}")
    public BaseResponse deleteById(@PathVariable Long id) {
        PickupSettingDelByIdRequest delByIdReq = new PickupSettingDelByIdRequest();
        delByIdReq.setId(id);
        delByIdReq.setStoreId(commonUtil.getStoreId());
        delByIdReq.setDeletePerson(commonUtil.getOperatorId());
        delByIdReq.setDeleteTime(LocalDateTime.now());
        pickupSettingProvider.deleteById(delByIdReq);
        operateLogMQUtil.convertAndSend("supplier自提点配置", "删除自提点", "删除自提点");
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "自提点启停用")
    @PostMapping("/status")
    public BaseResponse pickupSettingAudit(@RequestBody @Valid PickupSettingAuditRequest request) {
        request.setStoreId(commonUtil.getStoreId());
        request.setUpdatePerson(commonUtil.getOperatorId());
        request.setAuditStatus(null);
        pickupSettingProvider.pickupSettingAudit(request);
        operateLogMQUtil.convertAndSend("supplier自提点配置", "自提点启/停用", "自提点启/停用");
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "设为默认自提点")
    @PostMapping("/defaultAddress")
    public BaseResponse pickupSettingDefaultAddress(@RequestBody @Valid PickupSettingDefaultAddressRequest request) {
        request.setUpdatePerson(commonUtil.getOperatorId());
        request.setBaseStoreId(commonUtil.getStoreId());
        pickupSettingProvider.pickupSettingDefaultAddress(request);
        operateLogMQUtil.convertAndSend("supplier自提点配置", "设为默认自提点", "设为默认自提点");
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "查询自提订单设置")
    @GetMapping("/pickupConfig-boss/{companyType}")
    public BaseResponse pickupSettingConfigShow(@PathVariable Integer companyType) {
        // 查询boss端是否配置自提
        PickupSettingConfigResponse response = pickupSettingQueryProvider.pickupSettingConfigShow().getContext();
        if(commonUtil.getOperator().getPlatform() != Platform.STOREFRONT){
            // 查询boss端是否配置自提自营商家开关
            if (Objects.nonNull(companyType) && 0 == companyType){
                Integer selfMerchantStatus = response.getSelfMerchantStatus();
                if (Objects.nonNull(selfMerchantStatus)){
                    return BaseResponse.success(selfMerchantStatus);
                }
            }else {
                // 查询boss端是否配置自提第三方商家开关
                Integer thirdMerchantStatus = response.getThirdMerchantStatus();
                if (Objects.nonNull(thirdMerchantStatus)){
                    return BaseResponse.success(thirdMerchantStatus);
                }
            }
        }else {
            Integer storeStatus = response.getStoreStatus();
            if(Objects.nonNull(storeStatus)){
                return BaseResponse.success(storeStatus);
            }
        }

        return BaseResponse.success(0);
    }

    @Operation(summary = "列表查询")
    @PostMapping("/list")
    public BaseResponse<PickupSettingListResponse> getList(@RequestBody @Valid PickupSettingListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("id", "desc");
        commonUtil.checkStoreId(listReq.getStoreId());
        return pickupSettingQueryProvider.list(listReq);
    }

}
