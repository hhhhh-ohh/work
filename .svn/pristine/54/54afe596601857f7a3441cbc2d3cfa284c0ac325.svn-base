package com.wanmi.sbc.message.provider.impl.minimsgrecord;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ProgramNodeType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.message.api.provider.minimsgrecord.MiniMsgRecordQueryProvider;
import com.wanmi.sbc.message.api.request.minimsgrecord.MiniMsgRecordByIdRequest;
import com.wanmi.sbc.message.api.response.minimsgrecord.MiniMsgByIdResponse;
import com.wanmi.sbc.message.api.response.minimsgrecord.MiniMsgRecordByIdResponse;
import com.wanmi.sbc.message.minimsgrecord.model.root.MiniMsgRecord;
import com.wanmi.sbc.message.minimsgrecord.service.MiniMsgRecordService;
import com.wanmi.sbc.message.minimsgsetting.model.root.MiniMsgSetting;
import com.wanmi.sbc.message.minimsgsetting.service.MiniMsgSettingService;
import com.wanmi.sbc.message.minimsgtempsetting.model.root.MiniMsgTempSetting;
import com.wanmi.sbc.message.minimsgtempsetting.service.MiniMsgTempSettingService;
import java.util.List;
import java.util.Objects;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>小程序订阅消息配置表查询服务接口实现</p>
 * @author xufeng
 * @date 2022-08-08 16:51:37
 */
@RestController
@Validated
public class MiniMsgRecordQueryController implements MiniMsgRecordQueryProvider {
	@Autowired
	private MiniMsgRecordService miniMsgRecordService;

	@Autowired
	private MiniMsgSettingService miniMsgSettingService;

	@Autowired
	private MiniMsgTempSettingService miniMsgTempSettingService;

	@Override
	public BaseResponse<MiniMsgRecordByIdResponse> getById(@RequestBody @Valid MiniMsgRecordByIdRequest miniMsgRecordByIdRequest) {
		MiniMsgRecord miniMsgRecord =
		miniMsgRecordService.getOne(miniMsgRecordByIdRequest.getId());
		return BaseResponse.success(new MiniMsgRecordByIdResponse(miniMsgRecordService.wrapperVo(miniMsgRecord)));
	}

