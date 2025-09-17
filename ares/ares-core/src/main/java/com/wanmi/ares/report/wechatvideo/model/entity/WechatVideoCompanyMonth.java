package com.wanmi.ares.report.wechatvideo.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>视频号订单公司维度每月统计实体类</p>
 * @author 
 * @date 2022-04-24 17:54:30
 */
@Data
public class WechatVideoCompanyMonth implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private Long id;

	/**
	 * 公司ID
	 */
	private Integer companyInfoId;

	/**
	 * 视频号销售额
	 */
	private BigDecimal videoSaleAmount;

	/**
	 * 直播间销售额
	 */
	private BigDecimal liveSaleAmount;

	/**
	 * 橱窗销售额
	 */
	private BigDecimal shopwindowSaleAmount;

	/**
	 * 视频号退货额
	 */
	private BigDecimal videoReturnAmount;

	/**
	 * 直播间退货额
	 */
	private BigDecimal liveReturnAmount;

	/**
	 * 橱窗退货额
	 */
	private BigDecimal shopwindowReturnAmount;


	private Integer year;


	private Integer month;

	/**
	 * createTime
	 */
	private LocalDateTime createTime;

}