package com.wanmi.sbc.goods.api.request.groupongoodsinfo;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponGoodsByGrouponActivityIdAndGoodsInfoIdRequest extends BaseRequest {

    private static final long serialVersionUID = 3994409084636422257L;

    /**
     * 拼团活动ID
     */
    @Schema(description = "拼团活动ID")
    @NotBlank
    private String grouponActivityId;

    /**
     * SKU编号
     */
    @Schema(description = "SKU编号")
    @NotBlank
    private String goodsInfoId;

}