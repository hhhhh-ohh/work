package com.wanmi.sbc.marketing.api.request.plugin;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingWrapperDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * 批量订单提交营销处理请求结构
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingTradeBatchWrapperRequest extends BaseRequest {

    private static final long serialVersionUID = -8533866975446764421L;

    /**
     * 批量订单营销封装参数 {@link TradeMarketingWrapperDTO}
     */
    @Schema(description = "订单营销信息列表")
    @NotEmpty
    private List<TradeMarketingWrapperDTO> wraperDTOList;
}