	@Override
	public BaseResponse<MiniMsgByIdResponse> pullWxPage(@RequestBody @Valid MiniMsgRecordByIdRequest miniMsgRecordByIdRequest) {
		MiniMsgByIdResponse miniMsgByIdResponse =
				MiniMsgByIdResponse.builder().pullFlag(Boolean.FALSE).build();
		// 根据节点id获取相应配置
		ProgramNodeType nodeId = miniMsgRecordByIdRequest.getNodeId();
		MiniMsgSetting miniMsgSetting =
				miniMsgSettingService.findByNodeId(nodeId);
		// 配置不存在，配置是关闭状态，直接返回false
		if (Objects.isNull(miniMsgSetting)){
			return BaseResponse.success(miniMsgByIdResponse);
		}
		Integer status = miniMsgSetting.getStatus();
		Integer num = miniMsgSetting.getNum();
		// 配置不存在，配置是关闭状态，直接返回false
		if (Objects.isNull(status) || Constants.ZERO == status || Objects.isNull(num)){
			return BaseResponse.success(miniMsgByIdResponse);
		}
		if (num <= Constants.ONE) {
			dealTemplateIds(miniMsgByIdResponse, miniMsgSetting);
			return BaseResponse.success(miniMsgByIdResponse);
		}
		// 根据配置查看用户进入场景次数是否满足弹窗
		MiniMsgRecord miniMsgRecord =
				miniMsgRecordService.findByCustomerId(miniMsgRecordByIdRequest.getCustomerId());
    	int recordNum = Constants.ONE;
		// 为空则新增
		if (Objects.isNull(miniMsgRecord)){
			miniMsgRecord = new MiniMsgRecord();
			miniMsgRecord.setCustomerId(miniMsgRecordByIdRequest.getCustomerId());
			//累计次数
			switch (nodeId){
				case ORDER_PAY_SUCCESS:
					miniMsgRecord.setOrderPaySuccessNum(Constants.ONE);
					break;
				case REFUND_ORDER_SUCCESS:
					miniMsgRecord.setRefundOrderSuccessNum(Constants.ONE);
					break;
				case VIEW_COUPON:
					miniMsgRecord.setViewCouponNum(Constants.ONE);
					break;
				case GROUPON_SUCCESS:
					miniMsgRecord.setGrouponSuccessNum(Constants.ONE);
					break;
				case APPOINTMENT_SUCCESS:
					miniMsgRecord.setAppointmentSuccessNum(Constants.ONE);
					break;
				case MEMBER_PAY_SUCCESS:
					miniMsgRecord.setMemberPaySuccessNum(Constants.ONE);
					break;
				default:
					break;
			}
			miniMsgRecordService.add(miniMsgRecord);
			return BaseResponse.success(miniMsgByIdResponse);
		}else {
			Boolean returnBool = Boolean.FALSE;
			//累计次数
			switch (nodeId) {
				case ORDER_PAY_SUCCESS:
					if (Objects.isNull(miniMsgRecord.getOrderPaySuccessNum())) {
						miniMsgRecord.setOrderPaySuccessNum(Constants.ONE);
					} else {
						recordNum = miniMsgRecord.getOrderPaySuccessNum() + Constants.ONE;
						// 满足弹窗次数，返回true，同时将数据制为0
						if (recordNum >= num){
							recordNum = Constants.ZERO;
							returnBool = Boolean.TRUE;
						}
						miniMsgRecord.setOrderPaySuccessNum(recordNum);
					}
					break;
				case REFUND_ORDER_SUCCESS:
					if (Objects.isNull(miniMsgRecord.getRefundOrderSuccessNum())) {
						miniMsgRecord.setRefundOrderSuccessNum(Constants.ONE);
					} else {
						recordNum = miniMsgRecord.getRefundOrderSuccessNum() + Constants.ONE;
						// 满足弹窗次数，返回true，同时将数据制为0
						if (recordNum >= num){
							recordNum = Constants.ZERO;
							returnBool = Boolean.TRUE;
						}
						miniMsgRecord.setRefundOrderSuccessNum(recordNum);
					}
					break;
				case VIEW_COUPON:
					if (Objects.isNull(miniMsgRecord.getViewCouponNum())) {
						miniMsgRecord.setViewCouponNum(Constants.ONE);
					} else {
						recordNum = miniMsgRecord.getViewCouponNum() + Constants.ONE;
						// 满足弹窗次数，返回true，同时将数据制为0
						if (recordNum >= num){
							recordNum = Constants.ZERO;
							returnBool = Boolean.TRUE;
						}
						miniMsgRecord.setViewCouponNum(recordNum);
					}
					break;
				case GROUPON_SUCCESS:
					if (Objects.isNull(miniMsgRecord.getGrouponSuccessNum())) {
						miniMsgRecord.setGrouponSuccessNum(Constants.ONE);
					} else {
						recordNum = miniMsgRecord.getGrouponSuccessNum() + Constants.ONE;
						// 满足弹窗次数，返回true，同时将数据制为0
						if (recordNum >= num){
							recordNum = Constants.ZERO;
							returnBool = Boolean.TRUE;
						}
						miniMsgRecord.setGrouponSuccessNum(recordNum);
					}
					break;
				case APPOINTMENT_SUCCESS:
					if (Objects.isNull(miniMsgRecord.getAppointmentSuccessNum())) {
						miniMsgRecord.setAppointmentSuccessNum(Constants.ONE);
					} else {
						recordNum = miniMsgRecord.getAppointmentSuccessNum() + Constants.ONE;
						// 满足弹窗次数，返回true，同时将数据制为0
						if (recordNum >= num){
							recordNum = Constants.ZERO;
							returnBool = Boolean.TRUE;
						}
						miniMsgRecord.setAppointmentSuccessNum(recordNum);
					}
					break;
				case MEMBER_PAY_SUCCESS:
					if (Objects.isNull(miniMsgRecord.getMemberPaySuccessNum())) {
						miniMsgRecord.setMemberPaySuccessNum(Constants.ONE);
					} else {
						recordNum = miniMsgRecord.getMemberPaySuccessNum() + Constants.ONE;
						// 满足弹窗次数，返回true，同时将数据制为0
						if (recordNum >= num){
							recordNum = Constants.ZERO;
							returnBool = Boolean.TRUE;
						}
						miniMsgRecord.setMemberPaySuccessNum(recordNum);
					}
					break;
				default:
					break;
			}
			miniMsgRecordService.modify(miniMsgRecord);
			if (returnBool.equals(Boolean.TRUE)){
				dealTemplateIds(miniMsgByIdResponse, miniMsgSetting);
			}
			return BaseResponse.success(miniMsgByIdResponse);
		}
	}

	private void dealTemplateIds(MiniMsgByIdResponse miniMsgByIdResponse, MiniMsgSetting miniMsgSetting) {
		String message = miniMsgSetting.getMessage();
		List<MiniMsgTempSetting> miniMsgTempSettings = JSON.parseArray(message,
				MiniMsgTempSetting.class);
		List<String> templateIds = Lists.newArrayList();
		miniMsgTempSettings.forEach(miniProgramSubscribeTemplateSetting -> {
			if (Objects.nonNull(miniProgramSubscribeTemplateSetting)){
				MiniMsgTempSetting triggerNodeId =
						miniMsgTempSettingService.findByTriggerNodeId(miniProgramSubscribeTemplateSetting.getTriggerNodeId());
				templateIds.add(triggerNodeId.getTemplateId());
			}
		});

		miniMsgByIdResponse.setTemplateIds(templateIds);
		miniMsgByIdResponse.setPullFlag(Boolean.TRUE);
	}

}

