package com.wanmi.sbc.message.provider.impl.minimsgrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.ProgramNodeType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.message.api.provider.minimsgrecord.MiniMsgRecordProvider;
import com.wanmi.sbc.message.api.request.minimsgrecord.MiniMsgRecordModifyRequest;
import com.wanmi.sbc.message.minimsgrecord.model.root.MiniMsgRecord;
import com.wanmi.sbc.message.minimsgrecord.service.MiniMsgRecordService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * <p>小程序订阅消息配置表保存服务接口实现</p>
 * @author xufeng
 * @date 2022-08-08 16:51:37
 */
@RestController
@Validated
public class MiniMsgRecordController implements MiniMsgRecordProvider {
	@Autowired
	private MiniMsgRecordService miniMsgRecordService;

	@Override
	@Transactional
	public BaseResponse modify(@RequestBody @Valid MiniMsgRecordModifyRequest miniMsgRecordModifyRequest) {
		ProgramNodeType nodeId = miniMsgRecordModifyRequest.getNodeId();
		MiniMsgRecord miniMsgRecord =
				miniMsgRecordService.findByCustomerId(miniMsgRecordModifyRequest.getCustomerId());
		// 为空则新增
		if (Objects.isNull(miniMsgRecord)){
			miniMsgRecord = new MiniMsgRecord();
			miniMsgRecord.setCustomerId(miniMsgRecordModifyRequest.getCustomerId());
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
		}else {
			//累计次数
			switch (nodeId) {
				case ORDER_PAY_SUCCESS:
					if (Objects.isNull(miniMsgRecord.getOrderPaySuccessNum())) {
						miniMsgRecord.setOrderPaySuccessNum(Constants.ONE);
					} else {
						miniMsgRecord.setOrderPaySuccessNum(miniMsgRecord.getOrderPaySuccessNum() + Constants.ONE);
					}
					break;
				case REFUND_ORDER_SUCCESS:
					if (Objects.isNull(miniMsgRecord.getRefundOrderSuccessNum())) {
						miniMsgRecord.setRefundOrderSuccessNum(Constants.ONE);
					} else {
						miniMsgRecord.setRefundOrderSuccessNum(miniMsgRecord.getRefundOrderSuccessNum() + Constants.ONE);
					}
					break;
				case VIEW_COUPON:
					if (Objects.isNull(miniMsgRecord.getViewCouponNum())) {
						miniMsgRecord.setViewCouponNum(Constants.ONE);
					} else {
						miniMsgRecord.setViewCouponNum(miniMsgRecord.getViewCouponNum() + Constants.ONE);
					}
					break;
				case GROUPON_SUCCESS:
					if (Objects.isNull(miniMsgRecord.getGrouponSuccessNum())) {
						miniMsgRecord.setGrouponSuccessNum(Constants.ONE);
					} else {
						miniMsgRecord.setGrouponSuccessNum(miniMsgRecord.getGrouponSuccessNum() + Constants.ONE);
					}
					break;
				case APPOINTMENT_SUCCESS:
					if (Objects.isNull(miniMsgRecord.getAppointmentSuccessNum())) {
						miniMsgRecord.setAppointmentSuccessNum(Constants.ONE);
					} else {
						miniMsgRecord.setAppointmentSuccessNum(miniMsgRecord.getAppointmentSuccessNum() + Constants.ONE);
					}
					break;
				case MEMBER_PAY_SUCCESS:
					if (Objects.isNull(miniMsgRecord.getMemberPaySuccessNum())) {
						miniMsgRecord.setMemberPaySuccessNum(Constants.ONE);
					} else {
						miniMsgRecord.setMemberPaySuccessNum(miniMsgRecord.getMemberPaySuccessNum() + Constants.ONE);
					}
					break;
				default:
					break;
			}
			miniMsgRecordService.modify(miniMsgRecord);
		}
		return BaseResponse.SUCCESSFUL();
	}

}

