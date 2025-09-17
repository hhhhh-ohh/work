package com.wanmi.sbc.customer.api.request.ledgeraccount;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.ValidateUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

/**
 * @author xuyunpeng
 * @className LedgerVerifyContractInfoRequest
 * @description
 * @date 2022/7/7 3:22 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LedgerVerifyContractInfoRequest extends BaseRequest {
    private static final long serialVersionUID = 6083698638984270873L;

    /**
     * 营业执照名称
     */
    @Schema(description = "营业执照名称")
    @Length(min = 7, max = 50)
    @NotBlank
    private String merBlisName;

    /**
     * 营业执照号
     */
    @Schema(description = "营业执照号")
    @Length(max = 50)
    @NotBlank
    private String merBlis;

    /**
     * 法人身份证号
     */
    @Schema(description = "法人身份证号")
    private String larIdCard;

    /**
     * 结算账号
     */
    @Schema(description = "结算账号")
    @Length(max = 60)
    @NotBlank
    private String acctNo;

    @Override
    public void checkParam() {
        if (StringUtils.isBlank(larIdCard) || larIdCard.length() != Constants.NUM_18 || !ValidateUtil.isNumAndEng(larIdCard)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
