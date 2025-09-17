package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>店铺校验结果</p>
 * Created by of628-wenzhi on 2018-09-18-下午9:59.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreCheckResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * true|false:有效|无效,只要有一个失效，则返回false
     */
    @Schema(description = "店铺校验结果")
    private Boolean result;
}
