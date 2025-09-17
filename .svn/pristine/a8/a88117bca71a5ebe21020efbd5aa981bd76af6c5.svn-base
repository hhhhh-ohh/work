package com.wanmi.sbc.marketing.api.provider.bookingsale;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.bookingsale.*;
import com.wanmi.sbc.marketing.api.response.bookingsale.BookingSaleAddResponse;
import com.wanmi.sbc.marketing.api.response.bookingsale.BookingSaleModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>预售信息保存服务Provider</p>
 * @author dany
 * @date 2020-06-05 10:47:21
 */
@FeignClient(value = "${application.marketing.name}", contextId = "BookingSaleProvider")
public interface BookingSaleProvider {

	/**
	 * 新增预售信息API
	 *
	 * @author dany
	 * @param bookingSaleAddRequest 预售信息新增参数结构 {@link BookingSaleAddRequest}
	 * @return 新增的预售信息信息 {@link BookingSaleAddResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/bookingsale/add")
	BaseResponse<BookingSaleAddResponse> add(@RequestBody @Valid BookingSaleAddRequest bookingSaleAddRequest);

	/**
	 * 修改预售信息API
	 *
	 * @author dany
	 * @param bookingSaleModifyRequest 预售信息修改参数结构 {@link BookingSaleModifyRequest}
	 * @return 修改的预售信息信息 {@link BookingSaleModifyResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/bookingsale/modify")
	BaseResponse<BookingSaleModifyResponse> modify(@RequestBody @Valid BookingSaleModifyRequest bookingSaleModifyRequest);

	/**
	 * 单个删除预售信息API
	 *
	 * @author dany
	 * @param bookingSaleDelByIdRequest 单个删除参数结构 {@link BookingSaleDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/bookingsale/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid BookingSaleDelByIdRequest bookingSaleDelByIdRequest);

	/**
	 * 批量删除预售信息API
	 *
	 * @author dany
	 * @param bookingSaleDelByIdListRequest 批量删除参数结构 {@link BookingSaleDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/bookingsale/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid BookingSaleDelByIdListRequest bookingSaleDelByIdListRequest);


	/**
	 * 暂停/开启活动
	 * @param request
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/bookingsale/modify-status")
    BaseResponse modifyStatus(@RequestBody @Valid BookingSaleStatusRequest request);

	/**
	 * 关闭活动
	 *
	 * @author xuyunpeng
	 * @param request 预售关闭参数结构 {@link BookingSaleCloseRequest}
	 * @return
	 */
	@PostMapping("/marketing/${application.marketing.version}/bookingsale/close")
	BaseResponse close(@RequestBody @Valid BookingSaleCloseRequest request);


	@PostMapping("/marketing/${application.marketing.version}/bookingsale/syc-cache")
	BaseResponse sycCache();
}

