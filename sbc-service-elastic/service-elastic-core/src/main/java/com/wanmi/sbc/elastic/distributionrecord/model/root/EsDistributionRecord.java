package com.wanmi.sbc.elastic.distributionrecord.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;
import com.wanmi.sbc.customer.bean.vo.DistributionCustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSpecDetailRelVO;
import com.wanmi.sbc.marketing.bean.enums.CommissionReceived;
import com.wanmi.sbc.marketing.bean.vo.GoodsInfoForDistribution;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>DistributionRecord实体类</p>
 *
 * @author baijz
 * @date 2019-02-27 18:56:40
 */
@Data
@Document(indexName = EsConstants.DISTRIBUTION_RECORD)
public class EsDistributionRecord  {

    /**
     * 分销记录表主键
     */
    @Id
    private String recordId;

    /**
     * 货品Id
     */
    private String goodsInfoId;

    /**
     * 订单交易号
     */
    private String tradeId;

    /**
     * 店铺Id
     */
    private Long storeId;

    /**
     * 商家Id
     */
    private Long companyId;

    /**
     * 会员Id
     */
    private String customerId;

    /**
     * 分销员Id
     */
    private String distributorId;

    /**
     * 付款时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime payTime;

    /**
     * 订单完成时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime finishTime;

    /**
     * 佣金入账时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime missionReceivedTime;

    /**
     * 订单商品总金额
     */
    private BigDecimal orderGoodsPrice;

    /**
     * 商品的数量
     */
    private Long orderGoodsCount;

    /**
     * 佣金比例
     */
    private BigDecimal commissionRate;

    /**
     * 货品的总佣金
     */
    private BigDecimal commissionGoods;

    /**
     * 是否已入账 0：否  1：是
     */
    @Enumerated
    private CommissionReceived commissionState;

    /**
     * 是否删除 0：未删除  1：已删除
     */
    @Enumerated
    private DeleteFlag deleteFlag;

    private String distributorCustomerId;

    /**
     * 规格值信息
     */
    @Transient
    private List<GoodsInfoSpecDetailRelVO> goodsInfoSpecDetailRelVOS;

    /**
     * 分销记录使用的货品信息
     */
    @Transient
    private GoodsInfoForDistribution goodsInfo;

    /**
     * 会员信息
     */
    @Transient
    private CustomerDetailVO customerDetailVO;

    /**
     * 分销员信息
     */
    @Transient
    private DistributionCustomerVO distributionCustomerVO;

    /**
     * 店铺信息
     */
    @Transient
    private StoreVO storeVO;

    /**
     * 商家信息
     */
    @Transient
    private CompanyInfoVO companyInfoVO;

}
