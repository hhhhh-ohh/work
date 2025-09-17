package com.wanmi.sbc.customer.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午2:14 2019/5/23
 * @Description:
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreNameVO extends BasicResponse {

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

}
