package com.wanmi.sbc.setting.helpcenterarticlecate.model.root;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>帮助中心文章信息实体类</p>
 * @author 吕振伟
 * @date 2023-03-16 09:44:52
 */
@Data
@Entity
@Table(name = "help_center_article_cate")
public class HelpCenterArticleCate extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 分类名称
	 */
	@Column(name = "cate_name")
	private String cateName;

	/**
	 * 分类排序
	 */
	@Column(name = "cate_sort")
	private Integer cateSort;

	/**
	 * 是否默认分类 0:否，1：是
	 */
	@Column(name = "default_cate")
	private DefaultFlag defaultCate;

	/**
	 * 删除标记  0：正常，1：删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
