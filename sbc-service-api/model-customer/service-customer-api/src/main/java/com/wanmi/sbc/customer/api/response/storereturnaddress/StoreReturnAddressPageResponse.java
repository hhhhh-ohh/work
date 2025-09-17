package com.wanmi.sbc.customer.api.response.storereturnaddress;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.customer.bean.vo.StoreReturnAddressVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>店铺退货地址表分页结果</p>
 * @author dyt
 * @date 2020-11-02 11:38:39
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreReturnAddressPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 店铺退货地址表分页结果
     */
    @Schema(description = "店铺退货地址表分页结果")
    private MicroServicePage<StoreReturnAddressVO> storeReturnAddressVOPage;
}
