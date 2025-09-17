package com.wanmi.sbc.marketing.api.request.giftcard;


import com.wanmi.sbc.common.enums.DefaultFlag;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>礼品卡批量制卡生成参数</p>
 * @author 马连峰
 * @date 2022-12-08 20:38:29
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardBatchCreateRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 礼品卡Id
	 */
	@Schema(description = "礼品卡Id")
	@NotNull
	private Long giftCardId;

	/**
	 * 制卡数量
	 */
	@Schema(description = "生成数量")
	@NotNull
	@Max(10000)
	@Min(1)
	private Long createNum;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * excel导入的文件oss地址
	 */
	@Schema(description = "excel导入的文件oss地址", hidden = true)
	private String excelFilePath;

	@Schema(description = "是否导出小程序一卡一码URL，0:不导出，1：导出")
	private DefaultFlag exportMiniCodeType;

	@Schema(description = "是否导出H5一卡一码URL，0:不导出，1：导出")
	private DefaultFlag exportWebCodeType;
}