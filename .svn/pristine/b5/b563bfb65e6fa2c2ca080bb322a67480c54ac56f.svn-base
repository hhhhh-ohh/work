package com.wanmi.sbc.message.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>站内信消息会员关联表VO</p>
 * @author xuyunpeng
 * @date 2020-01-06 11:16:04
 */
@Schema
@Data
public class MessageSendCustomerScopeVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long scopeId;

	/**
	 * 消息id
	 */
	@Schema(description = "消息id")
	private Long messageId;

	/**
	 * 关联的等级、人群、标签id
	 */
	@Schema(description = "关联的等级、人群、标签id")
	private String joinId;

	/**
	 * 关联的等级、人群名称
	 */
	private String receiveName;

	/**
	 * 指定会员时，关联人的账户
	 */
	private String receiveAccount;

}