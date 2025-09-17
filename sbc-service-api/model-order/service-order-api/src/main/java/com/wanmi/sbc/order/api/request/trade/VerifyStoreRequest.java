package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wanggang
 * @createDate: 2018/12/3 10:06
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema
public class VerifyStoreRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID集合
     */
    @Schema(description = "店铺ID集合")
    private List<Long> storeIds;
}
