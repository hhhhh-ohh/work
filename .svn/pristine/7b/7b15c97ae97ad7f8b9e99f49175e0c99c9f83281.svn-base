package com.wanmi.sbc.message.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>接受明细</p>
 * @author zgl
 * @date 2019-12-03 15:36:05
 */
@Schema
@Data
public class SmsSendReceiveValueVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 名称
	 */
	@Schema(description = "接受名称")
	private String name;


    /**
     * 群体类型 0:系统1:自定义
     */
    @Schema(description = "群体类型 0:系统1:自定义")
	private String type;

	@Schema(description = "手机号")
    private String account;
}