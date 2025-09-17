package com.wanmi.sbc.vas.provider.impl.recommend.goodscorrelationmodelsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.vas.api.provider.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingProvider;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingAddRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingDelByIdListRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingDelByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingModifyRequest;
import com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingListResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.GoodsCorrelationModelSettingVO;
import com.wanmi.sbc.vas.recommend.goodscorrelationmodelsetting.model.root.GoodsCorrelationModelSetting;
import com.wanmi.sbc.vas.recommend.goodscorrelationmodelsetting.service.GoodsCorrelationModelSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>保存服务接口实现</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@RestController
@Validated
public class GoodsCorrelationModelSettingController implements GoodsCorrelationModelSettingProvider {
	@Autowired
	private GoodsCorrelationModelSettingService goodsCorrelationModelSettingService;

	@Override
	public BaseResponse<GoodsCorrelationModelSettingAddResponse> add(@RequestBody @Valid GoodsCorrelationModelSettingAddRequest goodsCorrelationModelSettingAddRequest) {
		GoodsCorrelationModelSetting goodsCorrelationModelSetting = KsBeanUtil.convert(goodsCorrelationModelSettingAddRequest, GoodsCorrelationModelSetting.class);
		return BaseResponse.success(new GoodsCorrelationModelSettingAddResponse(
				goodsCorrelationModelSettingService.wrapperVo(goodsCorrelationModelSettingService.add(goodsCorrelationModelSetting))));
	}

	@Override
	public BaseResponse<GoodsCorrelationModelSettingListResponse> modify(@RequestBody @Valid GoodsCorrelationModelSettingModifyRequest goodsCorrelationModelSettingModifyRequest) {
		List<GoodsCorrelationModelSetting> goodsCorrelationModelSetting = KsBeanUtil.convert(goodsCorrelationModelSettingModifyRequest.getFilterRulesSettingDTOList(), GoodsCorrelationModelSetting.class);
		goodsCorrelationModelSetting.forEach(v -> {
			v.setUpdatePerson(goodsCorrelationModelSettingModifyRequest.getUpdatePerson());
			v.setUpdateTime(goodsCorrelationModelSettingModifyRequest.getUpdateTime());
		});
		List<GoodsCorrelationModelSetting> goodsCorrelationModelSettings = goodsCorrelationModelSettingService.modify(goodsCorrelationModelSetting);
		List<GoodsCorrelationModelSettingVO> newList = goodsCorrelationModelSettings.stream().map(entity -> goodsCorrelationModelSettingService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new GoodsCorrelationModelSettingListResponse(newList));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid GoodsCorrelationModelSettingDelByIdRequest goodsCorrelationModelSettingDelByIdRequest) {
		GoodsCorrelationModelSetting goodsCorrelationModelSetting = KsBeanUtil.convert(goodsCorrelationModelSettingDelByIdRequest, GoodsCorrelationModelSetting.class);
		goodsCorrelationModelSetting.setDelFlag(DeleteFlag.YES);
		goodsCorrelationModelSettingService.deleteById(goodsCorrelationModelSetting);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid GoodsCorrelationModelSettingDelByIdListRequest goodsCorrelationModelSettingDelByIdListRequest) {
		List<GoodsCorrelationModelSetting> goodsCorrelationModelSettingList = goodsCorrelationModelSettingDelByIdListRequest.getIdList().stream()
			.map(Id -> {
				GoodsCorrelationModelSetting goodsCorrelationModelSetting = KsBeanUtil.convert(Id, GoodsCorrelationModelSetting.class);
				goodsCorrelationModelSetting.setDelFlag(DeleteFlag.YES);
				return goodsCorrelationModelSetting;
			}).collect(Collectors.toList());
		goodsCorrelationModelSettingService.deleteByIdList(goodsCorrelationModelSettingList);
		return BaseResponse.SUCCESSFUL();
	}

}

