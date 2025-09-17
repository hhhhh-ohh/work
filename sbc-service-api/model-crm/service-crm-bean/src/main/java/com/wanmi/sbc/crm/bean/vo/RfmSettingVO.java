package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.crm.bean.enums.Period;
import com.wanmi.sbc.crm.bean.enums.RFMType;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>rfm参数配置VO</p>
 * @author zhanglingke
 * @date 2019-10-14 14:33:42
 */
@Schema
@Data
public class RfmSettingVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 参数
	 */
	@Schema(description = "参数")
	private Integer param;

	/**
	 * 得分
	 */
	@Schema(description = "得分")
	private Integer score;

	/**
	 * 参数类型：0:R,1:F,2:M
	 */
	@Schema(description = "参数类型：0:R,1:F,2:M")
	private RFMType type;

	/**
	 * 统计周期：0:近一个月，1:近3个月，2:近6个月，3:近一年
	 */
	@Schema(description = "统计周期：0:近一个月，1:近3个月，2:近6个月，3:近一年")
	private Period period;

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

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	@JsonSerialize(using = CustomLocalDateTimeSerializer.class)
	@JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
	private LocalDateTime updateTime;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人")
	private String updatePerson;

	/**
	 * 删除标识,0:未删除，1:已删除
	 */
	@Schema(description = "删除标识,0:未删除，1:已删除")
	private DeleteFlag delFlag;

}