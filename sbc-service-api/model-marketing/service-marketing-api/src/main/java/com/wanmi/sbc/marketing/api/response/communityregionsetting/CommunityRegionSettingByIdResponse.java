package com.wanmi.sbc.marketing.api.response.communityregionsetting;

import com.wanmi.sbc.marketing.bean.vo.CommunityRegionSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）社区拼团区域设置表信息response</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityRegionSettingByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区拼团区域设置表信息
     */
    @Schema(description = "社区拼团区域设置表信息")
    private CommunityRegionSettingVO communityRegionSettingVO;
}
