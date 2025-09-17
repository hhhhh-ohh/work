package com.wanmi.sbc.goods.provider.impl.goodspropcaterel;

import com.wanmi.sbc.goods.api.request.goodspropcaterel.*;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyVO;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.provider.goodspropcaterel.GoodsPropCateRelProvider;
import com.wanmi.sbc.goods.api.response.goodspropcaterel.GoodsPropCateRelAddResponse;
import com.wanmi.sbc.goods.api.response.goodspropcaterel.GoodsPropCateRelModifyResponse;
import com.wanmi.sbc.goods.goodspropcaterel.service.GoodsPropCateRelService;
import com.wanmi.sbc.goods.goodspropcaterel.model.root.GoodsPropCateRel;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>商品类目与属性关联保存服务接口实现</p>
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
@RestController
@Validated
public class GoodsPropCateRelController implements GoodsPropCateRelProvider {
	@Autowired
	private GoodsPropCateRelService goodsPropCateRelService;

	@Override
	public BaseResponse<GoodsPropCateRelAddResponse> add(@RequestBody @Valid GoodsPropCateRelAddRequest goodsPropCateRelAddRequest) {
		GoodsPropCateRel goodsPropCateRel = KsBeanUtil.convert(goodsPropCateRelAddRequest, GoodsPropCateRel.class);
		return BaseResponse.success(new GoodsPropCateRelAddResponse(
				goodsPropCateRelService.wrapperVo(goodsPropCateRelService.add(goodsPropCateRel))));
	}

	@Override
	public BaseResponse modifySort(@RequestBody @Valid GoodsPropCateSortRequest request) {
		List<GoodsPropertyVO> goodsPropCateVOList = request.getGoodsPropCateVOList();
		goodsPropCateRelService.modifySort(goodsPropCateVOList);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid GoodsPropCateRelDelByIdRequest goodsPropCateRelDelByIdRequest) {
		GoodsPropCateRel goodsPropCateRel = KsBeanUtil.convert(goodsPropCateRelDelByIdRequest, GoodsPropCateRel.class);
		goodsPropCateRel.setDelFlag(DeleteFlag.YES);
		goodsPropCateRelService.deleteById(goodsPropCateRel);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid GoodsPropCateRelDelByIdListRequest goodsPropCateRelDelByIdListRequest) {
		List<GoodsPropCateRel> goodsPropCateRelList = goodsPropCateRelDelByIdListRequest.getRelIdList().stream()
			.map(RelId -> {
				GoodsPropCateRel goodsPropCateRel = KsBeanUtil.convert(RelId, GoodsPropCateRel.class);
				goodsPropCateRel.setDelFlag(DeleteFlag.YES);
				return goodsPropCateRel;
			}).collect(Collectors.toList());
		goodsPropCateRelService.deleteByIdList(goodsPropCateRelList);
		return BaseResponse.SUCCESSFUL();
	}

}

