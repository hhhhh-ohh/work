package com.wanmi.sbc.order.payingmemberpayrecord.model.root;

import java.math.BigDecimal;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

/**
 * <p>付费会员支付记录表实体类</p>
 * @author zhanghao
 * @date 2022-05-13 15:29:08
 */
@Data
@Entity
@Table(name = "paying_member_pay_record")
public class PayingMemberPayRecord extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;

	/**
	 * 业务id
	 */
	@Column(name = "business_id")
	private String businessId;

	/**
	 * chargeId
	 */
	@Column(name = "charge_id")
	private String chargeId;

	/**
	 * 申请价格
	 */
	@Column(name = "apply_price")
	private BigDecimal applyPrice;

	/**
	 * 实际成功交易价格
	 */
	@Column(name = "practical_price")
	private BigDecimal practicalPrice;

	/**
	 * 状态:0处理中(退款状态)/未支付(支付状态) 1成功 2失败
	 */
	@Column(name = "status")
	private Integer status;

	/**
	 * 支付渠道项id
	 */
	@Column(name = "channel_item_id")
	private Integer channelItemId;

	/**
	 * 回调时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "callback_time")
	private LocalDateTime callbackTime;

	/**
	 * 交易完成时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "finish_time")
	private LocalDateTime finishTime;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;



}
