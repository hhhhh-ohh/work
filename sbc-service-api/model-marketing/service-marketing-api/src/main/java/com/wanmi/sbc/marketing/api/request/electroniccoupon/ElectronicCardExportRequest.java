package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>电子卡密表导出查询请求参数</p>
 * @author 许云鹏
 * @date 2022-01-26 17:24:59
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCardExportRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-卡密IdList
	 */
	@Schema(description = "批量查询-卡密IdList")
	private List<String> idList;


	/**
	 * 卡券id
	 */
	@Schema(description = "卡券id")
	private Long couponId;

	/**
	 * 批次id
	 */
	@Schema(description = "批次id")
	private String recordId;

}
