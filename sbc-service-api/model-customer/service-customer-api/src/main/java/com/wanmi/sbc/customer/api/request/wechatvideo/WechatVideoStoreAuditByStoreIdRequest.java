package com.wanmi.sbc.customer.api.request.wechatvideo;

import com.wanmi.sbc.common.base.BaseRequest;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


/**
 * <p>单个查询视频带货申请请求参数</p>
 * @author zhaiqiankun
 * @date 2022-04-12 16:39:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoStoreAuditByStoreIdRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 店铺id
	 */
	@Schema(description = "店铺id", hidden = true)
	private Long storeId;

	/**
	 * 查询指定字段
	 */
	@Schema(description = "查询指定字段", hidden = true)
	private List<String> findFields;

}