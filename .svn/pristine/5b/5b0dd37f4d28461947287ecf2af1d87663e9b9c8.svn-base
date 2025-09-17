package com.wanmi.sbc.customer.api.request.wechatvideo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

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
public class VideoUserModifyFieldRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id - 集合
	 */
	@Schema(description = "id - 集合")
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


	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;


	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 删除标识,0:未删除1:已删除
	 */
	@Schema(description = "删除标识,0:未删除1:已删除")
	private Integer delFlag;


	/**
	 * 修改条件字段
	 */
	@Schema(description = "修改条件字段")
	private List<String> whereFields;

	/**
	 * 修改数据字段
	 */
	@Schema(description = "修改数据字段")
	private List<String> updateFields;

}
