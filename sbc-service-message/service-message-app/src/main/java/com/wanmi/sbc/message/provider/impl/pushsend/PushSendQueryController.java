package com.wanmi.sbc.message.provider.impl.pushsend;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.apppush.AppPushProvider;
import com.wanmi.sbc.empower.api.request.apppush.AppPushQueryRequest;
import com.wanmi.sbc.empower.api.response.apppush.AppPushQueryResponse;
import com.wanmi.sbc.empower.bean.enums.AppPushAppType;
import com.wanmi.sbc.empower.bean.enums.EmpowerErrorCodeEnum;
import com.wanmi.sbc.message.api.provider.pushsend.PushSendQueryProvider;
import com.wanmi.sbc.message.api.request.pushsend.PushSendByIdRequest;
import com.wanmi.sbc.message.api.request.pushsend.PushSendListRequest;
import com.wanmi.sbc.message.api.request.pushsend.PushSendPageRequest;
import com.wanmi.sbc.message.api.request.pushsend.PushSendQueryRequest;
import com.wanmi.sbc.message.api.response.pushsend.PushSendByIdResponse;
import com.wanmi.sbc.message.api.response.pushsend.PushSendListResponse;
import com.wanmi.sbc.message.api.response.pushsend.PushSendPageResponse;
import com.wanmi.sbc.message.bean.constant.PushErrorCode;
import com.wanmi.sbc.message.bean.enums.PushPlatform;
import com.wanmi.sbc.message.bean.enums.PushStatus;
import com.wanmi.sbc.message.bean.vo.PushSendVO;
import com.wanmi.sbc.message.pushdetail.model.root.PushDetail;
import com.wanmi.sbc.message.pushdetail.service.PushDetailService;
import com.wanmi.sbc.message.pushsend.model.root.PushSend;
import com.wanmi.sbc.message.pushsend.service.PushSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>会员推送信息查询服务接口实现</p>
 * @author Bob
 * @date 2020-01-08 17:15:32
 */
@RestController
@Validated
@Slf4j
public class PushSendQueryController implements PushSendQueryProvider {
	@Autowired
	private PushSendService pushSendService;
	@Autowired
	private AppPushProvider appPushProvider;
	@Autowired
	private PushDetailService pushDetailService;

	@Override
	public BaseResponse<PushSendPageResponse> page(@RequestBody @Valid PushSendPageRequest pushSendPageReq) {
		PushSendQueryRequest queryReq = KsBeanUtil.convert(pushSendPageReq, PushSendQueryRequest.class);
		Page<PushSend> pushSendPage = pushSendService.page(queryReq);
		Page<PushSendVO> newPage = pushSendPage.map(entity -> pushSendService.wrapperVo(entity));

		newPage.filter(pushSendVO -> pushSendVO.getPushTime() != null).
				filter(pushSendVO -> pushSendVO.getPushTime().plusDays(7).isAfter(LocalDateTime.now()))
				.forEach(this::detail);

		newPage.filter(pushSendVO -> pushSendVO.getPushTime() == null).
				filter(pushSendVO -> pushSendVO.getCreateTime().plusDays(7).isAfter(LocalDateTime.now()))
				.forEach(this::detail);

		MicroServicePage<PushSendVO> microPage = new MicroServicePage<>(newPage, pushSendPageReq.getPageable());
		PushSendPageResponse finalRes = new PushSendPageResponse(microPage);
		return BaseResponse.success(finalRes);
	}

	private void detail(PushSendVO pushSendVO){
		if (pushSendVO.getIosTaskId() != null){
			AppPushQueryRequest appPushQueryRequest =
					AppPushQueryRequest.builder().taskId(pushSendVO.getIosTaskId()).type(AppPushAppType.IOS).build();
			AppPushQueryResponse resultEntry = appPushProvider.query(appPushQueryRequest).getContext();
			if (Boolean.TRUE.equals(resultEntry.getSuccess())){
				PushDetail detail = new PushDetail();
				detail.setTaskId(pushSendVO.getIosTaskId());
				detail.setPlatform(PushPlatform.IOS);
				detail.setOpenSum(resultEntry.getOpenCount());
				if (Objects.nonNull(pushSendVO.getPushTime()) && LocalDateTime.now().isAfter(pushSendVO.getPushTime())){
					detail.setSendStatus(PushStatus.fromValue(resultEntry.getStatus()));
				} else if (Objects.isNull(pushSendVO.getPushTime())) {
					detail.setSendStatus(PushStatus.fromValue(resultEntry.getStatus()));
				}
				detail.setSendSum(resultEntry.getSentCount());
				detail.setCreateTime(LocalDateTime.now());
				detail.setPlanId(pushSendVO.getPlanId());
				pushDetailService.add(detail);
				pushSendVO.setIosPushDetail(pushDetailService.wrapperVo(detail));
			} else {
				log.error("PushSendQueryController.detail::友盟iOS查询接口失败");
				throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060020, new Object[]{"iOS查询"});
			}
		}

		if (pushSendVO.getAndroidTaskId() != null){
			AppPushQueryRequest appPushQueryRequest =
					AppPushQueryRequest.builder().taskId(pushSendVO.getAndroidTaskId()).type(AppPushAppType.ANDROID).build();
			AppPushQueryResponse resultEntry = appPushProvider.query(appPushQueryRequest).getContext();
			if (Boolean.TRUE.equals(resultEntry.getSuccess())){
				PushDetail detail = new PushDetail();
				detail.setTaskId(pushSendVO.getAndroidTaskId());
				detail.setPlatform(PushPlatform.ANDROID);
				detail.setOpenSum(resultEntry.getOpenCount());
				if (Objects.nonNull(pushSendVO.getPushTime()) && LocalDateTime.now().isAfter(pushSendVO.getPushTime())){
					detail.setSendStatus(PushStatus.fromValue(resultEntry.getStatus()));
				} else if (Objects.isNull(pushSendVO.getPushTime())) {
					detail.setSendStatus(PushStatus.fromValue(resultEntry.getStatus()));
				}
				detail.setSendSum(resultEntry.getSentCount());
				detail.setCreateTime(LocalDateTime.now());
				detail.setPlanId(pushSendVO.getPlanId());
				pushDetailService.add(detail);
				pushSendVO.setAndroidPushDetail(pushDetailService.wrapperVo(detail));
			} else {
				log.error("PushSendQueryController.detail::友盟android查询接口失败");
				throw new SbcRuntimeException(EmpowerErrorCodeEnum.K060020, new Object[]{"android查询"});
			}
		}
	}

	@Override
	public BaseResponse<PushSendListResponse> list(@RequestBody @Valid PushSendListRequest pushSendListReq) {
		PushSendQueryRequest queryReq = KsBeanUtil.convert(pushSendListReq, PushSendQueryRequest.class);
		List<PushSend> pushSendList = pushSendService.list(queryReq);
		List<PushSendVO> newList = pushSendList.stream().map(entity -> pushSendService.wrapperVo(entity)).collect(Collectors.toList());
		return BaseResponse.success(new PushSendListResponse(newList));
	}

	@Override
	public BaseResponse<PushSendByIdResponse> getById(@RequestBody @Valid PushSendByIdRequest pushSendByIdRequest) {
		PushSend pushSend =
		pushSendService.getOne(pushSendByIdRequest.getId());
		return BaseResponse.success(new PushSendByIdResponse(pushSendService.wrapperVo(pushSend)));
	}

}

