package com.wanmi.sbc.marketing.api.request.bookingsalegoods;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>单个删除预售商品信息请求参数</p>
 * @author dany
 * @date 2020-06-05 10:51:35
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleGoodsDelByIdRequest extends BaseRequest {
private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @NotNull
    private Long id;
}
