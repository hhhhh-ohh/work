package com.wanmi.sbc.marketing.discount.model.request;


import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.bean.dto.MarketingFullDiscountLevelDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;
import com.wanmi.sbc.marketing.common.request.MarketingSaveRequest;
import com.wanmi.sbc.marketing.discount.model.entity.MarketingFullDiscountLevel;

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
import java.util.stream.Collectors;

/**
 * 营销满折规则
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingFullDiscountSaveRequest extends MarketingSaveRequest {
    /**
     * 满折多级实体对象
     */
    @NotNull
    @Size(max=5)
    private List<MarketingFullDiscountLevelDTO> fullDiscountLevelList;

    public List<MarketingFullDiscountLevel> generateFullDiscountLevelList(Long marketingId) {
        return KsBeanUtil.convert(fullDiscountLevelList.stream().map((level) -> {
            level.setMarketingId(marketingId);
            return level;
        }).collect(Collectors.toList()), MarketingFullDiscountLevel.class);
    }

    public void valid() {
        Set set;

        if (this.getSubType() == MarketingSubType.DISCOUNT_FULL_AMOUNT) {
            set = new HashSet<BigDecimal>();
        } else {
            set = new HashSet<Long>();
        }


        // 校验参数，满金额时金额不能为空，满数量时数量不能为空
        fullDiscountLevelList.stream().forEach((level) -> {
            if (this.getSubType() == MarketingSubType.DISCOUNT_FULL_AMOUNT) {
                if (level.getFullAmount() == null) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                } else if (level.getFullAmount().compareTo(BigDecimal.valueOf(Constants.MARKETING_FULLAMOUNT_MIN)) < 0 ||  level.getFullAmount().compareTo(BigDecimal.valueOf(Constants.MARKETING_FULLAMOUNT_MAX)) > 0) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080005);
                }

                set.add(level.getFullAmount());
            } else if (this.getSubType() == MarketingSubType.DISCOUNT_FULL_COUNT) {
                if (level.getFullCount() == null) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                } else if (level.getFullCount().compareTo(Constants.MARKETING_FULLCOUNT_MIN) < 0 || level.getFullCount().compareTo(Constants.MARKETING_FULLCOUNT_MAX) > 0) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080006);
                }

                set.add(level.getFullCount());
            } else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (level.getDiscount() == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            } else if (level.getDiscount().compareTo(BigDecimal.valueOf(Constants.MARKETING_DISCOUNT_MIN)) < 0 ||  level.getDiscount().compareTo(BigDecimal.valueOf(Constants.MARKETING_DISCOUNT_MAX)) > 0) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080007);
            }
        });

        if (set.size() != fullDiscountLevelList.size()) {
            if (this.getSubType() == MarketingSubType.DISCOUNT_FULL_AMOUNT) {
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
