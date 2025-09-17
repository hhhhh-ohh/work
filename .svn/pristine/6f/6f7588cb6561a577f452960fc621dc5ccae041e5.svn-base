package com.wanmi.sbc.elastic.api.response.communityleader;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsCommunityLeaderAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的社区团长信息
     */
    @Schema(description = "社区团长信息")
    private List<CommunityLeaderVO> communityLeaderVOS;
}