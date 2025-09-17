package com.wanmi.sbc.vas.api.request.iep.iepsetting;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个查询企业购设置请求参数</p>
 * @author 宋汉林
 * @date 2020-03-02 20:15:04
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IepSettingByIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 *  id 
	 */
	@Schema(description = " id ")
	@NotNull
	private String id;

}