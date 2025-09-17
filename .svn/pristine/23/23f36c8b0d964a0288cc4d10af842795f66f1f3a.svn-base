package com.wanmi.sbc.marketing.drawprize.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.bean.enums.DrawPrizeType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>抽奖活动奖品表实体类</p>
 * @author wwc
 * @date 2021-04-12 16:54:59
 */
@Data
@Entity
@Table(name = "draw_prize")
public class DrawPrize implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 抽奖活动id
	 */
	@Column(name = "activity_id")
	private Long activityId;

	/**
	 * 奖品名称
	 */
	@Column(name = "prize_name")
	private String prizeName;

	/**
	 * 奖品类型（0：积分 1：优惠券 2：实物奖品 3：自定义）
	 */
	@Column(name = "prize_type")
	@Enumerated
	private DrawPrizeType prizeType;

	/**
	 * 奖品图片
	 */
	@Column(name = "prize_url")
	private String prizeUrl;

	/**
	 * 商品总量（1-99999999）
	 */
	@Column(name = "prize_num")
	private Integer prizeNum;

	/**
	 * 中奖概率0.01-100之间的数字
	 */
	@Column(name = "win_percent")
	private BigDecimal winPercent;

	/**
	 * 积分数值
	 */
	@Column(name = "points_num")
	private Long pointsNum;

	/**
	 * 优惠券奖品Id
	 */
	@Column(name = "coupon_code_id")
	private String couponCodeId;

	/**
	 * 自定义奖品
	 */
	@Column(name = "customize")
	private String customize;

	/**
	 * 创建时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@Column(name = "create_person")
	private String createPerson;

	/**
	 * 修改时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "update_time")
	private LocalDateTime updateTime;

	/**
	 * 修改人
	 */
	@Column(name = "update_person")
	private String updatePerson;

	/**
	 * 删除标识
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag = DeleteFlag.NO;

}