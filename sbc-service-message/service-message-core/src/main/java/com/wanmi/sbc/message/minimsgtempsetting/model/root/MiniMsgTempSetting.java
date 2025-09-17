package com.wanmi.sbc.message.minimsgtempsetting.model.root;


import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.TriggerNodeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * <p>小程序订阅消息模版配置表实体类</p>
 * @author xufeng
 * @date 2022-08-12 11:19:52
 */
@Data
@Entity
@Table(name = "mini_program_subscribe_template_setting")
public class MiniMsgTempSetting extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 模版ID
	 */
	@Column(name = "template_id")
	private String templateId;

	/**
	 * 模版名称
	 */
	@Column(name = "template_name")
	private String templateName;

	/**
	 * 类目ID
	 */
	@Column(name = "category_id")
	private String categoryId;

	/**
	 * 类目名称
	 */
	@Column(name = "category_name")
	private String categoryName;

	/**
	 * 关键词
	 */
	@Column(name = "keyword")
	private String keyword;

	/**
	 * 触发节点标志 0：商家发货 1：自动确认收货前24小时 2：售后申请商家审核通过或失败后 3：商家/供应商发送退货地址后 4：退款回调通知成功 5：自动发券至用户账户时 6：优惠券过期前24小时 7：距离拼团结束还剩3小时，且未成团 8：拼团成功 9：拼团失败 10：尾款开始支付 11：距离尾款结束支付还有3小时 12：付费会员距离过期前24小时
	 */
	@Column(name = "trigger_node_id")
	private TriggerNodeType triggerNodeId;

	/**
	 * 触发节点名称
	 */
	@Column(name = "trigger_node_name")
	private String triggerNodeName;

	/**
	 * 温馨提示
	 */
	@Column(name = "tips")
	private String tips;

	/**
	 * 温馨提示-提供修改
	 */
	@Column(name = "new_tips")
	private String newTips;

	/**
	 * 要跳转的页面
	 */
	@Column(name = "to_page")
	private String toPage;

	/**
	 * 要跳转的页面名称
	 */
	@Column(name = "to_page_name")
	private String toPageName;

	/**
	 * 模版标题ID
	 */
	@Column(name = "tid")
	private String tid;

	/**
	 * 开发者自行组合好的模板关键词列表，关键词顺序可以自由搭配（例如 [3,5,4] 或 [4,5,3]），最多支持5个，最少2个关键词组合
	 */
	@Column(name = "kid_list")
	private String kidList;

}
