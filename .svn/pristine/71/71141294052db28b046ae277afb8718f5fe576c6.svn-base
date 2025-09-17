package com.wanmi.sbc.marketing.provider.impl.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.CacheKeyConstant;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardBatchProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBatchAddResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBatchModifyResponse;
import com.wanmi.sbc.marketing.bean.constant.MarketingCommonErrorCode;
import com.wanmi.sbc.marketing.bean.enums.ExpirationType;
import com.wanmi.sbc.marketing.bean.enums.GiftCardBatchType;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCard;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardBatch;
import com.wanmi.sbc.marketing.giftcard.service.GiftCardBatchService;
import com.wanmi.sbc.marketing.giftcard.service.UniformsGiftCardBindingService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>礼品卡批次保存服务接口实现</p>
 * @author 马连峰
 * @date 2022-12-08 20:38:29
 */
@RestController
@Validated
public class GiftCardBatchController implements GiftCardBatchProvider {

	private static final String AUTO_REJECT_REASON_FOR_EXPIRATION = "卡已过期，库存返还";

	@Autowired private RedissonClient redissonClient;

	@Autowired private GiftCardBatchService giftCardBatchService;

	@Autowired private UniformsGiftCardBindingService uniformsGiftCardBindingService;

	@Override
	public BaseResponse batchMakeCreate(@RequestBody @Valid GiftCardBatchMakeCreateRequest request) {
		// 锁礼品卡id
		String lockKey = CacheKeyConstant.MARKETING_GIFT_CARD_STOCK_LOCK.concat(request.getGiftCardId().toString());
		RLock rLock = redissonClient.getFairLock(lockKey);
		rLock.lock();
		try {
			// 执行制卡
			giftCardBatchService.batchMakeCreate(request);
		}
		finally {
			rLock.unlock();
		}
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse batchSendCreate(@RequestBody @Valid GiftCardBatchSendCreateRequest request) {
		// 锁礼品卡id
		String lockKey = CacheKeyConstant.MARKETING_GIFT_CARD_STOCK_LOCK.concat(request.getGiftCardId().toString());
		RLock rLock = redissonClient.getFairLock(lockKey);
		rLock.lock();
		try {
			// 执行发卡
			giftCardBatchService.batchSendCreate(request);
		}
		finally {
			rLock.unlock();
		}
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse batchAudit(@RequestBody @Valid GiftCardBatchAuditRequest request) {
		// 锁礼品卡批次id
		String batchLockKey = CacheKeyConstant.MARKETING_GIFT_CARD_BATCH_ID_LOCK.concat(request.getGiftCardBatchId().toString());
		RLock rLock = redissonClient.getFairLock(batchLockKey);
		rLock.lock();
		try {
			// 标识是否系统自动驳回（礼品卡已过期的场景）
			boolean autoReject = false;

			// 1. 校验批次
			GiftCardBatch giftCardBatch = giftCardBatchService.getOne(request.getGiftCardBatchId());
			// 1.1 是否存在
            if (Objects.isNull(giftCardBatch)) {
            	// 批次不存在
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080035);
			}
            // 1.3 校验批次审核状态
			if (AuditStatus.WAIT_CHECK != giftCardBatch.getAuditStatus()) {
				// 已经审核过了
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080036);
			}

			// 2. 校验礼品卡
			GiftCard giftCard = giftCardBatch.getGiftCard();
			// 2.1 是否存在
			if (Objects.isNull(giftCard)) {
				// 礼品卡不存在
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
			}
			// 2.2 审核操作，校验是否过期，过期需"自动驳回"
			if (AuditStatus.CHECKED == request.getAuditStatus()) {
				if (ExpirationType.SPECIFIC_TIME == giftCard.getExpirationType()
						&& LocalDateTime.now().isAfter(giftCard.getExpirationTime())) {
					autoReject = true;
					request.setAuditStatus(AuditStatus.NOT_PASS);
					request.setAuditReason(AUTO_REJECT_REASON_FOR_EXPIRATION);
				}
			}

			// 3. 审核制卡/发卡批次
			if (GiftCardBatchType.MAKE == giftCardBatch.getBatchType()) {
				// 3.1 制卡审核
				giftCardBatchService.batchMakeAudit(request, giftCardBatch, giftCard);
			} else {
				// 3.2 发卡审核
				giftCardBatchService.batchSendAudit(request, giftCardBatch, giftCard);
			}

			// 4. "自动驳回"时，应给前端一个标识，展示message，区分普通异常
			if (autoReject) {
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080037);
			}
		}
		finally {
			rLock.unlock();
		}
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse checkForAudit(@RequestBody @Valid GiftCardBatchCheckForAuditRequest request) {
		// 锁礼品卡批次id
		String batchLockKey = CacheKeyConstant.MARKETING_GIFT_CARD_BATCH_ID_LOCK.concat(request.getGiftCardBatchId().toString());
		RLock rLock = redissonClient.getFairLock(batchLockKey);
		rLock.lock();
		try {

			GiftCardBatchAuditRequest auditRequest = new GiftCardBatchAuditRequest();
			auditRequest.setUserId(request.getUserId());
			auditRequest.setGiftCardBatchId(request.getGiftCardBatchId());

			// 标识是否系统自动驳回（礼品卡已过期的场景）
			boolean autoReject = false;

			// 1. 校验批次
			GiftCardBatch giftCardBatch = giftCardBatchService.getOne(request.getGiftCardBatchId());
			// 1.1 是否存在
			if (Objects.isNull(giftCardBatch)) {
				// 批次不存在
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080035);
			}
			// 1.3 校验批次审核状态
			if (AuditStatus.WAIT_CHECK != giftCardBatch.getAuditStatus()) {
				// 已经审核过了
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080036);
			}

