package com.wanmi.sbc.marketing.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>社区拼团区域设置表VO</p>
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@Schema
@Data
public class CommunityRegionSettingVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 区域id
	 */
	@Schema(description = "区域id")
	private Long regionId;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id")
	private Long storeId;

	/**
	 * 区域名称
	 */
	@Schema(description = "区域名称")
	private String regionName;

	/**
	 * 省市区名称列表
	 */
	@Schema(description = "省市区名称列表")
	private List<String> areaIdList;

	/**
	 * 省市区名称列表
	 */
	@Schema(description = "省市区名称列表")
	private List<String> areaNameList;

	/**
	 * 自提点Id列表
	 */
	@Schema(description = "自提点Id列表")
	private List<String> pickupPointIdList;

	/**
	 * 自提点名称列表
	 */
	@Schema(description = "自提点名称列表")
	private List<String> pickupPointNameList;
}