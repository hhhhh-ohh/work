package com.wanmi.sbc.goods.goodspropertydetail.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>商品属性值实体类</p>
 * @author chenli
 * @date 2021-04-21 14:57:33
 */
@Data
@Entity
@Table(name = "goods_property_detail")
public class GoodsPropertyDetail extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性值id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "detail_id")
	private Long detailId;

	/**
	 * 属性id外键
	 */
	@Column(name = "prop_id")
	private Long propId;

	/**
	 * 拼音
	 */
	@Column(name = "detail_pin_yin")
	private String detailPinYin;

	/**
	 * 属性值
	 */
	@Column(name = "detail_name")
	private String detailName;

	/**
	 * 排序
	 */
	@Column(name = "detail_sort")
	private Integer detailSort;

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
