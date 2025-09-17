package com.wanmi.sbc.setting.api.request.storewechatminiprogramconfig;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>门店微信小程序配置修改参数</p>
 * @author tangLian
 * @date 2020-01-16 11:47:15
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreWechatMiniProgramConfigModifyRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String id;

	/**
	 * 微信appId
	 */
	@Schema(description = "微信appId")
	private String appId;

	/**
	 * 微信appSecret
	 */
	@Schema(description = "微信appSecret")
	private String appSecret;

	/**
	 * 门店id
	 */
	@Schema(description = "门店id")
	private Long storeId;

	/**
	 * 商家id
	 */
	@Schema(description = "商家id")
	private Long companyInfoId;

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
	 * 删除标志
	 */
	@Schema(description = "删除标志")
	private DeleteFlag delFlag;

}