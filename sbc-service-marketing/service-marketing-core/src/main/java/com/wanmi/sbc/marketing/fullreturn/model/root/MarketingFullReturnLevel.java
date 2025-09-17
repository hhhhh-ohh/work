package com.wanmi.sbc.marketing.fullreturn.model.root;

import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 满返
 */
@Entity
@Table(name = "marketing_full_return_level")
@Data
public class MarketingFullReturnLevel {

    /**
     *  满返多级促销Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_level_id")
    private Long returnLevelId;

    /**
     *  满返Id
     */
    @Column(name = "marketing_id")
    private Long marketingId;

    /**
     *  满金额赠
     */
    @Column(name = "full_amount")
    private BigDecimal fullAmount;

    /**
     * 满返赠品明细
     */
    @Transient
    private List<MarketingFullReturnDetail> fullReturnDetailList;
}
