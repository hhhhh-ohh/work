package com.wanmi.sbc.customer.api.request.points;

import com.wanmi.sbc.common.base.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>会员积分明细新增参数</p>
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPointsDetailImportAddRequest extends BaseRequest {

    @Schema(description = "会员积分明细")
    private List<List<CustomerPointsDetailAddRequest>> customerPointsDetailAddRequestList;
}
