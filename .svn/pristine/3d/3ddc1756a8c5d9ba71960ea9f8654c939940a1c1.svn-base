package com.wanmi.sbc.empower.api.request.sms.aliyun;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>短信签名新增参数</p>
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
public class SmsSignAliyunAddRequest extends EmpowerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 短信配置id
     */
    @Schema(description = "短信配置id")
    private Long smsSettingId;

    /**
     * 签名名称
     */
    @Schema(description = "签名名称")
    @NotBlank
    private String signName;

    /**
     * 签名来源,0：企事业单位的全称或简称,1：工信部备案网站的全称或简称,2：APP应用的全称或简称,3：公众号或小程序的全称或简称,4：电商平台店铺名的全称或简称,5：商标名的全称或简称
     */
    @Schema(description = "签名来源,0：企事业单位的全称或简称,1：工信部备案网站的全称或简称,2：APP应用的全称或简称,3：公众号或小程序的全称或简称,4：电商平台店铺名的全称或简称,5：商标名的全称或简称")
    @NotNull
    private Integer signSource;

    /**
     * 短信签名申请说明
     */
    @Schema(description = "短信签名申请说明")
    @NotBlank
    private String remark;

    /**
     * 短信签名文件信息
     */
    @Schema(description = "短信签名文件信息")
    @NotEmpty
    private List<SmsSignAliyunFileRequest> signFilList;

}