package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 * 电子卡券表批量新增参数
 * @className ElectronicCouponAddBatchRequest
 * @author zhengyang
 * @date 2022/4/28 10:59 上午
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCouponAddBatchRequest extends BaseRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 电子卡券批量新增对象
	 */
	@NotEmpty
	@Schema(description = "电子卡券集合")
	private List<ElectronicCouponAddRequest> couponAddRequestList;
}
