package com.wanmi.sbc.setting.api.request.push;

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
 * <p>消息推送通用查询请求参数</p>
 * @author chenyufei
 * @date 2019-05-10 14:39:59
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppPushConfigQueryRequest extends BaseQueryRequest {

	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-消息推送配置编号List
	 */
	@Schema(description = "批量查询-消息推送配置编号List")
	private List<Long> appPushIdList;

	/**
	 * 消息推送配置编号
	 */
	@Schema(description = "消息推送配置编号")
	private Long appPushId;

	/**
	 * 消息推送配置名称
	 */
	@Schema(description = "消息推送配置名称")
	private String appPushName;

	/**
	 * 消息推送提供商  0:友盟
	 */
	@Schema(description = "消息推送提供商  0:友盟")
	private String appPushManufacturer;

	/**
	 * Android App Key
	 */
	@Schema(description = "Android App Key")
	private String androidAppKey;

	/**
	 * Android Umeng Message Secret
	 */
	@Schema(description = "Android Umeng Message Secret")
	private String androidUmengMessageSecret;

	/**
	 * Android App Master Secret
	 */
	@Schema(description = "Android App Master Secret")
	private String androidAppMasterSecret;

	/**
	 * IOS App Key
	 */
	@Schema(description = "IOS App Key")
	private String iosAppKey;

	/**
	 * IOS App Master Secret
	 */
	@Schema(description = "IOS App Master Secret")
	private String iosAppMasterSecret;

	/**
	 * 状态,0:未启用1:已启用
	 */
	@Schema(description = "状态,0:未启用1:已启用")
	private Integer status;

	/**
	 * 搜索条件:创建日期开始
	 */
	@Schema(description = "搜索条件:创建日期开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeBegin;
	/**
	 * 搜索条件:创建日期截止
	 */
	@Schema(description = "搜索条件:创建日期截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTimeEnd;

	/**
	 * 搜索条件:更新日期开始
	 */
	@Schema(description = "搜索条件:更新日期开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeBegin;
	/**
	 * 搜索条件:更新日期截止
	 */
	@Schema(description = "搜索条件:更新日期截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTimeEnd;

	/**
	 * 搜索条件:删除日期开始
	 */
	@Schema(description = "搜索条件:删除日期开始")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime delTimeBegin;
	/**
	 * 搜索条件:删除日期截止
	 */
	@Schema(description = "搜索条件:删除日期截止")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime delTimeEnd;

	/**
	 * 删除标志
	 */
	@Schema(description = "删除标志")
	private DeleteFlag delFlag;

}