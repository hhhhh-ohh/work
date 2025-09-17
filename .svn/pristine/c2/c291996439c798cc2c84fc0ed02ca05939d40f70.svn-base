package com.wanmi.sbc.marketing.giftcard.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.ExpirationType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardContactType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardScopeType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardType;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wur
 * @className GiftCards
 * @description 礼品卡信息
 * @date 2022/12/8 16:16
 **/
@Entity
@Table(name = "gift_card")
@Data
public class GiftCard implements Serializable {

    /**
     *  礼品卡Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_card_id")
    private Long giftCardId;

    /**
     *  礼品卡名称
     */
    @Column(name = "name")
    private String name;

    /**
     *  封面类型 0：指定颜色 1:指定图片
     */
    @Column(name = "background_type")
    private DefaultFlag backgroundType;

    /**
     *  封面数值
     */
    @Column(name = "background_detail")
    private String backgroundDetail;

    /**
     *  面值
     */
    @Column(name = "par_value")
    private Long parValue;

    /**
     * 库存类型 是否限制 0： 有限制  1：无限制
     */
    @Column(name = "stock_type")
    @Enumerated
    private DefaultFlag stockType;

    /**
     * 剩余库存
     */
    @Column(name = "stock")
    private Long stock;

    /**
     * 最近编辑库存
     */
    @Column(name = "origin_stock")
    private Long originStock;

    /**
     * 已生成卡总数
     */
    @Column(name = "make_num")
    private Long makeNum;

    /**
     * 已发卡总数
     */
    @Column(name = "send_num")
    private Long sendNum;

    /**
     * 过期类型
     */
    @Column(name = "expiration_type")
    @Enumerated
    private ExpirationType expirationType;

    /**
     * 指定月数
     */
    @Column(name = "range_month")
    private Long rangeMonth;

    /**
     * 指定失效时间
     */
    @Column(name = "expiration_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime expirationTime;

    /**
     * 关联商品 0：全部 1:按品牌 2：按分类 3：按店铺 4：自定义商品
     */
    @Column(name = "scope_type")
    @Enumerated
    private GiftCardScopeType scopeType;

    /**
     * 使用说明
     */
    @Column(name = "use_desc")
    private String useDesc;

    /**
     * 客服类型
     */
    @Column(name = "contact_type")
    @Enumerated
    private GiftCardContactType contactType;

    /**
     * 客服联系方式
     */
    @Column(name = "contact_phone")
    private String contactPhone;

    /**
     * 创建人
     */
    @Column(name = "create_person")
    private String createPerson;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @Column(name = "update_person")
    private String updatePerson;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag;

    /**
     * 礼品卡类型
     */
    @Column(name = "gift_card_type")
    @Enumerated
    private GiftCardType giftCardType;

    /**
     * 适用商品数量
     */
    @Column(name = "scope_goods_num")
    private Integer scopeGoodsNum;
}