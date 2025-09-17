package com.wanmi.sbc.goods.provider.impl.goodspropertydetail;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.provider.goodspropertydetail.GoodsPropertyDetailProvider;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailAddRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetail.GoodsPropertyDetailAddResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailModifyRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetail.GoodsPropertyDetailModifyResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailDelByIdRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailDelByIdListRequest;
import com.wanmi.sbc.goods.goodspropertydetail.service.GoodsPropertyDetailService;
import com.wanmi.sbc.goods.goodspropertydetail.model.root.GoodsPropertyDetail;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>商品属性值保存服务接口实现</p>
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
@RestController
@Validated
public class GoodsPropertyDetailController implements GoodsPropertyDetailProvider {
	@Autowired
	private GoodsPropertyDetailService goodsPropertyDetailService;

	@Override
	public BaseResponse<GoodsPropertyDetailAddResponse> add(@RequestBody @Valid GoodsPropertyDetailAddRequest goodsPropertyDetailAddRequest) {
		GoodsPropertyDetail goodsPropertyDetail = KsBeanUtil.convert(goodsPropertyDetailAddRequest, GoodsPropertyDetail.class);
		return BaseResponse.success(new GoodsPropertyDetailAddResponse(
				goodsPropertyDetailService.wrapperVo(goodsPropertyDetailService.add(goodsPropertyDetail))));
	}

	@Override
	public BaseResponse<GoodsPropertyDetailModifyResponse> modify(@RequestBody @Valid GoodsPropertyDetailModifyRequest goodsPropertyDetailModifyRequest) {
		GoodsPropertyDetail goodsPropertyDetail = KsBeanUtil.convert(goodsPropertyDetailModifyRequest, GoodsPropertyDetail.class);
		return BaseResponse.success(new GoodsPropertyDetailModifyResponse(
				goodsPropertyDetailService.wrapperVo(goodsPropertyDetailService.modify(goodsPropertyDetail))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid GoodsPropertyDetailDelByIdRequest goodsPropertyDetailDelByIdRequest) {
		GoodsPropertyDetail goodsPropertyDetail = KsBeanUtil.convert(goodsPropertyDetailDelByIdRequest, GoodsPropertyDetail.class);
		goodsPropertyDetail.setDelFlag(DeleteFlag.YES);
		goodsPropertyDetailService.deleteById(goodsPropertyDetail);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid GoodsPropertyDetailDelByIdListRequest goodsPropertyDetailDelByIdListRequest) {
		List<GoodsPropertyDetail> goodsPropertyDetailList = goodsPropertyDetailDelByIdListRequest.getDetailIdList().stream()
			.map(DetailId -> {
				GoodsPropertyDetail goodsPropertyDetail = KsBeanUtil.convert(DetailId, GoodsPropertyDetail.class);
				goodsPropertyDetail.setDelFlag(DeleteFlag.YES);
				return goodsPropertyDetail;
			}).collect(Collectors.toList());
		goodsPropertyDetailService.deleteByIdList(goodsPropertyDetailList);
		return BaseResponse.SUCCESSFUL();
	}

}

