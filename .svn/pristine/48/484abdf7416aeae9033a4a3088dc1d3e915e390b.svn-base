package com.wanmi.sbc.authority;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.RoleInfoQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeFindTodoFunctionIdsRequest;
import com.wanmi.sbc.customer.api.request.employee.RoleInfoListRequest;
import com.wanmi.sbc.customer.api.response.employee.EmployeeFindTodoFunctionIdsResponse;
import com.wanmi.sbc.customer.bean.vo.RoleInfoVO;
import com.wanmi.sbc.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description 角色权限服务
 * @author malianfeng
 * @date 2022/7/11 17:07
 */
@Slf4j
@Service
public class AuthBaseService {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private RoleInfoQueryProvider roleInfoQueryProvider;


    /**
     * 判断登录用户是否具备这些功能
     * @param functions 判断当前登录用户是否具有传入的这些功能权限
     */
    public List<String> findTodoFunctionIds(List<String> functions) {

        Operator operator = commonUtil.getOperator();
        if(Objects.equals(Platform.PLATFORM, operator.getPlatform())) {
            operator.setCompanyInfoId(null);
        }
        EmployeeFindTodoFunctionIdsResponse functionIdsResponse =
                employeeQueryProvider
                        .findTodoFunctionIds(
                                EmployeeFindTodoFunctionIdsRequest.builder()
                                        .functionList(functions)
                                        .operator(operator)
                                        .build())
                        .getContext();
        if (Objects.isNull(functionIdsResponse)
                || CollectionUtils.isEmpty(functionIdsResponse.getFunctions())) {
            return Lists.newArrayList();
        }
        return functionIdsResponse.getFunctions();
    }

    /**
     * 过滤roleId
     * @return
     */
    public List<Long> filterRoleId(List<Long> ids){
        RoleInfoListRequest roleInfoListRequest = new RoleInfoListRequest();
        roleInfoListRequest.setCompanyInfoId(commonUtil.getCompanyInfoId());
        if(StoreType.O2O.equals(commonUtil.getStoreType()) && BoolFlag.NO.equals(commonUtil.getCompanyType())){
            roleInfoListRequest.setCompanyInfoId(-1L);
        }
        List<RoleInfoVO> roleInfoVOList = roleInfoQueryProvider.listByCompanyInfoId(roleInfoListRequest).getContext().getRoleInfoVOList();
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(roleInfoVOList)){
            List<Long> roleInfoIds = roleInfoVOList.stream().map(RoleInfoVO::getRoleInfoId).collect(Collectors.toList());
            //过滤不存在的角色
            ids = ids.stream().filter(id -> roleInfoIds.contains(id)).collect(Collectors.toList());
        }
        return ids;
    }
}
