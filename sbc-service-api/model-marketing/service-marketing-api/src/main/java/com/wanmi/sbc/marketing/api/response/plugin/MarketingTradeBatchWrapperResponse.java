package com.wanmi.sbc.marketing.api.response.plugin;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.TradeMarketingWrapperVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 批量订单提交营销处理响应结构
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingTradeBatchWrapperResponse extends BasicResponse {

    private static final long serialVersionUID = 8033089588708965519L;

    /**
     * 批量订单提交营销插件参数 {@link TradeMarketingWrapperVO}
     */
    @Schema(description = "批量订单提交营销插件参数")
    private List<TradeMarketingWrapperVO> wraperVOList;
}
