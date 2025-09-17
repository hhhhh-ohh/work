package com.wanmi.sbc.goods.api.request.goodscommission;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.goods.bean.enums.CommissionFreightBearFlag;
import com.wanmi.sbc.goods.bean.enums.CommissionSynPriceType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @description   代销配置查询
 * @author  wur
 * @date: 2021/9/10 15:02
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCommissionConfigUpdateRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 设价类型：0.智能设价 1.手动设价
     */
    @Schema(description = "设价类型：0.智能设价 1.手动设价")
    private CommissionSynPriceType synPriceType;

    /**
     * 默认加价比例
     */
    @Schema(description = "默认加价比例")
    private BigDecimal addRate;

    /**
     * 低价是否自动下架：0.关 1.开
     */
    @Schema(description = "低价是否自动下架：0.关 1.开")
    private DefaultFlag underFlag;

    /**
     * 商品信息自动同步：0.关 1.开
     */
    @Schema(description = "商品信息自动同步：0.关 1.开")
    private DefaultFlag infoSynFlag;

    /**
     * 代销商品运费承担：0.买家 1.卖家
     */
    @Schema(description = "代销商品运费承担：0.买家 1.卖家")
    private CommissionFreightBearFlag freightBearFlag;

    @Override
    public void checkParam(){
        // 如果是智能设价验证加价比例
        if (!Objects.isNull(this.synPriceType) && CommissionSynPriceType.AI_SYN.toValue() == this.synPriceType.toValue()) {
            if (Objects.isNull(this.addRate) || this.addRate.compareTo(BigDecimal.ZERO) < 0 || this.addRate.compareTo(new BigDecimal(300)) > 0) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
            }
        }
    }
}