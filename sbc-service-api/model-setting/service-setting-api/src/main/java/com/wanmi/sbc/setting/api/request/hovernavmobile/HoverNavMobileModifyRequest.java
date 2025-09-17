package com.wanmi.sbc.setting.api.request.hovernavmobile;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.setting.bean.dto.HoverNavMobileItemDTO;
import com.wanmi.sbc.setting.bean.enums.UsePageType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * <p>移动端悬浮导航栏修改参数</p>
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
public class HoverNavMobileModifyRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Schema(description = "编号", hidden = true)
    private Long storeId;

    /**
     * 应用页面
     */
    @Schema(description = "应用页面")
    private List<UsePageType> usePages;

    /**
     * 导航项
     */
    @Schema(description = "导航项")
    @Valid
    private List<HoverNavMobileItemDTO> navItems;

}