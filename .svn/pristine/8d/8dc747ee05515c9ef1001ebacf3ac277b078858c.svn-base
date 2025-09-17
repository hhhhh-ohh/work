package com.wanmi.sbc.setting.api.provider.payadvertisement;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.setting.api.request.payadvertisement.*;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementAddResponse;
import com.wanmi.sbc.setting.api.response.payadvertisement.PayAdvertisementModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>支付广告页配置保存服务Provider</p>
 * @author 黄昭
 * @date 2022-04-06 10:03:54
 */
@FeignClient(value = "${application.setting.name}", contextId = "PayAdvertisementProvider")
public interface PayAdvertisementProvider {

	/**
	 * 新增支付广告页配置API
	 *
	 * @author 黄昭
	 * @param payAdvertisementAddRequest 支付广告页配置新增参数结构 {@link PayAdvertisementAddRequest}
	 * @return 新增的支付广告页配置信息 {@link PayAdvertisementAddResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/payadvertisement/add")
	BaseResponse add(@RequestBody @Valid PayAdvertisementAddRequest payAdvertisementAddRequest);

	/**
	 * 修改支付广告页配置API
	 *
	 * @author 黄昭
	 * @param payAdvertisementModifyRequest 支付广告页配置修改参数结构 {@link PayAdvertisementModifyRequest}
	 * @return 修改的支付广告页配置信息 {@link PayAdvertisementModifyResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/payadvertisement/modify")
	BaseResponse modify(@RequestBody @Valid PayAdvertisementModifyRequest payAdvertisementModifyRequest);

	/**
	 * 单个删除支付广告页配置API
	 *
	 * @author 黄昭
	 * @param payAdvertisementByIdRequest 单个删除参数结构 {@link PayAdvertisementByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/payadvertisement/delete-by-id")
	BaseResponse deleteById(@RequestBody @Valid PayAdvertisementByIdRequest payAdvertisementByIdRequest);

	/**
	 * 暂停支付广告页配置API
	 *
	 * @author 黄昭
	 * @param payAdvertisementDelByIdRequest 单个删除参数结构 {@link PayAdvertisementDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/payadvertisement/pause-by-id")
	BaseResponse pause(@RequestBody @Valid PayAdvertisementByIdRequest payAdvertisementDelByIdRequest);

	/**
	 * 开启支付广告页配置API
	 *
	 * @author 黄昭
	 * @param payAdvertisementDelByIdRequest 单个删除参数结构 {@link PayAdvertisementDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/payadvertisement/start-by-id")
	BaseResponse start(@RequestBody @Valid PayAdvertisementByIdRequest payAdvertisementDelByIdRequest);

	/**
	 * 关闭支付广告页配置API
	 *
	 * @author 黄昭
	 * @param payAdvertisementDelByIdRequest 单个删除参数结构 {@link PayAdvertisementDelByIdRequest}
	 * @return 删除结果 {@link BaseResponse}
	 */
	@PostMapping("/setting/${application.setting.version}/payadvertisement/close-by-id")
	BaseResponse close(@RequestBody @Valid PayAdvertisementByIdRequest payAdvertisementDelByIdRequest);

}

