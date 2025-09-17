package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>消息推送VO</p>
 * @author chenyufei
 * @date 2019-05-10 14:39:59
 */
@Schema
@Data
public class AppPushConfigVO extends BasicResponse {

	private static final long serialVersionUID = 1L;

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
	 * 创建日期
	 */
	@Schema(description = "创建日期")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 更新日期
	 */
	@Schema(description = "更新日期")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除日期
	 */
	@Schema(description = "删除日期")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime delTime;

	/**
	 * 删除标志
	 */
	@Schema(description = "删除标志")
	private DeleteFlag delFlag;

}