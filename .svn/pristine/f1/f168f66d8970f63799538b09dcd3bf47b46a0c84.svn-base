package com.wanmi.sbc.elastic.standard.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品库实体类
 * Created by dyt on 2017/4/11.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = EsConstants.DOC_STANDARD_GOODS)
public class EsStandardGoods {

    /**
     * 商品编号，采用UUID
     */
    @Id
    private String goodsId;

    /**
     * 分类编号
     */
    private Long cateId;

    /**
     * 品牌编号
     */
    private Long brandId;

    /**
     * SPU编码
     */
    private String goodsNo;

    /**
     * SKU编码
     */
    private List<String> goodsInfoNos;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品副标题
     */
    private String goodsSubtitle;

    /**
     * 计量单位
     */
    private String goodsUnit;

    /**
     * 商品主图
     */
    private String goodsImg;

    /**
     * 商品重量
     */
    private BigDecimal goodsWeight;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    /**
     * 成本价
     */
    private BigDecimal costPrice;

    /**
     * 供货价
     */
    private BigDecimal supplyPrice;

    /**
     * 建议零售价
     */
    private BigDecimal recommendedRetailPrice;

    /**
     * 创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    @Enumerated
    private DeleteFlag delFlag;

    /**
     * 是否多规格标记
     */
    private Integer moreSpecFlag;

    /**
     * 商品详情
     */
    private String goodsDetail;

    /**
     * 商品移动端详情
     */
    private String goodsMobileDetail;

    /**
     * 一对多关系，多个SKU编号
     */
    private List<String> goodsInfoIds;

    /**
     * 商品体积 单位：m3
     */
    private BigDecimal goodsCubage;

    /**
     * 商品视频链接
     */
    private String goodsVideo;

    /**
     * 供应商名称
     */
    private String providerName;

    /**
     * 商品来源，0供应商，1商家,2 linkedmall，3京东vop
     */
    private Integer goodsSource;

    /**
     * 删除原因
     */
    private String deleteReason;

    /**
     * 第三方平台的spuId
     */
    private String thirdPlatformSpuId;
    /**
     * 第三方卖家id
     */
    private Long sellerId;

    /**
     * 三方渠道类目id
     */
    private Long thirdCateId;

    /**
     * 上下架状态,0:下架1:上架2:部分上架
     */
    private Integer addedFlag;

    /**
     * 店铺id
     */
    private Long storeId;

    private long stock;

    private String providerGoodsId;

    /**
     * 已导入的店铺id
     */
    private List<Long> relStoreIds;

    /**
     * 商品类型 0、实物商品 1、虚拟商品 2、电子卡券
     */
    private Integer goodsType;
}
