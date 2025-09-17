package com.wanmi.sbc.message.api.request.smssignfileinfo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.message.api.request.SmsBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDateTime;

/**
 * <p>短信签名文件信息修改参数</p>
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
public class SmsSignFileInfoModifyRequest extends SmsBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    @Max(9223372036854775807L)
    @NotNull
    private Long id;

    /**
     * 短信签名id
     */
    @Schema(description = "短信签名id")
    @Max(9223372036854775807L)
    @NotNull
    private Long smsSignId;

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

}