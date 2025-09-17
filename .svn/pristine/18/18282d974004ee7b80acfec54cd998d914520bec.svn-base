package com.wanmi.sbc.setting.bean.dto;

import com.wanmi.sbc.common.enums.SellPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * @description 平台和第三方代销平台物流公司映射详情DTO
 * @author malianfeng
 * @date 2022/4/26 20:35
 */
@Data
public class ExpressCompanyThirdRelDetailDTO implements Serializable {

	private static final long serialVersionUID = 4326855945437375997L;

	public ExpressCompanyThirdRelDetailDTO() {

	}

	public ExpressCompanyThirdRelDetailDTO(Long expressCompanyId, String expressCompanyName, String expressCompanyCode,
										   Long thirdExpressCompanyId, String thirdExpressCompanyName, String thirdExpressCompanyCode,
										   SellPlatformType sellPlatformType) {
		this.expressCompanyId = expressCompanyId;
		this.expressCompanyName = expressCompanyName;
		this.expressCompanyCode = expressCompanyCode;
		this.thirdExpressCompanyId = thirdExpressCompanyId;
		this.thirdExpressCompanyName = thirdExpressCompanyName;
		this.thirdExpressCompanyCode = thirdExpressCompanyCode;
		this.sellPlatformType = sellPlatformType;
	}

	public ExpressCompanyThirdRelDetailDTO(Long expressCompanyId, Long thirdExpressCompanyId, String thirdExpressCompanyName, String thirdExpressCompanyCode,
										   SellPlatformType sellPlatformType) {
		this.expressCompanyId = expressCompanyId;
		this.thirdExpressCompanyId = thirdExpressCompanyId;
		this.thirdExpressCompanyName = thirdExpressCompanyName;
		this.thirdExpressCompanyCode = thirdExpressCompanyCode;
		this.sellPlatformType = sellPlatformType;
	}

	public ExpressCompanyThirdRelDetailDTO(Long expressCompanyId, String expressCompanyName, String expressCompanyCode) {
		this.expressCompanyId = expressCompanyId;
		this.expressCompanyName = expressCompanyName;
		this.expressCompanyCode = expressCompanyCode;
	}

	/**
	 * 平台物流ID
	 */
	@Schema(description = "平台物流ID")
	private Long expressCompanyId;

	/**
	 * 平台物流名称
	 */
	@Schema(description = "平台物流名称")
	private String expressCompanyName;

	/**
	 * 平台物流代码
	 */
	@Schema(description = "平台物流代码")
	private String expressCompanyCode;

	/**
	 * 第三方平台物流ID
	 */
	@Schema(description = "第三方平台物流ID")
	private Long thirdExpressCompanyId;

	/**
	 * 第三方平台物流名称
	 */
	@Schema(description = "第三方平台物流名称")
	private String thirdExpressCompanyName;

	/**
	 * 第三方平台物流代码
	 */
	@Schema(description = "第三方平台物流代码")
	private String thirdExpressCompanyCode;

	/**
	 * 第三方代销平台(0:微信视频号)
	 */
	@Schema(description = "第三方代销平台，0:微信视频号")
	@NotNull
	private SellPlatformType sellPlatformType;
}