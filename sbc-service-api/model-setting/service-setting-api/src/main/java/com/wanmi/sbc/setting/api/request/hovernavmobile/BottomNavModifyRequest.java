package com.wanmi.sbc.setting.api.request.hovernavmobile;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.setting.bean.dto.BottomNavMobileItemDTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>移动端底部导航栏修改参数</p>
 *
 * @author dyt
 * @date 2020-04-29 14:28:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BottomNavModifyRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Schema(description = "编号", hidden = true)
    private Long storeId;

    /**
     * 背景色
     */
    @Schema(description = "背景色")
    @NotBlank
    private String bottomNavBackgroundColor;

    /**
     * 导航项
     */
    @Schema(description = "导航项")
    @Valid
    @NotEmpty
    private List<BottomNavMobileItemDTO> bottomNavItems;

}