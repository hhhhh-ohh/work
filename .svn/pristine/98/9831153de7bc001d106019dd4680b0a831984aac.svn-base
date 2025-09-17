package com.wanmi.sbc.goods.api.request.buycyclegoodsinfo;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.math.BigDecimal;

/**
 * <p>周期购sku表修改参数</p>
 * @author zhanghao
 * @date 2022-10-11 17:46:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyCycleGoodsInfoModifyRequest extends BaseRequest {

	private static final long serialVersionUID = -7282136367907803008L;
	/**
	 * 主键
	 */
	@Schema(description = "主键")
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
	 * updatePerson
	 */
	@Schema(description = "updatePerson", hidden = true)
	private String updatePerson;

}
