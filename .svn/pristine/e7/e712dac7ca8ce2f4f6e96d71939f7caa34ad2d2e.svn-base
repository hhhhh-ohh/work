package com.wanmi.sbc.elastic.api.request.communityleader;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: HouShuai
 * @date: 2020/12/8 16:55
 * @description: 已新增的分销员信息
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EsCommunityLeaderAddRequest extends BaseRequest {

    /**
     * 已新增的分销员信息
     */
    @Schema(description = "分销员信息")
    private List<CommunityLeaderVO> list;
}
