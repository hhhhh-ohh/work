package com.wanmi.sbc.order.bean.dto;


import com.wanmi.sbc.account.bean.enums.PayType;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.goods.bean.enums.DeliverWay;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>按店铺拆分订单提交信息</p>
 * Created by of628-wenzhi on 2017-11-24-下午3:43.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class StoreCommitInfoDTO extends BaseRequest {

    private static final long serialVersionUID = -1980544654461057449L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 支付类型，默认在线支付
     */
    @Schema(description = "支付类型")
    @NotNull
    private PayType payType = PayType.OFFLINE;

    /**
     * 开票类型，必传 0：普通发票 1：增值税发票 -1：无
     */
    @Schema(description = "开票类型", required = true)
    private Integer invoiceType;

    /**
     * 普通发票与增票参数，如果需要开票则至少一项必传
     */
    @Schema(description = "普通发票与增票参数,如果需要开票则至少一项必传")
    private GeneralInvoiceDTO generalInvoice;

    /**
     * 增值税发票，如果需要开票则与普票至少一项必传
     */
    @Schema(description = "增值税发票,如果需要开票则与普票至少一项必传")
    private SpecialInvoiceDTO specialInvoice;

    /**
     * 开票项目id，如果需要开票则必传
     */
    @Schema(description = "开票项目id,如果需要开票则必传")
    private String invoiceProjectId;

    /**
     * 开票项目名称，如果需要开票则必传
     */
    @Schema(description = "开票项目名称,如果需要开票则必传")
    private String invoiceProjectName;

    /**
     * 开票项修改时间
     */
    @Schema(description = "开票项修改时间")
    private String invoiceProjectUpdateTime;

    /**
     * 是否单独的收货地址,默认：否
     */
    @Schema(description = "是否单独的收货地址", contentSchema = com.wanmi.sbc.common.enums.BoolFlag.class)
    private boolean specialInvoiceAddress;

    /**
     * 收货地址详细信息（包含省市区），如果需要开票,则必传
     */
    @Schema(description = "收货地址详细信息（包含省市区）,如果需要开票,则必传")
    private String invoiceAddressDetail;

    /**
     * 发票的收货地址ID,如果需要开票,则必传
     */
    @Schema(description = "发票的收货地址ID,如果需要开票,则必传")
    private String invoiceAddressId;

    /**
     * 发票收货地址修改时间，可空
     */
    @Schema(description = "发票收货地址修改时间")
    private String invoiceAddressUpdateTime;

    /**
     * 订单备注
     */
    @Schema(description = "订单备注")
    private String buyerRemark;

    /**
     * 附件, 逗号隔开
     */
    @Schema(description = "附件,逗号隔开")
    private String encloses;

    /**
     * 配送方式，默认快递
     */
    @Schema(description = "配送方式")
    private DeliverWay deliverWay = DeliverWay.EXPRESS;

    /**
     * 选择的店铺优惠券id
     */
    @Schema(description = "选择的店铺优惠券id")
    private String couponCodeId;

    /**
     * 运费券Id
     */
    @Schema(description = "运费券Id")
    private String freightCouponCodeId;

    /**
     * 店铺分销设置开关状态
     */
    private DefaultFlag storeOpenFlag = DefaultFlag.NO;

}
