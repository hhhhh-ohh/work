package com.wanmi.sbc.goods.standard.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.CanEmpty;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品库SKU实体类
 * Created by dyt on 2017/4/11.
 */
@Data
@Entity
@Table(name = "standard_sku")
public class StandardSku implements Serializable {

    /**
     * 商品SKU编号
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "goods_info_id")
    private String goodsInfoId;

    /**
     * 商品编号
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 商品SKU编码
     */
    @Column(name = "goods_info_no")
    private String goodsInfoNo;

    /**
     * 商品SKU名称
     */
    @Column(name = "goods_info_name")
    private String goodsInfoName;

    /**
     * 商品图片
     */
    @Column(name = "goods_info_img")
    @CanEmpty
    private String goodsInfoImg;

    /**
     * 商品市场价
     */
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    /**
     * 商品成本价
     */
    @CanEmpty
    @Column(name = "cost_price")
    private BigDecimal costPrice;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

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
     * 商品来源，0供应商，1商家,2 linkedmall,3 Platform,4 VOP
     */
    @Column(name = "goods_source")
    private Integer goodsSource;

    /**
     * 供应商商品sku
     */
    @Column(name = "provider_goods_info_id")
    private String providerGoodsInfoId;

    /**
     * 商品供货价
     */
    @Column(name = "supply_price")
    private BigDecimal supplyPrice;

    /**
     * 商品库存
     */
    @Column(name = "stock")
    private Long stock;

    /**
     * 商品参数
     */
    @CanEmpty
    @Column(name = "goods_param")
    private String goodsParam;

    /**
     * 条形码
     */
    @Column(name = "goods_info_barcode")
    private String goodsInfoBarcode;


    /**
     * 第三方平台的skuId
     */
    @Column(name = "third_platform_sku_id")
    private String thirdPlatformSkuId;

    /**
     * 第三方平台的spuId
     */
    @Column(name = "third_platform_spu_id")
    private String thirdPlatformSpuId;

    /**
     * 第三方卖家id
     */
    @Column(name = "seller_id")
    private Long sellerId;

    /**
     * 三方渠道类目id
     */
    @Column(name = "third_cate_id")
    private Long thirdCateId;

    /**
     * 上下架状态,0:下架1:上架2:部分上架
     */
    @Column(name = "added_flag")
    private Integer addedFlag;

    /**
     * 商品重量
     */
    @Column(name = "goods_weight")
    private BigDecimal goodsWeight;

    /**
     * 商品体积 单位：m3
     */
    @Column(name = "goods_cubage")
    private BigDecimal goodsCubage;


    /**
     * 商品类型，0:实体商品，1：虚拟商品 2：电子卡券
     */
    @Column(name = "goods_type")
    private Integer goodsType;

    /**
     * 订货号
     */
    @Column(name = "quick_order_no")
    private String quickOrderNo;
}
