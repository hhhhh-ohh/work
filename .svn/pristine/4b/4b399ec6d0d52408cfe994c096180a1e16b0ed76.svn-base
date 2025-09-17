package com.wanmi.sbc.marketing.api.provider.drawprize;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeByIdRequest;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizeListRequest;
import com.wanmi.sbc.marketing.api.request.drawprize.DrawPrizePageRequest;
import com.wanmi.sbc.marketing.api.response.drawprize.DrawPrizeByIdResponse;
import com.wanmi.sbc.marketing.api.response.drawprize.DrawPrizeListResponse;
import com.wanmi.sbc.marketing.api.response.drawprize.DrawPrizePageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>抽奖活动奖品表查询服务Provider</p>
 * @author wwc
 * @date 2021-04-12 16:54:59
 */
@FeignClient(value = "${application.marketing.name}", contextId = "DrawPrizeQueryProvider")
public interface DrawPrizeQueryProvider {

	/**
	 * 分页查询抽奖活动奖品表API
	 *
	 * @author wwc
	 * @param drawPrizePageReq 分页请求参数和筛选对象 {@link DrawPrizePageRequest}
	 * @return 抽奖活动奖品表分页列表信息 {@link DrawPrizePageResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawprize/page")
	BaseResponse<DrawPrizePageResponse> page(@RequestBody @Valid DrawPrizePageRequest drawPrizePageReq);

	/**
	 * 列表查询抽奖活动奖品表API
	 *
	 * @author wwc
	 * @param drawPrizeListReq 列表请求参数和筛选对象 {@link DrawPrizeListRequest}
	 * @return 抽奖活动奖品表的列表信息 {@link DrawPrizeListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawprize/list")
	BaseResponse<DrawPrizeListResponse> list(@RequestBody @Valid DrawPrizeListRequest drawPrizeListReq);

	/**
	 * 单个查询抽奖活动奖品表API
	 *
	 * @author wwc
	 * @param drawPrizeByIdRequest 单个查询抽奖活动奖品表请求参数 {@link DrawPrizeByIdRequest}
	 * @return 抽奖活动奖品表详情 {@link DrawPrizeByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawprize/get-by-id")
	BaseResponse<DrawPrizeByIdResponse> getById(@RequestBody @Valid DrawPrizeByIdRequest drawPrizeByIdRequest);

}

