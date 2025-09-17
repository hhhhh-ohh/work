package com.wanmi.sbc.setting.appexternallink.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>AppExternalLink实体类</p>
 * @author 黄昭
 * @date 2022-09-28 14:16:09
 */
@Data
@Entity
@Table(name = "app_external_link")
public class AppExternalLink extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 外部链接id
	 */
	@Column(name = "config_id")
	private Long configId;

	/**
	 * 页面名称
	 */
	@Column(name = "page_name")
	private String pageName;

	/**
	 * 页面链接
	 */
	@Column(name = "page_link")
	private String pageLink;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
