package com.wanmi.sbc.customer.api.response.store;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>根据商家id查询店铺信息response</p>
 * Created by of628-wenzhi on 2018-09-12-下午2:56.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreByCompanyInfoIdResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺信息
     */
    @Schema(description = "店铺信息")
    private StoreVO storeVO;
}
