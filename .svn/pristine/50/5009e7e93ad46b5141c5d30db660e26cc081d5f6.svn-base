package com.wanmi.sbc.setting.api.response;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.enums.EmailStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户财务邮箱response
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailConfigQueryResponse extends BasicResponse {
    /**
     * 邮箱配置Id
     */
    @Schema(description = "邮箱配置Id")
    private String emailConfigId;

    /**
     * 发信人邮箱地址
     */
    @Schema(description = "发信人邮箱地址")
    private String fromEmailAddress;

    /**
     * 发信人
     */
    @Schema(description = "发信人")
    private String fromPerson;

    /**
     * SMTP服务器主机名
     */
    @Schema(description = "SMTP服务器主机名")
    private String emailSmtpHost;

    /**
     * SMTP服务器端口号
     */
    @Schema(description = "SMTP服务器端口号")
    private String emailSmtpPort;

    /**
     * SMTP服务器授权码
     */
    @Schema(description = "SMTP服务器授权码")
    private String authCode;

    /**
     * 邮箱启用状态（0：未启用 1：已启用）
     */
    @Schema(description = "邮箱启用状态")
    private EmailStatus status;
}
