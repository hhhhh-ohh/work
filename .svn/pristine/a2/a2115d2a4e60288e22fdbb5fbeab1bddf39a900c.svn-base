package com.wanmi.sbc.marketing.drawprize.model.root;

import com.wanmi.sbc.marketing.bean.enums.DrawPrizeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>redis中存储的奖品数据</p>
 * @author wwc
 * @date 2021-04-12 16:54:59
 */
@Data
public class RedisDrawPrize implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 抽奖活动id
	 */
	private Long activityId;

	/**
	 * 奖品名称
	 */
	private String prizeName;

	/**
	 * 奖品图片
	 */
	private String prizeUrl;


	/**
	 * 中奖概率0.01-100之间的数字
	 */
	private BigDecimal winPercent;

	/**
	 * 奖品类型（0：积分 1：优惠券 2：实物奖品 3：自定义）
	 */
	private DrawPrizeType prizeType;


}