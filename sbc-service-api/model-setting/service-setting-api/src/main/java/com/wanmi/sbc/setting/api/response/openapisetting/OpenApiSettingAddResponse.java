package com.wanmi.sbc.setting.api.response.openapisetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.OpenApiSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 开放平台api设置新增结
 * @author lvzhenwei
 * @date 2021/4/14 3:13 下午
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingAddResponse extends BasicResponse {
    private static final long serialVersionUID = -7039912579336208317L;

    /** 已新增的开放平台api设置信息 */
    @Schema(description = "已新增的开放平台api设置信息")
    private OpenApiSettingVO openApiSettingVO;
}
