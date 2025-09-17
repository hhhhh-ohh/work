package com.wanmi.sbc.marketing.api.request.grouponactivity;

import com.wanmi.sbc.goods.bean.dto.GoodsMutexValidateDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>拼团通用互斥请求参数</p>
 *
 * @author bob
 * @date 2022-04-29 14:54:31
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class GrouponGoodsValidateRequest extends GoodsMutexValidateDTO {

	/**
	 * 非id
	 */
	@Schema(description = "notId")
	private String notId;
}