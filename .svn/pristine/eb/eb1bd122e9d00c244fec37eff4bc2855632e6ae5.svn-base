package com.wanmi.sbc.goods.api.request.livegoods;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>直播商品修改参数</p>
 * @author zwb
 * @date 2020-06-10 11:05:45
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LiveGoodsUpdateRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;
	/**
	 * goodsId集合
	 */
	@Schema(description = "goodsId集合")
	private List<Long> goodsIdList;
	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 微信id
	 */
	@Schema(description = "微信id")
	@Max(9223372036854775807L)
	private Long goodsId;

	/**
	 * 商品标题
	 */
	@Schema(description = "商品标题")
	@Length(max=255)
	private String name;

	/**
	 * 填入mediaID
	 */
	@Schema(description = "填入mediaID")
	@Length(max=255)
	private String coverImgUrl;

	/**
	 * 价格类型，1：一口价，2：价格区间，3：显示折扣价
	 */
	@Schema(description = "价格类型，1：一口价，2：价格区间，3：显示折扣价")
	@Max(127)
	private Integer priceType;

	/**
	 * 直播商品价格左边界
	 */
	@Schema(description = "直播商品价格左边界")
	private BigDecimal price;

	/**
	 * 直播商品价格右边界
	 */
	@Schema(description = "直播商品价格右边界")
	private BigDecimal price2;

	/**
	 * 商品详情页的小程序路径
	 */
	@Schema(description = "商品详情页的小程序路径")
	@Length(max=255)
	private String url;

	/**
	 * 库存
	 */
	@Schema(description = "库存")
	@Max(9223372036854775807L)
	private Long stock;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;


	/**
	 * 更新时间
	 */
	@Schema(description = "创建时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 商品详情id
	 */
	@Schema(description = "商品详情id")
	private String goodsInfoId;

	/**
	 * 店铺标识
	 */
	@Schema(description = "店铺标识")
	@Max(9223372036854775807L)
	private Long storeId;

	/**
	 * 提交审核时间
	 */
	@Schema(description = "提交审核时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime submitTime;

	/**
	 * 审核单ID
	 */
	@Schema(description = "审核单ID")
	@Max(9223372036854775807L)
	private Long auditId;

	/**
	 * 审核状态,0:未审核1 审核通过2审核失败3禁用中
	 */
	@Schema(description = "审核状态,0:未审核1 审核通过2审核失败3禁用中")
	@NotNull
	@Max(127)
	private Integer auditStatus;

	/**
	 * 审核原因
	 */
	@Schema(description = "审核原因")
	@Length(max=255)
	private String auditReason;

	/**
	 * 删除时间
	 */
	@Schema(description = "删除时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime deleteTime;

	/**
	 * 删除人
	 */
	@Schema(description = "删除人")
	@Length(max=255)
	private String deletePerson;


	/**
	 * 1, 2：表示是为api添加商品，否则是在MP添加商品
	 */
	@Schema(description = "1, 2：表示是为api添加商品，否则是在MP添加商品")
	@Max(127)
	private Integer thirdPartyTag;

	/**
	 * 删除标记 0:未删除1:已删除
	 */
	@Schema(description = "删除标记 0:未删除1:已删除")
	private DeleteFlag delFlag;

}