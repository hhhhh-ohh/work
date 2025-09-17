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
 * <p>拼团活动信息表VO</p>
 * @author groupon
 * @date 2019-05-15 14:02:38
 */
@Schema
@Data
public class GrouponSettingGoodsVO extends BasicResponse {

	private static final long serialVersionUID = -1089670429322755491L;
	/**
	 * spu编号
	 */
	@Schema(description = "spu编号")
	private String goodsId;

	/**
	 * spu编码
	 */
	@Schema(description = "spu编号")
	private String goodsNo;

	/**
	 * sku编号(默认取第一个)
	 */
	@Schema(description = "spu编号")
	private String goodsInfoId;

	/**
	 * spu商品名称
	 */
	@Schema(description = "spu商品名称")
	private String goodsName;

	/**
	 * 商品主图
	 */
	@Schema(description = "商品主图")
	private String goodsImg;

	/**
	 * 拼团价格--最低拼团价格
	 */
	@Schema(description = "拼团价格")
	private BigDecimal grouponPrice;

	/**
	 * 开始时间
	 */
	@Schema(description = "开始时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime startTime;

	/**
	 * 结束时间
	 */
	@Schema(description = "结束时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime endTime;
}