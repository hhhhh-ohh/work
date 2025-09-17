package com.wanmi.sbc.setting.api.response.systemresourcecate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.setting.bean.vo.SystemResourceCateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）平台素材资源分类信息response</p>
 * @author lq
 * @date 2019-11-05 16:14:55
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceCateByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 平台素材资源分类信息
     */
    @Schema(description = "平台素材资源分类信息")
    private SystemResourceCateVO systemResourceCateVO;
}
