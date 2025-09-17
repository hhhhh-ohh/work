package com.wanmi.sbc.goods.api.request.storetobeevaluate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>店铺服务待评价修改参数</p>
 * @author lzw
 * @date 2019-03-20 17:01:46
 */
@Schema
@Data
public class StoreTobeEvaluateModifyRequest extends BaseRequest {

	private static final long serialVersionUID = 9107631393684626373L;

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
	 * 店铺logo
	 */
	@Schema(description = "店铺logo")
	private String storeLogo;

	/**
	 * 店铺名称
	 */
	@Schema(description = "店铺名称")
	@Length(max=150)
	private String storeName;

	/**
	 * 订单号
	 */
	@Schema(description = "订单号")
	@NotBlank
	@Length(max=255)
	private String orderNo;

	/**
	 * 购买商品数量
	 */
	@NotBlank
	@Schema(description = "店铺名称")
	private Integer goodsNum;

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