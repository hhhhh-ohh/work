package com.wanmi.sbc.setting.api.provider.payadvertisement;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementPageRequest;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementPageResponse;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementListRequest;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementListResponse;
import com.wanmi.sbc.setting.api.request.payadvertisement.PayAdvertisementByIdRequest;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementByIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>支付广告页配置查询服务Provider</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@FeignClient(value = "${application.setting.name}", contextId = "PayAdvertisementQueryProvider")
public interface PayAdvertisementQueryProvider {

	/**
	 * 分页查询支付广告页配置API
	 *
	 * @author 黄昭
	 * @param payAdvertisementPageReq 分页请求参数和筛选对象 {@link PayAdvertisementPageRequest}
	 * @return 支付广告页配置分页列表信息 {@link PayAdvertisementPageResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/payadvertisement/page")
	BaseResponse<PayAdvertisementPageResponse> page(@RequestBody @Valid PayAdvertisementPageRequest payAdvertisementPageReq);

	/**
	 * 单个查询支付广告页配置API
	 *
	 * @author 黄昭
	 * @param payAdvertisementByIdRequest 单个查询支付广告页配置请求参数 {@link PayAdvertisementByIdRequest}
	 * @return 支付广告页配置详情 {@link PayAdvertisementByIdResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/payadvertisement/get-by-id")
	BaseResponse<PayAdvertisementByIdResponse> getById(@RequestBody @Valid PayAdvertisementByIdRequest payAdvertisementByIdRequest);

	/**
	 * 列表查询支付广告页配置API
	 *
	 * @author 黄昭
	 * @param payAdvertisementListReq 列表请求参数和筛选对象 {@link PayAdvertisementListRequest}
	 * @return 支付广告页配置的列表信息 {@link PayAdvertisementListResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/payadvertisement/list")
	BaseResponse<PayAdvertisementListResponse> list(@RequestBody @Valid PayAdvertisementListRequest payAdvertisementListReq);
}

