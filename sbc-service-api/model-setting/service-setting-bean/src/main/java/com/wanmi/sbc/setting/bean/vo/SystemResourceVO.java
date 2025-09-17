package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ResourceType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>平台素材资源VO</p>
 * @author lq
 * @date 2019-11-05 16:14:27
 */
@Schema
@Data
public class SystemResourceVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 素材资源ID
	 */
	@Schema(description = "素材资源ID")
	private Long resourceId;

	/**
	 * 资源类型(0:图片,1:视频)
	 */
	@Schema(description = "资源类型(0:图片,1:视频)")
	private ResourceType resourceType;

	/**
	 * 素材分类ID
	 */
	@Schema(description = "素材分类ID")
	private Long cateId;

	/**
	 * 店铺标识
	 */
	@Schema(description = "店铺标识")
	private Long storeId;

	/**
	 * 商家标识
	 */
	@Schema(description = "商家标识")
	private Long companyInfoId;

	/**
	 * 素材KEY
	 */
	@Schema(description = "素材KEY")
	private String resourceKey;

	/**
	 * 素材名称
	 */
	@Schema(description = "素材名称")
	private String resourceName;

	/**
	 * 素材地址
	 */
	@Schema(description = "素材地址")
	private String artworkUrl;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除")
	private DeleteFlag delFlag;

	/**
	 * oss服务器类型，对应system_config的config_type
	 */
	@Schema(description = "oss服务器类型，对应system_config的config_type")
	private String serverType;

	/**
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer sort;

}