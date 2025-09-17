package com.wanmi.sbc.customer.api.request.company;

import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.DateUtil;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: songhanlin
 * @Date: Created In 上午10:05 2017/11/1
 * @Description: 工商信息Request
 */
@Schema
@Data
public class CompanyInformationSaveRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @Schema(description = "编号")
    @NotNull
    private Long companyInfoId;

    /**
     * 企业名称
     */
    @Schema(description = "企业名称")
    @NotBlank
    @Length(min = 1, max = 50)
    private String companyName;

    /**
     * 社会信用代码
     */
    @Schema(description = "社会信用代码")
    @NotBlank
    @Length(min = 8, max = 30)
    private String socialCreditCode;

    /**
     * 住所
     */
    @Schema(description = "住所")
    @CanEmpty
    @Length(max = 60)
    private String address;

    /**
     * 法定代表人
     */
    @Schema(description = "法定代表人")
    @CanEmpty
    @Length(max = 10)
    private String legalRepresentative;

    /**
     * 注册资本
     */
    @Schema(description = "注册资本")
    @CanEmpty
    private BigDecimal registeredCapital = BigDecimal.ZERO;

    /**
     * 成立日期
     */
    @Schema(description = "成立日期")
    @CanEmpty
    private String foundDate;

    /**i
     * 营业期限自
     */
    @Schema(description = "营业期限自")
    @CanEmpty
    private String businessTermStart;

    /**
     * 营业期限至
     */
    @Schema(description = "营业期限至")
    @NotBlank
    private String businessTermEnd;

    /**
     * 经营范围
     */
    @Schema(description = "经营范围")
    @NotBlank
    @Length(min = 1, max = 500)
    private String businessScope;

    /**
     * 营业执照副本电子版
     */
    @Schema(description = "营业执照副本电子版")
    @NotBlank
    private String businessLicence;

    /**
     * 法人身份证正面
     */
    @Schema(description = "法人身份证正面")
    @CanEmpty
    private String frontIDCard;

    /**
     * 法人身份证反面
     */
    @Schema(description = "法人身份证反面")
    @CanEmpty
    private String backIDCard;

    @Override
    public void checkParam() {
        boolean flag = false;
        // 营业执照必填, 身份证选填
        if (StringUtils.split(businessLicence, ",").length != 1) {
            flag = true;
        } else if (StringUtils.isNotBlank(frontIDCard) && StringUtils.split(frontIDCard, ",").length > 1) {
            flag = true;
        } else if (StringUtils.isNotBlank(backIDCard) && StringUtils.split(backIDCard, ",").length > 1) {
            flag = true;
        }
        //营销期限校验
        String entTime = businessTermEnd.length() == 10 ? businessTermEnd+" 00:00:00.000" : businessTermEnd;
        LocalDateTime dateTime = DateUtil.parse(entTime, DateUtil.FMT_TIME_4);
        if (dateTime.isBefore(LocalDate.now().plusDays(Constants.ONE).atStartOfDay())) {
            flag = true;
        }
        if (flag) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
