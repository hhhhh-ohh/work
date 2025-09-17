package com.wanmi.sbc.account.api.request.company;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 修改商家收款账户请求
 * Created by daiyitian on 2018/10/15.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountModifyRequest extends BaseRequest {

    private static final long serialVersionUID = -5608219682492504047L;
    /**
     * 账户id
     */
    @Schema(description = "账户id")
    @NotNull
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
    @NotBlank
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
