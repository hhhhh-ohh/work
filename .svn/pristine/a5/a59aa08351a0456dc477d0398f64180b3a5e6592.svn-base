package com.wanmi.sbc.authority;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.employee.EmployeeQueryProvider;
import com.wanmi.sbc.customer.api.provider.employee.RoleInfoQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.employee.EmployeeByIdRequest;
import com.wanmi.sbc.customer.api.request.employee.RoleIdByEmployeeIdRequest;
import com.wanmi.sbc.customer.api.request.employee.RoleInfoListRequest;
import com.wanmi.sbc.customer.api.request.employee.RoleInfoQueryRequest;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.api.response.employee.EmployeeByIdResponse;
import com.wanmi.sbc.customer.api.response.employee.RoleInfoQueryResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.customer.bean.vo.RoleInfoVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.ledgeraccount.LedgerAccountBaseService;
import com.wanmi.sbc.setting.api.provider.RoleMenuProvider;
import com.wanmi.sbc.setting.api.provider.RoleMenuQueryProvider;
import com.wanmi.sbc.setting.api.request.*;
import com.wanmi.sbc.setting.api.response.RoleMenuFuncIdsQueryResponse;
import com.wanmi.sbc.setting.bean.enums.SystemAccount;
import com.wanmi.sbc.setting.bean.vo.MenuInfoVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 角色菜单权限管理Controller
 * Author: bail
 * Time: 2017/12/29
 */
@Tag(name = "RoleMenuAuthBaseController", description = "角色菜单权限管理 Api")
@RestController
@Validated
@RequestMapping("/roleMenuFunc")
public class RoleMenuAuthBaseController {

    @Autowired
    private RoleMenuProvider roleMenuProvider;

    @Autowired
    private RoleMenuQueryProvider roleMenuQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EmployeeQueryProvider employeeQueryProvider;

    @Autowired
    private RoleInfoQueryProvider roleInfoQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    /**
     * 标识门店-1
     */
    private static final Long O2O_COMPANYINFOID = -1L;

    @Autowired
    private LedgerAccountBaseService ledgerAccountBaseService;

    private static final List<String> ledgerMenus = Lists.newArrayList("清分结算","清分账户","分账绑定","清分结算", "分账关系协议", "分账开通记录");

    /**
     * 查询角色拥有的菜单id与权限id信息
     */
    @Operation(summary = "查询角色拥有的菜单id与权限id信息")
    @Parameter(name = "roleInfoId", description = "角色Id", required = true)
    @RequestMapping(value = "/{roleInfoId}", method = RequestMethod.GET)
    public BaseResponse<RoleMenuFuncIdsQueryResponse> get(@PathVariable Long roleInfoId) {
        RoleMenuFuncIdsQueryRequest request = new RoleMenuFuncIdsQueryRequest();
        request.setRoleInfoId(roleInfoId);

        return roleMenuQueryProvider.queryRoleMenuFuncIds(request);
    }

