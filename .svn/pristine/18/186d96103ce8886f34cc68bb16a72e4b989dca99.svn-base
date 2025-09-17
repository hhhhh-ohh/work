package com.wanmi.sbc.setting.storemessagenodesetting.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;
import jakarta.persistence.*;

/**
 * <p>商家消息节点设置实体类</p>
 * @author 马连峰
 * @date 2022-07-11 09:42:56
 */
@Data
@Entity
@Table(name = "store_message_node_setting")
public class StoreMessageNodeSetting extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 商家id
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 消息节点id
	 */
	@Column(name = "node_id")
	private Long nodeId;

	/**
	 * 消息节点标识
	 */
	@Column(name = "node_code")
	private String nodeCode;

	/**
	 * 启用状态 0:未启用 1:启用
	 */
	@Column(name = "status")
	private BoolFlag status;

	/**
	 * 删除标志 0:未删除 1:删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 商家库存预警值
	 */
	@Column(name = "warning_stock")
	private Long warningStock;

}
