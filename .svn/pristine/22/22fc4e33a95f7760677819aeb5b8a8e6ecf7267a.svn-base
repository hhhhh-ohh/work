package com.wanmi.sbc.empower.logisticslog.model.root;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.empower.logisticslogdetail.model.root.LogisticsLogDetail;

import jakarta.persistence.*;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>物流记录实体类</p>
 * @author 宋汉林
 * @date 2021-04-13 17:21:25
 */
@Data
@Entity
@Table(name = "logistics_log")
public class LogisticsLog extends BaseEntity {
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
	 * 店铺id
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 订单号
	 */
	@Column(name = "order_no")
	private String orderNo;

	/**
	 * 快递单号
	 */
	@Column(name = "logistic_no")
	private String logisticNo;

	/**
	 * 购买人编号
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 是否结束
	 */
	@Column(name = "end_flag")
	@Enumerated
	private BoolFlag endFlag;

	/**
	 * 监控状态:polling:监控中，shutdown:结束，abort:中止，updateall：重新推送。status=shutdown快递单为已签收时status= abort message为“3天查询无记录”或“60天无变化时”对于status=abort需要增加额外的处理逻辑
	 */
	@Column(name = "status")
	private String status;

	/**
	 * 快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态
	 */
	@Column(name = "state")
	private String state;

	/**
	 * 监控状态相关消息，如:3天查询无记录，60天无变化
	 */
	@Column(name = "message")
	private String message;

	/**
	 * 快递公司编码是否出过错
	 */
	@Column(name = "auto_check")
	private String autoCheck;

	/**
	 * 本地物流公司标准编码
	 */
	@Column(name = "com_old")
	private String comOld;

	/**
	 * 快递纠正新编码
	 */
	@Column(name = "com_new")
	private String comNew;

	/**
	 * 是否签收标记
	 */
	@Column(name = "is_check")
	private String isCheck;

	/**
	 * 手机号
	 */
	@Column(name = "phone")
	private String phone;

	/**
	 * 出发地城市
	 */
	@Column(name = "`from`")
	private String from;

	/**
	 * 目的地城市
	 */
	@Column(name = "`to`")
	private String to;

	/**
	 * 商品图片
	 */
	@Column(name = "goods_img")
	private String goodsImg;

	/**
	 * 商品名称
	 */
	@Column(name = "goods_name")
	private String goodsName;

	/**
	 * 订阅申请状态
	 */
	@Column(name = "success_flag")
	@Enumerated
	private BoolFlag successFlag;

	/**
	 * 签收时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "check_time")
	private LocalDateTime checkTime;

	/**
	 * 本地发货单号
	 */
	@Column(name = "deliver_id")
	private String deliverId;

	/**
	 * 是否有物流详细信息
	 */
	@Column(name = "has_details_flag")
	@Enumerated
	private BoolFlag hasDetailsFlag;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	@OneToMany(mappedBy="logisticsLog", fetch=FetchType.EAGER)
	@JsonBackReference
	@JSONField(serialize = false)
	private List<LogisticsLogDetail> logisticsLogDetails;
}
