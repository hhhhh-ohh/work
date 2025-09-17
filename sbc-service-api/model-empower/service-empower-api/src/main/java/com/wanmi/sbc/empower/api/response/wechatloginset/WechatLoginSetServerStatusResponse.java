package com.wanmi.sbc.empower.api.response.wechatloginset;

import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>获取授信开关状态</p>
 *
 * @author zhanghao
 * @date 2019-11-11 16:15:25
 */

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatLoginSetServerStatusResponse implements Serializable {


    private static final long serialVersionUID = 1L;
    /**
     * h5-微信授权登录是否启用 0 不启用， 1 启用
     */
    @Schema(description = "h5-微信授权登录是否启用 0 不启用， 1 启用")
    private DefaultFlag mobileStatus;

    /**
     * pc-微信授权登录是否启用 0 不启用， 1 启用
     */
    @Schema(description = "pc-微信授权登录是否启用 0 不启用， 1 启用")
    private DefaultFlag pcStatus;

    /**
     * app-微信授权登录是否启用 0 不启用， 1 启用
     */
    @Schema(description = "app-微信授权登录是否启用 0 不启用， 1 启用")
    private DefaultFlag appStatus;
}
