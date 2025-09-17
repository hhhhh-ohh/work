package com.wanmi.sbc.account.api.request.credit;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author huangzhao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckCreditRepayRequest extends BaseRequest implements Serializable {
    private static final long serialVersionUID = -7334018038985782116L;

    @Schema(description = "还款单号")
    @NotBlank
    private String repayOrderCode;

    @Schema(description = "审核状态 0：审核通过 1：审核驳回")
    @NotNull
    private Integer auditStatus;

    @Schema(description = "驳回理由")
    private String auditRemark;

    @Override
    public void checkParam() {
        if (Objects.equals(Constants.ONE,auditStatus) && StringUtils.isBlank(auditRemark)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
