package com.wanmi.sbc.customer.api.provider.goodsfootmark;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkByIdRequest;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkQueryRequest;
import com.wanmi.sbc.customer.api.response.goodsfootmark.GoodsfootmarkCountResponse;
import com.wanmi.sbc.customer.bean.vo.GoodsFootmarkVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>我的足迹查询服务Provider</p>
 * @author 
 * @date 2022-05-30 07:30:41
 */
@FeignClient(value = "${application.customer.name}", contextId = "GoodsFootmarkQueryProvider")
public interface GoodsFootmarkQueryProvider {

	/**
	 * 分页查询我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkPageReq 分页请求参数和筛选对象 {@link GoodsFootmarkQueryRequest}
	 */
	@PostMapping("/customer/${application.customer.version}/goodsfootmark/page")
	BaseResponse<MicroServicePage<GoodsFootmarkVO>> page(@RequestBody @Valid GoodsFootmarkQueryRequest goodsFootmarkPageReq);

	/**
	 * 统计我的足迹记录数量
	 *
	 * @author
	 * @param goodsFootmarkPageReq 分页请求参数和筛选对象 {@link GoodsFootmarkQueryRequest}
	 */
	@PostMapping("/customer/${application.customer.version}/goodsfootmark/count")
	BaseResponse<GoodsfootmarkCountResponse> count(@RequestBody @Valid GoodsFootmarkQueryRequest goodsFootmarkPageReq);

	/**
	 * 列表查询我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkListReq 列表请求参数和筛选对象 {@link GoodsFootmarkQueryRequest}
	 */
	@PostMapping("/customer/${application.customer.version}/goodsfootmark/list")
	BaseResponse<List<GoodsFootmarkVO>> list(@RequestBody @Valid GoodsFootmarkQueryRequest goodsFootmarkListReq);

	/**
	 * 单个查询我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkByIdRequest 单个查询我的足迹请求参数 {@link GoodsFootmarkByIdRequest}
	 */
	@PostMapping("/customer/${application.customer.version}/goodsfootmark/get-by-id")
	BaseResponse<GoodsFootmarkVO> getById(@RequestBody @Valid GoodsFootmarkByIdRequest goodsFootmarkByIdRequest);

}

