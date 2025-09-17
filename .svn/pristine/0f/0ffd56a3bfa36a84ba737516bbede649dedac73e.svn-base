package com.wanmi.sbc.vas.api.provider.recommend.goodscorrelationmodelsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingAddRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingDelByIdListRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingDelByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingModifyRequest;
import com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingAddResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>保存服务Provider</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@FeignClient(value = "${application.vas.name}", contextId = "GoodsCorrelationModelSettingProvider")
public interface GoodsCorrelationModelSettingProvider {

	/**
	 * 新增API
	 *
	 * @author zhongjichuan
	 * @param goodsCorrelationModelSettingAddRequest 新增参数结构 {@link GoodsCorrelationModelSettingAddRequest}
	 * @return 新增的信息 {@link GoodsCorrelationModelSettingAddResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodscorrelationmodelsetting/add")
    BaseResponse<GoodsCorrelationModelSettingAddResponse> add(@RequestBody @Valid GoodsCorrelationModelSettingAddRequest goodsCorrelationModelSettingAddRequest);

	/**
	 * 修改API
	 *
	 * @author zhongjichuan
	 * @param goodsCorrelationModelSettingModifyRequest 修改参数结构 {@link GoodsCorrelationModelSettingModifyRequest}
	 * @return 修改的信息 {@link GoodsCorrelationModelSettingListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodscorrelationmodelsetting/modify")
    BaseResponse<GoodsCorrelationModelSettingListResponse> modify(@RequestBody @Valid GoodsCorrelationModelSettingModifyRequest goodsCorrelationModelSettingModifyRequest);

	/**
	 * 单个删除API
	 *
	 * @author zhongjichuan
	 * @param goodsCorrelationModelSettingDelByIdRequest 单个删除参数结构 {@link GoodsCorrelationModelSettingDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodscorrelationmodelsetting/delete-by-id")
    BaseResponse deleteById(@RequestBody @Valid GoodsCorrelationModelSettingDelByIdRequest goodsCorrelationModelSettingDelByIdRequest);

	/**
	 * 批量删除API
	 *
	 * @author zhongjichuan
	 * @param goodsCorrelationModelSettingDelByIdListRequest 批量删除参数结构 {@link GoodsCorrelationModelSettingDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodscorrelationmodelsetting/delete-by-id-list")
    BaseResponse deleteByIdList(@RequestBody @Valid GoodsCorrelationModelSettingDelByIdListRequest goodsCorrelationModelSettingDelByIdListRequest);

}

