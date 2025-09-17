package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.common.enums.CompanySourceType;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class StoreBycompanySourceType implements Serializable {
    @Schema(description = "商家来源类型 0:商城入驻 1:linkMall初始化")
    private CompanySourceType companySourceType;
}
