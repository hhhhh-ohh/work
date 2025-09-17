package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>会员标签VO</p>
 * @author zhanglingke
 * @date 2019-10-14 11:19:11
 */
@Schema
@Data
public class CustomerTagVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 标签名称
	 */
	@Schema(description = "标签名称")
	private String name;

	/**
	 * 会员人数
	 */
	@Schema(description = "会员人数")
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
	private String createPerson;

}