package com.wanmi.sbc.message.api.request.storenoticesend;

import com.wanmi.sbc.message.bean.enums.StoreNoticeSendStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>商家公告状态修改参数</p>
 * @author 马连峰
 * @date 2022-07-04 10:56:58
 */
@Schema
@Data
public class StoreNoticeSendModifyStatusRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	@NotNull
	private Long id;

	/**
	 * 发送状态
	 */
	@Schema(description = "发送状态")
	@NotNull
	private StoreNoticeSendStatus sendStatus;
}
