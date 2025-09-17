package com.wanmi.sbc.goods.api.request.priceadjustmentrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * @description 根据ID更新扫描标识
 * @author malianfeng
 * @date 2021/9/13 10:53
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceAdjustmentRecordModifyScanTypeRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 调价单号列表
	 */
	@Schema(description = "调价单号列表")
	@NotNull
	private List<String> ids;

}