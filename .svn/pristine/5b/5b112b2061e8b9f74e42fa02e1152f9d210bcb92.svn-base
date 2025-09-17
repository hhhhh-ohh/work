package com.wanmi.sbc.marketing.gift.model.root;

import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * 满赠
 */
@Entity
@Table(name = "marketing_full_gift_detail")
@Data
public class MarketingFullGiftDetail implements Serializable {

    /**
     *  满赠赠品Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_detail_id")
    private Long giftDetailId;

    /**
     *  满赠多级促销Id
     */
    @Column(name = "gift_level_id")
    private Long giftLevelId;

    /**
     *  赠品Id
     */
    @Column(name = "product_id")
    private String productId;

    /**
     *  赠品数量
     */
    @Column(name = "product_num")
    private Long productNum;

    /**
     *  满赠ID
     */
    @Column(name = "marketing_id")
    private Long marketingId;

    /**
     *  赠品库存
     */
    @Column(name = "product_stock")
    private Integer productStock;

}
