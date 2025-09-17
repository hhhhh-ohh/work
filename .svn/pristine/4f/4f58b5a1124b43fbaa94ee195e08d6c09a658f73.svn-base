package com.wanmi.sbc.marketing.api.provider.electroniccoupon;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponByIdRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponListRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicCouponPageRequest;
import com.wanmi.sbc.marketing.api.request.electroniccoupon.ElectronicSendRecordNumRequest;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicCouponByIdResponse;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicCouponListResponse;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicCouponPageResponse;
import com.wanmi.sbc.marketing.api.response.electroniccoupon.ElectronicSendRecordNumResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>电子卡券表查询服务Provider</p>
 * @author 许云鹏
 * @date 2022-01-26 17:18:05
 */
@FeignClient(value = "${application.marketing.name}", contextId = "ElectronicCouponQueryProvider")
public interface ElectronicCouponQueryProvider {

	/**
	 * 分页查询电子卡券表API
	 *
	 * @author 许云鹏
	 * @param electronicCouponPageReq 分页请求参数和筛选对象 {@link ElectronicCouponPageRequest}
	 * @return 电子卡券表分页列表信息 {@link ElectronicCouponPageResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electroniccoupon/page")
	BaseResponse<ElectronicCouponPageResponse> page(@RequestBody @Valid ElectronicCouponPageRequest electronicCouponPageReq);

	/**
	 * 列表查询电子卡券表API
	 *
	 * @author 许云鹏
	 * @param electronicCouponListReq 列表请求参数和筛选对象 {@link ElectronicCouponListRequest}
	 * @return 电子卡券表的列表信息 {@link ElectronicCouponListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electroniccoupon/list")
	BaseResponse<ElectronicCouponListResponse> list(@RequestBody @Valid ElectronicCouponListRequest electronicCouponListReq);

	/**
	 * 单个查询电子卡券表API
	 *
	 * @author 许云鹏
	 * @param electronicCouponByIdRequest 单个查询电子卡券表请求参数 {@link ElectronicCouponByIdRequest}
	 * @return 电子卡券表详情 {@link ElectronicCouponByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electroniccoupon/get-by-id")
	BaseResponse<ElectronicCouponByIdResponse> getById(@RequestBody @Valid ElectronicCouponByIdRequest electronicCouponByIdRequest);

	/**
	 * 商品关联卡券信息API
	 *
	 * @author 许云鹏
	 * @param electronicSendRecordNumRequest 商品关联卡券信息请求参数 {@link ElectronicSendRecordNumRequest}
	 * @return 电子卡券表详情 {@link ElectronicSendRecordNumResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/electroniccoupon/goods-card-info")
	BaseResponse<ElectronicSendRecordNumResponse> goodsCardInfo(@RequestBody @Valid ElectronicSendRecordNumRequest electronicSendRecordNumRequest);

}

