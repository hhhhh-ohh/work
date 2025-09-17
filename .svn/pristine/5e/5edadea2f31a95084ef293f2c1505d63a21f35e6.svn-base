package com.wanmi.sbc.empower.api.request.channel.base;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

/**
 * @description 渠道配置修改
 * @author wur
 * @date 2021/5/13 17:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class ChannelConfigModifyRequest extends BaseRequest {

    private static final long serialVersionUID = -8015726253741444133L;

    /** 第三方平台类型 */
    @NotNull
    @Schema(description = "第三方平台类型")
    private ThirdPlatformType thirdPlatformType;

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
    private EnableStatus status;

    @Override
    public void checkParam() {
        if (ThirdPlatformType.LINKED_MALL.equals(thirdPlatformType)
                && StringUtils.isBlank(customerBizId)) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        if (ThirdPlatformType.VOP.equals(thirdPlatformType)
                && (StringUtils.isBlank(platformName) || StringUtils.isBlank(platformPwd))) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
