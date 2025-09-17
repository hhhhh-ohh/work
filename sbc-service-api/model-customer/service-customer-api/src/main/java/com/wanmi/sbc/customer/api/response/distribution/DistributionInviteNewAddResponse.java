package com.wanmi.sbc.customer.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewVo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 邀新记录新增结果
 * @Autho qiaokang
 * @Date：2019-03-04 15:18:59
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionInviteNewAddResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 邀新记录信息
     */
    @Schema(description = "邀新记录信息")
    DistributionInviteNewVo distributionInviteNewVo;

}
