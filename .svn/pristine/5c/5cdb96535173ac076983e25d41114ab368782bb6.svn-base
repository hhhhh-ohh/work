package com.wanmi.sbc.marketing.api.response.communityregionsetting;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>社区拼团区域设置表已被使用的地区结果</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityRegionSettingUsedAreaResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 已被使用的地区
     */
    @Schema(description = "已被使用的地区")
    private List<String> areaIds;

    /**
     * 已被使用的地区
     */
    @Schema(description = "已被使用的团长/自提点")
    private List<String> pickupPointIdList;
}
