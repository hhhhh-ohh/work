package com.wanmi.sbc.order.api.provider.wxpayuploadshippinginfo;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo.*;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayUploadShippingInfoAddResponse;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayUploadShippingInfoModifyResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>微信小程序支付发货信息保存服务Provider</p>
 * @author 吕振伟
 * @date 2023-07-24 13:59:10
 */
@FeignClient(value = "${application.order.name}", contextId = "WxPayUploadShippingInfoProvider")
public interface WxPayUploadShippingInfoProvider {

	/**
	 * 新增微信小程序支付发货信息API
	 *
	 * @author 吕振伟
	 * @param wxPayUploadShippingInfoAddRequest 微信小程序支付发货信息新增参数结构 {@link WxPayUploadShippingInfoAddRequest}
	 * @return 新增的微信小程序支付发货信息信息 {@link WxPayUploadShippingInfoAddResponse}
	 */
	@PostMapping("/order/${application.order.version}/wxpayuploadshippinginfo/add")
	BaseResponse<WxPayUploadShippingInfoAddResponse> add(@RequestBody @Valid WxPayUploadShippingInfoAddRequest wxPayUploadShippingInfoAddRequest);

	/**
	 * 修改微信小程序支付发货信息API
	 *
	 * @author 吕振伟
	 * @param wxPayUploadShippingInfoModifyRequest 微信小程序支付发货信息修改参数结构 {@link WxPayUploadShippingInfoModifyRequest}
	 * @return 修改的微信小程序支付发货信息信息 {@link WxPayUploadShippingInfoModifyResponse}
	 */
	@PostMapping("/order/${application.order.version}/wxpayuploadshippinginfo/modify")
	BaseResponse<WxPayUploadShippingInfoModifyResponse> modify(@RequestBody @Valid WxPayUploadShippingInfoModifyRequest wxPayUploadShippingInfoModifyRequest);

	/**
	 * 修改微信小程序支付发货信息API
	 *
	 * @author 吕振伟
	 * @param wxPayUploadShippingInfoModifyRequest 微信小程序支付发货信息修改参数结构 {@link WxPayUploadShippingInfoModifyRequest}
	 * @return 修改的微信小程序支付发货信息信息 {@link WxPayUploadShippingInfoModifyResponse}
	 */
	@PostMapping("/order/${application.order.version}/wxpayuploadshippinginfo/modify-result-status")
	BaseResponse<WxPayUploadShippingInfoModifyResponse> modifyResultStatus(@RequestBody @Valid WxPayUploadShippingInfoModifyRequest wxPayUploadShippingInfoModifyRequest);

	/**
	 * 单个删除微信小程序支付发货信息API
	 *
	 * @author 吕振伟
	 * @param wxPayUploadShippingInfoDelByIdRequest 单个删除参数结构 {@link WxPayUploadShippingInfoDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/order/${application.order.version}/wxpayuploadshippinginfo/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid WxPayUploadShippingInfoDelByIdRequest wxPayUploadShippingInfoDelByIdRequest);

	/**
	 * 批量删除微信小程序支付发货信息API
	 *
	 * @author 吕振伟
	 * @param wxPayUploadShippingInfoDelByIdListRequest 批量删除参数结构 {@link WxPayUploadShippingInfoDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/order/${application.order.version}/wxpayuploadshippinginfo/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid WxPayUploadShippingInfoDelByIdListRequest wxPayUploadShippingInfoDelByIdListRequest);

	/**
	 * 订单同步小程序物流信息
	 *
	 * @author 吕振伟
	 * @param request 批量删除参数结构 {@link WxPayUploadShippingInfoSyncRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/order/${application.order.version}/wxpayuploadshippinginfo/sync-by-trade-id-list")
	BaseResponse syncByTradeIdList(@RequestBody @Valid WxPayUploadShippingInfoSyncRequest request);

}

