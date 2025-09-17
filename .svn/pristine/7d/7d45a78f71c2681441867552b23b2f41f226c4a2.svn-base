package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>系统短信配置VO</p>
 * @author lq
 * @date 2019-11-05 16:13:47
 */
@Schema
@Data
public class SysSmsVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	private String smsId;

	/**
	 * 接口地址
	 */
	@Schema(description = "接口地址")
	private String smsUrl;

	/**
	 * 名称
	 */
	@Schema(description = "名称")
	private String smsName;

	/**
	 * SMTP密码
	 */
	@Schema(description = "SMTP密码")
	private String smsPass;

	/**
	 * 网关
	 */
	@Schema(description = "网关")
	private String smsGateway;

	/**
	 * 是否开启(0未开启 1已开启)
	 */
	@Schema(description = "是否开启(0未开启 1已开启)")
	private Integer isOpen;

	/**
	 * createTime
	 */
	@Schema(description = "createTime")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime createTime;

	/**
	 * modifyTime
	 */
	@Schema(description = "modifyTime")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime modifyTime;

	/**
	 * smsAddress
	 */
	@Schema(description = "smsAddress")
	private String smsAddress;

	/**
	 * smsProvider
	 */
	@Schema(description = "smsProvider")
	private String smsProvider;

	/**
	 * smsContent
	 */
	@Schema(description = "smsContent")
	private String smsContent;
}