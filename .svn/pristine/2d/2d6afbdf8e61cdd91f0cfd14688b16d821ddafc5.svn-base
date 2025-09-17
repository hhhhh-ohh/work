package com.wanmi.sbc.goods.api.request.goodstemplate;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

/**
 * <p>GoodsTemplate新增参数</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTemplateAddRequest extends BaseRequest {
	private static final long serialVersionUID = 1L;

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
	 * 店铺Id
	 */
	@Schema(description = "店铺Id", hidden = true)
	private Long storeId;

	/**
	 * 是否删除标志 0：否，1：是
	 */
	@Schema(description = "是否删除标志 0：否，1：是", hidden = true)
	private DeleteFlag delFlag;

	/**
	 * 创建人
	 */
	@Schema(description = "创建人", hidden = true)
	private String createPerson;

	/**
	 * 更新人
	 */
	@Schema(description = "更新人", hidden = true)
	private String updatePerson;

}