package com.wanmi.sbc.goods.api.request.goodstemplate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * <p>GoodsTemplate修改参数</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTemplateModifyRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Schema(description = "id")
	@NotNull
	private Long id;

	/**
	 * 名称
	 */
	@Schema(description = "名称")
	@NotBlank
	private String name;

	/**
	 * 展示位置 0:顶部 1:底部 2:全选
	 */
	@Schema(description = "展示位置 0:顶部 1:底部 2:全选")
	@NotNull
	private Integer position;

	/**
	 * 顶部内容
	 */
	@Schema(description = "顶部内容")
	private String topContent;

	/**
	 * 底部内容
	 */
	@Schema(description = "底部内容")
	private String downContent;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

	/**
	 * 店铺Id
	 */
	@Schema(description = "店铺Id", hidden = true)
	private Long storeId;

	@Override
	public void checkParam() {
		if (Objects.equals(Constants.ZERO,position) && StringUtils.isBlank(topContent)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		if (Objects.equals(Constants.ONE,position) && StringUtils.isBlank(downContent)){
			throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
		}
		if (Objects.equals(Constants.TWO,position)){
			if (StringUtils.isBlank(topContent) || StringUtils.isBlank(downContent)) {
				throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
			}
		}
	}
}
