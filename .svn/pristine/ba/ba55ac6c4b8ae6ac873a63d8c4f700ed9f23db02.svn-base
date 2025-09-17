package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>会员标签关联VO</p>
 * @author dyt
 * @date 2019-11-12 14:49:08
 */
@Schema
@Data
public class CustomerTagRelVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@Schema(description = "主键id")
	private Long id;

	/**
	 * 会员id
	 */
	@Schema(description = "会员id")
	private String customerId;

	/**
	 * 标签id
	 */
	@Schema(description = "标签id")
	private Long tagId;

    /**
     * 标签id
     */
    @Schema(description = "标签名称")
    private String tagName;
}