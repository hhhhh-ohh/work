package com.wanmi.sbc.customer.api.request.store;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * <p>根据companyIds查询未删除店铺列表request</p>
 * Created by of628-wenzhi on 2018-09-12-下午5:22.
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListCompanyStoreByCompanyIdsRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 公司id集合
     */
    @Schema(description = "公司id集合")
    @NotEmpty
    private List<Long> companyIds;

    /**
     * 店铺id集合
     */
    @Schema(description = "店铺id集合")
    @NotEmpty
    private List<Long> storeIds;
}
