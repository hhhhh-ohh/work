package com.wanmi.sbc.marketing.api.response.communityregionsetting;

import com.wanmi.sbc.marketing.bean.vo.CommunityRegionSettingVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区拼团区域设置表列表结果</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityRegionSettingListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区拼团区域设置表列表结果
     */
    @Schema(description = "社区拼团区域设置表列表结果")
    private List<CommunityRegionSettingVO> communityRegionSettingList;
}
