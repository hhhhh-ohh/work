package com.wanmi.sbc.empower.api.request.miniprogramset;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>小程序配置通用查询请求参数</p>
 * @author zhanghao
 * @date 2021-04-22 17:20:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniProgramSetQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-小程序配置主键List
	 */
	@Schema(description = "批量查询-小程序配置主键List")
	private List<Integer> idList;

	/**
	 * 小程序配置主键
	 */
	@Schema(description = "小程序配置主键")
	private Integer id;

	/**
	 * 小程序类型 0 微信小程序
	 */
	@Schema(description = "小程序类型 0 微信小程序")
	private Integer type;

	/**
	 * 备注
	 */
	@Schema(description = "备注")
	private String remark;

	/**
	 * 状态,0:未启用1:已启用
	 */
	@Schema(description = "状态,0:未启用1:已启用")
	private Integer status;

	/**
	 * 小程序AppID(应用ID)
	 */
	@Schema(description = "小程序AppID(应用ID)")
	private String appId;

	/**
	 * 小程序AppSecret(应用密钥)
	 */
	@Schema(description = "小程序AppSecret(应用密钥)")
	private String appSecret;

	/**
	 * 删除标识：0：未删除；1：已删除
	 */
	@Schema(description = "删除标识：0：未删除；1：已删除")
	private DeleteFlag delFlag;

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
	 * 创建人
	 */
	@Schema(description = "创建人")
	private String createPerson;

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
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

}