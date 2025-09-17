package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.enums.BoolFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>商品审核VO</p>
 * @author 黄昭
 * @date 2021-12-16 18:10:20
 */
@Schema
@Data
public class GoodsAuditVO extends GoodsVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品Id
	 */
	@Schema(description = "商品Id")
	private String goodsId;

	/**
	 * 旧商品Id
	 */
	@Schema(description = "旧商品Id")
	private String oldGoodsId;

	/**
	 * 审核类型 1:初次审核 2:二次审核
	 */
	@Schema(description = "审核类型 1:初次审核 2:二次审核")
	private Integer auditType;

	/**
	 * 商品类目名称
	 */
	@Schema(description = "商品类目名称")
	private String cateName;

	/**
	 * 商品品牌名称
	 */
	@Schema(description = "商品品牌名称")
	private String brandName;

	/**
	 * 店铺分类名称
	 */
	@Schema(description = "店铺分类名称")
	private List<String> storeCateNames;

	/**
	 * 是否可编辑
	 */
	@Schema(description = "是否可编辑")
	private BoolFlag editFlag = BoolFlag.YES;

	/**
	 * 商品类型，0：实体商品，1：虚拟商品 2：电子卡券'
	 */
	@Schema(description = "商品类型，0：实体商品，1：虚拟商品 2：电子卡券")
	private Integer goodsType;

	/**
	 * 电子卡券ID
	 */
	@Schema(description = "电子卡券ID")
	private Long electronicCouponsId;

	/**
	 * 电子卡券名称
	 */
	@Schema(description = "电子卡券名称")
	private String electronicCouponsName;
}