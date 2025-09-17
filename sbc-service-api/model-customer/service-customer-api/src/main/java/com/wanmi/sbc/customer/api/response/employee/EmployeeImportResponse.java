package com.wanmi.sbc.customer.api.response.employee;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.GenderType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeImportResponse extends BasicResponse {
    @Schema(description = "业务员Id")
    private String employeeId;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String employeeName;

    /**
     * 会员电话
     */
    @Schema(description = "会员电话")
    private String employeeMobile;

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private Long roleId;

    /**
     * 角色id数组
     */
    @Schema(description = "角色id数组")
    private List<String> roleIds;

    /**
     * 0 是 1否
     */
    @Schema(description = "是否业务员(0 是 1否)")
    private Integer isEmployee;

    /**
     * 账户名
     */
    @Schema(description = "账户名")
    private String accountName;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String accountPassword;

    /**
     * salt
     */
    @Schema(description = "salt")
    private String employeeSaltVal;

    /**
     * 账号状态
     */
    @Schema(description = "账号状态")
    private AccountState accountState;

    /**
     * 账号禁用原因
     */
    @Schema(description = "账号禁用原因")
    private String accountDisableReason;

    /**
     * 第三方店铺id
     */
    @Schema(description = "第三方店铺id")
    private String thirdId;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerId;

    /**
     * 删除标志
     */
    @Schema(description = "删除标志")
    private DeleteFlag delFlag = DeleteFlag.NO;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createPerson;

    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @Schema(description = "更新人")
    private String updatePerson;

    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deleteTime;

    @Schema(description = "删除人")
    private String deletePerson;

    /**
     * 登录失败次数
     */
    @Schema(description = "登录失败次数")
    private Integer loginErrorTime = 0;

    /**
     * 锁定时间
     */
    @Schema(description = "锁定时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime loginLockTime;

    /**
     * 会有登录时间
     */
    @Schema(description = "会有登录时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime loginTime;

    /**
     * 商家Id
     */
    @Schema(description = "商家Id")
    private Long companyInfoId;

    /**
     * 是否是主账号
     */
    @Schema(description = "是否是主账号", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer isMasterAccount;

    /**
     * 账号类型 0 b2b账号 1 s2b平台端账号 2 s2b商家端账号
     */
    @Schema(description = "账号类型")
    private AccountType accountType;

    /**
     * 所属部门集合
     */
    @Schema(description = "所属部门集合")
    private List<String> departmentIds;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 工号
     */
    @Schema(description = "工号")
    private String jobNo;

    /**
     * 职位
     */
    @Schema(description = "职位")
    private String position;

    /**
     * 生日
     */
    @Schema(description = "生日")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate birthday;

    /**
     * 性别，0：保密，1：男，2：女
     */
    @Schema(description = "性别，0：保密，1：男，2：女")
    private GenderType sex;

    /**
     * 交接人员工ID
     */
    @Schema(description = "交接人员工ID")
    private String heirEmployeeId;

    /**
     * 管理部门集合
     */
    @Schema(description = "管理部门集合")
    private List<String> manageDepartmentIds;

    /**
     * 是否是主管
     */
    @Schema(description = "是否是主管")
    private Integer isLeader;


    /**
     * 是否激活会员账号，0：否，1：是
     */
    @Schema(description = "是否激活会员账号")
    private Integer becomeMember;
}
