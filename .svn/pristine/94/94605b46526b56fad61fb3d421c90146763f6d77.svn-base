package com.wanmi.sbc.goods.goodspropcaterel.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>商品类目与属性关联实体类</p>
 * @author chenli
 * @date 2021-04-21 14:58:28
 */
@Data
@Entity
@Table(name = "goods_prop_cate_rel")
public class GoodsPropCateRel extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 关联表主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rel_id")
	private Long relId;

	/**
	 * 属性id
	 */
	@Column(name = "prop_id")
	private Long propId;

	/**
	 * 商品分类id
	 */
	@Column(name = "cate_id")
	private Long cateId;

	/**
	 * 父类编号
	 */
	@Column(name = "parent_id")
	private Long parentId;

	/**
	 * 分类路径
	 */
	@Column(name = "cate_path")
	private String catePath;

	/**
	 * 分类层次
	 */
	@Column(name = "cate_grade")
	private Integer cateGrade;

	/**
	 * 排序
	 */
	@Column(name = "rel_sort")
	private Integer relSort;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 删除时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "delete_time")
	private LocalDateTime deleteTime;

	/**
	 * 删除人
	 */
	@Column(name = "delete_person")
	private String deletePerson;

}
