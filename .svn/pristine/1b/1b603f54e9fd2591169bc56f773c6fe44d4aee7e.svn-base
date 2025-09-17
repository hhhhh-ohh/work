package com.wanmi.sbc.goods.api.provider.grouponsharerecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordPageRequest;
import com.wanmi.sbc.goods.api.response.grouponsharerecord.GrouponShareRecordPageResponse;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordListRequest;
import com.wanmi.sbc.goods.api.response.grouponsharerecord.GrouponShareRecordListResponse;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordByIdRequest;
import com.wanmi.sbc.goods.api.response.grouponsharerecord.GrouponShareRecordByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>拼团分享访问记录查询服务Provider</p>
 * @author zhangwenchang
 * @date 2021-01-07 15:02:41
 */
@FeignClient(value = "${application.goods.name}", contextId = "GrouponShareRecordQueryProvider")
public interface GrouponShareRecordQueryProvider {

	/**
	 * 分页查询拼团分享访问记录API
	 *
	 * @author zhangwenchang
	 * @param grouponShareRecordPageReq 分页请求参数和筛选对象 {@link GrouponShareRecordPageRequest}
	 * @return 拼团分享访问记录分页列表信息 {@link GrouponShareRecordPageResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/grouponsharerecord/page")
	BaseResponse<GrouponShareRecordPageResponse> page(@RequestBody @Valid GrouponShareRecordPageRequest grouponShareRecordPageReq);

	/**
	 * 列表查询拼团分享访问记录API
	 *
	 * @author zhangwenchang
	 * @param grouponShareRecordListReq 列表请求参数和筛选对象 {@link GrouponShareRecordListRequest}
	 * @return 拼团分享访问记录的列表信息 {@link GrouponShareRecordListResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/grouponsharerecord/list")
	BaseResponse<GrouponShareRecordListResponse> list(@RequestBody @Valid GrouponShareRecordListRequest grouponShareRecordListReq);

	/**
	 * 单个查询拼团分享访问记录API
	 *
	 * @author zhangwenchang
	 * @param grouponShareRecordByIdRequest 单个查询拼团分享访问记录请求参数 {@link GrouponShareRecordByIdRequest}
	 * @return 拼团分享访问记录详情 {@link GrouponShareRecordByIdResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/grouponsharerecord/get-by-id")
	BaseResponse<GrouponShareRecordByIdResponse> getById(@RequestBody @Valid GrouponShareRecordByIdRequest grouponShareRecordByIdRequest);

}

