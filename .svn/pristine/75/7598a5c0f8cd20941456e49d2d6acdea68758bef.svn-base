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
 * <p>系统日志VO</p>
 * @author guanfl
 * @date 2020-04-21 14:57:15
 */
@Schema
@Data
public class OperateDataLogVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	@Schema(description = "自增主键")
	private Long id;

	/**
	 * 操作内容
	 */
	@Schema(description = "操作内容")
	private String operateContent;

	/**
	 * 操作标识
	 */
	@Schema(description = "操作标识")
	private String operateId;

	/**
	 * 操作前数据
	 */
	@Schema(description = "操作前数据")
	private String operateBeforeData;

	/**
	 * 操作后数据
	 */
	@Schema(description = "操作后数据")
	private String operateAfterData;

	/**
	 * 操作人账号
	 */
	@Schema(description = "操作人账号")
	private String operateAccount;

	/**
	 * 操作人名称
	 */
	@Schema(description = "操作人名称")
	private String operateName;

	/**
	 * 操作时间
	 */
	@Schema(description = "操作时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime operateTime;

	/**
	 * 删除标记
	 */
	@Schema(description = "删除标记")
	private DeleteFlag delFlag;

}