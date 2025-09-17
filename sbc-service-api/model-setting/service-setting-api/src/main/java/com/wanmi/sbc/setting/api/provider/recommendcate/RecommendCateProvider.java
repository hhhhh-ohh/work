package com.wanmi.sbc.setting.api.provider.recommendcate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.recommendcate.*;
import com.wanmi.sbc.setting.api.response.recommendcate.RecommendCateAddResponse;
import com.wanmi.sbc.setting.api.response.recommendcate.RecommendCateModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>笔记分类表保存服务Provider</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@FeignClient(value = "${application.setting.name}", contextId = "RecommendCateProvider")
public interface RecommendCateProvider {

	/**
	 * 新增笔记分类表API
	 *
	 * @author 王超
	 * @param recommendCateAddRequest 笔记分类表新增参数结构 {@link RecommendCateAddRequest}
	 * @return 新增的笔记分类表信息 {@link RecommendCateAddResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommendcate/add")
	BaseResponse<RecommendCateAddResponse> add(@RequestBody @Valid RecommendCateAddRequest recommendCateAddRequest);

	/**
	 * 修改笔记分类表API
	 *
	 * @author 王超
	 * @param recommendCateModifyRequest 笔记分类表修改参数结构 {@link RecommendCateModifyRequest}
	 * @return 修改的笔记分类表信息 {@link RecommendCateModifyResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommendcate/modify")
	BaseResponse<RecommendCateModifyResponse> modify(@RequestBody @Valid RecommendCateModifyRequest recommendCateModifyRequest);

	/**
	 * 单个删除笔记分类表API
	 *
	 * @author 王超
	 * @param recommendCateDelByIdRequest 单个删除参数结构 {@link RecommendCateDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommendcate/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid RecommendCateDelByIdRequest recommendCateDelByIdRequest);

	/**
	 * 批量删除笔记分类表API
	 *
	 * @author 王超
	 * @param recommendCateDelByIdListRequest 批量删除参数结构 {@link RecommendCateDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommendcate/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid RecommendCateDelByIdListRequest recommendCateDelByIdListRequest);

	/**
	 * 拖拽排序笔记分类信息表API
	 *
	 * @author 王超
	 * @param recommendCateSortRequest 拖拽排序笔记分类信息结构 {@link RecommendCateSortRequest}
	 * @return 排序结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/recommendcate/drag-sort")
	BaseResponse dragSort(@RequestBody @Valid RecommendCateSortRequest recommendCateSortRequest);


}

