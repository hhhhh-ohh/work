package com.wanmi.sbc.goods.suppliercommissiongoods.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

/**
 * @description 商家与代销商品关联表
 * @author  wur
 * @date: 2021/9/9 14:41
 **/
@Data
@Entity
@Table(name = "supplier_commission_goods")
public class SupplierCommissionGood extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /**
     * 商家ID
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 商品Id
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 是否已同步：0：否，1：是
     */
    @Column(name = "syn_status")
    private DefaultFlag synStatus;

    @Column(name = "del_flag")
    private DeleteFlag delFlag;

    /**
     * 供应商商品Id
     */
    @Column(name = "provider_goods_id")
    private String providerGoodsId;

    /**
     * 是否有更新：0：否，1：是     用于方便商家变更记录查询使用
     */
    @Column(name = "update_flag")
    private DefaultFlag updateFlag;
}
