package com.wanmi.sbc.customer.api.response.communitypickup;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class CommunityLeaderPickupPointPageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 团长自提点表分页结果
     */
    @Schema(description = "团长自提点表分页结果")
    private MicroServicePage<CommunityLeaderPickupPointVO> communityLeaderPickupPointPage;
}
