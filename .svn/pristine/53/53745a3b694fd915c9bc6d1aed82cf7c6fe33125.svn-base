package com.wanmi.sbc.customer.provider.impl.goodsfootmark;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.api.provider.goodsfootmark.GoodsFootmarkQueryProvider;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkByIdRequest;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkQueryRequest;
import com.wanmi.sbc.customer.api.response.goodsfootmark.GoodsfootmarkCountResponse;
import com.wanmi.sbc.customer.bean.vo.GoodsFootmarkVO;
import com.wanmi.sbc.customer.goodsfootmark.model.root.GoodsFootmark;
import com.wanmi.sbc.customer.goodsfootmark.service.GoodsFootmarkService;
import com.wanmi.sbc.setting.api.provider.pagemanage.GoodsInfoExtendQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>我的足迹查询服务接口实现</p>
 * @author 
 * @date 2022-05-30 07:30:41
 */
@RestController
@Validated
public class GoodsFootmarkQueryController implements GoodsFootmarkQueryProvider {

	@Autowired private GoodsFootmarkService goodsFootmarkService;

	/**
	 * 分页查询我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkPageReq 分页请求参数和筛选对象 {@link GoodsFootmarkQueryRequest}
	 */
	@Override
	public BaseResponse<MicroServicePage<GoodsFootmarkVO>> page(@RequestBody @Valid GoodsFootmarkQueryRequest goodsFootmarkPageReq) {
		Page<GoodsFootmark> goodsFootmarkPage = goodsFootmarkService.page(goodsFootmarkPageReq);
		Page<GoodsFootmarkVO> newPage = goodsFootmarkPage.map(entity -> goodsFootmarkService.wrapperVo(entity));
		MicroServicePage<GoodsFootmarkVO> microPage = new MicroServicePage<>(newPage, goodsFootmarkPageReq.getPageable());
		return BaseResponse.success(microPage);
	}

	@Override
	public BaseResponse<GoodsfootmarkCountResponse> count(@RequestBody @Valid GoodsFootmarkQueryRequest goodsFootmarkQueryRequest) {
		return BaseResponse.success(new GoodsfootmarkCountResponse(goodsFootmarkService.count(goodsFootmarkQueryRequest)));
	}

	/**
	 * 列表查询我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkListReq 列表请求参数和筛选对象 {@link GoodsFootmarkQueryRequest}
	 */
	@Override
	public BaseResponse<List<GoodsFootmarkVO>> list(@RequestBody @Valid GoodsFootmarkQueryRequest goodsFootmarkListReq) {
		List<GoodsFootmark> goodsFootmarkList = goodsFootmarkService.list(goodsFootmarkListReq);
		List<GoodsFootmarkVO> newList = goodsFootmarkList.stream().map(entity -> goodsFootmarkService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(newList);
	}
	/**
	 * 单个查询我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkByIdRequest 单个查询我的足迹请求参数 {@link GoodsFootmarkByIdRequest}
	 */
	@Override
	public BaseResponse<GoodsFootmarkVO> getById(@RequestBody @Valid GoodsFootmarkByIdRequest goodsFootmarkByIdRequest) {
		GoodsFootmark goodsFootmark = goodsFootmarkService.getById(goodsFootmarkByIdRequest.getFootmarkId());
		return BaseResponse.success(goodsFootmarkService.wrapperVo(goodsFootmark));
	}

}

