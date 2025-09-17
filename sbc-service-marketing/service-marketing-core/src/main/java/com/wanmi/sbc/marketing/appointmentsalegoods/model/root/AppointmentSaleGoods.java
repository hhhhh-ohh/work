package com.wanmi.sbc.marketing.appointmentsalegoods.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.marketing.appointmentsale.model.root.AppointmentSale;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Data;

/**
 * <p>预约抢购实体类</p>
 *
 * @author zxd
 * @date 2020-05-21 13:47:11
 */
@Data
@Entity
@Table(name = "appointment_sale_goods")
public class AppointmentSaleGoods extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 预约id
     */
    @Column(name = "appointment_sale_id")
    private Long appointmentSaleId;

    /**
     * 商户id
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * skuID
     */
    @Column(name = "goods_info_id")
    private String goodsInfoId;

    /**
     * spuID
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 预约价
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * 预约数量
     */
    @Column(name = "appointment_count")
    private Integer appointmentCount;

    /**
     * 购买数量
     */
    @Column(name = "buyer_count")
    private Integer buyerCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_sale_id", insertable = false, updatable = false)
    private AppointmentSale appointmentSale;

    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Column(name = "goods_type")
    private Integer goodsType;
}