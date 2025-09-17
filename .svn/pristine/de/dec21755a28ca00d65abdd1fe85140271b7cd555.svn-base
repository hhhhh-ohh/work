package com.wanmi.sbc.goods.api.request.groupongoodsinfo;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponGoodsByGoodsInfoIdAndTimeRequest extends BaseRequest {

    private static final long serialVersionUID = 6654147175095672902L;

    /**
     * 拼团活动ID
     */
    @Schema(description = "拼团活动ID")
    @NotEmpty
    private List<String> goodsInfoIds;

    /**
     * 拼团活动开始时间
     */
    @Schema(description = "拼团活动开始时间")
    @NotNull
    private LocalDateTime startTime;

    /**
     * 拼团活动结束时间
     */
    @Schema(description = "拼团活动结束时间")
    @NotNull
    private LocalDateTime endTime;
}
