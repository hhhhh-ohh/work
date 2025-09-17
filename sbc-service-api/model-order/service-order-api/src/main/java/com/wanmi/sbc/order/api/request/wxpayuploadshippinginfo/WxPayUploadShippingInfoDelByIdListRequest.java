package com.wanmi.sbc.order.api.request.wxpayuploadshippinginfo;

import com.wanmi.sbc.order.api.request.OrderBaseRequest;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>批量删除微信小程序支付发货信息请求参数</p>
 * @author 吕振伟
 * @date 2023-07-24 14:13:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxPayUploadShippingInfoDelByIdListRequest extends OrderBaseRequest {
private static final long serialVersionUID = 1L;

	/**
	 * 批量删除-主键List
	 */
	@Schema(description = "批量删除-主键List")
	@NotEmpty
	private List<Long> idList;
}
