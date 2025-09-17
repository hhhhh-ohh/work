package com.wanmi.sbc.marketing.bargaingoods.model.root;

import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.DeleteFlag;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>砍价商品实体类</p>
 *
 * @author
 * @date 2022-05-20 09:59:19
 */
@Data
@Entity
@Table(name = "bargain_goods")
@DynamicInsert
public class BargainGoods implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * bargainGoodsId
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bargain_goods_id")
    private Long bargainGoodsId;

    /**
     * goodsInfoId
     */
    @Column(name = "goods_info_id")
    private String goodsInfoId;

    /**
     * goodsInfoName
     */
    @Column(name = "goods_info_name")
    private String goodsInfoName;

    /**
     * goodsInfoNo
     */
    @Column(name = "goods_info_no")
    private String goodsInfoNo;

    /**
     * goodsCateId
     */
    @Column(name = "goods_cate_id")
    private Long goodsCateId;

    /**
     * 市场价
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 商家编号
     */
    @Column(name = "company_code")
    private String companyCode;

    /**
     * 店铺Id
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 帮砍金额
     */
    @Column(name = "bargain_price")
    private BigDecimal bargainPrice;

    /**
     * 帮砍人数
     */
    @Column(name = "target_join_num")
    private Integer targetJoinNum;

    /**
     * 砍价库存
     */
    @Column(name = "bargain_stock")
    private Long bargainStock;

    /**
     * 剩余库存
     */
    @Column(name = "leave_stock")
    private Long leaveStock;

    /**
     * 驳回原因
     */
    @Column(name = "reason_for_rejection")
    private String reasonForRejection;

    /**
     * 审核状态，0：待审核，1：已审核，2：审核失败
     */
    @Column(name = "audit_status")
    @Enumerated
    private AuditStatus auditStatus;

    /**
     * 活动开始时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "begin_time")
    private LocalDateTime beginTime;

    /**
     * 活动结束时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @Column(name = "end_time")
    private LocalDateTime endTime;

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

    /**
     * 是否手动停止，0，否，1，是
     */
    @Column(name = "stoped")
    private Boolean stoped;

    /**
     * 是否包邮
     */
    @Column(name = "freight_free_flag")
    @Enumerated
    private DeleteFlag freightFreeFlag;

    /**
     * 商品可售状态
     */
    @Column(name = "goods_status")
    @Enumerated
    private DeleteFlag goodsStatus;

    /**
     * delFlag
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    /**
     * 新用户权重
     */
    @Column(name = "new_user_weight")
    private Double newUserWeight;

    /**
     * 老用户权重
     */
    @Column(name = "old_user_weight")
    private Double oldUserWeight;

}