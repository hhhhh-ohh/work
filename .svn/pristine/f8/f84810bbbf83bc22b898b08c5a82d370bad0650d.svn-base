package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>根据店铺id查询任意（包含已删除）店铺基本信息request</p>
 * Created by of628-wenzhi on 2018-09-12-下午2:46.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreListByStoreIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 店铺id集合
     */
    @Schema(description = "店铺id集合")
    @NotNull
    private List<Long> storeIds;
}
