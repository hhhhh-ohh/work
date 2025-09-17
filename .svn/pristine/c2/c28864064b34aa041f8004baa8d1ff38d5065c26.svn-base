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
 * <p>店铺签约和商家信息修改response</p>
 * Created by of628-wenzhi on 2018-09-18-下午10:04.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreContractModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 修改后的店铺信息
     */
    @Schema(description = "修改后的店铺信息")
    private StoreVO storeVO;
}
