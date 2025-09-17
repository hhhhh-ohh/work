package com.wanmi.sbc.marketing.api.response.communityregionsetting;

import com.wanmi.sbc.marketing.bean.vo.CommunityRegionSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区拼团商家设置表修改结果</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityRegionSettingModifyResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的社区拼团商家设置表信息
     */
    @Schema(description = "已修改的社区拼团商家设置表信息")
    private CommunityRegionSettingVO communityRegionSettingVO;
}
