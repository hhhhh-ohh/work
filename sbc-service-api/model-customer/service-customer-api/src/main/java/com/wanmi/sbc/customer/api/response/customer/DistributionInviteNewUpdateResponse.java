package com.wanmi.sbc.customer.api.response.customer;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewRecordVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionInviteNewUpdateResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @Schema(description = "邀新人数")
    @NotBlank
    private Integer inviteNum;

    @Schema(description = "邀新奖励金额")
    private BigDecimal inviteAmount;

    @Schema(description = "邀新记录")
    private List<DistributionInviteNewRecordVO> inviteNewRecordVOList;

}
