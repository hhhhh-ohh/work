package com.wanmi.sbc.setting.appexternalconfig.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;

import com.wanmi.sbc.setting.appexternallink.model.root.AppExternalLink;
import lombok.Data;
import com.wanmi.sbc.common.base.BaseEntity;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import java.util.List;

/**
 * <p>AppExternalConfig实体类</p>
 * @author 黄昭
 * @date 2022-09-27 15:26:05
 */
@Data
@Entity
@Table(name = "app_external_config")
public class AppExternalConfig extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 小程序名称
	 */
	@Column(name = "app_name")
	private String appName;

	/**
	 * 小程序appId
	 */
	@Column(name = "app_id")
	private String appId;

	/**
	 * 原始Id
	 */
	@Column(name = "original_id")
	private String originalId;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@OneToMany
	@JoinColumn(name = "config_id", insertable = false, updatable = false)
	@Where(clause = "del_flag = 0")
	@org.hibernate.annotations.OrderBy(clause = "create_time DESC")
	private List<AppExternalLink> appExternalLinks;
}
