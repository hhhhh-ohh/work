package com.wanmi.sbc.elastic.settlement.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.LakalaLedgerStatus;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author yangzhen @Description //结算单 @Date 10:21 2020/12/14 @Param
 *
 * @return
 */
@Document(indexName = EsConstants.DOC_LAKALA_SETTLEMENT)
@Data
public class EsLakalaSettlement {

    @Id private String id;

    private Long settleId;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime startTime;

    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    private Long storeId;

    private StoreType storeType;

    private BigDecimal totalAmount;

    private BigDecimal totalProviderPrice;

    private BigDecimal totalStorePrice;

    private BigDecimal totalCommissionPrice;

    private BigDecimal totalPlatformPrice;

    private BigDecimal totalSplitPayPrice;

    private BigDecimal totalCommonCouponPrice;

    private Long totalPoints;

    private BigDecimal pointPrice;

    private BigDecimal providerGoodsTotalPrice;

    private BigDecimal providerDeliveryTotalPrice;

    private BigDecimal deliveryPrice;

    private LakalaLedgerStatus lakalaLedgerStatus;

    private String storeName;

    private String settlementCode;

    private String companyCode;

    private String settleUuid;
}
