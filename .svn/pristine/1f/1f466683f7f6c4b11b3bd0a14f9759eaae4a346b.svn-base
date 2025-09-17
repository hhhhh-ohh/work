package com.wanmi.sbc.elastic.pointsgoods.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ES积分商品实体类
 * 以SKU维度
 * Created by dyt on 2017/4/21.
 */
@Document(indexName = EsConstants.DOC_POINTS_GOODS_INFO_TYPE)
@Data
public class EsPointsGoods implements Serializable {

    @Id
    private String id;

    /**
     * 店铺ID
     */
    private Long storeId;

    /**
     * 商品spuId
     */
    private String goodsId;

    /**
     * 商品skuId
     */
    private String goodsInfoId;

    /**
     * 商品SKU编码
     */
    private String goodsInfoNo;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品SKU名称
     */
    private String goodsInfoName;

    /**
     * 商品市场价
     */
    private BigDecimal marketPrice;


    /**
     * 可售状态 0不可售 1可售
     */
    private Integer vendibilityStatus;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 积分商品-是否启用 0:停用 1:启用
     */
    private Integer status;

    /**
     * 积分商品-推荐标价, 0:未推荐 1:已推荐
     */
    private Integer recommendFlag;

    /**
     * 积分商品-分类
     */
    private Integer cateId;

    /**
     * 积分商品-兑换积分
     */
    private Long points;

    /**
     * 积分商品-积分商品可兑换上限
     */
    private Long pointsStock;

    /**
     * 库存
     */
    private Long stock;

    /**
     * 积分商品-销量
     */
    private Long sales;

    /**
     * 结算价格
     */
    @Schema(description = "结算价格")
    private BigDecimal settlementPrice;

    /**
     * 积分商品-创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 积分商品-兑换开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    /**
     * 积分商品-兑换结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 上下架状态
     */
    private Integer addedFlag;

    /**
     * 供应商Id
     */
    private Long providerId;

    /**
     * 供应商店铺状态 0：关店 1：开店
     */
    private Integer providerStatus;

    /**
     * 商品sku删除状态 0：未删除 1：已删除
     */
    private Integer goodsDelFlag;

    /**
     * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
     */
    private Integer goodsType;
}