    /**
     * 编辑角色的菜单与权限信息
     *
     * @param roleMenuFuncRequest 该角色的菜单与权限信息
     */
    @Operation(summary = "编辑角色的菜单与权限信息")
    @RequestMapping(method = RequestMethod.PUT)
    public BaseResponse edit(@RequestBody RoleMenuAuthSaveRequest roleMenuFuncRequest) {
        RoleInfoQueryRequest queryRequest = new RoleInfoQueryRequest();
        queryRequest.setRoleInfoId(roleMenuFuncRequest.getRoleInfoId());
        RoleInfoQueryResponse queryResponse = roleInfoQueryProvider.getRoleInfoById(queryRequest).getContext();
        commonUtil.checkCompanyInfoId(queryResponse.getRoleInfoVO().getCompanyInfoId());
        roleMenuProvider.saveRoleMenuAuth(roleMenuFuncRequest);

        //操作日志记录
        operateLogMQUtil.convertAndSend("设置", "编辑角色权限",
                "编辑角色权限：" + (Objects.nonNull(queryResponse.getRoleInfoVO().getRoleName()) ? queryResponse.getRoleInfoVO().getRoleName() : ""));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 查询登录用户的所有的菜单信息
     */
    @Operation(summary = "查询登录用户的所有的菜单信息")
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public BaseResponse<List<MenuInfoVO>> getLoginMenus() {
        Operator operator = commonUtil.getOperator();

        EmployeeByIdResponse employee = employeeQueryProvider.getById(
                EmployeeByIdRequest.builder().employeeId(operator.getUserId()).build()
        ).getContext();

        List<MenuInfoVO> menuInfoResponseList = new ArrayList<>();
        if (employee != null) {
            // system账号特权 以及 店铺主账号查询对应平台的所有权限
            if (SystemAccount.SYSTEM.getDesc().equals(employee.getAccountName()) || Objects.equals(DefaultFlag.YES.toValue(), employee.getIsMasterAccount())) {
                AllRoleMenuInfoListRequest request = new AllRoleMenuInfoListRequest();
                request.setSystemTypeCd(operator.getPlatform());
                menuInfoResponseList =
                        roleMenuQueryProvider.listAllRoleMenuInfo(request).getContext().getMenuInfoVOList();
            } else if (StringUtils.isBlank(employee.getRoleIds())) {
                // 若没有角色,提示无权限联系管理员
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010091, new Object[]{"，请联系您的管理员"});
            } else {
                List<Long> ids = Arrays.asList(employee.getRoleIds().split(",")).stream()
                        .mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
                //过滤不存在的角色
                ids = this.filterRoleId(ids);
                if(CollectionUtils.isNotEmpty(ids)){
                    RoleMenuInfoListRequest request = new RoleMenuInfoListRequest();
                    request.setRoleInfoId(ids);
                    menuInfoResponseList = roleMenuQueryProvider.listRoleMenuInfo(request).getContext().getMenuInfoVOList();
                }
            }
            // 若没有任何菜单权限,请联系管理员
            if (CollectionUtils.isEmpty(menuInfoResponseList)) {
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010091, new Object[]{"，请联系您的管理员"});
            }

            final CopyOnWriteArrayList<MenuInfoVO> cowList = new CopyOnWriteArrayList<MenuInfoVO>(menuInfoResponseList);

            //商家端不展示菜单
            if (Platform.SUPPLIER.equals(operator.getPlatform())) {
                //查询商家类型
                StoreVO storeVO =
                        storeQueryProvider.getById(StoreByIdRequest.builder().storeId(Long.parseLong(operator.getStoreId())).build())
                                .getContext().getStoreVO();
                BoolFlag companyType = storeVO.getCompanyType();

                // 第三方商家，排除菜单：客服设置-在线客服
//                if (BoolFlag.YES == companyType) {
//                    this.removeMenusByNames(cowList, Lists.newArrayList("客服设置", "在线客服"));
//                }

                if (StoreType.CROSS_BORDER.equals(storeVO.getStoreType())) {
                    List<String> removeMenus =
                            Lists.newArrayList("第二件半价", "预约购买", "预售活动", "企业购","企业购商品", "拼团活动","代客下单");
                    for (MenuInfoVO item : cowList) {
                        if (removeMenus.contains(item.getTitle())) {
                            cowList.remove(item);
                        }
                    }
                } else {
                    List<String> removeMenus =
                            Lists.newArrayList("跨境商品管理","跨境订单管理");
                    for (MenuInfoVO item : cowList) {
                        if (removeMenus.contains(item.getTitle())) {
                            cowList.remove(item);
                        }
                    }
                }

                //不是京东vop的商家，不显示类目映射
                if (storeVO.getCompanySourceType() != CompanySourceType.JD_VOP) {
                    List<String> removeMenus =
                            Lists.newArrayList("类目映射");
                    for (MenuInfoVO item : cowList) {
                        if (removeMenus.contains(item.getTitle())) {
                            cowList.remove(item);
                        }
                    }
                }
            }
            //过滤供应商菜单
            if (Platform.PROVIDER.equals(operator.getPlatform())) {
                //查询商家类型
                StoreVO storeVO =
                        storeQueryProvider.getById(StoreByIdRequest.builder().storeId(Long.parseLong(operator.getStoreId())).build())
                                .getContext().getStoreVO();
                if (Objects.nonNull(storeVO.getCompanySourceType()) && CompanySourceType.JD_VOP.equals(storeVO.getCompanySourceType())) {
                    List<String> removeMenus =
                            Lists.newArrayList("发布商品", "批量发货", "物流设置","物流公司设置","运费模板","价格管理","批量调价","调价记录","订单处理","批量发货","待审核商品");
                    for (MenuInfoVO item : cowList) {
                        if (removeMenus.contains(item.getTitle())) {
                            cowList.remove(item);
                        }
                    }
                }
            }
            //拉卡拉开关未开启，过滤相关菜单
            if (!ledgerAccountBaseService.getGatewayOpen()) {
                for (MenuInfoVO item : cowList) {
                    if (ledgerMenus.contains(item.getTitle())) {
                        cowList.remove(item);
                    }
                }
            }
            return BaseResponse.success(cowList);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 查询登录用户拥有的功能
     */
    @Operation(summary = "查询登录用户拥有的功能，拥有权限的功能")
    @RequestMapping(value = "/functions", method = RequestMethod.GET)
    public BaseResponse<List<String>> getLoginFunctions() {
        Operator operator = commonUtil.getOperator();
        String roleId = employeeQueryProvider.getRoleIdByEmployeeId(
                RoleIdByEmployeeIdRequest.builder().employeeId(operator.getUserId()).build()
        ).getContext().getRoleId();
        List<String> functionList = new ArrayList<>();
        if (roleId != null) {
            Set<String> set = new HashSet<>();
            List<Long> ids;
            if(!SystemAccount.SYSTEM.getDesc().equals(roleId)){
                 ids = Arrays.asList(roleId.split(",")).stream()
                        .mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
                //过滤不存在的角色
                ids = this.filterRoleId(ids);
                ids.stream().forEach(id -> {
                    FunctionListByRoleIdRequest request = new FunctionListByRoleIdRequest();
                    request.setRoleId(id.toString());
                    request.setSystemTypeCd(operator.getPlatform());
                    List<String> list = roleMenuQueryProvider.listFunctionsByRoleId(request).getContext().getFunctionList();
                    if(CollectionUtils.isNotEmpty(list)){
                        set.addAll(list);
                    }
                });
                functionList.addAll(set);
            }else{
                FunctionListByRoleIdRequest request = new FunctionListByRoleIdRequest();
                request.setRoleId(roleId);
                request.setSystemTypeCd(operator.getPlatform());
                functionList = roleMenuQueryProvider.listFunctionsByRoleId(request).getContext().getFunctionList();
            }


            if(CollectionUtils.isEmpty(functionList)){
                throw new SbcRuntimeException(CustomerErrorCodeEnum.K010091, new Object[]{"，请联系您的管理员"});
            }
            return BaseResponse.success(functionList);
        } else {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000001);
        }
    }

