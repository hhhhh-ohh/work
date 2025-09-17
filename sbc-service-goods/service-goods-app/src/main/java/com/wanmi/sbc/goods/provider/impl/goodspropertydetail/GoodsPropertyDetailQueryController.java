package com.wanmi.sbc.goods.provider.impl.goodspropertydetail;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.goodspropertydetail.GoodsPropertyDetailQueryProvider;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailPageRequest;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailQueryRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetail.GoodsPropertyDetailPageResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailListRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetail.GoodsPropertyDetailListResponse;
import com.wanmi.sbc.goods.api.request.goodspropertydetail.GoodsPropertyDetailByIdRequest;
import com.wanmi.sbc.goods.api.response.goodspropertydetail.GoodsPropertyDetailByIdResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsPropertyDetailVO;
import com.wanmi.sbc.goods.goodspropertydetail.service.GoodsPropertyDetailService;
import com.wanmi.sbc.goods.goodspropertydetail.model.root.GoodsPropertyDetail;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>商品属性值查询服务接口实现</p>
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
@RestController
@Validated
public class GoodsPropertyDetailQueryController implements GoodsPropertyDetailQueryProvider {
	@Autowired
	private GoodsPropertyDetailService goodsPropertyDetailService;

	@Override
	public BaseResponse<GoodsPropertyDetailPageResponse> page(@RequestBody @Valid GoodsPropertyDetailPageRequest goodsPropertyDetailPageReq) {
		GoodsPropertyDetailQueryRequest queryReq = KsBeanUtil.convert(goodsPropertyDetailPageReq, GoodsPropertyDetailQueryRequest.class);
		Page<GoodsPropertyDetail> goodsPropertyDetailPage = goodsPropertyDetailService.page(queryReq);
		Page<GoodsPropertyDetailVO> newPage = goodsPropertyDetailPage.map(entity -> goodsPropertyDetailService.wrapperVo(entity));
		MicroServicePage<GoodsPropertyDetailVO> microPage = new MicroServicePage<>(newPage, goodsPropertyDetailPageReq.getPageable());
		GoodsPropertyDetailPageResponse finalRes = new GoodsPropertyDetailPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	@Override
	public BaseResponse<GoodsPropertyDetailListResponse> list(@RequestBody @Valid GoodsPropertyDetailListRequest goodsPropertyDetailListReq) {
		GoodsPropertyDetailQueryRequest queryReq = KsBeanUtil.convert(goodsPropertyDetailListReq, GoodsPropertyDetailQueryRequest.class);
		List<GoodsPropertyDetail> goodsPropertyDetailList = goodsPropertyDetailService.list(queryReq);
		List<GoodsPropertyDetailVO> newList = goodsPropertyDetailList.stream().map(entity -> goodsPropertyDetailService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new GoodsPropertyDetailListResponse(newList));
	}

	@Override
	public BaseResponse<GoodsPropertyDetailByIdResponse> getById(@RequestBody @Valid GoodsPropertyDetailByIdRequest goodsPropertyDetailByIdRequest) {
		GoodsPropertyDetail goodsPropertyDetail =
		goodsPropertyDetailService.getOne(goodsPropertyDetailByIdRequest.getDetailId());
		return BaseResponse.success(new GoodsPropertyDetailByIdResponse(goodsPropertyDetailService.wrapperVo(goodsPropertyDetail)));
	}

}

