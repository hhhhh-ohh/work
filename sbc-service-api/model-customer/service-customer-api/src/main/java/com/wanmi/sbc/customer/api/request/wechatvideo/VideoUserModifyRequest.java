package com.wanmi.sbc.customer.api.request.wechatvideo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;

import lombok.*;

import java.time.LocalDateTime;

/**
 * <p>视频号修改参数</p>
 * @author zhaiqiankun
 * @date 2022-04-02 11:43:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoUserModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@Max(9999999999L)
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

	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;


	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;


}
