package com.wanmi.sbc.setting.expresscompanythirdrel.root;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SellPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.expresscompany.model.root.ExpressCompany;
import com.wanmi.sbc.setting.thirdexpresscompany.root.ThirdExpressCompany;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description 平台和第三方代销平台物流公司映射实体
 * @author malianfeng
 * @date 2022/4/26 17:05
 */
@Data
@Entity
@Schema
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "express_company_third_rel")
public class ExpressCompanyThirdRel implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID,自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * 平台物流公司主键
	 */
	@Column(name = "express_company_id")
	private Long expressCompanyId;

	/**
	 * 第三方平台物流公司主键
	 */
	@Column(name = "third_express_company_id")
	private Long thirdExpressCompanyId;

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
	 * 更新时间
	 */
	@Column(name = "update_time")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除标记  0未删除  1已删除
	 */
	@Column(name = "del_flag")
	@Enumerated
	private DeleteFlag delFlag;

}