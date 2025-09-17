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
 * @description 开放平台api设置修改结果
 * @author lvzhenwei
 * @date 2021/4/14 3:19 下午
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 5633561620484557143L;

    /** 已修改的开放平台api设置信息 */
    @Schema(description = "已修改的开放平台api设置信息")
    private OpenApiSettingVO openApiSettingVO;
}
