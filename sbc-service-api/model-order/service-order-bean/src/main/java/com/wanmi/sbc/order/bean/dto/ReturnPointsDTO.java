package com.wanmi.sbc.order.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 退积分信息
 *
 * @author yang
 * @since 2019/4/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ReturnPointsDTO implements Serializable {
    /**
     * 申请积分
     */
    @Schema(description = "申请积分")
    private Long applyPoints;

    /**
     * 实退积分
     */
    @Schema(description = "实退积分")
    private Long actualPoints;

}
