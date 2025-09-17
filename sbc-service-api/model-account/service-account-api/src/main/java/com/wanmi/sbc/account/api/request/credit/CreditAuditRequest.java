package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.account.api.request.validgroup.AgreeGroup;
import com.wanmi.sbc.account.api.request.validgroup.RejectGroup;
import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 授信审核参数
 *
 * @author zhegnyang
 * @since 2021-03-01 10:22:24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditAuditRequest extends BaseRequest {

    /**
     * 申请ID
     */
    @NotNull(groups = {AgreeGroup.class, RejectGroup.class})
    @Schema(description = "申请ID")
    private String id;

    /**
     * 审批用户ID
     */
    @NotNull(groups = {AgreeGroup.class, RejectGroup.class})
    private String customerId;

    /**
     * 授信额度
     */
    @Schema(description = "授信额度")
    @NotNull(groups = {AgreeGroup.class})
    @Min(value = 0, groups = {AgreeGroup.class})
    @Max(value = 9999999, groups = {AgreeGroup.class})
    private BigDecimal creditAmount;

    /**
     * 生效天数
     */
    @Schema(description = "生效天数")
    @NotNull(groups = {AgreeGroup.class})
    @Min(value = 1, groups = {AgreeGroup.class})
    @Max(value = 365, groups = {AgreeGroup.class})
    private Long effectiveDays;

    /**
     * 拒绝原因
     */
    @Schema(description = "拒绝原因")
    @NotNull(groups = {RejectGroup.class})
    @Length(min = 1, max = 100, groups = {RejectGroup.class})
    private String rejectReason;
}
