package com.wanmi.sbc.order.bean.vo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.account.bean.enums.PayOrderStatus;
import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.vo.CompanyInfoVO;
import com.wanmi.sbc.customer.bean.vo.CustomerDetailVO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Schema
public class PayOrderVO extends BasicResponse {

    @Schema(description = "付款单id")
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

    @Schema(description = "更新时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime delTime;

    @Schema(description = "删除标记")
    private DeleteFlag delFlag;

    @Schema(description = "客户信息")
    private CustomerDetailVO customerDetail;

    @Schema(description = "支付金额")
    private BigDecimal payOrderPrice;

    /**
     * 支付单礼品卡抵扣
     */
    @Schema(description = "支付单礼品卡抵扣")
    private BigDecimal giftCardPrice;

    @Schema(description = "收款单")
    @JsonManagedReference
    private ReceivableVO receivable;

    @Schema(description = "支付类型")
    private PayType payType;

    @Schema(description = "公司信息id")
    private Long companyInfoId;

    @Schema(description = "公司信息")
    private CompanyInfoVO companyInfo;
}

