package com.wanmi.sbc.empower.deliveryrecord.model.root;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.api.constant.DeliveryStatusConstant;

import jakarta.persistence.*;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>达达配送记录实体类</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
@Data
@Entity
@Table(name = "delivery_record_dada")
public class DeliveryRecordDada {
	private static final long serialVersionUID = 1L;

    /**
     * 配送编号
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "delivery_id")
    private String deliveryId;

	/**
	 * 订单号
	 */
	@Column(name = "order_code")
	private String orderNo;

	/**
	 * 城市编码
	 */
	@Column(name = "city_code")
	private String cityCode;

	/**
	 * 订单金额;不含配送费
	 */
	@Column(name = "cargo_price")
	private BigDecimal cargoPrice;

	/**
	 * 订单重量
	 */
	@JSONField(name="cargo_weight")
	private BigDecimal cargoWeight;

	/**
	 * 是否需要垫付;1:是 0:否 (垫付订单金额，非运费)
	 */
	@Column(name = "is_prepay")
	private Integer isPrepay;

	/**
	 * 配送距离;单位:米
	 */
	@Column(name = "distance")
	private BigDecimal distance;

	/**
	 * 实际运费
	 */
	@Column(name = "fee")
	private BigDecimal fee;

	/**
	 * 运费
	 */
	@Column(name = "deliver_fee")
	private BigDecimal deliverFee;

	/**
	 * 小费
	 */
	@Column(name = "tips_fee")
	private BigDecimal tipsFee;

	/**
	 * 是否使用保价费;0:不使用,1:使用
	 */
	@Column(name = "is_use_insurance")
	private Integer isUseInsurance;

	/**
	 * 保价费
	 */
	@Column(name = "insurance_fee")
	private BigDecimal insuranceFee;

    /**
     * 违约金
     */
    @Column(name = "deduct_fee")
    private BigDecimal deductFee;

	/**
	 * @see DeliveryStatusConstant
	 * 0:接受订单1:待接单2:待取货3:配送中4:已完成5:已取消7:已过期8:指派单9:返回妥投异常中10:妥投异常完成100:骑士到店1000:创建达达运单失败
	 */
	@Column(name = "delivery_status")
	private Integer deliveryStatus;

    /**
     * 收货人姓名
     */
    @Column(name = "receiver_name")
    private String receiverName;

    /**
     * 收货人地址
     */
    @Column(name = "receiver_address")
    private String receiverAddress;

    /**
     * 收货人电话
     */
    @Column(name = "receiver_phone")
    private String receiverPhone;

    /**
     * 收货人地址维度（高德坐标系）
     */
    @Column(name = "receiver_lat")
    private BigDecimal receiverLat;

    /**
     * 收货人地址经度（高德坐标系）
     */
    @Column(name = "receiver_lng")
    private BigDecimal receiverLng;

    /**
     * 取消理由id
     */
    @Column(name = "cancel_reason_id")
    private Integer cancelReasonId;

    /**
     * 其他取消理由
     */
    @Column(name = "cancel_reason")
    private String cancelReason;

    /**
     * 配送员姓名
     */
    @Column(name = "dm_name")
    private String dmName;

    /**
     * 配送员手机号
     */
    @Column(name = "dm_mobile")
    private String dmMobile;

    /**
     * 店铺编号
     */
    @Transient
    private Long storeId;

    /**
     * 城市编号
     */
    @Transient
    private String cityName;

	/**
	 * 删除标识;0:未删除1:已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

    /**
     * 达达回调配送时间
     */
    @Column(name = "dada_update_time")
    private Long dadaUpdateTime;

	/**
	 * 创建时间
	 */
	@CreatedDate
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@Column(name = "create_person")
	private String createPerson;

	/**
	 * 更新时间
	 */
	@LastModifiedDate
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Column(name = "update_time")
	private LocalDateTime updateTime;

	/**
	 * 更新人
	 */
	@Column(name = "update_person")
	private String updatePerson;
}