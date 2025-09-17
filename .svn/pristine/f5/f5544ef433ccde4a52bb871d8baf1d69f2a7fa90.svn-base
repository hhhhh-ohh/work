package com.wanmi.sbc.marketing.api.provider.newcomerpurchaseconfig;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseConfigAddRequest;
import com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig.NewcomerPurchaseConfigAddResponse;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseConfigModifyRequest;
import com.wanmi.sbc.marketing.api.response.newcomerpurchaseconfig.NewcomerPurchaseConfigModifyResponse;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseConfigDelByIdRequest;
import com.wanmi.sbc.marketing.api.request.newcomerpurchaseconfig.NewcomerPurchaseConfigDelByIdListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>新人专享设置保存服务Provider</p>
 * @author zhanghao
 * @date 2022-08-19 14:28:12
 */
@FeignClient(value = "${application.marketing.name}", contextId = "NewcomerPurchaseConfigProvider")
public interface NewcomerPurchaseConfigProvider {

	/**
	 * 新增新人专享设置API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseConfigAddRequest 新人专享设置新增参数结构 {@link NewcomerPurchaseConfigAddRequest}
	 * @return 新增的新人专享设置信息 {@link NewcomerPurchaseConfigAddResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchaseconfig/add")
	BaseResponse<NewcomerPurchaseConfigAddResponse> add(@RequestBody @Valid NewcomerPurchaseConfigAddRequest newcomerPurchaseConfigAddRequest);

	/**
	 * 修改新人专享设置API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseConfigModifyRequest 新人专享设置修改参数结构 {@link NewcomerPurchaseConfigModifyRequest}
	 * @return 修改的新人专享设置信息 {@link NewcomerPurchaseConfigModifyResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchaseconfig/modify")
	BaseResponse<NewcomerPurchaseConfigModifyResponse> modify(@RequestBody @Valid NewcomerPurchaseConfigModifyRequest newcomerPurchaseConfigModifyRequest);

	/**
	 * 单个删除新人专享设置API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseConfigDelByIdRequest 单个删除参数结构 {@link NewcomerPurchaseConfigDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchaseconfig/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid NewcomerPurchaseConfigDelByIdRequest newcomerPurchaseConfigDelByIdRequest);

	/**
	 * 批量删除新人专享设置API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseConfigDelByIdListRequest 批量删除参数结构 {@link NewcomerPurchaseConfigDelByIdListRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchaseconfig/delete-by-id-list")
	BaseResponse deleteByIdList(@RequestBody @Valid NewcomerPurchaseConfigDelByIdListRequest newcomerPurchaseConfigDelByIdListRequest);


	/**
	 * 修改新人专享设置API
	 *
	 * @author zhanghao
	 * @param newcomerPurchaseConfigModifyRequest 新人专享设置修改参数结构 {@link NewcomerPurchaseConfigModifyRequest}
	 * @return 修改的新人专享设置信息 {@link NewcomerPurchaseConfigModifyResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/newcomerpurchaseconfig/save")
	BaseResponse save(@RequestBody @Valid NewcomerPurchaseConfigModifyRequest newcomerPurchaseConfigModifyRequest);


}

