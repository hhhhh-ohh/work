package com.wanmi.sbc.marketing.api.request.bookingsale;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除预售信息请求参数</p>
 * @author dany
 * @date 2020-06-05 10:47:21
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleDelByIdRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;

    /**
     * 商户id
     */
    @Schema(description = "商户id")
    private Long storeId;
}
