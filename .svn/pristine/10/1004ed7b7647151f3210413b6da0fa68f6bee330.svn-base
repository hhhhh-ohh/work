package com.wanmi.sbc.marketing.api.provider.drawactivity;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.drawactivity.*;
import com.wanmi.sbc.marketing.api.response.drawactivity.DrawActivityAddResponse;
import com.wanmi.sbc.marketing.api.response.drawactivity.DrawActivityModifyResponse;
import com.wanmi.sbc.marketing.api.response.drawactivity.DrawResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>抽奖活动表保存服务Provider</p>
 * @author wwc
 * @date 2021-04-12 10:31:05
 */
@FeignClient(value = "${application.marketing.name}", contextId = "DrawActivitySaveProvider")
public interface DrawActivitySaveProvider {

	/**
	 * 新增抽奖活动表API
	 *
	 * @author wwc
	 * @param drawActivityAddRequest 抽奖活动表新增参数结构 {@link DrawActivityAddRequest}
	 * @return 新增的抽奖活动表信息 {@link DrawActivityAddResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawactivity/add")
	BaseResponse<DrawActivityAddResponse> add(@RequestBody @Valid DrawActivityAddRequest drawActivityAddRequest);

	/**
	 * 修改抽奖活动表API
	 *
	 * @author wwc
	 * @param drawActivityModifyRequest 抽奖活动表修改参数结构 {@link DrawActivityModifyRequest}
	 * @return 修改的抽奖活动表信息 {@link DrawActivityModifyResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawactivity/modify")
	BaseResponse<DrawActivityModifyResponse> modify(@RequestBody @Valid DrawActivityModifyRequest drawActivityModifyRequest);

	/**
	 * 单个删除抽奖活动表API
	 *
	 * @author wwc
	 * @param drawActivityDelByIdRequest 单个删除参数结构 {@link DrawActivityDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawactivity/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid DrawActivityDelByIdRequest drawActivityDelByIdRequest);

	/**
	 * 批量删除抽奖活动表API
	 *
	 * @author wwc
	 * @param drawActivityDelByIdListRequest 批量删除参数结构 {@link DrawActivityDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawactivity/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid DrawActivityDelByIdListRequest drawActivityDelByIdListRequest);

	  /**
     * 暂停抽奖活动
     * @param pauseByIdRequest 抽奖活动ID {@link DrawActivityPauseByIdRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/drawactivity/pause-by-id")
    BaseResponse pauseById(@RequestBody @Valid DrawActivityPauseByIdRequest pauseByIdRequest);

    /**
     * 启动抽奖活动
     * @param startByIdRequest 抽奖活动ID {@link DrawActivityStartByIdRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/drawactivity/start-by-id")
    BaseResponse startById(@RequestBody @Valid DrawActivityStartByIdRequest startByIdRequest);

	/**
	 * C端点击抽奖获取奖品
	 *
	 * @author wwc
	 * @param lotteryByIdRequest C端点击抽奖获取奖品 {@link LotteryByIdRequest}
	 * @return 奖品编号 {@link Integer}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawactivity/lottery-draw")
	BaseResponse<DrawResultResponse> lotteryDraw(@RequestBody @Valid LotteryByIdRequest lotteryByIdRequest);

	/**
	 * 添加抽奖次数
	 * @param request
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawactivity/add-draw-count")
	BaseResponse<DrawResultResponse> addDrawCount(@RequestBody @Valid DrawActivityPauseByIdRequest request);

	/**
	 * 添加中奖次数
	 * @param request
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawactivity/add-award-count")
	BaseResponse<DrawResultResponse> addAwardCount(@RequestBody @Valid DrawActivityPauseByIdRequest request);

	/**
	 * 关闭抽奖活动
	 * @param drawActivityByIdRequest
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawactivity/close-by-id")
	BaseResponse closeById(@RequestBody @Valid DrawActivityByIdRequest drawActivityByIdRequest);
}

