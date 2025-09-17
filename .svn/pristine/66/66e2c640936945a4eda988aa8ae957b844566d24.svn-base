package com.wanmi.sbc.goods.goodscommissionpriceconfig.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.goods.bean.enums.CommissionPriceTargetType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * @description  代销智能设价加价比例设置
 * @author  wur
 * @date: 2021/9/9 14:41
 **/
@Data
@Entity
@Table(name = "goods_commission_price_config")
public class GoodsCommissionPriceConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

    /**
     * 商家Id
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 目标类型 0：类目 1：SKU
     */
    @Column(name = "target_type")
    private CommissionPriceTargetType targetType;

    /**
     * 目标唯一标识
     */
    @Column(name = "target_id")
    private String targetId;

    /**
     * 加价比例
     */
    @Column(name = "add_rate")
    private BigDecimal addRate;

    /**
     * 是否开启
     */
    @Column(name = "enable_status")
    private EnableStatus enableStatus;

    @Column(name = "del_flag")
    private DeleteFlag delFlag;

}
