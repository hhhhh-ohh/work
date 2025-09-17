package com.wanmi.sbc.marketing.bargain.model.root;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>砍价实体类</p>
 *
 * @author
 * @date 2022-05-20 09:14:05
 */
@Data
@MappedSuperclass
public class BargainBase implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * bargainId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bargain_id")
    private Long bargainId;

    /**
     * 砍价编号
     */
    @Column(name = "bargain_no")
    private Long bargainNo;

    /**
     * 砍价商品id
     */
    @Column(name = "bargain_goods_id")
    private Long bargainGoodsId;

    /**
     * goodsInfoId
     */
    @Column(name = "goods_info_id")
    private String goodsInfoId;

    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 发起时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "begin_time")
    private LocalDateTime beginTime;

    /**
     * 结束时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "end_time")
    private LocalDateTime endTime;

    /**
     * 发起人id
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 发起人账号
     */
    @Column(name = "customer_account")
    private String customerAccount;

    /**
     * 已砍人数
     */
    @Column(name = "join_num")
    private Integer joinNum;

    /**
     * 目标砍价人数
     */
    @Column(name = "target_join_num")
    private Integer targetJoinNum;

    /**
     * 已砍金额
     */
    @Column(name = "bargained_amount")
    private BigDecimal bargainedAmount;

    /**
     * 目标砍价金额
     */
    @Column(name = "target_bargain_price")
    private BigDecimal targetBargainPrice;

    /**
     * 订单号
     */
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "store_id")
    private Long storeId;

    /**
     * createTime
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * updateTime
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;


}