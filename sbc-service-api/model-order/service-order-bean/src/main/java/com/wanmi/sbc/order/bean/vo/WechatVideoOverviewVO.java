package com.wanmi.sbc.order.bean.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>视频号店铺带货统计VO</p>
 * @author zhaiqiankun
 * @date 2022-04-07 18:05:48
 */
@Schema
@Data
public class WechatVideoOverviewVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 公司ID
	 */
	@Schema(description = "公司ID")
	private Long companyInfoId;

	/**
	 * 视频号销售额
	 */
	@Schema(description = "视频号销售额")
	private BigDecimal videoSaleAmount = BigDecimal.ZERO;

	/**
	 * 直播间销售额
	 */
	@Schema(description = "直播间销售额")
	private BigDecimal liveSaleAmount = BigDecimal.ZERO;

	/**
	 * 橱窗销售额
	 */
	@Schema(description = "橱窗销售额")
	private BigDecimal shopwindowSaleAmount = BigDecimal.ZERO;

	/**
	 * 视频号退货额
	 */
	@Schema(description = "视频号退货额")
	private BigDecimal videoReturnAmount = BigDecimal.ZERO;

	/**
	 * 直播间退货额
	 */
	@Schema(description = "直播间退货额")
	private BigDecimal liveReturnAmount = BigDecimal.ZERO;

	/**
	 * 橱窗退货额
	 */
	@Schema(description = "橱窗退货额")
	private BigDecimal shopwindowReturnAmount = BigDecimal.ZERO;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

}