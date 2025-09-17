package com.wanmi.sbc.empower.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @className PoiVO
 * @description POI信息
 * @author    张文昌
 * @date      2021/7/15 15:35
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistrictVO implements Serializable {
	private static final long serialVersionUID = 7139624225527996708L;

	/**
	 * 名称
	 */
	@Schema(description = "名称")
	private String name;

	/**
	 * 区域编码
	 */
	@Schema(description = "区域编码")
	private String adcode;

	/**
	 * 坐标
	 */
	@Schema(description = "坐标")
	private String center;

	/**
	 * 级别
	 */
	@Schema(description = "级别")
	private String level;

	/**
	 * 父区域编码
	 */
	@Schema(description = "父区域编码")
	private String parentadcode;

	private List<DistrictVO> districts;

}