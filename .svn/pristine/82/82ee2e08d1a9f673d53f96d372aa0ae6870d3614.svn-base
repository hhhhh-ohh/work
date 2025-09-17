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
 * @description 根据id查询任意（包含已删除）开放平台api设置信息response
 * @author lvzhenwei
 * @date 2021/4/14 3:14 下午
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 2046000432546240109L;

    /** 开放平台api设置信息 */
    @Schema(description = "开放平台api设置信息")
    private OpenApiSettingVO openApiSettingVO;
}
