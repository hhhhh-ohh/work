package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.account.api.request.validgroup.AddGroup;
import com.wanmi.sbc.account.api.request.validgroup.EditGroup;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

/**
 * 授信申请参数
 *
 * @author zhegnyang
 * @since 2021-03-02 10:22:24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplyRequest extends BaseRequest {

    /**
     * 申请原因
     */
    @NotNull(groups = {AddGroup.class, EditGroup.class})
    @Length(min = 1, max = 500, groups = {AddGroup.class})
    @Schema(description = "申请原因")
    private String applyNotes;

    /**
     * 申请用户ID
     */
    @NotNull(groups = {AddGroup.class})
    private String customerId;

    /**
     * 客户账号
     */
    @NotNull(groups = {AddGroup.class})
    private String customerAccount;

    /**
     * 客户名称
     */
    private String customerName;

    /***
     * 是否变更额度申请
     */
    private BoolFlag isChangeFlag;
}
