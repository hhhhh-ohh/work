package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wanggang
 * @createDate: 2018/12/3 10:06
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class VerifyTradeMarketingRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    public VerifyTradeMarketingRequest(List<TradeMarketingDTO> tradeMarketingList, List<TradeItemDTO> oldGifts,
                                       List<TradeItemDTO> tradeItems, String customerId, Boolean isFoceCommit) {
        this.tradeMarketingList = tradeMarketingList;
        this.oldGifts = oldGifts;
        this.tradeItems = tradeItems;
        this.customerId = customerId;
        this.isFoceCommit = isFoceCommit;
    }

    @Schema(description = "订单营销信息列表")
    private List<TradeMarketingDTO> tradeMarketingList;

    @Schema(description = "赠品列表")
    private List<TradeItemDTO> oldGifts;

    @Schema(description = "订单列表")
    private List<TradeItemDTO> tradeItems;

    @Schema(description = "客户id")
    private String customerId;

    @Schema(description = "是否提交",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class )
    private Boolean isFoceCommit;

    @Schema(description = "门店ID")
    private Long storeId;

    @Schema(description = "插件类型")
    private PluginType pluginType;
}
