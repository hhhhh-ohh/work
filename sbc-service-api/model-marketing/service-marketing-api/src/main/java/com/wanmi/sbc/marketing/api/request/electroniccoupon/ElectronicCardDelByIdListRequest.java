package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>批量删除电子卡密表请求参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCardDelByIdListRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-卡密IdList
	 */
	@Schema(description = "批量删除-卡密IdList")
	@NotEmpty
	private List<String> idList;
}
