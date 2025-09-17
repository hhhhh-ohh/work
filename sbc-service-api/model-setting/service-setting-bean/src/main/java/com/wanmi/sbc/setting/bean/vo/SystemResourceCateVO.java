package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>平台素材资源分类VO</p>
 * @author lq
 * @date 2019-11-05 16:14:55
 */
@Schema
@Data
public class SystemResourceCateVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 素材资源分类id
	 */
	@Schema(description = "素材资源分类id")
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
	 * 分类名称
	 */
	@Schema(description = "分类名称")
	private String cateName;

	/**
	 * 父分类ID
	 */
	@Schema(description = "父分类ID")
	private Long cateParentId;

	/**
	 * 分类图片
	 */
	@Schema(description = "分类图片")
	private String cateImg;

	/**
	 * 分类层次路径,例1|01|001
	 */
	@Schema(description = "分类层次路径,例1|01|001")
	private String catePath;

	/**
	 * 分类层级
	 */
	@Schema(description = "分类层级")
	private Integer cateGrade;

	/**
	 * 拼音
	 */
	@Schema(description = "拼音")
	private String pinYin;

	/**
	 * 简拼
	 */
	@Schema(description = "简拼")
	private String spinYin;

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
	 * 排序
	 */
	@Schema(description = "排序")
	private Integer sort;

	/**
	 * 是否默认,0:否1:是
	 */
	@Schema(description = "是否默认,0:否1:是")
	 private DefaultFlag isDefault;

}