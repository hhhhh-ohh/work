package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingSubType;

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
 * author: sunkun
 * Date: 2018-11-21
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class MarketingFullReductionSaveDTO extends MarketingSaveDTO {

    private static final long serialVersionUID = 3890190785894292713L;

    /**
     * 满减多级实体对象
     */
    @Schema(description = "营销满减多级优惠列表")
    @NotNull
    @Size(max=5)
    private List<MarketingFullReductionLevelDTO> fullReductionLevelList;

    public List<MarketingFullReductionLevelDTO> generateFullReductionLevelList(Long marketingId) {
        return fullReductionLevelList.stream().map((level) -> {
            level.setMarketingId(marketingId);
            return level;
        }).collect(Collectors.toList());
    }

    public void valid() {
        Set set;

        if (this.getSubType() == MarketingSubType.REDUCTION_FULL_AMOUNT) {
            set = new HashSet<BigDecimal>();
        } else {
            set = new HashSet<Long>();
        }

        // 校验参数，满金额时金额不能为空，满数量时数量不能为空
        fullReductionLevelList.stream().forEach((level) -> {
            if (this.getSubType() == MarketingSubType.REDUCTION_FULL_AMOUNT) {
                if (level.getFullAmount() == null) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                } else if (level.getFullAmount().compareTo(BigDecimal.valueOf(Constants.MARKETING_FULLAMOUNT_MIN)) < 0 ||  level.getFullAmount().compareTo(BigDecimal.valueOf(Constants.MARKETING_FULLAMOUNT_MAX)) > 0) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080005);
                }

                set.add(level.getFullAmount());
            } else if (this.getSubType() == MarketingSubType.REDUCTION_FULL_COUNT) {
                if (level.getFullCount() == null) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                } else if (level.getFullCount().compareTo(Constants.MARKETING_FULLCOUNT_MIN) < 0 || level.getFullCount().compareTo(Constants.MARKETING_FULLCOUNT_MAX) > 0) {
                    throw new SbcRuntimeException(MarketingErrorCodeEnum.K080006);
                }

                set.add(level.getFullCount());
            } else {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            if (level.getReduction() == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            } else if (level.getReduction().compareTo(BigDecimal.valueOf(Constants.MARKETING_FULLAMOUNT_MIN)) < 0 ||  level.getReduction().compareTo(BigDecimal.valueOf(Constants.MARKETING_FULLAMOUNT_MAX)) > 0) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080005);
            } else if (this.getSubType() == MarketingSubType.REDUCTION_FULL_AMOUNT && level.getFullAmount().compareTo(level.getReduction()) <= 0) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080008);
            }
        });

        if (set.size() != fullReductionLevelList.size()) {
            if (this.getSubType() == MarketingSubType.REDUCTION_FULL_AMOUNT) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080011);
            } else {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080012);
            }
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
