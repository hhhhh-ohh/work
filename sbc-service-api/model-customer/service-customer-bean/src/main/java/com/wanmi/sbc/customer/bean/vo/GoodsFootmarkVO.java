package com.wanmi.sbc.customer.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.annotation.IsImage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.bean.enums.FootMarkGoodsStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>我的足迹VO</p>
 * @author 
 * @date 2022-05-30 07:30:41
 */
@Schema
public class GoodsFootmarkVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * footmarkId
	 */
	@Schema(description = "footmarkId")
	private Long footmarkId;

	/**
	 * footmarkIdStr
	 */
	@Schema(description = "footmarkIdStr")
	private String footmarkIdStr;


	/**
	 * 客户名称
	 */
	@Schema(description = "customerId")
	private String customerId;

	/**
	 * 商品Id,SKU ID
	 */
	@Schema(description = "goodsInfoId")
	private String goodsInfoId;

	/**
	 *   商品状态 0：缺货 1：失效
	 */
	@Schema(description = "商品状态 0：缺货 1：失效")
	FootMarkGoodsStatus footMarkGoodsStatus;

	/**
	 * 商品市场价
	 */
	@Schema(description = "商品市场价")
	private BigDecimal marketPrice;

	/**
	 * 商品SKU名称
	 */
	@Schema(description = "商品SKU名称")
	private String goodsInfoName;

	/**
	 * 商品图片
	 */
	@IsImage
	@Schema(description = "商品图片")
	private String goodsInfoImg;


	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 排序时间
	 */
	@Schema(description = "排序时间")
	@JsonSerialize(using = CustomLocalDateSerializer.class)
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate sortTime;

	/**
	 * 划线价
	 */
	@Schema(description = "划线价")
	private BigDecimal linePrice;

	public Long getFootmarkId() {
		return footmarkId;
	}

	public void setFootmarkId(Long footmarkId) {
		this.footmarkId = footmarkId;
	}

	public String getFootmarkIdStr() {
		if (footmarkIdStr == null) {
			footmarkIdStr = footmarkId.toString();
		}
		return footmarkIdStr;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getGoodsInfoId() {
		return goodsInfoId;
	}

	public void setGoodsInfoId(String goodsInfoId) {
		this.goodsInfoId = goodsInfoId;
	}

	public FootMarkGoodsStatus getFootMarkGoodsStatus() {
		return footMarkGoodsStatus;
	}

	public void setFootMarkGoodsStatus(FootMarkGoodsStatus footMarkGoodsStatus) {
		this.footMarkGoodsStatus = footMarkGoodsStatus;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getGoodsInfoName() {
		return goodsInfoName;
	}

	public void setGoodsInfoName(String goodsInfoName) {
		this.goodsInfoName = goodsInfoName;
	}

	public String getGoodsInfoImg() {
		return goodsInfoImg;
	}

	public void setGoodsInfoImg(String goodsInfoImg) {
		this.goodsInfoImg = goodsInfoImg;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public LocalDate getSortTime() {
		return this.updateTime.toLocalDate();
	}

	public void setSortTime(LocalDate sortTime) {
		this.sortTime = sortTime;
	}

	public DeleteFlag getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(DeleteFlag delFlag) {
		this.delFlag = delFlag;
	}

	public Long getViewTimes() {
		return viewTimes;
	}

	public void setViewTimes(Long viewTimes) {
		this.viewTimes = viewTimes;
	}

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除")
	private DeleteFlag delFlag;

	/**
	 * 浏览次数
	 */
	@Schema(description = "浏览次数")
	private Long viewTimes;

}