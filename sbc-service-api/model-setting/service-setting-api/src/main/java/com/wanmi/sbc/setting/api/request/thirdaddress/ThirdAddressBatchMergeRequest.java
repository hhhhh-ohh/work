package com.wanmi.sbc.setting.api.request.thirdaddress;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.setting.bean.dto.ThirdAddressDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>第三方地址合并保存操作</p>
 * @author dyt
 * @date 2020-08-14 13:41:44
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThirdAddressBatchMergeRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;


	/**
	 * 第三方地址数据
	 */
	@Schema(description = "第三方地址数据")
	@NotEmpty
	private List<ThirdAddressDTO> thirdAddressList;
}