package com.wanmi.sbc.empower.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>达达配送记录VO</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
@Schema
@Data
public class DeliveryRecordDadaVO implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
     * 配送编号
     */
    @Schema(description = "配送编号")
    private String deliveryId;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	private String orderNo;

	/**
	 * 城市编码
	 */
	@Schema(description = "城市编码")
	private String cityCode;

	/**
	 * 订单金额;不含配送费
	 */
	@Schema(description = "订单金额;不含配送费")
	private BigDecimal cargoPrice;

	/**
	 * 是否需要垫付;1:是 0:否 (垫付订单金额，非运费)
	 */
	@Schema(description = "是否需要垫付;1:是 0:否 (垫付订单金额，非运费)")
	private Integer isPrepay;

	/**
	 * 配送距离;单位:米
	 */
	@Schema(description = "配送距离;单位:米")
	private BigDecimal distance;

	/**
	 * 实际运费
	 */
	@Schema(description = "实际运费")
	private BigDecimal fee;

	/**
	 * 运费
	 */
	@Schema(description = "运费")
	private BigDecimal deliverFee;

	/**
	 * 小费
	 */
	@Schema(description = "小费")
	private BigDecimal tipsFee;

	/**
	 * 是否使用保价费;0:不使用,1:使用
	 */
	@Schema(description = "是否使用保价费;0:不使用,1:使用")
	private Integer isUseInsurance;

	/**
	 * 保价费
	 */
	@Schema(description = "保价费")
	private BigDecimal insuranceFee;

    /**
     * 违约金
     */
    @Schema(description = "deduct_fee")
    private BigDecimal deductFee;

	/**
	 * 0:接受订单1:待接单2:待取货3:配送中4:已完成5:已取消7:已过期8:指派单9:返回妥投异常中10:妥投异常完成100:骑士到店1000:创建达达运单失败
	 */
	@Schema(description = "0:接受订单1:待接单2:待取货3:配送中4:已完成5:已取消7:已过期8:指派单9:返回妥投异常中10:妥投异常完成100:骑士到店1000:创建达达运单失败")
	private Integer deliveryStatus;

    /**
     * 收货人姓名
     */
    @Schema(description = "收货人姓名")
    private String receiverName;

    /**
     * 收货人地址
     */
    @Schema(description = "收货人地址")
    private String receiverAddress;

    /**
     * 收货人电话
     */
    @Schema(description = "收货人电话")
    private String receiverPhone;

    /**
     * 收货人地址维度（高德坐标系）
     */
    @Schema(description = "收货人地址维度")
    private BigDecimal receiverLat;

    /**
     * 收货人地址经度（高德坐标系）
     */
    @Schema(description = "收货人地址经度")
    private BigDecimal receiverLng;

    /**
     * 取消理由id
     */
    @Schema(description = "取消理由id")
    private Integer cancelReasonId;

    /**
     * 取消理由
     */
    @Schema(description = "其他取消理由")
    private String cancelReason;

    /**
     * 配送员姓名
     */
    @Schema(description = "配送员姓名")
    private String dmName;

    /**
     * 配送员手机号
     */
    @Schema(description = "配送员手机号")
    private String dmMobile;
}