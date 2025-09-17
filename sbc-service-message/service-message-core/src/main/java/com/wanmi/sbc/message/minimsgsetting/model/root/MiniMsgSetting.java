package com.wanmi.sbc.message.minimsgsetting.model.root;


import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.ProgramNodeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * <p>小程序订阅消息配置表实体类</p>
 * @author xufeng
 * @date 2022-08-08 11:37:13
 */
@Data
@Entity
@Table(name = "mini_program_subscribe_message_setting")
public class MiniMsgSetting extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 授权节点id 0 订单支付成功 1 退单提交成功 2 查看我的优惠券 3 参与/发起拼团成功 4 商品预约成功 5 付费会员购买成功
	 */
	@Column(name = "node_id")
	private ProgramNodeType nodeId;

	/**
	 * 授权节点名称
	 */
	@Column(name = "node_name")
	private String nodeName;

	/**
	 * 授权频次
	 */
	@Column(name = "num")
	private Integer num;

	/**
	 * 选择订阅消息
	 */
	@Column(name = "message")
	private String message;

	/**
	 * 是否开启 0：否，1：是
	 */
	@Column(name = "status")
	private Integer status;

}
