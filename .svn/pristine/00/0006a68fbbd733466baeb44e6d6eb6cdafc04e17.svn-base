package com.wanmi.sbc.elastic.api.response.communityleader;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wc
 * @date 2020/12/9 21:09
 * @description <p> </p>
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLeaderExportResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 社区团长导出结果
     */
    @Schema(description = "社区团长导出结果")
    private List<CommunityLeaderVO> communityLeaderVOList;
}
