package com.wanmi.sbc.setting.api.request.platformaddress;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

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
public class PlatformAddressBatchAddRequest extends BaseRequest {
	private static final long serialVersionUID = -5164751914232147941L;

	/**
	 * 地址
	 */
	@Schema(description = "地址")
	List<PlatformAddressAddRequest> platformAddressAddRequests;
}