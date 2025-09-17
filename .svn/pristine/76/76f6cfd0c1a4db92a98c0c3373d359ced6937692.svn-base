package com.wanmi.sbc.goods.api.request.brand;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 根据店铺id迁移签约品牌请求结构
 * Created by daiyitian on 2018/11/02.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractBrandTransferByStoreIdRequest extends BaseRequest {

    private static final long serialVersionUID = 5857642008821449845L;

    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    @NotNull
    private Long storeId;

}
