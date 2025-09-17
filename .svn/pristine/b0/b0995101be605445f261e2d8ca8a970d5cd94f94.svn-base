package com.wanmi.sbc.message.api.request.minimsgsetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ProgramNodeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * <p>单个查询小程序订阅消息配置表请求参数</p>
 * @author xufeng
 * @date 2022-08-08 11:37:13
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgSettingByNodeIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 授权节点id 0 订单支付成功 1 退单提交成功 2 查看我的优惠券 3 参与/发起拼团成功 4 商品预约成功 5 付费会员购买成功
	 */
	@Schema(description = "授权节点id 0 订单支付成功 1 退单提交成功 2 查看我的优惠券 3 参与/发起拼团成功 4 商品预约成功 5 付费会员购买成功")
	private ProgramNodeType nodeId;

}