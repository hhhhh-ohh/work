package com.wanmi.sbc.empower.api.request.sms.aliyun;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <p>短信签名文件信息参数</p>
 *
 * @author lvzhenwei
 * @date 2019-12-04 14:19:35
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmsSignAliyunFileRequest extends EmpowerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 文件路径
     */
    @Schema(description = "文件路径")
    @Length(max = 255)
    @NotBlank
    private String fileUrl;

    /**
     * 文件名称
     */
    @Schema(description = "文件名称")
    @Length(max = 255)
    @NotBlank
    private String fileName;

}