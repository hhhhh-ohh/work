package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>限售VO</p>
 * @author 限售记录
 * @date 2021-11-23 15:59:01
 */
@Schema
@Data
public class RestrictedVO extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 限售数量
	 */
	@Schema(description = "限售数量")
	private Long restrictedNum;

	/**
	 * 是否限售地区
	 */
	@Schema(description = "是否限售地区")
	private Boolean restrictedAddressFlag = Boolean.FALSE;

}