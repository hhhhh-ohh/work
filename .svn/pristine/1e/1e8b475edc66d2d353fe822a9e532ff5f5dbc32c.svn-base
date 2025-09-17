package com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.*;

import java.util.List;

/**
 * <p>新人购优惠券批量编辑参数</p>
 * @author zhanghao
 * @date 2022-08-19 14:27:36
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseCouponBatchSaveRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;


	@Schema(description = "新人购优惠券修改参数集合")
	@NotEmpty
	@Size(max = 50,message = "最多保存50张新人购优惠券")
	private List<NewcomerPurchaseCouponModifyRequest> newcomerPurchaseCouponModifyRequestList;

}