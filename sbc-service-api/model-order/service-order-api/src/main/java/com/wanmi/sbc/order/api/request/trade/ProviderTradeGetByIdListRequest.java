package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ProviderTradeGetByIdListRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 交易id
     */
    @Schema(description = "交易idList")
    @NotEmpty
    private List<String> idList;

}
