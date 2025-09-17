package com.wanmi.sbc.goods.provider.impl.goodspropertydetailrel;

import com.wanmi.sbc.goods.api.request.standard.StandardImportGoodsRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyDetailRelVO;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.api.provider.goodspropertydetailrel.GoodsPropertyDetailRelProvider;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelAddRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailRelAddResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelModifyRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetailrel.GoodsPropertyDetailRelModifyResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelDelByIdRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetailrel.GoodsPropertyDetailRelDelByIdListRequest;
import com.wanmi.sbc.goods.goodspropertydetailrel.service.GoodsPropertyDetailRelService;
import com.wanmi.sbc.goods.goodspropertydetailrel.model.root.GoodsPropertyDetailRel;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.validation.Valid;

/**
 * <p>商品与属性值关联保存服务接口实现</p>
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@RestController
@Validated
public class GoodsPropertyDetailRelController implements GoodsPropertyDetailRelProvider {
	@Autowired
	private GoodsPropertyDetailRelService goodsPropertyDetailRelService;

	@Override
	public BaseResponse<GoodsPropertyDetailRelAddResponse> add(@RequestBody @Valid GoodsPropertyDetailRelAddRequest goodsPropertyDetailRelAddRequest) {
		GoodsPropertyDetailRel goodsPropertyDetailRel = KsBeanUtil.convert(goodsPropertyDetailRelAddRequest, GoodsPropertyDetailRel.class);
		return BaseResponse.success(new GoodsPropertyDetailRelAddResponse(
				goodsPropertyDetailRelService.wrapperVo(goodsPropertyDetailRelService.add(goodsPropertyDetailRel))));
	}

	@Override
	public BaseResponse<GoodsPropertyDetailRelModifyResponse> modify(@RequestBody @Valid GoodsPropertyDetailRelModifyRequest goodsPropertyDetailRelModifyRequest) {
		GoodsPropertyDetailRel goodsPropertyDetailRel = KsBeanUtil.convert(goodsPropertyDetailRelModifyRequest, GoodsPropertyDetailRel.class);
		return BaseResponse.success(new GoodsPropertyDetailRelModifyResponse(
				goodsPropertyDetailRelService.wrapperVo(goodsPropertyDetailRelService.modify(goodsPropertyDetailRel))));
	}

	@Override
	public BaseResponse deleteById(@RequestBody @Valid GoodsPropertyDetailRelDelByIdRequest goodsPropertyDetailRelDelByIdRequest) {
		GoodsPropertyDetailRel goodsPropertyDetailRel = KsBeanUtil.convert(goodsPropertyDetailRelDelByIdRequest, GoodsPropertyDetailRel.class);
		goodsPropertyDetailRel.setDelFlag(DeleteFlag.YES);
		goodsPropertyDetailRelService.deleteById(goodsPropertyDetailRel);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse deleteByIdList(@RequestBody @Valid GoodsPropertyDetailRelDelByIdListRequest goodsPropertyDetailRelDelByIdListRequest) {
		List<GoodsPropertyDetailRel> goodsPropertyDetailRelList = goodsPropertyDetailRelDelByIdListRequest.getDetailRelIdList().stream()
			.map(DetailRelId -> {
				GoodsPropertyDetailRel goodsPropertyDetailRel = KsBeanUtil.convert(DetailRelId, GoodsPropertyDetailRel.class);
				goodsPropertyDetailRel.setDelFlag(DeleteFlag.YES);
				return goodsPropertyDetailRel;
			}).collect(Collectors.toList());
		goodsPropertyDetailRelService.deleteByIdList(goodsPropertyDetailRelList);
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse importGoodsDetailRel(@RequestBody StandardImportGoodsRequest request) {
		/*List<GoodsPropertyDetailRelVO> detailRelVOList = goodsPropertyDetailRelService.findByGoodsId(request.getGoodsIds());
		goodsPropertyDetailRelService.importGoodsDetailRel(detailRelVOList);*/
		return BaseResponse.SUCCESSFUL();
	}

}

