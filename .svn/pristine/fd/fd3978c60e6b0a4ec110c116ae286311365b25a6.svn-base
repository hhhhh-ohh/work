package com.wanmi.sbc.goods.api.request.buycyclegoodsinfo;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import java.math.BigDecimal;

/**
 * <p>周期购sku表新增参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsInfoSaveRequest extends BaseRequest {


	private static final long serialVersionUID = 6094414518837192536L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;
	/**
	 * skuId
	 */
	@Schema(description = "skuId")
	private String goodsInfoId;
	/**
	 * spuId
	 */
	@Schema(description = "spuId")
	private String goodsId;

	/**
	 * 最低期数
	 */
	@Schema(description = "最低期数")
	private Integer minCycleNum;

	/**
	 * 每期价格
	 */
	@Schema(description = "每期价格")
	private BigDecimal cyclePrice;

	/**
	 * createPerson
	 */
	@Schema(description = "createPerson", hidden = true)
	private String createPerson;

	/**
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除", hidden = true)
	private DeleteFlag delFlag;

}