package com.wanmi.sbc.marketing.api.response.communityactivity;

import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class CommunityActivityByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区团购活动表信息
     */
    @Schema(description = "社区团购活动表信息")
    private CommunityActivityVO communityActivityVO;
}
