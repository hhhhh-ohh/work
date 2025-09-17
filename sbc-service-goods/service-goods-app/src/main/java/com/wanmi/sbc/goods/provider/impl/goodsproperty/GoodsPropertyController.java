package com.wanmi.sbc.goods.provider.impl.goodsproperty;

import com.wanmi.sbc.goods.api.request.goodsproperty.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.provider.goodsproperty.GoodsPropertyProvider;
import com.wanmi.sbc.goods.api.response.goodsproperty.GoodsPropertyAddResponse;
import com.wanmi.sbc.goods.api.response.goodsproperty.GoodsPropertyModifyResponse;
import com.wanmi.sbc.goods.goodsproperty.service.GoodsPropertyService;
import com.wanmi.sbc.goods.goodsproperty.model.root.GoodsProperty;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>商品属性保存服务接口实现</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@RestController
@Validated
public class GoodsPropertyController implements GoodsPropertyProvider {

	@Autowired
	private GoodsPropertyService goodsPropertyService;

	@Override
	public BaseResponse<Long> saveGoodsProperty(@RequestBody @Valid GoodsPropertyAddRequest request) {
		Long propId = goodsPropertyService.saveGoodsProperty(request);
		return BaseResponse.success(propId);
	}

	@Override
	public BaseResponse<GoodsPropertyModifyResponse> modifyGoodsProperty(@RequestBody @Valid GoodsPropertyModifyRequest request) {
		GoodsPropertyModifyResponse response = goodsPropertyService.modifyGoodsProperty(request);
		return BaseResponse.success(response);
	}

	@Override
	public BaseResponse modifyIndexFlag(@RequestBody @Valid GoodsPropertyIndexRequest request) {
		goodsPropertyService.updateIndexFlag(request);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid GoodsPropertyDelByIdRequest request) {
		goodsPropertyService.deleteById(request.getPropId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid GoodsPropertyDelByIdListRequest goodsPropertyDelByIdListRequest) {
		List<GoodsProperty> goodsPropertyList = goodsPropertyDelByIdListRequest.getPropIdList().stream()
			.map(PropId -> {
				GoodsProperty goodsProperty = KsBeanUtil.convert(PropId, GoodsProperty.class);
				goodsProperty.setDelFlag(DeleteFlag.YES);
				return goodsProperty;
			}).collect(Collectors.toList());
		goodsPropertyService.deleteByIdList(goodsPropertyList);
		return BaseResponse.SUCCESSFUL();
	}

}

