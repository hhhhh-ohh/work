package com.wanmi.sbc.customer.api.request.employee;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.customer.bean.enums.AccountState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@Schema
@Data
public class EmployeePageRequest extends BaseQueryRequest {


    private static final long serialVersionUID = 1L;

    @Schema(description = "员工id")
    private String employeeId;

    /**
     * 员工姓名
     */
    @Schema(description = "员工姓名")
    private String userName;

    /**
     * 用户手机
     */
    @Schema(description = "用户手机")
    private String userPhone;

    /**
     * 账户名称
     */
    @Schema(description = "账户名称")
    private String accountName;

    /**
     * 是否是业务员
     */
    @Schema(description = "是否是业务员(0 是 1否)")
    private Integer isEmployee;

    /**
     * 账户状态
     */
    @Schema(description = "账户状态")
    private AccountState accountState;

    /**
     * 角色id
     */
    @Schema(description = "角色列表")
    private List<String> roleIds;

    @Schema(description = "员工编号列表")
    private List<String> employeeIds;

    /**
     * 账号类型 0 b2b账号 1 s2b平台端账号 2 s2b商家端账号
     */
    @Schema(description = "账号类型")
    private AccountType accountType;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long companyInfoId;

    /**
     * 员工禁用原因
     */
    @Schema(description = "员工禁用原因")
    private String accountDisableReason;

    /**
     * 是否主账号
     */
    @Schema(description = "是否主账号")
    private Integer isMasterAccount;

    /**
     * 工号
     */
    @Schema(description = "工号")
    private String jobNo;

    /**
     * 是否主管，0：否，1：是
     */
    @Schema(description = "是否主管，0：否，1：是")
    private Integer isLeader;

    /**
     * 是否激活会员账号，0：否，1：是
     */
    @Schema(description = "是否激活会员账号，0：否，1：是")
    private Integer becomeMember;

    /**
     * 部门id列表
     */
    @Schema(description = "部门列表")
    private List<String> departmentIds;



    /**
     * 部门id列表(数据隔离)
     */
    @Schema(description = "部门列表")
    private List<String> departmentIsolationIdList;

    /**
     * 管理部门id列表(数据隔离)
     */
    @Schema(description = "管理部门列表")
    private List<String> manageDepartmentIdList;

    /**
     * 所属部门id列表(数据隔离)
     */
    @Schema(description = "所属部门列表")
    private List<String> belongToDepartmentIdList;

    /**
     * 业务员名称（仅限业务员交接使用）
     */
    @Schema(description = "业务员名称（仅限业务员交接使用）")
    private String employeeName;

    /**
     * 仅限业务员交接使用
     */
    @Schema(description = "是否查询业务员")
    private Boolean isEmployeeSearch;

    /**
     * 关键词
     */
    @Schema(description = "关键词")
    private String keywords;

    /**
     * 是否隐藏离职员工
     */
    @Schema(description = "是否隐藏离职员工 0: 否  1：是")
    private Integer isHiddenDimission;

    /**
     * 是否有归属部门或管理部门
     */
    @Schema(description = "是否有归属部门或管理部门")
    private Boolean belongToDepartment;

    /**
     * 是否是主账号
     */
    @Schema(description = "是否是主账号")
    private Integer isMaster;

    /**
     * 管理部门集合
     */
    @Schema(description = "管理部门集合")
    private String manageDepartmentIds;

    /**
     * 批量员工id
     */
    @Schema(description = "批量员工id")
    private List<String> idList;

    public List<String> getDepartmentIds() {

        if (CollectionUtils.isNotEmpty(departmentIds) && CollectionUtils.isNotEmpty(departmentIsolationIdList)) {
            departmentIds.retainAll(departmentIsolationIdList);
            return departmentIds;
        }
        if (CollectionUtils.isNotEmpty(departmentIsolationIdList)){
            return departmentIsolationIdList;
        }
        return departmentIds;
    }
}
