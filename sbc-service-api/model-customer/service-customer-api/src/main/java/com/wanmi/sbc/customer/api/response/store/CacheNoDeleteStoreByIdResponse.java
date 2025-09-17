package com.wanmi.sbc.customer.api.response.store;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.StoreCacheVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据id查询未删除店铺信息response</p>
 * Created by of628-wenzhi on 2018-09-12-下午2:56.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CacheNoDeleteStoreByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺信息
     */
    @Schema(description = "店铺信息")
    private StoreCacheVO storeVO;
}
