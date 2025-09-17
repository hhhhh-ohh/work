package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>运营计划与短信关联VO</p>
 * @author dyt
 * @date 2020-01-10 11:12:50
 */
@Schema
@Data
public class CustomerPlanSmsVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 标识
	 */
	@Schema(description = "标识")
	private Long id;

	/**
	 * 签名id
	 */
	@Schema(description = "签名id")
	private Long signId;

    /**
     * 签名名称s
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

	/**
	 * 计划id
	 */
	@Schema(description = "计划id")
	private Long planId;

}