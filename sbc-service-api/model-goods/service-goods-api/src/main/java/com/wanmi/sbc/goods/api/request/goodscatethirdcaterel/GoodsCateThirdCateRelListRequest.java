package com.wanmi.sbc.goods.api.request.goodscatethirdcaterel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>平台类目和第三方平台类目映射列表查询请求参数</p>
 * @author 
 * @date 2020-08-18 19:51:55
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCateThirdCateRelListRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Long> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 平台类目主键
	 */
	@Schema(description = "平台类目主键")
	private Long cateId;
	/**
	 * 平台类目主键
	 */
	@Schema(description = "平台类目主键")
	private List<Long> cateIdList;

	/**
	 * 第三方平台类目主键
	 */
	@Schema(description = "第三方平台类目主键")
	private Long thirdCateId;

	/**
	 * 第三方渠道(0，linkedmall)
	 */
	@Schema(description = "第三方渠道(0，linkedmall)")
	private ThirdPlatformType thirdPlatformType;

	/**
	 * 搜索条件:createTime开始
	 */
	@Schema(description = "搜索条件:createTime开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:createTime截止
	 */
	@Schema(description = "搜索条件:createTime截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 搜索条件:updateTime开始
	 */
	@Schema(description = "搜索条件:updateTime开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:updateTime截止
	 */
	@Schema(description = "搜索条件:updateTime截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除")
	private DeleteFlag delFlag;

}