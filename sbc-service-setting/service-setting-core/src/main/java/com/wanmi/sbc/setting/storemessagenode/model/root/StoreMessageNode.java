package com.wanmi.sbc.setting.storemessagenode.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;

import com.wanmi.sbc.common.enums.storemessage.StoreMessagePlatform;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>商家消息节点实体类</p>
 * @author 马连峰
 * @date 2022-07-11 09:41:21
 */
@Data
@Entity
@Table(name = "store_message_node")
public class StoreMessageNode extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 所属菜单名称
	 */
	@Column(name = "menu_name")
	private String menuName;

	/**
	 * 节点类型名称
	 */
	@Column(name = "type_name")
	private String typeName;

	/**
	 * 推送节点名称
	 */
	@Column(name = "push_name")
	private String pushName;

	/**
	 * 功能标识，用于鉴权
	 */
	@Column(name = "function_name")
	private String functionName;

	/**
	 * 节点标识
	 */
	@Column(name = "node_code")
	private String nodeCode;

	/**
	 * 节点通知内容模板
	 */
	@Column(name = "node_context")
	private String nodeContext;

	/**
	 * 平台类型 0:平台 1:商家 2:供应商
	 */
	@Column(name = "platform_type")
	@Enumerated
	private StoreMessagePlatform platformType;

	/**
	 * 删除标志 0:未删除 1:删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 排序字段
	 */
	@Schema(description = "排序字段")
	private Integer sort;

}
