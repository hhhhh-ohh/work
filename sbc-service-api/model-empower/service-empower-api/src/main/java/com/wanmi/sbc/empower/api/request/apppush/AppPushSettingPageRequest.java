package com.wanmi.sbc.empower.api.request.apppush;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;
import com.wanmi.sbc.empower.bean.enums.AppPushPlatformType;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>消息推送配置分页查询请求参数</p>
 * @author 韩伟
 * @date 2021-04-01 16:36:29
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPushSettingPageRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-主键List
	 */
	@Schema(description = "批量查询-主键List")
	private List<Integer> idList;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Integer id;

	/**
	 * 推送平台类型：0：友盟
	 */
	@Schema(description = "推送平台类型：0：友盟")
	private AppPushPlatformType platformType;

	/**
	 * 安卓应用的Appkey
	 */
	@Schema(description = "安卓应用的Appkey")
	private String androidKeyId;

	/**
	 * 安卓应用的Message Secret
	 */
	@Schema(description = "安卓应用的Message Secret")
	private String androidMsgSecret;

	/**
	 * 安卓应用的服务器秘钥
	 */
	@Schema(description = "安卓应用的服务器秘钥")
	private String androidKeySecret;

	/**
	 * IOS应用的Appkey
	 */
	@Schema(description = "IOS应用的Appkey")
	private String iosKeyId;

	/**
	 * IOS应用的服务器秘钥
	 */
	@Schema(description = "IOS应用的服务器秘钥")
	private String iosKeySecret;

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