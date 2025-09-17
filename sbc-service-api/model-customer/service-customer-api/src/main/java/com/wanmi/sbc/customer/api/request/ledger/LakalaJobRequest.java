package com.wanmi.sbc.customer.api.request.ledger;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author xuyunpeng
 * @className LakaLaJobRequest
 * @description
 * @date 2022/7/10 12:28 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LakalaJobRequest extends BaseRequest {
    private static final long serialVersionUID = -3628807715602209572L;

    @Schema(description = "参数")
    private String param;
}
