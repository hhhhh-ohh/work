package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>H5-拼团活动首页列表VO</p>
 * @author chenli
 * @date 2019-05-21 14:02:38
 */
@Schema
@Data
public class GrouponCenterVO extends BasicResponse {

	private static final long serialVersionUID = 6609792882010289467L;

	/**
	 * 活动ID
	 */
    @Schema(description = "活动ID")
	private String grouponActivityId;

	/**
	 * spu编号
	 */
	@Schema(description = "spu编号")
	private String goodsId;

	/**
	 * sku编号
	 */
	@Schema(description = "sku编号")
	private String goodsInfoId;

	/**
	 * spu商品名称
	 */
	@Schema(description = "spu商品名称")
	private String goodsName;

	/**
	 * 商品图片
	 */
	@Schema(description = "商品图片")
	private String goodsImg;

	/**
	 * 商品市场价
	 */
	@Schema(description = "商品市场价")
	private BigDecimal marketPrice;

	/**
	 * 拼团价格
	 */
	@Schema(description = "拼团价格")
	private BigDecimal grouponPrice;

	/**
	 * 拼团人数
	 */
    @Schema(description = "拼团人数")
	private Integer grouponNum;

	/**
	 * 已成团人数
	 */
	@Schema(description = "已成团人数")
	private Integer alreadyGrouponNum;

}