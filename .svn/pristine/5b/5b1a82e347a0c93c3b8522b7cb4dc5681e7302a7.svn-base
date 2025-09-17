package com.wanmi.sbc.setting.api.response.thirdplatformconfig;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 第三方平台配置结果
 *
 * @author lq
 * @date 2019-11-05 16:12:49
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPlatformConfigResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /** 客户业务id */
    @Schema(description = "客户业务id 当configType=‘third_platform_linked_mall’时，才有")
    private String customerBizId;

    /** 平台用户名 */
    @Schema(description = "平台用户名")
    private String platformName;

    /** 平台密码 */
    @Schema(description = "平台密码")
    private String platformPwd;

    /** 平台Id */
    @Schema(description = "平台Id")
    private String clientId;

    /** 平台密钥 */
    @Schema(description = "平台密钥")
    private String clientSecret;

    /** 状态 0:未启用1:已启用 */
    @Schema(description = "0:未启用1:已启用")
    private Integer status;

    /** 第三方平台类型 */
    @Schema(description = "0:未启用1:已启用")
    private ThirdPlatformType thirdPlatformType;
}
