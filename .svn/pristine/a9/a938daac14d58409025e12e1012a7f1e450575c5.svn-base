package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.enums.SellPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 平台和第三方代销平台物流公司映射详情DTO
 * @author malianfeng
 * @date 2022/4/26 20:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpressCompanyThirdRelDetailVO implements Serializable {

	private static final long serialVersionUID = 4326855945437375997L;


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
	private SellPlatformType sellPlatformType;
}