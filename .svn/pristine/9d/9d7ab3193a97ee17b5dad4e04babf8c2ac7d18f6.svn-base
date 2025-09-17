package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 保存订单赠品商品快照请求结构
 * @Author: daiyitian
 * @Description:
 * @Date: 2018-11-16 16:39
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class TradeItemSnapshotGiftRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 终端token
     */
    @Schema(description = "终端token", hidden = true)
    private String terminalToken;

    /**
     * 商品信息，必传
     */
    @Schema(description = "商品信息")
    @NotEmpty
    @Valid
    private List<TradeItemDTO> tradeItems;

    /**
     * 营销快照
     */
    @Schema(description = "营销快照")
    @NotNull
    private TradeMarketingDTO tradeMarketingDTO;

    @Schema(description = "门店ID")
    private Long storeId;
}
