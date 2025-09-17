package com.wanmi.sbc.goods.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.GoodsPriceType;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentResult;
import com.wanmi.sbc.goods.bean.enums.SaleType;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * <p>调价单详情表VO</p>
 * @author chenli
 * @date 2020-12-09 19:55:41
 */
@Schema
@Data
public class PriceAdjustmentRecordDetailVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

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
	 * 商品图片
	 */
	@Schema(description = "商品图片")
	private String goodsInfoImg;

	/**
	 * SKU编码
	 */
	@Schema(description = "SKU编码")
	private String goodsInfoNo;

	/**
	 * SKU ID
	 */
	@Schema(description = "SKU ID")
	private String goodsInfoId;

	/**
	 * 商品规格
	 */
	@Schema(description = "商品规格")
	private String goodsSpecText;

	/**
	 * 是否独立设价：0 否、1 是
	 */
	@Schema(description = "是否独立设价：0 否、1 是")
	private Boolean aloneFlag;

	/**
	 * 销售类别(0:批发,1:零售)
	 */
	@Schema(description = "销售类别(0:批发,1:零售)")
	private SaleType saleType;

	/**
	 * 设价类型,0:按客户(等级)1:按订货量(阶梯价)2:按市场价
	 */
	@Schema(description = "设价类型,0:按客户(等级)1:按订货量(阶梯价)2:按市场价")
	private GoodsPriceType priceType;

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


	/**
	 * 等级价
	 */
	@Schema(description = "等级价")
	private List<GoodsAdjustLevelPriceVO> leverPriceList;

	/**
	 * 阶梯价
	 */
	@Schema(description = "阶梯价")
	private List<GoodsAdjustIntervalPriceVO> intervalPriceList;

	/**
	 * 供货价
	 */
	@Schema(description = "供货价")
	private BigDecimal supplyPrice;

	/**
	 * 调整后供货价
	 */
	@Schema(description = "调整后供货价")
	private BigDecimal adjustSupplyPrice;

	/**
	 * 审核状态,0:未审核1 审核通过2审核失败
	 */
	@Schema(description = "审核状态,0:未审核1 审核通过2审核失败")
	private Integer auditStatus;

	/**
	 * 审核时间
	 */
	@Schema(description = "审核时间", hidden = true)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime auditTime;

	/**
	 *审核驳回理由
	 */
	@Schema(description = "审核驳回理由")
	private String auditReason;

	/**
	 * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
	 */
	@Schema(description = "goods_type")
	private Integer goodsType;
}