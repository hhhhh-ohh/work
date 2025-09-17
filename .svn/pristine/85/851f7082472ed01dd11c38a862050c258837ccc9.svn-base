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
 * <p>根据店铺ids查询店铺自定义字段response</p>
 * Created by daiyitian on 2020/12/22.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StorePartColsListByIdsResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺列表
     */
    @Schema(description = "店铺列表")
    private List<StoreVO> storeVOList;
}
