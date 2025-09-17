package com.wanmi.sbc.empower.deliveryrecord.request;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>达达配送订单请求</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DadaOrderRequest extends DadaBaseRequest {

	/**
	 * 订单号
	 */
    @JSONField(name = "origin_id")
	private String orderNo;

	/**
	 * 第三方店铺编码
	 */
	@JSONField(name = "shop_no")
	private String shopNo;

	/**
	 * 城市编码
	 */
	@JSONField(name = "city_code")
	private String cityCode;

	/**
	 * 订单金额;不含配送费
	 */
	@JSONField(name = "cargo_price")
	private BigDecimal cargoPrice;

	/**
	 * 是否需要垫付;1:是 0:否 (垫付订单金额，非运费)
	 */
	@JSONField(name = "is_prepay")
	private Integer isPrepay;

	/**
	 * 是否使用保价费;0:不使用,1:使用
	 */
	@JSONField(name = "is_use_insurance")
	private Integer isUseInsurance;

	/**
	 * 收货人姓名
	 */
	@JSONField(name = "receiver_name")
	private String receiverName;

	/**
	 * 收货人地址
	 */
	@JSONField(name = "receiver_address")
	private String receiverAddress;

    /**
     * 收货人电话
     */
    @JSONField(name = "receiver_phone")
    private String receiverPhone;

    /**
     * 收货人地址维度（高德坐标系）
     */
    @JSONField(name = "receiver_lat")
    private BigDecimal receiverLat;

    /**
     * 收货人地址经度（高德坐标系）
     */
    @JSONField(name = "receiver_lng")
    private BigDecimal receiverLng;

	/**
	 * 订单重量
	 */
	@JSONField(name="cargo_weight")
	private BigDecimal cargoWeight;

    /**
     * 回调URL
     */
    @JSONField(name = "callback")
    private String callback;

    /**
     * 收货码（0：不需要；1：需要。收货码的作用是：骑手必须输入收货码才能完成订单妥投）
     */
    @JSONField(name = "is_finish_code_needed")
    private Integer isFinishCodeNeeded;
}