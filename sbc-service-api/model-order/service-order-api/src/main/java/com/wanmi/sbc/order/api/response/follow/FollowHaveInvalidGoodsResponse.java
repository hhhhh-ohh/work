package com.wanmi.sbc.order.api.response.follow;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class FollowHaveInvalidGoodsResponse extends BasicResponse {

    @Schema(description = "是否存在失效商品",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    Boolean boolValue;
}
