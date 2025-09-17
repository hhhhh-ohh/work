package com.wanmi.sbc.customer.api.request.wechatvideo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>视频号分页查询请求参数</p>
 * @author zhaiqiankun
 * @date 2022-04-02 11:43:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoUserPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Integer> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Integer id;

	/**
	 * 推广员视频号昵称
	 */
	@Schema(description = "推广员视频号昵称")
	private String finderNickname;

	/**
	 * 推广员唯一ID
	 */
	@Schema(description = "推广员唯一ID")
	private String promoterId;

	/**
	 * 推广员openid
	 */
	@Schema(description = "推广员openid")
	private String promoterOpenId;

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
	 * 查询指定字段
	 */
	@Schema(description = "查询指定字段")
	private List<String> findFields;

}