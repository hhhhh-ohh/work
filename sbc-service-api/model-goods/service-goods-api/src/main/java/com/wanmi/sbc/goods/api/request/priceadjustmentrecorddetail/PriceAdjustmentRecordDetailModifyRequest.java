package com.wanmi.sbc.goods.api.request.priceadjustmentrecorddetail;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * <p>调价单详情表修改参数</p>
 * @author chenli
 * @date 2020-12-09 19:55:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceAdjustmentRecordDetailModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 调价单号
	 */
	@Schema(description = "调价单号")
	@NotBlank
	@Length(max=32)
	private String priceAdjustmentNo;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	@NotBlank
	@Length(max=255)
	private String goodsInfoName;

	/**
	 * 商品图片
	 */
	@Schema(description = "商品图片")
	@Length(max=255)
	private String goodsInfoImg;

	/**
	 * SKU编码
	 */
	@Schema(description = "SKU编码")
	@NotBlank
	@Length(max=45)
	private String goodsInfoNo;

	/**
	 * SKU ID
	 */
	@Schema(description = "SKU ID")
	@NotBlank
	private String goodsInfoId;

	/**
	 * 商品规格
	 */
	@Schema(description = "商品规格")
	@Length(max=255)
	private String goodsSpecText;

	/**
	 * 是否独立设价：0 否、1 是
	 */
	@Schema(description = "是否独立设价：0 否、1 是")
	private Integer aloneFlag;

	/**
	 * 销售类别(0:批发,1:零售)
	 */
	@Schema(description = "销售类别(0:批发,1:零售)")
	private Integer saleType;

	/**
	 * 设价类型,0:按客户(等级)1:按订货量(阶梯价)2:按市场价
	 */
	@Schema(description = "设价类型,0:按客户(等级)1:按订货量(阶梯价)2:按市场价")
	@Max(127)
	private Integer priceType;

	/**
	 * 原市场价
	 */
	@Schema(description = "原市场价")
	private BigDecimal originalMarketPrice;

	/**
	 * 调整后市场价
	 */
	@Schema(description = "调整后市场价")
	private BigDecimal adjustedMarketPrice;

	/**
	 * 差异
	 */
	@Schema(description = "差异")
	private BigDecimal priceDifference;

	/**
	 * 等级价 eg:[{},{}...]
	 */
	@Schema(description = "等级价 eg:[{},{}...]")
	@Length(max=500)
	private String leverPrice;

	/**
	 * 阶梯价 eg:[{},{}...]
	 */
	@Schema(description = "阶梯价 eg:[{},{}...]")
	@Length(max=500)
	private String intervalPrice;

	/**
	 * 执行结果：0 未执行、1 执行成功、2 执行失败
	 */
	@Schema(description = "执行结果：0 未执行、1 执行成功、2 执行失败")
	@NotNull
	private Integer adjustResult;

	/**
	 * 失败原因
	 */
	@Schema(description = "失败原因")
	@Length(max=255)
	private String failReason;

	/**
	 * 是否确认：0 未确认、1 已确认
	 */
	@Schema(description = "是否确认：0 未确认、1 已确认")
	private Integer confirmFlag;

}