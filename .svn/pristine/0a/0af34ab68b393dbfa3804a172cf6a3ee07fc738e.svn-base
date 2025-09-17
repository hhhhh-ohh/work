package com.wanmi.sbc.marketing.api.request.electroniccoupon;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuyunpeng
 * @className ElectronicCouponUpdateBindRequest
 * @description
 * @date 2022/6/15 2:56 PM
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicCouponUpdateBindRequest implements Serializable {

    private static final long serialVersionUID = -9015581436406121065L;

    /**
     * 解绑卡券ids
     */
    @Schema(description = "解绑卡券ids")
    private List<Long> unBindingIds;

    /**
     * 绑定卡券ids
     */
    @Schema(description = "绑定卡券ids")
    private List<Long> bindingIds;
}
