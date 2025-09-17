package com.wanmi.sbc.goods.goodspropertydetailrel.model.root;

import com.wanmi.sbc.goods.bean.enums.GoodsPropertyEnterType;
import com.wanmi.sbc.goods.bean.enums.GoodsPropertyType;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;

import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;

/**
 * <p>商品与属性值关联实体类</p>
 * @author chenli
 * @date 2021-04-21 15:00:14
 */
@Data
@Entity
@Table(name = "goods_property_detail_rel")
public class GoodsPropertyDetailRel extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * detailRelId
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "detail_rel_id")
	private Long detailRelId;

	/**
	 * 商品id
	 */
	@Column(name = "goods_id")
	private String goodsId;

	/**
	 * 商品分类id
	 */
	@Column(name = "cate_id")
	private Long cateId;

	/**
	 * 属性id
	 */
	@Column(name = "prop_id")
	private Long propId;

	/**
	 * 属性值id，以逗号隔开
	 */
	@Column(name = "detail_id")
	private String detailId;

	/**
	 * 商品类型 0商品 1商品库
	 */
	@Column(name = "goods_type")
	private GoodsPropertyType goodsType;

	/**
	 * 属性值输入方式，0选项 1文本 2日期 3省市 4国家或地区
	 */
	@Column(name = "prop_type")
	private GoodsPropertyEnterType propType;

	/**
	 * 输入方式为文本的属性值
	 */
	@Column(name = "prop_value_text")
	private String propValueText;

	/**
	 * 输入方式为日期的属性值
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "prop_value_date")
	private LocalDateTime propValueDate;

	/**
	 * 输入方式为省市的属性值，省市id用逗号隔开
	 */
	@Column(name = "prop_value_province")
	private String propValueProvince;

	/**
	 * 输入方式为国家或地区的属性值，国家和地区用逗号隔开
	 */
	@Column(name = "prop_value_country")
	private String propValueCountry;

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
	@Column(name = "delete_time")
	private LocalDateTime deleteTime;

	/**
	 * 删除人
	 */
	@Column(name = "delete_person")
	private String deletePerson;

}
