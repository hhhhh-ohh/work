package com.wanmi.sbc.marketing.payingmember.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author xuyunpeng
 * @className PayingMemberDiscount
 * @description
 * @date 2022/5/20 4:16 PM
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayingMemberDiscount {

    /**
     * 等级id
     */
    private Integer levelId;

    /**
     * 等级名称
     */
    private String levelName;

    /**
     * 商品预计优惠金额
     */
    private BigDecimal discountPrice;
}
