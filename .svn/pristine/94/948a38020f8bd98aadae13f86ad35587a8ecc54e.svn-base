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
 * @className ReceiverAddRequest
 * @description
 * @date 2022/7/4 9:58 AM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverSaveRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 营业执照名称
     */
    @Schema(description = "营业执照名称")
    @Length(min = 7, max=50)
    @NotBlank
    private String merBlisName;

    /**
     * 营业执照号
     */
    @Schema(description = "营业执照号")
    @Length(max=32)
    @NotBlank
    private String merBlis;

    /**
     * 法人姓名
     */
    @Schema(description = "法人姓名")
    @Length(max=20)
    @NotBlank
    private String larName;

    /**
     * 法人身份证号
     */
    @Schema(description = "法人身份证号")
    private String larIdCard;

    /**
     * 联系人
     */
    @Schema(description = "联系人")
    @Length(max=32)
    private String merContactMobile;


    /**
     * 账户名称
     */
    @Schema(description = "账户名称")
    @Length(max=32)
    @NotBlank
    private String acctName;

    /**
     * 账户卡号
     */
    @Schema(description = "账户卡号")
    @Length(max=32)
    @NotBlank
    private String acctNo;

    /**
     * 账户证件号
     */
    @Schema(description = "账户证件号")
    private String acctCertificateNo;

    /**
     * 账户开户行号
     */
    @Schema(description = "账户开户行号")
    @Length(max=20)
    @NotBlank
    private String openningBankCode;

    /**
     * 账户开户行名称
     */
    @Schema(description = "账户开户行名称")
    @Length(max=40)
    @NotBlank
    private String openningBankName;

    /**
     * 法人身份证正面
     */
    @Schema(description = "法人身份证正面")
    @NotBlank
    private String idCardFrontPic;

    /**
     * 法人身份证背面
     */
    @Schema(description = "法人身份证背面")
    @NotBlank
    private String idCardBackPic;

    /**
     * 银行卡
     */
    @Schema(description = "银行卡")
    @NotBlank
    private String bankCardPic;

    /**
     * 营业执照
     */
    @Schema(description = "营业执照")
    @NotBlank
    private String businessPic;

    @Override
    public void checkParam() {

        if (StringUtils.isBlank(larIdCard) || larIdCard.length() != Constants.NUM_18 || !ValidateUtil.isNumAndEng(larIdCard)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (StringUtils.isBlank(acctCertificateNo) || acctCertificateNo.length() != Constants.NUM_18) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
