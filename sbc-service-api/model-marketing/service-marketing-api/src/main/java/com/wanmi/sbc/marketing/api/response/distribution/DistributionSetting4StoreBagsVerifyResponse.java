package com.wanmi.sbc.marketing.api.response.distribution;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * <p>验证开店礼包商品状态</p>
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionSetting4StoreBagsVerifyResponse extends BasicResponse {
    private static final long serialVersionUID = 8540841095244647249L;
    @Schema(description = "验证开店礼包商品状态")
    private Boolean result;
}
