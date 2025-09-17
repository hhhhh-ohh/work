package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author edz
 * @className FindByTailOrderNoInRequest
 * @description TODO
 * @date 2022/10/14 22:10
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class FindByTailOrderNoInRequest extends BaseQueryRequest {

    @Schema(description = "尾款订单ID")
    private List<String> tailOrderIds;
}
