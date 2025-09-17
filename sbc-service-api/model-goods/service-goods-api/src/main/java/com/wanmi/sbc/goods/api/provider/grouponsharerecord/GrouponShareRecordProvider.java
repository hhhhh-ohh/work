package com.wanmi.sbc.goods.api.provider.grouponsharerecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordAddRequest;
import com.wanmi.sbc.goods.api.response.grouponsharerecord.GrouponShareRecordAddResponse;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordModifyRequest;
import com.wanmi.sbc.goods.api.response.grouponsharerecord.GrouponShareRecordModifyResponse;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordDelByIdRequest;
import com.wanmi.sbc.goods.api.request.grouponsharerecord.GrouponShareRecordDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>拼团分享访问记录保存服务Provider</p>
 * @author zhangwenchang
 * @date 2021-01-07 15:02:41
 */
@FeignClient(value = "${application.goods.name}", contextId = "GrouponShareRecordProvider")
public interface GrouponShareRecordProvider {

	/**
	 * 新增拼团分享访问记录API
	 *
	 * @author zhangwenchang
	 * @param grouponShareRecordAddRequest 拼团分享访问记录新增参数结构 {@link GrouponShareRecordAddRequest}
	 * @return 新增的拼团分享访问记录信息 {@link GrouponShareRecordAddResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/grouponsharerecord/add")
	BaseResponse<GrouponShareRecordAddResponse> add(@RequestBody @Valid GrouponShareRecordAddRequest grouponShareRecordAddRequest);

	/**
	 * 修改拼团分享访问记录API
	 *
	 * @author zhangwenchang
	 * @param grouponShareRecordModifyRequest 拼团分享访问记录修改参数结构 {@link GrouponShareRecordModifyRequest}
	 * @return 修改的拼团分享访问记录信息 {@link GrouponShareRecordModifyResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/grouponsharerecord/modify")
	BaseResponse<GrouponShareRecordModifyResponse> modify(@RequestBody @Valid GrouponShareRecordModifyRequest grouponShareRecordModifyRequest);

	/**
	 * 单个删除拼团分享访问记录API
	 *
	 * @author zhangwenchang
	 * @param grouponShareRecordDelByIdRequest 单个删除参数结构 {@link GrouponShareRecordDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/grouponsharerecord/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid GrouponShareRecordDelByIdRequest grouponShareRecordDelByIdRequest);

	/**
	 * 批量删除拼团分享访问记录API
	 *
	 * @author zhangwenchang
	 * @param grouponShareRecordDelByIdListRequest 批量删除参数结构 {@link GrouponShareRecordDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/goods/${application.goods.version}/grouponsharerecord/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid GrouponShareRecordDelByIdListRequest grouponShareRecordDelByIdListRequest);

}

