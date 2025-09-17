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
 * <p>平台素材资源分类修改结果</p>
 * @author lq
 * @date 2019-11-05 16:14:55
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceCateModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的平台素材资源分类信息
     */
    @Schema(description = "已修改的平台素材资源分类信息")
    private SystemResourceCateVO systemResourceCateVO;
}
