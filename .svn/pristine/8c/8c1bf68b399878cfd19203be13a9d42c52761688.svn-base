package com.wanmi.sbc.communityactivity.response;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.order.bean.vo.CommunitySimpleTradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>社区团购跟团分页结果</p>
 * @author dyt
 * @date 2023-07-24 14:26:35
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityActivityTradePageSiteResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区团购活动表分页结果
     */
    @Schema(description = "社区团购跟团分页结果")
    private MicroServicePage<CommunitySimpleTradeVO> communitySimpleTradePage;
}
