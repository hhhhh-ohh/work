package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.order.bean.dto.LogisticsDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>物流信息重复查询参数结构</p>
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogisticsRepeatRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 物流信息
     */
    @Schema(description = "物流信息")
    @NotEmpty
    private List<LogisticsDTO> logisticsDTOList;
}
