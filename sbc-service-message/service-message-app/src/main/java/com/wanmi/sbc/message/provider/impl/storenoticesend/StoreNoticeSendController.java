package com.wanmi.sbc.message.provider.impl.storenoticesend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.message.api.provider.storenoticesend.StoreNoticeSendProvider;
import com.wanmi.sbc.message.api.request.storenoticesend.*;
import com.wanmi.sbc.message.bean.enums.*;
import com.wanmi.sbc.message.storemessagedetail.service.StoreMessageDetailService;
import com.wanmi.sbc.message.storenoticescope.service.StoreNoticeScopeService;
import com.wanmi.sbc.message.storenoticesend.model.root.StoreNoticeSend;
import com.wanmi.sbc.message.storenoticesend.service.StoreNoticeSendService;
import com.wanmi.sbc.mq.api.provider.MqSendProvider;
import com.wanmi.sbc.mq.bean.constants.ProducerTopic;
import com.wanmi.sbc.mq.bean.dto.MqSendDelayDTO;

import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

/**
 * <p>商家公告保存服务接口实现</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@RestController
@Validated
@Slf4j
public class StoreNoticeSendController implements StoreNoticeSendProvider {

	@Autowired private StoreNoticeSendService storeNoticeSendService;

	@Autowired private StoreNoticeScopeService storeNoticeScopeService;

	@Autowired private StoreMessageDetailService storeMessageDetailService;

	@Autowired private MqSendProvider mqSendProvider;

	@Override
	@Transactional
	public BaseResponse add(@RequestBody @Valid StoreNoticeSendAddRequest addRequest) {

		// 1. 校验请求
		this.checkAddRequest(addRequest);

		// 2. 保存公告和发送范围
		StoreNoticeSend noticeSend = storeNoticeSendService.add(addRequest);

		// 3. 异步保存公告详情
		this.sendMqMessage(noticeSend, addRequest);

		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modify(@RequestBody @Valid StoreNoticeSendModifyRequest modifyRequest) {

		// 0. 查询公告详情
		StoreNoticeSend noticeSend = storeNoticeSendService.getOne(modifyRequest.getId());

		// 1. 校验请求
		this.checkModifyRequest(modifyRequest, noticeSend);

		// 2. 判断是否需要发MQ消息，仅当旧详情为已撤回/发送失败，需要再发mq消息
		StoreNoticeSendStatus oldStatus = noticeSend.getSendStatus();
		boolean needSendMq = oldStatus == StoreNoticeSendStatus.WITHDRAW || oldStatus == StoreNoticeSendStatus.SEND_FAIL;

		// 3. 保存公告和发送范围
		storeNoticeSendService.modify(noticeSend, modifyRequest);

		// 4. 仅当旧详情为已撤回/发送失败，需要再发mq消息
		if (needSendMq) {
			this.sendMqMessage(noticeSend, modifyRequest);
		}
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modifyStatusById(@Valid StoreNoticeSendModifyStatusRequest modifyStatusRequest) {
		storeNoticeSendService.modifySendStatusById(modifyStatusRequest.getId(), modifyStatusRequest.getSendStatus());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	public BaseResponse modifyScanFlag(@Valid StoreNoticeSendModifyScanFlagRequest modifyScanFlagRequest) {
        storeNoticeSendService.modifyScanFlag(modifyScanFlagRequest.getNoticeIds(), modifyScanFlagRequest.getScanFlag());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResponse deleteById(@RequestBody @Valid StoreNoticeSendDelByIdRequest storeNoticeSendDelByIdRequest) {
		// 1. 查询公告详情
		StoreNoticeSend noticeSend = storeNoticeSendService.getOne(storeNoticeSendDelByIdRequest.getId());
		StoreNoticeSendStatus status = noticeSend.getSendStatus();
		if (StoreNoticeSendStatus.SENT == status || StoreNoticeSendStatus.SENDING == status) {
			// 已发送或发送中状态不允许删除
			throw new SbcRuntimeException(MessageErrorCodeEnum.K090021, new Object[] {"已发送或发送中状态不允许删除，"});
		}
		// 2. 删除公告
		storeNoticeSendService.deleteById(noticeSend.getId());
		// 3. 删除公告发送范围
		storeNoticeScopeService.deleteByNoticeId(noticeSend.getId());
		// 4. 删除公告详情
		storeMessageDetailService.deleteByJoinId(noticeSend.getId());
		return BaseResponse.SUCCESSFUL();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResponse withdrawById(@RequestBody @Valid StoreNoticeSendDelByIdRequest storeNoticeSendDelByIdRequest) {
		// 1. 查询公告详情
		StoreNoticeSend noticeSend = storeNoticeSendService.getOne(storeNoticeSendDelByIdRequest.getId());
		if (StoreNoticeSendStatus.SENT != noticeSend.getSendStatus()) {
			// 非已发送不允许撤回
			throw new SbcRuntimeException(MessageErrorCodeEnum.K090021, new Object[] {"非已发送状态不允许撤回，"});
		}
		// 2. 删除公告详情
		storeMessageDetailService.deleteByJoinId(noticeSend.getId());
		// 3. 更改公告状态
		storeNoticeSendService.modifySendStatusById(noticeSend.getId(), StoreNoticeSendStatus.WITHDRAW);
		return BaseResponse.SUCCESSFUL();
	}

	/**
	 * 发消息异步保存公告详情
	 * @param noticeSend
	 */
	private void sendMqMessage(StoreNoticeSend noticeSend, StoreNoticeSendAddRequest sendAddRequest) {
		try {
			if (SendType.NOW == noticeSend.getSendTimeType()) {
				// 1.1 立即发送，直接发到消息队列
				MqSendDelayDTO mqSendDTO = new MqSendDelayDTO();
				mqSendDTO.setData(noticeSend.getId().toString());
				mqSendDTO.setTopic(ProducerTopic.STORE_NOTICE_SEND);
				mqSendDTO.setDelayTime(6000L);
				mqSendProvider.sendDelay(mqSendDTO);
				// 1.2 更改扫描标识为已扫描
				storeNoticeSendService.modifyScanFlag(Collections.singletonList(noticeSend.getId()), BoolFlag.YES);
			} else {
				// 2.1 定时发送，计算间隔时间
				Duration duration = Duration.between(LocalDateTime.now(), noticeSend.getSendTime());
				if (duration.toMinutes() < sendAddRequest.getWithinTime()) {
					// 若间隔小于定时扫表任务的withinTime（30分钟）时间，直接发到延迟队列
					MqSendDelayDTO mqSendDTO = new MqSendDelayDTO();
					mqSendDTO.setData(noticeSend.getId().toString());
					mqSendDTO.setTopic(ProducerTopic.STORE_NOTICE_SEND);
					mqSendDTO.setDelayTime(duration.isNegative() ? 6000L : duration.toMillis());
					mqSendProvider.sendDelay(mqSendDTO);
					// 2.2 更改扫描标识为已扫描
					storeNoticeSendService.modifyScanFlag(Collections.singletonList(noticeSend.getId()), BoolFlag.YES);
				}
			}
		} catch (Exception e) {
			// 发送MQ消息时产生异常，将公告置为发送失败
			storeNoticeSendService.modifySendStatusById(noticeSend.getId(), StoreNoticeSendStatus.SEND_FAIL);
		}
	}

	/**
	 * 校验添加请求
	 * @param addRequest
	 */
	private void checkAddRequest(StoreNoticeSendAddRequest addRequest) {
		// 1. 校验基本请求
		commonCheck(addRequest);
		// 2. 填充发送时间
		if (SendType.NOW == addRequest.getSendTimeType()) {
			// 立即发送，填充发送时间为当前
			addRequest.setSendTime(LocalDateTime.now());
		}
	}

	/**
	 * 校验编辑请求
	 * @param modifyRequest
	 * @param noticeSend
	 */
	private void checkModifyRequest(StoreNoticeSendModifyRequest modifyRequest, StoreNoticeSend noticeSend) {
		// 1. 校验基本请求
		commonCheck(modifyRequest);
		// 2. 判断是否存在
		if (Objects.isNull(noticeSend)) {
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		// 3. 已发送或发送中的公告，不能编辑
		StoreNoticeSendStatus sendStatus = noticeSend.getSendStatus();
		if (StoreNoticeSendStatus.SENT == sendStatus || StoreNoticeSendStatus.SENDING == sendStatus) {
			throw new SbcRuntimeException(MessageErrorCodeEnum.K090021, new Object[] {"已发送或发送中状态不允许编辑，"});
		}
		// 4. 定时发送的公告，未发送状态下
		if (SendType.DELAY == noticeSend.getSendTimeType() && StoreNoticeSendStatus.NOT_SENT == sendStatus) {
			// 不允许更改发送类型
			if (ObjectUtils.notEqual(noticeSend.getSendTimeType(), modifyRequest.getSendTimeType())) {
				throw new SbcRuntimeException(MessageErrorCodeEnum.K090020, new Object[] {"，不允许更改发送类型"});
			}
			// 不允许更改发送时间
			if (ObjectUtils.notEqual(noticeSend.getSendTime(), modifyRequest.getSendTime())) {
				throw new SbcRuntimeException(MessageErrorCodeEnum.K090020, new Object[] {"，不允许更改发送时间"});
			}
		}
		// 5. 填充发送时间
		if (SendType.NOW == modifyRequest.getSendTimeType()) {
			// 立即发送，填充发送时间为当前
			modifyRequest.setSendTime(LocalDateTime.now());
		}
	}

	/**
	 * 新增/编辑公共参数校验
	 * @param request
	 */
	private void commonCheck(StoreNoticeSendAddRequest request) {
		// 接收范围为商家时，清空供应商范围和目标ids
		if (request.getReceiveScope() == StoreNoticeReceiveScope.SUPPLIER) {
			request.setProviderScope(null);
			request.setProviderScopeIds(Collections.emptyList());
		}
		// 接收范围为供应商时，清空商家范围和目标ids
		if (request.getReceiveScope() == StoreNoticeReceiveScope.PROVIDER) {
			request.setSupplierScope(null);
			request.setSupplierScopeIds(Collections.emptyList());
		}
		// 接收范围为全部或商家，商家范围不能为空
		if (request.getReceiveScope() == StoreNoticeReceiveScope.ALL || request.getReceiveScope() == StoreNoticeReceiveScope.SUPPLIER) {
			if (Objects.isNull(request.getSupplierScope())) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
		// 接收范围为全部或供应商，供应商范围不能为空
		if (request.getReceiveScope() == StoreNoticeReceiveScope.ALL || request.getReceiveScope() == StoreNoticeReceiveScope.PROVIDER) {
			if (Objects.isNull(request.getProviderScope())) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
		// 商家范围不为空，且不是全部，需要指定目标范围ids
		if (Objects.nonNull(request.getSupplierScope()) && request.getSupplierScope() != StoreNoticeTargetScope.ALL) {
			if (CollectionUtils.isEmpty(request.getSupplierScopeIds())) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
		// 供应商范围不为空，且不是全部，需要指定目标范围ids
		if (Objects.nonNull(request.getProviderScope()) && request.getProviderScope() != StoreNoticeTargetScope.ALL) {
			if (CollectionUtils.isEmpty(request.getProviderScopeIds())) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
		// 定时发送，发送时间不能为空
		if (SendType.DELAY == request.getSendTimeType()) {
			if (Objects.isNull(request.getSendTime())) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
			// 不能早于当前时间
			if (request.getSendTime().isBefore(LocalDateTime.now())) {
				throw new SbcRuntimeException(MessageErrorCodeEnum.K090019, new Object[] {"不能早于当前时间"});
			}
			// 不能晚于当前时间1年
			if (request.getSendTime().isAfter(LocalDateTime.now().plusYears(Constants.ONE))) {
				throw new SbcRuntimeException(MessageErrorCodeEnum.K090019, new Object[] {"不能晚于当前时间1年"});
			}
		}
	}

}

