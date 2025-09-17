package com.wanmi.sbc.marketing.api.provider.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBatchAddResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBatchModifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * <p>礼品卡批次保存服务Provider</p>
 * @author 马连峰
 * @date 2022-12-08 20:38:29
 */
@FeignClient(value = "${application.marketing.name}", contextId = "GiftCardBatchProvider")
public interface GiftCardBatchProvider {

	/**
	 * 礼品卡批量制卡生成
	 *
	 * @author 马连峰
	 * @param request 礼品卡批量制卡参数 {@link GiftCardBatchMakeCreateRequest}
	 * @return 生成结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcardbatch/batch-make-create")
	BaseResponse batchMakeCreate(@RequestBody @Valid GiftCardBatchMakeCreateRequest request);

	/**
	 * 礼品卡批量发卡生成
	 *
	 * @author 马连峰
	 * @param request 礼品卡批量发卡参数 {@link GiftCardBatchSendCreateRequest}
	 * @return 生成结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcardbatch/batch-send-create")
	BaseResponse batchSendCreate(@RequestBody @Valid GiftCardBatchSendCreateRequest request);

	/**
	 * 礼品卡批次审核
	 *
	 * @author 马连峰
	 * @param request 礼品卡批次审核参数 {@link GiftCardBatchAuditRequest}
	 * @return 生成结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcardbatch/audit")
	BaseResponse batchAudit(@RequestBody @Valid GiftCardBatchAuditRequest request);

	/**
	 * 礼品卡批次审核前校验
	 *
	 * @author 马连峰
	 * @param request 礼品卡批次审核前校验参数 {@link GiftCardBatchCheckForAuditRequest}
	 * @return 生成结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcardbatch/check-for-audit")
	BaseResponse checkForAudit(@RequestBody @Valid GiftCardBatchCheckForAuditRequest request);

	/**
	 * 新增礼品卡批次API
	 *
	 * @author 马连峰
	 * @param giftCardBatchAddRequest 礼品卡批次新增参数结构 {@link GiftCardBatchAddRequest}
	 * @return 新增的礼品卡批次信息 {@link GiftCardBatchAddResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcardbatch/add")
	BaseResponse<GiftCardBatchAddResponse> add(@RequestBody @Valid GiftCardBatchAddRequest giftCardBatchAddRequest);

	/**
	 * 修改礼品卡批次API
	 *
	 * @author 马连峰
	 * @param giftCardBatchModifyRequest 礼品卡批次修改参数结构 {@link GiftCardBatchModifyRequest}
	 * @return 修改的礼品卡批次信息 {@link GiftCardBatchModifyResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcardbatch/modify")
	BaseResponse<GiftCardBatchModifyResponse> modify(@RequestBody @Valid GiftCardBatchModifyRequest giftCardBatchModifyRequest);

	/**
	 * 发提货卡给指定用户
	 *
	 * @author 刘方鑫
	 * @param request 发提货卡给指定用户参数 {@link GiftCardGainRequest}
	 * @return 发提货卡给指定用户发卡结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcardbatch/sendPickUpCardToAccount")
	BaseResponse<Boolean> sendPickUpCardToAccount(@RequestBody @Valid GiftCardGainRequest request);

	/**
	 * 旧校服发对应新校服提货卡给指定用户
	 *
	 * @author 刘方鑫
	 * @param oldSendNewSchoolUniformRequest 发提货卡给指定用户参数 {@link OldSendNewSchoolUniformRequest}
	 * @return 发提货卡给指定用户发卡结果 {@link BaseResponse}
	 */
	@PostMapping("/marketing/${application.marketing.version}/giftcardbatch/OldSendNewSchoolUniformToAccount")
	BaseResponse<Boolean> OldSendNewSchoolUniformToAccount(@RequestBody @Valid OldSendNewSchoolUniformRequest oldSendNewSchoolUniformRequest);
}

