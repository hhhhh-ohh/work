package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>店铺校验请求参数</p>
 * Created by of628-wenzhi on 2018-09-18-下午9:58.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class StoreCheckRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 需要校验的店铺id集合
     */
    @Schema(description = "需要校验的店铺id集合")
    private List<Long> ids;
}
