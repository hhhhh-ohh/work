package com.wanmi.sbc.marketing.api.response.communityactivity;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>社区团购活动表分页结果</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityActivityPageByLeaderResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区团购活动表分页结果
     */
    @Schema(description = "社区团购活动表分页结果")
    private MicroServicePage<CommunityActivityVO> communityActivityPage;

    /**
     * 社区团购团长表-自提点列表
     */
    @Schema(description = "社区团购团长表-统计列表")
    private List<GoodsInfoVO> communityStatisticsList;
}