    /**
     * 过滤roleId
     * @return
     */
    private List<Long> filterRoleId(List<Long> ids){
        RoleInfoListRequest roleInfoListRequest = new RoleInfoListRequest();
        Platform platform = commonUtil.getOperator().getPlatform();
        roleInfoListRequest.setCompanyInfoId(platform == Platform.STOREFRONT ? O2O_COMPANYINFOID : commonUtil.getCompanyInfoId());
        List<RoleInfoVO> roleInfoVOList = roleInfoQueryProvider.listByCompanyInfoId(roleInfoListRequest).getContext().getRoleInfoVOList();
        if(CollectionUtils.isNotEmpty(roleInfoVOList)){
            List<Long> roleInfoIds = roleInfoVOList.stream().map(RoleInfoVO::getRoleInfoId).collect(Collectors.toList());
            //过滤不存在的角色
            ids = ids.stream().filter(id -> roleInfoIds.contains(id)).collect(Collectors.toList());
        }
        return ids;
    }


    /**
     * 移除指定名称的菜单
     * @param originalMenus 原始菜单列表
     * @param removeMenuNames 需要移除的菜单名称列表
     */
    private void removeMenusByNames(List<MenuInfoVO> originalMenus, List<String> removeMenuNames) {
        if (CollectionUtils.isNotEmpty(originalMenus)) {
            originalMenus.removeIf(item -> removeMenuNames.contains(item.getTitle()));
        }
    }

}
