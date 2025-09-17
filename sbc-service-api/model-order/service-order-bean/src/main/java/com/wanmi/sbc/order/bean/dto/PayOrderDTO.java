package com.wanmi.sbc.order.bean.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.dto.CompanyInfoDTO;
import com.wanmi.sbc.customer.bean.dto.CustomerDetailDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 账务支付单
 * Created by zhangjin on 2017/4/20.
 */
@Data
@Schema
public class PayOrderDTO implements Serializable{


    @Schema(description = "支付单标识")
    private String payOrderId;

    /**
     * 支付单号
     */
    @Schema(description = "支付单号")
    private String payOrderNo;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    private String orderCode;

    /**
     * 会员id
     */
    @Schema(description = "会员id")
    private String customerDetailId;

    /**
     * 支付单状态
     */
    @Schema(description = "支付单状态")
    private PayOrderStatus payOrderStatus;

    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime delTime;

    @Schema(description = "删除标识，0: 否, 1: 是")
    private DeleteFlag delFlag;

    @Schema(description = "用户详情")
    private CustomerDetailDTO customerDetail;

    /**
     * 支付单金额
     */
    @Schema(description = "支付单金额")
    private BigDecimal payOrderPrice;

    /**
     * 支付单礼品卡抵扣
     */
    @Schema(description = "支付单礼品卡抵扣")
    private BigDecimal giftCardPrice;

    @Schema(description = "收款单")
    @JsonManagedReference
    private ReceivableDTO receivable;

    /**
     * 支付方式
     */
    @Schema(description = "支付方式")
    private PayType payType;

    /**
     * 商家编号
     */
    @Schema(description = "商家编号")
    private Long companyInfoId;

    @Schema(description = "公司信息")
    private CompanyInfoDTO companyInfo;
}
