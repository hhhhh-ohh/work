package com.wanmi.sbc.marketing.preferential.model.root;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author edz
 * @className MarketingPreferentialGoodsDetail
 * @description 活动商品详情
 * @date 2022/11/17 17:43
 **/
@Entity
@Table(name = "marketing_preferential_detail")
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class MarketingPreferentialDetail implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferentialDetailId;

    /**
     * 加价购档次阶梯ID
     */
    private Long preferentialLevelId;

    /**
     * 商品ID
     */
    private String goodsInfoId;

    /**
     * 加价购活动金额
     */
    private BigDecimal preferentialAmount;

    /**
     * 加价购活动ID
     */
    private Long marketingId;
}
