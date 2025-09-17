package com.wanmi.sbc.goods.api.request.flashsalegoods;

import com.wanmi.sbc.goods.bean.dto.GoodsMutexValidateDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>抢购商品表互斥验证请求参数</p>
 *
 * @author dyt
 * @date 2019-06-11 14:54:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class FlashSaleGoodsValidateRequest extends GoodsMutexValidateDTO {

	/**
	 * 非id
	 */
	@Schema(description = "notId")
	private String notId;
}