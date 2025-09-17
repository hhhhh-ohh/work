package com.wanmi.sbc.marketing.bargainjoin.model.root;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>帮砍记录实体类</p>
 *
 * @author
 * @date 2022-05-20 10:09:03
 */
@Data
@Entity
@Table(name = "bargain_join")
@DynamicInsert
public class BargainJoin implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * bargainJoinId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bargain_join_id")
    private Long bargainJoinId;

    /**
     * 砍价记录id
     */
    @Column(name = "bargain_id")
    private Long bargainId;

    /**
     * 砍价商品id
     */
    @Column(name = "goods_info_id")
    private String goodsInfoId;

    /**
     * 砍价的发起人
     */
    @Column(name = "customer_id")
    private String customerId;

    /**
     * 帮砍人id
     */
    @Column(name = "join_customer_id")
    private String joinCustomerId;

    /**
     * 砍价随机语
     */
    @Column(name = "bargin_goods_random_words")
    private String barginGoodsRandomWords;

    /**
     * 帮砍金额
     */
    @Column(name = "bargain_amount")
    private BigDecimal bargainAmount;

    /**
     * createTime
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "create_time")
    private LocalDateTime createTime;

}