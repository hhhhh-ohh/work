package com.wanmi.sbc.marketing.communitystockorder.model.root;


import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>社区团购备货单实体类</p>
 * @author dyt
 * @date 2023-08-03 14:05:20
 */
@Data
@Entity
@Table(name = "community_stock_order")
public class CommunityStockOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "id")
	private String id;

	/**
	 * 活动id
	 */
	@Column(name = "activity_id")
	private String activityId;

	/**
	 * 商品id
	 */
	@Column(name = "sku_id")
	private String skuId;

	/**
	 * 商品名称
	 */
	@Column(name = "goods_name")
	private String goodsName;

	/**
	 * 规格
	 */
	@Column(name = "spec_name")
	private String specName;

	/**
	 * 购买数量
	 */
	@Column(name = "num")
	private Long num;

	/**
	 * 创建时间
	 */
	@CreatedDate
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;

	/**
	 * 商品图片
	 */
	@Column(name = "goods_img")
	private String goodsImg;
}
