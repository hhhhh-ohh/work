package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.marketing.bean.enums.DrawPrizeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>抽奖活动奖品表VO</p>
 * @author wwc
 * @date 2021-04-12 16:54:59
 */
@Schema
@Data
public class DrawPrizeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 抽奖活动id
	 */
	@Schema(description = "抽奖活动id")
	private Long activityId;

	/**
	 * 奖品名称
	 */
	@Schema(description = "奖品名称")
	private String prizeName;

	/**
	 * 奖品类型（0：积分 1：优惠券 2：实物奖品 3：自定义）
	 */
	@Schema(description = "奖品类型（0：积分 1：优惠券 2：实物奖品 3：自定义）")
	private DrawPrizeType prizeType;

	/**
	 * 奖品图片
	 */
	@Schema(description = "奖品图片")
	private String prizeUrl;

	/**
	 * 商品总量（1-99999999）
	 */
	@Schema(description = "商品总量（1-99999999）")
	private Integer prizeNum;

	/**
	 * 中奖概率0.01-100之间的数字
	 */
	@Schema(description = "中奖概率0.01-100之间的数字")
	private BigDecimal winPercent;

	/**
	 * 积分数值
	 */
	@Schema(description = "积分数值,当prizeType为0时有值")
	private Long pointsNum;

	/**
	 * 优惠券奖品Id
	 */
	@Schema(description = "优惠券奖品Id,当prizeType为1时有值")
	private String couponCodeId;

	/**
	 * 自定义奖品
	 */
	@Schema(description = "自定义奖品,当prizeType为3时有值")
	private String customize;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 修改人
	 */
	@Schema(description = "修改人")
	private String updatePerson;

	/**
	 * 删除标识
	 */
	@Schema(description = "删除标识")
	private DeleteFlag delFlag;

	/**
	 * 优惠券信息
	 */
	@Schema(description = "优惠券信息")
	private CouponInfoVO couponInfoVO;
}