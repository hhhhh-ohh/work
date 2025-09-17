package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>开关店返回店铺信息</p>
 * Created by of628-wenzhi on 2018-09-18-下午9:47.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreSwitchResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺信息
     */
    @Schema(description = "店铺信息")
    private StoreVO storeVO;
}
