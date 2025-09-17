package com.wanmi.sbc.setting.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.bean.enums.EmailStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Schema
@Data
@EqualsAndHashCode(callSuper = true)
public class EmailConfigModifyRequest extends SettingBaseRequest {

    /**
     * 邮箱配置Id
     */
    @Schema(description = "邮箱配置Id")
    @NotNull
    private String emailConfigId;

    /**
     * 发信人邮箱地址
     */
    @Schema(description = "发信人邮箱地址")
    @NotNull
    private String fromEmailAddress;

    /**
     * 发信人
     */
    @Schema(description = "发信人")
    @NotNull
    private String fromPerson;

    /**
     * SMTP服务器主机名
     */
    @Schema(description = "SMTP服务器主机名")
    @NotNull
    private String emailSmtpHost;

    /**
     * SMTP服务器端口号
     */
    @Schema(description = "SMTP服务器端口号")
    @NotNull
    private String emailSmtpPort;

    /**
     * SMTP服务器授权码
     */
    @Schema(description = "SMTP服务器授权码")
    @NotNull
    private String authCode;

    /**
     * 邮箱启用状态（0：未启用 1：已启用）
     */
    @Schema(description = "邮箱启用状态")
    @NotNull
    private EmailStatus status;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @Override
    public void checkParam() {
        // 邮箱发信配置禁用状态下不需要判断参数
        if (this.status == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        } else if (this.status == EmailStatus.ENABLE) {
            if (this.emailConfigId == null
                    || StringUtils.isBlank(this.fromEmailAddress)
                    || StringUtils.isBlank(this.fromPerson)
                    || StringUtils.isBlank(this.emailSmtpHost)
                    || StringUtils.isBlank(this.emailSmtpPort)
                    || StringUtils.isBlank(this.authCode)) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
    }

}
