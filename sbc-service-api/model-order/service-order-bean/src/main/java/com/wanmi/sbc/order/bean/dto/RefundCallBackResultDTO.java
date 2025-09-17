package com.wanmi.sbc.order.bean.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.order.bean.enums.PayCallBackType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>支付回调结果实体类</p>
 * @author lvzhenwei
 * @date 2020-07-01 17:34:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RefundCallBackResultDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 退单号
	 */
	@Schema(description = "退单号")
	private String businessId;

	/**
	 * 回调结果xml内容
	 */
	@Schema(description = "回调xml内容")
	private String resultXml;

	/**
	 * 回调结果内容
	 */
	@Schema(description = "回调xml内容：解密")
	private String resultContext;

	/**
	 * 结果状态，0：待处理；1:处理中 2：处理成功；3：处理失败
	 */
	@Schema(description = "结果状态，0：待处理；1:处理中 2：处理成功；3：处理失败")
	private PayCallBackResultStatus resultStatus;

	/**
	 * 处理失败次数
	 */
	@Schema(description = "处理失败次数")
	private Integer errorNum;

	/**
	 * 支付方式，0：微信；1：支付宝；2：银联
	 */
	@Schema(description = "支付方式，0：微信；1：支付宝；2：银联")
	private PayCallBackType payType;

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

}