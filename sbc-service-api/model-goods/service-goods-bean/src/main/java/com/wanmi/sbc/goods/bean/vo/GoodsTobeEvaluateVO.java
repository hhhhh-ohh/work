package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.util.*;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>订单商品待评价VO</p>
 * @author lzw
 * @date 2019-03-20 14:47:38
 */
@Schema
@Data
public class GoodsTobeEvaluateVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private String id;

	/**
	 * 店铺Id
	 */
	@Schema(description = "店铺Id")
	private Long storeId;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	private String storeName;

	/**
	 * 商品id(spuId)
	 */
	@Schema(description = "商品id(spuId)")
	private String goodsId;

	/**
	 * 商品图片
	 */
	@Schema(description = "商品图片")
	private String goodsImg;

	/**
	 * 货品id(skuId)
	 */
	@Schema(description = "货品id(skuId)")
	private String goodsInfoId;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String goodsInfoName;

	/**
	 * 规格值名称
	 */
	@Schema(description = "规格值名称")
	private String goodsSpecDetail;

	/**
	 * 购买时间
	 */
	@Schema(description = "购买时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime buyTime;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	private String orderNo;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	private String customerId;

	/**
	 * 会员名称
	 */
	@Schema(description = "会员名称")
	private String customerName;

	/**
	 * 会员登录账号|手机号
	 */
	@Schema(description = "会员登录账号|手机号")
	private String customerAccount;

	/**
	 * 是否评价 0：未评价，1：已评价
	 */
	@Schema(description = "是否评价 0：未评价，1：已评价")
	private Integer evaluateStatus;

	/**
	 * 是否晒单 0：未晒单，1：已晒单
	 */
	@Schema(description = "是否晒单 0：未晒单，1：已晒单")
	private Integer evaluateImgStatus;

	/**
	 * 商品自动评价日期
	 */
	@Schema(description = "商品自动评价日期")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate autoGoodsEvaluateDate;

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
	 * 商品类型
	 */
	@Schema(description = "创建人")
	private PluginType pluginType;

}