package com.wanmi.sbc.vas.recommend.recommendsystemconfig.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;

import jakarta.persistence.*;

/**
 * <p>智能推荐配置实体类</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@Data
@Entity
@Table(name = "recommend_system_config")
public class RecommendSystemConfig extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 *  编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 键
	 */
	@Column(name = "config_key")
	private String configKey;

	/**
	 * 类型
	 */
	@Column(name = "config_type")
	private String configType;

	/**
	 * 名称
	 */
	@Column(name = "config_name")
	private String configName;

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
	 * 配置内容，如JSON内容
	 */
	@Column(name = "context")
	private String context;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}