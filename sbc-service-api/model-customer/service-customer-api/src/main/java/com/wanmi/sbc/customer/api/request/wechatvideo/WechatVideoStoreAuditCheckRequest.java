package com.wanmi.sbc.customer.api.request.wechatvideo;

import static com.wanmi.sbc.common.util.ValidateUtil.NULL_EX_MESSAGE;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.enums.CheckAction;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * @author zhaiqiankun
 * @className WechatVideoStoreAuditCheckRequest
 * @description 店铺审核
 * @date 2022/4/12 19:49
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoStoreAuditCheckRequest extends BaseRequest {
    private static final long serialVersionUID = -60118401751014926L;
    /**
     * id
     */
    @Schema(description = "id")
    @Max(9999999999L)
    @NotNull
    private Long storeId;


    /**
     * 申请开通行为，1：审核，2：驳回，3：禁用
     */
    @Schema(description = "申请开通行为，1：审核，2：驳回，3：禁用")
    @NotNull
    private CheckAction action;

    /**
     * 审核不通过原因
     */
    @Schema(description = "审核不通过原因")
    private String auditReason;

    @Override
    public void checkParam() {
        if (action != CheckAction.CHECKED && StringUtils.isBlank(auditReason)) {
            Validate.notNull(auditReason, NULL_EX_MESSAGE, "auditReason");
        }
    }
}
