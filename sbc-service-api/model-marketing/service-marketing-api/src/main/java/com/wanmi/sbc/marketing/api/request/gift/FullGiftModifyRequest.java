package com.wanmi.sbc.marketing.api.request.gift;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.marketing.api.request.market.MarketingModifyRequest;
import com.wanmi.sbc.marketing.bean.dto.FullGiftLevelDTO;
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

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-16 16:16
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class FullGiftModifyRequest extends MarketingModifyRequest {

    private static final long serialVersionUID = -2952406761735818859L;
    /**
     * 满赠多级实体对象
     */
    @Schema(description = "营销满赠多级优惠列表")
    @NotNull
    @Size(max=5)
    private List<FullGiftLevelDTO> fullGiftLevelList;

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
