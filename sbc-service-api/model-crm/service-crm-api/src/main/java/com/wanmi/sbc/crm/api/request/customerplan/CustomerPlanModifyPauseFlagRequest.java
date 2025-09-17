package com.wanmi.sbc.crm.api.request.customerplan;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p> 人群运营计划修改参数</p>
 * @author dyt
 * @date 2020-01-07 17:07:02
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPlanModifyPauseFlagRequest extends CrmBaseRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 标识
	 */
	@Schema(description = "标识")
	@NotNull
	private Long id;

    /**
     * 创建人
     */
    @Schema(description = "更新人", hidden = true)
    private String updatePerson;

    /**
     * 是否暂停 0:开启1:暂停
     */
    @Schema(description = "是否暂停 0:开启1:暂停")
    @NotNull
    private Boolean pauseFlag;

}