package com.wanmi.sbc.setting.api.request.thirdplatformconfig;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 第三方平台配置
 * Created by dyt on 2019/11/7.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdPlatformConfigByTypeRequest extends BaseRequest {

    /**
     * 配置ID
     */
    @Schema(description ="配置ID")
    private String configType;
}
