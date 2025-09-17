package com.wanmi.sbc.goods.distributionmatter.model.root;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.StoreState;
import com.wanmi.sbc.goods.bean.enums.MatterType;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分销素材实体类
 */
@Data
@Entity
@Table(name = "distribution_goods_matter")
public class DistributionGoodsMatter implements Serializable {

    private static final long serialVersionUID = 4219012659594080251L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;

//    /**
//     * 商品sku的id
//     */
//    @Column(name = "goods_info_id")
//    private String goodsInfoId;

    /**
     * 素材类型 0 图片 1 视频
     */
    @Enumerated
    @Column(name = "matter_type")
    private MatterType matterType;

    /**
     * 素材
     */
    @Column(name = "matter")
    private String matter;

    /**
     * 推荐语
     */
    @Column(name = "recommend")
    private String recommend;

    /**
     * 推荐次数
     */
    @Column(name = "recommend_num")
    private Integer recommendNum;

    /**
     * 发布者id
     */
    @Column(name = "operator_id")
    private String operatorId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 删除标志
     */
    @Column(name = "del_flag")
    @Enumerated
    private DeleteFlag delFlag = DeleteFlag.NO;

    /**
     * 删除时间
     */
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @Column(name = "del_time")
    private LocalDateTime deleteTime;

    /**
     * 商品信息
     */
    @ManyToOne
    @JoinColumn(name = "goods_info_id")
    @JsonBackReference
    @JSONField(serialize = false)
    private GoodsInfo goodsInfo;

    /**
     * 店铺Id
     */
    @Column(name = "store_id")
    private Long storeId;

    /**
     * 是否开启社交分销 0：关闭，1：开启
     */
    @Column(name = "open_flag")
    private DefaultFlag openFlag;

    /**
     * 店铺状态 0、开启 1、关店
     */
    @Column(name = "store_state")
    private StoreState storeState;

    /**
     * 签约结束日期
     */
    @Column(name = "contract_end_date")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;
}
