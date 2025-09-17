package com.wanmi.sbc.setting.api.provider.recommend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.recommend.*;
import com.wanmi.sbc.setting.api.request.recommend.RecommendAddRequest;
import com.wanmi.sbc.setting.api.request.recommend.RecommendByIdRequest;
import com.wanmi.sbc.setting.api.response.recommend.RecommendAddResponse;
import com.wanmi.sbc.setting.api.response.recommend.RecommendModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>种草信息表保存服务Provider</p>
 * @author 黄昭
 * @date 2022-05-17 16:24:21
 */
@FeignClient(value = "${application.setting.name}", contextId = "RecommendProvider")
public interface RecommendProvider {

	/**
	 * 新增种草信息表API
	 *
	 * @author 黄昭
	 * @param recommendAddRequest 种草信息表新增参数结构 {@link RecommendAddRequest}
	 * @return 新增的种草信息表信息 {@link RecommendAddResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommend/add")
	BaseResponse<RecommendAddResponse> add(@RequestBody @Valid RecommendAddRequest recommendAddRequest);

	/**
	 * 修改种草信息表API
	 *
	 * @author 黄昭
	 * @param recommendModifyRequest 种草信息表修改参数结构 {@link RecommendModifyRequest}
	 * @return 修改的种草信息表信息 {@link RecommendModifyResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommend/modify")
	BaseResponse<RecommendModifyResponse> modify(@RequestBody @Valid RecommendModifyRequest recommendModifyRequest);

	/**
	 * 单个删除种草信息表API
	 *
	 * @author 黄昭
	 * @param recommendDelByIdRequest 单个删除参数结构 {@link RecommendDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommend/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid RecommendDelByIdRequest recommendDelByIdRequest);

	/**
	 * 批量删除种草信息表API
	 *
	 * @author 黄昭
	 * @param recommendDelByIdListRequest 批量删除参数结构 {@link RecommendDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommend/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid RecommendDelByIdListRequest recommendDelByIdListRequest);

	/**
	 * 修改种草置顶
	 * @param request
	 * @return
	 */
	@PostMapping("/setting/${application.setting.version}/recommend/update/top")
    BaseResponse updateTop(@RequestBody @Valid RecommendByIdRequest request);

	/**
	 * 修改种草状态
	 * @param request
	 * @return
	 */
	@PostMapping("/setting/${application.setting.version}/recommend/update/status")
	BaseResponse updateStatus(@RequestBody @Valid RecommendByIdRequest request);

	/**
	 * 删除种草
	 * @param request
	 * @return
	 */
	@PostMapping("/setting/${application.setting.version}/recommend/update/del")
	BaseResponse delById(@RequestBody @Valid RecommendByIdRequest request);

	/**
	 * 种草统计数据同步mysql
	 *
	 * @author xufeng
	 * @param recommendByPageCodeRequest 种草统计数据同步mysql {@link RecommendByPageCodeRequest}
	 * @return 种草统计数据同步mysql {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommend/recommendSync")
	BaseResponse recommendSync(@RequestBody @Valid RecommendByPageCodeRequest recommendByPageCodeRequest);

}

