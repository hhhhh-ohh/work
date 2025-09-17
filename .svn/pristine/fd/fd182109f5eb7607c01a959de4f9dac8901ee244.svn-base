package com.wanmi.sbc.marketing.giftcard.model.root;

import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * @author wur
 * @className GiftCardScopes
 * @description 礼品卡对应商品关联表
 * @date 2022/12/8 16:17
 **/
@Entity
@Table(name = "gift_card_scope")
@Data
public class GiftCardScope implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_card_scope_id")
    private Long giftCardScopeId;

    /**
     *  礼品卡Id
     */
    @Column(name = "gift_card_id")
    private Long giftCardId;

    /**
     *  目标资源Id
     */
    @Column(name = "scope_id")
    private String scopeId;
}