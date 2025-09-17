package com.wanmi.sbc.communityleader.response;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>社区团购团长表分页结果</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLeaderPageMainResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区团购团长表分页结果
     */
    @Schema(description = "社区团购团长表分页结果")
    private MicroServicePage<CommunityLeaderVO> communityLeaderPage;

    /**
     * 社区团购团长表-自提点列表
     */
    @Schema(description = "社区团购团长表-自提点列表")
    private List<CommunityLeaderPickupPointVO> pickupPointList;

    /**
     * 社区团购团长表-自提点列表
     */
    @Schema(description = "社区团购团长表-统计列表")
    private List<CommunityStatisticsVO> communityStatisticsList;
}
