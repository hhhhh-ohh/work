package com.wanmi.sbc.setting.api.request.systemresource;

import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * <p>批量删除平台素材资源请求参数</p>
 * @author lq
 * @date 2019-11-05 16:14:27
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceDelByIdListRequest extends SettingBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-素材资源IDList
	 */
	@Schema(description = "批量删除-素材资源IDList")
	@NotEmpty
	private List<Long> resourceIds;

	/**
	 * 店铺标识
	 */
	@Schema(description = "店铺标识")
	private Long storeId;
}