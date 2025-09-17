package com.wanmi.sbc.marketing.api.response.electroniccoupon;

import com.wanmi.sbc.common.base.BasicResponse;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 更新返回统计数量Response
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCardModifyCountResponse extends BasicResponse {

    /**
     * 统计的数量
     */
    @Schema(description = "统计的数量")
    private Long count;
}
