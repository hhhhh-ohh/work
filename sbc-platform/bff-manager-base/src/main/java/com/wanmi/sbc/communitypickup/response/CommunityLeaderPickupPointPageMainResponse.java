package com.wanmi.sbc.communitypickup.response;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>团长自提点表分页结果</p>
 * @author dyt
 * @date 2023-07-21 14:10:45
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLeaderPickupPointPageMainResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 团长自提点表分页结果
     */
    @Schema(description = "团长自提点表分页结果")
    private MicroServicePage<CommunityLeaderPickupPointVO> communityLeaderPickupPointPage;

    /**
     * 社区团购活动统计信息表列表结果
     */
    @Schema(description = "社区团购活动统计信息表列表结果")
    private List<CommunityStatisticsVO> communityStatisticsList;
}
