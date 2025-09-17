package com.wanmi.sbc.setting.api.request.systemresourcecate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>平台素材资源分类列表查询请求参数</p>
 * @author lq
 * @date 2019-11-05 16:14:55
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceCateListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-素材资源分类idList
	 */
	@Schema(description = "批量查询-素材资源分类idList")
	private List<Long> cateIdList;

	/**
	 * 批量查询-素材资源父分类idList
	 */
	@Schema(description = "批量查询-素材资源父分类cateParentIds")
	private List<Long> cateParentIds;

	@Schema(description = "分类层次路径,例1|01|001")
	private  String likeCatePath;


	@Schema(description = "排除素材资源分类id")
	private  Long notCateId;

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
	 * 搜索条件:创建时间开始
	 */
	@Schema(description = "搜索条件:创建时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建时间截止
	 */
	@Schema(description = "搜索条件:创建时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 搜索条件:更新时间开始
	 */
	@Schema(description = "搜索条件:更新时间开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新时间截止
	 */
	@Schema(description = "搜索条件:更新时间截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除")
	private DeleteFlag delFlag;


	/**
	 * 是否默认,0:否1:是
	 */
	@Schema(description = "是否默认,0:否1:是")
	 private DefaultFlag isDefault;

}