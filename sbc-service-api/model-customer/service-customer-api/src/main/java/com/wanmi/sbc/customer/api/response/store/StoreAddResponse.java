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
 * <p>添加店铺后返回的店铺信息</p>
 * Created by of628-wenzhi on 2018-09-18-下午8:32.
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已添加的店铺信息
     */
    @Schema(description = "已添加的店铺信息")
    private StoreVO storeVO;
}
