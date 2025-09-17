package com.wanmi.sbc.marketing.fullreturn.model.root;

import lombok.Data;

import jakarta.persistence.*;

/**
 * 满返
 */
@Entity
@Table(name = "marketing_full_return_store")
@Data
public class MarketingFullReturnStore {

    /**
     *  满返店铺关联Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "return_store_id")
    private Long returnStoreId;

    /**
     *  店铺Id
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     *  满返ID
     */
    @Column(name = "marketing_id")
    private Long marketingId;

}
