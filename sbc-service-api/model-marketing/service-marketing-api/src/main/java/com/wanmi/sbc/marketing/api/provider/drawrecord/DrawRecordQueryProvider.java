package com.wanmi.sbc.marketing.api.provider.drawrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordByIdRequest;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordListRequest;
import com.wanmi.sbc.marketing.api.request.drawrecord.DrawRecordPageRequest;
import com.wanmi.sbc.marketing.api.response.drawrecord.DrawRecordByIdResponse;
import com.wanmi.sbc.marketing.api.response.drawrecord.DrawRecordListResponse;
import com.wanmi.sbc.marketing.api.response.drawrecord.DrawRecordPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>抽奖记录表查询服务Provider</p>
 * @author wwc
 * @date 2021-04-12 16:15:21
 */
@FeignClient(value = "${application.marketing.name}", contextId = "DrawRecordQueryProvider")
public interface DrawRecordQueryProvider {

	/**
	 * 分页查询抽奖记录表API
	 *
	 * @author wwc
	 * @param drawRecordPageReq 分页请求参数和筛选对象 {@link DrawRecordPageRequest}
	 * @return 抽奖记录表分页列表信息 {@link DrawRecordPageResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawrecord/page")
	BaseResponse<DrawRecordPageResponse> page(@RequestBody @Valid DrawRecordPageRequest drawRecordPageReq);

	/**
	 * 列表查询抽奖记录表API
	 *
	 * @author wwc
	 * @param drawRecordListReq 列表请求参数和筛选对象 {@link DrawRecordListRequest}
	 * @return 抽奖记录表的列表信息 {@link DrawRecordListResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawrecord/list")
	BaseResponse<DrawRecordListResponse> list(@RequestBody @Valid DrawRecordListRequest drawRecordListReq);

	/**
	 * 单个查询抽奖记录表API
	 *
	 * @author wwc
	 * @param drawRecordByIdRequest 单个查询抽奖记录表请求参数 {@link DrawRecordByIdRequest}
	 * @return 抽奖记录表详情 {@link DrawRecordByIdResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawrecord/get-by-id")
	BaseResponse<DrawRecordByIdResponse> getById(@RequestBody @Valid DrawRecordByIdRequest drawRecordByIdRequest);

	/**
	 * 查询抽奖记录总条数
	 * @param request
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/drawrecord/total")
	BaseResponse<Long> total(DrawRecordPageRequest request);
}

