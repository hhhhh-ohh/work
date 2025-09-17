package com.wanmi.sbc.goods.api.request.thirdgoodscate;

import com.wanmi.sbc.common.enums.AuditStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WechatCateCallbackRequest implements Serializable {

    /**
     * 微信审核id
     */
    @NotBlank
    private String auditId;

    @NotNull
    private AuditStatus auditStatus;

    private String rejectReason;
}
