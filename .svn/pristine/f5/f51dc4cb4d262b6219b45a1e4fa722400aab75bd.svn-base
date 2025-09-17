package com.wanmi.sbc.marketing.api.provider.drawactivity;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.drawactivity.*;
import com.wanmi.sbc.marketing.api.response.drawactivity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>抽奖活动表查询服务Provider</p>
 *
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@FeignClient(value = "${application.marketing.name}", contextId = "DrawActivityQueryProvider")
public interface DrawActivityQueryProvider {

    /**
     * 分页查询抽奖活动表API
     *
     * @param drawActivityPageReq 分页请求参数和筛选对象 {@link DrawActivityPageRequest}
     * @return 抽奖活动表分页列表信息 {@link DrawActivityPageResponse}
     * @author wwc
     */
    @PostMapping("/marketing/${application.marketing.version}/drawactivity/page")
    BaseResponse<DrawActivityPageResponse> page(@RequestBody @Valid DrawActivityPageRequest drawActivityPageReq);

    /**
     * 列表查询抽奖活动表API
     *
     * @param drawActivityListReq 列表请求参数和筛选对象 {@link DrawActivityListRequest}
     * @return 抽奖活动表的列表信息 {@link DrawActivityListResponse}
     * @author wwc
     */
    @PostMapping("/marketing/${application.marketing.version}/drawactivity/list")
    BaseResponse<DrawActivityListResponse> list(@RequestBody @Valid DrawActivityListRequest drawActivityListReq);

    /**
     * 单个查询抽奖活动表API
     *
     * @param drawActivityByIdRequest 单个查询抽奖活动表请求参数 {@link DrawActivityByIdRequest}
     * @return 抽奖活动表详情 {@link DrawActivityByIdResponse}
     * @author wwc
     */
    @PostMapping("/marketing/${application.marketing.version}/drawactivity/get-by-id")
    BaseResponse<DrawActivityByIdResponse> getById(@RequestBody @Valid DrawActivityByIdRequest drawActivityByIdRequest);

    /**
     * C端查看抽奖活动表详情
     *
     * @param detailByIdRequest C端查看抽奖活动表详情 {@link DrawDetailByIdRequest}
     * @return 抽奖活动表详情 {@link DrawActivityByIdResponse}
     * @author wwc
     */
    @PostMapping("/marketing/${application.marketing.version}/drawactivity/detail-for-web")
    BaseResponse<DrawDetailByIdResponse> detailForWeb(@RequestBody @Valid DrawDetailByIdRequest detailByIdRequest);

	/**
	 * 根据抽奖活动id查活动和活动详情
	 * @param drawActivityByIdRequest
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawactivity/detail-by-id")
    BaseResponse<DrawActivityGetDetailsByIdResponse> getDetailsById(
    		@RequestBody @Valid DrawActivityByIdRequest drawActivityByIdRequest);

    /**
     * 根据抽奖活动修改页面根据活动id回显
     * @param drawActivityForUpdateRequest
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/drawactivity/get-by-id-for-update")
    BaseResponse<DrawActivityForUpdateResponse> getByIdForUpdate(
            @RequestBody @Valid DrawActivityForUpdateRequest drawActivityForUpdateRequest);
}

