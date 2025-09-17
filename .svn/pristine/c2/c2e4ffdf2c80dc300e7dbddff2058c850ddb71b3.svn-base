package com.wanmi.sbc.account.api.request.company;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商家收款账户保存请求
 * Created by CHENLI on 2017/4/27.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAccountAddRequest extends BaseRequest {

    private static final long serialVersionUID = 5000031127526375844L;
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
