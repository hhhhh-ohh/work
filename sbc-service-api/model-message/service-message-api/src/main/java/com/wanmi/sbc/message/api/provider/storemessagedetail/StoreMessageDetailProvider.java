package com.wanmi.sbc.message.api.provider.storemessagedetail;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.StoreMessageMQRequest;
import com.wanmi.sbc.message.api.request.storemessagedetail.*;
import com.wanmi.sbc.message.api.response.storemessagedetail.StoreMessageDetailAddResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>商家消息/公告保存服务Provider</p>
 * @author 马连峰
 * @date 2022-07-05 10:52:24
 */
@FeignClient(value = "${application.message.name}", contextId = "StoreMessageDetailProvider")
public interface StoreMessageDetailProvider {

	/**
	 * 新增商家消息/公告API
	 *
	 * @author 马连峰
	 * @param storeMessageDetailAddRequest 商家消息/公告新增参数结构 {@link StoreMessageDetailAddRequest}
	 * @return 新增的商家消息/公告信息 {@link StoreMessageDetailAddResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storemessagedetail/add")
	BaseResponse<StoreMessageDetailAddResponse> add(@RequestBody @Valid StoreMessageDetailAddRequest storeMessageDetailAddRequest);

	/**
	 * 单个删除商家消息/公告API
	 *
	 * @author 马连峰
	 * @param storeMessageDetailDelByIdRequest 单个删除参数结构 {@link StoreMessageDetailDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storemessagedetail/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid StoreMessageDetailDelByIdRequest storeMessageDetailDelByIdRequest);


	/**
	 * 单个已读商家消息/公告API
	 *
	 * @author 马连峰
	 * @param storeMessageDetailReadByIdRequest 单个删除参数结构 {@link StoreMessageDetailReadByIdRequest}
	 * @return 已读结果 {@link BaseResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storemessagedetail/read-by-id")
	BaseResponse readById(@RequestBody @Valid StoreMessageDetailReadByIdRequest storeMessageDetailReadByIdRequest);

	/**
	 * 批量删除商家消息/公告API
	 *
	 * @author 马连峰
	 * @param storeMessageDetailDelByIdListRequest 批量删除参数结构 {@link StoreMessageDetailDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storemessagedetail/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid StoreMessageDetailDelByIdListRequest storeMessageDetailDelByIdListRequest);

	/**
	 * 批量已读商家消息/公告API
	 *
	 * @author 马连峰
	 * @param storeMessageDetailListRequest 批量删除参数结构 {@link StoreMessageDetailListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/message/${application.sms.version}/storemessagedetail/batchRead")
    BaseResponse batchRead(@RequestBody @Valid StoreMessageDetailListRequest storeMessageDetailListRequest);


}

