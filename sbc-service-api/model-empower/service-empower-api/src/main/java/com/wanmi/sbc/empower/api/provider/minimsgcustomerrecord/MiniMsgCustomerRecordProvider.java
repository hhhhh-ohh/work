package com.wanmi.sbc.empower.api.provider.minimsgcustomerrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCustomerRecordAddRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordDelByIdListRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordDelByIdRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordModifyByActivityIdRequest;
import com.wanmi.sbc.empower.api.request.minimsgcustomerrecord.MiniMsgCusRecordModifyRequest;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordAddResponse;
import com.wanmi.sbc.empower.api.response.minimsgcustomerrecord.MiniMsgCustomerRecordModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>客户订阅消息信息表保存服务Provider</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
@FeignClient(value = "${application.empower.name}", contextId = "MiniMsgCustomerRecordProvider")
public interface MiniMsgCustomerRecordProvider {

	/**
	 * 新增客户订阅消息信息表API
	 *
	 * @author xufeng
	 * @param miniMsgCustomerRecordAddRequest 客户订阅消息信息表新增参数结构 {@link MiniMsgCustomerRecordAddRequest}
	 * @return 新增的客户订阅消息信息表信息 {@link MiniMsgCustomerRecordAddResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/minimsgcustomerrecord/add")
	BaseResponse<MiniMsgCustomerRecordAddResponse> add(@RequestBody @Valid MiniMsgCustomerRecordAddRequest miniMsgCustomerRecordAddRequest);

	/**
	 * 修改客户订阅消息信息表API
	 *
	 * @author xufeng
	 * @param miniMsgCusRecordModifyRequest 客户订阅消息信息表修改参数结构 {@link MiniMsgCusRecordModifyRequest}
	 * @return 修改的客户订阅消息信息表信息 {@link MiniMsgCustomerRecordModifyResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/minimsgcustomerrecord/modify")
	BaseResponse<MiniMsgCustomerRecordModifyResponse> modify(@RequestBody @Valid MiniMsgCusRecordModifyRequest miniMsgCusRecordModifyRequest);

	/**
	 * 单个删除客户订阅消息信息表API
	 *
	 * @author xufeng
	 * @param miniMsgCusRecordDelByIdRequest 单个删除参数结构 {@link MiniMsgCusRecordDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/minimsgcustomerrecord/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid MiniMsgCusRecordDelByIdRequest miniMsgCusRecordDelByIdRequest);

	/**
	 * 批量删除客户订阅消息信息表API
	 *
	 * @author xufeng
	 * @param miniMsgCusRecordDelByIdListRequest 批量删除参数结构 {@link MiniMsgCusRecordDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/empower/${application.empower.version}/minimsgcustomerrecord/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid MiniMsgCusRecordDelByIdListRequest miniMsgCusRecordDelByIdListRequest);

	/**
	 * 更新活动id到用户信息表中
	 *
	 * @author xufeng
	 * @return 更新活动id到用户信息表中 {@link Long}
	 */
	@PostMapping("/empower/${application.empower.version}/minimsgcustomerrecord/update-activityid")
	BaseResponse updateActivityIdByIdList(@RequestBody @Valid MiniMsgCusRecordModifyByActivityIdRequest miniMsgCusRecordModifyByActivityIdRequest);

	/**
	 * 更新活动id到用户信息表中
	 *
	 * @author xufeng
	 * @return 更新活动id到用户信息表中 {@link Long}
	 */
	@PostMapping("/empower/${application.empower.version}/minimsgcustomerrecord/update-by-activityid")
	BaseResponse updateByActivityId(@RequestBody @Valid MiniMsgCusRecordModifyByActivityIdRequest miniMsgCusRecordModifyByActivityIdRequest);

}

