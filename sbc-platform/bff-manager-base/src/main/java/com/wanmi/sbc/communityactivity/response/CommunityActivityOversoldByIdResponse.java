package com.wanmi.sbc.communityactivity.response;


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
public class CommunityActivityOversoldByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 活动超卖结果
     */
    @Schema(description = "活动超卖结果 0：正常 1：未生成 2：存在超卖订单")
    private Integer result;
}
