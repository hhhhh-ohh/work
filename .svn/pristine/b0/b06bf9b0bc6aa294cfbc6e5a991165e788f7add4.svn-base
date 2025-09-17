package com.wanmi.sbc.crm.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>运营计划与短信关联参数</p>
 * @author dyt
 * @date 2020-01-10 11:12:50
 */
@Schema
@Data
public class CustomerPlanSmsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
     * 签名id
     */
    @Schema(description = "签名id")
    private Long signId;

    /**
     * 签名名称
     */
    @Schema(description = "签名名称")
    private String signName;

	/**
	 * 模板id
	 */
	@Schema(description = "模板id")
	private String templateCode;

	/**
	 * 模板内容
	 */
	@Schema(description = "模板内容")
	private String templateContent;
}