package com.wanmi.sbc.order.provider.impl.wxpayuploadshippinginfo;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.wxpayuploadshippinginfo.WxPayUploadShippingInfoProvider;
import com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo.*;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayUploadShippingInfoAddResponse;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayUploadShippingInfoModifyResponse;
import com.wanmi.sbc.order.wxpayuploadshippinginfo.model.root.WxPayUploadShippingInfo;
import com.wanmi.sbc.order.wxpayuploadshippinginfo.service.WxPayUploadShippingInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>微信小程序支付发货信息保存服务接口实现</p>
 * @author 吕振伟
 * @date 2023-07-24 13:59:10
 */
@RestController
@Validated
public class WxPayUploadShippingInfoController implements WxPayUploadShippingInfoProvider {
	@Autowired
	private WxPayUploadShippingInfoService wxPayUploadShippingInfoService;

	@Override
	public BaseResponse<WxPayUploadShippingInfoAddResponse> add(@RequestBody @Valid WxPayUploadShippingInfoAddRequest wxPayUploadShippingInfoAddRequest) {
		WxPayUploadShippingInfo wxPayUploadShippingInfo = KsBeanUtil.convert(wxPayUploadShippingInfoAddRequest, WxPayUploadShippingInfo.class);
		return BaseResponse.success(new WxPayUploadShippingInfoAddResponse(
				wxPayUploadShippingInfoService.wrapperVo(wxPayUploadShippingInfoService.add(wxPayUploadShippingInfo))));
	}

	@Override
	public BaseResponse<WxPayUploadShippingInfoModifyResponse> modify(@RequestBody @Valid WxPayUploadShippingInfoModifyRequest wxPayUploadShippingInfoModifyRequest) {
		WxPayUploadShippingInfo wxPayUploadShippingInfo = KsBeanUtil.convert(wxPayUploadShippingInfoModifyRequest, WxPayUploadShippingInfo.class);
		return BaseResponse.success(new WxPayUploadShippingInfoModifyResponse(
				wxPayUploadShippingInfoService.wrapperVo(wxPayUploadShippingInfoService.modify(wxPayUploadShippingInfo))));
	}

	@Override
	public BaseResponse<WxPayUploadShippingInfoModifyResponse> modifyResultStatus(@Valid WxPayUploadShippingInfoModifyRequest wxPayUploadShippingInfoModifyRequest) {
		WxPayUploadShippingInfo wxPayUploadShippingInfo = wxPayUploadShippingInfoService.getOneByBusinessId(wxPayUploadShippingInfoModifyRequest.getBusinessId());
		wxPayUploadShippingInfo.setErrorNum(wxPayUploadShippingInfoModifyRequest.getErrorNum());
		wxPayUploadShippingInfo.setResultStatus(wxPayUploadShippingInfoModifyRequest.getResultStatus());
		wxPayUploadShippingInfo.setResultContext(wxPayUploadShippingInfoModifyRequest.getResultContext());
		wxPayUploadShippingInfo.setUpdateTime(LocalDateTime.now());
		return BaseResponse.success(new WxPayUploadShippingInfoModifyResponse(
				wxPayUploadShippingInfoService.wrapperVo(wxPayUploadShippingInfoService.modify(wxPayUploadShippingInfo))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid WxPayUploadShippingInfoDelByIdRequest wxPayUploadShippingInfoDelByIdRequest) {
		wxPayUploadShippingInfoService.deleteById(wxPayUploadShippingInfoDelByIdRequest.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid WxPayUploadShippingInfoDelByIdListRequest wxPayUploadShippingInfoDelByIdListRequest) {
		wxPayUploadShippingInfoService.deleteByIdList(wxPayUploadShippingInfoDelByIdListRequest.getIdList());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse syncByTradeIdList(@RequestBody @Valid WxPayUploadShippingInfoSyncRequest request) {
		wxPayUploadShippingInfoService.syncByTradeIds(request.getTradeIds());
		return BaseResponse.SUCCESSFUL();
	}

}

