package com.wanmi.sbc.setting.api.response.systemconfig;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dyt on 2019/11/7.
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListShowTypeResponse extends BasicResponse {

    /**
     * 状态 0:未启用1:已启用
     */
    @Schema(description = "状态 0:订单精简版 1:订单明细版")
    private Integer status;
}
