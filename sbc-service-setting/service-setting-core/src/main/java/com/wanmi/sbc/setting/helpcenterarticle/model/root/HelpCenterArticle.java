package com.wanmi.sbc.setting.helpcenterarticle.model.root;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>帮助中心文章信息实体类</p>
 * @author 吕振伟
 * @date 2023-03-15 10:15:47
 */
@Data
@Entity
@Table(name = "help_center_article")
public class HelpCenterArticle extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 文章标题
	 */
	@Column(name = "article_title")
	private String articleTitle;

	/**
	 * 文章分类id
	 */
	@Column(name = "article_cate_id")
	private Long articleCateId;

	/**
	 * 文章内容
	 */
	@Column(name = "article_content")
	private String articleContent;

	/**
	 * 文章状态，0:展示，1:隐藏
	 */
	@Column(name = "article_type")
	@Enumerated
	private DefaultFlag articleType;

	/**
	 * 查看次数
	 */
	@Column(name = "view_num")
	private Long viewNum;

	/**
	 * 解决次数
	 */
	@Column(name = "solve_num")
	private Long solveNum;

	/**
	 * 未解决次数
	 */
	@Column(name = "unresolved_num")
	private Long unresolvedNum;

	/**
	 * 删除标记  0：正常，1：删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
