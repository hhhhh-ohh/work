package com.wanmi.sbc.goods.wechatvideosku.model.root;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.EditStatus;
import com.wanmi.sbc.goods.bean.enums.WechatShelveStatus;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>微信视频号带货商品实体类</p>
 * @author 
 * @date 2022-04-15 11:23:50
 */
@Data
@Entity
@Table(name = "wechat_sku")
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(name = "goodsInfo",attributeNodes = {@NamedAttributeNode("goodsInfo")})
public class WechatSku implements Serializable {
	private static final long serialVersionUID = 1L;

	public WechatSku(Long id, String wechatSkuId, Long productId, String goodsId, String goodsInfoId, EditStatus editStatus, WechatShelveStatus wechatShelveStatus, String rejectReason, String img, String downReason, LocalDateTime createTime, LocalDateTime updateTime, DeleteFlag delFlag, String createPerson, String updatePerson) {
		this.id = id;
		this.wechatSkuId = wechatSkuId;
		this.productId = productId;
		this.goodsId = goodsId;
		this.goodsInfoId = goodsInfoId;
		this.editStatus = editStatus;
		this.wechatShelveStatus = wechatShelveStatus;
		this.rejectReason = rejectReason;
		this.img = img;
		this.downReason = downReason;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.delFlag = delFlag;
		this.createPerson = createPerson;
		this.updatePerson = updatePerson;
	}

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 微信端sku_id
	 */
	@Column(name = "wechat_sku_id")
	private String wechatSkuId;

	/**
	 * 微信端商品id
	 */
	@Column(name = "product_id")
	private Long productId;

	@OneToOne
	@JoinColumn(name = "goods_info_id", insertable = false, updatable = false)
	private GoodsInfo goodsInfo;

	/**
	 * goodsId
	 */
	@Column(name = "goods_id")
	private String goodsId;
	/**
	 * goodsId
	 */
	@Column(name = "goods_info_id")
	private String goodsInfoId;

	/**
	 * 审核状态
	 */
	@Column(name = "edit_status")
	@Convert(converter = EditStatusAttributeConverter.class)
	private EditStatus editStatus;

	/**
	 * 0初始值5上架11自主下架13违规下架
	 */
	@Column(name = "wechat_shelve_status")
	@Convert(converter = WechatShelveStatusAttributeConverter.class)
	private WechatShelveStatus wechatShelveStatus;

	/**
	 * 审核不通过原因
	 */
	@Column(name = "reject_reason")
	private String rejectReason;

	/**
	 * 微信端商品图片
	 */
	@Column(name = "img")
	private String img;


	/**
	 * 微信端下架原因
	 */
	@Column(name = "down_reason")
	private String downReason;

	/**
	 * createTime
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;

	/**
	 * updateTime
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@Column(name = "update_time")
	private LocalDateTime updateTime;

	/**
	 * 是否删除，0，否，1是
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * createPerson
	 */
	@Column(name = "create_person")
	private String createPerson;

	/**
	 * updatePerson
	 */
	@Column(name = "update_person")
	private String updatePerson;

}