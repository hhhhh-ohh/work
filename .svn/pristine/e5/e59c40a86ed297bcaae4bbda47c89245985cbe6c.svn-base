package com.wanmi.sbc.setting.api.request.expresscompany;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>物流公司修改参数</p>
 * @author lq
 * @date 2019-11-05 16:10:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressCompanyModifyRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID,自增
	 */
	@Schema(description = "主键ID,自增")
	@Max(9223372036854775807L)
	private Long expressCompanyId;

	/**
	 * 物流公司名称
	 */
	@Schema(description = "物流公司名称")
	@Length(max=125)
	private String expressName;

	/**
	 * 物流公司代码
	 */
	@Schema(description = "物流公司代码")
	@Length(max=255)
	private String expressCode;

	/**
	 * 是否是常用物流公司 0：否 1：是
	 */
	@Schema(description = "是否是常用物流公司 0：否 1：是")
	private Integer isChecked;

	/**
	 * 是否是用户新增 0：否 1：是
	 */
	@Schema(description = "是否是用户新增 0：否 1：是")
	private Integer isAdd;

	/**
	 * 删除标志 默认0：未删除 1：删除
	 */
	@Schema(description = "删除标志 默认0：未删除 1：删除")
	private DeleteFlag delFlag;
}