package com.wanmi.sbc.elastic.api.response.communityleader;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.elastic.bean.vo.communityleader.EsCommunityLeaderVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wc
 * @date: 2020/12/7 11:23
 * @description: 社区团长分页查询
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsCommunityLeaderPageResponse extends BasicResponse {

    @Schema(description = "社区团长列表")
    private MicroServicePage<EsCommunityLeaderVO> communityLeaderVOS;
}