			// 2. 校验礼品卡
			GiftCard giftCard = giftCardBatch.getGiftCard();
			// 2.1 是否存在
			if (Objects.isNull(giftCard)) {
				// 礼品卡不存在
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080039);
			}
			// 2.2 校验是否过期，过期需"自动驳回"
			if (ExpirationType.SPECIFIC_TIME == giftCard.getExpirationType()
					&& LocalDateTime.now().isAfter(giftCard.getExpirationTime())) {
				autoReject = true;
				auditRequest.setBatchType(giftCardBatch.getBatchType());
				auditRequest.setAuditStatus(AuditStatus.NOT_PASS);
				auditRequest.setAuditReason(AUTO_REJECT_REASON_FOR_EXPIRATION);
			}

			// 3. "自动驳回"时，应给前端一个标识，展示message，区分普通异常
			if (autoReject) {
				if (GiftCardBatchType.MAKE == giftCardBatch.getBatchType()) {
					// 3.1 制卡审核
					giftCardBatchService.batchMakeAudit(auditRequest, giftCardBatch, giftCard);
				} else {
					// 3.2 发卡审核
					giftCardBatchService.batchSendAudit(auditRequest, giftCardBatch, giftCard);
				}
				throw new SbcRuntimeException(MarketingErrorCodeEnum.K080037);
			}
		}
		finally {
			rLock.unlock();
		}
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse<GiftCardBatchAddResponse> add(@RequestBody @Valid GiftCardBatchAddRequest giftCardBatchAddRequest) {
		GiftCardBatch giftCardBatch = KsBeanUtil.convert(giftCardBatchAddRequest, GiftCardBatch.class);
		return BaseResponse.success(new GiftCardBatchAddResponse(
				giftCardBatchService.wrapperVo(giftCardBatchService.add(giftCardBatch))));
	}

	@Override
	public BaseResponse<GiftCardBatchModifyResponse> modify(@RequestBody @Valid GiftCardBatchModifyRequest giftCardBatchModifyRequest) {
		GiftCardBatch giftCardBatch = KsBeanUtil.convert(giftCardBatchModifyRequest, GiftCardBatch.class);
		return BaseResponse.success(new GiftCardBatchModifyResponse(
				giftCardBatchService.wrapperVo(giftCardBatchService.modify(giftCardBatch))));
	}

	@Override
	public BaseResponse<Boolean> sendPickUpCardToAccount(@RequestBody @Valid GiftCardGainRequest request) {
		giftCardBatchService.sendPickUpCardToAccount(request);
		return BaseResponse.success(Boolean.TRUE);
	}

	@Override
	public BaseResponse<Boolean> OldSendNewSchoolUniformToAccount(@RequestBody @Valid OldSendNewSchoolUniformRequest oldSendNewSchoolUniformRequest) {
		List<OldSendNewSchoolUniformChildRequest> oldSendNewSchoolUniformChildRequestList = oldSendNewSchoolUniformRequest.getOldSendNewSchoolUniformChildRequestList();
		for (OldSendNewSchoolUniformChildRequest oldSendNewSchoolUniformChildRequest : oldSendNewSchoolUniformChildRequestList) {
			oldSendNewSchoolUniformRequest.setOrderDetailRetailId(oldSendNewSchoolUniformChildRequest.getOrderDetailRetailId());
			oldSendNewSchoolUniformRequest.setCityName(oldSendNewSchoolUniformChildRequest.getCityName());
			oldSendNewSchoolUniformRequest.setNumber(oldSendNewSchoolUniformChildRequest.getNumber());
			oldSendNewSchoolUniformRequest.setSeason(oldSendNewSchoolUniformChildRequest.getSeason());
			uniformsGiftCardBindingService.OldSendNewSchoolUniformToAccount(oldSendNewSchoolUniformRequest);
		}
		return BaseResponse.success(Boolean.TRUE);
	}
}

