package com.wanmi.sbc.vas.commodityscoringalgorithm.model.root;

import com.wanmi.sbc.common.base.BaseEntity;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * <p>商品评分算法实体类</p>
 * @author Bob
 * @date 2021-03-03 14:27:46
 */
@Data
@Entity
@Table(name = "commodity_scoring_algorithm")
public class CommodityScoringAlgorithm extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	/**
	 * 列名
	 */
	@Column(name = "key_type")
	private String keyType;

	/**
	 * 权值
	 */
	@Column(name = "weight")
	private BigDecimal weight;

	/**
	 * 标签ID
	 */
	@Column(name = "tag_id")
	private String tagId;

	/**
	 * 0: 否 1：是
	 */
	@Column(name = "is_selected")
	private DefaultFlag isSelected;

	/**
	 * 0:否 1:是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}