package com.wanmi.sbc.goods.grouponsharerecord.model.root;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.ShareChannel;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import lombok.Data;
import jakarta.persistence.*;
import com.wanmi.sbc.common.base.BaseEntity;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDateTime;

/**
 * <p>拼团分享访问记录实体类</p>
 * @author zhangwenchang
 * @date 2021-01-07 15:02:41
 */
@Data
@Entity
@Table(name = "groupon_share_record")
public class GrouponShareRecord{
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 拼团活动ID
	 */
	@Column(name = "groupon_activity_id")
	private String grouponActivityId;

	/**
	 * 会员Id
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * SPU id
	 */
	@Column(name = "goods_id")
	private String goodsId;

	/**
	 * SKU id
	 */
	@Column(name = "goods_info_id")
	private String goodsInfoId;

	/**
	 * 店铺ID
	 */
	@Column(name = "store_id")
	private Long storeId;

	/**
	 * 公司信息ID
	 */
	@Column(name = "company_info_id")
	private Long companyInfoId;

	/**
	 * 终端：1 H5，2pc，3APP，4小程序
	 */
	@Column(name = "terminal_source")
	private TerminalSource terminalSource;

	/**
	 * 分享渠道：0微信，1朋友圈，2QQ，3QQ空间，4微博，5复制链接，6保存图片
	 */
	@Column(name = "share_channel")
	private ShareChannel shareChannel;

	/**
	 * 分享人，通过分享链接访问的时候
	 */
	@Column(name = "share_customer_id")
	private String shareCustomerId;

	/**
	 * 0分享拼团，1通过分享链接访问拼团
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 创建时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;
}