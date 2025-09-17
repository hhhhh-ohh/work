package com.wanmi.sbc.marketing.halfpricesecondpiece.model.request;


import com.wanmi.sbc.marketing.bean.dto.HalfPriceSecondPieceLevelDTO;
import com.wanmi.sbc.marketing.common.request.MarketingSaveRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 第二件半价活动规则
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HalfPriceSecondPieceSaveRequest extends MarketingSaveRequest {
    private static final long serialVersionUID = 4894133284221615424L;

    /**
     * 第二件半价实体对象
     */
    @Schema(description = "第二件半价活动规则列表")
    private HalfPriceSecondPieceLevelDTO halfPriceSecondPieceLevel;

    public HalfPriceSecondPieceLevelDTO generateHalfPriceSecondPieceList(Long marketingId) {
        halfPriceSecondPieceLevel.setMarketingId(marketingId);
        return halfPriceSecondPieceLevel;
    }
}
