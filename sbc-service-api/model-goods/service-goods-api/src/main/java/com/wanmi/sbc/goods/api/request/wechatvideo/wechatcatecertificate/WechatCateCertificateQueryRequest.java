package com.wanmi.sbc.goods.api.request.wechatvideo.wechatcatecertificate;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>微信类目资质通用查询请求参数</p>
 * @author 
 * @date 2022-04-14 10:13:05
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatCateCertificateQueryRequest extends BaseQueryRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 批量查询-idList
	 */
	@Schema(description = "批量查询-idList")
	private List<Long> idList;

	/**
	 * id
	 */
	@Schema(description = "id")
	private Long id;

	/**
	 * 微信类目id
	 */
	@Schema(description = "微信类目id")
	private Long cateId;


}