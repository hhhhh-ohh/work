package com.wanmi.sbc.message.api.request.smssign;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.api.request.SmsBaseRequest;
import com.wanmi.sbc.message.api.request.smssignfileinfo.SmsSignFileInfoModifyRequest;
import com.wanmi.sbc.message.bean.enums.InvolveThirdInterest;
import com.wanmi.sbc.message.bean.enums.ReviewStatus;
import com.wanmi.sbc.message.bean.enums.SignSource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>短信签名修改参数</p>
 *
 * @author lvzhenwei
 * @date 2019-12-03 15:49:24
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSignModifyRequest extends SmsBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @NotNull
    private Long id;

    /**
     * 签名名称
     */
    @Schema(description = "签名名称")
    @NotBlank
    private String smsSignName;

    /**
     * 签名来源,0：企事业单位的全称或简称,1：工信部备案网站的全称或简称,2：APP应用的全称或简称,3：公众号或小程序的全称或简称,4：电商平台店铺名的全称或简称,5：商标名的全称或简称
     */
    @Schema(description = "签名来源,0：企事业单位的全称或简称,1：工信部备案网站的全称或简称,2：APP应用的全称或简称,3：公众号或小程序的全称或简称,4：电商平台店铺名的全称或简称,5：商标名的全称或简称")
    @NotNull
    private SignSource signSource;

    /**
     * 短信签名申请说明
     */
    @Schema(description = "短信签名申请说明")
    @NotBlank
    private String remark;

    /**
     * 是否涉及第三方利益：0：否，1：是
     */
    @Schema(description = "是否涉及第三方利益：0：否，1：是")
    @NotNull
    private InvolveThirdInterest involveThirdInterest;

    /**
     * 审核状态：0:待审核，1:审核通过，2:审核未通过
     */
    @Schema(description = "审核状态：0:待审核，1:审核通过，2:审核未通过")
    @NotNull
    private ReviewStatus reviewStatus;

    /**
     * 审核原因
     */
    @Schema(description = "审核原因")
    private String reviewReason;

    /**
     * 短信配置id
     */
    @Schema(description = "短信配置id")
    @NotNull
    private Long smsSettingId;

    /**
     * 删除标识，0：未删除，1：已删除
     */
    @Schema(description = "删除标识，0：未删除，1：已删除")
    @NotNull
    private DeleteFlag delFlag;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @NotNull
    private LocalDateTime createTime;

    /**
     * 短信签名文件信息
     */
    @Schema(description = "短信签名文件信息")
    private List<SmsSignFileInfoModifyRequest> smsSignFileInfoList;

}