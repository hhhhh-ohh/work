package com.wanmi.sbc.mq.report.entity;

import com.wanmi.sbc.common.base.BasicResponse;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.InvoiceState;
import com.wanmi.sbc.account.bean.enums.InvoiceType;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SignWordType;
import com.wanmi.sbc.common.sensitiveword.annotation.SensitiveWordsField;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.order.bean.enums.FlowState;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.Convert;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 订单开票返回列表
 * Created by CHENLI on 2017/5/5.
 */
@Schema
@Data
public class OrderInvoiceResponse extends BasicResponse {

    /**
     * 主键
     */
    @Schema(description = "主键")
    private String orderInvoiceId;

    /**
     * 客户ID
     */
    @Schema(description = "客户ID")
    private String customerId;

    /**
     * 客户名称
     */
    @Schema(description = "客户名称")
    @SensitiveWordsField(signType = SignWordType.NAME)
    private String customerName;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderNo;

    /**
     * 订单金额
     */
    @Schema(description = "订单金额")
    private BigDecimal orderPrice;

    /**
     * 付款状态0:已收款 1.未收款 2.待确认
     */
    @Schema(description = "付款状态")
    private PayOrderStatus payOrderStatus;

    /**
     * 流程状态（订单状态）
     */
    @Schema(description = "流程状态（订单状态）")
    private FlowState flowState;

    /**
     * 发票类型 0普通发票 1增值税专用发票
     */
    @Schema(description = "发票类型")
    private InvoiceType invoiceType;

    /**
     * 发票抬头
     */
    @Schema(description = "发票抬头")
    private String invoiceTitle;

    /**
     * 开票状态 0待开票 1 已开票
     */
    @Schema(description = "开票状态")
    private InvoiceState invoiceState;

    /**
     * 开票时间
     */
    @Schema(description = "开票时间")
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime invoiceTime;

    /**
     * 商家名称
     */
    @Schema(description = "商家名称")
    private String supplierName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;


    /**
     * 删除标识
     */
    @Schema(description = "删除标识")
    @Enumerated
    private DeleteFlag delFlag;


    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;


}
