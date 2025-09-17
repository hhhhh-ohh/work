package com.wanmi.sbc.empower.api.request.deliveryrecord;

import com.alibaba.fastjson2.annotation.JSONField;
import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * <p>达达配送记录查询配送费参数</p>
 *
 * @author zhangwenchang
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecordDadaDeliverFeeQueryRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺标识
     */
    @Schema(description = "店铺标识", hidden = true)
    private Long storeId;

    /**
     * 订单号
     */
    @Schema(description = "订单号")
    private String orderNo;

    /**
     * 收货地址城市名称
     */
    @Schema(description = "收货地址城市名称")
    @NotBlank
    private String cityName;

    /**
     * 城市编码
     */
    @Schema(description = "收货地址城市编码", hidden = true)
    private String cityCode;

    /**
     * 订单金额;不含配送费
     */
    @Schema(description = "订单金额;不含配送费")
    @NotNull
    private BigDecimal cargoPrice;

    /**
     * 是否使用保价费;0:不使用,1:使用
     */
    @Schema(description = "是否使用保价费;0:不使用,1:使用")
    @Range(min = 0, max = 1)
    private Integer isUseInsurance;

    /**
     * 收货人姓名
     */
    @Schema(description = "收货人姓名")
    @NotBlank
    private String receiverName;

    /**
     * 收货人地址
     */
    @Schema(description = "收货人地址")
    @NotBlank
    private String receiverAddress;

    /**
     * 收货人电话
     */
    @Schema(description = "收货人电话")
    @NotBlank
    private String receiverPhone;

    /**
     * 收货人地址维度（高德坐标系）
     */
    @Schema(description = "收货人地址维度")
    @NotNull
    private BigDecimal receiverLat;

    /**
     * 收货人地址经度（高德坐标系）
     */
    @Schema(description = "收货人地址经度")
    @NotNull
    private BigDecimal receiverLng;

    @Schema(description = "达达商户编号")
    private String sourceId;

    @Schema(description = "会员ID")
    @NotBlank
    private String customerId;

    /**
     * 第三方店铺编码
     */
    @JSONField(name = "shop_no")
    private String shopNo;
}