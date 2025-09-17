package com.wanmi.sbc.elastic.employee.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.*;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.GenderType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document(indexName = EsConstants.DOC_EMPLOYEE_TYPE)
@Data
public class EsEmployee implements Serializable {
    /**
     * 主键
     */
    @Id
    private String employeeId;

    /**
     * 会员名称
     */
    private String employeeName;

    /**
     * 会员电话
     */
    private String employeeMobile;

    /**
     * 角色id
     */
    private List<String> roleIds;

    /**
     * 0 是 1否
     */
    private Integer isEmployee;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * 密码
     */
    private String accountPassword;

    /**
     * salt
     */
    private String employeeSaltVal;

    /**
     * 账号状态
     */
    private AccountState accountState;

    /**
     * 账号禁用原因
     */
    private String accountDisableReason;

    /**
     * 第三方店铺id
     */
    private String thirdId;

    /**
     * 会员id
     */
    private String customerId;

    /**
     * 删除标志
     */
    private DeleteFlag delFlag = DeleteFlag.NO;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createPerson;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    private String updatePerson;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deleteTime;

    private String deletePerson;

    /**
     * 登录失败次数
     */
    private Integer loginErrorTime = 0;

    /**
     * 锁定时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime loginLockTime;

    /**
     * 会有登录时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime loginTime;

    /**
     * 商家Id
     */
    private Long companyInfoId;

    /**
     * 是否是主账号
     */
    private Integer isMasterAccount;

    /**
     * 账号类型 0 b2b账号 1 s2b平台端账号 2 s2b商家端账号 3 s2b品牌商端账号
     */
    private AccountType accountType;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 工号
     */
    private String jobNo;

    /**
     * 职位
     */
    private String position;

    /**
     * 生日
     */
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    private LocalDate birthday;

    /**
     * 性别，0：保密，1：男，2：女
     */
    private GenderType sex;

    /**
     * 是否激活会员账号，0：否，1：是
     */
    private Integer becomeMember;

    /**
     * 交接人员工ID
     */
    private String heirEmployeeId;

    /**
     * 所属部门集合
     */
    private List<String> departmentIds;

    /**
     * 管理部门集合
     */
    private List<String> manageDepartmentIds;

    /**
     * 是否是主管
     */
    private Integer isLeader;
}
