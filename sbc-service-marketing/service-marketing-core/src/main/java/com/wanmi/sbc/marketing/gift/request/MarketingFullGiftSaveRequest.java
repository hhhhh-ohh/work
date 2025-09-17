package com.wanmi.sbc.marketing.gift.request;


import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.bean.dto.MarketingFullGiftLevelDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.common.request.MarketingSaveRequest;
import com.wanmi.sbc.marketing.gift.model.root.MarketingFullGiftDetail;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 营销满赠规则
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingFullGiftSaveRequest extends MarketingSaveRequest {
    /**
     * 满赠多级实体对象
     */
    @NotNull
    @Size(max=5)
    private List<MarketingFullGiftLevelDTO> fullGiftLevelList;

    public List<MarketingFullGiftLevelDTO> generateFullGiftLevelList(Long marketingId) {
        return fullGiftLevelList.stream().map((level) -> {
            level.setGiftLevelId(null);
            level.setMarketingId(marketingId);
            return level;
        }).collect(Collectors.toList());
    }

    public List<MarketingFullGiftDetail> generateFullGiftDetailList(List<MarketingFullGiftLevelDTO> fullGiftLevelList) {
        return KsBeanUtil.convert(fullGiftLevelList.stream().map((level) ->
                level.getFullGiftDetailList().stream().map((detail) -> {
                    detail.setGiftLevelId(level.getGiftLevelId());
                    detail.setMarketingId(level.getMarketingId());
                    return detail;
        })).flatMap(Function.identity()).collect(Collectors.toList()), MarketingFullGiftDetail.class);
    }

    public void valid() {
        Set set;

        if (this.getSubType() == MarketingSubType.GIFT_FULL_AMOUNT) {
            set = new HashSet<BigDecimal>();
        } else {
            set = new HashSet<Long>();
        }

        // 校验参数，满金额时金额不能为空，满数量时数量不能为空
        fullGiftLevelList.stream().forEach((level) -> {
            if (this.getSubType() == MarketingSubType.GIFT_FULL_AMOUNT) {
                if (level.getFullAmount() == null) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                } else if (level.getFullAmount().compareTo(BigDecimal.valueOf(Constants.MARKETING_FULLAMOUNT_MIN)) < 0 ||  level.getFullAmount().compareTo(BigDecimal.valueOf(Constants.MARKETING_FULLAMOUNT_MAX)) > 0) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080005);
                }

                set.add(level.getFullAmount());
            } else if (this.getSubType() == MarketingSubType.GIFT_FULL_COUNT) {
                if (level.getFullCount() == null) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                } else if (level.getFullCount().compareTo(Constants.MARKETING_FULLCOUNT_MIN) < 0 || level.getFullCount().compareTo(Constants.MARKETING_FULLCOUNT_MAX) > 0) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080006);
                }

                set.add(level.getFullCount());
            } else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (level.getFullGiftDetailList() == null || level.getFullGiftDetailList().isEmpty()) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080009);
            } else if (level.getFullGiftDetailList().size() > Constants.MARKETING_Gift_TYPE_MAX) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080010);
            } else {
                level.getFullGiftDetailList().stream().forEach(detail -> {
                    if (detail.getProductNum() == null || detail.getProductNum() < Constants.MARKETING_Gift_MIN || detail.getProductNum() > Constants.MARKETING_Gift_MAX) {
                        throw new SbcRuntimeException(MarketingErrorCodeEnum.K080013);
                    }
                });
            }
        });

        if (set.size() != fullGiftLevelList.size()) {
            if (this.getSubType() == MarketingSubType.GIFT_FULL_AMOUNT) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080011);
            } else {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080012);
            }
        }

        if (this.getScopeType() == MarketingScopeType.SCOPE_TYPE_CUSTOM && isEmptyScope()) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }

    private Boolean isEmptyScope() {
        return this.getScopeIds() == null || this.getScopeIds().stream().allMatch(scopeId -> "".equals(scopeId.trim()));
    }
}
