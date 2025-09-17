package com.wanmi.sbc.goods.flashsaleRecord.model.root;

import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>抢购商品表实体类</p>
 *
 * @author xufeng
 * @date 2021-07-12 14:54:31
 */
@Data
@Entity
@Table(name = "flash_sale_record")
public class FlashSaleRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 会员ID
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * skuID
     */
    @Column(name = "goods_info_id")
    private String goodsInfoId;

    /**
     * 购买的数量
     */
    @Column(name = "purchase_num")
    private Long purchaseNum;

    /**
     * 秒杀商品主键
     */
    @Column(name = "flash_goods_id")
    private Long flashGoodsId;

    /**
     * 创建时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

}