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
 * <p>查询账期内有效店铺，自动关联5条信息response</p>
 * Created by of628-wenzhi on 2018-09-12-下午7:18.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListStoreByNameResponse extends BasicResponse {


    private static final long serialVersionUID = 1L;

    /**
     * 店铺列表
     */
    @Schema(description = "店铺列表")
    private List<StoreVO> storeVOList;
}
