package com.wanmi.sbc.vas.recommend.goodscorrelationmodelsetting.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.vas.bean.enums.recommen.StatisticalRangeType;
import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>实体类</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@Data
@Entity
@Table(name = "goods_correlation_model_setting")
public class GoodsCorrelationModelSetting extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 统计范围0：近一个月，1：近3个月，2：近6个月，3：近1年
	 */
	@Column(name = "statistical_range")
	private StatisticalRangeType statisticalRange;

	/**
	 * 预估订单数据量
	 */
	@Column(name = "trade_num")
	private Long tradeNum;

	/**
	 * 支持度
	 */
	@Column(name = "support")
	private BigDecimal support;

	/**
	 * 置信度
	 */
	@Column(name = "confidence")
	private BigDecimal confidence;

	/**
	 * 提升度
	 */
	@Column(name = "lift")
	private BigDecimal lift;

	/**
	 * 选中状态 0：未选中，1：选中
	 */
	@Column(name = "check_status")
	private BoolFlag checkStatus;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	public GoodsCorrelationModelSetting(){}

	public GoodsCorrelationModelSetting(StatisticalRangeType statisticalRange, Long tradeNum, BigDecimal support,
                                        BigDecimal confidence, BigDecimal lift, BoolFlag checkStatus, DeleteFlag delFlag, LocalDateTime createTime){
		super.setCreateTime(createTime);
		this.statisticalRange = statisticalRange;
		this.tradeNum = tradeNum;
		this.support = support;
		this.confidence = confidence;
		this.lift = lift;
		this.checkStatus = checkStatus;
		this.delFlag = delFlag;
	}

}