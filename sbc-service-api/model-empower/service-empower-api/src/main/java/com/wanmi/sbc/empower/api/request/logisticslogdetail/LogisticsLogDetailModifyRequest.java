package com.wanmi.sbc.empower.api.request.logisticslogdetail;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>物流记录明细修改参数</p>
 * @author 宋汉林
 * @date 2021-04-15 14:57:38
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogisticsLogDetailModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Schema(description = "主键")
	@Max(9223372036854775807L)
	private Long id;

	/**
	 * 内容上海分拨中心/装件入车扫
	 */
	@Schema(description = "内容上海分拨中心/装件入车扫")
	@Length(max=128)
	private String context;

	/**
	 * 时间，原始格式
	 */
	@Schema(description = "时间，原始格式")
	@Length(max=128)
	private String time;

	/**
	 * 本数据元对应的签收状态
	 */
	@Schema(description = "本数据元对应的签收状态")
	@Max(127)
	private Integer status;

	/**
	 * 本数据元对应的行政区域的编码
	 */
	@Schema(description = "本数据元对应的行政区域的编码")
	@Length(max=10)
	private String areaCode;

	/**
	 * 本数据元对应的行政区域的名称
	 */
	@Schema(description = "本数据元对应的行政区域的名称")
	@Length(max=128)
	private String areaName;

	/**
	 * 物流记录id
	 */
	@Schema(description = "物流记录id")
	@NotBlank
	@Length(max=32)
	private String logisticsLogId;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}
