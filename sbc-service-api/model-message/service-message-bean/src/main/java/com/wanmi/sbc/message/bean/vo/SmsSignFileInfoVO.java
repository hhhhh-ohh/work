package com.wanmi.sbc.message.bean.vo;

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
 * <p>短信签名文件信息VO</p>
 * @author lvzhenwei
 * @date 2019-12-04 14:19:35
 */
@Schema
@Data
public class SmsSignFileInfoVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private Long id;

	/**
	 * 短信签名id
	 */
	@Schema(description = "短信签名id")
	private Long smsSignId;

	/**
	 * 文件路径
	 */
	@Schema(description = "文件路径")
	private String fileUrl;

	/**
	 * 文件名称
	 */
	@Schema(description = "文件名称")
	private String fileName;

	/**
	 * 删除标识，0：未删除，1：已删除
	 */
	@Schema(description = "删除标识，0：未删除，1：已删除")
	private DeleteFlag delFlag;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

}