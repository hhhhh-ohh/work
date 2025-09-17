package com.wanmi.sbc.customer.goodsfootmark.model.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

/**
 * <p>我的足迹实体类</p>
 * @author 
 * @date 2022-05-30 07:30:41
 */
@Data
@Entity
@Table(name = "goods_footmark")
public class GoodsFootmark implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * footmarkId
	 */
	@Id
	@Column(name = "footmark_id")
	private Long footmarkId;

	/**
	 * customerId
	 */
	@Column(name = "customer_id")
	private String customerId;

	/**
	 * goodsInfoId
	 */
	@Column(name = "goods_info_id")
	private String goodsInfoId;


	/**
	 * 修改时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Column(name = "update_time")
	private LocalDateTime updateTime;


	/**
	 * 创建时间
	 */
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	@Column(name = "create_time")
	private LocalDateTime createTime;


	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

	/**
	 * 浏览次数
	 */
	@Column(name = "view_times")
	private Long viewTimes;

}