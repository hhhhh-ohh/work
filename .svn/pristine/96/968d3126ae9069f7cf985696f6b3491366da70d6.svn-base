package com.wanmi.sbc.empower.api.request.minimsgcustomerrecord;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * <p>客户订阅消息信息表新增参数</p>
 * @author xufeng
 * @date 2022-08-12 10:26:16
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MiniMsgCustomerRecordListAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 微信模版ID
	 */
	@Schema(description = "微信模版ID")
	@NotEmpty
	private List<String> templateIds;

	/**
	 * 第三方用户id
	 */
	@Schema(description = "第三方用户id")
	@NotBlank
	@Length(max=36)
	private String openId;

	/**
	 * 会员Id
	 */
	@Schema(description = "会员Id")
	@Length(max=32)
	private String customerId;

}