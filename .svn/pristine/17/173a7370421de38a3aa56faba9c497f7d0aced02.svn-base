package com.wanmi.sbc.empower.bean.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class PoiVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	@Schema(description = "名称")
	private String name;

	/**
	 * 兴趣点类型
	 */
	@Schema(description = "兴趣点类型")
	private String type;

	/**
	 * 经纬度，格式：X,Y
	 */
	@Schema(description = "经纬度 格式：X,Y")
	private String location;

	/**
	 * 省份名称
	 */
	@Schema(description = "省份名称")
	private String pname;

	/**
	 * 省份编码
	 */
	@Schema(description = "省份编码")
	private String pcode;

	/**
	 * 城市名
	 */
	@Schema(description = "城市名")
	private String cityname;

	/**
	 * 城市编码
	 */
	@Schema(description = "城市编码")
	private String citycode;

	/**
	 * 区域名称
	 */
	@Schema(description = "区域名称")
	private String adname;

	/**
	 * 区域编码
	 */
	@Schema(description = "区域编码")
	private String adcode;

	/**
	 * 乡镇/街道
	 */
	@Schema(description = "乡镇/街道")
	private String township;

	/**
	 * 乡镇街道编码
	 */
	@Schema(description = "乡镇街道编码")
	private String towncode;

	/**
	 * 地址
	 */
	@Schema(description = "地址")
	private String address;
}