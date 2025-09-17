package com.wanmi.sbc.marketing.bean.vo;
import com.wanmi.sbc.marketing.bean.enums.DrawPrizeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>返回C端抽奖活动奖品表VO</p>
 * @author wwc
 * @date 2021-04-12 16:54:59
 */
@Schema
@Data
public class WebPrizeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 奖品名称
	 */
	@Schema(description = "奖品名称")
	private String prizeName;

	/**
	 * 奖品图片
	 */
	@Schema(description = "奖品图片")
	private String prizeUrl;

	/**
	 * 奖品类型（0：积分 1：优惠券 2：实物奖品 3：自定义）
	 */
	@Schema(description = "奖品类型（0：积分 1：优惠券 2：实物奖品 3：自定义）")
	private DrawPrizeType prizeType;
}