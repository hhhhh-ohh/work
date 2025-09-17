package com.wanmi.sbc.customer.api.request.ledgeraccount;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.*;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/**
 * @author xuyunpeng
 * @className MerchantsAddRequest
 * @description
 * @date 2022/7/4 10:00 AM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantsSaveRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 营业执照名称
     */
    @Schema(description = "营业执照名称")
    @NotBlank
    @Length(min = 7, max = 50)
    private String merBlisName;

    /**
     * 商户mcc编码
     */
    @Schema(description = "商户mcc编码")
    @NotBlank
    private String mccCode;

    /**
     * 注册地址
     */
    @Schema(description = "注册地址")
    @NotBlank
    private String merRegDistCode;

    /**
     * 详情地址
     */
    @Schema(description = "详情地址")
    @NotBlank
    @Length(min = 6, max = 200)
    private String merRegAddr;

    /**
     * 营业执照号
     */
    @Schema(description = "营业执照号")
    @NotBlank
    @Length(max = 32)
    private String merBlis;

    /**
     * 营业执照开始时间
     */
    @Schema(description = "营业执照开始时间")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @NotNull
    private LocalDate merBlisStDt;

    /**
     * 营业执照有效期结束时间
     */
    @Schema(description = "营业执照有效期结束时间")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @NotNull
    private LocalDate merBlisExpDt;

    /**
     * 商户经营内容
     */
    @Schema(description = "商户经营内容")
    @NotBlank
    private String merBusiContent;

    /**
     * 法人姓名
     */
    @Schema(description = "法人姓名")
    @NotBlank
    @Length(max = 20)
    private String larName;

    /**
     * 法人身份证号
     */
    @Schema(description = "法人身份证号")
    private String larIdCard;

    /**
     * 身份证开始日期
     */
    @Schema(description = "身份证开始日期")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @NotNull
    private LocalDate larIdCardStDt;

    /**
     * 身份证有效期结束时间
     */
    @Schema(description = "身份证有效期结束时间")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @NotNull
    private LocalDate larIdCardExpDt;

    /**
     * 商户联系人
     */
    @Schema(description = "商户联系人")
    @NotBlank
    @Length(max = 32)
    private String merContactName;

    /**
     * 商户联系人手机号
     */
    @Schema(description = "商户联系人手机号")
    private String merContactMobile;

    /**
     * 账户名称
     */
    @Schema(description = "结算账户名称")
    @NotBlank
    @Length(max = 32)
    private String acctName;

    /**
     * 账户账户
     */
    @Schema(description = "结算账户账户")
    @NotBlank
    @Length(max = 32)
    private String acctNo;

    /**
     * 账户开户行号
     */
    @Schema(description = "结算账户开户行号")
    @NotBlank
    @Length(max = 20)
    private String openningBankCode;

    /**
     * 账户开户行名称
     */
    @Schema(description = "结算账户开户行名称")
    @NotBlank
    @Length(max = 40)
    private String openningBankName;

    /**
     * 账户清算行号
     */
    @Schema(description = "账户清算行号")
    @NotBlank
    @Length(max = 20)
    private String clearingBankCode;

    @Schema(description = "邮箱")
    private String email;

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

    /**
     * 商户门头照
     */
    @Schema(description = "商户门头照")
    @NotBlank
    private String merchantPic;

    /**
     * 商户内部照
     */
    @Schema(description = "商户内部照")
    @NotBlank
    private String shopinnerPic;

    @Schema(description = "注册地址省编号")
    @NotBlank
    private String merRegDistProvinceCode;

    @Schema(description = "注册地址市编号")
    @NotBlank
    private String merRegDistCityCode;

    @Override
    public void checkParam() {
        if (StringUtils.isBlank(larIdCard) || larIdCard.length() != Constants.NUM_18 || !ValidateUtil.isNumAndEng(larIdCard)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (StringUtils.isBlank(merContactMobile) || !ValidateUtil.isPhone(merContactMobile)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (StringUtils.isBlank(email) || !ValidateUtil.isEmail(email)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
