package com.wanmi.sbc.setting.thirdexpresscompany.root;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 第三方代销平台物流公司
 * @author malianfeng
 * @date 2022/4/26 17:22
 */
@Data
@Entity
@Schema
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "third_express_company")
public class ThirdExpressCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID,自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id	;

	/**
	 * 物流公司名称
	 */
	@Column(name = "express_name")
	private String expressName;

	/**
	 * 物流公司代码
	 */
	@Column(name = "express_code")
	private String expressCode;

	/**
	 * 第三方代销平台(0:微信视频号)
	 */
	@Column(name = "sell_platform_type")
	@Enumerated
	private SellPlatformType sellPlatformType;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 删除标记  0未删除  1已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;
}