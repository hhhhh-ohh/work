package com.wanmi.sbc.marketing.api.response.payingmember;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xuyunpeng
 * @className PayingMemberSkuResponse
 * @description
 * @date 2022/5/20 9:37 AM
 **/
@Data
@Builder
@Schema
@AllArgsConstructor
@NoArgsConstructor
public class PayingMemberSkuResponse implements Serializable {
    private static final long serialVersionUID = -5291642490630352654L;

    /**
     * 等级id
     */
    @Schema(description = "等级id")
    private Integer levelId;

    /**
     * 会员名称
     */
    @Schema(description = "会员名称")
    private String levelName;

    /**
     * 预计优惠金额
     */
    @Schema(description = "预计优惠金额")
    private BigDecimal discountPrice;
}
