package com.wanmi.sbc.vas.provider.impl.recommend.goodscorrelationmodelsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingQueryProvider;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingPageRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingQueryRequest;
import com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingListResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingPageResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsCorrelationModelSettingVO;
import com.wanmi.sbc.vas.recommend.goodscorrelationmodelsetting.model.root.GoodsCorrelationModelSetting;
import com.wanmi.sbc.vas.recommend.goodscorrelationmodelsetting.service.GoodsCorrelationModelSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>查询服务接口实现</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@RestController
@Validated
public class GoodsCorrelationModelSettingQueryController implements GoodsCorrelationModelSettingQueryProvider {
	@Autowired
	private GoodsCorrelationModelSettingService goodsCorrelationModelSettingService;

	@Override
	public BaseResponse<GoodsCorrelationModelSettingPageResponse> page(@RequestBody @Valid GoodsCorrelationModelSettingPageRequest goodsCorrelationModelSettingPageReq) {
		GoodsCorrelationModelSettingQueryRequest queryReq = KsBeanUtil.convert(goodsCorrelationModelSettingPageReq, GoodsCorrelationModelSettingQueryRequest.class);
		Page<GoodsCorrelationModelSetting> goodsCorrelationModelSettingPage = goodsCorrelationModelSettingService.page(queryReq);
		Page<GoodsCorrelationModelSettingVO> newPage = goodsCorrelationModelSettingPage.map(entity -> goodsCorrelationModelSettingService.wrapperVo(entity));
		MicroServicePage<GoodsCorrelationModelSettingVO> microPage = new MicroServicePage<>(newPage, goodsCorrelationModelSettingPageReq.getPageable());
		GoodsCorrelationModelSettingPageResponse finalRes = new GoodsCorrelationModelSettingPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<GoodsCorrelationModelSettingListResponse> list() {
		GoodsCorrelationModelSettingQueryRequest queryReq = GoodsCorrelationModelSettingQueryRequest.builder().delFlag(DeleteFlag.NO).build();
		List<GoodsCorrelationModelSetting> goodsCorrelationModelSettingList = goodsCorrelationModelSettingService.list(queryReq);
		List<GoodsCorrelationModelSettingVO> newList = goodsCorrelationModelSettingList.stream().map(entity -> goodsCorrelationModelSettingService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new GoodsCorrelationModelSettingListResponse(newList));
	}

	@Override
	public BaseResponse<GoodsCorrelationModelSettingByIdResponse> getById(@RequestBody @Valid GoodsCorrelationModelSettingByIdRequest goodsCorrelationModelSettingByIdRequest) {
		GoodsCorrelationModelSetting goodsCorrelationModelSetting =
		goodsCorrelationModelSettingService.getOne(goodsCorrelationModelSettingByIdRequest.getId());
		return BaseResponse.success(new GoodsCorrelationModelSettingByIdResponse(goodsCorrelationModelSettingService.wrapperVo(goodsCorrelationModelSetting)));
	}

}

