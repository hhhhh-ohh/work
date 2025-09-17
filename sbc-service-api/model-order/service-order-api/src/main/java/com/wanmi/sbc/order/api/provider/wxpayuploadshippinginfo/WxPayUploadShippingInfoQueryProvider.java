package com.wanmi.sbc.order.api.provider.wxpayuploadshippinginfo;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo.*;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayShippingOrderStatusResponse;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayUploadShippingInfoPageResponse;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayUploadShippingInfoListResponse;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayUploadShippingInfoByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>微信小程序支付发货信息查询服务Provider</p>
 * @author 吕振伟
 * @date 2023-07-24 13:59:10
 */
@FeignClient(value = "${application.order.name}", contextId = "WxPayUploadShippingInfoQueryProvider")
public interface WxPayUploadShippingInfoQueryProvider {

	/**
	 * 分页查询微信小程序支付发货信息API
	 *
	 * @author 吕振伟
	 * @param wxPayUploadShippingInfoPageReq 分页请求参数和筛选对象 {@link WxPayUploadShippingInfoPageRequest}
	 * @return 微信小程序支付发货信息分页列表信息 {@link WxPayUploadShippingInfoPageResponse}
	 */
	@PostMapping("/order/${application.order.version}/wxpayuploadshippinginfo/page")
	BaseResponse<WxPayUploadShippingInfoPageResponse> page(@RequestBody @Valid WxPayUploadShippingInfoPageRequest wxPayUploadShippingInfoPageReq);

	/**
	 * 列表查询微信小程序支付发货信息API
	 *
	 * @author 吕振伟
	 * @param wxPayUploadShippingInfoListReq 列表请求参数和筛选对象 {@link WxPayUploadShippingInfoListRequest}
	 * @return 微信小程序支付发货信息的列表信息 {@link WxPayUploadShippingInfoListResponse}
	 */
	@PostMapping("/order/${application.order.version}/wxpayuploadshippinginfo/list")
	BaseResponse<WxPayUploadShippingInfoListResponse> list(@RequestBody @Valid WxPayUploadShippingInfoListRequest wxPayUploadShippingInfoListReq);

	/**
	 * 单个查询微信小程序支付发货信息API
	 *
	 * @author 吕振伟
	 * @param wxPayUploadShippingInfoByIdRequest 单个查询微信小程序支付发货信息请求参数 {@link WxPayUploadShippingInfoByIdRequest}
	 * @return 微信小程序支付发货信息详情 {@link WxPayUploadShippingInfoByIdResponse}
	 */
	@PostMapping("/order/${application.order.version}/wxpayuploadshippinginfo/get-by-id")
	BaseResponse<WxPayUploadShippingInfoByIdResponse> getById(@RequestBody @Valid WxPayUploadShippingInfoByIdRequest wxPayUploadShippingInfoByIdRequest);

	/**
	 * 补偿处理微信小程序支付发货信息API
	 * @param wxPayUploadShippingInfoListReq
	 * @return
	 */
	@PostMapping("/order/${application.order.version}/wxpayuploadshippinginfo/handle-wx-pay-upload-shipping-info")
 	BaseResponse handleWxPayUploadShippingInfo(@RequestBody @Valid WxPayUploadShippingInfoListRequest wxPayUploadShippingInfoListReq);

    /**
     * 订单同步小程序物流查询状态信息
     *
     * @author 吕振伟
     * @param request 批量删除参数结构 {@link WxPayUploadShippingInfoSyncRequest}
     * @return 删除结果 {@link BaseResponse}
     */
	@PostMapping("/order/${application.order.version}/wxpayuploadshippinginfo/order-status")
	BaseResponse<WxPayShippingOrderStatusResponse> getOrderStatus(@RequestBody @Valid WxPayShippingOrderStatusRequest request);
}

