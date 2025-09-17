package com.wanmi.sbc.message.api.request.minimsgtempsetting;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.TriggerNodeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>小程序订阅消息模版配置表分页查询请求参数</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgTempSettingPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<Long> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 模版ID
	 */
	@Schema(description = "模版ID")
	private String templateId;

	/**
	 * 模版名称
	 */
	@Schema(description = "模版名称")
	private String templateName;

	/**
	 * 类目ID
	 */
	@Schema(description = "类目ID")
	private String categoryId;

	/**
	 * 类目名称
	 */
	@Schema(description = "类目名称")
	private String categoryName;

	/**
	 * 关键词
	 */
	@Schema(description = "关键词")
	private String keyword;

	/**
	 * 触发节点标志 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时
	 */
	@Schema(description = "触发节点标志 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时")
	private TriggerNodeType triggerNodeId;

	/**
	 * 触发节点名称
	 */
	@Schema(description = "触发节点名称")
	private String triggerNodeName;

	/**
	 * 温馨提示
	 */
	@Schema(description = "温馨提示")
	private String tips;

	/**
	 * 温馨提示-提供修改
	 */
	@Schema(description = "温馨提示-提供修改")
	private String newTips;

	/**
	 * 要跳转的页面
	 */
	@Schema(description = "要跳转的页面")
	private String toPage;

	/**
	 * 模版标题ID
	 */
	@Schema(description = "模版标题ID")
	private String tid;

	/**
	 * 开发者自行组合好的模板关键词列表，关键词顺序可以自由搭配（例如 [3,5,4] 或 [4,5,3]），最多支持5个，最少2个关键词组合
	 */
	@Schema(description = "开发者自行组合好的模板关键词列表，关键词顺序可以自由搭配（例如 [3,5,4] 或 [4,5,3]），最多支持5个，最少2个关键词组合")
	private String kidList;

}