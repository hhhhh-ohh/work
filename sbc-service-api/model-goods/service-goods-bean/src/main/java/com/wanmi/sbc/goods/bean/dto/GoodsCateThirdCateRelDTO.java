package com.wanmi.sbc.goods.bean.dto;

import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>平台类目和第三方平台类目映射VO</p>
 * @author 
 * @date 2020-08-18 19:51:55
 */
@Schema
@Data
public class GoodsCateThirdCateRelDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 平台类目主键
	 */
	@Schema(description = "平台类目主键")
	@NotNull
	private Long cateId;

	/**
	 * 第三方平台类目主键
	 */
	@Schema(description = "第三方平台类目主键")
	@NotNull
	private Long thirdCateId;

	/**
	 * 第三方渠道(0，linkedmall)
	 */
	@Schema(description = "第三方渠道(0，linkedmall)")
	@NotNull
	private ThirdPlatformType thirdPlatformType;

}