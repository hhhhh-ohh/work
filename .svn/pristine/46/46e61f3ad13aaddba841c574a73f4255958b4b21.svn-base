package com.wanmi.sbc.marketing.bean.dto;

import com.wanmi.sbc.marketing.bean.vo.CouponCodeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description 自动选券-当前券的实际抵扣
 * @author malianfeng
 * @date 2022/5/27 20:20
 */
@Schema
@Data
public class CouponCodeAutoSelectDTO extends CouponCodeVO {

    private static final long serialVersionUID = 8733982734522307406L;

    /**
     * 实际抵扣
     */
    @Schema(description = "实际抵扣")
    private BigDecimal actualDiscount = BigDecimal.ZERO;
}
