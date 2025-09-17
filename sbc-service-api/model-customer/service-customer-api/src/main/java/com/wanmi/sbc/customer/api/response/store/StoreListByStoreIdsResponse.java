package com.wanmi.sbc.customer.api.response.store;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据id查询任意（包含已删除）店铺信息response</p>
 * Created by of628-wenzhi on 2018-09-12-下午2:56.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreListByStoreIdsResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺ID集合
     */
    @Schema(description = "店铺ID集合")
    private List<Long> longList;
}
