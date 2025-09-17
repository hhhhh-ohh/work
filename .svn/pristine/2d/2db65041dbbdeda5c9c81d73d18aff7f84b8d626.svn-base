package com.wanmi.sbc.goods.goodscommissionconfig.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.bean.enums.CommissionFreightBearFlag;
import com.wanmi.sbc.goods.bean.enums.CommissionSynPriceType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * @description 商品代销设置
 * @author  wur
 * @date: 2021/9/9 14:41
 **/
@Data
@Entity
@Table(name = "goods_commission_config")
public class GoodsCommissionConfig extends BaseEntity {
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
     * 设价类型：0.智能设价 1.手动设价
     */
    @Column(name = "syn_price_type")
    private CommissionSynPriceType synPriceType;

    /**
     * 默认加价比例
     */
    @Column(name = "addRate")
    private BigDecimal addRate;

    /**
     * 低价是否自动下架：0.关 1.开
     */
    @Column(name = "under_flag")
    private DefaultFlag underFlag;

    /**
     * 商品信息自动同步：0.关 1.开
     */
    @Column(name = "info_syn_flag")
    private DefaultFlag infoSynFlag;

    /**
     * 代销商品运费承担：0.买家 1.卖家
     */
    @Column(name = "freight_bear_flag")
    private CommissionFreightBearFlag freightBearFlag;

    @Column(name = "del_flag")
    private DeleteFlag delFlag;

}
