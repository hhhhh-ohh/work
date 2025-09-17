package com.wanmi.sbc.goods.api.request.standard;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-07
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardGoodsRelByGoodsIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -3927156903628272769L;

    /**
     * 商品库id
     */
    @Schema(description = "商品库id")
    @NotEmpty
    private List<String> standardIds;
}
