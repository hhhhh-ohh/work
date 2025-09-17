package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p></p>
 * author: weiwenhao
 * Date: 2020-05-22
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class HalfPriceSecondPieceSaveDTO extends MarketingSaveDTO {

    private static final long serialVersionUID = -8101876593067211254L;

    /**
     * 第二件半价实体对象
     */
    @Schema(description = "第二件半价活动规则列表")
    @NotNull
    private HalfPriceSecondPieceLevelDTO halfPriceSecondPieceLevel;

    //第二件半价规则参数校验
    public void valid() {
        //判断参数不能为空，是否是0-9.9数据
        if (Objects.isNull(halfPriceSecondPieceLevel.getDiscount()) ||
                halfPriceSecondPieceLevel.getDiscount().compareTo(BigDecimal.ZERO) < 0 ||
                halfPriceSecondPieceLevel.getDiscount().compareTo(BigDecimal.valueOf(9.9)) > 0) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        //第二件半价件数参数不能为null,件数>1
        if (Objects.isNull(halfPriceSecondPieceLevel.getNumber()) ||
                halfPriceSecondPieceLevel.getNumber() <=1 ) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        if (this.getScopeType() != MarketingScopeType.SCOPE_TYPE_ALL) {
            if (this.getScopeIds() == null ||
                    this.getScopeIds().stream().allMatch(scopeId -> "".equals(scopeId.trim()))) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
            if (MarketingScopeType.SCOPE_TYPE_CUSTOM.equals(this.getScopeType())
                    && this.getScopeIds().size() > Constants.MARKETING_GOODS_SIZE_MAX) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080027,
                        new Object[]{Constants.MARKETING_GOODS_SIZE_MAX});
            }
        }
        // 验证活动时间
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(this.getBeginTime()) || now.isAfter(this.getEndTime())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
    }
}
