package com.wanmi.sbc.elastic.orderinvoice.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.EsConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author houshuai
 * @date 2020/12/29 19:17
 * @description <p> 订单开票信息 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = EsConstants.ORDER_INVOICE)
public class EsOrderInvoice  {

    /**
     * 主键
     */
    @Id
    private String orderInvoiceId;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单金额
     */
    private BigDecimal orderPrice;

    /**
     * 付款状态0:已收款 1.未收款 2.待确认
     */
    private Integer payOrderStatus;

    /**
     * 流程状态（订单状态）
     * 枚举类：com.wanmi.sbc.order.bean.enums.FlowState
     */
    private String flowState;

    /**
     * 发票类型 0普通发票 1增值税专用发票
     */
    private Integer invoiceType;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 开票状态 0待开票 1 已开票
     */
    private Integer invoiceState;

    /**
     * 开票时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime invoiceTime;

    /**
     * 新增时间
     */
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 商家名称
     */
    private String supplierName;


    private Long storeId;

    /**
     * 门店/店铺名称
     */
    private String storeName;

    /**
     * 是否删除
     */
    @Enumerated
    private DeleteFlag delFlag;

}
