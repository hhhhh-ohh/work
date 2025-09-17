package com.wanmi.sbc.marketing.api.request.bookingsale;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

/**
 * <p>活动开始暂停</p>
 *
 * @author zxd
 * @date 2020-06-05 15:32:23
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingSaleStatusRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @NotNull
    private Long id;


    @Schema(description = "是否暂停 0:否 1:是")
    @NotNull
    private Integer pauseFlag;

    @Schema(description = "商铺id")
    private Long storeId;

}
