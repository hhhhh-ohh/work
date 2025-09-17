package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p></p>
 * author: weiwenhao
 * Date: 2020-04-13
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class MarketingBuyoutPriceSaveDTO extends MarketingSaveDTO {

    private static final long serialVersionUID = -6627274888933495789L;

    /**
     * 满减多级实体对象
     */
    @Schema(description = "营销一口价多级优惠列表")
    @NotNull
    @Size(max = 5)
    private List<MarketingBuyoutPriceLevelDTO> buyoutPriceLevelList;

    public List<MarketingBuyoutPriceLevelDTO> generateFullReductionLevelList(Long marketingId) {
        return buyoutPriceLevelList.stream().map((level) -> {
            level.setMarketingId(marketingId);
            return level;
        }).collect(Collectors.toList());
    }

    public void valid() {
        Set set = new HashSet<Long>();
        Set setChoiceCount = new HashSet<Long>();

        // 校验参数，一口价打包活动件数和金额不能为空，不能重复的校验
        buyoutPriceLevelList.stream().forEach((level) -> {
            if (level.getFullAmount() == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            } else if (level.getFullAmount().compareTo(BigDecimal.valueOf(Constants.MARKETING_FULLAMOUNT_MIN)) < 0 || level.getFullAmount().compareTo(BigDecimal.valueOf(Constants.MARKETING_FULLAMOUNT_MAX)) > 0) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080005);
            }
            if (level.getChoiceCount() == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            } else if (level.getChoiceCount().compareTo(Constants.MARKETING_FULLCOUNT_MIN) < 0 || level.getChoiceCount().compareTo(Constants.MARKETING_FULLCOUNT_MAX) > 0) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080006);
            }
            set.add(level.getFullAmount());
            setChoiceCount.add(level.getChoiceCount());
        });

        if (getBeginTime().isAfter(getEndTime()) || getBeginTime().isEqual(getEndTime())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //判断一口价打包活动规则金额是否相同
        if (set.size() != buyoutPriceLevelList.size()) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080011);
        }
        //判断一口价打包活动规则件数是否相同
        if (setChoiceCount.size() != buyoutPriceLevelList.size()) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080012);
        }

        if (this.getScopeType() != MarketingScopeType.SCOPE_TYPE_ALL) {
            if (this.getScopeIds() == null || this.getScopeIds().stream().allMatch(scopeId -> "".equals(scopeId.trim()))) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (MarketingScopeType.SCOPE_TYPE_CUSTOM.equals(this.getScopeType())
                    && this.getScopeIds().size() > Constants.MARKETING_GOODS_SIZE_MAX) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080027,
                        new Object[]{Constants.MARKETING_GOODS_SIZE_MAX});
            }
        }
    }
}
