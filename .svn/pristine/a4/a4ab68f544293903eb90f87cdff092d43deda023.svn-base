package com.wanmi.sbc.goods.api.request.buycyclegoodsinfo;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

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
public class BuyCycleGoodsInfoAddRequest extends BaseRequest {


	private static final long serialVersionUID = 4145509744770741391L;
	/**
	 * skuId
	 */
	@Schema(description = "skuId")
	@NotNull
	private String goodsInfoId;
	/**
	 * spuId
	 */
	@Schema(description = "spuId")
	@Length(max=32)
	private String goodsId;

	/**
	 * 最低期数
	 */
	@Schema(description = "最低期数")
	@Max(999)
	@Min(1)
	@NotNull
	private Integer minCycleNum;

	/**
	 * 每期价格
	 */
	@Schema(description = "每期价格")
	@NotNull
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