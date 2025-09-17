package com.wanmi.sbc.customer.api.request.employee;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AccountType;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.customer.bean.enums.GenderType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Schema
@Data
public class EmployeeAddRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 员工名称
     */
    @Schema(description = "员工名称", required = true)
    @NotBlank
    private String employeeName;

    /**
     * 手机
     */
    @Schema(description = "手机", required = true)
    @NotBlank
    private String employeeMobile;

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private List<Long> roleIdList;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 账户名称
     */
    @Schema(description = "账户名称")
    private String accountName;

    /**
     * 员工账号
     */
    @Schema(description = "员工账号")
    private String accountPassword;

    /**
     * 是否是员工 0 是 1否
     */
    @Schema(description = "是否是员工(0 是 1否)", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    @NotNull
    private Integer isEmployee;

    /**
     * 是否发生短信
     */
    @Schema(description = "是否发送短信")
    private Boolean isSendPassword;

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
     * 归属部门
     */
    @Schema(description = "归属部门")
    private List<String> departmentIdList;

}
