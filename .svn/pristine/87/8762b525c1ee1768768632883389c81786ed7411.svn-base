package com.wanmi.sbc.empower.miniprogramset.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;

import jakarta.persistence.*;

/**
 * <p>小程序配置实体类</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@Data
@Entity
@Table(name = "mini_program_set")
public class MiniProgramSet extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 小程序配置主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 小程序类型 0 微信小程序
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 状态,0:未启用1:已启用
	 */
	@Column(name = "status")
	private Integer status;

	/**
	 * 小程序AppID(应用ID)
	 */
	@Column(name = "app_id")
	private String appId;

	/**
	 * 小程序AppSecret(应用密钥)
	 */
	@Column(name = "app_secret")
	private String appSecret;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
