package com.wanmi.sbc.marketing.api.request.gift;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description 根据营销id查询赠品明细的请求类
 * @author daiyitian
 * @date 2021/5/13 17:26
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullGiftDetailListByMarketingIdRequest{

    @NotNull
    @Schema(description = "营销Id")
    private Long marketingId;
}
