package com.wanmi.sbc.customer.api.response.communityleader;

import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据id查询任意（包含已删除）社区团购团长表信息response</p>
 * @author dyt
 * @date 2023-07-21 11:10:45
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLeaderByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区团购团长表信息
     */
    @Schema(description = "社区团购团长表信息")
    private CommunityLeaderVO communityLeaderVO;

    /**
     * 社区团购团长表-自提点列表
     */
    @Schema(description = "社区团购团长表-自提点列表")
    private List<CommunityLeaderPickupPointVO> pickupPointList;
}
