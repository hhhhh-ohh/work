package com.wanmi.sbc.vas.api.request.recommend.recommendpositionconfiguration;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionOpenFlag;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RecommendPositionConfigurationModifyIsOpenRequest
 * @description
 * @Author lvzhenwei
 * @Date 2020/11/17 16:43
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendPositionConfigurationModifyIsOpenRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;

    /**
     * 坑位开关，0：关闭；1：开启
     */
    @Schema(description = "坑位开关，0：关闭；1：开启")
    private PositionOpenFlag isOpen;

}
