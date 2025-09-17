package com.wanmi.sbc.account.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商家收款账户参数
 * Created by CHENLI on 2017/4/27.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountSaveDTO implements Serializable {

    private static final long serialVersionUID = 6343904265848423465L;
    /**
     * 账户id
     */
    @Schema(description = "账户id")
    private Long accountId;

    /**
     * 账户名称
     */
    @Schema(description = "账户名称")
    private String accountName;

    /**
     * 开户银行
     */
    @Schema(description = "开户银行")
    private String bankName;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String bankNo;

    /**
     * 银行账号编码
     */
    @Schema(description = "银行账号编码")
    private String bankCode;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 支行信息
     */
    @Schema(description = "支行信息")
    private String bankBranch;

    /**
     * 打款金额
     */
    @Schema(description = "打款金额")
    private BigDecimal remitPrice;

}
