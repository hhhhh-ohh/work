package com.wanmi.sbc.crm.api.request.customertag;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.crm.api.request.CrmBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * <p>会员标签修改参数</p>
 * @author zhanglingke
 * @date 2019-10-14 11:19:11
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTagModifyRequest extends CrmBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	@Max(9223372036854775807L)
	@NotNull
	private Long id;

	/**
	 * 标签名称
	 */
	@Schema(description = "标签名称")
	@Length(min = 1,max=20)
	private String name;

	/**
	 * 会员人数
	 */
	@Schema(description = "会员人数")
	@Max(9999999999L)
	private Integer customerNum;

	/**
	 * 删除标志位，0:未删除，1:已删除
	 */
	@Schema(description = "删除标志位，0:未删除，1:已删除")
	private DeleteFlag delFlag;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人")
	@Length(max=50)
	private String createPerson;

}