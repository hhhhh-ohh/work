package com.wanmi.sbc.customer.api.request.wechatvideo;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import lombok.*;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>单个删除视频带货申请请求参数</p>
 * @author zhaiqiankun
 * @date 2022-04-12 16:39:06
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatVideoStoreAuditDelByIdRequest extends CustomerBaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Integer id;
}
