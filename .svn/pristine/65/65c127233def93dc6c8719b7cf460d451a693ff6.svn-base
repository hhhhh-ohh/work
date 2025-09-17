package com.wanmi.sbc.customer.api.response.employee;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.customer.bean.enums.AccountState;
import com.wanmi.sbc.customer.bean.enums.CheckState;
import com.wanmi.sbc.customer.bean.enums.StoreState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author yangzhen
 * @Description //公司店铺信息统一返回
 * @Date 10:34 2020/12/8
 * @Param
 * @return
 **/
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreInformationResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String supplierName;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private String companyCode;

    /**
     * 商家账号
     */
    @Schema(description = "商家账号")
    private String accountName;

    /**
     * 商家类型 0、平台自营 1、第三方商家
     */
    @Schema(description = "商家类型(0、平台自营 1、第三方商家)")
    private Integer companyType;

    /**
     * 审核状态 0、待审核 1、已审核 2、审核未通过
     */
    @Schema(description = "审核状态")
    private CheckState auditState;

    /**
     * 审核未通过原因
     */
    @Schema(description = "审核未通过原因")
    private String auditReason;

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
     * 店铺状态 0、开启 1、关店
     */
    @Schema(description = "店铺状态")
    private StoreState storeState;

    /**
     * 账号关闭原因
     */
    @Schema(description = "账号关闭原因")
    private String storeClosedReason;

    /**
     * 商家类型0品牌商城，1商家
     */
    @Schema(description = "商家类型0品牌商城，1商家")
    private StoreType storeType;

    /**
     * 是否是主账号
     */
    @Schema(description = "店铺名称")
    private Integer isMasterAccount;

    /**
     * 店铺删除状态
     */
    @Schema(description = "店铺删除状态")
    private DeleteFlag storeDelFlag;

    /**
     * 公司删除状态
     */
    @Schema(description = "公司删除状态")
    private DeleteFlag companyInfoDelFlag;

    /**
     * 员工删除状态
     */
    @Schema(description = "员工删除状态")
    private DeleteFlag employeeDelFlag;


    /**
     * 是否确认打款 (-1:全部,0:否,1:是)
     */
    @Schema(description = "是否确认打款(-1:全部,0:否,1:是)")
    private Integer remitAffirm;


    /**
     * 二次签约审核状态
     */
    @Schema(description = "二次签约审核状态")
    private CheckState contractAuditState;

    /**
     * 二次签约拒绝原因
     */
    @Schema(description = "二次签约拒绝原因")
    private String contractAuditReason;
}
