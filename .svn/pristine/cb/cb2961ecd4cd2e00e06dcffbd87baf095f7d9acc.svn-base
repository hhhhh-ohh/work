package com.wanmi.sbc.order.api.request.payorder;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class FindPayOrderByPayOrderIdsRequest extends BaseRequest {

    @Schema(description = "付款单id列表")
    List<String> payOrderIds;
}
