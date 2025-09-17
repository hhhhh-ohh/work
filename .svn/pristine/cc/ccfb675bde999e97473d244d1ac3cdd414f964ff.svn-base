package com.wanmi.sbc.goods.goodsproperty.model.root;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>商品属性实体类</p>
 * @author chenli
 * @date 2021-04-21 14:56:01
 */
@Data
@Entity
@Table(name = "goods_property")
public class GoodsProperty extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 属性id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prop_id")
	private Long propId;

	/**
	 * 属性名称
	 */
	@Column(name = "prop_name")
	private String propName;

	/**
	 * 拼音
	 */
	@Column(name = "prop_pin_yin")
	private String propPinYin;

	/**
	 * 简拼
	 */
	@Column(name = "prop_short_pin_yin")
	private String propShortPinYin;

	/**
	 * 商品发布时属性是否必填
	 */
	@Column(name = "prop_required")
	private DefaultFlag propRequired;

	/**
	 * 是否行业特性，设置为行业特性的属性，会在前端商品详情规格上方作为行业特性参数露出，如药品参数、工业品参数等
	 */
	@Column(name = "prop_character")
	private DefaultFlag propCharacter;

	/**
	 * 属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区
	 */
	@Column(name = "prop_type")
	@Enumerated
	private GoodsPropertyEnterType propType;

	/**
	 * 是否开启索引 0否 1是
	 */
	@Column(name = "index_flag")
	@Enumerated
	private DefaultFlag indexFlag;

	/**
	 * 排序
	 */
	@Column(name = "prop_sort")
	private Long propSort;

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
