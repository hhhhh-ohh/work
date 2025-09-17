package com.wanmi.sbc.elastic.settlement.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.SettleStatus;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author yangzhen
 * @Description //结算单
 * @Date 10:21 2020/12/14
 * @Param
 * @return
 **/
@Document(indexName = EsConstants.DOC_SETTLEMENT)
@Data
public class EsSettlement {


    @Id
    private String id;

    /**
     * 用于生成结算单号，结算单号自增
     */
    private Long settleId;

    /**
     * 结算单创建时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 结算单号
     */
    private String settleCode;

    /**
     * 结算单开始时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    /**
     * 结算单结束时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    /**
     * 结算单结算时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime settleTime;

    /**
     * 商家Id
     */
    private Long storeId;

    /**
     * 商铺名称
     */
    private String storeName;

    /**
     * 交易总额
     */
    private BigDecimal salePrice;

    /**
     * 商品销售总数
     */
    private long saleNum;

    /**
     * 退款总额
     */
    private BigDecimal returnPrice;

    /**
     * 商品退货总数
     */
    private long returnNum;

    /**
     * 平台佣金总额
     */
    private BigDecimal platformPrice;

    /**
     * 店铺应收
     */
    private BigDecimal storePrice;

    /**
     * 商品供货总额
     */
    private BigDecimal providerPrice;

    /**
     * 总运费
     */
    private BigDecimal deliveryPrice;

    /**
     * 商品实付总额
     */
    private BigDecimal splitPayPrice;

    /**
     * 通用券优惠总额
     */
    private BigDecimal commonCouponPrice;

    /**
     * 积分抵扣总额
     */
    private BigDecimal pointPrice;

    /**
     * 分销佣金总额
     */
    private BigDecimal commissionPrice;

    /**
     * 结算状态
     */
    private SettleStatus settleStatus;

    /**
     * 商家类型
     */
    private StoreType storeType;

    /**
     * 总供货运费
     */
    private BigDecimal thirdPlatFormFreight;
}
