package com.wanmi.sbc.setting.helpcenterarticlerecord.model.root;

import com.wanmi.sbc.common.enums.DefaultFlag;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>帮助中心文章记录实体类</p>
 * @author 吕振伟
 * @date 2023-03-17 16:56:08
 */
@Data
@Entity
@Table(name = "help_center_article_record")
public class HelpCenterArticleRecord extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 文章id
	 */
	@Column(name = "article_id")
	private Long articleId;

	/**
	 * customerId
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * 解决状态  0：未解决，1：已解决
	 */
	@Column(name = "solve_type")
	@Enumerated
	private DefaultFlag solveType;

	/**
	 * 解决时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "solve_time")
	private LocalDateTime solveTime;

	/**
	 * 删除标记  0：正常，1：删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}
