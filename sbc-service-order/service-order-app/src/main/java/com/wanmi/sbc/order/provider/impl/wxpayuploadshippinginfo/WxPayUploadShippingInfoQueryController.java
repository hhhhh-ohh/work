package com.wanmi.sbc.order.provider.impl.wxpayuploadshippinginfo;

import com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo.*;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayShippingOrderStatusResponse;
import com.wanmi.sbc.order.bean.vo.WxPayUploadShippingInfoVO;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.order.api.provider.wxpayuploadshippinginfo.WxPayUploadShippingInfoQueryProvider;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayUploadShippingInfoPageResponse;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayUploadShippingInfoListResponse;
import com.wanmi.sbc.order.api.response.wxpayuploadshippinginfo.WxPayUploadShippingInfoByIdResponse;
import com.wanmi.sbc.order.wxpayuploadshippinginfo.service.WxPayUploadShippingInfoService;
import com.wanmi.sbc.order.wxpayuploadshippinginfo.model.root.WxPayUploadShippingInfo;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>微信小程序支付发货信息查询服务接口实现</p>
 * @author 吕振伟
 * @date 2023-07-24 13:59:10
 */
@RestController
@Validated
public class WxPayUploadShippingInfoQueryController implements WxPayUploadShippingInfoQueryProvider {
	@Autowired
	private WxPayUploadShippingInfoService wxPayUploadShippingInfoService;

	@Override
	public BaseResponse<WxPayUploadShippingInfoPageResponse> page(@RequestBody @Valid WxPayUploadShippingInfoPageRequest wxPayUploadShippingInfoPageReq) {
		WxPayUploadShippingInfoQueryRequest queryReq = KsBeanUtil.convert(wxPayUploadShippingInfoPageReq, WxPayUploadShippingInfoQueryRequest.class);
		Page<WxPayUploadShippingInfo> wxPayUploadShippingInfoPage = wxPayUploadShippingInfoService.page(queryReq);
		Page<WxPayUploadShippingInfoVO> newPage = wxPayUploadShippingInfoPage.map(entity -> wxPayUploadShippingInfoService.wrapperVo(entity));
		MicroServicePage<WxPayUploadShippingInfoVO> microPage = new MicroServicePage<>(newPage, wxPayUploadShippingInfoPageReq.getPageable());
		WxPayUploadShippingInfoPageResponse finalRes = new WxPayUploadShippingInfoPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<WxPayUploadShippingInfoListResponse> list(@RequestBody @Valid WxPayUploadShippingInfoListRequest wxPayUploadShippingInfoListReq) {
		WxPayUploadShippingInfoQueryRequest queryReq = KsBeanUtil.convert(wxPayUploadShippingInfoListReq, WxPayUploadShippingInfoQueryRequest.class);
		List<WxPayUploadShippingInfo> wxPayUploadShippingInfoList = wxPayUploadShippingInfoService.list(queryReq);
		List<WxPayUploadShippingInfoVO> newList = wxPayUploadShippingInfoList.stream().map(entity -> wxPayUploadShippingInfoService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new WxPayUploadShippingInfoListResponse(newList));
	}

	@Override
	public BaseResponse<WxPayUploadShippingInfoByIdResponse> getById(@RequestBody @Valid WxPayUploadShippingInfoByIdRequest wxPayUploadShippingInfoByIdRequest) {
		WxPayUploadShippingInfo wxPayUploadShippingInfo =
		wxPayUploadShippingInfoService.getOne(wxPayUploadShippingInfoByIdRequest.getId());
		return BaseResponse.success(new WxPayUploadShippingInfoByIdResponse(wxPayUploadShippingInfoService.wrapperVo(wxPayUploadShippingInfo)));
	}

	@Override
	public BaseResponse handleWxPayUploadShippingInfo(WxPayUploadShippingInfoListRequest wxPayUploadShippingInfoListReq) {
		wxPayUploadShippingInfoService.handleWxPayUploadShippingInfo(wxPayUploadShippingInfoListReq);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<WxPayShippingOrderStatusResponse> getOrderStatus(@RequestBody @Valid WxPayShippingOrderStatusRequest request) {
		WxPayShippingOrderStatusResponse response = wxPayUploadShippingInfoService.getWxOrderShippingStatus(request.getTradeId());
		return BaseResponse.success(response);
	}



}

