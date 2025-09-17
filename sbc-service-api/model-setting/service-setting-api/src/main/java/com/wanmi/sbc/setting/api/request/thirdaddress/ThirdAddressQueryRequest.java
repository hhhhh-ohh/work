package com.wanmi.sbc.setting.api.request.thirdaddress;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.setting.bean.enums.AddrLevel;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>第三方地址映射表通用查询请求参数</p>
 * @author dyt
 * @date 2020-08-14 13:41:44
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdAddressQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-第三方地址主键List
	 */
	@Schema(description = "批量查询-第三方地址主键List")
	private List<String> idList;

	/**
	 * 第三方地址主键
	 */
	@Schema(description = "第三方地址主键")
	private String id;

	/**
	 * 第三方地址编码id
	 */
	@Schema(description = "第三方地址编码id")
	private String thirdAddrId;

	/**
	 * 批量查询-第三方地址主键List
	 */
	@Schema(description = "批量查询-第三方地址编码id")
	private List<String> thirdAddrIdList;

	/**
	 * 第三方父级地址编码id
	 */
	@Schema(description = "第三方父级地址编码id")
	private String thirdParentId;

	/**
	 * 地址名称
	 */
	@Schema(description = "地址名称")
	private String addrName;

	/**
	 * 地址层级(0-省级;1-市级;2-区县级;3-乡镇或街道级)
	 */
	@Schema(description = "地址层级(0-省级;1-市级;2-区县级;3-乡镇或街道级)")
	private AddrLevel level;

	/**
	 * 第三方标志 0:likedMall 1:京东
	 */
	@Schema(description = "第三方标志 0:likedMall 1:京东")
	private ThirdPlatformType thirdFlag;

	/**
	 * 平台地址id
	 */
	@Schema(description = "平台地址id")
	private String platformAddrId;

	/**
	 * 批量查询-平台地址id
	 */
	@Schema(description = "批量查询-平台地址id")
	private List<String> platformAddrIdList;

	/**
	 * 为空平台地址id
	 */
	@Schema(description = "为空平台地址id")
	private Boolean emplyPlatformAddrId;

	/**
	 * 非地址名称
	 */
	@Schema(description = "非地址名称")
	private String notAddrName;

	/**
	 * 删除标志
	 */
	@Schema(description = "删除标志")
	private DeleteFlag delFlag;
}