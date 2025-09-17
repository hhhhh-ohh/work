package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.InvoiceState;
import com.wanmi.sbc.account.bean.enums.InvoiceType;
import com.wanmi.sbc.account.bean.enums.IsCompany;
import com.wanmi.sbc.account.bean.vo.InvoiceProjectVO;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-12-03 10:27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class OrderInvoiceVO extends BasicResponse {

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
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderNo;

    /**
     * 发票类型 0普通发票 1增值税专用发票 -1无
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
     * 开票项目id
     */
    @Schema(description = "开票项目id")
    private String projectId;

    /**
     * 删除标志
     */
    @Schema(description = "删除标志")
    private DeleteFlag delFlag;

    /**
     * 开票时间
     */
    @Schema(description = "开票时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime invoiceTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /**
     * 操作人
     */
    @Schema(description = "操作人")
    private String operateId;

    /**
     * 开票项目
     */
    @Schema(description = "开票项目")
    private InvoiceProjectVO invoiceProject;

    /**
     * 账务支付单
     */
    @Schema(description = "账务支付单")
    private PayOrderVO payOrder;

    /**
     * 是否是企业
     */
    @Schema(description = "是否是企业",contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private IsCompany isCompany;

    /**
     * 发票地址
     */
    @Schema(description = "发票地址")
    private String invoiceAddress;

    /**
     * 商家id
     */
    @Schema(description = "商家id")
    private Long companyInfoId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 纳税人识别号
     */
    @Schema(description = "纳税人识别号")
    private String taxpayerNumber;

}
