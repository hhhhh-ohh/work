package com.wanmi.sbc.goods.api.request.priceadjustmentrecorddetail;

import java.math.BigDecimal;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>调价单详情表分页查询请求参数</p>
 * @author chenli
 * @date 2020-12-09 19:55:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceAdjustmentRecordDetailPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Long> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 调价单号
	 */
	@Schema(description = "调价单号")
	private String priceAdjustmentNo;

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
	 * 商品规格
	 */
	@Schema(description = "商品规格")
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
	private String leverPrice;

	/**
	 * 阶梯价 eg:[{},{}...]
	 */
	@Schema(description = "阶梯价 eg:[{},{}...]")
	private String intervalPrice;

	/**
	 * 执行结果：0 未执行、1 执行成功、2 执行失败
	 */
	@Schema(description = "执行结果：0 未执行、1 执行成功、2 执行失败")
	private Integer adjustResult;

	/**
	 * 失败原因
	 */
	@Schema(description = "失败原因")
	private String failReason;

	/**
	 * 是否确认：0 未确认、1 已确认
	 */
	@Schema(description = "是否确认：0 未确认、1 已确认")
	private Integer confirmFlag;

}