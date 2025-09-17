package com.wanmi.sbc.setting.api.request.platformaddress;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.setting.bean.enums.AddrLevel;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

/**
 * <p>平台地址信息新增参数</p>
 * @author dyt
 * @date 2020-03-30 14:39:57
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformAddressAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 地址id
	 */
	@Schema(description = "地址id")
	@NotBlank
	@Length(max=50)
	private String addrId;

	/**
	 * 地址名称
	 */
	@Schema(description = "地址名称")
	@NotBlank
	@Length(max=200)
	private String addrName;

	/**
	 * 父地址ID
	 */
	@Schema(description = "父地址ID")
	@NotBlank
	@Length(max=50)
	private String addrParentId;

	/**
	 * 地址层级(0-省级;1-市级;2-区县级;3-乡镇或街道级)
	 */
	@Schema(description = "地址层级(0-省级;1-市级;2-区县级;3-乡镇或街道级)")
	@NotNull
	private AddrLevel addrLevel;

    /**
     * 排序号
     */
    @Schema(description = "排序号")
    private Integer sortNo;

	/**
	 * 坐标
	 */
	@Schema(description = "坐标")
	private String center;
}