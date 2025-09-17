package com.wanmi.sbc.marketing.api.response.communitystatistics;

import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;
import java.util.Map;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>社区团购活动统计信息表列表结果</p>
 * @author dyt
 * @date 2023-07-24 09:58:47
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStatisticsListResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区团购活动统计信息表列表结果
     */
    @Schema(description = "社区团购活动统计信息表列表结果")
    private List<CommunityStatisticsVO> communityStatisticsVOList;

    /**
     * 社区团购活动统计信息表列表结果
     */
    @Schema(description = "社区团购活动统计信息表列表结果")
    private Map<String,CommunityStatisticsVO> communityStatisticsVOMap;
}
