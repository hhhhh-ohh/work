package com.wanmi.sbc.message.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>短信用途VO</p>
 * @author zgl
 * @date 2019-12-03 15:43:37
 */
@Schema
@Data
public class SmsPurposeVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * businessType 参照com.wanmi.sbc.customer.bean.enums.SmsTemplate
	 */
	@Schema(description = "业务类型编码")
	private String businessType;

	/**
	 * 用途
	 */
	@Schema(description = "用途")
	private String purpose;

	/**
	 * 禁用标识：true已存在的用途禁用不可被选中，false可以选中
	 */
	@Schema(description = "用途")
	private Boolean disabled;
}