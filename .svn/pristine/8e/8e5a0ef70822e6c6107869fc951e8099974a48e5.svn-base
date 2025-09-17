package com.wanmi.sbc.marketing.common.model.root;

import com.wanmi.sbc.marketing.common.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;

/**
 * 营销和商品关联关系
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "marketing_scope")
public class MarketingScope extends BaseBean {

    private static final long serialVersionUID = -4158843904500546310L;
    /**
     * 货品与促销规则表Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "marketing_scope_id")
    private Long marketingScopeId;

    /**
     * 促销Id
     */
    @Column(name = "marketing_id")
    private Long marketingId;

    /**
     * 促销范围Id
     */
    @Column(name = "scope_id")
    private String scopeId;
}
