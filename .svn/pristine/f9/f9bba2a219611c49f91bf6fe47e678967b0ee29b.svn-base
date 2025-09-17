package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className ClosePickupSettingRequest
 * @description TODO
 * @author 黄昭
 * @date 2021/10/11 17:36
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClosePickupSettingRequest extends BaseRequest {

    private static final long serialVersionUID = -6288231315730322294L;

    @Schema(description = "商家类型")
    private Integer companyType;

    @Schema(description = "修改人")
    private String updatePerson;

    @Schema(description = "店铺类型")
    private Integer storeType;
}
