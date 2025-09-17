package com.wanmi.sbc.customer.api.request.distribution;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.CustomerType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Length;

/**
 * <p>分销员新增参数（运营端）</p>
 *
 * @author lq
 * @date 2019-02-19 10:13:15
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class DistributionCustomerAddForBossRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 会员登录账号|手机号
     */
    @Schema(description = "会员登录账号|手机号")
    @NotBlank
    @Length(max = 20)
    private String customerAccount;

    /**
     * 负责业务员
     */
    @Schema(description = "负责业务员")
    private String employeeId;


    /**
     * 客户类型 0:平台客户,1:商家客户
     */
    @Schema(description = "客户类型")
    private CustomerType customerType;

    /**
     * 分销员等级ID
     */
    @Schema(description = "分销员等级ID")
    private String distributorLevelId;

}