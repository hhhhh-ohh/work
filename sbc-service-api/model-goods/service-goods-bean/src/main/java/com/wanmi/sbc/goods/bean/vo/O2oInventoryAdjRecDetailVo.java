package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentResult;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/***
 * 库存调整详情Vo
 * @className O2oInventoryAdjRecDetailVo
 * @author zhengyang
 * @date 2021/8/7 17:54
 **/
@Data
@Schema
public class O2oInventoryAdjRecDetailVo extends BasicResponse {

	/**
	 * id
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 库存调整单号
	 */
	@Schema(description = "库存调整单号")
	private String inventoryAdjustmentNo;

	/**
	 * SKU ID
	 */
	@Schema(description = "SKU ID")
	private String goodsInfoId;

	/**
	 * 商品名称
	 */
	@Schema(description = "商品名称")
	private String goodsInfoName;

	/**
	 * SKU编码
	 */
	@Schema(description = "SKU编码")
	private String goodsInfoNo;

	/**
	 * 商品图片
	 */
	@Schema(description = "商品图片")
	private String goodsInfoImg;

	/**
	 * 商品规格
	 */
	@Schema(description = "商品规格")
	private String goodsSpecText;

	/**
	 * 原库存数量
	 */
	@Schema(description = "原库存数量")
	private BigDecimal originalStock;

	/**
	 * 调整后库存数量
	 */
	@Schema(description = "调整后库存数量")
	private BigDecimal adjustedStock;

	/**
	 * 差异
	 */
	@Schema(description = "差异")
	private BigDecimal stockDifference;

	/**
	 * 执行结果：0 未执行、1 执行成功、2 执行失败
	 */
	@Schema(description = "执行结果：0 未执行、1 执行成功、2 执行失败")
	private PriceAdjustmentResult adjustResult;

	/**
	 * 失败原因
	 */
	@Schema(description = "失败原因")
	private String failReason;

	/**
	 * 是否确认：0 未确认、1 已确认
	 */
	@Schema(description = "是否确认：0 未确认、1 已确认")
	private DefaultFlag confirmFlag;

}
