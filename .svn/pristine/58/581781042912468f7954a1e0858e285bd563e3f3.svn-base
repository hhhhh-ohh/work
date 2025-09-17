package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.annotation.IsImage;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>修改店铺logo与店招信息request</p>
 * Created by of628-wenzhi on 2018-09-18-下午6:01.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreModifyLogoRequest extends CustomerBaseRequest{
    private static final long serialVersionUID = 1L;

    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    @NotNull
    private Long storeId;

    /**
     * 店铺logo
     */
    @IsImage
    @Schema(description = "店铺logo")
    private String storeLogo;

    /**
     * 店铺店招
     */
    @IsImage
    @Schema(description = "店铺店招")
    private String storeSign;
}
