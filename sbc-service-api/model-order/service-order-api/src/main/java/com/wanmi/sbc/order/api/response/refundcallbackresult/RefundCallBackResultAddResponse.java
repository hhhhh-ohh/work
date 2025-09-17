package com.wanmi.sbc.order.api.response.refundcallbackresult;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>退款回调结果新增参数</p>
 * @author lvzhenwei
 * @date 2020-07-01 17:34:23
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundCallBackResultAddResponse extends BasicResponse {
	private static final long serialVersionUID = 1L;

	/**
	 * 新增成功返回主键ID
	 */
	@Schema(description = "ID")
	private String id;
}