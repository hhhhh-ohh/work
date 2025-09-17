package com.wanmi.sbc.goods.bean.dto;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.goods.bean.enums.O2oStockAdjustmentResult;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

/***
 * 库存调整详情数据传输对象
 * @className O2oInventoryAdjRecDetailDto
 * @author zhengyang
 * @date 2021/8/7 17:54
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class O2oInventoryAdjRecDetailDto extends BaseRequest {

	private static final long serialVersionUID = -4543501269164413030L;

	/**
	 * 库存调整单号
	 */
	@NotBlank
	@Length(max=32)
	@Schema(description = "库存调整单号")
	private String inventoryAdjustmentNo;

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

	/** 原库存数量 */
	@Schema(description = "原库存数量")
	private Long originalStock;

	/** 调整后库存数量 */
	@Schema(description = "调整后库存数量")
	private Long adjustedStock;

	/** 差异 */
	@Schema(description = "差异")
	private Long stockDifference;

	/**
	 * 执行结果：0 未执行、1 执行成功、2 执行失败
	 */
	@Schema(description = "执行结果：0 未执行、1 执行成功、2 执行失败")
	@NotNull
	private O2oStockAdjustmentResult adjustResult;

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
	private DefaultFlag confirmFlag;

	/**
	 * SPU ID: 临时字段，用于销售类型覆盖，不会做持久化处理
	 */
	private String goodsId;

	/***
	 * 空对象
	 */
	public static final O2oInventoryAdjRecDetailDto EMPTY_DTO = new O2oInventoryAdjRecDetailDto();
}