package com.wanmi.sbc.setting.api.provider.recommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.recommend.RecommendByPageCodeRequest;
import com.wanmi.sbc.setting.api.request.recommend.RecommendPageRequest;
import com.wanmi.sbc.setting.api.response.recommend.RecommendPageResponse;
import com.wanmi.sbc.setting.api.request.recommend.RecommendListRequest;
import com.wanmi.sbc.setting.api.response.recommend.RecommendListResponse;
import com.wanmi.sbc.setting.api.request.recommend.RecommendByIdRequest;
import com.wanmi.sbc.setting.api.response.recommend.RecommendByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>种草信息表查询服务Provider</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@FeignClient(value = "${application.setting.name}", contextId = "RecommendQueryProvider")
public interface RecommendQueryProvider {

	/**
	 * 分页查询种草信息表API
	 *
	 * @author 黄昭
	 * @param recommendPageReq 分页请求参数和筛选对象 {@link RecommendPageRequest}
	 * @return 种草信息表分页列表信息 {@link RecommendPageResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommend/page")
	BaseResponse<RecommendPageResponse> page(@RequestBody @Valid RecommendPageRequest recommendPageReq);

	/**
	 * 列表查询种草信息表API
	 *
	 * @author 黄昭
	 * @param recommendListReq 列表请求参数和筛选对象 {@link RecommendListRequest}
	 * @return 种草信息表的列表信息 {@link RecommendListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommend/list")
	BaseResponse<RecommendListResponse> list(@RequestBody @Valid RecommendListRequest recommendListReq);

	/**
	 * 单个查询种草信息表API
	 *
	 * @author 黄昭
	 * @param recommendByIdRequest 单个查询种草信息表请求参数 {@link RecommendByIdRequest}
	 * @return 种草信息表详情 {@link RecommendByIdResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommend/get-by-id")
	BaseResponse<RecommendByIdResponse> getById(@RequestBody @Valid RecommendByIdRequest recommendByIdRequest);

	/**
	 * 单个查询种草信息表API
	 *
	 * @author xufeng
	 * @param recommendByPageCodeRequest 单个查询种草信息表请求参数 {@link RecommendByPageCodeRequest}
	 * @return 种草信息表详情 {@link RecommendByIdResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommend/get-by-pagecode")
	BaseResponse<RecommendByIdResponse> getByPageCode(@RequestBody @Valid RecommendByPageCodeRequest recommendByPageCodeRequest);

}

