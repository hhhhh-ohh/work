package com.wanmi.sbc.marketing.fullreturn.request;

import com.wanmi.sbc.marketing.common.request.MarketingSaveRequest;
import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnDetail;
import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnLevel;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 营销满返规则
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingFullReturnSaveRequest extends MarketingSaveRequest {
    /**
     * 满返多级实体对象
     */
    @NotNull
    @Size(max=5)
    private List<MarketingFullReturnLevel> fullReturnLevelList;

    public List<MarketingFullReturnLevel> generateFullReturnLevelList(Long marketingId) {
        return fullReturnLevelList.stream().peek((level) -> {
            level.setReturnLevelId(null);
            level.setMarketingId(marketingId);
        }).collect(Collectors.toList());
    }

    public List<MarketingFullReturnDetail> generateFullReturnDetailList(List<MarketingFullReturnLevel> fullReturnLevelList) {
        return fullReturnLevelList.stream().flatMap((level) ->
                level.getFullReturnDetailList().stream().peek((detail) -> {
                    detail.setReturnLevelId(level.getReturnLevelId());
                    detail.setMarketingId(level.getMarketingId());
                })).collect(Collectors.toList());
    }
}
