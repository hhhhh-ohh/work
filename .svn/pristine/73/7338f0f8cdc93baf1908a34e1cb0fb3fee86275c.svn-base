package com.wanmi.sbc.vas.api.provider.recommend.goodscorrelationmodelsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingByIdRequest;
import com.wanmi.sbc.vas.api.request.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingPageRequest;
import com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingByIdResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingListResponse;
import com.wanmi.sbc.vas.api.response.recommend.goodscorrelationmodelsetting.GoodsCorrelationModelSettingPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>查询服务Provider</p>
 * @author zhongjichuan
 * @date 2020-11-27 11:27:06
 */
@FeignClient(value = "${application.vas.name}", contextId = "GoodsCorrelationModelSettingQueryProvider")
public interface GoodsCorrelationModelSettingQueryProvider {

	/**
	 * 分页查询API
	 *
	 * @author zhongjichuan
	 * @param goodsCorrelationModelSettingPageReq 分页请求参数和筛选对象 {@link GoodsCorrelationModelSettingPageRequest}
	 * @return 分页列表信息 {@link GoodsCorrelationModelSettingPageResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodscorrelationmodelsetting/page")
    BaseResponse<GoodsCorrelationModelSettingPageResponse> page(@RequestBody @Valid GoodsCorrelationModelSettingPageRequest goodsCorrelationModelSettingPageReq);

	/**
	 * 列表查询API
	 *
	 * @author zhongjichuan
	 * @return 的列表信息 {@link GoodsCorrelationModelSettingListResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodscorrelationmodelsetting/list")
    BaseResponse<GoodsCorrelationModelSettingListResponse> list();

	/**
	 * 单个查询API
	 *
	 * @author zhongjichuan
	 * @param goodsCorrelationModelSettingByIdRequest 单个查询请求参数 {@link GoodsCorrelationModelSettingByIdRequest}
	 * @return 详情 {@link GoodsCorrelationModelSettingByIdResponse}
	 */
	@PostMapping("/vas/${application.vas.version}/goodscorrelationmodelsetting/get-by-id")
    BaseResponse<GoodsCorrelationModelSettingByIdResponse> getById(@RequestBody @Valid GoodsCorrelationModelSettingByIdRequest goodsCorrelationModelSettingByIdRequest);

}

