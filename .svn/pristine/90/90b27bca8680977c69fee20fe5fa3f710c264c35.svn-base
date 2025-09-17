package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.order.bean.dto.TradeBatchDeliverDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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
public class TradeBatchDeliverRequest extends BaseRequest {

    /**
     * 批量发货参数
     */
    @Schema(description = "批量发货参数")
    @NotEmpty
    private List<TradeBatchDeliverDTO> batchDeliverDTOList;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    @NotNull
    private Operator operator;

}
