package com.wanmi.sbc.setting.api.request.thirdplatformconfig;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

/** 第三方平台配置 Created by dyt on 2019/11/7. */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPlatformConfigModifyRequest extends BaseRequest {

    /** 第三方平台类型 */
    @NotNull
    @Schema(description = "第三方平台类型")
    private Integer thirdPlatformType;

    /** 客户业务id */
    @Schema(description = "客户业务id 当configType=‘third_platform_linked_mall’时，才有")
    @Length(min = 17, max = 17)
    private String customerBizId;

    /** 平台用户名 */
    @Schema(description = "平台用户名")
    @Length(min = 1, max = 20)
    private String platformName;

    /** 平台密码 */
    @Schema(description = "平台密码")
    @Length(min = 1, max = 20)
    private String platformPwd;

    /** 平台ID */
    @Schema(description = "平台ID")
    @Length(min = 1, max = 60)
    private String clientId;

    /** 平台密钥 */
    @Schema(description = "平台密钥")
    @Length(min = 1, max = 60)
    private String clientSecret;

    /** 状态 0:未启用1:已启用 */
    @NotNull
    @Schema(description = "0:未启用1:已启用")
    private Integer status;

    public void checkParam() {
        if (ThirdPlatformType.LINKED_MALL.toValue() == thirdPlatformType
                && StringUtils.isBlank(customerBizId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (ThirdPlatformType.VOP.toValue() == thirdPlatformType
                && (StringUtils.isBlank(platformName) || StringUtils.isBlank(platformPwd))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
