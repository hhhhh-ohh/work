package com.wanmi.sbc.pickupsetting.request;

import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author wur
 * @className PickupSettingBatchQueryState
 * @description 批量查询自提点信息
 * @date 2022/4/19 17:00
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PickupSettingBatchQueryStateRequest extends ListNoDeleteStoreByIdsRequest {

    @Schema(description = "视频号渠道标识")
    private Boolean isChannelsFlag = Boolean.FALSE;
}