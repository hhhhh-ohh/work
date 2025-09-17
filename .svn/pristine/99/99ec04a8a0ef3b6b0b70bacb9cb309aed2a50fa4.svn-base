package com.wanmi.sbc.communityactivity.response;

import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据id查询任意（包含已删除）社区团购活动表信息response</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityActivityByIdMainResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区团购活动表信息
     */
    @Schema(description = "社区团购活动表信息")
    private CommunityActivityVO communityActivityVO;

    /**
     * 商品回显列表
     */
    @Schema(description = "商品回显列表")
    private List<GoodsInfoVO> skuList;

    /**
     * 团长佣金-自提点详情信息
     */
    @Schema(description = "团长佣金-自提点详情信息")
    private List<CommunityLeaderPickupPointVO> commissionLeaderList;
}
