package com.wanmi.sbc.goods.api.request.goodstobeevaluate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * <p>订单商品待评价修改参数</p>
 * @author lzw
 * @date 2019-03-20 14:47:38
 */
@Schema
@Data
public class GoodsTobeEvaluateModifyRequest extends BaseRequest {

	private static final long serialVersionUID = -2729709871487980733L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Length(max=32)
	private String id;

	/**
	 * 店铺Id
	 */
	@Schema(description = "店铺Id")
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	@Length(max=150)
	private String storeName;

	/**
	 * 商品id(spuId)
	 */
	@Schema(description = "商品id(spuId)")
	@NotBlank
	@Length(max=32)
	private String goodsId;

	/**
	 * 商品图片
	 */
	@Schema(description = "商品图片")
	@Length(max=255)
	private String goodsImg;

	/**
	 * 货品id(skuId)
	 */
	@Schema(description = "货品id(skuId)")
	@NotBlank
	@Length(max=32)
	private String goodsInfoId;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	@NotBlank
	@Length(max=255)
	private String goodsInfoName;

	/**
	 * 规格值名称
	 */
	@Schema(description = "规格值名称")
	@NotBlank
	@Length(max=45)
	private String goodsSpecDetail;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	@NotBlank
	@Length(max=255)
	private String orderNo;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	@NotBlank
	@Length(max=32)
	private String customerId;

	/**
	 * 会员名称
	 */
	@Schema(description = "会员名称")
	@Length(max=128)
	private String customerName;

	/**
	 * 会员登录账号|手机号
	 */
	@Schema(description = "会员登录账号|手机号")
	@NotBlank
	@Length(max=20)
	private String customerAccount;

	/**
	 * 是否评价 0：未评价，1：已评价
	 */
	@Schema(description = "是否评价 0：未评价，1：已评价")
	@Max(127)
	private Integer evaluateStatus;

	/**
	 * 是否晒单 0：未晒单，1：已晒单
	 */
	@Schema(description = "是否晒单 0：未晒单，1：已晒单")
	@Max(127)
	private Integer evaluateImgStatus;

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
	@Length(max=32)
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
	@Length(max=32)
	private String updatePerson;

}