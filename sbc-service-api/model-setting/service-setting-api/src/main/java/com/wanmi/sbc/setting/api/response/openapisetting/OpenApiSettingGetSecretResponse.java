package com.wanmi.sbc.setting.api.response.openapisetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.OpenApiSettingSecretVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 获取开放平台secret
 * @author lvzhenwei
 * @date 2021/4/14 4:27 下午
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingGetSecretResponse extends BasicResponse {
    private static final long serialVersionUID = -3876788423971624564L;

    /** 已新增的开放平台api设置信息 */
    @Schema(description = "已新增的开放平台api设置信息")
    private OpenApiSettingSecretVO openApiSettingSecretVO;
}
