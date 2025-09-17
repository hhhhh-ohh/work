package com.wanmi.sbc.crm.api.request.recommendsystemconfig;

import com.wanmi.sbc.crm.api.request.CrmBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除智能推荐配置请求参数</p>
 * @author lvzhenwei
 * @date 2020-11-27 16:28:20
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendSystemConfigDelByIdRequest extends CrmBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     *  编号
     */
    @Schema(description = " 编号")
    @NotNull
    private Long id;
}
