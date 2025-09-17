package com.wanmi.sbc.setting.api.response.openapisetting;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.setting.bean.vo.OpenApiSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @description 开放平台api设置分页结果
 * @author lvzhenwei
 * @date 2021/4/14 3:20 下午
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingPageResponse extends BasicResponse {
    private static final long serialVersionUID = 6197506110373084705L;

    /** 开放平台api设置分页结果 */
    @Schema(description = "开放平台api设置分页结果")
    private MicroServicePage<OpenApiSettingVO> openApiSettingVOPage;
}
