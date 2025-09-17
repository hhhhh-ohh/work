package com.wanmi.sbc.marketing.api.request.gift;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description 根据营销id和等级id批量查询赠品明细的请求类
 * @author daiyitian
 * @date 2021/5/13 17:26
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullGiftDetailListByMarketingIdsAndLevelIdsRequest extends BaseRequest {

    private static final long serialVersionUID = 1143651789462855860L;

    @NotEmpty
    @Schema(description = "营销Id")
    private List<Long> marketingIds;

    @NotEmpty
    @Schema(description = "满赠等级id")
    private List<Long> giftLevelIds;
}
