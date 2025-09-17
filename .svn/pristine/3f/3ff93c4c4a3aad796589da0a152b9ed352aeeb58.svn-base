package com.wanmi.sbc.marketing.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>抽奖结果返回值</p>
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@Schema
@Data
public class DrawResultVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 返回奖品编号 为空表示未中奖
	 */
	@Schema(description = "返回奖品编号")
	private Long prizeId;

	/**
	 * 剩余抽奖次数
	 */
	@Schema(description = "剩余抽奖次数")
	private Long lessDrawCount;

}