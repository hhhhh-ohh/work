package com.wanmi.sbc.customer.api.response.store;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据店铺ids查询任意（包含已删除）店铺列表response</p>
 * Created by of628-wenzhi on 2018-09-12-下午5:23.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListStoreByIdsResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺列表
     */
    @Schema(description = "店铺列表")
    private List<StoreVO> storeVOList;
}
