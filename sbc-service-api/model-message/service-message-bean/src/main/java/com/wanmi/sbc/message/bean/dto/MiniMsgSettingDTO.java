package com.wanmi.sbc.message.bean.dto;

import com.wanmi.sbc.common.enums.ProgramNodeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>小程序订阅消息配置表VO</p>
 * @author xufeng
 * @date 2022-08-08 11:37:13
 */
@Schema
@Data
public class MiniMsgSettingDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 授权节点id 0 订单支付成功 1 退单提交成功 2 查看我的优惠券 3 参与/发起拼团成功 4 商品预约成功 付费会员购买成功
	 */
	@Schema(description = "授权节点id 0 订单支付成功 1 退单提交成功 2 查看我的优惠券 3 参与/发起拼团成功 4 商品预约成功 付费会员购买成功")
	private ProgramNodeType nodeId;

	/**
	 * 授权节点名称
	 */
	@Schema(description = "授权节点名称")
	private String nodeName;

	/**
	 * 授权频次
	 */
	@Schema(description = "授权频次")
	private Integer num;

	/**
	 * 选择订阅消息
	 */
	@Schema(description = "选择订阅消息")
	private List<MiniMsgTemplateSettingSimpleDTO> data;

	/**
	 * 是否开启 0：否，1：是
	 */
	@Schema(description = "是否开启 0：否，1：是")
	private Integer status;

}