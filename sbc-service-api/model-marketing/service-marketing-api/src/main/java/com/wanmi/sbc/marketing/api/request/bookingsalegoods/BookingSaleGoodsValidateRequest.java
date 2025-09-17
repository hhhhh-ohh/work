package com.wanmi.sbc.marketing.api.request.bookingsalegoods;

import com.wanmi.sbc.goods.bean.dto.GoodsMutexValidateDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * <p>预售商品验证请求参数</p>
 * @author dyt
 * @date 2022-04-29 10:51:35
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class BookingSaleGoodsValidateRequest extends GoodsMutexValidateDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "非当前id")
	private Long notId;
}