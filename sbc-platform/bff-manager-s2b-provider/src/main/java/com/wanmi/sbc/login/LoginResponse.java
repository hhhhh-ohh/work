package com.wanmi.sbc.login;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CompanySourceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录返回
 * Created by aqlu on 15/11/28.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse extends BasicResponse {

    /**
     * jwt验证token
     */
    @Schema(description = "jwt验证token")
    private String token;

    /**
     * 账号名称
     */
    @Schema(description = "账号名称")
    private String accountName;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 审核状态 0、待审核 1、已审核 2、审核未通过
     */
    @Schema(description = "审核状态", contentSchema = com.wanmi.sbc.order.bean.enums.AuditState.class)
    private Integer auditState;

    /**
     * 是否主账号
     */
    @Schema(description = "是否主账号", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer isMasterAccount;

    /**
     * 是否业务员
     */
    @Schema(description = "是否业务员", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer isEmployee;

    /**
     * 业务员id
     */
    @Schema(description = "业务员id")
    private String employeeId;

    /**
     * 业务员名称
     */
    @Schema(description = "业务员名称")
    private String employeeName;

    /**
     * 供应商类型 0、平台自营 1、第三方供应商
     */
    @Schema(description = "供应商类型(0、平台自营 1、第三方供应商)")
    private BoolFlag companyType;
    
    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String supplierName;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 店铺logo
     */
    @Schema(description = "店铺logo")
    private String storeLogo;

    /**
     *  商家来源类型 0:商城入驻 1:linkMall初始化  2: vop
     */
    @Schema(description = "商家来源类型 0:商城入驻 1:linkMall初始化")
    private CompanySourceType companySourceType;

}
