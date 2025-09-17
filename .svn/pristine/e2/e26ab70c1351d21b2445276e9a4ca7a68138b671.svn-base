package com.wanmi.sbc.customer.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author houshuai
 * @date 2021/1/7 10:30
 * @description <p> </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class DistributionInviteNewSaveResponse extends BasicResponse {

    /**
     * 邀新记录
     */
    @Schema(description = "邀新记录信息")
    List<DistributionInviteNewRecordVO> inviteNewRecordVOList;

    /**
     * 分销员id
     */
    @Schema(description = "分销员id")
    private String distributionId;
}
