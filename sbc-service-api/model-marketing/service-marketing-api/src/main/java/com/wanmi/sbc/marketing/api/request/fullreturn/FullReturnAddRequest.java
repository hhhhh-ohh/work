package com.wanmi.sbc.marketing.api.request.fullreturn;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.marketing.api.request.market.MarketingAddRequest;
import com.wanmi.sbc.marketing.bean.dto.FullReturnLevelDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.enums.MarketingScopeType;
import com.wanmi.sbc.marketing.bean.enums.MarketingStoreType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @Author: xufeng
 * @Description:
 * @Date: 2022-04-06 14:02
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class FullReturnAddRequest extends MarketingAddRequest {

    private static final long serialVersionUID = 5732608614374946003L;
    /**
     * 满返多级实体对象
     */
    @Schema(description = "营销满返多级优惠列表")
    @NotNull
    @Size(max=5)
    private List<FullReturnLevelDTO> fullReturnLevelList;

    public void valid() {
        Set set = new HashSet<BigDecimal>();

        fullReturnLevelList.forEach((level) -> {
            if (level.getFullAmount() == null) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            } else if (level.getFullAmount().compareTo(BigDecimal.valueOf(Constants.ZERO)) == 0
                    || level.getFullAmount().compareTo(BigDecimal.valueOf(Constants.NUM_999999)) > 0) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }

            set.add(level.getFullAmount());

            if (level.getFullReturnDetailList() == null || level.getFullReturnDetailList().isEmpty()) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            } else if (level.getFullReturnDetailList().size() > Constants.TEN) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            } else {
                level.getFullReturnDetailList().forEach(detail -> {
                    if (detail.getCouponNum() == null || detail.getCouponNum() < Constants.ONE || detail.getCouponNum() > Constants.NUM_99999999) {
                        throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                    }
                });
            }
        });

        if (set.size() != fullReturnLevelList.size()) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080011);
        }

        if (this.getScopeType() != MarketingScopeType.SCOPE_TYPE_ALL) {
            if (this.getScopeIds() == null || this.getScopeIds().stream().allMatch(scopeId -> "".equals(scopeId.trim()))) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
        // 如果是平台
        if (BoolFlag.YES==this.getIsBoss()){
            if (this.getStoreType() == null){
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }else if (this.getStoreType() == MarketingStoreType.STORE_TYPE_APPOINT){
                if (this.getStoreIds() == null || this.getStoreIds().stream().allMatch(Objects::isNull)) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
                }
            }
        }
    }

}
