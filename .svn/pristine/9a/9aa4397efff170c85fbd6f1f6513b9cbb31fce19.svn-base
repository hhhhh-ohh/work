package com.wanmi.sbc.empower.ledgercontent.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>拉卡拉经营内容表实体类</p>
 * @author zhanghao
 * @date 2022-07-08 11:02:05
 */
@Data
@Entity
@Table(name = "ledger_content")
public class LedgerContent extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 经营内容id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "content_id")
	private Long contentId;

	/**
	 * 经营内容名称
	 */
	@Column(name = "content_name")
	private String contentName;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
