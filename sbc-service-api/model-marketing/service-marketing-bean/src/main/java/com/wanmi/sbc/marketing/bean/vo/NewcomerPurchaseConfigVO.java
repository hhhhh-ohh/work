package com.wanmi.sbc.marketing.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>新人专享设置VO</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:12
 */
@Schema
@Data
public class NewcomerPurchaseConfigVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Integer id;

	/**
	 * 海报设置
	 */
	@Schema(description = "海报设置")
	private String poster;

	/**
	 * 优惠券样式布局
	 */
	@Schema(description = "优惠券样式布局")
	private Integer couponLayout;

	/**
	 * 商品样式布局
	 */
	@Schema(description = "商品样式布局")
	private Integer goodsLayout;

	/**
	 * 活动规则详细
	 */
	@Schema(description = "活动规则详细")
	private String ruleDetail;

	/**
	 * 活动开始时间
	 */
	@Schema(description = "活动开始时间")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;

	/**
	 * 活动结束时间
	 */
	@Schema(description = "活动结束时间")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;


	/**
	 * 新人专享商品信息
	 */
	@Schema(description = "新人专享商品信息")
	private List<GoodsInfoVO> goodsInfoVOList;

}