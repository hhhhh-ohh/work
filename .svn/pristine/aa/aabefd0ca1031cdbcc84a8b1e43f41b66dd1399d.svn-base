package com.wanmi.sbc.order.bean.dto;


import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;
import com.wanmi.sbc.marketing.bean.dto.TradeMarketingDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class ProviderTradeRemedyDTO extends BaseRequest {

    private static final long serialVersionUID = 7232157652419534899L;

    @Schema(description = "订单标识")
    private String tradeId;

    @Schema(description = "支付方式")
    private PayType payType;

    @Schema(description = "收货人Id")
    private String consigneeId;

    @Schema(description = "收货地址修改时间")
    private String consigneeUpdateTime;

    @Schema(description = "收货地址")
    private String consigneeAddress;

    @Schema(description = "发货清单")
    @NotEmpty
    @Valid
    private List<TradeItemDTO> tradeItems;

    @Schema(description = "新添加的商品")
    private List<String> newSkuIds;

    @Schema(description = "订单价格")
    private TradePriceDTO tradePrice;

    @Schema(description = "发票信息")
    private InvoiceDTO invoice;

    @Schema(description = "买家备注")
    private String buyerRemark;

    @Schema(description = "卖家备注")
    private String sellerRemark;

    @Schema(description = "订单附件")
    private String encloses;

    @NotNull
    @Schema(description = "配送方式")
    private DeliverWay deliverWay;

    @Schema(description = "收货人信息")
    private ConsigneeDTO consignee;

    @Schema(description = "发票收货人信息")
    private ConsigneeDTO invoiceConsignee;

    /**
     * 订单营销信息快照
     */
    @Schema(description = "订单营销信息快照")
    @NotNull
    private List<TradeMarketingDTO> tradeMarketingList;

    /**
     * 是否强制提交，用于营销活动有效性校验，true: 无效依然提交， false: 无效做异常返回
     */
    @Schema(description = "是否强制提交，用于营销活动有效性校验", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    public boolean forceCommit;

    /**
     * 是否拼团订单
     */
    @Schema(description = "是否拼团订单")
    private boolean grouponFlag;

    /**
     * 订单拼团信息
     */
    @Schema(description = "订单拼团信息")
    private TradeGrouponDTO tradeGroupon;
}
