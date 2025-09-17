package com.wanmi.sbc.setting.api.request.country;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>国家地区通用查询请求参数</p>
 * @author chenli
 * @date 2021-04-27 16:10:00
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformCountryQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = -7601792530895816920L;

	/**
	 * 批量查询-主键ID,自增List
	 */
	@Schema(description = "批量查询-主键ID,自增List")
	private List<Long> countryIdList;

	/**
	 * 主键ID,自增
	 */
	@Schema(description = "主键ID,自增")
	private Long id;

	/**
	 * 国家地区名称
	 */
	@Schema(description = "国家地区名称")
	private String name;

	/**
	 * 国家地区简称
	 */
	@Schema(description = "国家地区简称")
	private String shortName;

}