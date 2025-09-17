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
 * @className DistributionReceiverSaveRequest
 * @description
 * @date 2022/7/8 1:44 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionReceiverSaveRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @Length(max=20)
    @NotBlank
    private String larName;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    private String larIdCard;

    /**
     * 联系电话
     */
    @Schema(description = "联系电话")
    private String merContactMobile;

    /**
     * 银行卡号
     */
    @Schema(description = "银行卡号")
    @Length(max=32)
    @NotBlank
    private String acctNo;

    /**
     * 账户开户行名称
     */
    @Schema(description = "账户开户行名称")
    @Length(max=40)
    @NotBlank
    private String openningBankName;

    /**
     * 身份证正面
     */
    @Schema(description = "身份证正面")
    @NotBlank
    private String idCardFrontPic;

    /**
     * 身份证背面
     */
    @Schema(description = "身份证背面")
    @NotBlank
    private String idCardBackPic;

    /**
     * 银行卡
     */
    @Schema(description = "银行卡")
    @NotBlank
    private String bankCardPic;

    /**
     * 是否同意签署协议
     */
    @Schema(description = "是否同意签署协议 true同意")
    private Boolean acceptFlag;

    @Override
    public void checkParam() {
        if (!Boolean.TRUE.equals(acceptFlag)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (StringUtils.isBlank(larIdCard) || larIdCard.length() != Constants.NUM_18 || !ValidateUtil.isNumAndEng(larIdCard)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
