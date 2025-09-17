package com.wanmi.sbc.marketing.api.request.communitysku;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * @author bob
 * @className UpdateSalesRequest
 * @description 社区团购库存
 * @date 2023/7/28 00:08
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSalesRequest extends BaseRequest {



    @Valid
    @NotEmpty
    private List<UpdateSalesDTO> updateSalesDTOS;

    @NotNull
    private Boolean addFlag;

    @Schema
    @Data
    public static class UpdateSalesDTO {
        /**
         * 库存数
         */
        @Schema(description = "库存数")
        @Min(0)
        private Long stock;

        /**
         * 商品skuId
         */
        @Schema(description = "商品skuId")
        private String goodsInfoId;

        /***
         * 活动ID
         */
        @Schema(description = "活动ID")
        private String activityId;
    }
}
