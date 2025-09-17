package com.wanmi.sbc.marketing.api.request.distribution;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 下午4:18 2019/2/19
 * @Description:
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributionStoreSettingListByStoreIdsRequest extends BaseRequest {

    /**
     * 店铺id集合
     */
    @Schema(description = "店铺id集合")
    @NotNull
    private List<String> storeIds;

}
