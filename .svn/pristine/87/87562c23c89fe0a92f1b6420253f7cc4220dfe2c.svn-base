package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>根据ids查询未删除店铺列表request</p>
 * Created by of628-wenzhi on 2018-09-12-下午5:22.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListNoDeleteStoreByIdsRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺id集合
     */
    @Schema(description = "店铺id集合")
    private List<Long> storeIds;
}
