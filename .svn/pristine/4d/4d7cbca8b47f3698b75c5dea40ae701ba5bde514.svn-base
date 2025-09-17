package com.wanmi.sbc.customer.api.provider.goodsfootmark;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.customer.api.request.goodsfootmark.*;
import com.wanmi.sbc.customer.bean.vo.GoodsFootmarkVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>我的足迹保存服务Provider</p>
 * @author 
 * @date 2022-05-30 07:30:41
 */
@FeignClient(value = "${application.customer.name}", contextId = "GoodsFootmarkSaveProvider")
public interface GoodsFootmarkSaveProvider {

	/**
	 * 新增我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkAddRequest 我的足迹新增参数结构 {@link GoodsFootmarkAddRequest}
	 * @return 新增的我的足迹信息 {@link GoodsFootmarkVO}
	 */
	@PostMapping("/customer/${application.customer.version}/goodsfootmark/add")
	BaseResponse add(@RequestBody @Valid GoodsFootmarkAddRequest goodsFootmarkAddRequest);

	/**
	 * 修改我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkModifyRequest 我的足迹修改参数结构 {@link GoodsFootmarkModifyRequest}
	 * @return 修改的我的足迹信息 {@link GoodsFootmarkVO}
	 */
	@PostMapping("/customer/${application.customer.version}/goodsfootmark/modify")
	BaseResponse<GoodsFootmarkVO> modify(@RequestBody @Valid GoodsFootmarkModifyRequest goodsFootmarkModifyRequest);

	/**
	 * 单个删除我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkDelByIdRequest 单个删除参数结构 {@link GoodsFootmarkDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/goodsfootmark/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid GoodsFootmarkDelByIdRequest goodsFootmarkDelByIdRequest);

	/**
	 * 批量删除我的足迹API
	 *
	 * @author 
	 * @param goodsFootmarkDelByIdListRequest 批量删除参数结构 {@link GoodsFootmarkDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/customer/${application.customer.version}/goodsfootmark/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid GoodsFootmarkDelByIdListRequest goodsFootmarkDelByIdListRequest);

	/**
	 * 批量删除我的足迹 3个月以上
	 *
	 */
	@PostMapping("/customer/${application.customer.version}/goodsfootmark/delete-by-updatetime")
	BaseResponse deleteByUpdateTime(@RequestBody @Valid GoodsFootmarkDelByTimeRequest goodsFootmarkDelByTimeRequest);



}

