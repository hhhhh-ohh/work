package com.wanmi.sbc.setting.payadvertisement.model.root;

import lombok.Data;

import jakarta.persistence.*;

/**
 * @author 黄昭
 * @className PayAdvertisementStore
 * @description 广告页配置门店关联
 * @date 2022/4/6 10:52
 **/

@Data
@Entity
@Table(name = "pay_advertisement_store")
public class PayAdvertisementStore {

    /**
     * 支付广告门店关联id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 支付广告id
     */
    @Column(name = "pay_advertisement_id")
    private Long payAdvertisementId;

    /**
     * 门店id
     */
    @Column(name = "store_id")
    private Long storeId;
}