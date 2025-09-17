package com.wanmi.sbc.empower.api.provider.minimsgcustomerrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordByActivityIdRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCustomerRecordByIdRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCustomerRecordListRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordPageRequest;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordByActivityIdResponse;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordByIdResponse;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordListResponse;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>客户订阅消息信息表查询服务Provider</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
@FeignClient(value = "${application.empower.name}", contextId = "MiniMsgCustomerRecordQueryProvider")
public interface MiniMsgCustomerRecordQueryProvider {

	/**
	 * 分页查询客户订阅消息信息表API
	 *
	 * @author xufeng
	 * @param minimsgcusrecordPageReq 分页请求参数和筛选对象 {@link MiniMsgCusRecordPageRequest}
	 * @return 客户订阅消息信息表分页列表信息 {@link MiniMsgCustomerRecordPageResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/minimsgcusrecord/page")
	BaseResponse<MiniMsgCustomerRecordPageResponse> page(@RequestBody @Valid MiniMsgCusRecordPageRequest minimsgcusrecordPageReq);

	/**
	 * 列表查询客户订阅消息信息表API
	 *
	 * @author xufeng
	 * @param minimsgcusrecordListReq 列表请求参数和筛选对象 {@link MiniMsgCustomerRecordListRequest}
	 * @return 客户订阅消息信息表的列表信息 {@link MiniMsgCustomerRecordListResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/minimsgcusrecord/list")
	BaseResponse<MiniMsgCustomerRecordListResponse> list(@RequestBody @Valid MiniMsgCustomerRecordListRequest minimsgcusrecordListReq);

	/**
	 * 单个查询客户订阅消息信息表API
	 *
	 * @author xufeng
	 * @param miniMsgCustomerRecordByIdRequest 单个查询客户订阅消息信息表请求参数 {@link MiniMsgCustomerRecordByIdRequest}
	 * @return 客户订阅消息信息表详情 {@link MiniMsgCustomerRecordByIdResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/minimsgcusrecord/get-by-id")
	BaseResponse<MiniMsgCustomerRecordByIdResponse> getById(@RequestBody @Valid MiniMsgCustomerRecordByIdRequest miniMsgCustomerRecordByIdRequest);

	/**
	 * 查询可推送人数
	 *
	 * @author xufeng
	 * @return 查询可推送人数 {@link Long}
	 */
	@PostMapping("/empower/${application.empower.version}/minimsgcusrecord/get-record-count")
	BaseResponse<Long> countRecordsByTriggerNodeId();

	/**
	 * 查询可实际推送人数
	 *
	 * @author xufeng
	 * @param messageCustomerRecordByActivityIdRequest 查询可实际推送人数 {@link MiniMsgCusRecordByActivityIdRequest}
	 * @return 查询可实际推送人数 {@link Long}
	 */
	@PostMapping("/empower/${application.empower.version}/minimsgcusrecord/get-real-record-count")
	BaseResponse<MiniMsgCustomerRecordByActivityIdResponse> countRecordsByActivityId(@RequestBody @Valid MiniMsgCusRecordByActivityIdRequest messageCustomerRecordByActivityIdRequest);


}

