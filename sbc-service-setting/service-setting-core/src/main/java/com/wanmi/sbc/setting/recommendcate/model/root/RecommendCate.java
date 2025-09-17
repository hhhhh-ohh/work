package com.wanmi.sbc.setting.recommendcate.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>笔记分类表实体类</p>
 * @author 王超
 * @date 2022-05-17 16:00:27
 */
@Data
@Entity
@Table(name = "recommend_cate")
public class RecommendCate extends BaseEntity {

	private static final long serialVersionUID = 9151440186023943630L;
	/**
	 * 分类id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cate_id")
	private Long cateId;

	/**
	 * 分类名称
	 */
	@Column(name = "cate_name")
	private String cateName;

	/**
	 * 排序
	 */
	@Column(name = "cate_sort")
	private Integer cateSort;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 删除时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "del_time")
	private LocalDateTime delTime;

	/**
	 * 删除人
	 */
	@Column(name = "del_person")
	private String delPerson;

}
