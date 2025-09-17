package com.wanmi.sbc.goods.priceadjustmentrecorddetail.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.goods.bean.enums.GoodsPriceType;
import com.wanmi.sbc.goods.bean.enums.PriceAdjustmentResult;
import com.wanmi.sbc.goods.bean.enums.SaleType;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>调价单详情表实体类</p>
 * @author chenli
 * @date 2020-12-09 19:55:41
 */
@Data
@Entity
@Table(name = "price_adjustment_record_detail")
public class PriceAdjustmentRecordDetail {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 调价单号
	 */
	@Column(name = "price_adjustment_no")
	private String priceAdjustmentNo;

	/**
	 * 商品名称
	 */
	@Column(name = "goods_info_name")
	private String goodsInfoName;

	/**
	 * 商品图片
	 */
	@Column(name = "goods_info_img")
	private String goodsInfoImg;

	/**
	 * SKU编码
	 */
	@Column(name = "goods_info_no")
	private String goodsInfoNo;

	/**
	 * SKU ID
	 */
	@Column(name = "goods_info_id")
	private String goodsInfoId;

	/**
	 * 商品规格
	 */
	@Column(name = "goods_spec_text")
	private String goodsSpecText;

	/**
	 * 是否独立设价：0 否、1 是
	 */
	@Column(name = "alone_flag")
	private Boolean aloneFlag;

	/**
	 * 销售类别(0:批发,1:零售)
	 */
	@Enumerated
	@Column(name = "sale_type")
	private SaleType saleType;

	/**
	 * 设价类型,0:按客户(等级)1:按订货量(阶梯价)2:按市场价
	 */
	@Enumerated
	@Column(name = "price_type")
	private GoodsPriceType priceType;

	/**
	 * 原市场价
	 */
	@Column(name = "original_market_price")
	private BigDecimal originalMarketPrice;

	/**
	 * 调整后市场价
	 */
	@Column(name = "adjusted_market_price")
	private BigDecimal adjustedMarketPrice;

	/**
	 * 差异
	 */
	@Column(name = "price_difference")
	private BigDecimal priceDifference;

	/**
	 * 等级价 eg:[{},{}...] List<GoodsLevelPrice>
	 */
	@Column(name = "lever_price")
	private String leverPrice;

	/**
	 * 阶梯价 eg:[{},{}...] List<GoodsIntervalPrice>
	 */
	@Column(name = "interval_price")
	private String intervalPrice;

	/**
	 * 执行结果：0 未执行、1 执行成功、2 执行失败
	 */
	@Column(name = "adjust_result")
	@Enumerated
	private PriceAdjustmentResult adjustResult;

	/**
	 * 失败原因
	 */
	@Column(name = "fail_reason")
	private String failReason;

	/**
	 * 是否确认：0 未确认、1 已确认
	 */
	@Enumerated
	@Column(name = "confirm_flag")
	private DefaultFlag confirmFlag;

	/**
	 * 原供货价
	 */
	@Column(name = "supply_price")
	private BigDecimal supplyPrice;

	/**
	 * 调整后供货价
	 */
	@Column(name = "adjust_supply_price")
	private BigDecimal adjustSupplyPrice;

	/**
	 *审核状态,0:未审核1 审核通过2审核失败3禁用中
	 */
	@Column(name = "audit_status")
	private Integer auditStatus;

	/**
	 *审核时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Column(name = "audit_time")
	private LocalDateTime auditTime;

	/**
	 *审核驳回理由
	 */
	@Column(name = "audit_reason")
	private String auditReason;
	/**
	 * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券
	 */
	@Column(name = "goods_type")
	private Integer goodsType;

}