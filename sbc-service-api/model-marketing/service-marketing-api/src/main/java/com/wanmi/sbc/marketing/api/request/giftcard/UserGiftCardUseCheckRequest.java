package com.wanmi.sbc.marketing.api.request.giftcard;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.vo.UserGiftCardUseCheckVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>用户礼品卡使用验证请求参数</p>
 * @author 吴瑞
 * @date 2022-12-12 09:45:09
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGiftCardUseCheckRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品详情
	 */
	@Schema(description = "礼品卡预估使用明细")
	@NotEmpty
	@Valid
	private List<UserGiftCardUseCheckVO> checkVOList;

	/**
	 * 用户Id
	 */
	@Schema(description = "用户Id")
	private String customerId;


}